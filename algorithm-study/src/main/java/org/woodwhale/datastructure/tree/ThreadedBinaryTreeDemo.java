package org.woodwhale.datastructure.tree;

import lombok.Data;

/**
 *	线索二叉树 
 *	
 *	背景：	
 *	现有一棵结点数目为n的二叉树，采用二叉链表的形式存储。
 *	对于每个结点均有指向左右孩子的两个指针域，而结点为n的二叉树一共有n-1条有效分支路径。
 *	那么，则二叉链表中存在2n-(n-1)=n+1个空指针域。那么，这些空指针造成了空间浪费。
 *
 * 	思考：
 *	我们如何区分一个结点的子节点指针是指向真实的子节点还是前驱/后继结点呢？
 *	解决方案：使用一个标记来标记当前的子节点是指向子节点还是指向前驱/后继节点
 *
 *	定义：
 *	n个结点的二叉树，采用链式存储结构时，有n+1个空链域，利用这些空链域存放指向结点的直接前驱和直接后继的指针。
 *	
 *	规定：	
 *	若结点有左子树，则其lelft域指示其左子结点，否则令left域指示其前驱；
 *	若结点有右子树，则其right域指示其右子结点，否则令right域指示其后继。
 *	
 *	为了避免混淆，我们需改变结点结构，在二叉存储结构的结点结构上增加两个标志域，这样，每个结点的存储结构如下：
 *		线索链表：以上述结点结构构成的二叉链表作为二叉树存储结构的链表。
 *		线索：指向结点前驱和后继的指针。
 *		线索二叉树：每个结点上加上线索的二叉树。
 *		线索化：对二叉树以某种次序遍历使其变为线索二叉树的过程。
 *
 *	建立线索二叉树，或者说，对二叉树线索化，实质上就是：
 *		遍历一棵二叉树，在遍历的过程中，检查当前结点的左、右指针域是否为空。
 *		如果为空，将它们改为指向前驱结点或后继结点的线索。
 *	
 *
 *	
 *	如：
 *				 1
 *			   /   \
 *			  3     6
 *			 / \   / 
 *			8  10 14
 *	前序遍历：1, 3, 8, 10, 6, 14
 *	中序遍历：8, 3, 10, 1, 14, 6
 *	后序遍历：8, 10, 3, 14, 6, 1
 *	
 *	c语言中文网对线索化思路总结如下：http://c.biancheng.net/cpp/html/982.html
 *	一、在中序线索二叉树上查找任意结点的中序前驱结点
 *	对于中序线索二叉树上的任一结点，寻找其中序的前驱结点，有以下两种情况：
 *		（1）如果该结点的左标志为1，那么其左指针域所指向的结点便是它的前驱结点；
 *		（2）如果该结点的左标志为0，表明该结点有左孩子，根据中序遍历的定义，它的前驱结点是以该结点的左孩子为根结点的子树的最右结点，
 *			即沿着其左子树的右指针链向下查找，当某结点的右标志为1 时，它就是所要找的前驱结点。
 *
 *	二、在中序线索二叉树上查找任意结点的中序后继结点
 *	对于中序线索二叉树上的任一结点，寻找其中序的后继结点，有以下两种情况：
 *		（1）如果该结点的右标志为1，那么其右指针域所指向的结点便是它的后继结点；
 *		（2）如果该结点的右标志为0，表明该结点有右孩子，根据中序遍历的定义，它的前驱结点是以该结点的右孩子为根结点的子树的最左结点，
 *			即沿着其右子树的左指针链向下查找，当某结点的左标志为1 时，它就是所要找的后继结点。
 *
 *	三、在中序线索二叉树上查找任意结点在先序下的后继
 *		这一操作的实现依据是：若一个结点是某子树在中序下的最后一个结点，则它必是该子树在先序下的最后一个结点。该结论可以用反证法证明。
 *		下面就依据这一结论，讨论在中序线索二叉树上查找某结点在先序下后继结点的情况。设开始时，指向此某结点的指针为p。
 *			（1）若待确定先序后继的结点为分支结点，则又有两种情况：
 *					① 当p->ltag=0 时，p->lchild 为p 在先序下的后继；
 *					② 当p->ltag=1 时，p->rchild 为p 在先序下的后继。
 *			（2）若待确定先序后继的结点为叶子结点，则也有两种情况：
 *					① 若p->rchild 是头结点，则遍历结束；
 *					② 若p->rchild 不是头结点，则p 结点一定是以p->rchild 结点为根的左子树中在中序遍历下的最后一个结点，
 *		因此p 结点也是在该子树中按先序遍历的最后一个结点。此时， 若p->rchild 结点有右子树， 
 *		则所找结点在先序下的后继结点的地址为p->rchild->rchild；若p->rchild 为线索，则让p＝p->rchild，反复情况（2）的判定。	
 *
 *	后序线索化需要在结点的结构体中增加 parent 属性，用来标记当前结点是不是父节点
 *	并且在线索化之前，整个二叉树就得带上 parent	
 *  			 1
 *			   /   \
 *			  3     6
 *			 / \   / \
 *			8  10 14  16
 *	为什么要增加 parent 属性呢？
 *	先看后序遍历 -> 8, 10, 3, 14, 6, 16, 1
 *	
 *	线索化之后，就是将：
 *		8号节点的left设为前驱结点，指向null
 *		8号节点的right设为后继结点，指向10号结点
 *
 *		10号节点的right设为后继结点，指向3号（父节点）结点
 *		
 *		3号节点的right设为后继结点，指向14号结点
 *		注意：
 *			3号结点的right原本是有值的，并且指向10号节点
 *			同理，6号结点也存在这样的问题，6号的right 需要指向 6 号的父节点1号。
 *		问题：
 *			如何判断10号是需要重改right指向呢？
 *		解决方案：
 *			每次遍历到当前结点，它本身就知道自己是父结点（至少有left或者right），那么直接将它的right指向带着parent结点的left结点即可。
 *			当前结点是叶子结点的时候，当自己的right为null，那么就指回自己的父结点，因为本身保存了parent，所以很容易指回去。
 *		
 *
 */
public class ThreadedBinaryTreeDemo {
	
	public static void main(String[] args) {
		
		// 测试前序线索化
//		testPreThreaded();
		
		// 测试中序线索化
//		testInfixThreaded();

		// 测试后序线索化
		testPostThreaded();
		
	}
	
	public static void testPostThreaded() {
		ThreadedBinaryTree threadedBinaryTree = createThreadedBinaryTreeWithParent();
		// 后序遍历
		threadedBinaryTree.postOrder();
		// 后序遍历线索化
		threadedBinaryTree.postThreaded();
		// 后序线索化的遍历 -> 8, 10, 3, 14, 6, 1
		threadedBinaryTree.postThreadedList();
	}
	
	/**
	 * 测试前序线索化遍历
	 */
	public static void testPreThreaded() {
		ThreadedBinaryTree threadedBinaryTree = createThreadedBinaryTreeWithoutParent();
		// 前序遍历
		threadedBinaryTree.preOrder();
		// 前序遍历线索化
		threadedBinaryTree.preThreaded();
		// 前序线索化的遍历 -> 1, 3, 8, 10, 6, 14
		threadedBinaryTree.preThreadedList();
		return;
	}
	
	/**
	 * 测试中序线索化遍历
	 */
	public static void testInfixThreaded() {
		ThreadedBinaryTree threadedBinaryTree = createThreadedBinaryTreeWithoutParent();
		// 中序遍历
		threadedBinaryTree.infixOrder();
		// 中序遍历线索化
		threadedBinaryTree.inifxThreaded();
		// 中序线索化的遍历 -> 8, 3, 10, 1, 14, 6
		threadedBinaryTree.inifxThreadedList();
		return;
	}
	
	
	/**
	 * 	创建二叉树不带parent标识
	 *			  1
	 *		    /   \
	 *		   3     6
	 *		  / \   / 
	 *		 8  10 14
	 * @return
	 */
	public static ThreadedBinaryTree createThreadedBinaryTreeWithoutParent() {
		BoyTreeNode root = new BoyTreeNode(1, "jack");
		BoyTreeNode node3 = new BoyTreeNode(3, "rose");
		BoyTreeNode node6 = new BoyTreeNode(6, "tim");
		BoyTreeNode node8 = new BoyTreeNode(8, "kitty");
		BoyTreeNode node10 = new BoyTreeNode(10, "king");
		BoyTreeNode node14 = new BoyTreeNode(14, "tony");
		
		node3.setLeft(node8);
		node3.setRight(node10);
		
		node6.setLeft(node14);

		root.setLeft(node3);
		root.setRight(node6);
		
		ThreadedBinaryTree threadedBinaryTree = new ThreadedBinaryTree(root);
		return threadedBinaryTree;
	}
	
	/**
	 * 	创建二叉树带parent标识
	 *			  1
	 *		    /   \
	 *		   3     6
	 *		  / \   / \
	 *		 8  10 14  16
	 *				\
	 *				18
	 * @return
	 */
	public static ThreadedBinaryTree createThreadedBinaryTreeWithParent() {
		BoyTreeNode root = new BoyTreeNode(1, "jack");
		BoyTreeNode node3 = new BoyTreeNode(3, "rose");
		BoyTreeNode node6 = new BoyTreeNode(6, "tim");
		BoyTreeNode node8 = new BoyTreeNode(8, "kitty");
		BoyTreeNode node10 = new BoyTreeNode(10, "king");
		BoyTreeNode node14 = new BoyTreeNode(14, "tony");
		BoyTreeNode node16 = new BoyTreeNode(16, "sam");
		BoyTreeNode node18 = new BoyTreeNode(18, "xxx");
		
		node3.setLeft(node8);
		node3.setRight(node10);
		
		node6.setLeft(node14);
		node6.setRight(node16);

		node14.setRight(node18);
		
		root.setLeft(node3);
		root.setRight(node6);
		
		node3.setParent(root);
		node6.setParent(root);
		
		node8.setParent(node3);
		node10.setParent(node3);
		
		node14.setParent(node6);
		node16.setParent(node6);
		node18.setParent(node14);
		
		ThreadedBinaryTree threadedBinaryTree = new ThreadedBinaryTree(root);
		return threadedBinaryTree;
	}
}


class ThreadedBinaryTree {

	private BoyTreeNode root;
	
	// 用于标记当前节点的父节点
	private BoyTreeNode pre = null;
	
	public ThreadedBinaryTree(BoyTreeNode root) {
		this.root = root;
	}

	/**
	 * 	中序线索化
	 */
	public void inifxThreaded() {
		inifxThreaded(root);
	}
	
	/**
	 * 	前序线索化
	 */
	public void preThreaded() {
		preThreaded(root);
	}
	
	/**
	 * 	后序线索化
	 */
	public void postThreaded() {
		postThreaded(root);
	}
	
	/**
	 * 	线索化的后序遍历
	 */
	public void postThreadedList() {
		BoyTreeNode node = root;

		/**
		 * 	先找到最开始遍历的节点, root 开始从左节点开始一直找，
		 * 	第一个node.getLeftType() == 1的节点就是第一个要遍历的节点
		 */
		while(node.getLeftType() == 0) {
			node = node.getLeft();
		}
		
		BoyTreeNode preNode = null;
		while(node != null) {
			/**
			 *	先打印出自己,
			 *	再遍历右指针指向的结点
			 */
			if(node.getRightType() == 1) {
				System.out.println(node);
				preNode = node;
				node = node.getRight();
			} else {
				/**
				 * 	node.getRight() == preNode
				 * 	表示当前结点是父节点，它的子节点指回了自己，
				 * 	把自己打印出来再记录到临时变量里
				 */
                if(node.getRight() == preNode) { //如果上个处理的节点是当前节点的右节点
                	System.out.println(node);
                    preNode = node;
                    // 当node是 root的时候，node.getParent()==null，直接结束循环
                    node = node.getParent();
                } else { //如果从左节点的进入则找到右子树的最左节点
                    node = node.getRight();
                    while(node.getLeftType() == 0) {
                        node = node.getLeft();
                    }
                }
			}
			
		}
	}
		
	/**
	 * 	线索化的前序遍历
	 */
	public void preThreadedList() {
		/**
		 * 	定义一个变量，存储当前要遍历的节点，从root开始
		 */
		BoyTreeNode node = root;
		while(node != null) {
			while(node.getLeftType() == 0) {
				System.out.println(node);
				node = node.getLeft();
			}
			
			System.out.println(node);
			node = node.getRight();
		}
	}

	/**
	 * 	线索化的中序遍历
	 */
	public void inifxThreadedList() {
		/**
		 * 	定义一个变量，存储当前要遍历的节点，从root开始
		 */
		BoyTreeNode node = root;
		
		while(node != null) {
			// node从root 开始，一直往左找，只要node.getLeftType() == 1，表示这个结点一定是最左边的结点，即第一个要遍历的结点
			while(node.getLeftType() == 0) {
				node = node.getLeft();
			}
			
			// 找到了就打印出来
			System.out.println(node);
			
			// 只要当前要遍历的结点有后驱结点，就一直把这个当前结点的后驱结点遍历打印出来
			while(node.getRightType() == 1) {
				node = node.getRight();
				System.out.println(node);
			}
			
			/**
			 * 	上面遍历结束，进行下一个结点的遍历 
			 * 	当最后一个结点遍历完的时候，其最后一个结点的右子节点一定是 null
			 * 	为什么这么肯定？
			 * 		因为在线索化的时候保证了最后一个结点的右子节点一定是null, 
			 * 		如果不是null 那么它的右子节点指针要么指向下一个真实的结点，要么指向自己的父节点
			 * 
			 */
			node = node.getRight();
		}
	}
	
	/**
	 * 	后序遍历线索化
	 * @param node
	 */
	private void postThreaded(BoyTreeNode node) {
		if(node == null) {
			return;
		}
		
		/**
		 * 	1. 处理线索当前节点的左节点
		 */
		postThreaded(node.getLeft());
		
		/**
		 * 	2. 处理线索当前节点的右节点
		 */
		postThreaded(node.getRight());
		
		/**
		 * 	3.1 处理前驱结点
		 */
		if(node.getLeft() == null) {
			node.setLeft(pre);
			node.setLeftType(1);
		}
		
		/**
		 * 	3.2 处理后继结点
		 */
		if(pre != null && pre.getRight() == null) {
			pre.setRight(node);
			pre.setRightType(1);
		}
		
		pre = node;
	}
	
	/**
	 * 	前序遍历线索化
	 * @param node
	 */
	private void preThreaded(BoyTreeNode node) {
		if(node == null) {
			return;
		}
		
		/**
		 * 	1.1 处理前驱结点
		 */
		if(node.getLeft() == null) {
			node.setLeft(pre);
			node.setLeftType(1);
		}
		
		/**
		 * 	1.2 处理后继结点
		 */
		if(pre != null && pre.getRight() == null) {
			pre.setRight(node);
			pre.setRightType(1);
		}
		
		pre = node;
		
		/**
		 * 	2. 处理线索当前节点的左节点
		 */
		if(node.getLeftType() == 0) {
			preThreaded(node.getLeft());
		}
		
		/**
		 * 	3. 处理线索当前节点的右节点
		 */
		if(node.getRightType() == 0) {
			preThreaded(node.getRight());
		}
	}
	
	/**
	 * 	中序遍历线索化
	 * @param node
	 */
	private void inifxThreaded(BoyTreeNode node) {
		if(node == null) {
			return;
		}
		
		/**
		 * 	1. 线索化左子节点
		 */
		inifxThreaded(node.getLeft());
		
		/**
		 * 	2.1 处理当前节点的前驱节点
		 */
		if(node.getLeft() == null) {
			node.setLeft(pre);
			node.setLeftType(1);
		}
		
		/**
		 * 	2.2 处理当前节点的后续节点
		 * 	思路分析： 当前结点的右结点，就是下一个要遍历结点的前置结点，
		 * 			所以这里只要判断自己的前置结点的右结点是不是空，是空就把自己链接到前置结点的右结点
		 */
		if(pre != null && pre.getRight() == null) {
			pre.setRight(node);
			pre.setRightType(1);
		}
		
		// 让自己成为下一个结点的前置结点
		pre = node;
		
		/**
		 * 	3. 线索化右子节点
		 */
		inifxThreaded(node.getRight());
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

/**
 * 	先搞懂 BinaryTreeDemo.java
 * 	知道如何前序，中序，后序遍历
 * 	此类在 BinaryTreeDemo.java 类的基础之上增加了线索化标识	  
 *
 */
@Data
class BoyTreeNode {
	private Integer id;
	private String name;
	private BoyTreeNode left;
	private BoyTreeNode right;
	
	/**
	 * 	如果 leftType = 0, 表示指向的是左子节点，如果 leftType = 1, 表示指向的是前驱结点
	 * 	如果 rightType = 0, 表示指向的是右子节点，如果 rightType = 1, 表示指向的是后继结点
	 */
	private int leftType = 0;
	private int rightType = 0;
	
	private BoyTreeNode parent; // 父节点的指针（只是在后序线索化过程中使用）
	
	public BoyTreeNode(Integer id, String name) {
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
	public BoyTreeNode preSearch(int id) {
		BoyTreeNode result = null;
		
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
	public BoyTreeNode infixSearch(int id) {
		BoyTreeNode result = null;
		
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
	public BoyTreeNode postSearch(int id) {
		BoyTreeNode result = null;
		
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
		return "BoyTreeNode [id=" + id + ", name=" + name + "]";
	}
}
