package org.ossmeter.platform.bugtrackingsystem.cache.provider;

import java.util.Iterator;

import org.ossmeter.platform.bugtrackingsystem.cache.CacheProvider;
import org.ossmeter.repository.model.BugTrackingSystem;

public abstract class BasicCacheProvider<T, K> extends CacheProvider<T, K> {
	public abstract Iterator<T> getItems(BugTrackingSystem bugTracker) throws Exception;
}
