package com.hy.pull.common.util.api;

import java.util.HashMap;
import java.util.Map;

import com.hy.pull.common.util.Encrypt;
import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.game.sa.XmlUtil;
import com.hy.pull.common.util.game.zj.ZJUtil;
import com.maven.util.RandomString;
import com.maven.util.StringUtils;

import net.sf.json.JSONObject;

/**
 * 洲际游戏接口
 * 
 * 
 * 注意：洲际游戏没有独立的创建账号与登录接口，采用的是一步到位的模式：创建账号并登录到洲际游戏平台  (如账号已经存在，直接登录) 
 * 
 * @author temdy
 */
public class ZJGameAPI implements BaseInterface {
	
	private String API_URL = "http://testzjxy.cg6188.com/zhouji/app/api.do";
	private String KEY = "lilongtest_582a1d68-e662-43c3-9876-0";
	
	
	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	
	/**
	 * 默认构造函数
	 */
	@Deprecated
	public ZJGameAPI(){
		
	}
	
	/**
	 * 构建函数，初始化参数
	 * @param API_URL API接口URL
	 * @param KEY 密钥L
	 */
	public ZJGameAPI(String API_URL,String KEY,String GAME_API_URL){
		this.API_URL = API_URL;
		this.KEY = KEY;
		this.GAME_API_URL = GAME_API_URL;
	}

	/**
	 * 创建用户接口
	 * username,password,nickname
	 * 
	 * 
	 * username: 合作伙伴平台的用户ID,字符串长度少于或者等于20位,  由字母和数字组成 
	 * password: 合作伙伴平台的用户原始密码，要求ＭＤ５加密,  长度为32位
	 * currency:   合作伙伴的用户ID所使用的币别
	 * nickname:   合作伙伴的用户昵称,  字符串长度少于或者等于20位
	 * language:   游戏语言(CN,HK,EN,KR)
	 * line:    线路(可选参数)   参数(1, 2, …)   默认线路为1, 注:具体线路参数ID以游戏平台
	 * 
	 * @param entity 参数列表
	 * @return 返回结果  0=没有错误,登录成功/创建成功
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() );
		try{
			
//			返回示例
//			{"request":null,"errorCode":0,"errorMessage":null,"logId":1479892642922,"params":{"link":"http://live.cg8080.com/liveflash1/route.jsp?code=78a6e02d-c4c3-4496-8176-8df141d89036&lc=cn&line=0&prefix=0"}}

			
			if(MapUtil.isNulls(entity, "username,password,nickname")){
				
				if( String.valueOf(entity.get("username")).length() > 20 ) {
					return Enum_MSG.参数错误.message("用户长度不能大于20位");
				}
				if( String.valueOf(entity.get("password")).length() > 20 ) {
					return Enum_MSG.参数错误.message("密码长度不能大于20位");
				}
//				if( String.valueOf(entity.get("nickname")).length() != 20 ) {
//					return Enum_MSG.参数错误.message("用户昵称长度应为20个字符");
//				}
				
				JSONObject params = new JSONObject();
				params.put("username", entity.get("username"));
				params.put("password", Encrypt.MD5(entity.get("password").toString()));
				params.put("nickname", entity.get("nickname"));
				params.put("currency", entity.get("currency") == null ? "CNY" : entity.get("currency"));
				params.put("language",  entity.get("language") == null ? "CN" : entity.get("language"));
//				params.put("line", entity.get("line"));可空
				JSONObject join = new JSONObject();
				join.put("hashCode", KEY);
				join.put("command", "LOGIN");
				join.put("params", params);
				
				String result = ZJUtil.getPost(API_URL, join);
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , result );
				
				JSONObject object =  JSONObject.fromObject( result );
				
				//接口调用成功
				if( !object.has("code") ) {
					
					if(object.getString("errorCode").equals("0")) {
						
						return Enum_MSG.成功.message(object);
					} else {
						if(object.getString("errorCode").equals("6606")){//6606 用户存在，但密码错误
							return Enum_MSG.账号已存在.message(object.getString("errorMessage"));
						}
						return Enum_MSG.失败.message(object.getString("errorCode"),object.getString("errorMessage"));
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
	 * @param entity 参数列表
	 * username 合作伙伴平台的用户ID,字符串长度少于或者等于20位, 由字母和数字组成
	 * password 合作伙伴平台的用户原始密码，要求ＭＤ５加密, 长度为 32 位
	 * @return 返回结果
	 */
	@Override
	public Object getBalance(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() );
		try{
			if(MapUtil.isNulls(entity, "username,password")){
				JSONObject params = new JSONObject();
				params.put("username", entity.get("username"));
				params.put("password", Encrypt.MD5(entity.get("password").toString()));
				JSONObject join = new JSONObject();
				join.put("hashCode", KEY);
				join.put("command", "GET_BALANCE");
				join.put("params", params);
				String result = ZJUtil.getPost(API_URL, join);
				
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , result );
				
				JSONObject object =  JSONObject.fromObject( result );
				
				//接口调用成功
				if( !object.has("code") ) {
					
					if(object.getString("errorCode").equals("0")) {
						
						return Enum_MSG.成功.message(object.getJSONObject("params").getString("balance"));
					} else {
						return Enum_MSG.失败.message(object.getString("errorCode"),object.getString("errorMessage"));
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
	 * 下分（取款）的接口username,password,ref,amount
	 * 
	 * ref=32位数的长度
	 * 
	 * 
	 * @param entity 参数列表
	 * username 合作伙伴平台的用户ID,字符串长度少于或者等于20位, 由字母和数字组成
	 * password 合作伙伴平台的用户原始密码，要求ＭＤ５加密, 长度为 32 位
	 * ref 唯一交易号, 由合作伙伴平台提供, 以备查验
	 * desc 本次交易描述, 可以为空
	 * amount 存款金额, 格式(#.00)
	 * @return 返回结果  当前balance
	 */
	@Override
	public Object withdraw(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() );
		try{
			
//			{"request":null,"errorCode":0,"errorMessage":null,"logId":1479893978495,"params":{"balance":1.0}}
			
			if(MapUtil.isNulls(entity, "username,password,ref,amount")){
				
				if( String.valueOf(entity.get("ref")).length() > 32 ) {
					return Enum_MSG.参数错误.message("订单号长度不能大于32位");
				}
				if( Double.valueOf(entity.get("amount").toString()) < 1.0 ) {
					return Enum_MSG.参数错误.message("取款金额不能小于1元");
				}
				
				JSONObject params = new JSONObject();
				params.put("username", entity.get("username"));
				params.put("password", Encrypt.MD5(entity.get("password").toString()));
				params.put("ref", entity.get("ref"));
				params.put("desc", entity.get("desc") == null ? "" : entity.get("desc"));
				params.put("amount", Double.valueOf(entity.get("amount").toString()).doubleValue() * 1.00 + "");
				JSONObject join = new JSONObject();
				join.put("hashCode", KEY);
				join.put("command", "WITHDRAW");
				join.put("params", params);

				String result = ZJUtil.getPost(API_URL, join);
				
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() , result );
				
				JSONObject object =  JSONObject.fromObject( result );
				
				
				//接口调用成功
				if( !object.has("code") ) {
					
					if(object.getString("errorCode").equals("0") ) {
						
						if(object.getJSONObject("params").has("balance")) {
							return Enum_MSG.成功.message(object.getJSONObject("params").getString("balance"));
						} else {
							return Enum_MSG.失败.message(object.getString("errorMessage"));
						}
						
					} else {
						return Enum_MSG.失败.message(object.getString("errorCode"),object.getString("errorMessage"));
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
	 * 上分（存款）的接口 username,password,ref,amount
	 * 
	 * ref=32位数的长度
	 * 
	 * 
	 * @param entity 参数列表
	 * username 合作伙伴平台的用户ID,字符串长度少于或者等于20位, 由字母和数字组成
	 * password 合作伙伴平台的用户原始密码，要求ＭＤ５加密, 长度为 32 位
	 * ref 唯一交易号, 由合作伙伴平台提供, 以备查验
	 * desc 本次交易描述, 可以为空
	 * amount 存款金额, 格式(#.00)
	 * @return 返回结果 当前balance
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() );
		try{
			
//			{"request":null,"errorCode":0,"errorMessage":null,"logId":1479893978495,"params":{"balance":1.0}}
			if(MapUtil.isNulls(entity, "username,password,ref,amount")){
				
				
				if( String.valueOf(entity.get("ref")).length() > 32 ) {
					return Enum_MSG.参数错误.message("订单号长度不能大于32位");
				}
				
				JSONObject params = new JSONObject();
				params.put("username", entity.get("username"));
				params.put("password", Encrypt.MD5(entity.get("password").toString()));
				params.put("ref", entity.get("ref"));
				params.put("desc", entity.get("desc") == null ? "" : entity.get("desc"));
				params.put("amount", Double.valueOf(entity.get("amount").toString()).doubleValue() * 1.00 + "");
				JSONObject join = new JSONObject();
				join.put("hashCode", KEY);
				join.put("command", "DEPOSIT");
				join.put("params", params);
				

				String result = ZJUtil.getPost(API_URL, join);
				
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() , result );
				
				JSONObject object =  JSONObject.fromObject( result );
				
				//接口调用成功
				if( !object.has("code") ) {
					
					if(object.getString("errorCode").equals("0")) {
						
						if(object.getJSONObject("params").has("balance")) {
							return Enum_MSG.成功.message(object.getJSONObject("params").getString("balance"));
						} else {
							return Enum_MSG.失败.message(object.getString("errorMessage"));
						}
						
					} else {
						return Enum_MSG.失败.message(object.getString("errorCode"),object.getString("errorMessage"));
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
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 更新信息接口
	 * @param entity 参数列表
	 * username 合作伙伴平台的用户ID,字符串长度少于或者等于20位, 由字母和数字组成
	 * password 合作伙伴平台的用户原始密码，要求ＭＤ５加密, 长度为 32 位
	 * @return 返回结果
	 */
	@Override
	public Object updateInfo(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString() );
		try{
			if(MapUtil.isNulls(entity, "username,password,newpassword")){
				JSONObject params = new JSONObject();
				params.put("username", entity.get("username"));
				params.put("password", Encrypt.MD5(entity.get("newpassword").toString()));
				JSONObject join = new JSONObject();
				join.put("hashCode", KEY);
				join.put("command", "CHANGE_PASSWORD");
				join.put("params", params);
				
				String result = ZJUtil.getPost(API_URL, join);
				
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString() , result );
				
				JSONObject object =  JSONObject.fromObject( result );
				
				//接口调用成功
				if( !object.has("code") ) {
					
					if(object.getString("errorCode").equals("0")) {
						
						return Enum_MSG.成功.message("修改成功");
						
					} else {
						return Enum_MSG.失败.message(object.getString("errorCode"),object.getString("errorMessage"));
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
	 * 获取订单接口ref
	 * 
	 * ref=32位数的长度
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	@Override
	public Object getOrder(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() );
		try{
			
//			{"request":null,"errorCode":0,"errorMessage":null,"logId":1479893978495,"params":{"balance":1.0}}
			
			if(MapUtil.isNulls(entity, "ref")){
				
				
				JSONObject params = new JSONObject();
				params.put("ref", entity.get("ref"));
				JSONObject join = new JSONObject();
				join.put("hashCode", KEY);
				join.put("command", "CHECK_REF");
				join.put("params", params);

				String result = ZJUtil.getPost(API_URL, join);
				
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() , result );
				
				JSONObject object =  JSONObject.fromObject( result );
				
				
				//接口调用成功
				if( !object.has("code") ) {
					
					//注意：此接口成功时返回的是6601，表示交易号已存在,操作状态为成功，如果返回0表示交易号不存在
					if(object.getString("errorCode").equals("6601")) {
						
						return Enum_MSG.成功.message("成功");
					} else {
						return Enum_MSG.失败.message(object.getString("errorMessage"));
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
	 * username 合作伙伴平台的用户ID,字符串长度少于或者等于20位, 由字母和数字组成
	 * password 合作伙伴平台的用户原始密码，要求ＭＤ５加密, 长度为 32 位
	 * currency 合作伙伴的用户 ID 所使用的币别
	 * nickname 合作伙伴的用户昵称, 字符串长度少于或者等于 20 位
	 * language 游戏语言(CN,HK,EN,KR)
	 * line 线路(可选参数) 参数(1, 2, …) 默认线路为1, 注:具体线路参数ID以游戏平台为准
	 * 
	 * 
	 * @return 返回结果 登录链接
	 */
	@Override
	public Object login(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() );
		try{
			
			//http://live.cg8080.com/liveflash1/route.jsp?code=78cf4eb2-8941-43ce-9999-5c7c8a7e64fc&lc=cn&line=0&prefix=0
			JSONObject object =  JSONObject.fromObject( createAccount(entity) );
			System.out.println("调用结果："+object);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() , object.toString() );
			
			if(object.getString("code").equals("0")) {
				
				return Enum_MSG.成功.message(object.getJSONObject("info").getJSONObject("params").getString("link"));
			} else {
				return Enum_MSG.失败.message(object.getString("info"));
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
	}

	/**
	 * 是否在线（验证交易号）接口
	 * @param entity 参数列表
	 * ref 唯一交易号
	 * @return 返回结果
	 */
	@Override
	public Object isOnLine(Map<String, Object> entity) {
		try{
			if(MapUtil.isNulls(entity, "ref")){
				JSONObject params = new JSONObject();
				params.put("ref", entity.get("ref"));
				JSONObject join = new JSONObject();
				join.put("hashCode", KEY);
				join.put("command", "CHECK_REF");
				join.put("params", params);
				return ZJUtil.getPost(API_URL, join);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	


	
	public static void main(String[] a){
		ZJGameAPI zj = new ZJGameAPI();
		Map<String, Object> entity = new HashMap<String, Object>();
//		entity.put("username", "A1GSbi4w11234OfzQFXJ");
		entity.put("username", RandomString.createRandomString(20));
		entity.put("password", "Cz7JQarR");
		entity.put("password", "1234567891");
		entity.put("nickname", "ddddddd");
//		entity.put("ref", Encrypt.MD5(StringUtils.getCurrenDate())+"");
		entity.put("ref", Encrypt.MD5("llkmzlbu123456789124")+"");
		entity.put("amount", "0.1");
		System.out.println(zj.createAccount(entity));
		System.out.println(zj.deposit(entity));//上分
		System.out.println(zj.getBalance(entity));
		System.out.println(zj.login(entity));
//		System.out.println(zj.withdraw(entity));//下分
//		System.out.println(zj.getOrder(entity));//订单查询
		
//		System.out.println(zj.updateInfo(entity));//
	}

}
