package com.maven.payment.kk;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.maven.payment.kk.KKAppConstants.KK_ProductType;
import com.maven.util.Encrypt;
import com.maven.util.HttpPostUtil;
import com.maven.util.JSONUnit;

public class KKSave {

	public static String getOrderTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(new Date());
	}
	
	public String getUrl(KKMerchantConfig merchant, KKOrderConfig order) {
		String sign = getSign(merchant, order);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put(KKAppConstants.req_payKey, merchant.getPayKey());
		paramMap.put(KKAppConstants.req_orderPrice, order.getOrderPrice());
		paramMap.put(KKAppConstants.req_outTradeNo, order.getOutTradeNo());
		paramMap.put(KKAppConstants.req_productType, merchant.getProductType());
		paramMap.put(KKAppConstants.req_orderTime, order.getOrderTime());
		paramMap.put(KKAppConstants.req_productName, order.getProductName());
		paramMap.put(KKAppConstants.req_orderIp, order.getOrderIp());
		paramMap.put(KKAppConstants.req_returnUrl, merchant.getReturnUrl());
		paramMap.put(KKAppConstants.req_notifyUrl, merchant.getNotifyUrl());
		paramMap.put(KKAppConstants.req_sign, sign);
		
		if(KK_ProductType.微信D0公众号支付.value.equals(merchant.getProductType())
				|| KK_ProductType.微信T0公众号支付.value.equals(merchant.getProductType())
				|| KK_ProductType.微信T1公众号支付.value.equals(merchant.getProductType())) {
			paramMap.put(KKAppConstants.req_appId, merchant.getWXappid());
			paramMap.put(KKAppConstants.req_openId, order.getOpenid());
		}
		System.out.println("可可支付请求地址：" + merchant.getPayUrl());
		System.out.println("可可支付请求参数：" + paramMap);
		String result = HttpPostUtil.doPostSubmitMap(merchant.getPayUrl(), paramMap);
		System.out.println("请求返回结果：" + result);
		if(StringUtils.isNotBlank(result)) {
			Map<String, String> data = JSONUnit.getMapFromJsonNew(result);
			String resultCode = data.get(KKAppConstants.rep_resultCode);
			String payMessage = data.get(KKAppConstants.rep_payMessage);
			String errMsg     = data.get(KKAppConstants.rep_errMsg);
			String rep_sign   = data.get(KKAppConstants.rep_sign);
			if ("0000".equals(resultCode)) {
				StringBuffer sb = new StringBuffer();
				sb.append(KKAppConstants.rep_payMessage).append("=").append(payMessage).append("&")
				  .append(KKAppConstants.rep_resultCode).append("=").append(resultCode).append("&")
				  .append("paySecret=").append(merchant.getMd5Key());
				System.out.println(sb.toString());
				String rep_local_sign = Encrypt.MD5(sb.toString()).toUpperCase();
				if(rep_local_sign.equals(rep_sign)) return payMessage;
			} else {
				System.err.println("错误信息：" + errMsg);
			}
		}
		
		return "";
	}
	/**
	 * 
	 * @param merchant
	 * @param order
	 * @return
	 */
	private String getSign(KKMerchantConfig merchant, KKOrderConfig order) {
		SortedMap<String, String> params = new TreeMap<String, String>();
		
		params.put(KKAppConstants.req_payKey, merchant.getPayKey());
		params.put(KKAppConstants.req_orderPrice, order.getOrderPrice());
		params.put(KKAppConstants.req_outTradeNo, order.getOutTradeNo());
		params.put(KKAppConstants.req_productType, merchant.getProductType());
		params.put(KKAppConstants.req_orderTime, order.getOrderTime());
		params.put(KKAppConstants.req_productName, order.getProductName());
		params.put(KKAppConstants.req_orderIp, order.getOrderIp());
		params.put(KKAppConstants.req_returnUrl, merchant.getReturnUrl());
		params.put(KKAppConstants.req_notifyUrl, merchant.getNotifyUrl());
		
		if(KK_ProductType.微信D0公众号支付.value.equals(merchant.getProductType())
				|| KK_ProductType.微信T0公众号支付.value.equals(merchant.getProductType())
				|| KK_ProductType.微信T1公众号支付.value.equals(merchant.getProductType())) {
			params.put(KKAppConstants.req_appId, merchant.getWXappid());
			params.put(KKAppConstants.req_openId, order.getOpenid());
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
		System.out.println("可可支付参数排序后原文：" + params);
		
		String paramStr = sb.append("paySecret=").append(merchant.getMd5Key()).toString();
		System.out.println("可可支付MD5签名原文：" + paramStr);
		
		String sign = Encrypt.MD5(paramStr).toUpperCase();
		System.out.println("可可支付MD5签名：" + sign);
		
		return sign;
	}
	/**
	 * 验签
	 * @param params
	 * @return
	 */
	public static boolean checkSign(Map<String, Object> requestMap, String md5Key) {
		SortedMap<String, String> params = new TreeMap<String, String>();
		
		Set<String> keys = requestMap.keySet();
		for (String key : keys) {
			if(!KKAppConstants.callback_sign.equals(key) && requestMap.get(key) != null
					&& StringUtils.isNotBlank(requestMap.get(key).toString()))
				params.put(key, requestMap.get(key).toString());
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
		System.out.println("可可支付回调参数Md5签名原文：" + paramStr);
		
		String localSign = Encrypt.MD5(paramStr).toUpperCase();
		String callbackSign = requestMap.get(KKAppConstants.callback_sign).toString();
		return localSign.equals(callbackSign);
	}
	
	
	/*==================== 微信接口相关方法 ====================*/
	public static String getWXCodeUrl(KKMerchantConfig merchant, KKOrderConfig order) throws UnsupportedEncodingException {
// https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
		StringBuffer url = new StringBuffer(merchant.getWXcodeUrl());
		url.append("?appid=").append(merchant.getWXappid())
		   .append("&redirect_uri=").append(URLEncoder.encode(merchant.getWXcallbackUrl(), "UTF-8"))
		   .append("&response_type=").append("code")
		   .append("&scope=").append("snsapi_base")
		   .append("&state=").append(order.getOutTradeNo())
		   .append("#wechat_redirect");
		return url.toString();
	}
	
	public static String getOpenId(KKMerchantConfig merchant, String code) {
// https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
		StringBuffer url = new StringBuffer("https://api.weixin.qq.com/sns/oauth2/access_token");
		url.append("?appid=").append(merchant.getWXappid())
		   .append("&secret=").append(merchant.getWXappSecret())
		   .append("&code=").append(code)
		   .append("&grant_type=authorization_code");
		System.out.println(url);
		
		String result = HttpPostUtil.doGetSubmit(url.toString());
		
		Map<String, String> resultMap = JSONUnit.getMapFromJsonNew(result);
		
		return resultMap.get("openid");
	}
}