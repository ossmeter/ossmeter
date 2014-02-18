package org.ossmeter.metricprovider.threadsrequestsreplies;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.ossmeter.metricprovider.requestreplyclassification.RequestReplyClassificationMetricProvider;
import org.ossmeter.metricprovider.requestreplyclassification.model.NewsgroupArticlesData;
import org.ossmeter.metricprovider.requestreplyclassification.model.Rrc;
import org.ossmeter.metricprovider.threads.ThreadsMetricProvider;
import org.ossmeter.metricprovider.threads.model.ArticleData;
import org.ossmeter.metricprovider.threads.model.ArticleDataComparator;
import org.ossmeter.metricprovider.threads.model.Threads;
import org.ossmeter.metricprovider.threadsrequestsreplies.model.CurrentDate;
import org.ossmeter.metricprovider.threadsrequestsreplies.model.ThreadStatistics;
import org.ossmeter.metricprovider.threadsrequestsreplies.model.ThreadsRR;
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
				RequestReplyClassificationMetricProvider.class.getCanonicalName());

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

		if (uses.size()!=2) {
			System.err.println("Metric: " + getIdentifier() + " failed to retrieve " + 
								"the two transient metrics it needs!");
			System.exit(-1);
		}

		Threads usedThreads = 
				((ThreadsMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		
		Rrc usedClassifier = 
				((RequestReplyClassificationMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));

		org.ossmeter.metricprovider.threads.model.CurrentDate threadDate = usedThreads.getDate().first();
		
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
		for (NewsgroupArticlesData article: usedClassifier.getNewsgroupArticles()) {
			articleReplyRequest.put(article.getUrl()+article.getArticleNumber(), 
										article.getClassificationResult());
		}

		int threadId = 1,
			threadSize = 0;
		String lastUrl_name = "";
		while ((threadSize>0)||(threadId==1)) {
			threadSize = 0;
			HashSet<ArticleData> articleSet = new HashSet<ArticleData>();
			Iterable<ArticleData> articleDataIt = usedThreads.getArticles().find(ArticleData.THREADID.eq(threadId));
			for (ArticleData article: articleDataIt) {
				articleSet.add(article);
				threadSize++;
			}
			SortedSet<ArticleData> sortedArticleSet = new TreeSet<ArticleData>(new ArticleDataComparator());
			sortedArticleSet.addAll(articleSet);

			String firstMessageTime = null;
	        Iterator<ArticleData> iterator = sortedArticleSet.iterator();
			boolean first=true,
					cont=true,
					isFirstRequest=true;

			while ((cont)&&(iterator.hasNext())) {
				ArticleData article = iterator.next();
				lastUrl_name = article.getUrl_name();
				String responseReply = articleReplyRequest.get(article.getUrl_name()+article.getArticleNumber());
				if (first) firstMessageTime = article.getDate();
				if (responseReply.equals("Reply")) cont=false;
				if ((first)&&(responseReply.equals("Reply"))) isFirstRequest=false;
				if ((!first)&&(responseReply.equals("Reply"))) {
					
					ThreadStatistics thread = new ThreadStatistics();
					thread.setUrl_name(lastUrl_name);
					thread.setFirstRequest(isFirstRequest);
					thread.setThreadId(threadId);
					thread.setAnswered(true);
					thread.setResponseDate(article.getDate());
					long duration = computeDurationInSeconds(firstMessageTime, article.getDate());
					thread.setResponseDurationSec(duration);
					db.getThreads().add(thread);
					
//					System.err.println("threadId: " + threadId + "\t" +
//							"firstMessageTime: " + firstMessageTime + "\t" +
//							"firstResponseTime: " + article.getDate() + "\t" + 
//							"duration: " + duration);
				}
				first=false;
			}
			if (cont&&(!first)) {
				ThreadStatistics thread = new ThreadStatistics();
				thread.setUrl_name(lastUrl_name);
				thread.setFirstRequest(isFirstRequest);
				thread.setThreadId(threadId);
				thread.setAnswered(false);
				db.getThreads().add(thread);
				
//				System.err.println("threadId: " + threadId + "\t" +
//						"firstMessageTime: " + firstMessageTime + "\t" +
//						"unanswered");
			}

			threadId++;
			db.sync();
		}

	}

	private long computeDurationInSeconds(String firstMessageTimeString, String firstResponseTimeString) {
		java.util.Date javaFirstMessageTime = NntpUtil.parseDate(firstMessageTimeString);
//		Date firstMessageTime = new Date(javaFirstMessageTime);
		java.util.Date javaFirstResponseTime = NntpUtil.parseDate(firstResponseTimeString);
//		Date firstResponseTime  = new Date(javaFirstResponseTime);
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
