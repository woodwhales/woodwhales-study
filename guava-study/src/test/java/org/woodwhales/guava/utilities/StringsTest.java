package org.woodwhales.guava.utilities;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import org.junit.Test;

import java.nio.charset.Charset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

/**
 * @projectName: guava
 * @author: woodwhales
 * @date: 20.7.1 22:03
 * @description:
 */
public class StringsTest {

    @Test
    public void testStringsMethod() {
        assertThat(Strings.emptyToNull(""), nullValue());
        assertThat(Strings.nullToEmpty(null), equalTo(""));
        assertThat(Strings.nullToEmpty("hello"), equalTo("hello"));
        assertThat(Strings.commonPrefix("Hello", "Hit"), equalTo("H"));
        assertThat(Strings.commonPrefix("Hello", "Xit"), equalTo(""));
        assertThat(Strings.commonSuffix("Hello", "Echo"), equalTo("o"));
        assertThat(Strings.repeat("ABC", 3), equalTo("ABCABCABC"));
        assertThat(Strings.isNullOrEmpty(null), equalTo(true));
        assertThat(Strings.isNullOrEmpty(""), equalTo(true));

        assertThat(Strings.padStart("AAA", 3, 'H'), equalTo("AAA"));
        assertThat(Strings.padStart("AAA", 5, 'H'), equalTo("HHAAA"));
        assertThat(Strings.padEnd("AAA", 5, 'H'), equalTo("AAAHH"));
    }

    @Test
    public void testCharsets() {
        Charset charset = Charset.forName("UTF-8");
        assertThat(Charsets.UTF_8, equalTo(charset));
    }

    @Test
    public void testCharMatcher() {
        assertThat(CharMatcher.javaDigit().matches('5'), equalTo(true));
        assertThat(CharMatcher.javaDigit().matches('x'), equalTo(false));

        assertThat(CharMatcher.is('A').countIn("Alx xys"), equalTo(1));
        assertThat(CharMatcher.breakingWhitespace().collapseFrom("      hello Guava     ", '*'), equalTo("*hello*Guava*"));
        assertThat(CharMatcher.javaDigit().or(CharMatcher.whitespace()).removeFrom("hello 234 world"), equalTo("helloworld"));
        assertThat(CharMatcher.javaDigit().or(CharMatcher.whitespace()).retainFrom("hello 234 world"), equalTo(" 234 "));
    }
}
