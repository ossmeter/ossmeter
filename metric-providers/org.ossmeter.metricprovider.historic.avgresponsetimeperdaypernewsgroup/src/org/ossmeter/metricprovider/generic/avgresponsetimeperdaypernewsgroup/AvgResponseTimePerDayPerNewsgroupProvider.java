package org.ossmeter.metricprovider.generic.avgresponsetimeperdaypernewsgroup;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.ossmeter.metricprovider.generic.avgresponsetimeperdaypernewsgroup.model.DailyAverageThreadResponseTime;
import org.ossmeter.metricprovider.generic.avgresponsetimeperdaypernewsgroup.model.DailyNewsgroupData;
import org.ossmeter.metricprovider.threadsrequestsreplies.ThreadsRequestsRepliesProvider;
import org.ossmeter.metricprovider.threadsrequestsreplies.model.ThreadStatistics;
import org.ossmeter.metricprovider.threadsrequestsreplies.model.ThreadsRR;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.googlecode.pongo.runtime.Pongo;

public class AvgResponseTimePerDayPerNewsgroupProvider implements IHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.generic.cumulativeavgresponsetimepernewsgroup";

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

		if (uses.size()!=1) {
			System.err.println("Metric: cumulativeavgresponsetimepernewsgroup failed to retrieve " + 
								"the transient metric it needs!");
			System.exit(-1);
		}

		ThreadsRR usedThreads = 
				((ThreadsRequestsRepliesProvider)uses.get(0)).adapt(context.getProjectDB(project));
		
		long sumOfDurations = 0;
		int threadsConsidered = 0;

		String lastUrl_name = "";
		
		for (ThreadStatistics thread: usedThreads.getThreads()) {
			lastUrl_name = thread.getUrl_name();
			if (thread.getAnswered()) {
				sumOfDurations += thread.getResponseDurationSec();
				threadsConsidered++;
			}
		}
				
		DailyAverageThreadResponseTime dailyAverageThreadResponseTime = new DailyAverageThreadResponseTime();
		
		if (threadsConsidered>0)
			dailyAverageThreadResponseTime.getNewsgroups().add(prepareNewsgroupData(lastUrl_name, sumOfDurations, threadsConsidered));

//		System.err.println(time(System.currentTimeMillis() - startTime) + "\tcumulative_new");
		return dailyAverageThreadResponseTime;
	}
	
	private static final long SECONDS_DAY = 24 * 60 * 60;
	
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
		return Arrays.asList(ThreadsRequestsRepliesProvider.class.getCanonicalName());
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
