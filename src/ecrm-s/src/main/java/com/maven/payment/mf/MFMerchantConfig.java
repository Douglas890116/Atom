package com.maven.payment.mf;

public class MFMerchantConfig {
	/** 商户号 **/
	private String MerId;
	/** 支付地址 **/
	private String payUrl;
	/** 签名key **/
	private String merKey;
	/** 回调地址 **/
	private String returnUrl;
	/** 商户扩展信息**/
	private String merInfo;
	/** 应答机制，固定值：1 **/
	private String needResponse;
	
	public String getMerId() {
		return MerId;
	}

	public void setMerId(String merId) {
		MerId = merId;
	}

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

	public String getMerKey() {
		return merKey;
	}

	public void setMerKey(String merKey) {
		this.merKey = merKey;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getMerInfo() {
		return merInfo;
	}

	public void setMerInfo(String merInfo) {
		this.merInfo = merInfo;
	}

	public String getNeedResponse() {
		return needResponse;
	}

	public void setNeedResponse(String needResponse) {
		this.needResponse = needResponse;
	}
}