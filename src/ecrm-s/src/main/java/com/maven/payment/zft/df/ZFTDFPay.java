package com.maven.payment.zft.df;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.maven.payment.zft.SHAUtil;
import com.maven.util.HttpPostUtil;

public class ZFTDFPay {
	private static final String SIGNTYPE = "SHA";
	private static final String CHARSET = "UTF-8";
	private static final String BATCHBIZTYPE = "00000";
	private static final String BATCHVERSION = "00";
	private static final String batchCount = "1";
	
	public String getUrl(ZFTDFMerchantConfig merchant, ZFTDFOrderConfig order) {
		String sign = getSign(merchant, order);
		String content = getBatchContent(1, order);
		
		StringBuffer url = new StringBuffer(merchant.getPayUrl());
		url.append(merchant.getMerId()).append("-").append(this.batchNo);
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put(ZFTDFAppConstants.req_sign, sign);
		params.put(ZFTDFAppConstants.req_signType, SIGNTYPE);
		params.put(ZFTDFAppConstants.req_merchantId, merchant.getMerId());
		params.put(ZFTDFAppConstants.req_charset, CHARSET);
		params.put(ZFTDFAppConstants.req_batchBiztype, BATCHBIZTYPE);
		params.put(ZFTDFAppConstants.req_batchDate	, order.getOrderDate());
		params.put(ZFTDFAppConstants.req_batchVersion, BATCHVERSION);
		params.put(ZFTDFAppConstants.req_batchNo, this.batchNo);
		params.put(ZFTDFAppConstants.req_batchCount, batchCount);
		params.put(ZFTDFAppConstants.req_batchAmount, order.getOrderAmount());
		params.put(ZFTDFAppConstants.req_batchContent, content);
		
		String result = HttpPostUtil.doPostSubmitMap3(url.toString(), params);
		System.out.println("代付结果：\n" + result);
		
		return result;
	}

	private String getSign(ZFTDFMerchantConfig merchant, ZFTDFOrderConfig order) {
		SortedMap<String, String> params = new TreeMap<String, String>();
		
		params.put(ZFTDFAppConstants.req_merchantId, merchant.getMerId());
		params.put(ZFTDFAppConstants.req_charset, CHARSET);
		params.put(ZFTDFAppConstants.req_batchBiztype, BATCHBIZTYPE);
		params.put(ZFTDFAppConstants.req_batchDate, order.getOrderDate());
		params.put(ZFTDFAppConstants.req_batchVersion, BATCHVERSION);
		params.put(ZFTDFAppConstants.req_batchNo, this.batchNo);
		params.put(ZFTDFAppConstants.req_batchCount, batchCount);
		params.put(ZFTDFAppConstants.req_batchAmount, order.getOrderAmount());
		params.put(ZFTDFAppConstants.req_batchContent, getBatchContent(1, order));

		System.out.println("支付通代付参数未排序原文：" + params);

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
		System.out.println("支付通代付参数排序后原文：" + params);

		String paramData = sb.substring(0, sb.length() - 1).concat(merchant.getShaKey());
		System.out.println("支付通代付待签名参数：" + paramData);

		String sign = SHAUtil.sign(paramData, CHARSET).toUpperCase();
		System.out.println("支付通代付参数签名：" + sign);

		return sign;
	}
	
	private String getBatchContent(int index, ZFTDFOrderConfig order) {
//		序号--------------------1
//		银行账户----------------621799xxxxxxxxx4393
//		开户名------------------张三
//		开户行名称--------------中国邮政储蓄银行
//		分行--------------------开福区分行
//		支行--------------------开福区分行
//		公/私-------------------私
//		金额--------------------487.00
//		币种--------------------CNY
//		省----------------------湖南省
//		市----------------------长沙
//		手机号------------------
//		证件类型----------------
//		证件号------------------
//		用户协议号--------------
//		商户订单号--------------201708075352476560485471005
//		备注--------------------OK
		StringBuffer content = new StringBuffer(index+"");
		content.append(",").append(order.getBankCard());
		content.append(",").append(order.getUserName());
		content.append(",").append(order.getBankName());
		content.append(",").append(order.getBranchName());
		content.append(",").append(order.getBranchName());
		content.append(",").append("私");
		content.append(",").append(order.getOrderAmount());
		content.append(",").append("CNY");
		content.append(",").append(order.getProvince());
		content.append(",").append(order.getCity());
		content.append(",");
		content.append(",");
		content.append(",");
		content.append(",");
		content.append(",").append(order.getOrderNo());
		content.append(",").append("OK");
		
		return content.toString();
	}
	private String getBatchNo() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssS");
		return format.format(new Date());
	}
	
	private String batchNo;
	
	public ZFTDFPay() {
		this.batchNo = getBatchNo();
	}
}