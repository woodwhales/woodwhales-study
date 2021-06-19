package org.woodwhales.io;

import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;

/**
 * java.io.Writer#write(java.lang.String) 方法的使用
 * java.io.Writer#write(char[]) 方法的使用
 * java.io.OutputStreamWriter#write(int) 方法的使用，注意：写入文件的int数据会被转成字符写入文件
 *
 *
 * 写完数据之后，一定要使用 flush() 方法
 */
public class Demo7 extends BaseDemo {

    @Test
    public void test1() {
        try (FileWriter fileWriter = new FileWriter(basePath + "test1.txt")){
            String content = "这是中文，this is english";
            fileWriter.write(content);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        try (FileWriter fileWriter = new FileWriter(basePath + "test1.txt")){
            String content = "这是中文，this is english";
            fileWriter.write(content.toCharArray());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() {
        try (FileWriter fileWriter = new FileWriter(basePath + "test1.txt")){
            fileWriter.write(97);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test4() {
        try (FileWriter fileWriter = new FileWriter(basePath + "test1.txt")){
            char[] chars = "这是中文，this is english".toCharArray();
            fileWriter.write(chars, 0, chars.length);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test5() {
        try (FileWriter fileWriter = new FileWriter(basePath + "test1.txt")){
            String content = "这是中文，this is english";
            // 只写入前 4 个字符
            fileWriter.write(content, 0, 4);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
