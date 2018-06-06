package com.maven.payment.lfqqpay;

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

public class LfQQPaySave {

	public String getUrl(LfQQPayMerchantConfig merchant, LfQQPayOrderConfig order)
			throws Exception {
		Map<String, String> params = getContentAndSign(merchant, order);

		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put(LfQQPayAppConstants.p1_partner, merchant.getMerNo());
		requestMap.put(LfQQPayAppConstants.p1_input_charset, CHART_SET);
		requestMap.put(LfQQPayAppConstants.p1_sign_type, SIGN_TYPE);
		requestMap.put(LfQQPayAppConstants.p1_sign, params.get("sign"));
		requestMap.put(LfQQPayAppConstants.p1_request_time, getRequestTime());
		requestMap.put(LfQQPayAppConstants.p1_content, params.get("content"));
		System.out.println("乐付QQ钱包接口 报头参数：" + requestMap);

		String result = HttpPostUtil.doPostSubmitMap(merchant.getPayUrl(), requestMap);
		System.out.println("乐付QQ钱包接口 支付请求结果：" + result);

		if (result == null || "-1".equals(result))
			return "";

		JSONObject returnData = JSONObject.fromObject(result);

		if (null != returnData && returnData.get(LfQQPayAppConstants.r1_is_succ).equals("T")) {
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
	        	if(returnData.has(LfQQPayAppConstants.r2_base64QRCode) && !returnData.optString(LfQQPayAppConstants.r2_base64QRCode).equals("")) {
	        		return returnData.optString(LfQQPayAppConstants.r2_base64QRCode);
	        	} else {
	        		return returnData.getString(LfQQPayAppConstants.r2_qq_pay_sm_url);
	        	}
	        	
//	        	return jsonObject.getString("wx_pay_sm_url");
//	        	return response;
	        	
	        }
		}
		return null;
	}

	// MD5 = 1f5e7a43dfe1fad176dfe38a13baa2c5
	/**
	 * QQ钱包支付:ali_pay
	 */
	private static final String SERVICE = "qq_pay";
	/**
	 * 编码格式:UTF-8
	 */
	private static final String CHART_SET = "UTF-8";
	/**
	 * 签名方式:MD5
	 */
	private static final String SIGN_TYPE = "SHA1WITHRSA";
	/**
	 * QQ钱包扫码:ali_sm
	 */
	private static final String QQ_PAY_TYPE = "qq_sm";

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
	private static Map<String, String> getContentAndSign(LfQQPayMerchantConfig merchant, LfQQPayOrderConfig order)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		SortedMap<String, String> params = new TreeMap<String, String>();
		params.put(LfQQPayAppConstants.p2_service, SERVICE);
		params.put(LfQQPayAppConstants.p2_partner, merchant.getMerNo());
		params.put(LfQQPayAppConstants.p2_out_trade_no, order.getOrderNo());
		params.put(LfQQPayAppConstants.p2_amount_str, order.getAmount());
		params.put(LfQQPayAppConstants.p2_qq_pay_type, QQ_PAY_TYPE);
		params.put(LfQQPayAppConstants.p2_subject, order.getSubject());
		params.put(LfQQPayAppConstants.p2_sub_body, order.getSubBody());
		params.put(LfQQPayAppConstants.p2_remark, order.getRemark());
		params.put(LfQQPayAppConstants.p2_return_url, merchant.getReturnUrl());

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
		System.out.println("乐付QQ钱包 业务参数原文：" + paramsStr);
		
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
	// 商户公钥：MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCfMI6iVKSVKA8VTSP81lU5Md4uuDLfSIe6yPGB/oK+gbZt9SMGG93NnlocNA0ZkrICnQjlMpqVZ4KNB3C1fI9X5KU7JcLKrmMJUpzLJEjlc4tXhPOdtNkpXGqjEi7+L/A9Ftio9aRudZkO+N7qCo2juJvDkhha/TPSWhp2qmQ8iwIDAQAB
	// 商户私钥：MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ8wjqJUpJUoDxVNI/zWVTkx3i64Mt9Ih7rI8YH+gr6Btm31IwYb3c2eWhw0DRmSsgKdCOUympVngo0HcLV8j1fkpTslwsquYwlSnMskSOVzi1eE85202SlcaqMSLv4v8D0W2Kj1pG51mQ743uoKjaO4m8OSGFr9M9JaGnaqZDyLAgMBAAECgYBWwvlKYkWdgcrOuk998zdZx+MnpX8ckQRg+joetp99w2axgWTIZJG8Cq1kJ75oDLr2j64UdUMpYNQXl1GHqM7ao+MbjkuKhNpwFHz8WpevIpbf+q4nXiREB6NP7qVjmJgsqj5F08f5dUFKa03bXTU+SS04AWAiskmtNjc5goxiCQJBAOSZ2nIDVq9YROR34ETByOojGTTeEHOLP2ccQG1dNZZLFjbRTVaixodXyujYlEzwzjbJhgGhzdDmnL89t9HRQUUCQQCyRPY7kBZexdBFUCLM+qr3NNZUSHsDSIXmUUMscHe0rHmHnc0PlK+2Y7o24e29NPkCewfMy1n9dlSTFTlNzJuPAkAVU40fidQyc4ep0M0pXPLDdRbK6dSbEuiNVANmsIVpSgP+hTQz2ueuhLA3XM0pCGYUkmRgDppG3NzOspFNj7oJAkEAhqm1X3UOkPTYySMpxrGsKCVPqkc+NVK5YywLlCw5INAQ1bMMTjLBMQPQjNjJ391+JLOPRtJt3hte8+RabzyaXQJBAI+AF9BEY1FVpbx6movUdYBKLCUbbziRtq4/pVIt6RX3OCkGTgsR+c7cakfz37EkAoa2+sZyKTR6sMgddcScCIQ= 
	// md5密钥：1ea3c7b82602817b14f07441601bc400
}