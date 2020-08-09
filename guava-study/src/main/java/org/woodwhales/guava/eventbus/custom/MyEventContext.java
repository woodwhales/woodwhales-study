package org.woodwhales.guava.eventbus.custom;

import java.lang.reflect.Method;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.4 14:28
 * @description:
 */
public interface MyEventContext {

    String getSource();

    Object getSubscriberObject();

    Method getSubscribeMethod();

    Object getEvent();

}
