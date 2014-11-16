package org.ossmeter.metricprovider.historic.newsgroups.severity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ossmeter.metricprovider.historic.newsgroups.severity.model.NewsgroupData;
import org.ossmeter.metricprovider.historic.newsgroups.severity.model.NewsgroupsSeveritiesHistoricMetric;
import org.ossmeter.metricprovider.historic.newsgroups.severity.model.SeverityLevel;
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

public class SeverityHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = "org.ossmeter.metricprovider.historic.newsgroups.severity";

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
		NewsgroupsSeveritiesHistoricMetric metric = new NewsgroupsSeveritiesHistoricMetric();
		
		if (uses.size()==1) {

			SeverityClassificationTransMetric severityClassifier = 
					 ((SeverityClassificationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			 
			 Map<String, Integer> threadsPerNewsgroup = new HashMap<String, Integer>();
			 Map<String, Map<String, Integer>> severitiesPerNewsgroup = new HashMap<String, Map<String, Integer>>();
			 
			 for (NewsgroupThreadData newsgroupThreadData: severityClassifier.getNewsgroupThreads()) {
				 
				 String newsgroupUrl = newsgroupThreadData.getUrl();
				 
				 if (threadsPerNewsgroup.containsKey(newsgroupUrl))
					 threadsPerNewsgroup.put(newsgroupUrl, threadsPerNewsgroup.get(newsgroupUrl) + 1);
				 else 
					 threadsPerNewsgroup.put(newsgroupUrl, 1);
				 
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
			 
			 }
			 
			 for (String newsgroupUrl: threadsPerNewsgroup.keySet()) {
				 NewsgroupData newsgroupData = new NewsgroupData();
				 int numberOfThreads = threadsPerNewsgroup.get(newsgroupUrl);
				 newsgroupData.setUrl(newsgroupUrl);
				 newsgroupData.setNumberOfThreads(numberOfThreads);
				 metric.getNewsgroupData().add(newsgroupData);
			 
				 Map<String, Integer> severityMap = severitiesPerNewsgroup.get(newsgroupUrl);
				 
				 for (String severity: severityMap.keySet()) {
					 int numberOfSeverityThreads = severityMap.get(severity);
					 SeverityLevel severityLevel = new SeverityLevel();
					 severityLevel.setUrl(newsgroupUrl);
					 severityLevel.setSeverityLevel(severity);
					 severityLevel.setNumberOfThreads(numberOfSeverityThreads);
					 if ( numberOfThreads > 0 )
						 severityLevel.setPercentage(( (float) 100 * numberOfSeverityThreads ) / numberOfThreads);
					 else
						 severityLevel.setPercentage( (float) 0 );
					 metric.getSeverityLevels().add(severityLevel);
				 }
			 
			 }
			 
		}
		return metric;
		
		

	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(SeverityClassificationTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "newsgroupseveritylevels";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Newsgroup Thread Severity Levels Per Day";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of newsgroup severity levels in threads submitted every day.";
	}
}
