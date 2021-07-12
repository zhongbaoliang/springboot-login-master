package com.alibaba.aspect.aopTest1;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
@Component
@Aspect
public class BeforeLog   {

    /**
     *
     * @param method 要执行的目标对象的方法
     * @param objects  参数
     * @param target  目标对象
     * @throws Throwable
     */
    @Before("execution(* com.alibaba.aspect.aopTest1.service.UserService1Impl.*(..))")
    public void before() throws Throwable {
        System.out.println("函数执行之前");
    }
}
