package com.maven.payment.zft.df;

public class ZFTDFMerchantConfig {
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