package com.maven.payment.zb;

public class ZBMerchantConfig {
	private String customer;
	private String asynbackurl;
	private String synbackurl;
	private String payUrl;
	private String md5Key;
	
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getAsynbackurl() {
		return asynbackurl;
	}
	public void setAsynbackurl(String asynbackurl) {
		this.asynbackurl = asynbackurl;
	}
	public String getSynbackurl() {
		return synbackurl;
	}
	public void setSynbackurl(String synbackurl) {
		this.synbackurl = synbackurl;
	}
	public String getPayUrl() {
		return payUrl;
	}
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
	public String getMd5Key() {
		return md5Key;
	}
	public void setMd5Key(String md5Key) {
		this.md5Key = md5Key;
	}
}