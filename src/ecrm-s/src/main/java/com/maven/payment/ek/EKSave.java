package com.maven.payment.ek;

import java.util.Map;

import com.maven.util.Encrypt;

public class EKSave {

	public String getUrl(EKMerchantConfig merchant, EKOrderConfig order) {
		String sign = getSign(merchant, order);
		StringBuffer url = new StringBuffer(merchant.getPayUrl());
		url.append("?").append(EKAppConstants.req_parter).append("=").append(merchant.getParter())
		   .append("&").append(EKAppConstants.req_type).append("=").append(order.getBankType())
		   .append("&").append(EKAppConstants.req_value).append("=").append(order.getOrderAmount())
		   .append("&").append(EKAppConstants.req_orderid).append("=").append(order.getOrderId())
		   .append("&").append(EKAppConstants.req_callbackurl).append("=").append(merchant.getNotifyUrl())
		   .append("&").append(EKAppConstants.req_hrefbackurl).append("=").append(merchant.getReturnUrl())
		   .append("&").append(EKAppConstants.req_payerIp).append("=").append(order.getIP())
		   .append("&").append(EKAppConstants.req_attach).append("=").append(order.getAttach())
		   .append("&").append(EKAppConstants.req_sign).append("=").append(sign);
		
		System.out.println("亿卡云支付链接地址：" + url.toString());
		
		return url.toString();
	}
	
	private String getSign(EKMerchantConfig merchant, EKOrderConfig order) {
		StringBuffer sb = new StringBuffer();
		sb.append(EKAppConstants.req_parter).append("=").append(merchant.getParter()).append("&")
		  .append(EKAppConstants.req_type).append("=").append(order.getBankType()).append("&")
		  .append(EKAppConstants.req_value).append("=").append(order.getOrderAmount()).append("&")
		  .append(EKAppConstants.req_orderid).append("=").append(order.getOrderId()).append("&")
		  .append(EKAppConstants.req_callbackurl).append("=").append(merchant.getNotifyUrl())
		  .append(merchant.getMd5Key());
		System.out.println("亿卡云签名原文：" + sb.toString());
		
		String sign = Encrypt.MD5(sb.toString());
		
		return sign;
	}
	
	public static boolean checkCallbackSign(Map<String, Object> params, String md5Key) {
		String callbackSign = params.get(EKAppConstants.callback_sign).toString();
		
		StringBuffer sb = new StringBuffer();
		sb.append(EKAppConstants.callback_orderid).append("=").append(params.get(EKAppConstants.callback_orderid).toString()).append("&")
		  .append(EKAppConstants.callback_opstate).append("=").append(params.get(EKAppConstants.callback_opstate).toString()).append("&")
		  .append(EKAppConstants.callback_ovalue).append("=").append(params.get(EKAppConstants.callback_ovalue)).append(md5Key);
		
		System.out.println("亿卡云回调签名原文：" + sb.toString());
		
		String sign = Encrypt.MD5(sb.toString());
		
		return sign.equals(callbackSign);
	}
}