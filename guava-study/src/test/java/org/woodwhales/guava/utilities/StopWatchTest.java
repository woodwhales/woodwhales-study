package org.woodwhales.guava.utilities;

import com.google.common.base.Stopwatch;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.1 22:32
 * @description:
 */
public class StopWatchTest {

    /**
     * 记录起始时间，记录终止时间，自动格式化时间差
     * @throws InterruptedException
     */
    @Test
    public void testStopWatch() throws InterruptedException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        TimeUnit.SECONDS.sleep(1);
        System.out.println(stopwatch.stop());
    }
}
