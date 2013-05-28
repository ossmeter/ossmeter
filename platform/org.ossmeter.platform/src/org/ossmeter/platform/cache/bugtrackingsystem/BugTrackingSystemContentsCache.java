package org.ossmeter.platform.cache.bugtrackingsystem;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;

public class BugTrackingSystemContentsCache implements IBugTrackingSystemContentsCache {
	
	protected HTreeMap<BugTrackingSystemBug, String> bugMap;
	protected HTreeMap<BugTrackingSystemComment, String> commentMap;
	
	public BugTrackingSystemContentsCache() {
		initialiseDB();
	}
	
	protected void initialiseDB() {
		 bugMap =     DBMaker.newTempHashMap(); // TODO: Do we want to allocate local storage for this?
		 commentMap = DBMaker.newTempHashMap(); // TODO: Do we want to allocate local storage for this?
	}
	
	@Override
	public String getCachedContents(BugTrackingSystemBug bug){
		return bugMap.get(bug);
	}
	
	// TODO: This needs to be bounded
	@Override
	public void putContents(BugTrackingSystemBug bug, String contents) {
		bugMap.put(bug, contents);
	}

	@Override
	public String getCachedContents(BugTrackingSystemComment comment){
		return commentMap.get(comment);
	}
	
	// TODO: This needs to be bounded
	@Override
	public void putContents(BugTrackingSystemComment comment, String contents) {
		commentMap.put(comment, contents);
	}
}
