package com.maven.payment.xingfu.df;

public class XingFDFMerchantConfig {
	private String merId;
	private String merKey;
	private String payUrl;
	private String service;
	private String notifyUrl;
	
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getMerKey() {
		return merKey;
	}
	public void setMerKey(String merKey) {
		this.merKey = merKey;
	}
	public String getPayUrl() {
		return payUrl;
	}
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
}