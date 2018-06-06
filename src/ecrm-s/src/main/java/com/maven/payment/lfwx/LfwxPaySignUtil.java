package com.maven.payment.lfwx;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.maven.util.Encrypt;
import com.maven.util.HttpPostUtil;
import com.maven.util.StringUtils;

import net.sf.json.JSONObject;
import net.sf.jsqlparser.expression.StringValue;

public class LfwxPaySignUtil {

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
	
	/**
	 * 获取签名和加密内容，组装成完整的请求URL
	 * @param merchant
	 * @param order
	 * @return
	 */
	public static String getRequestUrl(LfwxMerchantConfig merchant, LfwxOrderConfig order) {
		StringBuffer requestUrl = new StringBuffer();
		
//		1) 接口参数合成参数明文字符串 
//		2) 参数明文字符串加密获得密文字符数组(密文获得方式参考4.1加密说明)，加密使用的系统公钥。 [p1=v1&p2=v2]
//		3) 密文字符数组进行base64获得编码密文 
//		4) 对编码密文采用约定字符集再进行URLEncode编码，编码后密文存放content参数中 
//		5) 对参数明文字符串加签获得签名字符数组(签名获得方式参考4.2加签说明)，加签使用的是商户私钥。 
//		6) 签名字符数组进行base64获得编码密文 
//		7) 对编码密文采用约定字符集再进行URLEncode编码，编码后密文存放sign参数中
	
//		
//		service String 32 否 wx_pay
//		out_trade_no String 40 否 原始商户订单
//		amount_str String 40 否 ⾦额（0.01 ～9999999999.99）
//		wx_pay_type String 10 否 wx_sm: 微信扫码
//		subject String 20 否 商品名称
//		sub_body String 20 否 商品描述
//		remark String 20 是 备注
//		return_url String 1024 否 后台通知地址
		
		String CurrentTime = getCurrentTime();
		
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();  
        parameters.put(LfwxPayAppConstants.service, order.getService());  
        parameters.put(LfwxPayAppConstants.p1_out_trade_no, order.getOrdernumber());  
        parameters.put(LfwxPayAppConstants.p1_amount_str, order.getPaymoney()+"");  
        parameters.put(LfwxPayAppConstants.p1_wx_pay_type, order.getWx_pay_type());  
        parameters.put(LfwxPayAppConstants.p1_subject, order.getSubject());
        parameters.put(LfwxPayAppConstants.p1_sub_body, order.getSub_body());  
        parameters.put(LfwxPayAppConstants.p1_return_url, merchant.getReturn_url());
        parameters.put(LfwxPayAppConstants.p1_sign_type, merchant.getSignType());  
        parameters.put(LfwxPayAppConstants.p1_request_time, CurrentTime);
        parameters.put("partner", merchant.getMerNo());
        parameters.put(LfwxPayAppConstants.p1_input_charset, merchant.getInputCharset());
        
        
        StringBuffer sb = new StringBuffer();  
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
        Iterator it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();  
            String k = (String)entry.getKey();  
            Object v = entry.getValue();  
            if(null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {     
                sb.append(k + "=" + v + "&");
            }  
        }  
        
        String params = sb.substring(0, sb.length() - 1);
		System.out.println("业务参数原文："+params);
		
        try {
        	
        	
    		String paramsNew =  RSACoderUtil.getParamsWithDecodeByPublicKey(params, merchant.getInputCharset(), merchant.getPubKey());//对原文加密
    		System.out.println("业务参数密文："+paramsNew);
    		
    		String signNew = RSACoderUtil.sign(params.getBytes(merchant.getInputCharset()), merchant.getMerKey());//对原文签名
    		System.out.println("业务参数签名："+signNew);
    		
//    		partner_no String 32 否 商户合作号，由平台注册提供
//    		input_charset String  10 否 编码格式(UTF-8)
//    		sign_type String 3 否 签名⽅式（SHA1WITHRSA｜⽹关为MD5）
//    		sign String 256 否 签名字符串( ⽣成⽅式参考《加密描述⽂档》)
//    		request_time String 20 是 YYMMDDHHmmss
//    		content String 否 业务参数加密密⽂( ⽣成⽅式参考《加 密描述⽂档》)
    		
//    		requestUrl.append(merchant.getPayUrl()+"?");
    		
    		requestUrl.append("partner").append("=").append(merchant.getMerNo()).append("&");
    		requestUrl.append(LfwxPayAppConstants.p1_input_charset).append("=").append(merchant.getInputCharset()).append("&");
    		requestUrl.append(LfwxPayAppConstants.p1_sign_type).append("=").append(merchant.getSignType()).append("&");
    		requestUrl.append(LfwxPayAppConstants.sign).append("=").append(signNew).append("&");
    		requestUrl.append(LfwxPayAppConstants.p1_content).append("=").append(paramsNew).append("&");
    		
    		Map<String, String> paramsx = new HashMap<String, String>();
    		paramsx.put("partner", merchant.getMerNo());
    		paramsx.put(LfwxPayAppConstants.p1_input_charset, merchant.getInputCharset());
    		paramsx.put(LfwxPayAppConstants.p1_sign_type, merchant.getSignType());
    		paramsx.put(LfwxPayAppConstants.sign, signNew);
    		paramsx.put(LfwxPayAppConstants.p1_content, paramsNew);
    		
    		System.out.println("===============支付请求链接="+requestUrl);
    		
    		String result = HttpPostUtil.doPostSubmitMap(merchant.getPayUrl(), paramsx);
    		System.out.println("===============支付请求结果="+result);
    		
    		//{"is_succ":"T","fault_code":null,"fault_reason":null,"remark":null,
    		//"response":"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","sign":"vt0vEbLY/Xp8d6Dzj3SlD0OoVsbBDaeB5t+Ml3YI/tQ28hxD4eI3cqPbKEI1Qm74owrqsl/eLO9hSaPaMqIr/NA1ugqWAQXLUuvmd9gyC0GTRU4qRXMH6AqOO84PUPPldZoeMKL+Cn4/pYX0WtszlVqEAfrdtN90607FgRFe1zw=","charset":"utf-8"}
    		
    		JSONObject jsonObject = JSONObject.fromObject(result);
    		
    		//成功时校验签名及解密，获取返回的具体的数据
    		if(jsonObject.getString("is_succ").equals("T")) {
    			String p2_content = jsonObject.getString("response");
    			String p2_charset = jsonObject.getString("charset");
    			String return_sign = jsonObject.getString("sign");
    			
    	        byte[] responseByte = java.util.Base64.getDecoder().decode(p2_content);
    			String response = new String(RSACoderUtil.decryptByPrivateKey(responseByte, merchant.getMerKey()));
    			
    			System.out.println("response="+response);
    			System.out.println("return_sign="+return_sign);
    			
    	        boolean verfiy = RSACoderUtil.verify(response.getBytes() , merchant.getPubKey(), return_sign );
    	        
    	        if(verfiy) {
    	        	
//    	        	wx_pay_sm_url String 1024 否 微信⽀付识别⼆维码URL
//    	        	wx_pay_hx_url String 1024 是 微信⽀付唤醒app⽀付url
//    	        	base64QRCode String 2048 是微信⽀付⼆维码图⽚( 优先使⽤该返回，如果不存在则使⽤wx_pay_sm_url)
    	        	
    	        	System.out.println(response);
    	        	jsonObject = JSONObject.fromObject(response);
    	        	if(jsonObject.has("base64QRCode") && !jsonObject.optString("base64QRCode").equals("")) {
    	        		return jsonObject.optString("base64QRCode");
    	        	} else {
    	        		return jsonObject.getString("wx_pay_sm_url");
    	        	}
    	        	
//    	        	return jsonObject.getString("wx_pay_sm_url");
//    	        	return response;
    	        	
    	        }
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
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
	public static boolean checkResponseSign(HttpServletRequest req,Map<String, Object> __mer_params) {
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
			
			String p2_status = req.getParameter(LfwxPayAppConstants.p2_status);//1
	        String p2_content = req.getParameter(LfwxPayAppConstants.p2_content);
	        String p2_input_charset = req.getParameter(LfwxPayAppConstants.p2_input_charset);
	        String return_sign = req.getParameter(LfwxPayAppConstants.sign);
	        
	        byte[] responseByte = java.util.Base64.getDecoder().decode(URLDecoder.decode(p2_content) );
			String response = new String(RSACoderUtil.decryptByPrivateKey(responseByte, MEK_PRI_KEY));
			
	        boolean verfiy = RSACoderUtil.verify(response.getBytes() , PUBLIC_KEY, URLDecoder.decode(return_sign) );
	        
	        return verfiy;
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		return false;
	}
	public static void main(String[] args) {
		String xx = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAP0GDDcK55QCA9rEl0KEexJQQtbMDqoZPfSLa4MUTQ6n+ap6xzxZNczVQhSMVZg0tZIGfKayCogLz0nsl7XFJpd+DpjNewDnC42bcMZj637GyORsMAyIKt8wqt7HkHXMz3s8bZeYsMve6hf7m4YxlxUmFkDUp5ChwLSOCunzh5EbAgMBAAECgYBZp6jwYAbKpSQkgoBua28IgDQO1GNl1mfjnPtNiQX86XxH8hVixuGPYQl3Knqx4gtsYKwOjQu6RSUANrtTBzayxvRZL5MJo05rTGlvJ0w9Exs2N2cOgXK9iTN/+b858Ck9f2bOKD+Hg5qVGgjSKRk+/JPlcSIdcrqbMdHoCybgMQJBAP+TDR7bCBrLDo1FlE4Fous2kcPZXYG2zYJUdcMSK8WNUmjtkM43oqDF04vmRMWl5PFdiA8QuVIIw0ujM6AyV00CQQD9cei5zQCv8Qzs6YTvHz7tRePFZ+0hB5TvCYQDPtD4HteduLeJ4D0Hhw6Q73AvvEWjezUcjTjBJ7oXKCCKmOYHAkEA6E0QQviR0FC7RFt3JsfmwudR7PN5E7tF5u3AMHQmxyTiQC+XTGmzb3EBDQtbfU+B3oXGcvMfj1oZsXmBJl47jQJBAMQhDlQajNma1MHRxIm3yF6dozH0xtC0qVCCMKLCTbx1Qa5Qb9hGq3PT1DXc1RhbvhhRFDzQHId9UsjwyJ34zKkCQCqWUFqVqgFFexy87242ajCHaz7MwZO0shLfc4hF0dAbN+uEHTadgIStgh3zI3XHeQCd/zF8gOQtEtcInqnV7qs=";


		String bb = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAP0GDDcK55QCA9rEl0KEexJQQtbMDqoZPfSLa4MUTQ6n+ap6xzxZNczVQhSMVZg0tZIGfKayCogLz0nsl7XFJpd+DpjNewDnC42bcMZj637GyORsMAyIKt8wqt7HkHXMz3s8bZeYsMve6hf7m4YxlxUmFkDUp5ChwLSOCunzh5EbAgMBAAECgYBZp6jwYAbKpSQkgoBua28IgDQO1GNl1mfjnPtNiQX86XxH8hVixuGPYQl3Knqx4gtsYKwOjQu6RSUANrtTBzayxvRZL5MJo05rTGlvJ0w9Exs2N2cOgXK9iTN/+b858Ck9f2bOKD+Hg5qVGgjSKRk+/JPlcSIdcrqbMdHoCybgMQJBAP+TDR7bCBrLDo1FlE4Fous2kcPZXYG2zYJUdcMSK8WNUmjtkM43oqDF04vmRMWl5PFdiA8QuVIIw0ujM6AyV00CQQD9cei5zQCv8Qzs6YTvHz7tRePFZ+0hB5TvCYQDPtD4HteduLeJ4D0Hhw6Q73AvvEWjezUcjTjBJ7oXKCCKmOYHAkEA6E0QQviR0FC7RFt3JsfmwudR7PN5E7tF5u3AMHQmxyTiQC+XTGmzb3EBDQtbfU+B3oXGcvMfj1oZsXmBJl47jQJBAMQhDlQajNma1MHRxIm3yF6dozH0xtC0qVCCMKLCTbx1Qa5Qb9hGq3PT1DXc1RhbvhhRFDzQHId9UsjwyJ34zKkCQCqWUFqVqgFFexy87242ajCHaz7MwZO0shLfc4hF0dAbN+uEHTadgIStgh3zI3XHeQCd/zF8gOQtEtcInqnV7qs=";


		System.out.println(xx.equals(bb));





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

