package org.woodwhales.guava.utilities;

import com.google.common.base.Splitter;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.1 20:43
 * @description:
 */
public class SplitterTest {

    @Test
    public void test_splitter_on_split() {
        List<String> resultList = Splitter.on("|").splitToList("hello|world");
        assertNotNull(resultList);
        assertEquals(2, resultList.size());
        assertEquals("hello", resultList.get(0));
        assertEquals("world", resultList.get(1));
    }

    @Test
    public void test_splitter_on_split_omitEmpty() {
        List<String> resultList = Splitter.on("|").splitToList("hello|world|||");
        assertNotNull(resultList);
        assertEquals(5, resultList.size());

        List<String> omitEmptyResultList = Splitter.on("|")
                                            .omitEmptyStrings()
                                            .splitToList("hello|world|||");

        assertEquals(2, omitEmptyResultList.size());
    }

    @Test
    public void test_splitter_on_split_omitEmpty_trim() {
        List<String> resultList = Splitter.on("|").splitToList("hello | world|||");
        assertNotNull(resultList);
        assertEquals(5, resultList.size());
        assertEquals("hello ", resultList.get(0));
        assertEquals(" world", resultList.get(1));

        // trimResults
        List<String> trimResults = Splitter.on("|")
                                            .omitEmptyStrings()
                                            .trimResults()
                                            .splitToList("hello | world|||");

        assertEquals(2, trimResults.size());
        assertEquals("hello", trimResults.get(0));
        assertEquals("world", trimResults.get(1));
    }

    @Test
    public void test_splitter_on_fixedLength() {
        List<String> resultList = Splitter.fixedLength(4).splitToList("aabbccddeeff");
        assertNotNull(resultList);
        assertEquals("aabb", resultList.get(0));
        assertEquals("eeff", resultList.get(2));
    }

    @Test
    public void test_splitter_on_limit() {
        List<String> resultList = Splitter.on("#").limit(3).splitToList("java#guava#hello#world#abc");
        assertNotNull(resultList);
        assertEquals(3, resultList.size());
        assertEquals("java", resultList.get(0));
        assertEquals("hello#world#abc", resultList.get(2));
    }

    @Test
    public void test_splitter_on_toMap() {
        Map<String, String> map = Splitter.on("&")
                                            .withKeyValueSeparator("=")
                                            .split("key1=value1&key2=value2");
        assertNotNull(map);
        assertEquals(2, map.size());
        assertEquals("value1", map.get("key1"));
        assertEquals("value2", map.get("key2"));
    }
}
