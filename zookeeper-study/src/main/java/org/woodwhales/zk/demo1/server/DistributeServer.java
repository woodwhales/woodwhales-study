package org.woodwhales.zk.demo1.server;

import java.io.IOException;
import java.util.Objects;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DistributeServer {

	private String connectString = "localhost:2181";
	
	private int sessionTimeout = 2000;
	
	private ZooKeeper zkClient;
	
	private String ROOT_PATH = "/servers";
	
	public static void main(String[] args) throws Exception {
		
		DistributeServer distributeServer = new DistributeServer();
		
		// 链接zk集群
		distributeServer.getConnection();
		
		// 注册服务
		distributeServer.regist(args[0]);
		
		// 业务逻辑
		distributeServer.business();
	}

	private void business() throws InterruptedException {
		Thread.sleep(Long.MAX_VALUE);
	}

	private void regist(String hostName) throws KeeperException, InterruptedException {
		String rootPath = check();
		
		// 创建带序号的临时节点
		String registedPath = zkClient.create(rootPath + "/server", hostName.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		
		log.info("{} was onlined, registed path => {}", hostName, registedPath);
	}

	/**
	 * 检查是否有根节点，没有根节点则创建跟节点
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	private String check() throws KeeperException, InterruptedException {
		Stat rootPathStat = zkClient.exists(ROOT_PATH, false);
		
		if(Objects.isNull(rootPathStat)) {
			return zkClient.create(ROOT_PATH, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		
		return ROOT_PATH;
	}

	private void getConnection() throws IOException {
		zkClient = new ZooKeeper(connectString, sessionTimeout, (watcherEvent) -> {
			
		});
	}
	
}
