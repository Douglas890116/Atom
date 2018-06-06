package com.maven.payment.wft;

import java.util.Map;

import com.hy.pull.common.util.MoneyHelper;
import com.maven.util.Encrypt;

public class WFTSave {
	// 默认参数
	private static String PayType = "DT";
	private static String CurCode = "CNY";
	private static String SignType = "MD5";
	
	public String getUrl(WFTMerchantConfig merchant, WFTOrderConfig order) {
		String sign = getSign(merchant, order);
		StringBuffer url = new StringBuffer(merchant.getPayUrl());
		url.append("?").append(WFTAppConstants.req_MerId).append("=").append(merchant.getMerId())
		   .append("&").append(WFTAppConstants.req_OrdId).append("=").append(order.getOrderId())
		   .append("&").append(WFTAppConstants.req_OrdAmt).append("=").append(MoneyHelper.doubleFormat(order.getOrderAmount()))
		   .append("&").append(WFTAppConstants.req_PayType).append("=").append(PayType)
		   .append("&").append(WFTAppConstants.req_CurCode).append("=").append(CurCode)
		   .append("&").append(WFTAppConstants.req_BankCode).append("=").append(order.getBankCode())
		   .append("&").append(WFTAppConstants.req_ProductInfo).append("=").append(order.getProductInfo())
		   .append("&").append(WFTAppConstants.req_Remark).append("=").append(order.getRemark())
		   .append("&").append(WFTAppConstants.req_ReturnURL).append("=").append(merchant.getReturnUrl())
		   .append("&").append(WFTAppConstants.req_NotifyURL).append("=").append(merchant.getNotifyUrl())
		   .append("&").append(WFTAppConstants.req_SignType).append("=").append(SignType)
		   .append("&").append(WFTAppConstants.req_SignInfo).append("=").append(sign);
		System.out.println("旺付通支付接口链接：" + url.toString());
		
		return url.toString();
	}
	/**
	 * 获取MD5签名
	 * @param merchant
	 * @param order
	 * @return
	 */
	private String getSign(WFTMerchantConfig merchant, WFTOrderConfig order) {
		StringBuffer sb = new StringBuffer();
		sb.append(WFTAppConstants.req_MerId).append("=").append(merchant.getMerId()).append("&")
		  .append(WFTAppConstants.req_OrdId).append("=").append(order.getOrderId()).append("&")
		  .append(WFTAppConstants.req_OrdAmt).append("=").append(MoneyHelper.doubleFormat(order.getOrderAmount())).append("&")
		  .append(WFTAppConstants.req_PayType).append("=").append(PayType).append("&")
		  .append(WFTAppConstants.req_CurCode).append("=").append(CurCode).append("&")
		  .append(WFTAppConstants.req_BankCode).append("=").append(order.getBankCode()).append("&")
		  .append(WFTAppConstants.req_ProductInfo).append("=").append(order.getProductInfo()).append("&")
		  .append(WFTAppConstants.req_Remark).append("=").append(order.getRemark()).append("&")
		  .append(WFTAppConstants.req_ReturnURL).append("=").append(merchant.getReturnUrl()).append("&")
		  .append(WFTAppConstants.req_NotifyURL).append("=").append(merchant.getNotifyUrl()).append("&")
		  .append(WFTAppConstants.req_SignType).append("=").append(SignType).append("&")
		  .append("MerKey=").append(merchant.getMd5Key());
		System.out.println("旺付通签名原文：" + sb.toString());
		
		return Encrypt.MD5(sb.toString());
	}
	/**
	 * 回调验签
	 * @param request
	 * @param md5Key
	 * @return
	 */
	public static boolean checkCallbackSign(Map<String, Object> request, String md5Key) {
		StringBuffer sb = new StringBuffer();
		sb.append(WFTAppConstants.callback_MerId).append("=").append(request.get(WFTAppConstants.callback_MerId)).append("&")
		  .append(WFTAppConstants.callback_OrdId).append("=").append(request.get(WFTAppConstants.callback_OrdId)).append("&")
		  .append(WFTAppConstants.callback_OrdAmt).append("=").append(request.get(WFTAppConstants.callback_OrdAmt)).append("&")
		  .append(WFTAppConstants.callback_OrdNo).append("=").append(request.get(WFTAppConstants.callback_OrdNo)).append("&")
		  .append(WFTAppConstants.callback_ResultCode).append("=").append(request.get(WFTAppConstants.callback_ResultCode)).append("&")
		  .append(WFTAppConstants.callback_Remark).append("=").append(request.get(WFTAppConstants.callback_Remark)).append("&")
		  .append(WFTAppConstants.callback_SignType).append("=").append(request.get(WFTAppConstants.callback_SignType));
		System.out.println("旺付通回调签名原文：" + sb.toString());
		String signInfo = Encrypt.MD5(Encrypt.MD5(sb.toString()).concat(md5Key));
		String callbackSign = request.get(WFTAppConstants.callback_SignInfo).toString();
		
		return signInfo.equals(callbackSign);
	}
}