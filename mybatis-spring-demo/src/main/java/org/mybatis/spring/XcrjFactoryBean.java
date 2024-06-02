package org.mybatis.spring;

import com.xcrj.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Proxy;

public class XcrjFactoryBean implements FactoryBean {
    private Class mapperClass;
    private SqlSession sqlSession;

    public XcrjFactoryBean(Class mapperClass) {
        this.mapperClass = mapperClass;
    }

//    @Autowired
    public void setSqlSession(SqlSessionFactory sqlSessionFactory) {
        sqlSessionFactory.getConfiguration().addMapper(mapperClass);
        this.sqlSession = sqlSessionFactory.openSession();
    }

    //返回
    @Override
    public Object getObject() throws Exception {
        //动态代理创建代理对象
//        Object proxyInstance = Proxy.newProxyInstance(XcrjFactoryBean.class.getClassLoader(),
//                new Class[]{mapperClass},
//                (proxy, method, args) -> {
//                    return null;
//                });
//        return proxyInstance;

        //获得mybatis创建的mapper代理对象
        return sqlSession.getMapper(mapperClass);
    }

    @Override
    public Class<?> getObjectType() {
        return mapperClass;
    }
}
