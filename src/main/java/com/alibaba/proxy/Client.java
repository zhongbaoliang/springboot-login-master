package com.alibaba.proxy;

import java.lang.reflect.Proxy;
//动态代理实现
public class Client {

    public static void main(String[] args) {
        Host host=new Host();//真实对象
        ProxyInvocationHandler proxyInvocationHandler=new ProxyInvocationHandler();
        //通过 调用处理器 处理要被代理的对象（真实对象
        proxyInvocationHandler.setRent(host);
        //动态生成代理类proxy
        Rent proxy = (Rent) proxyInvocationHandler.getProxy();
        proxy.rent();
    }



}
