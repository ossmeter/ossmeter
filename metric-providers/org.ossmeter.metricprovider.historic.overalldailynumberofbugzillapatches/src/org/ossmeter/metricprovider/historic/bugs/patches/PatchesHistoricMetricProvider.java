package org.ossmeter.metricprovider.historic.bugs.patches;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.historic.bugs.patches.model.BugsPatchesHistoricMetric;
import org.ossmeter.metricprovider.historic.bugs.patches.model.DailyBugData;
import org.ossmeter.metricprovider.trans.bugs.patches.PatchesTransMetricProvider;
import org.ossmeter.metricprovider.trans.bugs.patches.model.BugTrackerData;
import org.ossmeter.metricprovider.trans.bugs.patches.model.BugsPatchesTransMetric;
import org.ossmeter.platform.AbstractHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class PatchesHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = "org.ossmeter.metricprovider.historic.bugs.patches";

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
		BugsPatchesHistoricMetric dailyNobp = new BugsPatchesHistoricMetric();
		if (uses.size()==1) {
			BugsPatchesTransMetric patchesTransMetric = ((PatchesTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			int totalNumberOfPatches = 0,
				totalCumulativeNumberOfPatches = 0;
			for (BugTrackerData bugTrackerData: patchesTransMetric.getBugTrackerData()) {
				int numberOfPatches = bugTrackerData.getNumberOfPatches(),
					cumulativeNumberOfPatches = bugTrackerData.getCumulativeNumberOfPatches();
				totalNumberOfPatches += numberOfPatches;
				totalCumulativeNumberOfPatches += cumulativeNumberOfPatches;
				if ( (numberOfPatches>0) || (cumulativeNumberOfPatches>0) ) {
					DailyBugData bugData = new DailyBugData();
					bugData.setBugTrackerId(bugTrackerData.getBugTrackerId());
					bugData.setNumberOfPatches(numberOfPatches);
					bugData.setCumulativeNumberOfPatches(cumulativeNumberOfPatches);
					dailyNobp.getBugs().add(bugData);
				}
			}
			dailyNobp.setNumberOfPatches(totalNumberOfPatches);
			dailyNobp.setCumulativeNumberOfPatches(totalCumulativeNumberOfPatches);
		}
		return dailyNobp;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(PatchesTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "bugpatches";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Bug Patches Per Day";
	}

	@Override
	public String getSummaryInformation() {
		return "This class computes the number of bug patches per day.";
	}
}
