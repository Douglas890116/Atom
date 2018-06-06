package com.maven.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * AG亚游的帮助类
 * 来自于AG亚游.pdf
 * @author klay
 *
 */
public class NHQUtils {
	
	
	/**********初始化平台的大厅类型************/
	public static Map<String, String> __gameType = new HashMap<String, String>(){{
		this.put("1", "百家乐");
		this.put("2", "龙虎");
		this.put("3", "番摊");
		this.put("4", "轮盘");
		this.put("5", "骰宝");
		this.put("6", "极速百家乐");
	}};
	
	

}