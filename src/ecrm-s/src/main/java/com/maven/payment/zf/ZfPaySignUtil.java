package com.maven.payment.zf;

import java.net.URLDecoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.maven.payment.lfwx.LfwxPayAppConstants;
import com.maven.util.Encrypt;
import com.maven.util.StringUtils;

import net.sf.jsqlparser.expression.StringValue;

public class ZfPaySignUtil {

	/**
	 * 获取当前时间，精确到yyyy-MM-dd HH:mm:ss
	 * 
	 * xufc20130419 修改
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		String strTime = "-1";
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			strTime = dateFormat.format(date);

		} catch (Exception e) {
			strTime = "-1";
			e.fillInStackTrace();
		}
		return strTime;
	}
	
	public static String getRequestUrl(ZfMerchantConfig merchant, ZfOrderConfig order) {
		StringBuffer requestUrl = new StringBuffer();
		
		String merchant_code = merchant.getMerNo();	
		String service_type = "direct_pay";
		String interface_version = "V3.0";		
		String input_charset = "UTF-8";				
		String notify_url = merchant.getNotiUrl();
		String sign_type = "RSA-S";		
		String order_no = order.getOrdernumber();		
		String order_time = getCurrentTime();		
		String order_amount = order.getPaymoney()+"";		
		String return_url = merchant.getPageUrl();
		String bank_code = order.getBankCode();		
		String redo_flag = "1";//不能重复订单号
		String product_name = "abc";
		String product_code = "";
		String product_num = "";
		String product_desc = "";
		String pay_type = "";
		String client_ip = "";
		String extend_param = "";
		String extra_return_param = "";
		String show_url = "";		
		
//		&pay_type=b2c&product_code=B&product_desc=B&product_name=A&product_num=1&redo_flag=1

		/** 数据签名
		签名规则定义如下：
		（1）参数列表中，除去sign_type、sign两个参数外，其它所有非空的参数都要参与签名，值为空的参数不用参与签名；
		（2）签名顺序按照参数名a到z的顺序排序，若遇到相同首字母，则看第二个字母，以此类推，组成规则如下：
		参数名1=参数值1&参数名2=参数值2&……&参数名n=参数值n
		*/
		
		StringBuffer signSrc= new StringBuffer();	
		
		/*
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();  
        parameters.put("bank_code", bank_code);  
        parameters.put("client_ip", client_ip);  
        parameters.put("extend_param", extend_param);  
        parameters.put("extra_return_param", extra_return_param);  
        parameters.put("input_charset", input_charset);
        parameters.put("interface_version", interface_version);  
        parameters.put("merchant_code", merchant_code);
        parameters.put("notify_url", notify_url);  
        parameters.put("order_amount", order_amount);
        parameters.put("order_no", order_no);
        parameters.put("order_time", order_time);
        
        parameters.put("pay_type", pay_type);
        parameters.put("product_code", product_code);
        parameters.put("product_desc", product_desc);
        parameters.put("product_name", product_name);
        parameters.put("product_num", product_num);
        parameters.put("redo_flag", redo_flag);
        parameters.put("return_url", return_url);
        parameters.put("service_type", service_type);
        parameters.put("show_url", show_url);
				
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
        Iterator it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();  
            String k = (String)entry.getKey();  
            Object v = entry.getValue();  
            if(null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k) && !"sign_type".equals(k)) {     
            	signSrc.append(k + "=" + v + "&");
            }  
        }  
		String signInfo = signSrc.substring(0, signSrc.length() - 1);
		*/
		
		if (!"".equals(bank_code)) {
			signSrc.append("bank_code=").append(bank_code).append("&");	
		}
		if (!"".equals(client_ip)) {
			signSrc.append("client_ip=").append(client_ip).append("&");	
		}
		if (!"".equals(extend_param)) {
			signSrc.append("extend_param=").append(extend_param).append("&");	
		}
		if (!"".equals(extra_return_param)) {
			signSrc.append("extra_return_param=").append(extra_return_param).append("&");	
		}
		
		signSrc.append("input_charset=").append(input_charset).append("&");			
		signSrc.append("interface_version=").append(interface_version);
		signSrc.append("&merchant_code=").append(merchant_code);
		signSrc.append("&notify_url=").append(notify_url);					
		signSrc.append("&order_amount=").append(order_amount);
		signSrc.append("&order_no=").append(order_no);		
		signSrc.append("&order_time=").append(order_time);
		
		if (!"".equals(pay_type)) {
			signSrc.append("&pay_type=").append(pay_type);	
		}	
		if (!"".equals(product_code)) {
			signSrc.append("&product_code=").append(product_code);	
		}
		if (!"".equals(product_desc)) {
			signSrc.append("&product_desc=").append(product_desc);	
		}		
		signSrc.append("&product_name=").append(product_name);
		if (!"".equals(product_num)) {
			signSrc.append("&product_num=").append(product_num);	
		}	
		if (!"".equals(redo_flag)) {
			signSrc.append("&redo_flag=").append(redo_flag);	
		}
		if (!"".equals(return_url)) {
			signSrc.append("&return_url=").append(return_url);	
		}	
		
		signSrc.append("&service_type=").append(service_type);
			
		if (!"".equals(show_url)) {
			signSrc.append("&show_url=").append(show_url);	
		}
		String signInfo = signSrc.toString();
		
		
		
		System.out.println("签名原文："+signInfo);
		
		
		String sign="";
		
		
		if("RSA-S".equals(sign_type)) {//sign_type = "RSA-S"
	
		/** 
			1)merchant_private_key，商户私钥，商户按照《密钥对获取工具说明》操作并获取商户私钥。获取商户私钥的同时，也要
   			获取商户公钥（merchant_public_key）并且将商户公钥上传到智付商家后台"公钥管理"（如何获取和上传请看《密钥对获取工具说明》），
  			不上传商户公钥会导致调试的时候报错“签名错误”。
  			2)demo提供的merchant_private_key是测试商户号1111110166的商户私钥，请自行获取商户私钥并且替换
  		*/
  
			String merchant_private_key = merchant.getMerKey();
			
			sign = signByPrivateKey(signInfo,merchant_private_key) ;  
			
			System.out.println("签名密文："+sign);
			System.out.println("签名用的私钥："+merchant_private_key);
			
			//System.out.println("RSA-S商家发送的签名字符串：" + signInfo.length() + " -->" + signInfo);
			//System.out.println("RSA-S商家发送的签名：" + sign.length() + " -->" + sign + "\n");
		
		}
		
		requestUrl.append(merchant.getPayUrl()+"?");
		requestUrl.append("sign").append("=").append(sign).append("&");
		requestUrl.append("merchant_code").append("=").append(merchant_code).append("&");
		requestUrl.append("service_type").append("=").append(service_type).append("&");
		requestUrl.append("interface_version").append("=").append(interface_version).append("&");
		requestUrl.append("input_charset").append("=").append(input_charset).append("&");
		requestUrl.append("notify_url").append("=").append(notify_url).append("&");
		requestUrl.append("sign_type").append("=").append(sign_type).append("&");
		requestUrl.append("order_no").append("=").append(order_no).append("&");
		requestUrl.append("order_time").append("=").append(order_time).append("&");
		requestUrl.append("order_amount").append("=").append(order_amount).append("&");
		requestUrl.append("return_url").append("=").append(return_url).append("&");
		requestUrl.append("bank_code").append("=").append(bank_code).append("&");
		requestUrl.append("redo_flag").append("=").append(redo_flag).append("&");
		requestUrl.append("product_name").append("=").append(product_name).append("&");
		
//		requestUrl.append("product_code").append("=").append(product_code).append("&");
//		requestUrl.append("product_num").append("=").append(product_num).append("&");
//		requestUrl.append("product_desc").append("=").append(product_desc).append("&");
//		requestUrl.append("pay_type").append("=").append(pay_type).append("&");
//		requestUrl.append("client_ip").append("=").append(client_ip).append("&");
//		requestUrl.append("extend_param").append("=").append(extend_param).append("&");
//		requestUrl.append("extra_return_param").append("=").append(extra_return_param).append("&");
//		requestUrl.append("show_url").append("=").append(show_url).append("");
		
		System.err.println(order.getOrdernumber()+"===============支付请求链接="+requestUrl);
		return requestUrl.toString();
	}
	
	
	public static String signByPrivateKey(String data, String privateKey) {
		try {

		    byte[] keyBytes = com.hy.pull.common.util.Base64.decode(privateKey);
		    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		    PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		    Signature signature = Signature.getInstance("MD5withRSA");
		    signature.initSign(privateK);
		    signature.update(data.getBytes("UTF-8"));
		    return com.hy.pull.common.util.Base64.encode(signature.sign()).replaceAll("\n", "").replaceAll("\r\n", "").replaceAll("\r", "");
		    
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static boolean validateSignByPublicKey(String paramStr, String publicKey, String signedData)
		    throws Exception
		  {
		    byte[] keyBytes = com.hy.pull.common.util.Base64.decode(publicKey);
		    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		    PublicKey publicK = keyFactory.generatePublic(keySpec);
		    Signature signature = Signature.getInstance("MD5withRSA");
		    signature.initVerify(publicK);
		    signature.update(paramStr.getBytes("UTF-8"));
		    return signature.verify(com.hy.pull.common.util.Base64.decode(signedData));
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
	public static boolean checkResponseSign(HttpServletRequest request,Map<String, Object> __mer_params) {
		
		try {
			// 接收智付返回的参数
			request.setCharacterEncoding("UTF-8");
			String interface_version = (String) request.getParameter("interface_version");
			String merchant_code = (String) request.getParameter("merchant_code");
			String notify_type = (String) request.getParameter("notify_type");
			String notify_id = (String) request.getParameter("notify_id");
			String sign_type = (String) request.getParameter("sign_type");
			String dinpaySign= (String) request.getParameter("sign");
			String order_no = (String) request.getParameter("order_no");
			String order_time = (String) request.getParameter("order_time");
			String order_amount = (String) request.getParameter("order_amount");
			String extra_return_param = (String) request.getParameter("extra_return_param");
			String trade_no = (String) request.getParameter("trade_no");
			String trade_time= (String) request.getParameter("trade_time");
			String trade_status = (String) request.getParameter("trade_status");
			String bank_seq_no= (String) request.getParameter("bank_seq_no");
			
					

			/** 数据签名
			签名规则定义如下：
			（1）参数列表中，除去sign_type、sign两个参数外，其它所有非空的参数都要参与签名，值为空的参数不用参与签名；
			（2）签名顺序按照参数名a到z的顺序排序，若遇到相同首字母，则看第二个字母，以此类推，组成规则如下：
			参数名1=参数值1&参数名2=参数值2&……&参数名n=参数值n
			*/

		 
		 	StringBuilder signStr = new StringBuilder();
		 	if(null != bank_seq_no && !bank_seq_no.equals("")) {
		 		signStr.append("bank_seq_no=").append(bank_seq_no).append("&");
		 	}
		 	if(null != extra_return_param && !extra_return_param.equals("")) {
		 		signStr.append("extra_return_param=").append(extra_return_param).append("&");
		 	}
		 	signStr.append("interface_version=").append(interface_version).append("&");
		 	signStr.append("merchant_code=").append(merchant_code).append("&"); 	
		 	signStr.append("notify_id=").append(notify_id).append("&");	 	
		 	signStr.append("notify_type=").append(notify_type).append("&"); 	
		 	signStr.append("order_amount=").append(order_amount).append("&");
		 	signStr.append("order_no=").append(order_no).append("&");
		 	signStr.append("order_time=").append(order_time).append("&");
		 	signStr.append("trade_no=").append(trade_no).append("&");	
		 	signStr.append("trade_status=").append(trade_status).append("&");
			signStr.append("trade_time=").append(trade_time);
			
			

		 	String signInfo =signStr.toString();
			
			System.out.println("智付返回的签名字符串：" + signInfo.length() + " -->" + signInfo);		
			System.out.println("智付返回的签名：" + dinpaySign.length() + " -->" + dinpaySign);								
			
			boolean verfiy = false;
			
			if("RSA-S".equals(sign_type)) {	//sign_type = "RSA-S"		
				
			/**
				1)dinpay_public_key，智付公钥，每个商家对应一个固定的智付公钥（不是使用工具生成的密钥merchant_public_key，不要混淆），
				     即为智付商家后台"公钥管理"->"智付公钥"里的绿色字符串内容
				2)demo提供的dinpay_public_key是测试商户号1111110166的智付公钥，请自行复制对应商户号的智付公钥进行调整和替换
			*/
			
			String dinpay_public_key = __mer_params.get("PUBLIC_KEY").toString();		
			verfiy=validateSignByPublicKey(signInfo, dinpay_public_key, dinpaySign);
				
			}
			
			if("RSA".equals(sign_type)){//数字证书加密方式 sign_type = "RSA"
				/*************
				String rootPath=this.getClass().getResource("/").toString();
				
				//请在商家后台证书下载处申请和下载pfx数字证书，一般要1~3个工作日才能获取到,1111110166.pfx是测试商户号1111110166的数字证书
				String path= rootPath.substring(rootPath.indexOf("/")+1,rootPath.length()-8)+"certification/1111110166.pfx";	
				String pfxPass = "87654321"; //证书密钥，初始密码是商户号		
				
				RSAWithHardware mh = new RSAWithHardware();						
				mh.initSigner(path, pfxPass);		  
				verfiy = mh.validateSignByPubKey(merchant_code, signInfo, dinpaySign);
				
				*****************/
			}
	        
	        return verfiy;
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		return false;
	}
	public static void main(String[] args) {
		try {
			String privattkey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANP/uoYTSj3Zi/dwpHUSP0DCATfNd52410jUXUmfGoJloErksCsYn+d9Hfmqb+RWXglUTsIo20GvYWQzcBKoqkUcRoWwP3Pq8KGJTPjHiL+WCUNB/Yl8GJqQgqRoAFRERLyjDtO+ohYocPEyM6gS9+aYOhtsmuLhGyk+qKKVdBlVAgMBAAECgYAhQB/aPZZMYx2f3qv8rLL9DjCwtupG86HYi25iSLEYuKXOeVXkPIWAAlC8Qu4vyxgOsIl1zICvKbnFxIeUdZ3W1bkFLYWsec6VGN9EDKhkNCIiBGgNGPSM2mOHIysRseECA76ATTfiLa9H4sCQ05kQSSz75zLyed1Qw4sQJgLmVQJBAP9MNONLKkFlUpuOnyKHxIT7rymzGQv/fQfoKI7cudhuuLxelOfmPIv7zpTdzBf+HR34y5npi3sFNhdnm2iE4QMCQQDUlQdkQbfH5u/N+v8EQUoNe7SOyarD/McwYUN4c8BaYohgtfj2QlP0vCAl7cqK7iJiCmTdAdfKHCkTlVCRsxDHAkBC75kiSw/eK5G4JXHF3PECqPapKUMU6Ty/+PfBVVAI1ibrhKToFG6liTUFxQ2A8OhzdkrzpM8kRx6CjnCpu8U/AkBwRVTmCh85oCm+K3VLlf+6Jz8wuilJ4NKCJpwvnVJVafyEtxaJWHZmpoSEA5YY0bOu+7ZRUovY3g6DnTQz/tI5AkEAvrOoBkA7AsSOaW1srPUwJrtoJO1j3/S2T89fkJbO2wzGcF/ojthPLDMw0GsITIUR4ZiV+HLBJTSMQ+qQUOYL/g==";
//			String privatekey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANP/uoYTSj3Zi/dwpHUSP0DCATfNd52410jUXUmfGoJloErksCsYn+d9Hfmqb+RWXglUTsIo20GvYWQzcBKoqkUcRoWwP3Pq8KGJTPjHiL+WCUNB/Yl8GJqQgqRoAFRERLyjDtO+ohYocPEyM6gS9+aYOhtsmuLhGyk+qKKVdBlVAgMBAAECgYAhQB/aPZZMYx2f3qv8rLL9DjCwtupG86HYi25iSLEYuKXOeVXkPIWAAlC8Qu4vyxgOsIl1zICvKbnFxIeUdZ3W1bkFLYWsec6VGN9EDKhkNCIiBGgNGPSM2mOHIysRseECA76ATTfiLa9H4sCQ05kQSSz75zLyed1Qw4sQJgLmVQJBAP9MNONLKkFlUpuOnyKHxIT7rymzGQv/fQfoKI7cudhuuLxelOfmPIv7zpTdzBf+HR34y5npi3sFNhdnm2iE4QMCQQDUlQdkQbfH5u/N+v8EQUoNe7SOyarD/McwYUN4c8BaYohgtfj2QlP0vCAl7cqK7iJiCmTdAdfKHCkTlVCRsxDHAkBC75kiSw/eK5G4JXHF3PECqPapKUMU6Ty/+PfBVVAI1ibrhKToFG6liTUFxQ2A8OhzdkrzpM8kRx6CjnCpu8U/AkBwRVTmCh85oCm+K3VLlf+6Jz8wuilJ4NKCJpwvnVJVafyEtxaJWHZmpoSEA5YY0bOu+7ZRUovY3g6DnTQz/tI5AkEAvrOoBkA7AsSOaW1srPUwJrtoJO1j3/S2T89fkJbO2wzGcF/ojthPLDMw0GsITIUR4ZiV+HLBJTSMQ+qQUOYL/g==";
			/**
			String signInfo = "bank_code=ICBC&client_ip=183.62.225.12&input_charset=UTF-8&interface_version=V3.0&merchant_code=4000001349&notify_url=http://paa.gzocmy.top//TPayment/ZfPayCallback&order_amount=150.0&order_no=1A00EB73B76E4510A7C7DF53F62E6D55&order_time=2017-05-17 19:17:26&pay_type=b2c&product_code=B&product_desc=B&product_name=A&product_num=1&redo_flag=1&service_type=direct_pay";
			
			String sss = signByPrivateKey(signInfo, privattkey);
			System.out.println(sss);
			
//			String public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCaea+CbaL0IjoACLtdGSKMGC01ohtSa4X6t+oOItF29d/yCrByEloB97ET+0UxUuSQ7yH6OLBOAfEeCugMI5P1vy1b942rWzHnNylEPAB0tmQUjb6elZXGdwwKr14dDNOQ+HOIiVA8/SFzW+41Z+srTQVTqWq8MO4+soMThCpRqwIDAQAB";
			String public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDT/7qGE0o92Yv3cKR1Ej9AwgE3zXeduNdI1F1JnxqCZaBK5LArGJ/nfR35qm/kVl4JVE7CKNtBr2FkM3ASqKpFHEaFsD9z6vChiUz4x4i/lglDQf2JfBiakIKkaABURES8ow7TvqIWKHDxMjOoEvfmmDobbJri4RspPqiilXQZVQIDAQAB";
			String dinpaySign = "ReQqg1J40/kJHrb6ec3NTP2Uz+JJBIZMB/LXttSkGrTpZ5PPpeLqSxXJLO93MV/9S8ile7cqQf9ILIdOsIx9AfwOfagnGDOoVw+fjpvnuE6zGJ+hw+zyR0pFPh+Rgt2BVyhU58NZ0B0aytr6jzXV4Zb1/gQhpFTw4jQoUGr8nMQ=";
			boolean verfiy= validateSignByPublicKey(signInfo, public_key, sss);
			System.out.println(verfiy);
			***/
			
			String signInfo = "bank_code=ICBC&input_charset=UTF-8&interface_version=V3.0&merchant_code=4000001349&notify_url=http://paa.gzocmy.top//TPayment/ZfPayCallback&order_amount=12.0&order_no=CAF931237E2F48DBA24DB2ECA982BF3A&order_time=2017-05-22 16:13:14&product_name=abc&redo_flag=1&return_url=http://paa.gzocmy.top//TPayment/success&service_type=direct_pay";
			String str2 = "bank_code=ICBC&input_charset=UTF-8&interface_version=V3.0&merchant_code=4000001349&notify_url=http://15l0549c66.iask.in:45191/bankPay/offli&order_amount=0.1&order_no=20170519182701&order_time=2017-05-19 18:27:01&product_name=test&redo_flag=1&return_url=http://15l0549c66.iask.in:45191/bankPay&service_type=direct_pay";
			
			String sss = signByPrivateKey(signInfo, privattkey);
			
			System.out.println(sss);
			System.out.println("cW051qMRxiD0irLHgvIWoAeE1ChxBQZneO+nEeT1u9Z65/wHg5kXmjciRRUkA44aM/MAk2POjy0m/EQyUZpcUtWyFfbfasgKzi2ztNsHNRrfVcchaRE4qUytdmXmb0sHKx9Jsjnfe0m3mZhJBLN5ZKJUjBTw+ga38aDv+EFePQA=");
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

