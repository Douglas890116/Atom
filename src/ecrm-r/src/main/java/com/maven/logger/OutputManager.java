package com.maven.logger;

public class OutputManager {
	
	/** 系统RunLogger日志监控 */
	public static SwithObject LOG_SYSTEM_RUNLOGGER = new SwithObject(true);
	
	/** 系统访问日志 */
	public static SwithObject LOG_SYSTEMVISITELOG = new SwithObject(true);
	
	/** 会员报表日志 */
	public static SwithObject LOG_REPORTMEMBER = new SwithObject(true);
	
	/** 日投注返利活动日志 */
	public static SwithObject LOG_DAY_BUT_BONUS = new SwithObject(true);
	
}
