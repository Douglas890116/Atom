package com.maven.payment.zfwx;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.maven.payment.lfwx.LfwxPayAppConstants;
import com.maven.util.Encrypt;
import com.maven.util.HttpPostUtil;
import com.maven.util.StringUtils;

import net.sf.json.JSONObject;
import net.sf.jsqlparser.expression.StringValue;

public class ZfWXPaySignUtil {

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
	
	public static String getRequestUrl(ZfWXMerchantConfig merchant, ZfWXOrderConfig order) {
		StringBuffer requestUrl = new StringBuffer();
		
		String merchant_code = merchant.getMerNo();	
		String service_type = order.getBankCode();//支付类型
		String interface_version = "V3.1";		
		String input_charset = "UTF-8";				
		String notify_url = merchant.getNotiUrl();
		String sign_type = "RSA-S";		
		String order_no = order.getOrdernumber();		
		String order_time = getCurrentTime();		
		String order_amount = order.getPaymoney()+"";		
		String return_url = merchant.getPageUrl();
		String product_name = order.getGoodName();		
		String product_code = order.getGoodsDetail();
		String product_num = "1";
		String product_desc = order.getGoodsDetail();
		String client_ip = "183.62.225.12";
		String extend_param = "";
		String extra_return_param = "2";

		/** 数据签名
		签名规则定义如下：
		（1）参数列表中，除去sign_type、sign两个参数外，其它所有非空的参数都要参与签名，值为空的参数不用参与签名；
		（2）签名顺序按照参数名a到z的顺序排序，若遇到相同首字母，则看第二个字母，以此类推，组成规则如下：
		参数名1=参数值1&参数名2=参数值2&……&参数名n=参数值n
		*/
		
		StringBuffer signSrc= new StringBuffer();	
		
		
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();  
		parameters.put("merchant_code", merchant_code);
        parameters.put("service_type", service_type);  
        parameters.put("notify_url", notify_url);  
        parameters.put("interface_version", interface_version);  
        parameters.put("client_ip", client_ip);  
        parameters.put("sign_type", sign_type);  
        parameters.put("order_no", order_no);
        parameters.put("order_time", order_time);
        parameters.put("order_amount", order_amount);
        parameters.put("product_name", product_name);
        parameters.put("product_code", product_code);
        parameters.put("product_desc", product_desc);
        parameters.put("product_num", product_num);
        
        parameters.put("extend_param", extend_param);  
        parameters.put("extra_return_param", extra_return_param);  
        
				
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
			parameters.put("sign", sign);
			
			//System.out.println("RSA-S商家发送的签名字符串：" + signInfo.length() + " -->" + signInfo);
			//System.out.println("RSA-S商家发送的签名：" + sign.length() + " -->" + sign + "\n");
		
		}
        
		System.out.println("完整的请求参数："+parameters);
		
		requestUrl.append(merchant.getPayUrl()+"?sign="+sign).append("&").append(signSrc.toString());
		
		String result = HttpPostUtil.doPostSubmitMap(merchant.getPayUrl(), parameters);
		System.out.println("付款结果："+result);
		Map<String, String> data = getQueryParams(result);
		
		//准备验证签名
		SortedMap<Object,Object> parameters2 = new TreeMap<Object,Object>();  
		it = data.keySet().iterator(); 
        while(it.hasNext()) {  
        	String k = (String)it.next();  
            String v = data.get(k);
            if(null != v && !"".equals(v) ) {     
            	parameters2.put(k, v);
            }  
        }  
        
        signSrc = new StringBuffer();
        es = parameters2.entrySet();//所有参与传参的参数按照accsii排序（升序）  
        it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();  
            String k = (String)entry.getKey();  
            Object v = entry.getValue();  
            if(null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k) && !"sign_type".equals(k)) {     
            	signSrc.append(k + "=" + v + "&");
            }  
        }  
		signInfo = signSrc.substring(0, signSrc.length() - 1);
		System.out.println("验证签名原文："+signInfo);
		
		String dinpaySign = data.get("sign");
        
		String dinpay_public_key = merchant.getPubKey();		
		
		try {
			boolean verfiy = validateSignByPublicKey(signInfo, dinpay_public_key, dinpaySign);
			
			if(verfiy) {
				System.out.println("验证签名通过:"+data);
				
				if(data.get("result_code").equals("0")) {
					return data.get("qrcode");
				} else {
					System.out.println("获取二维码失败："+data);
				}
				
			} else {
				System.out.println("验证签名不通过");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("验证签名异常："+e.getMessage());
		}
		
		return requestUrl.toString();
	}
	
	
	private static String signByPrivateKey(String data, String privateKey) {
		try {

		    byte[] keyBytes = com.hy.pull.common.util.Base64.decode(privateKey);
		    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		    PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		    Signature signature = Signature.getInstance("MD5withRSA");
		    signature.initSign(privateK);
		    signature.update(data.getBytes("utf-8"));
		    return com.hy.pull.common.util.Base64.encode(signature.sign()).replaceAll("\n", "").replaceAll("\r\n", "").replaceAll("\r", "");
		    
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private static boolean validateSignByPublicKey(String paramStr, String publicKey, String signedData)
		    throws Exception
		  {
		    byte[] keyBytes = com.hy.pull.common.util.Base64.decode(publicKey);
		    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		    PublicKey publicK = keyFactory.generatePublic(keySpec);
		    Signature signature = Signature.getInstance("MD5withRSA");
		    signature.initVerify(publicK);
		    signature.update(paramStr.getBytes("utf-8"));
		    return signature.verify(com.hy.pull.common.util.Base64.decode(signedData));
		  }
	
	
	/**
	 * 返回单个节点
	 * @param xmlStr
	 * @return
	 */
	private static Map<String, String> getQueryParams(String xmlStr) {
		if(xmlStr==null || xmlStr.equals("")) {
			return null;
		}
		Document doc = null;
		Map<String, String> data = new HashMap<String, String>();
		try {
			doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML    
			Element root = doc.getRootElement();
			List<Element> list2 = root.selectNodes("//dinpay/response");
			
			for (Element element : list2) {
				List<Element> list = element.elements();
				for (Element element2 : list) {
					data.put(element2.getName(), element2.getStringValue());
					//System.out.println(element2.getName() +"-"+ element2.getStringValue());
				}
			}
			return data;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		try {
//			String privattkey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBANBOhV29SrNcHHGuy1JYZs36roWjPw55C266WfrRMu84uHAAkqawcuaiMXCO1DnLqqKpNPS+BolkDM7NsL5y1BJVcF1yxE1dzeiPlDUmi8++w1lArXxJ22KG64siIcRawOcuiZsbJ7q/pAiw9yeeQJHgGXUiYTOyVjOQ9xyNVSwBAgMBAAECgYAu2Bmh1PnBkAtNfXN31y13UNKCdw2tIYdypTGq9/SuW2EOvvI7zh5owzy/BT+wp5596fNgvoMVzowazaLbkVMjTBciG897ftl2mKLFIt14lbkTmA+vL7+R5hWZ/ONR92jJJsxwtaAw5IatZECITs6kAAZve0tZKb6Y8e7QmYOjYQJBAPvHH/uA27LkA69yj7qWSAdZTblTznjasFOah4KYfpS7EPR7KiKhGR7qpr8LRCLOjuI6+CQahhbzVA6J6NATrK0CQQDTzMaafz5rkNR5+MLRdY6b5+ueFTggcLRTWwSfRTOpR5cTkknjE1rHvqoebMPGpCmTjoZpm9F5e+ijDT2ZifMlAkAoJX4yCj/97GGyDiBg7imaEmFUVcqsdqJ7Ej7dUheM+68ebK0xTGLHDT99YvyAWFNsO17p0tjiu/YBQ3g+BYUZAkBDXbWpvGExO26poPruPnLumdSyglNI1jA09xVAR3WIGTIvUm9X33cXY0iexr96dgqXWVTdUGdPOZ8kBXkbVepVAkAfTY9kioKQkACzJhGZwtUiLrpKNrjjf53SkQW4mf7IuaSkKpMldKA7nKaoeNgCx/yNdBDkqtPZrV3TebQkEhvg";
			String privattkey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANP/uoYTSj3Zi/dwpHUSP0DCATfNd52410jUXUmfGoJloErksCsYn+d9Hfmqb+RWXglUTsIo20GvYWQzcBKoqkUcRoWwP3Pq8KGJTPjHiL+WCUNB/Yl8GJqQgqRoAFRERLyjDtO+ohYocPEyM6gS9+aYOhtsmuLhGyk+qKKVdBlVAgMBAAECgYAhQB/aPZZMYx2f3qv8rLL9DjCwtupG86HYi25iSLEYuKXOeVXkPIWAAlC8Qu4vyxgOsIl1zICvKbnFxIeUdZ3W1bkFLYWsec6VGN9EDKhkNCIiBGgNGPSM2mOHIysRseECA76ATTfiLa9H4sCQ05kQSSz75zLyed1Qw4sQJgLmVQJBAP9MNONLKkFlUpuOnyKHxIT7rymzGQv/fQfoKI7cudhuuLxelOfmPIv7zpTdzBf+HR34y5npi3sFNhdnm2iE4QMCQQDUlQdkQbfH5u/N+v8EQUoNe7SOyarD/McwYUN4c8BaYohgtfj2QlP0vCAl7cqK7iJiCmTdAdfKHCkTlVCRsxDHAkBC75kiSw/eK5G4JXHF3PECqPapKUMU6Ty/+PfBVVAI1ibrhKToFG6liTUFxQ2A8OhzdkrzpM8kRx6CjnCpu8U/AkBwRVTmCh85oCm+K3VLlf+6Jz8wuilJ4NKCJpwvnVJVafyEtxaJWHZmpoSEA5YY0bOu+7ZRUovY3g6DnTQz/tI5AkEAvrOoBkA7AsSOaW1srPUwJrtoJO1j3/S2T89fkJbO2wzGcF/ojthPLDMw0GsITIUR4ZiV+HLBJTSMQ+qQUOYL/g==";
			String signInfo = "bank_code=ICBC&client_ip=183.62.225.12&input_charset=UTF-8&interface_version=V3.0&merchant_code=4000001349&notify_url=http://paa.gzocmy.top//TPayment/ZfPayCallback&order_amount=150.0&order_no=1A00EB73B76E4510A7C7DF53F62E6D55&order_time=2017-05-17 19:17:26&pay_type=b2c&product_code=B&product_desc=B&product_name=A&product_num=1&redo_flag=1&service_type=direct_pay";
			
			String sss = signByPrivateKey(signInfo, privattkey);
			System.out.println(sss);
			
//			String public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCaea+CbaL0IjoACLtdGSKMGC01ohtSa4X6t+oOItF29d/yCrByEloB97ET+0UxUuSQ7yH6OLBOAfEeCugMI5P1vy1b942rWzHnNylEPAB0tmQUjb6elZXGdwwKr14dDNOQ+HOIiVA8/SFzW+41Z+srTQVTqWq8MO4+soMThCpRqwIDAQAB";
			String public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDT/7qGE0o92Yv3cKR1Ej9AwgE3zXeduNdI1F1JnxqCZaBK5LArGJ/nfR35qm/kVl4JVE7CKNtBr2FkM3ASqKpFHEaFsD9z6vChiUz4x4i/lglDQf2JfBiakIKkaABURES8ow7TvqIWKHDxMjOoEvfmmDobbJri4RspPqiilXQZVQIDAQAB";
			String dinpaySign = "ReQqg1J40/kJHrb6ec3NTP2Uz+JJBIZMB/LXttSkGrTpZ5PPpeLqSxXJLO93MV/9S8ile7cqQf9ILIdOsIx9AfwOfagnGDOoVw+fjpvnuE6zGJ+hw+zyR0pFPh+Rgt2BVyhU58NZ0B0aytr6jzXV4Zb1/gQhpFTw4jQoUGr8nMQ=";
			boolean verfiy= validateSignByPublicKey(signInfo, public_key, sss);
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

