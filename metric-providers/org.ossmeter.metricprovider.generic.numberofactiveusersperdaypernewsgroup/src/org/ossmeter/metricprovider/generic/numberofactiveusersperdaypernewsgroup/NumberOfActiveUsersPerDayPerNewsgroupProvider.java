package org.ossmeter.metricprovider.generic.numberofactiveusersperdaypernewsgroup;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.activeusers.ActiveUsersMetricProvider;
import org.ossmeter.metricprovider.activeusers.model.ActiveUsers;
import org.ossmeter.metricprovider.activeusers.model.NewsgroupData;
import org.ossmeter.metricprovider.generic.numberofactiveusersperdaypernewsgroup.model.DailyActiveUsers;
import org.ossmeter.metricprovider.generic.numberofactiveusersperdaypernewsgroup.model.DailyNewsgroupData;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.googlecode.pongo.runtime.Pongo;

public class NumberOfActiveUsersPerDayPerNewsgroupProvider implements IHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.generic.numberofactiveusersperdaypernewsgroup";

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
		DailyActiveUsers dailyActiveUsers = new DailyActiveUsers();
		for (IMetricProvider used : uses) {
			  ActiveUsers ActiveUsers = ((ActiveUsersMetricProvider)used).adapt(context.getProjectDB(project));
			 for (NewsgroupData newsgroup: ActiveUsers.getNewsgroups()) {
				 DailyNewsgroupData dailyNewsgroupData = new DailyNewsgroupData();
				 dailyNewsgroupData.setUrl_name(newsgroup.getUrl_name());
				 dailyNewsgroupData.setNumberOfActiveUsers(newsgroup.getNumberOfActiveUsers());
				 dailyActiveUsers.getNewsgroups().add(dailyNewsgroupData);
			 }
		}
		return dailyActiveUsers;
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
		return "activeuserspernewsgroup";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of ActiveUsers Per Day Per Newsgroup Provider";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of active users " +
				"per day for each newsgroup separately.";
	}

}
