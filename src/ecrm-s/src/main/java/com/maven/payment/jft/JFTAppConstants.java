package com.maven.payment.jft;

/**
 * 
 */
public class JFTAppConstants {

	/**
	 * 支付请求参数名
	 */
	public static final String p1_MerId = "MerId";
	public static final String p1_OrdId = "OrdId";
	public static final String p1_OrdAmt = "OrdAmt";
	public static final String p1_PayType = "PayType";
	public static final String p1_CurCode = "CurCode";
	public static final String p1_BankCode = "BankCode";
	public static final String p1_ProductInfo = "ProductInfo";
	public static final String p1_Remark = "Remark";
	public static final String p1_ReturnURL = "ReturnURL";
	public static final String p1_NotifyURL = "NotifyURL";
	public static final String p1_SignType = "SignType";
	public static final String p1_SignInfo = "SignInfo";
	
	
	
	/**
	 * 结果通知参数名
	 */
	public static final String p2_MerId = "MerId";
	public static final String p2_OrdId = "OrdId";
	public static final String p2_OrdAmt = "OrdAmt";
	public static final String p2_OrdNo = "OrdNo";
	public static final String p2_ResultCode = "ResultCode";//Success001表示成功
	public static final String p2_Remark = "Remark";
	public static final String p2_SignType = "SignType";//默认值：MD5，目前仅支持MD5
	public static final String p2_SignInfo = "SignInfo";
	
}
