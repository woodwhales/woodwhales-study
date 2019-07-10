package org.woodwhales.spring;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.woodwhales.spring.code2.Car;

public class TestCode02 {
	
	@SuppressWarnings("resource")
	@Test
	public void testDI1() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans02.xml");
		Car car = (Car) applicationContext.getBean("car");
		System.out.println(car);
	}
	
	@SuppressWarnings("resource")
	@Test
	public void testDI2() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans02.xml");
		Car car = (Car) applicationContext.getBean("car2");
		System.out.println(car);
	}
}
