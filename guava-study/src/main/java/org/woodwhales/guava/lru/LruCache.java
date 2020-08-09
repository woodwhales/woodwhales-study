package org.woodwhales.guava.lru;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.4 21:00
 * @description:
 */
public interface LruCache<K, V> {

    /**
     * 插入数据
     * 当容器满的时候，将最久访问的数据从容器中移除，
     * 并将新加入的数据放入容器中
     * @param key
     * @param value
     */
    void put(K key, V value);

    /**
     * 获取数据
     * @param key
     * @return
     */
    V get(K key);

    /**
     * 将指定元素从容器中移除
     * @param key
     */
    void remove(K key);

    /**
     * 清空缓存
     */
    void clear();

    /**
     * 缓存当前已存有效数据的容量大小
     * @return
     */
    int size();

    /**
     * 当前缓存容器的最大可缓存容量
     * @return
     */
    int limit();
}
