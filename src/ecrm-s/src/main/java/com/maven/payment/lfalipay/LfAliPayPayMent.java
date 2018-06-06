package com.maven.payment.lfalipay;

import com.maven.payment.PayInterface;
import com.maven.util.RandomString;

public class LfAliPayPayMent<M extends LfAliPayMerchantConfig, O extends LfAliPayOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(LfAliPayMerchantConfig merchant, LfAliPayOrderConfig order) throws Exception {
		LfAliPaySave save = new LfAliPaySave();
		return save.getUrl(merchant, order);
	}

	@Override
	public String pay(LfAliPayMerchantConfig merchant, LfAliPayOrderConfig order) throws Exception {
		// 
		return null;
	}

	public static void main(String[] args) {
		PayInterface<LfAliPayMerchantConfig, LfAliPayOrderConfig> payment = new LfAliPayPayMent<LfAliPayMerchantConfig, LfAliPayOrderConfig>();
		// 商户信息
		LfAliPayMerchantConfig merchant = new LfAliPayMerchantConfig();
		merchant.setMerNo("100671");
		merchant.setPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMcRww57ecNBtZ1Wu1clzkNgNl97MGqUQ8ECVLys5WX30bfeRBpaJCX2x9Ia8EqI99QTDJjrwvnpOIFNo3qkNaL2L73PYo++bFUDVLpyUGK6R5eT7Z2gt8DY904Vni5ZRFeS7kn9a+dmHUcX9t2rXCpfrks5YQ6B91hcH4vX6eLwIDAQAB");
		merchant.setMerPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCtg/GN8y5BJpiW4rlOGHKc7XktpJ5U0cbMeAND8UnQbF+nRwZ6iKWJ5WDVYLaiF4Gy03rMn3TG02Js8pfROVWZzXNQJggUjlYTxDJUfBcTZIh25Q424zmtVO1JxiU8LuEGar63qOm4Fhb6AdJO1vInk9OSnyCWRqebSujzbAO3AwIDAQAB");
		merchant.setMerPrivateKey("MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAK2D8Y3zLkEmmJbiuU4YcpzteS2knlTRxsx4A0PxSdBsX6dHBnqIpYnlYNVgtqIXgbLTesyfdMbTYmzyl9E5VZnNc1AmCBSOVhPEMlR8FxNkiHblDjbjOa1U7UnGJTwu4QZqvreo6bgWFvoB0k7W8ieT05KfIJZGp5tK6PNsA7cDAgMBAAECgYBiBkhQiTF4XPWXSD6nf+g//2iWAInyIRDOMn6lw4qP1Km1NsrSLqBWZt9trFWA/QaooBCfyPWP3ZudMO/TozBX43W5vGmuiDtjADFY8u6JhNQF+0uMqu22YEiOGN9WXdaWYkuvvTvKiUynS3XWuafgf1t23ec5GsepCInpZgimAQJBAO3yP50zQMmjSiOtkW9Fb559TWKuAg5mmVSF1sLauwzKO8pP6Y/J2jY/lcFyByeyfSd3cUw7tBvlrSTjKf1BIhkCQQC6rjitwiphD9x3AH6mi02RnxyR9tSl9LNvQ4Fij+AS9CKEUXlLDMjRvdTtpNsouyuXF2KkVOd2pSPNm62qJ517AkEA0mSM0xbqhmAXwgxKuDSRSXZJ4qMxtkIJ+a6OAqUdF2YKEA0w268Df0whwnZs1TEBcNAwIdP4oWIoAqHKEuBwoQJBAJJ//PznfmiTkPlWyw2aDrK0AjDOWw0t3s73VAdsT3WwX5IleiGak9J9vicNE+yADnJRKNk7xDXI3TMS9BOvaRcCQQCeTOLqjX4HhJmCAoYmgjNAsnMUIG758sMZa24qCU0NxHHyiaux+hS12YqHBb9VTJtgd+FjFkJrsF+8OsBIS0fH");
		merchant.setPayUrl("http://service.lepayle.com/api/gateway");
		merchant.setReturnUrl("http://api.hyzonghe.net/TPayment/RYPayCallback");
		
		// 订单信息
		LfAliPayOrderConfig order = new LfAliPayOrderConfig();
		order.setAmount("1.00");
		order.setOrderNo(RandomString.UUID());
		order.setRemark("GoldEggRemark");
		order.setSubBody("VirtualCurrency");
		order.setSubject("Recharge");
		
		try {
			System.out.println(payment.save(merchant, order));;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}