package org.woodwhale.algorithm.sort;

import java.util.Arrays;

/**
 * 	希尔排序
 *
 */
public class ShellSort {

	public static void main(String[] args) {
		int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0, 10, -1, -3, 90, 97, -47}; 
		System.out.println("原数组：" + Arrays.toString(arr));
		superShellSort(arr);
		System.out.println("希尔排序：" + Arrays.toString(arr));
	}
	
	public static void testSuperShellSort() {
		long start = System.currentTimeMillis();
		superShellSort(creatBigArr(80000));
		long end = System.currentTimeMillis();
		System.out.println("cost time = " + (end - start));
	}
	
	public static int[] creatBigArr(int size) {
		int[] arrs = new int[size];
		for (int i = 0; i < size; i++) {
			arrs[i] = (int)(Math.random()*8000000);
		}
		return arrs;
	}

	/**
	 * 希尔排序(交换法,不推荐)
	 * @param arr
	 */
	public static void shellSort(int[] arr) {
		for(int step = arr.length/2; step > 0; step/=2) {
			// 设置步长为 step
			for(int i = step; i < arr.length; i++) {
				//每次俩俩比较,步长至少step
				for(int j = i-step; j >= 0; j-=step) {
					// 当前元素和之前的 step 位置元素比较，
					if(arr[j] > arr[j+step]) {
						swap(arr, j, j+step);
					}
				}
				
			}
		}
	}
	
	/**
	 * 	希尔排序（移位法,推荐）
	 * @param arr
	 */
	public static void superShellSort(int[] arr) {
		for(int step = arr.length/2; step > 0; step/=2) {
			for(int i = step; i < arr.length; i++) {
				int insertIndex = i-step;
				int insertValue = arr[i];
				if(arr[i] < arr[insertIndex]) {
					while(insertIndex >= 0 && insertValue < arr[insertIndex]) {
						// 由于当前正在遍历的元素arr[j] 比要插入的元素大，所以
						arr[insertIndex + step] = arr[insertIndex]; 
						insertIndex-=step;
					}
					arr[insertIndex + step] = insertValue;
				}
			}
		}
	}
	
	private static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
}
