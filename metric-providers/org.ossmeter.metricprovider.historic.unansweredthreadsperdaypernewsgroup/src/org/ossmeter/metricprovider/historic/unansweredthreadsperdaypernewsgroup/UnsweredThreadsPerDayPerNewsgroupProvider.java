package org.ossmeter.metricprovider.historic.unansweredthreadsperdaypernewsgroup;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.historic.unansweredthreadsperdaypernewsgroup.model.DailyNewsgroupData;
import org.ossmeter.metricprovider.historic.unansweredthreadsperdaypernewsgroup.model.DailyUnansweredThreads;
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

public class UnsweredThreadsPerDayPerNewsgroupProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.historic.unansweredthreadsperdaypernewsgroup";

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

//	private String time(long timeInMS) {
//		return DurationFormatUtils.formatDuration(timeInMS, "HH:mm:ss,SSS");
//	}
	
	@Override
	public Pongo measure(Project project) {
//		final long startTime = System.currentTimeMillis();

		if (uses.size()!=1) {
			System.err.println("Metric: unansweredthreadsperdaypernewsgroup failed to retrieve " + 
								"the transient metric it needs!");
			System.exit(-1);
		}

		ThreadsRR usedThreads = 
				((ThreadsRequestsRepliesProvider)uses.get(0)).adapt(context.getProjectDB(project));
		
		int unansweredThreads = 0;

		String lastUrl_name = "";
		
		for (ThreadStatistics thread: usedThreads.getThreads()) {
			lastUrl_name = thread.getUrl_name();
			if ((!thread.getAnswered())&&(thread.getFirstRequest())) unansweredThreads++;
		}
		
		DailyUnansweredThreads dailyUnansweredThreads = new DailyUnansweredThreads();

		if (unansweredThreads > 0)
			dailyUnansweredThreads.getNewsgroups().add(prepareNewsgroupData(lastUrl_name, unansweredThreads));
		
//		System.err.println(time(System.currentTimeMillis() - startTime) + "\tunanswered_new");
		return dailyUnansweredThreads;
	}

	private DailyNewsgroupData prepareNewsgroupData(String url_name, int unansweredThreads) {
		DailyNewsgroupData dailyNewsgroupData = new DailyNewsgroupData();
		dailyNewsgroupData.setUrl_name(url_name);
		dailyNewsgroupData.setNumberOfUnansweredThreads(unansweredThreads);
		return dailyNewsgroupData;
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
		return "unansweredthreadspernewsgroup";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Unanswered Threads Per Day Per Newsgroup";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of unanswered threads per day for each newsgroup separately.";
	}
}
