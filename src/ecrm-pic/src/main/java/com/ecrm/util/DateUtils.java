package com.ecrm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
	
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
	 * 获取年月日
	 * @param day
	 * @return
	 * @throws ParseException
	 */
	public static String getYearMonthDay(){
	    Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	
	
}
