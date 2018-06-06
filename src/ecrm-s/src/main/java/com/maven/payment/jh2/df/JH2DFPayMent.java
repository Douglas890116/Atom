package com.maven.payment.jh2.df;

import com.maven.payment.PayInterface;
import com.maven.util.RandomString;

public class JH2DFPayMent<M extends JH2DFMerchantConfig, O extends JH2DFOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(JH2DFMerchantConfig merchant, JH2DFOrderConfig order) throws Exception {
		return null;
	}

	@Override
	public String pay(JH2DFMerchantConfig merchant, JH2DFOrderConfig order) throws Exception {
		JH2DFUtil pay = new JH2DFUtil();
		return pay.doPay(merchant, order);
	}

	public static void main(String[] args) {
		PayInterface<JH2DFMerchantConfig, JH2DFOrderConfig> payment = new JH2DFPayMent<JH2DFMerchantConfig, JH2DFOrderConfig>();

		JH2DFMerchantConfig merchant = new JH2DFMerchantConfig();
		merchant.setPayKey("463d1877364b48389898789a01fbc8df");
		merchant.setMerKey("9bea5280411145cca688acbc383a9619");
		merchant.setPayUrl("http://gateway.zgmyc.top/accountProxyPay/initPay");
		merchant.setProxyType("T0");
		merchant.setProductType("QUICKPAY");
		
		JH2DFOrderConfig order = new JH2DFOrderConfig();
		order.setOrderNo("TEST"+RandomString.UUID().substring(6));
		order.setOrderPrice("100.0");
		order.setReceiverName("扎克");
		order.setReceiverAccountNo("6226090221368562");
		order.setBankCode("CMBCHINA");
		order.setBankName("招商银行");
		order.setBankClearNo("ClearNo");
		order.setBankBranchNo("branchNo");
		order.setBankBranchName("branchName");
		order.setProvince("province");
		order.setCity("city");
		order.setPhoneNo("13588888888");
		order.setCertType("IDENTITY");
		order.setCertNo("123456789012345678");
		
		try {
			String result = payment.pay(merchant, order);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
