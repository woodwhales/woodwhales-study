package org.woodwhale.datastructure.queue;

import java.util.Scanner;

/**
 * 	最简单的队列，使用数组实现，
 * 	缺陷：只能使用一次，不能重复使用。
 *
 */
public class QueueDemo {

	public static void main(String[] args) {
		QueueArray queue = new QueueArray(3);
		char key = ' ';
		Scanner scanner = new Scanner(System.in);
		
		boolean loop = true;
		
		while(loop) {
			System.out.println("s(show)");
			System.out.println("e(exit)");
			System.out.println("a(add)");
			System.out.println("g(get)");
			System.out.println("h(peek)");
			
			key = scanner.next().charAt(0);
			
			switch (key) {
			case 's':
				try {
					queue.show();
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
			case 'h':
				queue.show();
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

class QueueArray {
	private int arrSize;
	private int front;
	private int rear;
	private int[] arr;
	
	public QueueArray (int arrSize) {
		this.arrSize = arrSize;
		this.arr = new int[arrSize]; 
		this.front = -1;
		this.rear = -1;
	}
	
	public void show() {
		if(isEmpty()) {
			throw new RuntimeException("队列为空！");
		}
		
		System.out.println("当前队列为：");
		for (int i = this.front + 1; i < this.rear+1 ; i++) {
			System.out.printf("arr[%d] = %d ", i, this.arr[i]);
		}
		System.out.println();
	}

	public int deQueue() {
		if(isEmpty()) {
			throw new RuntimeException("队列为空！");
		}
		
		this.front++;
		return this.arr[this.front];
	}
	
	public boolean isEmpty() {
		return this.front == this.rear;
	}

	public void enQueue(int value) {
		if(isFull()) {
			throw new RuntimeException("队列满了！");
		}
		
		this.rear++;
		this.arr[rear] = value;
		return;
	}

	public boolean isFull() {
		return this.rear == this.arrSize - 1;
	}
	
}