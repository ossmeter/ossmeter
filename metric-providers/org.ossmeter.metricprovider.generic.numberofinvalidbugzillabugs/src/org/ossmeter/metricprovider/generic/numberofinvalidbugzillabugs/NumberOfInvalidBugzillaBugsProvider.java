package org.ossmeter.metricprovider.generic.numberofinvalidbugzillabugs;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.bugheadermetadata.BugHeaderMetadataMetricProvider;
import org.ossmeter.metricprovider.bugheadermetadata.model.BugData;
import org.ossmeter.metricprovider.bugheadermetadata.model.BugHeaderMetadata;
import org.ossmeter.metricprovider.generic.numberofinvalidbugzillabugs.model.DailyBugzillaData;
import org.ossmeter.metricprovider.generic.numberofinvalidbugzillabugs.model.DailyNoib;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.bts.bugzilla.Bugzilla;

import com.googlecode.pongo.runtime.Pongo;

public class NumberOfInvalidBugzillaBugsProvider implements IHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.generic.numberofinvalidbugzillabugs";

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
		DailyNoib dailyNoib = new DailyNoib();
		for (IMetricProvider used : uses) {
			BugHeaderMetadata usedBhm = ((BugHeaderMetadataMetricProvider)used).adapt(context.getProjectDB(project));
			int numberOfInvalidBugs = 0;
			for (BugData bugData: usedBhm.getBugs()) {
				if (bugData.getResolution().toLowerCase().equals("invalid")
						||(bugData.getResolution().toLowerCase().equals("notabug")))
					numberOfInvalidBugs++;
				 }
			DailyBugzillaData dailyBugzillaData = new DailyBugzillaData();
			dailyBugzillaData.setNumberOfInvalidBugs(numberOfInvalidBugs);
			dailyNoib.getBugzillas().add(dailyBugzillaData);
		}
		return dailyNoib;
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
		return "invalidbugs";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Invalid Bugzilla Bugs";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of invalid bugs " +
				"or bugs resolved as \"not-a-bug\" for each day.";
	}
}
