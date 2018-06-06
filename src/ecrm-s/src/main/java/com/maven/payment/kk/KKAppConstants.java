package com.maven.payment.kk;

import com.maven.util.RandomString;

/**
 * 可可支付
 * @author klay
 *
 */
public class KKAppConstants {
	
	public static String getOrderNo() {
		return RandomString.UUID().substring(2);
	}
	
	public enum KK_ProductType {
		微信T1扫码支付("10000101"),
		微信D0扫码支付("10000102"),
		微信T0扫码支付("10000103"),
		微信T1公众号支付("10000301"),
		微信D0公众号支付("10000302"),
		微信T0公众号支付("10000303"),
		支付宝T1WAP支付("20000201"),
		支付宝D0WAP支付("20000202"),
		支付宝T0WAP支付("20000203"),
		支付宝T1扫码支付("20000301"),
		支付宝D0扫码支付("20000302"),
		支付宝T0扫码支付("20000303"),
		QQ钱包扫码T1支付("70000201"),
		QQ钱包扫码D0支付("70000202"),
		QQ钱包扫码T0支付("70000203"),
		京东钱包扫码T1支付("80000201"),
		京东钱包扫码D0支付("80000202"),
		京东钱包扫码T0支付("80000203");
		public String value;
		private KK_ProductType(String value){
			this.value = value;
		}
	}
	/*========== 请求参数列表 ==========*/
	public static final String req_payKey = "payKey";
	public static final String req_orderPrice = "orderPrice";
	public static final String req_outTradeNo = "outTradeNo";
	public static final String req_productType = "productType";
	public static final String req_orderTime = "orderTime";
	public static final String req_productName = "productName";
	public static final String req_orderIp = "orderIp";
	public static final String req_returnUrl = "returnUrl";
	public static final String req_notifyUrl = "notifyUrl";
	public static final String req_appId = "appId";
	public static final String req_openId = "openId";
	public static final String req_sign = "sign";
	
	/*========== 响应参数列表 ==========*/
	public static final String rep_resultCode = "resultCode";
	public static final String rep_payMessage = "payMessage";
	public static final String rep_errMsg     = "errMsg";
	public static final String rep_sign       = "sign";
	
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
	public static final String callback_remitRequestNo = "remitRequestNo";
	public static final String callback_remitStatus = "remitStatus";
	public static final String callback_sign = "sign";
}