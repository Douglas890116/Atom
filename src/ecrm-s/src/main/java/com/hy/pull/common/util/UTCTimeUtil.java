package com.hy.pull.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * UTC时间转换
 * @author Administrator
 *
 */
public class UTCTimeUtil {

	
	private static DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss") ;  
    
	/** 
     * 得到UTC时间，类型为字符串，格式为"yyyyMMddHHmmss"<br /> 
     * 如果获取失败，返回null 
     * @return 
     */  
    public static String getUTCTimeStr(Date date) {  
        StringBuffer UTCTimeBuffer = new StringBuffer();  
        // 1、取得本地时间：  
        Calendar cal = Calendar.getInstance() ;  
        cal.setTime(date);
        // 2、取得时间偏移量：  
        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);  
        // 3、取得夏令时差：  
        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);  
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：  
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));  
        
        String year = cal.get(Calendar.YEAR) + "";  
        String month = cal.get(Calendar.MONTH)+1 + "";  
        String day = cal.get(Calendar.DAY_OF_MONTH) + "";  
        String hour = cal.get(Calendar.HOUR_OF_DAY) + "";  
        String minute = cal.get(Calendar.MINUTE) + "";   
        String second = cal.get(Calendar.SECOND) + "";
        
        if(month.length() == 1) {
        	month = "0" + month;
        }
        if(day.length() == 1) {
        	day = "0" + day;
        }
        if(hour.length() == 1) {
        	hour = "0" + hour;
        }
        if(minute.length() == 1) {
        	minute = "0" + minute;
        }
        if(second.length() == 1) {
        	second = "0" + second;
        }
        
        UTCTimeBuffer.append(year).append("").append(month).append("").append(day) ;  
        UTCTimeBuffer.append("").append(hour).append("").append(minute).append("").append(second) ;  
        try{  
            format.parse(UTCTimeBuffer.toString()) ;  
            return UTCTimeBuffer.toString() ;  
        }catch(ParseException e)  
        {  
            e.printStackTrace() ;  
        }  
        return null ;  
    }  
    
    /** 
     * 得到UTC时间，类型为字符串，格式为"yyyyMMddHHmmss"<br /> 
     * 如果获取失败，返回null 
     * @return 
     */  
    public static String getUTCTimeStr() {  
        StringBuffer UTCTimeBuffer = new StringBuffer();  
        // 1、取得本地时间：  
        Calendar cal = Calendar.getInstance() ;  
        // 2、取得时间偏移量：  
        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);  
        // 3、取得夏令时差：  
        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);  
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：  
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));  
        
        String year = cal.get(Calendar.YEAR) + "";  
        String month = cal.get(Calendar.MONTH)+1 + "";  
        String day = cal.get(Calendar.DAY_OF_MONTH) + "";  
        String hour = cal.get(Calendar.HOUR_OF_DAY) + "";  
        String minute = cal.get(Calendar.MINUTE) + "";   
        String second = cal.get(Calendar.SECOND) + "";
        
        if(month.length() == 1) {
        	month = "0" + month;
        }
        if(day.length() == 1) {
        	day = "0" + day;
        }
        if(hour.length() == 1) {
        	hour = "0" + hour;
        }
        if(minute.length() == 1) {
        	minute = "0" + minute;
        }
        if(second.length() == 1) {
        	second = "0" + second;
        }
        
        UTCTimeBuffer.append(year).append("").append(month).append("").append(day) ;  
        UTCTimeBuffer.append("").append(hour).append("").append(minute).append("").append(second) ;  
        try{  
            format.parse(UTCTimeBuffer.toString()) ;  
            return UTCTimeBuffer.toString() ;  
        }catch(ParseException e)  
        {  
            e.printStackTrace() ;  
        }  
        return null ;  
    }  
      
    /** 
     * 将UTC时间转换为东八区时间 
     * @param UTCTime 
     * @return 
     */  
    public static String getLocalTimeFromUTC(String UTCTime){  
        java.util.Date UTCDate = null ;  
        String localTimeStr = null ;  
        try {  
            UTCDate = format.parse(UTCTime);  
            format.setTimeZone(TimeZone.getTimeZone("GMT-8")) ;  
            localTimeStr = format.format(UTCDate) ;  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
           
        return localTimeStr ;  
    }  
      
    public static void main(String[] args) {   
        String UTCTimeStr = getUTCTimeStr() ;  
        System.out.println(UTCTimeStr);   
        System.out.println(getLocalTimeFromUTC(UTCTimeStr));  
    }  
    
    
}
