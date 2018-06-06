package com.hy.pull.common.util.api;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.game.xcp.XCPUtil;
import com.maven.config.SystemConstant;
import com.maven.util.Encrypt;
import com.maven.util.RandomString;

import net.sf.json.JSONObject;

/**
 * 德州电子游戏
 * @author temdy
 */
public class DZDYGameAPI implements BaseInterface {
	
	private String API_URL = "http://103.230.243.85:96/";
	private String API_URL_LOGIN = "http://mqdzpk.oss-cn-shanghai.aliyuncs.com/html5/demo/index.html";
	private String channelid = "iq6sebo0h7c87y0v";
	private String channelpwd = "miqu*7@";
	
	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	/**
	 * 默认构造函数
	 */
	@Deprecated
	public DZDYGameAPI(){
		if(API_URL.endsWith("/")) {
			API_URL = API_URL.substring(0, API_URL.length()-1);
		}
	}
	
	/**
	 * 构建函数，初始化参数
	 * @param API_URL
	 * @param channelid
	 * @param channelpwd
	 */
	public DZDYGameAPI(String API_URL,String channelid,String channelpwd, String API_URL_LOGIN, String GAME_API_URL){
		if(API_URL.endsWith("/")) {
			API_URL = API_URL.substring(0, API_URL.length()-1);
		}
		this.API_URL = API_URL;
		this.channelid = channelid;
		this.channelpwd = channelpwd;
		this.API_URL_LOGIN = API_URL_LOGIN;
		this.GAME_API_URL = GAME_API_URL;
	}

	/**
	 * 创建用户接口
	 * @param entity 参数列表 username,password
	 * username 用户名 用户长度必须为20位
	 * password 密码 密码长度必须为8位
	 * @return 返回结果
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() );
		try {
			if (MapUtil.isNulls(entity, "username,password")) {

				if (String.valueOf(entity.get("username")).length() > 20) {
					return Enum_MSG.参数错误.message("用户长度不能大于20位");
				}
				if (String.valueOf(entity.get("password")).length() > 20) {
					return Enum_MSG.参数错误.message("密码长度不能大于20位");
				}
				
				String time = System.currentTimeMillis() + "";
				StringBuilder param = new StringBuilder();
				param.append("s=");
				param.append("2");//操作类型1:登录,2:注册
				param.append("&accounts=");
				param.append(entity.get("username"));
				param.append("&clientIP=");
				param.append("192.168.1.1");
				param.append("&channelid=");
				param.append(this.channelid);
				param.append("&time=");
				param.append(time);
				param.append("&encryption=");
				param.append(Encrypt.MD5(time+this.channelpwd));
				
				String url = API_URL.concat("/accountHandle?").concat(param.toString());
				String result = XCPUtil.getUrl(url);
				System.out.println("请求结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				if(object.getJSONObject("d").getString("code").equals("0")) {
					return Enum_MSG.成功.message(object);
				} else {
					if(object.getJSONObject("d").getString("code").equals("135")) {//
						return Enum_MSG.账号已存在.message("账号已存在");
					}
					return Enum_MSG.失败.message(object);
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
	 * 获取余额
	 * @param entity 参数列表 username
	 * username 用户名
	 * @return 返回结果
	 */
	@Override
	public Object getBalance(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() );
		try {
			if (MapUtil.isNulls(entity, "username")) {

				String time = System.currentTimeMillis() + "";
				StringBuilder param = new StringBuilder();
				param.append("s=");
				param.append("1");//操作类型1:查询余额
				param.append("&accounts=");
				param.append(entity.get("username"));
				param.append("&channelid=");
				param.append(this.channelid);
				param.append("&time=");
				param.append(time);
				param.append("&encryption=");
				param.append(Encrypt.MD5(time+this.channelpwd));
				
				String url = API_URL.concat("/moneyHandle?").concat(param.toString());
				String result = XCPUtil.getUrl(url);
				System.out.println("请求结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				if(object.getJSONObject("d").getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getJSONObject("d").getString("gameMoney"));
				} else {
					return Enum_MSG.失败.message(object);
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
	 * 下分（取款）的接口
	 * @param entity 参数列表 username,money
	 * username 用户名
	 * @return 返回结果 sign即单号
	 */
	@Override
	public Object withdraw(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString()  );
		try {
			if (MapUtil.isNulls(entity, "username,money")) {
				
				
				if( Double.valueOf(entity.get("money").toString()) < 1.0 ) {
					return Enum_MSG.参数错误.message("取款金额不能小于1元");
				}
				
				
				String time = System.currentTimeMillis() + "";
				StringBuilder param = new StringBuilder();
				param.append("s=");
				param.append("3");//操作类型3:取款
				param.append("&accounts=");
				param.append(entity.get("username"));
				param.append("&clientIP=");
				param.append("192.168.1.1");
				param.append("&channelid=");
				param.append(this.channelid);
				param.append("&time=");
				param.append(time);
				param.append("&getMoney=");
				param.append(entity.get("money"));
				param.append("&encryption=");
				param.append(Encrypt.MD5(time+this.channelpwd));
				
				String url = API_URL.concat("/moneyHandle?").concat(param.toString());
				String result = XCPUtil.getUrl(url);
				System.out.println("请求结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				if(object.getJSONObject("d").getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getJSONObject("d").getString("sign"));
				} else {
					return Enum_MSG.失败.message(object);
				}
				
				
			}  else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			} 
		} catch (Exception e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}
	}

	/**
	 * 上分（存款）的接口
	 * @param entity 参数列表 username,money
	 * username 用户名
	 * @return 返回结果 sign即单号
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString()  );
		try {
			if (MapUtil.isNulls(entity, "username,money")) {
				
				
				if( Double.valueOf(entity.get("money").toString()) < 1.0 ) {
					return Enum_MSG.参数错误.message("取款金额不能小于1元");
				}
				
				
				String time = System.currentTimeMillis() + "";
				StringBuilder param = new StringBuilder();
				param.append("s=");
				param.append("2");//操作类型2:存款
				param.append("&accounts=");
				param.append(entity.get("username"));
				param.append("&clientIP=");
				param.append("192.168.1.1");
				param.append("&channelid=");
				param.append(this.channelid);
				param.append("&time=");
				param.append(time);
				param.append("&setMoney=");
				param.append(entity.get("money"));
				param.append("&encryption=");
				param.append(Encrypt.MD5(time+this.channelpwd));
				
				String url = API_URL.concat("/moneyHandle?").concat(param.toString());
				String result = XCPUtil.getUrl(url);
				System.out.println("请求结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				if(object.getJSONObject("d").getString("code").equals("0")) {
					return Enum_MSG.成功.message(object.getJSONObject("d").getString("sign"));
				} else {
					return Enum_MSG.失败.message(object);
				}
				
				
			}  else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
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
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获取订单接口
	 * @param entity 参数列表 username,ordernum
	 * @return 返回结果
	 */
	public Object getOrder(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString()  );
		try {
			if (MapUtil.isNulls(entity, "username,ordernum")) {
				
				String time = System.currentTimeMillis() + "";
				StringBuilder param = new StringBuilder();
				param.append("s=");
				param.append("4");//操作类型3:订单验证
				param.append("&accounts=");
				param.append(entity.get("username"));
				param.append("&clientIP=");
				param.append("192.168.1.1");
				param.append("&channelid=");
				param.append(this.channelid);
				param.append("&time=");
				param.append(time);
				param.append("&sign=");
				param.append(entity.get("ordernum"));
				param.append("&encryption=");
				param.append(Encrypt.MD5(time+this.channelpwd));
				System.out.println(param);
				String url = API_URL.concat("/moneyHandle?").concat(param.toString());
				String result = XCPUtil.getUrl(url);
				System.out.println("请求结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				if(object.getJSONObject("d").getString("code").equals("0")) {
					return Enum_MSG.成功.message(object);
				} else {
					return Enum_MSG.失败.message(object);
				}
				
				
			}  else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}
	}

	/**
	 * 登录的接口 只有PC版本，没有手机版本
	 * 
	 * @param entity 参数列表
	 * username 用户名
	 * password 密码
	 * @return 返回结果
	 */
	@Override
	public Object login(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString()  );
		try {
			if (MapUtil.isNulls(entity, "username,password")) {

//				if (String.valueOf(entity.get("username")).length() != 20) {
//					return Enum_MSG.参数错误.message("用户长度必须为20位");
//				}
//				if (String.valueOf(entity.get("password")).length() != 8) {
//					return Enum_MSG.参数错误.message("密码长度必须为8位");
//				}
				
				String time = System.currentTimeMillis() + "";
				StringBuilder param = new StringBuilder();
				param.append("s=");
				param.append("1");//操作类型1:登录,2:注册
				param.append("&accounts=");
				param.append(entity.get("username"));
				param.append("&clientIP=");
				param.append("192.168.1.1");
				param.append("&channelid=");
				param.append(this.channelid);
				param.append("&time=");
				param.append(time);
				param.append("&encryption=");
				param.append(Encrypt.MD5(time+this.channelpwd));
				
				String url = API_URL.concat("/accountHandle?").concat(param.toString());
				String result = XCPUtil.getUrl(url);
				System.out.println("请求结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				if(object.getJSONObject("d").getString("code").equals("0")) {
					
					String token = object.getJSONObject("d").getString("sid");
					
					return Enum_MSG.成功.message(this.API_URL_LOGIN+"?token="+token+"&account="+entity.get("username"));
					
				} else {
					return Enum_MSG.失败.message(object);
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
	 * 是否在线接口
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	@Override
	public Object isOnLine(Map<String, Object> entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public static void main(String[] arg){
		DZDYGameAPI xcp = new DZDYGameAPI();
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("username", RandomString.createRandomString(20));
		entity.put("password", RandomString.createRandomString(8));
		
		entity.put("money", "100");
		entity.put("ordernum", RandomString.createRandomString(32));
		
//		entity.put("username", "Q4U*uxf213&351_C@WZx");//*#
		System.out.println(xcp.createAccount(entity));
//		System.out.println(xcp.deposit(entity));
//		System.out.println(xcp.withdraw(entity));
//		System.out.println(xcp.getBalance(entity));
		
		entity.put("ordernum", "58491424");
//		System.out.println(xcp.getOrder(entity));
//		System.out.println(xcp.login(entity));
		//
		String API_URL = "http://103.230.243.85:96/";
		if(API_URL.endsWith("/")) {
			API_URL = API_URL.substring(0, API_URL.length()-1);
		}
		System.out.println(API_URL);
	}
}
