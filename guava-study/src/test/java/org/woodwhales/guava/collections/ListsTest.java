package org.woodwhales.guava.collections;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @projectName: guava-study
 * @author: woodwhales
 * @date: 20.8.15 15:52
 * @description:
 */
public class ListsTest {

    /**
     * 将list按照元素个数分割
     */
    @Test
    public void test() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
        List<List<Integer>> partition = Lists.partition(list, 2);
        partition.stream().forEach(System.out::println);
    }
}
