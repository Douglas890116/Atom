package com.hy.pull.common.util.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.simple.parser.JSONParser;

import com.google.gson.annotations.Since;
import com.hy.pull.common.util.AESUtil2Net;
import com.hy.pull.common.util.Encrypt;
import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.PBKDF2Encryption;
import com.hy.pull.common.util.StreamTool;
import com.hy.pull.common.util.game.sa.DeEnCode;
import com.hy.pull.common.util.game.sa.XmlUtil;
import com.hy.pull.common.util.game.tag.TagUtil;
import com.hy.pull.common.util.game.xcp.XCPUtil;
import com.maven.config.SystemConstant;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;
import com.maven.util.HttpPostUtil.UTF8PostMethod;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * EBet接口
 * @author temdy
 */
public class EBetGameAPI implements BaseInterface {
	
	private String API_URL = "http://haoying.ebet.im:8888/api/";
	private String KeyB = "KeyB";//该参数目前没有使用
	
	private String channelid = "201";//好赢作为集团号
	private String subchannelid = "1247";
	private String H5LOGIN_URL = "http://haoying.qwe.rocks/h5/0k8u12";
	
	private String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQn5QHg9QxSgWHwgi1eq9SLmgotyfLnYw1RWQuGdO1ANk1jA+/WvkwOCFqijQKRu8VBY+KQjwpcm/ROqXDNgVAPY9+U5g03br1Iia/3G8ZF3Xks/T0wy3rnT6dj1Y3Q8CBncADoEkvPnHSQbl/y185o5zmY+PNMeJxq7k63YvrjQIDAQAB";
	private String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJCflAeD1DFKBYfCCLV6r1IuaCi3J8udjDVFZC4Z07UA2TWMD79a+TA4IWqKNApG7xUFj4pCPClyb9E6pcM2BUA9j35TmDTduvUiJr/cbxkXdeSz9PTDLeudPp2PVjdDwIGdwAOgSS8+cdJBuX/LXzmjnOZj480x4nGruTrdi+uNAgMBAAECgYBupfjeiwjksQpsJJL/Lh9G1ASS6haDXUVxWGbeMppcCIsmwcMml1bBgqBmX9iS6FRxE/EPSb+3wjs0rBc4tHLs3emP8pl41iYwcO5lvNGMfXIgFvsLgoup/vj8IpRmi4Y+E7eiqTuPBwftbGB/2iN9KMCMD+l0ZzlMROyiCqw0zQJBAMUAGH7/5tERDr3bcZ8DD8NBdTUnzVJK182oNOyfWqbBBxH+8dzQn/X0weOyUkO//kCiXKm4fYpVJDZ+gKJhx+cCQQC778RIg/eQAANdjjtG0Jricuj47Twz0risMhKo0/0Uhru1e9XivynrKycGP3C/MykBVzNVPn3FGs6Shp4IA/JrAkByzlB4SsgIFOnDaUy4/37DKrWUqcJ1b9p+JsXZFDEvNxTYvvvS1N4z51TLTpO0mgIhRr27xyGaaW32OBjdCSEHAkEApYdiL6ikVN3eGPncddvof4lMb2usecygwXH9A1xr7Tdaf1eKJIPRLQO+BH++E3nBJSAj43H+Hqwkw+PzrwWi7wJBAIy0ljB4mfSOgFOK+HbUfQBUtSGHEGtndWG3IX1iqUZpow7+cVAE7xG3tv4vAhLEXe+YqxNdnkLT5FX0218p+Mk=";
	
	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	
	/**
	 * 默认构造函数
	 * 
	 * 测试时可以使用，不用传递秘钥进来
	 */
	@Deprecated
	public EBetGameAPI(){
		
	}
	
	/**
	 * 构建函数，初始化参数
	 * @param API_URL API接口URL
	 * @param MD5_KEY MD5密钥
	 * @param DES_KEY DES密钥
	 */
	public EBetGameAPI(String API_URL,String KeyB,String subchannelid,String H5LOGIN_URL,String channelid, String publicKey, String privateKey,String GAME_API_URL){
		this.API_URL = API_URL;
		this.KeyB = KeyB;
		this.subchannelid = subchannelid;
		this.H5LOGIN_URL = H5LOGIN_URL;
		this.channelid = channelid;
		this.publicKey = publicKey;
		this.privateKey = privateKey;
		this.GAME_API_URL = GAME_API_URL;
	}
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * 获取时间的当前日期
	 * @return
	 */
	private static String getCurrenDate_yyyyMMdd() {
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		return sdf.format(ca.getTime());
	}

	/**
	 * 创建用户接口
	 * @param entity 参数列表 username,password
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "username,password")){	
			
//			if( String.valueOf(entity.get("loginname")).length() > 15 ) {
//				return Enum_MSG.参数错误.message("用户长度不能大于15位");
//			}
//			if( String.valueOf(entity.get("password")).length() > 12 ) {
//				return Enum_MSG.参数错误.message("密码长度不能大于12位");
//			}
					
			String signature = sign(entity.get("username").toString().getBytes(), this.privateKey);
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("username", entity.get("username").toString());
			params.put("lang", "1");
			params.put("channelId", this.channelid);
			params.put("subChannelId", this.subchannelid);
			params.put("signature", signature);
			
			JSONObject result =  doPostSubmitMap(this.API_URL.concat("/syncuser"), params);
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , result.toString() );
			
			
			//	接口调用成功
			if( result.getString("status").equals("200") ) {
				
				return Enum_MSG.成功.message("成功");
				
			} else if( result.getString("status").equals("401") ) {//
				
				return Enum_MSG.账号已存在.message("账号已存在");
			} else {
				
				return Enum_MSG.失败.message(result.getString("status"), result.getString("status"));
			}
			
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	
	
	/**
	 * 获取余额
	 * @param entity 参数列表 MemberID,Amount,ExTransID
	 * 
	 * password 密码
	 * @return 返回结果
	 */
	@Override
	public Object getBalance(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "username,password")){	
			
//			if( String.valueOf(entity.get("loginname")).length() > 15 ) {
//				return Enum_MSG.参数错误.message("用户长度不能大于15位");
//			}
//			if( String.valueOf(entity.get("password")).length() > 12 ) {
//				return Enum_MSG.参数错误.message("密码长度不能大于12位");
//			}
					
			String timestamp = System.currentTimeMillis()+"";
			String signature = sign(entity.get("username").toString().concat(timestamp).getBytes(), this.privateKey);
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("username", entity.get("username").toString());
			params.put("timestamp", timestamp);
			params.put("channelId", this.channelid);
			params.put("signature", signature);
			
			JSONObject result =  doPostSubmitMap(this.API_URL.concat("/userinfo"), params);
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , result.toString() );
			
			
			//	接口调用成功
			if( result.getString("status").equals("200") ) {
				return Enum_MSG.成功.message(result.getString("money"));
			} else {
				return Enum_MSG.失败.message(result.getString("status"), result.getString("status"));
			}
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	
	
	/**
	 * 下分（取款）的接口
	 * @param entity 参数列表 username,remit,remitno
	 * 
	 * @return 返回结果
	 */
	public Object withdraw(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "username,remit,remitno")){	
			
			if( String.valueOf(entity.get("remitno")).length() > 25 ) {
				return Enum_MSG.参数错误.message("订单长度不能大于25位");
			}
//			if( String.valueOf(entity.get("password")).length() > 12 ) {
//				return Enum_MSG.参数错误.message("密码长度不能大于12位");
//			}
					
			String timestamp = System.currentTimeMillis()+"";
			String signature = sign(entity.get("username").toString().concat(timestamp).getBytes(), this.privateKey);
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("username", entity.get("username").toString());
			params.put("money", "-"+entity.get("remit").toString());//注意这里：存取款都是同一个接口，区别在于金额的正负关系
			params.put("rechargeReqId", entity.get("remitno").toString());
			params.put("channelId", this.channelid);
			params.put("signature", signature);
			params.put("timestamp", timestamp);
			
			JSONObject result =  doPostSubmitMap(this.API_URL.concat("/recharge"), params);
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() , result.toString() );
			
			
			//	接口调用成功
			if( result.getString("status").equals("200") ) {
				/*
				//二次调用核实接口确定是否转账成功，强制要求
				signature = sign(entity.get("remitno").toString().getBytes(), this.privateKey);
				params = new HashMap<String, String>();
				params.put("rechargeReqId", entity.get("remitno").toString());
				params.put("channelId", this.channelid);
				params.put("signature", signature);
				
				result =  doPostSubmitMap(this.API_URL.concat("/rechargestatus"), params);
				System.out.println("调用结果："+result);
				
				if( result.getString("status").equals("200") ) {
					return Enum_MSG.成功.message("成功");
				} else {
					return Enum_MSG.失败.message(result.getString("status"), "未能转账成功");
				}
				*/
				return Enum_MSG.成功.message("成功");
			} else {
				return Enum_MSG.失败.message(result.getString("status"), result.getString("status"));
			}
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	
	
	/**
	 * 上分（存款）的接口 username,remit,remitno
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "username,remit,remitno")){	
			
			if( String.valueOf(entity.get("remitno")).length() > 25 ) {
				return Enum_MSG.参数错误.message("订单长度不能大于25位");
			}
//			if( String.valueOf(entity.get("password")).length() > 12 ) {
//				return Enum_MSG.参数错误.message("密码长度不能大于12位");
//			}
					
			String timestamp = System.currentTimeMillis()+"";
			String signature = sign(entity.get("username").toString().concat(timestamp).getBytes(), this.privateKey);
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("username", entity.get("username").toString());
			params.put("money", entity.get("remit").toString());
			params.put("rechargeReqId", entity.get("remitno").toString());
			params.put("channelId", this.channelid);
			params.put("signature", signature);
			params.put("timestamp", timestamp);
			
			JSONObject result =  doPostSubmitMap(this.API_URL.concat("/recharge"), params);
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() , result.toString() );
			
			
			//	接口调用成功
			if( result.getString("status").equals("200") ) {
				/**
				//二次调用核实接口确定是否转账成功，强制要求
				signature = sign(entity.get("remitno").toString().getBytes(), this.privateKey);
				params = new HashMap<String, String>();
				params.put("rechargeReqId", entity.get("remitno").toString());
				params.put("channelId", this.channelid);
				params.put("signature", signature);
				
				result =  doPostSubmitMap(this.API_URL.concat("/rechargestatus"), params);
				System.out.println("调用结果："+result);
				
				if( result.getString("status").equals("200") ) {
					return Enum_MSG.成功.message("成功");
				} else {
					return Enum_MSG.失败.message(result.getString("status"), "未能转账成功");
				}
				*/
				return Enum_MSG.成功.message("成功");
			} else {
				return Enum_MSG.失败.message(result.getString("status"), result.getString("status"));
			}
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	/**
	 * 获取游戏结果
	 * @param entity 参数列表 startdate,enddate
	 * 
	 * 2016- 04- 16 22:00:22 
	 * 
	 * @return 返回结果
	 */
	private static Map<String, String> mapGamename = new HashMap<String, String>(){{
		this.put("1", "百家乐");
		this.put("2", "龙虎");
		this.put("3", "骰宝");
		this.put("4", "轮盘");
		this.put("5", "水果机");
	}};
	@Override
	public Object getRecord(Map<String, Object> entity) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		String starttime = "2017-11-04 10:00:00";//日期ex：2012-03-21 00:00:00
		String endtime = "2017-11-04 19:00:00";//日期ex：2012-03-21 23:59:59
		
		int pagelimit = 4999;//每页数量
		int page = 1;//查询页数
		
		String timestamp = System.currentTimeMillis()+"";
		String signature = sign(timestamp.getBytes(), this.privateKey);
		
		
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("startTimeStr", starttime);
		params.put("endTimeStr", endtime);
		params.put("pageSize", pagelimit+"");//最多上限5000
		params.put("pageNum", page+"");
		params.put("channelId", this.channelid);
		params.put("subChannelId", this.subchannelid);
		params.put("signature", signature);
		params.put("timestamp", timestamp);
		
		JSONObject result =  doPostSubmitMap("http://haoying.ebet.im:8888/api/".concat("userbethistory"), params);
		System.out.println("调用结果："+result);
		
		
		//	接口调用成功
		if( result.getString("status").equals("200") ) {
			
			int count = result.getInt("count");
			System.out.println("count="+count);
			JSONArray jsonArray = result.getJSONArray("betHistories");
			Map<String, Object> item = null;
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i); 
				
				item = new HashMap<String, Object>();
				String gametype = jsonObject.getString("gameType");
				item.put("gametype", gametype);
				item.put("gamename", mapGamename.get(gametype));
				item.put("betmap", jsonObject.getString("betMap"));
				item.put("judgeresult", jsonObject.getString("judgeResult"));
				item.put("userid", jsonObject.getString("userId"));
				item.put("bethistoryid", jsonObject.getString("betHistoryId"));
				item.put("payouttime", jsonObject.getString("payoutTime"));
				item.put("gametime", jsonObject.getString("createTime"));
				item.put("roundno", jsonObject.optString("roundNo"));
				item.put("subchannelid", jsonObject.getString("subChannelId"));
				item.put("validbet", jsonObject.getString("validBet"));
				item.put("payout", jsonObject.getString("payout"));
				item.put("username", jsonObject.getString("username"));
				
				item.put("bankercards", jsonObject.optString("bankerCards"));
				item.put("playercards", jsonObject.optString("playerCards"));
				item.put("alldices", jsonObject.optString("allDices"));
				item.put("dragoncard", jsonObject.optString("dragonCard"));
				item.put("tigercard", jsonObject.optString("tigerCard"));
				item.put("numbercard", jsonObject.optString("number"));
				
				item.put("createtime", new Date());
				item.put("bettime", new Date(Long.valueOf( item.get("gametime").toString()+"000" )));//这个时间戳是10位数的，所以加三个数转换成Java的时间戳
				item.put("betmoney", item.get("validbet"));
				item.put("netmoney", Double.valueOf(item.get("payout").toString()) == 0 ? ("-"+item.get("validbet")) : (Double.valueOf(item.get("payout").toString()) - Double.valueOf(item.get("validbet").toString())));
				
				list.add(item);
			}
			
			for (Map<String, Object> object : list) {
				System.out.println(object);
			}
			
		} else {
			//查找数据失败
		}
		
		return list;
	}
	

	/**
	 * H5登录时必须要用token登录
	 * @param entity
	 * @return
	 */
	private Object getH5Token(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "geH5Token", entity.toString()  );
		
		
		if(MapUtil.isNulls(entity, "username,password")){	
			
//			if( String.valueOf(entity.get("loginname")).length() > 15 ) {
//				return Enum_MSG.参数错误.message("用户长度不能大于15位");
//			}
//			if( String.valueOf(entity.get("password")).length() > 12 ) {
//				return Enum_MSG.参数错误.message("密码长度不能大于12位");
//			}
					
			String timestamp = System.currentTimeMillis()+"";
			String signature = sign(entity.get("username").toString().concat(timestamp).getBytes(), this.privateKey);
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("cmd", "RegisterOrLoginReq");
			params.put("eventType", "1");
			params.put("ip", "192.168.0.0");
			params.put("username", entity.get("username").toString());
			params.put("password", entity.get("password").toString());
			params.put("channelId", this.channelid);
			params.put("signature", signature);
			params.put("timestamp", timestamp);
			
			JSONObject result =  doPostSubmitMapToken(this.H5LOGIN_URL, params);
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getH5Token", entity.toString() , result.toString() );
			
			
			//	接口调用成功
			if( result.getString("status").equals("200") ) {
				return Enum_MSG.成功.message(result.getString("accessToken"));
			} else {
				return Enum_MSG.失败.message(result.getString("status"), "无法获取token："+result.getString("message"));
			}
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}

	}
	/**
	 * 更新信息接口
	 * @param entity 参数列表 username,password
	 * @return 返回结果
	 */
	@Override
	public Object updateInfo(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString()  );
		
		
		if(MapUtil.isNulls(entity, "username,password")){	
			
//			if( String.valueOf(entity.get("loginname")).length() > 15 ) {
//				return Enum_MSG.参数错误.message("用户长度不能大于15位");
//			}
//			if( String.valueOf(entity.get("password")).length() > 12 ) {
//				return Enum_MSG.参数错误.message("密码长度不能大于12位");
//			}
					
			//生成key
			//key=MD5（UserName+KeyB+YYYYMMDD） YYYYMMDD为北京时间(GMT+8)(20151209)
			String key = Encrypt.MD5(entity.get("username").toString() + this.KeyB + getCurrenDate_yyyyMMdd()).toLowerCase();//全小写
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("username", entity.get("username").toString());
			params.put("password", entity.get("password").toString());
			params.put("subchannelid", this.subchannelid);
			params.put("key", key);
			
			JSONObject result =  doPostSubmitMap(this.API_URL.concat("/ChangeMemberPwd"), params);
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString() , result.toString() );
			
			
			//	接口调用成功
			if( result.getString("status").equals("200") ) {
				return Enum_MSG.成功.message(result.getString("message"));
			} else {
				return Enum_MSG.失败.message(result.getString("status"), result.getString("status"));
			}
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}

	}

	/**
	 * 获取订单接口 ExTransID
	 * @return 返回结果
	 */
	@Override
	public Object getOrder(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "username,remitno")){	
			
			//二次调用核实接口确定是否转账成功，强制要求
			String signature = sign(entity.get("remitno").toString().getBytes(), this.privateKey);
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("rechargeReqId", entity.get("remitno").toString());
			params.put("channelId", this.channelid);
			params.put("signature", signature);
			
			JSONObject result =  doPostSubmitMap(this.API_URL.concat("/rechargestatus"), params);
			System.out.println("调用结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() , result.toString() );
			
			//	接口调用成功
			if( result.getString("status").equals("200") ) {
				return Enum_MSG.成功.message("确定订单成功");
			} else {
				return Enum_MSG.失败.message(result.getString("status"), "未能转账成功");
			}
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	/**
	 * 登录的接口
	 * @param entity 参数列表 username,password
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object login(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() );
		
		if(MapUtil.isNulls(entity, "username,password")){
			
			
			//http://mt.ebet2018.com/h5/lqbg0h?ts=xxxxx&username=xxxxx&accessToken=xxxxx
			
			//ts Unixtimestamp
			//username 玩家账号名
			//accessToken 输入通过GetToken()取得的token值
			String token = UUID.randomUUID().toString();
			
			return Enum_MSG.成功.message(this.H5LOGIN_URL.concat("?ts="+System.currentTimeMillis()).concat("&username=").concat(entity.get("username").toString()).concat("&accessToken=").concat(token));
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
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
	
	public static JSONObject doPostSubmitMapToken(String httpUrl,Map<String, String> params) {
        try {
        	
        	HttpPost httpRequst = new HttpPost(httpUrl);//创建HttpPost对象  
            //填入各个表单域的�?
        	List paramsxx = new ArrayList<BasicNameValuePair>(); 
        	
        	Iterator ite = params.entrySet().iterator();
    		while(ite.hasNext()){
    			Map.Entry<String, String> entry = (Entry<String, String>) ite.next();
    			String key = entry.getKey();//map中的key
    			String value = entry.getValue();//上面key对应的value
    			BasicNameValuePair xxx = new BasicNameValuePair(key,value);
    			paramsxx.add(xxx);
    		}
    		
        	UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(paramsxx,HTTP.UTF_8);
    		httpRequst.setEntity(encodedFormEntity);
    		httpRequst.addHeader("contentType", "text/plain");
    		
    		CloseableHttpClient httpClient = HttpClients.createDefault();
    		int timeout = 10000;
    		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
    		httpRequst.setConfig(requestConfig);
    		
            HttpResponse httpResponse = httpClient.execute(httpRequst);  
            
            JSONObject jsonObject = new JSONObject();
            if(httpResponse.getStatusLine().getStatusCode() == 200)  {
            	String str = EntityUtils.toString(httpResponse.getEntity());
            	System.out.println(str);
            	str = new JSONParser().parse(str).toString();
            	jsonObject = JSONObject.fromObject(str);
            } else {
            	jsonObject.put("status", ""+httpResponse.getStatusLine().getStatusCode());
    			jsonObject.put("message", "返回响应码不正确："+EntityUtils.toString(httpResponse.getEntity()));
            }
            
            return jsonObject;
            
		} catch (Exception e) {
			e.printStackTrace();
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "-1");
			jsonObject.put("message", e.getMessage());
			return jsonObject;
		}
        
	}
	
	public static JSONObject doPostSubmitMap(String httpUrl,Map<String, String> params) {
        try {
        	
        	System.out.println("ebet请求URL："+httpUrl);
        	System.out.println("ebet请求params："+params);
        	HttpPost httpRequst = new HttpPost(httpUrl);//创建HttpPost对象  
            //填入各个表单域的�?
        	List paramsxx = new ArrayList<BasicNameValuePair>(); 
        	
        	Iterator ite = params.entrySet().iterator();
    		while(ite.hasNext()){
    			Map.Entry<String, String> entry = (Entry<String, String>) ite.next();
    			String key = entry.getKey();//map中的key
    			String value = entry.getValue();//上面key对应的value
    			BasicNameValuePair xxx = new BasicNameValuePair(key,value);
    			paramsxx.add(xxx);
    		}
    		
        	UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(paramsxx,HTTP.UTF_8);
    		httpRequst.setEntity(encodedFormEntity);
    		httpRequst.addHeader("contentType", "application/x-www-form-urlencoded");
    		
    		CloseableHttpClient httpClient = HttpClients.createDefault();
    		int timeout = 10000;
    		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
    		httpRequst.setConfig(requestConfig);
    		
            HttpResponse httpResponse = httpClient.execute(httpRequst);  
            
            JSONObject jsonObject = new JSONObject();
            if(httpResponse.getStatusLine().getStatusCode() == 200)  {
            	String str = EntityUtils.toString(httpResponse.getEntity());
            	str = new JSONParser().parse(str).toString();
//            	System.out.println(str);
            	jsonObject = JSONObject.fromObject(str);
            } else {
            	jsonObject.put("status", ""+httpResponse.getStatusLine().getStatusCode());
    			jsonObject.put("message", "返回响应码不正确："+EntityUtils.toString(httpResponse.getEntity()));
            }
            
            return jsonObject;
            
		} catch (Exception e) {
			e.printStackTrace();
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "-1");
			jsonObject.put("message", e.getMessage());
			return jsonObject;
		}
        
	}
	
	// 私钥匙加密
	private static String sign(byte[] data, String privateKey) {
		try {
			byte[] keyBytes = Base64.getDecoder().decode(privateKey.getBytes());
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initSign(priKey);
			signature.update(data);
			return new String(Base64.getEncoder().encode(signature.sign()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 公钥匙验证
	private static boolean verify(byte[] data, String publicKey, String sign) {
		try {
			byte[] keyBytes = Base64.getDecoder().decode(publicKey.getBytes());
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initVerify(publicKey2);
			signature.update(data);
			return signature.verify(Base64.getDecoder().decode(sign));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] a){
//		password=XQ5Lv5Xq, loginname=zOne6KTd83sMEpb
		
		EBetGameAPI tag = new EBetGameAPI();
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("username", RandomString.createRandomString(30).toLowerCase());//#&*
		entity.put("password", RandomString.createRandomString(8));
//		System.out.println(tag.createAccount(entity));//username,password
		
		entity.put("username", "c484vmnw0vpjudpw88xxh1ioub31om");
		entity.put("password", "10KQO9bh");
//		
		entity.put("remitno", "IN"+RandomString.createRandomString(23));
		entity.put("remit", "2");
//		System.out.println(tag.deposit(entity));//username,remit,remitno
//
		entity.put("remitno", "OUT"+RandomStringNum.createRandomString(22));
		entity.put("remit", "1");
//		System.out.println(tag.withdraw(entity));//username,remit,remitno
//
//		
//		System.out.println(tag.getOrder(entity));//username,remitno
////		
//		System.out.println(tag.getBalance(entity));//username,password
		
//		entity.put("password", RandomString.createRandomString(8));
//		System.out.println(tag.updateInfo(entity));//username,password
//		
		
//		System.out.println(tag.login(entity));//username,password
//		
		System.out.println(tag.getRecord(entity));//
		
			
	}
}

