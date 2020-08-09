package org.woodwhales.guava.lru.test;

import org.woodwhales.guava.lru.LinkedListLruCache;
import org.woodwhales.guava.lru.LruCache;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.4 21:43
 * @description: 使用LinkedList+HashMap实现带LRU的缓存测试
 */
public class LinkedListLruCacheExample {

    public static void main(String[] args) {
        LruCache<String, Integer> cache = new LinkedListLruCache<>(3);

        cache.put("1", 1);
        cache.put("2", 2);
        cache.put("3", 3);

        System.out.println(cache);

        cache.put("4", 4);

        System.out.println(cache);

        // 由于使用了一次 2，因此原本 2 是最老的，现在是 3 是最老的
        System.out.println(cache.get("2"));

        System.out.println(cache);
    }
}
