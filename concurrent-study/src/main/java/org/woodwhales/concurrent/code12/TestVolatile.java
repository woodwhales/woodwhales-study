package org.woodwhales.concurrent.code12;

import java.util.ArrayList;
import java.util.List;

/**
 * volatile并不能保证多个线程共同修改running变量时所带来的不一致问题，
 * 也就是说volatile不能替代synchronized
 * 运行下面的程序，并分析结果
 */
public class TestVolatile {
    volatile int count = 0;

    void m() {
        for(int i = 0; i < 10000; i++) count++;
    }

    public static void main(String[] args) {
        TestVolatile t = new TestVolatile();

        List<Thread> threads = new ArrayList<Thread>();

        for(int i = 0; i < 10; i++) {
            threads.add(new Thread(t::m, "thread-" + i));
        }

        // 每个线程加到 10000 数, 目的想要10个线程一共加到100000
        threads.forEach((o) -> o.start());

        threads.forEach((o) -> {
            try {
                o.join();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 从结果可以看到,volatile不能保证原子性,只能保证原子性
        System.out.println(t.count);

    }

}


