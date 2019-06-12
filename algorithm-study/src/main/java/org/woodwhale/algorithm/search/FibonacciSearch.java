package org.woodwhale.algorithm.search;

import java.util.Arrays;

/**	
 *	斐波那契搜索 / 黄金分割搜索
 *
 *	黄金分割搜索是一种通过不断缩小单调函数的最值的已知范围，从而找到最值的方法。
 *	它的名称源于这个算法保持了间距具有黄金分割特性的三个点。
 *	这个算法与斐波那契搜索和二分查找关系紧密。
 *	黄金分割搜索是由Kiefer提出的，而斐波那契搜索是由Avriel和Wilde所提出。
 *
 *	由斐波那契数列的特性可以推导出：
 *	f(k) = f(k-1) + f(k-2);
 *	上面是数值，结合下标的可以得到：
 *	f(k)-1 = f(k-1) + f(k-2) -1;
 *	如果把下标看成整体，得到：
 *	f(k)-1 = (f(k-1)-1) + (f(k-2)-1) + 1;
 *	其中这个 1 就可以看作mid, mid将数组分为：前半部分：f(k-1)-1 和后半部分：f(k-2)-1
 *
 *	参考资料：
 *	查找算法：斐波那契查找
 *	https://juejin.im/post/5b894de96fb9a01a18268f67
 *	查找--有序表查找(折半查找，插值查找，斐波拉契查找)	
 *	http://www.jianshu.com/p/a3bda7ba96ea
 *	波那契查找算法
 *	https://wu-yudong.iteye.com/blog/1988305
 */
public class FibonacciSearch {
	
	public static void main(String[] args) {
		int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 }; 
		
		int key = 12;
		int index = fibonacciSearch(arr, key);
		check(arr, key, index);
		
	}
	
	private static void check(int[] arr, int key, int index) {
		if(-1 == index) {
			System.out.printf("%d 在原数组中未找到\n", key);
		} else {
			System.out.printf("arr[%d]=%d\n", index, arr[index]);
		}
	}
	
	private static int fibonacciSearch(int[] arr, int key) {
		/**
		 * 获取第一个大于等于数组长度的斐波纳契数
		 */
		int k = -1;
		while(arr.length > fibonacci(++k)-1) {
		}
		// 创建出临时数组，临时数组的长度就是上一步计算出的斐波那契数
		int[] temp = new int[fibonacci(k)-1];
		
		// 将原数组拷贝到 temp 数组
		System.arraycopy(arr, 0, temp, 0, arr.length);
		// temp 数组一般比原数组长，所以要把原数组最后一位数据填充剩余的位置
		Arrays.fill(temp, arr.length, temp.length, arr[arr.length-1]);
		return sort(temp, 0, arr.length-1, key, k);
		
	}

	/**
	 * @param temp 已经符合斐波那契数列的数组
	 * @param lo 原数组的最低位索引
	 * @param hi 原数组的最高位索引
	 * @param key 要搜索的关键词
	 * @param k 
	 * @return
	 */
	private static int sort(int[] temp, int lo, int hi, int key, int k) {
		
		while(lo <= hi) {
			// low：起始位置  
			// 前半部分有f[k-1]个元素，由于下标从0开始 
			// 则-1 获取 黄金分割位置元素的下标
			int mid = lo + (fibonacci(k-1) - 1);
			
			if(key < temp[mid]) {
				hi = mid-1;
				/**
				 * 	全部元素  = 前半部分 + 后半部分
				 * 	f[k] = f[k-1] + f[k-2]
				 * 	因为前半部分有f[k-1]个元素，所以 k = k-1
				 */
				k -= 1;
			} else if(key > temp[mid]) {
				lo = mid+1;
				/**
				 * 	全部元素  = 前半部分 + 后半部分
				 * 	f[k] = f[k-1] + f[k-2]
				 * 	因为后半部分有f[k-2]个元素，所以 k = k-2
				 */
				k -= 2;
			} else {
				
				// 出现这种情况是查找到补充的元素, 而补充的元素与high位置的元素一样, 所以直接取最低位置
				if(mid > hi) {
					return hi;
				} else {
					return mid;
				}
				
				// 等价于 hi 和 mid 两者取最小值
//				return Math.min(hi, mid);
			}
		}
		return -1;
	}

	/**
	 * 	斐波那契数列
	 * 	又译为菲波拿契数列、菲波那西数列、斐波那契数列、黄金分割数列
	 * 	用文字来说，就是费波那契数列由0和1开始，之后的费波那契系数就是由之前的两数相加而得出。首几个费波那契系数是：
	 * 	0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233……
	 * 
	 *  斐波那契数列的数学公式表示：
	 *	F[0] = 0
	 *	F[1] = 1
	 *	F[n] = F[n-1]+F[n-2] (n>=2)
	 * 
	 * 	产生斐波那契数的函数
	 * @param k 斐波那契函数参数值
	 * @return k参数得到的对应斐波那契数
	 */
	public static int fibonacci(int k) {
		if(0 == k) {
			return 0;
		}
		
		if(1 == k) {
			return 1;
		}
		
		if(k >= 2) {
			return fibonacci(k-1) + fibonacci(k-2);
		}
		
		throw new RuntimeException("index must bigger than 0");
	}
	
	public static void print(int[] arr) {
		System.out.println(Arrays.toString(arr));
	}
}
