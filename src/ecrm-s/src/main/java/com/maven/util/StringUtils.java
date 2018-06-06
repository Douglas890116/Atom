package com.maven.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	
	public static void main(String[] args) {
		
	}
	
	/**
	 * 将空值转换为无
	 * @param urlString
	 * @return
	 */
	public static Map<String, Object> getMapToValueNull(Map<String, Object> params) {
		try {
			Iterator<Entry<String, Object>> entries = params.entrySet().iterator();
			while (entries.hasNext()) {
				Entry<String, Object> entry = entries.next();
			    String key = (String)entry.getKey();
			    if(entry.getValue() == null || entry.getValue().equals("") || entry.getValue().equals("null")) {
			    	params.put(key, "无");
			    }
			}
			
			return params;
		} catch (Exception e) {
			e.printStackTrace();
			return params;
		}
	}
	
	/**
	 * 将空值转换为0
	 * @param urlString
	 * @return
	 */
	public static Map<String, Object> getMapToValueZero(Map<String, Object> params) {
		try {
			Iterator<Entry<String, Object>> entries = params.entrySet().iterator();
			while (entries.hasNext()) {
				Entry<String, Object> entry = entries.next();
			    String key = (String)entry.getKey();
			    if(entry.getValue() == null || entry.getValue().equals("") || entry.getValue().equals("null")) {
			    	params.put(key, "0");
			    }
			}
			return params;
		} catch (Exception e) {
			e.printStackTrace();
			return params;
		}
	}
	
	/**
	 * 根据给定URL字符串获取完整的跟域名，包括协议，端口
	 * @param urlString
	 * @return
	 */
	public static String getURLDomain(String urlString) {
		try {
			if(urlString == null) {
				return "";
			}
			java.net.URL  url = new  java.net.URL(urlString);
			String host = url.getHost();// 获取主机名 
			String port = url.getPort() + "" ;
			String domain = "";
			if(port.equals("-1")) {//不带端口
				domain = url.getProtocol()+"://"+host+"/";
			} else {
				domain = url.getProtocol()+"://"+host+":"+port+"/";
			}
			
			return domain;
		} catch (Exception e) {
			e.printStackTrace();
			return urlString;
		}
	}
	
	public static boolean equalsIgnoreCase(String str, String target) {
		if (str == null && target == null) return true;
		if (str == null && target != null) return false;
		if (str != null && target == null) return false;
		return str.equalsIgnoreCase(target);
	}
	
	public static boolean checkEmail(String email) {
		if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
			return false;
		}
		return true;
	}
	
	public static boolean stringFilterCheck(String str) {
		if(str.replaceAll("[\u4e00-\u9fa5]*[a-z]*[A-Z]*\\d*\\s*","").length()==0){ 
	        //不包含特殊字符 
	        return false; 
	    } 
	    return true; 
	}
	public static boolean isUpperOrUpper(String str) {
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (Character.isUpperCase(c)) {
				return true;
			}
			if(Character.isSpaceChar(c)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否是全字符或全数字
	 * 
	 * 如果是返回true
	 * @param str
	 * @return
	 */
	public static boolean isCharAllOrNumberAll(String str) {
		
		int digitCount = 0;
		int letterCount = 0;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (Character.isDigit(c)) {
				digitCount ++;
			}
			if(Character.isLetter(c)) {
				letterCount ++;
			}
		}
		if(digitCount == str.length() || letterCount == str.length()) {
			return true;
		}
		return false;
	}
	
	public static String stringFilterReplace(String str) {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	
	/**
	 * 对象转字符串
	 * yyyyMMddHHmmss
	 * @param str
	 * @return
	 */
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	public static String getCurrenDate() {
		String Time = sdf.format(new Date());
		return Time;
	}
	
	/**
	 * 对象转字符串
	 * yyyyMMddHHmmss
	 * @param str
	 * @return
	 */
	private static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMddHHmmsss");
	public static String getCurrenDateyyyyMMddHHmmss() {
		String Time = sdf3.format(new Date());
		return Time;
	}
	
	/**
	 * 对象转字符串
	 * yyyyMMddHHmmss
	 * @param str
	 * @return
	 */
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
	public static String getDate() {
		String Time = sdf2.format(new Date());
		return Time;
	}
	
	/**
	 * 查昨日日期
	 * yyyyMMddHHmmss
	 * @param str
	 * @return
	 */
	public static String getDateLast() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		String Time = sdf2.format(calendar.getTime());
		return Time;
	}
	
	/**
	 * 判断是否存在数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}
	
	public static List<String> getListByString(String str) {
		String[] strs = str.split(",");
		
		List<String> list = new ArrayList<String>();
		for (String string : strs) {
			list.add(string.trim());
		}
		return list;
	}
	
	/**
	 * 对象转字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String nullString(Object str) {
		if(str == null) {
			return "";
		} else {
			return str.toString().trim();
		}
	}
	
	/**
	 * 判断字符串是否有值
	 * 
	 * @param str
	 * @return
	 */
	public static boolean handString(Object str) {
		String string = nullString(str);
		if(string.length() > 0) {
			return true;
		} else{
			return false;
		}
	}

	/**
	 * 判断整数（大于0）
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		return str.matches("[\\d]+");
	}

	/**
	 * 判断小数（大于0）
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumberFloat(String str) {
		return str.matches("[\\d.]+");
	}

	
	 /** 
     * 去掉指定字符串的首尾特殊字符 
     *  
     * @param source 
     *            指定字符串 
     * @param beTrim 
     *            要去除的特殊字符 
     * @return 去掉特殊字符后的字符串 
     */  
    public static String trimStringWithAppointedChar(String source,  
            String beTrim) {  
        if (!source.equalsIgnoreCase("")) {  
            // 循环去掉字符串首的beTrim字符  
            String beginChar = source.substring(0, 1);  
            while (beginChar.equalsIgnoreCase(beTrim)) {  
                source = source.substring(1, source.length());  
                beginChar = source.substring(0, 1);  
            }  
  
            // 循环去掉字符串尾的beTrim字符  
            String endChar = source.substring(source.length() - 1, source  
                    .length());  
            while (endChar.equalsIgnoreCase(beTrim)) {  
                source = source.substring(0, source.length() - 1);  
                endChar = source  
                        .substring(source.length() - 1, source.length());  
            }  
        }  
        return source;  
    }  
    
    public static String trimPointChar(String s,String trimstr){
    	return s==null?null:s.replaceAll(trimstr, "");
    }
    
    /**
     * 将字符串数组转换成逗号分隔的字符串
     * @param s
     * @return
     */
    public static String toString(String[] s){
    	if(s==null||s.length==0) return null;
    	StringBuffer sf = new StringBuffer();
    	sf.append(s[0]);
    	for (int i=1;i<s.length;i++) {
			sf.append(",").append(s[i]);
		}
    	return sf.toString();
    }
    
 // 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
 
    /**
     * 完整的判断中文汉字和中文符号
     * 如果传入中包含，则返回true
     * 
     * 也就是不得出现全角字符
     * @param strName
     * @return
     */
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }
     
  
    
}
