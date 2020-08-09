package org.woodwhales.guava.eventbus.custom;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.4 13:55
 * @description: Bus 接口
 */
public interface MyBus {

    /**
     * 注册到 bus
     * @param subscriber 订阅者
     */
    void register(Object subscriber);

    /**
     * 从bus中取消注册
     * @param subscriber
     */
    void unRegister(Object subscriber);

    /**
     * 发送消息
     * @param event
     */
    void post(Object event);

    /**
     * 发送消息
     * @param event
     * @param topic
     */
    void post(Object event, String topic);


    /**
     * 关闭bus
     */
    void close();

    /**
     * 获取当前bus名称
     * @return
     */
    String getBusName();

}
