package com.hy.pull.common.util;

import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateFormatUtils;

import com.hy.pull.common.util.game.mg.MGUtil;

import sun.misc.BASE64Encoder;

/**
 * 申博加密解密工具类
 * 
 * @author Administrator
 *
 */
public class SGSUtil {

	private static final String pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	public static SimpleDateFormat foo = new SimpleDateFormat(pattern);
	
	private static final String pattern2 = "yyyy-MM-dd'T'HH:mm:ssZZ";
	public static SimpleDateFormat foo2 = new SimpleDateFormat(pattern2);
	
	public static String getDateCurren() {
		return DateFormatUtils.format(new Date(), pattern2);
	}
	public static String getDateCurrenStart1hour() {
		Calendar calendar=new GregorianCalendar();
		calendar.add(Calendar.MINUTE, -20);
		return DateFormatUtils.format(calendar.getTime(), pattern2);
	}
	
	/**
	 * 获取当前时间IOS格式
	 * @return
	 */
	public static String getIsoDateCurren() {



//		System.out.println("gc.getTime():"+gc.getTime());


//		System.out.println("gc.getTimeInMillis():"+new Date(gc.getTimeInMillis()));




		//当前系统默认时区的时间：


		Calendar calendar=new GregorianCalendar();


//		System.out.print("时区："+calendar.getTimeZone().getID()+" ");


//		System.out.println("时间："+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE));


		//美国洛杉矶时区


		TimeZone tz=TimeZone.getTimeZone("America/Los_Angeles");


		//时区转换


		calendar.setTimeZone(tz);


//		System.out.print("时区："+calendar.getTimeZone().getID()+" ");


//		System.out.println("时间："+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE));


		//1、取得本地时间：


		java.util.Calendar cal = java.util.Calendar.getInstance();

		 


		//2、取得时间偏移量：


		int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);

		 


		//3、取得夏令时差：


		int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);

		 


		//4、从本地时间里扣除这些差量，即可以取得UTC时间：


		cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));

		//之后调用cal.get(int x)或cal.getTimeInMillis()方法所取得的时间即是UTC标准时间。


		String UTCTIME = foo.format(new Date(cal.getTimeInMillis()));
//		System.out.println("UTC:"+ UTCTIME);
//
//
//		Calendar calendar1 = Calendar.getInstance();
//
//
//		TimeZone tztz = TimeZone.getTimeZone("GMT");       
//
//
//		calendar1.setTimeZone(tztz);
//
//
//		System.out.println(calendar.getTime());
//
//
//		System.out.println(calendar.getTimeInMillis()); 
		
		return UTCTIME;
	}
	/**
	 * 获取-当前时间1小时前
	 * @return
	 */
	public static String getIsoDateCurrenStart1hour() {

//		System.out.println("gc.getTime():"+gc.getTime());


//		System.out.println("gc.getTimeInMillis():"+new Date(gc.getTimeInMillis()));




		//当前系统默认时区的时间：


		Calendar calendar=new GregorianCalendar();


//		System.out.print("时区："+calendar.getTimeZone().getID()+" ");


//		System.out.println("时间："+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE));


		//美国洛杉矶时区


		TimeZone tz=TimeZone.getTimeZone("America/Los_Angeles");


		//时区转换


		calendar.setTimeZone(tz);


//		System.out.print("时区："+calendar.getTimeZone().getID()+" ");


//		System.out.println("时间："+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE));


		//1、取得本地时间：


		java.util.Calendar cal = java.util.Calendar.getInstance();

		cal.add(Calendar.HOUR_OF_DAY, -1);

		//2、取得时间偏移量：


		int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);

		 


		//3、取得夏令时差：


		int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);

		 


		//4、从本地时间里扣除这些差量，即可以取得UTC时间：


		cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));

		//之后调用cal.get(int x)或cal.getTimeInMillis()方法所取得的时间即是UTC标准时间。


		String UTCTIME = foo.format(new Date(cal.getTimeInMillis()));
//		System.out.println("UTC:"+ UTCTIME);
//
//
//		Calendar calendar1 = Calendar.getInstance();
//
//
//		TimeZone tztz = TimeZone.getTimeZone("GMT");       
//
//
//		calendar1.setTimeZone(tztz);
//
//
//		System.out.println(calendar.getTime());
//
//
//		System.out.println(calendar.getTimeInMillis()); 
		
		return UTCTIME;
	}
	

	// 二行制转字符串
	private static String byte2hex(byte[] b) {
		StringBuilder hs = new StringBuilder();
		String stmp;
		for (int n = 0; b != null && n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1)
				hs.append('0');
			hs.append(stmp);
		}
		return hs.toString();
	}

	private static byte[] hamcsha1(byte[] key, byte[] data) {
		try {
			SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);
//			return byte2hex(mac.doFinal(data));
			return mac.doFinal(data);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String getSignature(String client_secret, String StringToSign) {
		try {
//			Signature = Base64(HMAC-SHA1({client_secret}, UTF-8-Encoding-Of({StringToSign})));  
			
			byte[] hmacsha1Str = hamcsha1(client_secret.getBytes("UTF-8"), StringToSign.getBytes("UTF-8"));
			return new BASE64Encoder().encode(hmacsha1Str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		String client_secret = "gfdsnoifndkfdskjffgdf";
		String client_id = "gfsjkfhksjdfkjds";
		String X_Sgs_Date = MGUtil.getCurrenDateUTC();
		String StringToSign = client_secret + X_Sgs_Date;
		String Signature = SGSUtil.getSignature(client_secret, StringToSign);  
		String Authorization = "SGS" + " " + client_id + ":" + Signature;
		
		System.out.println(Signature);
		System.out.println(Authorization);
		System.out.println(getIsoDateCurren());
		System.out.println(getDateCurren());
		System.out.println(getDateCurrenStart1hour());
		
	}
}
