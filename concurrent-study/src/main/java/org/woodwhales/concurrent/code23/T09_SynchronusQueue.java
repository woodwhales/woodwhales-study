package org.woodwhales.concurrent.code23;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * SynchronousQueue 容量为0的队列
 * put方法的内部就是transferer
 */
public class T09_SynchronusQueue {
	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<String> strs = new SynchronousQueue<>();
		
		new Thread(()->{
			try {
				System.out.println(strs.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		
		strs.put("aaa"); // 阻塞等待消费者消费
		//strs.add("aaa");
		System.out.println(strs.size());
	}
}
