package org.woodwhales.io;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Dog implements Serializable {

    /**
     * Java的序列化机制是通过在运行时判断类的serialVersionUID来验证版本一致性的。
     * 在进行反序列化时，JVM会把传来的字节流中的serialVersionUID与本地相应实体（类）的serialVersionUID进行比较，
     * 如果相同就认为是一致的，可以进行反序列化，否则就会出现序列化版本不一致的异常。
     *
     */
    private static final long serialVersionUID = 1L;

    private String name;
    private Integer age;
}
