package org.woodwhales.countdownlatch;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

import lombok.Data;

/**
 * 抽象类，用于演示危险化工品车监控中心，统一检查
 *
 */
@Data
public abstract class DangerCenter implements Runnable {

	private CountDownLatch countDownLatch;	// 计数器
	private String station; 				// 危险品调度站
	private boolean ok;			// 检查结果，是否满足发车条件

	public DangerCenter(CountDownLatch countDownLatch, String stationName) {
		this.countDownLatch = countDownLatch;
		this.station = stationName;
		this.ok = false;
	}

	@Override
	public void run() {
		try {
			check();
			ok = true;
		} catch (Exception e) {
			e.printStackTrace();
			ok = false;
		} finally {
			if (Objects.nonNull(countDownLatch)) {
				countDownLatch.countDown();
			}
		}

	}

	/**
	 * 定义抽象的check方法，用于实现了DangerCenter的各级调度中心来重写check方法，因为每个调度中心自检的方法可能不一样
	 */
	public abstract void check();

}