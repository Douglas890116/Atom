package com.maven.payment.ch;

public class CHMerchantConfig {
	
	/** 商户号 */
	private String merCode;
	/** 签名Key */
	private String sha1Key;
	/** 支付接口地址 */
	private String payUrl;
	
	public String getMerCode() {
		return merCode;
	}
	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}
	public String getSha1Key() {
		return sha1Key;
	}
	public void setSha1Key(String sha1Key) {
		this.sha1Key = sha1Key;
	}
	public String getPayUrl() {
		return payUrl;
	}
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
	
}