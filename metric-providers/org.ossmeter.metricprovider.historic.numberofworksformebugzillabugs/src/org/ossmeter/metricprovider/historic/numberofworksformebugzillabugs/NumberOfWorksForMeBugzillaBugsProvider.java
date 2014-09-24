package org.ossmeter.metricprovider.historic.numberofworksformebugzillabugs;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.historic.numberofworksformebugzillabugs.model.DailyBugzillaData;
import org.ossmeter.metricprovider.historic.numberofworksformebugzillabugs.model.DailyNowfmb;
import org.ossmeter.metricprovider.trans.bugheadermetadata.BugHeaderMetadataMetricProvider;
import org.ossmeter.metricprovider.trans.bugheadermetadata.model.BugData;
import org.ossmeter.metricprovider.trans.bugheadermetadata.model.BugHeaderMetadata;
import org.ossmeter.platform.AbstractHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.bts.bugzilla.Bugzilla;

import com.googlecode.pongo.runtime.Pongo;

public class NumberOfWorksForMeBugzillaBugsProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.historic.numberofworksformebugzillabugs";

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
		DailyNowfmb dailyNowfmb = new DailyNowfmb();
		for (IMetricProvider used : uses) {
			 BugHeaderMetadata usedBhm = ((BugHeaderMetadataMetricProvider)used).adapt(context.getProjectDB(project));
			 int numberOfWorksForMeBugs = 0;
			 for (BugData bugData: usedBhm.getBugs()) {
				 if (bugData.getResolution().toLowerCase().equals("worksforme"))
					 numberOfWorksForMeBugs++;
			 }
			 if (numberOfWorksForMeBugs > 0) {
				 DailyBugzillaData dailyBugzillaData = new DailyBugzillaData();
				 dailyBugzillaData.setNumberOfWorksForMeBugs(numberOfWorksForMeBugs);
				 dailyNowfmb.getBugzillas().add(dailyBugzillaData);
			 }
		}
		return dailyNowfmb;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(BugHeaderMetadataMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "worksformebugs";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Works-For-Me Bugzilla Bugs";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of works-for-me bugs for each day.";
	}
}
