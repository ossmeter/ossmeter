package org.ossmeter.metricprovider.generic.numberofrequestsrepliesperdaypernewsgroup;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ossmeter.metricprovider.generic.numberofrequestsrepliesperdaypernewsgroup.model.DailyNewsgroupRepliesData;
import org.ossmeter.metricprovider.generic.numberofrequestsrepliesperdaypernewsgroup.model.DailyNewsgroupRequestsData;
import org.ossmeter.metricprovider.generic.numberofrequestsrepliesperdaypernewsgroup.model.DailyNorr;
import org.ossmeter.metricprovider.requestreplyclassification.RequestReplyClassificationMetricProvider;
import org.ossmeter.metricprovider.requestreplyclassification.model.NewsgroupArticlesData;
import org.ossmeter.metricprovider.requestreplyclassification.model.Rrc;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.googlecode.pongo.runtime.Pongo;

public class NumberOfRequestsRepliesPerDayPerNewsgroupProvider implements IHistoricalMetricProvider{
	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.generic.numberofrequestsrepliesperdaypernewsgroup";

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

		Rrc usedRrc = ((RequestReplyClassificationMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		DailyNorr dailyNorr = new DailyNorr();
		Set<String> newsgroupUrls = new HashSet<String>();
		Map<String, Integer> requests = new HashMap<String, Integer>(), 
							  replies = new HashMap<String, Integer>();
		for (NewsgroupArticlesData naData: usedRrc.getNewsgroupArticles()) {
			Map<String, Integer> rr = null;
			if (naData.getClassificationResult().equals("Request")) 
				rr = requests;
			else if (naData.getClassificationResult().equals("Reply")) 
				rr = replies;
			if (rr!=null) {
				newsgroupUrls.add(naData.getUrl());
				if (rr.containsKey(naData.getUrl()))
					rr.put(naData.getUrl(), rr.get(naData.getUrl()) + 1);
				else
					rr.put(naData.getUrl(), 1);
			} else {
				System.err.println("Classification result ( " + 
						naData.getClassificationResult() + 
						" ) should be either Request or Reply!");
			}
		}
		for (String newsgroupUrl: newsgroupUrls) {
			if (requests.containsKey(newsgroupUrl)) {
				DailyNewsgroupRequestsData dailyNewsgroupRequestsData = 
						new DailyNewsgroupRequestsData();
				dailyNewsgroupRequestsData.setUrl_name(newsgroupUrl);
				dailyNewsgroupRequestsData.setNumberOfRequests(requests.get(newsgroupUrl));
				dailyNorr.getRequests().add(dailyNewsgroupRequestsData);
			}
			
			if (replies.containsKey(newsgroupUrl)) {
				DailyNewsgroupRepliesData dailyNewsgroupRepliesData = 
						new DailyNewsgroupRepliesData();
				dailyNewsgroupRepliesData.setUrl_name(newsgroupUrl);
				dailyNewsgroupRepliesData.setNumberOfReplies(replies.get(newsgroupUrl));
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
		return "requestsrepliespernewsgroup";
	}

	@Override
	public String getFriendlyName() {
		return "Requests and Replies Per Newsgroup";
	}

	@Override
	public String getSummaryInformation() {
		return "This class computes the number of request and reply newsgroup articles " +
				"per day for each newsgroup separately.";
	}

}
