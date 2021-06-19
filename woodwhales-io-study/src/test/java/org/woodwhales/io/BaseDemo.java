package org.woodwhales.io;

import org.junit.Before;

import java.io.File;

public class BaseDemo {

    /**
     * 测试文件根目录
     */
    protected String basePath;

    /**
     * 测试父路径
     */
    protected String baseParentPath;

    /**
     * 测试文件，含有文本内容
     */
    protected File testContentFile;

    /**
     * 测试图片文件的文件名
     */
    protected String testImageFileName = "Inuyasha.jpg";

    /**
     * 测试图片文件
     */
    protected File testImageFile;

    @Before
    public void init() {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

        // target/test-classes 目录
        String resourcesPath = systemClassLoader.getResource("").getPath();
        basePath = resourcesPath;

        File parentFile = new File(basePath).getParentFile();
        baseParentPath = parentFile.getAbsolutePath();

        testContentFile = new File(systemClassLoader.getResource("testContent.txt").getPath());
        testImageFile = new File(systemClassLoader.getResource(testImageFileName).getPath());
    }



}
