package org.woodwhales.guava.eventbus.demo2;

import com.google.common.eventbus.Subscribe;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.2 22:19
 * @description:
 */
public class ChildListener extends FatherListener {

    @Subscribe
    @Override
    public void accept(String event) {
        System.out.println("ChildListener.accept, event = " + event);
    }

    @Subscribe
    public void consumeTask(String event) {
        System.out.println("ChildListener.consumeTask, event = " + event);
    }
}
