package com.maven.payment.xingfu;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.maven.payment.PayInterface;
import com.maven.payment.xingfu.XingFAppConstants.Enum_XingFService;
import com.maven.util.RandomString;

public class XingFPayMent<M extends XingFMerchantConfig, O extends XingFOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(XingFMerchantConfig merchant, XingFOrderConfig order) throws Exception {
		XingFSave save = new XingFSave();
		return save.getUrl(merchant, order);
	}

	@Override
	public String pay(XingFMerchantConfig merchant, XingFOrderConfig order) throws Exception {
		return null;
	}

	public static void main(String[] args) {
		PayInterface<XingFMerchantConfig, XingFOrderConfig> payment = new XingFPayMent<XingFMerchantConfig, XingFOrderConfig>();
		
		XingFMerchantConfig merchant = new XingFMerchantConfig();
		merchant.setService(Enum_XingFService.扫码支付.value);
		merchant.setMerId("2017092161012020");
		merchant.setMerKey("7fb58f1678284538f056dfc87616f864");
		merchant.setPayUrl("https://gate.lfbpay.com/cooperate/gateway.cgi");
		merchant.setReturnUrl("http://api.hyzonghe.net/TPayment/success");
		merchant.setNotifyUrl("http://api.hyzonghe.net/TPayment/XingFCallback");
		merchant.setExpireTime("300");
		
		XingFOrderConfig order = new XingFOrderConfig();
		order.setOrderNo(RandomString.UUID());
		order.setOrderAmount("10.0");
		order.setOrderDate(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		order.setClientIP("180.232.108.150");
		order.setBankId("4");
		order.setExtra("Recharge");
		order.setOrderSummary("Recharge");
		
		try {
			String url = payment.save(merchant, order);
			System.out.println("扫码地址: " + url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}