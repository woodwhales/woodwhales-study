package org.woodwhales.guava.eventbus.demo3;

import com.google.gson.Gson;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.2 22:33
 * @description: 父类事件对象
 */
public class FruitEvent {

    String name;

    public FruitEvent() {

    }

    public FruitEvent(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
