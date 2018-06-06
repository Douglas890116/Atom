package com.maven.payment.jh.quick;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.maven.payment.kk.KKAppConstants;
import com.maven.util.Encrypt;

public class JHQuickPaySave {

	public static String getOrderTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(new Date());
	}
	public String getUrl(JHQuickPayMerchantConfig merchant, JHQuickPayOrderConfig order) {
		String sign = getSign(merchant, order);
		
		StringBuffer url = new StringBuffer(merchant.getPayUrl());
		url.append("?").append(JHQuickPayAppConstants.req_payKey).append("=").append(merchant.getPayKey())
		   .append("&").append(JHQuickPayAppConstants.req_orderPrice).append("=").append(order.getOrderAmount())
		   .append("&").append(JHQuickPayAppConstants.req_outTradeNo).append("=").append(order.getOrderNo())
		   .append("&").append(JHQuickPayAppConstants.req_productType).append("=").append(merchant.getProductType())
		   .append("&").append(JHQuickPayAppConstants.req_orderTime).append("=").append(order.getOrderTime())
		   .append("&").append(JHQuickPayAppConstants.req_payBankAccountNo).append("=").append(order.getBankNo())
		   .append("&").append(JHQuickPayAppConstants.req_productName).append("=").append(order.getOrderName())
		   .append("&").append(JHQuickPayAppConstants.req_orderIp).append("=").append(order.getOrderIP())
		   .append("&").append(JHQuickPayAppConstants.req_returnUrl).append("=").append(merchant.getReturnUrl())
		   .append("&").append(JHQuickPayAppConstants.req_notifyUrl).append("=").append(merchant.getNotifyUrl())
		   .append("&").append(JHQuickPayAppConstants.req_sign).append("=").append(sign);
		
		System.out.println("聚合QuickPay支付链接地址：" + url.toString());
		
		return url.toString();
	}
	
	private String getSign(JHQuickPayMerchantConfig merchant, JHQuickPayOrderConfig order) {
		SortedMap<String, String> params = new TreeMap<String, String>();
		
		params.put(JHQuickPayAppConstants.req_payKey, merchant.getPayKey());
		params.put(JHQuickPayAppConstants.req_orderPrice, order.getOrderAmount());
		params.put(JHQuickPayAppConstants.req_outTradeNo, order.getOrderNo());
		params.put(JHQuickPayAppConstants.req_productType, merchant.getProductType());
		params.put(JHQuickPayAppConstants.req_orderTime, order.getOrderTime());
		params.put(JHQuickPayAppConstants.req_payBankAccountNo, order.getBankNo());
		params.put(JHQuickPayAppConstants.req_productName, order.getOrderName());
		params.put(JHQuickPayAppConstants.req_orderIp, order.getOrderIP());
		params.put(JHQuickPayAppConstants.req_returnUrl, merchant.getReturnUrl());
		params.put(JHQuickPayAppConstants.req_notifyUrl, merchant.getNotifyUrl());
		
		System.out.println("聚合QuickPay支付参数未排序原文：" + params);
		
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> es = params.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		System.out.println("聚合QuickPay支付参数排序后原文：" + params);
		
		String paramStr = sb.append("paySecret=").append(merchant.getMd5Key()).toString();
		System.out.println("聚合QuickPay支付MD5签名原文：" + paramStr);
		
		String sign = Encrypt.MD5(paramStr).toUpperCase();
		System.out.println("聚合QuickPay支付MD5签名：" + sign);
		
		return sign;
	}
	
	public static boolean checkSign(Map<String, Object> request, String md5Key) {
		SortedMap<String, String> params = new TreeMap<String, String>();
		
		Set<String> keys = request.keySet();
		for (String key : keys) {
			if(!JHQuickPayAppConstants.callback_sign.equals(key)
					&& request.get(key) != null && StringUtils.isNotBlank(request.get(key).toString()))
				params.put(key, request.get(key).toString());
		}
		
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> es = params.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		String paramStr = sb.append("paySecret=").append(md5Key).toString();
		System.out.println("聚合QuickPay支付回调参数Md5签名原文：" + paramStr);
		
		String localSign = Encrypt.MD5(paramStr).toUpperCase();
		String callbackSign = request.get(KKAppConstants.callback_sign).toString();
		return localSign.equals(callbackSign);
	}
}