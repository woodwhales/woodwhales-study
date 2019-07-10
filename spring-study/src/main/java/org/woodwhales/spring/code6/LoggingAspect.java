package org.woodwhales.spring.code6;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * AOP 的快速入门
 * 1. 加入 jar 包
 * 	com.springsource.net.sf.cglib-2.2.0.jar
 * 	com.springsource.org.aopalliance-1.0.0.jar
 * 	com.springsource.org.aspectj.weaver-1.6.8.RELEASE.jar
 * 	spring-aspects-4.0.0.RELEASE.jar
 * 
 * 2. 在 Spring 的配置文件中加入 aop 的命名空间。 
 * 
 * 3. 基于注解的方式来使用 AOP
 * 	3.1 在配置文件中配置自动扫描的包: 
 * 		<context:component-scan base-package="com.atguigu.spring.aop"/>
 * 	3.2 加入使 AspjectJ 注解起作用的配置: 
 * 		<aop:aspectj-autoproxy/>
 * 	为匹配的类自动生成动态代理对象. 
 * 
 * 4. 编写切面类: 
 * 	4.1 一个一般的 Java 类
 * 	4.2 在其中添加要额外实现的功能. 
 *
 * 5. 配置切面
 * 	5.1 切面必须是 IOC 中的 bean: 实际添加了 @Component 注解
 * 	5.2 声明是一个切面: 添加 @Aspect
 * 	5.3 声明通知: 即额外加入功能对应的方法. 
 * 		5.3.1 前置通知: @Before("execution(public int org.woodwhales.spring.code6.ArithmeticCalculator.*(int, int))")
 * @Before 表示在目标方法执行之前执行 @Before 标记的方法的方法体. 
 * @Before 里面的是切入点表达式: 
 * 
 * 6. 在通知中访问连接细节: 可以在通知方法中添加 JoinPoint 类型的参数, 
 * 	  从中可以访问到方法的签名和方法的参数. 
 * 
 * 7. @After 表示后置通知: 在方法执行之后执行的代码. 
 */

/**
 *	AspectJ 支持 5 种类型的通知注解: 
	@Before: 前置通知, 在方法执行之前执行
	@After: 后置通知, 在方法执行之后执行 
	@AfterRunning: 返回通知, 在方法返回结果之后执行
	@AfterThrowing: 异常通知, 在方法抛出异常之后
	@Around: 环绕通知, 围绕着方法执行
 *
 */



//ͨ通过添加 @Aspect 注解声明一个 bean 是一个切面
@Order(1) // 指定多个通知的执行顺序，数值越小，越会先执行
@Aspect
@Component
public class LoggingAspect {

	// @Before 表示在目标方法执行之前执行 @Before 标记的方法的方法体. 
	@Before("execution(public int org.woodwhales.spring.code6.ArithmeticCalculator.*(int, int))")
	public void beforeMethod(JoinPoint joinPoint){
		String methodName = joinPoint.getSignature().getName();
		Object [] args = joinPoint.getArgs();
		
		System.out.println("beforeMethod, The method " + methodName + " begins with " + Arrays.asList(args));
	}
	
	// @After 表示后置通知: 在方法执行之后执行的代码
	@After("execution(* org.woodwhales.spring.code6.*.*(..))")
	public void afterMethod(JoinPoint joinPoint){
		String methodName = joinPoint.getSignature().getName();
		System.out.println("afterMethod, The method " + methodName + " ends");
	}
	
	/**
	 * 	在方法正常结束后执行的代码
	 */
	@AfterReturning(value = "execution(* org.woodwhales.spring.code6.*.*(..))", returning="result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		String methodName = joinPoint.getSignature().getName();
		Object [] args = joinPoint.getArgs();
		System.out.println("afterReturning, The method " + methodName + " ends with " + Arrays.asList(args) + " result = " + result);
	}
	
	/**
	 * 	异常通知, 在方法抛出异常之后
	 * 	在方法执行异常的时候才执行的代码，并且可以指定特殊的异常
	 */
	@AfterThrowing(value = "execution(* org.woodwhales.spring.code6.*.*(..))", throwing = "exception")
	public void afterThrowing(JoinPoint joinPoint, Exception exception) {
		String methodName = joinPoint.getSignature().getName();
		Object [] args = joinPoint.getArgs();
		System.out.println("afterReturning, The method " + methodName + " exception = " + exception.getMessage());
	}
	
	@Around("execution(* org.woodwhales.spring.code6.*.*(..))")
	public Object around(ProceedingJoinPoint joinPoint) {
		Object result = null;
		
		String methodName = joinPoint.getSignature().getName();
		Object [] args = joinPoint.getArgs();
		
		try {
			System.out.println("around, The method " + methodName + "begins with " + Arrays.asList(args));
			result = joinPoint.proceed();
			System.out.println("around, The method " + methodName + "ends with" + result);
		} catch (Throwable e) {
			System.out.println("around, The method " + methodName + "exception = " + e);
			throw new RuntimeException(e);
		}
		System.out.println("around, The method " + methodName + "end");
		return result;
	}
}
