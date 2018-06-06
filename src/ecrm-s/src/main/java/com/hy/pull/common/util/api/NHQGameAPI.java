package com.hy.pull.common.util.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.hy.pull.common.util.Encrypt;
import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.game.NHQGame;
import com.hy.pull.common.util.game.sa.DeEnCode;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.util.RandomString;

import net.sf.json.JSONObject;


/**
 * 新环球游戏接口
 * @author temdy
 */
public class NHQGameAPI implements BaseInterface {
	
//	private String API_URL = "http://192.168.1.161:8080/hqbjlApi/web";
//	private String API_URL = "http://192.168.1.120:8080/hqbjlApi/web";
	private String API_URL = "http://www.hy95.net/web";
//	private String API_URL = "http://hyapi.xtdlivegame.com/web";
//	private String API_URL = "http://cn.api.tt168.net/web";
	private String MD5_KEY = "HEFZNnYR";
	private String DES_KEY = "di5DlWs2";
	private String hyAesKey = "VUhEndOteNSQDAAK";
	
	private String agentname = "hyzhen08";
	private String agentpwd = "KltO79fJ";
	
	//http://192.168.1.120:8080/hqbjlApi/web?params=j1dj2RMewuyde0gHtPOV1905Wmie7bzUiPyVc/f582d+j9A56cPX1ba98nb4ugB/cQHHPnSuIyQ2hNx0FCLs1k9zBn6ksR6lKAvNQMppRGk=&key=8fdbb05e76f542e5cd980cfc419fda92&agentname=qingguo&agentpwd=ELwJtHTf
	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	
	/**
	 * 默认构造函数
	 */
	@Deprecated
	public NHQGameAPI(){
		
	}
	
	/**
	 * 构建函数，初始化参数
	 * @param API_URL API接口URL
	 * @param MD5_KEY MD5密钥
	 */
	public NHQGameAPI(String API_URL,String MD5_KEY,String DES_KEY, String agentname, String agentpwd,String hyAesKey,String GAME_API_URL){
		this.API_URL = API_URL;
		this.MD5_KEY = MD5_KEY;
		this.DES_KEY = DES_KEY;
		this.hyAesKey = hyAesKey;
		
		this.agentname = agentname;
		this.agentpwd = agentpwd;
		this.GAME_API_URL = GAME_API_URL;
	}

	/**
	 * 创建用户接口
	 * @param entity 参数列表 username,password
	 * username 用户名 大于6小于20（建议加前缀）
	 * password 密码 大于6小于20
	 * @return 返回结果
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString()  );
		try{
			if(MapUtil.isNulls(entity, "username,password")){	
				
				if( String.valueOf(entity.get("username")).length() > 20 ) {
					return Enum_MSG.参数错误.message("用户长度不能大于20位，当前"+String.valueOf(entity.get("username")).length())+"位";
				}
				if( String.valueOf(entity.get("password")).length() > 20 ) {
					return Enum_MSG.参数错误.message("密码长度不能大于20位，当前"+String.valueOf(entity.get("password")).length())+"位";
				}
				
				StringBuilder params = new StringBuilder();
				params.append("!username=");
				params.append(entity.get("username"));
				params.append("!password=");
				params.append(entity.get("password"));
				params.append("!method=create");
				//params = new StringBuilder("username=C28ORdRlVdLTghk7uIqL!password=12345678!ip=127.0.0.2!currency=CNY!handicaps=1,2,3!method=create");
				String des = DeEnCode.encrypt(params.toString(), DES_KEY);
				String key = DeEnCode.string2MD5((params.toString()+MD5_KEY));
				
				String result = getUrl(API_URL + "?params="+des+"&key="+key + "&agentname="+this.agentname+ "&agentpwd="+this.agentpwd);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , result );
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( object.has("code") ) {
					
					if(object.getString("code").equals("1")) {
						
						return Enum_MSG.成功.message(object.getString("info"));
					} else {
						if(object.getString("code").equals("1004")) {
							return Enum_MSG.账号已存在.message(object.getString("info"));
						}
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

	/**
	 * 获取余额
	 * @param entity 参数列表 username
	 * username 用户名
	 * @return 返回结果
	 */
	@Override
	public Object getBalance(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString()  );
		try{
			if(MapUtil.isNulls(entity, "username")){	
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度必须为20位，当前"+String.valueOf(entity.get("username")).length())+"位";
//				}
				
				StringBuilder params = new StringBuilder();
				params.append("!username=");
				params.append(entity.get("username"));
				params.append("!method=balance");
				String des = DeEnCode.encrypt(params.toString(), DES_KEY);
				String key = DeEnCode.string2MD5((params.toString()+MD5_KEY));
				
				String result = getUrl(API_URL + "?params="+des+"&key="+key + "&agentname="+this.agentname+ "&agentpwd="+this.agentpwd);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , result );
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
	

	/**
	 * 下分（取款）的接口
	 * @param entity 参数列表 username,money
	 * username 用户名
	 * money 分数
	 * @return 返回结果 返回订单确认码，需要存储起来
	 */
	@Override
	public Object withdraw(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() );
		try{
			if(MapUtil.isNulls(entity, "username,money")){	
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度必须为20位，当前"+String.valueOf(entity.get("username")).length())+"位";
//				}
				if( Double.valueOf(entity.get("money").toString()) < 1.0 ) {
					return Enum_MSG.参数错误.message("取款金额不能小于1元");
				}
				
				StringBuilder params = new StringBuilder();
				params.append("!username=");
				params.append(entity.get("username"));
				params.append("!money=");
				params.append(entity.get("money"));
				params.append("!method=withdraw");
				String des = DeEnCode.encrypt(params.toString(), DES_KEY);
				String key = DeEnCode.string2MD5((params.toString()+MD5_KEY));
				
				String result = getUrl(API_URL + "?params="+des+"&key="+key + "&agentname="+this.agentname+ "&agentpwd="+this.agentpwd);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() , result );
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

	/**
	 * 上分（存款）的接口
	 * @param entity 参数列表 username,money
	 * username 用户名
	 * money 分数
	 * @return 返回结果 返回订单确认码，需要存储起来
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() );
		try{
			if(MapUtil.isNulls(entity, "username,money")){	
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度必须为20位，当前"+String.valueOf(entity.get("username")).length())+"位";
//				}
				
				StringBuilder params = new StringBuilder();
				params.append("!username=");
				params.append(entity.get("username"));
				params.append("!money=");
				params.append(entity.get("money"));
				params.append("!method=deposit");
				String des = DeEnCode.encrypt(params.toString(), DES_KEY);
				String key = DeEnCode.string2MD5((params.toString()+MD5_KEY));
				
				String result = getUrl(API_URL + "?params="+des+"&key="+key + "&agentname="+this.agentname+ "&agentpwd="+this.agentpwd);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() , result );
				
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

	/**
	 * 获取游戏结果
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	@Override
	public Object getRecord(Map<String, Object> entity) {
		StringBuilder params = new StringBuilder();
		params.append("parentid=");
		params.append(this.agentname);
		params.append("!begintime=");
		params.append("2017-06-01 00:00:00");
		params.append("!endtime=");
		params.append("2017-06-08 00:00:00");		
		params.append("!isall=1");
		params.append("!pagenum=");
		params.append("1");
		params.append("!method=");
		params.append("info");
		String des = GameHttpUtil.encrypt(params.toString(), DES_KEY);//对原文加密
		String key = DeEnCode.string2MD5((params.toString()+MD5_KEY));
		String jsonResult = getUrl(API_URL + "?params="+des+"&key="+key + "&agentname="+this.agentname+ "&agentpwd="+this.agentpwd);//完整请求链接URL
		
		return jsonResult;
	}

	/**
	 * 更新信息接口
	 * @param entity 参数列表 username,password,newpassword
	 * @return 返回结果
	 */
	@Override
	public Object updateInfo(Map<String, Object> entity) {
		
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString()  );
		try{
			if(MapUtil.isNulls(entity, "username,password,newpassword")){	
				
				if( String.valueOf(entity.get("newpassword")).length() > 20 ) {
					return Enum_MSG.参数错误.message("密码长度不能大于20位，当前"+String.valueOf(entity.get("password")).length())+"位";
				}
				
				StringBuilder params = new StringBuilder();
				params.append("!username=");
				params.append(entity.get("username"));
				params.append("!password=");
				params.append(entity.get("password"));
				params.append("!newpwd=");
				params.append(entity.get("newpassword"));
				params.append("!method=chgpwd");
				String des = DeEnCode.encrypt(params.toString(), DES_KEY);
				String key = DeEnCode.string2MD5((params.toString()+MD5_KEY));
				
				String result = getUrl(API_URL + "?params="+des+"&key="+key + "&agentname="+this.agentname+ "&agentpwd="+this.agentpwd);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString() , result );
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

	/**
	 * 获取订单接口
	 * @param entity 参数列表 orderid,type
	 * 
	 * orderid=订单编号，上分或者下分时接口主动返回的单号
	 * type=上分或者下分类型，confirmdeposit=上分确认，confirmwithdraw=下分确认
	 * 
	 * @return 返回结果 返回单号
	 */
	@Override
	public Object getOrder(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString()  );
		try{
			if(MapUtil.isNulls(entity, "orderid,type")){	
				
				StringBuilder params = new StringBuilder();
				params.append("!orderid=");
				params.append(entity.get("orderid"));
				params.append("!method=");
				params.append(entity.get("type"));
				String des = DeEnCode.encrypt(params.toString(), DES_KEY);
				String key = DeEnCode.string2MD5((params.toString()+MD5_KEY));
				
				String result = getUrl(API_URL + "?params="+des+"&key="+key + "&agentname="+this.agentname+ "&agentpwd="+this.agentpwd);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() , result );
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
		try{
			if(MapUtil.isNulls(entity, "username,password,deviceType")){	
				
				String deviceType = String.valueOf(entity.get("deviceType"));
				if( !deviceType.equals("0") && !deviceType.equals("1")) {
					return Enum_MSG.参数错误.message("HY游戏目前只能接受PC端与手机H5端的请求，即设备类型为0和1");
				}
				
				StringBuilder params = new StringBuilder();
				params.append("username=");
				params.append(entity.get("username"));
				params.append("!password=");
				params.append(entity.get("password"));
				params.append("!lang=");
				params.append(entity.get("lang") == null ? "cn" : entity.get("lang"));
				params.append("!method=play");
				if(entity.containsKey("lobbyurl") && entity.get("lobbyurl").toString().length() > 6) {
					if( !entity.get("lobbyurl").toString().toLowerCase().startsWith("http")) {
						entity.put("lobbyurl", "http://"+entity.get("lobbyurl").toString());
					}
					params.append("!url="+entity.get("lobbyurl"));
				}

				
				if( deviceType.equals("0")) {//PC
					
					String des = DeEnCode.encrypt(params.toString(), DES_KEY);
					String key = DeEnCode.string2MD5((params.toString() + MD5_KEY));
					
					String result = getUrl(API_URL + "?params="+des+"&key="+key + "&agentname="+this.agentname+ "&agentpwd="+this.agentpwd);
					System.out.println("调用结果："+result);
					BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() , result );
					
					return Enum_MSG.成功.message(result);
					
					/*
					//接口调用成功
					if( result.startsWith("http") || result.startsWith("phone")) {
						
						return Enum_MSG.成功.message(result);
						
					} else {//网络异常
						return result.toString();
					}
					*/
				} else {//H5 
					
					/*
					//非直连
					String param = Encrypt.AESEncrypt("user=" + entity.get("username") + "&password=" + entity.get("password"), hyAesKey);
					String result = "http://phone.432dw.com/autoLogin.do?param="+param;
					BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() , result );
					*/
					
					params = new StringBuilder();
					params.append("username=");
					params.append(entity.get("username"));
					params.append("!password=");
					params.append(entity.get("password"));
					params.append("!lang=");
					params.append(entity.get("lang") == null ? "cn" : entity.get("lang"));
					params.append("!method=play");
					params.append("!h5=1");
					if(entity.containsKey("lobbyurl") && entity.get("lobbyurl").toString().length() > 6) {
						if( !entity.get("lobbyurl").toString().toLowerCase().startsWith("http")) {
							entity.put("lobbyurl", "http://"+entity.get("lobbyurl").toString());
						}
						params.append("!url="+entity.get("lobbyurl"));
					}
					String des = DeEnCode.encrypt(params.toString(), DES_KEY);
					String key = DeEnCode.string2MD5((params.toString()+MD5_KEY));
					
					String result = getUrl(API_URL + "?params="+des+"&key="+key + "&agentname="+this.agentname+ "&agentpwd="+this.agentpwd);
					System.out.println("调用结果1："+result);
					BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() , result );
					//接口调用成功
					if( result.startsWith("http") ) {
						
						return Enum_MSG.成功.message(result);
						
					} else {//网络异常
						return result.toString();
					}
					
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
	
	private static String getUrl(String urls) {
		StringBuilder sb = new StringBuilder();
		try {
			
			URL url = new URL(urls);
	        HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
	        int timeout = 10000;
	        urlcon.setConnectTimeout(timeout);
	        urlcon.setReadTimeout(timeout);
	        urlcon.connect();
	        
	        System.out.println("请求链接："+urls);
	        
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
	
	public static void main(String[] args) throws Exception {
		NHQGameAPI nhq = new NHQGameAPI();
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("username", RandomString.createRandomString(20));
		entity.put("password", "12345678");
//		entity.put("username", "C28ORdRlVdLT72k7uIqL");
//		entity.put("password", "12345678");
		System.out.println(nhq.createAccount(entity));
		
		//password=12345678, username=iH816k2u1eJ9TniL5Dkh, newpassword=45412121
		
//		entity.put("username", "hyldw2230736");
//		entity.put("password", "44497202");	
		
//		entity.put("money", "0.1");
//		entity.put("orderid", "145976");
//		entity.put("type", "confirmwithdraw");
//		entity.put("deviceType", "0");
//		
//		System.out.println(nhq.getBalance(entity));
//		System.out.println(nhq.deposit(entity));
//		System.out.println(nhq.withdraw(entity));
		
//		System.out.println(nhq.getOrder(entity));//type=上分或者下分类型，confirmdeposit=上分确认，confirmwithdraw=下分确认
		entity.put("deviceType", "1");
//		entity.put("lobbyurl", "https://baidu.com");
		System.out.println(nhq.login(entity));
		
		
//		String param = Encrypt.AESEncrypt("user=hqyy303092&password=112233", "VUhEndOteNSQDAAK");
//		String result = "http://phone.432dw.com/autoLogin.do?param="+param;
//		System.out.println(result);
		
		entity.put("newpassword", "45412121");
//		System.out.println(nhq.updateInfo(entity));//username,password,newpassword
		
		//hkd.fgame.hy728.com/dobusiness.do?params=dT1QVGhyclVwUldEc1d4dHZadUVWMHxwPTEyMzQ1Njc4fGk9MHxsPWNu
//		System.out.println(nhq.getRecord(entity));
		//http://192.168.1.120:8080/dobusiness.do?params=dT1FZnY4WW9ZMEVrTkJxOVRJcDZUS3xwPTEyMzQ1Njc4fGk9MHxsPWNu
		
		//http://www.hy282.com/
		//http://www.hy393.com/
		//http://hyh5.hyzonghe.com
		//http://hyh5.hy728.com
	}
}
