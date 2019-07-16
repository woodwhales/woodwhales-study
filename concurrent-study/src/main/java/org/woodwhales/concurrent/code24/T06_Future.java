package org.woodwhales.concurrent.code24;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * ExecutorService的sbmit法的返回类型就是Future接口
 */
public class T06_Future {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        FutureTask<Integer> task = new FutureTask<>(() -> {
            TimeUnit.MILLISECONDS.sleep(500);
            return 1000;
        }); // lambda表达式相当于 new Callable () { Integer call();}

        new Thread(task).start();

        System.out.println(task.get()); // 阻塞

        // ******************************
        ExecutorService service = Executors.newFixedThreadPool(5);
        Future<Integer> f = service.submit(() -> {
            TimeUnit.MILLISECONDS.sleep(500);
            return 1;
        });
        System.out.println(f.get());
        System.out.println(f.isDone());

    }
}
