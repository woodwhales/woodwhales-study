package org.woodwhale.datastructure.linkedlist;

import lombok.AllArgsConstructor;

/**
 * 	双向链表
 *	这里引用了head 头结点，方便遍历操作，
 *	但是有个小问题：首结点的 pre 指向的是null，但是对于删除和遍历操作没有影响
 */
public class DoubledLinkedListDemo {

	public static void main(String[] args) {
		DoubledLinkedList linkedList = new DoubledLinkedList();
		
		linkedList.add(new Hero(1, "小美"));
		linkedList.add(new Hero(4, "小武"));
		linkedList.add(new Hero(2, "小仓"));
		linkedList.add(new Hero(6, "小泽"));
		linkedList.add(new Hero(5, "super 小仓"));
		linkedList.add(new Hero(7, "super 小泽"));
		linkedList.add(new Hero(3, "super 小仓 plus"));
		linkedList.add(new Hero(3, "super 小仓 plus2"));
		
		// 根据id更新结点
//		linkedList.list();
//		linkedList.update(new Hero(1, "super 小美"));
		
		// 根据id删除结点
//		linkedList.list();
//		linkedList.deleteById(1);

		// 在第k个结点插入新的结点
//		linkedList.list();
//		linkedList.addOnIndex(7, new Hero(8, "钢铁侠"));
		linkedList.list();
	}
}

class DoubledLinkedList {
	
	private DoubleNode head = new DoubleNode(null, null, null);
	
	/**
	 * 	在第k个位置插入结点，从1开始计数
	 */
	public boolean addOnIndex(int index, Hero hero) {
		int length = getLength();
		System.out.println(length);
		
		if(index <= 0 || index > length) {
			System.out.println("要插入的位置非法");
			return false;
		}
		
		DoubleNode currentNode = head.next;
		for (int i = 1; i < index; i++) {
			currentNode = currentNode.next;
		}
		
		currentNode.pre.next = new DoubleNode(currentNode.pre, hero, currentNode.next);
		return true;
	}
	
	/**
	 * 	获取链表长度
	 */
	public int getLength() {
		int count = 0;
		DoubleNode currentNode = head.next;
		while(currentNode != null) {
			count++;
			currentNode = currentNode.next;
		}
		return count;
	}
	
	/**
	 * 	根据id删除结点
	 */
	public DoubleNode deleteById(int id) {
		System.out.println("根据id删除对应的结点");
		if(isEmpty()) {
			return null;
		}
		
		DoubleNode currentNode = head.next;
		while(currentNode != null && id != currentNode.data.getId()) {
			currentNode = currentNode.next;
		}
		
		if(currentNode == null) {
			System.out.printf("要删掉id为%d的结点不存在！\n", id);
			return null;
		}
		
		currentNode.pre.next = currentNode.next;
		if(currentNode.next != null) {
			currentNode.next.pre = currentNode.pre;
		}
		return currentNode;
	}
	
	/**
	 * 	根据id 进行有序新增结点
	 */
	public void addById(Hero hero) {
		DoubleNode cur = head;
		
		boolean addFlag = true;
		while(cur.next != null) {
			
			if(hero.getId() > cur.next.data.getId()) {
				break;
			}
			
			if(hero.getId() == cur.next.data.getId()) {
				addFlag = false;
				break;
			}
			cur = cur.next;
		}
		
		DoubleNode currentNode = cur.next;
		if(addFlag) {
			cur.next = new DoubleNode(cur, hero, cur.next);
			return;
		} else {
			System.out.printf("新增结点失败，已存在id为%d的结点\n", currentNode.data.getId());
		}
	}
	
	/**
	 *	根据 id 更新链表中的结点 
	 */
	public boolean update(Hero hero) {
		if(isEmpty()) {
			return false;
		}
		
		DoubleNode currentNode = head.next;
		while(currentNode != null && hero.getId() != currentNode.data.getId()) {
			currentNode = currentNode.next;
		}
		
		if(currentNode == null) {
			System.out.printf("链表中不存在要更新id为%d的结点！\n", hero.getId());
			return false;
		}
		
		currentNode.data = hero;
		return true;
	}
	
	
	/**
	 * 	添加新的结点,增加到尾部
	 */
	public void add(Hero hero) {
		DoubleNode cur = head;
		
		while(cur.next != null) {
			cur = cur.next;
		}
		
		cur.next = new DoubleNode(cur, hero, cur.next);
		return;
	}
	
	/**
	 * 	遍历双向链表
	 */
	public void list() {
		System.out.println("遍历双向链表");
		if(isEmpty()) {
			return;
		}
		
		DoubleNode currentNode = head.next;
		while(currentNode != null) {
			System.out.println(currentNode.data);
			currentNode = currentNode.next;
		}
		System.out.println();
	}
	
	/**
	 * 	判断双向链表是否为空
	 */
	private boolean isEmpty() {
		if(head.next == null) {
			System.out.println("链表为空！");
			return true;
		}
		return false;
	}
	
}

@AllArgsConstructor
class DoubleNode {
	public DoubleNode pre;
	public Hero data;
	public DoubleNode next;
}