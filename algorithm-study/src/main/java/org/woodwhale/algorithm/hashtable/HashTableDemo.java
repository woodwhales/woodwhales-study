package org.woodwhale.algorithm.hashtable;

/**
 * 哈希表 一种数据结构 这里使用数组加链表的方式
 *
 */
public class HashTableDemo {

	public static void main(String[] args) {
		HashTab hashTab = new HashTab();
		hashTab.add(new Teacher(1, "tom"));
		hashTab.add(new Teacher(2, "jack"));
		hashTab.list();
		Teacher teacher = hashTab.findById(3);
		System.out.println(teacher);
	}

}

class HashTab {
	private int capacity;

	private TeacherLinkedList[] linkedListArray;

	public HashTab(int capacity) {
		if(capacity < 0) {
			throw new RuntimeException("初始化失败，capacity 参数非法 ");
		}
		init(capacity);
	}

	/**
	 * 	根据id查找元素
	 * @param id
	 * @return
	 */
	public Teacher findById(int id) {
		
		int index = id % this.capacity;
		return this.linkedListArray[index].findById(id);
	}

	/**
	 * 	初始化哈希表
	 * @param capacity
	 */
	private void init(int capacity) {
		this.capacity = capacity;
		this.linkedListArray = new TeacherLinkedList[this.capacity];
		
		for(int i = 0; i< this.linkedListArray.length; i++) {
			this.linkedListArray[i] = new TeacherLinkedList();
		}
	}

	/**
	 * 	默认哈希表容量为10
	 */
	public HashTab() {
		init(10);
	}
	
	/**
	 * 	新增元素
	 * @param teacher
	 */
	public void add(Teacher teacher) {
		int index = teacher.getId() % capacity;
		this.linkedListArray[index].add(teacher);
		return;
	}
	
	/**
	 * 	遍历哈希表
	 */
	public void list() {
		for(int i = 0; i < this.linkedListArray.length; i++) {
			this.linkedListArray[i].list();
		}
		return;
	}
}

class TeacherLinkedList {
	public Teacher head = null;

	private boolean isEmpty() {
		return head == null ? true : false;
	}
	
	public Teacher findById(int id) {
		
		if(isEmpty()) {
			return null;
		}
		
		Teacher cur = this.head;
		while(id != cur.getId()) {
			cur = cur.getNext();
		}
		return cur;
	}

	public void add(Teacher teacher) {
		if(isEmpty()) {
			head = teacher;
			return;
		}
		
		Teacher cur = head;
		while(cur.getNext() != null) {
			cur = cur.getNext(); 
		}
		
		cur.setNext(teacher);
		return;
	}
	
	public void list() {
		if(isEmpty()) {
			System.out.println("当前链表为空");
			return;
		}
		
		Teacher cur = head;
		while(cur != null) {
			System.out.printf("%s ", cur);
			cur = cur.getNext();
		}
		System.out.println();
		return;
	}

}

class Teacher {
	
	private Integer id;
	private String name;
	private Teacher next;
	
	public Teacher() {
	}
	
	public Teacher(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Teacher getNext() {
		return next;
	}

	public void setNext(Teacher next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", name=" + name + "]";
	}
	
}