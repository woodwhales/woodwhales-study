package org.woodwhales.concurrent.code23;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class T03_SynchronizedList {
	public static void main(String[] args) {
		List<String> strs = new ArrayList<>();
		// 此时 strsSync 的所有方法都是加锁的了
		List<String> strsSync = Collections.synchronizedList(strs);
	}
}
