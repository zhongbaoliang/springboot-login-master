package com.alibaba.aspect;

import com.alibaba.aspect.aopTest1.service.UserService1;
import com.alibaba.aspect.aopTest1.service.UserService1Impl;
//此模块未成功，因为没配置xml文件，将bean装入
public class LogAspect {
    public static void main(String[] args) {
        UserService1 userService1=new UserService1Impl();
        userService1.add();
    }
}
