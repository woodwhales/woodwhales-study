package org.woodwhales.io;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * java.io.FileInputStream#read() 方法的使用
 * java.io.FileInputStream#read(byte[]) 方法的使用
 */
public class Demo3 extends BaseDemo {

    /**
     * 使用 FileInputStream 读取文件内容
     *
     * java.io.FileInputStream#read() 方法使用一个字节一个字节的读取文件内容
     * 效率比较低
     */
    @Test
    public void testFileInputStreamRead1() {
        try (FileInputStream fis = new FileInputStream(testContentFile)){
            int byteContent;
            while ((byteContent = fis.read()) != -1) {
                System.out.print((char)byteContent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用 FileInputStream 读取文件内容
     *
     * java.io.FileInputStream#read(byte[]) 一次性读取 byte[] 长度的字节内容，
     * 如果读取正常，则返回实际读取到的字节数大小
     * 如果读取到文件内容末尾，则返回 -1
     * 效率比较低
     */
    @Test
    public void testFileInputStreamRead2() {
        byte[] buffer = new byte[8];
        try (FileInputStream fis = new FileInputStream(testContentFile)){
            // 实际读取到的字符长度
            int readByteContentLength;
            while ((readByteContentLength = fis.read(buffer)) != -1) {
                System.out.print(new String(buffer, 0, readByteContentLength));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
