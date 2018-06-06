package com.maven.payment.ys.ds;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.maven.payment.ys.SignUtils;
import com.maven.util.HttpPostUtil;

import net.sf.json.JSONObject;

public class YSDSSave {
	private static final String charset = "UTF-8";
	private static final String sign_type = "RSA";
	private static final String version = "3.0";
	private static final String currency = "CNY";
	/**
	 * 获取支付地址
	 * @param merchant
	 * @param order
	 * @return
	 * @throws IOException 
	 * @throws SignatureException 
	 * @throws FileNotFoundException 
	 * @throws CertificateException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyStoreException 
	 * @throws InvalidKeyException 
	 * @throws UnrecoverableKeyException 
	 * @throws Exception 
	 */
	public String getUrl(YSDSMerchantConfig merchant, YSDSOrderConfig order)
		throws UnrecoverableKeyException, InvalidKeyException, KeyStoreException, NoSuchAlgorithmException,
			CertificateException, FileNotFoundException, SignatureException, IOException {

		Map<String, String> params = new HashMap<String, String>();
		params.put(YSDSAppConstants.req_method, merchant.getMethod());
		params.put(YSDSAppConstants.req_partner_id, merchant.getMerId());
		params.put(YSDSAppConstants.req_timestamp, order.getOrderTime());
		params.put(YSDSAppConstants.req_charset, charset);
		params.put(YSDSAppConstants.req_sign_type, sign_type);
		params.put(YSDSAppConstants.req_notify_url, merchant.getNotifyUrl());
		params.put(YSDSAppConstants.req_version, version);
		params.put(YSDSAppConstants.req_biz_content, getBizContent(merchant, order).toString());
		params.put(YSDSAppConstants.req_sign, getSign(merchant, order));
		System.out.println("银盛代收请求参数：" + params);
		
		String result = HttpPostUtil.doPostSubmitMap(merchant.getPayUrl(), params);
		System.out.println("银盛代收请求结果：" + result);
		
		return result;		
	}
	/**
	 * 生成订单参数的json数据
	 * @param merchant
	 * @param order
	 * @return
	 */
	private JSONObject getBizContent(YSDSMerchantConfig merchant, YSDSOrderConfig order) {
		JSONObject content = new JSONObject();
		content.put(YSDSAppConstants.req_json_out_trade_no, order.getOrderNo());
		content.put(YSDSAppConstants.req_json_business_code, merchant.getBusinessCode());
		content.put(YSDSAppConstants.req_json_currency, currency);
		content.put(YSDSAppConstants.req_json_total_amount, order.getOrderAmount());
		content.put(YSDSAppConstants.req_json_subject, order.getOrderSubject());
		content.put(YSDSAppConstants.req_json_bank_name, order.getBankName());
		content.put(YSDSAppConstants.req_json_bank_province, order.getBankProvince());
		content.put(YSDSAppConstants.req_json_bank_city, order.getBankCity());
		content.put(YSDSAppConstants.req_json_bank_account_no, order.getBankAccountNo());
		content.put(YSDSAppConstants.req_json_bank_account_name, order.getBankAccountName());
		content.put(YSDSAppConstants.req_json_bank_account_type, order.getBankAccountType());
		content.put(YSDSAppConstants.req_json_bank_card_type, order.getBankCardType());
		content.put(YSDSAppConstants.req_json_bank_telephone_no, order.getBankTelephoneNo());
		System.out.println("银盛代收订单参数json：" + content);
		
		return content;
	}
	/**
	 * 参数签名
	 * @param merchant
	 * @param order
	 * @return
	 * @throws IOException 
	 * @throws SignatureException 
	 * @throws FileNotFoundException 
	 * @throws CertificateException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyStoreException 
	 * @throws InvalidKeyException 
	 * @throws UnrecoverableKeyException 
	 * @throws Exception 
	 */
	private String getSign(YSDSMerchantConfig merchant, YSDSOrderConfig order)
		throws UnrecoverableKeyException, InvalidKeyException, KeyStoreException, NoSuchAlgorithmException,
			CertificateException, FileNotFoundException, SignatureException, IOException {
		
		SortedMap<String, Object> params = new TreeMap<String, Object>();
		
		params.put(YSDSAppConstants.req_method, merchant.getMethod());
		params.put(YSDSAppConstants.req_partner_id, merchant.getMerId());
		params.put(YSDSAppConstants.req_timestamp, order.getOrderTime());
		params.put(YSDSAppConstants.req_charset, charset);
		params.put(YSDSAppConstants.req_sign_type, sign_type);
		params.put(YSDSAppConstants.req_notify_url, merchant.getNotifyUrl());
		params.put(YSDSAppConstants.req_version, version);
		params.put(YSDSAppConstants.req_biz_content, getBizContent(merchant, order));
		
		System.out.println("银盛支付待签名参数：" + params);
		
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, Object>> es = params.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator<Entry<String, Object>> it = es.iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
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
	 * 回调验签
	 * @param request
	 * @param privateKey
	 * @return
	 * @throws InvalidKeyException
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 * @throws SignatureException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean checkSign(Map<String, Object> request, String privateKey)
		throws InvalidKeyException, CertificateException, FileNotFoundException,
			SignatureException, UnsupportedEncodingException, NoSuchAlgorithmException {
		List<String> keys = new ArrayList<String>(request.keySet());
		Collections.sort(keys);
		
		StringBuffer prestr = new StringBuffer();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = String.valueOf(request.get(key));
			
			if (!YSDSAppConstants.callback_sign.equals(key)
				&& !value.equals("null") && StringUtils.isNotBlank(value)) {
				prestr.append(key).append("=").append(value).append("&");
			}
		}
		String content = prestr.substring(0, prestr.length() - 1);
		
		String sign = request.get(YSDSAppConstants.callback_sign).toString();
		
		return SignUtils.rsaCheckContent(content, sign, privateKey, charset);
	}
}