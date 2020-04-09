package org.woodwhales.zk.demo2;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import lombok.extern.slf4j.Slf4j;

/**
 * zookeeper 获取节点数据的demo演示
 *
 */
@Slf4j
public class ZKGetNodeData implements Watcher {

	private ZooKeeper zookeeper = null;

	public static final String zkServerPath = "127.0.0.1:2181";

	public static final Integer timeout = 5000;

	private static Stat stat = new Stat();
	
	private static CountDownLatch countDown = new CountDownLatch(1);

	public ZKGetNodeData() {

	}

	public ZKGetNodeData(String connectString) {
		try {
			zookeeper = new ZooKeeper(connectString, timeout, new ZKGetNodeData());
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
	
	public static void main(String[] args) throws Exception {
		
		ZKGetNodeData zkServer = new ZKGetNodeData(ZKGetNodeData.zkServerPath);
		
		/**
		 * 参数：
		 * path：节点路径
		 * watch：true或者false，注册一个watch事件
		 * stat：状态
		 */
		Stat stat = new Stat();
		byte[] resByte = zkServer.getZookeeper().getData("/woodwhales", true, stat);
		log.info("当前值 => {}", new String(resByte));
		countDown.await();
	}

	@Override
	public void process(WatchedEvent event) {
		try {
			
			if(event.getType() == EventType.NodeDataChanged){

				ZKGetNodeData zkServer = new ZKGetNodeData(zkServerPath);
				byte[] resByte = zkServer.getZookeeper().getData("/woodwhales", false, stat);
				log.info("更改后的值 => {}", new String(resByte));
				log.info("版本号变化的version => {}", stat.getVersion());
				countDown.countDown();
				
			} else if(event.getType() == EventType.NodeCreated) {
				
			} else if(event.getType() == EventType.NodeChildrenChanged) {
				
			} else if(event.getType() == EventType.NodeDeleted) {
				
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

}
