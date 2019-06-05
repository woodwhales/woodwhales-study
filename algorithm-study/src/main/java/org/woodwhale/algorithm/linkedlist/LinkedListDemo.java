package org.woodwhale.algorithm.linkedlist;

import java.util.Stack;

/**
 * 链表
 *	求单链表中有效结点的个数
 *	查找单链表中的倒数第k个结点
 *	单链表反转
 *	从尾到头遍历链表（注意是遍历，不能改动原链表结构，使用stack）
 */
public class LinkedListDemo {

	public static void main(String[] args) {
		LinkedList linkedList = new LinkedList();
		
		linkedList.add(new Hero(1, "小美"));
		linkedList.add(new Hero(4, "小武"));
		linkedList.add(new Hero(2, "小仓"));
		linkedList.add(new Hero(6, "小泽"));
		linkedList.add(new Hero(5, "super 小仓"));
		linkedList.add(new Hero(7, "super 小泽"));
		linkedList.add(new Hero(3, "super 小仓 plus"));
		linkedList.add(new Hero(7, "super 小泽"));
		
		// 获取链表长度
		linkedList.getLength();
		
		// 找到倒数第k个结点
		linkedList.findByLastIndex(1);
		
		// 链表反转
//		linkedList.list();
//		linkedList.reverse();
		
		// 反向遍历链表
		linkedList.reverseList();
		
		// 删除结点，并返回已删除的结点
//		linkedList.deleteById(4);
//		linkedList.list();
		
		// 根据id 更新结点数据
//		linkedList.updateByHero(new Hero(3, "super 小泽"));
		
		// 遍历链表
		linkedList.list();
	}
}

class LinkedList {
	private Node head = new Node(null, null);
	
	/**
	 * 反向遍历链表，不改变原链表结构
	 */
	public void reverseList() {
		System.out.println("链表反向遍历");
		if(isEmpty()) {
			return;
		}
		
		Stack<Node> stack = new Stack<Node>();
		Node currentNode = head.next;
		while(currentNode != null) {
			stack.push(currentNode);
			currentNode = currentNode.next;
		}
		
		while(stack.size() > 0) {
			System.out.println(stack.pop().data);
		}
		return;
	}
	
	/**
	 * 	链表反转
	 */
	public Node reverse() {
		if(isEmpty()) {
			return null;
		}
		
		Node reverseHead = new Node(null, null);
		
		Node currentNode = head.next;
		// 临时结点，暂时保存当前结点的下一个结点
		Node tempNode = null;
		while(currentNode != null) {
			tempNode = currentNode.next;
			// 插到首位置
			currentNode.next = reverseHead.next;
			reverseHead.next = currentNode;
			
			currentNode = tempNode;
		}
		head = reverseHead;
		return head;
	}
	
	/**
	 * 	找到链表的倒数第k个结点
	 */
	public Node findByLastIndex(int index) {
		if(isEmpty()) {
			return null;
		}
		
		int length = getLength();
		
		Node currentNode = head.next;
		for(int i = 0; (length - index > 0) && (i < length - index); i++) {
			currentNode = currentNode.next;
		}
		System.out.printf("导出第 %d 个结点为：%s\n", index, currentNode.data);
		return currentNode;

	}
	
	/**
	 * 	统计链表的长度
	 */
	public int getLength() {
		int length = 0;
		
		Node currentNode = head.next;
		while(currentNode != null) {
			length++;
			currentNode = currentNode.next;
		}
		return length;
	}
	
	/**
	 *	根据id 进程新增结点操作
	 */
	public void addById(Hero hero) {
		Node cur = head;
		
		Integer id = hero.getId();
		
		boolean addFlag = false;
		while(cur.next != null) {
			if(id < cur.next.data.getId()) {
				// 插到最后
				break;
			} else if(id == cur.next.data.getId()) {
				// 不允许插入
				addFlag = true;
				break;
			}
			
			cur = cur.next;
		}
		
		if(addFlag) {
			System.out.println("添加结点失败，已存在结点=" + cur.next.data);
		} else {
			cur.next = new Node(hero, cur.next);
		}
	}
	
	/**
	 * 	根据id进行更新操作
	 */
	public boolean updateByHero(Hero hero) {
		if(isEmpty()) {
			return false;
		}
		
		Node currentNode = head.next;
		while(currentNode != null && hero.getId() != currentNode.data.getId()) {
			currentNode = currentNode.next;
		}
		if(currentNode == null) {
			System.out.println("未找到要更新的结点");
			return false;
		}
		
		currentNode.data = hero;
		return true;
	}
	
	/**
	 * 	根据id 进行删除操作
	 */
	public Hero deleteById(int id) {
		if(isEmpty()) {
			return null;
		}
		
		Node cur = head;
		while(cur.next != null && id != cur.next.data.getId()) {
			cur = cur.next;
		}
		
		if(cur.next == null) {
			System.out.println("未找到要删除的id="+ id +"结点");
			return null;
		}
		
		Hero result = cur.next.data;
		System.out.println("成功删除结点值：" + result);
		cur.next = cur.next.next;
		return result;
	} 
	
	/**
	 * 从尾部添加结点
	 */
	public boolean add(Hero hero) {
		Node cur = head;
		
		// 设置一个游标(从首结点位置开始)， 只要当前游标的下一个结点不为null，就移动游标，直到游标的下一个结点为null，表示游标到了最尾部
		while(cur.next != null) {
			cur = cur.next;
		}
		
		// 此时游标指向结点的最尾部，将新结点添加到当前游标的下一个结点
		cur.next = new Node(hero, null);
		return true;
	}
	
	/**
	 * 遍历并打印链表
	 */
	public void list() {
		System.out.println("遍历并打印链表");
		if(isEmpty()) {
			return;
		}
		
		Node currentNode = head.next;
		while(currentNode != null) {
			System.out.println(currentNode.data);
			currentNode = currentNode.next;
		}
		return;
	}
	
	private boolean isEmpty() {
		if(head.next == null) {
			System.out.println("链表为空！");
			return true;
		}
		
		return false;
	}
}

class Node {
	public Hero data;
	public Node next;
	
	public Node(Hero hero, Node node) {
		this.data = hero;
		this.next = node;
	}
	
}