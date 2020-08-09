package org.woodwhales.guava.eventbus.custom;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.4 16:57
 * @description:
 */
public class MyAsyncEventBus extends MyEventBus {

    private static final String BUS_NAME = "ASYNC-EVENT-BUS";

    public MyAsyncEventBus(String busName, MyEventExceptionHandler eventExceptionHandler, ThreadPoolExecutor executor) {
        super(busName, eventExceptionHandler, executor);
    }

    public MyAsyncEventBus(String busName, ThreadPoolExecutor executor) {
        this(busName, null, executor);
    }

    public MyAsyncEventBus(ThreadPoolExecutor executor) {
        this(BUS_NAME, null, executor);
    }
}
