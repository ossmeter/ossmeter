package org.ossmeter.metricprovider.trans.bugs.bugmetadata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ossmeter.contentclassifier.opennlptartarus.libsvm.ClassificationInstance;
import org.ossmeter.contentclassifier.opennlptartarus.libsvm.Classifier;
import org.ossmeter.metricprovider.trans.bugs.bugmetadata.model.BugTrackerData;
import org.ossmeter.metricprovider.trans.bugs.bugmetadata.model.BugsBugMetadataTransMetric;
import org.ossmeter.metricprovider.trans.bugs.bugmetadata.model.CommentData;
import org.ossmeter.metricprovider.trans.sentimentclassification.SentimentClassificationTransMetricProvider;
import org.ossmeter.metricprovider.trans.sentimentclassification.model.BugTrackerCommentsData;
import org.ossmeter.metricprovider.trans.sentimentclassification.model.SentimentClassificationTransMetric;
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

import com.mongodb.DB;

public class BugMetadataTransMetricProvider implements ITransientMetricProvider<BugsBugMetadataTransMetric>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;

	protected MetricProviderContext context;
	
	protected List<IMetricProvider> uses;
	
	@Override
	public String getIdentifier() {
		return BugMetadataTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
	    return !project.getBugTrackingSystems().isEmpty();	   
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(SentimentClassificationTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
		this.platformBugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
	}

	@Override
	public BugsBugMetadataTransMetric adapt(DB db) {
		return new BugsBugMetadataTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, BugsBugMetadataTransMetric db) {
		//---------------------------------------
		BugTrackingSystemProjectDelta systemDelta = projectDelta.getBugTrackingSystemDelta();
//		BugTrackingSystemProjectDelta systemDelta = projectDelta.getBugTrackingSystemDelta();
		//---------------------------------------
		
		
		if (uses.size()!=1) {
			System.err.println("Metric: " + getIdentifier() + " failed to retrieve " + 
								"the transient metric it needs!");
			System.exit(-1);
		}

		SentimentClassificationTransMetric sentimentClassifier = 
				((SentimentClassificationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		//---------------------------------------
		for (BugTrackingSystemDelta delta: systemDelta.getBugTrackingSystemDeltas()) {
//		for (BugTrackingSystemDelta delta: systemDelta.getBugTrackingSystemDeltas()) {
		//---------------------------------------
		//---------------------------------------
			BugTrackingSystem bugTracker = delta.getBugTrackingSystem();
//			BugTrackingSystem bugTracker = delta.getBugTrackingSystem();
//			String bugTrackerId = bugTracker.getOSSMeterId();
		//---------------------------------------

			
			Map<String, List<String>> newBugsComments = new HashMap<String, List<String>>();
			for (BugTrackingSystemComment comment: delta.getComments()) {
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
			
			for (BugTrackingSystemComment comment: delta.getComments()) {
				Iterable<CommentData> commentIt = 
						db.getComments().find(CommentData.BUGTRACKERID.eq(bugTracker.getOSSMeterId()), 
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
				ClassificationInstance instance = prepareClassificationInstance(bugTracker, comment,
											  		positionFromThreadBeginning);
				classifier.add(instance);
				classificationInstanceIndex.put(comment.getBugId()+"_"+comment.getCommentId(), instance);
			}
			
			classifier.classify();

			storeComments(db, classifier, bugTracker, delta, classificationInstanceIndex);
			db.sync(); 

			for (BugTrackingSystemBug bug: delta.getNewBugs()) {
				storeBug(sentimentClassifier, db, bugTracker, bug);
			}
			db.sync();
			for (BugTrackingSystemBug bug: delta.getUpdatedBugs()) {
				storeBug(sentimentClassifier, db, bugTracker, bug);
			}
			db.sync();
		}
	}
	
	private ClassificationInstance prepareClassificationInstance(
			BugTrackingSystem bugTracker, BugTrackingSystemComment comment, 
			int positionFromThreadBeginning) {
		ClassificationInstance instance = new ClassificationInstance(); 
		instance.setBugTrackerId(bugTracker.getOSSMeterId());
		instance.setBugId(comment.getBugId());
		instance.setCommentId(comment.getCommentId());
		instance.setPositionFromThreadBeginning(positionFromThreadBeginning);
//		instance.setPositionFromThreadEnd(positionFromThreadEnd);
		instance.setText(comment.getText());
		return instance;
	}

	private BugTrackerData storeBug(SentimentClassificationTransMetric sentimentClassifier, 
								BugsBugMetadataTransMetric db, BugTrackingSystem bugTracker, 
								BugTrackingSystemBug bug) {
		Iterable<BugTrackerData> bugDataIt = 
				db.getBugTrackerData().find(BugTrackerData.BUGTRACKERID.eq(bugTracker.getBugTrackerType()), 
											BugTrackerData.BUGID.eq(bug.getBugId()));
		BugTrackerData bugData = null;
		for (BugTrackerData bd:  bugDataIt) bugData = bd;
		if (bugData == null) {
			bugData = new BugTrackerData();
			bugData.setBugTrackerId(bugTracker.getOSSMeterId());
			bugData.setBugId(bug.getBugId());
			bugData.setCreationTime(bug.getCreationTime().toString());
			db.getBugTrackerData().add(bugData);
		}
		bugData.setOperatingSystem(bug.getOperatingSystem());
		bugData.setPriority(bug.getPriority());
		bugData.setResolution(bug.getResolution());
		bugData.setStatus(bug.getStatus());
		if (bug instanceof BugzillaBug) {
			BugzillaBug bugzillaBug = (BugzillaBug) bug; 
			if (bugzillaBug.getLastClosed()!=null)
				bugData.setLastClosedTime(bugzillaBug.getLastClosed().toString());
		}
		updateSentimentPerThread(sentimentClassifier, db, bugData);
		return bugData;
	}

	private void updateSentimentPerThread(SentimentClassificationTransMetric sentimentClassifier, 
											BugsBugMetadataTransMetric db, BugTrackerData bugData) {
		Iterable<BugTrackerCommentsData> commentIt = 
				sentimentClassifier.getBugTrackerComments().find(
										BugTrackerCommentsData.BUGTRACKERID.eq(bugData.getBugTrackerId()), 
										BugTrackerCommentsData.BUGID.eq(bugData.getBugId()));
		int earliestCommentId=0, latestCommentId=0,
			totalSentiment=0, commentSum = 0;
		String startSentiment = "Neutral", 
			   endSentiment = "Neutral";
		boolean first = true;
		for (BugTrackerCommentsData bcd:  commentIt) {
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

	private void storeComments(BugsBugMetadataTransMetric db, Classifier classifier, BugTrackingSystem bugTracker, 
							   BugTrackingSystemDelta bugTrackingSystemDelta, 
							   Map<String, ClassificationInstance> classificationInstanceIndex) {
		for (BugTrackingSystemComment comment: bugTrackingSystemDelta.getComments()) {
			Iterable<CommentData> commentIt = 
					db.getComments().find(CommentData.BUGTRACKERID.eq(bugTracker.getOSSMeterId()), 
										  CommentData.BUGID.eq(comment.getBugId()),
										  CommentData.COMMENTID.eq(comment.getCommentId()));
			CommentData commentData = null;
			for (CommentData cd:  commentIt)
				commentData = cd;
			if (commentData == null) {
				commentData = new CommentData();
				commentData.setBugTrackerId(bugTracker.getOSSMeterId());
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
		return "This metric keeps various metadata of bug header, " +
				"i.e. priority, status, operation system and resolution.";
	}

}
