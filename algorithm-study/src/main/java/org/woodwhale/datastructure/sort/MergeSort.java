package org.woodwhale.datastructure.sort;

import java.util.Arrays;

/**
 *	归并排序
 *
 */
public class MergeSort {

	public static void main(String[] args) {
		int[] arr = {3,4,5,7,1,2,9,8,6};
		
		mergeSort(arr, 0, arr.length-1);
		
		print(arr);
	}
	
	public static void print(int[] arr) {
		System.out.println("归并排序");
		System.out.println(Arrays.toString(arr));
	}

	public static void mergeSort(int[] arr, int lo, int hi) {
		if(lo >= hi) {
			return;
		}
		// 分
		int mid = lo + (hi-lo)/2;
		mergeSort(arr, lo, mid);
		mergeSort(arr, mid+1, hi); 
		
		// 治
		merge(arr, lo, mid, hi);
	}

	private static void merge(int[] arr, int lo, int mid, int hi) {
		int[] temp =  new int[hi-lo+1];
		int i = lo;
		int j = mid + 1;
		int index = 0;
		// 依次将左边的数组和右边数组的数据俩俩比较，谁小谁先放到临时数组中
		while(i <= mid && j <= hi) {
			
//			if(arr[i] <= arr[j]) {
//				temp[index++] = arr[i++];
//			} else {
//				temp[index++] = arr[j++];
//			}
			
			// 对上面代码重构优化
			temp[index++] = arr[i] <= arr[j] ? arr[i++] : arr[j++]; 
		}
		
		while(i <= mid) {
			temp[index++] = arr[i++];
		}
		
		while(j <= hi) {
			temp[index++] = arr[j++];
		}
		
		System.arraycopy(temp, 0, arr, lo, temp.length);
	}
}
