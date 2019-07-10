package org.woodwhales.spring;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.woodwhales.spring.code4.Boss;
import org.woodwhales.spring.code4.DataSource;
import org.woodwhales.spring.code4.SuperBoss;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class TestCode04 {
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans04.xml");

	@Test
	public void testList() {
		Boss boss = (Boss) applicationContext.getBean("boss");
		System.out.println(boss);
	}
	
	@Test
	public void testMap() {
		SuperBoss superBoss = (SuperBoss) applicationContext.getBean(SuperBoss.class);
		System.out.println(superBoss);
	}
	
	@Test
	public void testProperties() {
		DataSource dataSource = applicationContext.getBean(DataSource.class);
		System.out.println(dataSource);
	}
	
	@Test
	public void testUtil() {
		Boss boss = (Boss) applicationContext.getBean("boss2");
		System.out.println(boss);
	}
	
	@Test
	public void testP() {
		Boss boss = (Boss) applicationContext.getBean("boss3");
		System.out.println(boss);
	}
	
	@Test
	public void testLoadFile() {
		ComboPooledDataSource comboPooledDataSource = applicationContext.getBean(ComboPooledDataSource.class);
		System.out.println(comboPooledDataSource.getUser());
		System.out.println(comboPooledDataSource.getMaxPoolSize());
	}
}
