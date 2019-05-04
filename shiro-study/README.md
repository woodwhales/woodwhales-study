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



