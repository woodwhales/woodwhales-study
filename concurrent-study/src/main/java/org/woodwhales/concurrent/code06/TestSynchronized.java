package org.woodwhales.concurrent.code06;

/**
 * 同步和非同步方法是否可以同时调用？
 *
 * 也就是在m1方法执行过程之中，m2这个非同步方法是否可以执行？
 *
 * 可以，运行代码可以看到，m1运行期间打印了m2，所以m2可以执行
 */
public class TestSynchronized {

	// 同步的方法
	public synchronized void m1() { 
		System.out.println(Thread.currentThread().getName() + " m1 start...");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " m1 end");
	}

	// 非同步的方法
	public void m2() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " m2 ");
	}
	
	public static void main(String[] args) {
		TestSynchronized t = new TestSynchronized();
		
		/*new Thread(()->t.m1(), "t1").start();
		new Thread(()->t.m2(), "t2").start();*/
		
		new Thread(t::m1, "t1").start();
		new Thread(t::m2, "t2").start();
		
		/*
		new Thread(new Runnable() {

			@Override
			public void run() {
				t.m1();
			}
			
		});
		*/
		
	}
	
}
