- 组件扫描：自动发现应用容器中需要创建的 Bean
- 自动装配：自动满足 Bean 之间的依赖


- [BeanFactory](uml/BeanFactory.uml) : 面向 Spring 自身
- [ApplicationContext](uml/ApplicationContext.uml) : 面向使用 Spring 框架的开发者
- AbstractApplicationContext : 大量的高级容器实现

---

模板方法模式

围绕抽象类，实现通用逻辑，定义模板结构，部分逻辑由子类实现

- 复用代码
- 反向控制

```java
public abstract class AbstractTemplate {
    // 模板方法
    public void templateMethod() {
        concreteMethod();
        hookMethod();
        abstractMethod();
    }

    // 具体方法
    public void concreteMethod() {
        System.out.println("doing");
    }

    // 钩子方法 子类可以依据情况实现的方法
    protected void hookMethod() {
    }
    
    // 抽象方法 必须让子类实现的方法
    public abstract void abstractMethod();
}
```

---

容器初始化主要脉络

配置文件 --读取--> [Resource](uml/Resource.uml) --解析--> [BeanDefinition](uml/BeanDefinition.uml) --注册--> 依赖注入

- 解析配置
- 定位与注册对象


> Spring 使用了 [Resource](uml/Resource.uml) 以及 [ResourceLoader](uml/ResourceLoader.uml) 来统一抽象其资源及其定位，使得资源以及资源的定位有了更加清晰的界限。
> 并且提供了合适的 Default 实现类，使得自定义实现更加方便以及清晰。
> BeanDefinitionReader 为利器的使用者，读取 BeanDefinition，并最终实现 BeanDefinitionRegistry 注册器接口，将 BeanDefinition 注册进容器里

> spring 的默认实现类和抽象类一般都会放在 support package 里面
> 文件输出流示例：6-6 00:08:00 注解配置的资源定位、加载、解析、注册全链路 6-11

> [spring 事件处理](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#context-functionality-events)

---

Spring 事件驱动模型：事件驱动模型的三大组成部分

- 事件：ApplicationEvent 抽象类
- 事件监听器：ApplicationListener
- 事件发布器：Publisher 以及 Multicaster

---

## SpringIoC 容器的源码解析

- 注解方式启动容器
- Bean
    - Spring 的一等公民
- BeanDefinition
    - 用来描述Bean配置信息
- BeanFactory
    - 简单容器
    - DefaultListableBeanFactory
- ApplicationContext
    - 高级容器
    - AbstractApplicationContext.refresh() （模板方法模式）
- Resource
    - 配置资源
- ResourceLoader 和 ResourcePatternResolver
    - 配置读取
    - 策略模式
- BeanDefinitionReader
    - 读取 BeanDefinition
- 注解
    - 注册方式
        - 内置 BeanDefinition：容器内部设置的，在容器的构造函数中注册
        - @Configuration 标记的配置类：在容器构造函数中调用register()方法注册
        - 常规 BeanDefinition：在refresh()里的后置处理器被调用时注册
    - AnnotatedBeanDefinitionReader
        - 负责对上面三种 BeanDefinition 进行解析
        - 并最终注册到 DefaultListableBeanFactory 里
    - DefaultListableBeanFactory
- xml
    - XmlBeanDefinitionReader
    - Document 对象
    - BeanDefinitionDocumentReader
    - GenericBeanDefinition
    - DefaultListableBeanFactory

## SpringIoC 容器的初始化

- postProcessor
    - BeanFactoryPostProcessor // 容器级别的后置处理器
    - BeanDefinitionRegistryPostProcessor // 关于 Bean Definition 注册的后置处理器
    - BeanPostProcessor // bean 级别的后置处理器
- Aware
- 事件监听器模型
    - 回调函数
    - 事件监听的三大组件：事件源、事件、事件监听器
    - ApplicationEventPublisher // 专门发布事件的
    - ApplicationEventMulticaster // 拥有事件发布、监听、注册功能的
- Refresh
    - prepareRefresh：刷新前的准备工作
    - obtainFreshBeanFactory：获取子类刷新后的内部 beanFactory 实例
    - prepareBeanFactory：为容器注册必要的系统级别 Bean
    - postProcessBeanFactory：允许容器的子类去注册 postProcessor
    - invokeBeanFactoryPostProcessors：调用容器注册的容器级别的后置处理器
    - registerBeanPostProcessors：向容器注册 Bean 级别的后置处理器
    - initMessageSource：初始化国际化配置
    - initApplicationEventMulticaster：初始化事件发布者组件
    - onRefresh：在单例 Bean 初始化之前预留给子类初始化其他特殊 Bean 的口子
    - registerListeners：向前面的事件发布者组件注册事件监听者
    - finishBeanFactoryInitialization：设置系统级别的服务，实例化所有非懒加载的单例
    - finishRefresh：触发初始化完成的回调方法，并发布容器刷新完成的事件给监听者
    - resetCommonCaches：重置 Spring 内核中的共用缓存