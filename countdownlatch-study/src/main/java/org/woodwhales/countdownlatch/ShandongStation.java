package org.woodwhales.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class ShandongStation extends DangerCenter {

	private static String stationName = "山东危险品调度站";

	public ShandongStation(CountDownLatch countDownLatch) {
		super(countDownLatch, stationName);
	}

	@Override
	public void check() {
		System.out.println(stationName + "车辆自检开始");

		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(stationName + "车辆自检完毕，一切正常");
	}
}