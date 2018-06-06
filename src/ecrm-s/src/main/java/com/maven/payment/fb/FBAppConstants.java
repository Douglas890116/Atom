package com.maven.payment.fb;

public class FBAppConstants {
	/*========== 请求参数列表 ==========*/
	public static final String req_app_id = "app_id";
	public static final String req_method = "method";
	public static final String req_format = "format";
	public static final String req_sign_method = "sign_method";
	public static final String req_sign = "sign";
	public static final String req_nonce = "nonce";
	public static final String req_version = "version";
	public static final String req_biz_content = "biz_content";
	// 业务参数
	public static final String req_merchant_order_sn = "merchant_order_sn";
	public static final String req_type = "type";
	public static final String req_total_fee = "total_fee";
	public static final String req_store_id = "store_id";
	public static final String req_cashier_id = "cashier_id";
	/*========== 响应参数列表 ==========*/
	public static final String rep_result_code = "result_code";
	public static final String rep_result_message = "result_message";
	public static final String rep_data = "data";
	public static final String rep_qr_code = "qr_code";
	public static final String rep_order_sn = "order_sn";
	/*========== 回调参数列表 ==========*/
	public static final String callback_result_code = "result_code";
	public static final String callback_result_message = "result_message";
	public static final String callback_sign = "sign";
	public static final String callback_data = "data";
	public static final String callback_merchant_order_sn = "merchant_order_sn";
	public static final String callback_total_fee = "total_fee";
}