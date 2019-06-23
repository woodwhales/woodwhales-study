package org.woodwhale.algorithm;

/**
 * 	二分搜索
 * 	
 * 	非递归实现
 *
 */
public class BinarySearch {
	public static void main(String[] args) {
		int[] arr = { 3, 4, 5, 7, 9, 10, 14, 15, 25, 27, 36 };
		
		int target = 23;
		
		int index = binarySearch(arr, target);
		if(index != -1) {
			System.out.printf("找到值为%d所在索引index = %d\n", arr[index], index);
		} else {
			System.out.println("未找到索引");
		}
	}
	
	public static int binarySearch(int[] arr, int target) {
		int left = 0;
		int right = arr.length - 1;
		int mid;
		while(left <= right) {
			mid = left + ((right - left) >>> 1);

			if(target == arr[mid]) {
				return mid;
			}
			
			if(target < arr[mid]) {
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}
		
		return -1;
	}
}
