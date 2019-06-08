package org.woodwhale.algorithm.sort;

import java.util.Arrays;

/**
 * 	插入排序
 *
 */
public class InsertSortDemo {

	public static void main(String[] args) {
		int[] arr = {-1, 2, 4, 6, 3, 1, -3, 9};
		insertSort(arr);
		System.out.println(Arrays.toString(arr));
	}
	
	public static void insertSort(int[] arr) {
		/**
		 * 变量放外面省去来回创建变量，减少性能消耗
		 */
		int insertValue = 0;
		int insertIndex = 0;
		// 从1 之后的数据都是无序的，每次从无序的数据集合里选一个出来，和前面的所有元素进行比较
		for (int i = 1;i < arr.length; i++) {
			insertValue = arr[i];
			
			insertIndex = i-1;
			// 依次看前面有序的每一个元素是不是比当前要插入的数大，
			while(insertIndex >= 0 && arr[insertIndex] > insertValue) {
				// 
				arr[insertIndex + 1] = arr[insertIndex];
				// 注意减减，元素会往前比较，越往前元素值越小
				insertIndex--;
			}
			// insertIndex 无论上面循环执行不执行，要把临时保存的 insertValue 插到 insertIndex下标之后的一个位置里
			// 上面循环执行完毕，insertIndex 减了一次，所以需要再回后面一个元素位置，把要插入的值插进去
			// 加了条件判断，省去当前插入值位置就是当前位置，再赋值一次的操作，但是多了判断操作，对时间复杂度影响不大
			if((insertIndex + 1) != i) {
				arr[insertIndex + 1] = insertValue;
			}
		}
	}

}
