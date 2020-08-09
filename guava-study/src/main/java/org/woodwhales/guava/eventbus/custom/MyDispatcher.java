package org.woodwhales.guava.eventbus.custom;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.4 14:33
 * @description: 消息执行器
 */
public class MyDispatcher {

    private final Executor executorService;

    private final MyEventExceptionHandler eventExceptionHandler;

    public static final Executor SEQ_EXECUTOR_SERVICE = SeqExecutorService.INSTANCE;

    public static final Executor PER_THREAD_EXECUTOR_SERVICE = PerThreadExecutorService.INSTANCE;

    private MyDispatcher(Executor executorService, MyEventExceptionHandler eventExceptionHandler) {
        this.executorService = executorService;
        this.eventExceptionHandler = eventExceptionHandler;
    }

    public void dispatch(MyBus bus, MyRegistry registry, Object event, String topic) {
        ConcurrentLinkedQueue<MySubscriber> subscribers = registry.scanSubscriber(topic);
        if(Objects.isNull(subscribers)) {
            if(Objects.nonNull(eventExceptionHandler)) {
                eventExceptionHandler.handle(new IllegalArgumentException(String.format("The topic [%s] not bind yet", topic)),
                        new DefaultMyContext(bus.getBusName(), null, event));
            }

            return;
        }

        // 遍历所有的监听者，拿到对应的事件参数对象，比对消息的事件类型和监听者参数事件类型是否一致
        subscribers.stream().filter(subscriber -> !subscriber.isDisabled())
                .filter(subscriber -> {
                    Method subscribeMethod = subscriber.getSubscribeMethod();
                    Class<?> parameterTypeClass = subscribeMethod.getParameterTypes()[0];
                    return parameterTypeClass.isAssignableFrom(event.getClass());
                })
                .forEach(subscriber -> invokeRealSubscribe(bus, subscriber, event));
    }

    private void invokeRealSubscribe(MyBus bus, MySubscriber subscriber, Object event) {

        Method subscribeMethod = subscriber.getSubscribeMethod();
        Object subscribeObject = subscriber.getSubscribeObject();

        this.executorService.execute(() -> {
            try {
                subscribeMethod.invoke(subscribeObject, event);
            } catch (Exception e) {
                if(Objects.nonNull(this.eventExceptionHandler)) {
                    this.eventExceptionHandler.handle(e, new DefaultMyContext(bus.getBusName(), subscriber, event));
                }
            }
        });
    }


    public static MyDispatcher newDispatcher(Executor executorService, MyEventExceptionHandler eventExceptionHandler) {
        return new MyDispatcher(executorService, eventExceptionHandler);
    }

    public static MyDispatcher newSeqDispatcher(MyEventExceptionHandler eventExceptionHandler) {
        return new MyDispatcher(SEQ_EXECUTOR_SERVICE, eventExceptionHandler);
    }

    public static MyDispatcher newPerThreadDispatcher(MyEventExceptionHandler eventExceptionHandler) {
        return new MyDispatcher(PER_THREAD_EXECUTOR_SERVICE, eventExceptionHandler);
    }

    public void close() {
        if(executorService instanceof ExecutorService) {
            ((ExecutorService) executorService).shutdown();
        }
    }

    private static class SeqExecutorService implements Executor {

        private static final SeqExecutorService INSTANCE = new SeqExecutorService();

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }

    private static class PerThreadExecutorService implements Executor {

        private static final PerThreadExecutorService INSTANCE = new PerThreadExecutorService();

        @Override
        public void execute(Runnable command) {
            new Thread(command).start();
        }
    }

    public static class DefaultMyContext implements MyEventContext {

        private final String busName;

        private final MySubscriber subscriber;

        private final Object event;

        public DefaultMyContext(String busName, MySubscriber subscriber, Object event) {
            this.busName = busName;
            this.subscriber = subscriber;
            this.event = event;
        }

        @Override
        public String getSource() {
            return this.busName;
        }

        @Override
        public Object getSubscriberObject() {
            return Objects.nonNull(subscriber) ? subscriber.getSubscribeObject() : null;
        }

        @Override
        public Method getSubscribeMethod() {
            return Objects.nonNull(subscriber) ? subscriber.getSubscribeMethod() : null;
        }

        @Override
        public Object getEvent() {
            return this.event;
        }
    }
}
