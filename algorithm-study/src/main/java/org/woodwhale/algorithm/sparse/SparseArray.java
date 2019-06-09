package org.woodwhale.algorithm.sparse;

/**
 *	稀疏数组与原二维数组的互转
 *
 */
public class SparseArray {

	public static void main(String[] args) {
		// 1. 生成棋盘
		int[][] array = gennerateArray(11);
		
		// 2. 设置棋子
		array[1][2] = 1;
		array[2][3] = 2;
		
		// 3. 查看棋盘
		printArray(array);
		
		// 4. 获取棋盘中的棋子个数
		int sum = getSum(array);
		
		// 5. 将棋盘压缩成稀疏数组
		int[][] sparseArray = compressToSparseArray(array, sum);
		
		// 6. 查看稀疏数组
		printArray(sparseArray);
		
		// 6. 将稀疏数组恢复成原棋盘
		int[][] array2 = restoreArray(sparseArray);
		
		// 7. 查看稀疏数组恢复后的棋盘
		printArray(array2);
	}

	/**
	 * 将稀疏数组恢复成原数组
	 */
	private static int[][] restoreArray(int[][] sparseArray) {
		int rowSize = sparseArray[0][0];
		int colSize = sparseArray[0][1];
		int[][] array = new int[rowSize][colSize];
		
		int sum = sparseArray[0][2];
		
		for(int i = 1; i<= sum; i++) {
			int row = sparseArray[i][0];
			int col = sparseArray[i][1];
			int data = sparseArray[i][2];
			array[row][col] = data;
		}
		
		return array;
	}

	/**
	 * 遍历棋盘，统计棋子个数
	 */
	private static int getSum(int[][] array) {
		int sum = 0;

		for (int[] row : array) {
			for (int data : row) {
				if (data != 0) {
					sum++;
				}
			}
		}

		return sum;
	}

	/**
	 * 压缩成稀疏数组
	 */
	private static int[][] compressToSparseArray(int[][] array, int sum) {
		int[][] sparseArray = new int[sum + 1][3];

		// 设置稀疏数组的第一行
		sparseArray[0][0] = array.length;
		sparseArray[0][1] = array.length;
		sparseArray[0][2] = sum;

		int count = 0;
		for (int row = 0; row < array.length; row++) {
			for (int col = 0; col < array.length; col++) {
				if (array[row][col] != 0) {
					count++;
					sparseArray[count][0] = row;
					sparseArray[count][1] = col;
					sparseArray[count][2] = array[row][col];
				}
			}
		}

		return sparseArray;
	}

	/**
	 * 产生原棋盘
	 */
	private static int[][] gennerateArray(int size) {
		int[][] array = new int[size][size];
		return array;
	}

	/**
	 * 遍历数组
	 */
	private static void printArray(int[][] array) {
		for (int[] row : array) {
			for (int data : row) {
				System.out.printf("%d ", data);
			}
			System.out.println();
		}
		System.out.println();
	}
}