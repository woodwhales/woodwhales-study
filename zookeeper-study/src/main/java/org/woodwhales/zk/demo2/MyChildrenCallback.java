package org.woodwhales.zk.demo2;

import java.util.List;

import org.apache.zookeeper.AsyncCallback.ChildrenCallback;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyChildrenCallback implements ChildrenCallback {

	@Override
	public void processResult(int rc, String path, Object ctx, List<String> children) {
		for (String child : children) {
			log.info("child => {}", child);
		}
		
		log.info("ChildrenCallback => {}", path);
		log.info("ctx => {}", (String)ctx);
		
	}
	
}
