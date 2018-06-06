package com.maven.payment.lfqqpay;

import com.maven.payment.PayInterface;
import com.maven.util.RandomString;

public class LfQQPayPayMent<M extends LfQQPayMerchantConfig, O extends LfQQPayOrderConfig> implements PayInterface<M, O> {

	@Override
	public String save(LfQQPayMerchantConfig merchant, LfQQPayOrderConfig order) throws Exception {
		LfQQPaySave save = new LfQQPaySave();
		return save.getUrl(merchant, order);
	}

	@Override
	public String pay(LfQQPayMerchantConfig merchant, LfQQPayOrderConfig order) throws Exception {
		// 
		return null;
	}

	public static void main(String[] args) {
		PayInterface<LfQQPayMerchantConfig, LfQQPayOrderConfig> payment = new LfQQPayPayMent<LfQQPayMerchantConfig, LfQQPayOrderConfig>();
		// 商户信息
		LfQQPayMerchantConfig merchant = new LfQQPayMerchantConfig();
		merchant.setMerNo("100672");
		merchant.setPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMcRww57ecNBtZ1Wu1clzkNgNl97MGqUQ8ECVLys5WX30bfeRBpaJCX2x9Ia8EqI99QTDJjrwvnpOIFNo3qkNaL2L73PYo++bFUDVLpyUGK6R5eT7Z2gt8DY904Vni5ZRFeS7kn9a+dmHUcX9t2rXCpfrks5YQ6B91hcH4vX6eLwIDAQAB");
		merchant.setMerPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCfMI6iVKSVKA8VTSP81lU5Md4uuDLfSIe6yPGB/oK+gbZt9SMGG93NnlocNA0ZkrICnQjlMpqVZ4KNB3C1fI9X5KU7JcLKrmMJUpzLJEjlc4tXhPOdtNkpXGqjEi7+L/A9Ftio9aRudZkO+N7qCo2juJvDkhha/TPSWhp2qmQ8iwIDAQAB");
		merchant.setMerPrivateKey("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ8wjqJUpJUoDxVNI/zWVTkx3i64Mt9Ih7rI8YH+gr6Btm31IwYb3c2eWhw0DRmSsgKdCOUympVngo0HcLV8j1fkpTslwsquYwlSnMskSOVzi1eE85202SlcaqMSLv4v8D0W2Kj1pG51mQ743uoKjaO4m8OSGFr9M9JaGnaqZDyLAgMBAAECgYBWwvlKYkWdgcrOuk998zdZx+MnpX8ckQRg+joetp99w2axgWTIZJG8Cq1kJ75oDLr2j64UdUMpYNQXl1GHqM7ao+MbjkuKhNpwFHz8WpevIpbf+q4nXiREB6NP7qVjmJgsqj5F08f5dUFKa03bXTU+SS04AWAiskmtNjc5goxiCQJBAOSZ2nIDVq9YROR34ETByOojGTTeEHOLP2ccQG1dNZZLFjbRTVaixodXyujYlEzwzjbJhgGhzdDmnL89t9HRQUUCQQCyRPY7kBZexdBFUCLM+qr3NNZUSHsDSIXmUUMscHe0rHmHnc0PlK+2Y7o24e29NPkCewfMy1n9dlSTFTlNzJuPAkAVU40fidQyc4ep0M0pXPLDdRbK6dSbEuiNVANmsIVpSgP+hTQz2ueuhLA3XM0pCGYUkmRgDppG3NzOspFNj7oJAkEAhqm1X3UOkPTYySMpxrGsKCVPqkc+NVK5YywLlCw5INAQ1bMMTjLBMQPQjNjJ391+JLOPRtJt3hte8+RabzyaXQJBAI+AF9BEY1FVpbx6movUdYBKLCUbbziRtq4/pVIt6RX3OCkGTgsR+c7cakfz37EkAoa2+sZyKTR6sMgddcScCIQ=");
		merchant.setPayUrl("http://service.lepayle.com/api/gateway");
		merchant.setReturnUrl("http://api.hyzonghe.net/TPayment/RYPayCallback");
		
		// 订单信息
		LfQQPayOrderConfig order = new LfQQPayOrderConfig();
		order.setAmount("1.00");
		order.setOrderNo(RandomString.UUID());
		order.setRemark("GoldEggRemark");
		order.setSubBody("VirtualCurrency");
		order.setSubject("Recharge");
		
		try {
			System.out.println(payment.save(merchant, order));;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}