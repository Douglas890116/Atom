package com.maven.payment.xingfu;

public class XingFAppConstants {
	public enum Enum_XingFService{
		网银支付B2C("TRADE.B2C"),
		网银支付B2B("TRADE.B2B"),
		扫码支付("TRADE.SCANPAY"),
		H5支付("TRADE.H5PAY");
		public String value;
		private Enum_XingFService(String value) {
			this.value = value;
		}
	}
	
	/*========== 请求参数列表 ==========*/
	public static final String req_service = "service";
	public static final String req_version = "version";
	public static final String req_merId = "merId";
	public static final String req_tradeNo = "tradeNo";
	public static final String req_tradeDate = "tradeDate";
	public static final String req_amount = "amount";
	public static final String req_notifyUrl = "notifyUrl";
	public static final String req_extra = "extra";
	public static final String req_summary = "summary";
	public static final String req_expireTime = "expireTime";
	public static final String req_clientIp = "clientIp";
	public static final String req_bankId = "bankId";
	public static final String req_typeId = "typeId";
	public static final String req_sign = "sign";
	/*========== 响应参数列表 ==========*/
	public static final String rep_message = "message";
	public static final String rep_detail = "detail";
	public static final String rep_code = "code";
	public static final String rep_desc = "desc";
	public static final String rep_qrCode = "qrCode";
	public static final String rep_sign = "sign";
	/*========== 回调参数列表 ==========*/
	public static final String callback_service = "service";
	public static final String callback_merId = "merId";
	public static final String callback_tradeNo = "tradeNo";
	public static final String callback_tradeDate = "tradeDate";
	public static final String callback_opeNo = "opeNo";
	public static final String callback_opeDate = "opeDate";
	public static final String callback_amount = "amount";
	public static final String callback_status = "status";
	public static final String callback_extra = "extra";
	public static final String callback_payTime = "payTime";
	public static final String callback_sign = "sign";
	public static final String callback_notifyType = "notifyType";
}