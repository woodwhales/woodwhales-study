package org.woodwhale.annotation;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.woodwhale.annotation.code07.AppConfig7;
import org.woodwhale.annotation.code07.Person;

public class TestCode07 {
	AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig7.class);

	@Test
	public void testValue() {
		Person person = applicationContext.getBean(Person.class);
		System.out.println(person);

		String property = applicationContext.getEnvironment().getProperty("person.nickName");
		System.out.println(property);
		applicationContext.close();
	}
}
