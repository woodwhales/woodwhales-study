package org.woodwhales.zk.curator;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyCuratorWatcher implements CuratorWatcher {

	@Override
	public void process(WatchedEvent event) throws Exception {
		log.info("触发watcher，节点路径为：{}",event.getPath());
	}

}