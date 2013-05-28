package org.ossmeter.platform.cache.bugtrackingsystem;

import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;

public interface IBugTrackingSystemContentsCache {
	
	public String getCachedContents(BugTrackingSystemBug bug);
	
	public String getCachedContents(BugTrackingSystemComment comment);
	
	public void putContents(BugTrackingSystemBug bug, String contents);

	public void putContents(BugTrackingSystemComment comment, String contents);

}
