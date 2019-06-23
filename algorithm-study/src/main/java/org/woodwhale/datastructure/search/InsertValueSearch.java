package org.woodwhale.datastructure.search;

/**
 * 	插值查找
 * 	对于数据量较大，关键字分布比较均匀的数据，适合插值查找
 *
 */
public class InsertValueSearch {

	public static void main(String[] args) {
		test();
	}
	
	public static void test() {
		int[] arr = {2, 4, 6, 8, 9, 10, 23, 24,56, 78, 82, 90};
		int key = 6;
		
		// 非递归插值查找
		int index = inserValueSearch(arr, 0, arr.length-1, key);
		check(arr, key, index);
		
		// 递归插值查找
		int index2 = inserValueSearch2(arr, 0, arr.length-1, key);
		check(arr, key, index2);
	}

	/**
	 * 	插值查找(非递归)
	 * @param arr
	 * @param i
	 * @param j
	 * @param key
	 * @return
	 */
	private static int inserValueSearch(int[] arr, int left, int right, int key) {
		int index = -1;
		if(left > right || key < arr[0] || key > arr[arr.length-1]) {
			return index;
		}

		int midVal;
		int mid = 0;
		while(right >= left && key >= arr[left] && key <= arr[right]) {
			mid = left + (right-left)*(key-arr[left])/(arr[right]-arr[left]);
			midVal = arr[mid];
			if(key > midVal) {
				left = mid + 1;
			} else if(key < midVal) {
				right = mid - 1;
			} else {
				index = mid;
				break;
			}
		}
		
		return index;
		
	}

	/**
	 * 	插值查找(递归)
	 * @param arr
	 * @param left
	 * @param right
	 * @param key
	 * @return
	 */
	private static int inserValueSearch2(int[] arr, int left, int right, int key) {
		int index = -1;
		if(left > right || key < arr[0] || key > arr[arr.length-1]) {
			return index;
		}
		
		/**
		 * 二分查找中关键的一行代码，是mid = (low + high) / 2， 转变一下就是mid = low + (high - low)/2,   
		 * （high-low）后面乘的这1/2就是二分查找每次查找的位置。
		 * 
		 * 要实现插值查找， 
		 * 只要把这里的1/2替换成我们所预测的关键字的位置占数组总长度的比例就可以了。
		 */
		int mid = left + (right-left)*(key-arr[left])/(arr[right]-arr[left]);
		int midVal = arr[mid];
		
		if(key > midVal) {
			left = mid + 1;
			return inserValueSearch(arr, left, right, key);
		} else if(key < midVal) {
			right = mid -1;
			return inserValueSearch(arr, left, right, key);
		} else {
			index = mid;
			return index;
		}
	}

	private static void check(int[] arr, int key, int index) {
		if (-1 == index) {
			System.out.printf("%d 在原数组中未找到\n", key);
		} else {
			System.out.printf("arr[%d]=%d\n", index, arr[index]);
		}
	}

}
