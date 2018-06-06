package com.hy.pull.common.util.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hy.pull.common.util.Encrypt;
import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.KYEncrypt;
import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.game.NHQGame;
import com.hy.pull.common.util.game.sa.DeEnCode;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.util.RandomString;

import net.sf.json.JSONObject;


/**
 * 开元游戏接口
 * @author temdy
 */
public class KYGameAPI implements BaseInterface {
	
	private String API_URL = "http://api.ky34.com/channelHandle";
	private String MD5_KEY = "c5f89d08d0474d10";
	private String DES_KEY = "21de254b1bfd4cb8";
	
	private String agent = "10074";
	
	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	
	
	/**
	 * 默认构造函数
	 */
	@Deprecated
	public KYGameAPI(){
		
	}
	
	/**
	 * 构建函数，初始化参数
	 * @param API_URL API接口URL
	 * @param MD5_KEY MD5密钥
	 */
	public KYGameAPI(String API_URL,String MD5_KEY,String DES_KEY, String agent, String GAME_API_URL){
		this.API_URL = API_URL;
		this.MD5_KEY = MD5_KEY;
		this.DES_KEY = DES_KEY;
		this.agent = agent;
		this.GAME_API_URL = GAME_API_URL;
	}

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
	private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");// 设置日期格式
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
				params.append("s=0&money=0");
				params.append("&account=");
				params.append(entity.get("username"));
				params.append("&orderid=");
				params.append(this.agent.concat(sdf2.format(new Date())));
				params.append("&ip=");
				params.append("192.168.1.1");
				params.append("&lineCode=");
				params.append("text11");
				
				String timestamp = System.currentTimeMillis() + "";
//				System.out.println("agent："+this.agent);
//				System.out.println("DES_KEY："+this.DES_KEY);
//				System.out.println("MD5_KEY："+this.MD5_KEY);
//				System.out.println("timestamp："+timestamp);
//				System.out.println("业务参数原文："+params);
				
				
				String des = KYEncrypt.AESEncrypt(params.toString(), DES_KEY);
				String key = KYEncrypt.MD5((this.agent+timestamp+MD5_KEY));
				
				
				String result = getUrl(API_URL + "?param="+des+"&key="+key + "&agent="+this.agent + "&timestamp="+timestamp);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , result );
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( object.has("d") ) {
					
					JSONObject data = object.getJSONObject("d");
					if(data.getString("code").equals("0")) {
						
						return Enum_MSG.成功.message(data.getString("url"));
					} else {
						return Enum_MSG.失败.message(data.getString("code"), "创建账号异常");
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
				params.append("s=1");
				params.append("&account=");
				params.append(entity.get("username"));
				params.append("&orderid=");
				params.append(this.agent.concat(sdf2.format(new Date())));
				
				String timestamp = System.currentTimeMillis() + "";
//				System.out.println("agent："+this.agent);
//				System.out.println("DES_KEY："+this.DES_KEY);
//				System.out.println("MD5_KEY："+this.MD5_KEY);
//				System.out.println("timestamp："+timestamp);
//				System.out.println("业务参数原文："+params);
				
				
				String des = KYEncrypt.AESEncrypt(params.toString(), DES_KEY);
				String key = KYEncrypt.MD5((this.agent+timestamp+MD5_KEY));
				
				
				String result = getUrl(API_URL + "?param="+des+"&key="+key + "&agent="+this.agent + "&timestamp="+timestamp);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , result );
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( object.has("d") ) {
					JSONObject data = object.getJSONObject("d");
					if(data.getString("code").equals("0")) {
						
						return Enum_MSG.成功.message(data.getString("money"));
					} else {
						return Enum_MSG.失败.message(data.getString("code"), "查询余额异常");
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
				params.append("s=3");
				params.append("&account=");
				params.append(entity.get("username"));
				params.append("&money=");
				params.append(entity.get("money"));
				params.append("&orderid=");
				params.append(this.agent.concat(sdf2.format(new Date())));
				
				String timestamp = System.currentTimeMillis() + "";
//				System.out.println("agent："+this.agent);
//				System.out.println("DES_KEY："+this.DES_KEY);
//				System.out.println("MD5_KEY："+this.MD5_KEY);
//				System.out.println("timestamp："+timestamp);
//				System.out.println("业务参数原文："+params);
				
				
				String des = KYEncrypt.AESEncrypt(params.toString(), DES_KEY);
				String key = KYEncrypt.MD5((this.agent+timestamp+MD5_KEY));
				
				
				String result = getUrl(API_URL + "?param="+des+"&key="+key + "&agent="+this.agent + "&timestamp="+timestamp);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() , result );
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( object.has("d") ) {
					
					JSONObject data = object.getJSONObject("d");
					if(data.getString("code").equals("0")) {
						return Enum_MSG.成功.message("下分成功");
					} else {
						return Enum_MSG.失败.message(data.getString("code"), "下分异常");
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
				params.append("s=2");
				params.append("&account=");
				params.append(entity.get("username"));
				params.append("&money=");
				params.append(entity.get("money"));
				params.append("&orderid=");
				params.append(this.agent.concat(sdf2.format(new Date())));
				
				String timestamp = System.currentTimeMillis() + "";
//				System.out.println("agent："+this.agent);
//				System.out.println("DES_KEY："+this.DES_KEY);
//				System.out.println("MD5_KEY："+this.MD5_KEY);
//				System.out.println("timestamp："+timestamp);
//				System.out.println("业务参数原文："+params);
				
				
				String des = KYEncrypt.AESEncrypt(params.toString(), DES_KEY);
				String key = KYEncrypt.MD5((this.agent+timestamp+MD5_KEY));
				
				
				String result = getUrl(API_URL + "?param="+des+"&key="+key + "&agent="+this.agent + "&timestamp="+timestamp);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() , result );
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				
				if( object.has("d") ) {
					JSONObject data = object.getJSONObject("d");
					if(data.getString("code").equals("0")) {
						
						return Enum_MSG.成功.message("上分成功");
					} else {
						return Enum_MSG.失败.message(data.getString("code"), "上分异常");
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
		params.append(this.agent);
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
		String jsonResult = getUrl(API_URL + "?params="+des+"&key="+key + "&agentname="+this.agent+ "&agentpwd="+this.agent);//完整请求链接URL
		
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
				
				String result = getUrl(API_URL + "?params="+des+"&key="+key + "&agentname="+this.agent+ "&agentpwd="+this.agent);
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
			if(MapUtil.isNulls(entity, "orderid")){	
				
				StringBuilder params = new StringBuilder();
				params.append("s=4");
				params.append(entity.get("money"));
				params.append("&orderid=");
				params.append(entity.get("orderid"));
				
				String timestamp = System.currentTimeMillis() + "";
//				System.out.println("agent："+this.agent);
//				System.out.println("DES_KEY："+this.DES_KEY);
//				System.out.println("MD5_KEY："+this.MD5_KEY);
//				System.out.println("timestamp："+timestamp);
//				System.out.println("业务参数原文："+params);
				
				
				String des = KYEncrypt.AESEncrypt(params.toString(), DES_KEY);
				String key = KYEncrypt.MD5((this.agent+timestamp+MD5_KEY));
				
				
				String result = getUrl(API_URL + "?param="+des+"&key="+key + "&agent="+this.agent + "&timestamp="+timestamp);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() , result );
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if( object.has("d") ) {
					
					JSONObject data = object.getJSONObject("d");
					if(data.getString("code").equals("0")) {
						if(data.getString("status").equals("1")) {
							return Enum_MSG.成功.message("订单交易成功");
						} else if(data.getString("status").equals("-1")) {
							return Enum_MSG.失败.message("订单交易不成功");
						} else if(data.getString("status").equals("0")) {
							return Enum_MSG.失败.message("订单交易不失败");
						}
						return Enum_MSG.失败.message(data.getString("status"), "查询订单异常");
					} else {
						return Enum_MSG.失败.message(data.getString("code"), "查询订单异常");
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
				
				return createAccount(entity);
				
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
		KYGameAPI nhq = new KYGameAPI();
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("username", RandomString.createRandomString(20));
		entity.put("password", "12345678");
		System.out.println(nhq.createAccount(entity));
		
		
//		entity.put("username", "hyeding3309");
//		entity.put("password", "d23443309");
		
		
		entity.put("type", "confirmwithdraw");
		entity.put("deviceType", "1");
		
		
		
		entity.put("money", "5");
		entity.put("orderid", RandomString.createRandomString(30));
		System.out.println(nhq.deposit(entity));
		
		entity.put("money", "4");
		entity.put("orderid", RandomString.createRandomString(30));
		System.out.println(nhq.withdraw(entity));
		
		System.out.println(nhq.getBalance(entity));
		
		System.out.println(nhq.getOrder(entity));
		
		System.out.println(nhq.login(entity));
		
//		System.out.println(nhq.updateInfo(entity));//username,password,newpassword
		
//		System.out.println(nhq.getRecord(entity));
		
		//http://api.yx.ihuyi.com/webservice/sms.php?method=Submit&account=APIID&password=APIKEY&mobile=手机号码&content=短信内容【签名】
	}
}
