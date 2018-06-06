package com.maven.payment.ek;

import com.maven.payment.PayInterface;
import com.maven.util.RandomString;

public class EKPayMent<M extends EKMerchantConfig, O extends EKOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(M merchant, O order) throws Exception {
		EKSave save = new EKSave();
		return save.getUrl(merchant, order);
	}

	@Override
	public String pay(M merchant, O order) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		PayInterface<EKMerchantConfig, EKOrderConfig> pay = new EKPayMent<EKMerchantConfig, EKOrderConfig>();
		
		EKMerchantConfig merchant = new EKMerchantConfig();
		merchant.setParter("1717");
		merchant.setMd5Key("500a5d1706fa4f3fa9393488a7b6d26e");
		merchant.setPayUrl("http://www.1983game.com/Bank/");
		merchant.setNotifyUrl("http://pay.99scm.com/TPayment/EKPayCallback");
		merchant.setReturnUrl("http://pay.99scm.com/TPayment/success");
		
		EKOrderConfig order = new EKOrderConfig();
		order.setOrderId(RandomString.UUID().substring(2));
		order.setOrderAmount("1.0");
		order.setBankType("963");
		order.setIP("180.232.108.150");
		order.setAttach("TEST");
		
		try {
			pay.save(merchant, order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}