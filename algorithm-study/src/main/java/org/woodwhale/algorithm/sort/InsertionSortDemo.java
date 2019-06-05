package org.woodwhale.algorithm.sort;

/**
 * 插入排序
 *
 */
public class InsertionSortDemo {

	public static void main(String[] args) {
		int[] array = {5, 7, 4, 2, 6, 3, 1, 9, 8}; 
		insertionSort(array);
		printArray(array);
	}

	/**
	 * 	插入排序
	 */
    public static void insertionSort(int[] arr) {
    	
    	int length = arr.length;
        if (length <= 1) return;

        for (int i = 1; i < length; ++i) {
            int value = arr[i];
            
            int j = i - 1;
            // 查找要插入的位置并移动数据
            for (; j >= 0; --j) {
                if (arr[j] > value) {
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }
            }
            arr[j + 1] = value;
        }
    }

	/**
	 * 	遍历打印数组
	 */
	private static void printArray(int[] array) {
		for (int data : array) {
			System.out.printf("%d ", data);
		}
		System.out.println();
	}
}
