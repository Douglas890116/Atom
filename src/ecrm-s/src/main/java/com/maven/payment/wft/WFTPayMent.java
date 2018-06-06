package com.maven.payment.wft;

import com.maven.payment.PayInterface;
import com.maven.util.RandomString;

public class WFTPayMent<M extends WFTMerchantConfig, O extends WFTOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(WFTMerchantConfig merchant, WFTOrderConfig order) throws Exception {
		WFTSave save = new WFTSave();
		return save.getUrl(merchant, order);
	}

	@Override
	public String pay(WFTMerchantConfig merchant, WFTOrderConfig order) throws Exception {
		return null;
	}

	public static void main(String[] args) {
		PayInterface<WFTMerchantConfig,  WFTOrderConfig> pay = new WFTPayMent<WFTMerchantConfig,  WFTOrderConfig>();
		// 商户信息
		WFTMerchantConfig merchant = new WFTMerchantConfig();
		merchant.setMerId("6900945");
		merchant.setMd5Key("x5GW8TKTnYJWDbSAdc5jABxdMPFNG6Zy");
		merchant.setPayUrl("http://cashier.1pagateway.com/payment/");
		merchant.setReturnUrl("http://api.hyzonghe.net/TPayment/success");
		merchant.setNotifyUrl("http://api.hyzonghe.net/TPayment/WFTPayCallback");
		// 订单信息
		WFTOrderConfig order = new WFTOrderConfig();
		order.setOrderId("TEST"+RandomString.UUID().substring(4));
		order.setOrderAmount("5.00");
		order.setBankCode("UNIONPAY");
		order.setProductInfo("Name");
		order.setRemark("20");
		try {
			pay.save(merchant, order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
