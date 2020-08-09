package org.woodwhales.guava.eventbus.custom;

import java.lang.reflect.Method;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.4 15:05
 * @description: 监听者信息对象
 */
public class MySubscriber {

    private final Object subscribeObject;

    private final Method subscribeMethod;

    private boolean disabled = false;

    public MySubscriber(Object subscribeObject, Method subscribeMethod) {
        this.subscribeObject = subscribeObject;
        this.subscribeMethod = subscribeMethod;
    }

    public Object getSubscribeObject() {
        return subscribeObject;
    }

    public Method getSubscribeMethod() {
        return subscribeMethod;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
