package com.maven.payment.lfalipay;

public class LfAliPayMerchantConfig {
	/**
	 * 商户合作号，对应参数里的partner
	 */
	private String merNo;
	/**
	 * 接口支付地址
	 */
	private String payUrl;
	/**
	 * 商户私钥
	 */
	private String merPublicKey;
	/**
	 * 商户公钥
	 */
	private String merPrivateKey;
	/**
	 * 平台公匙
	 */
	private String publicKey;
	/**
	 * 后台回调地址
	 */
	private String returnUrl;
	
	public String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	public String getPayUrl() {
		return payUrl;
	}
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
	public String getMerPublicKey() {
		return merPublicKey;
	}
	public void setMerPublicKey(String merPublicKey) {
		this.merPublicKey = merPublicKey;
	}
	public String getMerPrivateKey() {
		return merPrivateKey;
	}
	public void setMerPrivateKey(String merPrivateKey) {
		this.merPrivateKey = merPrivateKey;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
}