package org.ossmeter.metricprovider.historic.overalldailynumberofbugzillapatches;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.historic.overalldailynumberofbugzillapatches.model.DailyBugzillaData;
import org.ossmeter.metricprovider.historic.overalldailynumberofbugzillapatches.model.DailyNobp;
import org.ossmeter.metricprovider.trans.numberofbugzillapatches.NobpMetricProvider;
import org.ossmeter.metricprovider.trans.numberofbugzillapatches.model.BugzillaData;
import org.ossmeter.metricprovider.trans.numberofbugzillapatches.model.Nobp;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.bts.bugzilla.Bugzilla;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class OverallDailyNumberOfBugzillaPatchesProvider implements IHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.historic.overalldailynumberofbugzillapatches";

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
		for (BugTrackingSystem bugTrackingSystem: project.getBugTrackingSystems()) {
			if (bugTrackingSystem instanceof Bugzilla) return true;
		}
		return false;
	}

	@Override
	public Pongo measure(Project project) {
		DailyNobp dailyNobp = new DailyNobp();
		for (IMetricProvider used : uses) {
			 Nobp usedNobp = ((NobpMetricProvider)used).adapt(context.getProjectDB(project));
			 int numberOfBugzillaPatches = 0;
			 for (BugzillaData bugzilla: usedNobp.getBugzillas()) {
				 numberOfBugzillaPatches += bugzilla.getNumberOfPatches();
			 }
			 if (numberOfBugzillaPatches > 0) {
				 DailyBugzillaData dailyBugzillaData = new DailyBugzillaData();
				 dailyBugzillaData.setNumberOfPatches(numberOfBugzillaPatches);
				 dailyNobp.getBugzillas().add(dailyBugzillaData);
			 }
		}
		return dailyNobp;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(NobpMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "dailybugzillapatches";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Bugzilla Patches Per Day";
	}

	@Override
	public String getSummaryInformation() {
		return "This class computes the number of bugzilla patches per day.";
	}
}
