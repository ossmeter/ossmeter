package org.ossmeter.metricprovider.historic.bugs.comments;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.historic.bugs.comments.model.BugsCommentsHistoricMetric;
import org.ossmeter.metricprovider.historic.bugs.comments.model.DailyBugData;
import org.ossmeter.metricprovider.trans.bugs.comments.CommentsTransMetricProvider;
import org.ossmeter.metricprovider.trans.bugs.comments.model.BugTrackerData;
import org.ossmeter.metricprovider.trans.bugs.comments.model.BugsCommentsTransMetric;
import org.ossmeter.platform.AbstractHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class CommentsHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = "org.ossmeter.metricprovider.historic.bugs.comments";

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

	@Override
	public Pongo measure(Project project) {
		BugsCommentsHistoricMetric dailyNobc = new BugsCommentsHistoricMetric();
		if (uses.size()==1) {
			 BugsCommentsTransMetric usedNobc = ((CommentsTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			 int sumOfComments = 0,
				 cumulativeSumOfComments = 0;
			 System.err.println("\tusedNobc.getBugTrackerData().size(): " + usedNobc.getBugTrackerData().size());
			 for (BugTrackerData bugTrackerData: usedNobc.getBugTrackerData()) {
				 System.err.println("\tusedNobc.getBugTrackerData().size(): " + usedNobc.getBugTrackerData().size());
				 int numberOfComments = bugTrackerData.getNumberOfComments(),
					 cumulativeNumberOfComments = bugTrackerData.getCumulativeNumberOfComments();
				 sumOfComments += numberOfComments;
				 cumulativeSumOfComments += cumulativeNumberOfComments;
				 if ( (numberOfComments>0) || (cumulativeNumberOfComments>0) ) {
					 DailyBugData bugData = new DailyBugData();
					 bugData.setBugTrackerId(bugTrackerData.getBugTrackerId());
					 bugData.setNumberOfComments(numberOfComments);
					 bugData.setCumulativeNumberOfComments(cumulativeNumberOfComments);
					 dailyNobc.getBugs().add(bugData);
				 }
			 }
			 dailyNobc.setNumberOfComments(sumOfComments);
			 dailyNobc.setCumulativeNumberOfComments(cumulativeSumOfComments);
		}
		return dailyNobc;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(CommentsTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "bugcomments";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Bug Comments Per Day";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of comments submitted every day.";
	}
}
