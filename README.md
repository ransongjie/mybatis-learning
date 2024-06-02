# mybatis-demo 只使用mybatis
只使用mybatis，不使用spring
# mybatis-spring-demo mybatis整合spring

- https://mybatis.org/spring/zh_CN/getting-started.html

mybatis整合spring原理

- mybatis整合spring，就是将mybatis创建的userMapper的代理对象成为1个bean，即放入IOC容器中，Spring再注入userMapper属性
- 使用spring编程式创建bean的方式，放入IOC容器中

mybatis整合spring思路

- 问题：spring编程式创建bean的方式，放入IOC容器中，UserMapper是接口，拿不到实例。
- 解决：使用MapperFactoryBean
- 问题：多Mapper，多FactoryBean，多BeanDefinition，注册ApplicationContext，不够灵活
- 解决：FactoryBean中增加mapperClass属性，并提供有参构造方法
    - （之前，spring调用FactoryBean的无参构造创建 FactoryBean bean）
    - （现在，spring只能调用有参构造，spring编程式创建 beanDefinition 指定调用有参构造方法）
- 问题：上面只解决了 多Mapper，多FactoryBean，没有解决 多BeanDefinition，注册ApplicationContext 代码重复的问题
- 解决：将重复代码移动到 XcrjImportBeanDefinitionRegistrar BeanDefinition注册器中, @Import
- 问题：XcrjImportBeanDefinitionRegistrar 指定了业务Mapper，mybatis的作者并不知道你有哪些业务Mapper
- 解决：扫描Mapper，扫描路径（程序员指定），利用spring的扫描
    - spring的扫描忽略了interface，mybatis只关心interface
    - spring扫描到@Component注解的类，生成BeanDefinition
- 解决：XcrjClassPathBeanDefinitionScanner 利用 spring的扫描器
    - 在XcrjImportBeanDefinitionRegistrar中使用XcrjClassPathBeanDefinitionScanner扫描
- 问题：spring只能获得mapper接口，并实例化，自然报错
- 解决：重写 ClassPathBeanDefinitionScanner/doScan()方法，解决 多BeanDefinition，注册ApplicationContext
    - 获得 接口beanDefinition
    - 再修正 接口beanDefinition 为 FactoryBean。spirng 创建 FactoryBean bean时，将创建 Mapper bean
- 总结：只要org.mybatis.spring下的3个类即可
- 问题：FactoryBean getObject()方法的代理对象是demo，如何获得mybatis创建的Mapper代理对象
- 解决：使用sqlSession获得mybatis创建的Mapper代理对象
- 问题：如何FactoryBean/sqlSession属性赋值
- 解决：@Autowired setSqlSession(SqlSessionFactory){}, Autowired自动注入SqlSessionFactory bean
- 问题：需要得到 SqlSessionFactory，并注入 IOC容器
- 解决：SqlSessionFactory的创建和用户相关了，用户自己定义 SqlSessionFactory bean, 在 MyAPP
- 问题：mybatis源码中没有@Autowired注入SqlSessionFactory bean
- 解决：XcrjClassPathBeanDefinitionScanner/doScan()设置 setAutowireMode()，自动探测并注入需要的bean

mybatis整合spring过程

1. XcrjFactoryBean/getObject(){动态代理创建Mapper代理对象}
2. spring编程时创建BeanDefinition（Mapper代理对象）
3. 注册到ApplicationContext中，放入IOC容器中

# 补充

## mysql-connector-java 8.+

- com.mysql.cj.jdbc.Driver
- `jdbc:mysql://172.28.26.242:49156/mybatis_demo?useSSL=false&amp;serverTimezone=GMT%2B8&amp;characterEncoding=utf8`