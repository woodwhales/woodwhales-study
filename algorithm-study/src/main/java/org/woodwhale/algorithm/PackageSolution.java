package org.woodwhale.algorithm;

/**
 * 	背包问题
 * 	有一个背包，容量为4磅，现右如下物品：
 * 	
 * 	物品    重量    价格
 * 	吉他	 1	 1500
 *	音响	 4   3000
 *	电脑	 3   2000
 *
 *	1. 要求达到的目标为装入的背包的总价值最大，并且重量不超出
 *	2. 要求转入的物品不能重复
 *
 */
public class PackageSolution {
	public static void main(String[] args) {
		dynamic();
	}

	public static void dynamic() {

		int[] w = { 1, 4, 3 };
		int[] val = { 1500, 3000, 2000 };
		
		int m = 4; // 最大重量限制
		int n = val.length; // 物品的个数
		
		// v[i][j] 表示在前i个物品中能装入容量为j的背包中的最大价值
		int[][] v = new int[n + 1][m + 1];
		int[][] path = new int[n + 1][m + 1];
		init(v);
		
		for (int i = 1; i < v.length; i++) {
			for (int j = 1; j < v[0].length; j++) {
				if(w[i-1] > j) {
					v[i][j] = v[i-1][j];
				} else {
					/**
					 * 	就是在前一个子问题中和当前子问题中选择最大的值
					 * 	v[i][j] = Math.max(v[i-1][j], val[i-1]+v[i-1][j-w[i-1]]);
					 */
					if(v[i-1][j] < val[i-1]+v[i-1][j-w[i-1]]) {
						v[i][j] = val[i-1]+v[i-1][j-w[i-1]];
						// 记录选择的结果
						path[i][j] = 1;
					} else {
						v[i][j] = v[i-1][j];
					}
				}
			}
		}
		
		print(v);
		
		// 将最优选择过程打印出来
		int i = path.length-1;
		int j = path[0].length -1;
		
		while(i > 0 && j > 0) {
			if(path[i][j] == 1) {
				System.out.printf("第%d个商品放入到背包\n",i);
				j-=w[i-1];
			}
			i--;
		}
	}

	private static void print(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				System.out.print(arr[i][j] + "\t"); 
			}
			System.out.println();
		}
	}

	private static void init(int[][] v) {
		// 第一列置零
		for (int i = 0; i < v.length; i++) {
			v[i][0] = 0;
		}
		
		// 第一行置零
		for (int i = 0; i < v[0].length; i++) {
			v[0][i] = 0;
		}
	}
}
