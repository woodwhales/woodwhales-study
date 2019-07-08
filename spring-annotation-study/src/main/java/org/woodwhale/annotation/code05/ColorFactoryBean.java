package org.woodwhale.annotation.code05;

import org.springframework.beans.factory.FactoryBean;

public class ColorFactoryBean implements FactoryBean<Color> {

	@Override
	public Color getObject() throws Exception {
		System.out.println("getObject --> new Color()");
		return new Color();
	}

	@Override
	public Class<?> getObjectType() {
		return Color.class;
	}

	/**
	 * 	接口的默认方法，默认返回true，表示单例模式
	 * 	返回 false 表示每次获取 bean 的时候就会调用getObject()方法
	 */
	@Override
	public boolean isSingleton() {
		return false;
	}
}
