package org.woodwhales.guava.lru;

import com.google.common.base.Preconditions;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.4 21:02
 * @description: 使用LinkedHashMap实现带LRU的缓存
 * 本类不是一个线程安全的
 */
public class LinkedHashMapLruCache<K, V> implements LruCache<K, V> {

    private final int limit;

    private final InternalLruLinkedHashMapCache<K, V> cache;

    @Override
    public void put(K key, V value) {
        this.cache.put(key, value);
    }

    @Override
    public V get(K key) {
        return this.cache.get(key);
    }

    @Override
    public void remove(K key) {
        this.cache.remove(key);
    }

    @Override
    public void clear() {
        this.cache.clear();
    }

    @Override
    public int size() {
        return this.cache.size();
    }

    @Override
    public int limit() {
        return this.limit;
    }

    @Override
    public String toString() {
        return this.cache.toString();
    }

    private static class InternalLruLinkedHashMapCache<K, V> extends LinkedHashMap<K, V> {
        private int limit;

        public InternalLruLinkedHashMapCache(int limit) {
            super(limit, 0.75f, true);
            this.limit = limit;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return this.size() > this.limit;
        }
    }

    public LinkedHashMapLruCache(int limit) {
        Preconditions.checkArgument(limit > 0, "this limit must big than zero.");
        this.limit = limit;
        this.cache = new InternalLruLinkedHashMapCache<>(limit);
    }

}
