package org.ossmeter.metricprovider.trans.bugheadermetadata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ossmeter.contentclassifier.opennlptartarus.libsvm.ClassificationInstance;
import org.ossmeter.contentclassifier.opennlptartarus.libsvm.Classifier;
import org.ossmeter.metricprovider.trans.bugheadermetadata.model.BugData;
import org.ossmeter.metricprovider.trans.bugheadermetadata.model.BugHeaderMetadata;
import org.ossmeter.metricprovider.trans.bugheadermetadata.model.CommentData;
import org.ossmeter.metricprovider.trans.sentimentclassification.SentimentClassificationMetricProvider;
import org.ossmeter.metricprovider.trans.sentimentclassification.model.BugzillaCommentsData;
import org.ossmeter.metricprovider.trans.sentimentclassification.model.Sc;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.bugtrackingsystem.bugzilla.BugzillaBug;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemProjectDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.bts.bugzilla.Bugzilla;

import com.mongodb.DB;

public class BugHeaderMetadataMetricProvider implements ITransientMetricProvider<BugHeaderMetadata>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;

	protected MetricProviderContext context;
	
	protected List<IMetricProvider> uses;
	
	@Override
	public String getIdentifier() {
		return BugHeaderMetadataMetricProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
		for (BugTrackingSystem bugTrackingSystem: project.getBugTrackingSystems()) {
			if (bugTrackingSystem instanceof Bugzilla) return true;
		}
		return false;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(SentimentClassificationMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
		this.platformBugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
	}

	@Override
	public BugHeaderMetadata adapt(DB db) {
		return new BugHeaderMetadata(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, BugHeaderMetadata db) {
		BugTrackingSystemProjectDelta delta = projectDelta.getBugTrackingSystemDelta();
		
		if (uses.size()!=1) {
			System.err.println("Metric: " + getIdentifier() + " failed to retrieve " + 
								"the transient metric it needs!");
			System.exit(-1);
		}

		Sc sentimentClassifier = 
				((SentimentClassificationMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		for (BugTrackingSystemDelta bugTrackingSystemDelta : delta.getBugTrackingSystemDeltas()) {

			BugTrackingSystem bugTrackingSystem = bugTrackingSystemDelta.getBugTrackingSystem();
			if (!(bugTrackingSystem instanceof Bugzilla)) continue;
			Bugzilla bugzilla = (Bugzilla) bugTrackingSystem;
			
			Map<String, List<String>> newBugsComments = new HashMap<String, List<String>>();
			for (BugTrackingSystemComment comment: bugTrackingSystemDelta.getComments()) {
				List<String> newComments;
				if (newBugsComments.containsKey(comment.getBugId())) {
					newComments = newBugsComments.get(comment.getBugId());
				} else {
					newComments = new ArrayList<String>();
					newBugsComments.put(comment.getBugId(), newComments);
				}
				newComments.add(comment.getCommentId());
			}

			Classifier classifier = new Classifier();
			Map<String, ClassificationInstance> classificationInstanceIndex = 
												new HashMap<String, ClassificationInstance>();
			
			for (BugTrackingSystemComment comment: bugTrackingSystemDelta.getComments()) {
				Iterable<CommentData> commentIt = 
						db.getComments().find(CommentData.URL.eq(bugzilla.getUrl()), 
											  CommentData.PRODUCT.eq(bugzilla.getProduct()), 
											  CommentData.COMPONENT.eq(bugzilla.getComponent()),
											  CommentData.BUGID.eq(comment.getBugId()));
				int numberOfStoredComments = 0;
				for (Iterator<CommentData> it = commentIt.iterator(); it.hasNext();) {
					it.next();
					numberOfStoredComments++;
				}
				List<String> commentList = newBugsComments.get(comment.getBugId());
				int positionFromThreadBeginning = commentList.indexOf(comment.getCommentId());
//				int	positionFromThreadEnd = commentList.size() - positionFromThreadBeginning;
				positionFromThreadBeginning += numberOfStoredComments;	
				ClassificationInstance instance = prepareClassificationInstance(bugzilla, comment,
											  		positionFromThreadBeginning);
				classifier.add(instance);
				classificationInstanceIndex.put(comment.getBugId()+"_"+comment.getCommentId(), instance);
			}
			
			classifier.classify();

			storeComments(db, classifier, bugzilla, bugTrackingSystemDelta, classificationInstanceIndex);
			db.sync(); 

			for (BugTrackingSystemBug bug: bugTrackingSystemDelta.getNewBugs()) {
				storeBug(sentimentClassifier, db, bugzilla, (BugzillaBug) bug);
			}
			db.sync();
			for (BugTrackingSystemBug bug: bugTrackingSystemDelta.getUpdatedBugs()) {
				storeBug(sentimentClassifier, db, bugzilla, (BugzillaBug) bug);
			}
			db.sync();
		}
	}
	
	private ClassificationInstance prepareClassificationInstance(
			Bugzilla bugzilla, BugTrackingSystemComment comment, 
			int positionFromThreadBeginning) {
		ClassificationInstance instance = new ClassificationInstance(); 
		instance.setBugId(comment.getBugId());
		instance.setCommentId(comment.getCommentId());
		instance.setComponent(bugzilla.getComponent());
		instance.setPositionFromThreadBeginning(positionFromThreadBeginning);
//		instance.setPositionFromThreadEnd(positionFromThreadEnd);
		instance.setProduct(bugzilla.getProduct());
		instance.setText(comment.getText());
		instance.setUrl(bugzilla.getUrl());
		return instance;
	}

	private BugData storeBug(Sc sentimentClassifier, BugHeaderMetadata db, Bugzilla bugzilla, BugzillaBug bug) {
		Iterable<BugData> bugDataIt = 
				db.getBugs().find(BugData.URL.eq(bugzilla.getUrl()), 
								  BugData.PRODUCT.eq(bugzilla.getProduct()), 
								  BugData.COMPONENT.eq(bugzilla.getComponent()), 
								  BugData.BUGID.eq(bug.getBugId()));
		BugData bugData = null;
		for (BugData bd:  bugDataIt) bugData = bd;
		if (bugData == null) {
			bugData = new BugData();
			bugData.setUrl(bugzilla.getUrl());
			bugData.setProduct(bugzilla.getProduct());
			bugData.setComponent(bugzilla.getComponent());
			bugData.setBugId(bug.getBugId());
			bugData.setCreationTime(bug.getCreationTime().toString());
			db.getBugs().add(bugData);
		}
		bugData.setOperatingSystem(bug.getOperatingSystem());
		bugData.setPriority(bug.getPriority());
		bugData.setResolution(bug.getResolution());
		bugData.setStatus(bug.getStatus());
		if (bug.getLastClosed()!=null)
			bugData.setLastClosedTime(bug.getLastClosed().toString());
		updateSentimentPerThread(sentimentClassifier, db, bugData);
		return bugData;
	}

	private void updateSentimentPerThread(Sc sentimentClassifier, BugHeaderMetadata db, BugData bugData) {
		Iterable<BugzillaCommentsData> commentIt = 
				sentimentClassifier.getBugzillaComments().find(
										BugzillaCommentsData.URL.eq(bugData.getUrl()), 
										BugzillaCommentsData.PROD.eq(bugData.getProduct()), 
										BugzillaCommentsData.COMP.eq(bugData.getComponent()),
										BugzillaCommentsData.BUGID.eq(bugData.getBugId()));
		int earliestCommentId=0, latestCommentId=0,
			totalSentiment=0, commentSum = 0;
		String startSentiment = "Neutral", 
			   endSentiment = "Neutral";
		boolean first = true;
		for (BugzillaCommentsData bcd:  commentIt) {
			int cid = Integer.parseInt(bcd.getCommentId());
			String sentimentClass = bcd.getClassificationResult();
			if (first) {
				earliestCommentId = cid;
				startSentiment = sentimentClass;
				latestCommentId = cid;
				endSentiment = sentimentClass;
			}
			if (cid > latestCommentId) {
				latestCommentId = cid;
				endSentiment = sentimentClass;
			}
			if (cid < earliestCommentId) {
				earliestCommentId = cid;
				startSentiment = sentimentClass;
			}
			if (sentimentClass.equals("Positive")) 
				totalSentiment += 1;
			else if(sentimentClass.equals("Negative")) 
				totalSentiment -= 1;
			commentSum++;
			first = false;
		}
		bugData.setAverageSentiment(((float)totalSentiment)/commentSum);
		bugData.setStartSentiment(startSentiment);
		bugData.setEndSentiment(endSentiment);
		db.sync();
	}

	private void storeComments(BugHeaderMetadata db, Classifier classifier, Bugzilla bugzilla, 
							   BugTrackingSystemDelta bugTrackingSystemDelta, 
							   Map<String, ClassificationInstance> classificationInstanceIndex) {
		for (BugTrackingSystemComment comment: bugTrackingSystemDelta.getComments()) {
			Iterable<CommentData> commentIt = 
					db.getComments().find(CommentData.URL.eq(bugzilla.getUrl()), 
										  CommentData.PRODUCT.eq(bugzilla.getProduct()), 
										  CommentData.COMPONENT.eq(bugzilla.getComponent()),
										  CommentData.BUGID.eq(comment.getBugId()),
										  CommentData.COMMENTID.eq(comment.getCommentId()));
			CommentData commentData = null;
			for (CommentData cd:  commentIt)
				commentData = cd;
			if (commentData == null) {
				commentData = new CommentData();
				commentData.setUrl(bugzilla.getUrl());
				commentData.setProduct(bugzilla.getProduct());
				commentData.setComponent(bugzilla.getComponent());
				commentData.setBugId(comment.getBugId());
				commentData.setCommentId(comment.getCommentId());
				commentData.setCreationTime(comment.getCreationTime().toString());
				commentData.setCreator(comment.getCreator());
				ClassificationInstance classificationInstance = 
						classificationInstanceIndex.get(comment.getBugId()+"_"+comment.getCommentId());
				commentData.setContentClass(classifier.getClassificationResult(classificationInstance));
				db.getComments().add(commentData);
				db.sync();
			}
		}
	}

	@Override
	public String getShortIdentifier() {
		return "bugheadermetadata";
	}

	@Override
	public String getFriendlyName() {
		return "Bug Header Metadata";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric keeps various metadata of bugzilla bug header, " +
				"i.e. priority, status, operation system and resolution.";
	}

}
