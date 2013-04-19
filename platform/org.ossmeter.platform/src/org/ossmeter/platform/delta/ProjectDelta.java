package org.ossmeter.platform.delta;

import org.ossmeter.platform.Date;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelProjectDelta;
import org.ossmeter.platform.delta.communicationchannel.ICommunicationChannelManager;
import org.ossmeter.platform.delta.vcs.IVcsManager;
import org.ossmeter.platform.delta.vcs.VcsProjectDelta;
import org.ossmeter.repository.model.Project;

public class ProjectDelta {
	protected Date date;
	protected Project project;
	protected IVcsManager vcsManager;
	protected ICommunicationChannelManager communicationChannelManager;
	
	protected VcsProjectDelta vcsDelta;
	protected CommunicationChannelProjectDelta communicationChannelDelta;
//	protected TheOtherDelta
	
	public ProjectDelta(Project project, Date date, 
			IVcsManager vcsManager, ICommunicationChannelManager communicationChannelManager) {
		this.project = project;
		this.date = date;	
		this.vcsManager = vcsManager;
		this.communicationChannelManager = communicationChannelManager;
	}
	
	public boolean create() {
		try {
			vcsDelta = new VcsProjectDelta(project, date, vcsManager);
			communicationChannelDelta = new CommunicationChannelProjectDelta(project, date, communicationChannelManager);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Date getDate() {
		return date;
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setVcsDelta(VcsProjectDelta vcsDelta) {
		this.vcsDelta = vcsDelta;
	}
	
	public VcsProjectDelta getVcsDelta() {
		return this.vcsDelta;
	}

	public void setCommunicationChannelDelta(CommunicationChannelProjectDelta communicationChannelDelta) {
		this.communicationChannelDelta = communicationChannelDelta;
	}
	
	public CommunicationChannelProjectDelta getCommunicationChannelDelta() {
		return this.communicationChannelDelta;
	}

}
