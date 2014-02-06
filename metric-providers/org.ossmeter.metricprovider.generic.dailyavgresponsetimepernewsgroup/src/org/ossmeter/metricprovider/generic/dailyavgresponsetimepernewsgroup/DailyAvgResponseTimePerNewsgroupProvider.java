package org.ossmeter.metricprovider.generic.dailyavgresponsetimepernewsgroup;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.ossmeter.metricprovider.generic.dailyavgresponsetimepernewsgroup.model.DailyAverageThreadResponseTime;
import org.ossmeter.metricprovider.generic.dailyavgresponsetimepernewsgroup.model.DailyNewsgroupData;
import org.ossmeter.metricprovider.requestreplyclassification.RequestReplyClassificationMetricProvider;
import org.ossmeter.metricprovider.requestreplyclassification.model.NewsgroupArticlesData;
import org.ossmeter.metricprovider.requestreplyclassification.model.Rrc;
import org.ossmeter.metricprovider.threads.ThreadsMetricProvider;
import org.ossmeter.metricprovider.threads.model.ArticleData;
import org.ossmeter.metricprovider.threads.model.ArticleDataComparator;
import org.ossmeter.metricprovider.threads.model.CurrentDate;
import org.ossmeter.metricprovider.threads.model.Threads;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.communicationchannel.nntp.NntpUtil;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.googlecode.pongo.runtime.Pongo;

public class DailyAvgResponseTimePerNewsgroupProvider implements IHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.generic.dailyavgresponsetimepernewsgroup";

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
			System.err.println("Metric: dailyavgresponsetimepernewsgroup failed to retrieve " + 
								"the two transient metrics it needs!");
			System.exit(-1);
		}

		Threads usedThreads = 
				((ThreadsMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		
		Rrc usedClassifier = 
				((RequestReplyClassificationMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));

		CurrentDate cd = usedThreads.getDate().first();
		Date currentDate = null;
		try {currentDate = new Date(cd.getDate());} 
		catch (ParseException e) {e.printStackTrace();}

		Map<String, String> articleReplyRequest = new HashMap<String, String>();
		for (NewsgroupArticlesData article: usedClassifier.getNewsgroupArticles()) {
			articleReplyRequest.put(article.getUrl()+article.getArticleNumber(), article.getClassificationResult());
		}

		DailyAverageThreadResponseTime dailyAverageThreadResponseTime = new DailyAverageThreadResponseTime();

		long sumOfDurations = 0;
		int threadsConsidered = 0,
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
			SortedSet<ArticleData> sortedArticleSet = new TreeSet<ArticleData>(new ArticleDataComparator());
			sortedArticleSet.addAll(articleSet);

			String firstMessageTime = null;
	        Iterator<ArticleData> iterator = sortedArticleSet.iterator();
			boolean first=true,
					cont=true;

			while ((cont)&&(iterator.hasNext())) {
				ArticleData article = iterator.next();
				lastUrl_name = article.getUrl_name();
				String responseReply = articleReplyRequest.get(article.getUrl_name()+article.getArticleNumber());
				if (first) firstMessageTime = article.getDate();
				if (responseReply.equals("Reply")) cont=false;
				if ((!first)&&(responseReply.equals("Reply"))) {
					java.util.Date firstResponseDate = NntpUtil.parseDate(article.getDate());
					if (currentDate.compareTo(firstResponseDate)==0) {
						long duration = computeDurationInSeconds(firstMessageTime, article.getDate());
						sumOfDurations += duration;
						threadsConsidered++;
//						System.err.println("firstMessageTime: " + firstMessageTime + "\t" +
//								"firstResponseTime: " + article.getDate() + "\t" + 
//								"duration: " + duration + "\t" + 
//								"avg: " + computeAverageDuration(sumOfDurations, threadsConsidered));
					}
				}
				first=false;
			}
			threadId++;
		}

		if (threadsConsidered>0)
			dailyAverageThreadResponseTime.getNewsgroups().add(prepareNewsgroupData(lastUrl_name, sumOfDurations, threadsConsidered));

//		System.err.println(time(System.currentTimeMillis() - startTime) + "\tdaily_new");
		return dailyAverageThreadResponseTime;
	}

	private static final long SECONDS_DAY = 24 * 60 * 60;

	private long computeDurationInSeconds(String firstMessageTimeString, String firstResponseTimeString) {
		java.util.Date javaFirstMessageTime = NntpUtil.parseDate(firstMessageTimeString);
//		Date firstMessageTime = new Date(javaFirstMessageTime);
		java.util.Date javaFirstResponseTime = NntpUtil.parseDate(firstResponseTimeString);
//		Date firstResponseTime  = new Date(javaFirstResponseTime);
//		System.err.println(" --> firstMessageTime: "+ javaFirstMessageTime + "\t" + 
//							"firstResponseTime: " + javaFirstResponseTime);
		return Date.duration(javaFirstMessageTime, javaFirstResponseTime) / 1000;
	}


	private String computeAverageDuration(long sumOfDurations, int threads) {
		String formatted = null;
		if (threads>0) {
			long avgDuration = sumOfDurations/threads;
			int days = (int) (avgDuration / SECONDS_DAY);
			long lessThanDay = (avgDuration % SECONDS_DAY);
			formatted = days + ":" + 
					DurationFormatUtils.formatDuration(lessThanDay*1000, "HH:mm:ss:SS");
		} else {
			formatted = 0 + ":" + 
					DurationFormatUtils.formatDuration(0, "HH:mm:ss:SS");
		}
		return formatted;
	}

	private DailyNewsgroupData prepareNewsgroupData(String url_name, long sumOfDurations, int threads) {
//		System.out.println("PrepareNewsgroupData:\tsumOfDurations: " + sumOfDurations + "  threads: " + threads);
		DailyNewsgroupData dailyNewsgroupData = new DailyNewsgroupData();
		dailyNewsgroupData.setUrl_name(url_name);
		dailyNewsgroupData.setThreadsConsidered(threads);
		dailyNewsgroupData.setAvgResponseTime(computeAverageDuration(sumOfDurations, threads));
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
		return "avgresponsetimepernewsgroup";
	}

	@Override
	public String getFriendlyName() {
		return "Average Thread Response Time Per Day Per Newsgroup";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the average time in which the community " +
			   "responds to open threads per day for each newsgroup separately." + 
			   "Format: dd:HH:mm:ss:SS, where dd=days, HH:hours, mm=minutes, ss:seconds, SS=milliseconds.";
	}
}
