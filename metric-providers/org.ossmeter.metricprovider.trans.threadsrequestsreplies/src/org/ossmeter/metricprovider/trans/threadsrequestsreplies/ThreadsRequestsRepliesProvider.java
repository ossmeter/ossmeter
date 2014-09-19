package org.ossmeter.metricprovider.trans.threadsrequestsreplies;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.ossmeter.metricprovider.trans.requestreplyclassification.RequestReplyClassificationMetricProvider;
import org.ossmeter.metricprovider.trans.requestreplyclassification.model.NewsgroupArticlesData;
import org.ossmeter.metricprovider.trans.requestreplyclassification.model.Rrc;
import org.ossmeter.metricprovider.trans.sentimentclassification.SentimentClassificationMetricProvider;
import org.ossmeter.metricprovider.trans.sentimentclassification.model.Sc;
import org.ossmeter.metricprovider.trans.threads.ThreadsMetricProvider;
import org.ossmeter.metricprovider.trans.threads.model.ArticleData;
import org.ossmeter.metricprovider.trans.threads.model.ArticleDataComparator;
import org.ossmeter.metricprovider.trans.threads.model.ThreadData;
import org.ossmeter.metricprovider.trans.threads.model.Threads;
import org.ossmeter.metricprovider.trans.threadsrequestsreplies.model.CurrentDate;
import org.ossmeter.metricprovider.trans.threadsrequestsreplies.model.ThreadStatistics;
import org.ossmeter.metricprovider.trans.threadsrequestsreplies.model.ThreadsRR;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.communicationchannel.nntp.NntpUtil;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.mongodb.DB;

public class ThreadsRequestsRepliesProvider  implements ITransientMetricProvider<ThreadsRR>{

	protected PlatformCommunicationChannelManager communicationChannelManager;

	protected MetricProviderContext context;
	
	protected List<IMetricProvider> uses;

	@Override
	public String getIdentifier() {
		return ThreadsRequestsRepliesProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
		}
		return false;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(ThreadsMetricProvider.class.getCanonicalName(),
				RequestReplyClassificationMetricProvider.class.getCanonicalName(),
				SentimentClassificationMetricProvider.class.getCanonicalName());

	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
		this.communicationChannelManager = context.getPlatformCommunicationChannelManager();
	}

	@Override
	public ThreadsRR adapt(DB db) {
		return new ThreadsRR(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, ThreadsRR db) {
//		final long startTime = System.currentTimeMillis();
		
		db.getThreads().getDbCollection().drop();
		db.sync();

		if (uses.size()!=3) {
			System.err.println("Metric: " + getIdentifier() + " failed to retrieve " + 
								"the three transient metrics it needs!");
			System.exit(-1);
		}

		Threads usedThreads = 
				((ThreadsMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		
		Rrc usedClassifier = 
				((RequestReplyClassificationMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));

		Sc sentimentClassifier = 
				((SentimentClassificationMetricProvider)uses.get(2)).adapt(context.getProjectDB(project));
		
		org.ossmeter.metricprovider.trans.threads.model.CurrentDate threadDate = usedThreads.getDate().first();
		
		Iterable<CurrentDate> currentDateIt = db.getDate();
		CurrentDate currentDate = null;
		for (CurrentDate cd:  currentDateIt)
			currentDate = cd;
		if (currentDate != null)
			currentDate.setDate(threadDate.getDate());
		else {
			currentDate = new CurrentDate();
			currentDate.setDate(threadDate.getDate());
			db.getDate().add(currentDate);
		}
		
		Map<String, String> articleReplyRequest = new HashMap<String, String>();
		for (NewsgroupArticlesData article: usedClassifier.getNewsgroupArticles())
			articleReplyRequest.put(article.getUrl()+article.getArticleNumber(), 
										article.getClassificationResult());

		Map<String, String> articleSentiment = new HashMap<String, String>();
		for (org.ossmeter.metricprovider.trans.sentimentclassification.model.NewsgroupArticlesData 
				article: sentimentClassifier.getNewsgroupArticles())
			articleSentiment.put(article.getUrl()+article.getArticleNumber(), 
										article.getClassificationResult());

		Map<String, String> articleEmotionalDimensions = new HashMap<String, String>();
		for (org.ossmeter.metricprovider.trans.sentimentclassification.model.NewsgroupArticlesData 
				article: sentimentClassifier.getNewsgroupArticles())
			articleEmotionalDimensions.put(article.getUrl()+article.getArticleNumber(), 
										article.getEmotionalDimensions());

		for (ThreadData thread: usedThreads.getThreads()) {

			SortedSet<ArticleData> sortedArticleSet = new TreeSet<ArticleData>(new ArticleDataComparator());
			sortedArticleSet.addAll(thread.getArticles());

			String firstMessageTime = null;
	        Iterator<ArticleData> iterator = sortedArticleSet.iterator();
			boolean first=true,
					noReplyFound=true,
					isFirstRequest=true;

			String lastUrl_name = "";
			int totalSentiment = 0;
			ThreadStatistics threadStats = new ThreadStatistics();
			while (iterator.hasNext()) {
				ArticleData article = iterator.next();
				lastUrl_name = article.getUrl_name();
				String sentiment = articleSentiment.get(article.getUrl_name()+article.getArticleNumber());
				if (sentiment.equals("Positive")) 
					totalSentiment += 1;
				else if(sentiment.equals("Negative")) 
					totalSentiment -= 1;
//				String emotionalDimensions = articleEmotionalDimensions.get(article.getUrl_name()+article.getArticleNumber());
				String responseReply = articleReplyRequest.get(article.getUrl_name()+article.getArticleNumber());
				if (first) {
					threadStats.setStartSentiment(sentiment);
					firstMessageTime = article.getDate();
				}
				threadStats.setEndSentiment(sentiment);
				if ((first)&&(responseReply.equals("Reply"))) isFirstRequest=false;
				if ((!first)&&(noReplyFound)&&(responseReply.equals("Reply"))) {
					
					threadStats.setUrl_name(lastUrl_name);
					threadStats.setFirstRequest(isFirstRequest);
					threadStats.setThreadId(thread.getThreadId());
					threadStats.setAnswered(true);
					threadStats.setResponseDate(article.getDate());
					long duration = computeDurationInSeconds(firstMessageTime, article.getDate());
					threadStats.setResponseDurationSec(duration);
					db.getThreads().add(threadStats);
					
//					System.err.println("threadId: " + threadId + "\t" +
//							"firstMessageTime: " + firstMessageTime + "\t" +
//							"firstResponseTime: " + article.getDate() + "\t" + 
//							"duration: " + duration);
				}
				if (responseReply.equals("Reply")) noReplyFound=false;
				first=false;
			}
			threadStats.setAverageSentiment(((float)totalSentiment)/sortedArticleSet.size());
			if (noReplyFound&&(!first)) {
				threadStats = new ThreadStatistics();
				threadStats.setUrl_name(lastUrl_name);
				threadStats.setFirstRequest(isFirstRequest);
				threadStats.setThreadId(thread.getThreadId());
				threadStats.setAnswered(false);
				db.getThreads().add(threadStats);
				
//				System.err.println("threadId: " + threadId + "\t" +
//						"firstMessageTime: " + firstMessageTime + "\t" +
//						"unanswered");
			}
		}
		db.sync();
		
	}

	private long computeDurationInSeconds(String firstMessageTimeString, String firstResponseTimeString) {
		java.util.Date javaFirstMessageTime = NntpUtil.parseDate(firstMessageTimeString);
		java.util.Date javaFirstResponseTime = NntpUtil.parseDate(firstResponseTimeString);
//		System.err.println(" --> firstMessageTime: "+ javaFirstMessageTime + "\t" + 
//							"firstResponseTime: " + javaFirstResponseTime);
		return Date.duration(javaFirstMessageTime, javaFirstResponseTime) / 1000;
	}

	@Override
	public String getShortIdentifier() {
		return "threadsrequestsreplies";
	}

	@Override
	public String getFriendlyName() {
		return "Thread Statistics (answered?, answeredDuration";
	}

	@Override
	public String getSummaryInformation() {
		return "The metric computed for each thread whether it is answered." +
				"If yes, it also computes the response time";
	}

}
