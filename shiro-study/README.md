# springboot + shiro 安全认证框架整合

本项目没有过多的UI 设计，只是单纯地实现了shiro 和 springboot 的整合，前端页面使用了 thymeleaf，并在前端页面中使用 thymeleaf-shiro 标签进行页面显示。

## 运行项目

### 编译运行

直接在项目根目录运行 mvn 命令：

```java
mvn clean install -DskipTests
```

打包成功之后，在项目根目录的 target 目录得到可执行的 jar 文件，

项目使用了内置的`h2database`数据库，所以可以运行即可，不需要链接外置的数据库：

```java
java -jar springboot-shiro-1.0-SNAPSHOT.jar
```

即可访问：http://127.0.0.1:8080

### 默认帐号

默认帐号只有两个，分别有不同的权限，具体的数据库脚本在`src/main/resources/db`文件目录下：

| 用户名 | 密码     | 权限                  |
| ------ | -------- | --------------------- |
| admin  | admin123 | user:update, user:add |
| tom    | tom123   | user:add              |

## 实现功能

### 安全认证

只有用户和密码全部认证正确才能登录系统，否则一直停留在当前登录页面，并显示响应的错误信息。

### 授权功能

当用户安全认证登录成功之后，系统会根据当前用户的权限进行展示页面。

### RememberMe

记住我的到期时间为20秒。

由于记住我的功能和授权功能是互斥的，所以增加了一个`list page`页面，用来校验记住我功能是否成功有效。

## 博文参考

SpringBoot系列 - 集成Shiro权限管理：
https://www.xncoding.com/2017/07/07/spring/sb-shiro.html


Shiro+JWT+Spring Boot Restful简易教程：
https://github.com/Smith-Cruise/Spring-Boot-Shiro

Spring Boot教程(十六)：Spring Boot集成shiro：
https://blog.csdn.net/gnail_oug/article/details/80662553

Shiro用starter方式优雅整合到SpringBoot中：
https://segmentfault.com/a/1190000014479154

一文看懂安全控制框架Apache Shiro：
https://mp.weixin.qq.com/s/8gPY-ZnQTmXaXdmqGLaVsQ

Spring Boot + Vue + Shiro 实现前后端分离、权限控制：
https://mp.weixin.qq.com/s/czLXiMuji8WIP4tmKBDNrw

Spring Boot 整合 Shiro ，两种方式全总结！：
https://mp.weixin.qq.com/s/JU_-gn-yZ4VJJXTZvo7nZQ

Springboot + shiro权限管理。这或许是流程最详细、代码最干净、配置最简单的shiro上手项目了。:
https://www.ctolib.com/zhangyd-c-springboot-shiro.html

SpringBoot整合mybatis、shiro、redis实现基于数据库的细粒度动态权限管理系统实例:
https://blog.csdn.net/poorcoder_/article/details/71374002

一起来学SpringBoot | 第二十六篇：轻松搞定安全框架（Shiro）:
https://blog.battcn.com/2018/07/03/springboot/v2-other-shiro/

SpringBoot集成Shiro:
https://panlf.github.io/2018/07/05/SpringBoot%E9%9B%86%E6%88%90Shiro/

spring-boot-2.0.3不一样系列之shiro - 搭建篇:
https://cloud.tencent.com/developer/article/1333049

Spring Boot Shiro用户认证:
https://mrbird.cc/Spring-Boot-shiro%20Authentication.html

（SpringBoot）Shiro安全框架深入解析:
https://www.javazhiyin.com/24868.html

springboot + shiro之登录人数限制、登录判断重定向、session时间设置:
https://blog.51cto.com/wyait/2107423

spring boot shiro redis整合基于角色和权限的安全管理-Java编程:
http://www.leftso.com/blog/238.html

Spring Boot 集成 Shiro 权限管理与密码加盐加密存储:
https://xlui.me/t/spring-boot-shiro/

自研SpringBoot的Shiro自动配置模块:
https://www.chenmin.info/2019/01/23/%E8%87%AA%E7%A0%94SpringBoot%E7%9A%84Shiro%E8%87%AA%E5%8A%A8%E9%85%8D%E7%BD%AE%E6%A8%A1%E5%9D%97/

springboot+shiro+JWT 系统权限管理:
https://www.itwork.club/2018/10/08/springboot-shiro-jwt/