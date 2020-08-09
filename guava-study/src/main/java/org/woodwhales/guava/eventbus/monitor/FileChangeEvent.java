package org.woodwhales.guava.eventbus.monitor;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.2 23:51
 * @description:
 */
public class FileChangeEvent {

    private final WatchEvent.Kind<?> kind;

    private final Path path;

    public FileChangeEvent(WatchEvent.Kind<?> kind, Path path) {
        this.kind = kind;
        this.path = path;
    }

    public WatchEvent.Kind<?> getKind() {
        return kind;
    }

    public Path getPath() {
        return path;
    }
}
