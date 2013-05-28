package org.ossmeter.platform.delta.bugtrackingsystem;

import java.util.List;

import org.ossmeter.platform.Date;
import org.ossmeter.platform.cache.bugtrackingsystem.BugTrackingSystemContentsCache;
import org.ossmeter.platform.cache.bugtrackingsystem.BugTrackingSystemDeltaCache;
import org.ossmeter.platform.cache.bugtrackingsystem.IBugTrackingSystemContentsCache;
import org.ossmeter.platform.cache.bugtrackingsystem.IBugTrackingSystemDeltaCache;
import org.ossmeter.repository.model.BugTrackingSystem;

public abstract class PlatformBugTrackingSystemManager implements IBugTrackingSystemManager<BugTrackingSystem> {
	
	protected List<IBugTrackingSystemManager> bugTrackingSystemManagers;
	protected IBugTrackingSystemDeltaCache deltaCache;
	protected IBugTrackingSystemContentsCache contentsCache;
	
	abstract public List<IBugTrackingSystemManager> getBugTrackingSystemManagers();
	
	@Override
	public boolean appliesTo(BugTrackingSystem bugTrackingSystem) {
		return getBugTrackingSystemManager(bugTrackingSystem) != null;
	}

	protected IBugTrackingSystemManager getBugTrackingSystemManager(BugTrackingSystem bugTrackingSystem) {
		for (IBugTrackingSystemManager bugTrackingSystemManager : bugTrackingSystemManagers) {
			if (bugTrackingSystemManager.appliesTo(bugTrackingSystem)) {
				return bugTrackingSystemManager;
			}
		}
		return null;
	}
	
	@Override
	public Date getFirstDate(BugTrackingSystem bugTrackingSystem)
			throws Exception {
		for (IBugTrackingSystemManager bugTrackingSystemManager : bugTrackingSystemManagers) {
			if (bugTrackingSystemManager.appliesTo(bugTrackingSystem)) {
				return bugTrackingSystemManager.getFirstDate(bugTrackingSystem);
			}
		}
		return null;
	}
	
	@Override
	public BugTrackingSystemDelta getDelta(BugTrackingSystem bugTrackingSystem, Date date)  throws Exception {
		BugTrackingSystemDelta cache = getDeltaCache().getCachedDelta(bugTrackingSystem.getUrl(), date);
		if (cache != null) {
			System.err.println("BugTrackingSystemBug CACHE HIT!");
			return cache;
		}
		
		IBugTrackingSystemManager bugTrackingSystemManager = getBugTrackingSystemManager(bugTrackingSystem);
		if (bugTrackingSystemManager != null) {
			BugTrackingSystemDelta delta = bugTrackingSystemManager.getDelta(bugTrackingSystem, date);
			getDeltaCache().putDelta(bugTrackingSystem.getUrl(), date, delta);
			return delta;
		}
		return null;
	}

	@Override
	public String getContents(BugTrackingSystem bugTrackingSystem, BugTrackingSystemBug bug) throws Exception {
		String cache = getContentsCache().getCachedContents(bug);
		if (cache != null) {
			System.err.println("BugTrackingSystemBug CACHE HIT!");
			return cache;
		}

		IBugTrackingSystemManager bugTrackingSystemManager =
		getBugTrackingSystemManager((bug.getBugTrackingSystem()));
		
		if (bugTrackingSystemManager != null) {
			String contents = bugTrackingSystemManager.getContents(bugTrackingSystem, bug);
			getContentsCache().putContents(bug, contents);
			return contents;
		}
		return null;
	}
	
	@Override
	public String getContents(BugTrackingSystem bugTrackingSystem, BugTrackingSystemComment comment) throws Exception {
		String cache = getContentsCache().getCachedContents(comment);
		if (cache != null) {
			System.err.println("BugTrackingSystemBug CACHE HIT!");
			return cache;
		}

		IBugTrackingSystemManager bugTrackingSystemManager =
									getBugTrackingSystemManager((comment.getBugTrackingSystem()));
		
		if (bugTrackingSystemManager != null) {
			String contents = bugTrackingSystemManager.getContents(bugTrackingSystem, comment);
			getContentsCache().putContents(comment, contents);
			return contents;
		}
		return null;
	}
	
	public IBugTrackingSystemContentsCache getContentsCache() {
		if (contentsCache == null) {
			contentsCache = new BugTrackingSystemContentsCache();
		}
		return contentsCache;
	}
	
	public IBugTrackingSystemDeltaCache getDeltaCache() {
		if (deltaCache == null) {
			deltaCache = new BugTrackingSystemDeltaCache();
		}
		return deltaCache;		
	}

}
