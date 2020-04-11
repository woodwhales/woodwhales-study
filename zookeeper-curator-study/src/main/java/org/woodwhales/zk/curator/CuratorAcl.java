package org.woodwhales.zk.curator;

import java.util.ArrayList;
import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.woodwhales.zk.curator.checkConfig.utils.AclUtils;

public class CuratorAcl {

	public CuratorFramework client = null;
	public static final String zkServerPath = "127.0.0.1:2181";

	public CuratorAcl() {
		RetryPolicy retryPolicy = new RetryNTimes(3, 5000);
		// authorization(List<AuthInfo> authInfos) 也可以对多个用户进行登陆认证
		client = CuratorFrameworkFactory.builder().authorization("digest", "woodwhales1:123456".getBytes())
				.connectString(zkServerPath)
				.sessionTimeoutMs(10000).retryPolicy(retryPolicy)
				.namespace("workspace").build();
		client.start();
	}
	
	public void closeZKClient() {
		if (client != null) {
			this.client.close();
		}
	}
	
	public static void main(String[] args) throws Exception {
		// 实例化
		CuratorAcl cto = new CuratorAcl();
		boolean isZkCuratorStarted = cto.client.isStarted();
		System.out.println("当前客户的状态：" + (isZkCuratorStarted ? "连接中" : "已关闭"));
		
		String nodePath = "/acl/father/child/sub";
		
		List<ACL> acls = new ArrayList<ACL>();
		Id woodwhales1 = new Id("digest", AclUtils.getDigestUserPwd("woodwhales1:123456"));
		Id woodwhales2 = new Id("digest", AclUtils.getDigestUserPwd("woodwhales2:123456"));
		acls.add(new ACL(Perms.ALL, woodwhales1));
		acls.add(new ACL(Perms.READ, woodwhales2));
		acls.add(new ACL(Perms.DELETE | Perms.CREATE, woodwhales2));
		
		// 创建节点
		byte[] data = "spiderman".getBytes();
		cto.client.create().creatingParentsIfNeeded()
				.withMode(CreateMode.PERSISTENT)
				.withACL(acls, true) // 开启递归设置权限，仅限当前创建的节点，以前存在的节点是不会设置权限的
				.forPath(nodePath, data);
		

		cto.client.setACL().withACL(acls).forPath("/curatorNode");

		cto.closeZKClient();
		boolean isZkCuratorStarted2 = cto.client.isStarted();
		System.out.println("当前客户的状态：" + (isZkCuratorStarted2 ? "连接中" : "已关闭"));
	}
	
}