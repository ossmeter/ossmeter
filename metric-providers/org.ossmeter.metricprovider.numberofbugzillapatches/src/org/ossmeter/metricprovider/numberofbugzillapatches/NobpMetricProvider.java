package org.ossmeter.metricprovider.numberofbugzillapatches;

import java.util.Collections;
import java.util.List;

import org.ossmeter.metricprovider.numberofbugzillapatches.model.BugzillaData;
import org.ossmeter.metricprovider.numberofbugzillapatches.model.Nobp;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemAttachment;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemProjectDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.bts.bugzilla.Bugzilla;
import org.ossmeter.repository.model.Project;

import com.mongodb.DB;

public class NobpMetricProvider implements ITransientMetricProvider<Nobp>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;

	@Override
	public String getIdentifier() {
		return NobpMetricProvider.class.getCanonicalName();
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
	public Nobp adapt(DB db) {
		return new Nobp(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, Nobp db) {
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
			int patches = 0;
			for (BugTrackingSystemAttachment attachment :bugTrackingSystemDelta.getAttachments()) {
//				System.out.println("bugId: " + attachment.getBugId() 
//						+ "\tattachmentId: " + attachment.getAttachmentId() 
//						+ "\tattachmentFiletypeType: " + attachment.getFilename() 
//						+ "\tattachmentMIMEtype: " + attachment.getMimeType());
				if ((attachment.getFilename().contains("patch"))
						||(attachment.getMimeType().contains("patch"))) {
					patches++;
				}
			}
			System.err.println(patches + " patches captured");
			bugzillaData.setNumberOfPatches(patches);
			db.sync();
		}
	}

	@Override
	public String getShortIdentifier() {
		return "bugpatches";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Patches";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric keeps the number of patches submitted for each bug.";
	}

}
