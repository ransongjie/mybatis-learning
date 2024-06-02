package com.xcrj;

import com.xcrj.service.UserService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.XcrjImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.io.InputStream;

@ComponentScan("com.xcrj")
@Import(XcrjImportBeanDefinitionRegistrar.class)
public class MyApp {
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    public static void main(String[] args) {
        //启动spring
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(MyApp.class);

        //spring编程式创建bean
//        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
//        beanDefinition.setBeanClass(User.class);
//        beanDefinition.setBeanClass(UserMapper.class);//接口不能实例化，需要FactoryBean
        //创建2个bean: 1. FactoryBean bean; 2.getObject()返回的bean
//        beanDefinition.setBeanClass(XcrjFactoryBean.class);
//        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(UserMapper.class);//调用有参构造
//        context.registerBeanDefinition("userMapper", beanDefinition); //注册到IOC容器中

        //orderMapper
//        AbstractBeanDefinition orderBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
//        orderBeanDefinition.setBeanClass(XcrjOrderFactoryBean.class);
//        context.registerBeanDefinition("orderMapper", orderBeanDefinition);

        //灵活的 orderMapper
//        AbstractBeanDefinition orderBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
//        orderBeanDefinition.setBeanClass(XcrjFactoryBean.class);
//        orderBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue(OrderMapper.class);
//        context.registerBeanDefinition("orderMapper", orderBeanDefinition);

        context.refresh();

//        System.out.println(context.getBean(User.class));
        //获取getObject()返回的bean
//        System.out.println(context.getBean("user"));
        //获取FactoryBean bean, 默认spring加上&
//        System.out.println(context.getBean("&user"));

        //获取bean
        UserService userService = context.getBean("userService", UserService.class);
        userService.test();
    }
}
