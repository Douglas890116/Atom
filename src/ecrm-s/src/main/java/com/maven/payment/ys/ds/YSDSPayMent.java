package com.maven.payment.ys.ds;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.maven.payment.PayInterface;
import com.maven.payment.ys.SignUtils;

public class YSDSPayMent<M extends YSDSMerchantConfig, O extends YSDSOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(YSDSMerchantConfig merchant, YSDSOrderConfig order) throws Exception {
		YSDSSave save = new YSDSSave();
		return save.getUrl(merchant, order);
	}

	@Override
	public String pay(YSDSMerchantConfig merchant, YSDSOrderConfig order) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		PayInterface<YSDSMerchantConfig, YSDSOrderConfig> payment = new YSDSPayMent<YSDSMerchantConfig, YSDSOrderConfig>();
		
		YSDSMerchantConfig merchant = new YSDSMerchantConfig();
		merchant.setMerId("sf_test");
		merchant.setPrivateKeyUrl("F:\\项目文档\\功能需求\\第三方支付\\银盛支付\\深分测试账号\\sf_test.pfx");
		merchant.setPublicKeyUrl("F:\\项目文档\\功能需求\\第三方支付\\银盛支付\\深分测试账号\\businessgate.cer");
		merchant.setPassword("123456");
		merchant.setBusinessCode("01000010");
		merchant.setMethod("ysepay.ds.single.quick.accept");
		merchant.setPayUrl("https://mertest.ysepay.com/openapi_dsf_gateway/gateway.do");
		merchant.setNotifyUrl("http://pay.99scm.com/TPayment/YSDSPayCallback");
		
		YSDSOrderConfig order = new YSDSOrderConfig();
		order.setOrderNo(SignUtils.getOrderNo());
		order.setOrderAmount("1.0");
		order.setOrderTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		order.setOrderSubject("Recharge");
		order.setBankName("");
		order.setBankProvince("");
		order.setBankCity("");
		order.setBankAccountNo("");
		order.setBankAccountName("");
		order.setBankCardType("credit");
		order.setBankAccountType("personal");
		order.setBankTelephoneNo("18612891504");
		
		try {
			payment.save(merchant, order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
