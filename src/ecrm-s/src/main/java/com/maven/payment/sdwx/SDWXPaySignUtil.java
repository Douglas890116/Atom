package com.maven.payment.sdwx;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.rmi.RemoteException;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hy.pull.common.util.api.Enum_MSG;
import com.maven.payment.sdpay.CustomerLocator;
import com.maven.payment.sdpay.CustomerSoapStub;
import com.maven.payment.sdpay.MyDecrypt;
import com.maven.payment.sdpay.MyEncrypt;
import com.maven.util.Encrypt;
import com.maven.util.HttpPostUtil;
import com.maven.util.RandomStringNum;
import com.maven.util.StringUtils;

import net.sf.jsqlparser.expression.StringValue;

public class SDWXPaySignUtil {

	/**
	 * 付款接口=回调验证等
	 * @param merchant
	 * @param order
	 * @return
	 */
	public static Map<String, String> payoutCallback(HttpServletRequest request,Map<String, Object> __mer_params) {
		
//		<TransferInfomation> 
//		  <Id>12483</Id> 
//		  <RolloutAccount>622622068888888</RolloutAccount> 
//		  <IntoAccount>6228480040816139519</IntoAccount> 
//		  <IntoName>张三</IntoName> 
//		  <IntoBank1>中国农业银行股份有限公司</IntoBank1> 
//		  <IntoAmount>0.0300</IntoAmount> 
//		  <RecordsState>2</RecordsState> //讯息状态(0未处理，1正在处理，2成功，3失败，4其他) 
//		  <Tip>转账成功</Tip> 
//		  <ApplicationTime>2011/10/18 21:57:29</ApplicationTime> 
//		  <ProcessingTime>2011/10/18 21:58:08</ProcessingTime> 
//		  <SerialNumber>703956622026841</SerialNumber> //商家流水号 
//		  <beforeMoney>7491.310</beforeMoney> 
//		  <afterMoney>7491.280</afterMoney> 
//		  <bankNumber>X201110181278919841013650</bankNumber> 
//		</TransferInfomation> 
		
//		String pid = "egg777";
		String HiddenField1 = request.getParameter("HiddenField1");//加密讯息
	
//		String key1 = __mer_params.get("key1").toString();
//		String key2 = __mer_params.get("key2").toString();
		
		String key1 = "b06Alyqn/u0=";
		String key2 = "vwpBOJeKTKg=";
		
//		String md5key = __mer_params.get("md5key").toString();
		
		String decryptMsg = "";
		
		try {
			decryptMsg = MyDecrypt.DecryptData(HiddenField1, key1, key2);
			
			Map<String, String> data = getQueryParams(decryptMsg);
			
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 付款接口=请求
	 * @param merchant
	 * @param order
	 * @return
	 */
	public static String payout(SDWXMerchantConfig merchant, SDWXOrderConfig order) {
		
		String merchantsId = merchant.getMerchantid();//也就是操作员号
		String key1 = merchant.getKey1();
		String key2 = merchant.getKey2();
		String apiUrl = merchant.getPayUrl();
		
		
		StringBuffer xmldata = new StringBuffer();
		xmldata.append("<TransferInfomation>");
		xmldata.append("<Id>").append("0").append("</Id>");
		xmldata.append("<IntoAccount>").append(order.getIntoAccount()).append("</IntoAccount>");//转入卡号，只允许使用字母与数字组合 
		xmldata.append("<IntoName>").append(order.getIntoName()).append("</IntoName>");//转入姓名
		xmldata.append("<IntoBank1>").append(order.getIntoBank1()).append("</IntoBank1>");//转入银行
		xmldata.append("<IntoBank2>").append(" ").append("</IntoBank2>");//转入分行
		xmldata.append("<IntoAmount>").append(order.getIntoAmount()).append("</IntoAmount>");//转入金额，数值必须大于0；数值如有小数点则不可多于两个位
		xmldata.append("<SerialNumber>").append(order.getSerialNumber()).append("</SerialNumber>");//商家流水号，单号
		xmldata.append("</TransferInfomation>");
		
		System.out.println("key1="+key1);
		System.out.println("key2="+key2);
		System.out.println("LoginAccount="+merchantsId);
		
		System.out.println("xmldata加密前="+xmldata);
		String transsEncrypt = MyEncrypt.EncryptData(xmldata.toString(), key1, key2);
		System.out.println("xmldata加密后="+transsEncrypt);
		
		try {
			CustomerSoapStub service = (CustomerSoapStub)new CustomerLocator().getCustomerSoap(new URL(apiUrl));
			
//			//如果返回结果大于0则表示提交成功，返回值为这条记录的Id。如果返回值小于0代表提交失败，通过返回值可以判断失败的原因。
			
			int result = service.getFund(merchantsId, transsEncrypt);
			System.out.println("请求结果："+result);
			
			if(result > 0) {
				return Enum_MSG.成功.message("提交成功，流水号："+result);
			}
			
			return Enum_MSG.逻辑事务异常.message("提交出款失败，返回状态："+result);
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Enum_MSG.系统错误.message("系统异常："+e.getMessage());
			
		}catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Enum_MSG.系统错误.message("系统异常："+e.getMessage());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Enum_MSG.系统错误.message("系统异常："+e.getMessage());
		}
		
	}
	
	
	
	/**
	 * 获取当前时间，精确到yyyy-MM-dd HH:mm:ss
	 * 
	 * xufc20130419 修改
	 * 
	 * @return
	 */
	private static String getCurrentTime() {
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
	private static String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
	public static String getRequestUrl(SDWXMerchantConfig merchant, SDWXOrderConfig order) {
		order.setTime(getCurrentTime());
		
		StringBuffer requestUrl = new StringBuffer();
		
		String payurl = "";
		if(order.getPaytype().equals(SDWXPayAppConstants.Enum_PaymentType.个人计算机.value)) {
			payurl = merchant.getPayUrl6006();
		} else if(order.getPaytype().equals(SDWXPayAppConstants.Enum_PaymentType.手机.value)) {
			payurl = merchant.getPayUrl6009();
		} else if(order.getPaytype().equals(SDWXPayAppConstants.Enum_PaymentType.手机无插件.value)) {
			payurl = merchant.getPayUrl6010();
		}
		/*
		1.  准备提交的XML数据  [XML_DATA]. 
		2.  准备  DES 加密钥匙  (key1 [KEY1] and key2 [KEY2]).  
		3.  准备  32 字符串的  MD5 钥匙  [MD5_KEY]. 
		4.  计算  MD5 的加密  [MD5_ENCRYPT]. 
		
		[MD5_ENCRYPT] = MD5( [XML_DATA] + [MD5_KEY] )
		
		5.  使用DES的方法加密数据  [DES_DATA].
		
		[RANDOM_VALUE] = Random() 
		[DATA_TO_ENCRYPT] = [XML_DATA] + [MD5_ENCRYPT] + MD5([RANDOM_VALUE]) 
		[DES_DATA] = DES_ENCRYPT([DATA_TO_ENCRYPT], [KEY1], [KEY2])
		*/
		
		StringBuffer xmldata = new StringBuffer();
		xmldata.append(XML_HEADER);
		xmldata.append("<message>");
		xmldata.append("<cmd>").append(order.getPaytype()).append("</cmd>");
		xmldata.append("<merchantid>").append(merchant.getMerchantid()).append("</merchantid>");
		xmldata.append("<language>").append(merchant.getLanguage()).append("</language>");
		xmldata.append("<userinfo>");
		xmldata.append("<order>").append(order.getOrder()).append("</order>");
		xmldata.append("<username>").append(order.getUsername()).append("</username>");
		xmldata.append("<money>").append(order.getMoney()).append("</money>");
		xmldata.append("<unit>").append(merchant.getUnit()).append("</unit>");
		xmldata.append("<time>").append(order.getTime()).append("</time>");
		xmldata.append("<remark>").append(order.getRemark()).append("</remark>");
		xmldata.append("<backurl>").append(merchant.getBackurl()).append("</backurl>");
		xmldata.append("<backurlbrowser>").append(merchant.getBackurlbrowser()).append("</backurlbrowser>");
		xmldata.append("</userinfo>");
		xmldata.append("</message>");
		
		String xml = xmldata.toString();
		System.out.println(xml);
		
		String key1 = merchant.getKey1();
		System.out.println("key1="+key1);
		String key2 = merchant.getKey2();
		System.out.println("key2="+key2);
		String md5key = merchant.getMd5key();  //md5 key
		System.out.println("md5key="+md5key);
		
		String md5 = MD5Encode.encode(xml + md5key);
		System.out.println("md5="+md5);
		String d = xml + md5.toLowerCase();
		String DES_DATA = "";
		try {
			DES_DATA = EncryptUtil.EncryptData(d, key1, key2);
//			DES_DATA = DES_DATA.substring(0, DES_DATA.length() - 32);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		requestUrl.append(payurl).append("?");
		requestUrl.append("pid=").append(merchant.getMerchantid()).append("&");
		requestUrl.append("des=").append(URLEncoder.encode(DES_DATA)).append("");//加密的订单参数信息
		
		
		
		return requestUrl.toString();
	}
	
	
	/**
	 * 对返回结果：
	 * 
	 * 
	 * @param req
	 * @param MER_KEY
	 * @return
	 */
	public static Map<String, Object> checkResponseSign(HttpServletRequest request,Map<String, Object> __mer_params) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("verify", false);
		
		try {
			System.err.println("__mer_params="+__mer_params);
			
			String pid = (String) request.getParameter("pid");
			String des = (String) request.getParameter("res");
			/*
			解密過程： 
			1.  准备  DES 解密钥匙  (key1 [KEY1] and key2 [KEY2]).   
			2.  准备  32 字符串的  MD5 钥匙  [MD5_KEY]. 
			3.  准备要解密的数据  (HTTP POST的加密数据) [DES_DATA]. 
			[DECRYPT_DATA] = SD 2PAY_DECRYPT( [DES_DATA], [KEY1], [KEY2] ) 
			 
			4.  分拆已解密的XML数据([XML_DATA]) 和  MD5 数据  ([MD5_DATA]) 
			XML数据是加密的数据除去最后的32个字符。而最后的32字符是MD5数
			据。 
			[XML_DATA] = REMOVE_LAST_32_CHAR( [DECRYPT_DATA] ) 
			[MD5_DATA] = GET_LAST_32_CHAR( [DECRYPT_DATA] ) 
			 
			5.  通过计算MD5加密验证数据。  ([MD5_DATA_VERIFY]). 
			结果  [MD5_DATA_VERIFY]  必须跟  [MD5_DATA]  一样。 
			[MD5_DATA_VERIFY] = MD5( [XML_DATA] + [MD5_KEY] ) 
	        */
			
			String key1 = __mer_params.get("key1").toString();
			String key2 = __mer_params.get("key2").toString();
			String md5key = __mer_params.get("md5key").toString();
			
			String DECRYPT_DATA = DecryptUtil.DecryptData(des, key1, key2);
			System.err.println("DECRYPT_DATA="+DECRYPT_DATA);
			String XML_DATA = DECRYPT_DATA.substring(0, DECRYPT_DATA.length() - 32);
			System.err.println("XML_DATA="+XML_DATA);
			String MD5_DATA = DECRYPT_DATA.substring(DECRYPT_DATA.length() - 32);
			System.err.println("MD5_DATA="+MD5_DATA);
			String MD5_DATA_VERIFY = MD5Encode.encode(XML_DATA + md5key).toLowerCase();
			System.err.println("MD5_DATA_VERIFY="+MD5_DATA_VERIFY);
			
			
			boolean verify = MD5_DATA.equals(MD5_DATA_VERIFY);//校验
			
			result.put("verify", verify);
			result.put("data", getQueryParams(XML_DATA));
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		return result;
	}
	
	public static Map<String, Object> test() {
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("verify", false);
		
		try {
			
			String pid = "EG9783";
			String des = "4zWjqNN9KqLYry2stg73evIWlI7D++o3hPLLxxElUbMe+Umb/05xLF1W2/C+/CH7OO7YbhvqHVwMcbWB1mcJxtJbtjJHhzGJA2jOtQ4gpE7byz7hivsvuTC1tNWFdAJPOuolMaIc/LsO/DMj6J8gkCbIr9nKvlo1fDJ3FXqj6fAnC2lkRVz8ziABvEpWQnUGsclmOgD+1umL5vVJoZPnTMJfhJOO2d5BO7JJPA9zBt2eaGybMPfZP4Bq1OwU8fiier4o61hA4KTgdIDKF2H9uCHwgt+ZG6GQeZREU5VZT4bLNf7i+lV4SOrNDrw5cLFiSWBg1dS8QC4es2ARhLLzwS6oNUv2M3iGIvgrD5i9HJAfZoKurXH5ivjMprKmvaHuUleu1IOkUVzWOsmp1NOyXc5z2eTYgMjen9tv8MPpUGPpEtXVE7jWUUaIzkyWrmyXkYE9/ByYfMxSy1N65HwP9MQWHjplQHeXvPVrt+73SGM=";
			/*
			解密過程： 
			1.  准备  DES 解密钥匙  (key1 [KEY1] and key2 [KEY2]).   
			2.  准备  32 字符串的  MD5 钥匙  [MD5_KEY]. 
			3.  准备要解密的数据  (HTTP POST的加密数据) [DES_DATA]. 
			[DECRYPT_DATA] = SD 2PAY_DECRYPT( [DES_DATA], [KEY1], [KEY2] ) 
			 
			4.  分拆已解密的XML数据([XML_DATA]) 和  MD5 数据  ([MD5_DATA]) 
			XML数据是加密的数据除去最后的32个字符。而最后的32字符是MD5数
			据。 
			[XML_DATA] = REMOVE_LAST_32_CHAR( [DECRYPT_DATA] ) 
			[MD5_DATA] = GET_LAST_32_CHAR( [DECRYPT_DATA] ) 
			 
			5.  通过计算MD5加密验证数据。  ([MD5_DATA_VERIFY]). 
			结果  [MD5_DATA_VERIFY]  必须跟  [MD5_DATA]  一样。 
			[MD5_DATA_VERIFY] = MD5( [XML_DATA] + [MD5_KEY] ) 
	        */
			
			String key1 = "YTgWujPXTxA=";
			String key2 = "uS2J7YxCc3o=";
			String md5key = "13940e1f02294a6ba6de53eb8339d173";
			
			String DECRYPT_DATA = DecryptUtil.DecryptData(des, key1, key2);
			System.err.println("DECRYPT_DATA="+DECRYPT_DATA);
			String XML_DATA = DECRYPT_DATA.substring(0, DECRYPT_DATA.length() - 32);
			System.err.println("XML_DATA="+XML_DATA);
			String MD5_DATA = DECRYPT_DATA.substring(DECRYPT_DATA.length() - 32);
			System.err.println("MD5_DATA="+MD5_DATA);
			String MD5_DATA_VERIFY = MD5Encode.encode(XML_DATA + md5key).toLowerCase();
			System.err.println("MD5_DATA_VERIFY="+MD5_DATA_VERIFY);
			
			
			boolean verify = MD5_DATA.equals(MD5_DATA_VERIFY);//校验
			
			result.put("verify", verify);
			result.put("data", getQueryParams(XML_DATA));
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
        
		return result;
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
			List<Element> list2 = root.selectNodes("//message");
			
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
			
			
			String xml = "EncryptTest";
			System.out.println(xml);
			
			String key1 = "Me41nYiCCfg=";
			System.out.println("key1="+key1);
			String key2 = "VqgyF7X2mLE=";
			System.out.println("key2="+key2);
			String md5key = "2db0b12454c88ba9e622a8aa37bc1e35";  //md5 key
			System.out.println("md5key="+md5key);
			
			String DES_DATA = "";
			try {
				DES_DATA = EncryptUtil.EncryptData(xml, key1, key2);
				System.out.println("DES_DATA="+DES_DATA);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
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

