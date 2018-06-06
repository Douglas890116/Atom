package com.maven.payment.wft.df;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.maven.payment.PayInterface;
import com.maven.util.RandomString;

public class WFTDFPayMent<M extends WFTDFMerchantConfig, O extends WFTDFOrderConfig> implements PayInterface<M, O> {


	@Override
	public String save(WFTDFMerchantConfig merchant, WFTDFOrderConfig order) throws Exception {
		return null;
	}

	@Override
	public String pay(WFTDFMerchantConfig merchant, WFTDFOrderConfig order) throws Exception {
		WFTDFPay pay = new WFTDFPay();
		return pay.doPay(merchant, order);
	}

	public static void main(String[] args) {
		PayInterface<WFTDFMerchantConfig, WFTDFOrderConfig> payment = new WFTDFPayMent<WFTDFMerchantConfig, WFTDFOrderConfig>();

		WFTDFMerchantConfig merchant = new WFTDFMerchantConfig();
		merchant.setMerId("");
		merchant.setPayUrl("");
		merchant.setAESKey("");
		merchant.setPublicRSAKey("");
		merchant.setPrivateRSAKey("");
		merchant.setNotifyUrl("http://api.jdpayment.com/TPayment/WFTPayCallback");
		
		WFTDFOrderConfig order = new WFTDFOrderConfig();
		order.setOrderId("TEST"+RandomString.UUID().substring(4));
		order.setOrderAmount("10.0");
		order.setOrderTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		order.setUserName("测试姓名");
		order.setCardNum("6226090289998888");
		order.setBankCode("CMB");
		order.setBankProvince("地球");
		order.setBankCity("地球");
		order.setBankBranch("招商银行");
		
		try {
			payment.pay(merchant, order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}