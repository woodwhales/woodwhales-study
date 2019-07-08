package org.woodwhale.annotation;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.woodwhale.annotation.code06.AppConfig6;

public class TestCode06 {
	
	@Test
	public void testLifeMethod() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig6.class);
		System.out.println("容器创建完成");
		
//		Dog bean = applicationContext.getBean(Dog.class);
//		System.out.println(bean.getClass());
		applicationContext.close();
	}
}
