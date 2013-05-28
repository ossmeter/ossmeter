package org.ossmeter.platform.delta.bugtrackingsystem;

import org.ossmeter.platform.Date;
import org.ossmeter.repository.model.BugTrackingSystem;

public interface IBugTrackingSystemManager<B extends BugTrackingSystem> {
	
	public boolean appliesTo(B bugTrackingSystem);
	
	public BugTrackingSystemDelta getDelta(B bugTrackingSystem, Date date) throws Exception;

	public Date getFirstDate(B bugTrackingSystem) throws Exception;
	
	public String getContents(B bugTrackingSystem, BugTrackingSystemBug bug) throws Exception;
	
	public String getContents(B bugTrackingSystem, BugTrackingSystemComment comment) throws Exception;

}
