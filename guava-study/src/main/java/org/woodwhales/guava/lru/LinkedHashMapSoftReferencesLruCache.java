package org.woodwhales.guava.lru;

import com.google.common.base.Preconditions;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.5 00:45
 * @description: 使用软引用对缓存进行增强
 */
public class LinkedHashMapSoftReferencesLruCache<K, V> implements LruCache<K, V> {

    private final int limit;

    private final InternalLruLinkedHashMapCache<K, V> cache;

    private class InternalLruLinkedHashMapCache<K, V> extends LinkedHashMap<K, SoftReference<V>> {
        private int limit;

        public InternalLruLinkedHashMapCache(int limit) {
            super(limit, 0.75f, true);
            this.limit = limit;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, SoftReference<V>> eldest) {
            return this.size() > this.limit;
        }
    }

    public LinkedHashMapSoftReferencesLruCache(int limit) {
        Preconditions.checkArgument(limit > 0, "this limit must big than zero.");
        this.limit = limit;
        this.cache = new InternalLruLinkedHashMapCache<>(limit);
    }

    @Override
    public void put(K key, V value) {
        this.cache.put(key, new SoftReference<>(value));
    }

    @Override
    public V get(K key) {
        SoftReference<V> softReference = this.cache.get(key);
        if(Objects.isNull(softReference)) {
            return null;
        }
        return softReference.get();
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
}
