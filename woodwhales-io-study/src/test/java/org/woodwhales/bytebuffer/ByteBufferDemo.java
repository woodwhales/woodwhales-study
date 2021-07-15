package org.woodwhales.bytebuffer;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.woodwhales.bytebuffer.ByteBufferUtil.debugAll;

/**
 * @author woodwhales on 2021-06-26 18:59
 * @description
 */
@Slf4j
public class ByteBufferDemo {

    @Test
    public void writeToBuffer2() {
        // 读取时需要切换读取模式
        ByteBuffer byteBuffer = write1("woodwhales");
        debugAll(byteBuffer);

        read(byteBuffer);
        debugAll(byteBuffer);
    }

    @Test
    public void writeToBuffer1() {
        ByteBuffer byteBuffer = write2("woodwhales");
        debugAll(byteBuffer);

        // 切换为读模式
        byteBuffer.flip();

        read(byteBuffer);
        debugAll(byteBuffer);
    }

    /**
     * 读取时不需要切换读取模式
     * @param content
     * @return
     */
    private ByteBuffer write1(String content) {
        ByteBuffer buffer = StandardCharsets.UTF_8.encode(content);
        return buffer;
    }

    /**
     * 读取时需要切换读取模式
     * @param content
     * @return
     */
    private ByteBuffer write2(String content) {
        ByteBuffer buffer = ByteBuffer.allocate(content.length());
        buffer.put(content.getBytes(StandardCharsets.UTF_8));
        return buffer;
    }

    private void read(ByteBuffer buffer) {
        // 读取byteBuffer
        ArrayList<Character> charList = new ArrayList<>();
        while (buffer.hasRemaining()) {
            byte content = buffer.get();
            charList.add((char)content);
        }
        log.info("get content from bytebuffer, content = {}", charList);
    }

}
