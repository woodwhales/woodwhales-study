### MyBatis Generator 逆向工程代码生成器工具

#### 使用方法

在`init.properties`配置文件中配置要生成的表或路径

注意：

- `tableName=%` 表示生成所有数据库表，不建议使用

#### 执行命令

在项目根目录右击选择`"Show in"`中的`"Terminal"`选项，进入 dos 窗口，执行逆向生成命令：`mvn mybatis-generator:generate`

参考博文：

[MyBatis Generator 逆向工程详解](<https://woodwhales.github.io/2018/10/29/015/>)