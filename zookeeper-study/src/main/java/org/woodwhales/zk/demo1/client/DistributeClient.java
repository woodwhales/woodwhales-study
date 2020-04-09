package org.woodwhales.zk.demo1.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DistributeClient {

	private String connectString = "localhost:2181";
	
	private int sessionTimeout = 2000;
	
	private ZooKeeper zkClient;
	
	private String ROOT_PATH = "/servers";
		
	public static void main(String[] args) throws Exception {
		DistributeClient distributeClient = new DistributeClient();
		
		// 链接zk集群
		distributeClient.getConnection();
		
		// 业务逻辑
		distributeClient.business();
	}
	
	private void getChildren(String rootPath) throws KeeperException, InterruptedException {

		List<String> children = zkClient.getChildren(rootPath, true);
		
		// 存储服务器节点上主机名称集合
		ArrayList<String> hosts = new ArrayList<>(children.size());
		
		if(Objects.isNull(hosts) || hosts.size() == 0) {
			return;
		}
		
		for (String child : children) {
			
			byte[] data = zkClient.getData(ROOT_PATH + "/" + child, false, null);
			
			hosts.add(new String(data));
		}
		
		hosts.stream().forEach(host -> {
			log.info("host => {}", host);
		});
		
	}
	
	/**
	 * 检查是否有根节点，没有根节点则创建跟节点
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	private String check() throws KeeperException, InterruptedException, IOException {
		
		ZooKeeper _zkClient = new ZooKeeper(connectString, sessionTimeout,(watcherEvent)-> {});
		
		// 判断是否存在根节点
		Stat rootPathStat = _zkClient.exists(ROOT_PATH, false);
		
		String rootPath = "";
		
		// 不存在根节点就创建永久性根节点
		if(Objects.isNull(rootPathStat)) {
			rootPath = _zkClient.create(ROOT_PATH, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} else {
			rootPath = ROOT_PATH;
		}
			
		_zkClient.close();
		return rootPath;
	}

	private void business() throws InterruptedException {
		Thread.sleep(Long.MAX_VALUE);
	}

	private void getConnection() throws IOException, KeeperException, InterruptedException {
		// 检查是否有根节点
		String rootPath = check();
		
		zkClient.close();

		// 监听根节点
		zkClient = new ZooKeeper(connectString, sessionTimeout, (watcherEvent) -> {
			try {
				getChildren(rootPath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
	}
}
