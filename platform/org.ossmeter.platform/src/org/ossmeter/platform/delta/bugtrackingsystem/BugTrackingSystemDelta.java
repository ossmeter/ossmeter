package org.ossmeter.platform.delta.bugtrackingsystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.ossmeter.repository.model.BugTrackingSystem;

public class BugTrackingSystemDelta  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	transient protected BugTrackingSystem bugTrackingSystem;
	protected List<BugTrackingSystemBug> newBugs;
	protected List<BugTrackingSystemBug> updatedBugs;
	protected List<BugTrackingSystemComment> comments;

	
	public BugTrackingSystemDelta() {
		super();
		newBugs = new ArrayList<BugTrackingSystemBug>();
		updatedBugs = new ArrayList<BugTrackingSystemBug>();
		comments = new ArrayList<BugTrackingSystemComment>();
	}

	public BugTrackingSystem getBugTrackingSystem() {
		return bugTrackingSystem;
	}

	public void setBugTrackingSystem(BugTrackingSystem bugTrackingSystem) {
		this.bugTrackingSystem = bugTrackingSystem;
	}

	public List<BugTrackingSystemBug> getNewBugs() {
		return newBugs;
	}
	
	public List<BugTrackingSystemBug> getUpdatedBugs() {
		return updatedBugs;
	}
	
	public List<BugTrackingSystemComment> getComments() {
		return comments;
	}

}
