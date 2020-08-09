package org.woodwhales.guava.eventbus.monitor;

import com.google.common.eventbus.EventBus;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.2 23:55
 * @description:
 */
public class MonitorClient {

    /**
     * 要监听的目录
     */
    private final static String TARGET = "D:\\monitor";

    public static void main(String[] args) throws Exception {
        final EventBus eventBus = new EventBus();
        eventBus.register(new FileChangeListener());

        TargetMonitor targetMonitor = new DirectoryTargetMonitor(eventBus, TARGET);

        // 创建定时的线程池
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        // 在定时线程中进行中断监听
        executorService.schedule(() -> {
            try {
                targetMonitor.stopMonitor();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }, 5, TimeUnit.SECONDS);
        // 关闭线程池
        executorService.shutdown();

        // 开始目录监听
        targetMonitor.startMonitor();
    }
}
