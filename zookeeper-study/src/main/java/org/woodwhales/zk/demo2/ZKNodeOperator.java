package org.woodwhales.zk.demo2;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZKNodeOperator implements Watcher {

	private ZooKeeper zookeeper = null;
	
	public static final String zkServerPath = "127.0.0.1:2181";
	
	public static final Integer timeout = 5000;
	
	public ZKNodeOperator() {}
	
	public ZKNodeOperator(String connectString) {
		try {
			zookeeper = new ZooKeeper(connectString, timeout, new ZKNodeOperator());
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
	
	/**
	 * 
	 * @Title: ZKOperatorDemo.java
	 * @Description: 创建zk节点
	 */
	public void createZKNode(String path, byte[] data, List<ACL> acls) {
		
		String result = "";
		try {
			/**
			 * 同步或者异步创建节点，都不支持子节点的递归创建，异步有一个callback函数
			 * 参数：
			 * path：创建的路径
			 * data：存储的数据的byte[]
			 * acl：控制权限策略
			 * 			Ids.OPEN_ACL_UNSAFE --> world:anyone:cdrwa
			 * 			CREATOR_ALL_ACL --> auth:user:password:cdrwa
			 * createMode：节点类型, 是一个枚举
			 * 			PERSISTENT：持久节点
			 * 			PERSISTENT_SEQUENTIAL：持久顺序节点
			 * 			EPHEMERAL：临时节点
			 * 			EPHEMERAL_SEQUENTIAL：临时顺序节点
			 */
			result = zookeeper.create(path, data, acls, CreateMode.PERSISTENT);
			
//			String ctx = "{'create':'success'}";
//			zookeeper.create(path, data, acls, CreateMode.PERSISTENT, new CreateCallBack(), ctx);
			
			log.info("创建节点 -> {} 成功", result);
			
			Thread.sleep(2000);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		ZKNodeOperator zkServer = new ZKNodeOperator(zkServerPath);
		
		// 创建zk节点
//		zkServer.createZKNode("/testnode", "testnode".getBytes(), Ids.OPEN_ACL_UNSAFE);
		
		/**
		 * 参数：
		 * path：节点路径
		 * data：数据
		 * version：数据状态
		 */
//		Stat status  = zkServer.getZookeeper().setData("/testnode", "xyz".getBytes(), 2);
//		System.out.println(status.getVersion());
		
		/**
		 * 参数：
		 * path：节点路径
		 * version：数据状态
		 */
		zkServer.createZKNode("/test-delete-node", "123".getBytes(), Ids.OPEN_ACL_UNSAFE);
//		zkServer.getZookeeper().delete("/test-delete-node", 2);
		
		String ctx = "{'delete':'success'}";
		zkServer.getZookeeper().delete("/test-delete-node", 0, new MyDeleteCallback(), ctx);
		Thread.sleep(2000);
	}

	public ZooKeeper getZookeeper() {
		return zookeeper;
	}

	@Override
	public void process(WatchedEvent event) {
	}
}