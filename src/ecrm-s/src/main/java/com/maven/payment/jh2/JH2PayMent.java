package com.maven.payment.jh2;

import com.maven.payment.PayInterface;
import com.maven.util.RandomString;

public class JH2PayMent<M extends JH2MerchantConfig, O extends JH2OrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(JH2MerchantConfig merchant, JH2OrderConfig order) throws Exception {
		JH2Util save = new JH2Util();
		return save.getUrl(merchant, order);
	}

	@Override
	public String pay(JH2MerchantConfig merchant, JH2OrderConfig order) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		PayInterface<JH2MerchantConfig, JH2OrderConfig> payment = new JH2PayMent<JH2MerchantConfig, JH2OrderConfig>();
		
		JH2MerchantConfig merchant = new JH2MerchantConfig();
		merchant.setPayKey("463d1877364b48389898789a01fbc8df");
		merchant.setMerKey("9bea5280411145cca688acbc383a9619");
//		merchant.setPayUrl("http://gateway.zgmyc.top/b2cPay/initPay");// 网银支付url
		merchant.setPayUrl("http://gateway.zgmyc.top/cnpPay/initPay");// 扫码支付url
		merchant.setProductType("70000103");
		merchant.setReturnUrl("http://api.hyzonghe.net/TPayment/success");
		merchant.setNotifyUrl("http://api.hyzonghe.net/TPayment/JH2Callback");
		
		JH2OrderConfig order = new JH2OrderConfig();
		order.setOrderNo("TEST" + RandomString.UUID().substring(6));
		order.setOrderAmount("5.0");
		order.setOrderTime(JH2Util.getOrderTime());
		order.setOrderIP("180.232.108.150");
		order.setProductName("RCHARGE");
		order.setRemark("REMARK");
		
//		order.setBankAccountType("PRIVATE_DEBIT_ACCOUNT");
//		order.setBankCode("ICBC");
//		order.setMobile("");
		
		try {
			String result = payment.save(merchant, order);
			System.out.println("扫码地址：" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
