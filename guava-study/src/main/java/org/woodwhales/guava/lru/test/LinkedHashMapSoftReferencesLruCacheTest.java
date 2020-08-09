package org.woodwhales.guava.lru.test;

import org.woodwhales.guava.lru.LinkedHashMapLruCache;
import org.woodwhales.guava.lru.LinkedHashMapSoftReferencesLruCache;
import org.woodwhales.guava.lru.LruCache;

import java.util.concurrent.TimeUnit;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.5 00:58
 * @description: 使用软引用对缓存进行增强测试
 * 测试时，注意设置JVM参数：-Xms128m -Xmx128m -XX:+PrintGCDetails
 */
public class LinkedHashMapSoftReferencesLruCacheTest {

    public static void main(String[] args) throws Exception {
        testSoftReferencesCache();
        //testStrongReferenceCache();
    }

    /**
     * 当使用了带软引用的缓存的时候，相比强引用缓存，在不频繁写入缓存情况下，很难出现OOM
     * @throws Exception
     */
    private static void testSoftReferencesCache() throws Exception {
        LruCache<String, byte[]> cache = new LinkedHashMapSoftReferencesLruCache(100);

        for (int i = 1; i <= 100; i++) {
            cache.put(String.valueOf(i), new byte[1024 * 1024 * 2]);
            System.out.println("i = " + i + " was cached");
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }

    /**
     * 设置了最大堆内存是 128M
     * 每次创建对象并缓存，则增加 2M 内存，因此在大约缓存第60次的时候，就会出现OOM
     * @throws Exception
     */
    private static void testStrongReferenceCache() throws Exception {
        LruCache<String, byte[]> cache = new LinkedHashMapLruCache<>(100);

        for (int i = 1; i <= 100; i++) {
            cache.put(String.valueOf(i), new byte[1024 * 1024 * 2]);
            System.out.println("i = " + i + " was cached");
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }
}
