package org.ossmeter.metricprovider.historic.bugs.severity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ossmeter.metricprovider.historic.bugs.severity.model.BugData;
import org.ossmeter.metricprovider.historic.bugs.severity.model.BugsSeveritiesHistoricMetric;
import org.ossmeter.metricprovider.historic.bugs.severity.model.SeverityLevel;
import org.ossmeter.metricprovider.trans.severityclassification.SeverityClassificationTransMetricProvider;
import org.ossmeter.metricprovider.trans.severityclassification.model.BugTrackerBugsData;
import org.ossmeter.metricprovider.trans.severityclassification.model.SeverityClassificationTransMetric;
import org.ossmeter.platform.AbstractHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class SeverityHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = "org.ossmeter.metricprovider.historic.bugs.severity";

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
		BugsSeveritiesHistoricMetric metric = new BugsSeveritiesHistoricMetric();
		
		if (uses.size()==1) {

			SeverityClassificationTransMetric severityClassifier = 
					 ((SeverityClassificationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			 
			 Map<String, Integer> bugsPerTracker = new HashMap<String, Integer>();
			 Map<String, Map<String, Integer>> severitiesPerTracker = new HashMap<String, Map<String, Integer>>();
			 
			 for (BugTrackerBugsData bugTrackerBugsData: severityClassifier.getBugTrackerBugs()) {
				 
				 String trackerId = bugTrackerBugsData.getBugTrackerId();
				 
				 if (bugsPerTracker.containsKey(trackerId))
					 bugsPerTracker.put(trackerId, bugsPerTracker.get(trackerId) + 1);
				 else 
					 bugsPerTracker.put(trackerId, 1);
				 
				 String severity = bugTrackerBugsData.getSeverity();
				  Map<String, Integer> severityMap;
				 if (severitiesPerTracker.containsKey(trackerId))
					 severityMap = severitiesPerTracker.get(trackerId);
				 else {
					 severityMap = new HashMap<String, Integer>();
					 severitiesPerTracker.put(trackerId, severityMap);
				 }
				 
				 if (severityMap.containsKey(severity))
					 severityMap.put(severity, severityMap.get(severity) + 1);
				 else
					 severityMap.put(severity, + 1);
			 
			 }
			 
			 for (String bugTrackerId: bugsPerTracker.keySet()) {
				 BugData bugData = new BugData();
				 int numberOfBugs = bugsPerTracker.get(bugTrackerId);
				 bugData.setBugTrackerId(bugTrackerId);
				 bugData.setNumberOfBugs(numberOfBugs);
				 metric.getBugData().add(bugData);
			 
				 Map<String, Integer> severityMap = severitiesPerTracker.get(bugTrackerId);
				 
				 for (String severity: severityMap.keySet()) {
					 int numberOfSeverityBugs = severityMap.get(severity);
					 SeverityLevel severityLevel = new SeverityLevel();
					 severityLevel.setBugTrackerId(bugTrackerId);
					 severityLevel.setSeverityLevel(severity);
					 severityLevel.setNumberOfBugs(numberOfSeverityBugs);
					 if ( numberOfBugs > 0 )
						 severityLevel.setPercentage(( (float) 100 * numberOfSeverityBugs ) / numberOfBugs);
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
		return "bugseveritylevels";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Bug Severity Levels Per Day";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of bug severity levels in bugs submitted every day.";
	}
}
