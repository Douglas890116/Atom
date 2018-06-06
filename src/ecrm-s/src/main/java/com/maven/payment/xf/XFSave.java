package com.maven.payment.xf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

public class XFSave {

	public String getSaveUrl(XFMerchantConfig merchant, XFOrderConfig order)
			throws InvalidKeyException, NoSuchAlgorithmException, IllegalStateException, UnsupportedEncodingException {
		String sign = getSign(merchant, order);
		StringBuffer url = new StringBuffer(merchant.getPayUrl());
		url.append("?").append(XFAppConstants.req_cid).append("=").append(merchant.getCid()).append("&")
				.append(XFAppConstants.req_uid).append("=").append(merchant.getUid()).append("&")
				.append(XFAppConstants.req_time).append("=").append(order.getTime()).append("&")
				.append(XFAppConstants.req_amount).append("=").append(order.getAmount()).append("&")
				.append(XFAppConstants.req_order_id).append("=").append(order.getOrderId()).append("&")
				.append(XFAppConstants.req_ip).append("=").append(order.getIp()).append("&")
				.append(XFAppConstants.req_type).append("=").append(order.getType()).append("&")
				.append(XFAppConstants.req_tflag).append("=").append(order.getTflag()).append("&")
				.append(XFAppConstants.req_sign).append("=").append(sign);
		System.out.println("喜发存款支付链接：" + url.toString());

		return url.toString();
	}

	public String getPayJsonData(XFMerchantConfig merchant, XFOrderConfig order) {
		JSONObject json = new JSONObject();
		json.put(XFAppConstants.req_cid, merchant.getCid());
		json.put(XFAppConstants.req_uid, merchant.getUid());
		json.put(XFAppConstants.req_time, order.getTime());
		json.put(XFAppConstants.req_amount, order.getAmount());
		json.put(XFAppConstants.req_order_id, order.getOrderId());
		json.put(XFAppConstants.req_to_bank_flag, order.getBank());
		json.put(XFAppConstants.req_to_cardnumber, order.getCardnum());
		json.put(XFAppConstants.req_to_username, order.getUsername());
		return json.toString();
	}

	private String getSign(XFMerchantConfig merchant, XFOrderConfig order)
			throws InvalidKeyException, NoSuchAlgorithmException, IllegalStateException, UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		sb.append(XFAppConstants.req_cid).append("=").append(merchant.getCid()).append("&")
				.append(XFAppConstants.req_uid).append("=").append(merchant.getUid()).append("&")
				.append(XFAppConstants.req_time).append("=").append(order.getTime()).append("&")
				.append(XFAppConstants.req_amount).append("=").append(order.getAmount()).append("&")
				.append(XFAppConstants.req_order_id).append("=").append(order.getOrderId()).append("&")
				.append(XFAppConstants.req_ip).append("=").append(order.getIp());
		System.out.println("喜发支付签名原文：" + sb.toString());
		System.out.println("喜发支付签名Key：" + merchant.getSha1Key());
		String signData = SignUtils.HmacSHA1(sb.toString(), merchant.getSha1Key());
		return URLEncoder.encode(signData, "UTF-8");
	}

	public static Map<String, String> callbackUtil(HttpServletRequest request) throws UnsupportedEncodingException, IOException {
		Map<String, String> result = new HashMap<String, String>();
		String sign = request.getHeader("Content-Hmac");
		result.put(XFAppConstants.callback_sign, sign);
		System.out.println("喜发支付回调签名：" + sign);
		InputStreamReader isr = new InputStreamReader(request.getInputStream(), request.getCharacterEncoding());
		BufferedReader br = new BufferedReader(isr);
		StringBuilder sb = new StringBuilder();
		String temp;
		while ((temp=br.readLine())!= null) sb.append(temp);
		br.close();
		isr.close();
		String data = sb.toString();
		System.out.println("喜发支付回调json数据：" + data);
		result.put("jsonData", data);
		return result;
	}
	
	public static boolean checkSign(String jsonData, String key, String callbackSign)
			throws InvalidKeyException, NoSuchAlgorithmException, IllegalStateException, UnsupportedEncodingException {
		String localSign = SignUtils.HmacSHA1(jsonData, key);
		return localSign.equals(callbackSign);
	}
}