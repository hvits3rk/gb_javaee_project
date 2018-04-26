package com.romantupikov.simpleapp.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class Logger {
    @AroundInvoke
    public Object printLog(InvocationContext context) throws Exception {
        System.out.println("method invoked " + context.getMethod().getName());
        return context.proceed();
    }
}
