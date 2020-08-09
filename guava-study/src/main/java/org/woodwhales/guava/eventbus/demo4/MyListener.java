package org.woodwhales.guava.eventbus.demo4;

import com.google.common.eventbus.Subscribe;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.2 22:49
 * @description:
 */
public class MyListener {

    @Subscribe
    public void method1(Integer sum) {
        System.out.println("MyListener.method1 => " + sum);
    }

    @Subscribe
    public void method2(Integer sum) {
        System.out.println("MyListener.method2 => " + sum);
    }

    @Subscribe
    public void method3(Integer sum) {
        int i = sum / 0;
        System.out.println("MyListener.method3 => " + sum);
    }

}
