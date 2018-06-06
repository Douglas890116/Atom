package com.maven.payment.duo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;

import com.maven.util.Encrypt;

public class DuoUtil {
	private static final String payType = "DT";
	private static final String curCode = "CNY";
	private static final String signType = "MD5";

	public String getUrl(DuoMerchantConfig merchant, DuoOrderConfig order) {
		String signInfo = getSign(merchant, order);
		
		StringBuffer url = new StringBuffer(merchant.getPayUrl());
		url.append("?").append(DuoAppConstants.req_MerId).append("=").append(merchant.getMerId());
		url.append("&").append(DuoAppConstants.req_OrdId).append("=").append(order.getOrderId());
		url.append("&").append(DuoAppConstants.req_OrdAmt).append("=").append(order.getOrderAmount());
		url.append("&").append(DuoAppConstants.req_PayType).append("=").append(payType);
		url.append("&").append(DuoAppConstants.req_CurCode).append("=").append(curCode);
		url.append("&").append(DuoAppConstants.req_BankCode).append("=").append(order.getBankCode());
		url.append("&").append(DuoAppConstants.req_ProductInfo).append("=").append(order.getProInfo());
		url.append("&").append(DuoAppConstants.req_Remark).append("=").append(order.getRemark());
		url.append("&").append(DuoAppConstants.req_ReturnURL).append("=").append(merchant.getReturnUrl());
		url.append("&").append(DuoAppConstants.req_NotifyURL).append("=").append(merchant.getNotifyUrl());
		url.append("&").append(DuoAppConstants.req_SignType).append("=").append(signType);
		url.append("&").append(DuoAppConstants.req_SignInfo).append("=").append(signInfo);
		System.out.println("多多支付链接：" + url);
		
		return url.toString();
	}
	
	private String getSign(DuoMerchantConfig merchant, DuoOrderConfig order) {
		// md5(MerId=xxx&OrdId=xxx&OrdAmt=xxx&PayType=DT&CurCode=CNY&BankCode=xxx&ProductInfo= xxx&Remark=xxx&ReturnURL=xxx&NotifyURL=xxx&SignType=MD5&MerKey=xxx)
		StringBuffer sb = new StringBuffer();
		sb.append("MerId=").append(merchant.getMerId());
		sb.append("&OrdId=").append(order.getOrderId());
		sb.append("&OrdAmt=").append(order.getOrderAmount());
		sb.append("&PayType=").append(payType);
		sb.append("&CurCode=").append(curCode);
		sb.append("&BankCode=").append(order.getBankCode());
		sb.append("&ProductInfo=").append(order.getProInfo());
		sb.append("&Remark=").append(order.getRemark());
		sb.append("&ReturnURL=").append(merchant.getReturnUrl());
		sb.append("&NotifyURL=").append(merchant.getNotifyUrl());
		sb.append("&SignType=").append(signType);
		sb.append("&MerKey=").append(merchant.getMd5Key());
		System.out.println("多多支付签名参数原文：" + sb);
		
		String sign = Encrypt.MD5(sb.toString());
		System.out.println("多多支付参数签名：" + sign);
		
		return sign;
	}
	/**
	 * 回调验签
	 * @param request
	 * @param merKey
	 * @return
	 */
	public static boolean checkSign(Map<String, Object> request, String merKey) {
		//MerId=xxx&OrdId=xxx&OrdAmt=xxx&OrdNo=xxx&ResultCode=xxx&Remark=xx&SignType=xxx
		StringBuffer sb = new StringBuffer();
		sb.append("MerId=").append(request.get(DuoAppConstants.callback_MerId).toString());
		sb.append("&OrdId=").append(request.get(DuoAppConstants.callback_OrdId).toString());
		sb.append("&OrdAmt=").append(request.get(DuoAppConstants.callback_OrdAmt).toString());
		sb.append("&OrdNo=").append(request.get(DuoAppConstants.callback_OrdNo).toString());
		sb.append("&ResultCode=").append(request.get(DuoAppConstants.callback_ResultCode).toString());
		sb.append("&Remark=").append(request.get(DuoAppConstants.callback_Remark).toString());
		sb.append("&SignType=").append(request.get(DuoAppConstants.callback_SignType).toString());
		System.out.println("多多回调签名参数原文：" + sb);
		
		String stream = Encrypt.MD5(sb.toString());
		String localSign = Encrypt.MD5(stream.concat(merKey));
		System.out.println("多多回调参数签名：" + localSign);
		
		String callbackSign = request.get(DuoAppConstants.callback_SignInfo).toString();
		return localSign.equals(callbackSign);
	}
	
	public static void main(String[] args) {
		BigDecimal pi = new BigDecimal("3.141592653");
		System.out.println(new DecimalFormat("#.##").format(pi));
	}
}