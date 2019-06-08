package org.woodwhale.algorithm.recursion;

/**
 * 	八皇后问题
 * 	
 * 	八皇后问题是一个以国际象棋为背景的问题：
 * 	如何能够在8×8的国际象棋棋盘上放置八个皇后，
 * 	使得任何一个皇后都无法直接吃掉其他的皇后？为了达到此目的，
 * 	任两个皇后都不能处于同一条横行、纵行或斜线上。
 *
 *	设计思路：
 *		设计一个一维数组，长度为8，其下标可以表示棋盘的行数,其值表示棋盘的列数
 *		因此 ：
 *		arr[0] = 3，表示的是 第 1 行的第 （3+1）4 列的位置
 *		arr[1] = 4，表示的是 第 2 行的第 （4+1）5 列的位置
 *		
 *	问题其实等价于：
 *		要每一个位置的元素的值不能相同，每个元素的值的取值范围是0-7
 *		并且
 *		每个元素的所在行数和其他任一元素的所在行数和列数（列数就是元素的值）之差的绝对值必须不能相等
 */
public class EightQueensDemo {

	private int max;
	private int[] arr;
	static int count = 0;
	
	public EightQueensDemo(int max) {
		this.max = max;
		this.arr = new int[max];
	}

	public static void main(String[] args) {
		EightQueensDemo eightQueens = new EightQueensDemo(8);
		eightQueens.check(0);
		System.out.printf("共有%d种解\n", eightQueens.count);
	}
	
	/**
	 * 	
	 * @param n 将皇后放至在第n+1行数
	 */
	public void check(int n) {
		if(n == max) {
			print();
			return;
		}
		
		for(int i = 0; i < max; i++) {
			arr[n] = i;
			if(judge(n)) {
				check(n+1);
			}
		}
	}
	
	/**
	 * 
	 * @param arr 棋盘
	 * @param n 第n个皇后
	 * @return
	 */
	public boolean judge(int n) {
		/**
		 *	保证当前下标的元素与之前的元素之间不存在冲突：
		 *	所在行、列、斜线不能在一条线上
		 *	因为当前元素一定是放置到下一行，所以行问题直接解决了
		 *	因此：
		 *		列冲突的保证：要保证所在列, 即所在位置的元素值不能和之前的元素的值相同
		 *		斜线冲突的保证：当前元素的行数和列数与之前所有元素行数和列数的之差不能一致，（也就是两点坐标的斜率不能为1）
		 */
		for(int i = 0; i < n; i++) {
			/**
			 * 	因为一维数组的下标是行数，其元素值为列数，所以：
			 * 	arr[i] == arr[n] 表示在同一列
			 * 	两者行相减的绝对值和列的相减绝对值相同，就说明是在一条斜线上的，用数学公式表示就是：
			 * 	Math.abs(n-i) == Math.abs(arr[n] - arr[i])
			 */
			if(arr[n] == arr[i] || Math.abs(n-i) == Math.abs(arr[n] - arr[i])) {
				return false;
			}
		}
		return true;
	}
	
	public void print() {
		count++;
		for (int i : arr) {
			System.out.printf("%d ", arr[i]);
		}
		System.out.println();
	}
}
