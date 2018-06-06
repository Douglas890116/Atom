package com.maven.payment.jh.quick;

public class JHQuickPayMerchantConfig {
	private String payKey;
	private String md5Key;
	private String productType;
	private String payUrl;
	private String returnUrl;
	private String notifyUrl;
	
	public String getPayKey() {
		return payKey;
	}
	public void setPayKey(String md5Key) {
		this.payKey = md5Key;
	}
	public String getMd5Key() {
		return md5Key;
	}
	public void setMd5Key(String md5Key) {
		this.md5Key = md5Key;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
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