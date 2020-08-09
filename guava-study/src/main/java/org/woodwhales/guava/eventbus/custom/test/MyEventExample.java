package org.woodwhales.guava.eventbus.custom.test;

import org.woodwhales.guava.eventbus.custom.MyEventBus;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.4 16:13
 * @description:
 */
public class MyEventExample {

    public static void main(String[] args) {

        MyEventBus myEventBus = new MyEventBus((cause, context) -> {
            cause.printStackTrace();
            System.out.println("========================");
            System.out.println(context.getSubscribeMethod());
            System.out.println(context.getSubscriberObject());
            System.out.println(context.getSource());
            System.out.println(context.getEvent());
        });
        myEventBus.register(new MyEventBusListener());
        myEventBus.register(new MyEventBusListener2());

        myEventBus.post(1234, "woodwhales-topic");
        myEventBus.post("test-event");
        //myEventBus.post(456, "test-topic");
    }
}
