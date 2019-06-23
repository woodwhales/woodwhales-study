package org.woodwhale.datastructure.bst;

/**
 * 	二叉排序树 BST
 * 
 * 	二叉查找树（英语：Binary Search Tree）， 也称为二叉搜索树、有序二叉树（ordered binarytree）或排序二叉树（sorted binary tree），
 * 	 是指一棵空树或者具有下列性质的二叉树：
 * 	若任意节点的左子树不空，则左子树上所有节点的值均小于它的根节点的值； 若任意节点的右子树不空，则右子树上所有节点的值均大于它的根节点的值；
 * 	任意节点的左、右子树也分别为二叉查找树； 没有键值相等的节点。
 * 
 * 	二叉查找树相比于其他数据结构的优势在于查找、插入的时间复杂度较低。为 O(log n)}
 */
public class BinarySearchTreeDemo {

	public static void main(String[] args) {
		int[] arr = { 7, 3, 10, 12, 5, 1, 9 };

		BinarySearchTree binarySearchTree = createBinarySearchTree(arr);

//		System.out.println("中序遍历二叉搜索树");
//		binarySearchTree.infixOrder(); // 1, 3, 5, 6, 9, 10, 12

		// 测试搜索结点和父结点
//		testSearchNode(binarySearchTree, 12);

		// 添加新结点
		binarySearchTree.add(new Node(2));
//		binarySearchTree.add(new Node(11));
//		binarySearchTree.add(new Node(13));
//		System.out.println("中序遍历二叉搜索树");
//		binarySearchTree.infixOrder();

		// 删除结点
		binarySearchTree.deleteNode(3);
		binarySearchTree.deleteNode(5);
		binarySearchTree.deleteNode(2);
		binarySearchTree.deleteNode(12);
		binarySearchTree.deleteNode(7);
		binarySearchTree.deleteNode(10);
		binarySearchTree.deleteNode(1);
		binarySearchTree.deleteNode(9);

//		System.out.println("中序遍历二叉搜索树");
//		binarySearchTree.infixOrder();

	}

	public static BinarySearchTree createBinarySearchTree(int[] arr) {
		BinarySearchTree binarySearchTree = new BinarySearchTree();

		for (int data : arr) {
			binarySearchTree.add(new Node(data));
		}
		return binarySearchTree;
	}

	public static void testSearchNode(BinarySearchTree binarySearchTree, int value) {
		System.out.println("查找结点");
		Node node = binarySearchTree.search(value);
		System.out.println("node -> " + node);
		Node parentNode = binarySearchTree.searchParent(value);
		System.out.println("parentNode -> " + parentNode);
	}
}

class BinarySearchTree {
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