package com.maven.payment.el;

public class ELAppConstants {
	/*========== 请求参数列表 ==========*/
	public static final String req_inputCharset = "inputCharset";
	public static final String req_partnerId = "partnerId";
	public static final String req_signType = "signType";
	public static final String req_notifyUrl = "notifyUrl";
	public static final String req_returnUrl = "returnUrl";
	public static final String req_orderNo = "orderNo";
	public static final String req_orderAmount = "orderAmount";
	public static final String req_orderCurrency = "orderCurrency";
	public static final String req_orderDatetime = "orderDatetime";
	public static final String req_signMsg = "signMsg";
	public static final String req_subject = "subject";
	public static final String req_body = "body";
	public static final String req_extraCommonParam = "extraCommonParam";
	// 扫码独有参数
	public static final String req_payMode = "payMode";
	public static final String req_isPhone = "isPhone";
	// 代付独有参数
	public static final String req_cashType = "cashType";
	public static final String req_accountName = "accountName";
	public static final String req_bankName = "bankName";
	public static final String req_bankCardNo = "bankCardNo";
	public static final String req_canps = "canps";
	public static final String req_idCard = "idCard";
	/*========== 响应参数列表 ==========*/
	// 主要用于代付
	public static final String rep_inputCharset = "inputCharset";
	public static final String rep_signType = "signType";
	public static final String rep_signMsg = "signMsg";
	public static final String rep_orderNo = "orderNo";
	public static final String rep_errCode = "errCode";
	public static final String rep_errMsg = "errMsg";
	/*========== 回调参数列表 ==========*/
	public static final String callback_inputCharset = "inputCharset";
	public static final String callback_partnerId = "partnerId";
	public static final String callback_signType = "signType";
	public static final String callback_paymentOrderId = "paymentOrderId";
	public static final String callback_orderNo = "orderNo";
	public static final String callback_orderAmount = "orderAmount";
	public static final String callback_orderDatetime = "orderDatetime";
	public static final String callback_payDatetime = "payDatetime";
	public static final String callback_payResult = "payResult";
	public static final String callback_signMsg = "signMsg";
	public static final String callback_returnDatetime = "returnDatetime";
	public static final String callback_extraCommonParam = "extraCommonParam";
}