package com.maven.payment.jh.quick;

import com.maven.payment.PayInterface;
import com.maven.payment.kk.KKAppConstants;

public class JHQuickPayPayMent<M extends JHQuickPayMerchantConfig, O extends JHQuickPayOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(JHQuickPayMerchantConfig merchant, JHQuickPayOrderConfig order) throws Exception {
		JHQuickPaySave save = new JHQuickPaySave();
		return save.getUrl(merchant, order);
	}

	@Override
	public String pay(JHQuickPayMerchantConfig merchant, JHQuickPayOrderConfig order) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		PayInterface<JHQuickPayMerchantConfig, JHQuickPayOrderConfig> payment = new JHQuickPayPayMent<JHQuickPayMerchantConfig, JHQuickPayOrderConfig>();
		
		JHQuickPayMerchantConfig merchant = new JHQuickPayMerchantConfig();
		merchant.setPayKey("e4e4dcf01b07452aa26a47ed95e9d583");
		merchant.setMd5Key("495f5c2f19c14d82a3a08c3dbb17a249");
		merchant.setProductType("40000303");
		merchant.setPayUrl("http://gateway.kekepay.com/quickGateWayPay/initPay");
		merchant.setReturnUrl("http://api.hyzonghe.net/TPayment/success");
		merchant.setNotifyUrl("http://api.hyzonghe.net/JHQuickPayCallback");
		
		JHQuickPayOrderConfig order = new JHQuickPayOrderConfig();
		order.setOrderNo(KKAppConstants.getOrderNo());
		order.setOrderAmount("5.0");
		order.setBankNo("123456789");
		order.setOrderName("Recharge");
		order.setOrderTime(JHQuickPaySave.getOrderTime());
		order.setOrderIP("180.232.108.150");
		try {
			payment.save(merchant, order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
