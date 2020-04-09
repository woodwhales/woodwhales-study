CountDownLatch 在 java.util.concurrent.CountDownLatch 包里面，通常用在多线程中使用，假设现在有一件事情开了多线程去完成，但是我们需要保证所有的线程都成功运行结束，再去执行后面的代码，这个时候 CountDownLatch 这个类就派上用场了。

我们可以把 CountDownLatch 传入到每个线程里面去，在线程执行成功时调用 countDownLatch.countDown(); 方法，然后在主线程里面执行完多线程初始化和分发执行之后执行 countDownLatch.await(); 这样，只有在每个线程执行成功以后，也就是 CountDownLatch 的计数器减到 0 以后，才会执行 countDownLatch.await() 后面的代码。

简单的应用场景是这样的，但是具体的怎么设计还是需要仔细斟酌，否则有可能会出现 countDownLatch.countDown(); 永远无法减为 0 的情况。