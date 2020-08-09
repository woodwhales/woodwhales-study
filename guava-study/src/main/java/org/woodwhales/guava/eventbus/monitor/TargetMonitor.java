package org.woodwhales.guava.eventbus.monitor;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.2 23:34
 * @description:
 */
public interface TargetMonitor {

    /**
     * 开始监听
     * @throws Exception
     */
    void startMonitor() throws Exception;

    /**
     * 结束监听
     * @throws Exception
     */
    void stopMonitor() throws Exception;
}
