package com.maven.payment.wft.df;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.maven.payment.PayInterface;
import com.maven.util.Encrypt;
import com.maven.util.HttpPostUtil;
import com.maven.util.RSASignature;

import net.sf.json.JSONObject;

public class WFTDFPay {
	/* 固定值 */
	private static final String Version = "2.1";
	private static final String SignType = "RSA";
	private static final String Charset = "UTF-8";
	private static final String CurCode = "CNY";
	private static final String CorpPersonFlag = "1";
	/**
	 * 发起出款
	 * @param merchant
	 * @param order
	 * @return
	 * @throws Exception 
	 */
	public String doPay(WFTDFMerchantConfig merchant, WFTDFOrderConfig order) throws Exception {
		String sign = getRSASign(merchant, order);
		String payeeDetails = getPayeeDetails(merchant, order);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(WFTDFAppConstants.req_Version, Version);
		params.put(WFTDFAppConstants.req_SignType, SignType);
		params.put(WFTDFAppConstants.req_SignInfo, sign);
		params.put(WFTDFAppConstants.req_TranDateTime, order.getOrderTime());
		params.put(WFTDFAppConstants.req_Charset, Charset);
		params.put(WFTDFAppConstants.req_CurCode, CurCode);
		params.put(WFTDFAppConstants.req_Remark, order.getRemark());
		params.put(WFTDFAppConstants.req_MerId, merchant.getMerId());
		params.put(WFTDFAppConstants.req_PayeeDetails, payeeDetails);
		System.out.println("旺付通代付参数: " + params);
		
		String result = HttpPostUtil.doPostSubmitMap3(merchant.getPayUrl(), params);
		System.out.println("旺付通代付结果: " + result);
		
		if (StringUtils.isNotBlank(result)) {
			JSONObject json = JSONObject.fromObject(result);
			String code = json.getString(WFTDFAppConstants.rep_code);
			String message = json.getString(WFTDFAppConstants.rep_message);
			if ("1088".equals(code)) {
				return PayInterface.PAY_SUCCESS;
			} else {
				return message;
			}
		}
		return "旺付通代付失败...";
	}
	/**
	 * 获取业务数据加密
	 * @param merchant
	 * @param order
	 * @return
	 * @throws Exception 
	 */
	private String getPayeeDetails(WFTDFMerchantConfig merchant, WFTDFOrderConfig order) throws Exception {
		JSONObject json = new JSONObject();
		json.put(WFTDFAppConstants.json_OrdId, order.getOrderId());
		json.put(WFTDFAppConstants.json_RecvBankAcctName, order.getUserName());
		json.put(WFTDFAppConstants.json_RecvBankAccNumber, order.getCardNum());
		json.put(WFTDFAppConstants.json_BankCode, order.getBankCode());
		json.put(WFTDFAppConstants.json_RecvBankProvince, order.getBankProvince());
		json.put(WFTDFAppConstants.json_RecvBankCity, order.getBankCity());
		json.put(WFTDFAppConstants.json_RecvBankBranch, order.getBankBranch());
		json.put(WFTDFAppConstants.json_OrdAmt, order.getOrderAmount());
		json.put(WFTDFAppConstants.json_CorpPersonFlag, CorpPersonFlag);
		json.put(WFTDFAppConstants.json_NotifyURL, merchant.getNotifyUrl());
		System.out.println("旺付通代付业务参数原文: " + json);
		
		String AESEncrypt = Encrypt.AESEncodeEncrypt(json.toString(), merchant.getAESKey());
		System.out.println("旺付通代付业务参数AES加密: " + AESEncrypt);
		
		return AESEncrypt;
	}
	/**
	 * 获取数字签名
	 * @param merchant
	 * @param order
	 * @return
	 * @throws Exception 
	 */
	private String getRSASign(WFTDFMerchantConfig merchant, WFTDFOrderConfig order) throws Exception {
		SortedMap<String, String> params = new TreeMap<String, String>();
		
		params.put(WFTDFAppConstants.req_Version, Version);
		params.put(WFTDFAppConstants.req_SignType, SignType);
		params.put(WFTDFAppConstants.req_TranDateTime, order.getOrderTime());
		params.put(WFTDFAppConstants.req_Charset, Charset);
		params.put(WFTDFAppConstants.req_CurCode, CurCode);
		params.put(WFTDFAppConstants.req_Remark, order.getRemark());
		params.put(WFTDFAppConstants.req_MerId, merchant.getMerId());
		params.put(WFTDFAppConstants.req_PayeeDetails, getPayeeDetails(merchant, order));
		System.out.println("旺付通代付参数未排序原文: " + params);
		
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
		System.out.println("旺付通代付参数排序后原文：" + sb);
		
		String RSASign = RSASignature.sign(sb.toString(), merchant.getPrivateRSAKey(), Charset);
		System.out.println("旺付通代付参数RSA签名: " + RSASign);
		
		return RSASign;
	}
}