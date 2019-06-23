package org.woodwhale.datastructure.avl;

import lombok.Data;

/**
 * 	平衡二叉树
 *
 */
public class AVLTreeDemo {

	public static void main(String[] args) {
//		int[] arr = { 4, 3, 6, 5, 7, 8 }; // 需要左旋
		
//		int[] arr = { 10, 12, 8, 9, 7, 6 }; // 需要右旋
		
//		int[] arr = { 10, 11, 7, 6, 8, 9 }; // 需要双旋
		
		int[] arr = { 2, 1, 6, 5, 7, 3 }; // 需要双旋
		
		AVLTree avlTree = createAVLTree(arr);
		System.out.println("中序遍历：");
		avlTree.infixOrder();
		
		System.out.println("树的高度 -> " + avlTree.getRoot().height());
		System.out.println("左子树的高度 -> " + avlTree.getRoot().leftHeight());
		System.out.println("右子树的高度 -> " + avlTree.getRoot().rightHeight());
		System.out.println("当前根结点 -> " + avlTree.getRoot());
	}

	private static AVLTree createAVLTree(int[] arr) {
		AVLTree avlTree = new AVLTree();
		for (int data : arr) {
			avlTree.add(new Node(data));
		}
		
		return avlTree;
	}
}

@Data
class AVLTree {
	private Node root;

	public void deleteNode(int value) {
		if (root == null) {
			return;
		} else {
			Node targetNode = search(value);
			if (targetNode == null) {
				System.out.printf("未找到要删除的value=%d结点！\n", value);
				return;
			}

			if (isLeafNode(root)) {
				root = null;
				return;
			}

			Node parentNode = searchParent(value);

			// 要删除的结点是叶子结点
			if (isLeafNode(targetNode)) {
				if (isLeftNodeOfParentNode(parentNode, targetNode)) {
					parentNode.left = null;
					return;
				}

				if (isRightNodeOfParentNode(parentNode, targetNode)) {
					parentNode.right = null;
					return;
				}

			}

			// 要删除的结点有两个子结点
			if (hasDoubleChildNode(targetNode)) {
				// 找到要删除结点的右子树最小结点
				int minVal = deleteMaxValueOfRightChildTree(targetNode);
				targetNode.value = minVal;
				
				// 找到要删除结点的左子树最大结点
//				int maxVal = deleteMinValueOfLeftChildTree(targetNode);
//				targetNode.value = maxVal;
				return;
			}

			// 要删除的结点只有一个左结点
			if (hasSingleNodeOfLeftChildNode(targetNode)) {
				if (parentNode != null) {
					// 要删除的结点是父结点的左子结点
					if (isLeftNodeOfParentNode(parentNode, targetNode)) {
						parentNode.left = targetNode.left;
						return;
					}

					// 要删除的结点是父结点的右子结点
					if (isRightNodeOfParentNode(parentNode, targetNode)) {
						parentNode.right = targetNode.left;
						return;
					}
					
				}
				
				this.root = targetNode.left;
				return;
			}
			
			// 要删除的结点只有一个右结点
			if (hasSingleNodeOfRightChildNode(targetNode)) {
				if (parentNode != null) {
					// 要删除的结点是父结点的左子结点
					if (isLeftNodeOfParentNode(parentNode, targetNode)) {
						parentNode.left = targetNode.right;
						return;
					}

					// 要删除的结点是父结点的右子结点
					if (isRightNodeOfParentNode(parentNode, targetNode)) {
						parentNode.right = targetNode.right;
						return;
					}
				}

				this.root = targetNode.right;
				return;
			}
		}
	}


	private boolean hasSingleNodeOfRightChildNode(Node node) {
		return node.right != null && node.left == null;
	}

	private boolean hasSingleNodeOfLeftChildNode(Node node) {
		return node.right == null && node.left != null;
	}

	private boolean hasDoubleChildNode(Node node) {
		return (node.left != null && node.right != null);
	}

	private static boolean isLeftNodeOfParentNode(Node parentNode, Node childNode) {
		if (parentNode == null) {
			new RuntimeException("要判断的子节点是否属于父节点，该父节点为空！");
		} else if (parentNode.left == null) {
			new RuntimeException("要判断的子节点是否属于父节点，该父节点没有左结点！");
		}

		return parentNode.left == childNode;
	}

	private static boolean isRightNodeOfParentNode(Node parentNode, Node childNode) {
		if (parentNode == null) {
			new RuntimeException("要判断的子节点是否属于父节点，该父节点为空！");
		} else if (parentNode.right == null) {
			new RuntimeException("要判断的子节点是否属于父节点，该父节点没有右结点！");
		}

		return parentNode.right == childNode;
	}

	private static boolean isLeafNode(Node node) {
		return (node.left == null && node.right == null);
	}

	/**
	 * 	删除node结点的右子树中的最大结点，并返回其值
	 * 
	 * @param node
	 * @return
	 */
	private int deleteMaxValueOfRightChildTree(Node node) {
		Node tempNode = node.right;
		while (tempNode.left != null) {
			tempNode = tempNode.left;
		}

		deleteNode(tempNode.value);
		return tempNode.value;
	}
	
	/**
	 * 	删除node结点的左子树中的最小结点，并返回其值
	 * 
	 * @param node
	 * @return
	 */
	private int deleteMinValueOfLeftChildTree(Node node) {
		Node tempNode = node.right;
		while (tempNode.right != null) {
			tempNode = tempNode.right;
		}

		deleteNode(tempNode.value);
		return tempNode.value;
	}

	public void add(Node node) {
		if (this.root == null) {
			this.root = node;
			return;
		}

		this.root.add(node);
	}

	public void infixOrder() {
		if (isEmpty()) {
			return;
		}

		this.root.infixOrder();
	}

	public Node search(int value) {
		if (isEmpty()) {
			return null;
		}

		return this.root.search(value);
	}

	public Node searchParent(int value) {
		if (isEmpty()) {
			return null;
		}

		return this.root.searchParent(value);
	}

	private boolean isEmpty() {
		if (this.root == null) {
			System.out.println("二叉树为空！");
			return true;
		}
		return false;
	}

}

class Node {
	public int value;
	public Node left;
	public Node right;
	
	/**
	 * 	左旋
	 * 
	 * 	4, 3, 6, 5, 7, 8
	 * 
	 * 	   4                      6
	 * 	  / \                    / \
	 * 	 3   6					4   7
 	 * 		/ \        =>      / \   \ 
	 * 	   5   7              3   5   8
	 * 			\
	 * 			 8
	 */
	public void leftRotate() {
		// 创建一个新的结点，保存当前要左旋结点的值
		Node newNode = new Node(this.value);
		
		// 将当前要左旋结点的左子树挂到新结点的左子结点上
		newNode.left = this.left;
		
		// 将新结点的右子树连接挂到当前要左旋结点的右子树的左子树
		newNode.right = this.right.left;
		
		// 将当前要左旋结点的值设置成自己的右子结点的值
		this.value = this.right.value;
		
		// 将当前要左旋的结点的右结点重新挂到该右结点的右子树
		this.right = this.right.right;
		
		// 将当前要做旋的结点的左子树设置成新的结点
		this.left = newNode;
	}
	
	/**
	 * 	右旋
	 * 
	 * 	10, 12, 8, 9, 7, 6
	 * 
	 * 			 10                 8
	 * 		    /  \			   / \
	 * 		   8    12    =>      7   10
	 *        / \                /    / \
	 *       7   9              6    9   12
	 *      /
	 *     6
	 */
	public void rightRotate() {
		Node newNode = new Node(this.value);
		newNode.right = this.right;
		newNode.left = this.left.right;
		
		this.value = this.left.value;
		this.left = this.left.left;
		
		this.right = newNode;
	}
	
	
	/**
	 * 	计算左子树的高度
	 * @return
	 */
	public int leftHeight() {
		if(this.left == null) {
			return 0;
		}
		return this.left.height();
	}
	
	/**
	 * 	计算右子树的高度
	 * @return
	 */
	public int rightHeight() {
		if(this.right == null) {
			return 0;
		}
		return this.right.height();
	}
	
	/**
	 * 	计算当前树的高度
	 * @return
	 */
	public int height() {
		return Math.max((this.left == null) ? 0 : this.left.height(), (this.right == null) ? 0 : this.right.height()) + 1;
	}
	
	/**
	 * 	根据value搜索对应的结点的父节点
	 * 
	 * @param value
	 * @return
	 */
	public Node searchParent(int value) {
		if (this.left != null && this.left.value == value || this.right != null && this.right.value == value) {
			return this;
		}

		if (value < this.value && this.left != null) {
			return this.left.searchParent(value);
		}

		if (this.right != null) {
			return this.right.searchParent(value);
		}

		return null;

	}

	/**
	 * 	根据value搜索对应的结点
	 * 
	 * @param value
	 * @return
	 */
	public Node search(int value) {
		if (value == this.value) {
			return this;
		}

		if (value < this.value) {
			if (this.left == null) {
				return null;
			}

			return this.left.search(value);
		}

		if (this.right == null) {
			return null;
		}

		return this.right.search(value);
	}

	/**
	 * 添加结点
	 */
	public void add(Node node) {
		if (node.value < this.value) {
			if (this.left == null) {
				this.left = node;
				return;
			} else {
				this.left.add(node);
			}
		} else {
			if (this.right == null) {
				this.right = node;
				return;
			} else {
				this.right.add(node);
			}
		}

		// 右子树的高度 - 左子树的高度 > 1， 左旋
		if(this.rightHeight() - this.leftHeight() > 1) {
			if(this.right != null && this.right.leftHeight() > this.right.rightHeight()) {
				this.right.rightRotate();
			}
			
			leftRotate();
			return;
		}
		
		// 左子树的高度 - 右子树的高度 > 1， 右旋
		if(this.leftHeight() - this.rightHeight() > 1) {
			// 在右旋之前，一定要保证左子结点的左子树高度不能比其右子树高度高，否则会出现不平衡现象
			if(this.left != null && this.left.rightHeight() > this.left.leftHeight()) {
				this.left.leftRotate();
			}
			
			rightRotate();
			return;
		}
	}

	/**
	 * 	中序遍历
	 */
	public void infixOrder() {
		if (this.left != null) {
			this.left.infixOrder();
		}

		System.out.println(this);

		if (this.right != null) {
			this.right.infixOrder();
		}
	}

	public Node(int value) {
		super();
		this.value = value;
	}

	@Override
	public String toString() {
		return "Node [value=" + value + "]";
	}
}