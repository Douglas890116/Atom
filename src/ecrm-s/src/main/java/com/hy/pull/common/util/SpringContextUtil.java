package com.hy.pull.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext _applicationContext)
            throws BeansException {
        applicationContext = _applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            applicationContext = new ClassPathXmlApplicationContext("classpath*:resources/*-context.xml");
        }
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) getApplicationContext().getBean(name);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<?> name) throws BeansException {
        return (T) getApplicationContext().getBean(name);
    }
}