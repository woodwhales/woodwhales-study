package org.woodwhales.io;

import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;

/**
 * FileReader 适合读取文本文件，因为 read() 方法按照字符读取
 * java.io.InputStreamReader#read() 方法的使用
 * java.io.Reader#read(char[]) 方法的使用
 *
 */
public class Demo6 extends BaseIODemo {

    /**
     *
     */
    @Test
    public void test1() {
        try (FileReader fileReader = new FileReader(basePath + "testCharContent.txt")){
            int data;
            while ((data = fileReader.read()) != -1) {
                System.out.print((char)data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        try (FileReader fileReader = new FileReader(basePath + "testCharContent.txt")){
            // 使用字符数组接收读取到的文件内容
            char[] buffer = new char[4];
            int readLength;
            while ((readLength = fileReader.read(buffer)) != -1) {
                System.out.print(new String(buffer, 0, readLength));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
