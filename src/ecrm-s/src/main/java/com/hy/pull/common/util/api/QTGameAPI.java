package com.hy.pull.common.util.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.hy.pull.common.util.Encrypt;
import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.game.NHQGame;
import com.hy.pull.common.util.game.sa.DeEnCode;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.util.RandomString;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * QT游戏接口
 * @author temdy
 */
public class QTGameAPI implements BaseInterface {
	
	private String API_URL = "https://api.qtplatform.com";
	private String agentname = "api_jmt";
	private String agentpwd = "d56dx9f5";
	
	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	/**
	 * 默认构造函数
	 */
	@Deprecated
	public QTGameAPI(){
		
	}
	
	/**
	 * 构建函数，初始化参数
	 * @param API_URL API接口URL
	 * @param MD5_KEY MD5密钥
	 */
	public QTGameAPI(String API_URL,String agentname, String agentpwd,String GAME_API_URL){
		this.API_URL = API_URL;
		this.agentname = agentname;
		this.agentpwd = agentpwd;
		this.GAME_API_URL = GAME_API_URL;
	}

	/**
	 * 创建用户接口
	 * @param entity 参数列表 playerId,password,istrueplay
	 * 
	 * playerId=最少30个字符
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString()  );
		try{
			if(MapUtil.isNulls(entity, "playerId,password,istrueplay")){	
				
				entity.put("deviceType", "desktop");
				entity.put("gameId", "null");
				entity.put("returnUrl", "192.168.1.1");
				String result = login(entity).toString();
				
				JSONObject object = JSONObject.fromObject(result);
				if(object.getString("code").equals("0")) {
					return Enum_MSG.成功.message("创建成功");
				}
				
				return result;
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
	 * @param entity 参数列表 playerId
	 * username 用户名
	 * @return 返回结果
	 */
	@Override
	public Object getBalance(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString()  );
		try{
			if(MapUtil.isNulls(entity, "playerId")){//
				
				
				String result = getUrl(API_URL + "/v1/wallet/ext/"+entity.get("playerId") );
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( !object.has("code") ) {
					
					return Enum_MSG.成功.message(object.getString("amount"));
					
				} else {
					return Enum_MSG.失败.message(object.getString("code"), "查询订单："+object.getString("info"));
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
	 * @param entity 参数列表 playerId,amount,referenceId
	 * 
	 * referenceId=最少36个字符
	 * 
	 * @return 返回结果 返回订单确认码，需要存储起来
	 */
	@Override
	public Object withdraw(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() );
		try{
			if(MapUtil.isNulls(entity, "playerId,amount,referenceId")){	
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度必须为20位，当前"+String.valueOf(entity.get("username")).length())+"位";
//				}
				
				//预备转账
				JSONObject params = new JSONObject();
				params.put("playerId", entity.get("playerId"));
				params.put("amount", entity.get("amount"));
				params.put("referenceId", entity.get("referenceId"));
				params.put("type", "DEBIT");
				params.put("currency", "CNY");
				
				String result = sendPost(API_URL + "/v1/fund-transfers", params);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( object.has("status") && object.getString("status").equals("PENDING")) {//PENDING, COMPLETED, CANCELLED.待定，已完成，已取消。
					
					String transferId = object.getString("id");
					
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("status", "COMPLETED");
					
					result = sendPUT(API_URL + "/v1/fund-transfers/"+transferId+"/status", jsonObject);
					System.out.println("调用结果："+result);
					object = JSONObject.fromObject(result);
					
					if( object.has("status") && object.getString("status").equals("COMPLETED") ) {
						
						return Enum_MSG.成功.message(transferId);//返回单号需要存储起来
					} else {
						return Enum_MSG.失败.message(object.getString("code"), "完成交易步骤失败："+object.getString("info"));
					}
					
				} else {
					return Enum_MSG.失败.message(object.getString("code"), "预备交易步骤失败："+object.getString("info"));
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
	 * @param entity 参数列表 playerId,amount,referenceId
	 * 
	 * referenceId=最少36个字符
	 * 
	 * @return 返回结果 返回订单确认码，需要存储起来
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() );
		try{
			if(MapUtil.isNulls(entity, "playerId,amount,referenceId")){	
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度必须为20位，当前"+String.valueOf(entity.get("username")).length())+"位";
//				}
				
				//预备转账
				JSONObject params = new JSONObject();
				params.put("playerId", entity.get("playerId"));
				params.put("amount", entity.get("amount"));
				params.put("referenceId", entity.get("referenceId"));
				params.put("type", "CREDIT");
				params.put("currency", "CNY");
				
				String result = sendPost(API_URL + "/v1/fund-transfers", params);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( object.has("status") && object.getString("status").equals("PENDING")) {//PENDING, COMPLETED, CANCELLED.待定，已完成，已取消。
					
					String transferId = object.getString("id");
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("status", "COMPLETED");
					
					result = sendPUT(API_URL + "/v1/fund-transfers/"+transferId+"/status", jsonObject);
					System.out.println("完成交易调用结果："+result);
					object = JSONObject.fromObject(result);
					
					if( object.has("status") && object.getString("status").equals("COMPLETED") ) {
						
						return Enum_MSG.成功.message(transferId);//返回单号需要存储起来
					} else {
						return Enum_MSG.失败.message(object.getString("code"), "完成交易步骤失败："+object.getString("info"));
					}
					
				} else {
					return Enum_MSG.失败.message(object.getString("code"), "预备交易步骤失败："+object.getString("info"));
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

		String fromDateTime = "2017-06-10T00:00:00";//YYYY-MM-DDThh:mm:ss
		String toDateTime = "2017-06-27T19:00:00";//YYYY-MM-DDThh:mm:ss
		String size = "100";
		String page = "0";
		
		String url = API_URL.concat("/v1/game-transactions?").concat("from=").concat(fromDateTime).concat("&to=").concat(toDateTime).concat("&size=").concat(size).concat("&page=").concat(page);
		String result = getUrl( url );
		
		System.out.println("调用结果："+result);
		
		JSONObject object = JSONObject.fromObject(result);
		
		//接口调用成功
		if( !object.has("code") ) {
			
			int totalCount = object.getInt("totalCount");
			System.out.println("找到数据"+totalCount);
			JSONArray list = object.getJSONArray("items");
			if(totalCount > 0 ) {
				for (int i = 0; i < list.size(); i++) {
					JSONObject item = list.getJSONObject(i);
					System.out.println(item);
				}
			} 
			
		} else {
			return Enum_MSG.失败.message(object.getString("code"), "查询订单："+object.getString("info"));
		}
		
		return null;
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
	 * 获取订单接口
	 * @param entity 参数列表 transferId
	 * 
	 * 
	 * @return 返回结果 返回单号，使用的是QT平台的单号
	 */
	@Override
	public Object getOrder(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString()  );
		try{
			if(MapUtil.isNulls(entity, "transferId")){//QT平台的单号
				
				
				String result = getUrl(API_URL + "/v1/fund-transfers/"+entity.get("transferId") );
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( !object.has("code") ) {
					
					if( object.getString("status").equals("COMPLETED") ) {
						
						return Enum_MSG.成功.message("查询成功");
					} else {
						return Enum_MSG.失败.message( "状态为："+object.getString("status"));
					}
					
				} else {
					return Enum_MSG.失败.message(object.getString("code"), "查询订单："+object.getString("info"));
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
	 * @param entity 参数列表 playerId,deviceType,istrueplay,gameId,returnUrl
	 * 
	 * returnUrl
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object login(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString()  );
		try{
			if(MapUtil.isNulls(entity, "playerId,deviceType,istrueplay,gameId,returnUrl")){	
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度必须为20位，当前"+String.valueOf(entity.get("username")).length())+"位";
//				}
				
				String deviceType = String.valueOf(entity.get("deviceType"));
				String istrueplay = String.valueOf(entity.get("istrueplay"));
				String gameId = String.valueOf(entity.get("gameId"));
				
				//因创建用户接口也要用该接口，所以默认随意调用一个游戏
				if(gameId.equals("null")) {
					gameId = "QS-dragonshrine";
//					gameId = "OGS-1can2can";
				}
				
				JSONObject params = new JSONObject();
				params.put("playerId", entity.get("playerId"));
				params.put("gameId", gameId);
				params.put("country", "CN");
				params.put("currency", "CNY");
				params.put("lang", "zh_CN");
				params.put("mode", istrueplay.equals("true") ? "real" : "demo");//有效值为demo是“play for fun”，还是real玩真钱游戏。
				params.put("device", deviceType.equals("0") ? "desktop" : "mobile");//desktop or  mobile.
				params.put("returnUrl", entity.get("returnUrl"));//在移动端游戏中决定主页键的链接
				
					
				String result = sendPost(API_URL + "/v1/games/"+gameId+"/launch-url", params);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( object.has("url")) {
					
					return Enum_MSG.成功.message(object.getString("url"));
					
				} else {
					return Enum_MSG.失败.message(object.getString("code"), "登录游戏失败："+object.getString("info"));
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
	
	private String getUrl(String url) {
		//
		CloseableHttpClient httpClient = HttpClients.createDefault();
		int timeout = 10000;
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
		
		
		HttpGet request = new HttpGet(url);
		request.setConfig(requestConfig);
	    HttpResponse response=null;
	 
		try {
			
			JSONObject token = JSONObject.fromObject( getAccessToken() );
			String accesstoken = "";
			if(token.getString("code").equals("0")) {
				accesstoken = token.getString("info");
			} else {
				return token.toString();
			}
			
			request.addHeader("Authorization", "Bearer "+accesstoken);
			request.addHeader("Time-Zone", "Asia/Shanghai");
			
			/****调试信息***/
//			System.out.println("接口URL："+url);
//			Header[] headers = request.getAllHeaders();
//			System.out.println("请求Header数据：");
//			for (Header header : headers) {
//				System.out.println(header.getName()+"="+header.getValue());
//			}
//			System.out.println("请求方式："+request.getMethod());
			
			response = httpClient.execute(request);
			
			String result = EntityUtils.toString(response.getEntity());
			JSONObject resultObject = JSONObject.fromObject( result );
			
			if(resultObject.has("code")) {
				return Enum_MSG.失败.message(resultObject.getString("code"), resultObject.getString("info"));
			}
			
			return result;
			 
		} catch (IOException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}finally{
			
		}
	}
	
	private String getAccessToken() {
		String urls = this.API_URL.concat("/v1/auth/token?grant_type=password&response_type=token&username=").concat(this.agentname).concat("&password=").concat(this.agentpwd);
		System.out.println("get方式获取token="+urls);
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		int timeout = 10000;
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
		
		
		HttpPost request = new HttpPost(urls);
		request.setConfig(requestConfig);
	    HttpResponse response=null;
	 
		try {
			
			response = httpClient.execute(request);
			
			String result = EntityUtils.toString(response.getEntity());
			JSONObject jsonObject = JSONObject.fromObject(result);
			if(jsonObject.has("access_token")) {
				System.out.println("成功获取到TOKEN="+jsonObject.get("access_token"));
				return Enum_MSG.成功.message(jsonObject.get("access_token"));
			} else {
				return Enum_MSG.失败.message(jsonObject.get("code"));
			}
			 
		} catch (IOException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}finally{
			
		}
		
	}
	
	private String sendPost(String url,JSONObject jsonObject){
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		int timeout = 10000;
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
		
		
		HttpPost request = new HttpPost(url);
		request.setConfig(requestConfig);
	    HttpResponse response=null;
	 
		try {
			JSONObject token = JSONObject.fromObject( getAccessToken() );
			String accesstoken = "";
			if(token.getString("code").equals("0")) {
				accesstoken = token.getString("info");
			} else {
				return token.toString();
			}
			
			request.addHeader("Content-Type", "application/json");
			request.addHeader("Authorization", "Bearer "+accesstoken);
			
			StringEntity entity = new StringEntity(jsonObject.toString());
			request.setEntity(entity);
			
			/****调试信息***/
			System.out.println("接口URL："+url);
			Header[] headers = request.getAllHeaders();
			System.out.println("请求Header数据：");
			for (Header header : headers) {
				System.out.println(header.getName()+"="+header.getValue());
			}
			System.out.println("请求方式："+request.getMethod());
			System.out.println("json参数："+jsonObject);
			
			response = httpClient.execute(request);
			
			String result = EntityUtils.toString(response.getEntity());
			JSONObject resultObject = JSONObject.fromObject( result );
			
			if(resultObject.has("code")) {
				return Enum_MSG.失败.message(resultObject.getString("code"), resultObject.getString("code"));
			}
			
			return result;
			 
		} catch (IOException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}finally{
			
		}
	
	}
	
	private String sendPUT(String url,JSONObject jsonObject){
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		int timeout = 10000;
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
		
		
		HttpPut request = new HttpPut(url);
		request.setConfig(requestConfig);
	    HttpResponse response=null;
	 
		try {
			
			JSONObject token = JSONObject.fromObject( getAccessToken() );
			String accesstoken = "";
			if(token.getString("code").equals("0")) {
				accesstoken = token.getString("info");
			} else {
				return token.toString();
			}
			
			request.addHeader("Content-Type", "application/json");
			request.addHeader("Authorization", "Bearer "+accesstoken);
			
			StringEntity entity = new StringEntity(jsonObject.toString());
			request.setEntity(entity);
			
			/****调试信息***/
			System.out.println("接口URL："+url);
			Header[] headers = request.getAllHeaders();
			System.out.println("请求Header数据：");
			for (Header header : headers) {
				System.out.println(header.getName()+"="+header.getValue());
			}
			System.out.println("请求方式："+request.getMethod());
			System.out.println("json参数："+jsonObject);
			
			
			response = httpClient.execute(request);
			
			String result = EntityUtils.toString(response.getEntity());
			JSONObject resultObject = JSONObject.fromObject( result );
			
			if(resultObject.has("code")) {
				return Enum_MSG.失败.message(resultObject.getString("code"), resultObject.getString("info"));
			}
			
			return result;
			 
		} catch (IOException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}finally{
			
		}
	
	}

	public static void main(String[] args) throws Exception {
		QTGameAPI nhq = new QTGameAPI();
		Map<String, Object> entity = new HashMap<String, Object>();
//		entity.put("playerId", "EU9j7GvMU5ld0tAIIj5vZtMk07PO3F");
		entity.put("playerId", UUID.randomUUID().toString().substring(0,20));
		entity.put("password", "12345678");
		entity.put("istrueplay", "true");
//		System.out.println(nhq.createAccount(entity));//playerId,password,istrueplay
//		
//		
		entity.put("amount", "2");
		entity.put("referenceId", UUID.randomUUID().toString());
//		System.out.println(nhq.deposit(entity));//playerId,amount,referenceId
//		
//		entity.put("transferId", "58ec9df38542ff0001d475dd");
//		System.out.println(nhq.getOrder(entity));//transferId
//		
		System.out.println(nhq.getBalance(entity));//playerId
//		
		entity.put("amount", "2");
		entity.put("referenceId", UUID.randomUUID().toString());
//		System.out.println(nhq.withdraw(entity));//playerId,amount,referenceId
		
//		System.out.println(nhq.getBalance(entity));//playerId
		
//		entity.put("playerId", "5432e58f-4877-4543-9bfb-b1afd5c19fe1");
		System.out.println(nhq.getRecord(entity));
//		entity.put("returnUrl", "http://www.baidu.com");
//		System.out.println(nhq.login(entity));//playerId
	}
}
