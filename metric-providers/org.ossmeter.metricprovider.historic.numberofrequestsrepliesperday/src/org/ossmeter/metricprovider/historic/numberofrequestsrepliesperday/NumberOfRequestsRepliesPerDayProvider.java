package org.ossmeter.metricprovider.historic.numberofrequestsrepliesperday;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.historic.numberofrequestsrepliesperday.model.DailyNewsgroupRepliesData;
import org.ossmeter.metricprovider.historic.numberofrequestsrepliesperday.model.DailyNewsgroupRequestsData;
import org.ossmeter.metricprovider.historic.numberofrequestsrepliesperday.model.DailyNorr;
import org.ossmeter.metricprovider.trans.requestreplyclassification.RequestReplyClassificationMetricProvider;
import org.ossmeter.metricprovider.trans.requestreplyclassification.model.NewsgroupArticlesData;
import org.ossmeter.metricprovider.trans.requestreplyclassification.model.Rrc;
import org.ossmeter.platform.AbstractHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.googlecode.pongo.runtime.Pongo;

public class NumberOfRequestsRepliesPerDayProvider extends AbstractHistoricalMetricProvider{
	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.historic.numberofrequestsrepliesperday";

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

		DailyNorr dailyNorr = new DailyNorr();
		int requests = 0, 
			 replies = 0;
		for (IMetricProvider used : uses) {
			Rrc usedRrc = ((RequestReplyClassificationMetricProvider)used).
							adapt(context.getProjectDB(project));
			for (NewsgroupArticlesData naData: usedRrc.getNewsgroupArticles()) {
				if (naData.getClassificationResult().equals("Request")) 
					requests++;
				else if (naData.getClassificationResult().equals("Reply")) 
					replies++;
				else {
					System.err.println("Classification result ( " + 
							naData.getClassificationResult() + 
									" ) should be either Request or Reply!");
				}
			}
			if (requests > 0) {
				DailyNewsgroupRequestsData dailyNewsgroupRequestsData = 
						new DailyNewsgroupRequestsData();
				dailyNewsgroupRequestsData.setNumberOfRequests(requests);
				dailyNorr.getRequests().add(dailyNewsgroupRequestsData);
			}

			if (replies > 0) {
				DailyNewsgroupRepliesData dailyNewsgroupRepliesData = 
						new DailyNewsgroupRepliesData();
				dailyNewsgroupRepliesData.setNumberOfReplies(replies);
				dailyNorr.getReplies().add(dailyNewsgroupRepliesData);
			}
			
		}
		return dailyNorr;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(RequestReplyClassificationMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "requestsreplies";
	}

	@Override
	public String getFriendlyName() {
		return "Requests and Replies Per Day";
	}

	@Override
	public String getSummaryInformation() {
		return "This class computes the number of request and reply newsgroup articles " +
				"per day.";
	}

}
