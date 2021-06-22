package org.woodwhales.io;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * 创建文件的三种方式
 */
public class Demo1 extends BaseIODemo {

    /**
     * 创建文件方式1
     * @throws IOException
     */
    @Test
    public void testCreateFile1() throws IOException {
        String path = basePath + "newFile1.txt";
        System.out.println(path);
        File file = new File(path);
        boolean createFileSuccess = file.createNewFile();
        if(createFileSuccess) {
            System.out.println("createFileSuccess = " + createFileSuccess);
        }
    }

    /**
     * 创建文件方式2
     * @throws IOException
     */
    @Test
    public void testCreateFile2() throws IOException {
        File file = new File(new File(basePath), "newFile2.txt");
        boolean createFileSuccess = file.createNewFile();
        if(createFileSuccess) {
            System.out.println("createFileSuccess = " + createFileSuccess);
        }
    }

    /**
     * 创建文件方式3
     * @throws IOException
     */
    @Test
    public void testCreateFile3() throws IOException {
        File file = new File(basePath, "newFile3.txt");
        boolean createFileSuccess = file.createNewFile();
        if(createFileSuccess) {
            System.out.println("createFileSuccess = " + createFileSuccess);
        }
    }

}
