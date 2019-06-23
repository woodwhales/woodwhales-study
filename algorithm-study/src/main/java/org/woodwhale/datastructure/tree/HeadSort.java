package org.woodwhale.datastructure.tree;

import java.util.Arrays;

/**
 * 堆排序 堆排序是利用堆这种数据结构而设计的一种排序算法， 堆排序是一种选择排序，它的最坏，最好，平均时间复杂度均为O(nlogn)，它也是不稳定排序。
 * 
 * 堆： 堆是具有以下性质的完全二叉树： 每个结点的值都大于或等于其左右孩子结点的值，称为大顶堆；
 * 或者每个结点的值都小于或等于其左右孩子结点的值，称为小顶堆。
 * 
 * 	大顶堆：arr[i] >= arr[2i+1] && arr[i] >= arr[2i+2]
 * 	小顶堆：arr[i] <= arr[2i+1] && arr[i] <= arr[2i+2]
 *
 *
 *	[3, 4, 6, 8, 9, 0, 5]
 *
 *	                         arr[0]=3
 *							/    	 \
 *					 arr[1]=4   	 arr[2]=6
 *					 /     \          /       \
 *		       arr[3]=8   arr[4]=9 arr[5]=0  arr[6]=5
 *
 *	当前索引是index，那么当前结点是arr[index]
 *	左子节点：arr[2 * index + 1]
 *	右子节点：arr[2 * index + 2]
 *	父结点：arr[(index-1)/2]
 *
 */
public class HeadSort {

	public static void main(String[] args) {

		int[] arr = { 3, 4, 6, 8, 9, 0, 5, -1, 10 };

		headSort(arr);
	}

	public static void headSort(int[] arr) {

		/**
		 * 	先将数组遍历成大顶堆
		 */
		buildHeapSort(arr);

		/**
		 * 	将上面排序好的大顶堆，此时arr[0]一定是根节点，且数值最大，
		 * 	将arr[0]和arr[num] 进行数值交换
		 * 	num 表示当前要遍历节点的个数
		 */
		for (int num = arr.length - 1; num > 0; num--) {
			swap(arr, num, 0);
			/**
			 * 	交换之后，需要重新遍历得到新的大顶堆，
			 * 	此时第num索引对应的结点不需要在堆中了，
			 * 	第一次就长度正好是arr.length-1
			 * 	以后每次都是在上一次基础上减1，
			 * 	直接传 num 进去即可
			 */
			heapify(arr, 0, num);
		}

		System.out.println(Arrays.toString(arr));
	}

	/**
	 * 	将整个原数组，从最后一个结点的父结点开始，一直调整，
	 * 	就能把数组中最大的数调整到树顶
	 * @param arr
	 */
	private static void buildHeapSort(int[] arr) {
		// 最后一个结点的索引是 arr.length - 1
		int lastNode = arr.length - 1;
		// 父结点是 (当前结点索引-1/2) = 当前结点索引/2 - 1
		int parent = (lastNode - 1) / 2;
		// 父结点之后索引值一直减小，就是从左到右，从下往上调整
		for (int i = parent; i >= 0; i--) {
			heapify(arr, i, arr.length);
		}
	}

	/**
	 * 	完成以index为下标对应的非叶子节点的树，调整成大顶堆
	 * 	节点在arr[index]之后的节点进行顶堆调整
	 * 
	 * @param arr    待调整的数组
	 * @param index  非叶子节点在数组中的下标
	 * @param length 表示对多少个元素进行调整
	 */
	private static void heapify(int[] arr, int index, int length) {

		// 递归出口
		if (index >= length) {
			return;
		}

		/**
		 * 在当前节点和其子节点进行比较，谁最大谁就是当前节点
		 */
		int max = index;
		int left = 2 * index + 1;
		int right = 2 * index + 2;

		if (left < length && arr[left] > arr[max]) {
			max = left;
		}

		if (right < length && arr[right] > arr[max]) {
			max = right;
		}

		/**
		 * max != index 说明index 结点值小，交换值
		 * 交换之后，index 是最大值了，但是max 不一定是它的子树中最大的值，所以要递归调整
		 */
		if (max != index) {
			// 将大值调整到当前index结点的位置
			swap(arr, index, max);
			// 对下一层的节点进行交换
			heapify(arr, max, length);
		}
	}

	private static void swap(int[] arr, int i, int j) {
		int temp = arr[j];
		arr[j] = arr[i];
		arr[i] = temp;
	}
}
