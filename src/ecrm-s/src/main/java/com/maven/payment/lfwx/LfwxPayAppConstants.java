package com.maven.payment.lfwx;

/**
 * 
 */
public class LfwxPayAppConstants {

	public static final String service = "service";
	public static final String partner_no = "partner_no";//商户号
	public static final String sign = "sign";//签名 UTF-8编码（响应的sign需要对签名密文进行base64解密后获得SHA1WithRSA签名密文，验证签名时需要对获取的参数进行按字母排序）
	
	/**
	 * 支付请求参数名
	 */
	public static final String p1_input_charset = "input_charset";
	public static final String p1_sign_type = "sign_type";
	public static final String p1_request_time = "request_time";//YYMMDDHHmmss
	public static final String p1_return_url = "return_url";
	public static final String p1_out_trade_no = "out_trade_no";
	public static final String p1_amount_str = "amount_str";//金额（0.01 ～9999999999.99）
	
	public static final String p1_content = "content";
	
	public static final String p1_wx_pay_type = "wx_pay_type";
	public static final String p1_subject = "subject";
	public static final String p1_sub_body = "sub_body";
	
	
	/**
	 * 支付结果通知参数名
	 */
	public static final String p2_status = "status";//
	public static final String p2_is_succ = "is_succ";//T/F
	public static final String p2_content = "content";//is_succ 为true 时存放参数加密后的密⽂，待解密获得参数（对参数密文进行base64解码获得解码后对RAS加密密文）
	public static final String p2_input_charset = "input_charset";//请求字符集即系统约定字符集
	
}
