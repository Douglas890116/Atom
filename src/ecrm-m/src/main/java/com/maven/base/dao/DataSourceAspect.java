package com.maven.base.dao;


import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

public class DataSourceAspect {

	 public void before(JoinPoint point)
	    {
	        Object target = point.getTarget();
	        String method = point.getSignature().getName();

	        Class<?>[] classz = target.getClass().getInterfaces();

	        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature())
	                .getMethod().getParameterTypes();
	        try {
	            Method m = null; 
	            try {
	            	m = classz[0].getMethod(method, parameterTypes);
				} catch (NoSuchMethodException e) {
					m = classz[0].getMethod(method, Object.class);
				}
	            if (m != null && m.isAnnotationPresent(DataSource.class)) {
	                DataSource data = m
	                        .getAnnotation(DataSource.class);
	                DynamicDataSourceHolder.putDataSource(data.value());
	            }
	        }catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
