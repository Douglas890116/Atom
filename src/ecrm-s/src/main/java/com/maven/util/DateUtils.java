package com.maven.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
	
	private static SimpleDateFormat defaultSimpleDateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
	
	/**
	 * 获取今天的起始时间
	 * @return
	 */
	public static String getTodayByString() {
		Calendar calendar = Calendar.getInstance();  
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    return defaultSimpleDateFormat.format(calendar.getTime());
	}
	
	/**
	 * 获取今天的起始时间
	 * @return
	 */
	public static String getTodayByDateTime() {
	    return defaultSimpleDateFormat.format(new Date());
	}
	
	/**
	 * 将传入的时间格式化成 yyyy:MM:dd HH:mm:ss 格式
	 * @param _Date 
	 * @return   yyyy:MM:dd HH:mm:ss 格式字符串
	 */
	public static String FormatTimeStyle(Date _Date){
		if(_Date==null) return "";
		SimpleDateFormat _SimpleDateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
		return _SimpleDateFormat.format(_Date);
	}
	
	/**
	 * 将传入时间格式化成 yyyy-MM-dd 格式字符串
	 * @param _Date
	 * @return
	 */
	public static String FormatDateStyle(Date _Date){
		if(_Date==null) return "";
		SimpleDateFormat _SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
		return _SimpleDateFormat.format(_Date);
	}
	
	/**
	 * 将传入 yyyy-MM-dd字符串 转换成Date对象
	 * @param _Date
	 * @return
	 */
	public static Date FormatDateStringTime(String _Date) throws ParseException{
		if(_Date==null) return new Date();
		SimpleDateFormat _SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE);
		return _SimpleDateFormat.parse(_Date);
	}
	
	/**
	 * 将传入 yyyy-MM-dd HH:mm:ss字符串 转换成Date对象
	 * @param _Date
	 * @return
	 */
	public static Date FormatStandardDateStringTime(String _Date) throws ParseException{
		if(_Date==null) return new Date();
		SimpleDateFormat _SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
		return _SimpleDateFormat.parse(_Date);
	}
	
	/**
	 * 将传入时间格式化成 yyyy/MM/dd HH:mm:ss 格式字符串
	 * @param _Date
	 * @return
	 */
	public static String FormatTimeToJSDate(Date _Date){
		if(_Date==null) return null;
		SimpleDateFormat _SimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
		return _SimpleDateFormat.format(_Date);
	}
	
	/**
	 * 将传入时间格式化成 yyyy-MM-dd HH:mm:ss 格式字符串
	 * @param _Date
	 * @return
	 */
	public static String FormatTimeToStandarStringTime(Date _Date){
		if(_Date==null) return null;
		SimpleDateFormat _SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
		return _SimpleDateFormat.format(_Date);
	}
	
	/**
	 * 将传入时间格式化成dd格式字符串
	 * @param _Date
	 * @return
	 */
	public static String FormatTimeToddString(Date _Date){
		if(_Date==null) return null;
		SimpleDateFormat _SimpleDateFormat = new SimpleDateFormat("dd", Locale.SIMPLIFIED_CHINESE);
		return _SimpleDateFormat.format(_Date);
	}
	
	/**
	 * 将传入的时间字符串转换成时间格式
	 * @param _Date
	 * @return
	 * @throws ParseException
	 */
	public static Date FormatStringToDate(String _Date) throws ParseException{
		SimpleDateFormat _SimpleDateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
		return _SimpleDateFormat.parse(_Date);
	}
	
	/**
	 * 将传入的字符串转换成yyyymmddHHmmss时间格式
	 * @param _Date
	 * @return
	 * @throws ParseException
	 */
	public static String FormatDateToString(Date _Date,String formartstyle) throws ParseException{
		SimpleDateFormat _SimpleDateFormat = new SimpleDateFormat(formartstyle, Locale.SIMPLIFIED_CHINESE);
		return _SimpleDateFormat.format(_Date);
	}
	
	/**
	 * 获取年月日
	 * @param day
	 * @return
	 * @throws ParseException
	 */
	public static String getYearMonthDay() throws ParseException{
	    Calendar calendar = Calendar.getInstance();
        String  yestedayDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());	  
        return yestedayDate;
	}
	
	/**
	 * 获取月份第一天
	 * @parame monthplus -为减去月份 +为增加月份
	 * @return
	 */
	public static String getfirstDayofMonth(int monthplus){
	    String firstDayofMonth;
	    Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.MONTH, monthplus);
	    calendar.set(Calendar.DAY_OF_MONTH, 1);
	    firstDayofMonth = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	    return firstDayofMonth+" 00:00:00";
	}
	
	/**
	 * 获取月份最后第一天
	 * @parame monthplus -为减去月份 +为增加月份
	 * @return
	 */
	public static String getlastDayofMonth(int monthplus){
	    String lastDayofMonth;
	    Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.MONTH, monthplus+1);
	    calendar.set(Calendar.DAY_OF_MONTH, 1);
	    calendar.add(Calendar.DATE, -1);
	    lastDayofMonth = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	    return lastDayofMonth+" 23:59:59";
	}
	
	/**
	 * 获取上周第一天
	 * @return
	 */
	public static Date getLastMonday() throws Exception{
	    Calendar cal = Calendar.getInstance();
	    cal.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
	    cal.add(Calendar.DATE, -1*7);
	    cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
	    String lastMonday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	    return DateUtils.FormatDateStringTime(lastMonday);
	}
	
	/**
	 * 获取上周最后一天
	 * @return
	 */
	public static Date getLastSunday() throws Exception{
	    Calendar cal = Calendar.getInstance();
	    cal.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
	    cal.add(Calendar.DATE, -1*7);
	    cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	    String lastSunday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	    return DateUtils.FormatDateStringTime(lastSunday);
	}
	
	/**
	 * 获取上周第一天（相对时间）
	 * @return
	 */
	public static Date getLastMonday(Date date) throws Exception{
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
	    cal.add(Calendar.DATE, -1*7);
	    cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
	    String lastMonday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	    return DateUtils.FormatDateStringTime(lastMonday);
	}
	
	/**
	 * 获取上周最后一天（相对时间）
	 * @return
	 */
	public static Date getLastSunday(Date date) throws Exception{
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
	    cal.add(Calendar.DATE, -1*7);
	    cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	    String lastSunday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	    return DateUtils.FormatDateStringTime(lastSunday);
	}
	
	
}
