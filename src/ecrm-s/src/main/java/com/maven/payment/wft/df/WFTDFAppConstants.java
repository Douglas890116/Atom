package com.maven.payment.wft.df;

public class WFTDFAppConstants {
	/*============================== 请求参数列表 ==============================*/
	// 基础参数
	public static final String req_Version = "Version";
	public static final String req_SignType = "SignType";
	public static final String req_SignInfo = "SignInfo";
	public static final String req_TranDateTime = "TranDateTime";
	public static final String req_Charset = "Charset";
	public static final String req_CurCode = "CurCode";
	public static final String req_Remark = "Remark";
	public static final String req_MerId = "MerId";
	public static final String req_PayeeDetails = "PayeeDetails";
	// json参数
	public static final String json_OrdId = "OrdId";
	public static final String json_RecvBankAcctName = "RecvBankAcctName";
	public static final String json_RecvBankAccNumber = "RecvBankAccNumber";
	public static final String json_BankCode = "BankCode";
	public static final String json_RecvBankProvince = "RecvBankProvince";
	public static final String json_RecvBankCity = "RecvBankCity";
	public static final String json_RecvBankBranch = "RecvBankBranch";
	public static final String json_OrdAmt = "OrdAmt";
	public static final String json_CorpPersonFlag = "CorpPersonFlag";
	public static final String json_NotifyURL = "NotifyURL";
	/*============================== 响应参数列表 ==============================*/
	public static final String rep_code = "code";
	public static final String rep_message = "message";
	/*============================== 回调参数列表 ==============================*/
	public static final String callback_MerId = "MerId";
	public static final String callback_OrdId = "OrdId";
	public static final String callback_OrdAmt = "OrdAmt";
	public static final String callback_OrdNo = "OrdNo";
	public static final String callback_SignType = "SignType";
	public static final String callback_TradeStatus = "TradeStatus";
	public static final String callback_TradeMsg = "TradeMsg";
	public static final String callback_SignInfo = "SignInfo";
}