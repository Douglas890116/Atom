package com.maven.payment.rf;

import java.util.Map;

import com.maven.util.Encrypt;

public class RFSave {
	
	// 支付接口地址：https://payment.rfupayadv.com/prod/commgr/control/inPayService

	/**
	 * 获取支付链接
	 * @param merchant
	 * @param order
	 * @return
	 */
	public String getUrl(RFMerchantConfig merchant, RFOrderConfig order) {
		String sign = getSign(merchant, order);
		
		StringBuffer url = new StringBuffer(merchant.getPayUrl());
		url.append("?").append(RFAppConstants.r_partyId).append("=").append(merchant.getPartyId())
		   .append("&").append(RFAppConstants.r_accountId).append("=").append(merchant.getAccountId())
		   .append("&").append(RFAppConstants.r_appType).append("=").append(order.getAppType())
		   .append("&").append(RFAppConstants.r_orderNo).append("=").append(order.getOrderNo())
		   .append("&").append(RFAppConstants.r_orderAmount).append("=").append(order.getOrderAmount())
		   .append("&").append(RFAppConstants.r_goods).append("=").append(merchant.getGoods())
		   .append("&").append(RFAppConstants.r_returnUrl).append("=").append(merchant.getReturnUrl())
		   .append("&").append(RFAppConstants.r_checkUrl).append("=").append(merchant.getCheckUrl())
		   .append("&").append(RFAppConstants.r_cardType).append("=").append("01")// 01-RMB debit card 02-Credit card
		   .append("&").append(RFAppConstants.r_bank).append("=").append(order.getBank())
		   .append("&").append(RFAppConstants.r_refCode).append("=").append("00000000")// 預設00000000，子商戶參考編號
		   .append("&").append(RFAppConstants.r_encodeType).append("=").append("Md5")// 签名方式 默认Md5
		   .append("&").append(RFAppConstants.r_signMD5).append("=").append(sign);
		
		System.out.println("锐付支付接口支付链接：" + url.toString());
		
		return url.toString();
	}
	/**
	 * 获取签名字符串
	 * @param merchant
	 * @param order
	 * @return
	 */
	private static String getSign(RFMerchantConfig merchant, RFOrderConfig order) {
		/* The MD5 authentication string concatenated by below field name and value, and then change the encrypted string to lowercase. 
		 * orderNo 【Order no.】  appType  【Application type】  orderAmount  【Order amount】  encodeType  【Encode type】  【Merchant MD5 Key String】
		 */
		StringBuffer sb = new StringBuffer();
		sb.append("orderNo").append(order.getOrderNo())
		  .append("appType").append(order.getAppType())
		  .append("orderAmount").append(order.getOrderAmount())
		  .append("encodeType").append(merchant.getEncodeType())
		  .append(merchant.getMd5Key());
		
		String data = sb.toString();
		System.out.println("锐付MD5签名原文：" + data);
		
		String sign = Encrypt.MD5(data).toLowerCase();
		System.out.println("锐付MD5签名密文：" + sign);
		
		return sign; 
	}
	/**
	 * 校验回调签名
	 * @param params
	 * @param md5Key
	 * @return
	 */
	public static boolean cheakCallbackSign(Map<String, Object> params, String md5Key) {
		
		String callbackSign = params.get(RFAppConstants.callback_signMD5).toString();
		
		String orderNo = params.get(RFAppConstants.callback_orderNo).toString();
		String appType  = params.get(RFAppConstants.callback_appType ).toString();
		String orderAmount  = params.get(RFAppConstants.callback_orderAmount ).toString();
		String succ = params.get(RFAppConstants.callback_succ).toString();
		String encodeType = params.get(RFAppConstants.callback_encodeType ).toString();
		
		StringBuffer sb = new StringBuffer();
		sb.append("orderNo").append(orderNo)
		  .append("appType").append(appType)
		  .append("orderAmount").append(orderAmount)
		  .append("succ").append(succ)
		  .append("encodeType").append(encodeType)
		  .append(md5Key);
		String localSign = Encrypt.MD5(sb.toString()).toLowerCase();
		
		return localSign.equals(callbackSign);
	}
}