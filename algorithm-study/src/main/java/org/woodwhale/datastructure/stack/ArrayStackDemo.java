package org.woodwhale.datastructure.stack;

/**
 * 	数组实现栈
 *
 */
public class ArrayStackDemo {

	public static void main(String[] args) {
		ArrayStack stack = new ArrayStack(4);
		stack.push(1);
		stack.push(3);
		stack.push(4);
		stack.push(2);
		stack.pop();
		stack.pop();
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

class ArrayStack {
	private int maxSize;
	private int[] stack;
	private int top = -1; // 栈帧，默认是指向栈底的下一个位置
	
	public ArrayStack(int maxSize) {
		if(maxSize < 0) {
			throw new RuntimeException("初始化失败，栈大小参数设置非法！");
		}
		
		this.maxSize = maxSize;
		this.stack = new int[maxSize];
	}
	
	/**
	 *	判断栈空
	 */
	private boolean isEmpty() {
		return top == -1;
	}
	
	/**
	 *	判断栈满
	 */
	private boolean isFull() {
		return top == maxSize-1;
	}
	
	public void push(int value) {
		if(isFull()) {
			System.out.println("压栈失败，栈已满！");
			return;
		}
		System.out.printf("将 value = %d 成功压栈\n", value);
		this.stack[++top] = value;
	}
	
	public int pop() {
		if(isEmpty()) {
			throw new RuntimeException("弹栈失败，栈已空！");
		}
		
		int vlaue = this.stack[top--];
		System.out.printf("将 value = %d 成功弹栈\n", vlaue);
		return vlaue;
	}
	
	public void list() {
		System.out.println("遍历栈：栈顶 --- 栈底：");
		if(isEmpty()) {
			System.out.println("栈已空！");
			return;
		}
		
		for(int i = top; i > -1; i--) {
			int value = this.stack[i];
			System.out.printf("%d ", value);
		}
		System.out.println();
		return;
	}
}