package org.ossmeter.metricprovider.historic.numberofrequestsrepliesperuser;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.historic.numberofrequestsrepliesperuser.model.AverageArticlesPerUserData;
import org.ossmeter.metricprovider.historic.numberofrequestsrepliesperuser.model.AverageRRUser;
import org.ossmeter.metricprovider.historic.numberofrequestsrepliesperuser.model.AverageRepliesPerUserData;
import org.ossmeter.metricprovider.historic.numberofrequestsrepliesperuser.model.AverageRequestsPerUserData;
import org.ossmeter.metricprovider.trans.activeusers.ActiveUsersMetricProvider;
import org.ossmeter.metricprovider.trans.activeusers.model.ActiveUsers;
import org.ossmeter.metricprovider.trans.activeusers.model.User;
import org.ossmeter.platform.AbstractHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.googlecode.pongo.runtime.Pongo;

public class NumberOfRequestsRepliesPerUserProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.historic.numberofrequestsrepliesperuser";

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

		ActiveUsers activeUsers = 
				((ActiveUsersMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		int numberOfUsers = 0,
			numberOfArticles = 0,
			numberOfReplies = 0,
			numberOfRequests = 0;
		for (User user: activeUsers.getUsers()) {
			numberOfUsers++;
			numberOfArticles += user.getArticles();
			numberOfReplies += user.getReplies();
			numberOfRequests += user.getRequests();
		}
		
		AverageArticlesPerUserData avgArticles = new AverageArticlesPerUserData();
		avgArticles.setAverageArticlesPerUser( 
				((float) numberOfArticles) / numberOfUsers );
		
		AverageRepliesPerUserData avgReplies = new AverageRepliesPerUserData();
		avgReplies.setAverageRepliesPerUser(
				((float) numberOfReplies) / numberOfUsers);

		AverageRequestsPerUserData avgRequests = new AverageRequestsPerUserData();
		avgRequests.setAverageRequestsPerUser( 
				((float) numberOfRequests) / numberOfUsers );

		AverageRRUser avgRRUser = new AverageRRUser();
		avgRRUser.getArticles().add(avgArticles);
		avgRRUser.getReplies().add(avgReplies);
		avgRRUser.getRequests().add(avgRequests);

		return avgRRUser;
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
		return "requestsrepliesperuser";
	}

	@Override
	public String getFriendlyName() {
		return "Average Number Of Articles, Requests and Replies Per User Provider";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the average number of articles, requests and replies per user.";
	}
}
