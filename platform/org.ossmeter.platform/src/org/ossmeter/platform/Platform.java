package org.ossmeter.platform;

import org.ossmeter.platform.delta.vcs.ExtensionPointVcsManager;
import org.ossmeter.platform.delta.vcs.PlatformVcsManager;
import org.ossmeter.platform.delta.communicationchannel.ExtensionPointCommunicationChannelManager;
import org.ossmeter.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.ossmeter.platform.delta.bugtrackingsystem.ExtensionPointBugTrackingSystemManager;
import org.ossmeter.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.ossmeter.repository.model.Project;

import com.mongodb.Mongo;

public class Platform {

	protected ProjectRepositoryManager projectRepositoryManager = null;
	protected IMetricProviderManager metricProviderManager = null;
	protected SimpleMetricProviderScheduler scheduler = null;
	protected PlatformVcsManager vcsManager = null;
	protected PlatformCommunicationChannelManager communicationChannelManager = null;
	protected PlatformBugTrackingSystemManager bugTrackingSystemManager = null;
	protected MetricProviderContext metricProviderContext = null;
	protected Mongo mongo;
	
	public Platform(Mongo mongo) {
		this.mongo = mongo;
		projectRepositoryManager = new ProjectRepositoryManager(mongo);
		metricProviderManager = new ExtensionMetricProviderManager();
		scheduler = new SimpleMetricProviderScheduler(this);
		vcsManager = new ExtensionPointVcsManager();
		communicationChannelManager = new ExtensionPointCommunicationChannelManager();
		bugTrackingSystemManager = new ExtensionPointBugTrackingSystemManager();
		metricProviderContext = new MetricProviderContext(this);
	}
	
	public void run() throws Exception {
		scheduler.run();
	}
	
	public MetricsRepository getMetricsRepository(Project project) {
		return new MetricsRepository(project, mongo);
	}
	
	public ProjectRepositoryManager getProjectRepositoryManager() {
		return projectRepositoryManager;
	}
	
	public IMetricProviderManager getMetricProviderManager() {
		return metricProviderManager;
	}
	
	public void setMetricProviderManager(IMetricProviderManager metricProviderManager) {
		this.metricProviderManager = metricProviderManager;
	}
	
	public PlatformVcsManager getVcsManager() {
		return vcsManager;
	}
	
	public void setPlatformVcsManager(PlatformVcsManager vcsManager) {
		this.vcsManager = vcsManager;
	}
	
	public PlatformCommunicationChannelManager getCommunicationChannelManager() {
		return communicationChannelManager;
	}
	
	public void setPlatformCommunicationChannelManager(PlatformCommunicationChannelManager communicationChannelManager) {
		this.communicationChannelManager = communicationChannelManager;
	}
	
	public PlatformBugTrackingSystemManager getBugTrackingSystemManager() {
		return bugTrackingSystemManager;
	}
	
	public void setPlatformBugTrackingSystemManager(PlatformBugTrackingSystemManager bugTrackingSystemManager) {
		this.bugTrackingSystemManager = bugTrackingSystemManager;
	}
	
	public MetricProviderContext getMetricProviderContext() {
		return metricProviderContext;
	}
	
	public void setMetricProviderContext(MetricProviderContext metricProviderContext) {
		this.metricProviderContext = metricProviderContext;
	}
}
