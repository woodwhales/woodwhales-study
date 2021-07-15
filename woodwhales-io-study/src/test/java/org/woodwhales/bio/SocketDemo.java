package org.woodwhales.bio;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author woodwhales on 2021-06-22 11:32
 * @description
 */
@Slf4j
public class SocketDemo {

   @Test
   public void test() {
       ExecutorService clientCachePool = Executors.newCachedThreadPool();

       try (ServerSocket serverSocket = new ServerSocket(5555)) {
           log.info("serverSocket run...");
           while (true) {
               // 阻塞等待新的客户端链接，当有新的客户端链接时才执行 accept() 之后的逻辑
               Socket socket = serverSocket.accept();
               log.info("new client is connected");
               clientCachePool.execute(() -> handle(socket));
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

    private void handle(Socket socket) {
       try (InputStream inputStream = socket.getInputStream()){
           int readLength;
           byte[] buffer = new byte[16];
           while ((readLength = inputStream.read(buffer)) != -1) {
               log.info("accept message from client {}",new String(buffer, 0, readLength));
           }
       } catch(Exception e) {
           e.printStackTrace();
           log.error("client is force disconnect", e);
       } finally {
           log.info("client connect is closed");
       }
    }

}
