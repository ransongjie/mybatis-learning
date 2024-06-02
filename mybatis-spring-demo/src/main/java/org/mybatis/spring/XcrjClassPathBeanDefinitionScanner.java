package org.mybatis.spring;

import com.xcrj.mapper.UserMapper;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.Set;

public class XcrjClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {
    public XcrjClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    public int scan(String... basePackages) {
        int i = super.scan(basePackages);// 走spring的扫描逻辑
        System.out.println(i); // 扫描到多少个类
        return i;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        // mybatis 只关心接口
        return beanDefinition.getMetadata().isInterface();
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
            GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();

//            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(UserMapper.class);
            //先获得 Mapper的 BeanClassName
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanDefinition.getBeanClassName());
            //再重设 BeanClassName 为  FactoryBean 的 BeanClassName
            //spirng 在创建 FactoryBean bean时，将创建 Mapper bean
            beanDefinition.setBeanClassName(XcrjFactoryBean.class.getName());
            //自动注入SqlSessionFactory, XcrjFactoryBean 自动探测并注入需要的bean
            beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        }
        return beanDefinitionHolders;
    }
}