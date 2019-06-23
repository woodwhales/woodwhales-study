package org.woodwhale.datastructure.sort;

import java.util.Arrays;

/**
 * 桶排序
 *
 */
public class BucketSort {

	public static void main(String[] args) {
		int[] arr = { 90, 81, 73, 65, 27, 28, 19, 32, 24, 16 };
		sort(arr);
		print(arr);
	}

	public static void sort(int[] arr) {
		bucketSort(arr);
	}

	private static void bucketSort(int[] arr) {

		int[] result = new int[arr.length];
		
		// 找到最大值
		int findMax = findMax(arr);
		// 计算最大值的位数
		int maxLength = (""+findMax).length();
		
		/**
		 * 	桶子数一定是10个，因为不管数字的哪位位数都是0-9，
		 * 	所以 count[] 的下标就是位数，元素值就是当前下标对应位数的数字的数量
		 */
		int[] count = new int[10];

		// 以最大数字的位数为准，依次遍历每一遍数组，分别将对应的个位、十位…都放到对应的桶里，每一次放完，就遍历桶里记录的顺序，依次再复制回原数组
		for (int i = 0; i < maxLength; i++) {
			int divsion = (int) Math.pow(10, i);

			/**
			 * 	遍历所有数，把对应位数所在的桶位置，统计计数
			 * 	count[num] 就是当前位数的数字的个数
			 */
			for (int j = 0; j < arr.length; j++) {
				int num = arr[j] / divsion % 10;
				count[num]++;
			}

			/**
			 * 	累加count[], 找到每一个位数出现在正确顺序的出现最后次数
			 * 	参考:基数排序 RadixSort.java
			 */
			for (int k = 1; k < count.length; k++) {
				count[k] = count[k] + count[k - 1];
			}

			for (int n = arr.length - 1; n >= 0; n--) {
				int num = arr[n] / divsion % 10;
				result[--count[num]] = arr[n];
			}
			
			// 将当前排序好的数组拷贝回原数组
			System.arraycopy(result, 0, arr, 0, result.length);
			// 将桶计数器清零，进行下一次统计
			Arrays.fill(count, 0);
		}
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

	public static void print(int[] arr) {
		System.out.println(Arrays.toString(arr));
	}
}
