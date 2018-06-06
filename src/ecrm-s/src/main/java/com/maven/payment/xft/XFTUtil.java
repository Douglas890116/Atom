package com.maven.payment.xft;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.maven.payment.zft.SHAUtil;
import com.maven.payment.zft.ZFTAppConstants;
import com.maven.util.HttpPostUtil;

import net.sf.json.JSONObject;

public class XFTUtil {
	private static final String charset = "UTF-8";
	private static final String paymentType = "1";
	private static final String paymethod = "directPay";
	private static final String service = "online_pay";
	private static final String signType = "SHA";

	public String getUrl(XFTMerchantConfig merchant, XFTOrderConfig order) {
		String sign = getSHASign(merchant, order);
		
		StringBuffer url = new StringBuffer(merchant.getPayUrl());
		url.append(merchant.getMerId()).append("-").append(order.getOrderId());
		
		url.append("?").append(XFTAppConstants.req_charset).append("=").append(charset);
		url.append("&").append(XFTAppConstants.req_title).append("=").append("Recharge");
		url.append("&").append(XFTAppConstants.req_body).append("=").append("Recharge");
		url.append("&").append(XFTAppConstants.req_defaultbank).append("=").append(order.getBankCode());
		url.append("&").append(XFTAppConstants.req_isApp).append("=").append(order.getIsApp());
		url.append("&").append(XFTAppConstants.req_merchantId).append("=").append(merchant.getMerId());
		url.append("&").append(XFTAppConstants.req_notifyUrl).append("=").append(merchant.getNotifyUrl());
		url.append("&").append(XFTAppConstants.req_orderNo).append("=").append(order.getOrderId());
		url.append("&").append(XFTAppConstants.req_paymentType).append("=").append(paymentType);
		url.append("&").append(XFTAppConstants.req_paymethod).append("=").append(paymethod);
		url.append("&").append(XFTAppConstants.req_returnUrl).append("=").append(merchant.getReturnUrl());
		url.append("&").append(XFTAppConstants.req_service).append("=").append(service);
		url.append("&").append(XFTAppConstants.req_totalFee).append("=").append(order.getOrderAmount());
		url.append("&").append(XFTAppConstants.req_signType).append("=").append(signType);
		url.append("&").append(XFTAppConstants.req_sign).append("=").append(sign);
		System.out.println("信付通支付链接：\n" + url);
		
		if (order.getIsApp().equals("app")) {
			
			String urlStr = url.toString();
			String baseUrl = urlStr.split("[?]")[0];
			String query = urlStr.split("[?]")[1];
			String[] params = query.split("&");
			Map<String, String> paramsMap = new HashMap<String, String>();
			
			for (String param : params) {
				if (param.split("=").length > 1)
					paramsMap.put(param.split("=")[0], param.split("=")[1]);
			}
			System.out.println("支付地址：" + baseUrl);
			System.out.println("支付参数：" + paramsMap);
			String result = HttpPostUtil.doPostSubmitMap3(baseUrl, paramsMap);
			// {"codeUrl":"weixin://wxpay/bizpayurl?pr=cCI94xT","respCode":"S0001"}
			if (StringUtils.isNotBlank(result)) {
				System.out.println("信付通扫码支付请求结果：" + result);
				JSONObject json = JSONObject.fromObject(result);
				String status = json.getString(XFTAppConstants.rep_respCode);
				if ("S0001".equals(status)) {
					return json.getString(XFTAppConstants.rep_codeUrl);
				} else {
					System.err.println("请求出错：" + json.getString(XFTAppConstants.rep_respMessage));
				}
			}
		}
		return url.toString();
	}
	
	private String getSHASign(XFTMerchantConfig merchant, XFTOrderConfig order) {
		SortedMap<String, String> params = new TreeMap<String, String>();
		
		params.put(XFTAppConstants.req_charset, charset);
		params.put(XFTAppConstants.req_title, "Recharge");
		params.put(XFTAppConstants.req_body, "Recharge");
		params.put(XFTAppConstants.req_defaultbank, order.getBankCode());
		params.put(XFTAppConstants.req_isApp, order.getIsApp());
		params.put(XFTAppConstants.req_merchantId, merchant.getMerId());
		params.put(XFTAppConstants.req_notifyUrl, merchant.getNotifyUrl());
		params.put(XFTAppConstants.req_orderNo, order.getOrderId());
		params.put(XFTAppConstants.req_paymentType, paymentType);
		params.put(XFTAppConstants.req_paymethod, paymethod);
		params.put(XFTAppConstants.req_returnUrl, merchant.getReturnUrl());
		params.put(XFTAppConstants.req_service, service);
		params.put(XFTAppConstants.req_totalFee, order.getOrderAmount());
		System.out.println("信付通参数未排序原文：" + params);
		
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> es = params.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String k = entry.getKey();
			String v = entry.getValue();
			if (StringUtils.isNotBlank(k) && StringUtils.isNotBlank(v)) {
				sb.append(k + "=" + v + "&");
			}
		}
		
		String paramData = sb.substring(0, sb.length() - 1).concat(merchant.getMerKey());
		System.out.println("信付通签名参数原文：" + paramData);
		
		String sign = SHAUtil.sign(paramData, charset).toUpperCase();
		System.out.println("信付通签名参数签名：" + sign);
		
		return sign;
	}
	
	/**
	 * 验签
	 * @param request
	 * @param shaKey
	 * @return
	 */
	public static boolean checkSign(Map<String, Object> request, String shaKey) {
		SortedMap<String, String> params = new TreeMap<String, String>();
		
		Set<String> keys = request.keySet();
		for (String key : keys) {
			if (StringUtils.isNotBlank(key) 
					&& !key.equals(XFTAppConstants.callback_sign) && !key.equals(XFTAppConstants.callback_signType)
					&& request.get(key) != null && StringUtils.isNotBlank(request.get(key).toString())) {
				params.put(key, request.get(key).toString());
			}
		}
		
		System.out.println("信付通参数未排序原文：" + params);
		
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> es = params.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}
		System.out.println("信付通回调参数排序后原文：" + sb);
		
		String paramData = sb.substring(0, sb.length() - 1).concat(shaKey);
		System.out.println("信付通回调待签名参数：" + paramData);
		
		String sign = SHAUtil.sign(paramData, charset).toUpperCase();
		System.out.println("信付通参数签名：" + sign);
		
		String callbackSign = request.get(ZFTAppConstants.callback_sign).toString();
		
		return sign.equals(callbackSign);
	}
}