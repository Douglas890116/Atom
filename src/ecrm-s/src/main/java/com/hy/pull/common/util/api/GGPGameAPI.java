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
import java.util.List;
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
import com.hy.pull.common.util.MoneyHelper;
import com.hy.pull.common.util.game.NHQGame;
import com.hy.pull.common.util.game.sa.DeEnCode;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.util.RandomString;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * GGPoker游戏接口
 * 
 * 注意：该平台是以美元为币种的
 * 
 * @author temdy
 */
public class GGPGameAPI implements BaseInterface {
	
	private String API_URL = "http://api.ggpoker.site/integration/api/site";
	private String siteId = "jintabet";
	private String SecretKey = "rt8bp2ecj4d";
//	private String mysite = "http://ggpoker.egg88.com/";
	
	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	/**
	 * 默认构造函数
	 */
	@Deprecated
	public GGPGameAPI(){
		
	}
	
	/**
	 * 构建函数，初始化参数
	 * @param API_URL API接口URL
	 * @param MD5_KEY MD5密钥
	 */
	public GGPGameAPI(String API_URL,String siteId,String SecretKey,String GAME_API_URL){
		this.API_URL = API_URL;
		this.siteId = siteId;
		this.SecretKey = SecretKey;
		this.GAME_API_URL = GAME_API_URL;
	}

	/**
	 * 创建用户接口
	 * @param entity 参数列表 username,password
	 * 
	 * playerId=最少30个字符
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString()  );
		try{
			if(MapUtil.isNulls(entity, "username,password")){	
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度必须为20位，当前"+String.valueOf(entity.get("username")).length())+"位";
//				}
				String str = this.siteId+entity.get("username").toString()+entity.get("password").toString()+this.SecretKey;
//				System.out.println("siteId = "+siteId);
//				System.out.println("username = "+entity.get("username"));
//				System.out.println("password = "+entity.get("password"));
//				System.out.println("MD5 original data = "+str);
				String fingerprint = DeEnCode.string2MD5(str);
//				System.out.println("MD5 encode data = "+fingerprint);
				
				String result = getUrl(API_URL + "/create?siteId="+this.siteId+"&username="+entity.get("username")+"&password="+entity.get("password")+"&fingerprint="+fingerprint);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( object.getString("code").equals("SUCCESS")) {
					
					return Enum_MSG.成功.message("创建成功");//
					
				} else {
					return Enum_MSG.失败.message(object.getString("code"), "创建玩家失败："+object.getString("info"));
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
	 * username 用户名
	 * @return 返回结果
	 */
	@Override
	public Object getBalance(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() );
		try{
			if(MapUtil.isNulls(entity, "username")){	
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度必须为20位，当前"+String.valueOf(entity.get("username")).length())+"位";
//				}
				String str = this.siteId+entity.get("username").toString()+this.SecretKey;
//				System.out.println("siteId = "+siteId);
//				System.out.println("username = "+entity.get("username"));
//				System.out.println("password = "+entity.get("password"));
//				System.out.println("MD5 original data = "+str);
				String fingerprint = DeEnCode.string2MD5(str);
//				System.out.println("MD5 encode data = "+fingerprint);
				
				
				String result = getUrl(API_URL + "/balance?siteId="+this.siteId+"&username="+entity.get("username")+"&fingerprint="+fingerprint);
//				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( object.has("amount")) {
					
					return Enum_MSG.成功.message(object.getString("amount"));//
					
				} else {
					return Enum_MSG.失败.message(object.getString("code"), "查询余额："+object.getString("info"));
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
			if(MapUtil.isNulls(entity, "username,amount,requestId")){	
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度必须为20位，当前"+String.valueOf(entity.get("username")).length())+"位";
//				}
				
				String str = this.siteId+entity.get("username").toString()+""+entity.get("amount")+""+entity.get("requestId")+this.SecretKey;
//				System.out.println("siteId = "+siteId);
//				System.out.println("username = "+entity.get("username"));
//				System.out.println("password = "+entity.get("password"));
//				System.out.println("MD5 original data = "+str);
				String fingerprint = DeEnCode.string2MD5(str);
//				System.out.println("MD5 encode data = "+fingerprint);
				
				
				String result = getUrl(API_URL + "/debit?siteId="+this.siteId+"&username="+entity.get("username")+"&amount="+entity.get("amount")+"&requestId="+entity.get("requestId")+"&fingerprint="+fingerprint);
//				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( object.has("balance")) {
					
					return Enum_MSG.成功.message(object.getString("balance"));//
					
				} else {
					return Enum_MSG.失败.message(object.getString("code"), "取款交易失败："+object.getString("info"));
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
	 * @param entity 参数列表 username,amount,requestId
	 * 
	 * requestId=最大56个字符
	 * 
	 * @return 返回结果 返回订单确认码，需要存储起来
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() );
		try{
			if(MapUtil.isNulls(entity, "username,amount,requestId")){	
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度必须为20位，当前"+String.valueOf(entity.get("username")).length())+"位";
//				}
				
				String str = this.siteId+entity.get("username").toString()+""+entity.get("amount")+""+entity.get("requestId")+this.SecretKey;
//				System.out.println("siteId = "+siteId);
//				System.out.println("username = "+entity.get("username"));
//				System.out.println("amount = "+entity.get("amount"));
//				System.out.println("requestId = "+entity.get("requestId"));
//				System.out.println("MD5 original data = "+str);
				String fingerprint = DeEnCode.string2MD5(str);
//				System.out.println("MD5 encode data = "+fingerprint);
				
				String result = getUrl(API_URL + "/credit?siteId="+this.siteId+"&username="+entity.get("username")+"&amount="+entity.get("amount")+"&requestId="+entity.get("requestId")+"&fingerprint="+fingerprint);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( object.has("balance")) {
					
					return Enum_MSG.成功.message(object.getString("balance"));//
					
				} else {
					return Enum_MSG.失败.message(object.getString("code"), "存款交易失败："+object.getString("info"));
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

		String fromDate = "2017-10-29";//yyyy-MM-dd
		String toDate = "2017-10-31";//yyyy-MM-dd
		
		String str = this.siteId+fromDate+""+toDate+this.SecretKey;
		String fingerprint = DeEnCode.string2MD5(str);
		
		String result = getUrl(API_URL + "/ggr?siteId="+this.siteId+"&fromDate="+fromDate+"&toDate="+toDate+"&fingerprint="+fingerprint);
		JSONObject jsonObject = JSONObject.fromObject(result);
		System.out.println("调用结果："+jsonObject.getJSONArray("data"));
		
		JSONArray array = jsonObject.getJSONArray("data");
		System.out.println(array.size());
		for (int i = 0; i < array.size(); i++) {
			JSONObject object = array.getJSONObject(i);
			System.out.println(object);
		}
		
		return null;
	}

	/**
	 * 更新信息接口
	 * @param entity 参数列表 username,newpassword
	 * @return 返回结果
	 */
	@Override
	public Object updateInfo(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString()  );
		try{
			if(MapUtil.isNulls(entity, "username,newpassword")){	
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度必须为20位，当前"+String.valueOf(entity.get("username")).length())+"位";
//				}
				String str = this.siteId+entity.get("username").toString()+entity.get("newpassword").toString()+this.SecretKey;
//				System.out.println("siteId = "+siteId);
//				System.out.println("username = "+entity.get("username"));
//				System.out.println("password = "+entity.get("password"));
//				System.out.println("MD5 original data = "+str);
				String fingerprint = DeEnCode.string2MD5(str);
//				System.out.println("MD5 encode data = "+fingerprint);
				
				String result = getUrl(API_URL + "/create?siteId="+this.siteId+"&username="+entity.get("username")+"&password="+entity.get("newpassword")+"&fingerprint="+fingerprint);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( object.getString("code").equals("SUCCESS")) {
					
					return Enum_MSG.成功.message("修改密码成功");//
					
				} else {
					return Enum_MSG.失败.message(object.getString("code"), "修改密码失败："+object.getString("info"));
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
	 * 获取订单接口
	 * @param entity 参数列表 requestId
	 * 
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object getOrder(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString()  );
		try{
			if(MapUtil.isNulls(entity, "requestId")){	
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度必须为20位，当前"+String.valueOf(entity.get("username")).length())+"位";
//				}
				
				String str = this.siteId+entity.get("requestId").toString()+this.SecretKey;
//				System.out.println("siteId = "+siteId);
//				System.out.println("username = "+entity.get("username"));
//				System.out.println("password = "+entity.get("password"));
//				System.out.println("MD5 original data = "+str);
				String fingerprint = DeEnCode.string2MD5(str);
//				System.out.println("MD5 encode data = "+fingerprint);
				
				String result = getUrl(API_URL + "/check?siteId="+this.siteId+"&requestId="+entity.get("requestId")+"&fingerprint="+fingerprint);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( object.has("amount")) {
					
					return Enum_MSG.成功.message(object.getString("amount"));//
					
				} else {
					return Enum_MSG.失败.message(object.getString("code"), "查询订单失败："+object.getString("info"));
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
	 * @param entity 参数列表 playerId,deviceType,istrueplay,gameId
	 * 
	 * returnUrl
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object login(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() );
		try{
			if(MapUtil.isNulls(entity, "username,password")){	
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度必须为20位，当前"+String.valueOf(entity.get("username")).length())+"位";
//				}
				
				
				String result = getUrl(API_URL + "/login?password="+entity.get("username")+"&username="+entity.get("username"));
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( object.getString("code").equals("SUCCESS")) {
					
					return Enum_MSG.成功.message(object.getString("balance"));//
					
				} else {
					return Enum_MSG.失败.message(object.getString("code"), "存款交易失败："+object.getString("info"));
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
			System.out.println("请求URL："+url);
			response = httpClient.execute(request);
			
			String result = EntityUtils.toString(response.getEntity());
			System.out.println("result="+result);
			JSONObject resultObject = JSONObject.fromObject( result );
			
			if(resultObject.has("code")) {
				if(resultObject.getString("code").equals("SUCCESS")) {
					return result;
				}
				return Enum_MSG.失败.message(resultObject.getString("code"), resultObject.getString("message"));
			}
			 
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}finally{
		}
	}
	

	public static void main(String[] args) throws Exception {
		GGPGameAPI nhq = new GGPGameAPI();
		Map<String, Object> entity = new HashMap<String, Object>();
		
//		entity.put("username", username);
//		entity.put("password", "123456");
		
		
		
//		System.out.println(nhq.createAccount(entity));//username,password

		/*
		entity.put("amount", "2");
		entity.put("requestId", UUID.randomUUID().toString());
		System.out.println(nhq.deposit(entity));//username,amount,requestId
		
		entity.put("amount", "1");
		entity.put("requestId", UUID.randomUUID().toString());
		System.out.println(nhq.withdraw(entity));//username,amount,requestId
		*/
		
		
//		entity.put("newpassword", RandomString.createRandomString(8));
//		System.out.println(nhq.updateInfo(entity));//username,newpassword
		
//		System.out.println(nhq.getOrder(entity));//transferId

		
//		System.out.println(nhq.getBalance(entity));//playerId
		
		
		System.out.println(nhq.getRecord(entity));
		
		
	}
}
