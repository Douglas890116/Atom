package com.hy.pull.common.util.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.game.mg.MGUtil;
import com.hy.pull.common.util.game.sa.XmlUtil;
import com.hy.pull.common.util.game.ttg.TTGUtil;
import com.maven.config.SystemConstant;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;

import net.sf.json.JSONObject;

public class TTGGameAPI implements BaseInterface {

	
	private String privateServer =   "ams5-api.ttms.co:8443";
	private String privateLoginUrl = "ams5-games.ttms.co";
	private String partnerId0 = "HY88";
	private String partnerId = "HYNTTG";
	private String prefix = "HYN_";//交易订单前缀
	
	
	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	
	/**
	 * 默认构造函数
	 * 
	 * 不传递时使用默认的进行测试，正式时需要传递参数进来，此方法不使用
	 */
	@Deprecated
	public TTGGameAPI() {
		
	}
	/**
	 * 构建函数，初始化参数
	 * @param API_URL 接口URL
	 * @param UserName
	 * @param Password  
	 */
	public TTGGameAPI(String privateServer , String partnerId ,String privateLoginUrl,String prefix, String partnerId0, String GAME_API_URL) {
		this.privateServer = privateServer;
		this.privateLoginUrl = privateLoginUrl;
		this.partnerId = partnerId;
		this.prefix = prefix;
		this.partnerId0 = partnerId0;
		this.GAME_API_URL = GAME_API_URL;
	}
	
	
	private static String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	
	/**
	 * 创建账号
	 * 
	 * username,password,tester
	 * 
	 * Enter 1   if the player is a tester,  0   if player is not a tester.
	 * 
	 * @param entity
	 * @return
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() );
		try{
			
//			返回示例

			
			if(MapUtil.isNulls(entity, "username,password,tester")){
				
				if( String.valueOf(entity.get("username")).length() > 20 ) {
					return Enum_MSG.参数错误.message("用户长度不能大于20位");
				}
				if( String.valueOf(entity.get("password")).length() > 20 ) {
					return Enum_MSG.参数错误.message("密码长度不能大于20位");
				}
				
				//请求登录地址
				StringBuffer xmlData = new StringBuffer();
				xmlData.append(XML_HEADER);
				xmlData.append("<logindetail>");
				
				xmlData.append("<player ");
				xmlData.append(" account=\"").append("CNY").append("\"");
				xmlData.append(" country=\"").append("CN").append("\"");
				xmlData.append(" partnerId=\"").append(this.partnerId).append("\"");
//				xmlData.append(" firstName=\"").append(this.apipassword).append("\"");
//				xmlData.append(" lastName=\"").append(token).append("\"");
//				xmlData.append(" userName=\"").append(entity.get("username")).append("\"");
//				xmlData.append(" nickName=\"").append(entity.get("username")).append("\"");
				xmlData.append(" tester=\"").append(entity.get("tester")).append("\"");//Enter 1   if the player is a tester,  0   if player is not a tester.  If not entered, system will assume the player is not a tester.
				xmlData.append(" commonWallet=\"").append("0").append("\"");//Flag to identify whether or not player is using common wallet account.  1 means player is using common wallet account.  Otherwise 0. 
				xmlData.append("/> ");
				
				xmlData.append("<partners>");
				xmlData.append("<partner ").append(" partnerId=\"").append("zero").append("\"").append(" partnerType=\"").append("0").append("\"/>");
				xmlData.append("<partner ").append(" partnerId=\"").append("EVEB").append("\"").append(" partnerType=\"").append("1").append("\"/>");
				xmlData.append("<partner ").append(" partnerId=\"").append(this.partnerId0).append("\"").append(" partnerType=\"").append("1").append("\"/>");
				xmlData.append("<partner ").append(" partnerId=\"").append(this.partnerId).append("\"").append(" partnerType=\"").append("1").append("\"/>");
				xmlData.append("</partners>");
				
				xmlData.append("</logindetail> ");
				
				TTGUtil ttgUtil = new TTGUtil();
				
				String result = ttgUtil.sendXMLDataByPost(String.format("https://%s/cip/gametoken/%s", this.privateServer , String.valueOf(entity.get("username"))), xmlData.toString());
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , result );
				
				Map<String, String> item = XmlUtil.getQueryParamsByTTG(result);
				
				if(item.containsKey("token")) {
					return Enum_MSG.成功.message(item.get("token"));
				} else {
					item = XmlUtil.getQueryParamsByTTGerror(result);
					
					return Enum_MSG.失败.message(item.get("code"), "操作失败，请查看状态码 "+item.get("message"));
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
	 * 余额查询
	 * @param entity
	 * @return
	 */
	@Override
	public Object getBalance(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() );
		try{
			
//			返回示例

			
			if(MapUtil.isNulls(entity, "username")){
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度应为20个字符");
//				}
				
				//请求登录地址
				
				String result = getUrl(String.format("https://%s/cip/player/%s/balance", this.privateServer , String.valueOf(entity.get("username"))));
				
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , result );
				Map<String, String> item = XmlUtil.getQueryParamsByTTG(result);
				
				if(item.containsKey("real")) {
					return Enum_MSG.成功.message(item.get("real"));
				} else {
					return Enum_MSG.失败.message(item.get("code"), "操作失败，请查看状态码 "+item.get("message"));
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
	 * 用户取款
	 * username,exttransactionID,amount
	 * 
	 * exttransactionID=50个字符，可以是任意数字或字符
	 */
	public Object withdraw(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString()  );
		try{
			
//			返回示例

			
			if(MapUtil.isNulls(entity, "username,exttransactionID,amount")){
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度应为20个字符");
//				}
				if( String.valueOf(entity.get("exttransactionID")).length() > 50 ) {
					return Enum_MSG.参数错误.message("订单长度长度应为50个字符");
				}
				
				//请求登录地址
				StringBuffer xmlData = new StringBuffer();
				xmlData.append(XML_HEADER);
				
				xmlData.append("<transactiondetail ");
				xmlData.append(" uid=\"").append(entity.get("username")).append("\"");
				xmlData.append(" amount=\"").append("-"+entity.get("amount")).append("\"");
				xmlData.append("/> ");
				
				TTGUtil ttgUtil = new TTGUtil();
				
				String result = ttgUtil.sendXMLDataByPost(String.format("https://%s/cip/transaction/%s/%s", this.privateServer , this.partnerId, entity.get("exttransactionID").toString()), xmlData.toString());
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() , result );
				
				Map<String, String> item = XmlUtil.getQueryParamsByTTG(result);
				
				if(item.containsKey("retry") && item.get("retry").equals("0")) {//不需要重试
					return Enum_MSG.成功.message("成功");
				} else {
					
					return Enum_MSG.失败.message(item.get("code"), "操作失败，请查看状态码 "+item.get("message"));
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
	 * 用户存款
	 * username,exttransactionID,amount
	 * 
	 * exttransactionID=50个字符，可以是任意数字或字符
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() );
		try{
			
//			返回示例

			
			if(MapUtil.isNulls(entity, "username,exttransactionID,amount")){
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度应为20个字符");
//				}
				if( String.valueOf(entity.get("exttransactionID")).length() > 50 ) {
					return Enum_MSG.参数错误.message("订单长度长度应为50个字符");
				}
				
				//请求登录地址
				StringBuffer xmlData = new StringBuffer();
				xmlData.append(XML_HEADER);
				
				xmlData.append("<transactiondetail ");
				xmlData.append(" uid=\"").append(entity.get("username")).append("\"");
				xmlData.append(" amount=\"").append(entity.get("amount")).append("\"");
				xmlData.append("/> ");
				
				TTGUtil ttgUtil = new TTGUtil();
				
				String result = ttgUtil.sendXMLDataByPost(String.format("https://%s/cip/transaction/%s/%s", this.privateServer , this.partnerId, entity.get("exttransactionID").toString()), xmlData.toString());
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() , result );
				Map<String, String> item = XmlUtil.getQueryParamsByTTG(result);
				
				if(item.containsKey("retry") && item.get("retry").equals("0")) {//不需要重试
					return Enum_MSG.成功.message("成功");
				} else {
					
					return Enum_MSG.失败.message(item.get("code"), "操作失败，请查看状态码 "+item.get("message"));
				}
				
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
	}

	@Override
	public Object getRecord(Map<String, Object> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object updateInfo(Map<String, Object> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 订单查询
	 * 
	 * exttransactionID=订单长度长度应为50个字符
	 */
	@Override
	public Object getOrder(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() );
		try{
			
//			返回示例

			
			if(MapUtil.isNulls(entity, "username,exttransactionID")){
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度应为20个字符");
//				}
				if( String.valueOf(entity.get("exttransactionID")).length() > 50 ) {
//					return Enum_MSG.参数错误.message("订单长度长度应为50个字符");
				}
				
				//请求登录地址
				StringBuffer xmlData = new StringBuffer();
				xmlData.append(XML_HEADER);
				
				xmlData.append("<transactiondetail ");
				xmlData.append(" uid=\"").append(entity.get("username")).append("\"");
				xmlData.append(" amount=\"").append(entity.get("amount")).append("\"");
				xmlData.append("/> ");
				
				
				String result = getUrl(String.format("https://%s/cip/transaction/%s/%s", this.privateServer , this.partnerId, entity.get("exttransactionID").toString()));
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() , result );
				
				Map<String, String> item = XmlUtil.getQueryParamsByTTG(result);
				
				if(item.containsKey("amount") ) {
					return Enum_MSG.成功.message("成功");
				} else {
					
					return Enum_MSG.失败.message(item.get("code"), "操作失败，请查看状态码 "+item.get("message"));
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
	 * 登录接口。支持web与mobile大厅模式
	 * 
	 * 
	 */
	public Object login(Map<String, Object> entity) {
		//http://ams-games.stg.ttms.co/casino/mobile/lobby/index.html?playerHandle=100099442897720703547003088034857894&account=CNY&lang=zh-cn&deviceType=mobile
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "username,password,deviceType")){
			
//			if( String.valueOf(entity.get("username")).length() != 20 ) {
//				return Enum_MSG.参数错误.message("用户名长度应为20个字符");
//			}
//			if( String.valueOf(entity.get("password")).length() != 8 ) {
//				return Enum_MSG.参数错误.message("用户登录密码长度应为8个字符");
//			}
			
			JSONObject object = JSONObject.fromObject(createAccount(entity));
			if(object.getString("code").equals("0")) {
				String token = object.getString("info");
				
				String deviceType = String.valueOf(entity.get("deviceType"));
				
				String url = "";
				if( deviceType.equals("0")) {//PC
					url = String.format("http://%s/casino/mobile/lobby/index.html?playerHandle=%s&account=CNY&lang=zh-cn&deviceType=%s", this.privateLoginUrl , token, "web");
				} else {//H5
					url = String.format("http://%s/casino/mobile/lobby/index.html?playerHandle=%s&account=CNY&lang=zh-cn&deviceType=%s", this.privateLoginUrl , token, "mobile");
				}
				/**进入指定游戏
				http://pff.ttms.co/casino/generic/game/game.html?gameSuite=flash&gameName=FrogsNFlies&lang=zh-cn&playerHandle=999999&gameType=0&gameId=526&account=FunAcct
				
				
				游戏大厅http://pff.ttms.co/casino/generic/lobby/index.html?playerHandle=999999&account=FunAcct&lang=zh-cn 
				*/	
					
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() , url.toString() );
				
				return Enum_MSG.成功.message(url.toString());//返回跳转的地址
				
			} else {
				return object;
			}
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
		
		
	}

	@Override
	public Object isOnLine(Map<String, Object> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String getUrl(String urls) {
		StringBuilder sb = new StringBuilder();
		try {
			
			URL url = new URL(urls);
			HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
			int timeout = 10000;
			urlcon.setReadTimeout(timeout);
			urlcon.setConnectTimeout(timeout);
			urlcon.connect();         //获取连接
	           
			InputStream in = urlcon.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(in);
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			urlcon.disconnect();
			reader.close();
			inputStreamReader.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
//		username,password,confirmPassword,crId,crType,neId,neType,tarType
		
		TTGGameAPI gameAPI = new TTGGameAPI();
		
		Map<String, Object> entity = new HashMap<String, Object>();
		//entity.put("username", "HYH@#&*_12324");
		entity.put("tester", "0");//1测试 0非测试
		entity.put("username", "HYN"+RandomString.createRandomString(12));
		entity.put("password", RandomString.createRandomString(12));
		entity.put("confirmPassword", entity.get("password"));
		
//		entity.put("username", "HYHkHy5MWlHOTHr56TTJ");
//		System.out.println(entity);
		System.out.println(gameAPI.createAccount(entity));//10740737458443137745
		
		entity.put("exttransactionID", RandomStringNum.createRandomString(50));
		
		
		System.out.println(gameAPI.createAccount(entity));
		
		
		entity.put("amount", 2);
		System.out.println(gameAPI.deposit(entity));
		System.out.println(gameAPI.withdraw(entity));
		System.out.println(gameAPI.getBalance(entity));
		System.out.println(gameAPI.getOrder(entity));
		
		//http://ams-games.stg.ttms.co/casino/mobile/lobby/index.html?playerHandle=100099442897720703547003088034857894&account=CNY&lang=zh-cn
		//https://ams-games.stg.ttms.co/player/Menu.action?getMenu&playerHandle=100099442897720703547003088034857894&accName=CNY&lang=zh-cn&deviceType=web
		entity.put("deviceType", "0");
		
		System.out.println(gameAPI.login(entity));
	}
}
