package com.alibaba.aspect.aopTest1.service;

public class UserService1Impl implements UserService1{
    @Override
    public void add() {
        System.out.println("增加一个用户");
    }

    @Override
    public void delete() {
        System.out.println("删除一个用户");

    }

    @Override
    public void update() {
        System.out.println("更新一个用户");

    }

    @Override
    public void select() {
        System.out.println("查找一个用户");

    }
}
