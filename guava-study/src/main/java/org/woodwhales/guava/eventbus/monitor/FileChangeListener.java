package org.woodwhales.guava.eventbus.monitor;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.2 23:54
 * @description:
 */
public class FileChangeListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(FileChangeListener.class);

    @Subscribe
    public void onChange(FileChangeEvent event) {
        LOGGER.info("path => {}, kind => {}", event.getPath(), event.getKind());
    }
}
