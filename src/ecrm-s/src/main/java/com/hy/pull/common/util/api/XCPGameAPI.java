package com.hy.pull.common.util.api;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.game.xcp.XCPUtil;
import com.maven.config.SystemConstant;
import com.maven.util.RandomString;

import net.sf.json.JSONObject;

/**
 * 彩票游戏接口
 * @author temdy
 */
public class XCPGameAPI implements BaseInterface {
	
	private String API_URL = "http://www.webapi888.com/";
	private String agent = "APIZDDWGJ";
	private String FIRST_KEY = "bfi3j8effifjnwzi";
	private String LAST_KEY = "0lx499eliuds83oj";
	private String DES_KEY = "abcd1234";
	private String userpoint = "0";//返点，需要与代理具体沟通
	
	
	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	
	/**
	 * 默认构造函数
	 */
	@Deprecated
	public XCPGameAPI(){
		
	}
	
	/**
	 * 构建函数，初始化参数
	 * @param API_URL 接口URL
	 * @param FIRST_KEY 第一个KEY
	 * @param LAST_KEY 最后一个KEY
	 * @param DES_KEY 密钥KEY
	 */
	public XCPGameAPI(String API_URL,String FIRST_KEY,String LAST_KEY,String DES_KEY,String agent,String userpoint, String GAME_API_URL){
		this.API_URL = API_URL;
		this.FIRST_KEY = FIRST_KEY;
		this.LAST_KEY = LAST_KEY;
		this.DES_KEY = DES_KEY;
		this.agent = agent;
		this.userpoint = userpoint;
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

				StringBuilder param = new StringBuilder();
				param.append("username=");
				param.append(entity.get("username"));
				param.append("&type=1");
				param.append("&password=");
				param.append(entity.get("password"));
				param.append("&agent=");
				param.append(this.agent);
				param.append("&userpoint=");
				param.append(this.userpoint);//
				String url = getUrl(param.toString());
				String result = XCPUtil.getUrl(url);
				System.out.println("请求结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				if(object.getString("code").equals("200")) {
					return Enum_MSG.成功.message(new String(object.getString("msg").getBytes("UTF-8") , "UTF-8"));
				} else {
					if(object.getString("code").equals("415")) {
						return Enum_MSG.账号已存在.message(object.getString("msg"));
					}
					return Enum_MSG.失败.message(object.getString("code"), object.getString("msg"));
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
	 * type 操作类型(6)
	 * @return 返回结果
	 */
	@Override
	public Object getBalance(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() );
		try {
			if (MapUtil.isNulls(entity, "username")) {
				StringBuilder param = new StringBuilder();
				param.append("username=");
				param.append(entity.get("username"));
				param.append("&agent=");
				param.append(this.agent);
				param.append("&type=6");
				String url = getUrl(param.toString());
				String result = XCPUtil.getUrl(url);
				System.out.println("请求结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				if(object.getString("code").equals("200")) {
					return Enum_MSG.成功.message(new String(object.getString("msg").getBytes("UTF-8") , "UTF-8"));
				} else {
					return Enum_MSG.失败.message(object.getString("code"), object.getString("msg"));
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
	 * @param entity 参数列表
	 * username 用户名
	 * agent 代理帐号（由代理方提供）
	 * fMoney 充值金额
	 * ordernum 订单号（用户自己定义）
	 * @return 返回结果
	 */
	@Override
	public Object withdraw(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString()  );
		try {
			if (MapUtil.isNulls(entity, "username,fMoney,ordernum")) {
				
				if( String.valueOf(entity.get("ordernum")).length() > 32 ) {
					return Enum_MSG.参数错误.message("订单号长度不能超过32位");
				}
				if( Double.valueOf(entity.get("fMoney").toString()) < 1.0 ) {
					return Enum_MSG.参数错误.message("取款金额不能小于1元");
				}
				
				
				StringBuilder param = new StringBuilder();
				param.append("username=");
				param.append(entity.get("username"));
				param.append("&type=4");
				param.append("&agent=");
				param.append(this.agent);
				param.append("&fMoney=");
				param.append(entity.get("fMoney"));
				param.append("&ordernum=");
				param.append(entity.get("ordernum"));
				String url = getUrl(param.toString());
				String result = XCPUtil.getUrl(url);
				System.out.println("请求结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				if(object.getString("code").equals("200")) {
					return Enum_MSG.成功.message(new String(object.getString("msg").getBytes("UTF-8") , "UTF-8"));
				} else {
					return Enum_MSG.失败.message(object.getString("code"), object.getString("msg"));
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
	 * @param entity 参数列表
	 * username 用户名
	 * agent 代理帐号（由代理方提供）
	 * fMoney 充值金额
	 * ordernum 订单号（用户自己定义）
	 * @return 返回结果
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString()  );
		try {
			if (MapUtil.isNulls(entity, "username,fMoney,ordernum")) {
				
				if( String.valueOf(entity.get("ordernum")).length() > 32 ) {
					return Enum_MSG.参数错误.message("订单号长度不能超过32位");
				}
				
				
				StringBuilder param = new StringBuilder();
				param.append("username=");
				param.append(entity.get("username"));
				param.append("&type=5");
				param.append("&agent=");
				param.append(this.agent);
				param.append("&fMoney=");
				param.append(entity.get("fMoney"));
				param.append("&ordernum=");
				param.append(entity.get("ordernum"));
				String url = getUrl(param.toString());
				
				String result = XCPUtil.getUrl(url);
				System.out.println("请求结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				if(object.getString("code").equals("200")) {
					return Enum_MSG.成功.message(new String(object.getString("msg").getBytes("UTF-8") , "UTF-8"));
				} else {
					return Enum_MSG.失败.message(object.getString("code"), object.getString("msg"));
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
				
				StringBuilder param = new StringBuilder();
				param.append("username=");
				param.append(entity.get("username"));
				param.append("&type=7");
				param.append("&agent=");
				param.append(this.agent);
				param.append("&ordernum=");
				param.append(entity.get("ordernum"));
				String url = getUrl(param.toString());
				
				String result = XCPUtil.getUrl(url);
				System.out.println("请求结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				if(object.getString("code").equals("200")) {
					return Enum_MSG.成功.message(new String(object.getString("msg").getBytes("UTF-8") , "UTF-8"));
				} else {
					return Enum_MSG.失败.message(object.getString("code"), object.getString("msg"));
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

				StringBuilder param = new StringBuilder();
				param.append("username=");
				param.append(entity.get("username"));
				param.append("&type=2");//登录
				param.append("&password=");
				param.append(entity.get("password"));
				param.append("&agent=");
				param.append(this.agent);
				param.append("&userpoint=");
				param.append(this.userpoint);
				param.append("&refresh=");
				param.append("1");//1=自动跳转 。非1或不加不跳转 
				String urlx = getUrl(param.toString());
				
//				urlx = urlx.replaceAll("webApis2.php", "webApis.php");
				
				/****/
				String result = XCPUtil.getUrl(urlx);
				System.out.println("请求结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() , result );
				
				
				return Enum_MSG.成功.message(urlx);//返回跳转地址
				
				/*
				JSONObject object = JSONObject.fromObject(result);
				if(object.getString("code").equals("200")) {
					return Enum_MSG.成功.message(new String(object.getString("msg").getBytes("UTF-8") , "UTF-8"));
				} else {
					return Enum_MSG.失败.message(object.getString("code"), object.getString("msg"));
				}
				*/
				
				/*
				String API_ROOT = SystemConstant.getProperties("API_ROOT");
				StringBuilder url = new StringBuilder();
				url.append(API_ROOT);//API路径
				url.append("/xcpgame/index");
				url.append("?url=").append( URLEncoder.encode(urlx,"UTF-8") ).append("&");//
				
				return Enum_MSG.成功.message(url.toString());//返回跳转地址
				*/
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
	
	/**
	 * 获取URL
	 * @param param 参数
	 * @return 返回URL
	 */
	public String getUrl(String param){
		String url = null;
		try {
			System.out.println("参数原文："+param);
			String des = XCPUtil.encrypt(param, DES_KEY);
			String md5 = XCPUtil.MD5(FIRST_KEY.concat(des).concat(LAST_KEY));
			url = API_URL.concat("_api/webApis2.php?RetailParams=").concat(md5).concat(des);
			System.out.println("完整URL："+url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	public static void main(String[] arg){
		XCPGameAPI xcp = new XCPGameAPI();
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("username", RandomString.createRandomString(50));
		entity.put("password", RandomString.createRandomString(50));
		
		entity.put("fMoney", "50");
		entity.put("ordernum", RandomString.createRandomString(32));
		
		entity.put("username", "qajFw@#&1_SfUDOvJU5");//#&*
		entity.put("password", "XrgJACmn");
		System.out.println(xcp.createAccount(entity));
//		System.out.println(xcp.getBalance(entity));
//		System.out.println(xcp.deposit(entity));
//		System.out.println(xcp.withdraw(entity));
//		System.out.println(xcp.getOrder(entity));
//		System.out.println(xcp.login(entity));
	}
}
