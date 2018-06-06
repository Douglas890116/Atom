package com.hy.pull.common.util.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParamConfig;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;


import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.hy.pull.common.util.AESUtil2Net;
import com.hy.pull.common.util.Encrypt;
import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.PBKDF2Encryption;
import com.hy.pull.common.util.SGSUtil;
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
 * TGP的申博接口
 * @author temdy
 */
public class TGPGameAPI implements BaseInterface {
	
	private String API_URL = "https://tgpaccess.com";
	private String client_id = "jmt18";
	private String client_secret = "9aeBfisOAY9fF5jAJRDchMVY4F3TDQJqxypk3bNBqHz";
	private String istestplayer = "true";//
	private String LobbyDomain = "https://lobby188.info/";

	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	
	
	/**
	 * 默认构造函数
	 * 
	 * 测试时可以使用，不用传递秘钥进来
	 */
	@Deprecated
	public TGPGameAPI(){
		
	}
	
	/**
	 * 构建函数，初始化参数
	 * @param API_URL API接口URL
	 * @param MD5_KEY MD5密钥
	 * @param DES_KEY DES密钥
	 */
	
	public TGPGameAPI(String API_URL,String client_id,String client_secret,String istestplayer, String LobbyDomain,String GAME_API_URL){
		this.API_URL = API_URL;
		this.client_id = client_id;
		this.client_secret = client_secret;
		this.istestplayer = istestplayer;
		this.LobbyDomain = LobbyDomain;
		this.GAME_API_URL = GAME_API_URL;
	}
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
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
	 * @param entity 参数列表 username
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "username")){
			
			if( String.valueOf(entity.get("username")).length() > 20 ) {
				return Enum_MSG.参数错误.message("用户长度不能大于20位");
			}
			
//			VIP Level ID  Description 
//			1  Bronze - basic limits 
//			2  Silver - upgraded limits 
//			3  Gold - high limits 
//			4  Platinum - VIP limits 
//			5  Diamond - VVIP limits
			
			JSONObject object_request = new JSONObject();
			object_request.put("ipaddress", "192.168.1.1");
			object_request.put("username", entity.get("username"));
			object_request.put("userid", entity.get("username"));
			object_request.put("lang", "zh-CN");
			object_request.put("cur", "RMB");
			object_request.put("betlimitid", "1");
			object_request.put("istestplayer", istestplayer);//Whether a player is a test (house) player. 
			object_request.put("platformtype", "0");//The platform the player comes from. The default value is 0.  0 for Desktop and 1 for Mobile
			
			String url = this.API_URL.concat("/api/player/authorize");
			String result = sendJsonDataByPost(url, object_request.toString());
					
			
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , result );
			
			JSONObject object =  JSONObject.fromObject( result );
			
			//接口调用成功
			if( !object.has("code") ) {
				
				if(object.getString("isnew").equals("false")) {//已存在
					return Enum_MSG.账号已存在.message("存在相同账号");
				}
				
				return Enum_MSG.成功.message(object.getString("authtoken"));//token
				
			} else {//网络异常
				return object.toString();
			}
			
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
		
	}

	
	
	/**
	 * 获取余额
	 * @param entity 参数列表 username
	 * 
	 * password 密码
	 * @return 返回结果
	 */
	@Override
	public Object getBalance(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "username")){
			
			
//			http://<SGSIntegrationAPIServer>/player/balance?userid=fa39953d&cur=RMB
			
			String url = this.API_URL.concat("/api/player/balance?userid=").concat(entity.get("username").toString()).concat("&cur=RMB");
			String result = getHttpUrlData(url);
			
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , result );
			
			JSONObject object = JSONObject.fromObject(result);
			
			if( !object.has("code")) {
				
				return Enum_MSG.成功.message(object.getString("bal"));
			} else {
				return object.toString();
			}
			
			
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	
	
	/**
	 * 下分（取款）的接口
	 * @param entity 参数列表 username,orderid,amount
	 * 
	 * @return 返回结果
	 */
	public Object withdraw(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "username,orderid,amount")){
			
			if( String.valueOf(entity.get("orderid")).length() > 50 ) {
				return Enum_MSG.参数错误.message("订单号长度应为50个字符");
			}
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("userid", entity.get("username"));
			jsonObject.put("amt", entity.get("amount"));
			jsonObject.put("cur", "RMB");
			jsonObject.put("txid", entity.get("orderid"));
			jsonObject.put("timestamp", SGSUtil.getIsoDateCurren());
			
			String url = this.API_URL.concat("/api/wallet/debit");
			String result = sendJsonDataByPost(url, jsonObject.toString());
			
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() , result );
			
			JSONObject object = JSONObject.fromObject(result);
			
			if( !object.has("code")) {
				
				return Enum_MSG.成功.message(object.getString("bal"));
			} else {
				return object.toString();
			}
			
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	
	
	/**
	 * 上分（存款）的接口 username,orderid,amount
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "username,orderid,amount")){
			
			if( String.valueOf(entity.get("orderid")).length() > 50 ) {
				return Enum_MSG.参数错误.message("订单号长度应为50个字符");
			}
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("userid", entity.get("username"));
			jsonObject.put("amt", entity.get("amount"));
			jsonObject.put("cur", "RMB");
			jsonObject.put("txid", entity.get("orderid"));
			jsonObject.put("timestamp", SGSUtil.getIsoDateCurren());
			
			
			String url = this.API_URL.concat("/api/wallet/credit");
			String result = sendJsonDataByPost(url, jsonObject.toString());
			
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() , result );
			
			JSONObject object = JSONObject.fromObject(result);
			
			if( !object.has("code")) {
				
				return Enum_MSG.成功.message(object.getString("bal"));
			} else {
				return object.toString();
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
		
		try{
			
			
			String startdate = URLEncoder.encode(SGSUtil.getDateCurrenStart1hour() ,"UTF-8");
			String enddate = URLEncoder.encode(SGSUtil.getDateCurren() ,"UTF-8");
			
			String url = this.API_URL.concat("/api/history/bets").concat("?startdate="+startdate+"&enddate="+enddate+"&includetestplayers=true");
			String result = getHttpUrlData(url);
			System.out.println(url);
			
			System.out.println("startdate="+SGSUtil.getDateCurrenStart1hour());
			System.out.println("enddate="+SGSUtil.getDateCurren());
			System.out.println("请求URL："+url);
			System.out.println("调用结果："+result);
			
			
			if( !result.equals("0")) {
				
				return Enum_MSG.成功.message(result);
				
			} else {
				return Enum_MSG.接口调用错误.message("网络异常");
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
		
	}
	
	private String sendJsonDataByPost(String url, String json){
		
		try {
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
    		int timeout = 10000;
    		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
    		
//			System.out.println("请求URL："+url);
//			System.out.println("请求DATA："+json);
			// Send data by post method in HTTP protocol,use HttpPost instead of
			// PostMethod which was occurred in former version
			
			HttpPost post = new HttpPost(url);
			// Construct a string entity
			StringEntity entity = new StringEntity(json);
			// Set XML entity
			post.setEntity(entity);
			// Set content type of request header
			post.setConfig(requestConfig);
			
			JSONObject jsonObject = JSONObject.fromObject( getAccessToken() );
			if( !jsonObject.getString("code").equals("0")) {
				return Enum_MSG.失败.message(jsonObject.getString("info"));
			} 
			String AccessToken = jsonObject.getString("info");
			
			post.addHeader("Content-Type", "application/json");
			post.setHeader("Accept","application/json");
			post.addHeader("Authorization", "Bearer "+AccessToken);
			
//			Header[] headers = post.getAllHeaders();
//			System.out.println("请求Header数据：");
//			for (Header header : headers) {
//				System.out.println(header.getName()+"="+header.getValue());
//			}
			
			// Execute request and get the response
			HttpResponse response = httpClient.execute(post);
			// Response Header - StatusLine - status code
			
			
			String result = EntityUtils.toString(response.getEntity());
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		} finally{
			
		}
	}
	
	private String getHttpUrlData(String url){
		
		HttpMethod method = null;  
        try {  
            org.apache.commons.httpclient.URI uri = new org.apache.commons.httpclient.URI(url);  
            org.apache.commons.httpclient.HttpClient client = new org.apache.commons.httpclient.HttpClient();  
            HostConfiguration hcfg = new HostConfiguration();  
            hcfg.setHost(uri);  
            
            int timeout = 10000;
    		client.setConnectionTimeout(timeout);
    		client.setTimeout(timeout);
            
            client.setHostConfiguration(hcfg);  
            // 参数验证  
            client.getParams().setAuthenticationPreemptive(true);  
            // GET请求方式  
            method = new GetMethod(url);  
            
            JSONObject jsonObject = JSONObject.fromObject( getAccessToken() );
			if( !jsonObject.getString("code").equals("0")) {
				return Enum_MSG.失败.message(jsonObject.getString("info"));
			} 
			String AccessToken = jsonObject.getString("info");
			method.addRequestHeader("Authorization", "Bearer "+AccessToken);
			
            client.executeMethod(method);  
            
            return method.getResponseBodyAsString();  
        } catch (Exception e) {  
            e.printStackTrace();  
            return Enum_MSG.接口调用错误.message(e.getMessage());
        }  
	
	}

	
	private String getAccessToken() {
        try {
        	
        	String httpUrl = this.API_URL.concat("/api/oauth/token");
        	HttpPost httpRequst = new HttpPost(httpUrl);//创建HttpPost对象  
        	
            //填入各个表单域的�?
        	List paramsxx = new ArrayList<BasicNameValuePair>(); 
        	
        	BasicNameValuePair xxx1 = new BasicNameValuePair("client_id", this.client_id);
			paramsxx.add(xxx1);
			
			BasicNameValuePair xxx2 = new BasicNameValuePair("client_secret", this.client_secret);
			paramsxx.add(xxx2);
			
			BasicNameValuePair xxx3 = new BasicNameValuePair("grant_type", "client_credentials");
			paramsxx.add(xxx3);
			
			BasicNameValuePair xxx4 = new BasicNameValuePair("scope", "playerapi");
			paramsxx.add(xxx4);
			
    		
        	UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(paramsxx,HTTP.UTF_8);
    		httpRequst.setEntity(encodedFormEntity);
    		
    		httpRequst.addHeader("Content-Type", "application/x-www-form-urlencoded");
    		
    		
    		CloseableHttpClient httpClient = HttpClients.createDefault();
    		int timeout = 10000;
    		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
    		httpRequst.setConfig(requestConfig);
    		
            HttpResponse httpResponse = httpClient.execute(httpRequst);  
            
            if(httpResponse.getStatusLine().getStatusCode() == 200)  {
            	JSONObject jsonObject = new JSONObject();
            	jsonObject = JSONObject.fromObject(EntityUtils.toString(httpResponse.getEntity()));
            	System.out.println("获取TOKEN结果："+jsonObject);
            	
    			if(jsonObject.has("access_token")) {
    				System.out.println("成功获取到TOKEN="+jsonObject.getString("access_token"));
    				return Enum_MSG.成功.message(jsonObject.getString("access_token"));
    			} else {
    				return Enum_MSG.失败.message(jsonObject.getString("error"),jsonObject.getString("error_description"));
    			}
    			
            } else {
            	return Enum_MSG.失败.message("未能获取token");
            }
            
            
		} catch (Exception e) {
			e.printStackTrace();
			
			return Enum_MSG.失败.message("-1",e.getMessage());
		}
        
	}

	/**
	 * 更新信息接口
	 * @param entity 参数列表 username,password
	 * @return 返回结果
	 */
	@Override
	public Object updateInfo(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString()  );
		
		
		return Enum_MSG.参数错误.message("不支持的方法");

	}

	/**
	 * 获取订单接口 
	 * 
	 * 此接口不提供
	 * 
	 * @return 返回结果
	 */
	@Override
	@Deprecated
	public Object getOrder(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString()  );
		
		return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
	}

	/**
	 * 登录的接口
	 * @param entity 参数列表 username,deviceType,playtype
	 * 
	 * playtype：
	 * 
	 * SUNBET_SX=申博视讯
	 * RT_DZ=红虎视讯
	 * LX_SX=勒思视讯
	 * SUNBET_SX_DZ=申博电子和视讯
	 * 
	 * 
	 * IGPTech=IGPTech Mini-Game
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object login(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() );
		
		if(MapUtil.isNulls(entity, "username,deviceType,playtype")){//username,deviceType,playtype,gpcode,gcode
			
//			VIP Level ID  Description 
//			1  Bronze - basic limits 
//			2  Silver - upgraded limits 
//			3  Gold - high limits 
//			4  Platinum - VIP limits 
//			5  Diamond - VVIP limits
			
			JSONObject object_request = new JSONObject();
			object_request.put("ipaddress", "192.168.1.1");
			object_request.put("username", entity.get("username"));
			object_request.put("userid", entity.get("username"));
			object_request.put("lang", "zh-CN");
			object_request.put("cur", "RMB");
			object_request.put("betlimitid", "3");
			object_request.put("istestplayer", istestplayer);//Whether a player is a test (house) player. 
			object_request.put("platformtype", entity.get("deviceType"));//The platform the player comes from. The default value is 0.  0 for Desktop and 1 for Mobile
			
			String url = this.API_URL.concat("/api/player/authorize");
			String result = sendJsonDataByPost(url, object_request.toString());
					
			
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() , result );
			
			JSONObject object =  JSONObject.fromObject( result );
			
			//接口调用成功
			if( !object.has("code") ) {
				
				String authtoken = object.getString("authtoken");
				
				String playtype = String.valueOf(entity.get("playtype"));
				String deviceType = String.valueOf(entity.get("deviceType"));
				
				if(deviceType.equals("0")) {//PC
					https://lobby188.info/RTlobby?token={authtoken}
					if( playtype.equals("SUNBET_SX") ) {
						return Enum_MSG.成功.message(LobbyDomain+"/SBlobby?token="+authtoken);//token
					} else if( playtype.equals("RT_DZ") ) {
						return Enum_MSG.成功.message(LobbyDomain+"/RTlobby?token="+authtoken);//token
					} else if( playtype.equals("LX_SX") ) {
						return Enum_MSG.成功.message(LobbyDomain+"/LXlobby?token="+authtoken);//token
					} else if( playtype.equals("SUNBET_SX_DZ") ) {
						return Enum_MSG.成功.message(LobbyDomain+"/SRlobby?token="+authtoken);//token
					} else if( playtype.equals("IGPTech") ) {
						return Enum_MSG.成功.message(LobbyDomain+"/api/1/minilobby?token="+authtoken);//token
					}
					
				} else {//手机
					
					if( playtype.equals("SUNBET_SX") ) {
						return Enum_MSG.成功.message(LobbyDomain+"/SBmlobby?token="+authtoken);//token
					} else if( playtype.equals("RT_DZ") ) {
						return Enum_MSG.成功.message(LobbyDomain+"/RTmlobby?token="+authtoken);//token
					} else if( playtype.equals("LX_SX") ) {
						return Enum_MSG.成功.message(LobbyDomain+"/LRlobby?token="+authtoken);//token
					} else if( playtype.equals("SUNBET_SX_DZ") ) {
						return Enum_MSG.成功.message(LobbyDomain+"/SRmlobby?token="+authtoken);//token
					} else if( playtype.equals("IGPTech") ) {
						return Enum_MSG.成功.message(LobbyDomain+"/api/1/minilobby?token="+authtoken);//token
					}
					
				}
				
				/***************
				SB Mobile Lobby 申博手机大厅
				https://lobby188.info/SBmlobby?token={authtoken}
				SB Lobby 申博大厅
				https://lobby188.info/SBlobby?token={authtoken}

				RT Mobile Lobby 红虎手机大厅
				https://lobby188.info/RTmlobby?token={authtoken} 
				RT Lobby 
				https://lobby188.info/RTlobby?token={authtoken}
				

				SB RT Mobile Lobby 真人与电子手机大厅
				https://lobby188.info/SRmlobby?token={authtoken} 
				SB RT Lobby 真人与电游大厅
				https://lobby188.info/SRlobby?token={authtoken}
				

				LX RT Lobby 勒思手机电子厅 
				https://lobby188.info/LRlobby?token={authtoken}
				LX Lobby 勒思电子厅 
				https://lobby188.info/LXlobby?token={authtoken}
				
				IGPTech Mini-Game Lobby 
				https://lobby188.info/api/1/minilobby?token={authtoken}
				**************/
				return Enum_MSG.失败.message("不支持的游戏类型："+playtype);
				
			} else {//网络异常
				return object.toString();
			}
			
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
	
	
	public static void main(String[] a){
//		password=XQ5Lv5Xq, loginname=zOne6KTd83sMEpb
		
		TGPGameAPI tag = new TGPGameAPI();
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("username", RandomString.createRandomString(20).toLowerCase());//#&*
		entity.put("password", RandomString.createRandomString(8));
		System.out.println(tag.createAccount(entity));//username,password
		
//		
		entity.put("orderid", "IN"+RandomString.createRandomString(18));
		entity.put("amount", "2");
//		System.out.println(tag.deposit(entity));//username,orderid,amount
//
		entity.put("orderid", "OUT"+RandomStringNum.createRandomString(17));
		entity.put("amount", "1");
		System.out.println(tag.withdraw(entity));//username,orderid,amount
//
//		
		System.out.println(tag.getOrder(entity));//username,remitno
//
		System.out.println(tag.getBalance(entity));//username
//		
		entity.put("deviceType", "0");
		entity.put("playtype", "RT_DZ");
		System.out.println(tag.login(entity));//username,deviceType,playtype
//		
		System.out.println(tag.getRecord(entity));//
		
			
	}
}

