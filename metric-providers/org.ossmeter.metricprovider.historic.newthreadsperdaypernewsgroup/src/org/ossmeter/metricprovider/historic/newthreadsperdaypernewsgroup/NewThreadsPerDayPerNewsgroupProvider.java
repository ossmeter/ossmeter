package org.ossmeter.metricprovider.historic.newthreadsperdaypernewsgroup;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.historic.newthreadsperdaypernewsgroup.model.DailyNewThreads;
import org.ossmeter.metricprovider.historic.newthreadsperdaypernewsgroup.model.DailyNewsgroupData;
import org.ossmeter.metricprovider.trans.threads.ThreadsMetricProvider;
import org.ossmeter.metricprovider.trans.threads.model.NewsgroupData;
import org.ossmeter.metricprovider.trans.threads.model.Threads;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.googlecode.pongo.runtime.Pongo;

public class NewThreadsPerDayPerNewsgroupProvider implements IHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.historic.newthreadsperdaypernewsgroup";

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

		if (uses.size()!=1) {
			System.err.println("Metric: newthreadsperdaypernewsgroup failed to retrieve " + 
								"the transient metric it needs!");
			System.exit(-1);
		}

		DailyNewThreads dailyNewThreads = new DailyNewThreads();
		Threads usedThreads = ((ThreadsMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		for (NewsgroupData newsgroups: usedThreads.getNewsgroups()) {
			dailyNewThreads.getNewsgroups().
				add(prepareNewsgroupData(newsgroups.getUrl_name(), 
						newsgroups.getThreads()-newsgroups.getPreviousThreads()));
		}
		return dailyNewThreads;
	}
			
	private DailyNewsgroupData prepareNewsgroupData(String url_name, int newThreads) {
		DailyNewsgroupData dailyNewsgroupData = new DailyNewsgroupData();
		dailyNewsgroupData.setUrl_name(url_name);
		dailyNewsgroupData.setNumberOfNewThreads(newThreads);
		return dailyNewsgroupData;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(ThreadsMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "newthreadspernewsgroup";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of New Threads Per Day Per Newsgroup";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of new threads per day for each newsgroup separately.";
	}
}
