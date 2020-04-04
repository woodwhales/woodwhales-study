package org.woodwhale.annotation;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.woodwhale.annotation.code05.AppConfig5;
import org.woodwhale.annotation.code05.Color;

public class TestCode05 {
	ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig5.class);
	
	@Test
	public void testImport() {
		String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
        
        Color colorFactoryBean1 = (Color)applicationContext.getBean("colorFactoryBean");
        Color colorFactoryBean2 = (Color)applicationContext.getBean("colorFactoryBean");
        System.out.println("bean class type : " + colorFactoryBean1.getClass());
        
        System.out.println("colorFactoryBean1和colorFactoryBean2是否是相同对象 = " + (colorFactoryBean1 == colorFactoryBean2));
        
        // 使用"&"+id名称获取工厂对象本身
        Object bean = applicationContext.getBean("&colorFactoryBean");
        System.out.println(bean.getClass());
	}
	
	@Test
	public void testFactoryBean() {
        Color colorFactoryBean1 = (Color)applicationContext.getBean("colorFactoryBean");
        Color colorFactoryBean2 = (Color)applicationContext.getBean("colorFactoryBean");
        System.out.println("bean class type --> " + colorFactoryBean1.getClass());
        
        System.out.println(colorFactoryBean1 == colorFactoryBean2);
        
        // 在id前面增加 &，获取工厂对象本身
        Object bean = applicationContext.getBean("&colorFactoryBean");
        System.out.println("bean class type --> " + bean.getClass());
	}
}
