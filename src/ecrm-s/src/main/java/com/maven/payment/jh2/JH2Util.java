package com.maven.payment.jh2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.maven.util.Encrypt;
import com.maven.util.HttpPostUtil;
import com.maven.util.RandomString;

import net.sf.json.JSONObject;

/**
 * 神奇支付
 * @author zack
 *
 */
public class JH2Util {
	/**
	 * 生成神奇支付的订单号
	 * @return
	 */
	public static String getOrderNo() {
		return RandomString.UUID().substring(2);
	}
	/**
	 * 获取支付链接
	 * @param merchant
	 * @param order
	 * @return
	 */
	public String getUrl(JH2MerchantConfig merchant, JH2OrderConfig order) {
		String sign = getSign(merchant, order);

		if (merchant.getPayUrl().contains("b2cPay")) {
			StringBuffer url = new StringBuffer(merchant.getPayUrl());
			url.append("?").append(JH2AppConstants.req_payKey).append("=").append(merchant.getPayKey());
			url.append("&").append(JH2AppConstants.req_payKey).append("=").append(merchant.getPayKey());
			url.append("&").append(JH2AppConstants.req_orderPrice).append("=").append(order.getOrderAmount());
			url.append("&").append(JH2AppConstants.req_outTradeNo).append("=").append(order.getOrderNo());
			url.append("&").append(JH2AppConstants.req_productType).append("=").append(merchant.getProductType());
			url.append("&").append(JH2AppConstants.req_orderTime).append("=").append(order.getOrderTime());
			url.append("&").append(JH2AppConstants.req_productName).append("=").append(order.getProductName());
			url.append("&").append(JH2AppConstants.req_orderIp).append("=").append(order.getOrderIP());
			url.append("&").append(JH2AppConstants.req_bankCode).append("=").append(order.getBankCode());
			url.append("&").append(JH2AppConstants.req_bankAccountType).append("=").append(order.getBankAccountType());
			url.append("&").append(JH2AppConstants.req_returnUrl).append("=").append(merchant.getReturnUrl());
			url.append("&").append(JH2AppConstants.req_notifyUrl).append("=").append(merchant.getNotifyUrl());
			url.append("&").append(JH2AppConstants.req_remark).append("=").append(order.getRemark());
			url.append("&").append(JH2AppConstants.req_mobile).append("=").append(order.getMobile());
			url.append("&").append(JH2AppConstants.req_sign).append("=").append(sign);
			System.out.println("神奇支付链接：" + url);
			
			return url.toString();
		} else {
			Map<String, String> params = new HashMap<String, String>();
			params.put(JH2AppConstants.req_payKey, merchant.getPayKey());
			params.put(JH2AppConstants.req_orderPrice, order.getOrderAmount());
			params.put(JH2AppConstants.req_outTradeNo, order.getOrderNo());
			params.put(JH2AppConstants.req_productType, merchant.getProductType());
			params.put(JH2AppConstants.req_orderTime, order.getOrderTime());
			params.put(JH2AppConstants.req_productName, order.getProductName());
			params.put(JH2AppConstants.req_orderIp, order.getOrderIP());
			params.put(JH2AppConstants.req_bankCode, order.getBankCode());
			params.put(JH2AppConstants.req_bankAccountType, order.getBankAccountType());
			params.put(JH2AppConstants.req_returnUrl, merchant.getReturnUrl());
			params.put(JH2AppConstants.req_notifyUrl, merchant.getNotifyUrl());
			params.put(JH2AppConstants.req_remark, order.getRemark());
			params.put(JH2AppConstants.req_mobile, order.getMobile());
			params.put(JH2AppConstants.req_sign, sign);
			System.out.println("神奇请求参数：" + params);

			String result = HttpPostUtil.doPostSubmitMap3(merchant.getPayUrl(), params);
			System.out.println("神奇请求结果：" + result);

			if (StringUtils.isNotBlank(result)) {
				JSONObject jsonObject = JSONObject.fromObject(result);
				String resultCode = jsonObject.getString(JH2AppConstants.rep_resultCode);
				String payMessage = jsonObject.getString(JH2AppConstants.rep_payMessage);
				String errMsg = jsonObject.getString(JH2AppConstants.rep_errMsg);
				if ("0000".equals(resultCode)) return payMessage;
				System.err.println("神奇获取支付信息失败：" + errMsg);
			}
		}
		return null;
	}
	
	/**
	 * 获取签名
	 * @param merchant
	 * @param order
	 * @return
	 */
	private String getSign(JH2MerchantConfig merchant, JH2OrderConfig order) {
		SortedMap<String, String> params = new TreeMap<String, String>();
		
		params.put(JH2AppConstants.req_payKey, merchant.getPayKey());
		params.put(JH2AppConstants.req_orderPrice, order.getOrderAmount());
		params.put(JH2AppConstants.req_outTradeNo, order.getOrderNo());
		params.put(JH2AppConstants.req_productType, merchant.getProductType());
		params.put(JH2AppConstants.req_orderTime, order.getOrderTime());
		params.put(JH2AppConstants.req_productName, order.getProductName());
		params.put(JH2AppConstants.req_orderIp, order.getOrderIP());
		params.put(JH2AppConstants.req_bankCode, order.getBankCode());
		params.put(JH2AppConstants.req_bankAccountType, order.getBankAccountType());
		params.put(JH2AppConstants.req_returnUrl, merchant.getReturnUrl());
		params.put(JH2AppConstants.req_notifyUrl, merchant.getNotifyUrl());
		params.put(JH2AppConstants.req_remark, order.getRemark());
		params.put(JH2AppConstants.req_mobile, order.getMobile());
		System.out.println("神奇未排序签名参数：" + params);
		
		Set<Entry<String, String>> set = params.entrySet();
		Iterator<Entry<String, String>> iterator = set.iterator();
		Entry<String, String> entry = null;
		StringBuffer sb = new StringBuffer();
		while (iterator.hasNext()) {
			entry = iterator.next();
			String k = entry.getKey();
			String v = entry.getValue();
			if (StringUtils.isNotBlank(v)) {
				sb.append(k).append("=").append(v).append("&");
			}
		}
		sb.append("paySecret=").append(merchant.getMerKey());
		System.out.println("神奇待签名参数原文：" + sb);
		
		String sign = Encrypt.MD5(sb.toString()).toUpperCase();
		System.out.println("神奇参数签名：" + sign);
		
		return sign;
	}
	/**
	 * 验签
	 * @param request
	 * @param merKey
	 * @return
	 */
	public static boolean checkSign(Map<String, Object> request, String merKey) {
		List<String> keys = new ArrayList<String>(request.keySet());
		Collections.sort(keys);
		
		StringBuffer sb = new StringBuffer();
		for (String key : keys) {
			if (!key.equals(JH2AppConstants.callback_sign) && request.get(key) != null
					&& StringUtils.isNotBlank(request.get(key).toString())) {
				sb.append(key).append("=").append(request.get(key)).append("&");
			}
		}
		sb.append("paySecret=").append(merKey);
		System.out.println("神奇回调待签名参数原文：" + sb);
		
		String localSign = Encrypt.MD5(sb.toString()).toUpperCase();
		String callbackSign = request.get(JH2AppConstants.callback_sign).toString().toUpperCase();
		
		return localSign.equals(callbackSign);
	}
	
	public static String getOrderTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(new Date());
	}
}