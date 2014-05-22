package org.ossmeter.metricprovider.numberofbugzillacomments;

import java.util.Collections;
import java.util.List;

import org.ossmeter.metricprovider.numberofbugzillacomments.model.BugzillaData;
import org.ossmeter.metricprovider.numberofbugzillacomments.model.Nobc;
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

public class NobcMetricProvider implements ITransientMetricProvider<Nobc>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;

	@Override
	public String getIdentifier() {
		return NobcMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "nobc";
	}

	@Override
	public String getFriendlyName() {
		return "Number of Bugzilla comments";
	}

	@Override
	public String getSummaryInformation() {
		return "The number of Bugzilla comments over time. Lorum ipsum.";
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
	public Nobc adapt(DB db) {
		return new Nobc(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, Nobc db) {
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
			bugzillaData.setNumberOfComments(bugTrackingSystemDelta.getComments().size());
//			System.out.println("bugTrackingSystemDelta.getComments().size(): " + 
//								bugTrackingSystemDelta.getComments().size());
			db.sync();
		}
	}

}
