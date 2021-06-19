package org.woodwhales.io;

import org.junit.Test;

import java.io.*;

/**
 * 使用 BufferedReader 和 BufferedWriter 增强对 FileReader 和 FileWriter 使用
 * 实现文件拷贝
 * 注意：BufferedReader 和 BufferedWriter 不要操作二进制文件，只能操作文本文件
 */
public class Demo10 extends BaseDemo {

    @Test
    public void test() {
        String source = basePath + testStringContentFileName;
        String target = basePath + "back1.txt";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(source));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(target));){

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
