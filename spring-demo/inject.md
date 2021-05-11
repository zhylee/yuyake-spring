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