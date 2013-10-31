package org.ossmeter.metricprovider.generic.avgresponsetimeperdaypernewsgroup;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.ossmeter.metricprovider.generic.avgresponsetimeperdaypernewsgroup.model.DailyNewsgroupData;
import org.ossmeter.metricprovider.generic.avgresponsetimeperdaypernewsgroup.model.DailyAverageThreadResponseTime;
import org.ossmeter.metricprovider.requestreplyclassification.RequestReplyClassificationMetricProvider;
import org.ossmeter.metricprovider.requestreplyclassification.model.NewsgroupArticlesData;
import org.ossmeter.metricprovider.requestreplyclassification.model.Rrc;
import org.ossmeter.metricprovider.threads.ThreadsMetricProvider;
import org.ossmeter.metricprovider.threads.model.ArticleData;
import org.ossmeter.metricprovider.threads.model.ArticleDataComparator;
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

public class AvgResponseTimePerDayPerNewsgroupProvider implements IHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.generic.avgresponsetimeperdaypernewsgroup";

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

	@Override
	public Pongo measure(Project project) {

		if (uses.size()!=2) {
			System.err.println("Metric: unansweredthreadsperdaypernewsgroup failed to retrieve " + 
								"the two transient metrics it needs!");
			System.exit(-1);
		}

		Threads usedThreads = 
				((ThreadsMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		
		Rrc usedClassifier = 
				((RequestReplyClassificationMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));

		HashSet<ArticleData> articleSet = new HashSet<ArticleData>();
		for (ArticleData article: usedThreads.getArticles())
			articleSet.add(article);
		SortedSet<ArticleData> sortedArticleSet = new TreeSet<ArticleData>(new ArticleDataComparator());
		sortedArticleSet.addAll(articleSet);
		
		DailyAverageThreadResponseTime dailyAverageThreadResponseTime = 
				new DailyAverageThreadResponseTime();

		String lastUrl_name = "";
		int lastThreadId = -1, threads = 0;
		long sumOfDurations = 0;
		String firstMessageTime = "", firstResponseTime = "";
		Boolean answered = false;
		for (ArticleData article: sortedArticleSet) {
			if (!article.getUrl_name().equals(lastUrl_name)) {
				if (lastUrl_name.length()>0) {
					threads++;
					sumOfDurations += computeDurationInSeconds(firstMessageTime, firstResponseTime);
					dailyAverageThreadResponseTime.getNewsgroups().add(
							prepareNewsgroupData(lastUrl_name, sumOfDurations, threads));
					
				}
				lastUrl_name = article.getUrl_name();
				lastThreadId = article.getThreadId();
				firstMessageTime = "";
				firstResponseTime = "";
				answered = false;
			} else if (article.getThreadId() != lastThreadId) {
				if (firstResponseTime.length()>0) {
					threads++;
					sumOfDurations += computeDurationInSeconds(firstMessageTime, firstResponseTime);
				}
				firstMessageTime = "";
				firstResponseTime = "";
				answered = false;
				lastThreadId = article.getThreadId();
			}
			
			if ( answered == false ) {
				Iterable<NewsgroupArticlesData> newsgroupArticlesDataIt = usedClassifier.getNewsgroupArticles().
						find(NewsgroupArticlesData.URL.eq(article.getUrl_name()), 
								NewsgroupArticlesData.ARTICLENUMBER.eq(article.getArticleNumber()));
				NewsgroupArticlesData newsgroupArticleData = null;
				for (NewsgroupArticlesData art:  newsgroupArticlesDataIt) {
					newsgroupArticleData = art;
				}
				if (newsgroupArticleData == null) {
					System.err.println("Metric: " + IDENTIFIER + "\n\t" + 
							"there is no classification for article: " + article.getArticleNumber() +
							"\t of newsgroup: " + article.getUrl_name());
					System.exit(-1);
				} else {
					if (firstMessageTime.length()==0) 
						firstMessageTime = article.getDate();
					if ((newsgroupArticleData.getClassificationResult().equals("Reply")) &&
						((firstResponseTime.length()==0) )) {
						firstResponseTime = article.getDate();
						answered = true;
					}
				}
				
			}
		}
		if (firstResponseTime.length()>0) {
			threads++;
			sumOfDurations += computeDurationInSeconds(firstMessageTime, firstResponseTime);
		}
		if (threads>0)
			dailyAverageThreadResponseTime.getNewsgroups().add(prepareNewsgroupData(lastUrl_name, sumOfDurations, threads));
		return dailyAverageThreadResponseTime;
	}
	
	private static final long SECONDS_DAY = 24 * 60 * 60;

	private long computeDurationInSeconds(String firstMessageTimeString, String firstResponseTimeString) {
		java.util.Date javaFirstMessageTime = NntpUtil.parseDate(firstMessageTimeString);
		Date firstMessageTime = new Date(javaFirstMessageTime);
		java.util.Date javaFirstResponseTime = NntpUtil.parseDate(firstResponseTimeString);
		Date firstResponseTime  = new Date(javaFirstResponseTime);
		return Date.duration(firstMessageTime, firstResponseTime) / 1000;
	}

	private DailyNewsgroupData prepareNewsgroupData(String url_name, long sumOfDurations, int threads) {
//		System.out.println("PrepareNewsgroupData:\tsumOfDurations: " + sumOfDurations + "  threads: " + threads);
		long avgDuration = sumOfDurations/threads;
		int days = (int) (avgDuration / SECONDS_DAY);
		long lessThanDay = (avgDuration % SECONDS_DAY);
		String formatted = DurationFormatUtils.formatDuration(lessThanDay*1000, "HH:mm:ss:SS");

		DailyNewsgroupData dailyNewsgroupData = new DailyNewsgroupData();
		dailyNewsgroupData.setUrl_name(url_name);
		dailyNewsgroupData.setThreadsConsidered(threads);
		dailyNewsgroupData.setAvgResponseTime(days+":"+formatted);
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
