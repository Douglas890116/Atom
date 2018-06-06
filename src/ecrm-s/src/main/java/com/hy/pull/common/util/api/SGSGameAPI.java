package com.hy.pull.common.util.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hy.pull.common.util.Encrypt;
import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.SGSUtil;
import com.hy.pull.common.util.game.mg.MGUtil;
import com.hy.pull.common.util.game.sa.XmlUtil;
import com.hy.pull.common.util.game.ttg.TTGUtil;
import com.hy.pull.common.util.game.zj.ZJUtil;
import com.maven.config.SystemConstant;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;
import com.maven.utility.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpParamConfig;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;


/**
 * SGS申博游戏接口（申博不需要游戏密码）
 * @author temdy
 */
public class SGSGameAPI implements BaseInterface {

	
	private String API_URL = "http://sctrapi.sbuat.com/";
	private String client_id = "NBO";
	private String client_secret = "EE4DMAbSHoWZyMG3FrlFTtsVaNBJ56sUysMWIEAwTF1J";
	private String LobbyDomain = "http://staging.lobby188.info/";//
	private String istestplayer = "true";//
	
	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	
	/**
	 * 默认构造函数
	 * 
	 * 不传递时使用默认的进行测试，正式时需要传递参数进来，此方法不使用
	 */
	@Deprecated
	public SGSGameAPI() {
		
	}
	/**
	 * 构建函数，初始化参数
	 * @param API_URL 接口URL
	 * @param UserName
	 * @param Password  
	 */
	public SGSGameAPI(String API_URL, String client_id,String client_secret, String LobbyDomain,String istestplayer,String GAME_API_URL) {
		this.API_URL = API_URL;
		this.client_id = client_id;
		this.client_secret = client_secret;
		this.LobbyDomain = LobbyDomain;
		this.istestplayer = istestplayer;
		this.GAME_API_URL = GAME_API_URL;
	}
	
	
	/**
	 * 创建用户接口
	 * @param entity 参数列表 username,istestplayer
	 * 
	 * 不需要密码，支持特殊符号
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString()  );
		
		try{
			
//			返回示例

			
			if(MapUtil.isNulls(entity, "username")){
				
				if( String.valueOf(entity.get("username")).length() > 20 ) {
					return Enum_MSG.参数错误.message("用户长度不能大于20位");
				}
				
//				VIP Level ID  Description 
//				1  Bronze - basic limits 
//				2  Silver - upgraded limits 
//				3  Gold - high limits 
//				4  Platinum - VIP limits 
//				5  Diamond - VVIP limits
				
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
					
					if( object.getString("err").equals("null")) {
						
						if(object.getString("isnew").equals("false")) {//已存在
							return Enum_MSG.账号已存在.message("存在相同账号");
						}
						
						return Enum_MSG.成功.message(object.getString("authtoken"));//token
					} else {
						
						return Enum_MSG.失败.message(object.getString("err"), object.getString("errdesc"));
					}
					
				} else {//网络异常
					return object.toString();
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
		
	}

	/**
	 * 获取余额
	 * @param entity 参数列表 username
	 * @return 返回结果
	 */
	@Override
	public Object getBalance(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString()  );
		try{
			
//			返回示例

			
			if(MapUtil.isNulls(entity, "username")){
				
				
//				http://<SGSIntegrationAPIServer>/player/balance?userid=fa39953d&cur=RMB
				
				String url = this.API_URL.concat("/api/player/balance?userid=").concat(entity.get("username").toString()).concat("&cur=RMB");
				String result = getHttpUrlData(url);
				
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				
				if( !object.has("code")) {
					
					if( !object.getString("err").equals("null") ) {
						return Enum_MSG.失败.message(object.getString("err"), object.getString("errdesc"));
					}
					return Enum_MSG.成功.message(object.getString("bal"));
				} else {
					return object.toString();
				}
				
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
		
	}

	/**
	 * 下分（取款）的接口
	 * @param entity 参数列表 username,orderid,amount
	 * 
	 * orderid=20位数长度
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object withdraw(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString()  );
		try{
			
//			返回示例

			
			if(MapUtil.isNulls(entity, "username,orderid,amount")){
				
				if( String.valueOf(entity.get("orderid")).length() != 20 ) {
					return Enum_MSG.参数错误.message("订单号长度应为20个字符");
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
					
					if( !object.getString("err").equals("null")) {
						return Enum_MSG.失败.message(object.getString("err"), object.getString("errdesc"));
					}
					return Enum_MSG.成功.message(object.getString("bal"));
				} else {
					return object.toString();
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
	}

	/**
	 * 上分（存款）的接口
	 * @param entity 参数列表 username,orderid,amount
	 * 
	 * orderid=20位数长度
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString()  );

		try{
			
//			返回示例

			
			if(MapUtil.isNulls(entity, "username,orderid,amount")){
				
				if( String.valueOf(entity.get("orderid")).length() != 20 ) {
					return Enum_MSG.参数错误.message("订单号长度应为20个字符");
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
					
					if( !object.getString("err").equals("null")) {
						return Enum_MSG.失败.message(object.getString("err"), object.getString("errdesc"));
					}
					return Enum_MSG.成功.message(object.getString("bal"));
				} else {
					return object.toString();
				}
				
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
	}

	/**
	 * 获取游戏结果
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	@Override
	public Object getRecord(Map<String, Object> entity) {
		try{
			
			
			String startdate = URLEncoder.encode(SGSUtil.getDateCurrenStart1hour() ,"UTF-8");
			String enddate = URLEncoder.encode(SGSUtil.getDateCurren() ,"UTF-8");
			
			String url = this.API_URL.concat("/api/report/bethistory").concat("?startdate="+startdate+"&enddate="+enddate+"&includetestplayers=true");
			String result = getHttpUrlDataByOrder(url);
			
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

	/**
	 * 更新信息接口
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	@Override
	public Object updateInfo(Map<String, Object> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获取订单接口 username
	 * @param entity 参数列表
	 * 
	 * 此接口数据有2分钟左右延迟，所以不能使用
	 * @return 返回结果
	 */
	@Deprecated
	public Object getOrder(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString()  );
		try{
			
//			返回示例
//			
			
//			http://<SGSIntegrationAPIServer>/api/report/transferhistory 				
				
			if(MapUtil.isNulls(entity, "username,orderid")){
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度应为20个字符");
//				}
//				if( String.valueOf(entity.get("password")).length() != 8 ) {
//					return Enum_MSG.参数错误.message("用户登录密码长度应为8个字符");
//				}
				
				String startdate = URLEncoder.encode(SGSUtil.getDateCurrenStart1hour() ,"UTF-8");
				String enddate = URLEncoder.encode(SGSUtil.getDateCurren() ,"UTF-8");
				
				String url = this.API_URL.concat("/api/report/transferhistory?userid="+entity.get("username")+"&startdate="+startdate+"&enddate="+enddate+"&includetestplayers=true");
				System.out.println(url);
				
				String result = getHttpUrlDataByOrder(url);
				System.out.println("调用结果："+result);
//				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() , result );
				
				
				//接口调用成功
				if( !result.equals("0") ) {
					
					if(result.contains(entity.get("orderid").toString())) {
						return Enum_MSG.成功.message("成功");
					} else {
						return Enum_MSG.失败.message("没有找到订单");
					}
					
				} else {//网络异常
					return Enum_MSG.失败.message("网络异常或接口调用异常");
				}
				
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
	}
	/**
	 * 获取游戏列表接口
	 * @param entity 参数列表 username,deviceType
	 * @return 返回结果
	 */
	public static List<Map<String, String>> listGames = new ArrayList<Map<String,String>>();
	public static List<Map<String, String>> listGamesh5 = new ArrayList<Map<String,String>>();
	public List<Map<String, String>> getListGames(Map<String, Object> entity) {
		if(listGames.size() == 0) {
			gamelist(entity);
		}
		return listGames;
	}
	public List<Map<String, String>> getListGamesh5(Map<String, Object> entity) {
		if(listGamesh5.size() == 0) {
			gamelist(entity);
		}
		return listGamesh5;
	}
	private Object gamelist(Map<String, Object> entity) {

		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "gamelist", entity.toString()  );
		try{
			
//			返回示例
			
			if(MapUtil.isNulls(entity, "username,deviceType")){
				
//				
//				Code Game Provider
//				0 Desktop
//				1 Mobile
//				4 Mini Game
				

//				Table of Game Providers
//				Code Game Provider
//				
//				TGP Red Tiger
//				SB Sunbet
//				GB Globalbet
//				GD Gold Deluxe
//				LAX Laxino
//				FC Fly Cow
				
//				Game Types
//				ID Description
//				
//				0 Slot
//				1 Table Game
//				2 Live Dealer
//				3 Lobby
//				4 Instant Win
//				5 Lottery
//				6 Mini Game - Slot
//				7 Mini Game - Instant

//				进入某个游戏 
//				http://staging.lobby188.info/gamelauncher?gpcode=SB&gcode=Sunbet_Lobby&platform=1&token=uQLQxKbmYbYH6UJ7w9hpcWs4T79aQWYfZE4nIeUeePus1Du5vTofnpUPbrmFXE8H0btk5qSAMQKhpaMFD0R8eBLEwiymSX80DCDRM5XKyNjLorPR0Byi3FiqjA5JVD9FEo
				
				
				String url = this.API_URL.concat("/api/game/list?lang=zh-CN&platformtype="+entity.get("deviceType")+"&iconres=208x208");
				String result = getHttpUrlData(url);
				System.out.println("url="+url);
				
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "gamelist", entity.toString() , result );
				
				JSONObject object =  JSONObject.fromObject( result );
				
				//接口调用成功
				if( !object.has("code") ) {
					
					if( object.getString("err").equals("null")) {
						
						JSONArray list = object.getJSONArray("games");
						if(list != null && list.size() > 0) {
							for (int i = 0; i < list.size(); i++) {
								JSONObject temp = (JSONObject)list.get(i);
								Map map = (Map)JSONObject.toBean(temp, Map.class);
								map.put("iconurl", "http:"+map.get("iconurl").toString());
								if(entity.get("deviceType").toString().equals("0")) {
									listGames.add((Map)JSONObject.toBean(temp, Map.class));
								} else {
									listGamesh5.add((Map)JSONObject.toBean(temp, Map.class));
								}
							}
						}
						
						return Enum_MSG.成功.message("");//token
						
					} else {
						
						return Enum_MSG.失败.message(object.getString("err"), object.getString("errdesc"));
					}
					
				} else {//网络异常
					return object.toString();
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}


	}
	
	/**
	 * 登录的接口
	 * @param entity 参数列表 username,deviceType,playtype
	 * @return 返回结果
	 */
	@Override
	public Object login(Map<String, Object> entity) {

		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString()  );
		try{
			
//			返回示例

			
			if(MapUtil.isNulls(entity, "username,deviceType,playtype")){//username,deviceType,playtype,gpcode,gcode
				
//				VIP Level ID  Description 
//				1  Bronze - basic limits 
//				2  Silver - upgraded limits 
//				3  Gold - high limits 
//				4  Platinum - VIP limits 
//				5  Diamond - VVIP limits
				
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
						
				String deviceType = entity.get("deviceType").toString();
				
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() , result );
				
				JSONObject object =  JSONObject.fromObject( result );
				
				//接口调用成功
				if( !object.has("code") ) {
					
					if( object.getString("err").equals("null")) {
						
						String authtoken = object.getString("authtoken");
						
//						真人直达
//						SB/Sunbet_Lobby
						if(entity.get("playtype").toString().equals("SX")) {
							return Enum_MSG.成功.message(LobbyDomain+"/gamelauncher?gpcode=SB&gcode=Sunbet_Lobby&platform="+deviceType+"&token="+authtoken);
						} else {
							
							//进入大厅
							if(deviceType.equals("0")) {
								return Enum_MSG.成功.message(LobbyDomain+"/sc88desktop?token="+authtoken);//token
							} else {
								return Enum_MSG.成功.message(LobbyDomain+"/sc88mobile?token="+authtoken);//token
							}
							
							
						}
						
					} else {
						
						return Enum_MSG.失败.message(object.getString("err"), object.getString("errdesc"));
					}
					
				} else {//网络异常
					return object.toString();
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
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
	private String sendJsonDataByPost(String url, String json){
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		int timeout = 10000;
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
		
		
		try {
			System.out.println("请求URL："+url);
			System.out.println("请求DATA："+json);
			// Send data by post method in HTTP protocol,use HttpPost instead of
			// PostMethod which was occurred in former version
			HttpPost post = new HttpPost(url);
			post.setConfig(requestConfig);
			// Construct a string entity
			StringEntity entity = new StringEntity(json);
			// Set XML entity
			post.setEntity(entity);
			// Set content type of request header
			/***
			StringToSign = {client_secret} + {X-Sgs-Date}
			Signature = Base64(HMAC-SHA1({client_secret}, UTF-8-Encoding-Of({StringToSign})));  
			Authorization = "SGS" + " " + {client_id} + ":" + {Signature};  
			*/
			
			
			String X_Sgs_Date = SGSUtil.getIsoDateCurren();
			String StringToSign = this.client_secret + X_Sgs_Date;
			String Signature = SGSUtil.getSignature(this.client_secret, StringToSign);  
			String Authorization = "SGS" + " " + this.client_id + ":" + Signature;
			
			post.setHeader("Accept","application/json");
			post.setHeader("Content-Type", "application/json");// charset=utf8
			
			post.addHeader("Authorization", Authorization);
			post.addHeader("X-Sgs-Date", X_Sgs_Date);
			
			Header[] headers = post.getAllHeaders();
			System.out.println("请求Header数据：");
			for (Header header : headers) {
				System.out.println(header.getName()+"="+header.getValue());
			}
			
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
		
        try {  
        	CloseableHttpClient httpClient = HttpClients.createDefault();
    		int timeout = 10000;
    		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
    		
    		HttpGet request = new HttpGet(url);
    		request.setConfig(requestConfig);
            
            String X_Sgs_Date = SGSUtil.getIsoDateCurren();
			String StringToSign = this.client_secret + X_Sgs_Date;
			String Signature = SGSUtil.getSignature(this.client_secret, StringToSign);  
			String Authorization = "SGS" + " " + this.client_id + ":" + Signature;
            request.addHeader("Authorization", Authorization);
            request.addHeader("X-Sgs-Date", X_Sgs_Date);
            
            HttpResponse response = httpClient.execute(request);
			
			String result = EntityUtils.toString(response.getEntity()); 
			return result;
        } catch (Exception e) {  
            e.printStackTrace();  
            return Enum_MSG.接口调用错误.message(e.getMessage());
        }  
	
	}
	
	private String getHttpUrlDataByOrder(String url){

        try {  
        	CloseableHttpClient httpClient = HttpClients.createDefault();
    		int timeout = 10000;
    		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
    		
    		HttpGet request = new HttpGet(url);
    		request.setConfig(requestConfig);
            
    		String X_Sgs_Date = SGSUtil.getIsoDateCurren();
			String StringToSign = this.client_secret + X_Sgs_Date;
			String Signature = SGSUtil.getSignature(this.client_secret, StringToSign);  
			String Authorization = "SGS" + " " + this.client_id + ":" + Signature;
            request.addHeader("Authorization", Authorization);
            request.addHeader("X-Sgs-Date", X_Sgs_Date);
            
            HttpResponse response = httpClient.execute(request);
			
			String result = EntityUtils.toString(response.getEntity()); 
			return result;
        } catch (Exception e) {  
            e.printStackTrace();  
            return "0";
        }  
        
	}
	
	public static void main(String[] args) {
		
		SGSGameAPI gameAPI = new SGSGameAPI();
		
		Map<String, Object> entity = new HashMap<String, Object>();
		
		entity.put("username", RandomString.createRandomString(20));
		entity.put("username", "YxmkhL6VQarZtrbNB10E");
//		entity.put("username", "QADcB9VJpHL@4B_SR#bz");
		
//		System.out.println(gameAPI.createAccount(entity));//10740737458443137745
		
		
		entity.put("amount", "1");
		entity.put("orderid", RandomStringNum.createRandomString(20));
		System.out.println(gameAPI.deposit(entity));
////		
		entity.put("amount", "1");
		entity.put("orderid", RandomStringNum.createRandomString(20));
		System.out.println(gameAPI.withdraw(entity));
//		
		System.out.println(gameAPI.getBalance(entity));
//		
		System.out.println(gameAPI.getOrder(entity));
		
		
//		entity.put("deviceType", "0");
//		System.out.println(gameAPI.login(entity));
////		
		entity.put("deviceType", "1");
		entity.put("playtype", "DZ");
		System.out.println(gameAPI.login(entity));
		
//		System.out.println(gameAPI.getListGames(entity));
//		System.out.println(gameAPI.gamelist(entity));
		
//		System.out.println(gameAPI.getRecord(entity));
	}
}



