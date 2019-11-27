package com.jibug.cetty.sample.container;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BrSourceDataContainer {

    private static BrSourceDataContainer mxSourceDataContainer;
    private Map<Object, Object> cacheMap;
    private BrSourceDataContainer() {
        cacheMap =new ConcurrentHashMap<Object, Object>();
    }
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock r = rwLock.readLock();
    private final Lock w = rwLock.writeLock();


    public static BrSourceDataContainer getInstance() {
        if (mxSourceDataContainer ==null) {
            synchronized (BrSourceDataContainer.class) {
                if (mxSourceDataContainer ==null) {
                    mxSourceDataContainer =new BrSourceDataContainer();
                }
            }
        }
        return mxSourceDataContainer;
    }


    /**
     * 获取所有cache信息
     * @return cacheItems
     */
    public Map<Object, Object> getCacheItems() {
        return this.cacheMap;
    }

    /**
     * 清空cache
     */
    public void clearAllItems() {
        w.lock();
        try{
            cacheMap.clear();
        }finally {
           w.unlock();
        }
    }

    /**
     * 获取指定cache信息
     * @return cacheItem
     */
    public Object getCacheItem(Object key) {
        r.lock();
        try {
            if (cacheMap.containsKey(key)) {
                return cacheMap.get(key);
            }
        } finally {
            r.unlock();
        }
        return null;
    }

    /**
     * 存放cache信息
     */
    public void putCacheItem(Object key,Object value) {
        w.lock();
        try {
            if (!cacheMap.containsKey(key)) {
                cacheMap.put(key, value);
            }
        } finally {
            w.unlock();
        }
    }

    /**
     * 删除一个cache
     */
    public void removeCacheItem(Object key) {
        w.lock();
        try {
            if (cacheMap.containsKey(key)) {
                cacheMap.remove(key);
            }
        } finally {
            w.unlock();
        }
    }

    /**
     * 获取cache长度
     * @return size
     */
    public int getSize() {
        r.lock();
        try {
            return cacheMap.size();
        } finally {
            r.unlock();
        }
    }

}
