package org.ossmeter.metricprovider.generic.unansweredthreadsperdaypernewsgroup;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ossmeter.metricprovider.generic.unansweredthreadsperdaypernewsgroup.model.DailyNewsgroupData;
import org.ossmeter.metricprovider.generic.unansweredthreadsperdaypernewsgroup.model.DailyUnansweredThreads;
import org.ossmeter.metricprovider.requestreplyclassification.RequestReplyClassificationMetricProvider;
import org.ossmeter.metricprovider.requestreplyclassification.model.NewsgroupArticlesData;
import org.ossmeter.metricprovider.requestreplyclassification.model.Rrc;
import org.ossmeter.metricprovider.threads.ThreadsMetricProvider;
import org.ossmeter.metricprovider.threads.model.ArticleData;
import org.ossmeter.metricprovider.threads.model.Threads;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.googlecode.pongo.runtime.Pongo;

public class UnsweredThreadsPerDayPerNewsgroupProvider implements IHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.generic.unansweredthreadsperdaypernewsgroup";

	protected MetricProviderContext context;
	
	/**
	 * List of MPs that are used by this MP. These are MPs who have specified that 
	 * they 'provide' data for this MP.
	 */
	protected List<IMetricProvider> uses;
	
	@Override
	public String getIdentifier() {
		return IDENTIFIER;
	}
	
	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
		}
		return false;
	}

//	private String time(long timeInMS) {
//		return DurationFormatUtils.formatDuration(timeInMS, "HH:mm:ss,SSS");
//	}
	
	@Override
	public Pongo measure(Project project) {
//		final long startTime = System.currentTimeMillis();

		if (uses.size()!=2) {
			System.err.println("Metric: unansweredthreadsperdaypernewsgroup failed to retrieve " + 
								"the two transient metrics it needs!");
			System.exit(-1);
		}

		Threads usedThreads = 
				((ThreadsMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		
		Rrc usedClassifier = 
				((RequestReplyClassificationMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));

		Map<String, String> articleReplyRequest = new HashMap<String, String>();
		for (NewsgroupArticlesData article: usedClassifier.getNewsgroupArticles()) {
			articleReplyRequest.put(article.getUrl()+article.getArticleNumber(), article.getClassificationResult());
		}

		DailyUnansweredThreads dailyUnansweredThreads = new DailyUnansweredThreads();
		
		int unansweredThreads = 0,
			threadId = 1,
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

	        Iterator<ArticleData> iterator = articleSet.iterator();
			boolean cont=true;
			while ((cont)&&(iterator.hasNext())) {
				ArticleData article = iterator.next();
				lastUrl_name = article.getUrl_name();
				String responseReply = articleReplyRequest.get(article.getUrl_name()+article.getArticleNumber());
				if (responseReply.equals("Reply")) cont=false;
			}
			if (cont) unansweredThreads++;
			threadId++;
		}

		if (unansweredThreads > 0)
			dailyUnansweredThreads.getNewsgroups().add(prepareNewsgroupData(lastUrl_name, unansweredThreads));
		
//		System.err.println(time(System.currentTimeMillis() - startTime) + "\tunanswered_new");
		return dailyUnansweredThreads;
	}

	private DailyNewsgroupData prepareNewsgroupData(String url_name, int unansweredThreads) {
		DailyNewsgroupData dailyNewsgroupData = new DailyNewsgroupData();
		dailyNewsgroupData.setUrl_name(url_name);
		dailyNewsgroupData.setNumberOfUnansweredThreads(unansweredThreads);
		return dailyNewsgroupData;
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
	}

	@Override
	public String getShortIdentifier() {
		return "unansweredthreadspernewsgroup";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Unanswered Threads Per Day Per Newsgroup";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of unanswered threads per day for each newsgroup separately.";
	}
}
