package com.maven.payment.el;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.maven.util.RSASignature;

public class ELUtil {
	public static final String orderCurrency = "156";
	private static final String CHARSET = "UTF-8";
	private static final String inputCharset = "1";
	private static final String signTyle = "1";
	/**
	 * 生成支付链接
	 * @param merchant
	 * @param order
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws SignatureException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	public String getUrl(ELMerchantConfig merchant, ELOrderConfig order) throws InvalidKeyException,
		NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, UnsupportedEncodingException {

		String sign = getRSASign(merchant, order);
		StringBuffer url = new StringBuffer(merchant.getPayUrl());
		url.append("?").append(ELAppConstants.req_inputCharset).append("=").append(inputCharset);
		url.append("&").append(ELAppConstants.req_partnerId).append("=").append(merchant.getPartnerId());
		url.append("&").append(ELAppConstants.req_signType).append("=").append(signTyle);
		url.append("&").append(ELAppConstants.req_notifyUrl).append("=").append(merchant.getNotifyUrl());
		url.append("&").append(ELAppConstants.req_returnUrl).append("=").append(merchant.getReturnUrl());
		url.append("&").append(ELAppConstants.req_orderNo).append("=").append(order.getOrderNo());
		url.append("&").append(ELAppConstants.req_orderAmount).append("=").append(order.getOrderAmount());
		url.append("&").append(ELAppConstants.req_orderCurrency).append("=").append(order.getOrderCurrency());
		url.append("&").append(ELAppConstants.req_orderDatetime).append("=").append(order.getOrderDatetime());
		if (merchant.getPayUrl().contains("createQrCodePay")) {
			url.append("&").append(ELAppConstants.req_payMode).append("=").append(order.getPayMode());
			url.append("&").append(ELAppConstants.req_isPhone).append("=").append(order.getIsPhone());
		}
		url.append("&").append(ELAppConstants.req_signMsg).append("=").append(sign);
		System.out.println("亿联支付链接：\n" + url);

		return url.toString();
	}
	/**
	 * 发起代付
	 * @param merchant
	 * @param order
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws SignatureException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	public String doPay(ELMerchantConfig merchant, ELOrderConfig order) throws InvalidKeyException,
		NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, UnsupportedEncodingException {
		String sign = getRSASign(merchant, order);
		return sign;
	}
	/**
	 * 
	 * @param merchant
	 * @param order
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws SignatureException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	private String getRSASign(ELMerchantConfig merchant, ELOrderConfig order) throws InvalidKeyException,
		NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, UnsupportedEncodingException {
		SortedMap<String, String> params = new TreeMap<String, String>();
		
		params.put(ELAppConstants.req_inputCharset, inputCharset);
		params.put(ELAppConstants.req_partnerId, merchant.getPartnerId());
		params.put(ELAppConstants.req_notifyUrl, merchant.getNotifyUrl());
		params.put(ELAppConstants.req_returnUrl, merchant.getReturnUrl());
		params.put(ELAppConstants.req_orderNo, order.getOrderNo());
		params.put(ELAppConstants.req_orderAmount, order.getOrderAmount());
		params.put(ELAppConstants.req_orderCurrency, order.getOrderCurrency());
		params.put(ELAppConstants.req_orderDatetime, order.getOrderDatetime());
		if (merchant.getPayUrl().contains("createQrCodePay")) {
			params.put(ELAppConstants.req_payMode, order.getPayMode());
			params.put(ELAppConstants.req_isPhone, order.getIsPhone());
		}
		params.put(ELAppConstants.req_cashType, order.getCashType());
		params.put(ELAppConstants.req_accountName, order.getAccountName());
		params.put(ELAppConstants.req_bankName, order.getBankName());
		params.put(ELAppConstants.req_bankCardNo, order.getBankCardNo());
		params.put(ELAppConstants.req_canps, order.getCanps());
		System.out.println("亿联支付未排序参数：\n" + params);
		
		Set<Entry<String, String>> es = params.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator<Entry<String, String>> it = es.iterator();
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String k = (String) entry.getKey();
			String v = entry.getValue();
			if (StringUtils.isNotBlank(v) && !k.equals(ELAppConstants.req_signType) && !k.equals(ELAppConstants.req_signMsg)) {
				sb.append(k + "=" + v + "&");
			}
		}
		String data = sb.substring(0, sb.length()-1);
		System.out.println("亿联支付参数排序后原文：\n" + data);
		
		String sign = RSASignature.sign(data, merchant.getPrivateKey(), CHARSET);
		System.out.println("亿联支付RSA签名：\n" + sign);
		return sign;
	}
	/**
	 * 回调验签
	 * @param request
	 * @param publicKey 平台的验签公钥
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws SignatureException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 * @throws InvalidKeyException 
	 */
	public static boolean checkSign(Map<String, Object> request, String publicKey) throws InvalidKeyException,
		InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, UnsupportedEncodingException {
		SortedMap<String, String> params = new TreeMap<String, String>();
		
		Set<Entry<String, Object>> se = request.entrySet();
		Iterator<Entry<String, Object>> iterator = se.iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			String k = entry.getKey();
			Object v = entry.getValue();
			if (v != null) params.put(k, v.toString());
		}
		
		Set<Entry<String, String>> es = params.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator<Entry<String, String>> it = es.iterator();
		StringBuffer sb = new StringBuffer();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String k = entry.getKey();
			String v = entry.getValue();
			if (StringUtils.isNotBlank(v) && !k.equals(ELAppConstants.callback_signType) && !k.equals(ELAppConstants.callback_signMsg)) {
				sb.append(k + "=" + v + "&");
			}
		}
		String data = sb.substring(0, sb.length()-1);
		System.out.println("亿联支付回调参数排序后原文：\n" + data);
		
		String callbackSign = request.get(ELAppConstants.callback_signMsg).toString();
		return RSASignature.doCheck(data, callbackSign, publicKey, CHARSET);
	}
	
	public static void main(String[] args) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMDDhhmmss");
		System.out.println(dateFormat.format(new Date()));
	}
}