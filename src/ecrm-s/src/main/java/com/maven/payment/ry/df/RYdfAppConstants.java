package com.maven.payment.ry.df;

public class RYdfAppConstants {
	//==============================代付参数列表	==============================//
	//====================基本参数====================//
	/** 固定值，2.1 */
	public static final String payout_Version = "Version";
	/** 签名类型，目前仅支持 RSA */
	public static final String payout_SignType = "SignType";
	/** 数据签名字符串 */
	public static final String payout_SignInfo = "SignInfo";
	/** 时间戳，格式为 yyyyMMddHHmmss，时区为 GMT+8，例如： ，例如： 20160101120000。 */
	public static final String payout_TranDateTime = "TranDateTime";
	/** 统一为：UTF-8 */
	public static final String payout_Charset = "Charset";
	/** 固定值：CNY */
	public static final String payout_CurCode = "CurCode";
	/** 随机字符串，不超过 32位 */
	public static final String payout_Remark = "Remark";
	//====================业务参数====================//
	/** 商务Id */
	public static final String payout_MerId = "MerId";
	/** 代付收款人明细，AES加密后再Base64编码方式进行编码。相关参数下面 */
	public static final String payout_PayeeDetails = "PayeeDetails";
	//====================PayeeDetails参数====================//
	/** 代付订单号 */
	public static final String payoutDetail_OrdId = "OrdId";
	/** 收款人姓名 */
	public static final String payoutDetail_RecvBankAcctName = "RecvBankAcctName";
	/** 收款人账号 */
	public static final String payoutDetail_RecvBankAccNumber = "RecvBankAccNumber";
	/** 开户行编号 */
	public static final String payoutDetail_BankCode = "BankCode";
	/** 开户行省 */
	public static final String payoutDetail_RecvBankProvince = "RecvBankProvince";
	/** 开户行市 */
	public static final String payoutDetail_RecvBankCity = "RecvBankCity";
	/** 支行名称 */
	public static final String payoutDetail_RecvBankBranch = "RecvBankBranch";
	/** 代付金额 */
	public static final String payoutDetail_OrdAmt = "OrdAmt";
	/** 对私， 固定值 固定值 ： 1 */
	public static final String payoutDetail_CorpPersonFlag = "CorpPersonFlag";
	/** 异步通知链接地址 */
	public static final String payoutDetail_NotifyURL = "NotifyURL";
	//==============================代付响应参数列表	==============================//
	/** 返回代码。1088为成功，其他均为失败 */
	public static final String payout_response_code = "code";
	/** 返回信息 */
	public static final String payout_response_message = "message";
}