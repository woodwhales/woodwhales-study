package org.woodwhales.io;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * java.io.FileOutputStream#write(int) 方法的使用
 */
public class Demo4 extends BaseDemo {

    /**
     * java.io.FileOutputStream#write(int)
     * 如果文件不存在则创建文件，将文件中内容覆盖，即默认是覆盖模式，不是追加模式
     *
     * 如果想使用追加模式，则使用 FileOutputStream(String name, boolean append) 构造函数， appped 显示传 true
     *
     */
    @Test
    public void test() {
        try (FileOutputStream fos = new FileOutputStream(basePath + "file1.txt");){
            // 输出一个字符到文件，字符会自动转字节
            fos.write('W');

            // 输出字符串到文件，将字符串转成字节数组
            String content = "woodwhales.cn";
            fos.write(content.getBytes());

            // 输出 byte[] 从 offset 开始的 length 长度字节数据至文件
            fos.write(content.getBytes(), 0, content.length());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
