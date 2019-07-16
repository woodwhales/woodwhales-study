package org.woodwhales.concurrent.code22;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * 有N张火车票，每张票都有一个编号
 * 同时有10个窗口对外售票
 * 请写一个模拟程序
 *
 * 分析下面的程序可能会产生哪些问题？
 *
 * 使用Vector或者Collections.synchronizedXXX
 * 分析一下，这样能解决问题吗？
 *
 * Vector 是同步容器，所以方法都是同步的，因此 size 和 remove 是同步的，
 * 但是由于 while 判断和 remove 之间是分离的，所以这个小程序不是同步的。
 */
public class TicketSeller2 {
	static Vector<String> tickets = new Vector<>();

	static {
		for (int i = 0; i < 1000; i++) {
			tickets.add("票 编号：" + i);
		}
	}

	public static void main(String[] args) {

		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				while (tickets.size() > 0) {

					try {
						TimeUnit.MILLISECONDS.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					System.out.println("销售了--" + tickets.remove(0));
				}
			}).start();
		}
	}
}
