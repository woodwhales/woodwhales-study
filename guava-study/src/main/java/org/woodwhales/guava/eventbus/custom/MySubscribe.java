package org.woodwhales.guava.eventbus.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.4 15:00
 * @description: 自定义监听器订阅方法注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MySubscribe {

    String topic() default "default-topic";
}
