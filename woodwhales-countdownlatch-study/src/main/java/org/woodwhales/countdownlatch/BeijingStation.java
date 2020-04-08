package org.woodwhales.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class BeijingStation extends DangerCenter {

	private static String stationName = "北京危险品调度站";

	public BeijingStation(CountDownLatch countDownLatch) {
		super(countDownLatch, stationName);
	}

	@Override
	public void check() {
		System.out.println(stationName + "车辆自检开始");

		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(stationName + "车辆自检完毕，一切正常");
	}
}