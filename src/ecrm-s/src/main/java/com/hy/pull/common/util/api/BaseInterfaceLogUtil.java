package com.hy.pull.common.util.api;

import java.util.Date;

import com.maven.cache.SystemCache;

/**
 * 接口操作日志工具
 * 
 * 
 * @author Administrator
 *
 */
public class BaseInterfaceLogUtil {

	/**
	 * 接口调用前日志
	 * @param gameType
	 * @param funCode
	 * @param params
	 */
	public static void addAPILog(String gameType, String funCode, String params) {
//		BaseInterfaceLog interfaceLog = new BaseInterfaceLog(gameType, funCode, params, null);
//		SystemCache.getInstance().addBaseInterfaceLog(interfaceLog);
//		System.out.println("开始接口调用："+interfaceLog.toString());
	}
	
	/**
	 * 接口调用后日志
	 * @param gameType
	 * @param funCode
	 * @param params
	 * @param content
	 */
	public static void addAPILog(String gameType, String funCode, String params, String content) {
//		BaseInterfaceLog interfaceLog = new BaseInterfaceLog(gameType, funCode, params, content);
//		SystemCache.getInstance().addBaseInterfaceLog(interfaceLog);
//		System.out.println("完成接口调用："+interfaceLog.toString());
	}
	
	/**
	 * 
	 * 获取日志流水号
	 * 
	 * 
	 * @return
	 */
	private static int POKE = 10000;
	public static String getLogid(){
		POKE++;
		if(POKE>=99999) POKE = 10000;
		return (new Date().getTime()*100000 + POKE) + "";
	}
	
	public static void main(String[] args) {
		String xxxx = "1481472729048";
		System.out.println(new Date(1481472729048l).toLocaleString());
	}
	
}
