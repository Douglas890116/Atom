package com.maven.payment.dd;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.maven.util.Encrypt;

import net.sf.json.JSONObject;

public class DDSave {
	private static final String version = "V001";
	
	public String getJsonData(DDMerchantConfig merchant, DDOrderConfig order) {
		String sign = getSign(merchant, order);
		
		JSONObject json = new JSONObject();
		json.put(DDAppConstants.req_amount, order.getAmount());
		json.put(DDAppConstants.req_back_end_url, merchant.getNotifyUrl());
		json.put(DDAppConstants.req_code, merchant.getMerCode());
		json.put(DDAppConstants.req_commodity_name, order.getName());
		json.put(DDAppConstants.req_merchant_no, order.getOrderNo());
		json.put(DDAppConstants.req_pay_type, merchant.getPayType());
		json.put(DDAppConstants.req_return_url, merchant.getReturnUrl());
		json.put(DDAppConstants.req_version, version);
		json.put(DDAppConstants.req_context, sign);
		
		return json.toString();
	}

	private String getSign(DDMerchantConfig merchant, DDOrderConfig order) {
		SortedMap<String, String> params = new TreeMap<String, String>();
		
		params.put(DDAppConstants.req_amount, order.getAmount());
		params.put(DDAppConstants.req_back_end_url, merchant.getNotifyUrl());
		params.put(DDAppConstants.req_code, merchant.getMerCode());
		params.put(DDAppConstants.req_commodity_name, order.getName());
		params.put(DDAppConstants.req_merchant_no, order.getOrderNo());
		params.put(DDAppConstants.req_pay_type, merchant.getPayType());
		params.put(DDAppConstants.req_return_url, merchant.getReturnUrl());
		params.put(DDAppConstants.req_version, version);
		params.put(DDAppConstants.md5Key, merchant.getMd5Key());
		
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> se = params.entrySet();
		Iterator<Entry<String, String>> ie = se.iterator();
		while (ie.hasNext()) {
			Entry<String, String> entry = ie.next();
			String k = entry.getKey();
			String v = entry.getValue();
			sb.append(k).append("=").append(v).append("&");
			
		}
		String data = sb.substring(0, sb.length() - 1);
		System.out.println("点点支付签名原文：" + data);
		
		String sign = Encrypt.MD5(data);
		
		System.out.println("点点支付签名：" + sign);
		
		return sign;
	}
	
	public static boolean checkCallbackSign(Map<String, String> request, String merCode, String md5Key) {
		
		SortedMap<String, String> params = new TreeMap<String, String>();
		Set<String> keys = request.keySet();
		for (String key : keys) {
			if(!DDAppConstants.callback_sign.equals(key)) {
				params.put(key, request.get(key).toString());
			}
		}
		params.put(DDAppConstants.req_code, merCode);
		params.put(DDAppConstants.md5Key, md5Key);
		
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> se = params.entrySet();
		Iterator<Entry<String, String>> ie = se.iterator();
		while (ie.hasNext()) {
			Entry<String, String> entry = ie.next();
			String k = entry.getKey();
			String v = entry.getValue();
			sb.append(k).append("=").append(v).append("&");
			
		}
		String data = sb.substring(0, sb.length() - 1);
		System.out.println("点点回调签名原文：" + data);
		
		String localSign = Encrypt.MD5(data);
		System.out.println("点点回调本地签名：" + localSign);
		
		String callbackSign = request.get(DDAppConstants.callback_sign).toString();
		System.out.println("点点回调参数签名：" + callbackSign);
		
		return localSign.equals(callbackSign);
	}
	
	public String getPayOutJsonData(DDMerchantConfig merchant, DDOrderConfig order) {
		String context = getPayOutSign(merchant, order);
		
		JSONObject json = new JSONObject();
		json.put(DDAppConstants.req_version, version);
		json.put(DDAppConstants.req_code, merchant.getMerCode());
		json.put(DDAppConstants.req_context, context);
		json.put(DDAppConstants.req_pay_type, merchant.getPayType());
		json.put(DDAppConstants.df_is_compay, "0");
		json.put(DDAppConstants.req_amount, order.getAmount());
		json.put(DDAppConstants.df_product_id, "0201");
		json.put(DDAppConstants.req_merchant_no, order.getOrderNo());
		json.put(DDAppConstants.df_customer_phone, order.getCustomerPhone());
		json.put(DDAppConstants.df_customer_name, order.getCustomerName());
		json.put(DDAppConstants.df_customer_cert_type, order.getCustomerCertType());
		json.put(DDAppConstants.df_customer_cert_id, order.getCustomerCertId());
		json.put(DDAppConstants.df_bank_no, order.getBankNo());
		json.put(DDAppConstants.df_bank_name, order.getBankName());
		json.put(DDAppConstants.df_bank_card_no, order.getBankCardNo());
		return json.toString();
	}
	
	private String getPayOutSign(DDMerchantConfig merchant, DDOrderConfig order) {
		SortedMap<String, String> params = new TreeMap<String, String>();
		
		params.put(DDAppConstants.req_version, version);
		params.put(DDAppConstants.req_code, merchant.getMerCode());
		params.put(DDAppConstants.req_pay_type, merchant.getPayType());
		params.put(DDAppConstants.df_is_compay, "0");
		params.put(DDAppConstants.req_amount, order.getAmount());
		params.put(DDAppConstants.df_product_id, "0201");
		params.put(DDAppConstants.req_merchant_no, order.getOrderNo());
		params.put(DDAppConstants.df_customer_phone, order.getCustomerPhone());
		params.put(DDAppConstants.df_customer_name, order.getCustomerName());
		params.put(DDAppConstants.df_customer_cert_type, order.getCustomerCertType());
		params.put(DDAppConstants.df_customer_cert_id, order.getCustomerCertId());
		params.put(DDAppConstants.df_bank_no, order.getBankNo());
		params.put(DDAppConstants.df_bank_name, order.getBankName());
		params.put(DDAppConstants.df_bank_card_no, order.getBankCardNo());
		params.put(DDAppConstants.md5Key, merchant.getMd5Key());
		
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> se = params.entrySet();
		Iterator<Entry<String, String>> ie = se.iterator();
		while (ie.hasNext()) {
			Entry<String, String> entry = ie.next();
			String k = entry.getKey();
			String v = entry.getValue();
			sb.append(k).append("=").append(v).append("&");
			
		}
		String data = sb.substring(0, sb.length() - 1);
		System.out.println("点点代付签名原文：" + data);
		
		String sign = Encrypt.MD5(data);
		System.out.println("点点代付签名：" + sign);
		
		return sign;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, String> callbackUtil(HttpServletRequest request)
			throws UnsupportedEncodingException, IOException {
		Map<String, String> result = new HashMap<String, String>();
		Enumeration<?> params = request.getParameterNames();
		String data = "";
		if (params.hasMoreElements()) {
			data = String.valueOf(params.nextElement());
		}
		System.out.println("点点支付回调json数据：" + data);
		JSONObject json = JSONObject.fromObject(data);
		Set<String> keys = json.keySet();
		for (String key : keys) result.put(key, json.getString(key));
		
		return result;
	}
}