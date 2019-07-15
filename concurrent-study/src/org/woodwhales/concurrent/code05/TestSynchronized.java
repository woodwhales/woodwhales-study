package org.woodwhales.concurrent.code05;

/**
 * 分析一下这个程序的输出
 *
 * synchronized 不使用的时候，则会出现线程重入问题
 *
 * synchronized 的代码块是原子操作
 *
 */
public class TestSynchronized implements Runnable {

	private int count = 10;
	
	public /*synchronized*/ void run() { 
		count--;
		System.out.println(Thread.currentThread().getName() + " count = " + count);
	}
	
	public static void main(String[] args) {
		TestSynchronized testSynchronized = new TestSynchronized();

		for(int i=0; i<5; i++) {
			new Thread(testSynchronized, "THREAD" + i).start();
		}
	}
	
}