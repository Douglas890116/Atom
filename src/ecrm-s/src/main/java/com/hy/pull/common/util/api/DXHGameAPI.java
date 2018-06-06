package com.hy.pull.common.util.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.RSAUtil;
import com.hy.pull.common.util.game.pt.PTUtils;
import com.hy.pull.common.util.game.sa.MD5Encoder;
import com.maven.config.SystemConstant;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;

import net.sf.json.JSONObject;

/**
 * 大雄棋牌游戏接口
 * @author temdy
 */
public class DXHGameAPI implements BaseInterface {
	
	private String API_URL = "http://demo-socket.dashinggame.com/S13/services/dg/player";//
	private String merchantId = "1002810";//
	private String LOBBY_URL = "http://demo-sfgames.dashinggame.com/static/html/hall.html";//
	//原生私钥转换为PKCS8格式后的字符串
	private String pkcs8PrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMae33rrsUEohlYP06wf1u2uIiaqpTZu297is2PvFxAP/spOjD+0Dm3bq1Yha8v3cFItPqeDyWS9mrZm5/cfJW2Vc6pA8+qLi8aT0cHFCEspYdy4Bfnh9zt05isbzRqJDoO41ZDsURoFkgT4lja5imjfeyYo0g/fpay6bnpS5oOBAgMBAAECgYBahE7eqwkV480M7ZuOxtfha8lei8hNPUpwkiok8fI1vMRmGFPsODeXM1QrJYIF61dB7CKwnuuqQIqAc/dk9MnOD0q3qZ6jMb/M+jFq/73HwuM1f67gQvVwPJl7vjINhB+J/PParVqYkmvNOkuuOgqv/jf16qEovFdXlL9hutHGbQJBAPw9/pQ4adX1UDojdysBRjH8nL82TsGxeLH9b97iHF3kfYilvEUCqDNCi/K/4z/3XD63ADaW8cQ0n5AnQV31RacCQQDJlGAqvt92TAxf2UrJ855AGQnynpboX3KIim05dPWhSzYfnHm6IHFiXZnvljmkri1jcE+UqDOrGuSfOc3j++KXAkBF0XMR27uPuWMHdKGbibLAS0entYR/IHxj5957NuLbKk+E7zr5bw7XgWfzPSHNps4lncm1UnqA8H/qCrORKj6pAkEAhiIWT7tNBPGbtlfn3TQflHVU2j3PGvcQRm0eOwJpxBdA/43mrgSSjirMmNF0r/E6wJVmTvwRzYSKpq0XJOJiJwJBAMNl7PP+UuoHovWYc5JCNIbCHwQm0GchUWorIv+G/v2c2GJTFua77xgPcKym2pI4tq3iuelk/YD/Sz9yUr2znUQ=";
		
	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	/**
	 * 默认构造函数
	 * 
	 * 测试时可以使用，不用传递秘钥进来
	 */
	@Deprecated
	public DXHGameAPI(){
		if(API_URL.endsWith("/")) {
			API_URL = API_URL.substring(0, API_URL.length() - 1);
		}
	}
	
	
	/**
	 * 构建函数，初始化参数
	 * @param ENTITY_KEY 密钥ENTITY
	 */
	public DXHGameAPI(String API_URL, String merchantId, String LOBBY_URL, String pkcs8PrivateKey, String GAME_API_URL){
		this.API_URL = API_URL;
		this.merchantId = merchantId;
		this.LOBBY_URL = LOBBY_URL;
		this.pkcs8PrivateKey = pkcs8PrivateKey;
		this.GAME_API_URL = GAME_API_URL;
		if(API_URL.endsWith("/")) {
			API_URL = API_URL.substring(0, API_URL.length() - 1);
		}
	}
	

	/**
	 * 创建用户接口
	 * @param entity 参数列表 playerName,pwd
	 * 
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() );
		try{
			if(MapUtil.isNulls(entity, "playerName,pwd")){
				
				if( String.valueOf(entity.get("playerName")).length() > 30 ) {
					return Enum_MSG.参数错误.message("用户长度不能大于30位");
				}
				if( String.valueOf(entity.get("pwd")).length() > 30 ) {
					return Enum_MSG.参数错误.message("密码长度不能大于30位");
				}
				
				StringBuilder url = new StringBuilder();
				url.append(API_URL);
				url.append("/playerCreate/");
				url.append(entity.get("playerName"));
				url.append("/"+this.merchantId);
				url.append("/"+MD5Encoder.encode(entity.get("pwd").toString()));
				
				String result = doPostSubmit(url.toString());
				JSONObject object =  JSONObject.fromObject(result);
				if(object.has("code") ){
					return object;
				}
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , object.toString() );
				
				//存在该节点时，表示成功
				if(object.getString("resultCode").equals("1")) {
					//不返回相关数据。
					return Enum_MSG.成功.message(object);
				} else {
					if(object.getString("resultCode").equals("5")) {
						return Enum_MSG.账号已存在.message(object);
					}
					return Enum_MSG.失败.message("调用异常"+object.getString("resultCode"));
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

			
			if(MapUtil.isNulls(entity, "playerName")){		
				
				StringBuilder url = new StringBuilder();
				url.append(API_URL);
				url.append("/getPlayerBalance");
				url.append("/"+entity.get("playerName"));
				url.append("/"+this.merchantId);
				
				String result = doPostSubmit(url.toString());
				JSONObject object =  JSONObject.fromObject(result);
				if(object.has("code") ){
					return object;
				}
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , object.toString() );
				
					
				//存在该节点时，表示成功
				if(object.getString("resultCode").equals("1")) {
					//不返回相关数据。
					return Enum_MSG.成功.message(object.get("coinBalance"));//当前余额
				} else {
					return Enum_MSG.失败.message("调用异常"+object.getString("resultCode"));
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
			

			if(MapUtil.isNulls(entity, "playerName,extTransId,coins")){		
				
				if( String.valueOf(entity.get("extTransId")).length() > 40 ) {
					return Enum_MSG.参数错误.message("单号长度不能大于40位");
				}
				
				
				StringBuilder url = new StringBuilder();
				url.append(API_URL);
				url.append("/withdraw");
				url.append("/"+this.merchantId);
				url.append("/"+entity.get("playerName"));
				url.append("/"+entity.get("coins").toString());//金额
				url.append("/"+entity.get("extTransId").toString());//单号,50位数.区分IN与OUT订单
				
				String result = doPostSubmit(url.toString());
				JSONObject object =  JSONObject.fromObject(result);
				if(object.has("code") ){
					return object;
				}
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() , object.toString() );
				
				//存在该节点时，表示成功
				if(object.getString("resultCode").equals("1")) {
					//不返回相关数据。
					return Enum_MSG.成功.message(object.get("curBalance"));//当前余额
				} else {
					return Enum_MSG.失败.message("调用异常"+object.getString("resultCode"));
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

			if(MapUtil.isNulls(entity, "playerName,extTransId,coins")){		
				
				if( String.valueOf(entity.get("extTransId")).length() > 40 ) {
					return Enum_MSG.参数错误.message("单号长度不能大于40位");
				}
				
				StringBuilder url = new StringBuilder();
				url.append(API_URL);
				url.append("/deposit");
				url.append("/"+this.merchantId);
				url.append("/"+entity.get("playerName"));
				url.append("/"+entity.get("coins").toString());//金额
				url.append("/"+entity.get("extTransId").toString());//单号,50位数.区分IN与OUT订单
				
				String result = doPostSubmit(url.toString());
				JSONObject object =  JSONObject.fromObject(result);
				if(object.has("code") ){
					return object;
				}
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() , object.toString() );
				
				//存在该节点时，表示成功
				if(object.getString("resultCode").equals("1")) {
					//不返回相关数据。
					return Enum_MSG.成功.message(object.get("curBalance"));//当前余额
				} else {
					return Enum_MSG.失败.message("调用异常"+object.getString("resultCode"));
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
		// TODO Auto-generated method stub
		return null;
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
			
			if(MapUtil.isNulls(entity, "playerName,newPwd")){		
				
				if( String.valueOf(entity.get("newPwd")).length() > 30 ) {
					return Enum_MSG.参数错误.message("新密码长度不能大于30位");
				}
				
				StringBuilder url = new StringBuilder();
				url.append(API_URL);
				url.append("/updatePlayerPwds");//重置密码接口
				url.append("/"+entity.get("playerName"));
				url.append("/"+this.merchantId);
				url.append("/"+MD5Encoder.encode(entity.get("newPwd").toString()));
				
				String result = doPostSubmit(url.toString());
				JSONObject object =  JSONObject.fromObject(result);
				if(object.has("code") ){
					return object;
				}
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString() , object.toString() );
				
				//存在该节点时，表示成功
				if(object.getString("resultCode").equals("1")) {
					//不返回相关数据。
					return Enum_MSG.成功.message(object);//
				} else {
					return Enum_MSG.失败.message("调用异常"+object.getString("resultCode"));
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
			
			
			if(MapUtil.isNulls(entity, "playerName,extTransId")){		
				
				StringBuilder url = new StringBuilder();
				url.append(API_URL);
				url.append("/queryTransbyId");
				url.append("/"+entity.get("playerName"));
				url.append("/"+this.merchantId);
				url.append("/"+entity.get("extTransId").toString());
				
				String result = doPostSubmit(url.toString());
				JSONObject object =  JSONObject.fromObject(result);
				if(object.has("code") ){
					return object;
				}
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() , object.toString() );
				
				//存在该节点时，表示成功
				if(object.getString("resultCode").equals("1")) {
					//不返回相关数据。
					return Enum_MSG.成功.message(object);//
				} else {
					return Enum_MSG.失败.message("调用异常"+object.getString("resultCode"));
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
	 * playtype=DZ/SX
	 * 
	 * @param entity 参数列表 
	 * @return 返回结果
	 */
	@Override
	public Object login(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() );
		
		try{
			
			if(MapUtil.isNulls(entity, "playerName,pwd,loginIp")){		
				
				StringBuilder url = new StringBuilder();
				url.append(API_URL);
				url.append("/playerLogin/");
				url.append(entity.get("playerName"));
				url.append("/"+MD5Encoder.encode(entity.get("pwd").toString()));
				url.append("/"+entity.get("loginIp").toString());
				url.append("/"+this.merchantId);
				
				String result = doPostSubmit(url.toString());
				JSONObject object =  JSONObject.fromObject(result);
				if(object.has("code") ){
					return object;
				}
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() , object.toString() );
				
				//存在该节点时，表示成功
				if(object.getString("resultCode").equals("1")) {
					//不返回相关数据。
					
					StringBuffer buffer = new StringBuffer();
					buffer.append("<?xml version='1.0' encoding='utf-8' ?>");
					buffer.append("<message>");
					buffer.append("<playerName>").append(entity.get("playerName")).append("</playerName>");
					buffer.append("<uId>").append(object.get("uId")).append("</uId>");
					buffer.append("<merchantId>").append(this.merchantId).append("</merchantId>");
					buffer.append("</message>");
					
					System.out.println(buffer.toString());
					
					byte[] byte_xmlbase64 = Base64.encodeBase64(buffer.toString().getBytes("UTF-8"));
		        	String s_xmlbase64 = new String(byte_xmlbase64,"UTF-8");
		        	System.out.println("xml经过BASE64编码后：" + s_xmlbase64);
		        	
		        	String s_xmlSign = RSAUtil.rsaSign(buffer.toString(), this.pkcs8PrivateKey, "UTF-8");
		        	System.out.println("xml签名后：" + s_xmlSign);
					
					String loginurl = this.LOBBY_URL.concat("?EnStr=").concat(s_xmlSign).concat("&XmlStr=").concat(s_xmlbase64);
					
					return Enum_MSG.成功.message(loginurl);
				} else {
					return Enum_MSG.失败.message("调用异常"+object.getString("resultCode"));
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
			
			if(MapUtil.isNulls(entity, "playerName")){		
				
				StringBuilder url = new StringBuilder();
				url.append(API_URL);
				url.append("/isPlayerOnline");
				url.append("/"+entity.get("playerName"));
				url.append("/"+this.merchantId);
				
				String result = doPostSubmit(url.toString());
				JSONObject object =  JSONObject.fromObject(result);
				if(object.has("code") ){
					return object;
				}
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "isOnLine", entity.toString() , object.toString() );
				
				//存在该节点时，表示成功
				if(object.getString("resultCode").equals("1")) {
					//不返回相关数据。
					return Enum_MSG.成功.message(object.get("onlineStatus"));
				} else {
					return Enum_MSG.失败.message("调用异常"+object.getString("resultCode"));
				}
				
				
			}  else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}

		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
	}
	
	private static String doPostSubmit(String httpUrl) {
        try {
        	System.out.println("请求URL："+httpUrl);
        	HttpPost httpRequst = new HttpPost(httpUrl);//创建HttpPost对象  
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);  
            if(httpResponse.getStatusLine().getStatusCode() == 200)  {
            	String strResult = EntityUtils.toString(httpResponse.getEntity());
            	return strResult;
            } else {
            	return Enum_MSG.逻辑事务异常.message("网络异常：状态码"+httpResponse.getStatusLine().getStatusCode());
            }
		} catch (Exception e) {
			e.printStackTrace();
			return Enum_MSG.逻辑事务异常.message("网络异常："+e.getMessage());
		}
	}
	
	public static void main(String[] a){
		DXHGameAPI tag = new DXHGameAPI();
		Map<String, Object> entity = new HashMap<String, Object>();
		
		entity.put("playerName", RandomString.createRandomString(30));//#&*
		entity.put("pwd", RandomString.createRandomString(8));
		System.out.println(tag.createAccount(entity));//playerName,pwd
		
		entity.put("loginIp", "192.168.1.1");
//		entity.put("playerName", "GyDBQNQvIK48Cv50g71vZwcjMhBBRv");
		
		entity.put("extTransId", "IN"+RandomString.createRandomString(36));
		entity.put("coins", "100");
		System.out.println(tag.deposit(entity));//playerName,extTransId,coins
//
//		entity.put("extTransId", "OUT"+RandomString.createRandomString(36));
//		entity.put("coins", "2");
//		System.out.println(tag.withdraw(entity));//playerName,extTransId,coins
//		
//		System.out.println(tag.getOrder(entity));//playerName,extTransId
//		
//		System.out.println(tag.getBalance(entity));//playerName

		
//		entity.put("pwd", "BGj5GjBa");
		System.out.println(tag.login(entity));//playerName,pwd,loginIp
//		
		
	}
}
