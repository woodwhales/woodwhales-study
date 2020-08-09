package org.woodwhales.guava.eventbus.demo5;

import com.google.common.eventbus.EventBus;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.2 23:07
 * @description: 监听DeadEvent消息类型，那么可以从这个DeadEvent对象中获取事件源和eventBus
 */
public class DeadEventExample {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus("DEAD-EVENT-BUS");

        eventBus.register(new DeadEventListener());

        eventBus.post("message");
    }
}
