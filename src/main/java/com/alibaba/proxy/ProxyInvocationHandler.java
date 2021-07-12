package com.alibaba.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyInvocationHandler implements InvocationHandler {
    Rent rent;//被代理的接口
    public void setRent(Rent rent) {
        this.rent = rent;
    }

    public Rent getRent() {
        return rent;
    }

    //生成代理类
    public Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), rent.getClass().getInterfaces(),this);
    }


    //处理代理实例
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        seeHouse();
        Object invoke = method.invoke(rent, args);//调用被代理类的方法
        fare();
        return invoke;
    }





    //增强函数
    public void seeHouse(){
        System.out.println("中介带着看房！");
    }
    public void fare(){
        System.out.println("中介收服务费！");
    }
}
