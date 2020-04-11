package org.woodwhales.zk.curator;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryForever;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CuratorOperator {

	public CuratorFramework client = null;
	
	public static final String zkServerPath = "127.0.0.1:2181";
	
	/**
	 * 实例化zk客户端
	 */
	public CuratorOperator() {
		/**
		 * 1.重试api方式一
		 * 
		 * 推荐使用
		 * 
		 * curator链接zookeeper的策略:ExponentialBackoffRetry
		 * baseSleepTimeMs：初始sleep的时间
		 * maxRetries：最大重试次数
		 * maxSleepMs：最大重试时间
		 */
		RetryPolicy retryPolicy1 = new ExponentialBackoffRetry(1000, 5);
		
		/**
		 * 2.重试api方式二
		 * 
		 * 推荐使用
		 * 
		 * curator链接zookeeper的策略:RetryNTimes
		 * n：重试的次数
		 * sleepMsBetweenRetries：每次重试间隔的时间
		 */
		RetryPolicy retryPolicy2 = new RetryNTimes(3, 5000);
		
		/**
		 * 3.重试api方式三
		 * 
		 * curator链接zookeeper的策略:RetryOneTime
		 * sleepMsBetweenRetry:每次重试间隔的时间
		 */
		RetryPolicy retryPolicy3 = new RetryOneTime(3000);
		
		/**
		 * 4.重试api方式四
		 * 
		 * 永远重试，不推荐使用
		 * retryIntervalMs: 重试时间间隔
		 */
		RetryPolicy retryPolicy4 = new RetryForever(3000);
		
		/**
		 * 5.重试api方式五
		 * 
		 * curator链接zookeeper的策略:RetryUntilElapsed
		 * maxElapsedTimeMs:最大重试时间
		 * sleepMsBetweenRetries:每次重试间隔
		 * 重试时间超过maxElapsedTimeMs后，就不再重试
		 */
		RetryPolicy retryPolicy5 = new RetryUntilElapsed(2000, 3000);
		
		client = CuratorFrameworkFactory.builder()
				.connectString(zkServerPath)
				.sessionTimeoutMs(10000)
				.retryPolicy(retryPolicy1) // 使用第一种重试机制
				.namespace("workspace")
				.build();
		client.start();
	}
	
	/**
	 * 
	 * @Description: 关闭zk客户端连接
	 */
	public void closeZKClient() {
		if (Objects.nonNull(client)) {
			this.client.close();
		}
	}
	
	public static void main(String[] args) throws Exception {
		// 实例化
		CuratorOperator cto = new CuratorOperator();
		CuratorFrameworkState state = cto.client.getState();
		log.info("当前客户的状态：{}", (CuratorFrameworkState.STARTED == state ? "连接中" : "已关闭"));
		
		// 创建节点
		String nodePath = "/super/woodwhales";
		byte[] data = "superme".getBytes();
		cto.client
			.create()
			.creatingParentsIfNeeded()			// 可以递归创建节点
			.withMode(CreateMode.PERSISTENT)	// CreateMode：zk 原生的 api 
			.withACL(Ids.OPEN_ACL_UNSAFE)		// Ids： 原生的 api 
			.forPath(nodePath, data);
		
		// 更新节点数据
		byte[] newData = "batman".getBytes();
		Stat stat = cto.client.setData().withVersion(0).forPath(nodePath, newData);
		
		// 删除节点
		cto.client.delete()
				  .guaranteed()					// 如果删除失败，那么在后端还是继续会删除，直到成功
				  .deletingChildrenIfNeeded()	// 如果有子节点，就删除
				  .withVersion(0)
				  .forPath("/super/woodwhales");
		
		// 读取节点数据
		Stat stat2 = new Stat();
		byte[] data2 = cto.client.getData().storingStatIn(stat2).forPath(nodePath);
		System.out.println("节点" + nodePath + "的数据为: " + new String(data2));
		System.out.println("该节点的版本号为: " + stat2.getVersion());
		
		
		// 查询子节点
		List<String> childNodes = cto.client.getChildren().forPath(nodePath);
		System.out.println("开始打印子节点：");
		for (String s : childNodes) {
			System.out.println(s);
		}
		
				
		// 判断节点是否存在,如果不存在则为空
		Stat statExist = cto.client.checkExists().forPath(nodePath + "/abc");
		System.out.println(statExist);
		
		
		// watcher 事件  
		// 当使用usingWatcher的时候，监听只会触发一次，监听完毕后就销毁
		// CuratorWatcher 的接口process方法会抛出异常
		cto.client.getData().usingWatcher(new MyCuratorWatcher()).forPath(nodePath);
		
		// 使用原生的 Watcher 的接口process方法不会抛出异常
		cto.client.getData().usingWatcher(new MyWatcher()).forPath(nodePath);
		
		// 为节点添加watcher
		// NodeCache: 监听数据节点的变更，会触发事件
		final NodeCache nodeCache = new NodeCache(cto.client, nodePath);
		// buildInitial : 初始化的时候获取node的值并且缓存
		nodeCache.start(true);
		if (nodeCache.getCurrentData() != null) {
			System.out.println("节点初始化数据为：" + new String(nodeCache.getCurrentData().getData()));
		} else {
			System.out.println("节点初始化数据为空...");
		}
		
		nodeCache.getListenable().addListener(new NodeCacheListener() {
			@Override
			public void nodeChanged() throws Exception {
				if (Objects.isNull(nodeCache.getCurrentData())) {
					System.out.println("空");
					return;
				}
				String data = new String(nodeCache.getCurrentData().getData());
				System.out.println("节点路径：" + nodeCache.getCurrentData().getPath() + "数据：" + data);
			}
		});
		
		CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();
		
		
		// 为子节点添加watcher
		// PathChildrenCache: 监听数据节点的增删改，会触发事件
		String childNodePathCache =  nodePath;
		// cacheData: 设置缓存节点的数据状态
		final PathChildrenCache childrenCache = new PathChildrenCache(cto.client, childNodePathCache, true);
		/**
		 * StartMode: 初始化方式
		 * POST_INITIALIZED_EVENT：异步初始化，初始化之后会触发事件
		 * NORMAL：异步初始化
		 * BUILD_INITIAL_CACHE：同步初始化
		 */
		childrenCache.start(StartMode.POST_INITIALIZED_EVENT);
		
		List<ChildData> childDataList = childrenCache.getCurrentData();
		System.out.println("当前数据节点的子节点数据列表：");
		for (ChildData cd : childDataList) {
			String childData = new String(cd.getData());
			System.out.println(childData);
		}
		
		childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
			@Override
			public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
				
				switch (event.getType()) {
	                case INITIALIZED:
	                    log.info("子节点初始化ok...{}", event.getData().getPath());
	                    break;
	                case CHILD_ADDED:
	                	
	                	String path = event.getData().getPath();
						if (path.equals(ADD_PATH)) {
							log.info("添加子节点:{}", event.getData().getPath());
							log.info("子节点数据:{}", new String(event.getData().getData()));
						} else if (path.equals("/super/woodwhales/e")) {
							log.info("添加不正确...");
						}
						
	                    break;
	                case CHILD_REMOVED:
	                    log.info("删除子节点 : {}", event.getData().getPath());
	                    break;
	                case CHILD_UPDATED:
	                    log.info("修改子节点路径 : {}", event.getData().getPath());
	                    log.info("修改子节点数据: {}",new String(event.getData().getData()));
	                    break;
	                default:
	                    break;
				}
			}
				
		});
		
		Thread.sleep(3000);
		
		cto.closeZKClient();
		
		CuratorFrameworkState curatorFrameworkState = cto.client.getState();
		
		log.info("当前客户的状态: {}", (CuratorFrameworkState.STARTED == curatorFrameworkState ? "连接中" : "已关闭"));
	}
	
	public final static String ADD_PATH = "/super/woodwhales/d";
	
}
