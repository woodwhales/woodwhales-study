package org.woodwhales.guava.utilities;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.6.30 22:12
 * @description: 数组元素拼接
 */
public class JoinerTest {

    private final List<String> stringList = Arrays.asList(
            "Google", "Java", "Guava", "woodwhales"
    );

    private final List<String> stringWithNullValueList = Arrays.asList(
            "Google", "Java", "Guava", null
    );

    Map<String, String> map = ImmutableMap.of("key1", "Google", "key2", "Guava");

    @Test
    public void test_Joiner_On_Join() {
        String result = Joiner.on("#").join(stringList);
        assertEquals("Google#Java#Guava#woodwhales", result);
    }

    @Test(expected = NullPointerException.class)
    public void test_Joiner_On_Join_With_NullValue() {
        String result = Joiner.on("#").join(stringWithNullValueList);
        assertEquals("Google#Java#Guava#woodwhales", result);
    }

    @Test
    public void test_Joiner_With_NullValue() {
        String result = Joiner.on("#").skipNulls().join(stringWithNullValueList);
        assertEquals("Google#Java#Guava", result);
    }

    @Test
    public void test_Joiner_With_NullValue_Replace_Default() {
        String result = Joiner.on("#").useForNull("DEFAULT").join(stringWithNullValueList);
        assertEquals("Google#Java#Guava#DEFAULT", result);
    }

    @Test
    public void test_Joiner_Append_To_StringBuilder() {
        final StringBuilder builder = new StringBuilder();
        StringBuilder stringBuilder = Joiner.on("#").skipNulls().appendTo(builder, stringWithNullValueList);
        assertSame(builder, stringBuilder);
        assertEquals("Google#Java#Guava", stringBuilder.toString());
        assertEquals("Google#Java#Guava", builder.toString());
    }

    @Test
    public void test_Joiner_Append_To_FileWriter() {
        String targetName = "D:\\code\\#woodwhales\\guava\\src\\test\\test.txt";
        try (FileWriter fileWriter = new FileWriter(new File(targetName))) {
            Joiner.on("#").skipNulls().appendTo(fileWriter, stringWithNullValueList);
            assertEquals(true, Files.isFile().test(new File(targetName)));
        } catch (Exception exception) {
            fail("文件操作失败");
        }
    }

    @Test
    public void test_Join_By_Stream() {
        String result = stringWithNullValueList
                        .stream()
                        .filter(item -> Objects.nonNull(item) && !item.isEmpty())
                        .collect(Collectors.joining("#"));
        assertEquals("Google#Java#Guava", result);
    }

    @Test
    public void test_Joining_By_Stream_SkipNullValue() {
        String result = stringWithNullValueList
                        .stream()
                        .map(this::defaultValue)
                        .collect(Collectors.joining("#"));
        assertEquals("Google#Java#Guava#DEFAULT", result);
    }

    private String defaultValue(final String item) {
        return Objects.isNull(item) || item.isEmpty() ? "DEFAULT" :item;
    }

    /**
     * 应用场景，对请求地址中的参数进行kv拼接
     * {
     *      "k1": "v1",
     *      "k2": "v2"
     * }
     *
     * k1=v1&k2=v2
     */
    @Test
    public void test_Joining_With_Map() {
        String result = Joiner.on("&").withKeyValueSeparator("=").join(map);
        assertEquals("key1=Google&key2=Guava", result);
    }
}
