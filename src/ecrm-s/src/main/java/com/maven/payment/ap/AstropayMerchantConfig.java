package com.maven.payment.ap;

public class AstropayMerchantConfig {

	private String merLogin;
	private String merTransKey;
	private String merSecretKey;
	private String payUrl;
	private String returnUrl;
	private String notifyUrl;
	
	public String getMerLogin() {
		return merLogin;
	}
	public void setMerLogin(String merLogin) {
		this.merLogin = merLogin;
	}
	public String getMerTransKey() {
		return merTransKey;
	}
	public void setMerTransKey(String merTransKey) {
		this.merTransKey = merTransKey;
	}
	public String getMerSecretKey() {
		return merSecretKey;
	}
	public void setMerSecretKey(String merSecretKey) {
		this.merSecretKey = merSecretKey;
	}
	public String getPayUrl() {
		return payUrl;
	}
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
}