package org.woodwhale.algorithm.queue;

import java.util.Scanner;

/**
 *	使用数组实现循环链表
 *
 */
public class CircleQueueDemo {
	
	public static void main(String[] args) {
		CircleQueue queue = new CircleQueue(4);
		char key = ' ';
		Scanner scanner = new Scanner(System.in);
		
		boolean loop = true;
		
		while(loop) {
			System.out.println("s(show)");
			System.out.println("e(exit)");
			System.out.println("a(add)");
			System.out.println("g(get)");
			System.out.println("p(peek)");
			
			key = scanner.next().charAt(0);
			
			switch (key) {
			case 's':
				try {
					queue.show2();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case 'a':
				System.out.println("请输入一个数：");
				int value = scanner.nextInt();
				try {
					queue.enQueue(value);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case 'g':
				try {
					int result = queue.deQueue();
					System.out.println("出队的数据是：" + result);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case 'p':
				try {
					int peekValue = queue.peek();
					System.out.println("peekValue : " + peekValue);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case 'e':
				scanner.close();
				loop = false;
				break;
			}
		}
		
		System.out.println("程序退出成功！");
	}
}

class CircleQueue {
	private int arrSize;
	private int front;
	private int rear;
	private int[] arr;
	
	/**
	 * 初始化
	 */
	public CircleQueue(int arrSize) {
		this.arrSize = arrSize;
		this.front = 0;
		this.rear = 0;
		this.arr = new int[arrSize];
	}
	
	public int peek() {
		if(isEmpty()) {
			throw new RuntimeException("队列为空！");
		}
		
		return this.arr[this.front];
	}

	/**
	 * 入队
	 */
	public void enQueue(int value) {
		if(isFull()) {
			throw new RuntimeException("队列满了！");
		}
		
		this.arr[this.rear] = value;
		this.rear = (this.rear + 1) % arrSize;
		return;
	}

	/**
	 * 判断队列已满
	 */
	private boolean isFull() {
		return ((this.rear + 1) % this.arrSize) == this.front;
	}
	
	/**
	 * 出队
	 */
	public int deQueue() {
		if(isEmpty()) {
			throw new RuntimeException("队列为空！");
		}
		int value = this.arr[this.front];
		this.front = (this.front + 1) % arrSize;
		return value;
	}

	/**
	 * 判断为空队列
	 */
	private boolean isEmpty() {
		return this.rear == this.front;
	}
	
	/**
	 *  计算循环队列的有效数据的个数 
		情况一:  当rear大于front时，循环队列的长度：rear-front
		情况二:  当rear小于front时，循环队列的长度:分为两部分计算 0+rear 和   Quesize-front, 将两部分的长度合并到一起即为: rear-front + Quesize
		 
		 所以将两种情况合为一种，即为:
		 总长度是(rear-front+Quesize)%Quesize
	 */
	private int count() {
		return (this.rear - this.front + this.arrSize) % this.arrSize;
	}
	
	public void show() {
		if(isEmpty()) {
			throw new RuntimeException("队列为空！");
		}
		
		for (int i = front; i < front + count(); i++) {
			int index = i % this.arrSize;
			System.out.printf("arr[%d] = %d ", index, arr[index]);
		}
		System.out.println();
	}
	
	/**
	 *	另一种遍历方法：
	 * 	从front 遍历到rear 前面的元素，当遍历到rear就结束
	 */
	public void show2() {
		if(this.arrSize == 0) {
			throw new RuntimeException("队列为空！");
		}
		
		for (int i = front; i != this.rear; ++i) {
			System.out.printf("arr[%d] = %d ", i, arr[i]);
		}
		System.out.println();
	}
}
