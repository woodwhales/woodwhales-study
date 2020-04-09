package org.woodwhales.zk.demo2;

import org.apache.zookeeper.AsyncCallback.VoidCallback;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyDeleteCallback implements VoidCallback {
	
	@Override
	public void processResult(int rc, String path, Object ctx) {
		log.info("删除节点, path => {}", path);
		log.info("删除节点, ctx => {}", (String)ctx);
	}

}