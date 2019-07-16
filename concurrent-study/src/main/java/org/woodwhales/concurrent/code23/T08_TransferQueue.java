package org.woodwhales.concurrent.code23;

import java.util.concurrent.LinkedTransferQueue;

/**
 * LinkedTransferQueue
 * 消费者先启动，队列里有消息的时候就会直接转给消费者
 *
 * 适用于很高的并发
 */
public class T08_TransferQueue {
	public static void main(String[] args) throws InterruptedException {
		LinkedTransferQueue<String> strs = new LinkedTransferQueue<>();
		
		new Thread(() -> {
			try {
				System.out.println(strs.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		
		strs.transfer("aaa"); // 没有消费者的时候就会阻塞

		// strs.put("aaa"); // 这个不会阻塞
		

		/*new Thread(() -> {
			try {
				System.out.println(strs.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();*/
	}
}
