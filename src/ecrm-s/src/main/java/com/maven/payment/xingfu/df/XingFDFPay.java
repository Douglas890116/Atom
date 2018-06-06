package com.maven.payment.xingfu.df;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.maven.payment.PayInterface;
import com.maven.payment.xingfu.XingFSave;
import com.maven.util.Encrypt;
import com.maven.util.HttpPostUtil;

public class XingFDFPay {
	private String version = "1.0.0.0";
	private String purpose = "10";
	
	public String doPay(XingFDFMerchantConfig merchant, XingFDFOrderConfig order)
		throws UnsupportedEncodingException, ParserConfigurationException,
			FactoryConfigurationError, SAXException, IOException {
		
		String sign = getSign(merchant, order);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(XingFDFAppConstants.req_service, merchant.getService());
		params.put(XingFDFAppConstants.req_version, version);
		params.put(XingFDFAppConstants.req_merId, merchant.getMerId());
		params.put(XingFDFAppConstants.req_tradeNo, order.getOrderNo());
		params.put(XingFDFAppConstants.req_tradeDate, order.getOrderDate());
		params.put(XingFDFAppConstants.req_amount, order.getOrderAmount());
		params.put(XingFDFAppConstants.req_notifyUrl, merchant.getNotifyUrl());
		params.put(XingFDFAppConstants.req_extra, order.getExtra());
		params.put(XingFDFAppConstants.req_summary, order.getOrderSummary());
		params.put(XingFDFAppConstants.req_bankCardNo, order.getBankCardNo());
		params.put(XingFDFAppConstants.req_bankCardName, order.getBankCardName());
		params.put(XingFDFAppConstants.req_bankId, order.getBankId());
		params.put(XingFDFAppConstants.req_bankName, order.getBankName());
		params.put(XingFDFAppConstants.req_purpose, purpose);
		params.put(XingFDFAppConstants.req_sign, sign);
		System.out.println("星付代付请求参数：" + params);
		
		String result = HttpPostUtil.doPostSubmitMap3(merchant.getPayUrl(), params);
		System.out.println("星付代付请求结果：" + result);
		Map<String, String> resultMap = XingFSave.getXMLFromResult(result);
		System.out.println("星付代付请求响应参数：" + resultMap);
		
		String status = resultMap.get(XingFDFAppConstants.rep_code).toString();
		String message = resultMap.get(XingFDFAppConstants.rep_desc).toString();
		
		if ("00".equals(status)) {
			return PayInterface.PAY_SUCCESS;
		}
		
		return message;
	}
	
	private String getSign(XingFDFMerchantConfig merchant, XingFDFOrderConfig order) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(XingFDFAppConstants.req_service).append("=").append(merchant.getService()).append("&");
		sb.append(XingFDFAppConstants.req_version).append("=").append(version).append("&");
		sb.append(XingFDFAppConstants.req_merId).append("=").append(merchant.getMerId()).append("&");
		sb.append(XingFDFAppConstants.req_tradeNo).append("=").append(order.getOrderNo()).append("&");
		sb.append(XingFDFAppConstants.req_tradeDate).append("=").append(order.getOrderDate()).append("&");
		sb.append(XingFDFAppConstants.req_amount).append("=").append(order.getOrderAmount()).append("&");
		sb.append(XingFDFAppConstants.req_notifyUrl).append("=").append(merchant.getNotifyUrl()).append("&");
		sb.append(XingFDFAppConstants.req_extra).append("=").append(order.getExtra()).append("&");
		sb.append(XingFDFAppConstants.req_summary).append("=").append(order.getOrderSummary()).append("&");
		sb.append(XingFDFAppConstants.req_bankCardNo).append("=").append(order.getBankCardNo()).append("&");
		sb.append(XingFDFAppConstants.req_bankCardName).append("=").append(order.getBankCardName()).append("&");
		sb.append(XingFDFAppConstants.req_bankId).append("=").append(order.getBankId()).append("&");
		sb.append(XingFDFAppConstants.req_bankName).append("=").append(order.getBankName()).append("&");
		sb.append(XingFDFAppConstants.req_purpose).append("=").append(purpose).append(merchant.getMerKey());
		System.out.println("星付代付请求参数原文：" + sb);
		
		String sign = Encrypt.MD5(sb.toString());
		System.out.println("星付代付请求参数签名：" + sign);
		
		return sign;
	}
}