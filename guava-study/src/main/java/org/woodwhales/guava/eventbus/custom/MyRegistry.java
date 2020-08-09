package org.woodwhales.guava.eventbus.custom;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.4 14:04
 * @description: 已订阅bus的订阅者容器对象
 */
public class MyRegistry {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<MySubscriber>> subscriberContainer = new ConcurrentHashMap<> ();

    /**
     * 绑定
     * @param subscriber
     */
    public void bind(Object subscriber) {
        List<Method> subscriberMethods = getSubscriberMethods(subscriber);
        subscriberMethods.forEach(method -> tireSubscriber(subscriber, method));
    }

    private void tireSubscriber(Object subscriber, Method method) {
        MySubscribe mySubscribe = method.getDeclaredAnnotation(MySubscribe.class);
        String topic = mySubscribe.topic();
        subscriberContainer.computeIfAbsent(topic, key -> new ConcurrentLinkedQueue<>());
        subscriberContainer.get(topic).add(new MySubscriber(subscriber, method));
    }

    private List<Method> getSubscriberMethods(Object subscriber) {
        List<Method> methods = new ArrayList<>();

        Class<?> subscriberClass = subscriber.getClass();
        while (Objects.nonNull(subscriberClass)) {
            Method[] declaredMethods = subscriberClass.getDeclaredMethods();

            Arrays.stream(declaredMethods).filter(method ->
                method.isAnnotationPresent(MySubscribe.class) &&
                        method.getParameterCount() == 1 &&
                        method.getModifiers() == Modifier.PUBLIC
            ).forEach(methods::add);

            // 获取当前 subscriber 的父类
            subscriberClass = subscriberClass.getSuperclass();
        }

        return methods;
    }

    /**
     * 解绑
     * @param subscriber
     */
    public void unBind(Object subscriber) {
        subscriberContainer.forEach((key, queue) -> {
            queue.forEach(subscriberItem -> {
                if(subscriberItem.getSubscribeObject() == subscriber) {
                    subscriberItem.setDisabled(false);
                }
            });
        });
    }

    /**
     * 根据topic获取已经绑定的监听对象
     * @param topic
     * @return
     */
    public ConcurrentLinkedQueue<MySubscriber> scanSubscriber(final String topic) {
        return subscriberContainer.get(topic);
    }

}
