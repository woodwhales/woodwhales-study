package org.woodwhales.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class ShanxiStation extends DangerCenter {

	private static String stationName = "陕西危险品调度站";

	public ShanxiStation(CountDownLatch countDownLatch) {
		super(countDownLatch, stationName);
	}

	@Override
	public void check() {
		System.out.println(stationName + "车辆自检开始");

		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(stationName + "车辆自检完毕，一切正常");
	}
}