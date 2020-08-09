package org.woodwhales.guava.eventbus.demo2;

import com.google.common.eventbus.Subscribe;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.2 22:18
 * @description:
 */
public class FatherListener {

    @Subscribe
    public void accept(String event) {
        System.out.println("FatherListener.accept, event = " + event);
    }

    @Subscribe
    public void consume(String event) {
        System.out.println("FatherListener.consume, event = " + event);
    }
}
