package org.woodwhale.datastructure.search;

import java.util.ArrayList;
import java.util.List;

/**
 * 	二分查找法
 * 
 * 	关键字分布不均匀，折半比插值好一些
 * 
 * 	在计算机科学中，
 * 	二分搜索（英语：binary search），
 * 	也称折半搜索（英语：half-interval search）、
 * 	对数搜索（英语：logarithmic search），
 * 	是一种在有序数组中查找某一特定元素的搜索算法。
 * 
 * 	注意：数组一定是有序的！
 *
 */
public class BinarySearch {

	public static void main(String[] args) {
		testSearchAllKey();
		testSearch2();
		testSearch();
	}
	
	public static void testSearchAllKey() {
		int[] arr = { 1, 1, 23, 25, 34, 37, 45, 45, 76, 89, 92, 99, 99, 123, 123, 123 }; 
		
		int key = 45;
		List<Integer> indexList = searchAllKey(arr, key);
		check(arr, key, indexList);
	}
	
	/**
	 * 	将所有重复的值下标都找出来(非递归)
	 * @param arr
	 * @param key
	 * @return
	 */
	public static List<Integer> searchAllKey(int[] arr, int key) {
		
		List<Integer> indexList = new ArrayList<>();
		int left = 0;
		int right = arr.length-1;
		
		while (left <= right) {
			int mid = (left+right) >>> 1;
			int midVal = arr[mid];
			if(key < midVal) {
				right = mid - 1;
			} else if(key > midVal) {
				left = mid + 1;
			} else {
				// 能到这里，一定要么mid的左边连续区域有重复值，要么mid的右边连续区域有重复值
				int temp = mid - 1;
				// 从mid开始先找左边，直到和搜索值不相等或者到最头
				while(true) {
					if(temp < 0 || arr[temp] != midVal ) {
						break;
					}
					indexList.add(temp--);
				}

				// mid直接加到结果集合
				indexList.add(mid);
				
				// 从mid开始先找右边，直到和搜索值不相等或者到最尾
				temp = mid + 1;
				while(true) {
					if(temp > arr.length-1 || arr[temp] != midVal ) {
						break;
					}
					indexList.add(temp++);
				}
				// 找到了就要跳出循环
				return indexList;
			}
		}
		return indexList;
	}

	private static void check(int[] arr, int key, List<Integer> indexList) {
		if(indexList.isEmpty()) {
			System.out.printf("%d 在原数组中未找到\n", key);
		} else {
			for (Integer index : indexList) {
				System.out.printf("arr[%d]=%d\n", index, arr[index]);
			}
		}
	}

	/**
	 * 测试第二种写法
	 */
	public static void testSearch2() {
		int[] arr = { 1, 23, 25, 34, 37, 45, 76, 89, 92, 99 };
		
		int key = 45;
		int index = search2(arr, key);
		check(arr, key, index);
	}
	
	/**
	 * 测试第一种写法
	 */
	public static void testSearch() {
		int[] arr = { 1, 23, 25, 34, 37, 45, 76, 89, 92, 99 }; 
		
		int key = 176;
		int index = search(arr, key);
		check(arr, key, index);
	}
	
	private static void check(int[] arr, int key, int index) {
		if(-1 == index) {
			System.out.printf("%d 在原数组中未找到\n", key);
		} else {
			System.out.printf("arr[%d]=%d\n", index, arr[index]);
		}
	}
	
	public static int search2(int[] arr, int key) {
		return binarySearch2(arr, 0, arr.length-1, key);
	}
	
	
	/**
	 * 	推荐，非递归，模仿了 Arrays.binarySearch(a, key);
	 * @param arr
	 * @param left
	 * @param right
	 * @param key
	 * @return
	 */
	private static int binarySearch2(int[] arr, int left, int right, int key) {
		int index = -1;
		
		int mid = 0;
		while(left <= right) {
			mid = left + right >>> 1;
			
			if(key < arr[mid]) {
				right = mid-1;
			} else if(key > arr[mid]) {
				left = mid+1;
			} else {
				index = mid;
				break;
			}
		}
		
		return index;
	}

	/**
	 * 	
	 * @param arr 数组
	 * @param key 要找的值
	 * @return 第一个找到的索引，没找到返回-1
	 */
	public static int search(int[] arr, int key) {
		int index = binarySearch(arr, 0, arr.length-1, key);
		return index;
	}

	/**
	 * 	二分查找，递归
	 * @param arr
	 * @param min
	 * @param max
	 * @param key
	 * @return
	 */
	private static int binarySearch(int[] arr, int min, int max, int key) {
		int index = -1;
		if(min > max) {
			return index;
		}
		
		int mid = min + (max-min)/2;
		
		if(key > arr[mid]) {
			return binarySearch(arr, mid+1, max, key);
		} else if(key < arr[mid]) {
			return binarySearch(arr, min, mid-1, key);
		} else {
			return mid;
		}
		
	}
	
}
