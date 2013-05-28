package org.ossmeter.platform.cache.bugtrackingsystem;
import java.util.concurrent.ConcurrentNavigableMap;

import org.mapdb.DBMaker;
import org.mapdb.Fun;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;

public class BugTrackingSystemDeltaCache implements IBugTrackingSystemDeltaCache {
	
	protected ConcurrentNavigableMap<Fun.Tuple2<String, String>, BugTrackingSystemDelta> map;
	
	public BugTrackingSystemDeltaCache() {
		initialiseDB();
	}
	
	protected void initialiseDB() {
		 map = DBMaker.newTempTreeMap(); // TODO: Do we want to allocate local storage for this?
	}
	
	public BugTrackingSystemDelta getCachedDelta(String bugTrackingSystemUrl, Date date){
		return map.get(Fun.t2(bugTrackingSystemUrl, date.toString()));
	}
	
	// TODO: This needs to be bounded
	public void putDelta(String bugTrackingSystemUrl, Date date, BugTrackingSystemDelta delta) {
		map.put(Fun.t2(bugTrackingSystemUrl, date.toString()), delta);
	}
	
}
