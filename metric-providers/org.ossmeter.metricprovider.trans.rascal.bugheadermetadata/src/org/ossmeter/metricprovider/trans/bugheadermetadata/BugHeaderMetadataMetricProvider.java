package org.ossmeter.metricprovider.trans.bugheadermetadata;

import java.util.Collections;
import java.util.List;

import org.ossmeter.metricprovider.trans.bugheadermetadata.model.BugData;
import org.ossmeter.metricprovider.trans.bugheadermetadata.model.BugHeaderMetadata;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.bugtrackingsystem.bugzilla.BugzillaBug;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemProjectDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.bts.bugzilla.Bugzilla;
import org.ossmeter.repository.model.Project;

import com.mongodb.DB;

public class BugHeaderMetadataMetricProvider implements ITransientMetricProvider<BugHeaderMetadata>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;

	@Override
	public String getIdentifier() {
		return BugHeaderMetadataMetricProvider.class.getCanonicalName();
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
	public BugHeaderMetadata adapt(DB db) {
		return new BugHeaderMetadata(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, BugHeaderMetadata db) {
		BugTrackingSystemProjectDelta delta = projectDelta.getBugTrackingSystemDelta();
		
		for (BugTrackingSystemDelta bugTrackingSystemDelta : delta.getBugTrackingSystemDeltas()) {
			BugTrackingSystem bugTrackingSystem = bugTrackingSystemDelta.getBugTrackingSystem();
			if (!(bugTrackingSystem instanceof Bugzilla)) continue;
			Bugzilla bugzilla = (Bugzilla) bugTrackingSystem;
			for (BugTrackingSystemBug bug: bugTrackingSystemDelta.getNewBugs())
				storeBug(db, bugzilla, (BugzillaBug) bug);
			for (BugTrackingSystemBug bug: bugTrackingSystemDelta.getUpdatedBugs())
				storeBug(db, bugzilla, (BugzillaBug) bug);
			db.sync();
		}
	}
	private BugData storeBug(BugHeaderMetadata db, Bugzilla bugzilla, BugzillaBug bug) {
		Iterable<BugData> bugDataIt = 
				db.getBugs().find(BugData.URL.eq(bugzilla.getUrl()), 
								  BugData.PRODUCT.eq(bugzilla.getProduct()), 
								  BugData.COMPONENT.eq(bugzilla.getComponent()), 
								  BugData.BUGID.eq(bug.getBugId()));
		BugData bugData = null;
		for (BugData bd:  bugDataIt) bugData = bd;
		if (bugData == null) {
			bugData = new BugData();
			bugData.setUrl(bugzilla.getUrl());
			bugData.setProduct(bugzilla.getProduct());
			bugData.setComponent(bugzilla.getComponent());
			bugData.setBugId(bug.getBugId());
			bugData.setCreationTime(bug.getCreationTime().toString());
			db.getBugs().add(bugData);
		}
		bugData.setOperatingSystem(bug.getOperatingSystem());
		bugData.setPriority(bug.getPriority());
		bugData.setResolution(bug.getResolution());
		bugData.setStatus(bug.getStatus());
		if (bug.getLastClosed()!=null)
			bugData.setLastClosedTime(bug.getLastClosed().toString());
		return bugData;
	}

	@Override
	public String getShortIdentifier() {
		return "bugheadermetadata";
	}

	@Override
	public String getFriendlyName() {
		return "Bug Header Metadata";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric keeps various metadata of bugzilla bug header, " +
				"i.e. priority, status, operation system and resolution.";
	}

}
