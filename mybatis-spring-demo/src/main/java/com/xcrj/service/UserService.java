package com.xcrj.service;

import com.xcrj.mapper.BusMapper;
import com.xcrj.mapper.CarMapper;
import com.xcrj.mapper.OrderMapper;
import com.xcrj.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {//spring调用构造方法 创建Bean》注入属性
    //注入userMapper类型的Bean对象，userMapper接口的代理对象，需要xml定义Mapper的Bean
    //spring还是mybatis创建userMapper的代理对象？ mybatis创建，mybatis可以不依赖spring而使用
    //将mybatis创建的userMapper的代理对象，放入IOC容器中，Spring再注入属性
    //mybatis整合spring，就是将mybatis创建的userMapper的代理对象成为1个bean，即放入IOC容器中，Spring再注入userMapper属性
    /**
     * spring如何产生Bean
     * 1. 编程式：
     * 2. 声明式：@Component, @Bean, <bean/>xml标签；注意，需要被Spring扫描到
     */
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private BusMapper busMapper;

    public void test() {
        System.out.println(userMapper.selectById());
    }
}
