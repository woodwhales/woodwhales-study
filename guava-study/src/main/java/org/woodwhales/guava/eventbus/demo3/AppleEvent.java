package org.woodwhales.guava.eventbus.demo3;

import com.google.gson.Gson;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.2 22:35
 * @description: 子类事件对象
 */
public class AppleEvent extends FruitEvent {

    public AppleEvent() {
    }

    public AppleEvent(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
