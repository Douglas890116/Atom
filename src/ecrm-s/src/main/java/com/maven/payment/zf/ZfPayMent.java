package com.maven.payment.zf;

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
public class ZfPayMent<M extends ZfMerchantConfig,O extends ZfOrderConfig> implements PayInterface<M,O>{

	@Override
	public String save(ZfMerchantConfig merchant, ZfOrderConfig order) throws Exception {
		
		String requestUrl = ZfPaySignUtil.getRequestUrl(merchant, order);
		System.out.println("支付请求链接="+requestUrl);
		return requestUrl;
	}

	@Override
	@Deprecated
	public String pay(ZfMerchantConfig merchant, ZfOrderConfig order) throws Exception {
		return "";
	}

	
	public static void main(String[] args) throws Exception {
		PayInterface<ZfMerchantConfig,ZfOrderConfig> __yyePay = new ZfPayMent<ZfMerchantConfig, ZfOrderConfig>();
		ZfMerchantConfig merchant = new ZfMerchantConfig();
		merchant.setMerNo("4000001349");
		merchant.setMerKey("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANP/uoYTSj3Zi/dwpHUSP0DCATfNd52410jUXUmfGoJloErksCsYn+d9Hfmqb+RWXglUTsIo20GvYWQzcBKoqkUcRoWwP3Pq8KGJTPjHiL+WCUNB/Yl8GJqQgqRoAFRERLyjDtO+ohYocPEyM6gS9+aYOhtsmuLhGyk+qKKVdBlVAgMBAAECgYAhQB/aPZZMYx2f3qv8rLL9DjCwtupG86HYi25iSLEYuKXOeVXkPIWAAlC8Qu4vyxgOsIl1zICvKbnFxIeUdZ3W1bkFLYWsec6VGN9EDKhkNCIiBGgNGPSM2mOHIysRseECA76ATTfiLa9H4sCQ05kQSSz75zLyed1Qw4sQJgLmVQJBAP9MNONLKkFlUpuOnyKHxIT7rymzGQv/fQfoKI7cudhuuLxelOfmPIv7zpTdzBf+HR34y5npi3sFNhdnm2iE4QMCQQDUlQdkQbfH5u/N+v8EQUoNe7SOyarD/McwYUN4c8BaYohgtfj2QlP0vCAl7cqK7iJiCmTdAdfKHCkTlVCRsxDHAkBC75kiSw/eK5G4JXHF3PECqPapKUMU6Ty/+PfBVVAI1ibrhKToFG6liTUFxQ2A8OhzdkrzpM8kRx6CjnCpu8U/AkBwRVTmCh85oCm+K3VLlf+6Jz8wuilJ4NKCJpwvnVJVafyEtxaJWHZmpoSEA5YY0bOu+7ZRUovY3g6DnTQz/tI5AkEAvrOoBkA7AsSOaW1srPUwJrtoJO1j3/S2T89fkJbO2wzGcF/ojthPLDMw0GsITIUR4ZiV+HLBJTSMQ+qQUOYL/g==");


		merchant.setMerPubKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDT/7qGE0o92Yv3cKR1Ej9AwgE3zXeduNdI1F1JnxqCZaBK5LArGJ/nfR35qm/kVl4JVE7CKNtBr2FkM3ASqKpFHEaFsD9z6vChiUz4x4i/lglDQf2JfBiakIKkaABURES8ow7TvqIWKHDxMjOoEvfmmDobbJri4RspPqiilXQZVQIDAQAB");



		merchant.setPubKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnu2BBi+bYHiXSyVjvAgGUgExjL+68S9AR6TctBjRUJIU1SZ64xENYlUhy1Ei5LJjLYIETYeGX7Rzchd7pLpN9Ndy4nXSFgSnTUpd6W3zEuunprwyEGNlrqxm4KoBwoKYegP/xvgbcS4MYqMMC53y6GrUcKhtkZnrR7VpWF3Hq7wIDAQAB");
		merchant.setNotiUrl("http://api.hyzonghe.net/TPayment/ZPayCallback");
		merchant.setPayUrl("https://pay.dinpay.com/gateway?input_charset=UTF-8");
		merchant.setPageUrl("http://api.hyzonghe.net/TPayment/ZPayCallback");
		
		ZfOrderConfig  __yeeorder =  new ZfOrderConfig();
		__yeeorder.setOrdernumber(UUID.randomUUID().toString());
		__yeeorder.setPaymoney(100.00);
		__yeeorder.setRequestTime("20161122102012");
		__yeeorder.setTransip("192.168.1.0");
		__yeeorder.setBankCode("ICBC");
		
		
		String url = __yyePay.save(merchant, __yeeorder);
		String[] params = url.split("\\?")[1].split("&");
		for (String string : params) {
			System.err.println(string);
		}
		
//		JSONObject object = JSONObject.fromObject(result);
//		System.out.println(object.optString("address")); ;
		
	}
	

}
