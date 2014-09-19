package org.ossmeter.metricprovider.trans.contentclassesnntp;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.trans.contentclassesnntp.model.ContentClass;
import org.ossmeter.metricprovider.trans.contentclassesnntp.model.ContentClassesNNTP;
import org.ossmeter.metricprovider.trans.contentclassesnntp.model.NewsgroupData;
import org.ossmeter.metricprovider.trans.threads.ThreadsMetricProvider;
import org.ossmeter.metricprovider.trans.threads.model.ArticleData;
import org.ossmeter.metricprovider.trans.threads.model.ThreadData;
import org.ossmeter.metricprovider.trans.threads.model.Threads;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.mongodb.DB;

public class ContentClassesMetricProvider implements ITransientMetricProvider<ContentClassesNNTP>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;

	protected MetricProviderContext context;
	
	protected List<IMetricProvider> uses;

	@Override
	public String getIdentifier() {
		return ContentClassesMetricProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
		}
		return false;
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
		this.platformBugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
	}

	@Override
	public ContentClassesNNTP adapt(DB db) {
		return new ContentClassesNNTP(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, ContentClassesNNTP db) {

		if (uses.size()!=1) {
			System.err.println("Metric: " + getIdentifier() + " failed to retrieve " + 
								"the transient metric it needs!");
			System.exit(-1);
		}

		Threads usedThreads = 
				((ThreadsMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		db.getNewsgroups().getDbCollection().drop();
		db.getContentClasses().getDbCollection().drop();
		db.sync();
		
		for (ThreadData thread: usedThreads.getThreads()) {
			
			for (ArticleData articleData: thread.getArticles()) {
				
				Iterable<NewsgroupData> newsgroupDataIt = 
					db.getNewsgroups().findByUrl_name(articleData.getUrl_name());
				NewsgroupData newsgroupData = null;
				for (NewsgroupData ngd:  newsgroupDataIt) 
					newsgroupData = ngd;
				if (newsgroupData == null) {
					newsgroupData = new NewsgroupData();
					newsgroupData.setUrl_name(articleData.getUrl_name());
					newsgroupData.setNumberOfArticles(0);
					db.getNewsgroups().add(newsgroupData);
				}
				newsgroupData.setNumberOfArticles(newsgroupData.getNumberOfArticles() + 1);
				
				Iterable<ContentClass> contentClassIt =db.getContentClasses().find(
								ContentClass.URL_NAME.eq(articleData.getUrl_name()),
								ContentClass.CLASSLABEL.eq(articleData.getContentClass()));
				ContentClass contentClass = null;
				for (ContentClass cc:  contentClassIt) contentClass = cc;
				if (contentClass == null) {
					contentClass = new ContentClass();
					contentClass.setUrl_name(articleData.getUrl_name());
					contentClass.setClassLabel(articleData.getContentClass());
					contentClass.setNumberOfArticles(0);
					db.getContentClasses().add(contentClass);
				}
				contentClass.setNumberOfArticles(contentClass.getNumberOfArticles() + 1);
				db.sync();
			}
		}

		for (NewsgroupData newsgroupData: db.getNewsgroups()) {
			Iterable<ContentClass> contentClassIt = 
					db.getContentClasses().find(ContentClass.URL_NAME.eq(newsgroupData.getUrl_name()));
			for (ContentClass contentClass:  contentClassIt)
				contentClass.setPercentage( ((float) 100 * contentClass.getNumberOfArticles()) / 
														newsgroupData.getNumberOfArticles());
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
