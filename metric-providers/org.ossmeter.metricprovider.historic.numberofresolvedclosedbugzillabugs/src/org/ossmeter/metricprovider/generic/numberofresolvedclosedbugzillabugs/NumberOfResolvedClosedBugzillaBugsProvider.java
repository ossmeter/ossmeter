package org.ossmeter.metricprovider.generic.numberofresolvedclosedbugzillabugs;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.bugheadermetadata.BugHeaderMetadataMetricProvider;
import org.ossmeter.metricprovider.bugheadermetadata.model.BugData;
import org.ossmeter.metricprovider.bugheadermetadata.model.BugHeaderMetadata;
import org.ossmeter.metricprovider.generic.numberofresolvedclosedbugzillabugs.model.DailyBugzillaData;
import org.ossmeter.metricprovider.generic.numberofresolvedclosedbugzillabugs.model.DailyNorcb;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.bts.bugzilla.Bugzilla;

import com.googlecode.pongo.runtime.Pongo;

public class NumberOfResolvedClosedBugzillaBugsProvider implements IHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.generic.numberofresolvedclosedbugzillabugs";

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
		DailyNorcb dailyNorcb = new DailyNorcb();
		for (IMetricProvider used : uses) {
			 BugHeaderMetadata usedBhm = ((BugHeaderMetadataMetricProvider)used).adapt(context.getProjectDB(project));
			 int numberOfResolvedClosedBugs = 0;
			 for (BugData bugData: usedBhm.getBugs()) {
				 if (bugData.getStatus().toLowerCase().equals("resolved")||
						 (bugData.getStatus().toLowerCase().equals("closed")))
					 numberOfResolvedClosedBugs++;
			 }
			 if (numberOfResolvedClosedBugs > 0) {
				 DailyBugzillaData dailyBugzillaData = new DailyBugzillaData();
				 dailyBugzillaData.setNumberOfResolvedClosedBugs(numberOfResolvedClosedBugs);
				 dailyNorcb.getBugzillas().add(dailyBugzillaData);
			 }
		}
		return dailyNorcb;
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
		return "resolvedclosedbugs";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Resolved Closed Bugzilla Bugs";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of resolved or closed bugs.";
	}
}
