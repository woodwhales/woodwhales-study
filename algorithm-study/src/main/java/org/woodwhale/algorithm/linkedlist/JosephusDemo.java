package org.woodwhale.algorithm.linkedlist;

import lombok.Data;

/**
 *	约瑟夫问题
 *
 *	人们站在一个等待被处决的圈子里。 
 *	计数从圆圈中的指定点开始，并沿指定方向围绕圆圈进行。 
 *	在跳过指定数量的人之后，执行下一个人。 
 *	对剩下的人重复该过程，从下一个人开始，朝同一方向跳过相同数量的人，直到只剩下一个人，并被释放。
 *	问题即，给定人数、起点、方向和要跳过的数字，选择初始圆圈中的位置以避免被处决。
 *
 *	举例： 
 *	共有6个人围城一圈，编号（站位顺序）分别为： 1, 2, 3, 4, 5, 6
 *	从1号开始游戏，叫到第三个则出局：
 *	第 1 次叫号： 1（叫1），2（叫2），3（叫3，出局），此时剩余圈子：1, 2, 4, 5, 6
 *	第 2 次叫号： 4（叫1），5（叫2），6（叫3，出局），此时剩余圈子：1, 2, 4, 5
 *	第 3 次叫号： 1（叫1），2（叫2），4（叫3，出局），此时剩余圈子：1, 2, 5
 *	第 4 次叫号： 5（叫1），1（叫2），2（叫3，出局），此时剩余圈子：1, 5
 *	第 5 次叫号： 5（叫1），1（叫2），5（叫3，出局），此时剩余圈子：1
 *
 *	出局顺序：3 --> 6 --> 4 --> 2 --> 5 --> 1
 */
public class JosephusDemo {

	public static void main(String[] args) {
		CircleSingleLinkedList linkedList = new CircleSingleLinkedList();
		// 初始化链表方式1
		linkedList.createCircle(11);
		// 初始化链表方式2
//		linkedList.initLinkedList(6);
		
		linkedList.list();
		linkedList.countPerson(1, 3);
	}

}

class CircleSingleLinkedList {
	
	private Person first; // 标记谁是开始点
	
	public void countPerson(int startNo, int countNum) {
		if(startNo < 1 || startNo > countNum) {
			System.out.println("参数输入有误");
			return;
		}
		
		// 找到首位置之前的一个结点位置，为了数数到需要删除的结点，操作删除操作
		Person helperCur = first;
		while(true) {
			if(helperCur.getNext() == first) {
				break;
			}
			helperCur = helperCur.getNext();
		}
		
		/**
		 * 	将起始位置和辅助游标移动到指定起始位置
		 */
		for(int i = 0; i < startNo-1; i++) {
			first = first.getNext();
			helperCur = helperCur.getNext();
		}
		
		while(true) {
			if(helperCur == first) {
				break;
			}
			
			/**
			 * 	每多少次计数，从新计数
			 */
			for(int i = 0; i < countNum-1; i++) {
				first = first.getNext();
				helperCur = helperCur.getNext();
			}
			System.out.printf("%d --> ",first.getId());
			first = first.getNext();
			helperCur.setNext(first);
			
		}
		System.out.printf("%d\n", helperCur.getId());
		return;
	}

	/**
	 * 	初始化链表
	 * @param personNumber 要创建循环链表的结点个数
	 */
	public void initLinkedList(int personNumber) {
		if(personNumber < 1) {
			System.out.println("初始化人数数值非法！");
			return;
		}
		
		Person curPerson = null;
		for (int i = 1; i <= personNumber; i++) {
			Person person = new Person(i);
			
			if(i == 1) {
				// 当前只有一个结点，first 标志到第一个结点
				first = person;
			} else {
				curPerson.setNext(person);
			}
			// 将新结点指向第一个结点
			person.setNext(first);
			// 游标移动到最新的结点
			curPerson = person;
		}
		
	}
	
	/**
	 *	使用递归创建循环链表
	 * @param personNumber 要创建循环链表的结点个数
	 * @return
	 */
	public Person createCircle(int personNumber) {
		if(personNumber < 1) {
			System.out.println("输入参数非法！");
			return null;
		}
		
		// 创建当前的结点
		Person person = new Person(personNumber);
		if(personNumber == 1) {
			first = person;
			first.setNext(person);
			person.setNext(first);
			return person;
		}
		
		// 将当前结点添加到上一个结点之后
		createCircle(personNumber-1).setNext(person);
		// 将 first 设置为当前结点的下一个结点
		person.setNext(first);
		return person;
	}
	
	public int getLenth() {
		if(isEmpty()) {
			return 0;
		}
		
		Person cur = first;
		int count = 0;
		while(true) {
			if(cur.getNext() == first) {
				break;
			}
			cur = cur.getNext();
			count++;
		}
		System.out.println("链表长度"+count);
		return count;
	}
	
	/**
	 * 	遍历链表
	 */
	public void list() {
		if(isEmpty()) {
			return;
		}
		
		Person cur = first;
		while(true) {
			System.out.printf("%d ", cur.getId());
			if(cur.getNext() == first) {
				break;
			}
			cur = cur.getNext();
		}
		System.out.println();
		return;
	}

	private boolean isEmpty() {
		if(first == null) {
			System.out.println("链表为空！");
			return true;
		}
		return false;
	}
}

@Data
class JosephusNode {
	private Person first;
	private Person currentNode;
}

class Person {
	
	private int id;
	private Person next;
	
	public Person(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public Person getNext() {
		return next;
	}

	public Person setNext(Person next) {
		this.next = next;
		return this;
	}
	
}
