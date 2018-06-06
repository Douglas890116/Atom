package com.maven.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.maven.utility.PropertiesUtils;

public class SystemConstant {
  private static Logger logger= LogManager.getLogger(SystemConstant.class.getName());


  private static Properties properties;
  static {
    try {
      properties = PropertiesUtils.deepLoad("/properties/config-common.properties");
 /*     Enumeration<Object> key = properties.keys();
      while(key.hasMoreElements()){
    	  Object i = key.nextElement();
    	  System.out.println(String.valueOf(i)+"   "+properties.getProperty(String.valueOf(i)));
      }*/
    } catch (IOException e) {
      logger.error("加载、读取系统域名URL异常。", e);
    }
  }

  public static String getProperties(String key) {
    return properties.getProperty(key);
  }
  
  public static void main(String[] args) {
		 System.out.println(SystemConstant.getProperties("redis.pool.maxIdle"));
	}
}
