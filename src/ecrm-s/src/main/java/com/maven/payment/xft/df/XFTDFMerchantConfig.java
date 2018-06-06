package com.maven.payment.xft.df;

public class XFTDFMerchantConfig {
	private String merId;
	private String shaKey;
	private String payUrl;
	
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
	public String getPayUrl() {
		return payUrl;
	}
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
}