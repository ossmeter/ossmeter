package org.ossmeter.platform;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.ossmeter.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.ossmeter.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.ossmeter.platform.delta.vcs.PlatformVcsManager;
import org.ossmeter.repository.model.Project;

import com.mongodb.DB;

public class MetricProviderContext {
	
	protected Date date;
	protected Platform platform;
	protected Logger logger;
	
	public MetricProviderContext(Platform platform, Logger logger) {
		this.platform = platform;
		this.logger = logger;
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
	
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
	
	public Logger getLogger() {
		return logger;
	}
	
	public IProgressMonitor getProgressMonitor() {
		return null; // TODO
	}
}
