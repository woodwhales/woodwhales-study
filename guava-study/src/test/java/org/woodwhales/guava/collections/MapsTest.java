package org.woodwhales.guava.collections;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.*;

/**
 * @projectName: guava-study
 * @author: woodwhales
 * @date: 20.8.15 16:16
 * @description:
 */
public class MapsTest {

    /**
     * 将原始map中的key进行过滤，只保留满足条件的元素
     */
    @Test
    public void testFilter() {
        Map<String, Student> map = new HashMap<>();
        map.put("1", new Student("1", "zhangsan"));
        map.put("2", new Student("2", "zhaoliu"));
        map.put("3", new Student("3", "wangwu"));

        Map<String, Student> newMap = Maps.filterKeys(map, studentId ->
                Lists.newArrayList("2", "3").contains(studentId));
        newMap.entrySet().forEach(entry-> {
            System.out.println("key : " + entry.getKey() + " value : " + entry.getValue());
        });
    }

    /**
     * 将map中的value进行处理，并返回新的map
     */
    @Test
    public void testTransformValue() {
        Map<String, Student> map = new HashMap<>();
        map.put("1", new Student("1", "zhangsan"));
        map.put("2", new Student("2", "zhaoliu"));
        map.put("3", new Student("3", "wangwu"));
        Map<String, String> newMap = Maps.transformValues(map, Student::getName);
        newMap.entrySet().forEach(entry-> {
            System.out.println("key : " + entry.getKey() + " value : " + entry.getValue());
        });
    }

    /**
     * 将list中的元素作为key，function 中的函数取出value
     */
    @Test
    public void testToMap() {
        ArrayList<Student> list = Lists.newArrayList(new Student("1", "zhangsan"),
                new Student("2", "zhaoliu"),
                new Student("3", "wangwu"));

        ImmutableMap<Student, String> map = Maps.toMap(list, Student::getId);
        map.entrySet().forEach(entry-> {
            System.out.println("key : " + entry.getKey() + " value : " + entry.getValue());
        });
    }

    /**
     * 将list中的元素作为value，function 中的函数取出key
     */
    @Test
    public void testUniqueIndex() {
        ArrayList<Student> list = Lists.newArrayList(new Student("1", "zhangsan"),
                new Student("2", "zhaoliu"),
                new Student("3", "wangwu"));

        ImmutableMap<String, Student> map = Maps.uniqueIndex(list, Student::getId);
        map.entrySet().forEach(entry-> {
            System.out.println("key : " + entry.getKey() + " value : " + entry.getValue());
        });
    }


    /**
     * 将set中的元素作为key，function 中的函数取出value
     */
    @Test
    public void testAsMap() {
        HashSet<Student> set = Sets.newHashSet(new Student("1", "zhangsan"),
                new Student("2", "zhaoliu"), new Student("3", "wangwu"));
        Map<Student, String> map = Maps.asMap(set, Student::getName);

        map.entrySet().forEach(entry-> {
            System.out.println("key : " + entry.getKey() + " value : " + entry.getValue());
        });
    }


    class Student {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Student(String id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Student student = (Student) o;
            return Objects.equals(id, student.id) &&
                    Objects.equals(name, student.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public String toString() {
            return "Student{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
