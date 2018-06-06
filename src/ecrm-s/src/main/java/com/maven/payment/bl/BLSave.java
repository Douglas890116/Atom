package com.maven.payment.bl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.maven.util.SignUtil;

public class BLSave {
	
	private static final String tranName = "payment";
	private static final String version  = "1.00";
	/**
	 * 获取订单时间
	 * @return
	 */
	public static String getOrderTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(new Date());
	}
	/**
	 * 获取支付接口链接
	 * @param merchant
	 * @param order
	 * @return
	 */
	public String getUrl(BLMerchantConfig merchant, BLOrderConfig order) {
		// 获取加密签名
		String sign = getSign(merchant, order);
/*		
		<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
		<payment>
			-<tranName>payment</tranName>
			-<version>1.00</version>
			-<merCode>10000001</merCode>
			-<orderNo>075500201705232</orderNo>
			-<orderTime>20170523091851</orderTime>
			-<payType>17</payType>
			-<amount>11</amount>
			-<currency>CNY</currency>
			-<productName>网上充值</productName>
			-<orderDesc>支付</orderDesc>
			-<returnURL>http://localhost:8081/pp_server/paysuc.jsp</returnURL>
			-<notifyURL>http://localhost:8081/pp_server/success.jsp</notifyURL>
			-<sign>4B64C3DC02959689EA1CA3DEDAAF7982</sign>
		</payment>
*/
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>");
		sb.append("<payment>");
		sb.append("<tranName>payment</tranName>");
		sb.append("<version>1.00</version>");
		sb.append("<merCode>").append(merchant.getMerCode()).append("</merCode>");
		sb.append("<orderNo>").append(order.getOrderNo()).append("</orderNo>");
		sb.append("<orderTime>").append(order.getOrderTime()).append("</orderTime>");
		sb.append("<payType>").append(order.getPayType()).append("</payType>");
		sb.append("<amount>").append(order.getAmount()).append("</amount>");
		sb.append("<currency>").append(order.getCurrency()).append("</currency>");
		sb.append("<productName>Recharge</productName>");
		sb.append("<orderDesc>Description</orderDesc>");
		sb.append("<returnURL>").append(merchant.getReturnUrl()).append("</returnURL>");
		sb.append("<notifyURL>").append(merchant.getNotifyUrl()).append("</notifyURL>");
		sb.append("<sign>").append(sign).append("</sign>");
		sb.append("</payment>");
		
		System.out.println("宝立付参数xml：" + sb.toString());
		//请求地址：http://119.23.249.175:8080/pp_server/pay?tranType=xxxxx&param=
		StringBuffer apiUrl = new StringBuffer(merchant.getPayUrl());
		apiUrl.append("?tranType=").append(tranName).append("&param=").append(sb.toString());
		System.out.println("宝立付支付完成地址：" + apiUrl.toString());
		
		return apiUrl.toString();
	}
	
	/**
	 * 获取支付签名
	 * @param merchant
	 * @param order
	 * @return
	 */
	private String getSign(BLMerchantConfig merchant, BLOrderConfig order) {
		SortedMap<String, String> params = new TreeMap<String, String>();
		
		params.put(BLAppConstants.req_amount, order.getAmount());
		params.put(BLAppConstants.req_currency, order.getCurrency());
		params.put(BLAppConstants.req_merCode, merchant.getMerCode());
		params.put(BLAppConstants.req_notifyURL, merchant.getNotifyUrl());
		params.put(BLAppConstants.req_orderDesc, order.getOrderDesc());
		params.put(BLAppConstants.req_orderNo, order.getOrderNo());
		params.put(BLAppConstants.req_orderTime, order.getOrderTime());
		params.put(BLAppConstants.req_payType, order.getPayType());
		params.put(BLAppConstants.req_productName, order.getProductName());
		params.put(BLAppConstants.req_returnURL, merchant.getReturnUrl());
		params.put(BLAppConstants.req_tranName, tranName);
		params.put(BLAppConstants.req_version, version);
		
		System.out.println("宝立付参数未排序原文：" + params);
		
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
		System.out.println("宝立付参数排序后原文：" + params);
		
		String paramStr = sb.append("key=").append(merchant.getMd5Key()).toString();
		System.out.println("宝立付MD5签名原文：" + paramStr);
		
		String sign = SignUtil.MD5Encode(paramStr);
		System.out.println("宝立付MD5签名：" + sign);
		
		return sign;
	}
	/**
	 * 宝立付验签
	 * @param params
	 * @param md5Key
	 * @return
	 */
	public static boolean checkCallbackSign(Map<String, String> params, String md5Key) {
		SortedMap<String, String> parameters = new TreeMap<String, String>();
		if(StringUtils.isNotBlank(params.get(BLAppConstants.rep_tranName)))
			parameters.put(BLAppConstants.rep_tranName, params.get(BLAppConstants.rep_tranName));
		if(StringUtils.isNotBlank(params.get(BLAppConstants.rep_version)))
			parameters.put(BLAppConstants.rep_version, params.get(BLAppConstants.rep_version));
		if(StringUtils.isNotBlank(params.get(BLAppConstants.rep_merCode)))
			parameters.put(BLAppConstants.rep_merCode, params.get(BLAppConstants.rep_merCode));
		if(StringUtils.isNotBlank(params.get(BLAppConstants.rep_flowNo)))
			parameters.put(BLAppConstants.rep_flowNo, params.get(BLAppConstants.rep_flowNo));
		if(StringUtils.isNotBlank(params.get(BLAppConstants.rep_orderNo)))
			parameters.put(BLAppConstants.rep_orderNo, params.get(BLAppConstants.rep_orderNo));
		if(StringUtils.isNotBlank(params.get(BLAppConstants.rep_orderDate)))
			parameters.put(BLAppConstants.rep_orderDate, params.get(BLAppConstants.rep_orderDate));
		if(StringUtils.isNotBlank(params.get(BLAppConstants.rep_ordAmt)))
			parameters.put(BLAppConstants.rep_ordAmt, params.get(BLAppConstants.rep_ordAmt));
		if(StringUtils.isNotBlank(params.get(BLAppConstants.rep_payType)))
			parameters.put(BLAppConstants.rep_payType, params.get(BLAppConstants.rep_payType));
		if(StringUtils.isNotBlank(params.get(BLAppConstants.rep_currency)))
			parameters.put(BLAppConstants.rep_currency, params.get(BLAppConstants.rep_currency));
		if(StringUtils.isNotBlank(params.get(BLAppConstants.rep_paymentState)))
			parameters.put(BLAppConstants.rep_paymentState, params.get(BLAppConstants.rep_paymentState));
		if(StringUtils.isNotBlank(params.get(BLAppConstants.rep_orderDealTime)))
			parameters.put(BLAppConstants.rep_orderDealTime, params.get(BLAppConstants.rep_orderDealTime));
		if(StringUtils.isNotBlank(params.get(BLAppConstants.rep_workdate)))
			parameters.put(BLAppConstants.rep_workdate, params.get(BLAppConstants.rep_workdate));
		if(StringUtils.isNotBlank(params.get(BLAppConstants.rep_clearDate)))
			parameters.put(BLAppConstants.rep_clearDate, params.get(BLAppConstants.rep_clearDate));
		if(StringUtils.isNotBlank(params.get(BLAppConstants.rep_errorCode)))
			parameters.put(BLAppConstants.rep_errorCode, params.get(BLAppConstants.rep_errorCode));
		if(StringUtils.isNotBlank(params.get(BLAppConstants.rep_errorMessage)))
			parameters.put(BLAppConstants.rep_errorMessage, params.get(BLAppConstants.rep_errorMessage));
		
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator<Entry<String, String>> it = es.iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		String paramStr = sb.append("key=").append(md5Key).toString();
		System.out.println("本地生成的待加密参数原文：" + paramStr);
		String localSign = SignUtil.MD5Encode(paramStr);
		String callbackSign = params.get(BLAppConstants.rep_sign);
		
		return localSign.equals(callbackSign);
	}
	
	/**
	 * 宝立付回调处理参数
	 * @param request
	 * @param md5Key
	 * @return
	 * @throws DocumentException 
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> callbackUtil(HttpServletRequest request) throws DocumentException, IOException {
		int contentLength = request.getContentLength();
		byte buffer[] = new byte[contentLength];
		for (int i = 0; i < contentLength;) {
			int readlen = request.getInputStream().read(buffer, i, contentLength - i);
			if (readlen == -1) break;
			i += readlen;
		}
		
		String xmlStr = new String(buffer, "UTF-8");
		Map<String, String> result = new HashMap<String, String>();
		if(StringUtils.isBlank(xmlStr)) return result;
		Document xml = DocumentHelper.parseText(xmlStr);
		Element root = xml.getRootElement();
		List<Element> nodes = root.elements();
		for (Element e : nodes) {
			result.put(e.getName(), e.getStringValue());
		}
		System.out.println("==========宝立付回调参数==========");
		System.out.println("==========" + result + "==========");
		return result;
	}
}