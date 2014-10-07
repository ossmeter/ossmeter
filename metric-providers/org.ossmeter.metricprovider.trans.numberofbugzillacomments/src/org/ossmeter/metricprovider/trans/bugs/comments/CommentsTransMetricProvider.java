package org.ossmeter.metricprovider.trans.bugs.comments;

import java.util.Collections;
import java.util.List;

import org.ossmeter.metricprovider.trans.bugs.comments.model.BugTrackerData;
import org.ossmeter.metricprovider.trans.bugs.comments.model.BugsCommentsTransMetric;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemProjectDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.Project;

import com.mongodb.DB;

public class CommentsTransMetricProvider implements ITransientMetricProvider<BugsCommentsTransMetric>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;

	@Override
	public String getIdentifier() {
		return CommentsTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "bugcomments";
	}

	@Override
	public String getFriendlyName() {
		return "Number of bug comments";
	}

	@Override
	public String getSummaryInformation() {
		return "The number of bug comments over time. Lorum ipsum.";
	}
	
	@Override
	public boolean appliesTo(Project project) {
	    return !project.getBugTrackingSystems().isEmpty();	   
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		// DO NOTHING -- we don't use anything
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Collections.emptyList();
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.platformBugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
	}

	@Override
	public BugsCommentsTransMetric adapt(DB db) {
		return new BugsCommentsTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, BugsCommentsTransMetric db) {
		BugTrackingSystemProjectDelta delta = projectDelta.getBugTrackingSystemDelta();
		
		for (BugTrackingSystemDelta bugTrackingSystemDelta : delta.getBugTrackingSystemDeltas()) {
			BugTrackingSystem bugTrackingSystem = bugTrackingSystemDelta.getBugTrackingSystem();
			Iterable<BugTrackerData> bugTrackerDataIt = db.getBugTrackerData().
										find(BugTrackerData.BUGTRACKERID.eq(bugTrackingSystem.getOSSMeterId()));
			BugTrackerData bugTrackerData = null;
			for (BugTrackerData btd:  bugTrackerDataIt) {
				bugTrackerData = btd;
			}
			if (bugTrackerData == null) {
				bugTrackerData = new BugTrackerData();
				bugTrackerData.setBugTrackerId(bugTrackingSystem.getOSSMeterId());
				db.getBugTrackerData().add(bugTrackerData);
			} 
			int numberOfComments = bugTrackingSystemDelta.getComments().size();
			bugTrackerData.setNumberOfComments(numberOfComments);
			int cumulativeNumberOfComments = bugTrackerData.getCumulativeNumberOfComments();
			bugTrackerData.setCumulativeNumberOfComments(cumulativeNumberOfComments + numberOfComments);
//			System.out.println("bugTrackingSystemDelta.getComments().size(): " + 
//								bugTrackingSystemDelta.getComments().size());
			db.sync();
		}
	}

}
