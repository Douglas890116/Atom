package com.hy.pull.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonDateValueProcessor implements JsonValueProcessor{

	private String format ="yyyy-MM-dd HH:mm:ss";  
	
	 private Object process(Object value){  
	        if(value instanceof Date){  
	            SimpleDateFormat sdf = new SimpleDateFormat(format);  
	            return sdf.format(value);  
	        }  
	        return value == null ? "" : value.toString();  
	    }

	/* (non-Javadoc)
	 * @see net.sf.json.processors.JsonValueProcessor#processArrayValue(java.lang.Object, net.sf.json.JsonConfig)
	 */
	public Object processArrayValue(Object value, JsonConfig config) {
		// TODO Auto-generated method stub
		return process(value);
	}

	/* (non-Javadoc)
	 * @see net.sf.json.processors.JsonValueProcessor#processObjectValue(java.lang.String, java.lang.Object, net.sf.json.JsonConfig)
	 */
	public Object processObjectValue(String key, Object value, JsonConfig config) {
		// TODO Auto-generated method stub
		return process(value);
	}  

}
