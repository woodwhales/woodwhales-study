package org.woodwhales.nio;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author woodwhales on 2021-07-15 21:31
 * @description
 */
@Slf4j
public class MyServer {

    private Selector selector;

    public void setUp(int port, Consumer<String> clientMsgConsumer) {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            this.selector = Selector.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);

            // 设置 serverSocketChannel 关注 socket-accept 事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, null);
            log.info("server has started...");
            while (true) {
                log.info("before select()");
                selector.select();
                log.info("after select()");

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
                while (selectionKeyIterator.hasNext()) {
                    SelectionKey selectionKey = selectionKeyIterator.next();
                    // 这一步骤很重要，已经消费的 selectionKey 必须从 selectionKeys 中手动移除
                    selectionKeyIterator.remove();

                    // [可链接事件] - ServerSocketChannel
                    if(selectionKey.isAcceptable()) {
                        ServerSocketChannel ssc = (ServerSocketChannel)selectionKey.channel();
                        SocketChannel socketChannel = ssc.accept();
                        log.info("client has connected, client hashCode = {}", socketChannel.hashCode());
                        socketChannel.configureBlocking(false);
                        // 设置 socketChannel 关注 read 事件
                        socketChannel.register(selector, SelectionKey.OP_READ, null);
                    }

                    // [可读取事件] - SocketChannel
                    if(selectionKey.isReadable()) {
                        try {
                            SocketChannel ssc = (SocketChannel)selectionKey.channel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            int read = ssc.read(byteBuffer);
                            if(read == -1) {
                                log.info("client disconnect");
                                selectionKey.cancel();
                            } else {
                                byteBuffer.flip();
                                String clientMsg = Charset.defaultCharset()
                                                  .decode(byteBuffer)
                                                  .toString();
                                // 处理接收到 client 发送的消息
                                clientMsgConsumer.accept(clientMsg);
                            }
                        } catch (Exception e) {
                            selectionKey.cancel();
                            log.error("client force disconnect", e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("server has error", e);
        }

    }

    public static void main(String[] args) {
        MyServer myServer = new MyServer();
        myServer.setUp(7777, clientMsg -> {
            log.info("accept client msg = {}", clientMsg);
        });
    }

}
