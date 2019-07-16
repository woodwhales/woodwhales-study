package org.woodwhales.concurrent.code24;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *  输出结果：
 *  java.util.concurrent.ThreadPoolExecutor@7cca494b
 *  [Running, pool size = 5, active threads = 5,
 *  queued tasks = 1, completed tasks = 0]
 *  false
 *  true
 *  java.util.concurrent.ThreadPoolExecutor@7cca494b
 *  [Shutting down, pool size = 5, active threads = 5,
 *  queued tasks = 1, completed tasks = 0]
 *  pool-1-thread-3
 *  pool-1-thread-1
 *  pool-1-thread-5
 *  pool-1-thread-4
 *  pool-1-thread-2
 *  pool-1-thread-3
 *  true
 *  true
 *  java.util.concurrent.ThreadPoolExecutor@7cca494b
 *  [Terminated, pool size = 0, active threads = 0,
 *  queued tasks = 0, completed tasks = 6]
 *
 *  从输出结果可以看出，线程池维护着两个队列，一个叫queued tasks另一个叫completed tasks
 */
public class T05_ThreadPool {

    public static void main(String[] args) throws InterruptedException {
        // 创建一个可容纳固定大小为5个线程的线程池，返回 ExecutorService
        // ExecutorService 可以使用 execute 或者 submit方法
        ExecutorService service = Executors.newFixedThreadPool(5);
        for(int i = 0; i < 6; i++) {
            service.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }
        System.out.println(service);

        /**
         * isShutDown当调用shutdown()或shutdownNow()方法后返回为true。 
         * isTerminated当调用shutdown()方法后，并且所有提交的任务完成后返回为true;
         * isTerminated当调用shutdownNow()方法后，成功停止后返回为true;
         */
        service.shutdown(); // 正常关闭
        System.out.println(service.isTerminated());
        System.out.println(service.isShutdown());
        System.out.println(service);

        TimeUnit.SECONDS.sleep(5);
        System.out.println(service.isTerminated());
        System.out.println(service.isShutdown());
        System.out.println(service);
    }
}
