package com.maven.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.maven.utility.PropertiesUtils;

public class RedisProperties {
  private static Logger logger= LogManager.getLogger(RedisProperties.class.getName());


  private static Properties properties;
  static {
    try {
      properties = PropertiesUtils.deepLoad("/properties/config-redis.properties");
    } catch (IOException e) {
      logger.error("加载、读取系统域名URL异常。", e);
    }
  }

  public static String getProperties(String key) {
    return properties.getProperty(key);
  }
  
  public static Map<String,Object> getJedisPoolProperties(){
	  Iterator<Entry<Object, Object>> keys =  properties.entrySet().iterator();
	  Map<String,Object> properties = new HashMap<String, Object>();
	  while(keys.hasNext()){
		  Entry<Object, Object> e = keys.next();
		  String key = String.valueOf(e.getKey());
		  if(key.startsWith("redis.pool.")){
			  key = key.replace("redis.pool.","");
			  properties.put(key, e.getValue());
		  }
	  }
	  return properties;
  }
  
}
