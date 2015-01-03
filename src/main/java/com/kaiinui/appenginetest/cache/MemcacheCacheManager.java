package com.kaiinui.appenginetest.cache;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.googlecode.spring.appengine.cache.memcache.MemcacheCache;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by kaiinui on 2015/01/04.
 */
public class MemcacheCacheManager extends AbstractCacheManager {

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return Collections.emptySet();
    }

    @Override
    public Cache getCache(String name) {
        Cache cache = super.getCache(name);
        if (cache == null) {
            MemcacheService memcacheService = MemcacheServiceFactory.getMemcacheService(name);
            cache = new MemcacheCache(memcacheService);
            addCache(cache);
        }
        return cache;
    }
}
