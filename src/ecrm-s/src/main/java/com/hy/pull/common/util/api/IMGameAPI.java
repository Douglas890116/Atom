package com.hy.pull.common.util.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.simple.parser.JSONParser;

import com.google.gson.annotations.Since;
import com.hy.pull.common.util.AESUtil2Net;
import com.hy.pull.common.util.Encrypt;
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
 * IM体育接口
 * @author temdy
 */
public class IMGameAPI implements BaseInterface {
	
	private String API_URL = "http://imone.imaegisapi.com";
	private String MerchantCode = "O9BPE3BUwBgfD8D44A8Ulx9sMpDTJfRa";//商户号
	//商家名称 (英文) = DIWANGGUOJI

	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
//	产品钱包代码：ProductWallet
	
//	MWG捕鱼钱包 (MWG Fishing Wallet)  2 
//	GG捕鱼钱包 (GG Fishing Wallet)  4 
//	IM 老虎机钱包 (IM Slot Wallet)  101 
//	PlayTech钱包 (PlayTech Wallet)  102 
//	IM 娱乐场钱包 (IM Live Dealer Wallet)  201 
//	IM  体育博彩钱包  (IM Sportsbook Wallet)  301 
//	IM  电子竞技钱包 (IM eSports Wallet)  401
	
	/**
	 * 默认构造函数
	 * 
	 * 测试时可以使用，不用传递秘钥进来
	 */
	@Deprecated
	public IMGameAPI(){
		
	}
	
	/**
	 * 构建函数，初始化参数
	 * @param API_URL API接口URL
	 * @param MD5_KEY MD5密钥
	 * @param DES_KEY DES密钥
	 */
	public IMGameAPI(String API_URL,String MerchantCode,String GAME_API_URL){
		if(API_URL.endsWith("/")) {
			API_URL = API_URL.substring(0, API_URL.length() - 1);
		}
		this.API_URL = API_URL;
		this.MerchantCode = MerchantCode;
		this.GAME_API_URL = GAME_API_URL;
	}
	
	
	/**
	 * 获取时间的当前日期
	 * @return
	 */
	private static String getCurrenDate_yyyyMMdd() {
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		return sdf.format(ca.getTime());
	}

	/**
	 * 创建用户接口
	 * @param entity 参数列表 username,password
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "username,password,Currency")){	
			
			if( String.valueOf(entity.get("username")).length() > 25 ) {
				return Enum_MSG.参数错误.message("用户长度不能大于25位");
			}
			if( String.valueOf(entity.get("password")).length() > 25 ) {
				return Enum_MSG.参数错误.message("密码长度不能大于25位");
			}
					
			JSONObject xmlData = new JSONObject();
			xmlData.put("MerchantCode", this.MerchantCode);
			xmlData.put("PlayerId", entity.get("username").toString());
			xmlData.put("Password", entity.get("password").toString());
			xmlData.put("Currency", entity.get("Currency").toString());
			
			JSONObject result =  doPostSubmitMap(this.API_URL.concat("/Player/Register"), xmlData);
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , result.toString() );
			
			//	接口调用成功
			if( result.getString("Code").equals("0") ) {
				
				return Enum_MSG.成功.message("成功");
				
			} else if( result.getString("Code").equals("503") ) {//
				
				return Enum_MSG.账号已存在.message("账号已存在");
			} else {
				
				return Enum_MSG.失败.message(result.getString("Code"), result.getString("Message"));
			}
			
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
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
		
		if(MapUtil.isNulls(entity, "username,ProductWallet")){	
			
			
			JSONObject xmlData = new JSONObject();
			xmlData.put("MerchantCode", this.MerchantCode);
			xmlData.put("PlayerId", entity.get("username").toString());
			xmlData.put("ProductWallet", entity.get("ProductWallet").toString());
			
			JSONObject result =  doPostSubmitMap(this.API_URL.concat("/Player/GetBalance"), xmlData);
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , result.toString() );
			
			
			//	接口调用成功
			if( result.getString("Code").equals("0") ) {
				return Enum_MSG.成功.message(result.getString("Balance"));
			} else {
				return Enum_MSG.失败.message(result.getString("Code"), result.getString("Message"));
			}
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	
	
	/**
	 * 下分（取款）的接口
	 * @param entity 参数列表 username,remit,remitno
	 * 
	 * @return 返回结果
	 */
	public Object withdraw(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "username,ProductWallet,TransactionId,Amount")){	
			
			if( String.valueOf(entity.get("TransactionId")).length() > 50 ) {
				return Enum_MSG.参数错误.message("订单长度不能大于50位");
			}
//			if( String.valueOf(entity.get("password")).length() > 12 ) {
//				return Enum_MSG.参数错误.message("密码长度不能大于12位");
//			}
					
			JSONObject xmlData = new JSONObject();
			xmlData.put("MerchantCode", this.MerchantCode);
			xmlData.put("PlayerId", entity.get("username").toString());
			xmlData.put("ProductWallet", entity.get("ProductWallet").toString());
			xmlData.put("TransactionId", entity.get("TransactionId").toString());
			xmlData.put("Amount", "-"+entity.get("Amount").toString());//转出是负数金额
			
			JSONObject result =  doPostSubmitMap(this.API_URL.concat("/Transaction/PerformTransfer"), xmlData);
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() , result.toString() );
			
			
			//	接口调用成功
			if( result.getString("Code").equals("0") ) {
				
				//重要：如果资金转账API返回的status = Processed, 请务必调用CheckTransferStatus() API 来获取最新的转账交易状态。 
				if(result.getString("Status").equals("Processed")) {
					return getOrder(entity);
				} else {
					return Enum_MSG.成功.message("成功");
				}
				
			} else {
				return Enum_MSG.失败.message(result.getString("Code"), result.getString("Message"));
			}
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	
	
	/**
	 * 上分（存款）的接口 username,remit,remitno
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "username,ProductWallet,TransactionId,Amount")){	
			
			if( String.valueOf(entity.get("TransactionId")).length() > 50 ) {
				return Enum_MSG.参数错误.message("订单长度不能大于50位");
			}
//			if( String.valueOf(entity.get("password")).length() > 12 ) {
//				return Enum_MSG.参数错误.message("密码长度不能大于12位");
//			}
					
			JSONObject xmlData = new JSONObject();
			xmlData.put("MerchantCode", this.MerchantCode);
			xmlData.put("PlayerId", entity.get("username").toString());
			xmlData.put("ProductWallet", entity.get("ProductWallet").toString());
			xmlData.put("TransactionId", entity.get("TransactionId").toString());
			xmlData.put("Amount", entity.get("Amount").toString());//转入是正数金额
			
			
			JSONObject result =  doPostSubmitMap(this.API_URL.concat("/Transaction/PerformTransfer"), xmlData);
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() , result.toString() );
			
			
			//	接口调用成功
			if( result.getString("Code").equals("0") ) {
				
				//重要：如果资金转账API返回的status = Processed, 请务必调用CheckTransferStatus() API 来获取最新的转账交易状态。 
				if(result.getString("Status").equals("Processed")) {
					return getOrder(entity);
				} else {
					return Enum_MSG.成功.message("成功");
				}
				
			} else {
				return Enum_MSG.失败.message(result.getString("Code"), result.getString("Message"));
			}
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
	@Override
	public Object getRecord(Map<String, Object> entity) {
		
//		注意: 
//		1.日志的搜寻时间区间封顶在10分钟  (举例, 2016-10-10 00.00.00 - 2016-10-10 00.10.00)。 
//		2.与其他产品不同的是，体育博彩日志一页的容纳固定在5000笔。因此无需PagSize参数。如果日志超过5,000笔，营运商需发下一页的请求。 
//		3.关于并发请求限制，请参考第1.3节。 
//		4.日志可能有15 分钟的更新延迟。建议只搜索至15 分钟前的日志  (举例，当前时间为2016-10-10 00:30:00，可搜寻的日期区间为2016-10-10 00:05:00 - 2016-10-10 00:15:00)。 	
//		5.如果需要发下一个请求，营运商需等待至当前请求处理完毕。 	  
//		6.日志可以日志的下注时间或赛事时间来搜寻  (由DateFilterType参数来决定)。	
			
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		String starttime = "2017-10-17 13.10.00";//时间格式：  yyyy-MM-dd HH.mm.ss  
		String endtime = "2017-10-17 13.20.00";//时间格式：  yyyy-MM-dd HH.mm.ss  
		
		int pagelimit = 5000;//每页数量
		int page = 1;//查询页数
		
		JSONObject xmlData = new JSONObject();
		xmlData.put("MerchantCode", this.MerchantCode);
		xmlData.put("StartDate", starttime);
		xmlData.put("EndDate", endtime);
		xmlData.put("Page", page+"");
		xmlData.put("ProductWallet", "301");//此参数值必须为301或401
		xmlData.put("Currency", "CNY");//CNY, USD, EUR, JPY, MYR, IDR, VND, THB, KRW
		
		//1 = 下注时间  (Bet Date  注单的下注时间) 2 = 比赛时间  (Event Date所投注比赛的开赛时间)
		xmlData.put("DateFilterType", "1");
		//此参数用于决定回传那种注单. 可选参数。如果未传此参数，系统则返回所有结算与未结算注单。0 = 未结算  (Not Settled) 1 = 结算  (Settled) 
		xmlData.put("BetStatus", "0");
		
		//可选参数。如果提供此参数，系统则只返回所有最后跟新时间大于此值的注单。
		//xmlData.put("LastUpdatedDate", "1");时间格式：  yyyy-MM-dd HH.mm.ss  
		xmlData.put("Language", "ZH-CN");
		
		JSONObject result =  doPostSubmitMap(this.API_URL.concat("/Report/GetBetLog"), xmlData);
		//System.out.println("调用结果："+result);
		
		
		//	接口调用成功
		if( result.getString("Code").equals("0") ) {
			JSONObject Pagination = result.getJSONObject("Pagination");
			int CurrentPage = Pagination.getInt("TotalCount");
			int TotalPage = Pagination.getInt("TotalPage");
			int ItemPerPage = Pagination.getInt("ItemPerPage");
			int TotalCount = Pagination.getInt("TotalCount");
			if(TotalCount > 0) {
				//获取到有数据
				System.out.println("获取到数据："+TotalCount+"条");
				JSONArray array = result.getJSONArray("Result");
				for (int i = 0; i < array.size(); i++) {
					System.out.println(array.getJSONObject(i));
				}
			} else {
				System.out.println("没有找到数据");
			}
		} else {
			//查找数据失败
		}
		
		return list;
	}
	

	
	/**
	 * 重置密码
	 * @param entity 参数列表 username,password
	 * @return 返回结果
	 */
	@Override
	public Object updateInfo(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString()  );
		
		
		if(MapUtil.isNulls(entity, "username,password")){	
			
//			if( String.valueOf(entity.get("loginname")).length() > 15 ) {
//				return Enum_MSG.参数错误.message("用户长度不能大于15位");
//			}
//			if( String.valueOf(entity.get("password")).length() > 12 ) {
//				return Enum_MSG.参数错误.message("密码长度不能大于12位");
//			}
					
			JSONObject xmlData = new JSONObject();
			xmlData.put("MerchantCode", this.MerchantCode);
			xmlData.put("PlayerId", entity.get("username").toString());
			xmlData.put("Password", entity.get("password").toString());
			
			JSONObject result =  doPostSubmitMap(this.API_URL.concat("/Player/ResetPassword"), xmlData);
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString() , result.toString() );
			
			
			//	接口调用成功
			if( result.getString("Code").equals("0") ) {
				return Enum_MSG.成功.message(result.getString("Message"));
			} else {
				return Enum_MSG.失败.message(result.getString("Code"), result.getString("Message"));
			}
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}

	}

	/**
	 * 获取订单接口 ExTransID
	 * @return 返回结果
	 */
	@Override
	public Object getOrder(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "username,TransactionId,ProductWallet")){	
			
			JSONObject xmlData = new JSONObject();
			xmlData.put("MerchantCode", this.MerchantCode);
			xmlData.put("PlayerId", entity.get("username").toString());
			xmlData.put("ProductWallet", entity.get("ProductWallet").toString());
			xmlData.put("TransactionId", entity.get("TransactionId").toString());
			
			JSONObject result =  doPostSubmitMap(this.API_URL.concat("/Transaction/CheckTransferStatus"), xmlData);
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() , result.toString() );
			
			//	接口调用成功
			if( result.getString("Code").equals("0") ) {
				if(result.getString("Status").equals("Approved")) {
					return Enum_MSG.成功.message("确定订单成功");
				} else {
					return Enum_MSG.失败.message("当前订单状态为："+result.getString("Status"));
				}
				
			} else {
				return Enum_MSG.失败.message(result.getString("Code"), result.getString("Message"));
			}
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	/**
	 * 登录的接口
	 * @param entity 参数列表 username,GameCode,Language,ProductWallet,deviceType
	 * 
	 * IMSB
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object login(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() );
		
		if(MapUtil.isNulls(entity, "username,GameCode,Language,ProductWallet,deviceType")){	
			
			String deviceType = String.valueOf(entity.get("deviceType"));
			if( !deviceType.equals("0") && !deviceType.equals("1")) {
				return Enum_MSG.参数错误.message("HY游戏目前只能接受PC端与手机H5端的请求，即设备类型为0和1");
			}
			
			JSONObject xmlData = new JSONObject();
			xmlData.put("MerchantCode", this.MerchantCode);
			xmlData.put("PlayerId", entity.get("username").toString());
			xmlData.put("ProductWallet", entity.get("ProductWallet").toString());
			xmlData.put("Language", entity.get("Language").toString());
			xmlData.put("GameCode", entity.get("GameCode").toString());
			xmlData.put("IpAddress", "127.0.0.1");
			
			String url = this.API_URL.concat("/Game/NewLaunchGame");//PC
			if(deviceType.equals("1")) {
				url = this.API_URL.concat("/Game/NewLaunchMobileGame");//H5
			}
			
			JSONObject result =  doPostSubmitMap(url, xmlData);
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() , result.toString() );
			
			//	接口调用成功
			if( result.getString("Code").equals("0") ) {
				return Enum_MSG.成功.message(result.getString("GameUrl"));
				
			} else {
				return Enum_MSG.失败.message(result.getString("Code"), result.getString("Message"));
			}
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
		
	}
	/**
	 * 试玩接口
	 * @param entity 参数列表 username,password
	 * 
	 * @return 返回结果
	 */
	public Object login_try(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login_try", entity.toString() );
		
		if(MapUtil.isNulls(entity, "GameCode,Language,ProductWallet,deviceType")){	
			
			String deviceType = String.valueOf(entity.get("deviceType"));
			if( !deviceType.equals("0") && !deviceType.equals("1")) {
				return Enum_MSG.参数错误.message("HY游戏目前只能接受PC端与手机H5端的请求，即设备类型为0和1");
			}
			
			JSONObject xmlData = new JSONObject();
			xmlData.put("MerchantCode", this.MerchantCode);
//			xmlData.put("PlayerId", entity.get("username").toString());//不传递用户就是试玩
			xmlData.put("ProductWallet", entity.get("ProductWallet").toString());
			xmlData.put("Language", entity.get("Language").toString());
			xmlData.put("GameCode", entity.get("GameCode").toString());
			xmlData.put("IpAddress", "127.0.0.1");
			
			
			String url = this.API_URL.concat("/Game/LaunchFreeGame");//PC
			if(deviceType.equals("1")) {
				url = this.API_URL.concat("/Game/LaunchFreeMobileGame");//H5
			}
			
			JSONObject result =  doPostSubmitMap(this.API_URL.concat(url), xmlData);
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login_try", entity.toString() , result.toString() );
			
			//	接口调用成功
			if( result.getString("Code").equals("0") ) {
				return Enum_MSG.成功.message(result.getString("GameUrl"));
				
			} else {
				return Enum_MSG.失败.message(result.getString("Code"), result.getString("Message"));
			}
			
		}  else {
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
	
	private JSONObject doPostSubmitMap(String url, JSONObject xmlData) {
		
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
			
			JSONObject jsonObject = new JSONObject();
            if(statusCode == 200)  {
            	String result = EntityUtils.toString(response.getEntity());
            	jsonObject = JSONObject.fromObject(result);
            } else {
            	jsonObject.put("Code", ""+statusCode);
    			jsonObject.put("Message", "返回响应码不正确："+statusCode);
            }
            return jsonObject;
            
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Code", "-1");
			jsonObject.put("Message", e.getMessage());
			return jsonObject;
		} finally {
			
		}
	}
	
	public static void main(String[] a){
//		password=XQ5Lv5Xq, loginname=zOne6KTd83sMEpb
		
		IMGameAPI tag = new IMGameAPI();
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("Currency", "CNY");
		entity.put("ProductWallet", "301");
		
		entity.put("username", RandomString.createRandomString(20).toLowerCase());//#&*
		entity.put("password", RandomString.createRandomString(8));
//		System.out.println(tag.createAccount(entity));//username,password
		
		entity.put("username", "gwq1vqr0yu1199neekby");
		entity.put("password", "EWLfNsdD");
//		
		entity.put("TransactionId", "IN"+RandomString.createRandomString(23));
		entity.put("Amount", "20");
//		System.out.println(tag.deposit(entity));//username,remit,remitno
//
//		entity.put("TransactionId", "OUT"+RandomStringNum.createRandomString(22));
//		entity.put("Amount", "1");
//		System.out.println(tag.withdraw(entity));//username,remit,remitno
//
//		
//		System.out.println(tag.getOrder(entity));//username,remitno
////		
//		System.out.println(tag.getBalance(entity));//username,password
		
//		entity.put("password", RandomString.createRandomString(8));
//		System.out.println(tag.updateInfo(entity));//username,password
//		
		entity.put("GameCode", "IMSB");
		entity.put("Language", "ZH-CN");
		entity.put("deviceType", "0");
//		System.out.println(tag.login(entity));//GameCode,Language,ProductWallet,deviceType
//		
//		System.out.println(tag.getRecord(entity));//
		System.out.println("O9BPE3BUwBgfD8D44A8Ulx9sMpDTJfRa".length());
			
	}
}

