package org.woodwhales.guava.eventbus.monitor;

import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.*;
import java.util.Objects;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.2 23:35
 * @description:
 */
public class DirectoryTargetMonitor implements TargetMonitor {

    private final static Logger LOGGER = LoggerFactory.getLogger(DirectoryTargetMonitor.class);

    private volatile boolean isStarted = false;

    private WatchService watchService;

    private final EventBus eventBus;

    private final Path path;

    public DirectoryTargetMonitor(EventBus eventBus, String path) {
        this.eventBus = eventBus;
        this.path = Paths.get(path, "");
    }

    public DirectoryTargetMonitor(EventBus eventBus, String path, String... more) {
        this.eventBus = eventBus;
        this.path = Paths.get(path, more);
    }

    @Override
    public void startMonitor() throws Exception {
        watchService = FileSystems.getDefault().newWatchService();
        this.path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_CREATE);
        LOGGER.info("The directory [{}] is monitoring... ", path);

        this.isStarted = true;

        while (isStarted) {
            WatchKey watchKey = null;
            try {
                watchKey = this.watchService.take();
                watchKey.pollEvents().forEach(event -> {
                    Path path = (Path)event.context();
                    WatchEvent.Kind<?> kind = event.kind();
                    Path child = DirectoryTargetMonitor.this.path.resolve(path);
                    this.eventBus.post(new FileChangeEvent(kind, child));
                });
            } catch (Exception e) {
                this.isStarted = false;
            } finally {
                if(Objects.nonNull(watchKey)) {
                    watchKey.reset();
                }
            }
        }
    }

    @Override
    public void stopMonitor() throws Exception {
        LOGGER.info("The directory [{}] monitor will be stop...", path);
        Thread.currentThread().interrupt();
        this.isStarted = false;
        this.watchService.close();
        LOGGER.info("The directory [{}] monitor will be stop done.", path);
    }
}
