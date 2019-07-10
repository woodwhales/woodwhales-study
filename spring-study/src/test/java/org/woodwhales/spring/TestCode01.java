package org.woodwhales.spring;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.woodwhales.spring.code1.HelloWorld;

/**
 * 认识 spring 
 * @author Administrator
 *
 */
public class TestCode01 {

	@Test
	public void testHelloWorld() {
		// 创建对象
		HelloWorld helloWorld = new HelloWorld();
		// 为属性赋值
		helloWorld.setName("woodwhales");
		helloWorld.hello();
	}
	
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans01.xml");

	@Test
	public void testSpring() {
		// 创建spring的ioc容器对象
		// 从容器获取实例bean，根据id获取bean
		HelloWorld helloWorld = (HelloWorld) applicationContext.getBean("helloWorld");
		helloWorld.hello();
	}
	
	/**
	 * 无参构造函数在容器初始化的时候就被调用了
	 */
	@Test
	public void testSpringBeanScope() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans01.xml");
	}
	
	@Test
	public void testSpringBeanScope1() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans01.xml");
		HelloWorld helloWorld1 = (HelloWorld) applicationContext.getBean("helloWorld");
		HelloWorld helloWorld2 = (HelloWorld) applicationContext.getBean("helloWorld");
		System.out.println(helloWorld1 == helloWorld2);
	}
	

	/**
	 * 原型模式下,每次获取bean 的时候就会重新new 一个实例
	 * 会打印四次无参构造器，因为前两次是单例的helloworld 打印的
	 */
	@Test
	public void testSpringBeanScope2() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans01.xml");
		HelloWorld helloWorld1 = (HelloWorld) applicationContext.getBean("helloWorld2");
		HelloWorld helloWorld2 = (HelloWorld) applicationContext.getBean("helloWorld2");
		System.out.println(helloWorld1 == helloWorld2);
		// 每次获取bean 的时候就会重新new 一个实例
		HelloWorld helloWorld3 = (HelloWorld) applicationContext.getBean("helloWorld2");
	}
	
	
}
