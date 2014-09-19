package org.ossmeter.metricprovider.trans.contentclassesbugzilla;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.trans.bugheadermetadata.BugHeaderMetadataMetricProvider;
import org.ossmeter.metricprovider.trans.bugheadermetadata.model.BugHeaderMetadata;
import org.ossmeter.metricprovider.trans.bugheadermetadata.model.CommentData;
import org.ossmeter.metricprovider.trans.contentclassesbugzilla.model.BugzillaData;
import org.ossmeter.metricprovider.trans.contentclassesbugzilla.model.ContentClass;
import org.ossmeter.metricprovider.trans.contentclassesbugzilla.model.ContentClassesBugs;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.bts.bugzilla.Bugzilla;

import com.mongodb.DB;

public class ContentClassesMetricProvider implements ITransientMetricProvider<ContentClassesBugs>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;

	protected MetricProviderContext context;
	
	protected List<IMetricProvider> uses;

	@Override
	public String getIdentifier() {
		return ContentClassesMetricProvider.class.getCanonicalName();
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
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(BugHeaderMetadataMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
		this.platformBugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
	}

	@Override
	public ContentClassesBugs adapt(DB db) {
		return new ContentClassesBugs(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, ContentClassesBugs db) {

		if (uses.size()!=1) {
			System.err.println("Metric: " + getIdentifier() + " failed to retrieve " + 
								"the transient metric it needs!");
			System.exit(-1);
		}

		BugHeaderMetadata bugHeaderMetadata = 
				((BugHeaderMetadataMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		db.getBugzillas().getDbCollection().drop();
		db.getContentClasses().getDbCollection().drop();
		db.sync();

		for (CommentData commentData: bugHeaderMetadata.getComments()) {
			Iterable<BugzillaData> bugzillaDataIt = 
					db.getBugzillas().find(BugzillaData.URL.eq(commentData.getUrl()),
									 	   BugzillaData.COMPONENT.eq(commentData.getComponent()),
									 	   BugzillaData.PRODUCT.eq(commentData.getProduct()));
			BugzillaData bugzillaData = null;
			for (BugzillaData bd:  bugzillaDataIt) bugzillaData = bd;
			if (bugzillaData == null) {
				bugzillaData = new BugzillaData();
				bugzillaData.setUrl(commentData.getUrl());
				bugzillaData.setProduct(commentData.getProduct());
				bugzillaData.setComponent(commentData.getComponent());
				bugzillaData.setNumberOfComments(0);
				db.getBugzillas().add(bugzillaData);
			}
			bugzillaData.setNumberOfComments(bugzillaData.getNumberOfComments() + 1);
			
			Iterable<ContentClass> contentClassIt = 
					db.getContentClasses().find(ContentClass.URL.eq(commentData.getUrl()),
												ContentClass.COMPONENT.eq(commentData.getComponent()),
												ContentClass.PRODUCT.eq(commentData.getProduct()),
												ContentClass.CLASSLABEL.eq(commentData.getContentClass()));
			ContentClass contentClass = null;
			for (ContentClass cc:  contentClassIt) contentClass = cc;
			if (contentClass == null) {
				contentClass = new ContentClass();
				contentClass.setUrl(commentData.getUrl());
				contentClass.setProduct(commentData.getProduct());
				contentClass.setComponent(commentData.getComponent());
				contentClass.setClassLabel(commentData.getContentClass());
				contentClass.setNumberOfComments(0);
				db.getContentClasses().add(contentClass);
			}
			contentClass.setNumberOfComments(contentClass.getNumberOfComments() + 1);
			
			db.sync();
		}

		for (BugzillaData bugzillaData: db.getBugzillas()) {
			Iterable<ContentClass> contentClassIt = 
					db.getContentClasses().find(ContentClass.URL.eq(bugzillaData.getUrl()),
												ContentClass.COMPONENT.eq(bugzillaData.getComponent()),
												ContentClass.PRODUCT.eq(bugzillaData.getProduct()));
			for (ContentClass contentClass:  contentClassIt)
				contentClass.setPercentage( ((float) 100 * contentClass.getNumberOfComments()) / 
														bugzillaData.getNumberOfComments());
		}

		db.sync();
	}

	@Override
	public String getShortIdentifier() {
		return "contentClassesBugzilla";
	}

	@Override
	public String getFriendlyName() {
		return "Content Classes in Bugzilla Comments";
	}

	@Override
	public String getSummaryInformation() {
		return "Content Classes in Bugzilla Comments";
	}

}
