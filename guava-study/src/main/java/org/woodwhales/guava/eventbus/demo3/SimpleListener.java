package org.woodwhales.guava.eventbus.demo3;

import com.google.common.eventbus.Subscribe;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.2 22:32
 * @description: 一个监听子类对象，一个监听父类对象
 */
public class SimpleListener {

    @Subscribe
    public void acceptFruitEvent(FruitEvent fruitEvent) {
        System.out.println("SimpleListener.acceptFruitEvent => fruitEvent " + fruitEvent);
    }

    @Subscribe
    public void acceptAppleEvent(AppleEvent appleEvent) {
        System.out.println("SimpleListener.acceptAppleEvent => appleEvent " + appleEvent);
    }
}
