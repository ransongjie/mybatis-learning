package org.mybatis.spring;

import com.xcrj.mapper.OrderMapper;
import com.xcrj.mapper.UserMapper;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

public class XcrjOrderFactoryBean implements FactoryBean {
    //返回
    @Override
    public Object getObject() throws Exception {
        //动态代理创建代理对象
        Object proxyInstance = Proxy.newProxyInstance(XcrjOrderFactoryBean.class.getClassLoader(),
                new Class[]{OrderMapper.class},
                (proxy, method, args) -> {
                    return null;
                });
        return proxyInstance;
    }

    @Override
    public Class<?> getObjectType() {
        return OrderMapper.class;
    }
}
