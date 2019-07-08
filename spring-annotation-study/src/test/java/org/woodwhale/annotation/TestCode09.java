package org.woodwhale.annotation;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.woodwhale.annotation.code09.AppConfig9;
import org.woodwhale.annotation.code09.MyDataSource;

public class TestCode09 {

	@Test
	public void testProfile() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig9.class);
		MyDataSource dataSource = applicationContext.getBean(MyDataSource.class);
		System.out.println(dataSource);
	}
	
	@Test
	public void testProfileCode() {
		//1、创建一个applicationContext
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		//2、设置需要激活的环境
		applicationContext.getEnvironment().setActiveProfiles("dev");
		//3、注册主配置类
		applicationContext.register(AppConfig9.class);
		//4、启动刷新容器
		applicationContext.refresh();
		
		String[] beanNames = applicationContext.getBeanNamesForType(MyDataSource.class);
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}
	}
}
