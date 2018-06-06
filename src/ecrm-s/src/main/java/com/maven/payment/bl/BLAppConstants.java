package com.maven.payment.bl;

public class BLAppConstants {
	public enum BL_PayType {
		网银支付("17"),
		微信支付("12"),
		支付宝支付("14"),
		QQ钱包支付("15");
		public String value;
		private BL_PayType(String value) {
			this.value = value;
		}
	}

	/*========== 请 求 参 数 ==========*/
	public static String req_tranName = "tranName";
	public static String req_version = "version";
	public static String req_merCode = "merCode";
	public static String req_orderNo = "orderNo";
	public static String req_orderTime = "orderTime";
	public static String req_payType = "payType";
	public static String req_amount = "amount";
	public static String req_currency = "currency";
	public static String req_productName = "productName";
	public static String req_orderDesc = "orderDesc";
	public static String req_returnURL = "returnURL";
	public static String req_notifyURL = "notifyURL";
	public static String req_sign = "sign";
	
	/*========== 响 应 参 数 ==========*/
	public static String rep_tranName = "tranName";
	public static String rep_version = "version";
	public static String rep_merCode = "merCode";
	public static String rep_flowNo = "flowNo";
	public static String rep_orderNo = "orderNo";
	public static String rep_orderDate = "orderDate";
	public static String rep_ordAmt = "ordAmt";
	public static String rep_payType = "payType";
	public static String rep_currency = "currency";
	public static String rep_paymentState = "paymentState";
	public static String rep_orderDealTime = "orderDealTime";
	public static String rep_workdate = "workdate";
	public static String rep_clearDate = "clearDate";
	public static String rep_errorCode = "errorCode";
	public static String rep_errorMessage = "errorMessage";
	public static String rep_sign = "sign";
}