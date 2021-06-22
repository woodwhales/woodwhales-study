package org.woodwhales.io;

import org.junit.After;
import org.junit.Before;

import java.io.File;

public class BaseIODemo {

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
    protected String testContentFileName = "testContent.txt";

    /**
     * 测试文件，含有文本内容
     */
    protected String testStringContentFileName = "testStringContent.txt";

    /**
     * 测试图片文件的文件名
     */
    protected String testImageFileName = "Inuyasha.jpg";

    /**
     * 内容字符编码为 gbk 格式的文件名
     */
    protected String testGbkFileName = "testGbkContent.txt";

    @Before
    public final void init() {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

        // target/test-classes 目录
        String resourcesPath = systemClassLoader.getResource("").getPath();
        basePath = resourcesPath;

        File baseFile = new File(basePath);
        File parentFile = baseFile.getParentFile();
        baseParentPath = parentFile.getAbsolutePath();
    }

    @After
    public final void after() {
        System.out.println("this test process is done");
    }

}
