package com.maven.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class RegularCheck {
	
	/**
	 * 判断是否是域名
	 * @param domain
	 * @return
	 */
	public static boolean isDomain(String domain){
		 Pattern p = Pattern.compile("^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$",Pattern.CASE_INSENSITIVE );
		 Matcher m = p.matcher(domain);    
		 return m.find();
	}
	
	/**
	 * 获取一个URL的对应的域名,如果为手机版域名转换成pc版域名
	 * @param url
	 * @return
	 */
	public static String takeDomain(String url){
		if(StringUtils.isBlank(url)) return url;
		if(url.startsWith("http://")){
			url = url.replace("http://", "");
		}else if(url.startsWith("https://")){
			url = url.replace("https://", "");
		}
		int len = url.indexOf(":")>0?url.indexOf(":"):url.indexOf("/");
		if(len>0){
			url = url.substring(0,len);
		}
		return url.replaceAll("^[m,M]\\.", "");
	}
	
	public static void main(String[] args) {
		String url = "aM.0012.zhpt.com";
		System.out.println(isDomain(url));
	}

}
