package org.woodwhales.concurrent.code21;

import java.util.Arrays;

/**
 * 线程安全的单例模式：
 *
 * 阅读文章：http://www.cnblogs.com/xudong-bupt/p/3433643.html
 *
 * 更好的是采用下面的方式，既不用加锁，也能实现懒加载
 *
 */
public class Singleton {
	// 构造器私有化
	private Singleton() {
		System.out.println("single");
	}

	// 静态内部类私有化，这个内部类 new 出一个实例
	private static class Inner {
		private static Singleton s = new Singleton();
	}

	// 调用静态内部类获取当前实例
	public static Singleton getSingle() {
		return Inner.s;
	}
	
	public static void main(String[] args) {
		Thread[] ths = new Thread[200];
		for(int i=0; i<ths.length; i++) {
			ths[i] = new Thread(()->{
				Singleton.getSingle();
			});
		}
		
		Arrays.asList(ths).forEach(o->o.start());
	}
	
}
