package org.woodwhales.countdownlatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Test {
	
	public boolean checkAllStations() {
		// 开启计数器
    	CountDownLatch countDownLatch = new CountDownLatch(3);

        List<DangerCenter> stationList = new ArrayList<DangerCenter>();
        
        stationList.add(new BeijingStation(countDownLatch));
        stationList.add(new ShandongStation(countDownLatch));
        stationList.add(new ShanxiStation(countDownLatch));

        // 启动三个线程
        Executor stationThreadPool = Executors.newFixedThreadPool(stationList.size());

        for(DangerCenter stationCenter : stationList) {
			stationThreadPool.execute(stationCenter);
        }

        // 三个线程启动，就让计数器在主线程等待所有线程执行结束
        try {
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("所有检查站检查全部结束之后，核对每个检查站的检查结果");
        for(DangerCenter stationCenter : stationList) {
            if(!stationCenter.isOk()) {
                return false;
            }
        }

        return true;
        
	}
	
    public static void main(String[] args) {
    	Test test = new Test();
    	boolean result = test.checkAllStations();
    	System.out.println("检查结果 => " + result);
    	
    }
    
}
