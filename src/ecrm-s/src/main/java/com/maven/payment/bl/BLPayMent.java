package com.maven.payment.bl;

import com.maven.payment.PayInterface;
import com.maven.payment.bl.BLAppConstants.BL_PayType;
import com.maven.util.RandomString;

public class BLPayMent<M extends BLMerchantConfig,O extends BLOrderConfig> implements PayInterface<M,O>{

	@Override
	public String save(BLMerchantConfig merchant, BLOrderConfig order) throws Exception {
		BLSave save = new BLSave();
		return save.getUrl(merchant, order);
	}

	@Override
	public String pay(BLMerchantConfig merchant, BLOrderConfig order) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		PayInterface<BLMerchantConfig, BLOrderConfig> pay = new BLPayMent<BLMerchantConfig, BLOrderConfig>();
		
		BLMerchantConfig merchant = new BLMerchantConfig();
		merchant.setMerCode("PG00023");
		merchant.setMd5Key("fdZD.9Gr.au3Y");
		merchant.setPayUrl("http://119.23.249.156:8080/pp_server/pay");
		merchant.setNotifyUrl("https://www.google.com.hk");
		merchant.setReturnUrl("https://www.google.com.ph");
		
		BLOrderConfig order = new BLOrderConfig();
		order.setAmount("1000");
		order.setCurrency("CNY");
		order.setOrderDesc("Description");
		order.setOrderNo(RandomString.UUID());
		order.setOrderTime(BLSave.getOrderTime());
		order.setPayType(BL_PayType.QQ钱包支付.value);
		order.setProductName("Recharge");
		
		try {
			pay.save(merchant, order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
