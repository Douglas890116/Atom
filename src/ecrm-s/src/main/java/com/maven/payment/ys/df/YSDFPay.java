package com.maven.payment.ys.df;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.maven.payment.PayInterface;
import com.maven.payment.ys.SignUtils;
import com.maven.util.HttpPostUtil;

import net.sf.json.JSONObject;

public class YSDFPay {
	private static final String charset = "UTF-8";
	private static final String sign_type = "RSA";
	private static final String version = "3.0";
	private static final String currency = "CNY";
	
	public String doPay(YSDFMerchantConfig merchant, YSDFOrderConfig order)
		throws UnrecoverableKeyException, InvalidKeyException, KeyStoreException, NoSuchAlgorithmException,
			CertificateException, FileNotFoundException, SignatureException, IOException {
		
		String sign = getSign(merchant, order);
		String biz_content = getBizContent(merchant, order);

		Map<String, String> params = new HashMap<String, String>();
		params.put(YSDFAppConstants.req_method, merchant.getMethod());
		params.put(YSDFAppConstants.req_partner_id, merchant.getMerId());
		params.put(YSDFAppConstants.req_timestamp, order.getOrderTime());
		params.put(YSDFAppConstants.req_charset, charset);
		params.put(YSDFAppConstants.req_sign_type, sign_type);
		params.put(YSDFAppConstants.req_sign, sign);
		params.put(YSDFAppConstants.req_notify_url, merchant.getNotifyUrl());
		params.put(YSDFAppConstants.req_version, version);
		params.put(YSDFAppConstants.req_biz_content, biz_content);
		
		System.out.println("银盛代付请求参数：" + params);
		
		String result = HttpPostUtil.doPostSubmitMap(merchant.getPayUrl(), params);
		System.out.println("银盛代付请求结果" + result);
		
		if (StringUtils.isNotBlank(result)) {
			JSONObject json = JSONObject.fromObject(result);
			String rep_params = json.getString("ysepay_df_single_normal_accept_response");
			JSONObject json_params = JSONObject.fromObject(rep_params);
			String status = json_params.getString(YSDFAppConstants.rep_trade_status);
			if ("TRADE_ACCEPT_SUCCESS".equals(status)) {
				return PayInterface.PAY_SUCCESS;
			}
		}
		
		return "";
	}
	
	private String getBizContent(YSDFMerchantConfig merchant, YSDFOrderConfig order) {
		JSONObject content = new JSONObject();
		
		content.put(YSDFAppConstants.req_json_out_trade_no, order.getOrderNo());
		content.put(YSDFAppConstants.req_json_business_code, merchant.getBusinessCode());
		content.put(YSDFAppConstants.req_json_currency, currency);
		content.put(YSDFAppConstants.req_json_total_amount, order.getOrderAmount());
		content.put(YSDFAppConstants.req_json_subject, order.getOrderSubject());
		content.put(YSDFAppConstants.req_json_bank_name, order.getBankName());
		content.put(YSDFAppConstants.req_json_bank_province, order.getBankProvince());
		content.put(YSDFAppConstants.req_json_bank_city, order.getBankCity());
		content.put(YSDFAppConstants.req_json_bank_account_no, order.getBankAccountNo());
		content.put(YSDFAppConstants.req_json_bank_account_name, order.getBankAccountName());
		content.put(YSDFAppConstants.req_json_bank_account_type, order.getBankAccountType());
		content.put(YSDFAppConstants.req_json_bank_card_type, order.getBankCardType());
		content.put(YSDFAppConstants.req_json_bank_telephone_no, order.getBankTelephoneNo());
		
		System.out.println("银盛代付biz_content参数json：" + content);
		
		return content.toString();
	}
	
	private String getSign(YSDFMerchantConfig merchant, YSDFOrderConfig order)
		throws UnrecoverableKeyException, InvalidKeyException, KeyStoreException, NoSuchAlgorithmException,
			CertificateException, FileNotFoundException, SignatureException, IOException {
		
		SortedMap<String, Object> params = new TreeMap<String, Object>();

		params.put(YSDFAppConstants.req_method, merchant.getMethod());
		params.put(YSDFAppConstants.req_partner_id, merchant.getMerId());
		params.put(YSDFAppConstants.req_timestamp, order.getOrderTime());
		params.put(YSDFAppConstants.req_charset, charset);
		params.put(YSDFAppConstants.req_sign_type, sign_type);
		params.put(YSDFAppConstants.req_notify_url, merchant.getNotifyUrl());
		params.put(YSDFAppConstants.req_version, version);
		params.put(YSDFAppConstants.req_biz_content, getBizContent(merchant, order));

		System.out.println("银盛代付待签名参数：" + params);

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
		System.out.println("银盛代付参数排序后原文：" + sb);

		String content = sb.substring(0, sb.length() - 1);

		return SignUtils.rsaSign(content, merchant.getPrivateKeyUrl(), merchant.getPassword(), charset);
	}
}