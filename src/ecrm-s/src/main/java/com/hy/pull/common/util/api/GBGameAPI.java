package com.hy.pull.common.util.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.hy.pull.common.util.AESUtil2Net;
import com.hy.pull.common.util.GBUtil;
import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.PBKDF2Encryption;
import com.hy.pull.common.util.StreamTool;
import com.hy.pull.common.util.game.sa.DeEnCode;
import com.hy.pull.common.util.game.sa.XmlUtil;
import com.hy.pull.common.util.game.tag.TagUtil;
import com.hy.pull.common.util.game.xcp.XCPUtil;
import com.maven.config.SystemConstant;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;
import com.maven.util.HttpPostUtil.UTF8PostMethod;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * GB彩票接口
 * @author temdy
 */
public class GBGameAPI implements BaseInterface {
	
	private String API_URL = "http://api.gbfine.com/GBGameAPI/API.aspx";
	private String TPCode = "914";
	private String GeneralKey = "560522";
	private String SecretKey = "354138";
	private String YourDomain = "http://gb.dw6868.com/";//登录游戏使用

	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	/**
	 * 默认构造函数
	 * 
	 * 测试时可以使用，不用传递秘钥进来
	 */
	@Deprecated
	public GBGameAPI(){
		
	}
	
	/**
	 * 构建函数，初始化参数
	 * @param API_URL API接口URL
	 * @param MD5_KEY MD5密钥
	 * @param DES_KEY DES密钥
	 */
	
	public GBGameAPI(String API_URL,String TPCode,String SecretKey,String GeneralKey,String YourDomain, String GAME_API_URL){
		this.API_URL = API_URL;
		this.TPCode = TPCode;
		this.SecretKey = SecretKey;
		this.GeneralKey = GeneralKey;
		this.YourDomain = YourDomain;
		this.GAME_API_URL = GAME_API_URL;
	}

	/**
	 * 创建用户接口
	 * @param entity 参数列表 MemberID,password
	 * 
	 * 
	 * MemberID=50字符
	 * @return 返回结果
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString()  );
//		<result    info=""    msg=""/>
		
		if(MapUtil.isNulls(entity, "MemberID,password")){	
			
//			if( String.valueOf(entity.get("loginname")).length() > 15 ) {
//				return Enum_MSG.参数错误.message("用户长度不能大于15位");
//			}
//			if( String.valueOf(entity.get("password")).length() > 12 ) {
//				return Enum_MSG.参数错误.message("密码长度不能大于12位");
//			}
			
			JSONObject object = new JSONObject();
			JSONObject GB = new JSONObject();
			GB.put("Method", "CreateMember");
			GB.put("TPCode", this.TPCode);
			GB.put("AuthKey", this.GeneralKey);
			
			JSONObject Params = new JSONObject();
			Params.put("MemberID", entity.get("MemberID"));
			Params.put("FirstName", entity.get("MemberID"));
			Params.put("LastName", entity.get("MemberID"));
			Params.put("Nickname", entity.get("MemberID"));
			Params.put("Gender", "2");
			Params.put("Birthdate", "1980-01-01");
			Params.put("CyCode", "CN");
			Params.put("CurCode", "CNY");
			Params.put("LangCode", "zh-cn");
			Params.put("TPUniqueID", "new");
			GB.put("Params", Params);
			object.put("GB", GB);
			
			
			String result =  sendDataByPost(this.API_URL, object);
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , result );
			
			JSONObject data = JSONObject.fromObject(result);
			
			//	接口调用成功
			if( data.getJSONObject("GB").getJSONObject("Result").getString("Success").equals("1")) {//
				
				//"GBSN":1193141 注意，该参数比较重要，登录游戏时必须要填写
				return Enum_MSG.成功.message(data.getJSONObject("GB").getJSONObject("Result").getJSONObject("ReturnSet").getString("GBSN"));
			} else if(data.getJSONObject("GB").getJSONObject("Result").getJSONObject("ReturnSet").has("Error") && 
					data.getJSONObject("GB").getJSONObject("Result").getJSONObject("ReturnSet").getJSONObject("Error").getString("ErrorCode").equals("1101")) {//
				
				return Enum_MSG.账号已存在.message("账号已存在");
			} else {
				
				JSONObject objectresult = data.getJSONObject("GB").getJSONObject("Result").getJSONObject("ReturnSet").getJSONObject("Error");
				return Enum_MSG.失败.message(objectresult.getString("ErrorCode"), objectresult.getString("ErrorMsg"));
			}
			
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	/**
	 * 每一步接口调用都需要获取类似token的东西
	 * @param entity
	 * @return
	 */
	private boolean UpdateTPUniqueID(Map<String, Object> entity) {
		
		try {
			if(MapUtil.isNulls(entity, "MemberID,TPUniqueID")){
				
				JSONObject object = new JSONObject();
				JSONObject GB = new JSONObject();
				GB.put("Method", "UpdateTPUniqueID");
				GB.put("TPCode", this.TPCode);
				GB.put("AuthKey", this.GeneralKey);
				
				JSONObject Params = new JSONObject();
				Params.put("MemberID", entity.get("MemberID"));
				Params.put("TPUniqueID", entity.get("TPUniqueID"));
				GB.put("Params", Params);
				object.put("GB", GB);
				
				
				String result =  sendDataByPost(this.API_URL, object);
				System.out.println("TPUniqueID调用结果："+result);
				
				JSONObject data = JSONObject.fromObject(result);
				
				//	接口调用成功
				if( data.getJSONObject("GB").getJSONObject("Result").getString("Success").equals("1")) {//
					return true;
				} else {
					
					return false;
				}
				
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 获取余额
	 * @param entity 参数列表 MemberID,Amount,ExTransID
	 * 
	 * password 密码
	 * @return 返回结果
	 */
	@Override
	public Object getBalance(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "MemberID")){
			
			String TPUniqueID = UUID.randomUUID().toString();
			
			JSONObject object = new JSONObject();
			JSONObject GB = new JSONObject();
			GB.put("Method", "GetBalance");
			GB.put("TPCode", this.TPCode);
			GB.put("AuthKey", this.GeneralKey);
			
			JSONObject Params = new JSONObject();
			Params.put("MemberID", entity.get("MemberID"));
			Params.put("CurCode", "CNY");
			GB.put("Params", Params);
			object.put("GB", GB);
			
			
			String result =  sendDataByPost(this.API_URL, object);
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , result );
			
			JSONObject data = JSONObject.fromObject(result);
			
			//	接口调用成功
			if( data.getJSONObject("GB").getJSONObject("Result").getString("Success").equals("1")) {//
				
				double Balance = data.getJSONObject("GB").getJSONObject("Result").getJSONObject("ReturnSet").getDouble("Balance");
				double Balance2 = Balance / 100.00;
				System.out.println(Balance2);
				return Enum_MSG.成功.message(Balance2+"");
			} else {
				
				JSONObject objectresult = data.getJSONObject("GB").getJSONObject("Result").getJSONObject("ReturnSet").getJSONObject("Error");
				return Enum_MSG.失败.message(objectresult.getString("ErrorCode"), objectresult.getString("ErrorMsg"));
			}
			
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	
	
	/**
	 * 下分（取款）的接口
	 * @param entity 参数列表 MemberID,Amount,ExTransID
	 * 
	 * @return 返回结果
	 */
	public Object withdraw(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "MemberID,Amount,ExTransID")){
			
			String TPUniqueID = UUID.randomUUID().toString();
			
			JSONObject object = new JSONObject();
			JSONObject GB = new JSONObject();
			GB.put("Method", "Withdrawal");
			GB.put("TPCode", this.TPCode);
			GB.put("AuthKey", this.SecretKey);
			
			JSONObject Params = new JSONObject();
			Params.put("MemberID", entity.get("MemberID"));
			Params.put("Amount", Double.valueOf(entity.get("Amount").toString()) * 100);//金額基數為100，例如：Amount=1000，代表轉出10元。
			Params.put("ExTransID", entity.get("ExTransID"));
			Params.put("CurCode", "CNY");
			Params.put("TPUniqueID", TPUniqueID);
			GB.put("Params", Params);
			object.put("GB", GB);
			
			
			entity.put("TPUniqueID", TPUniqueID);
			if( !UpdateTPUniqueID(entity)) {
				return Enum_MSG.失败.message("未能更新TPUniqueID。接口调用失败");
			}
			
			String result =  sendDataByPost(this.API_URL, object);
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() , result );
			
			JSONObject data = JSONObject.fromObject(result);
			
			//	接口调用成功
			if( data.getJSONObject("GB").getJSONObject("Result").getString("Success").equals("1")) {//
				return Enum_MSG.成功.message("下分成功");
			} else {
				
				JSONObject objectresult = data.getJSONObject("GB").getJSONObject("Result").getJSONObject("ReturnSet").getJSONObject("Error");
				return Enum_MSG.失败.message(objectresult.getString("ErrorCode"), objectresult.getString("ErrorMsg"));
			}
			
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	
	
	/**
	 * 上分（存款）的接口 MemberID,Amount,ExTransID
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "MemberID,Amount,ExTransID")){
			
			String TPUniqueID = UUID.randomUUID().toString();
			
			JSONObject object = new JSONObject();
			JSONObject GB = new JSONObject();
			GB.put("Method", "Deposit");
			GB.put("TPCode", this.TPCode);
			GB.put("AuthKey", this.SecretKey);
			
			JSONObject Params = new JSONObject();
			Params.put("MemberID", entity.get("MemberID"));
			Params.put("Amount", Double.valueOf(entity.get("Amount").toString()) * 100);//金額基數為100，例如：Amount=1000，代表轉入10元。
			Params.put("ExTransID", entity.get("ExTransID"));
			Params.put("CurCode", "CNY");
			Params.put("TPUniqueID", TPUniqueID);
			GB.put("Params", Params);
			object.put("GB", GB);
			
			
			entity.put("TPUniqueID", TPUniqueID);
			if( !UpdateTPUniqueID(entity)) {
				return Enum_MSG.失败.message("未能更新TPUniqueID。接口调用失败");
			}
			
			String result =  sendDataByPost(this.API_URL, object);
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() , result );
			
			JSONObject data = JSONObject.fromObject(result);
			
			//	接口调用成功
			if( data.getJSONObject("GB").getJSONObject("Result").getString("Success").equals("1")) {//
				return Enum_MSG.成功.message("存款成功");
			} else {
				
				JSONObject objectresult = data.getJSONObject("GB").getJSONObject("Result").getJSONObject("ReturnSet").getJSONObject("Error");
				return Enum_MSG.失败.message(objectresult.getString("ErrorCode"), objectresult.getString("ErrorMsg"));
			}
			
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	/**
	 * 获取游戏结果
	 * @param entity 参数列表 startdate,enddate
	 * 
	 * 2016- 04- 16 22:00:22 
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object getRecord(Map<String, Object> entity) {
		
		String StartSettleID = "41849105";//結算流水號開始(大於)。流水號輸入1，則回傳2(含)之後注單，但並不包括1。
		String EndSettleID = "-1";//結算流水號結束。輸入-1為傳回最大筆數。
		
		JSONObject object = new JSONObject();
		JSONObject GB = new JSONObject();
		GB.put("Method", "GetGamingSettle");
		GB.put("TPCode", this.TPCode);
		GB.put("AuthKey", this.GeneralKey);
		
		JSONObject Params = new JSONObject();
		Params.put("StartSettleID", StartSettleID );
		Params.put("EndSettleID", EndSettleID );
		GB.put("Params", Params);
		object.put("GB", GB);
		
		
		String result =  sendDataByPost(this.API_URL, object);
//		System.out.println("调用结果："+result);
		
		JSONObject data = JSONObject.fromObject(result);
		
		//	接口调用成功
		if( data.getJSONObject("GB").getJSONObject("Result").getString("Success").equals("1")) {//交易狀態，1：成功、2：失敗、3：查無資料、4：進行中
			
//			BetTotalCnt	本次注單數量
//			BetTotalAmt	本次注單實際下注總金額：SUM(RealBetAmt) ，基數100

//			  "SettleID": "5746847",
//			  "BetID": "5606714" ,
//			  "BetGrpNO": "2015122915440044573528" ,
//			  "TPCode": "99999",
//			  "GBSN": "1193141",
//			  "MemberID": "wantbet",
//			  "CurCode": "cny",
//			  "BetDT": "2015-12-29 15:44:05",
//			  "BetType": "1",
//			  "BetTypeParam1": "1",
//			  "BetTypeParam2": "1",
//			  "Wintype": "1",
//			  "HxMGUID": "0",
//			  "InitBetAmt": "200",
//			  "RealBetAmt": "200",
//			  "HoldingAmt": "200",
//			  "InitBetRate": "25100",
//			  "RealBetRate": "0",
//			  "PreWinAmt": "50200",
//			  "BetResult": "0",
//			  "WLAmt": "0",
//			  "RefundBetAmt": "0",
//			  "TicketBetAmt": "200",
//			  "TicketResult": "0",
//			  "TicketWLAmt": "0",
//			  "SettleDT": "2015-12-29 15:44:41",
			
//			KenoList：数组数据
//			LottoList：数组数据
//			SscList：数组数据
			
			JSONArray list = data.getJSONObject("GB").getJSONObject("Result").getJSONObject("ReturnSet").getJSONArray("SettleList");//多条
			
			return list;
			
		} else {
			
			//获取数据失败
			return null;
		}
		
	}
	
	/** 
     * @Title:bytes2HexString 
     * @Description:字节数组转16进制字符串 
     * @param b 
     *            字节数组 
     * @return 16进制字符串 
     * @throws 
     */  
    public static String bytes2HexString(byte[] b) {  
        StringBuffer result = new StringBuffer();  
        String hex;  
        for (int i = 0; i < b.length; i++) {  
            hex = Integer.toHexString(b[i] & 0xFF);  
            if (hex.length() == 1) {  
                hex = '0' + hex;  
            }  
            result.append(hex.toUpperCase());  
        }  
        return result.toString();  
    }  
	


	/**
	 * 更新信息接口
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	@Override
	public Object updateInfo(Map<String, Object> entity) {
		return null;
	}

	/**
	 * 获取订单接口 ExTransID
	 * @return 返回结果
	 */
	@Override
	public Object getOrder(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString()  );
		if(MapUtil.isNulls(entity, "ExTransID")){
			
			String TPUniqueID = UUID.randomUUID().toString();
			
			JSONObject object = new JSONObject();
			JSONObject GB = new JSONObject();
			GB.put("Method", "GetFundTransStatus");
			GB.put("TPCode", this.TPCode);
			GB.put("AuthKey", this.GeneralKey);
			
			JSONObject Params = new JSONObject();
			Params.put("ExTransID", entity.get("ExTransID"));
			Params.put("TransType", entity.get("ExTransID").toString().startsWith("IN") ? "0" : "1");
			GB.put("Params", Params);
			object.put("GB", GB);
			
			
			String result =  sendDataByPost(this.API_URL, object);
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() , result );
			
			JSONObject data = JSONObject.fromObject(result);
			
			//	接口调用成功
			if( data.getJSONObject("GB").getJSONObject("Result").getString("Success").equals("1")) {//交易狀態，1：成功、2：失敗、3：查無資料、4：進行中
				
				if( data.getJSONObject("GB").getJSONObject("Result").getJSONObject("ReturnSet").getString("TransStatus").equals("1"))  {
					return Enum_MSG.成功.message("成功");
				} else {
					return Enum_MSG.失败.message("失败："+data.getJSONObject("GB").getJSONObject("Result").getJSONObject("ReturnSet").getString("TransStatus"));
				}
				
			} else {
				
				JSONObject objectresult = data.getJSONObject("GB").getJSONObject("Result").getJSONObject("ReturnSet").getJSONObject("Error");
				return Enum_MSG.失败.message(objectresult.getString("ErrorCode"), objectresult.getString("ErrorMsg"));
			}
			
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	/**
	 * 登录的接口
	 * @param entity 参数列表 MemberID,deviceType,gamecode,GBSN
	 * 
	 * gamecode=keno，lotto，ssc，pk10   分别为keno彩票，乐透，时时彩，PK10，三种不同类型的彩票游戏
	 * 
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object login(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() );
		
		if(MapUtil.isNulls(entity, "MemberID,deviceType,gamecode,GBSN")){
			
			String deviceType = String.valueOf(entity.get("deviceType"));
			if( !deviceType.equals("0") && !deviceType.equals("1")) {
				return Enum_MSG.参数错误.message("GB彩票游戏目前只能接受PC端与手机H5端的请求，即设备类型为0和1");
			}
			String gamecode = String.valueOf(entity.get("gamecode"));
			if( !gamecode.equals("keno") && !gamecode.equals("lotto") && !gamecode.equals("ssc") && !gamecode.equals("pk10") ) {
				return Enum_MSG.参数错误.message("GB彩票目前可以接受keno，lotto，ssc，pk10大类游戏");
			}
			
			String TPUniqueID = UUID.randomUUID().toString();
			entity.put("TPUniqueID", TPUniqueID);
			if( !UpdateTPUniqueID(entity)) {
				return Enum_MSG.失败.message("未能更新TPUniqueID。接口调用失败");
			}
			String uxts = System.currentTimeMillis() + "";
			uxts = uxts.substring(0, uxts.length() - 3);
//			System.out.println("0=TPUniqueID："+TPUniqueID);
			//uid=[GBSN]&sid=[TPUniqueID]&tryit=n&ts=[UxTS]
			String str = "uid=".concat(String.valueOf(entity.get("GBSN"))).concat("&sid=").concat(TPUniqueID).concat("&tryit=n").concat("&ts=").concat(uxts+"");
//			System.out.println("1=MD5原文："+str);
			String md5 = DeEnCode.string2MD5(str);
//			System.out.println("2=MD5密文："+md5);
			
					
			//uid=[GBSN]&sid=[TPUniqueID]&tryit=n&ts=[UxTS]&ck=[CKS]
			String data = "uid=".concat(String.valueOf(entity.get("GBSN"))).concat("&sid=").concat(TPUniqueID).concat("&tryit=n").concat("&ts=").concat(uxts+"").concat("&ck=").concat(md5);
//			System.out.println("3=AES原文："+data);
			
			String salt = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
        	String token = "";
        	
        	try {
        		String password = this.GeneralKey;
        		String ciphertext = GBUtil.getEncryptedPassword(password, salt);
    		    String key = ciphertext.substring(0,32);
    		    String iv = ciphertext.substring(32);
    		    token = GBUtil.encryptData(data,key,iv);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			String LangCode = "zh-cn";
			String tpid = this.TPCode;
			
			/*
			【網頁版遊戲網址】

			Keno Web Game URL = http://[YourDomain]/keno/default.aspx?tpid=[TPCode]&token=[Token]&languagecode=[LangCode]

			Lotto Web Game URL = http://[YourDomain]/lotto/default.aspx?tpid=[TPCode]&token=[Token]&languagecode=[LangCode]

			SSC Web Game URL = http://[YourDomain]/ssc/default.aspx?tpid=[TPCode]&token=[Token]&languagecode=[LangCode]

			PK拾遊戲網址 = http://[YourDomain]/pk10/default.aspx?tpid=[TPCode]&token=[Token]&languagecode=[LangCode]

			【手機版遊戲網址】

			Keno Mobile Game URL = http://[YourDomain]/mobile/keno/index.aspx?tpid=[TPCode]&token=[Token]&languagecode=[LangCode]

			Lotto Mobile Game URL = http://[YourDomain]/mobile/lotto/index.aspx?tpid=[TPCode]&token=[Token]&languagecode=[LangCode]

			SSC Mobile Game URL = http://[YourDomain]/mobile/ssc/index.aspx?tpid=[TPCode]&token=[Token]&languagecode=[LangCode]
			
			PK拾遊戲網址 = http://[YourDomain]/mobile/pk10/index.aspx?tpid=[TPCode]&token=[Token]&languagecode=[LangCode]
			
			*/
			
			
			StringBuffer url = new StringBuffer();
			url.append(this.YourDomain);
			if( deviceType.equals("0")) {//PC
				
				if(gamecode.equals("keno")) {
					url.append("/keno/default.aspx");
				} else if(gamecode.equals("lotto") ) {
					url.append("/lotto/default.aspx");
				} else if(gamecode.equals("ssc") ) {
					url.append("/ssc/default.aspx");
				} else if(gamecode.equals("pk10") ) {
					url.append("/pk10/default.aspx");
				}
				
			} else {
			
				if(gamecode.equals("keno")) {
					url.append("/mobile/keno/index.aspx");
				} else if(gamecode.equals("lotto") ) {
					url.append("/mobile/lotto/index.aspx");
				} else if(gamecode.equals("ssc") ) {
					url.append("/mobile/ssc/index.aspx");
				} else if(gamecode.equals("pk10") ) {
					url.append("/mobile/pk10/index.aspx");
				}

			}
			//http://gb.jmt18.com/Keno/Default.aspx?tpid=907&token=92199ac57fd635c39675d1ce8f209e49128f2c41f58ddbabbc9835b4eeb9a12e520d835719962c9595fcbc10f73f5721b6c817698e5439c04c742219535a4e517ecd8c649c241b1fb4d9fde74e55698f355dca3ddfd0dc523073a99e61250162&languagecode=zh-cn&guest=login&sc=
			url.append("?tpid=").append(tpid).append("&token=").append(token).append("&languagecode=").append(LangCode);
			
			//试玩可以传递下面的参数
			if(false) {
				url.append("&guest=login&sc=");
			}
			
			return Enum_MSG.成功.message(url.toString());
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
		
	}

	/**
	 * 是否在线接口
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	@Override
	public Object isOnLine(Map<String, Object> entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static String sendDataByPost(String url, JSONObject xmlData) {
		
		try {
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			int timeout = 15000;
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
			
			
			Integer statusCode = -1;
			// Create HttpClient Object
			System.out.println("请求URL："+url);
			System.out.println("请求DATA："+xmlData);
			// Send data by post method in HTTP protocol,use HttpPost instead of
			// PostMethod which was occurred in former version
			HttpPost post = new HttpPost(url);
			post.setConfig(requestConfig);
			// Construct a string entity
			StringEntity entity = new StringEntity(xmlData.toString());
			// Set XML entity
			post.setEntity(entity);
			// Set content type of request header
			post.setHeader("Content-Type", "application/json;charset=UTF-8");
			// Execute request and get the response
			HttpResponse response = httpClient.execute(post);
			// Response Header - StatusLine - status code
			
			statusCode = response.getStatusLine().getStatusCode();
			String result = EntityUtils.toString(response.getEntity());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		} finally {
			
		}
	}
	
	
	public static void main(String[] a){
//		password=XQ5Lv5Xq, loginname=zOne6KTd83sMEpb
		
		GBGameAPI tag = new GBGameAPI();
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("MemberID", RandomString.createRandomString(15));//#&*
		entity.put("password", RandomString.createRandomString(8));
//		System.out.println(tag.createAccount(entity));//MemberID,password
//		
		entity.put("MemberID", "hyldw2230736");
		entity.put("ExTransID", "IN"+RandomString.createRandomString(40));
		entity.put("Amount", "30");
		System.out.println(tag.deposit(entity));//MemberID,Amount,ExTransID
//
		entity.put("ExTransID", "OUT"+RandomStringNum.createRandomString(40));
		entity.put("Amount", "3");
//		System.out.println(tag.withdraw(entity));//MemberID,Amount,ExTransID
//
//		
//		System.out.println(tag.getOrder(entity));//ExTransID
////		
//		System.out.println(tag.getBalance(entity));//MemberID
//		
		
		entity.put("MemberID", "hyldw2230736");//#&*
		entity.put("deviceType", "0");
		entity.put("gamecode", "pk10");
		entity.put("GBSN", "2508064");
		System.out.println(tag.login(entity));//MemberID,deviceType,gamecode,GBSN
//		
//		entity.put("playtype", "0");
//		entity.put("loginname", "zOne6KTd83sMEpb");
//		entity.put("password", "XQ5Lv5Xq");
//		System.out.println(tag.login(entity));//loginname,password,playtype
		
//		System.out.println(tag.getRecord(entity));
	}
}

