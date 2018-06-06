package com.maven.payment.duo.df;

public class DuoDFMerchantConfig {
	/**
	 * 商户Id
	 */
	private String merId;
	/**
	 * 代付接口地址
	 */
	private String payUrl;
	/**
	 * RSA公钥
	 */
	private String RSAPublicKey;
	/**
	 * RSA私钥
	 */
	private String RSAPrivateKey;
	/**
	 * ASE私钥
	 */
	private String ASEKey;
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
	public String getPayUrl() {
		return payUrl;
	}
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
	public String getRSAPublicKey() {
		return RSAPublicKey;
	}
	public void setRSAPublicKey(String rSAPublicKey) {
		RSAPublicKey = rSAPublicKey;
	}
	public String getRSAPrivateKey() {
		return RSAPrivateKey;
	}
	public void setRSAPrivateKey(String rSAPrivateKey) {
		RSAPrivateKey = rSAPrivateKey;
	}
	public String getASEKey() {
		return ASEKey;
	}
	public void setASEKey(String aSEKey) {
		ASEKey = aSEKey;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
}