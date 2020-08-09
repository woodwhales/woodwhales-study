package org.woodwhales.guava.eventbus.custom.test;

import org.woodwhales.guava.eventbus.custom.MySubscribe;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.4 16:14
 * @description:
 */
public class MyEventBusListener2 {

    @MySubscribe
    public void test1(String event) {
        System.out.println("MyEventBusListener2.test1 => " + event);
    }

    @MySubscribe(topic = "woodwhales-topic")
    public void test2(String event) {
        System.out.println("MyEventBusListener2.test2 => " + event);
    }

    @MySubscribe(topic = "test-topic")
    public void test3(Integer event) {
        throw new RuntimeException("test3 happen exception");
    }

}
