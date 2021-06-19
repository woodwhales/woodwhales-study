package org.woodwhales.io;

import org.junit.Test;

import java.io.*;

/**
 * java.io.BufferedWriter 包装类
 */
public class Demo9 extends BaseDemo {

    @Test
    public void test() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(basePath + "test2.txt"))) {
            bufferedWriter.write("woodwhales.cn/1");
            bufferedWriter.newLine();
            bufferedWriter.write("woodwhales.cn/2");
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
