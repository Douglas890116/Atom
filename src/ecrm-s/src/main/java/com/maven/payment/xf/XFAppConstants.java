package com.maven.payment.xf;

public class XFAppConstants {
	public enum XF_PayType {
		网银支付("online"),
		快捷支付("quick"),
		二维码存款("qrcode");
		public String value;
		private XF_PayType(String value) {
			this.value = value;
		}
	}
	/*========== 请求参数列表 ==========*/
	public static String req_cid = "cid";
	public static String req_uid = "uid";
	public static String req_time = "time";
	public static String req_amount = "amount";
	public static String req_order_id = "order_id";
	public static String req_ip = "ip";
	public static String req_sign = "sign";
	public static String req_type = "type";
	public static String req_tflag = "tflag";
	public static String req_to_bank_flag = "to_bank_flag";
	public static String req_to_cardnumber = "to_cardnumber";
	public static String req_to_username = "to_username";
	public static String req_location = "location";
	public static String req_city = "city";
	/*========== 回调参数列表 ==========*/
	public static String callback_order_id = "order_id";
	public static String callback_direction = "direction";
	public static String callback_amount = "amount";
	public static String callback_customer_name = "customer_name";
	public static String callback_verified_time = "verified_time";
	public static String callback_created_time = "created_time";
	public static String callback_cmd = "cmd";
	public static String callback_type = "type";
	public static String callback_customer_bankflag = "customer_bankflag";
	public static String callback_out_cardnumber = "out_cardnumber";
	public static String callback_trans_fee = "trans_fee";
	public static String callback_sign = "sign";
}