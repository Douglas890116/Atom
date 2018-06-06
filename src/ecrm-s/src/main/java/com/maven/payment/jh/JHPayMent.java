package com.maven.payment.jh;

import com.maven.payment.PayInterface;
import com.maven.util.RandomString;

public class JHPayMent<M extends JHMerchantConfig, O extends JHOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(JHMerchantConfig merchant, JHOrderConfig order) throws Exception {
		JHSave save = new JHSave();
		return save.save(merchant, order);
	}

	@Override
	public String pay(JHMerchantConfig merchant, JHOrderConfig order) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		PayInterface<JHMerchantConfig, JHOrderConfig> pay = new JHPayMent<JHMerchantConfig, JHOrderConfig>();
		
		JHMerchantConfig merchant = new JHMerchantConfig();
		merchant.setMerid("20170719003");
		merchant.setMd5Key("yWzJWrAjRCXwcmHbIF306Ph1ZrgiZcph");
//		merchant.setPayUrl("http://jhpay.yizhibank.com/api/createOrder");// APP
//		merchant.setPayUrl("http://jhpay.yizhibank.com/api/createPcOrder");// PC
		
		merchant.setPayUrl("http://jh.yizhibank.com/api/alipayAppOrder"); // H5、APP
//		merchant.setPayUrl("http://jh.yizhibank.com/api/createAlipayOrder");// PC
		merchant.setNotifyUrl("http://pay.99scm.com/TPayment/JHPayCallbakc");
		
		JHOrderConfig order = new JHOrderConfig();
		order.setOrderNo(JHAppConstants.getOrderNo());
		order.setOrderAmount("1.0");
		order.setOrderTime(JHAppConstants.getOrderTime());
		order.setNoncestr(RandomString.UUID());
		
		try {
			String url = pay.save(merchant, order);
			System.out.println(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}