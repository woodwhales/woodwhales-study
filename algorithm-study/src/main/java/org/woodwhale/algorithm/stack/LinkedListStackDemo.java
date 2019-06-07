package org.woodwhale.algorithm.stack;

/**
 * 	单链表实现栈
 *
 */
public class LinkedListStackDemo {

	public static void main(String[] args) {
		LinkedListStack stack = new LinkedListStack();
		stack.push(1);
		stack.push(3);
		stack.push(4);
		stack.push(2);
		
		System.out.println("当前栈顶元素："+stack.peek());
		stack.pop();
		System.out.println("当前栈顶元素："+stack.peek());
		stack.list();
		
		stack.pop();
		stack.list();
		stack.push(2);
		stack.list();
		
		stack.pop();
		stack.pop();
		stack.list();
	}
}

class LinkedListStack {
	// 默认tail和头在同一个位置
	private LinkedListStackNode top = new LinkedListStackNode(-1, null);
	
	public boolean isEmpty() {
		return this.top.next == null;
	}
	
	public int pop() {
		if(isEmpty()) {
			throw new RuntimeException("弹栈失败，栈已空！");
		}
		
		// top.next 最后一个有效结点
		int vlaue = top.next.data;
		top.next = top.next.next;
		System.out.printf("将 value = %d 成功弹栈\n", vlaue);
		return vlaue;
	}
	
	public void push(int value) {
		
		System.out.printf("将 value = %d 成功压栈\n", value);
		
		// top 的下一个结点永远指向最后一个有效结点
		LinkedListStackNode node = new LinkedListStackNode(value, top.next);
		top.next = node;
		return;
	}
	
	public int getLength() {
		if(isEmpty()) {
			return 0;
		}
		
		int count = 0;
		LinkedListStackNode temp = top.next;
		while(temp != null) {
			count++;
			temp = temp.next;
		}
		return count;
	}
	
	public void list() {
		System.out.println("遍历栈：栈顶 --- 栈底：");
		if(isEmpty()) {
			System.out.println("栈已空！");
			return;
		}
		
		// 从尾结点开始遍历
		LinkedListStackNode temp = top.next;
		while(temp != null) {
			System.out.printf("%d ", temp.data);
			temp = temp.next;
		}
		System.out.println();
		return;
	}
	
	public int peek() {
		if(isEmpty()) {
			throw new RuntimeException("栈已空！");
		}
		
		return top.next.data;
	}
}

class LinkedListStackNode {
	public int data;
	public LinkedListStackNode next;
	
	public LinkedListStackNode(int data, LinkedListStackNode next) {
		this.data = data;
		this.next = next;
	}
	
}