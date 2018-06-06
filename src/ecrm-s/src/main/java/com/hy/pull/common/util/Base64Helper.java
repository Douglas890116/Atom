package com.hy.pull.common.util;

import org.apache.commons.codec.binary.Base64;
public class Base64Helper {

	/** 
	 * 编码 
	 * @param byteArray 
	 * @return 
	 */  
	   public static String encode(byte[] byteArray) {  
	       return new String(new Base64().encode(byteArray));  
	   }  
	  
	   /** 
	    * 解码 
	    * @param base64EncodedString 
	    * @return 
	    */  
	   public static byte[] decode(String base64EncodedString) {  
	    return new Base64().decode(base64EncodedString);  
	   }
}
