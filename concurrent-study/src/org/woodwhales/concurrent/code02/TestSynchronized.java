package org.woodwhales.concurrent.code02;

/**
 * synchronized关键字
 * 对某个对象加锁
 *
 * 锁定当卡对象，省去new一个锁的操作
 *
 */
public class TestSynchronized {
	
	private int count = 10;
	
	public void m() {
		// 任何线程要执行下面的代码，必须先拿到this的锁
		synchronized(this) {
			count--;
			System.out.println(Thread.currentThread().getName() + " count = " + count);
		}
	}
	
}

