package com.maven.payment.xbei;

import com.maven.util.RandomString;

/**
 * 
 */
public class XBAppConstants {

	public static final String Version = "Version";
	public static final String MerchantCode = "MerchantCode";
	public static final String TokenKey = "TokenKey";
	public static final String OrderId = "OrderId";
	public static final String Amount = "Amount";
	public static final String AsyNotifyUrl = "AsyNotifyUrl";
	public static final String SynNotifyUrl = "SynNotifyUrl";
	public static final String OrderDate = "OrderDate";
	public static final String TradeIp = "TradeIp";
	public static final String PayCode = "PayCode";
	public static final String SignValue = "SignValue";
	
	
	/**
	 * 结果通知参数名
	 */
	public static final String State = "State";
	public static final String SerialNo = "SerialNo";
	public static final String FinishTime = "FinishTime";
	
	
	/**
	 * 获取订单号
	 * 
	 * 新贝支付只能支持30位数的额单号[8位会员编码 + 13位数时间戳 + 9位随机数]
	 * 
	 * @param employeecode
	 * @return
	 */
	public static String getOrderNo(String employeecode) {
		String orderno = employeecode + System.currentTimeMillis() + ""+ RandomString.createRandomString(9);
		return orderno.toUpperCase();//全部为大写
	}
	
	public static void main(String[] args) {
		System.out.println(getOrderNo("E000IH26").length());
	}
}
