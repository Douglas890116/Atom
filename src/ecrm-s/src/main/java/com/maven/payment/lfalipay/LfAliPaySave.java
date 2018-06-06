package com.maven.payment.lfalipay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.maven.payment.lfwx.RSACoderUtil;
import com.maven.util.HttpPostUtil;

import net.sf.json.JSONObject;

public class LfAliPaySave {

	public String getUrl(LfAliPayMerchantConfig merchant, LfAliPayOrderConfig order)
			throws Exception {
		Map<String, String> params = getContentAndSign(merchant, order);

		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put(LfAliPayAppConstants.p1_partner, merchant.getMerNo());
		requestMap.put(LfAliPayAppConstants.p1_input_charset, CHART_SET);
		requestMap.put(LfAliPayAppConstants.p1_sign_type, SIGN_TYPE);
		requestMap.put(LfAliPayAppConstants.p1_sign, params.get("sign"));
		requestMap.put(LfAliPayAppConstants.p1_request_time, getRequestTime());
		requestMap.put(LfAliPayAppConstants.p1_content, params.get("content"));
		System.out.println("乐付支付宝接口 报头参数：" + requestMap);

		String result = HttpPostUtil.doPostSubmitMap(merchant.getPayUrl(), requestMap);
		System.out.println("乐付支付宝接口 支付请求结果：" + result);

		if (result == null || "-1".equals(result))
			return "";

		JSONObject returnData = JSONObject.fromObject(result);

		if (null != returnData && returnData.get(LfAliPayAppConstants.r1_is_succ).equals("T")) {
			String p2_content = returnData.getString("response");
//			String p2_charset = returnData.getString("charset");
			String return_sign = returnData.getString("sign");
			
	        byte[] responseByte = java.util.Base64.getDecoder().decode(p2_content);
			String response = new String(RSACoderUtil.decryptByPrivateKey(responseByte, merchant.getMerPrivateKey()));
			
			System.out.println("response="+response);
			System.out.println("return_sign="+return_sign);
			
	        boolean verfiy = RSACoderUtil.verify(response.getBytes() , merchant.getPublicKey(), return_sign);
	        
	        if(verfiy) {
	        	
//	        	wx_pay_sm_url String 1024 否 微信⽀付识别⼆维码URL
//	        	wx_pay_hx_url String 1024 是 微信⽀付唤醒app⽀付url
//	        	base64QRCode String 2048 是微信⽀付⼆维码图⽚( 优先使⽤该返回，如果不存在则使⽤wx_pay_sm_url)
	        	
	        	System.out.println(response);
	        	returnData = JSONObject.fromObject(response);
	        	if(returnData.has(LfAliPayAppConstants.r2_base64QRCode) && !returnData.optString(LfAliPayAppConstants.r2_base64QRCode).equals("")) {
	        		return returnData.optString(LfAliPayAppConstants.r2_base64QRCode);
	        	} else {
	        		return returnData.getString(LfAliPayAppConstants.r2_ali_pay_sm_url);
	        	}
	        	
//	        	return jsonObject.getString("wx_pay_sm_url");
//	        	return response;
	        	
	        }
		}
		return null;
	}

	// MD5 = 1f5e7a43dfe1fad176dfe38a13baa2c5
	/**
	 * 支付宝支付:ali_pay
	 */
	private static final String SERVICE = "ali_pay";
	/**
	 * 编码格式:UTF-8
	 */
	private static final String CHART_SET = "UTF-8";
	/**
	 * 签名方式:MD5
	 */
	private static final String SIGN_TYPE = "SHA1WITHRSA";
	/**
	 * 支付宝扫码:ali_sm
	 */
	private static final String ALI_PAY_TYPE = "ali_sm";

	/**
	 * 获取订单时间
	 * 
	 * @return
	 */
	private static String getRequestTime() {
		SimpleDateFormat format = new SimpleDateFormat("YYMMddHHmmss");
		return format.format(new Date());
	}

	/**
	 * 对业务参数进行处理
	 * 
	 * @param merchant
	 * @param order
	 * @return
	 * @throws Exception 
	 */
	private static Map<String, String> getContentAndSign(LfAliPayMerchantConfig merchant, LfAliPayOrderConfig order)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		SortedMap<String, String> params = new TreeMap<String, String>();
		params.put(LfAliPayAppConstants.p2_service, SERVICE);
		params.put(LfAliPayAppConstants.p2_partner, merchant.getMerNo());
		params.put(LfAliPayAppConstants.p2_out_trade_no, order.getOrderNo());
		params.put(LfAliPayAppConstants.p2_amount_str, order.getAmount());
		params.put(LfAliPayAppConstants.p2_ali_pay_type, ALI_PAY_TYPE);
		params.put(LfAliPayAppConstants.p2_subject, order.getSubject());
		params.put(LfAliPayAppConstants.p2_sub_body, order.getSubBody());
		params.put(LfAliPayAppConstants.p2_remark, order.getRemark());
		params.put(LfAliPayAppConstants.p2_return_url, merchant.getReturnUrl());

		Set<Entry<String, String>> es = params.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String k = (String) entry.getKey();
			String v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		String paramsStr = sb.substring(0, sb.length() - 1);
		System.out.println("乐付支付宝 业务参数原文：" + paramsStr);
		
		String content =  RSACoderUtil.getParamsWithDecodeByPublicKey(paramsStr, CHART_SET, merchant.getPublicKey());
		System.out.println("业务参数密文："+content);
		
		String sign = RSACoderUtil.sign(paramsStr.getBytes(CHART_SET), merchant.getMerPrivateKey());//对原文签名
		System.out.println("业务参数签名："+sign);
		
		Map<String, String> result = new HashMap<String, String>();
		result.put("content", content);
		result.put("sign", sign);
		
		return result;
	}
	// 平台公钥：MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMcRww57ecNBtZ1Wu1clzkNgNl97MGqUQ8ECVLys5WX30bfeRBpaJCX2x9Ia8EqI99QTDJjrwvnpOIFNo3qkNaL2L73PYo++bFUDVLpyUGK6R5eT7Z2gt8DY904Vni5ZRFeS7kn9a+dmHUcX9t2rXCpfrks5YQ6B91hcH4vX6eLwIDAQAB
	// 商户公钥：MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCtg/GN8y5BJpiW4rlOGHKc7XktpJ5U0cbMeAND8UnQbF+nRwZ6iKWJ5WDVYLaiF4Gy03rMn3TG02Js8pfROVWZzXNQJggUjlYTxDJUfBcTZIh25Q424zmtVO1JxiU8LuEGar63qOm4Fhb6AdJO1vInk9OSnyCWRqebSujzbAO3AwIDAQAB
	// 商户私钥：MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAK2D8Y3zLkEmmJbiuU4YcpzteS2knlTRxsx4A0PxSdBsX6dHBnqIpYnlYNVgtqIXgbLTesyfdMbTYmzyl9E5VZnNc1AmCBSOVhPEMlR8FxNkiHblDjbjOa1U7UnGJTwu4QZqvreo6bgWFvoB0k7W8ieT05KfIJZGp5tK6PNsA7cDAgMBAAECgYBiBkhQiTF4XPWXSD6nf+g//2iWAInyIRDOMn6lw4qP1Km1NsrSLqBWZt9trFWA/QaooBCfyPWP3ZudMO/TozBX43W5vGmuiDtjADFY8u6JhNQF+0uMqu22YEiOGN9WXdaWYkuvvTvKiUynS3XWuafgf1t23ec5GsepCInpZgimAQJBAO3yP50zQMmjSiOtkW9Fb559TWKuAg5mmVSF1sLauwzKO8pP6Y/J2jY/lcFyByeyfSd3cUw7tBvlrSTjKf1BIhkCQQC6rjitwiphD9x3AH6mi02RnxyR9tSl9LNvQ4Fij+AS9CKEUXlLDMjRvdTtpNsouyuXF2KkVOd2pSPNm62qJ517AkEA0mSM0xbqhmAXwgxKuDSRSXZJ4qMxtkIJ+a6OAqUdF2YKEA0w268Df0whwnZs1TEBcNAwIdP4oWIoAqHKEuBwoQJBAJJ//PznfmiTkPlWyw2aDrK0AjDOWw0t3s73VAdsT3WwX5IleiGak9J9vicNE+yADnJRKNk7xDXI3TMS9BOvaRcCQQCeTOLqjX4HhJmCAoYmgjNAsnMUIG758sMZa24qCU0NxHHyiaux+hS12YqHBb9VTJtgd+FjFkJrsF+8OsBIS0fH
	// md5密钥：1f5e7a43dfe1fad176dfe38a13baa2c5
}