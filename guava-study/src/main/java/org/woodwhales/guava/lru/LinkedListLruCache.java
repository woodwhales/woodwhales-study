package org.woodwhales.guava.lru;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.4 21:59
 * @description: 使用LinkedList+HashMap实现带LRU的缓存
 */
public class LinkedListLruCache<K, V> implements LruCache<K, V> {

    private final int limit;

    private final LinkedList<K> keys;

    private final HashMap<K, V> cache;

    public LinkedListLruCache(int limit) {
        Preconditions.checkArgument(limit > 0, "the limit big than zero.");
        this.limit = limit;
        this.keys = new LinkedList<K> ();
        this.cache = new HashMap<>(limit);
    }

    @Override
    public void put(K key, V value) {
        Preconditions.checkNotNull(key, "this key must nut null");
        Preconditions.checkNotNull(value, "this value must nut null");
        if(cache.size() >= limit) {
            K firstKey = keys.getFirst();
            keys.remove(firstKey);
            cache.remove(firstKey);
        }

        keys.addLast(key);
        cache.put(key, value);
    }

    @Override
    public V get(K key) {

        boolean exist = keys.remove(key);
        if(!exist) {
            return null;
        }

        keys.addLast(key);
        return cache.get(key);
    }

    @Override
    public void remove(K key) {
        boolean exist = keys.remove(key);
        if(exist) {
            cache.remove(key);
        }
    }

    @Override
    public void clear() {
        keys.clear();
        cache.clear();
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public int limit() {
        return this.limit;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append(keys.stream().map(key -> key + "=" + cache.get(key)).collect(Collectors.joining(",")));
        builder.append("}");
        return builder.toString();
    }
}
