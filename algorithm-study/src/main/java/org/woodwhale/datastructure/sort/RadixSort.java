package org.woodwhale.datastructure.sort;

import java.util.Arrays;

/**
 * 	基数排序
 * 	适用条件：数据量比较大，数据存在一定的范围之间，
 * 	特点：不需要比较就能排序
 * 
 * 	先参考计数排序 CountingSort.java 例子，明白什么是最基本的计数排序
 *
 */
public class RadixSort {

	public static void main(String[] args) {
		int[] arr = { 12, 19, 13, 18, 11, 14, 16, 15, 17, 15, 16, 14, 11, 13, 12, 18, 17, 19, 12, 13, 15, 14, 11, 16, 10 };
		
		int max = findMax(arr);
		int min = findMin(arr);
		
		sort(arr, min, max);
		print(arr);
	}
	
	public static void sort(int[] arr, int min, int max) {

		int[] count = new int[max - min + 1];

		for (int i = 0; i < arr.length; i++) {
			count[(arr[i] - min)]++;
		}

		/**
		 * 	由于count 统计了所有取值范围数值出现的个数并不能知道数据的原始顺序（数据稳定性）
		 * 	所以可以进行累加，计算每个值出现的最后一次位置
		 * 
		 * 	count = [3, 2, 1, 3, 2, 1]
		 * 
		 * 	表示数字 0 出现了 3次，
		 * 	表示数字 1 出现了 2次，
		 * 	表示数字 2 出现了 1次，
		 * 	表示数字 3 出现了 3次，
		 *	表示数字 4 出现了 2次，
		 *	表示数字 5 出现了 1次，
		 *
		 *	{0, 0 ,0, 1, 1, 2, 3, ..., 5}
		 *
		 *	数字最后一个0的位置是 2，
		 *	数字最后一个1的位置是 4
		 *	
		 *	因此将 count 前一个和前一个累加一下：
		 *	count = [3, 2, 1, 3, 2, 1]
		 *	累加之后：
		 *	[3, 5, 6, 9, 11, 12]
		 *	
		 *	此时，
		 *	3表示0最后一次出现在第3个位置，
		 *	5表示1最后一次出现在第4个位置，
		 *	6表示2最后一次出现在第5个位置，
		 *	……
		 *	12表示5最后一次出现在第11个位置
		 */
		for(int i = 1; i < count.length ; i++) {
			count[i] = count[i] + count[i-1]; 
		}
		
		int[] result = new int[arr.length]; 
		for(int i = arr.length - 1; i >= 0; i--) {
			/**
			 * 	arr[i] 表示原数组中的每一个值
			 * 	count[arr[i]-min] 该值最后一次出现位置+1, 所以要先减减
			 * 	count[arr[i]-min] 减减的同时，该值最后一次出现位置要往前移动，所以要减减
			 */
			result[--count[arr[i]-min]] = arr[i];
		}
		
		System.arraycopy(result, 0, arr, 0, result.length);
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
