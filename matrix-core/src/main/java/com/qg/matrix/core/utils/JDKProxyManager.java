package com.qg.matrix.core.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class JDKProxyManager implements InvocationHandler {
    private  Map<Class<?>, Object> proxyMap = new HashMap<>();
    // 被代理对象
    private Object target;

    // 通过反射机制获取对象，获取接口
    public Object getInstance(Object target) throws Exception {
        this.target = target;
        Class<?> clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object obj = method.invoke(this.target, args);
        return obj;
    }
}
