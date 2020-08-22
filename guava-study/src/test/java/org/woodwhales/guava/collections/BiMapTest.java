package org.woodwhales.guava.collections;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.junit.Test;

import java.util.Objects;

/**
 * @projectName: guava-study
 * @author: woodwhales
 * @date: 20.8.15 17:00
 * @description:
 */
public class BiMapTest {

    /**
     * key 和 value 对调
     */
    @Test
    public void test() {
        HashBiMap<String, Child> map = HashBiMap.create();

        map.put("1", new Child("1", "zhangsan"));
        map.put("2", new Child("2", "wuwang"));
        map.put("3", new Child("3", "lisi"));

        map.entrySet().forEach(entry-> {
            System.out.println("key : " + entry.getKey() + " value : " + entry.getValue());
        });

        BiMap<Child, String> inverseMap = map.inverse();
        inverseMap.entrySet().forEach(entry-> {
            System.out.println("key : " + entry.getKey() + " value : " + entry.getValue());
        });

    }

    class Child {
        private String id;
        private String name;

        public Child(String id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Child)) return false;
            Child child = (Child) o;
            return Objects.equals(id, child.id) &&
                    Objects.equals(name, child.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public String toString() {
            return "Child{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
