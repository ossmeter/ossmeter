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
import org.ossmeter.repository.model.Project;

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
		return true; // FIXME: This should really check whether there are any providers
					 // for this MP. Otherwise it'll create an empty DB for every project.
					 // This is not possible in the current implementation because the 'uses'
					 // property is set AFTER this method is called.
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
}
