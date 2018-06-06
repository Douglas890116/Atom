package com.maven.payment.leying;

import com.maven.util.Encrypt;

public class LEYINGSave {
	
	private String version="";
	
	private String serialID="";
	
	private String submitTime="";
	
	private String failureTime="";
	
	private String customerIP="";
	
	private String orderDetails="";
	
	private String totalAmount="";
	
	private String type="1000";
	
	private String buyerMarked="";
	
	private String payType="";
	
	private String orgCode="";
	
	private String currencyCode="1";
	
	private String directFlag="0";
	
	private String borrowingMarked="0";
	
	private String couponFlag="1";
	
	private String platformID="";
	
	private String returnUrl="";
	
	private String noticeUrl="";
	
	private String partnerID="";
	
	private String remark="";
	
	private String charset="";
	
	private String signType="";
	
	@SuppressWarnings("unused")
	private String signMsg;
	
	private String md5key="";
	
	private String saveUrl="";
	
	public LEYINGSave(String version, String payType, String returnUrl, String noticeUrl, String partnerID,
			String charset, String signType, String md5key, String saveUrl) {
		this.version = version;
		this.payType = payType;
		this.signType = signType;
		this.charset = charset;
		this.returnUrl = returnUrl;
		this.noticeUrl = noticeUrl;
		this.partnerID = partnerID;
		this.md5key = md5key;
		this.saveUrl = saveUrl;
	}

	public String getUrl(String serialID,String submitTime,String orderDetails,String totalAmount){
		this.serialID = serialID;
		this.submitTime = submitTime;
		this.orderDetails = orderDetails;
		this.totalAmount = totalAmount;
		
		StringBuffer sbf = new StringBuffer();
		sbf.append("version").append("=").append(this.version).append("&")
		.append("serialID").append("=").append(this.serialID).append("&")
		.append("submitTime").append("=").append(this.submitTime).append("&")
		.append("failureTime").append("=").append(this.failureTime).append("&")
		.append("customerIP").append("=").append(this.customerIP).append("&")
		.append("orderDetails").append("=").append(this.orderDetails).append("&")
		.append("totalAmount").append("=").append(this.totalAmount).append("&")
		.append("type").append("=").append(this.type).append("&")
		.append("buyerMarked").append("=").append(this.buyerMarked).append("&")
		.append("payType").append("=").append(this.payType).append("&")
		.append("orgCode").append("=").append(this.orgCode).append("&")
		.append("currencyCode").append("=").append(this.currencyCode).append("&")
		.append("directFlag").append("=").append(this.directFlag).append("&")
		.append("borrowingMarked").append("=").append(this.borrowingMarked).append("&")
		.append("couponFlag").append("=").append(this.couponFlag).append("&")
		.append("platformID").append("=").append(this.platformID).append("&")
		.append("returnUrl").append("=").append(this.returnUrl).append("&")
		.append("noticeUrl").append("=").append(this.noticeUrl).append("&")
		.append("partnerID").append("=").append(this.partnerID).append("&")
		.append("remark").append("=").append(this.remark).append("&")
		.append("charset").append("=").append(this.charset).append("&")
		.append("signType").append("=").append(this.signType).append("&")
		.append("signMsg").append("=").append(this.getSignMsg());
		return this.saveUrl+"?"+sbf.toString();
	}
	
	public String getSignMsg(){
		StringBuffer sbf = new StringBuffer();
		sbf.append("version").append("=").append(this.version).append("&")
		.append("serialID").append("=").append(this.serialID).append("&")
		.append("submitTime").append("=").append(this.submitTime).append("&")
		.append("failureTime").append("=").append(this.failureTime).append("&")
		.append("customerIP").append("=").append(this.customerIP).append("&")
		.append("orderDetails").append("=").append(this.orderDetails).append("&")
		.append("totalAmount").append("=").append(this.totalAmount).append("&")
		.append("type").append("=").append(this.type).append("&")
		.append("buyerMarked").append("=").append(this.buyerMarked).append("&")
		.append("payType").append("=").append(this.payType).append("&")
		.append("orgCode").append("=").append(this.orgCode).append("&")
		.append("currencyCode").append("=").append(this.currencyCode).append("&")
		.append("directFlag").append("=").append(this.directFlag).append("&")
		.append("borrowingMarked").append("=").append(this.borrowingMarked).append("&")
		.append("couponFlag").append("=").append(this.couponFlag).append("&")
		.append("platformID").append("=").append(this.platformID).append("&")
		.append("returnUrl").append("=").append(this.returnUrl).append("&")
		.append("noticeUrl").append("=").append(this.noticeUrl).append("&")
		.append("partnerID").append("=").append(this.partnerID).append("&")
		.append("remark").append("=").append(this.remark).append("&")
		.append("charset").append("=").append(this.charset).append("&")
		.append("signType").append("=").append(this.signType).append("&")
		.append("pkey").append("=").append(this.md5key);
		return Encrypt.MD5(sbf.toString());
	}
	
}
