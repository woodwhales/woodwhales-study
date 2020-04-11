package org.woodwhales.zk.demo2;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZKNodeExist implements Watcher {

	private ZooKeeper zookeeper = null;

	public static final String zkServerPath = "127.0.0.1:2181";

	public static final Integer timeout = 5000;

	public ZKNodeExist() {
	}

	public ZKNodeExist(String connectString) {
		try {
			zookeeper = new ZooKeeper(connectString, timeout, new ZKNodeExist());
		} catch (IOException e) {
			e.printStackTrace();
			if (zookeeper != null) {
				try {
					zookeeper.close();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private static CountDownLatch countDown = new CountDownLatch(1);

	public static void main(String[] args) throws Exception {

		ZKNodeExist zkServer = new ZKNodeExist(zkServerPath);

		/**
		 * 参数： path：节点路径 watch：watch
		 */
		Stat stat = zkServer.getZookeeper().exists("/woodwhales-fake", true);
		if (stat != null) {
			log.info("查询的节点版本, dataVersion => ", stat.getVersion());
		} else {
			log.info("该节点不存在");
		}

		countDown.await();
	}

	@Override
	public void process(WatchedEvent event) {
		if (event.getType() == EventType.NodeCreated) {

			log.info("节点创建");
			countDown.countDown();
			
		} else if (event.getType() == EventType.NodeDataChanged) {
			
			log.info("节点数据改变");
			countDown.countDown();
			
		} else if (event.getType() == EventType.NodeDeleted) {
			
			log.info("节点删除");
			countDown.countDown();
			
		}
	}

	public ZooKeeper getZookeeper() {
		return zookeeper;
	}

	public void setZookeeper(ZooKeeper zookeeper) {
		this.zookeeper = zookeeper;
	}
}