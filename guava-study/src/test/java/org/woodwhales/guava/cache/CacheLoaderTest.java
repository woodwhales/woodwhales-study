package org.woodwhales.guava.cache;

import com.google.common.base.Optional;
import com.google.common.cache.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.5 15:02
 * @description:
 */
public class CacheLoaderTest {

    private boolean queryDataFromDB = false;

    /**
     * 通过表达式创建 LoadingCache
     */
    @Test
    public void testBuilderBySpec() {
        String spec = "maximumSize=3,recordStats";

        CacheBuilderSpec cacheBuilderSpec = CacheBuilderSpec.parse(spec);
        LoadingCache<String, String> cache = CacheBuilder.from(cacheBuilderSpec)
                                                .build(CacheLoader.from(String::toUpperCase));

        cache.getUnchecked("woodwhales1");
        cache.getUnchecked("woodwhales2");
        cache.getUnchecked("woodwhales3");

        cache.getUnchecked("woodwhales4");

        assertEquals(3L, cache.size());
        assertNull(cache.getIfPresent("woodwhales1"));
    }

    /**
     * 缓存命中，缓存未命中指标
     * recordStats 默认不开启
     * cache.stats() 每次调用都会生成新的 CacheStats 对象，防止多个线程取得当前对象的数据，做本线程逻辑处理
     */
    @Test
    public void testCacheStat() {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                                                    .maximumSize(3)
                                                    .recordStats()
                                                    .build(CacheLoader.from(String::toUpperCase));

        cache.getUnchecked("wood");

        CacheStats stats = cache.stats();
        // 命中数量为0
        assertEquals(0L, stats.hitCount());
        // 未命中数量为1
        assertEquals(1L, stats.missCount());

        cache.getUnchecked("wood");

        // cache.stats() 每次调用都会生成新的 CacheStats 对象
        stats = cache.stats();
        // 命中数量为0
        assertEquals(1L, stats.hitCount());
        // 未命中数量为1
        assertEquals(1L, stats.missCount());

    }

    /**
     * 缓存被移除的时候，增加监听
     */
    @Test
    public void testRemovalNotification() {
        RemovalListener<String, String> listener = notification -> {
            if(notification.wasEvicted()) {
                RemovalCause removalCause = notification.getCause();
                assertEquals(RemovalCause.SIZE, removalCause);
                assertEquals("wood", notification.getKey());
            }
        };
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                                                .maximumSize(3)
                                                .build(CacheLoader.from(String::toUpperCase));

        cache.getUnchecked("wood");
        cache.getUnchecked("woods");
        cache.getUnchecked("woodwhales");
    }

    /**
     * 预加载缓存
     * cache.putAll() 这种方式有个问题，
     * 预热的数据，是外部手动准备的，这里有个后门。
     * 预热的数据逻辑和加载数据的逻辑不一致，那就出现数据不一致问题
     *
     */
    @Test
    public void testPreLoad() {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                                            .expireAfterAccess(30, TimeUnit.SECONDS)
                                            .build(CacheLoader.from(String::toUpperCase));

        Map<String, String> preData = new HashMap<String, String>() {
            {
                String[] datas = {"abc", "def"};
                for (String data : datas) {
                    put(data, data.toUpperCase());
                }
            }
        };
        cache.putAll(preData);
        assertEquals(2L, cache.size());
        assertEquals("ABC", cache.getIfPresent("abc"));
    }

    /**
     * 当两个线程同时获取缓存的时候，
     * 线程1 获取到了缓存并修改了数值
     * 线程2 获取到了缓存并修改了数值
     *
     * 那么缓存中应该存在最后一次更新的数据
     *
     * refreshAfterWrite 表示，每个数据在被缓存时，记录一下这个数据的缓存时间，
     * 当有其他使用者使用的时候，guava会计算一下这个获取时间是不是在refreshAfterWrite时间范围内
     * 如果不是，则从LoadingCache从重新加载数据
     */
    @Test
    public void testRefresh() throws InterruptedException {

        AtomicInteger count = new AtomicInteger(0);
        LoadingCache<String, Long> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(30, TimeUnit.SECONDS)
                .refreshAfterWrite(2, TimeUnit.SECONDS)
                .build(CacheLoader.from(key -> {
                    count.incrementAndGet();
                    return System.currentTimeMillis();
                }));

        Long result1 = cache.getUnchecked("woodwhales");
        TimeUnit.SECONDS.sleep(3);
        Long result2 = cache.getUnchecked("woodwhales");
        assertNotEquals(result2, result1);
        assertNotEquals(1, count);
    }

    /**
     * value封装成 Optional 对象，防止value为null
     */
    @Test
    public void testOptionalValue() {
        CacheLoader<String, Optional<Student>> cacheLoader = CacheLoader.from(name -> {
            if("null".equals(name)) {
                return Optional.fromNullable(null);
            }
            return Optional.of(new Student(name, name, name));
        });

        LoadingCache<String, Optional<Student>> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(30, TimeUnit.SECONDS)
                .softValues()
                .build(cacheLoader);

        assertNull(cache.getUnchecked("null").orNull());

        Student student = cache.getUnchecked("null").or(new Student("default", "default", "default"));
        assertNotNull(student);
        assertEquals("default", student.getName());
    }

    /**
     * 获取缓存时，value为null的时候会抛出异常
     */
    @Test
    public void testNullValue() {

        CacheLoader<String, Student> cacheLoader = CacheLoader.from(name -> "null".equals(name) ? null : new Student(name, name, name));
        LoadingCache<String, Student> cache = CacheBuilder.newBuilder()
                                                        .expireAfterAccess(30, TimeUnit.SECONDS)
                                                        .softValues()
                                                        .build(cacheLoader);

        assertNotNull(cache.getUnchecked("woodwhales"));

        assertThrows(CacheLoader.InvalidCacheLoadException.class, () -> {
            // 获取缓存时，value为null的时候会抛出异常
            assertNull(cache.getUnchecked("null"));
        });
    }

    /**
     * 软引用，当快要OOM的时候，GC时会将软引用回收掉
     * 测试时设置 JVM 参数：-Xms128m -Xmx128m -XX:+PrintGCDetails
     * @throws InterruptedException
     */
    @Test
    public void testSoftValues() throws InterruptedException {
        LoadingCache<String, Student> cache = CacheBuilder.newBuilder()
                                                            .expireAfterAccess(30, TimeUnit.SECONDS)
                                                            .softValues()
                                                            .build(createCacheLoaderByLambda());
        // 如果不是软引用，一定会在120次循环的时候就会OOM
        for (int i = 0; i < 1000; i++) {
            cache.put("index" + i, new Student("name" + i, "name" + i, "name" + i));
            System.out.println(String.format("name = [%s] put cache", i));
            TimeUnit.MILLISECONDS.sleep(500);
        }

    }

    /**
     * 幻影（虚）引用，只要发生一次GC，那么幻影引用就会被回收
     */
    @Test
    public void testWeakKey() throws InterruptedException {
        LoadingCache<String, Student> cache = CacheBuilder.newBuilder()
                                                            .expireAfterAccess(30, TimeUnit.SECONDS)
                                                            .weakKeys()
                                                            .weakValues()
                                                            .build(createCacheLoaderByLambda());

        assertNotNull(cache.getUnchecked("wood"));
        assertNotNull(cache.getUnchecked("whales"));

        System.gc();

        TimeUnit.SECONDS.sleep(1);

        assertNull(cache.getIfPresent("wood"));
        assertNull(cache.getIfPresent("whales"));
        assertEquals(0L, cache.size());
    }

    /**
     * expireAfterWrite 表示在expireAfterAccess时间内，没有对元素进行写操作就会缓存失效
     * 也就是，仅仅读取缓存是不会延缓存长失效时间
     */
    @Test
    public void testEvictionByWriteTime() throws InterruptedException {
        LoadingCache<String, Student> cache = CacheBuilder.newBuilder()
                                                        .expireAfterWrite(2, TimeUnit.SECONDS)
                                                        .build(createCacheLoaderByLambda());

        assertNotNull(cache.getUnchecked("woods"));

        TimeUnit.SECONDS.sleep(1);
        assertNotNull(cache.getIfPresent("woods"));

        TimeUnit.MILLISECONDS.sleep(990);
        assertNotNull(cache.getIfPresent("woods"));

        TimeUnit.SECONDS.sleep(1);
        assertNull(cache.getIfPresent("woods"));
    }

    /**
     * expireAfterAccess 表示在expireAfterAccess时间内，没有对这个元素做访问操作，就会缓存失效
     * 访问：read/update/write
     */
    @Test
    public void testEvictionByAccessTime() throws InterruptedException {
        LoadingCache<String, Student> cache = CacheBuilder.newBuilder()
                                                        .expireAfterAccess(2, TimeUnit.SECONDS)
                                                        .build(createCacheLoaderByLambda());

        assertNotNull(cache.getUnchecked("woods"));
        TimeUnit.SECONDS.sleep(3);
        assertNull(cache.getIfPresent("woods"));
    }

    /**
     * 使用lambda表达式创建 CacheLoader
     * @return
     */
    private CacheLoader<String, Student> createCacheLoaderByLambda() {
        return CacheLoader.from(name -> new Student(name, name, name));
    }

    /**
     * 根据缓存元素的权重限制LRU
     * maximumWeight 缓存中元素不能超过最大权重
     */
    @Test
    public void testEvictionByWeight() {

        Weigher<String, Student> weigher = (key, student) -> (student.getName() + student.getAddress() + student.getClassName()).length();

        LoadingCache<String, Student> cache = CacheBuilder.newBuilder()
                                                        .maximumWeight(45)
                                                        .concurrencyLevel(1)
                                                        .weigher(weigher)
                                                        .build(createCacheLoader());

        // 权重是 15
        Student student = cache.getUnchecked("woods");
        assertQueryDataFromDB();

        // 权重是 15
        student = cache.getUnchecked("whale");
        assertQueryDataFromDB();

        // 权重是 15
        student = cache.getUnchecked("kings");
        assertQueryDataFromDB();

        assertEquals(3L, cache.size());
        assertNotNull(cache.getIfPresent("woods"));

        // 权重是 21
        student = cache.getUnchecked("jackson");
        assertNotNull(cache.getIfPresent("woods"));
        assertEquals(2L, cache.size());

    }

    /**
     * 根据缓存元素个数限制LRU
     */
    @Test
    public void testEvictionBySize() {
        LoadingCache<String, Student> cache = CacheBuilder.newBuilder()
                                                            .maximumSize(3)
                                                            .build(createCacheLoader());

        Student student = cache.getUnchecked("woodwhales1");
        assertQueryDataFromDB();

        student = cache.getUnchecked("woodwhales2");
        assertQueryDataFromDB();

        student = cache.getUnchecked("woodwhales3");
        assertQueryDataFromDB();

        assertEquals(3L, cache.size());

        // 缓存第四个元素，那么第一个元素就会被移除缓存
        student = cache.getUnchecked("woodwhales4");

        // cache.getIfPresent() 方法不会加载缓存，仅在缓存里找数据
        assertNull(cache.getIfPresent("woodwhales1"));
        assertEquals(3L, cache.size());
        // 使用 cache.getIfPresent()查询缓存中最新的元素，一定是最后缓存的数据
        assertNotNull(cache.getIfPresent("woodwhales4"));
    }

    /**
     * 创建缓存加载的实现
     * @return
     */
    private CacheLoader<String, Student> createCacheLoader() {
        return new CacheLoader<String, Student>() {
            @Override
            public Student load(String key) throws Exception {
                return findStudentByName(key);
            }
        };
    }


    /**
     *  缓存基础使用单元测试
     *  maximumSize 设置最大缓存元素个数
     *  expireAfterAccess 设置过期时间
     *  new CacheLoader 如果缓存中没有，则调用CacheLoader实例获取数据并缓存
     */
    @Test
    public void testBasic() throws ExecutionException, InterruptedException {

        LoadingCache<String, Student> cache = CacheBuilder.newBuilder()
                                                            .maximumSize(3)
                                                            .expireAfterAccess(30, TimeUnit.MILLISECONDS)
                                                            .build(createCacheLoader());

        // cache.get() 会显示抛出异常
        // cache.getUnchecked() 不会显示抛出异常
        Student student = cache.get("woodwhales");
        assertNotNull(student);
        assertQueryDataFromDB();

        student = cache.get("woodwhales");
        assertNotNull(student);
        assertQueryDataFromCache();

        // 30 毫秒缓存过期，再次获取，一定从数据库中获取
        TimeUnit.MILLISECONDS.sleep(50);
        student = cache.get("woodwhales");
        assertNotNull(student);
        assertQueryDataFromDB();
    }

    private void assertQueryDataFromCache() {
        assertEquals(false, queryDataFromDB);
    }

    private void assertQueryDataFromDB() {
        assertEquals(true, queryDataFromDB);
        queryDataFromDB = false;
    }

    /**
     * 模拟从数据库中获取数据
     * @param name
     * @return
     */
    private Student findStudentByName(String name) {
        System.out.println(String.format("select name = %s from DataBase", name));
        queryDataFromDB = true;
        return new Student(name, name, name);
    }
}
