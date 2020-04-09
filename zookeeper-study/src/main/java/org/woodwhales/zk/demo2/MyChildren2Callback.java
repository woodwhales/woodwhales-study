package org.woodwhales.zk.demo2;

import java.util.List;

import org.apache.zookeeper.AsyncCallback.Children2Callback;
import org.apache.zookeeper.data.Stat;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyChildren2Callback implements Children2Callback {

	@Override
	public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
		for (String child : children) {
			log.info("child => {}", child);
		}
		
		log.info("ChildrenCallback => {}", path);
		log.info("ctx => {}", (String)ctx);
		log.info("stat => {}", stat.toString());
	}

}
