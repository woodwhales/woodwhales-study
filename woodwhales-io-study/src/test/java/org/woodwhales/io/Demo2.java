package org.woodwhales.io;

import org.junit.Test;

import java.io.File;

/**
 * File 对象的常用方法
 */
public class Demo2 extends BaseIODemo {

    @Test
    public void test() {
        File file = new File(basePath + "newFile1.txt");
        boolean exists = file.exists();
        System.out.println("exists1 = " + exists);
        System.out.println("exists2 = " + new File(basePath + "xxoo").exists());

        boolean isFile = file.isFile();
        System.out.println("isFile1 = " + isFile);
        System.out.println("isFile2 = " + new File(basePath + "xxoo").isFile());

        boolean directory = file.isDirectory();
        System.out.println("directory1 = " + directory);
        System.out.println("directory2 = " + new File(baseParentPath).isDirectory());

        String absolutePath = file.getAbsolutePath();
        System.out.println("absolutePath = " + absolutePath);

        String name = file.getName();
        System.out.println("name = " + name);

        String parent = file.getParent();
        System.out.println("parent1 = " + parent);
        System.out.println("parent2 = " + new File(baseParentPath).getParent());

        // 文件字节大小
        long length = file.length();
        System.out.println("length = " + length);
    }
}
