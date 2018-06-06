package com.maven.payment.ry;

public class RYOrderConfig {
	/**
	 * 订单号
	 */
	private String merOrdId;
	/**
	 * 订单金额
	 */
	private String merOrdAmt;
	/**
	 * 支付类型
	 * 10-网关 支付
	 * 20-快捷支付
	 * 30-微信扫码支付 微信扫码支付
	 * 40-支付 宝扫码支付 宝扫码支付
	 * 50-QQ钱包 扫码 支
	 */
	private String payType;
	/**
	 * 银行编码
	 */
	private String bankCode;
	/**
	 * 备注
	 */
	private String remark;
	
	public String getMerOrdId() {
		return merOrdId;
	}
	public void setMerOrdId(String merOrdId) {
		this.merOrdId = merOrdId;
	}
	public String getMerOrdAmt() {
		return merOrdAmt;
	}
	public void setMerOrdAmt(String merOrdAmt) {
		this.merOrdAmt = merOrdAmt;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public static void main(String[] args) {
		String s = "Charset=UTF-8&CurCode=RMB&MerId=22276540&PayeeDetails=iv+PhT/T//vLrSDmWyXfEWbAS0c05RG1j6rDMTWP0/BA1lsJwRmg3NqYSrSv9h0uYNHoFMWfkNZoKjR5ToVau0As678MMCyTYzPl9gjRf27xyTXjFnegMKTiJ7KdG5Sb72t2kkH8wTNwm8zT3jY4XizvwbZzBKPxvuGVMWFp1XgbHPsoGZ5X73kIRAOlO2WPTlOEk7i9Hb1odF5BdTl70Q3Xmaa+W9YGUC4GVBcGvFCVPfeuPdDmnqjXZtuha7kwmcUiVsKLfP0Y1Hj75qp+W1nlSc6wOddC10ucKHotlYtJtJ1cB/QGeYIbkaWcjFX0GzKDfBO9qMRTo379rU9V9jAJqmxYaA6XpaG8ITwVPTn0DqoIdzIU/GXBUGsRWUaWoOOL3yIMBfhRKkJFx0aovQ==&Remark=Test Remark&SignType=RSA&TranDateTime=20170125024944&Version=2.1";
		String[] t = s.split("&");
		for (String str : t) {
			System.out.println(str);
		}
	}
}