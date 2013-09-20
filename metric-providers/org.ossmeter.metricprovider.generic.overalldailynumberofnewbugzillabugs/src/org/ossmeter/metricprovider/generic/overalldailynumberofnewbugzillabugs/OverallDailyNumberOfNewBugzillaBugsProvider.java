package org.ossmeter.metricprovider.generic.overalldailynumberofnewbugzillabugs;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.generic.overalldailynumberofnewbugzillabugs.model.DailyBugzillaData;
import org.ossmeter.metricprovider.generic.overalldailynumberofnewbugzillabugs.model.DailyNonbb;
import org.ossmeter.metricprovider.numberofnewbugzillabugs.NonbbMetricProvider;
import org.ossmeter.metricprovider.numberofnewbugzillabugs.model.BugzillaData;
import org.ossmeter.metricprovider.numberofnewbugzillabugs.model.Nonbb;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.bts.bugzilla.Bugzilla;

import com.googlecode.pongo.runtime.Pongo;

public class OverallDailyNumberOfNewBugzillaBugsProvider implements IHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.generic.overalldailynumberofnewbugzillabugs";

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
	public String getShortIdentifier() {
		return "odnonbb";
	}

	@Override
	public String getFriendlyName() {
		return "Number of bug reports per day";
	}

	@Override
	public String getSummaryInformation() {
		return "The number of new bugs reported per day during the period of interest. " +
				"A small number of bug reports can indicate either a bug-free, robust project " +
				"or a project with a small/inactive user community.";
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
		DailyNonbb dailyNonbb = new DailyNonbb();
		for (IMetricProvider used : uses) {
			 Nonbb usedNonbb = ((NonbbMetricProvider)used).adapt(context.getProjectDB(project));
			 int numberOfNewBugzillaBugs = 0;
			 for (BugzillaData bugzilla: usedNonbb.getBugzillas()) {
				 numberOfNewBugzillaBugs += bugzilla.getNumberOfBugs();
			 }
			 DailyBugzillaData dailyBugzillaData = new DailyBugzillaData();
			 dailyBugzillaData.setNumberOfBugs(numberOfNewBugzillaBugs);
			 dailyNonbb.getBugzillas().add(dailyBugzillaData);
		}
		return dailyNonbb;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(NonbbMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}
}
