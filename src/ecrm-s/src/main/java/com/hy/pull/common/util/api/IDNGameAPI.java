package com.hy.pull.common.util.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.RSAUtil;
import com.hy.pull.common.util.game.pt.PTUtils;
import com.hy.pull.common.util.game.sa.MD5Encoder;
import com.maven.config.SystemConstant;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;

import net.sf.json.JSONObject;

/**
 * IDN戏接口
 * @author temdy
 */
public class IDNGameAPI implements BaseInterface {
	
	private String API_URL = "http://devapi.idnpoker.com:2802/";//
	private String secret_key = "51e344eb8077d7ac84b8ed715";//
	private String LOBBY_URL = "http://new.lobbyint.pw/lobby.php?";//
	
	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	
//	CBHAA
	
	/**
	 * 默认构造函数
	 * 
	 * 测试时可以使用，不用传递秘钥进来
	 */
	@Deprecated
	public IDNGameAPI(){
		if( !API_URL.endsWith("?")) {
			API_URL = API_URL.concat("?");
		}
	}
	
	
	/**
	 * 构建函数，初始化参数
	 * @param ENTITY_KEY 密钥ENTITY
	 */
	public IDNGameAPI(String API_URL, String secret_key, String LOBBY_URL, String GAME_API_URL){
		this.API_URL = API_URL;
		this.secret_key = secret_key;
		this.LOBBY_URL = LOBBY_URL;
		this.GAME_API_URL = GAME_API_URL;
		
		if( !API_URL.endsWith("?")) {
			API_URL = API_URL.concat("?");
		}
	}
	
	private static String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	/**
	 * 创建用户接口
	 * @param entity 参数列表 username,password
	 * 
	 * 用户名最大15位数
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() );
		try{
			if(MapUtil.isNulls(entity, "username,password")){
				
				if( String.valueOf(entity.get("username")).length() > 15 ) {
					return Enum_MSG.参数错误.message("用户长度不能大于15位");
				}
				if( String.valueOf(entity.get("password")).length() > 25 ) {
					return Enum_MSG.参数错误.message("密码长度不能大于25位");
				}
				
//				<request>
//					<secret_key>token</secret_key>
//					<id>1</id>
//					<userid>UserId</userid>
//					<password>Password</password>
//					<confirm_password>confrimation_password</confirm_password>
//					<username>username</username>
//				</request>

				StringBuilder xmlstr = new StringBuilder();
				xmlstr.append("<request>");
				xmlstr.append("<secret_key>").append(this.secret_key).append("</secret_key>");
				xmlstr.append("<id>").append("1").append("</id>");//固定值。每个api方法都有id值
				xmlstr.append("<userid>").append(entity.get("username")).append("</userid>");
				xmlstr.append("<password>").append(entity.get("password")).append("</password>");
				xmlstr.append("<confirm_password>").append(entity.get("password")).append("</confirm_password>");
				xmlstr.append("<username>").append(entity.get("username")).append("</username>");
				xmlstr.append("</request>");
				
				String result = sendXMLDataByPost(this.API_URL, xmlstr.toString());
				if(result.equals("-1") ){
					return Enum_MSG.接口调用错误.message("网络异常");
				}
				Map<String, String> data = getQueryParams(result);
				System.out.println("请求结果："+data);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , data.toString() );
				
				//存在该节点时，表示成功
				if(data.get("status").equals("1")) {
					
					
					/************尝试创建移动版的用户名，密码同上***********/
					/************第一次尝试使用同样的用户名去创建***********/
					/************第二次尝试使用app前缀的用户名去创建***********/
					
					xmlstr = new StringBuilder();
					xmlstr.append("<request>");
					xmlstr.append("<secret_key>").append(this.secret_key).append("</secret_key>");
					xmlstr.append("<id>").append("17").append("</id>");//固定值。每个api方法都有id值
					xmlstr.append("<userid>").append(entity.get("username")).append("</userid>");
					xmlstr.append("<loginid>").append(entity.get("username")).append("</loginid>");//Maximum characters on loginid is 25
					xmlstr.append("</request>");
					
					result = sendXMLDataByPost(this.API_URL, xmlstr.toString());
					if(result.equals("-1") ){
						return Enum_MSG.接口调用错误.message("网络异常");
					}
					data = getQueryParams(result);
					System.out.println(data);
					
					return Enum_MSG.成功.message(data.get("message"));
					
					
				} else {
					if(data.get("error").equals("12")) {
						return Enum_MSG.账号已存在.message(data.get("message"));
					}
					return Enum_MSG.失败.message("调用异常。message:"+data.get("message") + ", code:"+data.get("error"));
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
	 * @param entity 参数列表 playerName
	 * playername 玩家账号
	 * 
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object getBalance(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() );
		try{
			
//			错误代码清单：

			
			if(MapUtil.isNulls(entity, "username")){		
				
				StringBuilder xmlstr = new StringBuilder();
				xmlstr.append("<request>");
				xmlstr.append("<secret_key>").append(this.secret_key).append("</secret_key>");
				xmlstr.append("<id>").append("10").append("</id>");//固定值。每个api方法都有id值
				xmlstr.append("<userid>").append(entity.get("username")).append("</userid>");
				xmlstr.append("</request>");
				
				String result = sendXMLDataByPost(this.API_URL, xmlstr.toString());
				if(result.equals("-1") ){
					return Enum_MSG.接口调用错误.message("网络异常");
				}
				Map<String, String> data = getQueryParams(result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , data.toString() );
				
					
				//存在该节点时，表示成功
				if(data.containsKey("balance")) {
					//不返回相关数据。
					return Enum_MSG.成功.message(data.get("balance"));//当前余额
				} else {
					return Enum_MSG.失败.message("调用异常。message:"+data.get("message") + ", code:"+data.get("error"));
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
	 * @param entity 参数列表
	 * playername 玩家
	 * amount 分数
	 * externaltranid 交易单号
	 * 
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object withdraw(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() );
		try{
			

			if(MapUtil.isNulls(entity, "username,orderid,amount")){		
				
				if( String.valueOf(entity.get("orderid")).length() > 36 ) {
					return Enum_MSG.参数错误.message("单号长度不能大于36位");
				}
				
				
				StringBuilder xmlstr = new StringBuilder();
				xmlstr.append("<request>");
				xmlstr.append("<secret_key>").append(this.secret_key).append("</secret_key>");
				xmlstr.append("<id>").append("4").append("</id>");//固定值。每个api方法都有id值
				xmlstr.append("<userid>").append(entity.get("username")).append("</userid>");
				xmlstr.append("<withdraw>").append(entity.get("amount")).append("</withdraw>");
				xmlstr.append("<id_transaction>").append(entity.get("orderid")).append("</id_transaction>");
				xmlstr.append("</request>");
				
				String result = sendXMLDataByPost(this.API_URL, xmlstr.toString());
				if(result.equals("-1") ){
					return Enum_MSG.接口调用错误.message("网络异常");
				}
				Map<String, String> data = getQueryParams(result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() , data.toString() );
				
				//存在该节点时，表示成功
				if(data.get("status").equals("1")) {
					//不返回相关数据。
					return Enum_MSG.成功.message(data.get("balance"));//当前余额
				} else {
					return Enum_MSG.失败.message("调用异常。message:"+data.get("message") + ", code:"+data.get("error"));
				}
				
				
			}  else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}


		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
	}

	/**
	 * 上分（存款）的接口
	 * @param entity 参数列表 playerName,extTransId,coins
	 * 
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() );
		try{

			if(MapUtil.isNulls(entity, "username,orderid,amount")){		
				
				if( String.valueOf(entity.get("orderid")).length() > 36 ) {
					return Enum_MSG.参数错误.message("单号长度不能大于36位");
				}
				
				StringBuilder xmlstr = new StringBuilder();
				xmlstr.append("<request>");
				xmlstr.append("<secret_key>").append(this.secret_key).append("</secret_key>");
				xmlstr.append("<id>").append("3").append("</id>");//固定值。每个api方法都有id值
				xmlstr.append("<userid>").append(entity.get("username")).append("</userid>");
				xmlstr.append("<id_transaction>").append(entity.get("orderid")).append("</id_transaction>");
				xmlstr.append("<deposit>").append(entity.get("amount")).append("</deposit>");
				xmlstr.append("</request>");
				
				String result = sendXMLDataByPost(this.API_URL, xmlstr.toString());
				if(result.equals("-1") ){
					return Enum_MSG.接口调用错误.message("网络异常");
				}
				Map<String, String> data = getQueryParams(result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() , data.toString() );
				
				//存在该节点时，表示成功
				if(data.get("status").equals("1")) {
					//不返回相关数据。
					return Enum_MSG.成功.message(data.get("balance"));//当前余额
				} else {
					return Enum_MSG.失败.message("调用异常。message:"+data.get("message") + ", error:"+data.get("error"));
				}
				
				
			}  else {
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
		
		try {
			//只能抓取15天内的数据
			/*
			StringBuilder xmlstr = new StringBuilder();
			xmlstr.append("<request>");
			xmlstr.append("<secret_key>").append(this.secret_key).append("</secret_key>");
			xmlstr.append("<id>").append("8").append("</id>");//固定值。每个api方法都有id值
			xmlstr.append("<date>").append("6").append("</date>");//month/date/year 07/30/2015
			xmlstr.append("<start_time>").append("").append("</start_time>");//hour:minute	08:00
			xmlstr.append("<end_time>").append("").append("</end_time>");//hour:minute 14:00
			xmlstr.append("</request>");
			*/
			
			StringBuilder xmlstr = new StringBuilder();
			xmlstr.append("<request>");
			xmlstr.append("<secret_key>").append(this.secret_key).append("</secret_key>");
			xmlstr.append("<id>").append("8").append("</id>");//固定值。每个api方法都有id值
			xmlstr.append("<date>").append("6/23/2017").append("</date>");//month/date/year 07/30/2015
			xmlstr.append("</request>");
			
			String result = sendXMLDataByPost(this.API_URL, xmlstr.toString());
			System.out.println(result);
			if(result.equals("-1") ){
				return Enum_MSG.接口调用错误.message("网络异常");
			}
			List<Map<String, String>> list = getQueryParamsList2(result);
			for (Map<String, String> map : list) {
				System.out.println(map);
			}
			return list;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 更新信息接口（更新密码）
	 * @param entity 参数列表
	 * playername 玩家
	 * @return 返回结果
	 */
	@Override
	public Object updateInfo(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString() );
		try{
			
			if(MapUtil.isNulls(entity, "username,password,newpassword")){		
				
				if( String.valueOf(entity.get("newpassword")).length() > 25 ) {
					return Enum_MSG.参数错误.message("新密码长度不能大于25位");
				}
				
				StringBuilder xmlstr = new StringBuilder();
				xmlstr.append("<request>");
				xmlstr.append("<secret_key>").append(this.secret_key).append("</secret_key>");
				xmlstr.append("<id>").append("16").append("</id>");//固定值。每个api方法都有id值
				xmlstr.append("<userid>").append(entity.get("username")).append("</userid>");
				xmlstr.append("<password>").append(entity.get("password")).append("</password>");
				xmlstr.append("<new_password>").append(entity.get("newpassword")).append("</new_password>");
				xmlstr.append("<retypenew_password>").append(entity.get("newpassword")).append("</retypenew_password>");
				xmlstr.append("</request>");
				
				String result = sendXMLDataByPost(this.API_URL, xmlstr.toString());
				if(result.equals("-1") ){
					return Enum_MSG.接口调用错误.message("网络异常");
				}
				Map<String, String> data = getQueryParams(result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString() , data.toString() );
				
				//存在该节点时，表示成功
				if(data.get("status").equals("1")) {
					//不返回相关数据。
					return Enum_MSG.成功.message(data.get("message"));//
				} else {
					return Enum_MSG.失败.message("调用异常。message:"+data.get("message") + ", code:"+data.get("error"));
				}
				
				
			}  else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
	}

	/**
	 * 获取订单接口
	 * @param entity 参数列表 playerName,extTransId
	 * 
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object getOrder(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() );
		try{
			
			
			if(MapUtil.isNulls(entity, "username,orderid")){		
				
				StringBuilder xmlstr = new StringBuilder();
				xmlstr.append("<request>");
				xmlstr.append("<secret_key>").append(this.secret_key).append("</secret_key>");
				xmlstr.append("<id>").append("7").append("</id>");//固定值。每个api方法都有id值
				xmlstr.append("<userid>").append(entity.get("username")).append("</userid>");
				xmlstr.append("<action>").append(entity.get("orderid").toString().startsWith("IN")? "1" : "2").append("</action>");//Deposit = 1  or  Withdraw = 2
				xmlstr.append("<id_transaction>").append(entity.get("orderid")).append("</id_transaction>");
				xmlstr.append("</request>");
				
				String result = sendXMLDataByPost(this.API_URL, xmlstr.toString());
				if(result.equals("-1") ){
					return Enum_MSG.接口调用错误.message("网络异常");
				}
				Map<String, String> data = getQueryParams(result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() , data.toString() );
				
				//为1表示单号已存在
				if(data.get("status").equals("1")) {
					//不返回相关数据。
					return Enum_MSG.成功.message(data);//
				} else {
					return Enum_MSG.失败.message("调用异常。message:"+data.get("message") + ", code:"+data.get("error"));
				}
				
			}  else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}

		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
	}

	/**
	 * 登录的接口 playername,password,deviceType,playtype
	 * 
	 * 
	 * @param entity 参数列表 
	 * @return 返回结果
	 */
	@Override
	public Object login(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() );
		
		try{
			
			if(MapUtil.isNulls(entity, "username,password,loginIp")){		
				
				StringBuilder xmlstr = new StringBuilder();
				xmlstr.append("<request>");
				xmlstr.append("<secret_key>").append(this.secret_key).append("</secret_key>");
				xmlstr.append("<id>").append("2").append("</id>");//固定值。每个api方法都有id值
				xmlstr.append("<userid>").append(entity.get("username")).append("</userid>");
				xmlstr.append("<password>").append(entity.get("password")).append("</password>");
				xmlstr.append("<ip>").append(entity.get("loginIp")).append("</ip>");
				xmlstr.append("</request>");
				
				String result = sendXMLDataByPost(this.API_URL, xmlstr.toString());
				if(result.equals("-1") ){
					return Enum_MSG.接口调用错误.message("网络异常");
				}
				Map<String, String> data = getQueryParams(result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() , data.toString() );
				
				//存在该节点时，表示成功
				if(data.containsKey("session")) {
					//不返回相关数据。
					
//					http://lobby.idn07089533.com/lobby.php?user=LOREM&key=SESSION
					
					String loginurl = LOBBY_URL.concat("user=").concat(entity.get("username").toString()).concat("&key=").concat(data.get("session"));
					http://new.lobbyint.pw/lobby.php?user=LOREM&key=SESSION 
					return Enum_MSG.成功.message(loginurl);
				} else {
					return Enum_MSG.失败.message("调用异常。message:"+data.get("message") + ", code:"+data.get("error"));
				}
				
				
			}  else {
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
	 * playername 玩家
	 * 
	 * 
	 * @return 返回结果 正确：0为否，1为是
	 */
	@Override
	public Object isOnLine(Map<String, Object> entity) {
		try{
			
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);

		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
	}
	/**
	 * Send a XML-Formed string to HTTP Server by post method
	 * 
	 * @param url
	 *            the request URL string
	 * @param xmlData
	 *            XML-Formed string ,will not check whether this string is
	 *            XML-Formed or not
	 * @return the HTTP response status code ,like 200 represents OK,404 not
	 *         found
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
//	private static DefaultHttpClient client;
	private static String sendXMLDataByPost(String url, String xmlData) throws ClientProtocolException, IOException {
		try {
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			int timeout = 10000;
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
			
			
			
			System.out.println("请求URL："+url);
			System.out.println("请求DATA："+xmlData);
			// Send data by post method in HTTP protocol,use HttpPost instead of
			// PostMethod which was occurred in former version
			HttpPost post = new HttpPost(url);
			post.setConfig(requestConfig);
			// Construct a string entity
			StringEntity entity = new StringEntity(xmlData);
			// Set XML entity
			post.setEntity(entity);
			// Set content type of request header
			post.setHeader("Content-Type", "application/xml;charset=UTF-8");
			// Execute request and get the response
			HttpResponse response = httpClient.execute(post);
			// Response Header - StatusLine - status code
			
			//statusCode = response.getStatusLine().getStatusCode();
			String result = EntityUtils.toString(response.getEntity());
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}
	}
	
	/**
	 * 返回多个detail节点
	 * @param xmlStr
	 * @return
	 */
	private static List<Map<String, String>> getQueryParamsList2(String xmlStr) {
		if(xmlStr==null || xmlStr.equals("")) {
			return null;
		}
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML    
			Element root = doc.getRootElement();
			
			List<Element> list = root.selectNodes("//response/data/detail");
			List<Map<String, String>> listData = new ArrayList<Map<String,String>>();
			
			
			for (Element element : list) {
				Map<String, String> data = new HashMap<String, String>();
				
				Iterator<Element> iterator = element.elementIterator();
				while (iterator.hasNext()) {
					Element element2 = (Element) iterator.next();
					data.put(element2.getName(), element2.getStringValue());
//					System.out.println(element2.getName() +"-"+ element2.getStringValue());
				}
				listData.add(data);
			}
			return listData;
			
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 返回多个row节点
	 * @param xmlStr
	 * @return
	 */
	private static List<Map<String, String>> getQueryParamsList(String xmlStr) {
		if(xmlStr==null || xmlStr.equals("")) {
			return null;
		}
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML    
			Element root = doc.getRootElement();
			
			List<Element> list = root.selectNodes("//response/row");
			List<Map<String, String>> listData = new ArrayList<Map<String,String>>();
			
			
			for (Element element : list) {
				Map<String, String> data = new HashMap<String, String>();
				
				Iterator<Element> iterator = element.elementIterator();
				while (iterator.hasNext()) {
					Element element2 = (Element) iterator.next();
					data.put(element2.getName(), element2.getStringValue());
//					System.out.println(element2.getName() +"-"+ element2.getStringValue());
				}
				listData.add(data);
			}
			return listData;
			
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
			List<Element> list2 = root.selectNodes("//response");
			
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
	
	public static void main(String[] a){
		IDNGameAPI tag = new IDNGameAPI();
		Map<String, Object> entity = new HashMap<String, Object>();
		
		entity.put("username", RandomString.createRandomString(15).toLowerCase());//#&*
		entity.put("password", RandomString.createRandomString(6)+"d6");
//		entity.put("username", "SvQlOmuhjGJW0I5");
//		entity.put("password", "KeGcb700");
//		System.out.println(tag.createAccount(entity));//username,password
		
		
		entity.put("orderid", "IN"+RandomString.createRandomString(30));
		entity.put("amount", "3");
//		System.out.println(tag.deposit(entity));//username,orderid,amount
		
		
		entity.put("orderid", "OUT"+RandomString.createRandomString(30));
		entity.put("amount", "2");
//		System.out.println(tag.withdraw(entity));//username,orderid,amount
		
//		System.out.println(tag.getOrder(entity));//username,orderid
		
//		System.out.println(tag.getBalance(entity));//username
		entity.put("loginIp", "192.168.1.1");
//		System.out.println(tag.login(entity));//username,password,loginIp
		
		System.out.println(tag.getRecord(entity));;
	}
}
