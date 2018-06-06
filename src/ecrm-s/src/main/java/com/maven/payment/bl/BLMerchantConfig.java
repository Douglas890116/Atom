package com.maven.payment.bl;

public class BLMerchantConfig {
	/** 商户号 */
	private String merCode;
	/** 签名Key */
	private String md5Key;
	/** 支付接口地址 */
	private String payUrl;
	/** 请求结束返回地址 */
	private String returnUrl;
	/** 回调地址 */
	private String notifyUrl;
	
	public String getMerCode() {
		return merCode;
	}
	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}
	public String getMd5Key() {
		return md5Key;
	}
	public void setMd5Key(String md5Key) {
		this.md5Key = md5Key;
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