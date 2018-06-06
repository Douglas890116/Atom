package com.maven.payment.ys.df;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.maven.payment.PayInterface;
import com.maven.payment.ys.SignUtils;

public class YSDFPayMent<M extends YSDFMerchantConfig, O extends YSDFOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(YSDFMerchantConfig merchant, YSDFOrderConfig order) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String pay(YSDFMerchantConfig merchant, YSDFOrderConfig order) throws Exception {
		YSDFPay pay = new YSDFPay();
		return pay.doPay(merchant, order);
	}

	public static void main(String[] args) {
		PayInterface<YSDFMerchantConfig, YSDFOrderConfig> payment = new YSDFPayMent<YSDFMerchantConfig, YSDFOrderConfig>();
		
		YSDFMerchantConfig merchant = new YSDFMerchantConfig();
		merchant.setMerId("sf_test");
		merchant.setPrivateKeyUrl("F:\\项目文档\\功能需求\\第三方支付\\银盛支付\\深分测试账号\\sf_test.pfx");
		merchant.setPublicKeyUrl("F:\\项目文档\\功能需求\\第三方支付\\银盛支付\\深分测试账号\\businessgate.cer");
		merchant.setPassword("123456");
		merchant.setBusinessCode("01000009");
		merchant.setMethod("ysepay.df.single.normal.accept");
		merchant.setPayUrl("https://mertest.ysepay.com/openapi_dsf_gateway/gateway.do");
		merchant.setNotifyUrl("http://pay.99scm.com/TPayment/YSDFPayCallback");
		
		YSDFOrderConfig order = new YSDFOrderConfig();
		order.setOrderNo(SignUtils.getOrderNo());
		order.setOrderAmount("1.0");
		order.setOrderTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		order.setOrderSubject("Recharge");
		order.setBankName("招商银行");
		order.setBankProvince("北京市");
		order.setBankCity("北京市");
		order.setBankAccountNo("1593574568527624");
		order.setBankAccountName("测试");
		order.setBankAccountType("personal");
		order.setBankCardType("debit");
		order.setBankTelephoneNo("18612891504");
		
		try {
			payment.pay(merchant, order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
