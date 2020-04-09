package org.woodwhales.zk.demo2;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZKGetChildrenList implements Watcher {

	private ZooKeeper zookeeper = null;

	public static final String zkServerPath = "127.0.0.1:2181";

	public static final Integer timeout = 5000;

	public ZKGetChildrenList() {
	}

	public ZKGetChildrenList(String connectString) {
		try {
			zookeeper = new ZooKeeper(connectString, timeout, new ZKGetChildrenList());
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

		ZKGetChildrenList zkServer = new ZKGetChildrenList(zkServerPath);

		/**
		 * 参数： path：父节点路径 watch：true或者false，注册一个watch事件
		 */
		List<String> strChildList = zkServer.getZookeeper().getChildren("/woodwhales", true);
		for (String child : strChildList) {
			log.info("child => {}", child);
		}

		// 异步调用
		String ctx = "{'callback':'ChildrenCallback'}";
//		zkServer.getZookeeper().getChildren("/woodwhales", true, new MyChildrenCallback(), ctx);
		zkServer.getZookeeper().getChildren("/woodwhales", true, new MyChildren2Callback(), ctx);

		countDown.await();
	}

	@Override
	public void process(WatchedEvent event) {
		try {
			// 注意这个事件只会是当前父节点的子节点被创建或者删除才会触发，修改子节点的内容不会触发NodeChildrenChanged事件
			if (event.getType() == EventType.NodeChildrenChanged) {
				log.info("event type = NodeChildrenChanged");
				
				ZKGetChildrenList zkServer = new ZKGetChildrenList(zkServerPath);
				
				List<String> strChildList = zkServer.getZookeeper().getChildren(event.getPath(), false);
				for (String child : strChildList) {
					log.info("child => {}", child);
				}
				
				countDown.countDown();
				
			} else if (event.getType() == EventType.NodeCreated) {
				
				log.info("event type = NodeCreated");
				
			} else if (event.getType() == EventType.NodeDataChanged) {

				log.info("event type = NodeDataChanged");

			} else if (event.getType() == EventType.NodeDeleted) {
				
				log.info("event type = NodeDeleted");
				
			}
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public ZooKeeper getZookeeper() {
		return zookeeper;
	}

	public void setZookeeper(ZooKeeper zookeeper) {
		this.zookeeper = zookeeper;
	}

}
