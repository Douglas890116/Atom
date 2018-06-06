package com.maven.payment.sdp2p;

import java.util.Date;

import com.maven.util.RandomString;

/**
 * 
 */
public class SDWXPayAppConstants {

	public enum Enum_PaymentType{
		个人计算机("6006","个人计算机"),
		手机("6009","手机"),
		手机无插件("6010","手机无插件"),
		;
		public String value;
		public String desc;
		
		private Enum_PaymentType(String value,String desc){
			this.value=value;
			this.desc=desc;
		}
	}
	
	/**
	 * 
	 * 获取3位数单号
	 * 
	 * @return
	 */
	private static int POKE = 100;
	public static String getPatchno(){
		POKE++;
		if(POKE>=990) POKE = 100;
		return ( POKE) + "";
	}
	
	/**
	 * 获取订单号
	 * 
	 * SD支付只能支持24位数的单号[8位会员编码 + 13位数时间戳 + 3位随机数]
	 * 
	 * @param employeecode
	 * @return
	 */
	public static String getOrderNo(String employeecode) {
		String orderno = employeecode + System.currentTimeMillis() + ""+ getPatchno();
		return orderno.toUpperCase();//全部为大写
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 10000; i++) {
			String no = getOrderNo("E000IH26");
			System.out.println(no + "=" + no.length());
		}
	}
	
}
