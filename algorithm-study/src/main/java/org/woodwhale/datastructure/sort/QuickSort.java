package org.woodwhale.datastructure.sort;

import java.util.Arrays;

/**
 *	快速排序
 *
 */
public class QuickSort {

	public static void main(String[] args) {
		int[] arr = {4, 3, 6, 7, 5, 8, 9, 1, 2};

		quickSort(arr, 0, arr.length - 1);
		System.out.println(Arrays.toString(arr));
	}
	
	public static void quickSort(int[] arr, int left, int right) {
		if(left > right) {
			return;
		}
		
		int l = left; 
		int r = right;
		int base = arr[left];
		// 只要两者不相遇，就一直比下去
		while(l != r) {
			// 因为基本元素在左边，因此要先从右边开始移动
			while(arr[r] >= base && r > l ) {
				r--;
			}
			
			while(arr[l] <= base && r > l) {
				l++;
			}
			
			// 两个循环能出来，就是因为本来在左边的元素却在右边，本来在右边的元素却在左边，所以要位置互换
			swap(arr, l, r);
		}
		
		// 此时 l和r 一定相等
		// 将 base与中间计算出的位置进行元素互换
		arr[left] = arr[l];
		arr[l] = base;
		
		// 先左边递归遍历一次
		quickSort(arr, left, l-1);
		// 再右边递归遍历一次
		quickSort(arr, r+1, right);
		
	}
	
	private static void swap(int[] arr, int high, int low) {
		int temp = arr[high];
		arr[high] = arr[low];
		arr[low] = temp;
	}

}
