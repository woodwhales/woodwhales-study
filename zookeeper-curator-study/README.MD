# curator-framework 使用

## pom 依赖

```xml
<dependency>
    <groupId>org.apache.curator</groupId>
    <artifactId>curator-framework</artifactId>
    <version>4.3.0</version>
</dependency>
<dependency>
    <groupId>org.apache.curator</groupId>
    <artifactId>curator-recipes</artifactId>
    <version>4.3.0</version>
</dependency>
```

## 重试策略

> - RetryOneTime：只重连一次
> - RetryNTime：指定重连的次数N
> - RetryUtilElapsed：指定最大重连超时时间和重连时间间隔，间歇性重连直到超时或者链接成功
> - ExponentialBackoffRetry：基于 “backoff”方式重连，和 RetryUtilElapsed 的区别是重连的时间间隔是动态的。
> - BoundedExponentialBackoffRetry： 同 ExponentialBackoffRetry的区别是增加了最大重试次数的控制

客户端启动的时候可以设置重试策略（retryPolicy）：

```java
CuratorFramework client = CuratorFrameworkFactory.builder()
				.connectString(zkServerPath)
				.sessionTimeoutMs(10000)
				.retryPolicy(retryPolicy)
				.namespace("workspace")
				.build();

client.start();
```

### 重试策略1

```java
/**
 * 1.重试api方式一
 * 
 * 推荐使用
 * 
 * curator链接zookeeper的策略:ExponentialBackoffRetry
 * baseSleepTimeMs：初始sleep的时间
 * maxRetries：最大重试次数
 * maxSleepMs：最大重试时间
 */
RetryPolicy retryPolicy1 = new ExponentialBackoffRetry(1000, 5);
```

### 重试策略2

```java
/**
 * 2.重试api方式二
 * 
 * 推荐使用
 * 
 * curator链接zookeeper的策略:RetryNTimes
 * n：重试的次数
 * sleepMsBetweenRetries：每次重试间隔的时间
 */
RetryPolicy retryPolicy2 = new RetryNTimes(3, 5000);
```

### 重试策略3

```java
/**
 * 3.重试api方式三
 * 
 * curator链接zookeeper的策略:RetryOneTime
 * sleepMsBetweenRetry:每次重试间隔的时间
 */
RetryPolicy retryPolicy3 = new RetryOneTime(3000);
```

### 重试策略4

```java
/**
 * 4.重试api方式四
 * 
 * 永远重试，不推荐使用
 * retryIntervalMs: 重试时间间隔
 */
RetryPolicy retryPolicy4 = new RetryForever(3000);
```

### 重试策略5

```java
/**
 * 5.重试api方式五
 * 
 * curator链接zookeeper的策略:RetryUntilElapsed
 * maxElapsedTimeMs:最大重试时间
 * sleepMsBetweenRetries:每次重试间隔
 * 重试时间超过maxElapsedTimeMs后，就不再重试
 */
RetryPolicy retryPolicy5 = new RetryUntilElapsed(2000, 3000);
```

## 创建节点

```java
	// 实例化
	CuratorOperator cto = new CuratorOperator();
	CuratorFrameworkState state = cto.client.getState();
	log.info("当前客户的状态：{}", (CuratorFrameworkState.STARTED == state ? "连接中" : "已关闭"));
	
	// 创建节点
	String nodePath = "/super/woodwhales";
	byte[] data = "superme".getBytes();
	cto.client
		.create()
		.creatingParentsIfNeeded()			// 可以递归创建节点
		.withMode(CreateMode.PERSISTENT)	// CreateMode：zk 原生的 api 
		.withACL(Ids.OPEN_ACL_UNSAFE)		// Ids： 原生的 api 
		.forPath(nodePath, data);

// CuratorOperator 构造函数
public CuratorOperator() {
   CuratorFramework client = CuratorFrameworkFactory.builder()
				.connectString(zkServerPath)
				.sessionTimeoutMs(10000)
				.retryPolicy(retryPolicy1) // 使用第一种重试机制
				.namespace("workspace")
				.build();
		client.start();
}
```
注意：上述节点”super/woodwhales“会创建在客户端链接开始设置的`namespace`里。

## 更新节点

```java
	String nodePath = "/super/woodwhales";    
	byte[] newData = "batman".getBytes();
    Stat stat = cto.client.setData().withVersion(0).forPath(nodePath, newData);
```
注意：更新节点过程中如果有异常，客户端链接不会断开，因此如果有异常要自己处理，客户端资源关闭。

## 删除节点

```java
	String nodePath = "/super/woodwhales";
	cto.client.delete()
			  .guaranteed()					// 如果删除失败，那么在后端还是继续会删除，直到成功
			  .deletingChildrenIfNeeded()	// 如果有子节点，就删除
			  .withVersion(0)
			  .forPath(nodePath);
```
## 读取节点

```java
	String nodePath = "/super/woodwhales";
	Stat stat2 = new Stat(); // 把获取到节点的状态数据保存到这个临时变量里
    byte[] data2 = cto.client.getData().storingStatIn(stat2).forPath(nodePath);
    System.out.println("节点" + nodePath + "的数据为: " + new String(data2));
    System.out.println("该节点的版本号为: " + stat2.getVersion());
```

## 查询子节点

```java
	String nodePath = "/super/woodwhales";
	List<String> childNodes = cto.client.getChildren().forPath(nodePath);
	System.out.println("开始打印子节点：");
	for (String s : childNodes) {
		System.out.println(s);
	}
```
## 判断节点是否存在

```java
	String nodePath = "/super/woodwhales";
	Stat statExist = cto.client.checkExists().forPath(nodePath + "/abc");
	System.out.println(statExist);
```
## 监听节点变化（仅限一次）

当使用usingWatcher的时候，监听只会触发一次，监听完毕后就销毁。**使用 CuratorWatcher 接口的时候：event.getPath() 不会打印出 namespace ！**

```java
	String nodePath = "/super/woodwhales";	
	// 当使用usingWatcher的时候，监听只会触发一次，监听完毕后就销毁
	// CuratorWatcher 的接口process方法会抛出异常
	cto.client.getData().usingWatcher(new MyCuratorWatcher()).forPath(nodePath);

    // 使用原生的 Watcher 的接口process方法不会抛出异常
    cto.client.getData().usingWatcher(new MyWatcher()).forPath(nodePath);

// MyCuratorWatcher
@Slf4j
public class MyCuratorWatcher implements CuratorWatcher {

	@Override
	public void process(WatchedEvent event) throws Exception {
		log.info("触发watcher，节点路径为：{}", event.getPath());
	}

}

// MyWatcher
@Slf4j
public class MyWatcher implements Watcher {

	@Override
	public void process(WatchedEvent event) {
		log.info("触发watcher，节点路径为：{}", event.getPath());
	}

}
```
## 监听节点变化（持续监听）

nodeCache.start(boolean buildInitial); 开启节点缓存。默认不开启 buildInitial。如果开启 buildInitial，则会把本次节点的初始化数据缓存到本地。一般在开发中会开启。

> buildInitial if true,  will be called before this method returns in order to get an initial view of the node

```java
	// NodeCache: 监听数据节点的变更，会触发事件
	final NodeCache nodeCache = new NodeCache(cto.client, nodePath);
	// buildInitial : 初始化的时候获取node的值并且缓存
	nodeCache.start(true);
	if (nodeCache.getCurrentData() != null) {
		System.out.println("节点初始化数据为：" + new String(nodeCache.getCurrentData().getData()));
	} else {
		System.out.println("节点初始化数据为空...");
	}

	nodeCache.getListenable().addListener(new NodeCacheListener() {
        @Override
        public void nodeChanged() throws Exception {
            if (Objects.isNull(nodeCache.getCurrentData())) {
                System.out.println("空");
                return;
            }
            String data = new String(nodeCache.getCurrentData().getData());
            System.out.println("节点路径：" + nodeCache.getCurrentData().getPath() + "数据：" + data);
        }
    });
	
	CountDownLatch countDownLatch = new CountDownLatch(1);
	countDownLatch.await();	
```
## 为子节点添加watcher

> tartMode: 初始化方式
>
> POST_INITIALIZED_EVENT：异步初始化，初始化之后会触发事件
>
> NORMAL：异步初始化
>
> BUILD_INITIAL_CACHE：同步初始化

```java
    // PathChildrenCache: 监听数据节点的增删改，会触发事件
    String childNodePathCache =  nodePath;
    // cacheData: 设置缓存节点的数据状态
    final PathChildrenCache childrenCache = new PathChildrenCache(cto.client, childNodePathCache, true);

    childrenCache.start(StartMode.POST_INITIALIZED_EVENT);

    List<ChildData> childDataList = childrenCache.getCurrentData();
    System.out.println("当前数据节点的子节点数据列表：");
    for (ChildData cd : childDataList) {
        String childData = new String(cd.getData());
        System.out.println(childData);
    }

    childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
        public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {

            switch (event.getType()) {
                case INITIALIZED:
                    log.info("子节点初始化ok...{}", event.getData().getPath());
                    break;
                case CHILD_ADDED:

                    String path = event.getData().getPath();
                    if (path.equals(ADD_PATH)) {
                        log.info("添加子节点:{}", event.getData().getPath());
                        log.info("子节点数据:{}", new String(event.getData().getData()));
                    } else if (path.equals("/super/woodwhales/e")) {
                        log.info("添加不正确...");
                    }

                    break;
                case CHILD_REMOVED:
                    log.info("删除子节点 : {}", event.getData().getPath());
                    break;
                case CHILD_UPDATED:
                    log.info("修改子节点路径 : {}", event.getData().getPath());
                    log.info("修改子节点数据: {}",new String(event.getData().getData()));
                    break;
                default:
                    break;
            }
        }

    });
```

## 监听数据节点的增删改

为节点添加 PathChildrenCacheListener 监听器，可以监听节点的增删改动作：

```java
// 为子节点添加watcher
// PathChildrenCache: 监听数据节点的增删改，会触发事件
String childNodePathCache =  nodePath;
// cacheData: 设置缓存节点的数据状态
final PathChildrenCache childrenCache = new PathChildrenCache(cto.client, childNodePathCache, true);
/**
		 * StartMode: 初始化方式
		 * POST_INITIALIZED_EVENT：异步初始化，初始化之后会触发事件
		 * NORMAL：异步初始化
		 * BUILD_INITIAL_CACHE：同步初始化
		 */
childrenCache.start(StartMode.POST_INITIALIZED_EVENT);

List<ChildData> childDataList = childrenCache.getCurrentData();
System.out.println("当前数据节点的子节点数据列表：");
for (ChildData cd : childDataList) {
    String childData = new String(cd.getData());
    System.out.println(childData);
}

childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
    @Override
    public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {

        switch (event.getType()) {
            case INITIALIZED:
                log.info("子节点初始化ok...{}", event.getData().getPath());
                break;
            case CHILD_ADDED:

                String path = event.getData().getPath();
                if (path.equals(ADD_PATH)) {
                    log.info("添加子节点:{}", event.getData().getPath());
                    log.info("子节点数据:{}", new String(event.getData().getData()));
                } else if (path.equals("/super/woodwhales/e")) {
                    log.info("添加不正确...");
                }

                break;
            case CHILD_REMOVED:
                log.info("删除子节点 : {}", event.getData().getPath());
                break;
            case CHILD_UPDATED:
                log.info("修改子节点路径 : {}", event.getData().getPath());
                log.info("修改子节点数据: {}",new String(event.getData().getData()));
                break;
            default:
                break;
        }
    }

});
```

## 节点监听小案例

> org.woodwhales.zk.curator.checkConfig

三个客户端监听某个节点，节点的变化会被客户端及时监听到，并作出相应的业务逻辑。

## 权限设置

```java
	byte[] data = "spiderman".getBytes();
    cto.client.create().creatingParentsIfNeeded()
        .withMode(CreateMode.PERSISTENT)
        .withACL(acls, true) // 开启递归设置权限，仅限当前创建的节点，以前存在的节点是不会设置权限的
        .forPath(nodePath, data);
```





## 参考资料

[ZooKeeper 客户端之 Curator](https://winrains.cn/archives/10263)

[ZooKeeper的全部,从小白到精通](https://blog.csdn.net/DataJunGe/article/details/104994481)

https://github.com/guoyansi/imooc-zookeeper-curator-maven