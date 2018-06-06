package com.maven.payment.lfwx;

import java.util.UUID;

import com.maven.payment.PayInterface;
import com.maven.payment.lf.LfPaySignUtil;
import com.maven.util.Encrypt;
import com.maven.util.HttpPostUtil;

import net.sf.json.JSONObject;

/**
 * 
 * @author 
 *
 */
public class LfwxPayMent<M extends LfwxMerchantConfig,O extends LfwxOrderConfig> implements PayInterface<M,O>{

	@Override
	public String save(LfwxMerchantConfig merchant, LfwxOrderConfig order) throws Exception {
//		AttrCheckout.checkout(merchant, false, YOMMerchantConfig.M_Save_Paramters.paramters());
//		AttrCheckout.checkout(order, false, YOMOrderConfig.O_Save_Paramters.paramters());
		
		String requestUrl = LfwxPaySignUtil.getRequestUrl(merchant, order);
		System.out.println("支付请求链接="+requestUrl);
		
//		{"is_succ":"F","fault_code":"E1008001","fault_reason":"service缺失或内容为空","remark":null,"response":null,"sign":null,"charset":"utf-8"}
		
		return requestUrl;
	}

	@Override
	@Deprecated
	public String pay(LfwxMerchantConfig merchant, LfwxOrderConfig order) throws Exception {
		return "";
	}

	
	
	public static void main(String[] args) throws Exception {
		PayInterface<LfwxMerchantConfig,LfwxOrderConfig> __yyePay = new LfwxPayMent<LfwxMerchantConfig, LfwxOrderConfig>();
		LfwxMerchantConfig merchant = new LfwxMerchantConfig();
		merchant.setMerNo("100322");
		merchant.setMerKey("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAP0GDDcK55QCA9rEl0KEexJQQtbMDqoZPfSLa4MUTQ6n+ap6xzxZNczVQhSMVZg0tZIGfKayCogLz0nsl7XFJpd+DpjNewDnC42bcMZj637GyORsMAyIKt8wqt7HkHXMz3s8bZeYsMve6hf7m4YxlxUmFkDUp5ChwLSOCunzh5EbAgMBAAECgYBZp6jwYAbKpSQkgoBua28IgDQO1GNl1mfjnPtNiQX86XxH8hVixuGPYQl3Knqx4gtsYKwOjQu6RSUANrtTBzayxvRZL5MJo05rTGlvJ0w9Exs2N2cOgXK9iTN/+b858Ck9f2bOKD+Hg5qVGgjSKRk+/JPlcSIdcrqbMdHoCybgMQJBAP+TDR7bCBrLDo1FlE4Fous2kcPZXYG2zYJUdcMSK8WNUmjtkM43oqDF04vmRMWl5PFdiA8QuVIIw0ujM6AyV00CQQD9cei5zQCv8Qzs6YTvHz7tRePFZ+0hB5TvCYQDPtD4HteduLeJ4D0Hhw6Q73AvvEWjezUcjTjBJ7oXKCCKmOYHAkEA6E0QQviR0FC7RFt3JsfmwudR7PN5E7tF5u3AMHQmxyTiQC+XTGmzb3EBDQtbfU+B3oXGcvMfj1oZsXmBJl47jQJBAMQhDlQajNma1MHRxIm3yF6dozH0xtC0qVCCMKLCTbx1Qa5Qb9hGq3PT1DXc1RhbvhhRFDzQHId9UsjwyJ34zKkCQCqWUFqVqgFFexy87242ajCHaz7MwZO0shLfc4hF0dAbN+uEHTadgIStgh3zI3XHeQCd/zF8gOQtEtcInqnV7qs=");

		merchant.setMerPubKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD9Bgw3CueUAgPaxJdChHsSUELWzA6qGT30i2uDFE0Op/mqesc8WTXM1UIUjFWYNLWSBnymsgqIC89J7Je1xSaXfg6YzXsA5wuNm3DGY+t+xsjkbDAMiCrfMKrex5B1zM97PG2XmLDL3uoX+5uGMZcVJhZA1KeQocC0jgrp84eRGwIDAQAB");
		merchant.setPubKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMcRww57ecNBtZ1Wu1clzkNgNl97MGqUQ8ECVLys5WX30bfeRBpaJCX2x9Ia8EqI99QTDJjrwvnpOIFNo3qkNaL2L73PYo++bFUDVLpyUGK6R5eT7Z2gt8DY904Vni5ZRFeS7kn9a+dmHUcX9t2rXCpfrks5YQ6B91hcH4vX6eLwIDAQAB");
		merchant.setPayUrl("http://service.lepayle.com/api/gateway");
		merchant.setReturn_url("http://service.lepayle.com/gateway/pay");
		
		LfwxOrderConfig  __yeeorder =  new LfwxOrderConfig();
		__yeeorder.setOrdernumber(UUID.randomUUID().toString());
		__yeeorder.setPaymoney(100.00);
		__yeeorder.setTransip("192.168.1.0");
		String url = __yyePay.save(merchant, __yeeorder);
//		String[] params = url.split("\\?")[1].split("&");
//		for (String string : params) {
//			System.err.println(string);
//		}
		
		String requestUrl = url;
		
	}
	

}
