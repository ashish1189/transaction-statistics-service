package com.n26.api.utils;

import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.n26.api.utils.Constants.TIME_TO_LIVE;

@ToString
public class Cache<K, V> {

    private long timeToLive;
    private HashMap<K, V> cacheMap;

    @ToString
    private class CachedObject {
        public long lastAccessed = System.currentTimeMillis();
        public V value;

        protected CachedObject(V value) {
            this.value = value;
        }
    }

    public Cache() {
        this.timeToLive = TIME_TO_LIVE;
        cacheMap = new HashMap<>();

        if (timeToLive > 0) {
            Thread t = new Thread(() -> {
                while (true) {
                    cleanup();
                }
            });

            t.setDaemon(true);
            t.start();
        }
    }

    // Synchronised PUT method
    public void put(K key, V value) {
        synchronized (cacheMap) {
            cacheMap.put(key, (V) new CachedObject(value));
        }
    }

    // Synchronised GET method
    public V get(K key) {
        synchronized (cacheMap) {
            CachedObject c = (CachedObject) cacheMap.get(key);

            if (c == null)
                return null;
            else
                return c.value;
        }
    }

    // Synchronised REMOVE method
    public void remove(K key) {
        synchronized (cacheMap) {
            cacheMap.remove(key);
        }
    }

    // Synchronised Get Cache Size()
    public int size() {
        synchronized (cacheMap) {
            return cacheMap.size();
        }
    }

    // Synchronised REMOVE method
    public void clear() {
        synchronized (cacheMap) {
            cacheMap.clear();
        }
    }

    // Synchronised CLEANUP method
    public void cleanup() {

        long now = System.currentTimeMillis();
        ArrayList<K> deleteKey;

        synchronized (cacheMap) {
            Iterator<Map.Entry<K, V>> itr = cacheMap.entrySet().iterator();

            deleteKey = new ArrayList<>((cacheMap.size() / 2) + 1);
            K key;
            V cached;

            while (itr.hasNext()) {
                Map.Entry<K, V> entry = itr.next();
                key = entry.getKey();
                cached = entry.getValue();
                if (cached != null && (now > (((CachedObject) cached).lastAccessed + timeToLive))) {
                    deleteKey.add(key);
                }
            }
        }

        for (K key : deleteKey) {
            synchronized (cacheMap) {
                cacheMap.remove(key);
            }

            Thread.yield();
        }
    }
}
