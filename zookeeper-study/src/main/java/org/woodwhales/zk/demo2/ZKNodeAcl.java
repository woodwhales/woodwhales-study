package org.woodwhales.zk.demo2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.woodwhales.zk.demo2.utils.AclUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZKNodeAcl implements Watcher {

	private ZooKeeper zookeeper = null;

	public static final String zkServerPath = "127.0.0.1:2181";

	public static final Integer timeout = 5000;

	public ZKNodeAcl() {
	}

	public ZKNodeAcl(String connectString) {
		try {
			zookeeper = new ZooKeeper(connectString, timeout, new ZKNodeAcl());
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

	public void createZKNode(String path, byte[] data, List<ACL> acls) {

		String result = "";
		try {
			/**
			 * 同步或者异步创建节点，都不支持子节点的递归创建，异步有一个callback函数 参数： path：创建的路径 data：存储的数据的byte[]
			 * acl：控制权限策略 Ids.OPEN_ACL_UNSAFE --> world:anyone:cdrwa CREATOR_ALL_ACL -->
			 * auth:user:password:cdrwa createMode：节点类型, 是一个枚举 PERSISTENT：持久节点
			 * PERSISTENT_SEQUENTIAL：持久顺序节点 EPHEMERAL：临时节点 EPHEMERAL_SEQUENTIAL：临时顺序节点
			 */
			result = zookeeper.create(path, data, acls, CreateMode.PERSISTENT);
			log.info("创建节点：{} 成功", result);
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		ZKNodeAcl zkServer = new ZKNodeAcl(zkServerPath);

		/**
		 * ====================== 创建node start ======================
		 */
		// 1. acl 任何人都可以访问, world:anyone:cdrwa
		zkServer.createZKNode("/calwoodwhales", "test".getBytes(), Ids.OPEN_ACL_UNSAFE);

		// 2. 自定义用户认证访问
		List<ACL> acls = new ArrayList<ACL>();
		Id woodwhales1 = new Id("digest", AclUtils.getDigestUserPwd("woodwhales1:123456"));
		Id woodwhales2 = new Id("digest", AclUtils.getDigestUserPwd("woodwhales2:123456"));
		
		acls.add(new ACL(Perms.ALL, woodwhales1));											// 给 woodwhales1 账号全部权限
		acls.add(new ACL(Perms.READ, woodwhales2));											// 给 woodwhales2 账号读取权限
		acls.add(new ACL(Perms.DELETE | Perms.CREATE, woodwhales2));						// 给 woodwhales2 账号删除和创建权限
		zkServer.createZKNode("/calwoodwhales/testdigest", "testdigest".getBytes(), acls);	// 创建子节点并设置权限

		// 3. 注册过的用户必须通过addAuthInfo才能操作节点，参考命令行 addauth
		zkServer.getZookeeper().addAuthInfo("digest", "woodwhales1:123456".getBytes());
		zkServer.createZKNode("/calwoodwhales/testdigest/childtest", "childtest".getBytes(), Ids.CREATOR_ALL_ACL);
		Stat stat1 = new Stat();
		
		byte[] data1 = zkServer.getZookeeper().getData("/calwoodwhales/testdigest", false, stat1);
		log.info("data => {}", new String(data1));
		zkServer.getZookeeper().setData("/calwoodwhales/testdigest", "now".getBytes(), 1);

		// ip方式的acl
		List<ACL> aclsIP = new ArrayList<ACL>();
		Id ipId = new Id("ip", "192.168.0.104");
		aclsIP.add(new ACL(Perms.ALL, ipId));
		zkServer.createZKNode("/calwoodwhales/iptest6", "iptest".getBytes(), aclsIP);

		// 验证ip是否有权限
		zkServer.getZookeeper().setData("/calwoodwhales/iptest6", "now".getBytes(), 1);
		
		Stat stat = new Stat();
		byte[] data2 = zkServer.getZookeeper().getData("/calwoodwhales/iptest6", false, stat);
		
		log.info("data => {}", new String(data2));
		log.info("version => {}", stat.getVersion());
	}

	public ZooKeeper getZookeeper() {
		return zookeeper;
	}

	@Override
	public void process(WatchedEvent event) {

	}
}