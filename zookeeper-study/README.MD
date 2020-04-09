> zookeeper windows 环境搭建，参见：[zookeeper window环境安装、集成为windows服务、单机伪集群搭建](https://woodwhales.cn/2020/04/06/065/)

使用 zookeeper 原生 api 进行单元测试，参见：/zk/src/test/java/org/woodwhales/zk/TestZookeeper.java

#### org.woodwhales.zk.demo1

示例中模拟了三个服务器注册zk，三个客户端监听服务器状态。

#### org.woodwhales.zk.demo2

获取子节点示例

/zk/src/main/java/org/woodwhales/zk/demo2/ZKGetChildrenList.java

获取节点数据

/zk/src/main/java/org/woodwhales/zk/demo2/ZKGetNodeData.java

节点权限设置（账号密码和 IP 权限）

/zk/src/main/java/org/woodwhales/zk/demo2/ZKNodeAcl.java

判断节点是否存在

/zk/src/main/java/org/woodwhales/zk/demo2/ZKNodeExist.java

节点的简单操作

/zk/src/main/java/org/woodwhales/zk/demo2/ZKNodeOperator.java

