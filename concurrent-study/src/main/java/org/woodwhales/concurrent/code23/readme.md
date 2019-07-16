总结：

1、对于map/set的选择使用

- 不加锁的情况下，使用：HashMap、TreeMap、LinkedHashMap
- 并发不是很高的情况下，使用：Hashtable、Collections.sychronizedXXX
- 高并发，使用：ConcurrentHashMap
- 高并发并且排序，使用：ConcurrentSkipListMap 

2、队列

- 不同步：ArrayList、LinkedList
- 同步：Collections.synchronizedXXX
- 读多写少：CopyOnWriteList
- 高并发队列：CocurrentLinkedQueue / concurrentArrayQueue
- BlockingQueue阻塞式队列：
    - LinkedBQ 无界队列
    - ArrayBQ 有界队列
    - TransferQueue 直接将消息transfer给消费者，没有消费者就阻塞
    - SynchronusQueue 容量为0的队列
    - DelayQueue 执行定时任务
