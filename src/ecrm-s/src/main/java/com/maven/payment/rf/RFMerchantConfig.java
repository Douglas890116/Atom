package com.maven.payment.rf;
/**
 * 锐付接口商户信息
 * @author klay
 *
 */
public class RFMerchantConfig {
	/** 商户Id */
	private String partyId;
	/** 账户Id */
	private String accountId;
	/** 订单前缀 */
	private String goods;
	/** 加密类型, 默认MD5 */
	private String encodeType;
	/** MD5签名Key */
	private String md5Key;
	/** 支付接口地址 */
	private String payUrl;
	/** 支付完成后返回地址 */
	private String returnUrl;
	/** 回调地址 */
	private String checkUrl;
	
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
	public String getGoods() {
		return goods;
	}
	public void setGoods(String goods) {
		this.goods = goods;
	}
	public String getEncodeType() {
		return encodeType;
	}
	public void setEncodeType(String encodeType) {
		this.encodeType = encodeType;
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
	public String getCheckUrl() {
		return checkUrl;
	}
	public void setCheckUrl(String checkUrl) {
		this.checkUrl = checkUrl;
	}
}