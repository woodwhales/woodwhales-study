package org.woodwhale.algorithm.sort;

import java.util.Arrays;

/**
 * 	选择排序
 *
 */
public class SelectSort {

	public static void main(String[] args) {
		int[] arr = {-1, 2, 4, 6, 3, 1};
		selectSort(arr);
		System.out.println(Arrays.toString(arr));
	}
	
	public static void selectSort(int[] arr) {
		
		for(int i = 0; i < arr.length-1; i++) {
			for(int j = i+1; j < arr.length; j++) {
				if(arr[i] > arr[j]) {
					swap(arr, i, j);
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
