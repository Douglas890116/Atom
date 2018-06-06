package com.maven.payment.lfalipay;

public class LfAliPayAppConstants {
	
/*================================================== 报头参数 ==================================================*/
	public static final String p1_partner = "partner";// 32	否	商户合作号，由平台注册提供
	public static final String p1_service = "service";// 32	否	支付宝支付:ali_pay
	public static final String p1_input_charset = "input_charset";// 10	否	编码格式:UTF-8
	public static final String p1_sign_type = "sign_type";// 3	否	签名方式:MD5
	public static final String p1_sign = "sign";// 256	否	签名字符串
	public static final String p1_request_time = "request_time";// 20	可	YYMMDDHHmmss
	public static final String p1_content = "content";// 1024	否	业务请求参数，需要p2方式进行拼接，再进行URLEncode
	
/*================================================== 业务参数 ==================================================*/
	public static final String p2_service = "service";// 32	否	支付宝支付:ali_pay
	public static final String p2_partner = "partner";// 32	否	商户合作号，由平台注册提供
	public static final String p2_out_trade_no = "out_trade_no";// 40	否	原始商户订单
	public static final String p2_amount_str = "amount_str";// 40	可	金额（0.01～9999999999.99）
	public static final String p2_ali_pay_type = "ali_pay_type";// 10	否	支付宝扫码:ali_sm
	public static final String p2_subject = "subject";// 20	否	商品名称(不要带特殊字符)
	public static final String p2_sub_body = "sub_body";// 20	否	商品描述(不要带特殊字符)
	public static final String p2_remark = "remark";// 20	可	备注
	public static final String p2_return_url = "return_url";// 1024	否	后台通知地址
	
/*================================================== 响应报头 ==================================================*/
	public static final String r1_is_succ = "is_succ";// 10	否	成功标志:T/F
	public static final String r1_fault_code = "fault_code";// 3	可	错误码
	public static final String r1_fault_reason = "fault_reason";// 256	可	错误原因
	public static final String r1_remark = "remark";// 256	可	备注
	public static final String r1_sign = "sign";// 256	否	待验签字符串
	public static final String r1_response = "response";// 1024	否	响应业务参数内容
	
/*================================================== 响应业务 ==================================================*/
	public static final String r2_ali_pay_sm_url = "ali_pay_sm_url";// 1024	否	支付宝支付唤醒URL
	public static final String r2_base64QRCode = "base64QRCode";// 2048	可	支付宝支付二维码图片(优先使用该返回，如果不存在则使用ali_pay_sm_url)）
}