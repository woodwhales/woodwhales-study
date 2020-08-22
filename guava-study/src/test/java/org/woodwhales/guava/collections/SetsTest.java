package org.woodwhales.guava.collections;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @projectName: guava-study
 * @author: woodwhales
 * @date: 20.8.15 15:55
 * @description:
 */
public class SetsTest {

    /**
     * 差集
     * 不允许比较的元素为null
     */
    @Test
    public void testDifference() {
        HashSet<Integer> set1 = Sets.newHashSet(1, 2, 3);
        HashSet<Integer> set2 = Sets.newHashSet(2, 3, 4);

        Sets.SetView<Integer> difference1 = Sets.difference(set1, set2);
        // 输出 1
        System.out.println(difference1);

        // 输出 2
        Sets.SetView<Integer> difference2 = Sets.difference(set2, set1);
        System.out.println(difference2);
    }

    /**
     * 交集
     * 不允许比较的元素为null
     */
    @Test
    public void testIntersection() {
        Set<Integer> set1 = Sets.newHashSet(1, 2, 3);
        Set<Integer> set2 = Sets.newHashSet(2, 3, 4);
        Sets.SetView<Integer> intersection = Sets.intersection(set1, set2);
        System.out.println(intersection);
    }

    /**
     * 并集
     * 不允许要操作的集合为null
     */
    @Test
    public void tsetUnion() {
        Set<Integer> set1 = Sets.newHashSet(1, 2, 3);
        Set<Integer> set2 = Sets.newHashSet(2, 3, 4);
        Sets.SetView<Integer> union = Sets.union(set1, set2);
        System.out.println(union);
    }


}
