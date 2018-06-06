package com.maven.payment.ap;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.maven.payment.PayInterface;
import com.maven.payment.ap.AstropayAppConstants.Astro_PayType;
import com.maven.util.JSONUnit;
import com.maven.util.RandomString;

public class AstropayPayMent<M extends AstropayMerchantConfig,O extends AstropayOrderConfig> implements PayInterface<M, O> {
	/**
	 * 支付接口
	 */
	@Override
	public String save(AstropayMerchantConfig merchant, AstropayOrderConfig order) throws Exception {
		AstropaySave save = new AstropaySave();
		String responseStr = save.doPay(merchant, order);
		if (StringUtils.isNotBlank(responseStr)) {
			Map<String, String> responseMap = JSONUnit.getMapFromJsonNew(responseStr);
			String transCode = responseMap.get(AstropayAppConstants.rep_response_code);
			String transMsg  = responseMap.get(AstropayAppConstants.rep_response_reason_text);
			if ("1".equals(transCode)) {
				System.out.println("支付成功msg : " + transMsg);
				String md5Hash = responseMap.get(AstropayAppConstants.rep_md5_hash);
				String transactionId = responseMap.get(AstropayAppConstants.rep_TransactionID);
				String amount = responseMap.get(AstropayAppConstants.rep_x_amount);
				boolean status = save.checkSign(merchant.getMerLogin(), transactionId, amount, md5Hash);
				if (status) {
					return PayInterface.PAY_SUCCESS;
				} else {
					System.err.println("验签失败：" + md5Hash);
				}
			} else {
				System.err.println("支付失败code : " + transCode);
				System.err.println("支付失败msg  : " + transMsg);
			}
		}
		return "FAIL";
	}
	/**
	 * 出款接口
	 */
	@Override
	public String pay(AstropayMerchantConfig merchant, AstropayOrderConfig order) throws Exception {
		AstropaySave save = new AstropaySave();
		String responseStr = save.doCashOut(merchant, order);
		if (StringUtils.isNotBlank(responseStr) && responseStr.startsWith("{")) {
			Map<String, String> responseMap = JSONUnit.getMapFromJsonNew(responseStr);
			String transCode = responseMap.get(AstropayAppConstants.rep_code);
			String transMsg  = responseMap.get(AstropayAppConstants.rep_message);
			if ("200".equals(transCode)) {
				System.out.println("出款成功msg : " + transMsg);
				String SHA1Sign = responseMap.get(AstropayAppConstants.rep_control);
				String id_cashout = responseMap.get(AstropayAppConstants.rep_id_cashout);
				String amount = responseMap.get(AstropayAppConstants.rep_amount);
				String x_currency = responseMap.get(AstropayAppConstants.rep_currency);
				boolean status = save.checkSign(merchant, order, id_cashout, amount, x_currency, SHA1Sign);
				if (status) {
					return PayInterface.PAY_SUCCESS;
				} else {
					System.err.println("验签失败：" + SHA1Sign);
				}
			} else {
				System.err.println("出款失败code : " + transCode);
				System.err.println("出款失败msg  : " + transMsg);
			}
		}
		return "FAIL";
	}

	public static void main(String[] args) {
		PayInterface<AstropayMerchantConfig, AstropayOrderConfig> payment = new AstropayPayMent<AstropayMerchantConfig, AstropayOrderConfig>();
		
		AstropayMerchantConfig merchant = new AstropayMerchantConfig();
		// 生产环境
//		merchant.setMerLogin("rGWh3HuoeT8FYFsXNp0skreoRqjBFZMK");
//		merchant.setMerTransKey("9bwQ259H0dhNektwg9bS7weA9lDUxe7Z");
//		merchant.setMerSecretKey("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
//		merchant.setPayUrl("https://api.astropaycard.com/verif/validator");
//		merchant.setNotifyUrl("http://api.hyzonghe.net/TPayment/AstropayCallback");
//		merchant.setReturnUrl("http://api.hyzonghe.net/TPayment/success");
		// 测试环境
		merchant.setMerLogin("ghzB9ScBwZAIyjlhA13AwFV8GOYS2dDw");
		merchant.setMerTransKey("exZ0NA6nNvGFaxBMvuofxc8Iu0sgEET4");
		merchant.setMerSecretKey("pfVIeYVNxBNvKOjbZhdj9k6ljPlCq6qF");
		merchant.setPayUrl("https://sandbox-api.astropaycard.com/verif/validator");
		merchant.setNotifyUrl("http://api.hyzonghe.net/TPayment/AstropayCallback");
		merchant.setReturnUrl("http://api.hyzonghe.net/TPayment/success");
		
		AstropayOrderConfig order = new AstropayOrderConfig();
		order.setOrderNo(RandomString.UUID());
		order.setCurrency("RMB");
		order.setOrderAmount("10.00");
		order.setOrderType(Astro_PayType.AUTH_CAPTURE.value);
		order.setUserNo("E0000000");
		order.setUserCardNum("1615185404865127");
		order.setUserCardCode("5071");
		order.setUserCardExpDate("08/2018");
		
		order.setUserEmail("cashout_302@mail.com");
		order.setUserName("John Smith");
		order.setUserDocument("L02-04-178005");
		order.setUserCountry("BR");
		
		
		try {
//			String result = payment.save(merchant, order);
			String result = payment.pay(merchant, order);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}