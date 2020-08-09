package org.woodwhales.guava.reference;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.4 22:27
 * @description:
 * 测试时，注意设置JVM参数：-Xms128m -Xmx128m -XX:+PrintGCDetails
 * JVM 参数解释参考文档：https://www.oracle.com/java/technologies/javase/vmoptions-jsp.html
 * JVM 设置命令自动生成在线工具：http://jvmmemory.com/
 */
public class ReferenceExample {
    
    public static void main(String[] args) throws Exception {
        //testStrongReferences();
        testSoftReferences();
        //testWeakReferences();

        //testSoftCompareWeakReferences1();
        //testSoftCompareWeakReferences2();
        //testPhantomReferences();

    }

    /**
     * 幻影引用
     * 测试时，注意设置JVM参数：-Xms128m -Xmx128m -XX:+PrintGCDetails
     *
     * 幻影引用必须配合 ReferenceQueue 使用，
     * 因为使用 PhantomReference 对象本身是拿不到该具体实例，当这个幻影引用被回收时，就会放到 ReferenceQueue 队列中
     *
     * 最佳实践：
     * @See org.apache.commons.io.FileCleaningTracker
     *      org.apache.commons.io.FileCleaningTracker.Tracker
     */
    private static void testPhantomReferences() throws Exception {
        int index = 10;
        MyReference reference = new MyReference(index);
        ReferenceQueue referenceQueue = new ReferenceQueue<>();
        MyPhantomReference phantomReferences = new MyPhantomReference(reference, referenceQueue, index);

        reference = null;
        System.gc();
        TimeUnit.SECONDS.sleep(2);
        // phantomReferences.get() 拿不到对象
        System.out.println(phantomReferences.get());
        // 虚引用被回收会进入 ReferenceQueue 队列中
        Reference object = referenceQueue.remove();
        ((MyPhantomReference)object).doAction();
    }

    /**
     * 软引用和虚引用的比较2
     * 测试时，注意设置JVM参数：-Xms128m -Xmx128m -XX:+PrintGCDetails
     */
    private static void testSoftCompareWeakReferences2() throws Exception {
        // 如果是虚引用，那么只要是GC发生，虚引用就会被回收
        MyReference reference = new MyReference(10);
        WeakReference<MyReference> weakReference = new WeakReference<>(reference);

        System.out.println(weakReference.get().index);

        reference = null;
        System.gc();
        TimeUnit.SECONDS.sleep(1);
        // 这里一定会抛出空指针，因为上面GC的时候，虚引用就会被回收
        System.out.println(weakReference.get().index);

    }

    /**
     * 软引用和虚引用的比较1
     * 测试时，注意设置JVM参数：-Xms128m -Xmx128m -XX:+PrintGCDetails
     */
    private static void testSoftCompareWeakReferences1() throws Exception {
        MyReference reference = new MyReference(10);
        SoftReference<MyReference> softReference = new SoftReference<>(reference);

        reference = null;

        // 强引用已经手动标记为无用引用，但是这个强引用指向的对象，还被软引用维护着，那么只有等快要OOM的时候才会回收。
        System.out.println(softReference.get().index);

        System.gc();

        TimeUnit.SECONDS.sleep(2);
        System.out.println(softReference.get().index);
    }

    /**
     * 虚引用测试
     * 测试时，注意设置JVM参数：-Xms128m -Xmx128m -XX:+PrintGCDetails
     *
     * 虚引用比软引用更脆弱，只要有GC时，虚引用就会被回收
     *
     * @throws Exception
     */
    public static void testWeakReferences() throws Exception {
        List<WeakReference<MyReference>> list = new ArrayList<>();

        int index = 1;

        while (true) {
            int currentIndex = index++;
            list.add(new WeakReference<>(new MyReference(currentIndex)));
            System.out.println("the index = " + index + " MyReference inserted to list");
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }

    /**
     * 软引用测试
     *
     * 测试时，注意设置JVM参数：-Xms128m -Xmx128m -XX:+PrintGCDetails
     *
     * 当内存快要耗尽时，GC 会回收掉软引用。
     *
     * 注意：由于内存占用太快，导致当内存快要耗尽时，GC还没来得及回收掉软引用时，也会出现OOM异常。
     *
     * @throws Exception
     */
    public static void testSoftReferences() throws Exception {
        List<SoftReference<MyReference>> list = new ArrayList<>();

        int index = 1;

        while (true) {
            int currentIndex = index++;
            list.add(new SoftReference<>(new MyReference(currentIndex)));
            System.out.println("the index = " + index + " MyReference inserted to list");
            //TimeUnit.MILLISECONDS.sleep(500);
            // 当设置吃内存的速度慢一点的时候，即软引用在GC时来得及回收，那么内存会一直有新的空间可以使用，OOM 的情况能得到缓解
            TimeUnit.SECONDS.sleep(1);
        }

    }


    /**
     * 测试强引用
     *
     * 测试时，注意设置JVM参数：-Xms128m -Xmx128m -XX:+PrintGCDetails
     *
     * 因为不断的创建 MyReference 对象，而这些对象是强引用，一直在被 list 维护着，
     * 所以在程序运行期间，就算发生垃圾回收，也不会将list维护的强引用对象回收，
     * 因此堆内存会不断被占用，直到内存溢出。
     *
     * @throws Exception
     */
    private static void testStrongReferences() throws Exception {
        List<MyReference> list = new ArrayList<>();

        int index = 1;

        while (true) {
            int currentIndex = index++;
            list.add(new MyReference(currentIndex));
            System.out.println("the index = " + index + " MyReference inserted to list");
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }

    private static class MyPhantomReference extends PhantomReference<Object> {

        private final int index;

        public MyPhantomReference(Object referent, ReferenceQueue<? super Object> queue, int index) {
            super(referent, queue);
            this.index = index;
        }

        public void doAction() {
            System.out.println("index = [ "+ index +"] will be GC");
        }
    }

    private static class MyReference {

        private final int index;

        private int[] data = new int[1024 * 1024];

        private MyReference(int index) {
            this.index = index;
        }

        /**
         * Java回收该类的一个对象时，就会调用这个已经被重写的finalize()方法。
         * 标记当前对象在下一次GC的时候会被垃圾回收，如果不想被回收，那么可以重写这个方法，拯救这个对象和 root 强关联。
         * @throws Throwable
         */
        @Override
        protected void finalize() throws Throwable {
            System.out.println("index = [ "+ index +"] will be GC");
        }
    }
}
