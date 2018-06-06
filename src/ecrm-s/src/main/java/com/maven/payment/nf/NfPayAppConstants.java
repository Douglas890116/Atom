package com.maven.payment.nf;

import com.maven.util.RandomString;

/**
 * 
 */
public class NfPayAppConstants {

	/**
	 * 获取订单号
	 * 
	 * 牛支付只能支持30位数的额单号[8位会员编码 + 13位数时间戳 + 9位随机数]
	 * 
	 * @param employeecode
	 * @return
	 */
	public static String getOrderNo(String employeecode) {
		String orderno = employeecode + System.currentTimeMillis() + ""+ RandomString.createRandomString(9);
		return orderno.toUpperCase();//全部为大写
	}
	
}
