package org.woodwhales.concurrent.code20;

import java.util.concurrent.TimeUnit;

/**
 * volatile 修饰的变量对应的值修改了，那么其他线程会知道
 */
public class ThreadLocal1 {
	volatile static Person p = new Person();

	public static void main(String[] args) {

		new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println(p.name);
		}).start();

		new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			p.name = "lisi";
		}).start();
	}
}

class Person {
	String name = "zhangsan";
}
