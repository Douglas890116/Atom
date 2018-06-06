package com.maven.payment.ry;

import java.util.Map;

import com.hy.pull.common.util.Encrypt;

public class RYSave {
	/**
	 * 获取支付接口
	 * @param merchant
	 * @param order
	 * @return
	 */
	public String getUrl(RYMerchantConfig merchant,RYOrderConfig order) {
		String sign = getRequestSign(merchant, order);
		StringBuffer sb = new StringBuffer(merchant.getPayUrl());
		sb.append("?merId=").append(merchant.getMerId())
		  .append("&merOrdId=").append(order.getMerOrdId())
		  .append("&merOrdAmt=").append(order.getMerOrdAmt())
		  .append("&payType=").append(order.getPayType())
		  .append("&bankCode=").append(order.getBankCode())
		  .append("&remark=").append(order.getRemark())
		  .append("&returnUrl=").append(merchant.getReturnUrl())
		  .append("&notifyUrl=").append(merchant.getNotifyUrl())
		  .append("&signType=").append("MD5")
		  .append("&signMsg=").append(sign);
		String apiUrl = sb.toString();
		System.out.println("如意支付接口链接：" + apiUrl);

		return apiUrl;
	}
//	/**
//	 * 微信支付宝支付返回扫码图片或地址
//	 * 但，貌似没什么用！
//	 * @param apiUrl
//	 * @return
//	 */
//	private static String getQRUrl(String apiUrl) {
//		System.out.println("如意微信支付宝，完整支付链接：\n" + apiUrl);
//		String result = HttpPostUtil.doPostSubmit(apiUrl);
//		if (StringUtils.isBlank(result)) return "";
//        if (result.startsWith("[") && result.endsWith("]"))
//            result = result.substring(1, result.length() - 1);
//        Map<String, String> resultMap = new HashMap<String, String>();
//        resultMap = JSONUnit.getMapFromJsonNew(result);
//        if (StringUtils.isNotBlank(resultMap.get("retCode")) && resultMap.get("retCode").equals("SUCCESS")) {
//			return resultMap.get("retUrl");
//		}
//        return "";
//	}
	/**
	 * 加密签名
	 * @param merchant
	 * @param order
	 * @return
	 */
	public static String getRequestSign(RYMerchantConfig merchant,RYOrderConfig order) {
		StringBuffer sb = new StringBuffer();
		/*下单 接口 签名规则
			md5(
				merId=xxx
				&merOrdId=xxx
				&merOrdAmt=xxx
				&payType=xxx
				&bankCode=xxx
				&remark=xxx
				&returnUrl=xxx
				&notifyUrl=xxx
				&signType=MD5
				&merKey=xxx)
		*/
		sb.append("merId=").append(merchant.getMerId())
		  .append("&merOrdId=").append(order.getMerOrdId())
		  .append("&merOrdAmt=").append(order.getMerOrdAmt())
		  .append("&payType=").append(order.getPayType())
		  .append("&bankCode=").append(order.getBankCode())
		  .append("&remark=").append(order.getRemark())
		  .append("&returnUrl=").append(merchant.getReturnUrl())
		  .append("&notifyUrl=").append(merchant.getNotifyUrl())
		  .append("&signType=").append("MD5")
		  .append("&merKey=").append(merchant.getMerKey());
		String data = sb.toString();
		System.out.println("如意支付签名原文：" + data);
		String md5Sign = Encrypt.MD5(data);
		System.out.println("如意支付签名：" + md5Sign);
		return md5Sign;
	}
	/**
	 * 验证回调参数
	 * @param map
	 * @return
	 */
	public static boolean checkReponseParams(Map<String, Object> map, String merKey) {
		StringBuffer sb = new StringBuffer();
		// 支付结果签名约定：
		/* MD5(
			merId=xxx
			&merOrdId=xxx
			&merOrdAmt=xxx
			&sysOrdId=xxx
			&tradeStatus=xxx
			&remark=xxx
			&signType=xxx
			&merKey=xxx
			)
		*/	
		sb.append("merId=").append(map.get(RYAppConstants.callback_merId))
		  .append("&merOrdId=").append(map.get(RYAppConstants.callback_merOrdId))
		  .append("&merOrdAmt=").append(map.get(RYAppConstants.callback_merOrdAmt))
		  .append("&sysOrdId=").append(map.get(RYAppConstants.callback_sysOrdId))
		  .append("&tradeStatus=").append(map.get(RYAppConstants.callback_tradeStatus))
		  .append("&remark=").append(map.get(RYAppConstants.callback_remark))
		  .append("&signType=").append(map.get(RYAppConstants.callback_signType))
		  .append("&merKey=").append(merKey);
		String data = sb.toString();
		System.out.println("如意支付回调签名原文：" + data);
		String md5Sign = Encrypt.MD5(data);
		System.out.println("如意支付回调参数签名：" + md5Sign);
		return md5Sign.equals(map.get(RYAppConstants.callback_signMsg));
	}
	
	//==================================================如意相关方法==================================================//
	
	
}