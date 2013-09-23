package org.ossmeter.metricprovider.generic.numberoffixedbugzillabugs;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.bugheadermetadata.BugHeaderMetadataMetricProvider;
import org.ossmeter.metricprovider.bugheadermetadata.model.BugData;
import org.ossmeter.metricprovider.bugheadermetadata.model.BugHeaderMetadata;
import org.ossmeter.metricprovider.generic.numberoffixedbugzillabugs.model.DailyBugzillaData;
import org.ossmeter.metricprovider.generic.numberoffixedbugzillabugs.model.DailyNofb;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.bts.bugzilla.Bugzilla;

import com.googlecode.pongo.runtime.Pongo;

public class NumberOfFixedBugzillaBugsProvider implements IHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.generic.numberoffixedbugzillabugs";

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
		DailyNofb dailyNofb = new DailyNofb();
		for (IMetricProvider used : uses) {
			BugHeaderMetadata usedBhm = ((BugHeaderMetadataMetricProvider)used).adapt(context.getProjectDB(project));
			int numberOfFixedBugs = 0;
			 for (BugData bugData: usedBhm.getBugs()) {
				 if ((bugData.getResolution().toLowerCase().equals("fixed"))
					 ||(bugData.getResolution().toLowerCase().equals("upstream"))
					 ||(bugData.getResolution().toLowerCase().equals("currentrelease"))
					 ||(bugData.getResolution().toLowerCase().equals("nextrelease"))
					 ||(bugData.getResolution().toLowerCase().equals("rawhide")))
					 numberOfFixedBugs++;
			 }
			 DailyBugzillaData dailyBugzillaData = new DailyBugzillaData();
			 dailyBugzillaData.setNumberOfFixedBugs(numberOfFixedBugs);
			 dailyNofb.getBugzillas().add(dailyBugzillaData);
		}
		return dailyNofb;
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
		return "fixedbugs";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Fixed Bugzilla Bugs";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of fixed bugs for each day. " +
				"[Also considered are the following labels:upstream, currentrelease, nextrelease, rawhide.]";
	}
}
