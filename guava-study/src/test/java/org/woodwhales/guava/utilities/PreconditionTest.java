package org.woodwhales.guava.utilities;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.1 21:15
 * @description:
 */
public class PreconditionTest {

    @Test(expected = NullPointerException.class)
    public void checkNotNull() {
        List<String> list = null;
        Preconditions.checkNotNull(list);
    }

    @Test(expected = NullPointerException.class)
    public void checkNotNullWithMsg() {
        List<String> list = null;
        try {
            Preconditions.checkNotNull(list, "不允许对象为空");
        } catch (Exception e) {
            assertEquals("不允许对象为空", e.getMessage());
            throw e;
        }
    }

    @Test(expected = NullPointerException.class)
    public void check_NotNull_WithFormatMsg() {
        List<String> list = null;
        try {
            Preconditions.checkNotNull(list, "不允许对象为空，size 必须为%s", 2);
        } catch (Exception e) {
            assertEquals("不允许对象为空，size 必须为2", e.getMessage());
            throw e;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkArgument() {
        String type = "A";
        Preconditions.checkArgument(type.equals("B"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkArgumentWithMsg() {
        String type = "A";
        try {
            Preconditions.checkArgument(type.equals("B"), "参数不合法");
        } catch (Exception e) {
            assertEquals("参数不合法", e.getMessage());
            throw e;
        }
    }

    @Test(expected = IllegalStateException.class)
    public void checkState() {
        String type = "A";
        try {
            Preconditions.checkState(type.equals("B"), "状态不合法");
        } catch (Exception e) {
            assertEquals("状态不合法", e.getMessage());
            throw e;
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test() {
        ImmutableList<Object> list = ImmutableList.of();
        Preconditions.checkElementIndex(2, list.size());
    }

    @Test(expected = NullPointerException.class)
    public void testObjects() {
        try {
            Objects.requireNonNull(null, "对象不允许为空");
        } catch (Exception e) {
            assertEquals("对象不允许为空", e.getMessage());
            throw e;
        }
    }

    @Test(expected = AssertionError.class)
    public void testAssert() {
        List<String> list = null;
        assert list != null;
    }

    @Test(expected = AssertionError.class)
    public void testAssertWithMsg() {
        List<String> list = null;
        try {
            assert list != null : "不允许为空";
        } catch (Exception e) {
            assertEquals("不允许为空", e.getMessage());
            throw e;
        }
    }
}
