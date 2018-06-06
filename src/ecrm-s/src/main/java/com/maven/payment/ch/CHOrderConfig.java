package com.maven.payment.ch;

public class CHOrderConfig {

	// 固定值
	public static final String p3_Cur = "CNY"; // 交易币种
	public static final String p0_Cmd = "Buy"; // 业务类型
	
	private String p8_Url; // 回调地址
	// 必须参数
	private String p2_Order; // 商户订单号
	private String p4_Amt; // 支付金额
	private String pa_FrpId; // 支付通道编码
	private String hmac;// 签名数据

	// 网银支付必传
	private String pg_BankCode; // 银行编码

	// 快捷支付直连参数
	private String pc_CardNo; // 卡号
	private String pd_Name;// 姓名
	private String pe_CredType;// 证件类型
	private String pe_IdNum;// 证件号
	private String pf_PhoneNum;// 银行预留手机号
	private String pf_SmsTrxId; // 短信验证码标识
	private String pf_kaptcha;// 短信验证码
	// 快捷支付直连且是信用卡时必传
	private String pc_ExpireYear;// 信用卡有效期年
	private String pc_ExpireMonth;// 信用卡有效期月
	private String pc_CVV; // 信用卡 CVV 3 或 4 位
	
	public String getP8_Url() {
		return p8_Url;
	}
	public void setP8_Url(String p8_Url) {
		this.p8_Url = p8_Url;
	}
	public String getP2_Order() {
		return p2_Order;
	}
	public void setP2_Order(String p2_Order) {
		this.p2_Order = p2_Order;
	}
	public String getP4_Amt() {
		return p4_Amt;
	}
	public void setP4_Amt(String p4_Amt) {
		this.p4_Amt = p4_Amt;
	}
	public String getPa_FrpId() {
		return pa_FrpId;
	}
	public void setPa_FrpId(String pa_FrpId) {
		this.pa_FrpId = pa_FrpId;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
	public String getPg_BankCode() {
		return pg_BankCode;
	}
	public void setPg_BankCode(String pg_BankCode) {
		this.pg_BankCode = pg_BankCode;
	}
	public String getPc_CardNo() {
		return pc_CardNo;
	}
	public void setPc_CardNo(String pc_CardNo) {
		this.pc_CardNo = pc_CardNo;
	}
	public String getPd_Name() {
		return pd_Name;
	}
	public void setPd_Name(String pd_Name) {
		this.pd_Name = pd_Name;
	}
	public String getPe_CredType() {
		return pe_CredType;
	}
	public void setPe_CredType(String pe_CredType) {
		this.pe_CredType = pe_CredType;
	}
	public String getPe_IdNum() {
		return pe_IdNum;
	}
	public void setPe_IdNum(String pe_IdNum) {
		this.pe_IdNum = pe_IdNum;
	}
	public String getPf_PhoneNum() {
		return pf_PhoneNum;
	}
	public void setPf_PhoneNum(String pf_PhoneNum) {
		this.pf_PhoneNum = pf_PhoneNum;
	}
	public String getPf_SmsTrxId() {
		return pf_SmsTrxId;
	}
	public void setPf_SmsTrxId(String pf_SmsTrxId) {
		this.pf_SmsTrxId = pf_SmsTrxId;
	}
	public String getPf_kaptcha() {
		return pf_kaptcha;
	}
	public void setPf_kaptcha(String pf_kaptcha) {
		this.pf_kaptcha = pf_kaptcha;
	}
	public String getPc_ExpireYear() {
		return pc_ExpireYear;
	}
	public void setPc_ExpireYear(String pc_ExpireYear) {
		this.pc_ExpireYear = pc_ExpireYear;
	}
	public String getPc_ExpireMonth() {
		return pc_ExpireMonth;
	}
	public void setPc_ExpireMonth(String pc_ExpireMonth) {
		this.pc_ExpireMonth = pc_ExpireMonth;
	}
	public String getPc_CVV() {
		return pc_CVV;
	}
	public void setPc_CVV(String pc_CVV) {
		this.pc_CVV = pc_CVV;
	}
	
}