package org.woodwhale.annotation.code05;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	/**
	 * AnnotationMetadata:当前类的注解信息
	 * BeanDefinitionRegistry:BeanDefinition注册类
	 */
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		
		String[] beanNames = registry.getBeanDefinitionNames();
		for (String beanName : beanNames) {
			System.out.println("registerBeanDefinitions --> " + beanName);
		}
		
		boolean blue = registry.containsBeanDefinition("org.woodwhale.king.code05.Blue");
		boolean color = registry.containsBeanDefinition("org.woodwhale.king.code05.Color");
		
		if(color && blue) {
			// 手动创建一个 BeanDefinition 并注册到容器中
			RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(RainBow.class);
			registry.registerBeanDefinition("rainBow", rootBeanDefinition);
		}
		
	}

}
