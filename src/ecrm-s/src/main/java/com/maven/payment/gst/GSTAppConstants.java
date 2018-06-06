package com.maven.payment.gst;

/**
 * 国盛通支付
 */
public class GSTAppConstants {
	
	/**
	 * 国盛通支付类型
	 * @author klay
	 *
	 */
	public enum GST_PayType {
		网银支付("1"),
		微信支付("2"),
		支付宝支付("3");
		
		public String value;

		private GST_PayType(String value) {
			this.value = value;
		}
	}

	/**
	 * 支付请求参数名
	 */
	public static final String p1_input_charset = "input_charset";
	public static final String p1_inform_url = "inform_url";
	public static final String p1_return_url = "return_url";
	public static final String p1_pay_type = "pay_type";
	public static final String p1_bank_code = "bank_code";
	public static final String p1_merchant_code = "merchant_code";
	public static final String p1_order_no = "order_no";
	public static final String p1_order_amount = "order_amount";
	public static final String p1_order_time = "order_time";
	public static final String p1_req_referer = "req_referer";
	public static final String p1_customer_ip = "customer_ip";
	public static final String p1_return_params = "return_params";
	public static final String p1_sign = "sign";
	
	/**
	 * 结果通知参数名
	 */
	public static final String p2_merchant_code = "merchant_code";
	public static final String p2_order_no = "order_no";
	public static final String p2_order_amount = "order_amount";
	public static final String p2_return_params = "return_params";
	public static final String p2_trade_no = "trade_no";
	public static final String p2_order_time = "order_time";
	public static final String p2_trade_status = "trade_status";//success-交易成功 failed-交易失败  paying-交易中
	public static final String p2_sign = "sign";
	
}
