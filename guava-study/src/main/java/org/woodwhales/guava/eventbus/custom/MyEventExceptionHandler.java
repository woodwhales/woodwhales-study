package org.woodwhales.guava.eventbus.custom;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.4 14:14
 * @description:
 */
public interface MyEventExceptionHandler {

    /**
     * 对 event 异常处理
     * @param cause
     * @param eventContext
     */
    void handle(Throwable cause, MyEventContext eventContext);

}
