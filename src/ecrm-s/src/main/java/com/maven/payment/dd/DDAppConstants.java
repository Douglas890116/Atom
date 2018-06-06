package com.maven.payment.dd;

public class DDAppConstants {
	/*========== 请求参数列表 ==========*/
	public static final String req_version = "version";
	public static final String req_code = "code";
	public static final String req_context = "context";
	public static final String req_pay_type = "pay_type";
	public static final String req_commodity_name = "commodity_name";
	public static final String req_amount = "amount";
	public static final String req_back_end_url = "back_end_url";
	public static final String req_return_url = "return_url";
	public static final String req_merchant_no = "merchant_no";
	/*========== 响应参数列表 ==========*/
	public static final String rep_returnMsg = "returnMsg";
	public static final String rep_serverCode = "serverCode";
	/*========== 回调参数列表 ==========*/
	public static final String callback_notify_time = "notify_time";
	public static final String callback_gmt_create = "gmt_create";
	public static final String callback_outer_trade_no = "outer_trade_no";
	public static final String callback_trade_status = "trade_status";
	public static final String callback_amount = "amount";
	public static final String callback_sign = "sign";
	/*========== 代付参数列表 ==========*/
	public static final String df_is_compay = "is_compay";
	public static final String df_product_id = "product_id";
	public static final String df_customer_phone = "customer_phone";
	public static final String df_customer_name = "customer_name";
	public static final String df_customer_cert_type = "customer_cert_type";
	public static final String df_customer_cert_id = "customer_cert_id";
	public static final String df_bank_no = "bank_no";
	public static final String df_bank_name = "bank_name";
	public static final String df_bank_card_no = "bank_card_no";
	
	public static final String md5Key = "ecode";
}