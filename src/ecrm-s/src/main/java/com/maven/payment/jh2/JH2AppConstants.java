package com.maven.payment.jh2;
/**
 * 聚合支付 - 金塔
 * @author zack
 *
 */
public class JH2AppConstants {
	/*========== 请求参数列表 ==========*/
	public static final String req_payKey = "payKey";
	public static final String req_orderPrice = "orderPrice";
	public static final String req_outTradeNo = "outTradeNo";
	public static final String req_productType = "productType";
	public static final String req_orderTime = "orderTime";
	public static final String req_productName = "productName";
	public static final String req_orderIp = "orderIp";
	public static final String req_bankCode = "bankCode";// (B2C独有)
	public static final String req_bankAccountType = "bankAccountType";// (B2C独有)
	public static final String req_returnUrl = "returnUrl";
	public static final String req_notifyUrl = "notifyUrl";
	public static final String req_remark = "remark";
	public static final String req_mobile = "mobile";// (B2C独有) 移动端 - 当为手机端时此参数不为空 值为 1
	public static final String req_sign = "sign";
	/*========== 响应参数列表 ==========*/
	public static final String rep_resultCode = "resultCode";
	public static final String rep_payMessage = "payMessage";
	public static final String rep_errMsg = "errMsg";
	public static final String rep_sign = "sign";
	/*========== 回调参数列表 ==========*/
	public static final String callback_payKey = "payKey";
	public static final String callback_productName = "productName";
	public static final String callback_outTradeNo = "outTradeNo";
	public static final String callback_orderPrice = "orderPrice";
	public static final String callback_productType = "productType";
	public static final String callback_tradeStatus = "tradeStatus";
	public static final String callback_successTime = "successTime";
	public static final String callback_orderTime = "orderTime";
	public static final String callback_trxNo = "trxNo";
	public static final String callback_remark = "remark";
	public static final String callback_sign = "sign";
}