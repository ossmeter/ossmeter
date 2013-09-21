package org.ossmeter.metricprovider.numberofnewbugzillabugs;

import java.util.Collections;
import java.util.List;

import org.ossmeter.metricprovider.numberofnewbugzillabugs.model.BugzillaData;
import org.ossmeter.metricprovider.numberofnewbugzillabugs.model.Nonbb;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemProjectDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.bts.bugzilla.Bugzilla;

import com.mongodb.DB;

public class NonbbMetricProvider implements ITransientMetricProvider<Nonbb>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;

	@Override
	public String getIdentifier() {
		return NonbbMetricProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
		for (BugTrackingSystem bugTrackingSystem: project.getBugTrackingSystems()) {
			if (bugTrackingSystem instanceof Bugzilla) return true;
		}
		return false;
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
	public Nonbb adapt(DB db) {
		return new Nonbb(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, Nonbb db) {
		BugTrackingSystemProjectDelta delta = projectDelta.getBugTrackingSystemDelta();
		
		for (BugTrackingSystemDelta bugTrackingSystemDelta : delta.getBugTrackingSystemDeltas()) {
			BugTrackingSystem bugTrackingSystem = bugTrackingSystemDelta.getBugTrackingSystem();
			if (!(bugTrackingSystem instanceof Bugzilla)) continue;
			Bugzilla bugzilla = (Bugzilla) bugTrackingSystem;
			Iterable<BugzillaData> bugzillaDataIt = db.getBugzillas().
					find(BugzillaData.URL.eq(bugzilla.getUrl()), 
							BugzillaData.PRODUCT.eq(bugzilla.getProduct()), 
							BugzillaData.COMPONENT.eq(bugzilla.getComponent()));
			BugzillaData bugzillaData = null;
			for (BugzillaData bd:  bugzillaDataIt) {
				bugzillaData = bd;
			}
			if (bugzillaData == null) {
				bugzillaData = new BugzillaData();
				bugzillaData.setUrl(bugzilla.getUrl());
				bugzillaData.setProduct(bugzilla.getProduct());
				bugzillaData.setComponent(bugzilla.getComponent());
				db.getBugzillas().add(bugzillaData);
			} 
			bugzillaData.setNumberOfBugs(bugTrackingSystemDelta.getNewBugs().size());
//			System.out.println("bugTrackingSystemDelta.getNewBugs().size(): " + 
//								bugTrackingSystemDelta.getNewBugs().size());
			db.sync();
		}
	}

	@Override
	public String getShortIdentifier() {
		return "newbugs";
	}

	@Override
	public String getFriendlyName() {
		return "Number of bugs";
	}

	@Override
	public String getSummaryInformation() {
		return "The number of bugs over time. Lorum ipsum.";
	}

}
