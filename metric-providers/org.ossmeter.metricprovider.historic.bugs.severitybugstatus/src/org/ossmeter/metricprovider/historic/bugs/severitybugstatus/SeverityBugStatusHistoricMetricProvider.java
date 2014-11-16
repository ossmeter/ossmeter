package org.ossmeter.metricprovider.historic.bugs.severitybugstatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ossmeter.metricprovider.historic.bugs.severitybugstatus.model.BugsSeverityBugStatusHistoricMetric;
import org.ossmeter.metricprovider.historic.bugs.severitybugstatus.model.SeverityLevel;
import org.ossmeter.metricprovider.trans.bugs.bugmetadata.BugMetadataTransMetricProvider;
import org.ossmeter.metricprovider.trans.bugs.bugmetadata.model.BugData;
import org.ossmeter.metricprovider.trans.bugs.bugmetadata.model.BugsBugMetadataTransMetric;
import org.ossmeter.metricprovider.trans.severityclassification.SeverityClassificationTransMetricProvider;
import org.ossmeter.metricprovider.trans.severityclassification.model.BugTrackerBugsData;
import org.ossmeter.metricprovider.trans.severityclassification.model.SeverityClassificationTransMetric;
import org.ossmeter.platform.AbstractHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class SeverityBugStatusHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = "org.ossmeter.metricprovider.historic.bugs.severitybugstatus";

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
		BugsSeverityBugStatusHistoricMetric metric = new BugsSeverityBugStatusHistoricMetric();
		
		if (uses.size()==2) {

			SeverityClassificationTransMetric severityClassifier = 
					 ((SeverityClassificationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			 
			BugsBugMetadataTransMetric bugMetadata = 
					 ((BugMetadataTransMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));
			 
			Map<String, Map<String, Integer>> severitiesPerTracker = new HashMap<String, Map<String, Integer>>(),
											  resolvedClosedBugs = new HashMap<String, Map<String, Integer>>(),
											  wontFixBugs = new HashMap<String, Map<String, Integer>>(),
											  worksForMeBugs = new HashMap<String, Map<String, Integer>>(),
											  nonResolvedClosedBugs = new HashMap<String, Map<String, Integer>>(),
											  invalidBugs = new HashMap<String, Map<String, Integer>>(),
											  fixedBugs = new HashMap<String, Map<String, Integer>>(),
											  duplicateBugs = new HashMap<String, Map<String, Integer>>();
			 
			 
			 for (BugTrackerBugsData bugTrackerBugsData: severityClassifier.getBugTrackerBugs()) {
				 
				 String trackerId = bugTrackerBugsData.getBugTrackerId();
				 
				 String severity = bugTrackerBugsData.getSeverity();
				 Map<String, Integer> severityMap = retrieveOrAdd(severitiesPerTracker, trackerId);
				 addOrIncrease(severityMap, severity);
			 
				 BugData bugData = null;
				 Iterable<BugData> bugDataIt = bugMetadata.getBugData().find(BugData.BUGTRACKERID.eq(trackerId),
						 													 BugData.BUGID.eq(bugTrackerBugsData.getBugId()));
				 for (BugData bd: bugDataIt) bugData = bd;

				 if (bugData.getStatus().toLowerCase().equals("resolved")||
						 (bugData.getStatus().toLowerCase().equals("closed"))) {
					 severityMap = retrieveOrAdd(resolvedClosedBugs, trackerId);
					 addOrIncrease(severityMap, severity);
				 }
				 if (bugData.getResolution().toLowerCase().equals("wontfix")
						 ||(bugData.getResolution().toLowerCase().equals("cantfix"))) {
					 severityMap = retrieveOrAdd(wontFixBugs, trackerId);
					 addOrIncrease(severityMap, severity);
				 }
				 if (bugData.getResolution().toLowerCase().equals("worksforme")) {
					 severityMap = retrieveOrAdd(worksForMeBugs, trackerId);
					 addOrIncrease(severityMap, severity);
				 }
				 if (!bugData.getStatus().toLowerCase().equals("resolved")
						 &&(!bugData.getStatus().toLowerCase().equals("closed"))) {
					 severityMap = retrieveOrAdd(nonResolvedClosedBugs, trackerId);
					 addOrIncrease(severityMap, severity);
				 }
				 if (bugData.getResolution().toLowerCase().equals("invalid")
						 ||(bugData.getResolution().toLowerCase().equals("notabug"))) {
					 severityMap = retrieveOrAdd(invalidBugs, trackerId);
					 addOrIncrease(severityMap, severity);
				 }
				 if ((bugData.getResolution().toLowerCase().equals("fixed"))
						 ||(bugData.getResolution().toLowerCase().equals("upstream"))
						 ||(bugData.getResolution().toLowerCase().equals("currentrelease"))
						 ||(bugData.getResolution().toLowerCase().equals("nextrelease"))
						 ||(bugData.getResolution().toLowerCase().equals("rawhide"))) {
					 severityMap = retrieveOrAdd(fixedBugs, trackerId);
					 addOrIncrease(severityMap, severity);
				 }
				 if (bugData.getResolution().toLowerCase().equals("duplicate")) {
					 severityMap = retrieveOrAdd(duplicateBugs, trackerId);
					 addOrIncrease(severityMap, severity);
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
					 
					 int numberOfResolvedClosedBugs = getValue(resolvedClosedBugs, bugTrackerId, severity);
					 if (numberOfResolvedClosedBugs > 0) {
						 severityLevel.setNumberOfResolvedClosedBugs(numberOfResolvedClosedBugs);
						 severityLevel.setPercentageOfResolvedClosedBugs( ((float) numberOfResolvedClosedBugs) / numberOfSeverityBugs);
					 }
					 
					 int numberOfWontFixBugs = getValue(wontFixBugs, bugTrackerId, severity);					 
					 if (numberOfWontFixBugs > 0) {
						 severityLevel.setNumberOfWontFixBugs(numberOfWontFixBugs);
						 severityLevel.setPercentageOfWontFixBugs( ((float) numberOfWontFixBugs) / numberOfSeverityBugs);
					 }
					 
					 int numberOfWorksForMeBugs = getValue(worksForMeBugs, bugTrackerId, severity);
					 if (numberOfWorksForMeBugs > 0) {
						 severityLevel.setNumberOfWorksForMeBugs(numberOfWorksForMeBugs);
						 severityLevel.setPercentageOfWorksForMeBugs( ((float) numberOfWorksForMeBugs) / numberOfSeverityBugs);
					 }
					 
					 int numberOfNonResolvedClosedBugs = getValue(nonResolvedClosedBugs, bugTrackerId, severity);
					 if (numberOfNonResolvedClosedBugs > 0) {
						 severityLevel.setNumberOfNonResolvedClosedBugs(numberOfNonResolvedClosedBugs);
						 severityLevel.setPercentageOfNonResolvedClosedBugs( ((float) numberOfNonResolvedClosedBugs) / numberOfSeverityBugs);
					 }
					 
					 int numberOfInvalidBugs = getValue(invalidBugs, bugTrackerId, severity);
					 if (numberOfInvalidBugs > 0) {
						 severityLevel.setNumberOfInvalidBugs(numberOfInvalidBugs);
						 severityLevel.setPercentageOfInvalidBugs( ((float) numberOfInvalidBugs) / numberOfSeverityBugs);
					 }
					 
					 int numberOfFixedBugs = getValue(fixedBugs, bugTrackerId, severity);
					 if (numberOfFixedBugs > 0) {
						 severityLevel.setNumberOfFixedBugs(numberOfFixedBugs);
						 severityLevel.setPercentageOfFixedBugs( ((float) numberOfFixedBugs) / numberOfSeverityBugs);
					 }
					 
					 int numberOfDuplicateBugs = getValue(duplicateBugs, bugTrackerId, severity);
					 if (numberOfDuplicateBugs > 0) {
						 severityLevel.setNumberOfDuplicateBugs(numberOfDuplicateBugs);
						 severityLevel.setPercentageOfDuplicateBugs( ((float) numberOfDuplicateBugs) / numberOfSeverityBugs);
					 }
					 
					 metric.getSeverityLevels().add(severityLevel);
				 }
			 
			 }
			 
		}
		return metric;
	
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

	private void addOrIncrease(Map<String, Integer> map, String item) {
		if (map.containsKey(item))
			map.put(item, map.get(item) + 1);
		else
			map.put(item, + 1);
	}
	
	private int getValue(Map<String, Map<String, Integer>> map,	String item, String component) {
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
							 BugMetadataTransMetricProvider.class.getCanonicalName());
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
