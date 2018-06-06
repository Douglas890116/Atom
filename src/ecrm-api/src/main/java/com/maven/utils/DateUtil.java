package com.maven.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {
    
    /**
     * 
     * @param startDateTime
     * @param endDateTime
     * @param timeType  1:返回相差小时   2：返回相差分钟
     * @return
     * @throws ParseException 
     */
    public static long dateSubtraction(Date startDateTime, Date endDateTime, int timeType) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (startDateTime != null && endDateTime != null) {
            long temp = 0;
            try {
              temp = format.parse(format.format(endDateTime)).getTime() - startDateTime.getTime();
            } catch (ParseException e) {
              e.printStackTrace();
            }
            if(1 == timeType){
              //返回相差小时
              return temp / 1000 / 3600;
            }else if (2 == timeType){
              //返回相差分钟
              return temp / 1000 / 3600 * 60;
            }
            return 0L;
        }
        return 0;
    }
    
    /**
	 * 把日期date的parm参数的值加上number
	 * @Method add
	 * @param  @param date【需要进行运算的日期】
	 * @param  @param parm【日期参数如：年（Calendar.YEAR）、月（Calendar.MONTH）、日（Calendar.DATE）、小时（Calendar.HOUR）、分钟（Calendar.MINUTE）、秒（Calendar.SECOND）】
	 * @param  @param number【需要加的数值。传入负数可进行减操作】
	 * @param  @return
	 * @return Date
	 * @throws
	 */
	public static Date add(Date date,int parm,int number){
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(parm, number);
		return ca.getTime();
	}
}
