package com.maven.payment.lf;

import java.util.UUID;

import com.maven.payment.PayInterface;
import com.maven.util.Encrypt;
import com.maven.util.HttpPostUtil;

import net.sf.json.JSONObject;

/**
 * 
 * @author 
 *
 */
public class LfPayMent<M extends LfMerchantConfig,O extends LfOrderConfig> implements PayInterface<M,O>{

	@Override
	public String save(LfMerchantConfig merchant, LfOrderConfig order) throws Exception {
//		AttrCheckout.checkout(merchant, false, YOMMerchantConfig.M_Save_Paramters.paramters());
//		AttrCheckout.checkout(order, false, YOMOrderConfig.O_Save_Paramters.paramters());
		
		String requestUrl = LfPaySignUtil.getRequestUrl(merchant, order);
		System.out.println("支付请求链接="+requestUrl);
		return requestUrl;
	}

	@Override
	@Deprecated
	public String pay(LfMerchantConfig merchant, LfOrderConfig order) throws Exception {
		return "";
	}

	
	public static void main(String[] args) throws Exception {
		PayInterface<LfMerchantConfig,LfOrderConfig> __yyePay = new LfPayMent<LfMerchantConfig, LfOrderConfig>();
		LfMerchantConfig merchant = new LfMerchantConfig();
		merchant.setMerNo("100067");
		merchant.setMerKey("MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIQwzlUeCm6uYIrMjtt2VmIqcYy2rjK65AZfbfcGyX8RIX4fGUP+pjZ7ID4Wpa6T9l1fA5kVx8/0QyG94EwywMpV5//U5YwPzYNQBv54dq3rH13/Ab65UDDx/NhYoIY7H5OZeK6FZpfVFRXnDMQYOEObkXi1BOwaIMjja7cPxDDHAgMBAAECgYAQE+uUmLHGInlliFm7wZniGK104ucvDmIbkFHMoMuzI79CGsA07+0BZVpXkuOV5zMoyhyi6u5BkcD0gqacPfQKuwQQ1SmgaJRTjI3I8ZIIIHgWYlXQHxJDcEZPwDJYvEBINMRY0JzzZdmDFngRwXPN4eErti9Qfj77usMjHkGW+QJBAMzYAjDscz2I0oxd9Y2jLRDqNrrXHlDjgLJ5jx+wz1MnSbXIbAZUEQpoE9dF81T/ZIe7cR4vsKmcV/ENH7yhbmUCQQClM/dOZm9eVihepLca+DCkPsLK43lbqXfuRC0rH4m7MvNdvsowheukyAUdAOgooLmjz/DOpoNcwB1CcKT2Kwm7AkBZWVcLU/eoPYfpJgq3bxd3K1IYCUD182Xtc0xUfGDSWm6yVeuYjw6nxWHyIlbTGlsVQVND4XVjZgiN4jWXp6ppAkBt49Vbt1PZgqIz4olSTUm912mnoAy35lC6k2sgRkhN16R+9Ux1Xn/TCqIsGtBTRiZ2Svm53JWA+uH7sgZpIVPFAkAy/rn2RyCGCz0fcw/jmZrSN2kF4jbmO++gV37wnHHHibY62m7rZOPRRSmGlkZULjIEx9uKE1ZHwzwd1baoWpG8");
		merchant.setMerPubKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEMM5VHgpurmCKzI7bdlZiKnGMtq4yuuQGX233Bsl/ESF+HxlD/qY2eyA+FqWuk/ZdXwOZFcfP9EMhveBMMsDKVef/1OWMD82DUAb+eHat6x9d/wG+uVAw8fzYWKCGOx+TmXiuhWaX1RUV5wzEGDhDm5F4tQTsGiDI42u3D8QwxwIDAQAB");
		merchant.setMd5Key("8026F52ED0AD8F7C975B6BEB597205A7");
		merchant.setPubKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMcRww57ecNBtZ1Wu1clzkNgNl97MGqUQ8ECVLys5WX30bfeRBpaJCX2x9Ia8EqI99QTDJjrwvnpOIFNo3qkNaL2L73PYo++bFUDVLpyUGK6R5eT7Z2gt8DY904Vni5ZRFeS7kn9a+dmHUcX9t2rXCpfrks5YQ6B91hcH4vX6eLwIDAQAB");
		merchant.setNotiUrl("http://api.hyzonghe.net/TPayment/LfPayCallback");
		merchant.setPayUrl("http://service.lepayle.com/gateway/pay");
		
		
		LfOrderConfig  __yeeorder =  new LfOrderConfig();
		__yeeorder.setOrdernumber(UUID.randomUUID().toString());
		__yeeorder.setPaymoney(100.00);
		__yeeorder.setRequestTime("20161122102012");
		__yeeorder.setTransip("192.168.1.0");
		String url = __yyePay.save(merchant, __yeeorder);
		String[] params = url.split("\\?")[1].split("&");
		for (String string : params) {
			System.err.println(string);
		}
		
//		JSONObject object = JSONObject.fromObject(result);
//		System.out.println(object.optString("address")); ;
		
	}
	

}
