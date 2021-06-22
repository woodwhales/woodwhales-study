package org.woodwhales.io;

import org.junit.Test;

import java.io.*;

/**
 * 使用 java.io.ObjectInputStream 将文件中的数据反序列化
 */
public class Demo13 extends BaseIODemo {

    @Test
    public void test() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(basePath + "test.dat"))) {
            boolean b = ois.readBoolean();
            int i = ois.readInt();
            String s = ois.readUTF();
            double v = ois.readDouble();
            Dog o = (Dog)ois.readObject();

            System.out.println("b = " + b);
            System.out.println("i = " + i);
            System.out.println("s = " + s);
            System.out.println("v = " + v);
            System.out.println("o = " + o);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}
