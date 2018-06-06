package com.maven.payment.ys.alipay;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.maven.payment.ys.SignUtils;

public class YSALIPaySave {
	private static final String charset = "UTF-8";
	private static final String sign_type = "RSA";
	private static final String version = "3.0";
	private static final String pay_mode = "native";
	private static final String bank_type = "1903000";
	
	public String getUrl(YSALIPayMerchantConfig merchant, YSALIPayOrderConfig order) throws Exception {
		String sign = getSign(merchant, order);
		
		StringBuffer url = new StringBuffer(merchant.getPayUrl());
		url.append("?").append(YSALIPayAppConstants.req_method).append("=").append(merchant.getMethod())
		   .append("&").append(YSALIPayAppConstants.req_partner_id).append("=").append(merchant.getMerId())
		   .append("&").append(YSALIPayAppConstants.req_timestamp).append("=").append(order.getOrderTime())
		   .append("&").append(YSALIPayAppConstants.req_charset).append("=").append(charset)
		   .append("&").append(YSALIPayAppConstants.req_sign_type).append("=").append(sign_type)
		   .append("&").append(YSALIPayAppConstants.req_notify_url).append("=").append(merchant.getNotifyUrl())
		   .append("&").append(YSALIPayAppConstants.req_return_url).append("=").append(merchant.getReturnUrl())
		   .append("&").append(YSALIPayAppConstants.req_version).append("=").append(version)
		   .append("&").append(YSALIPayAppConstants.req_out_trade_no).append("=").append(order.getOrderNo())
		   .append("&").append(YSALIPayAppConstants.req_subject).append("=").append(order.getOrderSubject())
		   .append("&").append(YSALIPayAppConstants.req_total_amount).append("=").append(order.getOrderAmount())
		   .append("&").append(YSALIPayAppConstants.req_seller_id).append("=").append(merchant.getMerId())
		   .append("&").append(YSALIPayAppConstants.req_seller_name).append("=").append(merchant.getMerName())
		   .append("&").append(YSALIPayAppConstants.req_timeout_express).append("=").append(merchant.getTimeout())
		   .append("&").append(YSALIPayAppConstants.req_pay_mode).append("=").append(pay_mode)
		   .append("&").append(YSALIPayAppConstants.req_bank_type).append("=").append(bank_type)
		   .append("&").append(YSALIPayAppConstants.req_business_code).append("=").append(merchant.getBusinessCode())
		   .append("&").append(YSALIPayAppConstants.req_sign).append("=").append(sign);
		System.out.println("银盛支付接口链接：" + url);
		
		return url.toString();
	}
	
	/**
	 * 参数签名
	 * @param merchant
	 * @param order
	 * @return
	 * @throws Exception
	 */
	private String getSign(YSALIPayMerchantConfig merchant, YSALIPayOrderConfig order) throws Exception {
		SortedMap<String, String> params = new TreeMap<String, String>();
		
		params.put(YSALIPayAppConstants.req_method, merchant.getMethod());
		params.put(YSALIPayAppConstants.req_partner_id, merchant.getMerId());
		params.put(YSALIPayAppConstants.req_timestamp, order.getOrderTime());
		params.put(YSALIPayAppConstants.req_charset, charset);
		params.put(YSALIPayAppConstants.req_sign_type, sign_type);
		params.put(YSALIPayAppConstants.req_notify_url, merchant.getNotifyUrl());
		params.put(YSALIPayAppConstants.req_return_url, merchant.getReturnUrl());
		params.put(YSALIPayAppConstants.req_version, version);
		params.put(YSALIPayAppConstants.req_out_trade_no, order.getOrderNo());
		params.put(YSALIPayAppConstants.req_subject, order.getOrderSubject());
		params.put(YSALIPayAppConstants.req_total_amount, order.getOrderAmount());
		params.put(YSALIPayAppConstants.req_seller_id, merchant.getMerId());
		params.put(YSALIPayAppConstants.req_seller_name, merchant.getMerName());
		params.put(YSALIPayAppConstants.req_timeout_express, merchant.getTimeout());
		params.put(YSALIPayAppConstants.req_pay_mode, pay_mode);
		params.put(YSALIPayAppConstants.req_bank_type, bank_type);
		params.put(YSALIPayAppConstants.req_business_code, merchant.getBusinessCode());
		
		System.out.println("银盛支付待签名参数：" + params);
		
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> es = params.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String k = entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		System.out.println("银盛支付参数排序后原文：" + sb);
		
		String content = sb.substring(0, sb.length() - 1);
		
		
		return SignUtils.rsaSign(content, merchant.getPrivateKeyUrl(), merchant.getPassword(), charset);
	}
	/**
	 * 验签
	 * @param request
	 * @param publicKeyUrl
	 * @return
	 * @throws FileNotFoundException 
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 * @throws SignatureException 
	 * @throws CertificateException 
	 * @throws InvalidKeyException 
	 */
	public static boolean checkSign(Map<String, Object> request, String publicKeyUrl)
		throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeyException,
			CertificateException, SignatureException, UnsupportedEncodingException {
		List<String> keys = new ArrayList<String>(request.keySet());
		Collections.sort(keys);
		
		StringBuffer prestr = new StringBuffer();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = String.valueOf(request.get(key));
			
			if (!YSALIPayAppConstants.callback_sign.equals(key)
				&& !value.equals("null") && StringUtils.isNotBlank(value)) {
				prestr.append(key).append("=").append(value).append("&");
			}
		}
		String content = prestr.substring(0, prestr.length() - 1);
		
		String sign = request.get(YSALIPayAppConstants.callback_sign).toString();
		
		return SignUtils.rsaCheckContent(content, sign, publicKeyUrl, charset);
	}
}