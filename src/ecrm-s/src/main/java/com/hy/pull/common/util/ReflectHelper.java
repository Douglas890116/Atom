package com.hy.pull.common.util;

import java.lang.reflect.Field;

/**
 * java反射帮助类
 * 创建日期 2016-10-06
 * @author temdy
 */
public class ReflectHelper {
	
    /**
     * 获取obj对象fieldName的Field
     * 
     * @param obj 类对象
     * @param fieldName 字段名称
     * @return 字段
     */
    public static Field getFieldByFieldName(Object obj, String fieldName) {
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
            }
        }
        return null;
    }

    /**
     * 获取obj对象fieldName的属性值
     * 
     * @param obj 类对象
     * @param fieldName 字段名称
     * @return 对象的属性值
     * @throws SecurityException 抛出SecurityException异常
     * @throws NoSuchFieldException 抛出NoSuchFieldException异常
     * @throws IllegalArgumentException 抛出IllegalArgumentException异常
     * @throws IllegalAccessException 抛出IllegalAccessException异常
     */
    public static Object getValueByFieldName(Object obj, String fieldName) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field field = getFieldByFieldName(obj, fieldName);
        Object value = null;
        if (field != null) {
            if (field.isAccessible()) {
                value = field.get(obj);
            } else {
                field.setAccessible(true);
                value = field.get(obj);
                field.setAccessible(false);
            }
        }
        return value;
    }

    /**
     * 设置obj对象fieldName的属性值
     * 
     * @param obj 类对象
     * @param fieldName 字段名称
     * @param value 值
     * @throws SecurityException 抛出SecurityException异常
     * @throws NoSuchFieldException 抛出NoSuchFieldException异常
     * @throws IllegalArgumentException 抛出IllegalArgumentException异常
     * @throws IllegalAccessException 抛出IllegalAccessException异常
     */
    public static void setValueByFieldName(Object obj, String fieldName, Object value) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        if (field.isAccessible()) {
            field.set(obj, value);
        } else {
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(false);
        }
    }
}
