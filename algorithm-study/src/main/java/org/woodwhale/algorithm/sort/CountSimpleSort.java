package org.woodwhale.algorithm.sort;

import java.util.Arrays;

/**
 *	 计数排序初版（不推荐，这种写法[不稳定] ）
 *
 */
public class CountSimpleSort {

	public static void main(String[] args) {
		int[] arr = { 12, 19, 13, 18, 11, 14, 16, 15, 17, 15, 16, 14, 11, 13, 12, 18, 17, 19, 12, 13, 15, 14, 11, 16, 10 };
		
		int max = findMax(arr);
		int min = findMin(arr);
		
		sort(arr, min, max);
		print(arr);
	}

	public static void sort(int[] arr, int min, int max) {

		// 由于整个数组的所有取值范围是 min-max，所以使用长度为 max - min + 1 的数组记录所有取值范围内数字的出现次数
		int[] count = new int[max - min + 1];

		for (int i = 0; i < arr.length; i++) {
			count[(arr[i] - min)]++;
		}

		for (int i = 0, j = 0; i < count.length; i++) {
			while(count[i] > 0) {
				arr[j++] = (min + i);
				count[i]--;
			}
		}
	}

	public static void print(int[] arr) {
		System.out.println(Arrays.toString(arr));
	}

	public static int findMax(int[] arr) {
		int max = arr[0];

		for (int data : arr) {
			if (data > max) {
				max = data;
			}
		}

		return max;
	}
	
	public static int findMin(int[] arr) {
		int min = arr[0];

		for (int data : arr) {
			if (data < min) {
				min = data;
			}
		}

		return min;
	}

}
