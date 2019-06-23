package org.woodwhale.algorithm;

/**
 * 	分治管理
 * 	汉诺塔
 *
 */
public class HanoiTower {

	public static void main(String[] args) {
		hanoiTower(5);
	}
	
	public static void hanoiTower(int num) {
		hanoiTower(num, 'A', 'B', 'C');
	}
	
	/**
	 * 	
	 * @param num
	 * @param a 表示要移动的盘子刚开始的位置
	 * @param b	表示移动盘子要借助的中间位置
	 * @param c 表示盘子最终要移动到的位置
	 */
	private static void hanoiTower(int num, char a, char b, char c) {
		if(num == 1) {
			System.out.println(a + " -> " + c);
			return;
		}
		
		hanoiTower(num-1, a, c, b);
		System.out.println(a + " -> " + c);
		hanoiTower(num-1, b, a, c);
	}
	
}