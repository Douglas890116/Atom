package com.maven.privacy;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PrivateDataHandle {
	
	public static final String ASTERISK = "********";
	
	public static <T>T handle(T t,String[] fileds) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		for (String vfieldName : fileds) {
   		 	PropertyDescriptor descriptor=new PropertyDescriptor(vfieldName, t.getClass());
            Method writeMethod=descriptor.getWriteMethod();
            writeMethod.invoke(t, ASTERISK);
		}
		return t;
	}

}
