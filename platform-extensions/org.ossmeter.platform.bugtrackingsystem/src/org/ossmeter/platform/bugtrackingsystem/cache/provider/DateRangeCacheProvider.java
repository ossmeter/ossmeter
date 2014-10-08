package org.ossmeter.platform.bugtrackingsystem.cache.provider;

import java.util.Date;
import java.util.Iterator;

import org.ossmeter.platform.bugtrackingsystem.cache.CacheProvider;
import org.ossmeter.repository.model.BugTrackingSystem;

public abstract class DateRangeCacheProvider<T, K>  extends CacheProvider<T, K>{
	public abstract Iterator<T> getItems(Date after, Date before,
			BugTrackingSystem bugTracker) throws Exception;
}