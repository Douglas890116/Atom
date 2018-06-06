package com.maven.payment.sdp2p;

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

import com.maven.payment.sdp2p.wsdl.ApplyForABankLocator;
import com.maven.payment.sdp2p.wsdl.ApplyForABankSoapStub;
import com.maven.payment.sdpay.CustomerLocator;
import com.maven.payment.sdpay.CustomerSoapStub;
import com.maven.util.Encrypt;
import com.maven.util.HttpPostUtil;
import com.maven.util.RandomStringNum;
import com.maven.util.StringUtils;

import net.sf.jsqlparser.expression.StringValue;

public class SDP2PPaySignUtil {
	

	/**
	 * 发起一个请求
	 * @param LoginUser
	 * @param EncryptedStr
	 * @param WebServiceUrl
	 * @param merchantConfig 商户相关配置参数
	 * @param orderConfig 订单信息
	 * @return
	 */
	public static Map<String, String> SubmitRequest(SDP2PMerchantConfig merchantConfig,SDP2POrderConfig orderConfig){
    	String Result = "";
    	try {
    		
			
			String userName = orderConfig.getUserName();
	        String orderNo = orderConfig.getOrderNo();
	        String money = orderConfig.getMoney();
	        String bank = orderConfig.getBank();
	        

	        String LoginUser = merchantConfig.getLoginAccount();
			String key1 = merchantConfig.getKey1();
			String key2 = merchantConfig.getKey2();
			String WebServiceUrl = merchantConfig.getWebServiceUrl();
			String EncryptedStr = "";
			

			String xml = "<t_savingApply><id>0</id><storeOrderId>"+orderNo+"</storeOrderId><sBank1>"+
						bank+"</sBank1><sName>" + userName + "</sName><sPrice>"+money+"</sPrice><sPlayersId>"+orderConfig.getsPlayersId()+"</sPlayersId></t_savingApply>";

			
	        System.out.println("XML:" + xml);
			try {
				 EncryptedStr = MyEncrypt.EncryptData(xml, key1, key2);
//				 EncryptedStr = EncryptedStr.replaceAll("\r\n", "");
//				 EncryptedStr = URLEncoder.encode(EncryptedStr,"UTF-8");
//				 EncryptedStr = "8Q+t9j1MFrRbzSWL4DtZ86HA9MXJN95thg9c7ek14SpIWYT8LeO82ptQRizVbu7EapBxZ9BHGPg6xX+c196FuooyujaSqqyEqI8jzXxJIQxzO1PnwrRRln/vOo1i7QVsCKBU8BjsHl/BSM/kawBdM4k3svvZxQoJxjErCZ5xDbJgH23W+ZLaIWdV0MHp0oHWw2e3RmMLCneFA7gcLz4/FDULbkqeB15Ozc57/FZ+7dt4KD7C8jThe773yb1R7rbzNMwEb2SlDEPd5076eYb5TEWMd69pTHkPGr0ABg8BKv3AmvCtuoE4CystkNpSTHVjp3qaihjvUeqhfTpPJK12Qw==";
				 System.out.println("LoginUser: "+LoginUser);
				 System.out.println("key1: "+key1);
				 System.out.println("key2: "+key2);
				 System.out.println("Encrypt: " + EncryptedStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			System.out.println("Start...");
    		System.out.println(WebServiceUrl);
			ApplyForABankSoapStub service = (ApplyForABankSoapStub)new ApplyForABankLocator().getApplyForABankSoap(new URL(WebServiceUrl));
			Result = service.applyBank(merchantConfig.getLoginAccount(), EncryptedStr);
			System.out.println("Result: " + Result);
	        //-1	String	Input Error: case may be related to encryption, xml format problem
	        //-10	String	No collection bank
	        //-11	String	No collection card
	        //-12	String	Wrong key
	        //-13	String	Login account did not found
	        //-14	String	Login account is null
	        //-15	String	Duplicate request
	        //-16	String	Money less than 0
			
			String xmldata = MyDecrypt.DecryptData(Result, key1, key2);
			System.out.println(xmldata);
			
			Map<String, String> data = new HashMap<String, String>();
			if(StringUtils.isNumber(xmldata.replaceAll("-", ""))) {
				data.put("error", "获取银行卡信息错误："+xmldata);
				return data;
			}
			
			data = getQueryParams(xmldata, "t_savingApply");
			
			return data;
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return null;
    }
	
	/**
	 * 解密响应xml密文
	 * @param LoginUser
	 * @param EncryptedStr
	 * @param WebServiceUrl
	 * @param merchantConfig 商户相关配置参数
	 * @param orderConfig 订单信息
	 * @return
	 */
	public static Map<String, String> DecryResponse(String xmldata, String key1, String key2){
		System.out.println("解密前data="+xmldata);
		String data = MyDecrypt.DecryptData(xmldata, key1, key2);
		System.out.println("解密后data="+data);
		Map<String, String> map = getQueryParams(data, "t_savingApply");
		return map;
	}
	
	/**
	 * 返回单个节点
	 * @param xmlStr
	 * @return
	 */
	private static Map<String, String> getQueryParams(String xmlStr,String rootcode) {
		if(xmlStr==null || xmlStr.equals("")) {
			return null;
		}
		Document doc = null;
		Map<String, String> data = new HashMap<String, String>();
		try {
			doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML    
			Element root = doc.getRootElement();
			List<Element> list2 = root.selectNodes("//"+rootcode);
			
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
			
			SDP2PMerchantConfig merchantConfig = new SDP2PMerchantConfig();
			merchantConfig.setKey1("ZJW5/T3Zr50=");
			merchantConfig.setKey2("uDKye36AIXs=");
			merchantConfig.setLoginAccount("egg777t");
			merchantConfig.setWebServiceUrl("https://deposit2.sdapayapi.com/9001/ApplyForABank.asmx");
			
//			SDP2POrderConfig orderConfig = new SDP2POrderConfig();
//			orderConfig.setBank("ICBC");
//			orderConfig.setMoney("100");
//			orderConfig.setOrderNo(UUID.randomUUID().toString());
//			orderConfig.setUserName("dsdsd");
//			orderConfig.setsPlayersId(UUID.randomUUID().toString().substring(20));
//			
//			SubmitRequest(merchantConfig, orderConfig);
			
			String xmldata = "8Q+t9j1MFrSJxcOKyOPMh8iCWYHu7nbs1w4102zwsdNdjFP8itmuee2bhOy4+3kTM4Zy95IHuIFoJn/1cj8HtZZoMbPiN0uXjviH6r8/dFUOQiyf9GXZu8zVONIHyw4fkiXxyXlADkT2rq0kQlW61+f5N+0YcHpb6rmkHfUfa4UbJRl0LXD6Z3ybYOJ3IJBneyD3Kr1gqwm7Ke8ruPqlvyXbbrQek9gp47hdVZwnblTW8oe6Q/RPLDtUB9p7PAnm23taWVX0VVkFm7MfHlWeuuHh+MlnBnjxC+1gTdZze99A2QZGSOg1VWyOGepWBtVKqMhN9iSfh7b+0EZfXCXb4BpEj782gh4bKDGs0SUQXQfvzwdtMl+pzFUQb+C/QccVijX0HeqZQ2rmsBtAMX/u3WX2+/thyqglgePmqd9z8Q3judEcVxsXVWSGWc0O8S86KTEKeza0v2JE7mN87EUGRNvNOYTA2kla6JhsvEoKdEHxj8QjBg5FHVn39nN6fXt9o2ZyqTtORt1VGt5s3g/XdZwERgYTwZf6ZhBrXxWN/92CTHbnNfgO0StAXhgJ3jSeNT0qP4/yvaRFaopxWd8iRHWndrlF+DdVK+wJT1PpiRu1W2vrg7CJP1PNUouxbTp9jTb0Im8VjAUEAUJ2LSBOJJ2lS9+B7VgB8xnLQ+0AtyG3I4Sm7tGmrToMeoWTUMYrp6tQMFOF0ahvX4WQ1eLqYNDg45Aro2UOsgtoiSAaaxoBphPfATl+Kg/zxVCTQir83V+bzCfOCXazMMcMm4Aq+388WH+ekwxws81PFrDu+TyoWwhC6eCJsl2vpKc7vrs8FlOG8Bc4W1zdhKzwy0l2QgvWKgxVlV0oCfbki+DKaNeGBHCZMSh399miEc6Q82frOqy9bnxT/RovwZg1htHhPLurNKgqOpZY6Nk6gIh1TG2XWR3QyVBkLJ/SZ7Dma2l9KnGn+qEvNAfQvViSiYeNbuBju1YRR/4WVhrDMOkBdha7yQ+SZltK/40hdN7GiSL3qcBaTudRZwy2hhuyeQD3Z1C/zRkL1WzMqxthHnNlNllk1o67iFwQrRURXFCE6Ei9Mwa62JD7nTM2TdEmYXhLGpFRCtjw0ie1XojZ5zUwtPWCSZwLrPKRXzWw1Hw9iraERg4nZFKLX7jnRmbyM7k6kReGHEVD70/M0k6E6Uqs5owQcCfj3lZRTvUNTLrV1v3BBgNiAFwpyChwgJobpNNXZZx6GcPcKQx2Jg5JWrv5We8+yct1NnVLyQMASWxihglk40agrMSJlgOqEthVy3OQYLsiPMMxQl5ad3to5GrZTcRjs2+K0tz72hGbPGl4gtBAa4AcBwpzT5z0QM8EncauyQ==";
			String key1 = "ZJW5/T3Zr50=";
			String key2 = "uDKye36AIXs=";
			
			System.out.println("解密前data="+xmldata);
			String data = MyDecrypt.DecryptData(xmldata, key1, key2);
			System.out.println("解密后data="+data);
			data = "<t_savingApply><id>7033793</id><storeOrderId>328EE05ACA784E4792921E022B59C5F3</storeOrderId><sBankAccount></sBankAccount><sBank1>icbc</sBank1><sBank2></sBank2><sName>kaixin0909</sName><sPrice>115.00</sPrice><sPlayersId>kaixin0909</sPlayersId><eBank>ICBC</eBank><eBank2>深圳公明支行</eBank2><eName>周志刚</eName><eBankAccount>6212264000071671206</eBankAccount><ePrice>115.39</ePrice><ePoundage>0.00</ePoundage><eProvince>UYx3TM0zmVah6ZUkWvCAA5pZtnhbtjK1fyeZsDK3vBEfqgtARKQ2YBb8gVbbnQXo</eProvince><ecity>UfHguW31W4NbcdxL6j3c1zV810xnfMEOSlfkOxnRVb+1UDiz9vcT1Q==</ecity><storeId>411</storeId><storename>RH00411906</storename><state>1</state><matchingDate>2017-05-10 17:51:06</matchingDate><date>2017-05-10 17:43:22</date><SendOrNot>0</SendOrNot><SendTimes>3</SendTimes><Approach>2</Approach><matchingInfoId>5001956</matchingInfoId><Fees>0.00000</Fees><ip>52.175.30.174</ip><CompareMode>1</CompareMode><pushTime>2017-05-10 17:43:22</pushTime><email>11@gg.com</email></t_savingApply>";
			Map<String, String> map = getQueryParams(data, "t_savingApply");
			System.out.println(map);
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

