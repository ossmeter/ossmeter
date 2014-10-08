package org.ossmeter.platform.bugtrackingsystem.cache;

import java.util.HashMap;
import java.util.Map;

import org.ossmeter.repository.model.BugTrackingSystem;

public class Caches<T, K> {
    private Map<String, Cache<T, K>> map = new HashMap<String, Cache<T, K>>();
    private CacheProvider<T, K> provider;

    public Caches(CacheProvider<T, K> provider) {
        if (null == provider) {
            throw new IllegalArgumentException("provider cannot be null");
        }

        this.provider = provider;
    }

    public Cache<T, K> getCache(BugTrackingSystem bugTracker,
            boolean createIfNotExists) {
        String id = bugTracker.getOSSMeterId();

        Cache<T, K> cache = null;

        cache = map.get(id);

        if (null == cache && createIfNotExists) {
            cache = new Cache<T, K>(bugTracker, provider);
            map.put(id, cache);
        }

        return cache;
    }
}
