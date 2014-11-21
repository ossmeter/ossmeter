package org.ossmeter.metricprovider.historic.newsgroups.users;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.historic.newsgroups.users.model.DailyNewsgroupData;
import org.ossmeter.metricprovider.historic.newsgroups.users.model.NewsgroupsUsersHistoricMetric;
import org.ossmeter.metricprovider.trans.newsgroups.activeusers.ActiveUsersTransMetricProvider;
import org.ossmeter.metricprovider.trans.newsgroups.activeusers.model.NewsgroupData;
import org.ossmeter.metricprovider.trans.newsgroups.activeusers.model.NewsgroupsActiveUsersTransMetric;
import org.ossmeter.platform.AbstractHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.googlecode.pongo.runtime.Pongo;

public class UsersHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.historic.newsgroups.users";

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
		NewsgroupsUsersHistoricMetric users = new NewsgroupsUsersHistoricMetric();
		if (uses.size()==1) {
			NewsgroupsActiveUsersTransMetric activeUsers = ((ActiveUsersTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			int numberOfUsers = 0,
				numberOfActiveUsers = 0,
				numberOfInactiveUsers = 0;
			for (NewsgroupData newsgroup: activeUsers.getNewsgroups()) {
				if ((newsgroup.getUsers() > 0) || (newsgroup.getActiveUsers() > 0) || (newsgroup.getInactiveUsers() > 0)) {
					DailyNewsgroupData dailyNewsgroupData = new DailyNewsgroupData();
					dailyNewsgroupData.setNewsgroupName(newsgroup.getNewsgroupName());
					if (newsgroup.getUsers() > 0) {
						dailyNewsgroupData.setNumberOfUsers(newsgroup.getUsers());
						numberOfUsers += newsgroup.getUsers();
					}
					if (newsgroup.getActiveUsers() > 0) {
						dailyNewsgroupData.setNumberOfActiveUsers(newsgroup.getActiveUsers());
						numberOfActiveUsers += newsgroup.getActiveUsers();
					}
					if (newsgroup.getInactiveUsers() > 0) {
						dailyNewsgroupData.setNumberOfInactiveUsers(newsgroup.getInactiveUsers());
						numberOfInactiveUsers += newsgroup.getInactiveUsers();
					}
					users.getNewsgroups().add(dailyNewsgroupData);
				}
			}
			users.setNumberOfUsers(numberOfUsers);
			users.setNumberOfActiveUsers(numberOfActiveUsers);
			users.setNumberOfInactiveUsers(numberOfInactiveUsers);
		}
		return users;
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
		return "activeinactiveuserspernewsgroup";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Active and Inactive Users Per Day Per Newsgroup Provider";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of active and inactive users " +
				"per day for each newsgroup separately.";
	}

}
