package org.woodwhale.algorithm.tree;

/**
 *	顺序存储二叉树
 *	数组：[1, 2, 3, 4, 5, 6, 7] 
 *	
 *							 arr[0]=1
 *							/       \
 *					   arr[1]=2      arr[2]=3
 *					   /     \          /     \
 *				arr[3]=4  arr[4]=5 arr[5]=6  arr[6]=7 
 *
 *	一般index=0，是根节点
 *	arr[index]节点的左子结点是arr[2*index+1]
 *	arr[index]节点的右子结点是arr[2*index+2]
 *	arr[index]节点的父结点是arr[(index-1)/2]
 *
 */
public class ArrayBinaryTreeDemo {
	public static void main(String[] args) {
		int[] arr = {1, 2, 3, 4, 5, 6, 7};
		
		ArrayBinaryTree arrayBinaryTree = new ArrayBinaryTree(arr);
		System.out.println("数组转二叉树 -> 前序遍历");
		arrayBinaryTree.preOrder(0);
	}
}

class ArrayBinaryTree {
	private int[] arr;
	
	public ArrayBinaryTree(int[] arr) {
		this.arr = arr;
	}
	
	private boolean isEmpty() {
		return (arr== null || arr.length == 0);
	}
	
	/**
	 * 	数组转二叉树, 前序遍历
	 * @param index
	 */
	public void preOrder(int index) {
		if(isEmpty()) {
			System.out.println("数组为空，不能遍历");
			return;
		}
		
		System.out.print(arr[index] + " ");

		if((2*index+1) < arr.length) {
			preOrder(2*index+1);
		}
		
		if((2*index+2) < arr.length) {
			preOrder(2*index+2);
		}
		return;
	}
}