/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.ossmeter.platform.delta.bugtrackingsystem;

import java.util.List;

import org.ossmeter.platform.Date;
import org.ossmeter.platform.cache.bugtrackingsystem.BugTrackingSystemContentsCache;
import org.ossmeter.platform.cache.bugtrackingsystem.BugTrackingSystemDeltaCache;
import org.ossmeter.platform.cache.bugtrackingsystem.IBugTrackingSystemContentsCache;
import org.ossmeter.platform.cache.bugtrackingsystem.IBugTrackingSystemDeltaCache;
import org.ossmeter.platform.delta.NoManagerFoundException;
import org.ossmeter.repository.model.BugTrackingSystem;

import com.mongodb.DB;

public abstract class PlatformBugTrackingSystemManager implements IBugTrackingSystemManager<BugTrackingSystem> {
	
	protected List<IBugTrackingSystemManager> bugTrackingSystemManagers;
	protected IBugTrackingSystemDeltaCache deltaCache;
	protected IBugTrackingSystemContentsCache contentsCache;
	
	abstract public List<IBugTrackingSystemManager> getBugTrackingSystemManagers();
	
	@Override
	public boolean appliesTo(BugTrackingSystem bugTrackingSystem) {
		try {
			return getBugTrackingSystemManager(bugTrackingSystem) != null;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	protected IBugTrackingSystemManager getBugTrackingSystemManager(BugTrackingSystem bugTrackingSystem) throws Exception {
		for (IBugTrackingSystemManager bugTrackingSystemManager : getBugTrackingSystemManagers()) {
			if (bugTrackingSystemManager.appliesTo(bugTrackingSystem)) {
				return bugTrackingSystemManager;
			}
		}
		throw new NoManagerFoundException("No bug tracking system manager found for " + bugTrackingSystem);
	}
	
	@Override
	public Date getFirstDate(DB db, BugTrackingSystem bugTrackingSystem)
			throws Exception {
		IBugTrackingSystemManager bugTrackingSystemManager = getBugTrackingSystemManager(bugTrackingSystem);
		if (bugTrackingSystemManager != null) {
			return bugTrackingSystemManager.getFirstDate(db, bugTrackingSystem);
		}
		
		return null;
	}
	
	@Override
	public BugTrackingSystemDelta getDelta(DB db, BugTrackingSystem bugTrackingSystem, Date date)  throws Exception {
		BugTrackingSystemDelta cache = getDeltaCache().getCachedDelta(bugTrackingSystem.getUrl(), date);
		if (cache != null) {
			System.err.println("BugTrackingSystemBug CACHE HIT!");
			return cache;
		}
		
		IBugTrackingSystemManager bugTrackingSystemManager = getBugTrackingSystemManager(bugTrackingSystem);
		if (bugTrackingSystemManager != null) {
			BugTrackingSystemDelta delta = bugTrackingSystemManager.getDelta(db, bugTrackingSystem, date);
			getDeltaCache().putDelta(bugTrackingSystem.getUrl(), date, delta);
			return delta;
		}
		return null;
	}

	@Override
	public String getContents(DB db, BugTrackingSystem bugTrackingSystem, BugTrackingSystemBug bug) throws Exception {
		String cache = getContentsCache().getCachedContents(bug);
		if (cache != null) {
			System.err.println("BugTrackingSystemBug CACHE HIT!");
			return cache;
		}

		IBugTrackingSystemManager bugTrackingSystemManager =
		getBugTrackingSystemManager((bug.getBugTrackingSystem()));
		
		if (bugTrackingSystemManager != null) {
			String contents = bugTrackingSystemManager.getContents(db, bugTrackingSystem, bug);
			getContentsCache().putContents(bug, contents);
			return contents;
		}
		return null;
	}
	
	@Override
	public String getContents(DB db, BugTrackingSystem bugTrackingSystem, BugTrackingSystemComment comment) throws Exception {
		String cache = getContentsCache().getCachedContents(comment);
		if (cache != null) {
			System.err.println("BugTrackingSystemBug CACHE HIT!");
			return cache;
		}

		IBugTrackingSystemManager bugTrackingSystemManager =
									getBugTrackingSystemManager((comment.getBugTrackingSystem()));
		
		if (bugTrackingSystemManager != null) {
			String contents = bugTrackingSystemManager.getContents(db, bugTrackingSystem, comment);
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
