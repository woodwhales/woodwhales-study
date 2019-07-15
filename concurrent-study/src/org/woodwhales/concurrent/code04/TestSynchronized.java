package org.woodwhales.concurrent.code04;

import org.woodwhales.concurrent.code03.T;

/**
 * synchronized关键字
 * 对某个对象加锁
 *
 * synchronized写在静态方法上，表示锁定当前类的了对象
 */
public class TestSynchronized {

    private static int count = 10;

    // 这里等同于synchronized(org.woodwhales.concurrent.code04.TestSynchronized.class)
    public synchronized static void m() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    // 考虑一下这里写synchronized(this)是否可以？ 可以
    public static void mm() {
        synchronized(T.class) {
            count --;
        }
    }
}
