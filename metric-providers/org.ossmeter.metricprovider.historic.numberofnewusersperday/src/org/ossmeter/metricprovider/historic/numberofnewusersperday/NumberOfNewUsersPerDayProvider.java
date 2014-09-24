package org.ossmeter.metricprovider.historic.numberofnewusersperday;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.historic.numberofnewusersperday.model.DailyNewUsers;
import org.ossmeter.metricprovider.historic.numberofnewusersperday.model.DailyNewsgroupData;
import org.ossmeter.metricprovider.trans.activeusers.ActiveUsersMetricProvider;
import org.ossmeter.metricprovider.trans.activeusers.model.ActiveUsers;
import org.ossmeter.metricprovider.trans.activeusers.model.NewsgroupData;
import org.ossmeter.platform.AbstractHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.googlecode.pongo.runtime.Pongo;

public class NumberOfNewUsersPerDayProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.generic.numberofnewusersperday";

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
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
		}
		return false;
	}

	@Override
	public Pongo measure(Project project) {

		 DailyNewUsers dailyUsers = new DailyNewUsers();
		for (IMetricProvider used : uses) {
			ActiveUsers ActiveUsers = ((ActiveUsersMetricProvider)used).adapt(context.getProjectDB(project));
			int newUsers = 0;
			for (NewsgroupData newsgroup: ActiveUsers.getNewsgroups())
				newUsers += (newsgroup.getUsers() - newsgroup.getPreviousUsers());
			if (newUsers > 0) {
				DailyNewsgroupData dailyNewsgroupData = new DailyNewsgroupData();
				dailyNewsgroupData.setNumberOfNewUsers(newUsers);
				dailyUsers.getNewsgroups().add(dailyNewsgroupData);
			}
		}
		return dailyUsers;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(ActiveUsersMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "newusersperday";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of New Users Per Day Provider";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of new users per day.";
	}
}
