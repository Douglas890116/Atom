package com.maven.payment.jh.h5;

import com.maven.payment.PayInterface;
import com.maven.util.RandomString;

public class JHH5PayMent<M extends JHH5MerchantConfig, O extends JHH5OrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(JHH5MerchantConfig merchant, JHH5OrderConfig order) throws Exception {
		JHH5Save save = new JHH5Save();
		return save.getUrl(merchant, order);
	}

	@Override
	public String pay(JHH5MerchantConfig merchant, JHH5OrderConfig order) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		PayInterface<JHH5MerchantConfig, JHH5OrderConfig> payment = new JHH5PayMent<JHH5MerchantConfig, JHH5OrderConfig>();
		
		JHH5MerchantConfig merchant = new JHH5MerchantConfig();
		merchant.setMerId("20170811143015025");
		merchant.setAppId("jhb5y943hz158wlp666");
		merchant.setMd5Key("jlkL2wx85z7GNm9DoXVtQxxwgQ0G4Tex");
		merchant.setPayUrl("https://test.juhebaopay.com:8081/jhb-cashier/cashier/payGateWay");
		merchant.setReturnUrl("http://pay.99scm.com/TPayment/success");
		merchant.setNotifyUrl("http://pay.99scm.com/TPayment/JHH5Callback");
		
		JHH5OrderConfig order = new JHH5OrderConfig();
		order.setOrderId(RandomString.UUID());
		order.setOrderAmount("1000");
		order.setOrderUserId("E000000");
		order.setSubjectNo("777");
		order.setSubject("Recharge");
		
		try {
			payment.save(merchant, order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
