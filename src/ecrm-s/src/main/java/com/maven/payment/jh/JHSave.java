package com.maven.payment.jh;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.maven.util.Encrypt;
import com.maven.util.HttpPostUtil;

import net.sf.json.JSONObject;

public class JHSave {
	
	public String save(JHMerchantConfig merchant, JHOrderConfig order) {
		String sign = getSign(merchant, order);
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put(JHAppConstants.req_merchantOutOrderNo, order.getOrderNo());
		params.put(JHAppConstants.req_merid, merchant.getMerid());
		params.put(JHAppConstants.req_noncestr, order.getNoncestr());
		params.put(JHAppConstants.req_notifyUrl, merchant.getNotifyUrl());
		params.put(JHAppConstants.req_orderMoney, order.getOrderAmount());
		params.put(JHAppConstants.req_orderTime, order.getOrderTime());
		params.put(JHAppConstants.req_sign, sign);
		
		if(merchant.getPayUrl().indexOf("createOrder") > 0
			|| merchant.getPayUrl().indexOf("alipayAppOrder") > 0) {
			StringBuffer sb = new StringBuffer(merchant.getPayUrl());
			sb.append("?").append(JHAppConstants.req_merchantOutOrderNo).append("=").append(order.getOrderNo());
			sb.append("&").append(JHAppConstants.req_merid).append("=").append(merchant.getMerid());
			sb.append("&").append(JHAppConstants.req_noncestr).append("=").append(order.getNoncestr());
			sb.append("&").append(JHAppConstants.req_notifyUrl).append("=").append(merchant.getNotifyUrl());
			sb.append("&").append(JHAppConstants.req_orderMoney).append("=").append(order.getOrderAmount());
			sb.append("&").append(JHAppConstants.req_orderTime).append("=").append(order.getOrderTime());
			sb.append("&").append(JHAppConstants.req_sign).append("=").append(sign);
			return sb.toString();
		}
		
		String result = HttpPostUtil.doPostSubmitMap(merchant.getPayUrl(), params);
		
		System.out.println("聚合支付接口请求结果：" + result);
		
		JSONObject json = JSONObject.fromObject(result);
		if (json.has(JHAppConstants.rep_url)) {
			return json.getString(JHAppConstants.rep_url);
		} else {
			return "";
		}
	}

	private String getSign(JHMerchantConfig merchant, JHOrderConfig order) {
		SortedMap<String, String> params = new TreeMap<String, String>();
		
		params.put(JHAppConstants.req_merchantOutOrderNo, order.getOrderNo());
		params.put(JHAppConstants.req_merid, merchant.getMerid());
		params.put(JHAppConstants.req_noncestr, order.getNoncestr());
		params.put(JHAppConstants.req_orderMoney, order.getOrderAmount());
		params.put(JHAppConstants.req_notifyUrl, merchant.getNotifyUrl());
		params.put(JHAppConstants.req_orderTime, order.getOrderTime());
		
		System.out.println("聚合支付参数未排序原文：" + params);
		
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
		System.out.println("聚合支付参数排序后原文：" + params);
		
		String paramStr = sb.append("key=").append(merchant.getMd5Key()).toString();
		System.out.println("聚合支付MD5签名原文：" + paramStr);
		
		String sign = Encrypt.MD5(paramStr);
		System.out.println("聚合支付MD5签名：" + sign);
		
		return sign;
	}
	
	/**
	 * 验签
	 * @param request
	 * @param md5Key
	 * @return
	 */
	public static boolean checkCallbackSign(Map<String, Object> request, String md5Key) {
		SortedMap<String, String> params = new TreeMap<String, String>();
		
		Set<String> keys = request.keySet();
		for (String key : keys) {
			params.put(key, request.get(key).toString());
		}
		
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> es = params.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (StringUtils.isNotBlank(k) && !JHAppConstants.callback_sign.equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		System.out.println("聚合回调参数排序后原文：" + params);
		
		String paramStr = sb.append("key=").append(md5Key).toString();
		System.out.println("聚合回调MD5签名原文：" + paramStr);
		
		String localSign = Encrypt.MD5(paramStr);
		String callbackSign = request.get(JHAppConstants.callback_sign).toString();
		
		return localSign.equals(callbackSign);
		
	}
}