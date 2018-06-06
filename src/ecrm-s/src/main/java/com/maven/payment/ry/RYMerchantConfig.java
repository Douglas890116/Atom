package com.maven.payment.ry;

public class RYMerchantConfig {
	/**
	 * 商户ID
	 */
	private String merId;
	/**
	 * 商户签名Key
	 */
	private String merKey;
	/**
	 * 支付接口地址
	 */
	private String payUrl;
	/**
	 * 前端返回页面
	 */
	private String returnUrl;
	/**
	 * 回调地址
	 */
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