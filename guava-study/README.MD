## Guava 学习笔记源码

### EventBus

#### demo 示例

> org.woodwhales.guava.eventbus.demo*

#### demo 案例

> org.woodwhales.guava.eventbus.monitor

监听某个文件夹，其文件夹中的文件发生变化，将这个变化事件发送给 eventbus， 该 eventbus 的事件监听者，监听到变化，并作出日志输出。

文件夹位置：org.woodwhales.guava.eventbus.monitor.MonitorClient#TARGET

#### 自定义 EventBus

> org.woodwhales.guava.eventbus.custom

参见博文：[Guava EventBus 使用介绍及自实现详解](https://woodwhales.cn/2020/07/06/072/)

### CacheLoader

> org.woodwhales.guava.cache.CacheLoaderTest

### Precondition

断言工具

> org.woodwhales.guava.utilities.PreconditionTest

### Joiner

集合元素转字符串工具

> map中的元素转http请求参数形式：org.woodwhales.guava.utilities.JoinerTest#test_Joining_With_Map

### Splitter

字符串分割工具

> org.woodwhales.guava.utilities.SplitterTest

## 核心知识

### Java 四种引用

> org.woodwhales.guava.reference.ReferenceExample

参见博文：[Java 的四种引用](https://woodwhales.cn/2020/07/05/071/)

### LRU 算法

> org.woodwhales.guava.lru

参见博文：[LRU算法的Java实现（两种实现+增强实现）](https://woodwhales.cn/2020/07/05/070/)