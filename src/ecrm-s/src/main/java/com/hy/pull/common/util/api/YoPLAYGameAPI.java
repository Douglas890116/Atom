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
import com.hy.pull.common.util.YoPlayDESEncrypt;
import com.hy.pull.common.util.game.NHQGame;
import com.hy.pull.common.util.game.sa.DeEnCode;
import com.hy.pull.common.util.game.sa.XmlUtil;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;

import net.sf.json.JSONObject;


/**
 * YOPLAY游戏接口
 * @author temdy
 */
public class YoPLAYGameAPI implements BaseInterface {
	
	private String API_URL = "http://join.jdpayment.com:81/doService.do";
	private String API_LOGIN_URL = "http://game.jdpayment.com/forwardGame.do";
	
	private String MD5_KEY = "PvT2rmsQxmBn";
	private String DES_KEY = "AvPFaZWB";
	
	private String agentid = "Y09";
	private String agentname = "ejiny09real";
	private String agentpwd = "39feb5d3ba1cebdf083f22a9b149e3c4";
	
	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	
	/**
	 * 默认构造函数
	 */
	@Deprecated
	public YoPLAYGameAPI(){
		
	}
	
	/**
	 * 构建函数，初始化参数
	 * @param API_URL API接口URL
	 * @param MD5_KEY MD5密钥
	 */
	public YoPLAYGameAPI(String API_URL,String MD5_KEY,String DES_KEY, String agentid, String agentname, String agentpwd,String API_LOGIN_URL,String GAME_API_URL){
		this.API_URL = API_URL;
		this.MD5_KEY = MD5_KEY;
		this.DES_KEY = DES_KEY;
		this.API_LOGIN_URL = API_LOGIN_URL;
		this.agentid = agentid;
		this.agentname = agentname;
		this.agentpwd = agentpwd;
		this.GAME_API_URL = GAME_API_URL;
	}

	/**
	 * 创建用户接口
	 * @param entity 参数列表 loginname,password,acctype
	 * 
	 * username 用户名 大于6小于20（建议加前缀）
	 * password 密码 大于6小于20
	 * 
	 * acctype= 1 代表真錢账号; 0 代表试玩账号 
	 * @return 返回结果
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString()  );
		try{
			if(MapUtil.isNulls(entity, "loginname,password,acctype")){	
				
				if( String.valueOf(entity.get("loginname")).length() > 20 ) {
					return Enum_MSG.参数错误.message("用户长度不能大于20位，当前"+String.valueOf(entity.get("loginname")).length())+"位";
				}
				if( String.valueOf(entity.get("password")).length() > 20 ) {
					return Enum_MSG.参数错误.message("密码长度不能大于20位，当前"+String.valueOf(entity.get("password")).length())+"位";
				}
				
				StringBuilder params = new StringBuilder();
				params.append("cid=");
				params.append(agentid);
				params.append("&loginname=");
				params.append(entity.get("loginname"));
				params.append("&password=");
				params.append(RandomString.createRandomString(4)+entity.get("password")+RandomString.createRandomString(6));
				params.append("&action=");
				params.append("lg");
				params.append("&acctype=");
				params.append(entity.get("acctype"));
				params.append("&agent=");
				params.append(agentname);
				
				System.out.println("参数原文："+params);
				String des = YoPlayDESEncrypt.encrypt(DES_KEY, params.toString());
				String key = YoPlayDESEncrypt.string2MD5(params.toString(), agentpwd, MD5_KEY);
				
				String result = getUrl(API_URL + "?params="+des+"&key="+key );
				System.out.println("调用结果："+result);
				if(result.equals("-1")) {
					Enum_MSG.接口调用错误.message("网络异常");
				}
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , result );
				Map<String, String> data = XmlUtil.getQueryParamsByTAG(result);
				
				//接口调用成功
				if(data.get("info").equals("0")) {
					
					return Enum_MSG.成功.message(data.get("msg"));
					
				} else {
					
					return Enum_MSG.失败.message(data.get("info"), data.get("msg"));
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
	 * @param entity 参数列表 loginname,password,acctype
	 * 
	 * username 用户名
	 * @return 返回结果
	 */
	@Override
	public Object getBalance(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString()  );
		try{
			if(MapUtil.isNulls(entity, "loginname,password,acctype")){	
				
//				if( String.valueOf(entity.get("username")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户名长度必须为20位，当前"+String.valueOf(entity.get("username")).length())+"位";
//				}
				
				StringBuilder params = new StringBuilder();
				params.append("cid=");
				params.append(agentid);
				params.append("&loginname=");
				params.append(entity.get("loginname"));
				params.append("&password=");
				params.append(RandomString.createRandomString(4)+entity.get("password")+RandomString.createRandomString(6));
				params.append("&action=");
				params.append("gb");
				params.append("&acctype=");
				params.append(entity.get("acctype"));
				params.append("&agent=");
				params.append(agentname);
				
				String des = YoPlayDESEncrypt.encrypt(DES_KEY, params.toString());
				String key = YoPlayDESEncrypt.string2MD5(params.toString(), agentpwd, MD5_KEY);
				
				String result = getUrl(API_URL + "?params="+des+"&key="+key );
				System.out.println("调用结果："+result);
				if(result.equals("-1")) {
					Enum_MSG.接口调用错误.message("网络异常");
				}
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , result );
				Map<String, String> data = XmlUtil.getQueryParamsByTAG(result);
				
				//接口调用成功
				return Enum_MSG.成功.message(data.get("info"));
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
	}
	

	private Object doWithdraw(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "doWithdraw", entity.toString() );
		try{
			if(MapUtil.isNulls(entity, "loginname,password,acctype,money,method,billno")){	
				
				if( Double.valueOf(entity.get("money").toString()) < 1.0 ) {
					return Enum_MSG.参数错误.message("金额不能小于1元");
				}
				if( String.valueOf(entity.get("billno")).length() != 16 ) {
					return Enum_MSG.参数错误.message("订单号必须是16位数的数字，不含代理账号");
				}
				if( String.valueOf(entity.get("billno")).startsWith(this.agentid) ) {
					return Enum_MSG.参数错误.message("订单号不含代理账号");
				}
				
				StringBuilder params = new StringBuilder();
				params.append("cid=");
				params.append(agentid);
				params.append("&loginname=");
				params.append(entity.get("loginname"));
				params.append("&password=");
				params.append(RandomString.createRandomString(4)+entity.get("password")+RandomString.createRandomString(6));
				params.append("&action=");
				params.append(entity.get("method"));
				params.append("&billno=");
				params.append(this.agentid + entity.get("billno").toString());
				params.append("&type=OUT");//OUT: 從遊戲账號转款到網站賬號
				params.append("&credit=");
				params.append(entity.get("money"));
				params.append("&acctype=");
				params.append(entity.get("acctype"));
				if(entity.containsKey("flag")) {
					params.append("&flag=");
					params.append(entity.get("flag"));
				}
				params.append("&agent=");
				params.append(agentname);
				
				String des = YoPlayDESEncrypt.encrypt(DES_KEY, params.toString());
				String key = YoPlayDESEncrypt.string2MD5(params.toString(), agentpwd, MD5_KEY);
				
				String result = getUrl(API_URL + "?params="+des+"&key="+key );
				System.out.println("调用结果："+result);
				if(result.equals("-1")) {
					Enum_MSG.接口调用错误.message("网络异常");
				}
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "doWithdraw", entity.toString() , result );
				
				//接口调用成功
				Map<String, String> data = XmlUtil.getQueryParamsByTAG(result);
				
				//接口调用成功
				if(data.get("info").equals("0")) {
					
					
					return Enum_MSG.成功.message(data.get("msg"));
					
				} else {
					return Enum_MSG.失败.message(data.get("info"), data.get("msg"));
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
	 * @param entity 参数列表 loginname,password,acctype,money,billno
	 * 
	 * username 用户名
	 * money 分数
	 * @return 返回结果 返回订单确认码，需要存储起来
	 */
	@Override
	public Object withdraw(Map<String, Object> entity) {
		entity.put("method", "tc");
		
		JSONObject result = JSONObject.fromObject(doWithdraw(entity).toString());
		
		//预备转账操作成功
		if(result.getString("code").equals("0")) {
			entity.put("flag", "1");
		} else {
			entity.put("flag", "0");
		}
		
		entity.put("method", "tcc");
		return doWithdraw(entity);
	}
	
	private Object doDeposit(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "doDeposit", entity.toString() );
		try {
			if(MapUtil.isNulls(entity, "loginname,password,acctype,money,method,billno")){	
				
				if( Double.valueOf(entity.get("money").toString()) < 1.0 ) {
					return Enum_MSG.参数错误.message("金额不能小于1元");
				}
				if( String.valueOf(entity.get("billno")).length() != 16 ) {
					return Enum_MSG.参数错误.message("订单号必须是16位数的数字，不含代理账号");
				}
				if( String.valueOf(entity.get("billno")).startsWith(this.agentid) ) {
					return Enum_MSG.参数错误.message("订单号不含代理账号");
				}
				
				StringBuilder params = new StringBuilder();
				params.append("cid=");
				params.append(agentid);
				params.append("&loginname=");
				params.append(entity.get("loginname"));
				params.append("&password=");
				params.append(RandomString.createRandomString(4)+entity.get("password")+RandomString.createRandomString(6));
				params.append("&action=");
				params.append(entity.get("method"));
				params.append("&billno=");
				params.append(this.agentid + entity.get("billno").toString());
				params.append("&type=IN");//OUT: 從遊戲账號转款到網站賬號
				params.append("&credit=");
				params.append(entity.get("money"));
				params.append("&acctype=");
				params.append(entity.get("acctype"));
				if(entity.containsKey("flag")) {
					params.append("&flag=");
					params.append(entity.get("flag"));
				}
				params.append("&agent=");
				params.append(agentname);
				
				String des = YoPlayDESEncrypt.encrypt(DES_KEY, params.toString());
				String key = YoPlayDESEncrypt.string2MD5(params.toString(), agentpwd, MD5_KEY);
				
				String result = getUrl(API_URL + "?params="+des+"&key="+key );
				System.out.println("调用结果："+result);
				if(result.equals("-1")) {
					Enum_MSG.接口调用错误.message("网络异常");
				}
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "doDeposit", entity.toString() , result );
				
				//接口调用成功
				Map<String, String> data = XmlUtil.getQueryParamsByTAG(result);
				
				//接口调用成功
				if(data.get("info").equals("0")) {
					
					
					return Enum_MSG.成功.message(data.get("msg"));
					
				} else {
					return Enum_MSG.失败.message(data.get("info"), data.get("msg"));
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}
	}
	
	/**
	 * 上分（存款）的接口
	 * @param entity 参数列表 loginname,password,acctype,money,billno
	 * 
	 * username 用户名
	 * money 分数
	 * @return 返回结果 返回订单确认码，需要存储起来
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		entity.put("method", "tc");
		
		JSONObject result = JSONObject.fromObject(doDeposit(entity).toString());
		
		//预备转账操作成功
		if(result.getString("code").equals("0")) {
			entity.put("flag", "1");
		} else {
			entity.put("flag", "0");
		}
		
		entity.put("method", "tcc");
		return doDeposit(entity);
		
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
	 * @param entity 参数列表 acctype,billno
	 * 
	 * 
	 * @return 返回结果 返回单号
	 */
	@Override
	public Object getOrder(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString()  );
		try{
			
			if(MapUtil.isNulls(entity, "acctype,billno")){	
				
				if( String.valueOf(entity.get("billno")).length() != 16 ) {
					return Enum_MSG.参数错误.message("订单号必须是16位数的数字，不含代理账号");
				}
				if( String.valueOf(entity.get("billno")).startsWith(this.agentid) ) {
					return Enum_MSG.参数错误.message("订单号不含代理账号");
				}
				
				StringBuilder params = new StringBuilder();
				params.append("cid=");
				params.append(agentid);
				params.append("&action=");
				params.append("qos");
				params.append("&billno=");
				params.append(this.agentid + entity.get("billno").toString());
				params.append("&acctype=");
				params.append(entity.get("acctype"));
				params.append("&agent=");
				params.append(agentname);
				
				String des = YoPlayDESEncrypt.encrypt(DES_KEY, params.toString());
				String key = YoPlayDESEncrypt.string2MD5(params.toString(), agentpwd, MD5_KEY);
				
				String result = getUrl(API_URL + "?params="+des+"&key="+key );
				System.out.println("调用结果："+result);
				if(result.equals("-1")) {
					Enum_MSG.接口调用错误.message("网络异常");
				}
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() , result );
				
				//接口调用成功
				Map<String, String> data = XmlUtil.getQueryParamsByTAG(result);
				
				//接口调用成功
				if(data.get("info").equals("0")) {
					
					
					return Enum_MSG.成功.message(data.get("msg"));
					
				} else {
					return Enum_MSG.失败.message(data.get("info"), data.get("msg"));
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
	 * @param entity 参数列表 loginname,password,acctype,lang,gameType
	 * username 用户名
	 * password 密码
	 * lang 语言选择
	 * @return 返回结果
	 */
	@Override
	public Object login(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString()  );
		try{
			
//			语言：
//			zh-cn (简体中文)  1 
//			zh-tw (䌓体中文）  2 
//			en-us(英语)  3 
//			euc-jp(日语)  4 
//			ko(韩语)  5 
//			th(泰文)  6 
//			vi(越南文)  8 
			
//			游戏类型：
//			YP800                yoplay大厅 
//			YP801               森林舞会单人版 
//			YP802               奔驰宝马单人版 
//			YP803               急速赛马 
//			YP804               猜猜乐 
//			YP805               水果拉霸 
//			YP806               德州牛仔 
//			YP807               飞禽走兽 
//			YP808               水果派对 
//			YP810               森林舞会多人版 
//			YP813               水果拉霸多人版 
//			YP817        百人牛牛 
			
			if(MapUtil.isNulls(entity, "loginname,password,acctype,lang,gameType")){	
				
				StringBuilder params = new StringBuilder();
				params.append("cid=");
				params.append(agentid);
				params.append("&loginname=");
				params.append(entity.get("loginname"));
				params.append("&password=");
				params.append(entity.get("password"));
				params.append("&acctype=");
				params.append(entity.get("acctype"));
				params.append("&lang=");
				params.append(entity.get("lang"));
				params.append("&gameType=");
				params.append(entity.get("gameType"));
				params.append("&agent=");
				params.append(agentname);
				
				String des = YoPlayDESEncrypt.encrypt(DES_KEY, params.toString());
				String key = YoPlayDESEncrypt.string2MD5Login(des,  MD5_KEY);
				
				
				StringBuilder url = new StringBuilder();
				url.append(this.GAME_API_URL);//API路径
				url.append("/yoplaygame/index");
				
				url.append("?params=").append(des).append("&");//
				url.append("key=").append(key).append("&");//
				url.append("url=").append( URLEncoder.encode(this.API_LOGIN_URL, "UTF-8") ).append("&");//
				
				return Enum_MSG.成功.message(url.toString());//返回跳转的地址
				
				
				
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
			System.out.println("请求链接："+urls);
			
			URL url = new URL(urls);
	        HttpURLConnection urlcon = (HttpURLConnection)url.openConnection();
	        int timeout = 30000;//注意：使用Httpclient调用接口的时候，请设置timeout》=30秒
	        urlcon.setConnectTimeout(timeout);
	        urlcon.setReadTimeout(timeout);
	        urlcon.connect();
	        
	        
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
			
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "-1";
		}
	}
	
	public static void main(String[] args) throws Exception {
		YoPLAYGameAPI nhq = new YoPLAYGameAPI();
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("acctype", "1");
		
		entity.put("loginname", RandomString.createRandomString(20).toUpperCase());
		entity.put("password", "12345678");
		System.out.println(nhq.createAccount(entity));
		
		entity.put("money", "500");
		entity.put("billno", System.currentTimeMillis()+""+RandomStringNum.createRandomString(3));
		System.out.println(nhq.deposit(entity));
		
		
		entity.put("money", "1");
		entity.put("billno", System.currentTimeMillis()+""+RandomStringNum.createRandomString(3));
//		System.out.println(nhq.withdraw(entity));
		
//		System.out.println(nhq.getBalance(entity));
//		
		
//		System.out.println(nhq.getOrder(entity));//
		
		
		entity.put("lang", "1");
		entity.put("gameType", "YP800");
		System.out.println(nhq.login(entity));//loginname,password,acctype,lang,gameType
		
		
		
	}
}
