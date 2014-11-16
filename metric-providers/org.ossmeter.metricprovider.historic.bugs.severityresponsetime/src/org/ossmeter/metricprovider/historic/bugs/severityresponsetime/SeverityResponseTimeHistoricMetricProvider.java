package org.ossmeter.metricprovider.historic.bugs.severityresponsetime;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.ossmeter.metricprovider.historic.bugs.severityresponsetime.model.BugsSeverityResponseTimeHistoricMetric;
import org.ossmeter.metricprovider.historic.bugs.severityresponsetime.model.SeverityLevel;
import org.ossmeter.metricprovider.trans.bugs.requestsreplies.BugsRequestsRepliesTransMetricProvider;
import org.ossmeter.metricprovider.trans.bugs.requestsreplies.model.BugStatistics;
import org.ossmeter.metricprovider.trans.bugs.requestsreplies.model.BugsRequestsRepliesTransMetric;
import org.ossmeter.metricprovider.trans.severityclassification.SeverityClassificationTransMetricProvider;
import org.ossmeter.metricprovider.trans.severityclassification.model.BugTrackerBugsData;
import org.ossmeter.metricprovider.trans.severityclassification.model.SeverityClassificationTransMetric;
import org.ossmeter.platform.AbstractHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class SeverityResponseTimeHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = "org.ossmeter.metricprovider.historic.bugs.severityresponsetime";

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
	    return !project.getBugTrackingSystems().isEmpty();	   
	}

	@Override
	public Pongo measure(Project project) {
		BugsSeverityResponseTimeHistoricMetric metric = new BugsSeverityResponseTimeHistoricMetric();
		
		if (uses.size()==2) {

			SeverityClassificationTransMetric severityClassifier = 
					 ((SeverityClassificationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			 
			BugsRequestsRepliesTransMetric bugsRequestsReplies = 
					((BugsRequestsRepliesTransMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));

			Map<String, Map<String, Integer>> severitiesPerTracker = new HashMap<String, Map<String, Integer>>();
			Map<String, Map<String, Long>> durations = new HashMap<String, Map<String, Long>>();
			 
			 for (BugTrackerBugsData bugTrackerBugsData: severityClassifier.getBugTrackerBugs()) {
				 
				 String trackerId = bugTrackerBugsData.getBugTrackerId();
				 
				 String severity = bugTrackerBugsData.getSeverity();
				 Map<String, Integer> severityMap = retrieveOrAdd(severitiesPerTracker, trackerId);
				 addOrIncrease(severityMap, severity);
			 
				 BugStatistics bugStatistics = null;
				 Iterable<BugStatistics> bugStatisticsIt = bugsRequestsReplies.getBugs().
						 							 	   find(BugStatistics.BUGTRACKERID.eq(trackerId),
						 							 			BugStatistics.BUGID.eq(bugTrackerBugsData.getBugId()));
				 for (BugStatistics bd: bugStatisticsIt) bugStatistics = bd;

				 if ((bugStatistics!=null) && bugStatistics.getAnswered()) {
					 Map<String, Long> sevMap = retrieveOrAddLong(durations, trackerId);
					 addOrIncrease(sevMap, severity, bugStatistics.getResponseDurationSec());
				 }

			 }
			 
			 for (String bugTrackerId: severitiesPerTracker.keySet()) {
			 
				 Map<String, Integer> severityMap = severitiesPerTracker.get(bugTrackerId);
				 
				 for (String severity: severityMap.keySet()) {
					 int numberOfSeverityBugs = severityMap.get(severity);
					 SeverityLevel severityLevel = new SeverityLevel();
					 severityLevel.setBugTrackerId(bugTrackerId);
					 severityLevel.setSeverityLevel(severity);
					 severityLevel.setNumberOfBugs(numberOfSeverityBugs);
					 
					 long duration = getValueLong(durations, bugTrackerId, severity);
					 if (duration > 0) {
						 long avgResponseTime = computeAverageDuration(duration, numberOfSeverityBugs);
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
							 BugsRequestsRepliesTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "bugseveritysentiment";
	}

	@Override
	public String getFriendlyName() {
		return "Sentiment Per Bug Severity Levels Per Day";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the average sentiment, the sentiment at " +
			   "the beginning of threads and the sentiment at the end of threads " +
			   "per severity level, in bugs submitted every day.";
	}
}
