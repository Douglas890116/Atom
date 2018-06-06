package com.maven.payment.rf.df;

public class RFDFAppConstants {
	public enum Enum_DirectPaidout {
		直接下发("1"),
		审核下发("0");
		public String value;
		private Enum_DirectPaidout(String value) {
			this.value = value;
		}
	}
	/*========== 请求参数列表 ==========*/
	// 主参数
	public static final String req_payData = "payData";
	// json参数-业务参数
	public static final String req_json_partyId = "partyId";
	public static final String req_json_accountId = "accountId";
	public static final String req_json_note = "note";
	public static final String req_json_directPaidout = "directPaidout";
	public static final String req_json_paidoutPassword = "paidoutPassword";
	public static final String req_json_content = "content";
	// json参数-content参数
	public static final String content_orderNo = "orderNo";
	public static final String content_bankName = "bankName";
	public static final String content_provice = "provice";
	public static final String content_city = "city";
	public static final String content_branch = "branch";
	public static final String content_payee_name = "payee_name";
	public static final String content_payee_card = "payee_card";
	public static final String content_amount = "amount";
	public static final String content_refCode = "refCode";
	public static final String content_remark = "remark";
	public static final String content_signMd5 = "signMd5";
	/*========== 响应参数列表 ==========*/
	public static final String rep_ = "";
	/*========== 回调参数列表 ==========*/
}