package org.woodwhales.io;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * java.io.BufferedReader 包装类
 */
public class Demo8 extends BaseDemo {

    @Test
    public void test() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(basePath + testStringContentFileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
