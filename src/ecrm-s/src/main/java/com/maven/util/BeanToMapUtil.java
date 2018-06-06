package com.maven.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("all")
public class BeanToMapUtil {
  private static final Logger logger = LogManager.getLogger(BeanToMapUtil.class.getName());
  
  /**
   * 将Map转换成JavaBean
   * @param type
   * @param map
   * @return
   * @throws IntrospectionException
   * @throws IllegalAccessException
   * @throws InstantiationException
   * @throws InvocationTargetException
 * @throws ClassNotFoundException 
 * @throws IllegalArgumentException 
   */
  public static <T> T convertMap(Map<String,Object> map,Class type) 
		  throws  ClassNotFoundException, InstantiationException, ParseException,IllegalAccessException, IntrospectionException, IllegalArgumentException, InvocationTargetException {
	  BeanInfo beanInfo = Introspector.getBeanInfo(type);
      Object obj = type.newInstance();
      PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
      for (int i = 0; i< propertyDescriptors.length; i++) {
          PropertyDescriptor descriptor = propertyDescriptors[i];
          String propertyName = descriptor.getName();
          if (map.containsKey(propertyName)) {
              Object value = map.get(propertyName);
              Object[] args = new Object[1];
              String fieldtype = descriptor.getPropertyType().getName();
              if(fieldtype.equals(BigDecimal.class.getName())){
            	  args[0] = new BigDecimal(String.valueOf(value));
              }else if(fieldtype.equals(Double.class.getName())){
				  args[0] = Double.valueOf(String.valueOf(value));
              }else if(fieldtype.equals(Integer.class.getName())  || fieldtype.equals(int.class.getName())){
            	  args[0] = Integer.parseInt(String.valueOf(value));
              }else if(fieldtype.equals(Date.class.getName())){
            	  DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				  args[0] = format.parse((String) value);
              }else if(fieldtype.equals(Byte.class.getName()) || fieldtype.equals(byte.class.getName())){
            	  args[0] = Byte.valueOf(String.valueOf(value));
              }else if(fieldtype.equals(Short.class.getName()) || fieldtype.equals(short.class.getName())){
            	  args[0] = Short.valueOf(String.valueOf(value));
              }else if(fieldtype.equals(Boolean.class.getName()) || fieldtype.equals(boolean.class.getName())){
            	  args[0] =Boolean.valueOf(String.valueOf(value));
              }else if(fieldtype.equals(Long.class.getName()) || fieldtype.equals(long.class.getName())){
            	  args[0] =Long.valueOf(String.valueOf(value));
              }else{
            	  args[0] = value;
              }
              descriptor.getWriteMethod().invoke(obj, args);
          }
      }
      return (T)obj;
  }
  
  


  /**
   * 将一个 JavaBean 对象转化为一个  Map
   * @throws InvocationTargetException 
   * @throws IllegalAccessException 
   * @throws IllegalArgumentException 
   */
  public static Map<String,Object> convertBean(Object bean,boolean isFull){
      Class type = bean.getClass();
      Map returnMap = new HashMap();
      BeanInfo beanInfo;
      try {
        beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else if(isFull){
                    returnMap.put(propertyName, "");
                }
            }
        }
      } catch (Exception e) {
        e.printStackTrace();
        logger.error(e);
      }
      return returnMap;
  }
}
