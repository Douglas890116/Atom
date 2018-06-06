package com.maven.payment.rf.df;

public class RFDFMerchantConfig {
	private String partyId;
	private String accountId;
	private String password;
	private String md5Key;
	private String directPaidout;
	private String note;
	private String payUrl;
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMd5Key() {
		return md5Key;
	}
	public void setMd5Key(String md5Key) {
		this.md5Key = md5Key;
	}
	public String getDirectPaidout() {
		return directPaidout;
	}
	public void setDirectPaidout(String directPaidout) {
		this.directPaidout = directPaidout;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getPayUrl() {
		return payUrl;
	}
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
}