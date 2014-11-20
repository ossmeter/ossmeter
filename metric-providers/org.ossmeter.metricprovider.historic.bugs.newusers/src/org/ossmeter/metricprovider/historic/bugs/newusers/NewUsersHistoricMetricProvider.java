package org.ossmeter.metricprovider.historic.bugs.newusers;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.historic.bugs.newusers.model.BugsNewUsersHistoricMetric;
import org.ossmeter.metricprovider.historic.bugs.newusers.model.DailyBugTrackerData;
import org.ossmeter.metricprovider.trans.bugs.activeusers.ActiveUsersTransMetricProvider;
import org.ossmeter.metricprovider.trans.bugs.activeusers.model.BugData;
import org.ossmeter.metricprovider.trans.bugs.activeusers.model.BugsActiveUsersTransMetric;
import org.ossmeter.platform.AbstractHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class NewUsersHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = "org.ossmeter.metricprovider.historic.bugs.newusers";

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
		BugsNewUsersHistoricMetric dailyNewUsers = new BugsNewUsersHistoricMetric();
		if (uses.size()==1) {
			int newUsersSum = 0,
				cumulativeNewUsersSum = 0;
			BugsActiveUsersTransMetric activeUsers = ((ActiveUsersTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			for (BugData newsgroup: activeUsers.getBugs()) {
				int newUsers = newsgroup.getUsers() - newsgroup.getPreviousUsers(),
					cumulativeNewUsers = newsgroup.getUsers();
				newUsersSum += newUsers;
				cumulativeNewUsersSum += cumulativeNewUsers;
				if ( (newUsers > 0) || (cumulativeNewUsers > 0) ) {
					DailyBugTrackerData dailyBugTrackerData = new DailyBugTrackerData();
					dailyBugTrackerData.setBugTrackerId(newsgroup.getBugTrackerId());
					dailyBugTrackerData.setNumberOfNewUsers(newUsers);
					dailyBugTrackerData.setCumulativeNumberOfNewUsers(cumulativeNewUsers);
					dailyNewUsers.getBugTrackers().add(dailyBugTrackerData);
				}
			}
			dailyNewUsers.setNumberOfNewUsers(newUsersSum);
			dailyNewUsers.setCumulativeNumberOfNewUsers(cumulativeNewUsersSum);
		}
		return dailyNewUsers;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(ActiveUsersTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "newuserspernewsgroup";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of New Users Per Day Per Newsgroup Provider";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of new users " +
				"per day for each newsgroup separately.";
	}

}
