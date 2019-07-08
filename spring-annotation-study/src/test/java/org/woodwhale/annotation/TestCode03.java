package org.woodwhale.annotation;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.woodwhale.annotation.code03.AppConfig3;
import org.woodwhale.annotation.code03.Student;

public class TestCode03 {
	ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig3.class);

	@Test
	public void testScopes() {
		Student student1 = applicationContext.getBean(Student.class);
		Student student2 = applicationContext.getBean(Student.class);
		System.out.println(student1);
		System.out.println(student2);
		System.out.println(student1 == student2);
	}
}
