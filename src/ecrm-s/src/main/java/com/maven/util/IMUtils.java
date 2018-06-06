package com.maven.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * IM体育的帮助类
 * @author klay
 *
 */
public class IMUtils {
	
	/**********初始化盘口类别************/
	public static Map<String, String> __Market = new HashMap<String, String>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		this.put("Early", "早盘");
		this.put("Today", "今日");
		this.put("Live", "滚球");
	}};
	
	/**********初始化盘口类别************/
	public static Map<String, String> __Period = new HashMap<String, String>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		this.put("1H", "上半场");
		this.put("2H", "下半场");
		this.put("FT", "全场");
	}};
	
	
}