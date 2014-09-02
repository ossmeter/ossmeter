package org.ossmeter.platform.delta.bugtrackingsystem;

import org.ossmeter.platform.Date;
import org.ossmeter.repository.model.BugTrackingSystem;

import com.mongodb.DB;

public interface IBugTrackingSystemManager<B extends BugTrackingSystem> {
	
	public boolean appliesTo(B bugTrackingSystem);
	
	public BugTrackingSystemDelta getDelta(DB db, B bugTrackingSystem, Date date) throws Exception;

	public Date getFirstDate(DB db, B bugTrackingSystem) throws Exception;
	
	public String getContents(DB db, B bugTrackingSystem, BugTrackingSystemBug bug) throws Exception;
	
	public String getContents(DB db, B bugTrackingSystem, BugTrackingSystemComment comment) throws Exception;

}
