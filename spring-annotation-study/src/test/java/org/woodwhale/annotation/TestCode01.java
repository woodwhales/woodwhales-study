package org.woodwhale.annotation;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.woodwhale.annotation.code01.AppConfig;
import org.woodwhale.annotation.code01.Person;

public class TestCode01 {

	@SuppressWarnings("resource")
	@Test
	public void testLoadXml() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
		Person bean = (Person) applicationContext.getBean("person");
		System.out.println(bean);
	}
	
	@SuppressWarnings("resource")
	@Test
	public void testLoadAnnotation() {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		Person person = applicationContext.getBean(Person.class);
		System.out.println(person);
		
		// 遍历所有 Person 类型的 bean 的id
		printBeanName(applicationContext, Person.class);
	}
	
	private void printBeanName(ApplicationContext applicationContext, Class<Person> clazz) {
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}
	}
	
}
