package com.maven.payment.lf;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.maven.util.Encrypt;
import com.maven.util.StringUtils;

import net.sf.jsqlparser.expression.StringValue;

public class LfPaySignUtil {

	/**
	 * 获取当前时间，精确到yyyyMMddHHmmss
	 * 
	 * xufc20130419 修改
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		String strTime = "-1";
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			strTime = dateFormat.format(date);

		} catch (Exception e) {
			strTime = "-1";
			e.fillInStackTrace();
		}
		return strTime;
	}
	
	public static String getRequestUrl(LfMerchantConfig merchant, LfOrderConfig order) {
		StringBuffer requestUrl = new StringBuffer();
		
//		service String 32 否 gateway_pay
//		partner_no String 32 否 商户合作号，由平台注册提供
//		input_charset String  10 否 编码格式(UTF-8)
//		sign_type String 3 否 签名⽅式（⽹关为MD5）
//		sign String 256 否
//		签名字符串( ⽣成⽅式参考《加密描
//		述⽂档》中的⽹关加密)
//		request_time String 20 否 YYMMDDHHmmss
//		return_url String 1024 否 后台通知地址
//		out_trade_no String 40 否 原始商户订单
//		amount_str String 40 否 ⾦额（0.01 ～9999999999.99）
//		tran_ip String 20 否 ***.***.***.***
//		good_name String 40 否 商品名称
//		goods_detail String 40 否 商品详情

		
		String sign = getRequestSign(merchant.getMerNo(), order.getOrdernumber(), order.getPaymoney()  , order.getTransip(), merchant.getNotiUrl(), order.getRequestTime(), order.getGoodName(), merchant.getMd5Key());
		
		requestUrl.append(merchant.getPayUrl()+"?");
		requestUrl.append(LfPayAppConstants.service).append("=").append("gateway_pay").append("&");
//		requestUrl.append(LfPayAppConstants.partner_no).append("=").append(merchant.getMerNo()).append("&");
		requestUrl.append("partner").append("=").append(merchant.getMerNo()).append("&");
		requestUrl.append(LfPayAppConstants.p1_input_charset).append("=").append("UTF-8").append("&");
		requestUrl.append(LfPayAppConstants.p1_sign_type).append("=").append("MD5").append("&");
		requestUrl.append(LfPayAppConstants.sign).append("=").append(sign).append("&");
		requestUrl.append(LfPayAppConstants.p1_request_time).append("=").append(order.getRequestTime()).append("&");
		requestUrl.append(LfPayAppConstants.p1_return_url).append("=").append(merchant.getNotiUrl()).append("&");
		requestUrl.append("bank_code").append("=").append(order.getBankCode()).append("&");
		
		
		requestUrl.append(LfPayAppConstants.p1_out_trade_no).append("=").append(order.getOrdernumber()).append("&");
		requestUrl.append(LfPayAppConstants.p1_amount_str).append("=").append(order.getPaymoney()).append("&");
		requestUrl.append(LfPayAppConstants.p1_tran_ip).append("=").append(order.getTransip()).append("&");
		requestUrl.append(LfPayAppConstants.p1_good_name).append("=").append(order.getGoodName()).append("&");
		requestUrl.append(LfPayAppConstants.p1_goods_detail).append("=").append(order.getGoodsDetail());
		
		System.err.println(order.getOrdernumber()+"===============支付请求链接="+requestUrl);
		return requestUrl.toString();
	}
	
	/**
	 * 支付请求做MD5参数签名
	 * @param c_code
	 * @param pay_type
	 * @param pay_amt
	 * @param agent_bill_id
	 * @param MER_KEY
	 * @return
	 */
	private static String getRequestSign(String partner , String out_trade_no , double paymoney ,String tran_ip, String callbackurl, String request_time, String good_name, String MD5_KEY) {
		  
//		sign=  
//		partner
//		service
//		out_trade_no
//		amount_str
//		tran_ip
//		buyer_name
//		buyer_contact
//		good_name
//		request_time
//		return_url
//		verfication_code - 商户识别码（对私钥进行MD5）
		
		StringBuffer sValue = new StringBuffer();
		
//		sValue.append(LfPayAppConstants.partner_no).append("=").append(partner);
		sValue.append("partner").append("=").append(partner);
		sValue.append("&").append(LfPayAppConstants.service).append("=").append("gateway_pay");
		sValue.append("&").append(LfPayAppConstants.p1_out_trade_no).append("=").append(out_trade_no);
		sValue.append("&").append(LfPayAppConstants.p1_amount_str).append("=").append(paymoney);
		sValue.append("&").append(LfPayAppConstants.p1_tran_ip).append("=").append(tran_ip);
		
//		sValue.append("&").append(LfPayAppConstants.p1_buyer_name).append("=").append("");//可空
//		sValue.append("&").append(LfPayAppConstants.p1_buyer_contact).append("=").append("");//可空
		
		sValue.append("&").append(LfPayAppConstants.p1_good_name).append("=").append(good_name);
		sValue.append("&").append(LfPayAppConstants.p1_request_time).append("=").append(request_time);
		sValue.append("&").append(LfPayAppConstants.p1_return_url).append("=").append(callbackurl);
		sValue.append("&").append(LfPayAppConstants.p1_verfication_code).append("=").append(MD5_KEY);
		
		
		System.err.println(out_trade_no+"===============支付请求做MD5参数="+sValue);
		String sNewString = null;
			
		sNewString = Encrypt.MD5(sValue.toString());
				
		System.err.println(out_trade_no+"===============支付请求做MD5参数签名="+sNewString);
		return (sNewString);
	}
	
	
	/**
	 * 对返回结果：
	 * 
	 * response-参数密文，需要先base64解密，然后RSA解密
	 * sign-签名密文，需要先base64解密，然后将业务参数按字母顺序进行签名后延签
	 * 
	 * @param req
	 * @param MER_KEY
	 * @return
	 */
	public static boolean checkResponseSign(HttpServletRequest req,
			Map<String, Object> __mer_params) {
		
		try {
			
//			
//			{
//			out_trade_no=EC7182497BC14B868ECFCA6EF8222865, 
//			request_time=2016-11-23 09:00:32, 
//			input_charset=utf-8, 
//			sign=K6WDXgZdxod%2BYknb8BBX8uW5DSN6790nr7VpWXsKH56ssKeZmwPEE4xte%2FiSfNKG83lviSWtIeHy%2FsM7IVYhPIIkSpM5k6lgrH064O9MsblSGwfYkJakk1Gkp1RZLWGSVhMiv%2BwIY4PhnU7p6QEvjg0yJvPml2Hd9oH5T%2BKiQRA%3D, 
//			sign_type=SHA1WithRSA, 
//			content=ZPXySIBnkYi6gLOaQalvfUfG477hcGvdFctuNB9MqwvZYu%2B8INsS2ppCNYERz0imHXlSeKZvFg0ovNaF1Y1jX9B8W8bg86be5%2FoCTejY6jHmy5wPhb9ZtoD5l5nNaRKaoQ701vkfzpcuO81pJfOPMXqTKUm%2FRWW4k95Ovi6v7RxCgFOrFLERH1QPdcgF1zclQwL9XCCLZTZWEka92uJ06tr9dsE8GCVq8Svmzokpe2YnZcBVMdXlGH1LwRxhZ8hXfmbYN0Z17fHe4%2FjfnH7fVvvPcs6V2UTdB6oQBMrsuQgEcVP%2B30A0TOHk0C9cLdFCn99MyuFF%2FzOuNjMD8N8f5g%2FvMGXzyYUxBnr%2BXRuKgL1pQGD%2F1ZYsDbCi0aC1an%2BEua%2FEMD4KEKQoeQeNsRsnbG%2BgtPBzacBqZubImgAMkaZsGyARaFNbJyVrS7ef1HpZgLptvZ3wTW%2FgcgAwAvgPXPBgbeAT5uTj2%2FcMAv1n%2Bpak%2F9Uh5GP8VzsLrK22YndG, 
//			status=1
//			}
//			
			String MEK_PUB_KEY = String.valueOf(__mer_params.get("MEK_PUB_KEY"));//商户公钥
			String MD5_KEY = String.valueOf(__mer_params.get("MD5_KEY"));//商户MD5秘钥
			String MEK_PRI_KEY = String.valueOf(__mer_params.get("MEK_PRI_KEY"));//商户秘钥
			String MER_NO = String.valueOf(__mer_params.get("MER_NO"));//商户号
			String PUBLIC_KEY = String.valueOf(__mer_params.get("PUBLIC_KEY"));//乐付公钥
			
			String p2_status = req.getParameter(LfPayAppConstants.p2_status);//1
	        String p2_content = req.getParameter(LfPayAppConstants.p2_content);
	        String p2_input_charset = req.getParameter(LfPayAppConstants.p2_input_charset);
	        String return_sign = req.getParameter(LfPayAppConstants.sign);
	        
	        byte[] responseByte = java.util.Base64.getDecoder().decode(URLDecoder.decode(p2_content) );
	        //
	        
			String response = new String(RSACoderUtil.decryptByPrivateKey(responseByte, MEK_PRI_KEY));
			
	        boolean verfiy = RSACoderUtil.verify(response.getBytes() , PUBLIC_KEY, URLDecoder.decode(return_sign) );
	        
	        return verfiy;
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		return false;
	}
	public static void main(String[] args) {
		try {
			String PUBLIC_KEY  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDMcRww57ecNBtZ1Wu1clzkNgNl97MGqUQ8ECVLys5WX30bfeRBpaJCX2x9Ia8EqI99QTDJjrwvnpOIFNo3qkNaL2L73PYo++bFUDVLpyUGK6R5eT7Z2gt8DY904Vni5ZRFeS7kn9a+dmHUcX9t2rXCpfrks5YQ6B91hcH4vX6eLwIDAQAB";//乐付公钥
			
			
			
			String MEK_PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEMM5VHgpurmCKzI7bdlZiKnGMtq4yuuQGX233Bsl/ESF+HxlD/qY2eyA+FqWuk/ZdXwOZFcfP9EMhveBMMsDKVef/1OWMD82DUAb+eHat6x9d/wG+uVAw8fzYWKCGOx+TmXiuhWaX1RUV5wzEGDhDm5F4tQTsGiDI42u3D8QwxwIDAQAB";
			String MEK_PRI_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIQwzlUeCm6uYIrMjtt2VmIqcYy2rjK65AZfbfcGyX8RIX4fGUP+pjZ7ID4Wpa6T9l1fA5kVx8/0QyG94EwywMpV5//U5YwPzYNQBv54dq3rH13/Ab65UDDx/NhYoIY7H5OZeK6FZpfVFRXnDMQYOEObkXi1BOwaIMjja7cPxDDHAgMBAAECgYAQE+uUmLHGInlliFm7wZniGK104ucvDmIbkFHMoMuzI79CGsA07+0BZVpXkuOV5zMoyhyi6u5BkcD0gqacPfQKuwQQ1SmgaJRTjI3I8ZIIIHgWYlXQHxJDcEZPwDJYvEBINMRY0JzzZdmDFngRwXPN4eErti9Qfj77usMjHkGW+QJBAMzYAjDscz2I0oxd9Y2jLRDqNrrXHlDjgLJ5jx+wz1MnSbXIbAZUEQpoE9dF81T/ZIe7cR4vsKmcV/ENH7yhbmUCQQClM/dOZm9eVihepLca+DCkPsLK43lbqXfuRC0rH4m7MvNdvsowheukyAUdAOgooLmjz/DOpoNcwB1CcKT2Kwm7AkBZWVcLU/eoPYfpJgq3bxd3K1IYCUD182Xtc0xUfGDSWm6yVeuYjw6nxWHyIlbTGlsVQVND4XVjZgiN4jWXp6ppAkBt49Vbt1PZgqIz4olSTUm912mnoAy35lC6k2sgRkhN16R+9Ux1Xn/TCqIsGtBTRiZ2Svm53JWA+uH7sgZpIVPFAkAy/rn2RyCGCz0fcw/jmZrSN2kF4jbmO++gV37wnHHHibY62m7rZOPRRSmGlkZULjIEx9uKE1ZHwzwd1baoWpG8";
			
			String content = "ZPXySIBnkYi6gLOaQalvfUfG477hcGvdFctuNB9MqwvZYu%2B8INsS2ppCNYERz0imHXlSeKZvFg0ovNaF1Y1jX9B8W8bg86be5%2FoCTejY6jHmy5wPhb9ZtoD5l5nNaRKaoQ701vkfzpcuO81pJfOPMXqTKUm%2FRWW4k95Ovi6v7RxCgFOrFLERH1QPdcgF1zclQwL9XCCLZTZWEka92uJ06tr9dsE8GCVq8Svmzokpe2YnZcBVMdXlGH1LwRxhZ8hXfmbYN0Z17fHe4%2FjfnH7fVvvPcs6V2UTdB6oQBMrsuQgEcVP%2B30A0TOHk0C9cLdFCn99MyuFF%2FzOuNjMD8N8f5g%2FvMGXzyYUxBnr%2BXRuKgL1pQGD%2F1ZYsDbCi0aC1an%2BEua%2FEMD4KEKQoeQeNsRsnbG%2BgtPBzacBqZubImgAMkaZsGyARaFNbJyVrS7ef1HpZgLptvZ3wTW%2FgcgAwAvgPXPBgbeAT5uTj2%2FcMAv1n%2Bpak%2F9Uh5GP8VzsLrK22YndG";
			String return_sign = "K6WDXgZdxod%2BYknb8BBX8uW5DSN6790nr7VpWXsKH56ssKeZmwPEE4xte%2FiSfNKG83lviSWtIeHy%2FsM7IVYhPIIkSpM5k6lgrH064O9MsblSGwfYkJakk1Gkp1RZLWGSVhMiv%2BwIY4PhnU7p6QEvjg0yJvPml2Hd9oH5T%2BKiQRA%3D";
			
			byte[] responseByte = "WlBYeVNJQm5rWWk2Z0xPYVFhbHZmVWZHNDc3aGNHdmRGY3R1TkI5TXF3dlpZdSs4SU5zUzJwcENOWUVSejBpbUhYbFNlS1p2Rmcwb3ZOYUYxWTFqWDlCOFc4Ymc4NmJlNS9vQ1Rlalk2akhteTV3UGhiOVp0b0Q1bDVuTmFSS2FvUTcwMXZrZnpwY3VPODFwSmZPUE1YcVRLVW0vUldXNGs5NU92aTZ2N1J4Q2dGT3JGTEVSSDFRUGRjZ0YxemNsUXdMOVhDQ0xaVFpXRWthOTJ1SjA2dHI5ZHNFOEdDVnE4U3Ztem9rcGUyWW5aY0JWTWRYbEdIMUx3UnhoWjhoWGZtYllOMFoxN2ZIZTQvamZuSDdmVnZ2UGNzNlYyVVRkQjZvUUJNcnN1UWdFY1ZQKzMwQTBUT0hrMEM5Y0xkRkNuOTlNeXVGRi96T3VOak1EOE44ZjVnL3ZNR1h6eVlVeEJucitYUnVLZ0wxcFFHRC8xWllzRGJDaTBhQzFhbitFdWEvRU1ENEtFS1FvZVFlTnNSc25iRytndFBCemFjQnFadWJJbWdBTWthWnNHeUFSYUZOYkp5VnJTN2VmMUhwWmdMcHR2WjN3VFcvZ2NnQXdBdmdQWFBCZ2JlQVQ1dVRqMi9jTUF2MW4rcGFrLzlVaDVHUDhWenNMcksyMlluZEcKCQkJCQk=".getBytes();
			String response = new String(RSACoderUtil.decryptByPrivateKey(responseByte, MEK_PRI_KEY));
			System.out.println(response);
			
//			String sign = new String(RSACoderUtil.decryptByPublicKey(signByte, PUBLIC_KEY));
//			System.out.println(sign);
	        boolean verfiy = RSACoderUtil.verify(response.getBytes() , PUBLIC_KEY, URLDecoder.decode(return_sign) );
	        
	        System.out.println(verfiy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 参数过滤
	 * @param text
	 * @return
	 */
	@SuppressWarnings("unused")
	private String formatString(String text){ 
		if(text == null) {
			return ""; 
		}
		return text;
	}
}

