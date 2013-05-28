package org.ossmeter.platform;

import org.ossmeter.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.ossmeter.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.ossmeter.platform.delta.vcs.PlatformVcsManager;
import org.ossmeter.repository.model.Project;

import com.mongodb.DB;

public class MetricProviderContext {
	
	protected Platform platform;
	
	public MetricProviderContext(Platform platform) {
		this.platform = platform;
	}
	
	public void setPlatform(Platform platform) {
		this.platform = platform;
	}
	
	public PlatformVcsManager getPlatformVcsManager() {
		return platform.getVcsManager();
	}
	
	public PlatformCommunicationChannelManager getPlatformCommunicationChannelManager() {
		return platform.getCommunicationChannelManager();
	}
	
	public PlatformBugTrackingSystemManager getPlatformBugTrackingSystemManager() {
		return platform.getBugTrackingSystemManager();
	}
	
	public DB getProjectDB(Project project) {
		return platform.getMetricsRepository(project).getDb();
	}
}
