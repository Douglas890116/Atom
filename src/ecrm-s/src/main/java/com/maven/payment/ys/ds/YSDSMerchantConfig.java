package com.maven.payment.ys.ds;

public class YSDSMerchantConfig {
	private String merId;        // 商户号
	private String privateKeyUrl;// 私钥地址
	private String publicKeyUrl; // 银盛公钥地址
	private String password;     // 证书密码
	private String businessCode; // 业务编码
	private String payUrl;       // 支付地址
	private String method;       // 支付方法
	private String notifyUrl;    // 回调地址
	
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getPrivateKeyUrl() {
		return privateKeyUrl;
	}
	public void setPrivateKeyUrl(String privateKeyUrl) {
		this.privateKeyUrl = privateKeyUrl;
	}
	public String getPublicKeyUrl() {
		return publicKeyUrl;
	}
	public void setPublicKeyUrl(String publicKeyUrl) {
		this.publicKeyUrl = publicKeyUrl;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBusinessCode() {
		return businessCode;
	}
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	public String getPayUrl() {
		return payUrl;
	}
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
}