package org.ossmeter.metricprovider.historic.overallsentimentnntp;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.historic.overallsentimentnntp.model.DailyNewsgroupData;
import org.ossmeter.metricprovider.historic.overallsentimentnntp.model.OverallSentimentNNTP;
import org.ossmeter.metricprovider.trans.threadsrequestsreplies.ThreadsRequestsRepliesProvider;
import org.ossmeter.metricprovider.trans.threadsrequestsreplies.model.ThreadStatistics;
import org.ossmeter.metricprovider.trans.threadsrequestsreplies.model.ThreadsRR;
import org.ossmeter.platform.AbstractHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.googlecode.pongo.runtime.Pongo;

public class OverallSentimentNNTPMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.generic.overallsentimentnntp";

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
		for (CommunicationChannel communicationchannel: project.getCommunicationChannels()) {
			if (communicationchannel instanceof NntpNewsGroup) return true;
		}
		return false;
	}

	@Override
	public Pongo measure(Project project) {
		if (uses.size()!=1) {
			System.err.println("Metric: avgnumberofrequestsreplies failed to retrieve " + 
								"the transient metric it needs!");
			System.exit(-1);
		}

		ThreadsRR threadsRR = ((ThreadsRequestsRepliesProvider)uses.get(0)).adapt(context.getProjectDB(project));

		float overallSentiment = 0,
				startSentiment = 0,
				endSentiment = 0;
		for (ThreadStatistics threadStatistics: threadsRR.getThreads()) {
			overallSentiment += threadStatistics.getAverageSentiment();
			String start = threadStatistics.getStartSentiment();
			if (start.equals("Positive"))
				startSentiment+=1;
			else if (start.equals("Negative"))
				startSentiment-=1;
			String end = threadStatistics.getEndSentiment();
			if (end.equals("Positive"))
				endSentiment+=1;
			else if (end.equals("Negative"))
				endSentiment-=1;
		}
		long size = threadsRR.getThreads().size();
		if (size>0) {
			overallSentiment /= size;
			startSentiment /= size;
			endSentiment /= size;
		}
		
		DailyNewsgroupData dailyBugzillaData = new DailyNewsgroupData();
		dailyBugzillaData.setOverallAverageSentiment(overallSentiment);
		dailyBugzillaData.setOverallSentimentAtThreadBeggining(startSentiment);
		dailyBugzillaData.setOverallSentimentAtThreadEnd(endSentiment);
		OverallSentimentNNTP overallSentimentNNTP = new OverallSentimentNNTP();
		overallSentimentNNTP.getNewsgroups().add(dailyBugzillaData);
		System.out.println("overallSentiment: " + overallSentiment + "\t" + 
				"startSentiment: " + startSentiment + "\t" + 
				"endSentiment: " + endSentiment);
		return overallSentimentNNTP;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(ThreadsRequestsRepliesProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "overallsentimentNNTP";
	}

	@Override
	public String getFriendlyName() {
		return "Overall Sentiment of Newsgroup Articles";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the overall sentiment per NNTP repository up to the processing date." +
				"The overall sentiment score ranges from -1 (negative sentiment) to +1 (positive sentiment)." +
				"In the computation, the sentiment score of each thread contributes equally, independently of its size.";
	}
}
