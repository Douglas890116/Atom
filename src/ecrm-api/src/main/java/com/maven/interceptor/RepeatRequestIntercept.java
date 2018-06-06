package com.maven.interceptor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.maven.exception.LogicTransactionException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.TLogger;

public class RepeatRequestIntercept {

	public static Map<String,Map<String,Date>> USER_REQUESTTIME = new HashMap<String, Map<String,Date>>();
	
	/**
	 * 
	 * @param employeecode
	 * @param url
	 * @param millinsecond
	 * @throws LogicTransactionException
	 */
	public static void isIntercept(String employeecode ,String url,long millinsecond) throws LogicTransactionException{
		Map<String,Date> employee_request = USER_REQUESTTIME.get(employeecode);
		if(employee_request==null){
			employee_request = new HashMap<String, Date>();
			USER_REQUESTTIME.put(employeecode, employee_request);
		}
		Date date = employee_request.get(url);
		if(date==null){
			employee_request.put(url, new Date());
		}else{
			TLogger.getLogger().Debug("employeecode:"+employeecode+" url:"+url+"  date:"+date+" 当前时间:"+new Date().getTime()+"  上次访问时间："+date.getTime()+"  间隔时间："+(new Date().getTime()-date.getTime()));
			if(new Date().getTime()-date.getTime()< millinsecond){
				throw new LogicTransactionRollBackException(millinsecond/1000 + "秒内不能重复请求 ");
			}
			TLogger.getLogger().Debug("请求成功:"+new Date());
			employee_request.put(url, new Date());
		}
	}
	
}
