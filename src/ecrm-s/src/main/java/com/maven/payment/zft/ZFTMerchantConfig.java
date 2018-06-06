package com.maven.payment.zft;

public class ZFTMerchantConfig {
	private String merId;
	private String shaKey;
	private String merEmail;
	private String payType;
	
	private String appName;
	
	private String payUrl;
	private String returnUrl;
	private String notifyUrl;
	
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getShaKey() {
		return shaKey;
	}
	public void setShaKey(String shaKey) {
		this.shaKey = shaKey;
	}
	public String getMerEmail() {
		return merEmail;
	}
	public void setMerEmail(String merEmail) {
		this.merEmail = merEmail;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPayUrl() {
		return payUrl;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
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