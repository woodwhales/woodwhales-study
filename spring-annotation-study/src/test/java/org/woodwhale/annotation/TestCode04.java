package org.woodwhale.annotation;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.woodwhale.annotation.code04.AppConfig4;

public class TestCode04 {
	
	ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig4.class);
	
	@Test
	public void testCondition() {
		String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
	}
}
