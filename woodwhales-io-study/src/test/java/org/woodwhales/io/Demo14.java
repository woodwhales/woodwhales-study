package org.woodwhales.io;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 使用 java.io.InputStreamReader 获取指定字符集的输入流
 */
public class Demo14 extends BaseIODemo {

    /**
     * 使用 BufferedInputStream 读取和程序不是一致的字符集文件时，读取到的文件内容是乱码的
     * 解决方案参见 test2()
     */
    @Test
    public void test1() {
        try (FileInputStream fileInputStream = new FileInputStream(basePath + testGbkFileName);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {
            byte[] buffer = new byte[16];
            int readLength;
            while((readLength = bufferedInputStream.read(buffer)) != -1) {
                String lineContent = new String(buffer, 0, readLength);
                System.out.println(lineContent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用 InputStreamReader 读取指定字符集文件内容
     *
     */
    @Test
    public void test2() {
        try (FileInputStream fileInputStream = new FileInputStream(basePath + testGbkFileName);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
             // 指定输入流的字符集编码
             InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream, "gbk")) {
            char[] buffer = new char[16];
            int readLength;
            while((readLength = inputStreamReader.read(buffer)) != -1) {
                String lineContent = new String(buffer, 0, readLength);
                System.out.println(lineContent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
