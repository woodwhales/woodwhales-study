package org.woodwhales.guava.eventbus.custom.test;

import org.woodwhales.guava.eventbus.custom.MyAsyncEventBus;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.4 17:02
 * @description:
 */
public class MyAsyncEventExample {

    public static void main(String[] args) {

        MyAsyncEventBus myAsyncEventBus = new MyAsyncEventBus((ThreadPoolExecutor) Executors.newFixedThreadPool(4));
        myAsyncEventBus.register(new MyEventBusListener());

        myAsyncEventBus.post("woodwhales-async");

    }
}
