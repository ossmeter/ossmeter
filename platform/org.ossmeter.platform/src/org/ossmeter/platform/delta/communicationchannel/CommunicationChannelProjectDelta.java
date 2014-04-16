package org.ossmeter.platform.delta.communicationchannel;

import java.util.ArrayList;
import java.util.List;

import org.ossmeter.platform.Date;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;

import com.mongodb.DB;


public class CommunicationChannelProjectDelta {
	
	protected List<CommunicationChannelDelta> communicationChannelDeltas = new ArrayList<CommunicationChannelDelta>();
	
	public CommunicationChannelProjectDelta(DB db, Project project, Date date, 
			ICommunicationChannelManager communicationChannelManager) throws Exception {
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			communicationChannelDeltas.add(communicationChannelManager.getDelta(db, communicationChannel, date));
		}
	}
	
	public List<CommunicationChannelDelta> getCommunicationChannelSystemDeltas() {
		return communicationChannelDeltas;
	}
	
	public void setRepoDeltas(List<CommunicationChannelDelta> communicationChannelDeltas) {
		this.communicationChannelDeltas = communicationChannelDeltas;
	}
}
