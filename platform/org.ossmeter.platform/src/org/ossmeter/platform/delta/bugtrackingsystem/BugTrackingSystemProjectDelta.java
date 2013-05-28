package org.ossmeter.platform.delta.bugtrackingsystem;

import java.util.ArrayList;
import java.util.List;

import org.ossmeter.platform.Date;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.Project;


public class BugTrackingSystemProjectDelta {
	
	protected List<BugTrackingSystemDelta> bugTrackingSystemDeltas = 
			new ArrayList<BugTrackingSystemDelta>();
	
	public BugTrackingSystemProjectDelta(Project project, Date date, 
			IBugTrackingSystemManager bugTrackingSystemManager) throws Exception {
		for (BugTrackingSystem bugTrackingSystem : project.getBugTrackingSystems()) {
			bugTrackingSystemDeltas.add(bugTrackingSystemManager.getDelta(bugTrackingSystem, date));
		}
	}
	
	public List<BugTrackingSystemDelta> getBugTrackingSystemDeltas() {
		return bugTrackingSystemDeltas;
	}
	
	public void setRepoDeltas(List<BugTrackingSystemDelta> bugTrackingSystemDeltas) {
		this.bugTrackingSystemDeltas = bugTrackingSystemDeltas;
	}
}
