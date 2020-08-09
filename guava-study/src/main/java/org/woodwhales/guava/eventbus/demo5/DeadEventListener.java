package org.woodwhales.guava.eventbus.demo5;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.2 23:05
 * @description:
 */
public class DeadEventListener {

    @Subscribe
    public void handle(DeadEvent deadEvent) {
        System.out.println("DeadEventListener.handle deadEvent => " + deadEvent);
        System.out.println("DeadEventListener.handle event => " + deadEvent.getEvent());
        System.out.println("DeadEventListener.handle source => " + deadEvent.getSource());
    }
}
