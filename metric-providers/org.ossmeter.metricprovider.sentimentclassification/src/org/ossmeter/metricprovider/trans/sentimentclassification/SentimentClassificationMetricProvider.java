package org.ossmeter.metricprovider.trans.sentimentclassification;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.time.DurationFormatUtils;

import org.ossmeter.metricprovider.trans.sentimentclassification.model.BugzillaCommentsData;
import org.ossmeter.metricprovider.trans.sentimentclassification.model.NewsgroupArticlesData;
import org.ossmeter.metricprovider.trans.sentimentclassification.model.Sc;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemProjectDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelProjectDelta;
import org.ossmeter.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.bts.bugzilla.Bugzilla;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;
import org.ossmeter.repository.model.Project;
import org.ossmeter.sentimentclassifier.opennlptartarus.libsvm.ClassificationInstance;
import org.ossmeter.sentimentclassifier.opennlptartarus.libsvm.Classifier;
import org.ossmeter.sentimentclassifier.opennlptartarus.libsvm.EmotionalDimensions;

import com.mongodb.DB;

public class SentimentClassificationMetricProvider  implements ITransientMetricProvider<Sc>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;
	protected PlatformCommunicationChannelManager communicationChannelManager;

	@Override
	public String getIdentifier() {
		return SentimentClassificationMetricProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
		for (BugTrackingSystem bugTrackingSystem: project.getBugTrackingSystems()) {
			if (bugTrackingSystem instanceof Bugzilla) return true;
		}
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
		}
		return false;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		// DO NOTHING -- we don't use anything
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Collections.emptyList();
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.platformBugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
		this.communicationChannelManager = context.getPlatformCommunicationChannelManager();
	}

	@Override
	public Sc adapt(DB db) {
		return new Sc(db);
	}
	
	@Override
	public void measure(Project project, ProjectDelta projectDelta, Sc db) {
		final long startTime = System.currentTimeMillis();
		long previousTime = startTime;
		System.err.println("Started " + getIdentifier());

		BugTrackingSystemProjectDelta btspDelta = projectDelta.getBugTrackingSystemDelta();
		clearDB(db);
    	Classifier classifier = new Classifier();
		for (BugTrackingSystemDelta bugTrackingSystemDelta : btspDelta.getBugTrackingSystemDeltas()) {
			BugTrackingSystem bugTrackingSystem = bugTrackingSystemDelta.getBugTrackingSystem();
			if (!(bugTrackingSystem instanceof Bugzilla)) continue;
			Bugzilla bugzilla = (Bugzilla) bugTrackingSystem;
			for (BugTrackingSystemComment comment: bugTrackingSystemDelta.getComments()) {
				BugzillaCommentsData bugzillaCommentsData = findBugzillaComment(db, bugzilla, comment);
				if (bugzillaCommentsData == null) {
					bugzillaCommentsData = new BugzillaCommentsData();
					bugzillaCommentsData.setUrl(bugzilla.getUrl());
					bugzillaCommentsData.setProd(bugzilla.getProduct());
					bugzillaCommentsData.setComp(bugzilla.getComponent());
					bugzillaCommentsData.setBugId(comment.getBugId());
					bugzillaCommentsData.setCommentId(comment.getCommentId());
					db.getBugzillaComments().add(bugzillaCommentsData);
				} 
				ClassificationInstance classificationInstance = prepareBugzillaCommentInstance(bugzilla, comment);
		        classifier.add(classificationInstance);
			}
			db.sync();
		}
		
		previousTime = printTimeMessage(startTime, previousTime, classifier.instanceListSize(),
										"prepared bugzilla comments");
		
		CommunicationChannelProjectDelta ccpDelta = projectDelta.getCommunicationChannelDelta();
		for ( CommunicationChannelDelta communicationChannelDelta: ccpDelta.getCommunicationChannelSystemDeltas()) {
			CommunicationChannel communicationChannel = communicationChannelDelta.getCommunicationChannel();
			if (!(communicationChannel instanceof NntpNewsGroup)) continue;
			NntpNewsGroup newsgroup = (NntpNewsGroup) communicationChannel;
			for (CommunicationChannelArticle article: communicationChannelDelta.getArticles()) {
				NewsgroupArticlesData newsgroupArticlesData = findNewsgroupArticle(db, newsgroup, article);
				if (newsgroupArticlesData == null) {
					newsgroupArticlesData = new NewsgroupArticlesData();
					newsgroupArticlesData.setUrl(newsgroup.getUrl());
					newsgroupArticlesData.setArticleNumber(article.getArticleNumber());
					db.getNewsgroupArticles().add(newsgroupArticlesData);
				} 
				ClassificationInstance classificationInstance = prepareNewsgroupArticleInstance(newsgroup, article);
				classifier.add(classificationInstance);
			}
			db.sync();
		}
		
		previousTime = printTimeMessage(startTime, previousTime, classifier.instanceListSize(),
										"prepared newsgroup articles");

		classifier.classify();

		previousTime = printTimeMessage(startTime, previousTime, classifier.instanceListSize(),
										"classifier.classify() finished");

		for (BugTrackingSystemDelta bugTrackingSystemDelta : btspDelta.getBugTrackingSystemDeltas()) {
			BugTrackingSystem bugTrackingSystem = bugTrackingSystemDelta.getBugTrackingSystem();
			if (!(bugTrackingSystem instanceof Bugzilla)) continue;
			Bugzilla bugzilla = (Bugzilla) bugTrackingSystem;
			for (BugTrackingSystemComment comment: bugTrackingSystemDelta.getComments()) {
				BugzillaCommentsData bugzillaCommentsData = findBugzillaComment(db, bugzilla, comment);
				ClassificationInstance classificationInstance = prepareBugzillaCommentInstance(bugzilla, comment);
				String classificationResult = classifier.getClassificationResult(classificationInstance);
				bugzillaCommentsData.setClassificationResult(classificationResult);
				String emotionalDimensions = EmotionalDimensions.getDimensions(classificationInstance);
				bugzillaCommentsData.setEmotionalDimensions(emotionalDimensions);
			}
			db.sync();
		}

		previousTime = printTimeMessage(startTime, previousTime, classifier.instanceListSize(),
										"stored classified bugzilla comments");

		for ( CommunicationChannelDelta communicationChannelDelta: ccpDelta.getCommunicationChannelSystemDeltas()) {
			CommunicationChannel communicationChannel = communicationChannelDelta.getCommunicationChannel();
			if (!(communicationChannel instanceof NntpNewsGroup)) continue;
			NntpNewsGroup newsgroup = (NntpNewsGroup) communicationChannel;
			for (CommunicationChannelArticle article: communicationChannelDelta.getArticles()) {
				NewsgroupArticlesData newsgroupArticlesData = findNewsgroupArticle(db, newsgroup, article);
				ClassificationInstance classificationInstance = prepareNewsgroupArticleInstance(newsgroup, article);
				String classificationResult = classifier.getClassificationResult(classificationInstance);
				newsgroupArticlesData.setClassificationResult(classificationResult);
				String emotionalDimensions = EmotionalDimensions.getDimensions(classificationInstance);
				newsgroupArticlesData.setEmotionalDimensions(emotionalDimensions);
			}
			db.sync();
		}

//		previousTime = printTimeMessage(startTime, previousTime, classifier.instanceListSize(),
//										"stored classified newsgroup articles");
 	}
	
	private long printTimeMessage(long startTime, long previousTime, int size, String message) {
		long currentTime = System.currentTimeMillis();
		System.err.println(time(currentTime - previousTime) + "\t" +
						   time(currentTime - startTime) + "\t" +
						   size + "\t" + message);
		return currentTime;
	}

	private String time(long timeInMS) {
		return DurationFormatUtils.formatDuration(timeInMS, "HH:mm:ss,SSS");
	}

	private ClassificationInstance prepareBugzillaCommentInstance(Bugzilla bugzilla,
			BugTrackingSystemComment comment) {
    	ClassificationInstance classificationInstance = new ClassificationInstance();
        classificationInstance.setUrl(bugzilla.getUrl());
        classificationInstance.setProduct(bugzilla.getProduct());
        classificationInstance.setComponent(bugzilla.getComponent());
        classificationInstance.setBugId(comment.getBugId());
        classificationInstance.setCommentId(comment.getCommentId());
        classificationInstance.setText(comment.getText());
        return classificationInstance;
	}

	private ClassificationInstance prepareNewsgroupArticleInstance(NntpNewsGroup newsgroup, CommunicationChannelArticle article) {
    	ClassificationInstance classificationInstance = new ClassificationInstance();
        classificationInstance.setUrl(newsgroup.getUrl());
        classificationInstance.setArticleNumber(article.getArticleNumber());
        classificationInstance.setSubject(article.getSubject());
        classificationInstance.setText(article.getText());
        return classificationInstance;
	}

	private BugzillaCommentsData findBugzillaComment(
			Sc db, Bugzilla bugzilla, BugTrackingSystemComment comment) {
		BugzillaCommentsData bugzillaCommentsData = null;
		Iterable<BugzillaCommentsData> bugzillaCommentsDataIt = 
				db.getBugzillaComments().
						find(BugzillaCommentsData.URL.eq(bugzilla.getUrl()), 
								BugzillaCommentsData.PROD.eq(bugzilla.getProduct()), 
								BugzillaCommentsData.COMP.eq(bugzilla.getComponent()),
								BugzillaCommentsData.BUGID.eq(comment.getBugId()),
								BugzillaCommentsData.COMMENTID.eq(comment.getCommentId()));
		for (BugzillaCommentsData bcd:  bugzillaCommentsDataIt) {
			bugzillaCommentsData = bcd;
		}
		return bugzillaCommentsData;
	}
	

	private NewsgroupArticlesData findNewsgroupArticle(
			Sc db, NntpNewsGroup newsgroup, CommunicationChannelArticle article) {
		NewsgroupArticlesData newsgroupArticlesData = null;
		Iterable<NewsgroupArticlesData> newsgroupArticlesDataIt = 
				db.getNewsgroupArticles().
						find(NewsgroupArticlesData.URL.eq(newsgroup.getUrl()), 
								NewsgroupArticlesData.ARTICLENUMBER.eq(article.getArticleNumber()));
		for (NewsgroupArticlesData nad:  newsgroupArticlesDataIt) {
			newsgroupArticlesData = nad;
		}
		return newsgroupArticlesData;
	}

	private void clearDB(Sc db) {
		db.getBugzillaComments().getDbCollection().drop();
	}

	@Override
	public String getShortIdentifier() {
		// TODO Auto-generated method stub
		return "requestreplyclassification";
	}

	@Override
	public String getFriendlyName() {
		return "Request Reply Classification";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes if each bugzilla comment or newsgroup article is a " +
				"request of a reply.";
	}

}
