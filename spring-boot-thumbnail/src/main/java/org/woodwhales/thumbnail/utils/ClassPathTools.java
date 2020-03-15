package org.woodwhales.thumbnail.utils;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

/**
 * @projectName: spring-boot-image
 * @author: woodwhales
 * @date: 20.3.12 22:50
 * @description:
 */
public class ClassPathTools {

    private  ClassPathTools () {}

    public static String buildChildFileAbsolutePath(String childPath, String fileName) {
        return buildChildFile(childPath, fileName).getAbsolutePath() + File.separator + fileName;
    }

    /**
     *
     * @param childPath 工程根目录（classpath）下的子目录路径，
     * @param fileName 文件名称，需要带后缀名
     * @return 工程根目录（classpath）下的 childPath 目录下的 fileName 的 File 对象
     */
    public static File getClassPathChildFile(String childPath, String fileName) {
        File childFile = buildChildFile(childPath, fileName);
        return new File (childFile.getAbsolutePath() + File.separator + fileName);
    }

    private static File buildChildFile(String childPath, String fileName) {
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Objects.requireNonNull(path, "获取系统根目录异常");

        if(!path.exists()) {
            path = new File("");
        }

        File childFileDir = new File(path.getAbsolutePath(), childPath);
        if(!childFileDir.exists()) {
            childFileDir.mkdirs();
        }
        return childFileDir;
    }
}
