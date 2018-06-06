package com.maven.payment.ap;

public class AstropayAppConstants {
	/**
	 * 支付类型
	 * @author klay
	 *
	 */
	public enum Astro_PayType {
		AUTH_ONLY("AUTH_ONLY"),
		CAPTURE_ONLY("CAPTURE_ONLY"),
		AUTH_CAPTURE("AUTH_CAPTURE"),
		REFUND("REFUND"),
		VOID("VOID");
		public String value;
		private Astro_PayType(String value) {
			this.value = value;
		}
	}
	/**
	 * 出款类型
	 * @author klay
	 *
	 */
	public enum Astro_CashOutType {
		FastCashout("FastCashout"),
		MerchantCashout("MerchantCashout"),
		MobileCashout("MobileCashout");
		public String value;
		private Astro_CashOutType(String value) {
			this.value = value;
		}
	}
	/*========== 请求参数 ==========*/
	public static final String req_x_login         = "x_login";
	public static final String req_x_tran_key      = "x_tran_key";
	public static final String req_x_trans_key      = "x_trans_key";
	public static final String req_x_type          = "x_type";
	public static final String req_x_card_num      = "x_card_num";
	public static final String req_x_card_code     = "x_card_code";
	public static final String req_x_exp_date      = "x_exp_date";
	public static final String req_x_amount        = "x_amount";
	public static final String req_x_currency      = "x_currency";
	public static final String req_x_unique_id     = "x_unique_id";
	public static final String req_x_invoice_num   = "x_invoice_num";
	public static final String req_x_email         = "x_email";
	public static final String req_x_name          = "x_name";
	public static final String req_x_document      = "x_document";
	public static final String req_x_country       = "x_country";
	public static final String req_x_control       = "x_control";
	public static final String req_x_mobile_number = "x_mobile_number";
	// 非必填参数
	public static final String req_x_version          = "x_version";
	public static final String req_x_duplicate_window = "x_duplicate_window";
	public static final String req_x_response_format  = "x_response_format";
	public static final String req_x_delim_char       = "x_delim_char";
	public static final String req_x_delim_data       = "x_delim_data";
	public static final String req_x_method           = "x_method";
	public static final String req_x_trans_id         = "x_trans_id";
	public static final String req_x_window           = "x_window";
	public static final String req_x_reference        = "x_reference";
	public static final String req_notification_url   = "notification_url";
	/*========== 响应参数 ==========*/
	public static final String rep_response_code        = "response_code";
	public static final String rep_response_subcode     = "response_subcode";
	public static final String rep_response_reason_code = "response_reason_code";
	public static final String rep_response_reason_text = "response_reason_text";
	public static final String rep_approval_code        = "approval_code";
	public static final String rep_AVS                  = "AVS";
	public static final String rep_TransactionID        = "TransactionID";
	public static final String rep_r_unique_id          = "r_unique_id";
	public static final String rep_x_invoice_num        = "x_invoice_num";
	public static final String rep_x_description        = "x_description";
	public static final String rep_x_amount             = "x_amount";
	public static final String rep_x_type               = "x_type";
	public static final String rep_md5_hash             = "md5_hash";
	public static final String rep_cc_response          = "cc_response";
	public static final String rep_code                 = "code";
	public static final String rep_message              = "message";
	public static final String rep_response             = "response";
	public static final String rep_id_cashout           = "id_cashout";
	public static final String rep_auth_code            = "auth_code";
	public static final String rep_email                = "email";
	public static final String rep_amount               = "amount";
	public static final String rep_currency             = "currency";
	public static final String rep_control              = "control";
	public static final String rep_card_number          = "card_number";
	public static final String rep_card_expiration      = "card_expiration";
	public static final String rep_card_cvv             = "card_cvv";
	public static final String rep_mobile_number        = "mobile_number";
	/*========== 回调参数 ==========*/
}