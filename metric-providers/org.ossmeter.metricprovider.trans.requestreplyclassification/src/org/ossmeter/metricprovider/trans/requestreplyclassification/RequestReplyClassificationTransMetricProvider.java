package org.ossmeter.metricprovider.trans.requestreplyclassification;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.ossmeter.metricprovider.trans.requestreplyclassification.model.BugTrackerCommentsData;
import org.ossmeter.metricprovider.trans.requestreplyclassification.model.NewsgroupArticlesData;
import org.ossmeter.metricprovider.trans.requestreplyclassification.model.RequestReplyClassificationTransMetric;
import org.ossmeter.platform.Date;
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
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;
import org.ossmeter.requestreplyclassifier.opennlptartarus.libsvm.ClassificationInstance;
import org.ossmeter.requestreplyclassifier.opennlptartarus.libsvm.Classifier;

import com.mongodb.DB;

public class RequestReplyClassificationTransMetricProvider  implements ITransientMetricProvider<RequestReplyClassificationTransMetric>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;
	protected PlatformCommunicationChannelManager communicationChannelManager;

	@Override
	public String getIdentifier() {
		return RequestReplyClassificationTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
		}
		return !project.getBugTrackingSystems().isEmpty();	   
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
	public RequestReplyClassificationTransMetric adapt(DB db) {
		return new RequestReplyClassificationTransMetric(db);
	}
	
	@Override
	public void measure(Project project, ProjectDelta projectDelta, RequestReplyClassificationTransMetric db) {
		final long startTime = System.currentTimeMillis();
		long previousTime = startTime;
		System.err.println("Started " + getIdentifier());

		BugTrackingSystemProjectDelta btspDelta = projectDelta.getBugTrackingSystemDelta();
		clearDB(db);
    	Classifier classifier = new Classifier();
		for (BugTrackingSystemDelta bugTrackingSystemDelta : btspDelta.getBugTrackingSystemDeltas()) {
			BugTrackingSystem bugTracker = bugTrackingSystemDelta.getBugTrackingSystem();
			for (BugTrackingSystemComment comment: bugTrackingSystemDelta.getComments()) {
				BugTrackerCommentsData bugTrackerCommentsData = findBugTrackerComment(db, bugTracker, comment);
				if (bugTrackerCommentsData == null) {
					bugTrackerCommentsData = new BugTrackerCommentsData();
					bugTrackerCommentsData.setBugTrackerId(bugTracker.getOSSMeterId());
					bugTrackerCommentsData.setBugId(comment.getBugId());
					bugTrackerCommentsData.setCommentId(comment.getCommentId());
					bugTrackerCommentsData.setDate(new Date(comment.getCreationTime()).toString());
					db.getBugTrackerComments().add(bugTrackerCommentsData);
				} 
				prepareBugTrackerCommentInstance(classifier, bugTracker, comment);
			}
			db.sync();
		}
		
		previousTime = printTimeMessage(startTime, previousTime, classifier.instanceListSize(),
										"prepared bug comments");
		
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
					newsgroupArticlesData.setDate(new Date(article.getDate()).toString());
					db.getNewsgroupArticles().add(newsgroupArticlesData);
				} 
				prepareNewsgroupArticleInstance(classifier, newsgroup, article);
			}
			db.sync();
		}
		
		previousTime = printTimeMessage(startTime, previousTime, classifier.instanceListSize(),
										"prepared newsgroup articles");

		classifier.classify();

		previousTime = printTimeMessage(startTime, previousTime, classifier.instanceListSize(),
										"classifier.classify() finished");

		for (BugTrackingSystemDelta bugTrackingSystemDelta : btspDelta.getBugTrackingSystemDeltas()) {
			BugTrackingSystem bugTracker = bugTrackingSystemDelta.getBugTrackingSystem();
			for (BugTrackingSystemComment comment: bugTrackingSystemDelta.getComments()) {
				BugTrackerCommentsData bugTrackerCommentsData = findBugTrackerComment(db, bugTracker, comment);
				String classificationResult = getBugTrackerCommentClass(classifier, bugTracker, comment);
				bugTrackerCommentsData.setClassificationResult(classificationResult);
			}
			db.sync();
		}

		previousTime = printTimeMessage(startTime, previousTime, classifier.instanceListSize(),
										"stored classified bug comments");

		for ( CommunicationChannelDelta communicationChannelDelta: ccpDelta.getCommunicationChannelSystemDeltas()) {
			CommunicationChannel communicationChannel = communicationChannelDelta.getCommunicationChannel();
			if (!(communicationChannel instanceof NntpNewsGroup)) continue;
			NntpNewsGroup newsgroup = (NntpNewsGroup) communicationChannel;
			for (CommunicationChannelArticle article: communicationChannelDelta.getArticles()) {
				NewsgroupArticlesData newsgroupArticlesData = findNewsgroupArticle(db, newsgroup, article);
				String classificationResult = getNewsgroupArticleClass(classifier, newsgroup, article);
				newsgroupArticlesData.setClassificationResult(classificationResult);
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

	private void prepareBugTrackerCommentInstance(Classifier classifier, BugTrackingSystem bugTracker,
			BugTrackingSystemComment comment) {
    	ClassificationInstance classificationInstance = new ClassificationInstance();
        classificationInstance.setBugTrackerId(bugTracker.getOSSMeterId());
        classificationInstance.setBugId(comment.getBugId());
        classificationInstance.setCommentId(comment.getCommentId());
        classificationInstance.setText(comment.getText());
        classifier.add(classificationInstance);
	}

	private String getBugTrackerCommentClass(Classifier classifier, BugTrackingSystem bugTracker,
			BugTrackingSystemComment comment) {
    	ClassificationInstance classificationInstance = new ClassificationInstance();
        classificationInstance.setBugTrackerId(bugTracker.getOSSMeterId());
        classificationInstance.setBugId(comment.getBugId());
        classificationInstance.setCommentId(comment.getCommentId());
        return classifier.getClassificationResult(classificationInstance);
	}

	private void prepareNewsgroupArticleInstance(Classifier classifier, NntpNewsGroup newsgroup, 
			CommunicationChannelArticle article) {
    	ClassificationInstance classificationInstance = new ClassificationInstance();
        classificationInstance.setUrl(newsgroup.getUrl());
        classificationInstance.setArticleNumber(article.getArticleNumber());
        classificationInstance.setSubject(article.getSubject());
        classificationInstance.setText(article.getText());
        classifier.add(classificationInstance);
	}

	private String getNewsgroupArticleClass(Classifier classifier, NntpNewsGroup newsgroup, 
			CommunicationChannelArticle article) {
    	ClassificationInstance classificationInstance = new ClassificationInstance();
        classificationInstance.setUrl(newsgroup.getUrl());
        classificationInstance.setArticleNumber(article.getArticleNumber());
        classificationInstance.setSubject(article.getSubject());
        return classifier.getClassificationResult(classificationInstance);
	}

	private BugTrackerCommentsData findBugTrackerComment(RequestReplyClassificationTransMetric db, 
									BugTrackingSystem bugTracker, BugTrackingSystemComment comment) {
		BugTrackerCommentsData bugTrackerCommentsData = null;
		Iterable<BugTrackerCommentsData> bugTrackerCommentsDataIt = 
				db.getBugTrackerComments().
						find(BugTrackerCommentsData.BUGTRACKERID.eq(bugTracker.getOSSMeterId()), 
								BugTrackerCommentsData.BUGID.eq(comment.getBugId()),
								BugTrackerCommentsData.COMMENTID.eq(comment.getCommentId()));
		for (BugTrackerCommentsData bcd:  bugTrackerCommentsDataIt) {
			bugTrackerCommentsData = bcd;
		}
		return bugTrackerCommentsData;
	}
	

	private NewsgroupArticlesData findNewsgroupArticle(RequestReplyClassificationTransMetric db, 
									NntpNewsGroup newsgroup, CommunicationChannelArticle article) {
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

	private void clearDB(RequestReplyClassificationTransMetric db) {
		db.getBugTrackerComments().getDbCollection().drop();
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
		return "This metric computes if each bug comment or newsgroup article is a " +
				"request of a reply.";
	}

}
