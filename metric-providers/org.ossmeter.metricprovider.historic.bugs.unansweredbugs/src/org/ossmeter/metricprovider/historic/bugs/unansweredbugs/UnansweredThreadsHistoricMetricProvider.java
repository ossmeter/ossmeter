package org.ossmeter.metricprovider.historic.bugs.unansweredbugs;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.historic.bugs.unansweredbugs.model.BugsUnansweredBugsHistoricMetric;
import org.ossmeter.metricprovider.trans.bugs.requestsreplies.BugsRequestsRepliesTransMetricProvider;
import org.ossmeter.metricprovider.trans.bugs.requestsreplies.model.BugStatistics;
import org.ossmeter.metricprovider.trans.bugs.requestsreplies.model.BugsRequestsRepliesTransMetric;
import org.ossmeter.platform.AbstractHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class UnansweredThreadsHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.historic.bugs.unansweredbugs";

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
	    return !project.getBugTrackingSystems().isEmpty();	   
	}

//	private String time(long timeInMS) {
//		return DurationFormatUtils.formatDuration(timeInMS, "HH:mm:ss,SSS");
//	}
	
	@Override
	public Pongo measure(Project project) {
//		final long startTime = System.currentTimeMillis();

		if (uses.size()!=1) {
			System.err.println("Metric: " + IDENTIFIER + " failed to retrieve " + 
								"the transient metric it needs!");
			System.exit(-1);
		}

		BugsRequestsRepliesTransMetric usedRequestsReplies = 
				((BugsRequestsRepliesTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		
		int sumOfUnansweredBugs = 0;
		for (BugStatistics bug: usedRequestsReplies.getBugs()) {
			if ((!bug.getAnswered())&&(bug.getFirstRequest()))
				sumOfUnansweredBugs++;
		}
		
		BugsUnansweredBugsHistoricMetric dailyUnansweredBugs = 
									new BugsUnansweredBugsHistoricMetric();

		dailyUnansweredBugs.setNumberOfUnansweredBugs(sumOfUnansweredBugs);
		
//		System.err.println(time(System.currentTimeMillis() - startTime) + "\tunanswered_new");
		return dailyUnansweredBugs;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(BugsRequestsRepliesTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "unansweredbugs";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Unanswered Bugs Per Day";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of unanswered bugs per day.";
	}
}
