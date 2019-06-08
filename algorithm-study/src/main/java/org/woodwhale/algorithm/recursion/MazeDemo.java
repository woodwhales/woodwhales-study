package org.woodwhale.algorithm.recursion;

/**
 *	迷宫
 *
 */
public class MazeDemo {

	public static void main(String[] args) {
		// 创建地图
		int[][] map = createMap(8, 7);
		// 设置障碍
		setWall(map);
		
		// 打印地图
		printMap(map);
		
		// 开始走迷宫
		goWay(map, 1, 1);
		
		printMap(map);
		
	}

	/**
	 * 	开始走迷宫
	 * 	游戏规则，走到(地图的最右下角的地方表示走出迷宫
	 * 	围墙是 1，已经走过的通路是2，走过但未成功走出的路是3
	 * 	找出路的策略：当遇到障碍物时，按照：下->左->上->右的策略依次找出路
	 * @param map 迷宫地图
	 * @param row	起始行位置
	 * @param col	起始列位置
	 */
	public static boolean goWay(int[][] map, int row, int col) {
		// 当最右下角的位置是2 表示走出迷宫
		if(map[map.length-2][map[row].length-2] == 2) {
			return true;
		} else {
			if(map[row][col] == 0) {
				
				map[row][col] = 2; // 假设能走通
				if(goWay(map, row + 1, col)) { //走下
					return true;
				} else if(goWay(map, row, col + 1)) { //走右
					return true;
				} else if(goWay(map, row - 1, col)) { //走上
					return true;
				}  else if(goWay(map, row, col-1)) { //走左
					return true;
				} else { // 上面的策略路线都走了一遍，都没走通，那么一定是走不通的死路
					map[row][col] = 3; // 标记当前路为死路
					return false;
				}
			} else { // 不是0的状态，一定就是 1，2，3，这些路都不能走，因此直接返回false
				return false;
			}
		}
	}

	/**
	 * 	设置障碍
	 * @param map
	 */
	public static void setWall(int[][] map) {
		map[3][1] = 1;
		map[3][2] = 1;
	}

	/**
	 *	创建迷宫地图
	 * @param row 行
	 * @param col 列
	 * @return 返回迷宫（二维数组）
	 */
	public static int[][] createMap(int row, int col) {
		int[][] miGong = new int[row][col];
		
		createWall(miGong);
		return miGong;
	}
	
	/**
	 * 	生成围墙
	 * @param map
	 */
	private static void createWall(int[][] map) {
		for(int row = 0; row < map.length; row++) {
			map[row][0] = 1;
			map[row][map[row].length-1] = 1;
			for(int col = 0; col < map[row].length; col++) {
				map[0][col] = 1;
				map[map.length-1][col] = 1;
			}
		}
	}

	/**
	 * 	打印迷宫地图
	 * @param map
	 */
	public static void printMap(int[][] map) {
		System.out.println("迷宫：");
		for(int row = 0; row < map.length; row++) {
			for(int col = 0; col < map[row].length; col++) {
				System.out.printf("%d ", map[row][col]);
			}
			System.out.println();	
		}
	}
}
