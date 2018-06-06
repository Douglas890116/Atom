package com.maven.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerManager {
	
	private SwithObject swith = null;
	
	private Logger logger = null;

	private LoggerManager(String name,SwithObject swith){
		this.swith = swith;
		this.logger = LogManager.getLogger(name);
	}
	
	public static LoggerManager getLogger(String name,SwithObject swith){
		return new LoggerManager(name, swith);
	}
	
	public  void Trace(String message){
		if(swith.SWITH){
			logger.trace(message);
		}
	}
	public  void Trace(String message,Throwable ex){
		if(swith.SWITH){
			if(ex==null){
				logger.trace(message);
			}else{
				logger.trace(message,ex);
			}
		}
	}
	public  void Info(String message){
		if(swith.SWITH){
			logger.info(message);
		}
	}
	
	public  void Info(String message,Throwable ex){
		if(swith.SWITH){
			if(ex==null){
				logger.info(message);
			}{
				logger.trace(message,ex);
			}
		}
	}
	
	public  void Debug(String message){
		if(swith.SWITH){
			logger.debug(message);
		}
	}
	
	public  void Debug(String message,Throwable ex){
		if(swith.SWITH){
			if(ex==null){
				logger.debug(message);
			}else{
				logger.debug(message,ex);
			}
		}
	}
	public  void Warn(String message){
		if(swith.SWITH){
			logger.warn(message);
		}
	}
	public  void Warn(String message,Throwable ex){
		if(swith.SWITH){
			if(ex==null){
				logger.warn(message);
			}else{
				logger.warn(message,ex);
			}
		}
	}
	public  void Error(String message){
		logger.error(message);
	}
	public  void Error(String message,Throwable ex){
		if(ex==null){
			logger.error(message);
		}else{
			logger.error(message,ex);
		}
	}
	
	public  void Fatal(String message){
		logger.fatal(message);
	}
	
	public  void Fatal(String message,Throwable ex){
		if(ex==null){
			logger.fatal(message);
		}else{
			logger.fatal(message,ex);
		}
	}
}
