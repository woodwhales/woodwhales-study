package org.woodwhale.annotation;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.woodwhale.annotation.code08.AppConfig8;
import org.woodwhale.annotation.code08.Food;
import org.woodwhale.annotation.code08.controller.UserController;

public class TestCode08 {
	AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig8.class);
	
	@Test
	public void testAutowired() {
		System.out.println(applicationContext.getBean(UserController.class));
		System.out.println(applicationContext.getBean(Food.class));
	}
}
