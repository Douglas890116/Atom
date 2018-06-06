package com.maven.logger;

/**
 * 垂直(业务)日志记录
 * @author Administrator
 */
public class TLogger {
	
	private static LoggerManager MAINLOG = LoggerManager.getLogger("MAIN", new SwithObject(true));
	
	private static ThreadLocal<LoggerManager> currentLogger = new ThreadLocal<LoggerManager>(); 
	
	public static LoggerManager getLogger(){
		LoggerManager log = (LoggerManager)currentLogger.get();
		return log==null?MAINLOG:log;
	}
	
	public static void setLogger(LoggerManager loggerManager){
		currentLogger.set(loggerManager);
	}

}
