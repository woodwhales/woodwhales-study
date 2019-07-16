- Executor
  
  只有一个execute()方法，执行某个任务的接口

- ExecutorService
  
  只有一个submit()方法，可以传参Runnable接口或Callable接口
   
- Callable
  
  和 Runnable 类似，但是call 方法有返回值并且抛出异常。

- Executors 
  
  操作Executor的工具类，类似的工具：Arrays，Collections

- ThreadPool
   
  线程池

- Future

  ExecutorService的submit方法返回类型

- ThreadpoolExecutor

    - FixedThreadPool

    - CachedThreadPool

    - SingleThreadPool

    - ScheduledThreadPool

> 以上线程池背后都是由ThreadPoolExecutor在支撑

- WorkStealingPool

- ForkJoinPool

parallel stream api