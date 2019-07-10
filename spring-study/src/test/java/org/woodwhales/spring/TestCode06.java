package org.woodwhales.spring;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.woodwhales.spring.code6.ArithmeticCalculator;

public class TestCode06 {
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans06.xml");
	
	@Test
	public void testAop() {
		ArithmeticCalculator arithmeticCalculator = applicationContext.getBean(ArithmeticCalculator.class);
		arithmeticCalculator.add(20, 20);
		arithmeticCalculator.div(20, 10);
	}
}
