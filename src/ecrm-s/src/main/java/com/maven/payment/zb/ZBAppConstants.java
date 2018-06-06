package com.maven.payment.zb;

import com.maven.util.RandomString;

/**
 * 众宝支付相关参数
 * @author klay
 *
 */
public class ZBAppConstants {
	
	public static String getOrderNo() {
		return RandomString.UUID().substring(2);
	}
	/*========== 请求参数列表 ==========*/
	public static final String req_customer = "customer";
	public static final String req_banktype = "banktype";
	public static final String req_amount = "amount";
	public static final String req_orderid = "orderid";
	public static final String req_asynbackurl = "asynbackurl";
	public static final String req_request_time = "request_time";
	public static final String req_synbackurl = "synbackurl";
	public static final String req_onlyqr = "onlyqr";
	public static final String req_attach = "attach";
	public static final String req_sign = "sign";
	/*========== 响应参数列表 ==========*/
	/*========== 回调参数列表 ==========*/
	public static final String callback_orderid = "orderid";
	public static final String callback_result = "result";
	public static final String callback_amount = "amount";
	public static final String callback_zborderid = "zborderid";
	public static final String callback_completetime = "completetime";
	public static final String callback_notifytime = "notifytime";
	public static final String callback_sign = "sign";
	public static final String callback_attach = "attach";
	public static final String callback_msg = "msg";
}