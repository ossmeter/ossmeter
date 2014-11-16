package org.ossmeter.metricprovider.historic.newsgroups.severityresponsetime;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.ossmeter.metricprovider.historic.newsgroups.severityresponsetime.model.BugsSeverityResponseTimeHistoricMetric;
import org.ossmeter.metricprovider.historic.newsgroups.severityresponsetime.model.SeverityLevel;
import org.ossmeter.metricprovider.trans.newsgroups.threadsrequestsreplies.ThreadsRequestsRepliesTransMetricProvider;
import org.ossmeter.metricprovider.trans.newsgroups.threadsrequestsreplies.model.NewsgroupsThreadsRequestsRepliesTransMetric;
import org.ossmeter.metricprovider.trans.newsgroups.threadsrequestsreplies.model.ThreadStatistics;
import org.ossmeter.metricprovider.trans.severityclassification.SeverityClassificationTransMetricProvider;
import org.ossmeter.metricprovider.trans.severityclassification.model.NewsgroupThreadData;
import org.ossmeter.metricprovider.trans.severityclassification.model.SeverityClassificationTransMetric;
import org.ossmeter.platform.AbstractHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.googlecode.pongo.runtime.Pongo;

public class SeverityResponseTimeHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = "org.ossmeter.metricprovider.historic.newsgroups.severityresponsetime";

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
		BugsSeverityResponseTimeHistoricMetric metric = new BugsSeverityResponseTimeHistoricMetric();
		
		if (uses.size()==2) {

			SeverityClassificationTransMetric severityClassifier = 
					 ((SeverityClassificationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			 
			NewsgroupsThreadsRequestsRepliesTransMetric threadsRequestsReplies = 
					((ThreadsRequestsRepliesTransMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));

			Map<String, Map<String, Integer>> severitiesPerNewsgroup = new HashMap<String, Map<String, Integer>>();
			Map<String, Map<String, Long>> durations = new HashMap<String, Map<String, Long>>();
			 
			 for (NewsgroupThreadData newsgroupThreadData: severityClassifier.getNewsgroupThreads()) {
				 
				 String trackerId = newsgroupThreadData.getUrl();
				 
				 String severity = newsgroupThreadData.getSeverity();
				 Map<String, Integer> severityMap = retrieveOrAdd(severitiesPerNewsgroup, trackerId);
				 addOrIncrease(severityMap, severity);
			 
				 ThreadStatistics threadStatistics = null;
				 Iterable<ThreadStatistics> threadStatisticsIt = threadsRequestsReplies.getThreads().
						 							 	   find(ThreadStatistics.URL_NAME.eq(trackerId),
						 							 			 ThreadStatistics.THREADID.eq(newsgroupThreadData.getThreadId()));
				 for (ThreadStatistics ts: threadStatisticsIt) threadStatistics = ts;

				 if ((threadStatistics!=null) && threadStatistics.getAnswered()) {
					 Map<String, Long> sevMap = retrieveOrAddLong(durations, trackerId);
					 addOrIncrease(sevMap, severity, threadStatistics.getResponseDurationSec());
				 }

			 }
			 
			 for (String newsgroupUrl: severitiesPerNewsgroup.keySet()) {
			 
				 Map<String, Integer> severityMap = severitiesPerNewsgroup.get(newsgroupUrl);
				 
				 for (String severity: severityMap.keySet()) {
					 int numberOfSeverityThreads = severityMap.get(severity);
					 SeverityLevel severityLevel = new SeverityLevel();
					 severityLevel.setUrl(newsgroupUrl);
					 severityLevel.setSeverityLevel(severity);
					 severityLevel.setNumberOfThreads(numberOfSeverityThreads);
					 
					 long duration = getValueLong(durations, newsgroupUrl, severity);
					 if (duration > 0) {
						 long avgResponseTime = computeAverageDuration(duration, numberOfSeverityThreads);
						 severityLevel.setAvgResponseTime(avgResponseTime);
						 String avgResponseTimeFormatted = format(avgResponseTime);
						 severityLevel.setAvgResponseTimeFormatted(avgResponseTimeFormatted);

					 }

					 metric.getSeverityLevels().add(severityLevel);
				 }
			 
			 }
			 
		}
		return metric;
	
	}
	
	private static final long SECONDS_DAY = 24 * 60 * 60;

	private long computeAverageDuration(long sumOfDurations, int threads) {
		if (threads>0)
			return sumOfDurations/threads;
		return 0;
	}

	private String format(long avgDuration) {
		String formatted = null;
		if (avgDuration>0) {
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
			
	private Map<String, Integer> retrieveOrAdd(
								 Map<String, Map<String, Integer>> map, String trackerId) {
		 Map<String, Integer> component;
		 if (map.containsKey(trackerId))
			 component = map.get(trackerId);
		 else {
			 component = new HashMap<String, Integer>();
			 map.put(trackerId, component);
		 }
		return component;
	}

	private Map<String, Long> retrieveOrAddLong(
								Map<String, Map<String, Long>> map, String trackerId) {
		Map<String, Long> component;
		if (map.containsKey(trackerId))
			component = map.get(trackerId);
		else {
			component = new HashMap<String, Long>();
			map.put(trackerId, component);
		}
		return component;
	}
	
	private void addOrIncrease(Map<String, Integer> map, String item) {
		if (map.containsKey(item))
			map.put(item, map.get(item) + 1);
		else
			map.put(item, + 1);
	}
	
	private void addOrIncrease(Map<String, Long> map, String item, long increment) {
		if (map.containsKey(item))
			map.put(item, map.get(item) + increment);
		else
			map.put(item, increment);
	}

	private long getValueLong(Map<String, Map<String, Long>> map,	String item, String component) {
		if (!map.containsKey(item))
			return 0;
		if (!map.get(item).containsKey(component))
			return 0;
		return map.get(item).get(component);
	}
	
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(SeverityClassificationTransMetricProvider.class.getCanonicalName(),
							 ThreadsRequestsRepliesTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "newsgroupseveritysentiment";
	}

	@Override
	public String getFriendlyName() {
		return "Sentiment Per Newsgroup Thread Severity Levels Per Day";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the average sentiment, the sentiment at " +
			   "the beginning of threads and the sentiment at the end of threads " +
			   "per severity level, in newsgroup threads submitted every day.";
	}
}
