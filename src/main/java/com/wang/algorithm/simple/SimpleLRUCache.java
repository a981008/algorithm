package com.wang.algorithm.simple;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 利用 LinedHashMap 实现的 LRUCache，用此来做基准
 */
public class SimpleLRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int cap;

    public SimpleLRUCache(int cap) {
        super(cap, 0.75f, true);
        this.cap = cap;
    }

    public V put(K key, V val) {
        return super.put(key, val);
    }

    public V get(Object key) {
        return super.get(key);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
        return size() > cap;
    }
}
