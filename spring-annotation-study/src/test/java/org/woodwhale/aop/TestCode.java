package org.woodwhale.aop;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.woodwhale.aop.AopConfig;
import org.woodwhale.aop.MathCalculator;

public class TestCode {

	AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AopConfig.class);
	
	/**
	 * aop注解的三个步骤：
	 * 	1）、将业务逻辑组件和切面类都加入到容器中；告诉Spring哪个是切面类（@Aspect）
	 * 	2）、在切面类上的每一个通知方法上标注通知注解，告诉Spring何时何地运行（切入点表达式）
	 *  3）、开启基于注解的aop模式；@EnableAspectJAutoProxy
	 */
	@Test
	public void testAOP() {
		MathCalculator mathCalculator = applicationContext.getBean(MathCalculator.class);
		mathCalculator.div(10, 1);
		System.out.println("--------------");
		mathCalculator.div(10, 0);
		applicationContext.close();
	}
}
