package org.woodwhales.concurrent.code19;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * 面试题：写一个固定容量同步容器，拥有put和get方法，以及getCount方法，
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 *
 * 使用wait和notify/notifyAll来实现
 */
public class MyContainer1<T> {
	final private LinkedList<T> lists = new LinkedList<>();
	final private int MAX = 10; // 最多10个元素
	private int count = 0;

	public synchronized void put(T t) {
		while (lists.size() == MAX) { // 想想为什么用while而不是用if？
			/**
			 * 如果使用 if 的时候，那么当条件成立，执行完当前语句，
			 * 此时其他消费者立即拿到了锁，消费了一个，随后又有其他生产者给容器加满了，
			 * 此时当前线程又唤醒了，会直接生产，此时会出现溢出生产。
			 *
			 * 使用while 则会在出 while 之前判断一下while 条件成不成立，
			 * 不成立才出循环，执行后面的操作。
			 */
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		lists.add(t);
		++count;
		this.notifyAll(); // 通知消费者线程进行消费
	}

	public synchronized T get() {
		T t = null;
		while (lists.size() == 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		t = lists.removeFirst();
		count--;
		this.notifyAll(); //通知生产者进行生产
		return t;
	}

	public static void main(String[] args) {
		MyContainer1<String> c = new MyContainer1<>();
		//启动消费者线程
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				for (int j = 0; j < 5; j++) System.out.println(c.get());
			}, "c" + i).start();
		}

		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//启动生产者线程
		for (int i = 0; i < 2; i++) {
			new Thread(() -> {
				for (int j = 0; j < 25; j++) {
					c.put(Thread.currentThread().getName() + " " + j);
				}
			}, "p" + i).start();
		}
	}
}