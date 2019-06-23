package org.woodwhale.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 
 * 	贪心算法（英语：greedy algorithm），又称贪婪算法，
 * 	是一种在每一步选择中都采取在当前状态下最好或最优（即最有利）的选择，从而希望导致结果是最好或最优的算法。
 *
 */
public class Greedy {
	public static void main(String[] args) {
		//创建广播电台
		HashMap<String, HashSet<String>> broadCasts = new HashMap<>();
		
		HashSet<String> set1 = new HashSet<>();
		set1.add("北京");
		set1.add("上海");
		set1.add("天津");
		
		HashSet<String> set2 = new HashSet<>();
		set2.add("广州");
		set2.add("北京");
		set2.add("深圳");
		
		HashSet<String> set3 = new HashSet<>();
		set3.add("成都");
		set3.add("上海");
		set3.add("杭州");
		
		HashSet<String> set4 = new HashSet<>();
		set4.add("上海");
		set4.add("天津");
		
		HashSet<String> set5 = new HashSet<>();
		set5.add("杭州");
		set5.add("大连");
		
		broadCasts.put("k1", set1);
		broadCasts.put("k2", set2);
		broadCasts.put("k3", set3);
		broadCasts.put("k4", set4);
		broadCasts.put("k5", set5);
		
		//allAreas 所有地区集合
		HashSet<String> allAreas = new HashSet<>();
		broadCasts.forEach((key, value) -> {
			value.forEach(item -> {
				allAreas.add(item);
			});
		});
		
		System.out.println("allAreas = " + Arrays.toString(allAreas.toArray()));
		
		// 创建ArrayList 存放选中的广播集合
		ArrayList<String> selects = new ArrayList<>();
		
		HashSet<String> tempSet = new HashSet<>();
		
		String maxkey = null;
		
		while(allAreas.size() != 0) {
			maxkey = null;
			
			for (String key : broadCasts.keySet()) {
				tempSet.clear();
				// 当前key能覆盖的地区先加到临时集合中
				tempSet.addAll(broadCasts.get(key));
				// 求交集，并赋值给 tempSet
				tempSet.retainAll(allAreas);
				if(tempSet.size() > 0 && (maxkey == null ||tempSet.size() > broadCasts.get(maxkey).size())) {
					maxkey = key;
				}

			}

			if(maxkey != null) {
				selects.add(maxkey);
				allAreas.removeAll(broadCasts.get(maxkey));
			}
		}
		
		System.out.println("得到的选择结果是" + selects);
	}
}
