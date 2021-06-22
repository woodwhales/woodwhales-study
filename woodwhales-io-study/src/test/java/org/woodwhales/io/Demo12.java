package org.woodwhales.io;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 使用 java.io.ObjectOutputStream 将数据序列化到文件中
 *
 * java.io.ObjectOutputStream#writeObject(java.lang.Object) 的参数对象必须实现 Serializable 接口
 */
public class Demo12 extends BaseIODemo {

    @Test
    public void test() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(basePath + "test.dat"))) {
          oos.writeBoolean(true);
            oos.writeInt(120);
            oos.writeUTF("wooodwhales.cn");
            oos.writeDouble(5.2);
            oos.writeObject(new Dog("旺财", 12 ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

