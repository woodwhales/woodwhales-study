package org.woodwhale.annotation;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.woodwhale.annotation.code02.AppConfig2;

public class TestCode02 {

	@SuppressWarnings("resource")
	@Test
	public void testComponentScan() {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig2.class);
		String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
	}
}
