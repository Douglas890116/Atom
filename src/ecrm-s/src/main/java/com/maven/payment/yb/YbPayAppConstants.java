package com.maven.payment.yb;

/**
 * 
 */
public class YbPayAppConstants {

	public static final String partner = "partner";//商户号
	public static final String key = "key";//商户秘钥
	public static final String sign = "sign";//签名 GB2312编码
	
	/**
	 * 支付请求参数名
	 */
	public static final String p1_banktype = "banktype";
	public static final String p1_paymoney = "paymoney";
	public static final String p1_ordernumber = "ordernumber";
	public static final String p1_callbackurl = "callbackurl";
	public static final String p1_hrefbackurl = "hrefbackurl";
	public static final String p1_attach = "attach";
	
	
	
	/**
	 * 支付结果通知参数名
	 */
	public static final String p2_orderstatus = "orderstatus";//1:支付成功，非1为支付失败
	public static final String p2_ordernumber = "ordernumber";
	public static final String p2_paymoney = "paymoney";
	public static final String p2_sysnumber = "sysnumber";//此次交易中银宝商务接口系统内的订单ID
	public static final String p2_attach = "attach";
	
}
