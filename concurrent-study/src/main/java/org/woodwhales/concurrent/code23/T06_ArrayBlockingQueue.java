package org.woodwhales.concurrent.code23;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * ArrayBlockingQueue 有界的队列
 */
public class T06_ArrayBlockingQueue {

	static BlockingQueue<String> strs = new ArrayBlockingQueue<>(10);

	static Random r = new Random();

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			strs.put("a" + i);
		}
		
		// strs.put("aaa"); // 满了就会等待，程序阻塞
		strs.add("aaa"); // 容器满了就不能再增加了, 会报异常：java.lang.IllegalStateException: Queue full
		//strs.offer("aaa"); // 容器满了，加不了，返回false，不会报错
		//strs.offer("aaa", 1, TimeUnit.SECONDS); // 在多少秒之内加，超时还没有加成功就会返回false
		
		System.out.println(strs);
	}
}
