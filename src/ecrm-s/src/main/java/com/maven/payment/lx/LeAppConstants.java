package com.maven.payment.lx;

public class LeAppConstants {
	/*==============================请求参数==============================*/
	/** 必填，版本号，固定值：1.0.0 **/
	public final static String request_version = "version";
	/** 必填，编码方式，固定值：UTF-8 **/
	public static final String request_encoding ="encoding";
	/** 必填， 请求参数的签名 **/
	public static final String request_signature ="signature";
	/** 请求方保留信息 **/
	public static final String request_reqReserved ="reqReserved";
	/** 必填， 商户号 **/
	public static final String request_mchId ="mchId";
	/** 子商户号 **/
	public static final String request_subMchId ="subMchId";
	/** 代理商商户号 **/
	public static final String request_agentMchId ="agentMchId";
	/** 代理商应用ID **/
	public static final String request_agentMchAppId ="agentMchAppId";
	/** 必填， 应用ID **/
	public static final String request_cmpAppId ="cmpAppId";
	/** 必填，支付渠道编码 **/
	public static final String request_payTypeCode ="payTypeCode";
	/** 必填，订单号 **/
	public static final String request_outTradeNo ="outTradeNo";
	/** 必填，交易发送时间yyyyMMddHHmmss **/
	public static final String request_tradeTime ="tradeTime";
	/** 必填，交易金额单位为分，示例123456值为1234.56元 **/
	public static final String request_amount  ="amount ";
	/** 必填，摘要 **/
	public static final String request_summary ="summary";
	/** 摘要详情 **/
	public static final String request_summaryDetail ="summaryDetail";
	/** 终端设备**/
	public static final String request_deviceId ="deviceId";
	/** 必填，终端设备IP **/
	public static final String request_deviceIp  ="deviceIp ";
	/** 买家ID **/
	public static final String request_buyerId ="buyerId";
	/** 必填返回商户系统网址 **/
	public static final String request_returnUrl ="returnUrl";
	/*==============================响应参数==============================*/
	/** 必填，版本号，固定值：1.0.0 **/
	public static final String response_version = "version";
	/** 必填，编码方式，固定值：UTF-8 **/
	public static final String response_encoding = "encoding";
	/** 必填，对请求参数的签名 **/
	public static final String response_signature = "signature";
	/** 请求方保留信息 **/
	public static final String response_reqReserved = "reqReserved";
	/** 必填，商户号 **/
	public static final String response_mchId = "mchId";
	/** 子商户号 **/
	public static final String response_subMchId = "subMchId";
	/** 代理商商户号 **/
	public static final String response_agentMchId = "agentMchId";
	/** 必填，引用ID **/
	public static final String response_cmpAppId = "cmpAppId";
	/** 必填，订单号 **/
	public static final String response_outTradeNo = "outTradeNo";
	/** 必填，交易金额单位为分，示例123456值为1234.56元 **/
	public static final String response_amount = "amount";
	/** 支付渠道订单号 **/
	public static final String response_payTypeTradeNo = "payTypeTradeNo";
	/** 本系统订单号 **/
	public static final String response_tradeNo = "tradeNo";
	/** 交易完成时间 **/
	public static final String response_endTime = "endTime";
	/** 渠道用户ID **/
	public static final String response_pmtChnlUserId = "pmtChnlUserId";
	/** PC网页支付收银台网址，payTypeCode等于web.pay或者等于PT0032返回有值 **/
	public static final String response_webOrderInfo = "webOrderInfo";
	/** H5支付收银网址，payTypeCode等于wap.pay返回有值 **/
	public static final String response_h5OrderInfo = "h5OrderInfo";
	/** 必填，返回码，000000 **/
	public static final String response_respCode = "respCode";
	/** 必填，返回码信息，成功 **/
	public static final String response_respMsg = "respMsg";
	/*==============================回调参数==============================*/
	/** 必填，版本号 **/
	public static final String callback_version = "version";
	/** 必填，编码方式 **/
	public static final String callback_encoding = "encoding";
	/** 必填，签名 **/
	public static final String callback_signature = "signature";
	/** 必填，商户号 **/
	public static final String callback_mchId = "mchId";
	/** 应用ID **/
	public static final String callback_cmpAppId = "cmpAppId";
	/** 必填，交易金额 **/
	public static final String callback_amount = "amount";
	/** 必填，订单号 **/
	public static final String callback_outTradeNo = "outTradeNo";
	/** 支付渠道订单号 **/
	public static final String callback_payTypeOrderNo = "payTypeOrderNo";
	/** 本系统订单号 **/
	public static final String callback_orderNo = "orderNo";
}