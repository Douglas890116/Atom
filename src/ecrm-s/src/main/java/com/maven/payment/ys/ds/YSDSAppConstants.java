package com.maven.payment.ys.ds;

public class YSDSAppConstants {
	/*========== 请求参数列表 ==========*/
	// 基础参数
	public static final String req_method = "method";
	public static final String req_partner_id = "partner_id";
	public static final String req_timestamp = "timestamp";
	public static final String req_charset = "charset";
	public static final String req_sign_type = "sign_type";
	public static final String req_sign = "sign";
	public static final String req_notify_url = "notify_url";
	public static final String req_version = "version";
	public static final String req_biz_content = "biz_content";
	// 订单参数
	public static final String req_json_out_trade_no = "out_trade_no";
	public static final String req_json_business_code = "business_code";
	public static final String req_json_currency = "currency";
	public static final String req_json_total_amount = "total_amount";
	public static final String req_json_subject = "subject";
	public static final String req_json_bank_name = "bank_name";
	public static final String req_json_bank_province = "bank_province";
	public static final String req_json_bank_city = "bank_city";
	public static final String req_json_bank_account_no = "bank_account_no";
	public static final String req_json_bank_account_name = "bank_account_name";
	public static final String req_json_bank_account_type = "bank_account_type";
	public static final String req_json_bank_card_type = "bank_card_type";
	public static final String req_json_bank_telephone_no = "bank_telephone_no";
	/*========== 响应参数列表 ==========*/
	public static final String rep_out_trade_no = "out_trade_no";
	public static final String rep_trade_status = "trade_status";
	public static final String rep_trade_status_description = "trade_status_description";
	public static final String rep_total_amount = "total_amount";
	public static final String rep_account_date = "account_date";
	public static final String rep_trade_no = "trade_no";
	public static final String rep_fee = "fee";
	/*========== 回调参数列表 ==========*/
	public static final String callback_sign_type = "sign_type";
	public static final String callback_sign = "sign";
	public static final String callback_notify_type = "notify_type";
	public static final String callback_notify_time = "notify_time";
	public static final String callback_out_trade_no = "out_trade_no";
	public static final String callback_trade_status = "trade_status";
	public static final String callback_trade_status_description = "trade_status_description";
	public static final String callback_total_amount = "total_amount";
	public static final String callback_account_date = "account_date";
	public static final String callback_trade_no = "trade_no";
	public static final String callback_fee = "fee";
}