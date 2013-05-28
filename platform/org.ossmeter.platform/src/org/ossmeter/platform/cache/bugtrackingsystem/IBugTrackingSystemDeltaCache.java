package org.ossmeter.platform.cache.bugtrackingsystem;

import org.ossmeter.platform.Date;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;

public interface IBugTrackingSystemDeltaCache {
	
	public BugTrackingSystemDelta getCachedDelta(String bugTrackingSystemUrl, Date date);
	
	public void putDelta(String bugTrackingSystemUrl, Date date, BugTrackingSystemDelta delta);
}
