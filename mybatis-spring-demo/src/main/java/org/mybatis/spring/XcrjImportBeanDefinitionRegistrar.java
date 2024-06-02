package org.mybatis.spring;

import com.xcrj.mapper.OrderMapper;
import com.xcrj.mapper.UserMapper;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

public class XcrjImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        //扫描 Mapper接口
        String path = "com.xcrj.mapper";
        XcrjClassPathBeanDefinitionScanner scanner = new XcrjClassPathBeanDefinitionScanner(registry);
        scanner.addIncludeFilter(new TypeFilter() {
            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                return true;
            }
        });
        scanner.scan(path);

//        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
//        beanDefinition.setBeanClass(XcrjFactoryBean.class);
//        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(UserMapper.class);//调用有参构造
//        registry.registerBeanDefinition("userMapper", beanDefinition); //注册到IOC容器中
//
//        AbstractBeanDefinition orderBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
//        orderBeanDefinition.setBeanClass(XcrjFactoryBean.class);
//        orderBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue(OrderMapper.class);
//        registry.registerBeanDefinition("orderMapper", orderBeanDefinition);
    }

}
