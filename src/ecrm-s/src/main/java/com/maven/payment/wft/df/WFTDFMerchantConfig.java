package com.maven.payment.wft.df;

public class WFTDFMerchantConfig {
	private String merId;
	private String publicRSAKey;
	private String privateRSAKey;
	private String AESKey;
	private String payUrl;
	private String notifyUrl;
	
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getPublicRSAKey() {
		return publicRSAKey;
	}
	public void setPublicRSAKey(String publicRSAKey) {
		this.publicRSAKey = publicRSAKey;
	}
	public String getPrivateRSAKey() {
		return privateRSAKey;
	}
	public void setPrivateRSAKey(String privateRSAKey) {
		this.privateRSAKey = privateRSAKey;
	}
	public String getAESKey() {
		return AESKey;
	}
	public void setAESKey(String aESKey) {
		AESKey = aESKey;
	}
	public String getPayUrl() {
		return payUrl;
	}
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
}