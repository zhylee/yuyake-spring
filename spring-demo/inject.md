## spring ioc 依赖注入

- `AbstractBeanFactory`
    - `doGetBean`：获取 Bean 实例
- `DefaultSingletonRegistry`
    - `getSingleton`：获取单例实例
    - 三级缓存：解决循环依赖
- `AbstractAutowireCapableBeanFactory`
    - `createBean`：创建 Bean 实例的准备
    - `doCreateBean`：创建 Bean 实例
    - `applyMergedBeanDefinitionPostProcessors`：处理 `@Autowired` 以及 `@Value`
    - `populateBean`：给 Bean 实例注入属性值（依赖注入在此）
- `AutowiredAnnotationBeanPostProcessor`
    - `postProcessProperties`：Autowired 的依赖注入逻辑
- `DefaultListableBeanFactory`
    - `doResolveDependency`：依赖解析
- `DependencyDescriptor`
    - `injectionPoint`：创建依赖实例
    
### doGetBean

1. 尝试从缓存获取 Bean
2. 循环依赖的判断
3. 递归去父容器获取 Bean 实例
4. 从当前容器获取 BeanDefinition 实例
5. 递归实例化显式依赖的 Bean（depends-on）
6. 根据不同的 Scope 才用不同的策略创建 Bean 实例
7. 对 Bean 进行类型检查

### createBean

- Bean 类型解析
- 处理方法覆盖
- Bean 实例化前的后置处理
- doCreateBean
    - 创建 Bean 实例（工厂方法、含参构造器注入、无参构造器注入）
    - 记录下被 @Autowired 或者 @Value 标记上的方法和成员变量
    - 是否允许提前暴露
    - 填充 Bean 属性
    - initializeBean
    - 注册相关销毁逻辑
    - 返回创建好的实例

### 循环依赖（[三级缓存](org.springframework.beans.factory.support.DefaultSingletonBeanRegistry)解决方案）

1. `getBean(A)`
2. `getSingleton`
3. `createBeanInstance`
4. `addSingletonFactory` -> `A` 实例的 `ObjectFactory` 放入三级缓存中
5. `populateBean` -> 为 `A` 实例赋值属性 `B`

依赖注入 `B`

1. `getBean(B)`
2. `getSingleton`
3. `createBeanInstance`
4. `addSingletonFactory` -> `B` 实例的 `ObjectFactory` 放入三级缓存中
5. `populateBean` -> 为 `B` 实例赋值属性 `A`

依赖注入 `A`

1. `getBean(A)`
2. `getSingleton` -> `ObjectFactory` 创建 `A` 实例 -> 注入 `B` -> `B` 创建成功并放入一级缓存清空其他缓存

### Spring 是否支持所有循环依赖的情况

循环依赖的情况如下

- 构造器循环依赖（singleton、prototype）
- Setter注入循环依赖（singleton、prototype）

---

Spring 不支持 prototype 的循环依赖
因为没有设置三级缓存进行支持，只能通过将 Bean 名字放入缓存里阻断无限循环

只支持Setter注入的单例循环依赖