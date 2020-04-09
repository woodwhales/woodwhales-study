package org.woodwhales.zk.curator;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyWatcher implements Watcher {

	@Override
	public void process(WatchedEvent event) {
		log.info("触发watcher，节点路径为：{}", event.getPath());
	}

}