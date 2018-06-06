package com.maven.payment.jh.h5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.maven.util.Encrypt;

import net.sf.json.JSONObject;

public class JHH5Save {
	/**
	 * 获取支付链接
	 * @param merchant
	 * @param order
	 * @return
	 */
	public String getUrl(JHH5MerchantConfig merchant, JHH5OrderConfig order) {
		String sign = getSign(merchant, order);
		
		StringBuffer url = new StringBuffer(merchant.getPayUrl());
		url.append("?").append(JHH5AppConstants.req_merchantId).append("=").append(merchant.getMerId())
		   .append("&").append(JHH5AppConstants.req_customerId).append("=").append(order.getOrderUserId())
		   .append("&").append(JHH5AppConstants.req_merchantOrderId).append("=").append(order.getOrderId())
		   .append("&").append(JHH5AppConstants.req_appinfoId).append("=").append(merchant.getAppId())
		   .append("&").append(JHH5AppConstants.req_subjectNo).append("=").append(order.getSubjectNo())
		   .append("&").append(JHH5AppConstants.req_subject).append("=").append(order.getSubject())
		   .append("&").append(JHH5AppConstants.req_amount).append("=").append(order.getOrderAmount())
		   .append("&").append(JHH5AppConstants.req_payDirect).append("=").append("")
		   .append("&").append(JHH5AppConstants.req_directPayType).append("=").append("")
		   .append("&").append(JHH5AppConstants.req_notifyUrl).append("=").append(merchant.getNotifyUrl())
		   .append("&").append(JHH5AppConstants.req_returnUrl).append("=").append(merchant.getReturnUrl())
		   .append("&").append(JHH5AppConstants.req_sign).append("=").append(sign);
		System.out.println("聚合H5支付链接：" + url);
		
		return url.toString();
	}
	/**
	 * 参数签名
	 * @param merchant
	 * @param order
	 * @return
	 */
	private String getSign(JHH5MerchantConfig merchant, JHH5OrderConfig order) {
		SortedMap<String, String> params = new TreeMap<String, String>();
		
		params.put(JHH5AppConstants.req_merchantId, merchant.getMerId());
		params.put(JHH5AppConstants.req_customerId, order.getOrderUserId());
		params.put(JHH5AppConstants.req_merchantOrderId, order.getOrderId());
		params.put(JHH5AppConstants.req_appinfoId, merchant.getAppId());
		params.put(JHH5AppConstants.req_subjectNo, order.getSubjectNo());
		params.put(JHH5AppConstants.req_subject, order.getSubject());
		params.put(JHH5AppConstants.req_amount, order.getOrderAmount());
		params.put(JHH5AppConstants.req_payDirect, "");
		params.put(JHH5AppConstants.req_directPayType, "");
		params.put(JHH5AppConstants.req_notifyUrl, merchant.getNotifyUrl());
		params.put(JHH5AppConstants.req_returnUrl, merchant.getReturnUrl());
		
		System.out.println("聚合H5签名原文：" + params);
		
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
		System.out.println("聚合H5排序后原文：" + params);
		
		String paramData = sb.substring(0, sb.length() - 1).concat(merchant.getMd5Key());
		System.out.println("聚合H5待签名参数：" + paramData);
		
		String sign = Encrypt.MD5(paramData);
		System.out.println("聚合H5参数签名：" + sign);
		
		return sign;
	}
	/**
	 * 回调验签
	 * @param request
	 * @param md5Key
	 * @return
	 */
	public static boolean checkSign(Map<String, Object> request, String md5Key) {
		List<String> keys = new ArrayList<String>(request.keySet());
		Collections.sort(keys);
		
		StringBuffer prestr = new StringBuffer();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = String.valueOf(request.get(key));
			
			if (!JHH5AppConstants.callback_sign.equals(key) && !value.equals("null"))
				prestr.append(key).append("=").append(value).append("&");
		}
		String content = prestr.substring(0, prestr.length() - 1).concat(md5Key);
		String localSign = Encrypt.MD5(content);
		
		String callbackSign = request.get(JHH5AppConstants.callback_sign).toString();
		
		return callbackSign.equals(localSign);
	}
	/**
	 * 处理回调
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> callbackUtil(HttpServletRequest request) throws IOException {
		Map<String, Object> params = new HashMap<String, Object>();
		
		int contentLength = request.getContentLength();
		byte buffer[] = new byte[contentLength];
		for (int i = 0; i < contentLength;) {
			int readlen = request.getInputStream().read(buffer, i, contentLength - i);
			if (readlen == -1) break;
			i += readlen;
		}
		
		String json = new String(buffer, "UTF-8");
		System.out.println("聚合收银台回调json：" + json);
		
		JSONObject obj = JSONObject.fromObject(json);
		Iterator<String> keys = obj.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			params.put(key, obj.get(key));
		}
		
		return params;
	}
}