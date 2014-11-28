package org.ossmeter.metricprovider.historic.newsgroups.newusers;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.historic.newsgroups.newusers.model.DailyNewsgroupData;
import org.ossmeter.metricprovider.historic.newsgroups.newusers.model.NewsgroupsNewUsersHistoricMetric;
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

public class NewUsersHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = "org.ossmeter.metricprovider.historic.newsgroups.newusers";

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
		NewsgroupsNewUsersHistoricMetric dailyNewUsers = new NewsgroupsNewUsersHistoricMetric();
		if (uses.size()==1) {
			int newUsersSum = 0,
				cumulativeNewUsersSum = 0;
			NewsgroupsActiveUsersTransMetric activeUsers = ((ActiveUsersTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			for (NewsgroupData newsgroup: activeUsers.getNewsgroups()) {
				int newUsers = newsgroup.getUsers() - newsgroup.getPreviousUsers(),
					cumulativeNewUsers = newsgroup.getUsers();
				newUsersSum += newUsers;
				cumulativeNewUsersSum += cumulativeNewUsers;
				if ( (newUsers > 0) || (cumulativeNewUsers > 0) ) {
					DailyNewsgroupData dailyNewsgroupData = new DailyNewsgroupData();
					dailyNewsgroupData.setNewsgroupName(newsgroup.getNewsgroupName());
					dailyNewsgroupData.setNumberOfNewUsers(newUsers);
					dailyNewsgroupData.setCumulativeNumberOfNewUsers(cumulativeNewUsers);
					dailyNewUsers.getNewsgroups().add(dailyNewsgroupData);
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
