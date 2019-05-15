package com.jake.example.jakelearnandroid.ioc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ListenerInvocationHandler implements InvocationHandler {

    private Object target;

    private HashMap<String, Method> methodHashMap = new HashMap<>();

    public ListenerInvocationHandler(Object target) {
        this.target = target;

    }
    // onclick 方法执行之前的方法
    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

        if (target != null) {
            // 执行onclick
            String methodName = method.getName();
            method = methodHashMap.get(methodName);
            if (method != null) {
                return method.invoke(target, objects);
            }
        }

        return null;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public void addMethod(String name , Method method) {
        methodHashMap.put(name, method);
    }
}
