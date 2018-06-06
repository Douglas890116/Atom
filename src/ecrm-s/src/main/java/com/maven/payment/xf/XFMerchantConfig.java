package com.maven.payment.xf;

public class XFMerchantConfig {
	private String cid;
	private String uid;
	private String payUrl;
	private String sha1Key;
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPayUrl() {
		return payUrl;
	}
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
	public String getSha1Key() {
		return sha1Key;
	}
	public void setSha1Key(String sha1Key) {
		this.sha1Key = sha1Key;
	}
}