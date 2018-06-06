package com.maven.config;

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
            System.out.println("初始化ApplicationContext...");
            applicationContext = new ClassPathXmlApplicationContext("classpath*:spring/xjw-*.xml");
        }
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) getApplicationContext().getBean(name);
    }
}