package org.woodwhales.guava.eventbus.custom.test;

import org.woodwhales.guava.eventbus.custom.MySubscribe;

import java.util.concurrent.TimeUnit;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.4 16:14
 * @description:
 */
public class MyEventBusListener {

    @MySubscribe
    public void test1(String event) {
        System.out.println("MyEventBusListener.test1 => " + event);
    }

    @MySubscribe
    public void test3(String event) {
        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("MyEventBusListener.test3 => " + event);
    }


    @MySubscribe(topic = "woodwhales-topic")
    public void test2(Integer event) {
        System.out.println("MyEventBusListener.test2 => " + event);
    }

}
