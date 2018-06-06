package com.maven.payment.lfdf;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.maven.payment.PayInterface;

import net.sf.json.JSONObject;

/**
 * 
 * @author 
 *
 */
public class LfDFPayMent<M extends LfDFMerchantConfig,O extends LfDFOrderConfig> implements PayInterface<M,O>{

	@Deprecated
	public String save(LfDFMerchantConfig merchant, LfDFOrderConfig order) throws Exception {
		
		return "";
	}

	@Override
	public String pay(LfDFMerchantConfig merchant, LfDFOrderConfig order) throws Exception {
		String result = LfDFPaySignUtil.getRequestUrl(merchant, order);
		
		JSONObject jsonObject = JSONObject.fromObject(result);
		if(jsonObject.getString("code").equals("0")) {
			return PayInterface.PAY_SUCCESS;
		}
		return jsonObject.getString("info");
	}

	
	public static void main(String[] args) throws Exception {
		PayInterface<LfDFMerchantConfig,LfDFOrderConfig> __yyePay = new LfDFPayMent<LfDFMerchantConfig, LfDFOrderConfig>();
		LfDFMerchantConfig merchant = new LfDFMerchantConfig();
		merchant.setMerNo("100527");
		merchant.setMerKey("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALmb+kAtzhQTwXNNHQJtrKdKgcgmL0yJHzv2EuBXyJ/G30P6wWHmcpNS5ZXEP5VoBNkQbE+VHIiFZejZdgRt4YE0zpbn9WlxFaSk8japkeTUNLZSLFr8QlPk2wVkII0brD23ZIOdDBNPOlefHpHgb52eY1YWfRuzUDOTLA456XK9AgMBAAECgYB2AOaPWw/Dz/OTnJku7A9ypHnyh+RWYm9LLd0+aFES09sqWwgLd/msFG4GU6ihEmrSnhBeYEyttzHdYjQYCMvuxaHIOxvgZQFC/PZXY3X6O0vcdlab+vEmmNiyE44hT0sEoHvrT/ZIa6asKRPEm6ZfbMzdqF43cYf13jL5xcHduQJBAOi2i9Mg1VDYNJSiho/2BYVzgUFit9VSTe7ckwuf4oy4URSko890eBW0mvBZe0Y7Fv9V0oSWbLnrkstH9K22zZcCQQDMLsPXWhSkjJLjGEc72QIJ3dP2iJHBOKMXENlFJb2VhmZwfMgscoGEqHPNjWAjdVPD+h7PuUrD97ixUx+zFHTLAkEAkdAPvLKN9qICED/l4L5ep0fAbZ/VYCy5+DjtShNceJ/k7fJbUdnAhJktm/v1agQMXOEBj2JRrg0VZZoojdBE8QJAaeWfQOPBbQ5TaGDBITMNsZxRQL3YCoSbYW0loo1OZGyOWKz/DL5emjTbyrIWOI2zYiMpM8cAFd829RENkc18PQJAHLfDYfYd/Nv4ID0Z2MHKl4FOqel0elH8RrHJWk5zN3aECSEmNI1i7gb7swHusTzmwAo6ButjIc7cU7wToGDpIQ==");
		merchant.setMerPubKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC5m/pALc4UE8FzTR0CbaynSoHIJi9MiR879hLgV8ifxt9D+sFh5nKTUuWVxD+VaATZEGxPlRyIhWXo2XYEbeGBNM6W5/VpcRWkpPI2qZHk1DS2Uixa/EJT5NsFZCCNG6w9t2SDnQwTTzpXnx6R4G+dnmNWFn0bs1AzkywOOelyvQIDAQAB");
		merchant.setPubKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMcRww57ecNBtZ1Wu1clzkNgNl97MGqUQ8ECVLys5WX30bfeRBpaJCX2x9Ia8EqI99QTDJjrwvnpOIFNo3qkNaL2L73PYo++bFUDVLpyUGK6R5eT7Z2gt8DY904Vni5ZRFeS7kn9a+dmHUcX9t2rXCpfrks5YQ6B91hcH4vX6eLwIDAQAB");
		merchant.setNotiUrl("http://api.hyzonghe.net/TPayment/LfPayCallback");
		merchant.setPayUrl("http://service.lepayle.com/api/quickdraw");
		
		
		LfDFOrderConfig  __yeeorder =  new LfDFOrderConfig();
		__yeeorder.setOrdernumber(UUID.randomUUID().toString());
		__yeeorder.setPaymoney(10.00);
		__yeeorder.setRequestTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		
		__yeeorder.setBankaccountname("炮哥");
		__yeeorder.setBankaccountno("6228481234567890");
		__yeeorder.setBankCode("中国");
		__yeeorder.setBankmobile_no("13612345678");
		__yeeorder.setBankprovince("中国");
		__yeeorder.setBankcity("中国");
		__yeeorder.setBanksitename("中国");
		__yeeorder.setBanksn("ICBC");
		
		String url = __yyePay.pay(merchant, __yeeorder);
		System.out.println(url);
//		JSONObject object = JSONObject.fromObject(result);
//		System.out.println(object.optString("address")); ;
		
	}
	

}
