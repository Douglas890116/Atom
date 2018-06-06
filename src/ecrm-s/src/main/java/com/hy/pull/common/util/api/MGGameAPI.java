package com.hy.pull.common.util.api;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hy.pull.common.util.Encrypt;
import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.game.mg.MGUtil;
import com.hy.pull.common.util.game.sa.XmlUtil;
import com.hy.pull.common.util.game.ttg.TTGUtil;
import com.hy.pull.common.util.game.zj.ZJUtil;
import com.maven.config.SystemConstant;
import com.maven.util.RandomStringNum;
import com.maven.utility.DateUtil;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;

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
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpParamConfig;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;


/**
 * MG游戏接口
 * @author temdy
 */
public class MGGameAPI implements BaseInterface {

//	private String API_URL = "https://ag.adminserv88.com/";
	
	private String HOST = "ag.adminserv88.com";
	private String P_USM = "HYRCNYA";
	private String P_PWD = "axBzXGY7SnEKQYqx";
	private String HOR_ID = "110424823";
	
	private String apiusername = "apiadmin";
	private String apipassword = "apipassword";
	private String partnerId = "HYR";
	
	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	
//	以下几个也是参数，但不通过构造进来
//	private String crId;
//	private String crType;
//	private String neId;
//	private String neType;
//	private String tarType;
	
	
	
	/**
	 * 默认构造函数
	 * 
	 * 不传递时使用默认的进行测试，正式时需要传递参数进来，此方法不使用
	 */
	@Deprecated
	public MGGameAPI() {
		
	}
	/**
	 * 构建函数，初始化参数
	 * @param API_URL 接口URL
	 * @param UserName
	 * @param Password  
	 */
	public MGGameAPI(String HOST, String P_USM, String P_PWD , String apiusername, String apipassword, String partnerId,String HOR_ID, String GAME_API_URL) {
		this.HOST = HOST;
		this.P_USM = P_USM;
		this.P_PWD = P_PWD;
		this.HOR_ID = HOR_ID;
		
		this.apiusername = apiusername;
		this.apipassword = apipassword;
		this.partnerId = partnerId;
		this.GAME_API_URL = GAME_API_URL;
	}
	
	
	/**
	 * 创建用户接口
	 * @param entity 参数列表 username,password,confirmPassword,crId,crType,neId,neType,tarType
	 * 
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString()  );
//		/lps/secure/network/ {id}/downline  
		
		try{
			
//			返回示例
//			{"request":null,"errorCode":0,"errorMessage":null,"logId":1479892642922,"params":{"link":"http://live.cg8080.com/liveflash1/route.jsp?code=78a6e02d-c4c3-4496-8176-8df141d89036&lc=cn&line=0&prefix=0"}}

			
			if(MapUtil.isNulls(entity, "username,password,confirmPassword,crId,crType,neId,neType,tarType")){
				
				if( String.valueOf(entity.get("username")).length() > 20 ) {
					return Enum_MSG.参数错误.message("用户长度不能大于20位");
				}
				if( String.valueOf(entity.get("password")).length() > 20 ) {
					return Enum_MSG.参数错误.message("密码长度不能大于20位");
				}
				
				String token = getToken();
				JSONObject objecttoken =  JSONObject.fromObject( token );
				if(objecttoken.has("token")) {
					token = objecttoken.getString("token");
				} else {
					return Enum_MSG.参数错误.message("未能获取到TOKEN");
				}
				
				CloseableHttpClient httpClient = HttpClients.createDefault();
				int timeout = 10000;
				RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
				
				
				HttpPut request = new HttpPut("https://".concat(HOST).concat("/lps/secure/network/").concat(entity.get("neId").toString()).concat("/downline") );
				request.setConfig(requestConfig);
				
				request.addHeader("X-Requested-With", "X-Api-Client");
				request.addHeader("X-Api-Call", "X-Api-Client");
				request.addHeader("X-Api-Auth", token);   //跟据文档2.1　首先获取token
				request.addHeader("Content-Type", "application/json");
				HttpResponse response=null;
				/**
				System.out.println(request.getMethod());
				System.out.println(request.toString());
				for (Header xxxx : request.getAllHeaders()) {
					System.out.println(xxxx.getName() + " = "+xxxx.getValue());
				}
				*/
				JSONObject params = new JSONObject();
				params.put("username", entity.get("username"));
				params.put("name", entity.get("username"));
				params.put("password", entity.get("password").toString());
				params.put("currency", entity.get("currency") == null ? "CNY" : entity.get("currency"));
				params.put("language",  entity.get("language") == null ? "CN" : entity.get("language"));
				params.put("confirmPassword", entity.get("confirmPassword"));
				
				params.put("crId", entity.get("crId"));//Your network id .
				params.put("crType", entity.get("crType"));//Your network type.  
				params.put("neId", entity.get("neId"));//The member's parent ID . 
				params.put("neType", entity.get("neType"));//The member's parent type.  
				params.put("tarType", entity.get("tarType"));
				
				JSONObject casino = new JSONObject();
				casino.put("enable", true);
				params.put("casino", casino);
				
				JSONObject poker = new JSONObject();
				poker.put("enable", false);
				params.put("poker", poker);
//				System.out.println(params);
				
				//crType　常量为A
				//neType 常量为A
				StringEntity param =new StringEntity(params.toString());
				request.setEntity(param);
				response = httpClient.execute(request);
				String result = EntityUtils.toString(response.getEntity());
						
//				String result = ZJUtil.getPost(API_URL.concat("/lps/secure/network/").concat(entity.get("neId").toString()).concat("/downline") , params);
				
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , result );
				
				JSONObject object =  JSONObject.fromObject( result );
				
				//接口调用成功
				if( object.has("code") ) {
					
					if(object.getString("success").equals("true")) {
						
						return Enum_MSG.成功.message("创建成功");
					} else {
						if(object.getString("message").contains("existed")) {
							return Enum_MSG.账号已存在.message(object.getString("message"));
						}
						return Enum_MSG.失败.message(object.getString("message"));
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
	 * @return 返回结果
	 */
	@Override
	public Object getBalance(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString()  );
		try{
			
//			返回示例

			
			if(MapUtil.isNulls(entity, "username,password")){
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度应为20个字符");
//				}
//				if( String.valueOf(entity.get("password")).length() != 8 ) {
//					return Enum_MSG.参数错误.message("用户登录密码长度应为8个字符");
//				}
				
				String token = getTokenByUserLogin(entity);
				JSONObject objecttoken =  JSONObject.fromObject( token );
				if(objecttoken.get("code").equals("0")) {
					token = objecttoken.getString("info");
				} else {
					return token;//直接返回错误
				}
				
				StringBuffer xmlData = new StringBuffer();
				xmlData.append(XML_HEADER);
				xmlData.append("<mbrapi-account-call");
				xmlData.append(" timestamp=\"").append(MGUtil.getCurrenDateUTC()).append("\"");
				xmlData.append(" apiusername=\"").append(this.apiusername).append("\"");
				xmlData.append(" apipassword=\"").append(this.apipassword).append("\"");
				xmlData.append(" token=\"").append(token).append("\"");
				xmlData.append("/>");
				
				TTGUtil ttgUtil = new TTGUtil();
				String result = ttgUtil.sendXMLDataByPost("https://".concat(HOST).concat("/member-api-web/member-api") , xmlData.toString());
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , result );
				
				Map<String, String> item = XmlUtil.getQueryParamsMGbalance(result);
				
//				System.out.println(item);
				
				if(item.containsKey("cash-balance")) {
					return Enum_MSG.成功.message(item.get("cash-balance"));
				} else {
					return Enum_MSG.失败.message("获取token失败");
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
	 * @param entity 参数列表 username,password,orderid,amount
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

			
			if(MapUtil.isNulls(entity, "username,password,orderid,amount")){
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度应为20个字符");
//				}
//				if( String.valueOf(entity.get("password")).length() != 8 ) {
//					return Enum_MSG.参数错误.message("用户登录密码长度应为8个字符");
//				}
				if( String.valueOf(entity.get("orderid")).length() != 20 ) {
					return Enum_MSG.参数错误.message("订单号长度应为20个字符");
				}
				if( Double.valueOf(entity.get("amount").toString()) < 1.0 ) {
					return Enum_MSG.参数错误.message("取款金额不能小于1元");
				}
				
				String token = getTokenByUserLogin(entity);
				JSONObject objecttoken =  JSONObject.fromObject( token );
				if(objecttoken.get("code").equals("0")) {
					token = objecttoken.getString("info");
				} else {
					return token;//直接返回错误
				}
				
				StringBuffer xmlData = new StringBuffer();
				xmlData.append(XML_HEADER);
				xmlData.append("<mbrapi-changecredit-call");
				xmlData.append(" timestamp=\"").append(MGUtil.getCurrenDateUTC()).append("\"");
				xmlData.append(" apiusername=\"").append(this.apiusername).append("\"");
				xmlData.append(" apipassword=\"").append(this.apipassword).append("\"");
				xmlData.append(" token=\"").append(token).append("\"");
				
				xmlData.append(" product=\"").append("casino").append("\"");
				xmlData.append(" operation=\"").append("withdraw").append("\"");// The operation to be perform ["topup", "withdraw"] 
				xmlData.append(" amount=\"").append(entity.get("amount")).append("\"");
				xmlData.append(" tx-id=\"").append(entity.get("orderid")).append("\"");
				
				xmlData.append("/>");
				
				TTGUtil ttgUtil = new TTGUtil();
				String result = ttgUtil.sendXMLDataByPost("https://".concat(HOST).concat("/member-api-web/member-api") , xmlData.toString());
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() , result );
				Map<String, String> item = XmlUtil.getQueryParamsByMG(result);

				if(item.get("status").equals("0")) {
					return Enum_MSG.成功.message("操作成功");
				} else {
					return Enum_MSG.失败.message(item.get("status"), "操作失败，请查看状态码");
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
	 * @param entity 参数列表 username,password,orderid,amount
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

			
			if(MapUtil.isNulls(entity, "username,password,orderid,amount")){
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度应为20个字符");
//				}
//				if( String.valueOf(entity.get("password")).length() != 8 ) {
//					return Enum_MSG.参数错误.message("用户登录密码长度应为8个字符");
//				}
				if( String.valueOf(entity.get("orderid")).length() != 20 ) {
					return Enum_MSG.参数错误.message("订单号长度应为20个字符");
				}
				
				String token = getTokenByUserLogin(entity);
				JSONObject objecttoken =  JSONObject.fromObject( token );
				if(objecttoken.get("code").equals("0")) {
					token = objecttoken.getString("info");
				} else {
					return token;//直接返回错误
				}
				
				StringBuffer xmlData = new StringBuffer();
				xmlData.append(XML_HEADER);
				xmlData.append("<mbrapi-changecredit-call");
				xmlData.append(" timestamp=\"").append(MGUtil.getCurrenDateUTC()).append("\"");
				xmlData.append(" apiusername=\"").append(this.apiusername).append("\"");
				xmlData.append(" apipassword=\"").append(this.apipassword).append("\"");
				xmlData.append(" token=\"").append(token).append("\"");
				
				xmlData.append(" product=\"").append("casino").append("\"");
				xmlData.append(" operation=\"").append("topup").append("\"");// The operation to be perform ["topup", "withdraw"] 
				xmlData.append(" amount=\"").append(entity.get("amount")).append("\"");
				xmlData.append(" tx-id=\"").append(entity.get("orderid")).append("\"");
				
				xmlData.append("/>");
				
				TTGUtil ttgUtil = new TTGUtil();
				String result = ttgUtil.sendXMLDataByPost("https://".concat(HOST).concat("/member-api-web/member-api") , xmlData.toString());
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() , result );
				Map<String, String> item = XmlUtil.getQueryParamsByMG(result);

				if(item.get("status").equals("0")) {
					return Enum_MSG.成功.message("操作成功");
				} else {
					return Enum_MSG.失败.message(item.get("status"), "操作失败，请查看状态码");
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
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 更新信息接口
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	@Override
	public Object updateInfo(Map<String, Object> entity) {

		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString()  );

		try{
			
//			返回示例

			
			if(MapUtil.isNulls(entity, "username,password")){
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度应为20个字符");
//				}
//				if( String.valueOf(entity.get("password")).length() != 8 ) {
//					return Enum_MSG.参数错误.message("用户登录密码长度应为8个字符");
//				}
				
				JSONObject params = new JSONObject();
				params.put("account_id", entity.get("username"));
				params.put("password", entity.get("password"));
				
				
				String result = sendPUT("https://".concat(HOST).concat("/v1/account/member/password") , params);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString() , result );
				Map<String, String> item = XmlUtil.getQueryParamsByMG(result);

				if(item.get("status").equals("0")) {
					return Enum_MSG.成功.message("操作成功");
				} else {
					return Enum_MSG.失败.message(item.get("status"), "操作失败，请查看状态码");
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
	 * 获取订单接口 username,password
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	@Deprecated
	public Object getOrder(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString()  );
		try{
			
//			返回示例
//			
			
//			start=end=yyyy:MM:dd:HH
//			http://${host}/lps/secure/hortx/${id}?start=${startTime}&end=${endTime}&timezone=${timezone}
				
				
			if(MapUtil.isNulls(entity, "username,password")){
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度应为20个字符");
//				}
//				if( String.valueOf(entity.get("password")).length() != 8 ) {
//					return Enum_MSG.参数错误.message("用户登录密码长度应为8个字符");
//				}
				
				String token = getToken();
				JSONObject objecttoken =  JSONObject.fromObject( token );
				if(objecttoken.has("token")) {
					token = objecttoken.getString("token");
				} else {
					return Enum_MSG.参数错误.message("未能获取到TOKEN");
				}
				System.out.println("获取token："+token);
				
				//只检索当天与昨天的
				Date now = new Date();
				String startTime = parseDate(DateUtil.add(now, Calendar.HOUR_OF_DAY, -1), "yyyy:MM:dd:HH:mm") ;
				String endTime = parseDate(now, "yyyy:MM:dd:HH:mm") ;
				String timezone = "Asia/Shanghai";//上海时区
				String url = String.format("http://"+"%s/lps/secure/agenttx/%s?start=%s&end=%s&timezone=%s", 
						this.HOST, this.HOR_ID, startTime, endTime, timezone);
				
				String result = getUrl(url, token);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() , result );
				
				//空数据
				if(result.equals("[]")) {
					return Enum_MSG.失败.message("没有找到数据");
				}
				
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( object.has("code") ) {
					
					if(object.getString("code").equals("1")) {
						
						return Enum_MSG.成功.message(object.getString("info"));
					} else {
						return Enum_MSG.失败.message(object.getString("code"), object.getString("info"));
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
	private static String getUrl(String urls,String token) {
		try {
			
			Response response =  
			                      Request.Get(urls) 
			                                     .addHeader("X-Requested-With", "X-Api-Client") 
			                                    .addHeader("X-Api-Call", "X-Api-Client") 
			                                    .addHeader("X-Api-Auth", token).connectTimeout(10000).socketTimeout(10000)  
			                                    .execute();  
			 
			HttpResponse hRes = response.returnResponse(); 
			if (hRes.getStatusLine().getStatusCode() == 200) {  
			    return IOUtils.toString(hRes.getEntity().getContent()); 
			} else if (hRes.getStatusLine().getStatusCode() == 401) { 
			    return Enum_MSG.接口调用错误.message("Unauthorized : " + hRes.getStatusLine().getStatusCode());
			} 
			
		} catch (IOException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 把日期转换成指定格式的日期字符串
	 * @Method parse
	 * @param  @param date【需要转换的日期】
	 * @param  @param format【合法的日期时间格式如：yyyy-MM-dd、yyyy-MM-dd HH：mm：ss、yyyy年MM月dd、yyyy/MM/dd HH：mm：ss等】
	 * @param  @return
	 * @return String
	 * @throws
	 */
	private static String parseDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String temp = "";
		if(date!=null){
			try {
				temp = sdf.format(date);
			} catch (Exception e) {
				System.out.println(date + "不是【" + format + "】格式的日期！");
			}
		}
		return temp;
	}
	
	/**
	 * 登录的接口
	 * @param entity 参数列表 username,password,gameId,deviceType
	 * @return 返回结果
	 */
	@Override
	public Object login(Map<String, Object> entity) {

		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString()  );
		try{
			
//			返回示例

			
			if(MapUtil.isNulls(entity, "username,password,deviceType,playtype")){
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度应为20个字符");
//				}
//				if( String.valueOf(entity.get("password")).length() != 8 ) {
//					return Enum_MSG.参数错误.message("用户登录密码长度应为8个字符");
//				}
				String deviceType = String.valueOf(entity.get("deviceType"));
				if( !deviceType.equals("0") && !deviceType.equals("1")) {
					return Enum_MSG.参数错误.message("MG游戏目前只能接受PC端与手机H5端的请求，即设备类型为0和1");
				}
				String playtype = String.valueOf(entity.get("playtype"));
				if( !playtype.equals("DZ") && !playtype.equals("SX")) {
					return Enum_MSG.参数错误.message("MG游戏目前可以接受SX和DZ大类游戏");
				}
				
				String token = getTokenByUserLogin(entity);
				JSONObject objecttoken =  JSONObject.fromObject( token );
				if(objecttoken.get("code").equals("0")) {
					token = objecttoken.getString("info");
				} else {
					return token;//直接返回错误
				}
				
				//请求登录地址
				StringBuffer xmlData = new StringBuffer();
				xmlData.append(XML_HEADER);
				xmlData.append("<mbrapi-launchurl-call ");
				xmlData.append(" timestamp=\"").append(MGUtil.getCurrenDateUTC()).append("\"");
				xmlData.append(" apiusername=\"").append(this.apiusername).append("\"");
				xmlData.append(" apipassword=\"").append(this.apipassword).append("\"");
				xmlData.append(" token=\"").append(token).append("\"");
				xmlData.append(" language=\"").append("zh").append("\"");
				
				//如果是视讯，则默认一个游戏
				if(playtype.equals("SX")) {
					String gameId = "35219";//web版本的 21点多人Playboy桌
					if(deviceType.equals("1")) {
						gameId = "59627";//h5版本的百家乐Playboy桌
					}
					//如果有传递游戏代码进来
					if(entity.containsKey("gameId")) {
						gameId = entity.get("gameId").toString();
					}

					xmlData.append(" gameId=\"").append(gameId).append("\"");
				} else {
					//如果有传递游戏代码进来
					String gameId = "66914";//web版本的  迷失拉斯维加斯
					if(deviceType.equals("1")) {
						gameId = "66916";//h5版本的迷失拉斯维加斯
					}
					//如果有传递游戏代码进来
					if(entity.containsKey("gameId")) {
						gameId = entity.get("gameId").toString();
					}
					xmlData.append(" gameId=\"").append(gameId).append("\"");
				}
//				System.out.println(playtype);
//				System.out.println(xmlData);
				
				String apiroot = this.GAME_API_URL;
				
				xmlData.append(" bankingUrl=\"").append(entity.get("depositurl")).append("\"");//充值页面
				xmlData.append(" lobbyUrl=\"").append(entity.get("homeurl")).append("\"");//大厅页面
				xmlData.append(" logoutRedirectUrl=\"").append(entity.get("homeurl")).append("\"");//登出跳转页面
				xmlData.append(" demoMode=\"").append("false").append("\"");//试玩模式
				xmlData.append("/>");
				
				TTGUtil ttgUtil = new TTGUtil();
				String result = ttgUtil.sendXMLDataByPost("https://".concat(HOST).concat("/member-api-web/member-api") , xmlData.toString());
				System.out.println("MG登录调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() , result );
				
				Map<String, String> item = XmlUtil.getQueryParamsByMG(result);
				System.out.println("MG登录调用结果："+item);
				
				if(item.get("status").equals("0")) {
					System.out.println("MG登录调用结果："+item.get("launchUrl"));
					return Enum_MSG.成功.message(item.get("launchUrl"));
				} else {
					return Enum_MSG.失败.message(item.get("status"), "操作失败，请查看状态码");
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
	private static String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	private String getTokenByUserLogin(Map<String, Object> entity) {
		
//		<mbrapi -login-call   
//	    timestamp="2012 -10-10 06:28:22.965 UTC"  
//	    	    apiusername="api-username"  
//	    	    apipassword="api -password"  
//	    	    username="username of member" 
//	    	    password="password of the member" 
//	    	    ipaddress="192.168.77.77" 
//	    	    partnerId="HO " 
//	    	    currencyCode="USD" 
//	    	/>
		
		try{
			
//			返回示例
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getTokenByUserLogin", entity.toString()  );
			
			if(MapUtil.isNulls(entity, "username,password")){
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度应为20个字符");
//				}
//				if( String.valueOf(entity.get("password")).length() != 8 ) {
//					return Enum_MSG.参数错误.message("用户登录密码长度应为8个字符");
//				}
				
				StringBuffer xmlData = new StringBuffer();
				xmlData.append(XML_HEADER);
				xmlData.append("<mbrapi-login-call");
				xmlData.append(" timestamp=\"").append(MGUtil.getCurrenDateUTC()).append("\"");
				xmlData.append(" apiusername=\"").append(this.apiusername).append("\"");
				xmlData.append(" apipassword=\"").append(this.apipassword).append("\"");
				xmlData.append(" username=\"").append(entity.get("username")).append("\"");
				xmlData.append(" password=\"").append(entity.get("password")).append("\"");
				xmlData.append(" ipaddress=\"").append("192.168.77.77").append("\"");
				xmlData.append(" partnerId=\"").append("HY").append("\"");//写死的？
				xmlData.append(" currencyCode=\"").append("CNY").append("\"");
				xmlData.append("/>");
				
				System.out.println(xmlData);
				
				TTGUtil ttgUtil = new TTGUtil();
				String result = ttgUtil.sendXMLDataByPost("https://".concat(HOST).concat("/member-api-web/member-api") , xmlData.toString());
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getTokenByUserLogin", entity.toString() , result );
				System.out.println("mbrapi-login-call结果："+result);
				Map<String, String> item = XmlUtil.getQueryParamsByMG(result);
//				System.out.println(item);
				if(item.containsKey("token")) {
					return Enum_MSG.成功.message(item.get("token"));
				} else {
					return Enum_MSG.失败.message("mbrapi-login-call获取token失败");
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
	}
	private String getToken(){
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		int timeout = 10000;
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
		
		
		String url="http://"+this.HOST+"/lps/j_spring_security_check?j_username="+P_USM+"&j_password="+P_PWD;
		System.out.println("get方式="+url);
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getToken", url );
		
		HttpPost request = new HttpPost(url);
		request.setConfig(requestConfig);
	    HttpResponse response=null;
	 
		try {
			
			request.addHeader("X-Requested-With", "X-Api-Client");
			request.addHeader("X-Api-Call", "X-Api-Client");
			
			response = httpClient.execute(request);
			
			String token=EntityUtils.toString(response.getEntity());
			
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getToken", url, token );
			
			return token;

			 
		} catch (IOException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}finally{
			
		}
		
	
	}
	
	private static String sendPUT(String url,JSONObject jsonObject){
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		int timeout = 10000;
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
		
		
		HttpPost request = new HttpPost(url);
		request.setConfig(requestConfig);
	    HttpResponse response=null;
	 
		try {
			
			
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
			System.out.println("result="+result);
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
	
	public static void main(String[] args) {
//		username,password,confirmPassword,crId,crType,neId,neType,tarType
		
		MGGameAPI gameAPI = new MGGameAPI();
		
		Map<String, Object> entity = new HashMap<String, Object>();
//		entity.put("username", "yat"+RandomStringNum.createRandomString(10));
//		entity.put("password", "Cz7JQarR");
		entity.put("username", "hyrsuzhou");
		entity.put("password", "hyr123456");
		entity.put("confirmPassword", "hyr123456");
		
		
		entity.put("crId", "110424823");
		entity.put("crType", "a");
		entity.put("neId", "110424823");
		entity.put("neType", "a");
		entity.put("tarType", "m");
		
//		entity.put("P_USM", "YATCNYA");
//		entity.put("P_PWD", "cQ7HAkjZLb9s3wXM");
//		entity.put("HOR_ID", "109946712");
//		entity.put("apiusername", "apiadmin");
//		entity.put("apipassword", "apipassword");
		
//		System.out.println(gameAPI.createAccount(entity));//10740737458443137745
		
//		crId=86292241, 
//		crType=hor, 
//		neId=93980368, 
//		neType=A, 
//		tarType=m, 
		
//		password=46746639, 
//		confirmPassword=46746639, 
//		username=31385121194150288097
				
		
//		{neId=86292333, password=Cz7JQarR, crId=86292241, crType=hor, tarType=m, confirmPassword=Cz7JQarR, neType=sh, username=10740737458443137745}
//		entity.put("username", "92907318@#&*_8169258");
		
//		entity.put("password", RandomStringNum.createRandomString(30));
//		System.out.println(gameAPI.getTokenByUserLogin(entity));
		
		
//		System.out.println(entity);
		
		
		entity.put("orderid", RandomStringNum.createRandomString(20));
		entity.put("amount", "5");
//		System.out.println(gameAPI.deposit(entity));
		
		entity.put("orderid", RandomStringNum.createRandomString(20));
		entity.put("amount", "5");
//		System.out.println(gameAPI.withdraw(entity));
		
		
//		System.out.println(gameAPI.getBalance(entity));
		
//		entity.put("password", "123456789");
//		System.out.println(gameAPI.updateInfo(entity));
		
//		System.out.println(gameAPI.getOrder(entity));
		
		entity.put("deviceType", "1");
		entity.put("playtype", "DZ");
		entity.put("gameId", "70307");
		System.out.println(gameAPI.login(entity));
	}
}



