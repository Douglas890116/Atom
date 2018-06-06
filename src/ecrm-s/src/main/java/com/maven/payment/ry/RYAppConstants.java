package com.maven.payment.ry;

public class RYAppConstants {
	/**
	 * 如意支付类型
	 * @author klay
	 *
	 */
	public enum RY_PayType {
		网关支付("10"), 
		快捷支付("20"), 
		微信扫码支付("30"), 
		支付宝扫码支付("40"), 
		QQ钱包扫码支付("50"),
		JD钱包扫码支付("60");
		public String value;

		private RY_PayType(String value) {
			this.value = value;
		}
	}
	//==============================请求参数列表==============================//
	/** 商户号 */
	public static final String request_merId = "merId";
	/** 订单号 */
	public static final String request_merOrdId = "merOrdId";
	/** 订单金额 */
	public static final String request_merOrdAmt = "merOrdAmt";
	/** 支付类型
	 *  10-网关支付
	 *  20-快捷支付
	 *  30-微信扫码支付 微信扫码支付
	 *  40-支付 宝扫码支付 宝扫码支付
	 *  50-QQ钱包 扫码 支付
	 */
	public static final String request_payType = "payType";
	/** 银行代码 */
	public static final String request_bankCode = "bankCode";
	/** 备注信息 */
	public static final String request_remark = "remark";
	/** 前端返回地址 */
	public static final String request_returnUrl = "returnUrl";
	/** 回调地址 */
	public static final String request_notifyUrl = "notifyUrl";
	/** 签名 方式 : MD5 或 RSA, 默认 MD5 */
	public static final String request_signType = "signType";
	/** 签名数据 */
	public static final String request_signMsg = "signMsg";
	//==============================返回参数列表==============================//
	/** 商户订单 */
	public static final String reponse_merOrdId = "merOrdId";
	/** 订单金额 */
	public static final String reponse_merOrdAmt = "merOrdAmt";
	/** 支付类型
	 *  wxpay-微信支付
	 *  alipay-支付宝支付
	 */
	public static final String reponse_payType = "payType";
	/** 请求结果，SUCCESS表示成功 */
	public static final String reponse_retCode = "retCode";
	/** 请求结果类型
	 *  img-图片 格式
	 *  qrcode-二维码
	 */
	public static final String reponse_retType = "retType";
	/** 支付的图片链接或二维码的图片链接 */
	public static final String reponse_retUrl = "retUrl";
	//==============================回调参数列表==============================//
	/** 商户号 */
	public static final String callback_merId = "merId";
	/** 商户订单号 */
	public static final String callback_merOrdId = "merOrdId";
	/** 订单金额 */
	public static final String callback_merOrdAmt = "merOrdAmt";
	/** 接口订单号 */
	public static final String callback_sysOrdId = "sysOrdId";
	/** 交易 状态 ，success002表示成功 表 */
	public static final String callback_tradeStatus = "tradeStatus";
	/** 备注信息 */
	public static final String callback_remark = "remark";
	/** 签名 方式 ，默认 MD5 */
	public static final String callback_signType = "signType";
	/** 签名数据 */
	public static final String callback_signMsg = "signMsg";
}