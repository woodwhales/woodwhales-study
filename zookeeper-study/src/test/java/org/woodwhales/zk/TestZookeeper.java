package org.woodwhales.zk;

import java.util.List;
import java.util.Objects;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestZookeeper {

	// 链接zk集群，使用英文逗号分隔
	private String connectString = "127.0.0.1:2181";
	
	// 链接超时时间
	private int sessionTimeout = 2000;
	
	private ZooKeeper zkClient;

	@Before
	public void init() throws Exception {
		zkClient = new ZooKeeper(connectString, sessionTimeout, (watchedEvent) -> {
			log.info("event = {}", watchedEvent);
			
			String path = watchedEvent.getPath();
			EventType type = watchedEvent.getType();
			
			// 获取事件类型
			log.info("type = {}", type.name());

			System.out.println(path);

			List<String> children;
			try {
				// 获取并监听节点变化
				children = zkClient.getChildren("/", true);
				children.stream().forEach(System.out::println);
			} catch (KeeperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	// 创建节点
	@Test
	public void testCreateNode() throws KeeperException, InterruptedException {
		String path = zkClient.create("/woodwhales", "woodwhales.cn".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println(path);
		
		
		// 当前链接的会话id
		long sessionId = zkClient.getSessionId();

		// 当前会话id的十六进制编号
		String sessionIdStr = Long.toHexString(sessionId);
		System.out.println("sessionId = " + sessionIdStr);
		
		// 当前链接的会话链接密码
		byte[] sessionPasswd = zkClient.getSessionPasswd();
	}
	
	// 异步创建创建节点
	@Test
	public void testCreateNodeAsync() throws KeeperException, InterruptedException {
		String context = "{\"code\":0}";
		// 异步创建，没有返回值，只在回调函数中获取创建成功的结果
		zkClient.create("/woodwhales", "woodwhales.cn".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, (int rc, String path, Object ctx, String name) -> {
			// 创建成功返回 0
			System.out.println("rc => " + rc);
			
			// 创建的节点path
			System.out.println("path => " + path);
			
			// 异步创建时带的额外信息
			System.out.println("ctx => " + (String)ctx);
			
			// 创建成功之后的节点名称，一般和path一样，除非带顺序的节点
			System.out.println("name => " + name);
		}, context);
		
		// 睡3毫秒，保证回调函数能来得及执行
		Thread.sleep(3000L);
	}
	
	// 获取节点
	@Test
	public void testGetData() throws KeeperException, InterruptedException {
		List<String> children = zkClient.getChildren("/", false);
		
		children.stream().forEach(System.out::println);
	}
	
	// 监听节点变化
	@Test
	public void testGetDataAndWatch() throws InterruptedException, KeeperException {
		Thread.sleep(Long.MAX_VALUE);
	}
	
	// 判断节点是否存在
	@Test
	public void testExists() throws InterruptedException, KeeperException {
		Stat stat = zkClient.exists("/woodwhales", false);
		
		if(Objects.isNull(stat)) {
			System.out.println("not existed");
		} else {
			System.out.println("existed");
			System.out.println("stat = " + stat);
		}
	}

}
