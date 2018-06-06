package com.maven.payment.jh2.df;

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

import net.sf.json.JSONObject;

public class JH2DFUtil {
	private static final String bankAccountType = "PRIVATE_DEBIT_ACCOUNT";

	public String doPay(JH2DFMerchantConfig merchant, JH2DFOrderConfig order) {
		String sign = getSign(merchant, order);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(JH2DFAppConstants.req_payKey, merchant.getPayKey());
		params.put(JH2DFAppConstants.req_outTradeNo, order.getOrderNo());
		params.put(JH2DFAppConstants.req_orderPrice, order.getOrderPrice());
		params.put(JH2DFAppConstants.req_proxyType, merchant.getProxyType());
		params.put(JH2DFAppConstants.req_productType, merchant.getProductType());
		params.put(JH2DFAppConstants.req_bankAccountType, bankAccountType);
		params.put(JH2DFAppConstants.req_phoneNo, order.getPhoneNo());
		params.put(JH2DFAppConstants.req_receiverName, order.getReceiverName());
		params.put(JH2DFAppConstants.req_certType, order.getCertType());
		params.put(JH2DFAppConstants.req_certNo, order.getCertNo());
		params.put(JH2DFAppConstants.req_receiverAccountNo, order.getReceiverAccountNo());
		params.put(JH2DFAppConstants.req_bankClearNo, order.getBankClearNo());
		params.put(JH2DFAppConstants.req_bankBranchNo, order.getBankBranchNo());
		params.put(JH2DFAppConstants.req_bankName, order.getBankName());
		params.put(JH2DFAppConstants.req_bankCode, order.getBankCode());
		params.put(JH2DFAppConstants.req_bankBranchName, order.getBankBranchName());
		params.put(JH2DFAppConstants.req_province, order.getProvince());
		params.put(JH2DFAppConstants.req_city, order.getCity());
		params.put(JH2DFAppConstants.req_sign, sign);
		System.out.println("金塔聚合代付请求参数：" + params);
		
		String result = HttpPostUtil.doPostSubmitMap3(merchant.getPayUrl(), params);
		System.out.println("金塔聚合代付请求结果：" + result);
		
		if (StringUtils.isNotBlank(result)) {
			JSONObject jsonObject = JSONObject.fromObject(result);
			String resultCode = jsonObject.getString(JH2DFAppConstants.rep_resultCode);
//			String outTradeNo = jsonObject.getString(JH2DFAppConstants.rep_outTradeNo);
//			String remitStatus  = jsonObject.getString(JH2DFAppConstants.rep_remitStatus);
//			String orderPrice = jsonObject.getString(JH2DFAppConstants.rep_orderPrice);
			String errMsg = jsonObject.getString(JH2DFAppConstants.rep_errMsg);
			if ("0000".equals(resultCode)) return PayInterface.PAY_SUCCESS;
			return errMsg;
		}
		return result;
	}
	
	private String getSign(JH2DFMerchantConfig merchant, JH2DFOrderConfig order) {
		SortedMap<String, String> params = new TreeMap<String, String>();
		
		params.put(JH2DFAppConstants.req_payKey, merchant.getPayKey());
		params.put(JH2DFAppConstants.req_outTradeNo, order.getOrderNo());
		params.put(JH2DFAppConstants.req_orderPrice, order.getOrderPrice());
		params.put(JH2DFAppConstants.req_proxyType, merchant.getProxyType());
		params.put(JH2DFAppConstants.req_productType, merchant.getProductType());
		params.put(JH2DFAppConstants.req_bankAccountType, bankAccountType);
		params.put(JH2DFAppConstants.req_phoneNo, order.getPhoneNo());
		params.put(JH2DFAppConstants.req_receiverName, order.getReceiverName());
		params.put(JH2DFAppConstants.req_certType, order.getCertType());
		params.put(JH2DFAppConstants.req_certNo, order.getCertNo());
		params.put(JH2DFAppConstants.req_receiverAccountNo, order.getReceiverAccountNo());
		params.put(JH2DFAppConstants.req_bankClearNo, order.getBankClearNo());
		params.put(JH2DFAppConstants.req_bankBranchNo, order.getBankBranchNo());
		params.put(JH2DFAppConstants.req_bankName, order.getBankName());
		params.put(JH2DFAppConstants.req_bankCode, order.getBankCode());
		params.put(JH2DFAppConstants.req_bankBranchName, order.getBankBranchName());
		params.put(JH2DFAppConstants.req_province, order.getProvince());
		params.put(JH2DFAppConstants.req_city, order.getCity());
		System.out.println("金塔聚合代付未排序签名参数：" + params);
		
		Set<Entry<String, String>> set = params.entrySet();
		Iterator<Entry<String, String>> iterator = set.iterator();
		Entry<String, String> entry = null;
		StringBuffer sb = new StringBuffer();
		while (iterator.hasNext()) {
			entry = iterator.next();
			String k = entry.getKey();
			String v = entry.getValue();
			if (StringUtils.isNotBlank(v)) {
				sb.append(k).append("=").append(v).append("&");
			}
		}
		sb.append("paySecret=").append(merchant.getMerKey());
		System.out.println("金塔聚合待代付签名参数原文：" + sb);
		
		String sign = Encrypt.MD5(sb.toString()).toUpperCase();
		System.out.println("金塔聚合代付参数签名：" + sign);
		
		return sign;
	}
}