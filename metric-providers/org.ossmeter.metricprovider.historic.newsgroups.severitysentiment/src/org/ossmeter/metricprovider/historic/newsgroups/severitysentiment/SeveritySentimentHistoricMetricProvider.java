package org.ossmeter.metricprovider.historic.newsgroups.severitysentiment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ossmeter.metricprovider.historic.newsgroups.severitysentiment.model.NewsgroupsSeveritySentimentHistoricMetric;
import org.ossmeter.metricprovider.historic.newsgroups.severitysentiment.model.SeverityLevel;
import org.ossmeter.metricprovider.trans.newsgroups.sentiment.SentimentTransMetricProvider;
import org.ossmeter.metricprovider.trans.newsgroups.sentiment.model.NewsgroupsSentimentTransMetric;
import org.ossmeter.metricprovider.trans.newsgroups.sentiment.model.ThreadStatistics;
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

public class SeveritySentimentHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = "org.ossmeter.metricprovider.historic.newsgroups.severitysentiment";

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
		for (CommunicationChannel communicationchannel: project.getCommunicationChannels()) {
			if (communicationchannel instanceof NntpNewsGroup) return true;
		}
		return false;
	}

	@Override
	public Pongo measure(Project project) {
		NewsgroupsSeveritySentimentHistoricMetric metric = new NewsgroupsSeveritySentimentHistoricMetric();
		
		if (uses.size()==2) {

			SeverityClassificationTransMetric severityClassifier = 
					 ((SeverityClassificationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

			NewsgroupsSentimentTransMetric threadsRequestReplies = 
					((SentimentTransMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));

			 Map<String, Map<String, Integer>> sentimentAtBeginning = new HashMap<String, Map<String, Integer>>(),
					 			  			   sentimentAtEnd = new HashMap<String, Map<String, Integer>>();
			 Map<String, Map<String, Float>> sentimentAverage = new HashMap<String, Map<String, Float>>();
		 			  
			 Map<String, Map<String, Integer>> severitiesPerNewsgroup = new HashMap<String, Map<String, Integer>>();
			 
			 for (NewsgroupThreadData newsgroupThreadData: severityClassifier.getNewsgroupThreads()) {
				 
				 String newsgroupUrl = newsgroupThreadData.getUrl();
				 
				 String severity = newsgroupThreadData.getSeverity();
				 Map<String, Integer> severityMap;
				 if (severitiesPerNewsgroup.containsKey(newsgroupUrl))
					 severityMap = severitiesPerNewsgroup.get(newsgroupUrl);
				 else {
					 severityMap = new HashMap<String, Integer>();
					 severitiesPerNewsgroup.put(newsgroupUrl, severityMap);
				 }
				 
				 if (severityMap.containsKey(severity))
					 severityMap.put(severity, severityMap.get(severity) + 1);
				 else
					 severityMap.put(severity, + 1);
			 
				 ThreadStatistics threadData = null;
				 Iterable<ThreadStatistics> threadDataIt = threadsRequestReplies.getThreads().
						 						find(ThreadStatistics.URL_NAME.eq(newsgroupUrl),
						 							 ThreadStatistics.THREADID.eq(newsgroupThreadData.getThreadId()));
				 for (ThreadStatistics ts: threadDataIt) threadData = ts;
		 
				 float averageSentiment = threadData.getAverageSentiment();
				 Map<String, Float> sentAverage = retrieveOrAddFloat(sentimentAverage, newsgroupUrl);
				 addOrIncreaseFloat(sentAverage, severity, averageSentiment);
				 
				 int startSentiment = transformSentimentToInteger(threadData.getStartSentiment());
				 Map<String, Integer> sentBeginning = retrieveOrAdd(sentimentAtBeginning, newsgroupUrl);
				 addOrIncrease(sentBeginning, severity, startSentiment);
				 
				 int endSentiment = transformSentimentToInteger(threadData.getEndSentiment());
				 Map<String, Integer> sentEnd = retrieveOrAdd(sentimentAtEnd, newsgroupUrl);
				 addOrIncrease(sentEnd, severity, endSentiment);

			 }
			 
			 for (String newsgroupUrl: severitiesPerNewsgroup.keySet()) {
			 
				 Map<String, Integer> severityMap = severitiesPerNewsgroup.get(newsgroupUrl);
				 
				 for (String severity: severityMap.keySet()) {
					 int numberOfSeverityThreads = severityMap.get(severity);
					 SeverityLevel severityLevel = new SeverityLevel();
					 severityLevel.setUrl(newsgroupUrl);
					 severityLevel.setSeverityLevel(severity);
					 severityLevel.setNumberOfThreads(numberOfSeverityThreads);
					 float averageSentiment = sentimentAverage.get(newsgroupUrl).get(severity) / numberOfSeverityThreads;
					 severityLevel.setAverageSentiment(averageSentiment);
					 float sentimentAtThreadBeggining = 
							 ((float) sentimentAtBeginning.get(newsgroupUrl).get(severity)) / numberOfSeverityThreads;
					 severityLevel.setSentimentAtThreadBeggining(sentimentAtThreadBeggining);
					 float sentimentAtThreadEnd = 
							 ((float) sentimentAtEnd.get(newsgroupUrl).get(severity)) / numberOfSeverityThreads;
					 severityLevel.setSentimentAtThreadEnd(sentimentAtThreadEnd);
					 metric.getSeverityLevels().add(severityLevel);
				 }
			 
			 }
			 
		}
		return metric;
	
	}
	
	private int transformSentimentToInteger(String sentimentString) {
		 if (sentimentString.equals("Negative"))
			 return -1;
		 else if (sentimentString.equals("Positive"))
			 return 1;
		 else
			 return 0;
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
	
	private Map<String, Float> retrieveOrAddFloat(
			 Map<String, Map<String, Float>> map, String trackerId) {
		Map<String, Float> component;
		if (map.containsKey(trackerId))
			component = map.get(trackerId);
		else {
			component = new HashMap<String, Float>();
			map.put(trackerId, component);
		}
		return component;
	}
	
	private void addOrIncrease(Map<String, Integer> map, String item, int increment) {
		if (map.containsKey(item))
			map.put(item, map.get(item) + increment);
		else
			map.put(item, + increment);
	}
	
	private void addOrIncreaseFloat(Map<String, Float> map, String item, float increment) {
		if (map.containsKey(item))
			map.put(item, map.get(item) + increment);
		else
			map.put(item, + increment);
	}
	
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(SeverityClassificationTransMetricProvider.class.getCanonicalName(),
							 SentimentTransMetricProvider.class.getCanonicalName());
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
		return "Sentiment Per Thread Severity Levels Per Day";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the average sentiment, the sentiment at " +
			   "the beginning of threads and the sentiment at the end of threads " +
			   "per severity level, in newsgroup threads submitted every day.";
	}
}
