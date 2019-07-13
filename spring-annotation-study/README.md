## annotation

- code01
	spring注解开发入门，使用注解方式，省去了xml，全部通过java bean的配置类方式注入。
- code02
	组件扫描@ComponentScan
- code03
	懒加载@Lazy
- code04
	根据条件选择性注入@Conditional
- code05
	工厂bean注入
- code06
	bean的初始化和销毁方法，实现BeanPostProcessor接口，可以在bean初始化方法前后做一些逻辑操作。
- code07
	加载外部配置文件，配合@Value注解，实现外部配置文件加载到bean属性上。
- code08
	@Autowired自动依赖注入
- code09
	根据不同的环境注入不同的bean，@Profile注解

## aop

和xml配置的最大区别在于：原来的xml配置

````
<aop:aspectj-autoproxy></aop:aspectj-autoproxy>
````


​	换成了@EnableAspectJAutoProxy注解

### aop 原理

AOP原理：看给容器中注册了什么组件，这个组件什么时候工作，这个组件的功能是什么？

#### @EnableAspectJAutoProxy

@EnableAspectJAutoProxy是什么？

查看源码：`@Import(AspectJAutoProxyRegistrar.class)`表示给容器中导入`AspectJAutoProxyRegistrar`；

AspectJAutoProxyRegistrar 实现了 ImportBeanDefinitionRegistrar 接口。因此利用AspectJAutoProxyRegistrar自定义给容器中注册bean；

AspectJAutoProxyRegistrar 的目的就是给容器中注册一个名字叫：org.springframework.aop.config.internalAutoProxyCreator的组件，这个组件的真实身份就是`AnnotationAwareAspectJAutoProxyCreator`；

#### AnnotationAwareAspectJAutoProxyCreator

查看AnnotationAwareAspectJAutoProxyCreator的父类继承关系：

```java
AnnotationAwareAspectJAutoProxyCreator
			->AspectJAwareAdvisorAutoProxyCreator
 				->AbstractAdvisorAutoProxyCreator
 					->AbstractAutoProxyCreator					
```

这个`AbstractAutoProxyCreator`实现了`SmartInstantiationAwareBeanPostProcessor`和`BeanFactoryAware`接口。因此需要搞懂AnnotationAwareAspectJAutoProxyCreator，就需要搞懂后置处理器（在bean初始化完成前后做事情）、自动装配BeanFactory。

## tx

和xml配置的最大区别在于：原来的xml配置：

```
<tx:annotation-driven transaction-manager="transactionManager"/>
```

换成了@EnableTransactionManagement注解

使用@Bean 注入一个事务管理器：

```java
@Bean
public PlatformTransactionManager transactionManager() throws Exception{
    return new DataSourceTransactionManager(dataSource());
}
```

