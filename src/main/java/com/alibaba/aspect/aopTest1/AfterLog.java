package com.alibaba.aspect.aopTest1;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
@Component
@Aspect
public class AfterLog  {
    @After("execution(* com.alibaba.aspect.aopTest1.service.UserService1Impl.*(..))")
    public void afterReturning() throws Throwable {
        System.out.println("函数返回之后");
    }
}

