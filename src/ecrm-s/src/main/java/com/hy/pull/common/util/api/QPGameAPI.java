package com.hy.pull.common.util.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.hy.pull.common.util.Encrypt;
import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.game.NHQGame;
import com.hy.pull.common.util.game.qp.QPUtil;
import com.hy.pull.common.util.game.sa.DeEnCode;
import com.hy.pull.common.util.game.zj.ZJUtil;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;

import net.sf.json.JSONObject;


/**
 * 火星棋牌游戏接口
 * @author temdy
 */
public class QPGameAPI implements BaseInterface {
	
	private String serviceDomain = "http://diwangqp99.com";
	private String agentId = "1";
	 private String apiKey = "HY345YDLop#jkljl";
	
	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	
	
	/**
	 * 默认构造函数
	 */
	@Deprecated
	public QPGameAPI(){
		
	}
	
	/**
	 * 构建函数，初始化参数
	 * @param API_URL API接口URL
	 * @param MD5_KEY MD5密钥
	 */
	public QPGameAPI(String serviceDomain,String agentId,String apiKey,String GAME_API_URL){
		this.serviceDomain = serviceDomain;
		this.agentId = agentId;
		this.apiKey = apiKey;
		this.GAME_API_URL = GAME_API_URL;
	}

	/**
	 * 创建用户接口
	 * @param entity 参数列表 username,password
	 * username 用户名长度必须为15位
	 * password 密码 大于6小于20
	 * @return 返回结果
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString()  );
		try{
			if(MapUtil.isNulls(entity, "username,password")){	
				
				if( String.valueOf(entity.get("username")).length() > 15 ) {
					return Enum_MSG.参数错误.message("用户长度不能大于15位，当前"+String.valueOf(entity.get("username")).length())+"位";
				}
				if( String.valueOf(entity.get("password")).length() > 15 ) {
					return Enum_MSG.参数错误.message("用户长度不能大于15位，当前"+String.valueOf(entity.get("password")).length())+"位";
				}
				
				JSONObject params = new JSONObject();
				params.put("Account", entity.get("username"));
				params.put("LoginPass", entity.get("password"));
				
				System.out.println("加密原文："+params.toString());
				String data = QPUtil.Encrypt(params.toString(), this.apiKey);
				System.out.println("加密密文："+data);
				
				String url = this.serviceDomain + "/Service.ashx?a=" + this.agentId + "&m=RegisterUser";
				String result = doPostSubmit(url, data);
				System.out.println("调用结果："+result);
				
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , result );
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( !object.has("code") ) {
					
					if(object.getString("flag").equals("1")) {
						
						return Enum_MSG.成功.message(object.getString("content"));
					} else {
						
						if(object.getString("content") != null && object.getString("content").contains("存在")) {
							return Enum_MSG.账号已存在.message(object.getString("content"));
						} 
						
						return Enum_MSG.失败.message(object.getString("content"));
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
	 * @param entity 参数列表 username,password
	 * username 用户名
	 * @return 返回结果
	 */
	@Override
	public Object getBalance(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString()  );
		try{
			if(MapUtil.isNulls(entity, "username,password")){	
				
//				if( String.valueOf(entity.get("username")).length() != 15 ) {
//					return Enum_MSG.参数错误.message("用户名长度必须为15位，当前"+String.valueOf(entity.get("username")).length())+"位";
//				}
				
				
				System.out.println("加密原文："+entity.get("username"));
				String data = QPUtil.Encrypt(entity.get("username").toString(), this.apiKey);
				System.out.println("加密密文："+data);
				
				String url = this.serviceDomain + "/Service.ashx?a=" + this.agentId + "&m=GetCredit";
				String result = doPostSubmit(url, data);
				System.out.println("调用结果："+result);
				
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , result );
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( !object.has("code") ) {
					
					if(object.getString("flag").equals("1")) {
						
//						Score：游戏积分可用于游戏的积分
//						InsureScore：保险柜积分
						String content = object.getString("content");
						content = content.replaceAll("\\\\", "");
						object = JSONObject.fromObject(content);
						
						double amount = Double.valueOf(object.getString("Score"))+Double.valueOf(object.getString("InsureScore"));
						
						return Enum_MSG.成功.message(amount+"");
					} else {
						return Enum_MSG.失败.message(object.getString("content"));
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
	 * 下分（取款）的接口
	 * @param entity 参数列表 username,money,orderID
	 * 
	 * username 用户名
	 * money 分数
	 * orderID=32长度
	 * @return 返回结果 返回订单确认码，需要存储起来
	 */
	@Override
	public Object withdraw(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() );
		try{
			if(MapUtil.isNulls(entity, "username,money,orderID")){	
				
//				if( String.valueOf(entity.get("username")).length() != 15 ) {
//					return Enum_MSG.参数错误.message("用户名长度必须为15位，当前"+String.valueOf(entity.get("username")).length())+"位";
//				}
				if( String.valueOf(entity.get("orderID")).length() != 32 ) {
					return Enum_MSG.参数错误.message("订单号长度必须为32位，当前"+String.valueOf(entity.get("orderID")).length())+"位";
				}
				
				JSONObject params = new JSONObject();
				params.put("Account", entity.get("username"));
				params.put("Credit",  - Double.valueOf(entity.get("money").toString()));
				params.put("OrderID", entity.get("orderID"));
				
				String data = QPUtil.Encrypt(params.toString(), this.apiKey);
				System.out.println("加密原文："+params.toString());
				System.out.println("加密密文："+data);
				
				String url = this.serviceDomain + "/Service.ashx?a=" + this.agentId + "&m=WithDraw";
				String result = doPostSubmit(url, data);
				System.out.println("调用结果："+result);
				
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() , result );
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( !object.has("code") ) {
					
					if(object.getString("flag").equals("1")) {
						
						return Enum_MSG.成功.message(object.getString("content"));
					} else {
						return Enum_MSG.失败.message(object.getString("content"));
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
	 * 上分（存款）的接口
	 * @param entity 参数列表 username,money,orderID
	 * username 用户名
	 * money 分数
	 * orderID=32位数
	 * @return 返回结果 返回订单确认码，需要存储起来
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() );
		try{
			if(MapUtil.isNulls(entity, "username,money,orderID")){	
				
//				if( String.valueOf(entity.get("username")).length() != 15 ) {
//					return Enum_MSG.参数错误.message("用户名长度必须为15位，当前"+String.valueOf(entity.get("username")).length())+"位";
//				}
				if( String.valueOf(entity.get("orderID")).length() != 32 ) {
					return Enum_MSG.参数错误.message("订单号长度必须为32位，当前"+String.valueOf(entity.get("orderID")).length())+"位";
				}
				
				JSONObject params = new JSONObject();
				params.put("Account", entity.get("username"));
				params.put("Credit", entity.get("money"));
				params.put("OrderID", entity.get("orderID"));
				
				String data = QPUtil.Encrypt(params.toString(), this.apiKey);
				System.out.println("加密原文："+params.toString());
				System.out.println("加密密文："+data);
				String url = this.serviceDomain + "/Service.ashx?a=" + this.agentId + "&m=Deposit";
				String result = doPostSubmit(url, data);
				System.out.println("调用结果："+result);
				
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() , result );
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( !object.has("code") ) {
					
					if(object.getString("flag").equals("1")) {
						
						return Enum_MSG.成功.message(object.getString("content"));
					} else {
						return Enum_MSG.失败.message(object.getString("content"));
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
	 * 获取游戏结果
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	@Override
	public Object getRecord(Map<String, Object> entity) {
		try{
//			if(MapUtil.isNulls(entity, "username,password")){	
				
//				if( String.valueOf(entity.get("username")).length() != 15 ) {
//					return Enum_MSG.参数错误.message("用户名长度必须为15位，当前"+String.valueOf(entity.get("username")).length())+"位";
//				}
//				if( String.valueOf(entity.get("password")).length() != 8 ) {
//					return Enum_MSG.参数错误.message("用户名长度必须为8位，当前"+String.valueOf(entity.get("password")).length())+"位";
//				}
				
				JSONObject params = new JSONObject();
				params.put("AgentId", this.agentId);
				//起止时间之间不能超过300000分钟
				params.put("StartTime",  "2017-06-07 10:00:00");
				params.put("EndTime",    "2017-06-08 14:00:00");
				
				System.out.println("加密原文："+params.toString());
				String data = QPUtil.Encrypt(params.toString(), this.apiKey);
				System.out.println("加密密文："+data);
				
				String url = this.serviceDomain + "/Service.ashx?a=" + this.agentId + "&m=GetGameRecord";
				String result = doPostSubmit(url, data);
				System.out.println("调用结果："+result);
				
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( !object.has("code") ) {
					
					if(object.getString("flag").equals("1")) {
						
						return Enum_MSG.成功.message(object.getString("content"));
					} else {
						return Enum_MSG.失败.message(object.getString("content"));
					}
					
				} else {//网络异常
					return object.toString();
				}
				
				
			
		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
	}

	/**
	 * 更新信息接口（修改密码）
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	@Override
	public Object updateInfo(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString() );
		try{
			if(MapUtil.isNulls(entity, "username,password,newpassword")){	
				
//				if( String.valueOf(entity.get("username")).length() != 15 ) {
//					return Enum_MSG.参数错误.message("用户名长度必须为15位，当前"+String.valueOf(entity.get("username")).length())+"位";
//				}
				
				JSONObject params = new JSONObject();
				params.put("Account", entity.get("username"));
				params.put("LoginPass", entity.get("newpassword"));
				
				String data = QPUtil.Encrypt(params.toString(), this.apiKey);
				System.out.println("加密原文："+params.toString());
				System.out.println("加密密文："+data);
				
				String url = this.serviceDomain + "/Service.ashx?a=" + this.agentId + "&m=SetPassword";
				String result = doPostSubmit(url, data);
				System.out.println("调用结果："+result);
				
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString() , result );
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( !object.has("code") ) {
					
					if(object.getString("flag").equals("1")) {
						
						return Enum_MSG.成功.message(object.getString("content"));
					} else {
						return Enum_MSG.失败.message(object.getString("content"));
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
	 * 获取订单接口
	 * @param entity 参数列表 username,orderID
	 * 
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object getOrder(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString()  );
		try{
			if(MapUtil.isNulls(entity, "username,orderID")){	
				
				JSONObject params = new JSONObject();
				params.put("Account", entity.get("username"));
				params.put("OrderID", entity.get("orderID"));
				
				String data = QPUtil.Encrypt(params.toString(), this.apiKey);
				System.out.println("加密原文："+params.toString());
				System.out.println("加密密文："+data);
				String url = this.serviceDomain + "/Service.ashx?a=" + this.agentId + "&m=CheckOrder";
				String result = doPostSubmit(url, data);
				System.out.println("调用结果："+result);
				
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() , result );
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( !object.has("code") ) {
					
					if(object.getString("flag").equals("1")) {
						
						return Enum_MSG.成功.message(object.getString("content"));
					} else {
						return Enum_MSG.失败.message(object.getString("content"));
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
	 * @param entity 参数列表
	 * username 用户名
	 * password 密码
	 * lang 语言选择
	 * @return 返回结果
	 */
	@Override
	public Object login(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString()  );
		return Enum_MSG.成功.message("棋牌无登录业务，不存在web版本或H5版本的登录地址");
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
	
	private static String doPostSubmit(String httpUrl,String data) {
        try {
        	
        	CloseableHttpClient httpClient = HttpClients.createDefault();
    		int timeout = 10000;
    		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
    		
    		
        	HttpPost httpRequst = new HttpPost(httpUrl);//创建HttpPost对象  
        	httpRequst.setConfig(requestConfig);
            //填入各个表单域的�?
        	List paramsxx = new ArrayList<BasicNameValuePair>(); 
        	
        	BasicNameValuePair xxx = new BasicNameValuePair("d",data);
			paramsxx.add(xxx);
    		
        	UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(paramsxx,HTTP.UTF_8);
    		httpRequst.setEntity(encodedFormEntity);
    		
    		System.out.println("请求URL："+httpUrl);
    		System.out.println("请求data："+data);
    		
            HttpResponse httpResponse = httpClient.execute(httpRequst);  
            if(httpResponse.getStatusLine().getStatusCode() == 200)  {
            	String strResult = EntityUtils.toString(httpResponse.getEntity());
            	return strResult;
            } else {
            	return httpResponse.getStatusLine().getStatusCode()+"";
            }
		} catch (Exception e) {
			e.printStackTrace();
			return -1+"";
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		QPGameAPI nhq = new QPGameAPI();
		/*
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("username", "acc_info_test2");
//		entity.put("username", RandomString.createRandomString(15));
		entity.put("password", "888999*1^");
//		entity.put("newpassword", "123456789");
		entity.put("money", "2");
		
		entity.put("type", "confirmwithdraw");
		entity.put("deviceType", "0");
//		System.out.println(nhq.createAccount(entity));
		
		entity.put("orderID", RandomString.createRandomString(32));
//		System.out.println(nhq.deposit(entity));
//		
		entity.put("orderID", RandomString.createRandomString(32));
		System.out.println(nhq.getBalance(entity));
//		System.out.println(nhq.withdraw(entity));
//		System.out.println(nhq.getBalance(entity));
		
//		System.out.println(nhq.getOrder(entity));//type=上分或者下分类型，confirmdeposit=上分确认，confirmwithdraw=下分确认
		
//		System.out.println(nhq.login(entity));
//		System.out.println(nhq.getRecord(entity));
//		System.out.println(nhq.updateInfo(entity));
		*/
		test2();
	}
	
	private static void test2() {
		String str1 = doPostSubmit("http://diwangqp99.com/Service.ashx?a=1&m=GetCredit", "p3Uc/W18MtiecRtfPYcMwA==");
		String str2 = doPostSubmit("http://diwangqp99.com/Service.ashx?a=1&m=GetCredit", "p3Uc/W18MtiecRtfPYcMwA==");
		System.out.println(str1);
		System.out.println(str2);
	}
	
	private static void test() {
		String apiKey = "HY345YDLop#jkljl";
		JSONObject params = new JSONObject();
		params.put("Account", "dw2230736b81w8g");
		params.put("OptType", "login");
		
		try {
			System.out.println("加密原文："+params.toString());
			String data = QPUtil.Encrypt(params.toString(), apiKey);
			System.out.println("加密密文："+data);
			System.out.println("加密密文url encode："+URLEncoder.encode(data));
			
			System.out.println("解密密文："+QPUtil.Decrypt("dUwUOfTvwv20BUcz+8ED8w==", apiKey));
			
			System.out.println("加密密文123456789："+QPUtil.Encrypt("123456789", apiKey));
			
			System.out.println("解密密文："+QPUtil.Decrypt("eQQNtxE1O3G/HhkMSC/eDYRgkp4vd7lVHKBo6Pv0+EF8SP2sw34xv+iEh2ep3Nkb", apiKey));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
