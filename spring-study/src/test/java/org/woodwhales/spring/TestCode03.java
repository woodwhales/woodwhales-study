package org.woodwhales.spring;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.woodwhales.spring.code3.Person;

public class TestCode03 {
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

	@Test
	public void testDI() {
		Person person = (Person) applicationContext.getBean("person");
		System.out.println(person);
	}
	
	@Test
	public void testDI2() {
		Person person = (Person) applicationContext.getBean("person2");
		System.out.println(person);
	}
}
