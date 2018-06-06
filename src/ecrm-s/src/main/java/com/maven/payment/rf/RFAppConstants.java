package com.maven.payment.rf;

public class RFAppConstants {
	/**
	 * 锐付支付类型
	 * @author klay
	 *
	 */
	public enum RF_AppType {
		// TODO 锐付支付类型 待完善 
		网银支付(""), 
		微信支付("WECHAT"), 
		支付宝支付("ALIPAY"),
		QQ钱包支付("QPAY");
		public String value;

		private RF_AppType(String value) {
			this.value = value;
		}
	}
/*================================================== 请求参数列表 ==================================================*/
	/** 商户Id, 由平台提供 */
	public static final String r_partyId = "partyId";
	/** 账户Id, 有平台提供*/
	public static final String r_accountId = "accountId";
	/** 支付类型, 
	 * NULL		online payment	为空跳到收银台
	 * Wechat	WECHAT			微信
	 * ALIPAY	ALIPAY			支付宝
	 * WAP		MWEB			wap支付
	 * Mobile Quick Pay	Mobile	手机支付
	 */
	public static final String r_appType = "appType";
	/** 订单号, 30位, 前缀goods */
	public static final String r_orderNo = "orderNo";
	/** 订单金额, 2位小数, 币种:RMB */
	public static final String r_orderAmount = "orderAmount";
	/** 前缀由平台分发 */
	public static final String r_goods = "goods";
	/** 支付完成后返回的地址 */
	public static final String r_returnUrl = "returnUrl";
	/** 回调地址 */
	public static final String r_checkUrl = "checkUrl";
	/**
	 * 支付卡类型 
	 * 01: RMB debit card	借记卡
	 * 02: Credit card		信用卡
	 */
	public static final String r_cardType = "cardType";
	/** 银行Code, 当参数appType为空时可为空 */
	public static final String r_bank = "bank";
	/** 加密类型:MD5 */
	public static final String r_encodeType = "encodeType";
	/** 子商户号，可为空 */
	public static final String r_refCode = "refCode";
	/** 签名参数 */
	public static final String r_signMD5 = "signMD5";
	
	/*================================================== 回调参数列表 ==================================================*/
	/** 商户Id */
	public static final String callback_partyId = "partyId";
	/** 支付类型 */
	public static final String callback_appType = "appType";
	/** 订单号 */
	public static final String callback_orderNo = "orderNo";
	/** 订单金额 */
	public static final String callback_orderAmount = "orderAmount";
	/** 前缀 */
	public static final String callback_goods = "goods";
	/** 加密类型，默认MD5 */
	public static final String callback_encodeType = "encodeType";
	/** 回调签名 */
	public static final String callback_signMD5 = "signMD5";
	/** 商户订单号 */
	public static final String callback_tradeNo = "tradeNo";
	/** 银行账单号 */
	public static final String callback_bankBillNo = "bankBillNo";
	/** Y：成功，N：失败 */
	public static final String callback_succ = "succ";
}