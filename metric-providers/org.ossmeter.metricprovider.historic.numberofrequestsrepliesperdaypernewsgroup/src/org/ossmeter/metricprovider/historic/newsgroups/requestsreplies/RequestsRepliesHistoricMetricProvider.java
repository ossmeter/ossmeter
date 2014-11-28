package org.ossmeter.metricprovider.historic.newsgroups.requestsreplies;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.model.DailyNewsgroupData;
import org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.model.NewsgroupsRequestsRepliesHistoricMetric;
import org.ossmeter.metricprovider.trans.requestreplyclassification.RequestReplyClassificationTransMetricProvider;
import org.ossmeter.metricprovider.trans.requestreplyclassification.model.NewsgroupArticles;
import org.ossmeter.metricprovider.trans.requestreplyclassification.model.RequestReplyClassificationTransMetric;
import org.ossmeter.platform.AbstractHistoricalMetricProvider;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.googlecode.pongo.runtime.Pongo;

public class RequestsRepliesHistoricMetricProvider extends AbstractHistoricalMetricProvider{
	public final static String IDENTIFIER = "org.ossmeter.metricprovider.historic.newsgroups.requestsreplies";

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
		Date currentDate = context.getDate();
		NewsgroupsRequestsRepliesHistoricMetric dailyNorr = new NewsgroupsRequestsRepliesHistoricMetric();
		if (uses.size()==1) {
			int cumulativeRequestSum = 0, 
				cumulativeReplySum = 0,
				requestSum = 0, 
				replySum = 0;
			RequestReplyClassificationTransMetric usedRrc = 
					((RequestReplyClassificationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			Set<String> newsgroupNames = new HashSet<String>();
			Map<String, Integer> cumulativeRequests = new HashMap<String, Integer>(), 
								 cumulativeReplies = new HashMap<String, Integer>(),
								 requests = new HashMap<String, Integer>(), 
								 replies = new HashMap<String, Integer>();
			for (NewsgroupArticles naData: usedRrc.getNewsgroupArticles()) {
				Map<String, Integer> crr = null, rr = null;
				Date naDate = null;
				try {
					naDate = new Date(naData.getDate());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (naData.getClassificationResult().equals("Request")) {
					crr = cumulativeRequests;
					cumulativeRequestSum++;
					if (naDate.compareTo(currentDate)==0) {
						rr = requests;
						requestSum++;
					}
				} else if (naData.getClassificationResult().equals("Reply")) {
					crr = cumulativeReplies;
					cumulativeReplySum++;
					if (naDate.compareTo(currentDate)==0) {
						rr = replies;
						replySum++;
					}
				}
				if (crr!=null) {
					newsgroupNames.add(naData.getNewsgroupName());
					if (crr.containsKey(naData.getNewsgroupName()))
						crr.put(naData.getNewsgroupName(), crr.get(naData.getNewsgroupName()) + 1);
					else
						crr.put(naData.getNewsgroupName(), 1);
				} else {
					System.err.println("Classification result ( " + 
							naData.getClassificationResult() + 
							" ) should be either Request or Reply!");
				}
				if (rr!=null) {
					if (rr.containsKey(naData.getNewsgroupName()))
						rr.put(naData.getNewsgroupName(), rr.get(naData.getNewsgroupName()) + 1);
					else
						rr.put(naData.getNewsgroupName(), 1);
				}
			}
			for (String newsgroupName: newsgroupNames) {
				DailyNewsgroupData dailyNewsgroupData = new DailyNewsgroupData();
				dailyNewsgroupData.setNewsgroupName(newsgroupName);
				if (cumulativeRequests.containsKey(newsgroupName))
					dailyNewsgroupData.setCumulativeNumberOfRequests(cumulativeRequests.get(newsgroupName));
				if (cumulativeReplies.containsKey(newsgroupName))
					dailyNewsgroupData.setCumulativeNumberOfReplies(cumulativeReplies.get(newsgroupName));
				if (requests.containsKey(newsgroupName))
					dailyNewsgroupData.setNumberOfRequests(requests.get(newsgroupName));
				if (replies.containsKey(newsgroupName))
					dailyNewsgroupData.setNumberOfReplies(replies.get(newsgroupName));
				dailyNorr.getNewsgroups().add(dailyNewsgroupData);
			}
			dailyNorr.setCumulativeNumberOfRequests(cumulativeRequestSum);
			dailyNorr.setCumulativeNumberOfReplies(cumulativeReplySum);
			dailyNorr.setNumberOfRequests(requestSum);
			dailyNorr.setNumberOfReplies(replySum);
		}
		
		return dailyNorr;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(RequestReplyClassificationTransMetricProvider.class.getCanonicalName());
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
