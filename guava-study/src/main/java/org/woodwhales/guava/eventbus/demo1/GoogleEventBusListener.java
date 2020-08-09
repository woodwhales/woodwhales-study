package org.woodwhales.guava.eventbus.demo1;

import com.google.common.eventbus.Subscribe;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.2 22:08
 * @description:
 */
public class GoogleEventBusListener {

    @Subscribe
    public void method1(String event) {
        System.out.println("GoogleEventBusListener.method1, event = " + event);
    }

    @Subscribe
    public void method2(Integer event) {
        System.out.println("GoogleEventBusListener.method2, event = " + event);
    }
}
