package org.woodwhale.algorithm;

/**
 * KMP 算法
 *
 */
public class KmpAlgorithm {
	public static void main(String[] args) {
		String src = "ABCDEDACABCDABDABDE";
		String target = "ABCDABD";

		int index = kmpSearch(src, target);
		System.out.println("index = " + index);
	}

	/**
	 * 将搜索词计算得到部分匹配值表
	 * 
	 * @param target
	 * @return
	 */
	private static int[] kmpNext(String target) {
		int[] next = new int[target.length()];

		next[0] = 0;

		for (int i = 1, j = 0; i < target.length(); i++) {
			while (j > 0 && target.charAt(i) != target.charAt(j)) {
				j = next[j - 1];
			}

			if (target.charAt(i) == target.charAt(j)) {
				j++;
			}
			next[i] = j;
		}

		return next;
	}

	private static int kmpSearch(String src, String target) {
		int[] next = kmpNext(target);

		for (int i = 0, j = 0; i < src.length(); i++) {
			while (j > 0 && src.charAt(i) != target.charAt(j)) {
				j = next[j - 1];
			}
			
			if(src.charAt(i) == target.charAt(j)) {
				j++;
			}

			if (j == target.length()) {
				return i - j + 1;
			}
		}

		return -1;
	}

}
