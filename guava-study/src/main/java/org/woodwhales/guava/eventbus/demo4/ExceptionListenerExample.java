package org.woodwhales.guava.eventbus.demo4;

import com.google.common.eventbus.EventBus;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.2 22:48
 * @description: 多个监听器同时监听相同消息，其中一个监听器有异常，那么不会影响其他监听器消费消息
 * 可以在EventBus构造函数中传入一个异常处理器，当监听器有异常抛出时，会执行这个异常处理器
 *
 * 思考点：如果异常处理很长时间，会不会导致后面的监听器等待执行
 *
 */
public class ExceptionListenerExample {

    public static void main(String[] args) {

        EventBus eventBus = new EventBus(new MySubscriberExceptionHandler());

        eventBus.register(new MyListener());

        eventBus.post(Integer.MAX_VALUE);
    }
}
