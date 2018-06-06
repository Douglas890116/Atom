package com.maven.payment.xingfu;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.hy.pull.common.util.Base64;
import com.maven.payment.xingfu.XingFAppConstants.Enum_XingFService;
import com.maven.util.Encrypt;
import com.maven.util.HttpPostUtil;

public class XingFSave {
	private String version = "1.0.0.0";
	
	public String getUrl(XingFMerchantConfig merchant, XingFOrderConfig order)
		throws ParserConfigurationException, FactoryConfigurationError, SAXException, IOException {
		
		String sign = getSign(merchant, order);
		
		StringBuffer url = new StringBuffer(merchant.getPayUrl());
		url.append("?");
		
		url.append(XingFAppConstants.req_service).append("=").append(merchant.getService()).append("&");
		url.append(XingFAppConstants.req_version).append("=").append(version).append("&");
		url.append(XingFAppConstants.req_merId).append("=").append(merchant.getMerId()).append("&");
		
		if (merchant.getService().equals(Enum_XingFService.扫码支付.value)
			|| merchant.getService().equals(Enum_XingFService.H5支付.value))
			url.append(XingFAppConstants.req_typeId).append("=").append(order.getBankId()).append("&");
		
		url.append(XingFAppConstants.req_tradeNo).append("=").append(order.getOrderNo()).append("&");
		url.append(XingFAppConstants.req_tradeDate).append("=").append(order.getOrderDate()).append("&");
		url.append(XingFAppConstants.req_amount).append("=").append(order.getOrderAmount()).append("&");
		url.append(XingFAppConstants.req_notifyUrl).append("=").append(merchant.getNotifyUrl()).append("&");
		url.append(XingFAppConstants.req_extra).append("=").append(order.getExtra()).append("&");
		url.append(XingFAppConstants.req_summary).append("=").append(order.getOrderSummary()).append("&");
		url.append(XingFAppConstants.req_expireTime).append("=").append(merchant.getExpireTime()).append("&");
		url.append(XingFAppConstants.req_clientIp).append("=").append(order.getClientIP()).append("&");
		
		if (merchant.getService().equals(Enum_XingFService.网银支付B2B.value)
			|| merchant.getService().equals(Enum_XingFService.网银支付B2C.value))
			url.append(XingFAppConstants.req_bankId).append("=").append(order.getBankId()).append("&");
		
		url.append(XingFAppConstants.req_sign).append("=").append(sign);
		
		System.out.println("星付支付链接：" + url);
		
		if (merchant.getService().equals(Enum_XingFService.扫码支付.value)) {
			Map<String, String> paramMap = new HashMap<String, String>();
			String query = url.toString().split("[?]")[1];
			String[] params = query.split("&");
			for (String param : params) {
				paramMap.put(param.split("=")[0], param.split("=")[1]);
			}
			String result = HttpPostUtil.doPostSubmitMap3(merchant.getPayUrl(), paramMap);
			System.out.println("星付扫码支付请求结果：" + result);
			
			Map<String, String> reponse = getXMLFromResult(result);
			System.out.println("星付扫码支付返回参数：" + reponse);
			String status = reponse.get(XingFAppConstants.rep_code).toString();
			String qrCode = reponse.get(XingFAppConstants.rep_qrCode).toString();
			
			if ("00".equals(status)) {
				return Base64.decodeByHtmlStr(qrCode);
			} else {
				return "";
			}
		}
		
		return url.toString();
	}

	private String getSign(XingFMerchantConfig merchant, XingFOrderConfig order) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(XingFAppConstants.req_service).append("=").append(merchant.getService()).append("&");
		sb.append(XingFAppConstants.req_version).append("=").append(version).append("&");
		sb.append(XingFAppConstants.req_merId).append("=").append(merchant.getMerId()).append("&");
		
		if (merchant.getService().equals(Enum_XingFService.扫码支付.value)
			|| merchant.getService().equals(Enum_XingFService.H5支付.value))
			sb.append(XingFAppConstants.req_typeId).append("=").append(order.getBankId()).append("&");
		
		sb.append(XingFAppConstants.req_tradeNo).append("=").append(order.getOrderNo()).append("&");
		sb.append(XingFAppConstants.req_tradeDate).append("=").append(order.getOrderDate()).append("&");
		sb.append(XingFAppConstants.req_amount).append("=").append(order.getOrderAmount()).append("&");
		sb.append(XingFAppConstants.req_notifyUrl).append("=").append(merchant.getNotifyUrl()).append("&");
		sb.append(XingFAppConstants.req_extra).append("=").append(order.getExtra()).append("&");
		sb.append(XingFAppConstants.req_summary).append("=").append(order.getOrderSummary()).append("&");
		sb.append(XingFAppConstants.req_expireTime).append("=").append(merchant.getExpireTime()).append("&");
		sb.append(XingFAppConstants.req_clientIp).append("=").append(order.getClientIP()).append("&");
		
		if (merchant.getService().equals(Enum_XingFService.网银支付B2B.value)
			|| merchant.getService().equals(Enum_XingFService.网银支付B2C.value))
			sb.append(XingFAppConstants.req_bankId).append("=").append(order.getBankId()).append("&");
		
		String orgin = sb.substring(0, sb.length() - 1).concat(merchant.getMerKey());
		System.out.println("星付支付待签名参数原文：" + orgin);
		
		String sign = Encrypt.MD5(orgin);
		System.out.println("星付支付签名：" + sign);
		
		return sign;
	}
	/**
	 * 回调验签
	 * @param request
	 * @param merKey
	 * @return
	 */
	public static boolean checkSign(Map<String, Object> request, String merKey) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(XingFAppConstants.callback_service).append("=").append(request.get(XingFAppConstants.callback_service)).append("&");
		sb.append(XingFAppConstants.callback_merId).append("=").append(request.get(XingFAppConstants.callback_merId)).append("&");
		sb.append(XingFAppConstants.callback_tradeNo).append("=").append(request.get(XingFAppConstants.callback_tradeNo)).append("&");
		sb.append(XingFAppConstants.callback_tradeDate).append("=").append(request.get(XingFAppConstants.callback_tradeDate)).append("&");
		sb.append(XingFAppConstants.callback_opeNo).append("=").append(request.get(XingFAppConstants.callback_opeNo)).append("&");
		sb.append(XingFAppConstants.callback_opeDate).append("=").append(request.get(XingFAppConstants.callback_opeDate)).append("&");
		sb.append(XingFAppConstants.callback_amount).append("=").append(request.get(XingFAppConstants.callback_amount)).append("&");
		sb.append(XingFAppConstants.callback_status).append("=").append(request.get(XingFAppConstants.callback_status)).append("&");
		sb.append(XingFAppConstants.callback_extra).append("=").append(request.get(XingFAppConstants.callback_extra)).append("&");
		sb.append(XingFAppConstants.callback_payTime).append("=").append(request.get(XingFAppConstants.callback_payTime)).append("&");
		
		String orgin = sb.substring(0, sb.length() - 1).concat(merKey);
		System.out.println("星付回调待签名参数原文：" + orgin);
		
		String localSign = Encrypt.MD5(orgin);
		String callbackSign = request.get(XingFAppConstants.callback_sign).toString();
		
		return localSign.equalsIgnoreCase(callbackSign);
	}
	
	public static Map<String, String> getXMLFromResult(String resultStr)
		throws ParserConfigurationException, FactoryConfigurationError,
			UnsupportedEncodingException, SAXException, IOException {
		
		Map<String, String> result = new HashMap<String, String>();
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(new ByteArrayInputStream(resultStr.getBytes("UTF-8")));
		Element root = doc.getDocumentElement();
		NodeList message = root.getChildNodes();
		
		Node node_detail = message.item(0);
		NodeList detail = node_detail.getChildNodes();
		for (int i = 0; i < detail.getLength(); i++) {
			Node node = detail.item(i);
			String name = node.getNodeName();
			String value = node.getFirstChild().getNodeValue();
			result.put(name, value);
		}
		
		Node node_sign = message.item(1);
		result.put(node_sign.getNodeName(), node_sign.getFirstChild().getNodeValue());
		
		return result;
	}
}