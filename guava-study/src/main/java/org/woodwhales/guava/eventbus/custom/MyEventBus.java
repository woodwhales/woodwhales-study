package org.woodwhales.guava.eventbus.custom;

import java.util.Objects;
import java.util.concurrent.Executor;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.4 14:01
 * @description:
 */
public class MyEventBus implements MyBus {

    private final MyRegistry registry = new MyRegistry();

    private String busName;

    private static final String DEFAULT_BUS_NAME = "DEFAULT";

    private static final String DEFAULT_TOPIC = "default-topic";

    private final MyDispatcher dispatcher;

    public MyEventBus() {
        this(DEFAULT_BUS_NAME, null, MyDispatcher.SEQ_EXECUTOR_SERVICE);
    }

    public MyEventBus(MyEventExceptionHandler eventExceptionHandler) {
        this(DEFAULT_BUS_NAME, eventExceptionHandler, MyDispatcher.SEQ_EXECUTOR_SERVICE);
    }

    public MyEventBus(String busName, MyEventExceptionHandler eventExceptionHandler, Executor executor) {
        if(Objects.isNull(busName) || busName.isEmpty()) {
            busName = DEFAULT_BUS_NAME;
        }
        this.busName = busName;
        this.dispatcher = MyDispatcher.newDispatcher(executor, eventExceptionHandler);
    }

    @Override
    public void register(Object subscriber) {
        this.registry.bind(subscriber);
    }

    @Override
    public void unRegister(Object subscriber) {
        this.unRegister(subscriber);
    }

    @Override
    public void post(Object event) {
        this.post(event, DEFAULT_TOPIC);
    }

    @Override
    public void post(Object event, String topic) {
        this.dispatcher.dispatch(this, registry, event, topic);
    }

    @Override
    public void close() {
        this.dispatcher.close();
    }

    @Override
    public String getBusName() {
        return this.busName;
    }
}
