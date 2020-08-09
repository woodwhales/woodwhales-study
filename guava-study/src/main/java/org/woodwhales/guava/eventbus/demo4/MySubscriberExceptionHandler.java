package org.woodwhales.guava.eventbus.demo4;

import com.google.common.base.Throwables;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.2 22:54
 * @description:
 */
public class MySubscriberExceptionHandler implements SubscriberExceptionHandler {

    @Override
    public void handleException(Throwable exception, SubscriberExceptionContext context) {
        System.out.println("======== handleException start =========");
        System.out.println("exception => " + Throwables.getStackTraceAsString(exception));
        System.out.println("EventBus => " + context.getEventBus());
        System.out.println("Subscriber => " + context.getSubscriber().getClass().getName());
        System.out.println("SubscriberMethod => " + context.getSubscriberMethod());
        System.out.println("======== handleException end =========");
    }
}
