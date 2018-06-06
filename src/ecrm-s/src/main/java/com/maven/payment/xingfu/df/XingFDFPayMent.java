package com.maven.payment.xingfu.df;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.maven.payment.PayInterface;
import com.maven.util.RandomString;

public class XingFDFPayMent<M extends XingFDFMerchantConfig, O extends XingFDFOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(XingFDFMerchantConfig merchant, XingFDFOrderConfig order) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String pay(XingFDFMerchantConfig merchant, XingFDFOrderConfig order) throws Exception {
		XingFDFPay pay = new XingFDFPay();
		return pay.doPay(merchant, order);
	}

	public static void main(String[] args) {
		PayInterface<XingFDFMerchantConfig, XingFDFOrderConfig> payment = new XingFDFPayMent<XingFDFMerchantConfig, XingFDFOrderConfig>();
		
		XingFDFMerchantConfig merchant = new XingFDFMerchantConfig();
		merchant.setService("TRADE.SETTLE");
		merchant.setMerId("2017092161012020");
		merchant.setMerKey("7fb58f1678284538f056dfc87616f864");
		merchant.setPayUrl("https://gate.lfbpay.com/cooperate/gateway.cgi");
		merchant.setNotifyUrl("http://api.hyzonghe.net/TPayment/XingFCallback");
		
		XingFDFOrderConfig order = new XingFDFOrderConfig();
		order.setOrderNo(RandomString.UUID());
		order.setOrderAmount("1.0");
		order.setOrderDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		
		order.setBankId("CMB");
		order.setBankCardNo("21349846135164879512");
		order.setBankCardName("测试");
		order.setBankName("bankName");
		
		order.setExtra("自动出款");
		order.setOrderSummary("DF");
		
		try {
			payment.pay(merchant, order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
