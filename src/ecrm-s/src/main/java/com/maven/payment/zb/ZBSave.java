package com.maven.payment.zb;

import java.util.Map;

import com.maven.util.Encrypt;

public class ZBSave {

	public String getUrl(ZBMerchantConfig merchant, ZBOrderConfig order) {
		String sign = getSign(merchant, order);
		StringBuffer sb = new StringBuffer(merchant.getPayUrl());
		sb.append("?").append(ZBAppConstants.req_customer).append("=").append(merchant.getCustomer())
		  .append("&").append(ZBAppConstants.req_banktype).append("=").append(order.getBanktype())
		  .append("&").append(ZBAppConstants.req_amount).append("=").append(order.getAmount())
		  .append("&").append(ZBAppConstants.req_orderid).append("=").append(order.getOrderid())
		  .append("&").append(ZBAppConstants.req_asynbackurl).append("=").append(merchant.getAsynbackurl())
		  .append("&").append(ZBAppConstants.req_request_time).append("=").append(order.getRequestTime())
		  .append("&").append(ZBAppConstants.req_synbackurl).append("=").append(merchant.getSynbackurl())
		  .append("&").append(ZBAppConstants.req_attach).append("=").append(order.getAttach())
		  .append("&").append(ZBAppConstants.req_sign).append("=").append(sign);
		System.out.println("众宝支付接口完整链接：" + sb.toString());
		
		return sb.toString();
	}
	
	private String getSign(ZBMerchantConfig merchant, ZBOrderConfig order) {
		StringBuffer sb = new StringBuffer();
		sb.append(ZBAppConstants.req_customer).append("=").append(merchant.getCustomer()).append("&")
		  .append(ZBAppConstants.req_banktype).append("=").append(order.getBanktype()).append("&")
		  .append(ZBAppConstants.req_amount).append("=").append(order.getAmount()).append("&")
		  .append(ZBAppConstants.req_orderid).append("=").append(order.getOrderid()).append("&")
		  .append(ZBAppConstants.req_asynbackurl).append("=").append(merchant.getAsynbackurl()).append("&")
		  .append(ZBAppConstants.req_request_time).append("=").append(order.getRequestTime()).append("&")
		  .append("key=").append(merchant.getMd5Key());
		System.out.println("众宝支付请求签名原文：" + sb.toString());
		String sign = Encrypt.MD5(sb.toString());
		System.out.println("众宝支付请求签名密文：" + sign);
		return sign;
	}
	
	public static boolean checkCallbackSign(Map<String, Object> request, String md5Key) {
		StringBuffer sb = new StringBuffer();
		sb.append(ZBAppConstants.callback_orderid).append("=").append(request.get(ZBAppConstants.callback_orderid)).append("&")
		  .append(ZBAppConstants.callback_result).append("=").append(request.get(ZBAppConstants.callback_result)).append("&")
		  .append(ZBAppConstants.callback_amount).append("=").append(request.get(ZBAppConstants.callback_amount)).append("&")
		  .append(ZBAppConstants.callback_zborderid).append("=").append(request.get(ZBAppConstants.callback_zborderid)).append("&")
		  .append(ZBAppConstants.callback_completetime).append("=").append(request.get(ZBAppConstants.callback_completetime)).append("&")
		  .append("key=").append(md5Key);
		System.out.println("众宝支付回调签名原文：" + sb.toString());
		String sign = Encrypt.MD5(sb.toString());
		System.out.println("众宝支付回调签名密文：" + sign);
		String callbackSign = String.valueOf(request.get(ZBAppConstants.callback_sign));
		return sign.equals(callbackSign);
	}
}