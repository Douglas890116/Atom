package com.maven.payment.rf.df;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.maven.util.Encrypt;
import com.maven.util.HttpPostUtil;
import com.maven.util.StringUtils;

import net.sf.json.JSONObject;

public class RFDFPay {
	private static final String refCode = "00000000";
	
	public String getUrl(RFDFMerchantConfig merchant, RFDFOrderConfig order) {
		String sign = getSign(merchant, order);
		
		JSONObject content = new JSONObject();
		content.put(RFDFAppConstants.content_orderNo, order.getOrderNo());
		content.put(RFDFAppConstants.content_bankName, order.getBankName());
		content.put(RFDFAppConstants.content_provice, order.getBankProvice());
		content.put(RFDFAppConstants.content_city, order.getBankCity());
		content.put(RFDFAppConstants.content_branch, order.getBankBranch());
		content.put(RFDFAppConstants.content_payee_name, order.getUserName());
		content.put(RFDFAppConstants.content_payee_card, order.getCardNum());
		content.put(RFDFAppConstants.content_amount, order.getOrderAmount());
		content.put(RFDFAppConstants.content_refCode, refCode);
		content.put(RFDFAppConstants.content_remark, order.getRemark());
		content.put(RFDFAppConstants.content_signMd5, sign);
		
		JSONObject payData = new JSONObject();
		payData.put(RFDFAppConstants.req_json_partyId, merchant.getPartyId());
		payData.put(RFDFAppConstants.req_json_accountId, merchant.getAccountId());
		payData.put(RFDFAppConstants.req_json_note, merchant.getNote());
		payData.put(RFDFAppConstants.req_json_directPaidout, merchant.getDirectPaidout());
		payData.put(RFDFAppConstants.req_json_paidoutPassword, merchant.getPassword());
		payData.put(RFDFAppConstants.req_json_content, new Object[]{content});
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(RFDFAppConstants.req_payData, payData.toString());
		System.out.println("锐付代付接口参数集：" + params);
		
		String result = HttpPostUtil.doPostSubmitMap(merchant.getPayUrl(), params);
		System.out.println("锐付代付接口请求结果：" + result);
		
		return result;
	}
	
	private String getSign(RFDFMerchantConfig merchant, RFDFOrderConfig order) {
		SortedMap<String, String> params = new TreeMap<String, String>();
		
		params.put(RFDFAppConstants.content_amount, order.getOrderAmount());
		params.put(RFDFAppConstants.content_bankName, order.getBankName());
		params.put(RFDFAppConstants.content_branch, order.getBankBranch());
		params.put(RFDFAppConstants.content_city, order.getBankCity());
		params.put(RFDFAppConstants.content_orderNo, order.getOrderNo());
		params.put(RFDFAppConstants.content_payee_card, order.getCardNum());
		params.put(RFDFAppConstants.content_payee_name, order.getUserName());
		params.put(RFDFAppConstants.content_provice, order.getBankProvice());
		params.put(RFDFAppConstants.content_refCode, refCode);
		params.put(RFDFAppConstants.content_remark, order.getRemark());
		System.out.println("锐付代付接口待签名参数：" + params);
		
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> es = params.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String k = entry.getKey();
			String v = entry.getValue();
			sb.append(k + "=" + v + "&");
		}
		// 將證書同時拼接到參數字串尾部進行 md5 加密
		sb.append(merchant.getMd5Key());
		System.out.println("锐付代付接口参数排序后原文：" + sb);

		String sign = Encrypt.MD5(sb.toString()).toLowerCase();
		System.out.println("锐付代付接口参数签名：" + sign);
		
		return sign;
	}
}