package org.woodwhales.guava.eventbus.demo2;

import com.google.common.eventbus.EventBus;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.2 22:21
 * @description: 多个监听器监听消息
 * 当只注册监听器中的父类中也监听了某些消息的时候，父类的监听方法也会执行。
 * 如果父类的监听方法被子类覆盖监听，则只执行子类监听器的方法
 *
 */
public class MultipleListenerExample {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        // 监听器注册到 eventBus
        eventBus.register(new ChildListener());

        // 发送消息
        eventBus.post("this message from eventBus");
    }
}
