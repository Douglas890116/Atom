package com.maven.payment.zft;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.maven.util.HttpPostUtil;

import net.sf.json.JSONObject;
/**
 * 支付通存款类
 * @author klay
 *
 */
public class ZFTSave {
	
	private static final String SERVICE = "online_pay";
	private static final String CHARSET = "UTF-8";
	private static final String SIGNTYPE = "SHA";
	private static final String PAYMENTTYPE = "1";

	public String getUrl(ZFTMerchantConfig merchant, ZFTOrderConfig order) {
		String sign = getSign(merchant, order);
		
		StringBuffer url = new StringBuffer(merchant.getPayUrl());
		url.append(merchant.getMerId()).append("-").append(order.getOrderNo());
		
		url.append("?").append(ZFTAppConstants.req_service).append("=").append(SERVICE);
		url.append("&").append(ZFTAppConstants.req_merchantId).append("=").append(merchant.getMerId());
		url.append("&").append(ZFTAppConstants.req_notifyUrl).append("=").append(merchant.getNotifyUrl());
		url.append("&").append(ZFTAppConstants.req_returnUrl).append("=").append(merchant.getReturnUrl());
		url.append("&").append(ZFTAppConstants.req_sign).append("=").append(sign);
		url.append("&").append(ZFTAppConstants.req_signType).append("=").append(SIGNTYPE);
		url.append("&").append(ZFTAppConstants.req_charset).append("=").append(CHARSET);
		url.append("&").append(ZFTAppConstants.req_title).append("=").append(order.getOrderTitle());
		url.append("&").append(ZFTAppConstants.req_body).append("=").append(order.getOrderBody());
		url.append("&").append(ZFTAppConstants.req_orderNo).append("=").append(order.getOrderNo());
		url.append("&").append(ZFTAppConstants.req_totalFee).append("=").append(order.getOrderAmount());
		url.append("&").append(ZFTAppConstants.req_paymentType).append("=").append(PAYMENTTYPE);
		url.append("&").append(ZFTAppConstants.req_defaultbank).append("=").append(order.getOrderBank());
		url.append("&").append(ZFTAppConstants.req_paymethod).append("=").append(merchant.getPayType());
		url.append("&").append(ZFTAppConstants.req_isApp).append("=").append(order.getIsApp());
		url.append("&").append(ZFTAppConstants.req_sellerEmail).append("=").append(merchant.getMerEmail());

		if ("H5".equals(order.getIsApp())) {
			url.append("&").append(ZFTAppConstants.req_userIp).append("=").append(order.getUserIP());
			url.append("&").append(ZFTAppConstants.req_appName).append("=").append(merchant.getAppName());
			url.append("&").append(ZFTAppConstants.req_appMsg).append("=").append(order.getAppMsg());
			url.append("&").append(ZFTAppConstants.req_appType).append("=").append(order.getAppType());
			url.append("&").append(ZFTAppConstants.req_backUrl).append("=").append(merchant.getReturnUrl());
		}
		
		System.out.println("支付通直接链接：\n" + url.toString());
		
		if ("ALIPAY".equals(order.getOrderBank())
			|| "WXPAY".equals(order.getOrderBank())
			|| "QQPAY".equals(order.getOrderBank())) {
			
			String urlStr = url.toString();
			String baseUrl = urlStr.split("[?]")[0];
			String query = urlStr.split("[?]")[1];
			String[] params = query.split("&");
			Map<String, String> paramsMap = new HashMap<String, String>();
			
			for (String param : params) {
				if (param.split("=").length > 1)
					paramsMap.put(param.split("=")[0], param.split("=")[1]);
			}
			
			String result = HttpPostUtil.doPostSubmitMap(baseUrl, paramsMap);
			// {"codeUrl":"weixin://wxpay/bizpayurl?pr=cCI94xT","respCode":"S0001"}
			if (StringUtils.isNotBlank(result)) {
				System.out.println("支付通微信、支付宝、QQ钱包请求结果：" + result);
				JSONObject json = JSONObject.fromObject(result);
				String status = json.getString("respCode");
				String QRUrl = json.getString("codeUrl");
				if ("S0001".equals(status)) {
					return QRUrl;
				} else {
					System.err.println("请求出错");
				}
			}
		}
		
		return url.toString();
	}
	/**
	 * 获取支付签名
	 * @param merchant
	 * @param order
	 * @return
	 */
	private String getSign(ZFTMerchantConfig merchant, ZFTOrderConfig order) {
		SortedMap<String, String> params = new TreeMap<String, String>();
		
		params.put(ZFTAppConstants.req_service, SERVICE);
		params.put(ZFTAppConstants.req_merchantId, merchant.getMerId());
		params.put(ZFTAppConstants.req_notifyUrl, merchant.getNotifyUrl());
		params.put(ZFTAppConstants.req_returnUrl, merchant.getReturnUrl());
//		params.put(ZFTAppConstants.req_sign, sign);
//		params.put(ZFTAppConstants.req_signType, SIGNTYPE);
		params.put(ZFTAppConstants.req_charset, CHARSET);
		params.put(ZFTAppConstants.req_title, order.getOrderTitle());
		params.put(ZFTAppConstants.req_body, order.getOrderBody());
		params.put(ZFTAppConstants.req_orderNo, order.getOrderNo());
		params.put(ZFTAppConstants.req_totalFee, order.getOrderAmount());
		params.put(ZFTAppConstants.req_paymentType, PAYMENTTYPE);
		params.put(ZFTAppConstants.req_defaultbank, order.getOrderBank());
		params.put(ZFTAppConstants.req_paymethod, merchant.getPayType());
		params.put(ZFTAppConstants.req_isApp, order.getIsApp());
		params.put(ZFTAppConstants.req_sellerEmail, merchant.getMerEmail());

		if ("H5".equals(order.getIsApp())) {
			params.put(ZFTAppConstants.req_userIp, order.getUserIP());
			params.put(ZFTAppConstants.req_appName, merchant.getAppName());
			params.put(ZFTAppConstants.req_appMsg, order.getAppMsg());
			params.put(ZFTAppConstants.req_appType, order.getAppType());
			params.put(ZFTAppConstants.req_backUrl, merchant.getReturnUrl());
		}
		
		
		System.out.println("支付通参数未排序原文：" + params);
		
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
		System.out.println("支付通参数排序后原文：" + params);
		
		String paramData = sb.substring(0, sb.length() - 1).concat(merchant.getShaKey());
		System.out.println("支付通待签名参数：" + paramData);
		
		String sign = SHAUtil.sign(paramData, CHARSET).toUpperCase();
		System.out.println("支付通参数签名：" + sign);
		
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
					&& !key.equals(ZFTAppConstants.callback_sign) && !key.equals(ZFTAppConstants.callback_signType)
					&& request.get(key) != null && StringUtils.isNotBlank(request.get(key).toString())) {
				params.put(key, request.get(key).toString());
			}
		}
		
		System.out.println("支付通参数未排序原文：" + params);
		
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
		System.out.println("支付通回调参数排序后原文：" + params);
		
		String paramData = sb.substring(0, sb.length() - 1).concat(shaKey);
		System.out.println("支付通回调待签名参数：" + paramData);
		
		String sign = SHAUtil.sign(paramData, CHARSET).toUpperCase();
		System.out.println("支付通参数签名：" + sign);
		
		String callbackSign = request.get(ZFTAppConstants.callback_sign).toString();
		
		return sign.equals(callbackSign);
	}
}