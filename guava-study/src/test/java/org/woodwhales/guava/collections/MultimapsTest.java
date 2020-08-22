package org.woodwhales.guava.collections;

import com.google.common.collect.ArrayListMultimap;
import org.junit.Test;

import java.util.List;

/**
 * @projectName: guava-study
 * @author: woodwhales
 * @date: 20.8.15 16:54
 * @description:
 */
public class MultimapsTest {

    /**
     * map中的key 有冲突时，将相同key的元素放到 list 中
     */
    @Test
    public void test() {
        ArrayListMultimap<Integer, Integer> multimap = ArrayListMultimap.create();

        multimap.put(1, 1);
        multimap.put(1, 2);
        multimap.put(2, 2);

        List<Integer> list1 = multimap.get(1);
        System.out.println(list1);

        List<Integer> list2 = multimap.get(2);
        System.out.println(list2);
    }
}
