package org.woodwhales.aop.code02;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	
	/**
	 * 定义一个方法, 用于声明切入点表达式. 一般地, 该方法中再不需要添入其他的代码. 
	 * 使用 @Pointcut 来声明切入点表达式. 
	 * 后面的其他通知直接使用方法名来引用当前的切入点表达式. 
	 * 
	 * 在同一个包下的引用，用类型.方法名，不同包下引用，用全包名.方法名
	 */
	@Pointcut("execution(public int org.woodwhales.aop.code02.ArithmeticCalculator.*(..))")
	public void declareJointPointExpression(){
		
	}

	@Before("declareJointPointExpression()")
	public void beforeMethod(JoinPoint joinPoint){
		String methodName = joinPoint.getSignature().getName();
		Object [] args = joinPoint.getArgs();
		
		System.out.println("beforeMethod, The method " + methodName + " begins with " + Arrays.asList(args));
	}
	
	@After("declareJointPointExpression()")
	public void afterMethod(JoinPoint joinPoint){
		String methodName = joinPoint.getSignature().getName();
		System.out.println("afterMethod, The method " + methodName + " ends");
	}

	@AfterReturning(value = "declareJointPointExpression()", returning="result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		String methodName = joinPoint.getSignature().getName();
		Object [] args = joinPoint.getArgs();
		System.out.println("afterReturning, The method " + methodName + " ends with " + Arrays.asList(args) + " result = " + result);
	}

}
