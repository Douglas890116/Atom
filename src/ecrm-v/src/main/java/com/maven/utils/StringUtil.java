package com.maven.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 字符串操作类 包括金额转换、类型转换时间转换等
 * 
 * @author xufc
 * 
 */
@SuppressWarnings("rawtypes") 
public class StringUtil {

	
	
	
	/**
	 * 日期加减（yyyyMMdd格式）
	 * 
	 * @param date
	 * @param num
	 *            （加减天数，可以是负数）
	 * @return
	 */
	public static int getDateAdd(int date, int num) {
		if (num == 0) {
			return date;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		try {
			Date date2 = dateFormat.parse(date + "");
			calendar.setTime(date2);
			calendar.add(Calendar.DATE, num);

			return Integer.parseInt(dateFormat.format(calendar.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
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
	public static int getDiffen(int currenDate, int date) {
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
	 * <p>
	 * Title: 验证手机号码
	 * </p>
	 * 
	 * @return
	 * @author xufc
	 * @created Feb 18, 2014 2:56:46 PM
	 */
	public static boolean checkPhoneNo(String phoneNum) {
		String regExp = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		java.util.regex.Matcher m = p.matcher(phoneNum);
		return m.find();
	}

	/**
	 * 
	 * <p>
	 * Title: 验证车牌号码（传入的车牌号要变成大写）
	 * </p>
	 * 
	 * @param carNo
	 * @return
	 * @author xufc
	 * @created Feb 18, 2014 3:00:18 PM
	 */
	public static boolean checkCarNo(String carNo) {
		carNo = carNo.toUpperCase();
		String regExp = "^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$";
		Pattern p = Pattern.compile(regExp);
		java.util.regex.Matcher m = p.matcher(carNo);
		return m.find();
	}

	/**
	 * 判断整数（大于0）
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		return str.matches("[\\d]+");
	}

	/**
	 * 判断小数（大于0）
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumberFloat(String str) {
		return str.matches("[\\d.]+");
	}

	/**
	 * <p>
	 * Title: 获取指定年月的�?�天�?
	 * </p>
	 * 
	 * @param yyyyMM
	 * @return
	 * @author xufc
	 * @created Oct 13, 2013 12:44:48 AM
	 */
	public static int getActualMaximum(int yyyyMM) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(yyyyMM / 100, yyyyMM % 100 - 1, 1);
		int days = calendar.getActualMaximum(Calendar.DATE);
		return days;
	}

	
	/**
	 * 判断是否存在数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断中文或非中文
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		if (str.length() < str.getBytes().length) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 产生 min～max的随机整�? 包括 min 但不包括 max
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandom(int min, int max) {
		int r = getRandom(max - min);
		return r + min;
	}

	/**
	 * 产生0～max的随机整�? 包括0 不包括max
	 * 
	 * @param max
	 *            随机数的上限
	 * @return
	 */
	public static int getRandom(int max) {
		return new Random().nextInt(max);
	}

	public static int getIntValue(Object obj) {
		if (obj == null || obj.equals(""))
			return 0;
		else
			return Integer.parseInt(obj.toString());
	}

	/**
	 * 尝试把Object转换为double，失败返�?0.0，不报错
	 * 
	 * @param obj
	 *            Object
	 * @return double
	 */
	public static double getDoubleValue(Object obj) {
		try {
			return Double.parseDouble(obj.toString());
		} catch (Exception e) {
			return 0.0;
		}
	}

	/**
	 * �?查输入字符串
	 * 
	 * @param strin
	 *            String 输入字符�?
	 * @return String 如果输入字符串为�?,则返回空字符�?,否则返回去除空格的字符串.
	 */
	public static String getTrimString(String strin) {
		if (strin == null) {
			strin = "";
		} else {
			strin = strin.trim();
		}

		if (strin.equals("")) {
			strin = "";
		}
		return strin;
	}

	/**
	 * �?查输入对�?,是否字符�?
	 * 
	 * @param o
	 *            Object 输入对象
	 * @return String 如果输入对象是字符串,且字符串为空,则返回空字符�?,否则返回去除空格的字符串.
	 */
	public static String getString(Object o) {

		String strv = "";
		try {

			if (o == null) {
				strv = "";
			} else {
				strv = (o.toString());
			}
		} catch (Exception ex) {

		}

		return strv;
	}

	/**
	 * �?查输入对�?,是否字符�?
	 * 
	 * @param o
	 *            Object 输入对象
	 * @return String 如果输入对象是字符串,且字符串为空,则返回空字符�?,否则返回去除空格的字符串.
	 */
	public static String getTrimString(Object o) {
		String strv = "";
		if (o == null) {
			strv = "0";
		} else if (o == "") {
			strv = "0";
		} else if (o == "null") {
			strv = "0";
		} else {
			strv = o.toString().trim();
		}

		return strv;
	}

	public static String moneyFormatStr2(double total) {

		String ret = "";

		try {

			total = round(total, 2);

			java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat(
					"####0.00");

			ret = decimalFormat.format(Math.round(total * 100) / (double) 100);

		} catch (Exception ex) {

			ex.printStackTrace();

			ret = "0.00";

		}

		return ret;
	}

	public static String handleNumber(Object obj) {
		if (obj == null)
			return "0";
		if (obj.toString().equals(""))
			return "0";
		if (obj.toString().equals("null"))
			return "0";
		else
			return obj.toString();
	}

	public static String handleNumber2(Object obj) {
		if (obj == null)
			return "0.00";
		if (obj.toString().equals("0"))
			return "0.00";
		if (obj.toString().equals("0.0"))
			return "0.00";
		if (obj.toString().equals(""))
			return "0.00";
		if (obj.toString().equals("null"))
			return "0.00";
		else
			return obj.toString();
	}

	/**
	 * 提供精确的小数位四舍五入处理�?
	 * 
	 * @param v
	 *            �?要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {

		if (scale < 0) {
			throw new IllegalArgumentException("参数必须大于0");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static double moneyFormatDouble2(double total) {

		double ret = 0;

		try {

			ret = round(total, 2);

		} catch (Exception ex) {

			ex.printStackTrace();

			ret = 0;

		}

		return ret;
	}

	public static double moneyFormatDouble4(double total) {

		double ret = 0;

		try {

			ret = round(total, 4);

		} catch (Exception ex) {

			ex.printStackTrace();

			ret = 0;

		}

		return ret;
	}

	public static double moneyFormatDouble6(double total) {

		double ret = 0;

		try {

			ret = round(total, 6);

		} catch (Exception ex) {

			ex.printStackTrace();

			ret = 0;

		}

		return ret;
	}

	/**
	 * �?测输入字符串是否浮点�?
	 * 
	 * @param strv
	 *            String 输入字符�?
	 * @return int 如果输入字符串是浮点�?,则把字符串转换为浮点�?,并返�?
	 */
	public static double getDouble(Object str) {

		String strv = getTrimString(str);
		double dret = 0;
		if (strv == null || strv.equals("null") || strv.equals("")) {
			dret = 0;
		} else {
			try {

				dret = Double.parseDouble(strv);
			} catch (Exception e) {
				System.err.println("str=" + str);
				e.printStackTrace();
				dret = 0;
			}
		}
		return dret;

	}

	/**
	 * �?测输入字符串是否浮点�?
	 * 
	 * @param strv
	 *            String 输入字符�?
	 * @return int 如果输入字符串是浮点�?,则把字符串转换为浮点�?,并返�?
	 */
	public static String getDouble_1(String str) {
		String retlStr = null;
		if (str != null && !"".equals(str)) {
			double d = Double.parseDouble(str);
			if (d != 0) {
				retlStr = d + "";
			}
		}
		return retlStr;
	}

	/**
	 * 字符串转换为两位小数的数�?
	 * 
	 * @param sObj
	 * @return
	 */
	public static String getStringFromDouble(String sObj) {
		String sNewObj = "";
		String sInteger = "";// 整数部分
		String sDecimal = "";// 小数部分
		if (sObj.length() > 2) {
			sInteger = sObj.substring(0, sObj.length() - 2);
			sDecimal = sObj.substring(sObj.length() - 2, sObj.length());
			sNewObj = sInteger + "." + sDecimal;
		} else {
			sNewObj = "0.00";
		}
		return sNewObj;

	}

	/**
	 * �?测输入字符串是否整数
	 * 
	 * @param strv
	 *            String 输入字符�?
	 * @return int 如果输入字符串是整数,则把字符串转换为整数,并返�?
	 */
	public static int getInt(Object str) {
		String strv = getTrimString(str);
		int iret = 0;
		if (strv == null) {
			iret = 0;
		} else {
			try {
				if (strv.indexOf(".") > 0) {
					strv = strv.substring(0, strv.indexOf("."));
				}
				iret = Integer.parseInt(strv);
			} catch (Exception e) {
				iret = 0;
			}
		}
		return iret;
	}

	public static long getLong(Object str) {
		String strv = getTrimString(str);
		long iret = 0;
		if (strv == null) {
			iret = 0;
		} else {
			try {
				if (strv.indexOf(".") > 0) {
					strv = strv.substring(0, strv.indexOf("."));
				}
				iret = Long.parseLong(strv);
			} catch (Exception e) {
				iret = 0;
			}
		}
		return iret;
	}

	/**
	 * �?测输入对象是否整�?
	 * 
	 * @param obj
	 *            Object 输入对象
	 * @return String 如果输入对象是整�?,则把输入对象转换为整�?,并返�?
	 */
	public static String getIntString(Object obj) {
		int iret = 0;
		if (obj == null) {
			iret = 0;
		} else {
			try {
				String strv = (String) obj;
				if (strv.indexOf(".") > 0) {
					strv = strv.substring(0, strv.indexOf("."));
				}
				iret = Integer.parseInt(strv);
			} catch (Exception e) {
				iret = 0;
			}
		}
		return String.valueOf(iret);
	}

	/**
	 * 取应用服务器日期,返回<=0为失�?
	 * 
	 * @return long 返回应用服务器的日期,例如:20070128
	 * @throws JOBExp
	 */
	public static long getDate() throws Exception {
		long lret = -1;
		String strdate = "0";
		Date today = new Date();
		DateFormat todayfmt = null;
		String stryear = "", strmonth = "", strday = "";
		try {
			todayfmt = DateFormat.getDateInstance(DateFormat.MEDIUM);
			strdate = todayfmt.format(today);
			stryear = strdate.substring(0, strdate.indexOf("-"));
			strdate = strdate.substring(strdate.indexOf("-") + 1);
			strmonth = strdate.substring(0, strdate.indexOf("-"));
			strdate = strdate.substring(strdate.indexOf("-") + 1);
			strday = strdate.substring(strdate.indexOf("-") + 1);
			if (strmonth.length() == 1) {
				strmonth = "0" + strmonth;
			}
			if (strday.length() == 1) {
				strday = "0" + strday;
			}
			lret = Long.parseLong(stryear + strmonth + strday);

		} catch (Exception ex) {
			lret = -1;
		} finally {
		}
		return lret;
	}

	/**
	 * 取应用服务器年份,返回<=0为失�?
	 * 
	 * @return long 取应用服务器年份,例如:2007
	 * @throws JOBExp
	 */
	public static long getYear() {
		long lret = -1;
		String strdate = "0";
		Date today = new Date();
		DateFormat todayfmt = null;
		String stryear = "";
		try {
			todayfmt = DateFormat.getDateInstance(DateFormat.MEDIUM);
			strdate = todayfmt.format(today);
			// SCom.printDebugLog(strdate);
			// SCom.printDebugLog(strdate.indexOf("-")+"");
			stryear = strdate.substring(0, strdate.indexOf("-"));
			lret = Long.parseLong(stryear);

		} catch (Exception ex) {
			lret = -1;
		} finally {
		}
		return lret;
	}

	/**
	 * 对输入日期进行格式化
	 * 
	 * @param date
	 *            String 输入日期,例如:20070202; 200702; 200702-200712,20070202123040
	 * @return String 输出日期,例如:2007-02-02;2007-02;2007-02-2007-12;2007-02-02
	 *         12:30:40
	 */
	public static String formatDate(String date) {

		StringBuffer buff = new StringBuffer();
		if (date == null || date.equals("0")) {
			return "---";
		} else {
			if (date.length() == 6) { // 6位日期格式化
				buff.append(date.substring(0, 4) + "-");
				buff.append(date.substring(4, 6) + " ");
			}
			if (date.length() == 8) { // 8位日期格式化
				buff.append(date.substring(0, 4) + "-");
				buff.append(date.substring(4, 6) + "-");
				buff.append(date.substring(6, 8) + " ");
			}
			if (date.length() == 13) { // 13位日期格式化
				buff.append(date.substring(0, 4) + "-");
				buff.append(date.substring(4, 6) + "-");
				buff.append("-");
				buff.append(date.substring(7, 11) + ":");
				buff.append(date.substring(11, 13) + ":");
			}
			if (date.length() == 14) { // 14位日期格式化
				buff.append(date.substring(0, 4) + "-");
				buff.append(date.substring(4, 6) + "-");
				buff.append(date.substring(6, 8) + " ");
				buff.append(date.substring(8, 10) + ":");
				buff.append(date.substring(10, 12) + ":");
				buff.append(date.substring(12, 14) + "");
			}

		}
		return buff.toString();
	}
	

	/**
	 * 对输入日期进行格式化
	 * 
	 * @param date
	 *            String 输入日期,例如: 20070202123040
	 * @return String 输出日期,例如:2007�?02�?02�?;2007�?02�?;2007�?02�?-2007�?12�?
	 */
	public static String formatFullDateTime(String date) {

		StringBuffer buff = new StringBuffer();
		if (date == null || date.equals("0")) {
			return "";
		} else {
			if (date.length() >= 14) {
				buff.append(date.substring(0, 4) + "-")
						.append(date.substring(4, 6) + "-")
						.append(date.substring(6, 8) + " ")
						.append(date.substring(8, 10) + ":")
						.append(date.substring(10, 12) + ":")
						.append(date.substring(12, 14));
			} else if (date.length() == 8) {
				buff.append(date.substring(0, 4) + "-")
						.append(date.substring(4, 6) + "-")
						.append(date.substring(6, 8) + " ").append("00:00:00");

			}

		}
		return buff.toString();
	}
	

	/**
	 * 从输入日期中,获取年份
	 * 
	 * @param date
	 *            String 输入时间,例如:200702021224
	 * @return int 输出年份,例如:2007
	 */
	public static int getYearFrmStr(String date) {
		StringBuffer buff = new StringBuffer();
		if (date == null || date.equals("0")) {
			return 0;
		} else {
			date = getTrimString(date);
			if (date.length() == 8 || date.length() == 12) { // 8位日期格�?
				buff.append(date.substring(0, 4));
			} else {
				return 0;
			}
		}
		return Integer.parseInt(buff.toString());
	}

	/**
	 * 从输入日期中,获取月份
	 * 
	 * @param date
	 *            String 输入时间,例如:"20070212" 或�?? "200702021224"
	 * @return int 输出月份,例如:2
	 */
	public static int getMonthFrmStr(String date) {
		StringBuffer buff = new StringBuffer();
		if (date == null || date.equals("0")) {
			return 0;
		} else {
			date = getTrimString(date);
			if (date.length() == 8 || date.length() == 12) { // 8位日期格�?
				buff.append(date.substring(4, 6));
			} else {
				return 0;
			}
		}
		return Integer.parseInt(buff.toString());
	}

	/**
	 * 从输入日期中,获取日份
	 * 
	 * @param date
	 *            String 输入时间,例如:"20070212" 或�?? "200702021224"
	 * @return int 输出日份,例如:2
	 */
	public static int getDayFrmStr(String date) {
		StringBuffer buff = new StringBuffer();
		if (date == null || date.equals("0")) {
			return 0;
		} else {
			date = getTrimString(date);
			if (date.length() == 8 || date.length() == 12) { // 8位日期格�?
				buff.append(date.substring(6, 8));
			} else {
				return 0;
			}
		}
		return Integer.parseInt(buff.toString());
	}

	/**
	 * 从输入日期中,获取小时
	 * 
	 * @param date
	 *            String 输入时间,例如:"200702021224"
	 * @return int 输出日份,例如:12
	 */
	public static int getHourFrmStr(String date) {
		StringBuffer buff = new StringBuffer();
		if (date == null || date.equals("0")) {
			return 0;
		} else {
			date = getTrimString(date);
			if (date.length() == 12) { // 8位日期格�?
				buff.append(date.substring(8, 10));
			} else {
				return 0;
			}
		}
		return Integer.parseInt(buff.toString());
	}

	/**
	 * 从输入日期中,获取分钟
	 * 
	 * @param date
	 *            String 输入时间,例如:"200702021224"
	 * @return int 输出分钟,例如:24
	 */
	public static int getMinFrmStr(String date) {
		StringBuffer buff = new StringBuffer();
		if (date == null || date.equals("0")) {
			return 0;
		} else {
			date = getTrimString(date);
			if (date.length() == 12) { // 8位日期格�?
				buff.append(date.substring(10, 12));
			} else {
				return 0;
			}
		}
		return Integer.parseInt(buff.toString());
	}

	/**
	 * 获取当前应用系统的日�?
	 * 
	 * @return String 当前应用系统的日�?,例如:200722
	 */
	public static String getCurrentDate() {
		String strTime = "-1";
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			strTime = dateFormat.format(date);
		} catch (Exception e) {
			e.fillInStackTrace();
		}
		return strTime;
	}

	/**
	 * 获取当前应用系统的日�?
	 * 
	 * @return String 当前应用系统的日�?,例如:200722
	 */
	public static String getCurrentDate_YM() {
		String strTime = "-1";
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
			strTime = dateFormat.format(date);

		} catch (Exception e) {
			e.fillInStackTrace();
		}
		return strTime;
	}

	/**
	 * 获取当前应用系统的前�?天日�?
	 * 
	 * @return String 当前应用系统的前�?天日�?,例如:20070203
	 */
	public static String getCurrentPrevDate(int delta) {
		String strTime = "-1";
		try {
			Calendar calendar = Calendar.getInstance();

			calendar.setTime(new Date());

			calendar.add(Calendar.DATE, delta);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			strTime = dateFormat.format(calendar.getTime());

		} catch (Exception e) {
			e.fillInStackTrace();
			strTime = "-1";
		}
		return strTime;
	}

	/**
	 * 计算若干个月之后的年�?
	 * 
	 * @param beginTime
	 *            �?始年�?
	 * @param month
	 *            经过月数
	 * @return
	 */
	public static int getMonthsLater(int beginYearMonth, int month) {
		int _year = beginYearMonth / 100;
		int _month = beginYearMonth - _year * 100;

		if (month > 0) {
			int _tempMon = _month + month; // - 1;
			if (_tempMon > 12) {
				_year = _year + _tempMon / 12;
				_tempMon = _tempMon % 12;
				if (_tempMon == 0) {
					_year = _year - 1;
					_tempMon = 12; // _month;
				}
			}
			return _year * 100 + _tempMon;
		} else if (month < 0) {
			_year = _year - Math.abs(month) / 12;
			int _tempMon = _month - Math.abs(month) % 12;
			if (_tempMon <= 0) {
				_year = _year - 1;
				_tempMon = 12 - Math.abs(_tempMon);
			}
			return _year * 100 + _tempMon;
		} else {
			return beginYearMonth;
		}

	}

	/**
	 * 获取当前应用系统的前�?天日�?
	 * 
	 * @return String 当前应用系统的前�?天日�?,例如:20070203
	 */
	public static String getAnyDeltaMonth(String strDate, int delta) {
		String strTime = "-1";

		int len = strDate.length();

		if (len == 6) {

			strDate = strDate + "01";

		} else if (len == 4) {

			strDate = strDate + "0101";
		}

		try {
			Calendar calendar = Calendar.getInstance();

			calendar.setTime(new Date());

			calendar.set(Calendar.YEAR, getYearFrmStr(strDate));

			calendar.set(Calendar.MONTH, getMonthFrmStr(strDate) - 1);

			calendar.set(Calendar.DATE, getDayFrmStr(strDate));

			if (len == 6) {
				calendar.add(Calendar.MONTH, delta);
			} else if (len == 8) {
				calendar.add(Calendar.DATE, delta);
			} else if (len == 4) {
				calendar.add(Calendar.YEAR, delta);
			}

			SimpleDateFormat dateFormat = null;

			if (len == 4) {

				dateFormat = new SimpleDateFormat("yyyy");

			} else if (len == 6) {

				dateFormat = new SimpleDateFormat("yyyyMM");

			} else if (len == 8) {

				dateFormat = new SimpleDateFormat("yyyyMMdd");
			}

			strTime = dateFormat.format(calendar.getTime());

		} catch (Exception e) {
			e.printStackTrace();
			strTime = "-1";
		}
		return strTime;
	}

	/**
	 * 获取当前年月
	 * 
	 * @return
	 */
	public static String getYYYYMM() {
		String strTime = "-1";
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
			strTime = dateFormat.format(date);

		} catch (Exception e) {
			strTime = "-1";
			e.fillInStackTrace();
		}
		return strTime;
	}

	/**
	 * 获取当前年月�?
	 * 
	 * @return
	 */
	public static String getYYYYMMdd() {
		String strTime = "-1";
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			strTime = dateFormat.format(date);

		} catch (Exception e) {
			strTime = "-1";
			e.fillInStackTrace();
		}
		return strTime;
	}

	/**
	 * 获取当前时间，精确到yyyyMMddHHmmss
	 * 
	 * xufc20130419 修改
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		String strTime = "-1";
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			strTime = dateFormat.format(date);

		} catch (Exception e) {
			strTime = "-1";
			e.fillInStackTrace();
		}
		return strTime;
	}

	public static String getyyyyMMddHHmmssSSS() {
		String strTime = "-1";
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyyMMddHHmmssSSS");
			strTime = dateFormat.format(date);

		} catch (Exception e) {
			strTime = "-1";
			e.fillInStackTrace();
		}
		return strTime;
	}

	/**
	 * 获取当前年月�?,2004-06-08 05:33:99
	 * 
	 * @return
	 */
	public static String getStryyyyMMddHHmmss() {
		String strTime = "-1";
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			strTime = dateFormat.format(date);

		} catch (Exception e) {
			strTime = "-1";
			e.fillInStackTrace();
		}
		return strTime;
	}

	/**
	 * 获取当前年月�?,2004-06-08 05:33:99
	 * 
	 * @return
	 */
	public static String getStryyyyMMddHHmmss(Date date) {
		String strTime = "-1";
		try {

			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			strTime = dateFormat.format(date);

		} catch (Exception e) {
			strTime = "-1";
			e.fillInStackTrace();
		}
		return strTime;
	}

	/**
	 * �?查是否数字（不带小数�?
	 * 
	 * @param sNumber
	 * @return
	 */
	public static boolean checkNumber(String sNumber) {
		if ("".equals(getTrimString(sNumber))) {
			return false;
		} else {
			try {
				Long.parseLong(sNumber);
				return true;
			} catch (Exception ex) {
				return false;
			}
		}
	}

	/**
	 * �?查是否数字（带两位小数）
	 * 
	 * @param sNumber
	 * @return
	 */
	public static boolean checkDecimal(String sNumber) {
		if ("".equals(getTrimString(sNumber))) {
			return false;
		} else {
			try {
				Double.parseDouble(sNumber);
				return true;
			} catch (Exception ex) {
				return false;
			}
		}
	}

	/**
	 * �?查输入日期的合法�?
	 * 
	 * @param strDate
	 *            String 输入日期,例如:"20070212"
	 * @return boolean �?查结�?
	 */
	public static boolean checkDateFormat(String strDate) {

		if (strDate == null) {
			return false;
		}
		if (strDate.length() != 8) {
			return false;
		} else {
			String strNum = "1234567890";
			char[] cStart = strDate.toCharArray();
			for (int i = 0; i < cStart.length; i++) {
				char c = cStart[i];
				if (strNum.indexOf(c) < 0) {
					return false;
				}
			}
		}
		return true;
	}

	// �?查时刻hh:mm的合法�??
	public static boolean checkClockFormat(String strClock) {
		if (strClock == null) {
			return false;
		}

		if (strClock.length() != 4) {
			return false;
		} else {

			// 小时
			String hour = strClock.substring(0, 2);
			// 分钟
			String min = strClock.substring(2, strClock.length());

			if (checkNumFormat(hour) == false) {
				return false;
			}

			if (checkNumFormat(min) == false) {
				return false;
			}

			int iHour = Integer.parseInt(hour);
			if (iHour < 0 || iHour > 23) {
				return false;
			}

			int iMin = Integer.parseInt(min);
			if (iMin < 0 || iMin > 59) {
				return false;
			}

		}
		return true;
	}

	public static boolean checkNumFormat(String strNum) {

		if (strNum == null) {
			return false;
		}

		char[] cMin = strNum.toCharArray();
		for (int i = 0; i < cMin.length; i++) {
			char c = cMin[i];
			if (strNum.indexOf(c) < 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 获得当前系统的日期和时刻
	 * 
	 * @return String 当前系统的日期和时刻,例如: 2007-1-29 11:40:54
	 */
	public static String getCurrentDateTimeFormat() {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance();
		return dateFormat.format(date);
	}

	/**
	 * 根据输入日期和输入时�?,转换成Date类型
	 * 
	 * @param strdate
	 *            String 输入日期
	 * @param strTime
	 *            String 输入时间
	 * @return Date 转换后的Date实例
	 */
	public static Date getDateFormat(String strdate, String strTime) {

		strTime += ":00";

		DateFormat dateFormat = DateFormat.getDateTimeInstance();
		if (getTrimString(strdate).equals("")) {
			return null;
		}

		StringBuffer buffer = new StringBuffer();
		buffer.append(strdate.substring(0, 4)).append("-")
				.append(strdate.substring(4, 6)).append("-")
				.append(strdate.substring(6, 8)).append(" ").append(strTime);

		try {
			return dateFormat.parse(buffer.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String FormatSQLDate(Date date) {

		if (date == null) {
			return "";
		}

		String strTime = "()";

		try {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			strTime = dateFormat.format(date);

		} catch (Exception e) {
			strTime = "-1";
			e.fillInStackTrace();
		}

		return strTime;
	}

	/**
	 * 返回当前时间加一天，自己传入格式标准
	 * 
	 * @param formatstr
	 * @return
	 */
	public static String formatCurrenDateAdd1day(String formatstr) {
		if (formatstr == null) {
			return "";
		}
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, 1);
			SimpleDateFormat dateFormat = new SimpleDateFormat(formatstr);
			return dateFormat.format(calendar.getTime());
		} catch (Exception e) {
			e.fillInStackTrace();
		}

		return null;
	}

	/**
	 * 返回当前时间，自己传入格式标�?
	 * 
	 * @param formatstr
	 * @return
	 */
	public static String formatCurrenDate(String formatstr) {
		if (formatstr == null) {
			return "";
		}
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(formatstr);
			return dateFormat.format(new Date());
		} catch (Exception e) {
			e.fillInStackTrace();
		}

		return null;
	}

	/**
	 * 根据输入Date�?,转换成对应的日期String,例如:20070202
	 * 
	 * @param date
	 *            Date 输入Date�?
	 * @return String 日期String
	 */
	public static String FormatDate(Date date) {

		if (date == null) {
			return "";
		}

		// 日期格式�?
		DateFormat dateFormat = DateFormat.getDateInstance();
		String strdate = dateFormat.format(date);

		// 获取年份
		String year = strdate.substring(0, strdate.indexOf("-"));
		// 去除年份
		strdate = strdate.substring(strdate.indexOf("-") + 1);

		// 获取月份
		String month = strdate.substring(0, strdate.indexOf("-"));
		if (month.length() == 1) {
			month = "0" + month;
		}

		// 获取日期
		String day = strdate.substring(strdate.indexOf("-") + 1);
		if (day.length() == 1) {
			day = "0" + day;
		}

		return year + month + day;
	}

	/**
	 * 格式化输入时�?,例如:12�?24�?, 输入时间�?1224,格式化后�?12:24
	 * 
	 * @param strTime
	 *            String 输入时间
	 * @return String 格式化时�?
	 */
	public static String getTimeFormat(String strTime) {
		if (strTime == null) {
			return "";
		}

		if (strTime.length() != 4) {
			return "";
		} else {

			// 小时
			String hour = strTime.substring(0, 2);
			// 分钟
			String min = strTime.substring(2, 4);

			if (checkNumFormat(hour) == false) {
				return "";
			}

			if (checkNumFormat(min) == false) {
				return "";
			}

			int iHour = Integer.parseInt(hour);
			if (iHour < 0 || iHour > 23) {
				return "";
			}

			int iMin = Integer.parseInt(min);
			if (iMin < 0 || iMin > 59) {
				return "";
			}
			return hour + ":" + min;
		}
	}

	/**
	 * 截取操作时间方法
	 * 
	 * @param czsj
	 *            String 操作时间,例如:"200702121230"
	 * @return String 截取操作时间的字符串,例如:"20070212"
	 * @throws Exception
	 */
	public static String subCZSJ(String czsj) throws Exception {

		// �?查参�?
		String date = getTrimString(czsj);
		if (date.equals("") || date.length() < 8) {
			return "";
		}

		return date.substring(0, 8);
	}

	public static String getFullDateTime() {
		SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat
				.getDateTimeInstance();

		return dateFormat.format(new Date());
	}

	public static String formatFullDateTime(Date date) {
		SimpleDateFormat dateFormat = (SimpleDateFormat) DateFormat
				.getDateTimeInstance();

		return dateFormat.format(date);
	}

	/**
	 * 根据格式返回时间字符�?
	 * 
	 * @param date
	 * @param pattent
	 * @return
	 */
	public static String formatDateTime(Date date, String pattent) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattent);
		return dateFormat.format(date);
	}

	public static String getStrValue(HttpServletRequest request, String key) {

		if (request == null || key == null) {
			return "";
		}
		return getTrimString(request.getParameter(key));
	}

	public static String getStrValue(HttpSession session, String key) {

		if (session == null || key == null) {
			return "";
		}
		return getTrimString(session.getAttribute(key));
	}

	public static String getStrValue(Hashtable hashtable, String key) {

		if (hashtable == null || key == null) {
			return "";
		}
		return getTrimString(hashtable.get(key));
	}

	public static String getStrValue(Map map, String key) {

		if (map == null || key == null) {
			return "";
		}
		return getTrimString(map.get(key));
	}

	public static String getStrValue(List ltindata, int index) {

		if (ltindata == null || ltindata.size() <= 0) {
			return "";
		}
		return getTrimString(ltindata.get(index));
	}

	public static String alertInfo(Object strInfo) {
		return "<script>alert('" + getTrimString(strInfo) + "')</script>";
	}

	public static String relocation(Object strInfo) {
		return "<script>document.location.href = \"" + getTrimString(strInfo)
				+ "\"</script>";
	}

	public static String refreshParent() {
		StringBuffer script = new StringBuffer();
		script.append("<script>")
				.append("window.opener.document.forms[0].submit();")
				.append("window.close();").append("</script>");

		return script.toString();
	}

	public static int getStringInt(String strin) {

		int iret;
		if (strin == null || strin.equals(""))
			iret = 0;
		else
			try {
				iret = Integer.parseInt(strin.trim());
			} catch (Exception e) {
				iret = 0;
			}
		return iret;
	}

	public static String[] split2(String tempaborts, String dot) {
		// 创建StringTokenizer对象
		StringTokenizer st = new StringTokenizer(tempaborts, dot);
		// 获取总数
		int len = st.countTokens();
		String[] val = new String[len];
		int m = 0;
		// 分割
		while (st.hasMoreTokens()) {
			val[m] = st.nextToken();
			m++;
		}
		return val;
	}

	public static String append(String[] strArrays, String delim) {

		if (strArrays == null)
			return null;

		if (delim == null)
			return null;

		StringBuffer strb_buff = new StringBuffer();

		for (int i = 0; i < strArrays.length; i++) {
			strb_buff.append(strArrays[i]).append(delim);
		}

		return strb_buff.toString();

	}

	public static String subString(String strin, int len) {

		if (strin == null || strin.equals("")) {
			return "";
		}
		try {
			int strlen = strin.length();
			if (strlen <= len) {
				return strin;
			} else {
				return strin.substring(0, len) + "...";
			}
		} catch (Exception ex) {
			ex.printStackTrace();

		}

		return "";
	}

	public static String retError(String strError) {

		// 声明字符缓冲
		StringBuffer strbuff = new StringBuffer();

		// 组合字符�?
		strbuff.append("error[@@#]").append(strError).append("[@@#](END)[@@#]");

		return strbuff.toString();
	}

	public static String NumToStr(String num) {

		BigDecimal bigDecimal = new BigDecimal(num);
		return bigDecimal.toString();
	}

	public static String getGender(String idno) {

		int sex = 0;
		if (idno.length() < 18) {
			sex = Integer.parseInt(idno.substring(14, 15));
		} else {
			sex = Integer.parseInt(idno.substring(16, 17));
		}
		return (sex % 2) == 1 ? "00" : "01";
	}

	public static String getfinancedistrict(String district) {

		String districtname = "";
		// 辖区名称转换
		if (district.equals("01")) {

			districtname = "香洲";

		} else if (district.equals("02")) {

			districtname = "金湾";

		} else if (district.equals("03")) {

			districtname = "斗门";

		} else if (district.equals("09")) {

			districtname = "万山";

		} else if (district.equals("11")) {

			districtname = "高栏�?";

		} else if (district.equals("12")) {

			districtname = "高新";

		} else if (district.equals("13")) {

			districtname = "横琴";

		} else {

			districtname = "辖区名称不存�?";

		}

		return districtname;
	}

	/**
	 * 从公民身份号码获得生�?
	 * 
	 * @param idno
	 *            身份证号�?
	 * @return int (正确返回yyyymmdd, 错误返回-1)
	 */
	public static String getBirthFromIdno(String idno) {

		if (idno == null) {
			return "-1";
		}

		int length = idno.length();
		String birthDay = "-1"; // 储存公民身份号码
		if (length == 15 || length == 15 + 1 || length == 15 + 2) { // 公民身份号码�?15�?
			birthDay = "19" + idno.substring(6, 12);
		} else if (length == 18 || length == 18 + 1) { // 公民身份号码�?18�?
			birthDay = idno.substring(6, 14);
		} else {
			return "-1";
		}
		return birthDay;

	}

	/**
	 * 从公民身份号码获得生�?
	 * 
	 * @param idno
	 *            身份证号�?
	 * @return int (正确返回yyyymmdd, 错误返回-1)
	 */
	public static String getBirthYYYYMMFromIdno(String idno) {

		if (idno == null) {
			return "-1";
		}

		int length = idno.length();
		String birthDay = "-1"; // 储存公民身份号码
		if (length == 15 || length == 15 + 1 || length == 15 + 2) { // 公民身份号码�?15�?
			birthDay = "19" + idno.substring(6, 10);
		} else if (length == 18 || length == 18 + 1) { // 公民身份号码�?18�?
			birthDay = idno.substring(6, 12);
		} else {
			return "-1";
		}
		return birthDay;

	}

	/**
	 * 获取两个日期间隔天数
	 * 
	 * @param startDate
	 *            �?始日�? YYYYMMDD
	 * @param endDate
	 *            结束日期 YYYYMMDD
	 * @return 间隔天数:有正，有�?
	 */
	public static int intervalOrgDays(int startDate, int endDate) {
		int interval = 0;
		int orgStartDate = startDate;
		int orgEndDate = endDate;
		int tempDate = 0;
		if (startDate > endDate) {
			tempDate = startDate;
			startDate = endDate;
			endDate = tempDate;
		}

		// 平年月的天数
		int _monthDays[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		String _startDate = Integer.toString(startDate);
		String _endDate = Integer.toString(endDate);

		int _startYear = Integer.parseInt(_startDate.substring(0, 4));
		int _startMonth = Integer.parseInt(_startDate.substring(4, 6));
		int _startDay = Integer.parseInt(_startDate.substring(6, 8));

		int _endYear = Integer.parseInt(_endDate.substring(0, 4));
		int _endMonth = Integer.parseInt(_endDate.substring(4, 6));
		int _endDay = Integer.parseInt(_endDate.substring(6, 8));

		// 计算�?始年度到当年底的天数
		int _passDay = 0; // 当年已过的天�?
		if (_startMonth < 3) {
			for (int index = 0; index < _startMonth - 1; index++) {
				_passDay += _monthDays[index];
			}
			_passDay += _startDay - 1;

			if (isLeapYear(_startYear)) {
				interval = 366 - _passDay;
			} else {
				interval = 365 - _passDay;
			}
		} else {
			for (int index = 0; index < _startMonth - 1; index++) {
				_passDay += _monthDays[index];
			}
			_passDay += _startDay - 1;

			interval = 365 - _passDay;
		}

		// 计算�?始下�?年度到结束前年度的天�?
		if (_startYear == _endYear) { // 同一�?
			if (isLeapYear(_startYear)) {
				interval -= 366;
			} else {
				interval = interval - 365;
			}
		}
		_startYear++;
		while (_startYear < _endYear) {
			if (isLeapYear(_startYear)) {
				interval += 366;
			} else {
				interval += 365;
			}
			_startYear++;
		}

		// 计算结束年度的天�?
		_passDay = 0;
		if (_endMonth > 3) {
			for (int index = 0; index < _endMonth - 1; index++) {
				_passDay += _monthDays[index];
			}
			_passDay += _endDay;

			if (isLeapYear(_endYear)) {
				interval += (_passDay + 1);
			} else {
				interval += _passDay;
			}
		} else {
			for (int index = 0; index < _endMonth - 1; index++) {
				_passDay += _monthDays[index];
			}
			_passDay += _endDay;

			interval += _passDay;
		}
		if (orgStartDate > orgEndDate) {
			interval = -interval;
		}

		return interval;
	}

	/**
	 * 是否闰年
	 * 
	 * @param CurYear
	 *            年度
	 * @return True - 是闰年； false - 平年
	 */
	public static boolean isLeapYear(int CurYear) {
		boolean _bIsLeap = false;
		// 判定平年闰年
		if (((CurYear % 4 == 0) && (CurYear % 100 != 0))
				|| (CurYear % 400 == 0)) {
			_bIsLeap = true;
		} else {
			_bIsLeap = false;
		}
		return _bIsLeap;
	}

	/**
	 * 根据当前年月，获得上�?年月
	 * 
	 * @param yearMonth
	 *            当前年月
	 * @return 上一年月
	 */
	public static String getPreviousMonth(String yearMonth) {

		String PreviousMonth = "";
		int year = new Integer(yearMonth.substring(0, 4)).intValue();
		int month = new Integer(yearMonth.substring(4, 6)).intValue();

		month = month - 1;
		if (month == 0) { // 跨年�?
			year = year - 1;
			month = 12;
		}

		if (month < 10) {
			PreviousMonth = String.valueOf(year) + "0" + String.valueOf(month);
		} else {
			PreviousMonth = String.valueOf(year) + String.valueOf(month);
		}

		return PreviousMonth;
	}

	/**
	 * 根据当前年月，获得下�?年月
	 * 
	 * @param yearMonth
	 *            当前年月
	 * @return 下一年月
	 */
	public static String getLastMonth(String yearMonth) {
		String LastMonth = "";
		int year = new Integer(yearMonth.substring(0, 4)).intValue();
		int month = new Integer(yearMonth.substring(4, 6)).intValue();
		month = month + 1;
		if (month == 13) { // 跨年�?
			year = year + 1;
			month = 1;
		}
		if (month < 10) {
			LastMonth = String.valueOf(year) + "0" + String.valueOf(month);
		} else {
			LastMonth = String.valueOf(year) + String.valueOf(month);
		}
		return LastMonth;
	}

	/**
	 * 在字符串左侧�?0
	 * 
	 * @param str
	 *            传入的字数串
	 * @param length
	 *            长度
	 * @return String 左侧补了0的字符串
	 */
	public static String setStringLength(String str, int length) {
		StringBuffer result = new StringBuffer(length);
		int len = 0;
		if (str == null || str.equals("")) {
			len = 0;
		} else {
			len = str.trim().getBytes().length;
		}
		int dif = length - len;
		if (dif > 0) {
			for (int i = 0; i < length - len; i++) {
				result.append("0");
			}
		}
		result.append(str);
		return result.toString();
	}

	/**
	 * 在字符串左侧补字�?
	 * 
	 * @param str
	 *            传入的字数串
	 * @param length
	 *            长度
	 * @return String 左侧补了字符的字符串
	 */
	public static String setStringBlankLength(String str, int length,
			String blank) {
		StringBuffer result = new StringBuffer(length);
		int len = 0;
		if (str == null || str.equals("")) {
			len = 0;
		} else {
			len = str.trim().getBytes().length;
		}
		int dif = length - len;
		if (dif > 0) {
			for (int i = 0; i < length - len; i++) {
				result.append(blank);
			}
		}
		result.append(str);
		return result.toString();
	}

	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		BigDecimal b3 = new BigDecimal(1);
		BigDecimal b4 = b1.subtract(b2);
		return Double.parseDouble(String.valueOf(b4.divide(b3, 6,
				BigDecimal.ROUND_HALF_UP)));
	}

	public static String add(double v1, double v2) {

		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		BigDecimal b3 = new BigDecimal(1);
		BigDecimal b4 = b1.add(b2);
		return String.valueOf(b4.divide(b3, 6, BigDecimal.ROUND_HALF_UP));
	}

	/**
	 * 提供（相对）精确的除法运算�?�当发生除不尽的情况时，由scale参数�? 定精度，以后的数字四舍五入�??
	 * 
	 * @param v1
	 *            被除�?
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示�?要精确到小数点以后几位�??
	 * @return 两个参数的商
	 */
	public static String div(String v1, String v2, int scale) {
		v1 = StoN(v1);
		v2 = StoN(v2);
		if (scale < 0) {
			throw new IllegalArgumentException("参数必须大于0");
		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		BigDecimal b3 = new BigDecimal(1);
		BigDecimal b4 = b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
		return String.valueOf(b4.divide(b3, 2, BigDecimal.ROUND_HALF_UP));
	}

	public static String StoN(String str) {
		String sReturn = "";
		if (str == null)
			str = "";
		if (str.trim().equals(""))
			sReturn = "0";
		else
			sReturn = str;
		return sReturn;
	}

	public static String getYYYYMMfromOneDay(int Date) {
		String strYYYYMM = "";

		int tmpYYYYMM = Date;

		strYYYYMM += tmpYYYYMM;

		while (tmpYYYYMM >= 199808) {
			tmpYYYYMM = getMonthsLater(tmpYYYYMM, -1);
			strYYYYMM += "," + tmpYYYYMM;
		}
		;
		return strYYYYMM.toString();
	}

	public static String getCurrentPrevDate() {
		String strTime = "-1";
		try {
			Calendar calendar = Calendar.getInstance();

			calendar.setTime(new Date());

			calendar.add(Calendar.DATE, -1);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			strTime = dateFormat.format(calendar.getTime());

		} catch (Exception e) {
			e.fillInStackTrace();
		}
		return strTime;
	}

	/**
	 * 
	 * <p>
	 * Title: 当前时间运算
	 * </p>
	 * <p>
	 * Description: 使用类似Calendar.DATE的写法，并传入加减的数�?�，返回格式化后的数�?
	 * </p>
	 * 
	 * @author xufc
	 * @version 1.0
	 * @created Aug 2, 2013 10:26:06 AM
	 */
	public static String getCurrentDateM(int type, int num, String formatStr) {
		try {
			Calendar calendar = Calendar.getInstance();
			// calendar.add(Calendar.DATE, -1);
			calendar.add(type, num);
			SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
			return dateFormat.format(calendar.getTime());
		} catch (Exception e) {
			e.fillInStackTrace();
		}
		return null;
	}

	private static String[] weeks = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };

	public static String getCurrentWeekChina(int value) {
		if (value < 0 || value > 6) {
			return "格式错误";
		} else {
			return weeks[value];
		}
	}

	/**
	 * <p>
	 * Title: 根据指定日期得到星期X
	 * </p>
	 * 
	 * @param date
	 *            yyyy-MM-DD或�?�yyyyMMdd 格式
	 * @return 星期日，星期�?�?
	 * @author xufc
	 * @created Oct 14, 2013 5:59:49 PM
	 */
	public static String getCurrentWeekChina(String date) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		if (date.indexOf("-") > -1) {
			sd = new SimpleDateFormat("yyyy-MM-dd");
		}
		SimpleDateFormat sdw = new SimpleDateFormat("E");
		Date d = null;
		try {
			d = sd.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdw.format(d);
	}

	public static void main(String[] args) {
		System.out.println(isNumeric("4541321"));
	}

	/**
	 * 全角转换成半�?
	 * 
	 * @param sValue
	 * @return
	 */
	public static String getQj2Bj(String sValue) {
		String[] sArrQj = { "�?", "�?", "�?", "�?", "�?", "�?", "�?", "�?",
				"�?", "�?", "�?", "�?", "�?", "�?", "�?", "�?", "�?", "�?",
				"�?", "�?", "�?", "�?", "�?", "�?", "�?", "�?", "�?", "�?",
				"�?", "�?", "�?", "�?", "�?", "�?", "�?", "�?", "�?", "�?",
				"�?" };
		String[] sArrBj = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"(", ")", "-", "A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
				"V", "W", "X", "Y", "Z" };
		String sNewString = "";
		for (int i = 0; i < sValue.length(); i++) {
			char chObj = sValue.charAt(i);
			boolean bolQj = false;
			for (int j = 0; j < sArrQj.length; j++) {
				if (sArrQj[j].equals(String.valueOf(chObj))) {
					sNewString += sArrBj[j];
					bolQj = true;
					break;
				}
			}
			if (!bolQj) {
				sNewString += chObj;
			}
		}
		return sNewString;
	}

	public static String handleNull(Object obj) {
		if (obj == null)
			return "";
		if (obj.toString().equals("null"))
			return "";
		if (obj.toString().equals("-=选择=-"))
			return "";
		if (obj.toString().equals("1900-01-01"))
			return "";
		return obj.toString().trim();
	}


	/**
	 * 计算两个年月之间相隔的月�?
	 * 
	 * @param StartDate
	 *            �?始的年月(int YYYYMM)
	 * @param EndDate
	 *            结束的年�?(int YYYYMM)
	 * @return 计算日期相隔的月�?
	 */
	public static int getDifferenceMonth(int startMonth, int endMonth) {
		int _iYearS = startMonth / 100;
		int _iYearE = endMonth / 100;
		int _iMonthS = startMonth - _iYearS * 100;
		int _iMonthE = endMonth - _iYearE * 100;
		int _iMonthSum = 0;
		int _iYearSum = _iYearE - _iYearS;
		if (_iYearSum == 0) {
			_iMonthSum = _iMonthE - _iMonthS;
		}
		if (_iYearSum >= 1) {
			_iMonthSum = (12 - _iMonthS) + _iMonthE + 12 * (_iYearSum - 1);
		}
		return _iMonthSum;
	}

	public static String moneyFormatStr2(String strtotal) {
		String ret = "";
		try {
			double total = Double.parseDouble(strtotal);
			total = round(total, 2);
			java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat(
					"####0.00");
			ret = decimalFormat.format(Math.round(total * 100) / (double) 100);
		} catch (Exception ex) {
			ex.printStackTrace();
			ret = "0.00";
		}
		return ret;
	}

	public static String moneyFormatStr(String strtotal) {
		String ret = "";
		try {
			double total = Double.parseDouble(strtotal);
			total = round(total, 2);
			java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat(
					"###,###,###.00");
			ret = decimalFormat.format(Math.round(total * 100) / (double) 100);
		} catch (Exception ex) {
			ex.printStackTrace();
			ret = "0.00";
		}
		return ret;
	}

	public static String moneyFormatStr(double total) {
		String ret = "";
		try {
			total = round(total, 2);
			java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat(
					"###,###.00");
			ret = decimalFormat.format(Math.round(total * 100) / (double) 100);
		} catch (Exception ex) {
			ex.printStackTrace();
			ret = "0.00";
		}
		return ret;
	}

	/**
	 * 返回当前传入时间加一天，自己传入格式标准
	 * 
	 * @param formatstr
	 * @return
	 */
	public static String formatCurrenDateAdddayBySelf(String formatstr,
			int dateSum) {
		if (formatstr == null) {
			return "";
		}
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, dateSum);
			SimpleDateFormat dateFormat = new SimpleDateFormat(formatstr);
			return dateFormat.format(calendar.getTime());
		} catch (Exception e) {
			e.fillInStackTrace();
		}

		return null;
	}

	public static String formatUnix(String date) {
		if (date == null || date.equals("")) {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date());
		}
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(
					getLong(date) * 1000));
		} catch (Exception e) {
			e.fillInStackTrace();
		}
		return date;
	}
	public static String fixPre(int i) {
		return (i > 9) ? String.valueOf(i) : "0" + i;
	}
	public static String formatYMD(Date date) {
		return format(date, "yyyyMMdd");
	}

	public static String formatYM(Date date) {
		return format(date, "yyyyMM");
	}

	public static String formatFullTime(Date date) {
		return format(date, "yyyyMMddHHmmssS");
	}

	public static String formatYMDHMS(Date date) {
		return format(date, "yyyyMMddHHmmss");
	}

	public static String formatDotDate(Date date) {
		return format(date, "yyyy.MM.dd");
	}

	public static String formatLineDate(Date date) {
		return format(date, "yyyy-MM-dd");
	}

	public static String formatChnDate(Date date) {
		return format(date, "yyyy年MM月dd日");
	}

	public static String formatTime(Date date) {
		return format(date, "HH:mm:ss");
	}
	
	/**
	 * 根据传入的字符串(yyyy-mm-yy)返回yyyy年MM月dd日
	 * @param recource
	 * @return
	 */
	public static String formatDateByStr(Object recource) {
		if(getString(recource).trim().equals("")){//没传入值就返回当前
			return formatChnDate(new Date());
		} else {
			String str = recource.toString();
			if(str.length() == 10) {
				String[] strs = str.split("-");
				return strs[0]+"年"+strs[1]+"月"+strs[2]+"日";
			} else if(str.length() == 7) {//只有yyyy-mm
				String[] strs = str.split("-");
				return strs[0]+"年"+strs[1]+"月01日";
			} else if(str.length() == "20120903112531565".length()) {//只有yyyymmddhhmmssfff
				return str.substring(0,4)+"年"+str.substring(4,6)+"月"+str.substring(6,8)+"日";
			} else if(str.indexOf("-") == -1) {//可能是yyyymmdd格式
				if(str.length() == 6){//yyyymm
					return str.substring(0,4)+"年"+str.substring(4,6)+"月"+"01日";
				}else if(str.length() == 8){//yyyymmdd
					return str.substring(0,4)+"年"+str.substring(4,6)+"月"+str.substring(6,8)+"日";
				}else {
					return formatChnDate(new Date());
				}
			} else {
				return formatChnDate(new Date());
			}
		}
	}

	/**
	 * 根据传入的字符串(yyyymmyy)返回yyyy年MM月
	 * @param recource
	 * @return
	 */
	public static String formatDateByStrYYYYMM(Object recource) {
		if(getTrimString(recource).equals("")){//没传入值就返回当前
			return "无数据";
		} else {
			String str = recource.toString();
			if(str.equals("0")) {
				return "无数据";
			} else if(str.length() == 4) {//只有年
				return str+"年";
			} else if(str.length() == 6) {//只有年月
				return str.substring(0,4)+"年"+str.substring(4,6)+"月";
			} else if(str.length() == 8) {//只有年月日
				return str.substring(0,4)+"年"+str.substring(4,6)+"月"+str.substring(6,8)+"日";
			} else {
				return str;
			}
		}
	}
	
	public static String format(Date date, String formatString) {

		if (date == null) {
			return "";
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String year = String.valueOf(cal.get(Calendar.YEAR));

		int month = cal.get(Calendar.MONTH) + 1;
		String M = String.valueOf(month);
		String MM = fixPre(month);

		int day = cal.get(Calendar.DATE);
		String d = String.valueOf(day);
		String dd = fixPre(day);

		int h24 = cal.get(Calendar.HOUR_OF_DAY);
		int h12 = cal.get(Calendar.HOUR);
		String H = String.valueOf(h24);
		String HH = fixPre(h24);
		String h = String.valueOf(h12);
		String hh = fixPre(h12);

		int minute = cal.get(Calendar.MINUTE);
		String m = String.valueOf(minute);
		String mm = fixPre(minute);

		int second = cal.get(Calendar.SECOND);
		String s = String.valueOf(second);
		String ss = fixPre(second);
		int ms = cal.get(Calendar.MILLISECOND);
		ms += 1000;
		String S = String.valueOf(ms).substring(1);

		formatString = formatString.replaceAll("yyyy", year);
		formatString = formatString.replaceAll("MM", MM);
		formatString = formatString.replaceAll("M", M);
		formatString = formatString.replaceAll("dd", dd);
		formatString = formatString.replaceAll("d", d);
		formatString = formatString.replaceAll("HH", HH);
		formatString = formatString.replaceAll("H", H);
		formatString = formatString.replaceAll("hh", hh);
		formatString = formatString.replaceAll("h", h);
		formatString = formatString.replaceAll("mm", mm);
		formatString = formatString.replaceAll("m", m);
		formatString = formatString.replaceAll("ss", ss);
		formatString = formatString.replaceAll("s", s);
		formatString = formatString.replaceAll("S", S);
		return formatString;
	}
	/**
     * 获取当前日期是星期几<br>
     * 
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
}
