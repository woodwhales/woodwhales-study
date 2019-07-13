package org.woodwhales.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.woodwhales.aop.code01.ArithmeticCalculator;

public class TestCode01 {
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans-aop-code01.xml");
	
	@Test
	public void testAop() {
		ArithmeticCalculator arithmeticCalculator = applicationContext.getBean(ArithmeticCalculator.class);
		arithmeticCalculator.add(20, 20);
		arithmeticCalculator.div(20, 10);
	}
}
