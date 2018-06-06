package com.maven.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import com.maven.exception.ArgumentValidationException;

public class AttrCheckout {
	
	/**
	 * 验证参数是否存在 , 
	 * 如传入 variable 参数则执行严格校验，将所有执行校验的参数克隆到新对象
	 * @param obj 参数对象
	 * @param attribute 须校验参数
	 * @param variable 除校验参数外须克隆参数
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T checkout(T obj,boolean clone,String[] attribute, String... variable) throws Exception{
		if(attribute.length == 0 || obj == null) return obj;
			Object object = null;
			if(obj instanceof Map){
				object = attributeMap(obj,clone,  attribute,variable);
	    	}else if(obj instanceof List){
	    		object = attributeList(obj,clone, attribute ,variable);
	    	}else{
	    		object = attributeClass(obj,clone, attribute ,variable);
	    	}
			return (T)object;
	}
	
	/**
	 * Map参数验证
	 * @param obj
	 * @param attribute
	 * @param variable
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static <T> T attributeMap(T obj,boolean clone,String[] attribute,String... variable) throws Exception{
		Map<String,Object> object = (Map<String, Object>)obj;
		for (String key : attribute) {
			if(object.get(key)==null) throw new ArgumentValidationException("parameter [ "+key+" ] is null");
		}
		if(clone){
			String [] keys = (String[])ArrayUtils.addAll(attribute, variable);
			Map<String,Object> cloneObject = new HashMap<String, Object>();
			for (String vkey : keys) {
				cloneObject.put(vkey, object.get(vkey));
			}
			return (T)cloneObject;
		}
		return (T)obj;
	}
	
	
	/**
	 * 对象参数验证
	 * @param obj
	 * @param attribute
	 * @param variable
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static <T> T attributeClass(T obj,boolean clone,String[] attribute,String... variable) throws Exception{
        for (String fieldName : attribute) {
    		 PropertyDescriptor descriptor=new PropertyDescriptor(fieldName, obj.getClass());
    		 Method readMethod=descriptor.getReadMethod();
             Object dataValue=readMethod.invoke(obj);
             if(dataValue==null) throw new ArgumentValidationException("parameter  [ "+fieldName+" ] is null");
		}
        if(clone){
        	Object cloneObject= Class.forName(obj.getClass().getName()).newInstance();
        	String [] keys = (String[])ArrayUtils.addAll(attribute, variable);
        	for (String vfieldName : keys) {
       		 	PropertyDescriptor descriptor=new PropertyDescriptor(vfieldName, obj.getClass());
       		 	Method readMethod=descriptor.getReadMethod();
       		 	Object dataValue=readMethod.invoke(obj);
                Method writeMethod=descriptor.getWriteMethod();
                writeMethod.invoke(cloneObject, dataValue);
    		}
        	return (T)cloneObject;
        }
		return (T)obj;
	}
	
	/**
	 * List集合参数验证
	 * @param obj
	 * @param attribute
	 * @param variable
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static <T> T attributeList(T obj,boolean clone,String[] attribute,String... variable) throws Exception {
		List<Object> cloneObject = new ArrayList<Object>();
		List<Object> object = (List<Object>)obj;
		for (Object o : object) {
			Object oc = null;
			if(obj instanceof Map){
				oc = attributeMap(o, clone, attribute ,variable);
	    	}else{
	    		oc = attributeClass(o,clone, attribute ,variable);
	    	}
			cloneObject.add(oc);
		}
		return (T)cloneObject;
 	}
	
	/**
	 * 转换1,2,3类型string参数转换为List<Integer> 
	 * @param s_list
	 * @return
	 */
	public static List<Integer> convertListString(String s) {
		List<String> s_list = Arrays.asList(s.split(","));
		List<Integer> i_list = new ArrayList<Integer>();
		for (int i=0; i<s_list.size(); i++) {
			i_list.add(Integer.valueOf(s_list.get(i)));
		}
		return i_list;
	}
	
}
