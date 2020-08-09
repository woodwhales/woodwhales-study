package org.woodwhales.guava.eventbus.demo1;

import com.google.common.eventbus.EventBus;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.2 22:08
 * @description: EventBus 简单示例
 */
public class SimpleEventBusExample {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();

        // 监听器注册到 eventBus
        eventBus.register(new GoogleEventBusListener());

        // 发送消息
        eventBus.post("this message from eventBus");
        eventBus.post(200);
    }

}
