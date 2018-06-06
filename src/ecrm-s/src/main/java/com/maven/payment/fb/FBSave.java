package com.maven.payment.fb;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.maven.util.Encrypt;

public class FBSave {
	private static final String format = "json";
	private static final String sign_method = "md5";
	private static final String version = "1.0";
	/**
	 * 获取支付参数json
	 * @param merchant
	 * @param order
	 * @return
	 */
	public String getJsonData(FBMerchantConfig merchant, FBOrderConfig order) {
		String sign = getSign(merchant, order);
		
		// 业务参数
		StringBuffer content = new StringBuffer();
		content.append("{");
		
		content.append("\\\"").append(FBAppConstants.req_merchant_order_sn).append("\\\"");
		content.append(":");
		content.append("\\\"").append(order.getOrderId()).append("\\\"");
		
		content.append(",");
		
		content.append("\\\"").append(FBAppConstants.req_type).append("\\\"");
		content.append(":");
		content.append("").append(order.getOrderType()).append("");
		
		content.append(",");
		
		content.append("\\\"").append(FBAppConstants.req_total_fee).append("\\\"");
		content.append(":");
		content.append("").append(order.getOrderAmount()).append("");
		
		content.append("}");
		
		// 基本参数
		StringBuffer data = new StringBuffer();
		data.append("{");
		
		data.append("\"").append(FBAppConstants.req_app_id).append("\"");
		data.append(":");
		data.append("\"").append(merchant.getAppId()).append("\"");
		
		data.append(",");
		
		data.append("\"").append(FBAppConstants.req_method).append("\"");
		data.append(":");
		data.append("\"").append(merchant.getMethod()).append("\"");
		
		data.append(",");
		
		data.append("\"").append(FBAppConstants.req_format).append("\"");
		data.append(":");
		data.append("\"").append(format).append("\"");
		
		data.append(",");
		
		data.append("\"").append(FBAppConstants.req_version).append("\"");
		data.append(":");
		data.append("\"").append(version).append("\"");
		
		data.append(",");
		
		data.append("\"").append(FBAppConstants.req_sign_method).append("\"");
		data.append(":");
		data.append("\"").append(sign_method).append("\"");
		
		data.append(",");
		
		data.append("\"").append(FBAppConstants.req_nonce).append("\"");
		data.append(":");
		data.append("\"").append(order.getNonce()).append("\"");
		
		data.append(",");
		
		data.append("\"").append(FBAppConstants.req_biz_content).append("\"");
		data.append(":");
		data.append("\"").append(content.toString()).append("\"");
		
		data.append(",");
		
		data.append("\"").append(FBAppConstants.req_sign).append("\"");
		data.append(":");
		data.append("\"").append(sign).append("\"");
		
		
		data.append("}");
		
		System.out.println("付呗支付参数：" + data.toString());
		
		return data.toString();
	}
	/**
	 * 获取签名
	 * @param merchant
	 * @param order
	 * @return
	 */
	private String getSign(FBMerchantConfig merchant, FBOrderConfig order) {
		// 业务参数
		StringBuffer content = new StringBuffer();
		content.append("{");
		
		content.append("\"").append(FBAppConstants.req_merchant_order_sn).append("\"");
		content.append(":");
		content.append("\"").append(order.getOrderId()).append("\"");
		
		content.append(",");
		
		content.append("\"").append(FBAppConstants.req_type).append("\"");
		content.append(":");
		content.append("").append(order.getOrderType()).append("");
		
		content.append(",");
		
		content.append("\"").append(FBAppConstants.req_total_fee).append("\"");
		content.append(":");
		content.append("").append(order.getOrderAmount()).append("");
		
		content.append("}");
		
		SortedMap<String, String> params = new TreeMap<String, String>();
		params.put(FBAppConstants.req_app_id, merchant.getAppId());
		params.put(FBAppConstants.req_method, merchant.getMethod());
		params.put(FBAppConstants.req_format, format);
		params.put(FBAppConstants.req_version, version);
		params.put(FBAppConstants.req_sign_method, sign_method);
		params.put(FBAppConstants.req_nonce, order.getNonce());
		params.put(FBAppConstants.req_biz_content, content.toString());
		
		System.out.println("付呗支付参数未排序原文：" + params);
		
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
		String origin = sb.substring(0, sb.length() - 1);
		System.out.println("付呗支付参数排序后原文：" + origin);
		
		String paramStr = origin + merchant.getMd5Key();
		System.out.println("付呗支付MD5签名原文：" + paramStr);
		
		String sign = Encrypt.MD5(paramStr).toUpperCase();
		System.out.println("付呗支付MD5签名：" + sign);
		
		return sign;
	}
	/**
	 * 验签
	 * @param request
	 * @param md5Key
	 * @return
	 */
	public static boolean checkSign(Map<String, Object> request, String md5Key) {
		SortedMap<String, String> params = new TreeMap<String, String>();
		
		Set<String> keys = request.keySet();
		for (String key : keys) {
			if(!FBAppConstants.callback_sign.equals(key)
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
		String origin = sb.substring(0, sb.length() - 1);
		System.out.println("付呗支付回调参数排序后原文：" + origin);
		
		String paramStr = origin + md5Key;
		System.out.println("付呗支付回调MD5签名原文：" + paramStr);
		
		String sign = Encrypt.MD5(paramStr).toUpperCase();
		System.out.println("付呗支付回调MD5签名：" + sign);
		
		String callbackSign = request.get(FBAppConstants.callback_sign).toString();
		
		return sign.equals(callbackSign);
	}
}