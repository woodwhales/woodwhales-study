package org.woodwhale.algorithm.tree;

import lombok.Data;

/**
 *	二叉树
 *	
 *	         1,小苍
 *			 /   \
 *		2,小波           3,小泽
 *			      /  \
 *			   5,小美    4,小野
 *			   /
 *		     6,小鸡
 */
public class BinaryTreeDemo {

	public static void main(String[] args) {
		StudentTreeNode root = new StudentTreeNode(1, "小苍");
		StudentTreeNode node2 = new StudentTreeNode(2, "小波");
		StudentTreeNode node3 = new StudentTreeNode(3, "小泽");
		StudentTreeNode node4 = new StudentTreeNode(4, "小野");
		StudentTreeNode node5 = new StudentTreeNode(5, "小美");
		StudentTreeNode node6 = new StudentTreeNode(6, "小鸡");
		
		node5.setLeft(node6);
		
		node3.setLeft(node5);
		node3.setRight(node4);
		
		root.setLeft(node2);
		root.setRight(node3);
		
		BinaryTree binaryTree = new BinaryTree(root);
		
		// 前序遍历 -> 1,2,3,5,6,4
		binaryTree.preOrder();
		
		// 中序遍历 -> 2,1,6,5,3,4
		binaryTree.infixOrder();
		
		// 后序遍历 -> 2,6,5,4,3,1
		binaryTree.postOrder();
		
		
		// 前序遍历查找
		int id1 = 3;
		StudentTreeNode preResult = binaryTree.preSearch(id1);
		check(preResult, id1);
		
		// 中序遍历查找
		int id2 = 5;
		StudentTreeNode infixResult = binaryTree.infixSearch(id2);
		check(infixResult, id2);
		
		// 后序遍历查找
		int id3 = 2;
		StudentTreeNode postResult = binaryTree.postSearch(id3);
		check(postResult, id3);
		
		// 根据id删除结点
//		int id = 5;
//		binaryTree.preOrder();
//		binaryTree.deleteNode(id);
//		binaryTree.preOrder();
		
		// 根据id删除替代结点
		int id = 5;
		binaryTree.preOrder();
		binaryTree.deleteNode2(id);
		binaryTree.preOrder();
	}

	private static void check(StudentTreeNode preResult, int id) {
		if(preResult == null) {
			System.out.printf("id=%d节点未找到\n", id);
			return;
		}
		
		System.out.printf("id=%d节点找到 -> %s\n", id, preResult);
		return;
	}
}

class BinaryTree {
	private StudentTreeNode root;
	
	public BinaryTree(StudentTreeNode root) {
		this.root = root;
	}
	
	/**
	 * 	根据id删除结点
	 * @param id
	 */
	public void deleteNode(int id) {
		if(isEmpty()) {
			System.out.println("树为空");
			return;
		}
		
		if(id == this.root.getId()) {
			this.root = null;
		} else {
			this.root.deleteNode(id);
		}
	}
	
	/**
	 * 	根据id删除结点
	 * @param id
	 */
	public void deleteNode2(int id) {
		if(isEmpty()) {
			System.out.println("树为空");
			return;
		}
		
		if(id == this.root.getId()) {
			this.root = null;
		} else {
			this.root.deleteNode2(id);
		}
	}

	/**
	 * 	后序遍历查找
	 * @param id
	 */
	public StudentTreeNode postSearch(int id) {
		if(isEmpty()) {
			System.out.println("树为空");
			return null;
		}
		
		return this.root.postSearch(id);
	}

	/**
	 * 	中序遍历查找
	 * @param id
	 */
	public StudentTreeNode infixSearch(int id) {
		if(isEmpty()) {
			System.out.println("树为空");
			return null;
		}
		
		return this.root.infixSearch(id);
	}

	/**
	 * 	前序遍历查找
	 * @param id
	 */
	public StudentTreeNode preSearch(int id) {
		if(isEmpty()) {
			System.out.println("树为空");
			return null;
		}
		
		return this.root.preSearch(id);
	}

	/**
	 * 	前序遍历
	 */
	public void preOrder() {
		System.out.println("前序遍历");
		
		if(isEmpty()) {
			System.out.println("树为空");
			return;
		}
		
		this.root.preOrder();
		System.out.println();
	}
	
	/**
	 * 	中序遍历
	 */
	public void infixOrder() {
		System.out.println("中序遍历");
		
		if(isEmpty()) {
			System.out.println("树为空");
			return;
		}
		
		this.root.infixOrder();
		System.out.println();
	}
	
	/**
	 * 	后序遍历
	 */
	public void postOrder() {
		System.out.println("后序遍历");
		
		if(isEmpty()) {
			System.out.println("树为空");
			return;
		}
		
		this.root.postOrder();
		System.out.println();
	}
	
	private boolean isEmpty() {
		return root == null ? true : false;
	}
}

@Data
class StudentTreeNode {

	private Integer id;
	private String name;
	private StudentTreeNode left;
	private StudentTreeNode right;
	
	public StudentTreeNode(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	/**
	 * 	根据id删除结点
	 * 	如果要删除的结点是父节点A，
	 * 	当该父节点A有一个子节点B的时候，则删除这个子节点B，保留当前本要删除的结点A
	 * 	当该父节点A有两个子节点，分别是左子结点B和右子结点C的时候，则删除这个左子节点B，保留当前本要删除的结点A
	 * @param id
	 */
	public void deleteNode2(int id) {
		
		if(this.left != null) {
			if(id == this.left.getId()) {
				
				if(this.left.left != null || this.left.right != null) {
					this.left.left = null;
					return;
				}
				
				this.left = null;
				return;
			}

			this.left.deleteNode2(id);
		}
		
		if(this.right != null) {
			if(id == this.right.getId()) {
				if(this.right.left != null || this.left.right != null) {
					this.right.left = null;
					return;
				}
				
				this.right = null;
				return;
			}
			
			this.right.deleteNode2(id);
		}
	}

	/**
	 * 	根据id删除结点
	 * @param id
	 */
	public void deleteNode(int id) {
		
		if(this.left != null) {
			if(id == this.left.getId()) {
				this.left = null;
				return;
			}

			this.left.deleteNode(id);
		}
		
		if(this.right != null) {
			if(id == this.right.getId()) {
				this.right = null;
				return;
			}
			
			this.right.deleteNode(id);
		}
	}

	/**
	 * 	前缀遍历搜索
	 * @param id
	 * @return
	 */
	public StudentTreeNode preSearch(int id) {
		StudentTreeNode result = null;
		
		if(id == this.id) {
			result = this;
			return result;
		}
		
		if(this.left != null) {
			result = this.left.preSearch(id);
		}
		
		if(result != null) {
			return result;
		}
		
		if(this.right != null) {
			result = this.right.preSearch(id);
		}
		
		return result;
	}

	/**
	 * 	中缀遍历搜索
	 * @param id
	 * @return
	 */
	public StudentTreeNode infixSearch(int id) {
		StudentTreeNode result = null;
		
		if(this.left != null) {
			result = this.left.infixSearch(id);
		}
		
		if(result != null) {
			return result;
		}
		
		if(id == this.id) {
			result = this;
			return result;
		}
		
		if(this.right != null) {
			result = this.right.infixSearch(id);
		}
		
		return result;
	}

	/**
	 * 	后缀遍历搜索
	 * @param id
	 * @return
	 */
	public StudentTreeNode postSearch(int id) {
		StudentTreeNode result = null;
		
		if(this.left != null) {
			result = this.left.postSearch(id);
		}
		
		if(result != null) {
			return result;
		}
		
		if(this.right != null) {
			result = this.right.postSearch(id);
		}
		
		if(result != null) {
			return result;
		}
		
		if(id == this.id) {
			result = this; 
		}
		
		return result;
	}

	public void infixOrder() {
		
		if(this.left != null) {
			this.left.infixOrder();
		}
		
		System.out.print(this.id +" ");
		
		if(this.right != null) {
			this.right.infixOrder();
		}
		
		return;
	}

	public void postOrder() {
		
		if(this.left != null) {
			this.left.postOrder();
		}
		
		if(this.right != null) {
			this.right.postOrder();
		}

		System.out.print(this.id +" ");
		
		return;
	}

	public void preOrder() {
		
		System.out.print(this.id +" ");
		
		if(this.left != null) {
			this.left.preOrder();
		}
		
		if(this.right != null) {
			this.right.preOrder();
		}
		
		return;
	}

	@Override
	public String toString() {
		return "StudentTreeNode [id=" + id + ", name=" + name + "]";
	}
	
}
