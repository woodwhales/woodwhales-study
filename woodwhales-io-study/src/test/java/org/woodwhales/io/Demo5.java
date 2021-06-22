package org.woodwhales.io;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 使用 FileInputStream 和 FileOutputStream 实现文件拷贝
 */
public class Demo5 extends BaseIODemo {

    @Test
    public void test() {
        String source = basePath + testImageFileName;
        String target = basePath + "back.jpg";

        try (FileInputStream fis = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(target);){

            byte[] buffer = new byte[1024];
            int readLength;
            while ((readLength = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, readLength);
            }
            System.out.println("copy file process success");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
