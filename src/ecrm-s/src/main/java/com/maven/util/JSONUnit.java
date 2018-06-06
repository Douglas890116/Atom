package com.maven.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class JSONUnit {
	
	/**
	 * 将json转换成Map
	 * @param jsonString
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String,Object> getMapFromJson(String jsonString) { 
		Map<String,Object> map = new HashMap<String,Object>(); 
		try {
			JSONObject jsonObject = JSONObject.fromObject(jsonString); 
	        for(Iterator iter = jsonObject.keys(); iter.hasNext();){ 
	            String key = (String)iter.next(); 
	            map.put(key, jsonObject.get(key));
	        }
	   
		} catch (Exception e) {
			e.printStackTrace();
		}
        return map; 
    } 
	/**
	 * 将json转换成Map
	 * @param jsonString
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String,String> getMapFromJsonNew(String jsonString) { 
		Map<String,String> map = new HashMap<String,String>(); 
		try {
			JSONObject jsonObject = JSONObject.fromObject(jsonString); 
	        for(Iterator iter = jsonObject.keys(); iter.hasNext();){ 
	            String key = (String)iter.next(); 
	            map.put(key, jsonObject.optString(key));
	        }
	   
		} catch (Exception e) {
			e.printStackTrace();
		}
        return map; 
    } 
	
	  /** 
	   * 把数据对象转换成json字符串 
	   * DTO对象形如：{"id" : idValue, "name" : nameValue, ...} 
	   * 数组对象形如：[{}, {}, {}, ...] 
	   * map对象形如：{key1 : {"id" : idValue, "name" : nameValue, ...}, key2 : {}, ...} 
	   * @param object 
	   * @return 
	   */ 
	   public static String getJSONString(Object object){ 
		   String jsonString = null; 
		   try {
		    //日期值处理器 
		    JsonConfig jsonConfig = new JsonConfig(); 
		    jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateValueProcessor()); 
		    if(object != null){ 
			    if(object instanceof Collection || object instanceof Object[]){ 
			    	jsonString = JSONArray.fromObject(object, jsonConfig).toString(); 
			    }else{ 
			    	jsonString = JSONObject.fromObject(object, jsonConfig).toString(); 
			    } 
		    } 
		   } catch (Exception e) {
				e.printStackTrace();
			}
		    return jsonString == null ? "{}" : jsonString; 
		}
	   
	   
	   /**
		 * 将JSON数组对象转换成List集合对象
		 * @param jsonString
		 * @param clazz
		 * @return
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List getDTOList(String jsonString, Class clazz){ 
			List list = new ArrayList();
			try {
				JSONArray array = JSONArray.fromObject(jsonString); 
				for(Iterator iter = array.iterator(); iter.hasNext();){ 
				JSONObject jsonObject = (JSONObject)iter.next(); 
				list.add(JSONObject.toBean(jsonObject, clazz)); 
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list; 
		} 
		
		/** 
		* 从一个JSON 对象字符格式中得到一个java对象，形如： 
		* {"id" : idValue, "name" : nameValue, "aBean" : {"aBeanId" : aBeanIdValue, ...}} 
		* @param object 
		* @param clazz 
		* @return 
		*/ 
		@SuppressWarnings("rawtypes")
		public static Object getDTO(String jsonString, Class clazz){ 
			JSONObject jsonObject = null; 
			try{ 
			jsonObject = JSONObject.fromObject(jsonString); 
			}catch(Exception e){ 
			e.printStackTrace(); 
			} 
			return JSONObject.toBean(jsonObject, clazz); 
		} 
	   
		/**
		 * 将JSON转换成对象，对象内可包含List对象
		 * * {beansList:[{}, {}, ...], "name" : nameValue, "aBean" : {"aBeanId" : aBeanIdValue, ...},  } 
		 * @param jsonString
		 * @param clazz
		 * @param map
		 * @return
		 */
		@SuppressWarnings("rawtypes")
		public static Object getDTO(String jsonString, Class  clazz, Map<String, Object> object){ 
			 JSONObject jsonObject = null; 
			 try{ 
			 jsonObject = JSONObject.fromObject(jsonString); 
			 }catch(Exception e){ 
			 e.printStackTrace(); 
			 } 
			 return JSONObject.toBean(jsonObject, clazz, object); 
		} 
		
}
