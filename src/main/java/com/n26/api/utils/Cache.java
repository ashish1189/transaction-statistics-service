package com.n26.api.utils;

import com.n26.api.model.Transaction;
import lombok.Getter;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

import static com.n26.api.utils.Constants.TIME_TO_LIVE;

@ToString
public class Cache<K, V> {

    private long timeToLive;
    private HashMap<K, V> cacheMap;

    @ToString
    @Getter
    private class CachedObject<V> {
        public long insertedAt = System.currentTimeMillis();
        public V value;

        protected CachedObject(V value) {
            this.value = value;
            if (value instanceof Transaction)
                this.insertedAt = ((Transaction) value).getTimestamp().toEpochMilli();
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
            CachedObject<V> c = (CachedObject) cacheMap.get(key);

            return c == null ? null : c.value;
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

    // Synchronised Values method
    public Collection<V> values() {
        synchronized (cacheMap) {
            return cacheMap
                    .values()
                    .stream()
                    .map(cache -> ((CachedObject<V>) cache).getValue())
                    .collect(Collectors.toList());
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
                if (cached != null && (now > (((CachedObject) cached).insertedAt + timeToLive))) {
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
