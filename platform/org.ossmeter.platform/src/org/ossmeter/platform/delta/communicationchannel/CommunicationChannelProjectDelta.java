package org.ossmeter.platform.delta.communicationchannel;

import java.util.ArrayList;
import java.util.List;

import org.ossmeter.platform.Date;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;


public class CommunicationChannelProjectDelta {
	
	protected List<CommunicationChannelDelta> newsgroupDeltas = new ArrayList<CommunicationChannelDelta>();
	
	public CommunicationChannelProjectDelta(Project project, Date date, 
			ICommunicationChannelManager communicationChannelManager) throws Exception {
		for (CommunicationChannel communicationChannel : project.getCommunicationChannels()) {
			newsgroupDeltas.add(communicationChannelManager.getDelta(communicationChannel, date));
		}
	}
	
	public List<CommunicationChannelDelta> getNewsgroupDeltas() {
		return newsgroupDeltas;
	}
	
	public void setRepoDeltas(List<CommunicationChannelDelta> newsgroupDeltas) {
		this.newsgroupDeltas = newsgroupDeltas;
	}
}
