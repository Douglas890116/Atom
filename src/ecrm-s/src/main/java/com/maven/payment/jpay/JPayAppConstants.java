package com.maven.payment.jpay;

/**
 * 
 */
public class JPayAppConstants {

	public static final String c_code = "c_code";//商户号
	public static final String c_key = "key";//商户秘钥
	
	/**
	 * 支付请求参数名
	 */
	public static final String p1_pay_type = "pay_type";
	public static final String p1_pay_amt = "pay_amt";
	public static final String p1_goods_name = "goods_name";
	public static final String p1_remark = "remark";
	public static final String p1_agent_bill_id = "agent_bill_id";
	public static final String p1_notify_url = "notify_url";
	public static final String p1_callback_url = "callback_url";
	public static final String p1_sign = "sign";
	//返回结果
	public static final String rep_res = "res";
	public static final String rep_paysvr = "paysvr";
	public static final String rep_address = "address";
	
	
	
	/**
	 * 支付结果通知参数名
	 */
	public static final String p2_res = "res";
	public static final String p2_pay_type = "pay_type";
	public static final String p2_pay_amt = "pay_amt";
	public static final String p2_jpay_bill_id = "jpay_bill_id";
	public static final String p2_agent_bill_id = "agent_bill_id";
	public static final String p2_sign = "sign";
	
}
