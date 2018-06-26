package com.hy.pull.common.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class SystemConstant {
	private static Map<String, String> configMap = new HashMap<String, String>();
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("config-common");
		init(bundle);
	}
	
	private static void init(ResourceBundle bundle) {
		Enumeration<String> keys = bundle.getKeys();
		
		while (keys.hasMoreElements()) {
			String key = keys.nextElement().trim();
			String value = "";
			value = bundle.getString(key).trim();
			configMap.put(key, value);
		}
	}
	
	/**  
     * 
     *   
     * @param key  
     * @return  
     */  
    public static String getValueByKey(String key) {  
          return configMap.get(key).trim();
    }
	
	
	public static void main(String[] args) {
		System.out.println(SystemConstant.getValueByKey("ZJGame"));
	}
}
