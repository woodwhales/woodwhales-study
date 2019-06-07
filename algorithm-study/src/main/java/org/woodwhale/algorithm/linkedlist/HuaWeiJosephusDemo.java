package org.woodwhale.algorithm.linkedlist;

/**
 * 功能: 约瑟夫问题众所周知，原始的约瑟夫问题是这样的：有n个人，编号为1,2,..., n，站成一圈，
	每次第m个将会被处决，直到只剩下一个人。约瑟夫通过给出m来决定赦免其中的一个人。
	例如当n=6,m=5时，5,4,6,2,3将会被依次处决，而1将会幸免。
	假如有k个好人，和k个坏人，所有人站成一圈，前k个人是好人，后k个人是坏人，
	编写程序计算一个最小的m，使k个坏人都被处决，而不处决任何好人。
	    
	输入: k 为正整数
	    
	输出: 
	     
	返回: 最小的m，使k个坏人都被处决，而不处决任何好人。
		k=1  时: 1 2
		m=2: 2
	     
		k=2  时: 1 2 3 4
		m=7: 3 -> 4
	     
		k=3  时: 1 2 3 4 5 6
		m=5: 5 -> 4 -> 6
 *
 */
public final class HuaWeiJosephusDemo {

	public static void main(String[] args) {
		System.out.println(getMinimumM(5));
	}
 
	/**
	 *	思路分析：
	 *		先简单分析一下 m 的是干什么的？
	 *		1, 2, … k-1, k, k+1, k+2, ……, 2k-1, 2k
	 *		m 至少从 k+1 开始，不然上来就把第m个位置的好人干死了
	 *		叫号是从1开始数，数到m数字，当前位置的人被干死
	 *
	 *		将所有人编号，从0开始编号，
	 *		第一次叫到m的人，被干死，此时的被干死的人的位置为x，那么它的编号是x+1, 此时剩余存活人数是 2k-1
	 *		接着下一个位置x+1的人从新开始从1叫号，叫到下一个m的位置一定是(x+m-1)+1=x+m，他的编号一定是x+m+1, 此时剩余存活人数是 2k-2
	 *		依次，每次记录一下被干死的位置是哪里：
	 *			所以，每一次被干死的位置，一定要落在[k+1 - 2k]人身上，也就是位置是[k, 2k-1]的位置，
	 *			用一个变量来记录每次的死者位置编号，那么一定每次都是大于 k的位置
	 *			如果不符合这个条件，那么m要自增，所有的规则重新计算

	 *		直到剩余存活人数是 k 个的时候就输出m		
	 *
	 * @param k
	 * @return
	 */
	public static int getMinimumM(int k) {
		int m = k+1; // m 至少为 k+1，不然上来就把第m个位置的好人干死了
		int remainPeople = 2 * k; //从K下标开始查找，剩下的人数初始化为2 * K
		int currIndex = 0; //当前应该删除的位置
		int deleteNum = 0; //已经删除的人数
		int start = 1; //从第一个人开始计数
		
		while(true) {
			// 从start位置开始数1，数到m，就到currIndex位置, 当currIndex为0的时候，为最后一个元素
			currIndex = (start + m - 1) % remainPeople;
			
			// 当前的删除位置必须大于 k，也就是删除位置必须是：[k+1 … 2k]
			if(currIndex > k || currIndex == 0) {
				// 判断是否删除K个，如果是的话，则m已经找到, 由于上面判断的卡位，这里的删除数直接累加即可
				deleteNum++;
				if (deleteNum == k) {
					return m;
				}
				
				/* 删除一个元素，这个元素位于 [K..2K]之间 */
				remainPeople--;
				
				/* 判断下一个起点 */
				if(currIndex == 0) {
					start = 1;
				} else {
					start = currIndex;
				}
			} else { //这个m不符合要求
				start = 1;
				remainPeople = 2 * k;
				deleteNum = 0;
				m++; //判断下一个m
			}
		}
	}
}
