package org.woodwhales.guava.compare;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author woodwhales on 2020-08-10
 * @description 排序工具 ComparisonChain
 * 它执行比较操作直至发现非零的结果，在那之后的比较输入将被忽略
 *
 * Ordering
 *
 * natural()
 * 对可排序类型做自然排序，如数字按大小，日期按先后排序
 *
 *
 */
public class ComparisonChainTest {

    @Test
    public void testComparisonChain() {

        Student student1 = new Student("张三", 10, "北京");
        Student student2 = new Student("李四", 10, "南京");
        Student student3 = new Student("王五", 10, null);
        Student student4 = new Student("小王五", 10, null);

        List<Student> list = new ArrayList<>();
        list.add(student1);
        list.add(student2);
        list.add(student3);
        list.add(student4);

        // 年龄一样，则将 null 值排序在最后
        list.stream().sorted(Student::compareToNullsLast)
                    .collect(Collectors.toList())
                    .forEach(System.out::println);

        System.out.println("============");

        // 年龄一样，则将 null 值排序在最前
        list.stream().sorted(Student::compareToNullsFirst)
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

}
