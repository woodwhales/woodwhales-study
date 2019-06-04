package org.woodwhale.algorithm.sort;

public class BubbleSortDemo {

	public static void main(String[] args) {
		int [] array = {3, 4, 1, 8, 9, 5, 2, 6, 7};
		
		System.out.println("superBubbleSort ( good )");
		superBubbleSort(array);
		
		int [] arrays = {3, 4, 1, 8, 9, 5, 2, 6, 7};
		System.out.println("bubbleSort ( bad )");
		bubbleSort(arrays);
	}
	
	/**
	 * 	冒泡排序
	 * 	外层循环控制排序趟数，每次要找出最大值，因此至少跑 length-1 趟
	 *  内层循环控制每一趟排序多少次，也就是剩余未确定数字俩俩比较，大的在前，小的在后
	 *  
	 *  当遍历一遍没有数据交换的时候，表示所有数据已经排序好了，直接中断循环
	 */
	private static void superBubbleSort(int [] array) {
		int length = array.length;
		for(int i = 0; i < length-1; i++ ) {
			boolean flag = false;
			for (int j = 0; j < length-1-i; j++) {
				if(array[j] > array[j+1]) {
					swap(array, j, j+1);
					flag = true;
				}
			}
			if(!flag) {
				break;
			}
			printArray(array);
		}
	}
	
	/**
	 * 	打印数组
	 */
	private static void printArray(int[] array) {
		for (int i : array) {
			System.out.print(i+" ");
		}
		System.out.println();
	}

	/**
	 * 	冒泡排序
	 */
	private static void bubbleSort(int [] array) {
		int length = array.length;
		for(int i = 0; i < length-1; ++i) {
			for (int j = 0; j < length-1-i; ++j) {
				if(array[j] > array[j+1]) {
					swap(array, j, j+1);
				}
			}
			printArray(array);
		}
	}

	/**
	 * 	将两个位置的值进行互换
	 */
	private static void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}
