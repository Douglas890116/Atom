package com.maven.payment.duo;

import com.maven.payment.PayInterface;
import com.maven.util.RandomString;

public class DuoPayMent<M extends DuoMerchantConfig, O extends DuoOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(DuoMerchantConfig merchant, DuoOrderConfig order) throws Exception {
		DuoUtil save = new DuoUtil();
		return save.getUrl(merchant, order);
	}

	@Override
	public String pay(DuoMerchantConfig merchant, DuoOrderConfig order) throws Exception {
		return null;
	}

	public static void main(String[] args) {
		PayInterface<DuoMerchantConfig, DuoOrderConfig> payment = new DuoPayMent<DuoMerchantConfig, DuoOrderConfig>();
		
		DuoMerchantConfig merchant = new DuoMerchantConfig();
		// 多多支付FC044
		merchant.setMerId("90168452");
		merchant.setMd5Key("JdCGSZQpQ3fASCf3ZeYm8hrjRxRkiMSx");
		merchant.setPayUrl("http://cashier.duoduo999.com/payment/");
		merchant.setNotifyUrl("http://api.hyzonghe.net/TPayment/DuoCallback");
		merchant.setReturnUrl("http://api.hyzonghe.net/TPayment/success");
		
		DuoOrderConfig order = new DuoOrderConfig();
		order.setOrderId("TEST".concat(RandomString.UUID().substring(4)));
		order.setOrderAmount("5.00");
		order.setBankCode("QQWALLET");
		order.setProInfo("Recharge");
		order.setRemark("Remark");
		
		try {
			payment.save(merchant, order);
		} catch (Exception e) {
			e.printStackTrace();
		};
	}
}