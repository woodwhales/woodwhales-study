package org.woodwhales.io;

import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * 使用 java.io.OutputStreamWriter 输出指定字符集文本内容
 */
public class Demo15 extends BaseIODemo {

    @Test
    public void test() {
        String charsetName = "gbk";
        String content = "这是一段gbk编码的文本内容";
        try (FileOutputStream fileOutputStream = new FileOutputStream(basePath + "testGbk.txt");
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bufferedOutputStream, charsetName)) {
            outputStreamWriter.write(content);
            outputStreamWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
