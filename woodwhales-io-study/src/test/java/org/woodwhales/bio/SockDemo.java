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
public class SockDemo {

   @Test
   public void test() {
       ExecutorService clientCachePool = Executors.newCachedThreadPool();

       try (ServerSocket serverSocket = new ServerSocket(5555)) {
           log("serverSocket run...");
           while (true) {
               Socket socket = serverSocket.accept();
               log("new client is connected");
               clientCachePool.execute(() -> handle(socket));
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   private void log(String msg) {
       System.out.println(String.format("[%s-%d] %s",
                                        Thread.currentThread().getName(),
                                        Thread.currentThread().getId(), msg));
   }

    private void handle(Socket socket) {
       try (InputStream inputStream = socket.getInputStream();){
           int readLength;
           byte[] buffer = new byte[16];
           while ((readLength = inputStream.read(buffer)) != -1) {
               log("accept message from client : " + new String(buffer, 0, readLength));
           }
       } catch(Exception e) {
           e.printStackTrace();
       } finally {
           log("client connect is closed");
       }
    }

}
