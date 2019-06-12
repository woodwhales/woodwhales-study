package org.woodwhale.algorithm.tree;

import java.util.Arrays;

/**
 * 堆排序 堆排序是利用堆这种数据结构而设计的一种排序算法， 堆排序是一种选择排序，它的最坏，最好，平均时间复杂度均为O(nlogn)，它也是不稳定排序。
 * 
 * 堆： 堆是具有以下性质的完全二叉树： 每个结点的值都大于或等于其左右孩子结点的值，称为大顶堆；
 * 或者每个结点的值都小于或等于其左右孩子结点的值，称为小顶堆。
 * 
 * 大顶堆：arr[i] >= arr[2i+1] && arr[i] >= arr[2i+2] 小顶堆：arr[i] <= arr[2i+1] &&
 * arr[i] <= arr[2i+2]
 *
 */
public class HeadSort {

	public static void main(String[] args) {

		int[] arr = { 4, 6, 8, 5, 9 };

		headSort(arr);
	}

	public static void headSort(int[] arr) {

		/**
		 * 	先将数组遍历成大顶堆
		 */
		buildHeapSort(arr);

		/**
		 * 	将上面排序号的大顶堆，此时arr[0]一定是根节点，且数值最大，
		 * 	将arr[0]和arr[num] 进行数值交换
		 * 	num 表示当前要遍历节点的个数
		 */
		for (int num = arr.length - 1; num > 0; num--) {
			swap(arr, num, 0);
			// 交换之后，需要重新遍历得到新的大顶堆
			heapify(arr, 0, num);
		}

		System.out.println(Arrays.toString(arr));
	}

	private static void buildHeapSort(int[] arr) {
		int lastNode = arr.length - 1;
		int parent = (lastNode - 1) / 2;

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

		if (max != index) {
			swap(arr, index, max);
			// 对下一层的节点进行交换
			heapify(arr, left, max);
		}
	}

	private static void swap(int[] arr, int i, int j) {
		int temp = arr[j];
		arr[j] = arr[i];
		arr[i] = temp;
	}
}
