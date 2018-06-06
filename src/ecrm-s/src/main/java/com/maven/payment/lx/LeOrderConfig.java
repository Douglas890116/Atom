package com.maven.payment.lx;

public class LeOrderConfig {
	/**支付渠道编码*/
	private String payTypeCode;
	/**订单号*/
	private String outTradeNo;
	/**交易时间*/
	private String tradeTime;
	/**交易金额*/
	private String amount;
	/**摘要*/
	private String summary;
	/**摘要详情*/
	private String summaryDetail;
	/**终端设备*/
	private String deviceId;
	/**终端设备IP*/
	private String deviceIp;
	/**买家ID*/
	private String buyerId;
	
	public String getPayTypeCode() {
		return payTypeCode;
	}
	public void setPayTypeCode(String payTypeCode) {
		this.payTypeCode = payTypeCode;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getSummaryDetail() {
		return summaryDetail;
	}
	public void setSummaryDetail(String summaryDetail) {
		this.summaryDetail = summaryDetail;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceIp() {
		return deviceIp;
	}
	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
}