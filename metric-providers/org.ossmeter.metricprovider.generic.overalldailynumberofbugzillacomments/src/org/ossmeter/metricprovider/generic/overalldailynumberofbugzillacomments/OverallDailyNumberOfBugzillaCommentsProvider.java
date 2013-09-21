package org.ossmeter.metricprovider.generic.overalldailynumberofbugzillacomments;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.generic.overalldailynumberofbugzillacomments.model.DailyBugzillaData;
import org.ossmeter.metricprovider.generic.overalldailynumberofbugzillacomments.model.DailyNobc;
import org.ossmeter.metricprovider.numberofbugzillacomments.NobcMetricProvider;
import org.ossmeter.metricprovider.numberofbugzillacomments.model.BugzillaData;
import org.ossmeter.metricprovider.numberofbugzillacomments.model.Nobc;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.bts.bugzilla.Bugzilla;

import com.googlecode.pongo.runtime.Pongo;

public class OverallDailyNumberOfBugzillaCommentsProvider implements IHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.generic.overalldailynumberofbugzillacomments";

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
		DailyNobc dailyNobc = new DailyNobc();
		for (IMetricProvider used : uses) {
			 Nobc usedNobc = ((NobcMetricProvider)used).adapt(context.getProjectDB(project));
			 int numberOfBugzillaComments = 0;
			 for (BugzillaData bugzilla: usedNobc.getBugzillas()) {
				 numberOfBugzillaComments += bugzilla.getNumberOfComments();
			 }
			 DailyBugzillaData dailyBugzillaData = new DailyBugzillaData();
			 dailyBugzillaData.setNumberOfComments(numberOfBugzillaComments);
			 dailyNobc.getBugzillas().add(dailyBugzillaData);
		}
		return dailyNobc;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(NobcMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "dailybugzillacomments";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Bugzilla Comments Per Day";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of comments submitted every day.";
	}
}
