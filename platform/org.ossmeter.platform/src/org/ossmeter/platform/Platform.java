package org.ossmeter.platform;

import static java.nio.file.Paths.get;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.ossmeter.platform.delta.bugtrackingsystem.ExtensionPointBugTrackingSystemManager;
import org.ossmeter.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.ossmeter.platform.delta.communicationchannel.ExtensionPointCommunicationChannelManager;
import org.ossmeter.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.ossmeter.platform.delta.vcs.ExtensionPointVcsManager;
import org.ossmeter.platform.delta.vcs.PlatformVcsManager;
import org.ossmeter.repository.model.Project;

import com.mongodb.Mongo;

public class Platform {

	protected ProjectRepositoryManager projectRepositoryManager = null;
	protected IMetricProviderManager metricProviderManager = null;
	protected SimpleMetricProviderScheduler scheduler = null;
	protected PlatformVcsManager vcsManager = null;
	protected PlatformCommunicationChannelManager communicationChannelManager = null;
	protected PlatformBugTrackingSystemManager bugTrackingSystemManager = null;
	protected Mongo mongo;
	protected final Path localStorageHomeDirectory = get(System.getProperty("user.home"), "ossmeter");

	public Platform(Mongo mongo) {
		this.mongo = mongo;
		projectRepositoryManager = new ProjectRepositoryManager(mongo);
		metricProviderManager = new ExtensionPointMetricProviderManager();
		scheduler = new SimpleMetricProviderScheduler(this);
		vcsManager = new ExtensionPointVcsManager();
		communicationChannelManager = new ExtensionPointCommunicationChannelManager();
		bugTrackingSystemManager = new ExtensionPointBugTrackingSystemManager();
		initialisePlatformLocalStorage();
	}
	
	public void run() throws Exception {
		scheduler.run();
	}
	
	public Path getLocalStorageHomeDirectory() {
		return localStorageHomeDirectory;
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
	
	private void initialisePlatformLocalStorage() {
		try{	
			if (Files.notExists(localStorageHomeDirectory)) {
					Files.createDirectory(localStorageHomeDirectory);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
