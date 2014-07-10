package org.ossmeter.platform.bugtrackingsystem;

import java.util.HashMap;
import java.util.Map;

import org.ossmeter.repository.model.BugTrackingSystem;

public class BugTrackerItemCaches<T, K> {
    private Map<String, BugTrackerItemCache<T, K>> map = new HashMap<String, BugTrackerItemCache<T, K>>();
    private BugTrackerItemCache.Provider<T, K> provider;

    public BugTrackerItemCaches(BugTrackerItemCache.Provider<T, K> provider) {
        if (null == provider) {
            throw new IllegalArgumentException("provider cannot be null");
        }

        this.provider = provider;
    }

    public BugTrackerItemCache<T, K> getCache(BugTrackingSystem bugTracker,
            boolean createIfNotExists) {
        String id = bugTracker.getOSSMeterId();

        BugTrackerItemCache<T, K> cache = null;

        cache = map.get(id);

        if (null == cache && createIfNotExists) {
            cache = new BugTrackerItemCache<T, K>(bugTracker, provider);
            map.put(id, cache);
        }

        return cache;
    }
}
