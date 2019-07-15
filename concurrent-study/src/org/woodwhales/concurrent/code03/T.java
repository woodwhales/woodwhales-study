package org.woodwhales.concurrent.code03;

/**
 * synchronized关键字
 * 对某个对象加锁
 *
 * 	等同于 code02，都表示锁定的是当前对象
 */
public class T {

	private int count = 10;

	// 等同于在方法的代码执行时要synchronized(this)
	public synchronized void m() {
		count--;
		System.out.println(Thread.currentThread().getName() + " count = " + count);
	}

}
