package org.woodwhales.guava.eventbus.demo3;

import com.google.common.eventbus.EventBus;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.2 22:32
 * @description: event 如果有父类，此时某个监听器监听的event是这个基类，
 * 那么消息是这个子类的时候，该监听父类的监听器方法也会执行
 */
public class MultipleEventExample {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();

        eventBus.register(new SimpleListener());

        eventBus.post(new AppleEvent("apple"));

        eventBus.post(new FruitEvent("fruit"));
    }
}
