package com.maven.payment.ry.df;

import java.util.HashMap;
import java.util.Map;

import com.maven.payment.PayInterface;
import com.maven.util.HttpPostUtil;
import com.maven.util.JSONUnit;
/**
 * 如意代付接口
 * @author klay
 *
 * @param <M>
 * @param <O>
 */
public class RYdfPayMent<M extends RYdfMerchantConfig, O extends RYdfOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(RYdfMerchantConfig merchant, RYdfOrderConfig order) throws Exception {
		// nothing
		return null;
	}

	@Override
	public String pay(RYdfMerchantConfig merchant, RYdfOrderConfig order) throws Exception {
		RYdfPay pay = new RYdfPay();
		String url = pay.getUrl(merchant, order);
		String result = HttpPostUtil.doGetSubmit(url);
		System.out.println("如意代付请求响应结果原文：" + result);
		if(result.startsWith("[") && result.endsWith("]"))
			result = result.substring(1, result.length() - 1);
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap = JSONUnit.getMapFromJsonNew(result);
		if (resultMap.get("code") != null && resultMap.get("code").equals("1088")) {
			return PayInterface.PAY_SUCCESS;
		} else {
			return resultMap.get("message");
		}
	}
	
	public static void main(String[] args) {
		
	}
}