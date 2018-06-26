package com.hy.pull.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//import org.apache.log4j.Logger;


/**
 * 日期和时间处理工具类（主要用于日期时间格式的转换和日期时间之间的换算等）
 * @Class DateUtil
 * @author yangang
 * @version 
 * @Date Feb 27, 2012 4:27:12 PM
 */
public class DateUtil {

	
	/**
	 * log4j日志记录对象实例
	 */
	//private static Logger log = Logger.getLogger(DateUtil.class);
	
	/**
	 * 获得系统当前时间
	 * @Method getCursorDate
	 * @param  @return
	 * @return Date
	 * @throws
	 */
	public static Date getCursorDate() {
		return Calendar.getInstance().getTime();
	}
	
	/**
	 * 两个日期相减，得到间隔天数（yyyyMMdd格式）
	 * 
	 * currenDate - date
	 * 
	 * 
	 * @param currenDate
	 * @param date
	 * @return
	 */
	public static int getDiffenDate(int currenDate, int date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		try {
			Date date1 = dateFormat.parse(currenDate + "");
			Date date2 = dateFormat.parse(date + "");
			long time1 = date1.getTime();
			long time2 = date2.getTime();
			long ddd = time1 - time2;
			int diffDate = (int) ddd / 3600000 / 24;
			System.out.println(diffDate);
			return diffDate;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 两个日期相减，得到间隔天数（Date格式）
	 * 
	 * currenDate - date
	 * 
	 * 
	 * @param currenDate
	 * @param date
	 * @return
	 */
	public static int getDiffenDate(Date currenDate, Date date) {

		try {
			long time1 = currenDate.getTime();
			long time2 = date.getTime();
			long ddd = time1 - time2;
			int diffDate = (int) ddd / 3600000 / 24;
			System.out.println(diffDate);
			return diffDate;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 获得系统当前日期时间指定格式的字符串
	 * @Method getCursorDate
	 * @param  @param format【合法的日期时间格式如：yyyy-MM-dd、yyyy-MM-dd HH：mm：ss、yyyy年MM月dd、yyyy/MM/dd HH：mm：ss等】
	 * @param  @return
	 * @return String【格式和format参数的一致】
	 * @throws
	 */
	public static String getCursorDate(String format) {
		String date = "";
		SimpleDateFormat sdf = null;
		if (format != null && !"".equals(format.trim())) {
			sdf = new SimpleDateFormat(format);
		} else {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		date = sdf.format(Calendar.getInstance().getTime());      
		return date;
	}
	
	/**
	 *  获得指定日期下一天的指定格式字符串
	 * @Method getNextDate
	 * @param  @param date【指定日期字符串格式必须与format参数的相同】
	 * @param  @param format【合法的日期时间格式如：yyyy-MM-dd、yyyy-MM-dd HH：mm：ss、yyyy年MM月dd、yyyy/MM/dd HH：mm：ss等】
	 * @param  @return
	 * @return String【格式和format参数的一致】
	 * @throws
	 */
	public static String getNextDate(String date,String format) {
		String date2 = "";
		SimpleDateFormat sdf = null;
		if (format != null && !"".equals(format.trim())) {
			sdf = new SimpleDateFormat(format);
		} else {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		
		Calendar ca=Calendar.getInstance();
		try {
			ca.setTime(sdf.parse(date));
		} catch (ParseException e) {
			//log.error(e);
		}
		ca.add(Calendar.DATE, 1);
		date2 = sdf.format(ca.getTime());      
		return date2;
	}
	
	/**
	 * 获得指定日期加上n个月后的指定格式字符串
	 * @Method addMonth
	 * @param  @param date【指定日期字符串格式必须与format参数的相同】
	 * @param  @param format【合法的日期时间格式如：yyyy-MM-dd、yyyy-MM-dd HH：mm：ss、yyyy年MM月dd、yyyy/MM/dd HH：mm：ss等】
	 * @param  @param n【月数】
	 * @param  @return
	 * @return String
	 * @throws
	 */
	public static String addMonth(String date,String format,int n) {
		String date2 = "";
		SimpleDateFormat sdf = null;
		if (format != null && !"".equals(format.trim())) {
			sdf = new SimpleDateFormat(format);
		} else {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		
		Calendar ca=Calendar.getInstance();
		try {
			ca.setTime(sdf.parse(date));
		} catch (ParseException e) {
			//log.error(e);
		}
		ca.add(Calendar.MONTH, n);
		date2 = sdf.format(ca.getTime());
		return date2;
	}
	
	/**
	 * 获取今天是星期几（返回中文的：星期日、星期六等）
	 * @Method getCursorWeek
	 * @param  @return
	 * @return String
	 * @throws
	 */
	public static String getCursorWeek() {
		String week="";
		Calendar cdate=Calendar.getInstance();
		if(cdate.get(Calendar.DAY_OF_WEEK) == 1) {
			week="星期日";
        }else if(cdate.get(Calendar.DAY_OF_WEEK) == 2) {
        	week="星期一" ;          
        }else if(cdate.get(Calendar.DAY_OF_WEEK) == 3) {
        	week="星期二" ;          
        }else if(cdate.get(Calendar.DAY_OF_WEEK) == 4) {
        	week="星期三" ;          
        }else if(cdate.get(Calendar.DAY_OF_WEEK) == 5) {
        	week="星期四" ;          
        }else if(cdate.get(Calendar.DAY_OF_WEEK) == 6) {
        	week="星期五" ;          
        }else if(cdate.get(Calendar.DAY_OF_WEEK) == 7) {
        	week="星期六" ;          
        }		
		return week;
	}


	/**
	 * 获得本周的第一天指定格式的日期字符串
	 * @Method getFirstDayOfWeek
	 * @param  @param format【合法的日期时间格式如：yyyy-MM-dd、yyyy-MM-dd HH：mm：ss、yyyy年MM月dd、yyyy/MM/dd HH：mm：ss等】
	 * @param  @return
	 * @return String
	 * @throws
	 */
	public static String getFirstDayOfWeek(String format) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar ca = Calendar.getInstance();
		ca.setTime(getCursorDate());
		int num = ca.get(Calendar.DAY_OF_WEEK);
		ca.add(Calendar.DATE, 1 - num);
		str = sdf.format(ca.getTime());
		return str;
	}

	/**
	 * 获得指定日期的周, 周的周一那天的日期
	 * @Method getFirstDayOfWeek
	 * @param  @param format【合法的日期时间格式如：yyyy-MM-dd、yyyy-MM-dd HH：mm：ss、yyyy年MM月dd、yyyy/MM/dd HH：mm：ss等】
	 * @param  @return
	 * @return String
	 * @throws
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		int num = ca.get(Calendar.DAY_OF_WEEK);
		ca.add(Calendar.DATE, 2 - num);
		return ca.getTime();
	}

	/**
	 * 获得本周的最后一天指定格式的日期字符串
	 * @Method getLastDayOfWeek
	 * @param  @param format【合法的日期时间格式如：yyyy-MM-dd、yyyy-MM-dd HH：mm：ss、yyyy年MM月dd、yyyy/MM/dd HH：mm：ss等】
	 * @param  @return
	 * @return String
	 * @throws
	 */
	public static String getLastDayOfWeek(String format) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar ca = Calendar.getInstance();
		ca.setTime(getCursorDate());
		int num = ca.get(Calendar.DAY_OF_WEEK);
		ca.add(Calendar.DATE, 7 - num);
		str = sdf.format(ca.getTime());
		return str;
	}

	/**
	 * 获得本月的第一天指定格式的日期字符串
	 * @Method getFirstDayOfMonth
	 * @param  @param format【合法的日期时间格式如：yyyy-MM-dd、yyyy-MM-dd HH：mm：ss、yyyy年MM月dd、yyyy/MM/dd HH：mm：ss等】
	 * @param  @return
	 * @return String
	 * @throws
	 */
	public static String getFirstDayOfMonth(String format) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar ca = Calendar.getInstance();
		ca.setTime(getCursorDate());
		ca.set(Calendar.DATE, 1);
		str = sdf.format(ca.getTime());
		return str;
	}
	
	/**
	 * 获取指定日期所在月的最后一天
	 * @Method getMothLastDay
	 * @param  @param date【指定日期】
	 * @param  @return
	 * @return Date
	 * @throws
	 */
    public static Date getMothLastDay(Date date){
    	if(date!=null){
    		Calendar ca=Calendar.getInstance();
    		ca.setTime(date);
    		ca.set(Calendar.MONTH, (ca.get(Calendar.MONTH)+1));
    		ca.set(Calendar.DATE, 1);
    		ca.add(Calendar.DATE, -1);
    		return ca.getTime();
    	}else{
    		return null;
    	}
    }

	/**
	 * 获得本月的最后一天指定格式的日期字符串
	 * @Method getLastDayOfMonth
	 * @param  @param format【合法的日期时间格式如：yyyy-MM-dd、yyyy-MM-dd HH：mm：ss、yyyy年MM月dd、yyyy/MM/dd HH：mm：ss等】
	 * @param  @return
	 * @return String
	 * @throws
	 */
	public static String getLastDayOfMonth(String format) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar ca = Calendar.getInstance();
		ca.setTime(getCursorDate());
		ca.add(Calendar.MONTH, 1);
		ca.set(Calendar.DATE, 1);
		ca.add(Calendar.DATE, -1);
		str = sdf.format(ca.getTime());
		return str;
	}

	/**
	 * 获得本季度的第一天指定格式的日期字符串
	 * @Method getFirstDayOfQuarter
	 * @param  @param format【合法的日期时间格式如：yyyy-MM-dd、yyyy-MM-dd HH：mm：ss、yyyy年MM月dd、yyyy/MM/dd HH：mm：ss等】
	 * @param  @return
	 * @return String
	 * @throws
	 */
	public static String getFirstDayOfQuarter(String format) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar ca = Calendar.getInstance();
		ca.setTime(getCursorDate());
		int num = ca.get(Calendar.MONTH);
		ca.set(Calendar.MONTH, num / 3 * 3);
		ca.set(Calendar.DATE, 1);
		str = sdf.format(ca.getTime());
		return str;
	}

	/**
	 * 获得本季度的最后一天指定格式的日期字符串
	 * @Method getLastDayOfQuarter
	 * @param  @param format【合法的日期时间格式如：yyyy-MM-dd、yyyy-MM-dd HH：mm：ss、yyyy年MM月dd、yyyy/MM/dd HH：mm：ss等】
	 * @param  @return
	 * @return String
	 * @throws
	 */
	public static String getLastDayOfQuarter(String format) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar ca = Calendar.getInstance();
		ca.setTime(getCursorDate());
		int num = ca.get(Calendar.MONTH);
		ca.set(Calendar.MONTH, (num / 3 + 1) * 3 - 1);
		ca.set(Calendar.DATE, 1);
		ca.add(Calendar.MONTH, 1);
		ca.add(Calendar.DATE, -1);
		str = sdf.format(ca.getTime());
		return str;
	}
	
	/**
	 * 把长整型日期转换成指定格式的字符串
	 * @Method getDateByLong
	 * @param  @param longdate【需要转换的长整型日期；参数类型为长整数值】
	 * @param  @param format【合法的日期时间格式如：yyyy-MM-dd、yyyy-MM-dd HH：mm：ss、yyyy年MM月dd、yyyy/MM/dd HH：mm：ss等】
	 * @param  @return
	 * @return String
	 * @throws
	 */
	 public static String getDateByLong(long longdate,String format){
		 Calendar ca=Calendar.getInstance();
		 ca.setTimeInMillis(longdate);
		 SimpleDateFormat sdf=new SimpleDateFormat(format);
		 return sdf.format(ca.getTime());
	 }

	/**
	 * 把长整型日期转换成指定格式的字符串
	 * @Method getDateByLong
	 * @param  @param longdate【需要转换的长整型日期；参数类型为字符串】
	 * @param  @param format【合法的日期时间格式如：yyyy-MM-dd、yyyy-MM-dd HH：mm：ss、yyyy年MM月dd、yyyy/MM/dd HH：mm：ss等】
	 * @param  @return
	 * @return String
	 * @throws
	 */
	 public static String getDateByLong(String longdate,String format){
		 Calendar ca=Calendar.getInstance();
		 long date=ca.getTimeInMillis();
		 try{date=Long.parseLong(longdate);}catch(Exception e){}
		 ca.setTimeInMillis(date);
		 SimpleDateFormat sdf=new SimpleDateFormat(format);
		 return sdf.format(ca.getTime());
	 }

	/**
	 * 把指定格式的日期转换为长整型数值
	 * @Method getLongByDate
	 * @param  @param date【指定格式的日期字符串】
	 * @param  @param format【合法的日期时间格式如：yyyy-MM-dd、yyyy-MM-dd HH：mm：ss、yyyy年MM月dd、yyyy/MM/dd HH：mm：ss等】
	 * @param  @return
	 * @return long
	 * @throws
	 */
	 public static long getLongByDate(String date,String format){
		 Calendar ca=Calendar.getInstance();
		 SimpleDateFormat sdf=new SimpleDateFormat(format);
		 try {
		 ca.setTime(sdf.parse(date));
		 } catch (Exception e) {
		 System.out.println("日期"+date+"不是"+format+"格式的！");
		 }
		 return ca.getTimeInMillis();
	 }
	 
	/**
	 * 把时间精确到日并转换成长整型数值
	 * @Method getNoTime
	 * @param  @param longdate【需要转换的长整型日期；参数类型为字符串】
	 * @param  @return
	 * @return long
	 * @throws
	 */
	 public static long getNoTime(String longdate){
		 Calendar ca=Calendar.getInstance();
		 long temp=ca.getTimeInMillis();
		 try{temp=Long.parseLong(longdate);}catch(Exception e){}
		 ca.setTimeInMillis(temp);
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		 String tempdate=sdf.format(ca.getTime());
		 try {
			 ca.setTime(sdf.parse(tempdate));
		 } catch (Exception e) {
			 //log.error(e);
		 }
		 return ca.getTimeInMillis();
	 }
	 
	/**
	 * 把指定日期时间精确到日如果为空就返回当前日期
	 * @Method parse
	 * @param  @param date【指定日期时间】
	 * @param  @return
	 * @return Calendar
	 * @throws
	 */
	public static Calendar parse(Date date) {
		Calendar ca = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String tempdate = sdf.format(date);
		try {
			ca.setTime(sdf.parse(tempdate));
		} catch (Exception e) {
			//log.error(e);
		}		
		return ca;
	}

	/**
	 * 把指定格式的日期字符串转换成日期
	 * @Method parse
	 * @param  @param date【指定格式的日期字符串】
	 * @param  @param format【合法的日期时间格式如：yyyy-MM-dd、yyyy-MM-dd HH：mm：ss、yyyy年MM月dd、yyyy/MM/dd HH：mm：ss等】
	 * @param  @return
	 * @return Date
	 * @throws
	 */
	public static Date parse(String date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date2 = null;
		if (date != null && !"".equals(date.trim())) {
			try {
				date2 = sdf.parse(date);
			} catch (ParseException e) {
				//log.error(e);
			}
		}
		return date2;
	}
	

	/**
	 * 把日期转换成指定格式的日期字符串
	 * @Method parse
	 * @param  @param date【需要转换的日期】
	 * @param  @param format【合法的日期时间格式如：yyyy-MM-dd、yyyy-MM-dd HH：mm：ss、yyyy年MM月dd、yyyy/MM/dd HH：mm：ss等】
	 * @param  @return
	 * @return String
	 * @throws
	 */
	public static String parse(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String temp = "";
		if(date!=null){
			try {
				temp = sdf.format(date);
			} catch (Exception e) {
				System.out.println(date + "不是【" + format + "】格式的日期！");
			}
		}
		return temp;
	}
	
    
	/**
	 * 在当前时间上加上months个月后的指定格式字符串
	 * @Method addMonths
	 * @param  @param months
	 * @param  @param format
	 * @param  @return
	 * @return String
	 * @throws
	 */
	public static String addMonths(int months,String format) {
		String date = "";
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.MONTH, months);
		date = new  SimpleDateFormat(format).format(ca.getTime());
		return date;
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
	
	/**
	 * 获取系统当前月份的中文名称
	 * @Method getMonth
	 * @param  @return
	 * @return String
	 * @throws
	 */
	public static String getMonth(){
		String[] month = {"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};
		GregorianCalendar   calendar=new   GregorianCalendar();
		return month[calendar.get(Calendar.MONTH)];
	}
	
	/**
	 * 获取系统当前年份的数值
	 * @Method getYear
	 * @param  @return
	 * @return int
	 * @throws
	 */
	public static int getYear(){
		GregorianCalendar   calendar=new   GregorianCalendar();
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 获取系统当前日的数值
	 * @Method getDay
	 * @param  @return
	 * @return int
	 * @throws
	 */
	public static int getDay(){
		GregorianCalendar   calendar=new   GregorianCalendar();
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	

	/**
	 * 获取系统未来一周的日期与中文星期的对应关系
	 * @Method getNextSevenDay
	 * @param  @return
	 * @return Map<String,String>
	 * @throws
	 */
	public static Map<String,String> getNextSevenDay(){
		
		String date = DateUtil.getCursorDate("yyyy-MM-dd");
		Map<String, String> dateMap = new TreeMap<String, String>();
		Calendar cdate = Calendar.getInstance();
		
		for(int i=0;i<7;i++){
			cdate.setTime(DateUtil.parse(date, "yyyy-MM-dd"));
			String week="";
			if(cdate.get(Calendar.DAY_OF_WEEK) == 1) {
				week="星期日";
	        }else if(cdate.get(Calendar.DAY_OF_WEEK) == 2) {
	        	week="星期一" ;          
	        }else if(cdate.get(Calendar.DAY_OF_WEEK) == 3) {
	        	week="星期二" ;          
	        }else if(cdate.get(Calendar.DAY_OF_WEEK) == 4) {
	        	week="星期三" ;          
	        }else if(cdate.get(Calendar.DAY_OF_WEEK) == 5) {
	        	week="星期四" ;          
	        }else if(cdate.get(Calendar.DAY_OF_WEEK) == 6) {
	        	week="星期五" ;          
	        }else if(cdate.get(Calendar.DAY_OF_WEEK) == 7) {
	        	week="星期六" ;          
	        }	
			dateMap.put(date, week);
			date = DateUtil.getNextDate(date, "yyyy-MM-dd");
		}
		
		return dateMap;
	}
	
	
	/**
	 * 获取某日期所在月份的所有天数
	 * @param date
	 * @param format
	 * @return
	 * @throws Exception
	 */
    public static List<String> getDaysOfMonth(String date,
			String format)throws Exception{
    	Calendar ca = Calendar.getInstance();
		List<String> list = new ArrayList<String>();
		try {
			SimpleDateFormat fmt = new SimpleDateFormat(format);
			ca.setTime(fmt.parse(date));
			int num = ca.get(Calendar.DAY_OF_MONTH);
			int month = ca.get(Calendar.MONTH);
			for (int i = 1; i < 32; i++) {
				ca.setTime(fmt.parse(date));
				ca.add(Calendar.DATE, i - num);
				if (ca.get(Calendar.MONTH) != month) {
					continue;
				}
				list.add(fmt.format(ca.getTime()));
			}			        
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("获取" + date + "所在月份的所有天数出错");
		}
    	return list;
    }
	
    /**
     * 当前时间 比 传入时间相差多少分钟。
     * @param date
     * @return
     */
    public static Integer subDate(Date date){
		return (int) ((System.currentTimeMillis() - date.getTime())/1000/60);
    }
    
    /**
     * 获取传入时间的当天开始时间
     * @param day
     * @param addsub
     * @return
     */
    public static Date getDayStart(Date day, int addsub){
    	Calendar currentDate = new GregorianCalendar();   
    	currentDate.add(Calendar.DATE, addsub);
    	currentDate.set(Calendar.HOUR_OF_DAY, 0);  
    	currentDate.set(Calendar.MINUTE, 0);  
    	currentDate.set(Calendar.SECOND, 0);
    	currentDate.set(Calendar.MILLISECOND, 0);
    	return (Date)currentDate.getTime();
    }
    
    
    public static void main(String[] args) {
    	
    	String today = DateUtil.parse(new Date(), "yyyy-MM-dd");//今天
    	String yestoday1 = DateUtil.parse(DateUtil.add(new Date(), Calendar.DATE, -1), "yyyy-MM-dd");//昨天
    	String yestoday2 = DateUtil.parse(DateUtil.add(new Date(), Calendar.DATE, -2), "yyyy-MM-dd");//前天
    	String yestoday3 = DateUtil.parse(DateUtil.add(new Date(), Calendar.DATE, -6), "yyyy-MM-dd");//一周内
    	String yestoday4 = DateUtil.parse(DateUtil.add(new Date(), Calendar.DATE, -6), "yyyy-MM-dd");//两周内
	}
    
}
