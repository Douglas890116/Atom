package com.maven.payment.ek;

public class EKMerchantConfig {
	private String parter;
	private String md5Key;
	private String payUrl;
	private String returnUrl;
	private String notifyUrl;
	public String getParter() {
		return parter;
	}
	public void setParter(String parter) {
		this.parter = parter;
	}
	public String getMd5Key() {
		return md5Key;
	}
	public void setMd5Key(String md5Key) {
		this.md5Key = md5Key;
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