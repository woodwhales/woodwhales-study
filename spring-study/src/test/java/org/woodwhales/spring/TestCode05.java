package org.woodwhales.spring;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.woodwhales.spring.code5.Hero;
import org.woodwhales.spring.code5.Tank;

public class TestCode05 {
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans05.xml");

	@Test
	public void testAutowired() {
		Hero hero = applicationContext.getBean(Hero.class);
		System.out.println(hero);
	}
	
	@Test
	public void testExtends() {
		Tank tank2 = (Tank) applicationContext.getBean("tank2");
		System.out.println(tank2);
	}
}
