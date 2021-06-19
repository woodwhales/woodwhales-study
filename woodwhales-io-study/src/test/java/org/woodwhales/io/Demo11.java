package org.woodwhales.io;

import org.junit.Test;

import java.io.*;

/**
 *
 * 使用 BufferedInputStream 和 BufferedOutputStream 增强对 FileInputStream 和 FileOutputStream 使用，实现文件拷贝
 *
 */
public class Demo11 extends BaseDemo {

    @Test
    public void test() {
        String source = basePath + testStringContentFileName;
        String target = basePath + "back2.jpg";
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(target))){
            byte[] buffer = new byte[16];
            int readLength;
            while((readLength = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, readLength);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
