package org.woodwhales.concurrent.code01;

/**
 * synchronized关键字
 * 对某个对象加锁
 */
public class TestSynchronized {
	
	private int count = 10;

	// new 出一个锁
	private Object o = new Object();
	
	public void m() {
		// 任何线程要执行下面的代码，必须先拿到o的锁
		synchronized(o) {
			count--;
			System.out.println(Thread.currentThread().getName() + " count = " + count);
		}
	}
	
}

