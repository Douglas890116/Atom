package com.hy.pull.common.util.api;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import com.hy.pull.common.util.MoneyHelper;
import com.maven.cache.SystemCache;
import com.maven.entity.EmployeeApiAccout;
import com.maven.game.enums.GameEnum;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;
import com.maven.util.StringUtils;

import net.sf.json.JSONObject;

/**
 * 游戏接口调用入口
 * 
 * 
 * 
 * @author Administrator
 *
 */
public class GameAPI {
	
	//key=enterprisecode+gametype
	//value=map
	public static Map<String, Map<String, String>> apikeys = new HashMap<String, Map<String, String>>();
	
	private static Map<String, String> initKeys(String gametype,String enterprisecode) {
		Map<String, String> keydata = new HashMap<String, String>();
		
		if(gametype.equals(GameEnum.Enum_GameType.AG.gametype) || gametype.equals(GameEnum.Enum_GameType.PNG.gametype)) {//TAG
			
			keydata = new HashMap<String, String>();
			keydata.put("API_URL", null);
			keydata.put("MD5_KEY", null);
			keydata.put("DES_KEY", null);
			keydata.put("cagent", null);
			keydata.put("API_URL_GCL", null);
			keydata.put("oddtype", null);
			keydata.put("GAME_API_URL", null);
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
			
		} else if(gametype.equals(GameEnum.Enum_GameType.AV老虎机.gametype)) {
			
			keydata = new HashMap<String, String>();
			keydata.put("API_URL", null);
			keydata.put("operatorID", null);
			keydata.put("operatorPassword", null);
			keydata.put("GAME_API_URL", null);
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
			
		} else if(gametype.equals(GameEnum.Enum_GameType.PT.gametype)) {
			
			keydata = new HashMap<String, String>();
			keydata.put("ENTITY_KEY", null);
			keydata.put("adminname", null);
			keydata.put("kioskname", null);
			keydata.put("IS_RELEASE", null);
			keydata.put("GAME_API_URL", null);
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
			
		} else if(gametype.equals(GameEnum.Enum_GameType.WIN88.gametype)) {
			
			keydata = new HashMap<String, String>();
			keydata.put("ENTITY_KEY", null);
			keydata.put("adminname", null);
			keydata.put("kioskname", null);
			keydata.put("IS_RELEASE", null);
			keydata.put("GAME_API_URL", null);
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
			
		} else if(gametype.equals(GameEnum.Enum_GameType.东方.gametype)) {
			
			keydata = new HashMap<String, String>();
			keydata.put("API_URL", null);
			keydata.put("KEY", null);
			keydata.put("AGENT", null);
			keydata.put("limitvideo", null);
			keydata.put("limitroulette", null);
			keydata.put("GAME_API_URL", null);
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
			
		} else if(gametype.equals(GameEnum.Enum_GameType.东方AG.gametype)) {//
			
			keydata = new HashMap<String, String>();
			keydata.put("API_URL", null);
			keydata.put("KEY", null);
			keydata.put("AGENT", null);
			keydata.put("GAME_API_URL", null);
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
			
		} else if(gametype.equals(GameEnum.Enum_GameType.棋牌.gametype)) {
			
			keydata = new HashMap<String, String>();
			keydata.put("serviceDomain", null);
			keydata.put("agentId", null);
			keydata.put("apiKey", null);
			keydata.put("GAME_API_URL", null);
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
			
		} else if(gametype.equals(GameEnum.Enum_GameType.沙巴.gametype)) {
			
			keydata = new HashMap<String, String>();
			keydata.put("API_URL", null);
			keydata.put("KEY", null);
			keydata.put("AGENT", null);
			keydata.put("GAME_API_URL", null);
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
			
		} else if(gametype.equals(GameEnum.Enum_GameType.沙龙.gametype)) {
			
			keydata = new HashMap<String, String>();
			keydata.put("API_URL", null);
			keydata.put("KEY", null);
			keydata.put("MD5_KEY", null);
			keydata.put("DES_KEY", null);
			keydata.put("CHECK_KEY", null);
			keydata.put("LOGIN_URL", null);
			keydata.put("LOBBY_CODE", null);
			keydata.put("GAME_API_URL", null);
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
			
		} else if(gametype.equals(GameEnum.Enum_GameType.波音.gametype)) {
			
			keydata = new HashMap<String, String>();
			keydata.put("API_URL", null);
			keydata.put("API_URL_LOGIN", null);
			keydata.put("website", null);
			keydata.put("uppername", null);
			keydata.put("KeyB_createAccount", null);
			keydata.put("KeyB_getBalance", null);
			keydata.put("KeyB_withdraw", null);
			keydata.put("KeyB_deposit", null);
			keydata.put("KeyB_getOrder", null);
			keydata.put("KeyB_login", null);
			keydata.put("GAME_API_URL", null);
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
			
		} else if(gametype.equals(GameEnum.Enum_GameType.洲际.gametype)) {
			
			keydata = new HashMap<String, String>();
			keydata.put("API_URL", null);
			keydata.put("KEY", null);
			keydata.put("GAME_API_URL", null);
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
			
		} else if(gametype.equals(GameEnum.Enum_GameType.环球.gametype)) {
			
			keydata = new HashMap<String, String>();
			keydata.put("API_URL", null);
			keydata.put("MD5_KEY", null);
			keydata.put("DES_KEY", null);
			keydata.put("hyAesKey", null);
			keydata.put("agentname", null);
			keydata.put("agentpwd", null);
			keydata.put("GAME_API_URL", null);
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
			
		} else if(gametype.equals(GameEnum.Enum_GameType.祥瑞.gametype)) {//彩票
			
			keydata = new HashMap<String, String>();
			keydata.put("API_URL", null);
			keydata.put("FIRST_KEY", null);
			keydata.put("LAST_KEY", null);
			keydata.put("DES_KEY", null);
			keydata.put("agent", null);
			keydata.put("userpoint", null);//返点，需要与代理具体沟通
			keydata.put("GAME_API_URL", null);
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
			
		} else if(gametype.equals(GameEnum.Enum_GameType.MG.gametype)) {//MG
			
			keydata = new HashMap<String, String>();
			keydata.put("HOST", null);
			keydata.put("P_USM", null);
			keydata.put("P_PWD", null);
			keydata.put("HOR_ID", null);
			keydata.put("apiusername", null);
			keydata.put("apipassword", null);
			keydata.put("partnerId", null);
			keydata.put("crId", null);
			keydata.put("crType", null);
			keydata.put("neId", null);
			keydata.put("neType", null);
			keydata.put("tarType", null);
			keydata.put("GAME_API_URL", null);
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
			
		} else if(gametype.equals(GameEnum.Enum_GameType.TTG.gametype)) {//TTG
			
			keydata = new HashMap<String, String>();
			keydata.put("privateServer", null);
			keydata.put("privateLoginUrl", null);
			keydata.put("partnerId", null);
			keydata.put("prefix", null); 
			keydata.put("partnerId0", null);
			keydata.put("GAME_API_URL", null);
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
			
		} else if(gametype.equals(GameEnum.Enum_GameType.DZDY.gametype)) {//DZDY
			
			keydata = new HashMap<String, String>();
			keydata.put("API_URL", null);
			keydata.put("API_URL_LOGIN", null);
			keydata.put("channelid", null);
			keydata.put("channelpwd", null);
			keydata.put("GAME_API_URL", null);
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
			
		} else if(gametype.equals(GameEnum.Enum_GameType.SGS.gametype)) {//SGS申博
			
			keydata = new HashMap<String, String>();
			keydata.put("API_URL", null);
			keydata.put("client_id", null);
			keydata.put("client_secret", null);
			keydata.put("LobbyDomain", null);
			keydata.put("istestplayer", null);
			keydata.put("GAME_API_URL", null);
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
			
		} else if(gametype.equals(GameEnum.Enum_GameType.GG.gametype)) {//GG游行天下
			
			keydata = new HashMap<String, String>();
			keydata.put("API_URL", null);
			keydata.put("cagent", null);
			keydata.put("MD5_KEY", null);
			keydata.put("DES_KEY", null);
			keydata.put("GAME_API_URL", null);
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
			
		} else if(gametype.equals(GameEnum.Enum_GameType.IDN.gametype)) {//IDN
			
			keydata = new HashMap<String, String>();
			keydata.put("API_URL", null);
			keydata.put("secret_key", null);
			keydata.put("LOBBY_URL", null);
			keydata.put("GAME_API_URL", null);
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
			
		} else if(gametype.equals(GameEnum.Enum_GameType.M88.gametype)) {//M88
			
			keydata = new HashMap<String, String>();
			keydata.put("SB_API_URL", null);
			keydata.put("SB_LOGIN_URL", null);
			keydata.put("SB_operator_id", null);
			
			keydata.put("SX_API_URL", null);
			keydata.put("SX_LOGIN_URL", null);
			keydata.put("SX_operator_id", null);
			keydata.put("SX_site_code", null);
			keydata.put("SX_secret_key", null);
			keydata.put("SX_product_code", null);
			keydata.put("GAME_API_URL", null);
			
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
			
		} else if(gametype.equals(GameEnum.Enum_GameType.HAB.gametype)) {//哈巴电子
			
			keydata = new HashMap<String, String>();
			keydata.put("API_URL", null);
			keydata.put("LOGIN_URL", null);
			keydata.put("BrandId", null);
			keydata.put("APIKey", null);
			keydata.put("GAME_API_URL", null);
			
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
			
		} else if(gametype.equals(GameEnum.Enum_GameType.QTtech.gametype)) {
			
			keydata = new HashMap<String, String>();
			keydata.put("API_URL", null);
			keydata.put("agentname", null);
			keydata.put("agentpwd", null);
			keydata.put("GAME_API_URL", null);
			
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
		} else if(gametype.equals(GameEnum.Enum_GameType.GB彩票.gametype)) {
			
			keydata = new HashMap<String, String>();
			keydata.put("API_URL", null);
			keydata.put("TPCode", null);
			keydata.put("SecretKey", null);
			keydata.put("GeneralKey", null);
			keydata.put("YourDomain", null);
			keydata.put("GAME_API_URL", null);
			
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
		} else if(gametype.equals(GameEnum.Enum_GameType.EBet.gametype)) {
			
			keydata = new HashMap<String, String>();
			keydata.put("API_URL", null);
			keydata.put("KeyB", null);
			keydata.put("subchannelid", null);
			keydata.put("H5LOGIN_URL", null);
			keydata.put("channelid", null);
			keydata.put("publicKey", null);
			keydata.put("privateKey", null);
			keydata.put("GAME_API_URL", null);
			
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
		} else if(gametype.equals(GameEnum.Enum_GameType.TGPlayer.gametype)) {
			
			keydata = new HashMap<String, String>();
			keydata.put("API_URL", null);
			keydata.put("client_id", null);
			keydata.put("client_secret", null);
			keydata.put("istestplayer", null);
			keydata.put("LobbyDomain", null);
			keydata.put("GAME_API_URL", null);
			
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
		} else if(gametype.equals(GameEnum.Enum_GameType.WIN88.gametype)) {
			
			keydata = new HashMap<String, String>();
			keydata.put("ENTITY_KEY", null);
			keydata.put("adminname", null);
			keydata.put("kioskname", null);
			keydata.put("IS_RELEASE", null);
			keydata.put("GAME_API_URL", null);
			
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
		} else if(gametype.equals(GameEnum.Enum_GameType.GGPoker.gametype)) {
			
			keydata = new HashMap<String, String>();
			keydata.put("API_URL", null);
			keydata.put("siteId", null);
			keydata.put("SecretKey", null);
			keydata.put("GAME_API_URL", null);
			
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
		} else if(gametype.equals(GameEnum.Enum_GameType.eIBCGame.gametype)) {
			
			keydata = new HashMap<String, String>();
			keydata.put("API_URL", null);
			keydata.put("OpCode", null);
			keydata.put("PrivateKey", null);
			keydata.put("Domain", null);
			keydata.put("CnameDomain", null);
			keydata.put("GAME_API_URL", null);
			
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
		} else if(gametype.equals(GameEnum.Enum_GameType.YoPLAY.gametype)) {
			
			keydata = new HashMap<String, String>();
			keydata.put("API_URL", null);
			keydata.put("MD5_KEY", null);
			keydata.put("DES_KEY", null);
			keydata.put("agentid", null);
			keydata.put("agentname", null);
			keydata.put("agentpwd", null);
			keydata.put("API_LOGIN_URL", null);
			keydata.put("GAME_API_URL", null);
			
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
		} else if(gametype.equals(GameEnum.Enum_GameType.去玩棋牌.gametype)) {
			
			keydata = new HashMap<String, String>();
			keydata.put("API_URL", null);
			keydata.put("AGENT_NAME", null);
			keydata.put("AGENT_PASSWORD", null);
			keydata.put("GAME_API_URL", null);
			
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
		} else if(gametype.equals(GameEnum.Enum_GameType.IM体育.gametype)) {
			
			keydata = new HashMap<String, String>();
			keydata.put("API_URL", null);
			keydata.put("MerchantCode", null);
			keydata.put("GAME_API_URL", null);
			
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
		} else if(gametype.equals(GameEnum.Enum_GameType.JDB168.gametype)) {
			
			keydata = new HashMap<String, String>();
			keydata.put("GAME_API_URL", null);
			keydata.put("API_URL", null);
			keydata.put("PARENT", null);
			keydata.put("SITE", null);
			keydata.put("KEY", null);
			keydata.put("IV", null);
			keydata.put("DC", null);
			
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprisecode, gametype, keydata);
		}
		
		
		//保存到内存
		apikeys.put(enterprisecode+gametype, keydata);
		
		return keydata;
	}
	/*
	"username";//用户名
	"password";//用户密码
	"gametype";//游戏类型
	"biggametype";//游戏类型
	"orderno";//订单编号 entity.get("ORDER_NO").toString()
	"deviceType";//设备类型 只能接受PC端与手机H5端的请求，即设备类型为0和1
	"money";//上下分金额参数
	"enterprisecode";//企业编码
	*/
	
	public static void main(String[] args) {
		String no = getAPIorderno15();
		String username = "hytry"+no;//默认20位长度
		System.out.println(username+"="+username.length());
		
		System.out.println("230331476075593368".length());
	}
	
	private static Map<String, String> tempAccount = new HashMap<String, String>();
	//测试创建游戏账户
	@Deprecated
	public static String createAccountTest(String username, String password,String gametype,String enterprisecode) {
		if(tempAccount.containsKey(username)) {
			return Enum_MSG.账号已存在.message("已存在"+username);
		} else {
			tempAccount.put(username, password);
			FileHelper.writeFile("d:/tempAccount.txt", username+"="+password+"="+gametype+"="+enterprisecode);
			return Enum_MSG.成功.message("创建完毕"+username + "=" + password + "=" + gametype + "=" + enterprisecode + "=");
		}
	}
	
	/**
	 * 创建游戏账户
	 * 
	 * @param username 一律20位长度。（AV老虎机与棋牌必须15位数、PT可以最长30位数）
	 * @param password 一律8位数长度
	 * @param gametype
	 * @param enterprisecode
	 * @return
	 * 
	 * code=0成功，返回100表示账号已存在，其他为具体错误
	 * 
	 *	info=错误消息/成功消息/具体数据
	 *
	 */
	public static String createAccount(String username, String password,String gametype,String enterprisecode) {
		Map<String, Object> entity = new HashMap<String, Object>();
		
		Map<String, String> keydata = apikeys.get(enterprisecode+gametype);//查找本企业本游戏类型的keys
		
		if(keydata == null || keydata.size() == 0) {
			keydata = initKeys(gametype, enterprisecode);
		}
		if(keydata == null || keydata.size() == 0) {
			return Enum_MSG.参数错误.message("相关API参数未找到。");
		} else {
			Iterator<String> iterator = keydata.keySet().iterator();
			while(iterator.hasNext()){
			     String key = iterator.next().toString();
			     String val = keydata.get(key);
			     if(val == null || val.equals("")) {
			    	 return Enum_MSG.参数错误.message("相关API参数未找到：参数"+key);
			     }
			}
		}
		
		if(gametype.equals(GameEnum.Enum_GameType.AG.gametype) || gametype.equals(GameEnum.Enum_GameType.PNG.gametype)) {//TAG
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String cagent = keydata.get("cagent");
			String API_URL_GCL = keydata.get("API_URL_GCL");
			String oddtype = keydata.get("oddtype");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			TAGGameAPI api = new TAGGameAPI(API_URL, MD5_KEY, DES_KEY, cagent , API_URL_GCL, oddtype, GAME_API_URL);
			entity.put("loginname", username);//用户长度必须为20位
			entity.put("password", password);
			entity.put("actype", "1");//账号类型（0、试玩，1、真钱）
			entity.put("oddtype", oddtype);//(20~50000) 
			return api.createAccount(entity).toString();//loginname,actype,password,oddtype
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.AV老虎机.gametype)) {
			
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String operatorID = keydata.get("operatorID");
			String operatorPassword = keydata.get("operatorPassword");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			AVGameAPI gameAPI = new AVGameAPI(API_URL, operatorID, operatorPassword, GAME_API_URL);
			entity.put("userID", username);//用户名长度必须为15位
			return gameAPI.createAccount(entity).toString();
			
		} else if(gametype.equals(GameEnum.Enum_GameType.PT.gametype)) {
			
			//根据企业查找对应的key
			String ENTITY_KEY = keydata.get("ENTITY_KEY");
			String adminname = keydata.get("adminname");
			String kioskname = keydata.get("kioskname");
			String IS_RELEASE = keydata.get("IS_RELEASE");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			boolean RELEASE = false;
			if(IS_RELEASE.toLowerCase().equals("true")) {
				RELEASE = true;
			}
			
			PTGameAPI ptGameAPI = new PTGameAPI(ENTITY_KEY, adminname, kioskname, RELEASE, GAME_API_URL);
			entity.put("playername", username);//用户长度不能超过30位
			entity.put("password", password);
			return ptGameAPI.createAccount(entity).toString();
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.WIN88.gametype)) {
			
			//根据企业查找对应的key
			String ENTITY_KEY = keydata.get("ENTITY_KEY");
			String adminname = keydata.get("adminname");
			String kioskname = keydata.get("kioskname");
			String IS_RELEASE = keydata.get("IS_RELEASE");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			boolean RELEASE = false;
			if(IS_RELEASE.toLowerCase().equals("true")) {
				RELEASE = true;
			}
			
			WIN88GameAPI ptGameAPI = new WIN88GameAPI(ENTITY_KEY, adminname, kioskname, RELEASE, GAME_API_URL);
			entity.put("playername", username);//用户长度不能超过30位
			entity.put("password", password);
			return ptGameAPI.createAccount(entity).toString();
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.东方.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String AGENT = keydata.get("AGENT");
			String limitvideo = keydata.get("limitvideo");
			String limitroulette = keydata.get("limitroulette");
			if(limitvideo == null || limitvideo.trim().equals("") || limitvideo.equals("null")) {
				limitvideo = "1";
			}
			if(limitroulette == null || limitroulette.trim().equals("") || limitroulette.equals("null")) {
				limitroulette = "1";
			}
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			OGGameAPI gameAPI = new OGGameAPI(API_URL, KEY, AGENT, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			entity.put("password", password);
			entity.put("limit", "1,1,1,1,1,1,1,1,1,1,1,1,1");//该值传什么都不影响,对方已经不处理该参数
			entity.put("limitvideo", limitvideo);//视讯游戏限红ID
			entity.put("limitroulette", limitroulette);//轮盘限红组ID
			return gameAPI.createAccount(entity).toString();//username,password,limit,limitvideo,limitroulette
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.东方AG.gametype)) {//
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String AGENT = keydata.get("AGENT");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			AGGameAPI gameAPI = new AGGameAPI(API_URL, KEY, AGENT, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			entity.put("password", password);
			entity.put("limit", "1,1,1,1,1,1,1,1,1,1,1,1,1");
			entity.put("limitvideo", "1");
			entity.put("limitroulette", "1");
			return gameAPI.createAccount(entity).toString();//username,password,limit,limitvideo,limitroulette
			
		} else if(gametype.equals(GameEnum.Enum_GameType.棋牌.gametype)) {
			
			
			//根据企业查找对应的key
			String serviceDomain = keydata.get("serviceDomain");
			String agentId = keydata.get("agentId");
			String apiKey = keydata.get("apiKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			QPGameAPI gameAPI = new QPGameAPI(serviceDomain, agentId, apiKey, GAME_API_URL);
			entity.put("username", username);//用户长度必须为15位
			entity.put("password", password);
			return gameAPI.createAccount(entity).toString();//username,password
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.沙巴.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String AGENT = keydata.get("AGENT");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			IBCGameAPI gameAPI = new IBCGameAPI(API_URL, KEY, AGENT, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			entity.put("password", password);
			entity.put("limit", "1,1,1,1,1,1,1,1,1,1,1,1,1");
			entity.put("limitvideo", "1");
			entity.put("limitroulette", "1");
			return gameAPI.createAccount(entity).toString();//username,password,limit,limitvideo,limitroulette
			
		} else if(gametype.equals(GameEnum.Enum_GameType.沙龙.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String CHECK_KEY = keydata.get("CHECK_KEY");
			String LOGIN_URL = keydata.get("LOGIN_URL");
			String LOBBY_CODE = keydata.get("LOBBY_CODE");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			SAGameAPI gameAPI = new SAGameAPI(API_URL, KEY, MD5_KEY, DES_KEY, CHECK_KEY, LOGIN_URL,LOBBY_CODE, GAME_API_URL);
			entity.put("Username", username);//用户长度必须为20位
			return gameAPI.createAccount(entity).toString();//
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.波音.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String API_URL_LOGIN = keydata.get("API_URL_LOGIN");
			String website = keydata.get("website");
			String uppername = keydata.get("uppername");
			
			String KeyB_createAccount = keydata.get("KeyB_createAccount");
			String KeyB_getBalance = keydata.get("KeyB_getBalance");
			String KeyB_withdraw = keydata.get("KeyB_withdraw");
			String KeyB_deposit = keydata.get("KeyB_deposit");
			String KeyB_getOrder = keydata.get("KeyB_getOrder");
			String KeyB_login = keydata.get("KeyB_login");
			
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			BBINGameAPI gameAPI = new BBINGameAPI(API_URL, website, uppername,API_URL_LOGIN, GAME_API_URL);
			entity.put("username", username);//用户名长度应为20个字符
			entity.put("password", password);
			entity.put("KeyB", KeyB_createAccount);
			return gameAPI.createAccount(entity).toString();//username,password,KeyB
			
		} else if(gametype.equals(GameEnum.Enum_GameType.洲际.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			ZJGameAPI gameAPI = new ZJGameAPI(API_URL, KEY, GAME_API_URL);
			entity.put("username", username);//用户名长度应为20个字符
			entity.put("password", password);
			entity.put("nickname", username);
			return gameAPI.createAccount(entity).toString();//username,password,nickname
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.环球.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String hyAesKey = keydata.get("hyAesKey");
			String agentname = keydata.get("agentname");
			String agentpwd = keydata.get("agentpwd");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			NHQGameAPI gameAPI = new NHQGameAPI(API_URL, MD5_KEY, DES_KEY, agentname, agentpwd, hyAesKey, GAME_API_URL);
			entity.put("username", username);//用户名长度应为20个字符
			entity.put("password", password);
			return gameAPI.createAccount(entity).toString();//username,password
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.祥瑞.gametype)) {//彩票
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String FIRST_KEY = keydata.get("FIRST_KEY");
			String LAST_KEY = keydata.get("LAST_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String agent = keydata.get("agent");
			String userpoint = keydata.get("userpoint");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			XCPGameAPI gameAPI = new XCPGameAPI(API_URL, FIRST_KEY, LAST_KEY, DES_KEY, agent, userpoint, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			entity.put("password", password);
			return gameAPI.createAccount(entity).toString();//username,password
			
		} else if(gametype.equals(GameEnum.Enum_GameType.MG.gametype)) {//MG
			
			//根据企业查找对应的key
			String HOST = keydata.get("HOST");
			String P_USM = keydata.get("P_USM");
			String P_PWD = keydata.get("P_PWD");
			String HOR_ID = keydata.get("HOR_ID");
			String apiusername = keydata.get("apiusername");
			String apipassword = keydata.get("apipassword");
			String partnerId = keydata.get("partnerId");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			MGGameAPI gameAPI = new MGGameAPI(HOST, P_USM, P_PWD, apiusername, apipassword, partnerId, HOR_ID, GAME_API_URL);
			entity.put("username", username);//用户名长度应为20个字符
			entity.put("password", password);
			entity.put("confirmPassword", password);
			//以下几个也是参数
			entity.put("crId", keydata.get("crId"));
			entity.put("crType", keydata.get("crType"));
			entity.put("neId", keydata.get("neId"));
			entity.put("neType", keydata.get("neType"));
			entity.put("tarType", keydata.get("tarType"));
			
			return gameAPI.createAccount(entity).toString();//username,password,confirmPassword,crId,crType,neId,neType,tarType
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.TTG.gametype)) {//TTG
			
			//根据企业查找对应的key
			String privateServer = keydata.get("privateServer");
			String partnerId = keydata.get("partnerId");
			String privateLoginUrl = keydata.get("privateLoginUrl");
			String prefix = keydata.get("prefix");
			String partnerId0 = keydata.get("partnerId0");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			TTGGameAPI gameAPI = new TTGGameAPI(privateServer, partnerId, privateLoginUrl,prefix, partnerId0, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			entity.put("password", password);
			entity.put("tester", "0");//1=tester,  0=不是测试.
			return gameAPI.createAccount(entity).toString();//username,password
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.DZDY.gametype)) {//DZDY
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String API_URL_LOGIN = keydata.get("API_URL_LOGIN");
			String channelid = keydata.get("channelid");
			String channelpwd = keydata.get("channelpwd");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			DZDYGameAPI gameAPI = new DZDYGameAPI(API_URL, channelid, channelpwd,API_URL_LOGIN, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			entity.put("password", password);
			return gameAPI.createAccount(entity).toString();//username,password
			
		} else if(gametype.equals(GameEnum.Enum_GameType.SGS.gametype)) {//申博
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String client_id = keydata.get("client_id");
			String client_secret = keydata.get("client_secret");
			String LobbyDomain = keydata.get("LobbyDomain");
			String istestplayer = keydata.get("istestplayer");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			SGSGameAPI gameAPI = new SGSGameAPI(API_URL, client_id, client_secret, LobbyDomain, istestplayer, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			entity.put("istestplayer", istestplayer);//用户长度必须为20位
			return gameAPI.createAccount(entity).toString();//username,password
			
		} else if(gametype.equals(GameEnum.Enum_GameType.GG.gametype)) {//GG游行天下
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String cagent = keydata.get("cagent");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			GGGameAPI gameAPI = new GGGameAPI(API_URL, MD5_KEY, DES_KEY , cagent, GAME_API_URL);
			entity.put("loginname", username);//用户长度必须为20位
			entity.put("password", password);
			entity.put("actype", "1");//真钱
			return gameAPI.createAccount(entity).toString();//loginname,password,actype
			
		} else if(gametype.equals(GameEnum.Enum_GameType.IDN.gametype)) {//IDN
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String secret_key = keydata.get("secret_key");
			String LOBBY_URL = keydata.get("LOBBY_URL");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			IDNGameAPI gameAPI = new IDNGameAPI(API_URL, secret_key, LOBBY_URL, GAME_API_URL);
			entity.put("username", username);//用户长度必须为15位
			entity.put("password", password);
			return gameAPI.createAccount(entity).toString();//username,password
			
		} else if(gametype.equals(GameEnum.Enum_GameType.M88.gametype)) {//M88
			
			//根据企业查找对应的key
			String SB_API_URL = keydata.get("SB_API_URL");
			String SB_LOGIN_URL = keydata.get("SB_LOGIN_URL");
			String SB_operator_id = keydata.get("SB_operator_id");
			
			String SX_API_URL = keydata.get("SX_API_URL");
			String SX_LOGIN_URL = keydata.get("SX_LOGIN_URL");
			String SX_operator_id = keydata.get("SX_operator_id");
			String SX_site_code = keydata.get("SX_site_code");
			String SX_secret_key = keydata.get("SX_secret_key");
			String SX_product_code = keydata.get("SX_product_code");
			
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			M88GameAPI gameAPI = new M88GameAPI(SB_API_URL, SB_LOGIN_URL, SB_operator_id, SX_API_URL, SX_operator_id, SX_LOGIN_URL,  SX_site_code, SX_secret_key, SX_product_code, GAME_API_URL);
			entity.put("username", username);//用户长度必须为17位
			entity.put("odds_type", "32");//香港盘口
			return gameAPI.createAccount(entity).toString();//username,odds_type
			
		} else if(gametype.equals(GameEnum.Enum_GameType.HAB.gametype)) {//哈巴电子
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String LOGIN_URL = keydata.get("LOGIN_URL");
			String BrandId = keydata.get("BrandId");
			String APIKey = keydata.get("APIKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			HBGameAPI gameAPI = new HBGameAPI( BrandId,  APIKey,  API_URL,  LOGIN_URL, GAME_API_URL);
			entity.put("username", username);//
			entity.put("password", password);//
			entity.put("istrueplay", "true");//
			return gameAPI.createAccount(entity).toString();//username,password,istrueplay
			
		} else if(gametype.equals(GameEnum.Enum_GameType.QTtech.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String agentname = keydata.get("agentname");
			String agentpwd = keydata.get("agentpwd");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			QTGameAPI gameAPI = new QTGameAPI(API_URL, agentname, agentpwd, GAME_API_URL);
			entity.put("playerId", username);//
			entity.put("password", password);//
			entity.put("istrueplay", "true");//
			return gameAPI.createAccount(entity).toString();//playerId,password,istrueplay
			
		} else if(gametype.equals(GameEnum.Enum_GameType.GB彩票.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String TPCode = keydata.get("TPCode");
			String SecretKey = keydata.get("SecretKey");
			String GeneralKey = keydata.get("GeneralKey");
			String YourDomain = keydata.get("YourDomain");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			GBGameAPI gameAPI = new GBGameAPI( API_URL, TPCode, SecretKey, GeneralKey, YourDomain, GAME_API_URL);
			entity.put("MemberID", username);//
			entity.put("password", password);//
			return gameAPI.createAccount(entity).toString();//MemberID,password
			
		} else if(gametype.equals(GameEnum.Enum_GameType.EBet.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KeyB = keydata.get("KeyB");
			String subchannelid = keydata.get("subchannelid");
			String H5LOGIN_URL = keydata.get("H5LOGIN_URL");
			String channelid = keydata.get("channelid");
			String publicKey = keydata.get("publicKey");
			String privateKey = keydata.get("privateKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			EBetGameAPI gameAPI = new EBetGameAPI( API_URL, KeyB, subchannelid, H5LOGIN_URL,  channelid,  publicKey,  privateKey, GAME_API_URL);
			entity.put("username", username);//
			entity.put("password", password);//
			return gameAPI.createAccount(entity).toString();//username,password
			
		} else if(gametype.equals(GameEnum.Enum_GameType.TGPlayer.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String client_id = keydata.get("client_id");
			String client_secret = keydata.get("client_secret");
			String istestplayer = keydata.get("istestplayer");
			String LobbyDomain = keydata.get("LobbyDomain");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			TGPGameAPI gameAPI = new TGPGameAPI( API_URL, client_id, client_secret, istestplayer,  LobbyDomain, GAME_API_URL);
			entity.put("username", username);//
			entity.put("password", password);//
			return gameAPI.createAccount(entity).toString();//username
			
		} else if(gametype.equals(GameEnum.Enum_GameType.GGPoker.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String siteId = keydata.get("siteId");
			String SecretKey = keydata.get("SecretKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			GGPGameAPI gameAPI = new GGPGameAPI( API_URL, siteId, SecretKey, GAME_API_URL);
			entity.put("username", username);//
			entity.put("password", password);//
			return gameAPI.createAccount(entity).toString();//username
			
		} else if(gametype.equals(GameEnum.Enum_GameType.eIBCGame.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String OpCode = keydata.get("OpCode");
			String PrivateKey = keydata.get("PrivateKey");
			String Domain = keydata.get("Domain");
			String CnameDomain = keydata.get("CnameDomain");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			IBCEGameAPI gameAPI = new IBCEGameAPI( API_URL, OpCode, PrivateKey, Domain,CnameDomain, GAME_API_URL);
			entity.put("PlayerName", username);//
			entity.put("password", password);//
			return gameAPI.createAccount(entity).toString();//PlayerName,password
			
		} else if(gametype.equals(GameEnum.Enum_GameType.YoPLAY.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String agentid = keydata.get("agentid");
			String agentname = keydata.get("agentname");
			String agentpwd = keydata.get("agentpwd");
			String API_LOGIN_URL = keydata.get("API_LOGIN_URL");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			YoPLAYGameAPI gameAPI = new YoPLAYGameAPI(API_URL, MD5_KEY, DES_KEY,  agentid,  agentname,  agentpwd, API_LOGIN_URL, GAME_API_URL);
			entity.put("loginname", username);//
			entity.put("password", password);//
			entity.put("acctype", "1");//
			return gameAPI.createAccount(entity).toString();//loginname,password,acctype
			
		} else if(gametype.equals(GameEnum.Enum_GameType.去玩棋牌.gametype)) {
			String API_URL = keydata.get("API_URL");
			String AGENT_NAME = keydata.get("AGENT_NAME");
			String AGENT_PASSWORD = keydata.get("AGENT_PASSWORD");
			String GAME_API_URL = keydata.get("GAME_API_URL");
			
			QWPGameAPI gameAPI = new QWPGameAPI(API_URL, AGENT_NAME, AGENT_PASSWORD, GAME_API_URL);
			entity.put("Account", username);
			entity.put("Password", password);
			return gameAPI.createAccount(entity).toString();
		} else if(gametype.equals(GameEnum.Enum_GameType.IM体育.gametype)) {
			String API_URL = keydata.get("API_URL");
			String MerchantCode = keydata.get("MerchantCode");
			String GAME_API_URL = keydata.get("GAME_API_URL");
			
			IMGameAPI gameAPI = new IMGameAPI(API_URL, MerchantCode, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			entity.put("Currency", "CNY");
			return gameAPI.createAccount(entity).toString();//username,password,Currency
		} else if (gametype.equals(GameEnum.Enum_GameType.JDB168.gametype)) {
			String GAME_API_URL = keydata.get("GAME_API_URL");
			String API_URL = keydata.get("API_URL");
			String PARENT = keydata.get("PARENT");
			String SITE = keydata.get("SITE");
			String KEY = keydata.get("KEY");
			String IV = keydata.get("IV");
			String DC = keydata.get("DC");
			
			JDB168GameAPI gameAPI = new JDB168GameAPI(API_URL, GAME_API_URL, PARENT, DC, SITE, IV, KEY);
			entity.put("uid", username);
			entity.put("name", username);
			return gameAPI.createAccount(entity).toString();
		}
		
		return Enum_MSG.参数错误.message("游戏业务类型不正确");
	}

	/**
	 * 查询用户余额
	 * 
	 * @param username
	 * @param password
	 * @param gametype
	 * @param enterprisecode
	 * @return
	 * code=0成功，其他失败
	 *	info=错误消息/成功消息/具体数据
	 */
	public static Object getBalance(String username, String password,String gametype,String enterprisecode) {
		Map<String, Object> entity = new HashMap<String, Object>();
		
		Map<String, String> keydata = apikeys.get(enterprisecode+gametype);//查找本企业本游戏类型的keys
		
		if(keydata == null || keydata.size() == 0) {
			keydata = initKeys(gametype, enterprisecode);
		}
		if(keydata == null || keydata.size() == 0) {
			return Enum_MSG.参数错误.message("相关API参数未找到。");
		} else {
			Iterator<String> iterator = keydata.keySet().iterator();
			while(iterator.hasNext()){
			     String key = iterator.next().toString();
			     String val = keydata.get(key);
			     if(val == null || val.equals("")) {
			    	 return Enum_MSG.参数错误.message("相关API参数未找到：参数"+key);
			     }
			}
		}
		
		if(gametype.equals(GameEnum.Enum_GameType.AG.gametype) || gametype.equals(GameEnum.Enum_GameType.PNG.gametype)) {//TAG
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String cagent = keydata.get("cagent");
			String API_URL_GCL = keydata.get("API_URL_GCL");
			String oddtype = keydata.get("oddtype");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			TAGGameAPI api = new TAGGameAPI(API_URL, MD5_KEY, DES_KEY, cagent , API_URL_GCL, oddtype, GAME_API_URL);
			entity.put("loginname", username);
			entity.put("password", password);
			entity.put("actype", "1");//账号类型（0、试玩，1、真钱）
			entity.put("oddtype", oddtype);
			return api.getBalance(entity);//loginname,actype,password
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.AV老虎机.gametype)) {
			
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String operatorID = keydata.get("operatorID");
			String operatorPassword = keydata.get("operatorPassword");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			AVGameAPI gameAPI = new AVGameAPI(API_URL, operatorID, operatorPassword, GAME_API_URL);
			entity.put("userID", username);
			return gameAPI.getBalance(entity);
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.PT.gametype)) {
			
			//根据企业查找对应的key
			String ENTITY_KEY = keydata.get("ENTITY_KEY");
			String adminname = keydata.get("adminname");
			String kioskname = keydata.get("kioskname");
			String IS_RELEASE = keydata.get("IS_RELEASE");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			boolean RELEASE = false;
			if(IS_RELEASE.toLowerCase().equals("true")) {
				RELEASE = true;
			}
			
			
			entity.put("playername", username);
			entity.put("password", password);
			
			PTGameAPI ptGameAPI = new PTGameAPI(ENTITY_KEY, adminname, kioskname, RELEASE, GAME_API_URL);
			return ptGameAPI.getBalance(entity);
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.WIN88.gametype)) {
			
			//根据企业查找对应的key
			String ENTITY_KEY = keydata.get("ENTITY_KEY");
			String adminname = keydata.get("adminname");
			String kioskname = keydata.get("kioskname");
			String IS_RELEASE = keydata.get("IS_RELEASE");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			boolean RELEASE = false;
			if(IS_RELEASE.toLowerCase().equals("true")) {
				RELEASE = true;
			}
			
			
			entity.put("playername", username);
			entity.put("password", password);
			
			WIN88GameAPI ptGameAPI = new WIN88GameAPI(ENTITY_KEY, adminname, kioskname, RELEASE, GAME_API_URL);
			return ptGameAPI.getBalance(entity);
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.东方.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String AGENT = keydata.get("AGENT");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			OGGameAPI gameAPI = new OGGameAPI(API_URL, KEY, AGENT, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			return gameAPI.getBalance(entity);//username,password
			
		} else if(gametype.equals(GameEnum.Enum_GameType.东方AG.gametype)) {//已取消
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String AGENT = keydata.get("AGENT");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			AGGameAPI gameAPI = new AGGameAPI(API_URL, KEY, AGENT, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			return gameAPI.getBalance(entity);//username,password
			
		} else if(gametype.equals(GameEnum.Enum_GameType.棋牌.gametype)) {
			
			//根据企业查找对应的key
			String serviceDomain = keydata.get("serviceDomain");
			String agentId = keydata.get("agentId");
			String apiKey = keydata.get("apiKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			QPGameAPI gameAPI = new QPGameAPI(serviceDomain, agentId, apiKey, GAME_API_URL);
			entity.put("username", username);//用户长度必须为15位
			entity.put("password", password);
			return gameAPI.getBalance(entity);//username,password
			
		} else if(gametype.equals(GameEnum.Enum_GameType.沙巴.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String AGENT = keydata.get("AGENT");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			IBCGameAPI gameAPI = new IBCGameAPI(API_URL, KEY, AGENT, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			return gameAPI.getBalance(entity);//username,password
			
		} else if(gametype.equals(GameEnum.Enum_GameType.沙龙.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String CHECK_KEY = keydata.get("CHECK_KEY");
			String LOGIN_URL = keydata.get("LOGIN_URL");
			String LOBBY_CODE = keydata.get("LOBBY_CODE");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			SAGameAPI gameAPI = new SAGameAPI(API_URL, KEY, MD5_KEY, DES_KEY, CHECK_KEY, LOGIN_URL , LOBBY_CODE, GAME_API_URL);
			entity.put("Username", username);
			return gameAPI.getBalance(entity);//
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.波音.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String API_URL_LOGIN = keydata.get("API_URL_LOGIN");
			String website = keydata.get("website");
			String uppername = keydata.get("uppername");
			
			String KeyB_createAccount = keydata.get("KeyB_createAccount");
			String KeyB_getBalance = keydata.get("KeyB_getBalance");
			String KeyB_withdraw = keydata.get("KeyB_withdraw");
			String KeyB_deposit = keydata.get("KeyB_deposit");
			String KeyB_getOrder = keydata.get("KeyB_getOrder");
			String KeyB_login = keydata.get("KeyB_login");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			BBINGameAPI gameAPI = new BBINGameAPI(API_URL, website, uppername,API_URL_LOGIN, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			entity.put("KeyB", KeyB_getBalance);
			return gameAPI.getBalance(entity);//username,password,KeyB
			
		} else if(gametype.equals(GameEnum.Enum_GameType.洲际.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			ZJGameAPI gameAPI = new ZJGameAPI(API_URL, KEY, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			return gameAPI.getBalance(entity);//username,password
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.环球.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String hyAesKey = keydata.get("hyAesKey");
			String agentname = keydata.get("agentname");
			String agentpwd = keydata.get("agentpwd");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			NHQGameAPI gameAPI = new NHQGameAPI(API_URL, MD5_KEY, DES_KEY, agentname, agentpwd, hyAesKey, GAME_API_URL);
			entity.put("username", username);
			return gameAPI.getBalance(entity);//username,password
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.祥瑞.gametype)) {//彩票
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String FIRST_KEY = keydata.get("FIRST_KEY");
			String LAST_KEY = keydata.get("LAST_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String agent = keydata.get("agent");
			String userpoint = keydata.get("userpoint");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			XCPGameAPI gameAPI = new XCPGameAPI(API_URL, FIRST_KEY, LAST_KEY, DES_KEY, agent, userpoint, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			return gameAPI.getBalance(entity);//username
			
		} else if(gametype.equals(GameEnum.Enum_GameType.MG.gametype)) {//MG
			
			//根据企业查找对应的key
			String HOST = keydata.get("HOST");
			String P_USM = keydata.get("P_USM");
			String P_PWD = keydata.get("P_PWD");
			String HOR_ID = keydata.get("HOR_ID");
			String apiusername = keydata.get("apiusername");
			String apipassword = keydata.get("apipassword");
			String partnerId = keydata.get("partnerId");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			MGGameAPI gameAPI = new MGGameAPI(HOST, P_USM, P_PWD, apiusername, apipassword, partnerId, HOR_ID, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			return gameAPI.getBalance(entity);//username,password
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.TTG.gametype)) {//TTG
			
			//根据企业查找对应的key
			String privateServer = keydata.get("privateServer");
			String partnerId = keydata.get("partnerId");
			String privateLoginUrl = keydata.get("privateLoginUrl");
			String prefix = keydata.get("prefix");
			String partnerId0 = keydata.get("partnerId0");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			TTGGameAPI gameAPI = new TTGGameAPI(privateServer, partnerId, privateLoginUrl, prefix,partnerId0, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			return gameAPI.getBalance(entity);//username
			
		} else if(gametype.equals(GameEnum.Enum_GameType.DZDY.gametype)) {//DZDY
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String API_URL_LOGIN = keydata.get("API_URL_LOGIN");
			String channelid = keydata.get("channelid");
			String channelpwd = keydata.get("channelpwd");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			DZDYGameAPI gameAPI = new DZDYGameAPI(API_URL, channelid, channelpwd,API_URL_LOGIN, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			return gameAPI.getBalance(entity).toString();//username
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.SGS.gametype)) {//申博
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String client_id = keydata.get("client_id");
			String client_secret = keydata.get("client_secret");
			String LobbyDomain = keydata.get("LobbyDomain");
			String istestplayer = keydata.get("istestplayer");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			SGSGameAPI gameAPI = new SGSGameAPI(API_URL, client_id, client_secret, LobbyDomain, istestplayer, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			return gameAPI.getBalance(entity).toString();//username
			
		} else if(gametype.equals(GameEnum.Enum_GameType.GG.gametype)) {//GG游行天下
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String cagent = keydata.get("cagent");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			GGGameAPI gameAPI = new GGGameAPI(API_URL, MD5_KEY, DES_KEY , cagent, GAME_API_URL);
			entity.put("loginname", username);//用户长度必须为20位
			entity.put("password", password);
			return gameAPI.getBalance(entity).toString();//loginname,password
			
		} else if(gametype.equals(GameEnum.Enum_GameType.IDN.gametype)) {//IDN
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String secret_key = keydata.get("secret_key");
			String LOBBY_URL = keydata.get("LOBBY_URL");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			IDNGameAPI gameAPI = new IDNGameAPI(API_URL, secret_key, LOBBY_URL, GAME_API_URL);
			entity.put("username", username);//用户长度必须为15位
			return gameAPI.getBalance(entity).toString();//username,password
			
		} else if(gametype.equals(GameEnum.Enum_GameType.M88.gametype)) {//M88
			
			//根据企业查找对应的key
			String SB_API_URL = keydata.get("SB_API_URL");
			String SB_LOGIN_URL = keydata.get("SB_LOGIN_URL");
			String SB_operator_id = keydata.get("SB_operator_id");
			
			String SX_API_URL = keydata.get("SX_API_URL");
			String SX_LOGIN_URL = keydata.get("SX_LOGIN_URL");
			String SX_operator_id = keydata.get("SX_operator_id");
			String SX_site_code = keydata.get("SX_site_code");
			String SX_secret_key = keydata.get("SX_secret_key");
			String SX_product_code = keydata.get("SX_product_code");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			M88GameAPI gameAPI = new M88GameAPI(SB_API_URL, SB_LOGIN_URL, SB_operator_id, SX_API_URL, SX_operator_id, SX_LOGIN_URL,  SX_site_code, SX_secret_key, SX_product_code, GAME_API_URL);
			entity.put("username", username);//用户长度必须为17位
			return gameAPI.getBalance(entity).toString();//username,odds_type
			
		} else if(gametype.equals(GameEnum.Enum_GameType.HAB.gametype)) {//哈巴电子
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String LOGIN_URL = keydata.get("LOGIN_URL");
			String BrandId = keydata.get("BrandId");
			String APIKey = keydata.get("APIKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			HBGameAPI gameAPI = new HBGameAPI( BrandId,  APIKey,  API_URL,  LOGIN_URL, GAME_API_URL);
			entity.put("username", username);//
			entity.put("password", password);//
			return gameAPI.getBalance(entity).toString();//username,password
			
		} else if(gametype.equals(GameEnum.Enum_GameType.QTtech.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String agentname = keydata.get("agentname");
			String agentpwd = keydata.get("agentpwd");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			QTGameAPI gameAPI = new QTGameAPI(API_URL, agentname, agentpwd, GAME_API_URL);
			entity.put("playerId", username);//
			return gameAPI.getBalance(entity).toString();//playerId
			
		} else if(gametype.equals(GameEnum.Enum_GameType.GB彩票.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String TPCode = keydata.get("TPCode");
			String SecretKey = keydata.get("SecretKey");
			String GeneralKey = keydata.get("GeneralKey");
			String YourDomain = keydata.get("YourDomain");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			GBGameAPI gameAPI = new GBGameAPI( API_URL, TPCode, SecretKey, GeneralKey, YourDomain, GAME_API_URL);
			entity.put("MemberID", username);//
			return gameAPI.getBalance(entity).toString();//MemberID
			
		} else if(gametype.equals(GameEnum.Enum_GameType.EBet.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KeyB = keydata.get("KeyB");
			String subchannelid = keydata.get("subchannelid");
			String H5LOGIN_URL = keydata.get("H5LOGIN_URL");
			String channelid = keydata.get("channelid");
			String publicKey = keydata.get("publicKey");
			String privateKey = keydata.get("privateKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			EBetGameAPI gameAPI = new EBetGameAPI( API_URL, KeyB, subchannelid, H5LOGIN_URL,  channelid,  publicKey,  privateKey, GAME_API_URL);
			entity.put("username", username);//
			entity.put("password", password);//
			return gameAPI.getBalance(entity).toString();//username,password
			
		} else if(gametype.equals(GameEnum.Enum_GameType.TGPlayer.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String client_id = keydata.get("client_id");
			String client_secret = keydata.get("client_secret");
			String istestplayer = keydata.get("istestplayer");
			String LobbyDomain = keydata.get("LobbyDomain");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			TGPGameAPI gameAPI = new TGPGameAPI( API_URL, client_id, client_secret, istestplayer,  LobbyDomain, GAME_API_URL);
			entity.put("username", username);//
			entity.put("password", password);//
			return gameAPI.getBalance(entity).toString();//username
			
		} else if(gametype.equals(GameEnum.Enum_GameType.GGPoker.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String siteId = keydata.get("siteId");
			String SecretKey = keydata.get("SecretKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			GGPGameAPI gameAPI = new GGPGameAPI( API_URL, siteId, SecretKey, GAME_API_URL);
			entity.put("username", username);//
			entity.put("password", password);//
			JSONObject jsonObject = JSONObject.fromObject(gameAPI.getBalance(entity).toString()) ;//username
			
			if(jsonObject.getString("code").equals("0")) {
				String real_money = MoneyHelper.doubleFormat(jsonObject.getDouble("info") / SystemCache.getInstance().getExchangeRateAll("USD"));//转美元
				return Enum_MSG.成功.message(real_money + "");
			}
			return jsonObject.toString();
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.eIBCGame.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String OpCode = keydata.get("OpCode");
			String PrivateKey = keydata.get("PrivateKey");
			String Domain = keydata.get("Domain");
			String CnameDomain = keydata.get("CnameDomain");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			IBCEGameAPI gameAPI = new IBCEGameAPI( API_URL, OpCode, PrivateKey, Domain, CnameDomain, GAME_API_URL);
			entity.put("PlayerName", username);//
			entity.put("password", password);//
			return gameAPI.getBalance(entity).toString();//PlayerName,password
			
		} else if(gametype.equals(GameEnum.Enum_GameType.YoPLAY.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String agentid = keydata.get("agentid");
			String agentname = keydata.get("agentname");
			String agentpwd = keydata.get("agentpwd");
			String API_LOGIN_URL = keydata.get("API_LOGIN_URL");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			YoPLAYGameAPI gameAPI = new YoPLAYGameAPI(API_URL, MD5_KEY, DES_KEY,  agentid,  agentname,  agentpwd, API_LOGIN_URL, GAME_API_URL);
			entity.put("loginname", username);//
			entity.put("password", password);//
			entity.put("acctype", "1");//
			return gameAPI.getBalance(entity).toString();//loginname,password,acctype
			
		} else if (gametype.equals(GameEnum.Enum_GameType.去玩棋牌.gametype)) {
			String API_URL = keydata.get("API_URL");
			String AGENT_NAME = keydata.get("AGENT_NAME");
			String AGENT_PASSWORD = keydata.get("AGENT_PASSWORD");
			String GAME_API_URL = keydata.get("GAME_API_URL");
			
			QWPGameAPI gameAPI = new QWPGameAPI(API_URL, AGENT_NAME, AGENT_PASSWORD, GAME_API_URL);
			entity.put("account", username);
			return gameAPI.getBalance(entity).toString();
		} else if(gametype.equals(GameEnum.Enum_GameType.IM体育.gametype)) {
			String API_URL = keydata.get("API_URL");
			String MerchantCode = keydata.get("MerchantCode");
			String GAME_API_URL = keydata.get("GAME_API_URL");
			
			IMGameAPI gameAPI = new IMGameAPI(API_URL, MerchantCode, GAME_API_URL);
			entity.put("username", username);
			entity.put("ProductWallet", "301");//IM体育
			return gameAPI.getBalance(entity).toString();//username,ProductWallet
		} else if (gametype.equals(GameEnum.Enum_GameType.JDB168.gametype)) {
			String GAME_API_URL = keydata.get("GAME_API_URL");
			String API_URL = keydata.get("API_URL");
			String PARENT = keydata.get("PARENT");
			String SITE = keydata.get("SITE");
			String KEY = keydata.get("KEY");
			String IV = keydata.get("IV");
			String DC = keydata.get("DC");
			
			JDB168GameAPI gameAPI = new JDB168GameAPI(API_URL, GAME_API_URL, PARENT, DC, SITE, IV, KEY);
			entity.put("uid", username);
			return gameAPI.getBalance(entity).toString();
		}
		
		
		return Enum_MSG.参数错误.message("游戏业务类型不正确");
		
	}

	
	/**
	 * 订单查询
	 * 
	 * <p>MG目前不支持订单查询</p>
	 * 
	 * 
	 * @param username
	 * @param password
	 * @param gametype
	 * @param enterprisecode
	 * @param orderno 统一19位数
	 * @param money 金额应大于1，并且是整数 （对于东方、沙巴、东方AG订单查询需要该笔交易的具体金额）
	 * @return
	 * code=0成功，其他失败
	 *	info=错误消息/成功消息/具体数据
	 */
	public static Object getOrder(String username, String password,String gametype,String enterprisecode,String orderno,int money) {
		Map<String, Object> entity = new HashMap<String, Object>();
		
		
		Map<String, String> keydata = apikeys.get(enterprisecode+gametype);//查找本企业本游戏类型的keys
		
		if(keydata == null || keydata.size() == 0) {
			keydata = initKeys(gametype, enterprisecode);
		}
		if(keydata == null || keydata.size() == 0) {
			return Enum_MSG.参数错误.message("相关API参数未找到。");
		} else {
			Iterator<String> iterator = keydata.keySet().iterator();
			while(iterator.hasNext()){
			     String key = iterator.next().toString();
			     String val = keydata.get(key);
			     if(val == null || val.equals("")) {
			    	 return Enum_MSG.参数错误.message("相关API参数未找到：参数"+key);
			     }
			}
		}
		
		
		if(gametype.equals(GameEnum.Enum_GameType.AG.gametype) || gametype.equals(GameEnum.Enum_GameType.PNG.gametype)) {//TAG
			
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String cagent = keydata.get("cagent");
			String API_URL_GCL = keydata.get("API_URL_GCL");
			String oddtype = keydata.get("oddtype");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			TAGGameAPI api = new TAGGameAPI(API_URL, MD5_KEY, DES_KEY, cagent , API_URL_GCL, oddtype, GAME_API_URL);
			entity.put("actype", "1");//账号类型（0、试玩，1、真钱）
			entity.put("billno", getAPIOrderid(orderno));
			entity.put("oddtype", oddtype);
			return api.getOrder(entity);//billno,actype 订单号必须是16位数的数字
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.AV老虎机.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String operatorID = keydata.get("operatorID");
			String operatorPassword = keydata.get("operatorPassword");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			AVGameAPI gameAPI = new AVGameAPI(API_URL, operatorID, operatorPassword, GAME_API_URL);
			entity.put("userID", username);
			entity.put("transactionID", getAPIOrderid(orderno));
			entity.put("transType", orderno.startsWith("1")? "DEPOSIT" : "WITHDRAW");//交易类型只能接受DEPOSIT存款与WITHDRAW取款两种标识符  综合平台的上分单号是1打头的，2是下分打头的
			return gameAPI.getOrder(entity);//userID,transactionID,transType
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.PT.gametype)) {
			
			//根据企业查找对应的key
			String ENTITY_KEY = keydata.get("ENTITY_KEY");
			String adminname = keydata.get("adminname");
			String kioskname = keydata.get("kioskname");
			String IS_RELEASE = keydata.get("IS_RELEASE");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			boolean RELEASE = false;
			if(IS_RELEASE.toLowerCase().equals("true")) {
				RELEASE = true;
			}
			
			entity.put("externaltransactionid", getAPIOrderid(orderno));
			
			PTGameAPI ptGameAPI = new PTGameAPI(ENTITY_KEY, adminname, kioskname, RELEASE, GAME_API_URL);
			return ptGameAPI.getOrder(entity);
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.WIN88.gametype)) {
			
			//根据企业查找对应的key
			String ENTITY_KEY = keydata.get("ENTITY_KEY");
			String adminname = keydata.get("adminname");
			String kioskname = keydata.get("kioskname");
			String IS_RELEASE = keydata.get("IS_RELEASE");
			
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			boolean RELEASE = false;
			if(IS_RELEASE.toLowerCase().equals("true")) {
				RELEASE = true;
			}
			
			entity.put("externaltransactionid", getAPIOrderid(orderno));
			
			WIN88GameAPI ptGameAPI = new WIN88GameAPI(ENTITY_KEY, adminname, kioskname, RELEASE, GAME_API_URL);
			return ptGameAPI.getOrder(entity);
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.东方.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String AGENT = keydata.get("AGENT");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			OGGameAPI gameAPI = new OGGameAPI(API_URL, KEY, AGENT, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			entity.put("billno", getAPIOrderid(orderno));
			entity.put("credit", money);
			entity.put("type", orderno.startsWith("1")? "IN" : "OUT");//type=IN/OUT  综合平台的上分单号是1打头的，2是下分打头的
			return gameAPI.getOrder(entity);//username,password,billno,credit,type
			
		} else if(gametype.equals(GameEnum.Enum_GameType.东方AG.gametype)) {//已取消
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String AGENT = keydata.get("AGENT");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			AGGameAPI gameAPI = new AGGameAPI(API_URL, KEY, AGENT, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			entity.put("billno", getAPIOrderid(orderno));
			entity.put("credit", money);
			entity.put("type", orderno.startsWith("1")? "IN" : "OUT");//type=IN/OUT  综合平台的上分单号是1打头的，2是下分打头的
			return gameAPI.getOrder(entity);//username,password,billno,credit,type
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.棋牌.gametype)) {
			
			//根据企业查找对应的key
			String serviceDomain = keydata.get("serviceDomain");
			String agentId = keydata.get("agentId");
			String apiKey = keydata.get("apiKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			QPGameAPI gameAPI = new QPGameAPI(serviceDomain, agentId, apiKey, GAME_API_URL);
			entity.put("username", username);//用户长度必须为15位
			entity.put("orderID", getAPIOrderid(orderno));
			return gameAPI.getOrder(entity);//username,orderID
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.沙巴.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String AGENT = keydata.get("AGENT");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			IBCGameAPI gameAPI = new IBCGameAPI(API_URL, KEY, AGENT, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			entity.put("billno", getAPIOrderid(orderno));
			entity.put("credit", money);
			entity.put("type", orderno.startsWith("1")? "IN" : "OUT");//type=IN/OUT  综合平台的上分单号是1打头的，2是下分打头的
			return gameAPI.getOrder(entity);//username,password,billno,credit,type
			
		} else if(gametype.equals(GameEnum.Enum_GameType.沙龙.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String CHECK_KEY = keydata.get("CHECK_KEY");
			String LOGIN_URL = keydata.get("LOGIN_URL");
			String LOBBY_CODE = keydata.get("LOBBY_CODE");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			SAGameAPI gameAPI = new SAGameAPI(API_URL, KEY, MD5_KEY, DES_KEY, CHECK_KEY, LOGIN_URL, LOBBY_CODE, GAME_API_URL);
			entity.put("OrderId", getAPIOrderid(orderno));
			return gameAPI.getOrder(entity);//
			
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.波音.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String API_URL_LOGIN = keydata.get("API_URL_LOGIN");
			String website = keydata.get("website");
			String uppername = keydata.get("uppername");
			
			String KeyB_createAccount = keydata.get("KeyB_createAccount");
			String KeyB_getBalance = keydata.get("KeyB_getBalance");
			String KeyB_withdraw = keydata.get("KeyB_withdraw");
			String KeyB_deposit = keydata.get("KeyB_deposit");
			String KeyB_getOrder = keydata.get("KeyB_getOrder");
			String KeyB_login = keydata.get("KeyB_login");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			BBINGameAPI gameAPI = new BBINGameAPI(API_URL, website, uppername, API_URL_LOGIN, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			entity.put("transid", getAPIOrderid(orderno));//19位数
			entity.put("KeyB", KeyB_getOrder);
			return gameAPI.getOrder(entity);//username,password,KeyB,transid
			
		} else if(gametype.equals(GameEnum.Enum_GameType.洲际.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			ZJGameAPI gameAPI = new ZJGameAPI(API_URL, KEY, GAME_API_URL);
			entity.put("ref", getAPIOrderid(orderno));
			return gameAPI.getOrder(entity);//ref
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.环球.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String hyAesKey = keydata.get("hyAesKey");
			String agentname = keydata.get("agentname");
			String agentpwd = keydata.get("agentpwd");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			//新环球特殊，如果上下分不成功，则对方不会返回主键单号，所以可能为空
			String apiOrderid = getAPIOrderid(orderno);
			if(apiOrderid == null || apiOrderid.equals("") || apiOrderid.equals("null")){
				return Enum_MSG.失败.message("未能查找到API单号，可能是上下分不成功！");
			}
			
			NHQGameAPI gameAPI = new NHQGameAPI(API_URL, MD5_KEY, DES_KEY, agentname, agentpwd, hyAesKey, GAME_API_URL);
			entity.put("orderid", apiOrderid);
			entity.put("type", orderno.startsWith("1")? "confirmdeposit" : "confirmwithdraw" );//type=上分或者下分类型，confirmdeposit=上分确认，confirmwithdraw=下分确认
			return gameAPI.getOrder(entity);//orderid,type
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.祥瑞.gametype)) {//彩票
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String FIRST_KEY = keydata.get("FIRST_KEY");
			String LAST_KEY = keydata.get("LAST_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String agent = keydata.get("agent");
			String userpoint = keydata.get("userpoint");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			XCPGameAPI gameAPI = new XCPGameAPI(API_URL, FIRST_KEY, LAST_KEY, DES_KEY, agent, userpoint, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			entity.put("ordernum", getAPIOrderid(orderno));//32位数
			return gameAPI.getOrder(entity);//username,ordernum
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.MG.gametype)) {//MG=不提供订单查询接口，目前好像有点问题
			
			//根据企业查找对应的key
			String HOST = keydata.get("HOST");
			String P_USM = keydata.get("P_USM");
			String P_PWD = keydata.get("P_PWD");
			String HOR_ID = keydata.get("HOR_ID");
			String apiusername = keydata.get("apiusername");
			String apipassword = keydata.get("apipassword");
			String partnerId = keydata.get("partnerId");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			MGGameAPI gameAPI = new MGGameAPI(HOST, P_USM, P_PWD, apiusername, apipassword, partnerId, HOR_ID, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			return Enum_MSG.接口调用错误.message("MG目前不支持订单查询");
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.TTG.gametype)) {//TTG
			
			//根据企业查找对应的key
			String privateServer = keydata.get("privateServer");
			String partnerId = keydata.get("partnerId");
			String privateLoginUrl = keydata.get("privateLoginUrl");
			String prefix = keydata.get("prefix");
			String partnerId0 = keydata.get("partnerId0");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			TTGGameAPI gameAPI = new TTGGameAPI(privateServer, partnerId, privateLoginUrl, prefix, partnerId0, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			entity.put("exttransactionID", getAPIOrderid(orderno));//用户长度必须为20位
			return gameAPI.getOrder(entity);//username,exttransactionID
			
		} else if(gametype.equals(GameEnum.Enum_GameType.DZDY.gametype)) {//DZDY
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String API_URL_LOGIN = keydata.get("API_URL_LOGIN");
			String channelid = keydata.get("channelid");
			String channelpwd = keydata.get("channelpwd");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			//新环球特殊，如果上下分不成功，则对方不会返回主键单号，所以可能为空
			String apiOrderid = getAPIOrderid(orderno);
			if(apiOrderid == null || apiOrderid.equals("") || apiOrderid.equals("null")){
				return Enum_MSG.失败.message("未能查找到API单号，可能是上下分不成功！");
			}
			
			DZDYGameAPI gameAPI = new DZDYGameAPI(API_URL, channelid, channelpwd,API_URL_LOGIN, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			entity.put("ordernum", apiOrderid);//
			return gameAPI.getOrder(entity).toString();//username,ordernum
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.SGS.gametype)) {//申博
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String client_id = keydata.get("client_id");
			String client_secret = keydata.get("client_secret");
			String LobbyDomain = keydata.get("LobbyDomain");
			String istestplayer = keydata.get("istestplayer");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			SGSGameAPI gameAPI = new SGSGameAPI(API_URL, client_id, client_secret, LobbyDomain, istestplayer, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			entity.put("password", password);
			return Enum_MSG.接口调用错误.message("SGS目前不支持订单查询");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.GG.gametype)) {//GG游行天下
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String cagent = keydata.get("cagent");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			GGGameAPI gameAPI = new GGGameAPI(API_URL, MD5_KEY, DES_KEY , cagent, GAME_API_URL);
			entity.put("billno", getAPIOrderid(orderno));
			return gameAPI.getOrder(entity).toString();//billno
			
		} else if(gametype.equals(GameEnum.Enum_GameType.IDN.gametype)) {//IDN
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String secret_key = keydata.get("secret_key");
			String LOBBY_URL = keydata.get("LOBBY_URL");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			IDNGameAPI gameAPI = new IDNGameAPI(API_URL, secret_key, LOBBY_URL, GAME_API_URL);
			entity.put("username", username);//用户长度必须为15位
			entity.put("orderid", getAPIOrderid(orderno));
			return gameAPI.getOrder(entity).toString();//username,orderid
			
		} else if(gametype.equals(GameEnum.Enum_GameType.M88.gametype)) {//M88
			
			//根据企业查找对应的key
			String SB_API_URL = keydata.get("SB_API_URL");
			String SB_LOGIN_URL = keydata.get("SB_LOGIN_URL");
			String SB_operator_id = keydata.get("SB_operator_id");
			
			String SX_API_URL = keydata.get("SX_API_URL");
			String SX_LOGIN_URL = keydata.get("SX_LOGIN_URL");
			String SX_operator_id = keydata.get("SX_operator_id");
			String SX_site_code = keydata.get("SX_site_code");
			String SX_secret_key = keydata.get("SX_secret_key");
			String SX_product_code = keydata.get("SX_product_code");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			M88GameAPI gameAPI = new M88GameAPI(SB_API_URL, SB_LOGIN_URL, SB_operator_id, SX_API_URL, SX_operator_id, SX_LOGIN_URL,  SX_site_code, SX_secret_key, SX_product_code, GAME_API_URL);
			entity.put("username", username);//用户长度必须为17位
			entity.put("trans_id", getAPIOrderid(orderno));
			return gameAPI.getOrder(entity).toString();//username,odds_type
			
		} else if(gametype.equals(GameEnum.Enum_GameType.HAB.gametype)) {//哈巴电子
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String LOGIN_URL = keydata.get("LOGIN_URL");
			String BrandId = keydata.get("BrandId");
			String APIKey = keydata.get("APIKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			HBGameAPI gameAPI = new HBGameAPI( BrandId,  APIKey,  API_URL,  LOGIN_URL, GAME_API_URL);
			entity.put("username", username);//
			entity.put("password", password);//
			entity.put("RequestId", getAPIOrderid(orderno));//
			return gameAPI.getOrder(entity).toString();//username,password,RequestId
			
		} else if(gametype.equals(GameEnum.Enum_GameType.QTtech.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String agentname = keydata.get("agentname");
			String agentpwd = keydata.get("agentpwd");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			QTGameAPI gameAPI = new QTGameAPI(API_URL, agentname, agentpwd, GAME_API_URL);
			entity.put("transferId", getAPIOrderid(orderno));//
			return gameAPI.getOrder(entity).toString();//transferId
			
		} else if(gametype.equals(GameEnum.Enum_GameType.GB彩票.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String TPCode = keydata.get("TPCode");
			String SecretKey = keydata.get("SecretKey");
			String GeneralKey = keydata.get("GeneralKey");
			String YourDomain = keydata.get("YourDomain");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			GBGameAPI gameAPI = new GBGameAPI( API_URL, TPCode, SecretKey, GeneralKey, YourDomain, GAME_API_URL);
			entity.put("ExTransID", getAPIOrderid(orderno));//
			return gameAPI.getOrder(entity).toString();//ExTransID
			
		} else if(gametype.equals(GameEnum.Enum_GameType.EBet.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KeyB = keydata.get("KeyB");
			String subchannelid = keydata.get("subchannelid");
			String H5LOGIN_URL = keydata.get("H5LOGIN_URL");
			String channelid = keydata.get("channelid");
			String publicKey = keydata.get("publicKey");
			String privateKey = keydata.get("privateKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			EBetGameAPI gameAPI = new EBetGameAPI( API_URL, KeyB, subchannelid, H5LOGIN_URL,  channelid,  publicKey,  privateKey, GAME_API_URL);
			entity.put("username", username);//
			entity.put("remitno", getAPIOrderid(orderno));//
			return gameAPI.getOrder(entity).toString();//username,remitno
			
		} else if(gametype.equals(GameEnum.Enum_GameType.TGPlayer.gametype)) {//不提供订单查询接口
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String client_id = keydata.get("client_id");
			String client_secret = keydata.get("client_secret");
			String istestplayer = keydata.get("istestplayer");
			String LobbyDomain = keydata.get("LobbyDomain");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			TGPGameAPI gameAPI = new TGPGameAPI( API_URL, client_id, client_secret, istestplayer,  LobbyDomain, GAME_API_URL);
			entity.put("username", username);//
			entity.put("password", password);//
			return gameAPI.getOrder(entity).toString();//username
			
		} else if(gametype.equals(GameEnum.Enum_GameType.GGPoker.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String siteId = keydata.get("siteId");
			String SecretKey = keydata.get("SecretKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			GGPGameAPI gameAPI = new GGPGameAPI( API_URL, siteId, SecretKey, GAME_API_URL);
			entity.put("username", username);//
			entity.put("requestId", getAPIOrderid(orderno));//
			return gameAPI.getOrder(entity).toString();//username
			
		} else if(gametype.equals(GameEnum.Enum_GameType.eIBCGame.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String OpCode = keydata.get("OpCode");
			String PrivateKey = keydata.get("PrivateKey");
			String Domain = keydata.get("Domain");
			String CnameDomain = keydata.get("CnameDomain");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			IBCEGameAPI gameAPI = new IBCEGameAPI( API_URL, OpCode, PrivateKey, Domain, CnameDomain, GAME_API_URL);
			entity.put("PlayerName", username);//
			entity.put("OpTransId", getAPIOrderid(orderno));//
			entity.put("password", password);//
			return gameAPI.getOrder(entity).toString();//PlayerName,OpTransId,password
			
		} else if(gametype.equals(GameEnum.Enum_GameType.YoPLAY.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String agentid = keydata.get("agentid");
			String agentname = keydata.get("agentname");
			String agentpwd = keydata.get("agentpwd");
			String API_LOGIN_URL = keydata.get("API_LOGIN_URL");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			YoPLAYGameAPI gameAPI = new YoPLAYGameAPI(API_URL, MD5_KEY, DES_KEY,  agentid,  agentname,  agentpwd, API_LOGIN_URL, GAME_API_URL);
			entity.put("billno", getAPIOrderid(orderno));//
			entity.put("acctype", "1");//
			return gameAPI.getOrder(entity).toString();//acctype,billno
			
		} else if(gametype.equals(GameEnum.Enum_GameType.去玩棋牌.gametype)) {
			String API_URL = keydata.get("API_URL");
			String AGENT_NAME = keydata.get("AGENT_NAME");
			String AGENT_PASSWORD = keydata.get("AGENT_PASSWORD");
			String GAME_API_URL = keydata.get("GAME_API_URL");
			
			QWPGameAPI gameAPI = new QWPGameAPI(API_URL, AGENT_NAME, AGENT_PASSWORD, GAME_API_URL);
			entity.put("orderId", getAPIOrderid(orderno));
			return gameAPI.getOrder(entity).toString();
		} else if(gametype.equals(GameEnum.Enum_GameType.IM体育.gametype)) {
			String API_URL = keydata.get("API_URL");
			String MerchantCode = keydata.get("MerchantCode");
			String GAME_API_URL = keydata.get("GAME_API_URL");
			
			IMGameAPI gameAPI = new IMGameAPI(API_URL, MerchantCode, GAME_API_URL);
			entity.put("username", username);
			entity.put("TransactionId", getAPIOrderid(orderno));
			entity.put("ProductWallet", "301");//IM体育
			return gameAPI.getOrder(entity).toString();//username,TransactionId,ProductWallet
		} else if (gametype.equals(GameEnum.Enum_GameType.JDB168.gametype)) {
			String GAME_API_URL = keydata.get("GAME_API_URL");
			String API_URL = keydata.get("API_URL");
			String PARENT = keydata.get("PARENT");
			String SITE = keydata.get("SITE");
			String KEY = keydata.get("KEY");
			String IV = keydata.get("IV");
			String DC = keydata.get("DC");
			
			JDB168GameAPI gameAPI = new JDB168GameAPI(API_URL, GAME_API_URL, PARENT, DC, SITE, IV, KEY);
			entity.put("serialNo", getAPIOrderid(orderno));
			return gameAPI.getOrder(entity).toString();
		}
		
		
		return Enum_MSG.参数错误.message("游戏业务类型不正确");
		
	}
	

	/**
	 * 上分接口
	 * 
	 * @param username
	 * @param password
	 * @param gametype
	 * @param enterprisecode
	 * @param orderno 统一19位数
	 * @param money 金额应大于1，并且是整数
	 * @return
	 * code=0成功，其他失败
	 *	info=错误消息/成功消息/具体数据
	 */
	public static Object deposit(String username, String password,String gametype,String enterprisecode,String orderno,int money) {
		Map<String, Object> entity = new HashMap<String, Object>();
		
		
		Map<String, String> keydata = apikeys.get(enterprisecode+gametype);//查找本企业本游戏类型的keys
		
		if(keydata == null || keydata.size() == 0) {
			keydata = initKeys(gametype, enterprisecode);
		}
		if(keydata == null || keydata.size() == 0) {
			return Enum_MSG.参数错误.message("相关API参数未找到。");
		} else {
			Iterator<String> iterator = keydata.keySet().iterator();
			while(iterator.hasNext()){
			     String key = iterator.next().toString();
			     String val = keydata.get(key);
			     if(val == null || val.equals("")) {
			    	 return Enum_MSG.参数错误.message("相关API参数未找到：参数"+key);
			     }
			}
		}
		
		
		if(money < 1 ) {
			return Enum_MSG.参数错误.message("金额必须至少为1.00，并且建议为整数");
		}
		if(money > 300000) {
			return Enum_MSG.参数错误.message("超过风险控制值，请做好风险控制");
		}
		
		if(gametype.equals(GameEnum.Enum_GameType.AG.gametype) || gametype.equals(GameEnum.Enum_GameType.PNG.gametype)) {//TAG
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String cagent = keydata.get("cagent");
			String API_URL_GCL = keydata.get("API_URL_GCL");
			String oddtype = keydata.get("oddtype");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			String apiOrderid = getAPIorderno16();//生成API端的单号，订单号必须是16位数的数字
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			TAGGameAPI api = new TAGGameAPI(API_URL, MD5_KEY, DES_KEY, cagent , API_URL_GCL,oddtype, GAME_API_URL);
			entity.put("loginname", username);
			entity.put("password", password);
			entity.put("actype", "1");//账号类型（0、试玩，1、真钱）
			entity.put("billno", apiOrderid);//
			entity.put("credit", money);
			entity.put("oddtype", oddtype);
			
			
			String result = api.deposit(entity).toString();//loginname,actype,password,billno,credit
			return result;
			
		} else if(gametype.equals(GameEnum.Enum_GameType.AV老虎机.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String operatorID = keydata.get("operatorID");
			String operatorPassword = keydata.get("operatorPassword");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid = RandomStringNum.createRandomString(20);//生成API端的单号,订单号必须是20位数的数字
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			AVGameAPI gameAPI = new AVGameAPI(API_URL, operatorID, operatorPassword, GAME_API_URL);
			entity.put("userID", username);
			entity.put("transactionID", apiOrderid);//
			entity.put("amount", money);
			
			String result = gameAPI.deposit(entity).toString();//userID,amount,transactionID
			
			return result;
			
		} else if(gametype.equals(GameEnum.Enum_GameType.PT.gametype)) {
			
			//根据企业查找对应的key
			String ENTITY_KEY = keydata.get("ENTITY_KEY");
			String adminname = keydata.get("adminname");
			String kioskname = keydata.get("kioskname");
			String IS_RELEASE = keydata.get("IS_RELEASE");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			boolean RELEASE = false;
			if(IS_RELEASE.toLowerCase().equals("true")) {
				RELEASE = true;
			}
			
			String apiOrderid = RandomStringNum.createRandomString(40);//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			PTGameAPI ptGameAPI = new PTGameAPI(ENTITY_KEY, adminname, kioskname, RELEASE, GAME_API_URL);
			entity.put("playername", username);
			entity.put("amount", money);
			entity.put("externaltranid", apiOrderid);//string, AlphaNum(0-200) 。交易ID
			
			String result = ptGameAPI.deposit(entity).toString();//playername,amount,externaltranid
			
			return result;
			
		} else if(gametype.equals(GameEnum.Enum_GameType.WIN88.gametype)) {
			
			//根据企业查找对应的key
			String ENTITY_KEY = keydata.get("ENTITY_KEY");
			String adminname = keydata.get("adminname");
			String kioskname = keydata.get("kioskname");
			String IS_RELEASE = keydata.get("IS_RELEASE");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			boolean RELEASE = false;
			if(IS_RELEASE.toLowerCase().equals("true")) {
				RELEASE = true;
			}
			
			String apiOrderid = RandomStringNum.createRandomString(40);//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			WIN88GameAPI ptGameAPI = new WIN88GameAPI(ENTITY_KEY, adminname, kioskname, RELEASE, GAME_API_URL);
			entity.put("playername", username);
			entity.put("amount", money);
			entity.put("externaltranid", apiOrderid);//string, AlphaNum(0-200) 。交易ID
			
			String result = ptGameAPI.deposit(entity).toString();//playername,amount,externaltranid
			
			return result;
			
		} else if(gametype.equals(GameEnum.Enum_GameType.东方.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String AGENT = keydata.get("AGENT");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid = getAPIorderno16();//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			OGGameAPI gameAPI = new OGGameAPI(API_URL, KEY, AGENT, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			entity.put("billno", apiOrderid);//订单号长度不能大于16位，并且是纯数字
			entity.put("credit", money);
			
			String result = gameAPI.deposit(entity).toString();//username,password,billno,credit
			
			return result;
			
		} else if(gametype.equals(GameEnum.Enum_GameType.东方AG.gametype)) {//已取消
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String AGENT = keydata.get("AGENT");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid = getAPIorderno16();//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			AGGameAPI gameAPI = new AGGameAPI(API_URL, KEY, AGENT, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			entity.put("billno", apiOrderid);//订单号长度不能大于16位，并且是纯数字
			entity.put("credit", money);

			String result = gameAPI.deposit(entity).toString();//username,password,billno,credit
			
			return result;
			
		} else if(gametype.equals(GameEnum.Enum_GameType.棋牌.gametype)) {
			
			
			//根据企业查找对应的key
			String serviceDomain = keydata.get("serviceDomain");
			String agentId = keydata.get("agentId");
			String apiKey = keydata.get("apiKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid = RandomString.createRandomString(32);//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			QPGameAPI gameAPI = new QPGameAPI(serviceDomain, agentId, apiKey, GAME_API_URL);
			entity.put("username", username);//用户长度必须为15位
			entity.put("orderID", apiOrderid);//32位数
			entity.put("money", money);
			return gameAPI.deposit(entity);//username,money,orderID
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.沙巴.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String AGENT = keydata.get("AGENT");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid = getAPIorderno16();//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			IBCGameAPI gameAPI = new IBCGameAPI(API_URL, KEY, AGENT, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			entity.put("billno", apiOrderid);//订单号长度不能大于16位，并且是纯数字
			entity.put("credit", money);
			
			String result = gameAPI.deposit(entity).toString();//username,password,billno,credit
			
			return result;
			
		} else if(gametype.equals(GameEnum.Enum_GameType.沙龙.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String CHECK_KEY = keydata.get("CHECK_KEY");
			String LOGIN_URL = keydata.get("LOGIN_URL");
			String LOBBY_CODE = keydata.get("LOBBY_CODE");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid = "IN" + StringUtils.getCurrenDate() + username.toString();//生成API端的单号，用户名是20位数
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			SAGameAPI gameAPI = new SAGameAPI(API_URL, KEY, MD5_KEY, DES_KEY, CHECK_KEY, LOGIN_URL, LOBBY_CODE, GAME_API_URL);
			entity.put("Username", username);
			entity.put("OrderId", apiOrderid);
			entity.put("CreditAmount", money);
			String result = gameAPI.deposit(entity).toString();//Username,OrderId,CreditAmount 订单ID最长40位: OUT+YYYYMMDDHHMMSS+Username  例如: “OUT20131129130345peter1235” 
			
			return result;
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.波音.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String API_URL_LOGIN = keydata.get("API_URL_LOGIN");
			String website = keydata.get("website");
			String uppername = keydata.get("uppername");
			
			String KeyB_createAccount = keydata.get("KeyB_createAccount");
			String KeyB_getBalance = keydata.get("KeyB_getBalance");
			String KeyB_withdraw = keydata.get("KeyB_withdraw");
			String KeyB_deposit = keydata.get("KeyB_deposit");
			String KeyB_getOrder = keydata.get("KeyB_getOrder");
			String KeyB_login = keydata.get("KeyB_login");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			String apiOrderid = getAPIorderno19();//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			BBINGameAPI gameAPI = new BBINGameAPI(API_URL, website, uppername, API_URL_LOGIN, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			entity.put("remitno", apiOrderid);//19位数
			entity.put("remit", Double.valueOf(money).intValue());//波音必须是正整数，不能含有小数点，以及不能小于1
			entity.put("KeyB", KeyB_deposit);
			
			String result = gameAPI.deposit(entity).toString();//username,password,KeyB,remitno,remit
			
			return result;
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.洲际.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid = RandomString.createRandomString(32);//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			ZJGameAPI gameAPI = new ZJGameAPI(API_URL, KEY, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			entity.put("ref", apiOrderid);//32位数
			entity.put("amount", money);
			
			String result = gameAPI.deposit(entity).toString();//username,password,ref,amount
			
			return result;
			
		} else if(gametype.equals(GameEnum.Enum_GameType.环球.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String hyAesKey = keydata.get("hyAesKey");
			String agentname = keydata.get("agentname");
			String agentpwd = keydata.get("agentpwd");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			NHQGameAPI gameAPI = new NHQGameAPI(API_URL, MD5_KEY, DES_KEY, agentname, agentpwd, hyAesKey, GAME_API_URL);
			entity.put("username", username);
			entity.put("money", money);
			
			
			String result = gameAPI.deposit(entity).toString();//username,money
			JSONObject object = JSONObject.fromObject(result);
			//成功时返回的是该笔交易的主键，订单查询时需要传递该主键，视为订单号
			if(object.getString("code").equals("0")) {
				SystemCache.getInstance().setAPIClientOrderid(orderno, object.getString("info"));
			}
			
			return result;
			
		} else if(gametype.equals(GameEnum.Enum_GameType.祥瑞.gametype)) {//彩票
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String FIRST_KEY = keydata.get("FIRST_KEY");
			String LAST_KEY = keydata.get("LAST_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String agent = keydata.get("agent");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid = RandomStringNum.createRandomString(32);//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			String userpoint = keydata.get("userpoint");
			XCPGameAPI gameAPI = new XCPGameAPI(API_URL, FIRST_KEY, LAST_KEY, DES_KEY, agent, userpoint, GAME_API_URL);
			entity.put("username", username);
			entity.put("fMoney", money);
			entity.put("ordernum", apiOrderid);//订单号长度不能超过32位
			return gameAPI.deposit(entity);//username,fMoney,ordernum
			
		} else if(gametype.equals(GameEnum.Enum_GameType.MG.gametype)) {//MG
			
			
			//根据企业查找对应的key
			String HOST = keydata.get("HOST");
			String P_USM = keydata.get("P_USM");
			String P_PWD = keydata.get("P_PWD");
			String HOR_ID = keydata.get("HOR_ID");
			String apiusername = keydata.get("apiusername");
			String apipassword = keydata.get("apipassword");
			String partnerId = keydata.get("partnerId");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid = RandomStringNum.createRandomString(20);//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			MGGameAPI gameAPI = new MGGameAPI(HOST, P_USM, P_PWD, apiusername, apipassword, partnerId, HOR_ID, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			entity.put("orderid", apiOrderid);//订单号长度应为20个字符
			entity.put("amount", money);
			return gameAPI.deposit(entity);//username,password,orderid,amount
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.TTG.gametype)) {//TTG
			
			//根据企业查找对应的key
			String privateServer = keydata.get("privateServer");
			String partnerId = keydata.get("partnerId");
			String privateLoginUrl = keydata.get("privateLoginUrl");
			String prefix = keydata.get("prefix");
			String partnerId0 = keydata.get("partnerId0");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			int prefixLen = prefix.length();
			String apiOrderid = prefix + RandomString.createRandomString(50 - prefixLen);//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			TTGGameAPI gameAPI = new TTGGameAPI(privateServer, partnerId, privateLoginUrl, prefix, partnerId0, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			entity.put("exttransactionID", apiOrderid);
			entity.put("amount", money);
			return gameAPI.deposit(entity);//username,exttransactionID,amount
			
		}  else if(gametype.equals(GameEnum.Enum_GameType.DZDY.gametype)) {//DZDY
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String API_URL_LOGIN = keydata.get("API_URL_LOGIN");
			String channelid = keydata.get("channelid");
			String channelpwd = keydata.get("channelpwd");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			DZDYGameAPI gameAPI = new DZDYGameAPI(API_URL, channelid, channelpwd,API_URL_LOGIN, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			entity.put("money", money);//
			
			String result = gameAPI.deposit(entity).toString();//username,money
			
			JSONObject object = JSONObject.fromObject(result);
			//成功时返回的是该笔交易的主键，订单查询时需要传递该主键，视为订单号
			if(object.getString("code").equals("0")) {
				SystemCache.getInstance().setAPIClientOrderid(orderno, object.getString("info"));
			}
			
			return result;
		} else if(gametype.equals(GameEnum.Enum_GameType.SGS.gametype)) {//申博
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String client_id = keydata.get("client_id");
			String client_secret = keydata.get("client_secret");
			String LobbyDomain = keydata.get("LobbyDomain");
			String istestplayer = keydata.get("istestplayer");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			String apiOrderid = RandomString.createRandomString(20);//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			SGSGameAPI gameAPI = new SGSGameAPI(API_URL, client_id, client_secret, LobbyDomain, istestplayer, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			entity.put("orderid", apiOrderid);
			entity.put("amount", money);
			return gameAPI.deposit(entity).toString();//username,orderid,amount
			
		} else if(gametype.equals(GameEnum.Enum_GameType.GG.gametype)) {//GG游行天下
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String cagent = keydata.get("cagent");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid =  getAPIorderno16();//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			GGGameAPI gameAPI = new GGGameAPI(API_URL, MD5_KEY, DES_KEY , cagent, GAME_API_URL);
			entity.put("loginname", username);//用户长度必须为20位
			entity.put("password", password);
			entity.put("billno", apiOrderid);
			entity.put("credit", money);
			return gameAPI.deposit(entity).toString();//loginname,password,billno,credit
			
		} else if(gametype.equals(GameEnum.Enum_GameType.IDN.gametype)) {//IDN
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String secret_key = keydata.get("secret_key");
			String LOBBY_URL = keydata.get("LOBBY_URL");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid =  "IN"+UUID.randomUUID().toString().replaceAll("-", "");//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			IDNGameAPI gameAPI = new IDNGameAPI(API_URL, secret_key, LOBBY_URL, GAME_API_URL);
			entity.put("username", username);//用户长度必须为15位
			entity.put("orderid", apiOrderid);
			entity.put("amount", money);
			return gameAPI.deposit(entity).toString();//username,orderid,amount
			
		} else if(gametype.equals(GameEnum.Enum_GameType.M88.gametype)) {//M88
			
			//根据企业查找对应的key
			String SB_API_URL = keydata.get("SB_API_URL");
			String SB_LOGIN_URL = keydata.get("SB_LOGIN_URL");
			String SB_operator_id = keydata.get("SB_operator_id");
			
			String SX_API_URL = keydata.get("SX_API_URL");
			String SX_LOGIN_URL = keydata.get("SX_LOGIN_URL");
			String SX_operator_id = keydata.get("SX_operator_id");
			String SX_site_code = keydata.get("SX_site_code");
			String SX_secret_key = keydata.get("SX_secret_key");
			String SX_product_code = keydata.get("SX_product_code");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid =  "IN"+getAPIorderno18();//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			M88GameAPI gameAPI = new M88GameAPI(SB_API_URL, SB_LOGIN_URL, SB_operator_id, SX_API_URL, SX_operator_id, SX_LOGIN_URL,  SX_site_code, SX_secret_key, SX_product_code, GAME_API_URL);
			entity.put("username", username);//用户长度必须为17位
			entity.put("amount", money);
			entity.put("trans_id", apiOrderid);
			return gameAPI.deposit(entity).toString();//username,amount,trans_id
			
		} else if(gametype.equals(GameEnum.Enum_GameType.HAB.gametype)) {//哈巴电子
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String LOGIN_URL = keydata.get("LOGIN_URL");
			String BrandId = keydata.get("BrandId");
			String APIKey = keydata.get("APIKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid =  "IN"+UUID.randomUUID().toString();//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			HBGameAPI gameAPI = new HBGameAPI( BrandId,  APIKey,  API_URL,  LOGIN_URL, GAME_API_URL);
			entity.put("username", username);//
			entity.put("password", password);//
			entity.put("Amount", money);//
			entity.put("RequestId", apiOrderid);//
			return gameAPI.deposit(entity).toString();//username,password,Amount,RequestId
			
		} else if(gametype.equals(GameEnum.Enum_GameType.QTtech.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String agentname = keydata.get("agentname");
			String agentpwd = keydata.get("agentpwd");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid =  UUID.randomUUID().toString();//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			QTGameAPI gameAPI = new QTGameAPI(API_URL, agentname, agentpwd, GAME_API_URL);
			entity.put("playerId", username);
			entity.put("amount", money);
			entity.put("referenceId", apiOrderid);
			return gameAPI.deposit(entity).toString();//playerId,amount,referenceId
			
		} else if(gametype.equals(GameEnum.Enum_GameType.GB彩票.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String TPCode = keydata.get("TPCode");
			String SecretKey = keydata.get("SecretKey");
			String GeneralKey = keydata.get("GeneralKey");
			String YourDomain = keydata.get("YourDomain");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid =  "IN"+UUID.randomUUID().toString();//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			GBGameAPI gameAPI = new GBGameAPI( API_URL, TPCode, SecretKey, GeneralKey, YourDomain, GAME_API_URL);
			entity.put("MemberID", username);//
			entity.put("Amount", money);//
			entity.put("ExTransID", apiOrderid);//
			return gameAPI.deposit(entity).toString();//MemberID,Amount,ExTransID
			
		} else if(gametype.equals(GameEnum.Enum_GameType.EBet.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KeyB = keydata.get("KeyB");
			String subchannelid = keydata.get("subchannelid");
			String H5LOGIN_URL = keydata.get("H5LOGIN_URL");
			String channelid = keydata.get("channelid");
			String publicKey = keydata.get("publicKey");
			String privateKey = keydata.get("privateKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid =  "IN"+orderno;//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			
			EBetGameAPI gameAPI = new EBetGameAPI( API_URL, KeyB, subchannelid, H5LOGIN_URL,  channelid,  publicKey,  privateKey, GAME_API_URL);
			entity.put("username", username);//
			entity.put("remit", money);//
			entity.put("remitno", apiOrderid);//
			return gameAPI.deposit(entity).toString();//username,remit,remitno
			
		} else if(gametype.equals(GameEnum.Enum_GameType.TGPlayer.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String client_id = keydata.get("client_id");
			String client_secret = keydata.get("client_secret");
			String istestplayer = keydata.get("istestplayer");
			String LobbyDomain = keydata.get("LobbyDomain");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid =  "IN"+orderno;//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			TGPGameAPI gameAPI = new TGPGameAPI( API_URL, client_id, client_secret, istestplayer,  LobbyDomain, GAME_API_URL);
			entity.put("username", username);//
			entity.put("orderid", apiOrderid);//
			entity.put("amount", money);//
			return gameAPI.deposit(entity).toString();//username,orderid,amount
			
		} else if(gametype.equals(GameEnum.Enum_GameType.GGPoker.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String siteId = keydata.get("siteId");
			String SecretKey = keydata.get("SecretKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid =  "IN"+orderno;//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			double real_money = MoneyHelper.moneyFormatDouble(money * SystemCache.getInstance().getExchangeRateAll("USD"));//转美元
			
			GGPGameAPI gameAPI = new GGPGameAPI( API_URL, siteId, SecretKey, GAME_API_URL);
			entity.put("username", username);//
			entity.put("amount", real_money);//
			entity.put("requestId", apiOrderid);//
			return gameAPI.deposit(entity).toString();//username,amount,requestId
			
		} else if(gametype.equals(GameEnum.Enum_GameType.eIBCGame.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String OpCode = keydata.get("OpCode");
			String PrivateKey = keydata.get("PrivateKey");
			String Domain = keydata.get("Domain");
			String CnameDomain = keydata.get("CnameDomain");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid =  "IN"+orderno;//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			IBCEGameAPI gameAPI = new IBCEGameAPI( API_URL, OpCode, PrivateKey, Domain, CnameDomain, GAME_API_URL);
			entity.put("PlayerName", username);//
			entity.put("OpTransId", apiOrderid);//
			entity.put("amount", money);//
			entity.put("password", password);//
			return gameAPI.deposit(entity).toString();//PlayerName,OpTransId,amount,password
			
		} else if(gametype.equals(GameEnum.Enum_GameType.YoPLAY.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String agentid = keydata.get("agentid");
			String agentname = keydata.get("agentname");
			String agentpwd = keydata.get("agentpwd");
			String API_LOGIN_URL = keydata.get("API_LOGIN_URL");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid =  getAPIorderno16();
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			YoPLAYGameAPI gameAPI = new YoPLAYGameAPI(API_URL, MD5_KEY, DES_KEY,  agentid,  agentname,  agentpwd, API_LOGIN_URL, GAME_API_URL);
			entity.put("loginname", username);//
			entity.put("password", password);//
			entity.put("acctype", "1");//
			entity.put("money", money);//
			entity.put("billno", apiOrderid);//
			return gameAPI.deposit(entity).toString();//loginname,password,acctype,money,billno
			
		} else if (gametype.equals(GameEnum.Enum_GameType.去玩棋牌.gametype)) {
			String API_URL = keydata.get("API_URL");
			String AGENT_NAME = keydata.get("AGENT_NAME");
			String AGENT_PASSWORD = keydata.get("AGENT_PASSWORD");

			String apiOrderid =  "IN"+orderno;//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			String GAME_API_URL = keydata.get("GAME_API_URL");
			
			QWPGameAPI gameAPI = new QWPGameAPI(API_URL, AGENT_NAME, AGENT_PASSWORD, GAME_API_URL);
			entity.put("Account", username);
			entity.put("Amount", money);
			entity.put("IP", "192.168.1.125");
			entity.put("OrderId", apiOrderid);
			return gameAPI.deposit(entity).toString();
		} else if(gametype.equals(GameEnum.Enum_GameType.IM体育.gametype)) {
			String API_URL = keydata.get("API_URL");
			String MerchantCode = keydata.get("MerchantCode");
			String GAME_API_URL = keydata.get("GAME_API_URL");

			String apiOrderid =  "IN"+orderno;//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			IMGameAPI gameAPI = new IMGameAPI(API_URL, MerchantCode, GAME_API_URL);
			entity.put("username", username);
			entity.put("Amount", money);
			entity.put("ProductWallet", "301");//IM体育
			entity.put("TransactionId", apiOrderid);
			return gameAPI.deposit(entity).toString();//username,ProductWallet,TransactionId,Amount
		} else if (gametype.equals(GameEnum.Enum_GameType.JDB168.gametype)) {
			String GAME_API_URL = keydata.get("GAME_API_URL");
			String API_URL = keydata.get("API_URL");
			String PARENT = keydata.get("PARENT");
			String SITE = keydata.get("SITE");
			String KEY = keydata.get("KEY");
			String IV = keydata.get("IV");
			String DC = keydata.get("DC");
			
			String apiOrderid =  "IN"+orderno;//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			JDB168GameAPI gameAPI = new JDB168GameAPI(API_URL, GAME_API_URL, PARENT, DC, SITE, IV, KEY);
			entity.put("uid", username);
			entity.put("serialNo", apiOrderid);
			entity.put("amount", money);
			return gameAPI.deposit(entity).toString();
		}
		
		
		return Enum_MSG.参数错误.message("游戏业务类型不正确");
		
		
	}

	
	
	/**
	 * 下分接口
	 * 
	 * @param username
	 * @param password
	 * @param gametype
	 * @param enterprisecode
	 * @param orderno 统一19位数
	 * @param money 金额应大于1，并且是整数
	 * @return
	 * code=0成功，其他失败
	 *	info=错误消息/成功消息/具体数据
	 */
	public static Object withdraw(String username, String password,String gametype,String enterprisecode,String orderno, int money) {
		Map<String, Object> entity = new HashMap<String, Object>();
		
		
		Map<String, String> keydata = apikeys.get(enterprisecode+gametype);//查找本企业本游戏类型的keys
		
		if(keydata == null || keydata.size() == 0) {
			keydata = initKeys(gametype, enterprisecode);
		}
		if(keydata == null || keydata.size() == 0) {
			return Enum_MSG.参数错误.message("相关API参数未找到。");
		} else {
			Iterator<String> iterator = keydata.keySet().iterator();
			while(iterator.hasNext()){
			     String key = iterator.next().toString();
			     String val = keydata.get(key);
			     if(val == null || val.equals("")) {
			    	 return Enum_MSG.参数错误.message("相关API参数未找到：参数"+key);
			     }
			}
		}
		
		
		if(money < 1 ) {
			return Enum_MSG.参数错误.message("金额必须至少为1.00，并且建议为整数");
		}
		if(money > 300000) {
//			return Enum_MSG.参数错误.message("超过风险控制值，请做好风险控制");
		}
		
		if(gametype.equals(GameEnum.Enum_GameType.AG.gametype) || gametype.equals(GameEnum.Enum_GameType.PNG.gametype)) {//TAG
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String cagent = keydata.get("cagent");
			String API_URL_GCL = keydata.get("API_URL_GCL");
			String oddtype = keydata.get("oddtype");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid = getAPIorderno16();//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			TAGGameAPI api = new TAGGameAPI(API_URL, MD5_KEY, DES_KEY, cagent, API_URL_GCL,oddtype, GAME_API_URL);
			entity.put("loginname", username);
			entity.put("password", password);
			entity.put("actype", "1");//账号类型（0、试玩，1、真钱）
			entity.put("billno", apiOrderid);//订单号必须是16位数的数字
			entity.put("credit", money);
			entity.put("oddtype", oddtype);
			return api.withdraw(entity);//loginname,actype,password,billno,credit
			
		} else if(gametype.equals(GameEnum.Enum_GameType.AV老虎机.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String operatorID = keydata.get("operatorID");
			String operatorPassword = keydata.get("operatorPassword");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid = RandomStringNum.createRandomString(20);//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			AVGameAPI gameAPI = new AVGameAPI(API_URL, operatorID, operatorPassword, GAME_API_URL);
			entity.put("userID", username);
			entity.put("transactionID", apiOrderid);//订单号必须是20位数的数字
			entity.put("amount", money);
			return gameAPI.withdraw(entity);//userID,amount,transactionID
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.PT.gametype)) {
			
			//根据企业查找对应的key
			String ENTITY_KEY = keydata.get("ENTITY_KEY");
			String adminname = keydata.get("adminname");
			String kioskname = keydata.get("kioskname");
			String IS_RELEASE = keydata.get("IS_RELEASE");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			boolean RELEASE = false;
			if(IS_RELEASE.toLowerCase().equals("true")) {
				RELEASE = true;
			}
			
			String apiOrderid = RandomStringNum.createRandomString(40);//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			PTGameAPI ptGameAPI = new PTGameAPI(ENTITY_KEY, adminname, kioskname, RELEASE, GAME_API_URL);
			entity.put("playername", username);
			entity.put("amount", money);
			entity.put("externaltranid", apiOrderid);//string, AlphaNum(0-200) 。交易ID
			
			return ptGameAPI.withdraw(entity);//playername,amount,externaltranid
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.WIN88.gametype)) {
			
			//根据企业查找对应的key
			String ENTITY_KEY = keydata.get("ENTITY_KEY");
			String adminname = keydata.get("adminname");
			String kioskname = keydata.get("kioskname");
			String IS_RELEASE = keydata.get("IS_RELEASE");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			boolean RELEASE = false;
			if(IS_RELEASE.toLowerCase().equals("true")) {
				RELEASE = true;
			}
			
			String apiOrderid = RandomStringNum.createRandomString(40);//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			WIN88GameAPI ptGameAPI = new WIN88GameAPI(ENTITY_KEY, adminname, kioskname, RELEASE, GAME_API_URL);
			entity.put("playername", username);
			entity.put("amount", money);
			entity.put("externaltranid", apiOrderid);//string, AlphaNum(0-200) 。交易ID
			
			return ptGameAPI.withdraw(entity);//playername,amount,externaltranid
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.东方.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String AGENT = keydata.get("AGENT");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid = getAPIorderno16();//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			OGGameAPI gameAPI = new OGGameAPI(API_URL, KEY, AGENT, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			entity.put("billno", apiOrderid);//订单号长度不能大于16位，并且是纯数字
			entity.put("credit", money);
			return gameAPI.withdraw(entity);//username,password,billno,credit
			
		} else if(gametype.equals(GameEnum.Enum_GameType.东方AG.gametype)) {//已取消
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String AGENT = keydata.get("AGENT");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid = getAPIorderno16();//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			AGGameAPI gameAPI = new AGGameAPI(API_URL, KEY, AGENT, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			entity.put("billno", apiOrderid);//订单号长度不能大于16位，并且是纯数字
			entity.put("credit", money);
			return gameAPI.withdraw(entity);//username,password,billno,credit
			
		} else if(gametype.equals(GameEnum.Enum_GameType.棋牌.gametype)) {
			
			//根据企业查找对应的key
			String serviceDomain = keydata.get("serviceDomain");
			String agentId = keydata.get("agentId");
			String apiKey = keydata.get("apiKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid = RandomString.createRandomString(32);//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			QPGameAPI gameAPI = new QPGameAPI(serviceDomain, agentId, apiKey, GAME_API_URL);
			entity.put("username", username);//用户长度必须为15位
			entity.put("orderID", apiOrderid);//32位数
			entity.put("money", money);
			return gameAPI.withdraw(entity);//username,money,orderID
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.沙巴.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String AGENT = keydata.get("AGENT");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid = getAPIorderno16();//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			IBCGameAPI gameAPI = new IBCGameAPI(API_URL, KEY, AGENT, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			entity.put("billno", apiOrderid);//订单号长度不能大于16位，并且是纯数字
			entity.put("credit", money);
			return gameAPI.withdraw(entity);//username,password,billno,credit
			
		} else if(gametype.equals(GameEnum.Enum_GameType.沙龙.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String CHECK_KEY = keydata.get("CHECK_KEY");
			String LOGIN_URL = keydata.get("LOGIN_URL");
			String LOBBY_CODE = keydata.get("LOBBY_CODE");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			String apiOrderid =  "OUT" + StringUtils.getCurrenDate() + username.toString();//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			SAGameAPI gameAPI = new SAGameAPI(API_URL, KEY, MD5_KEY, DES_KEY, CHECK_KEY, LOGIN_URL, LOBBY_CODE, GAME_API_URL);
			entity.put("Username", username);//用户名是20位数
			entity.put("OrderId", apiOrderid);
			entity.put("DebitAmount", money);
			return gameAPI.withdraw(entity);//Username,OrderId,DebitAmount 订单ID最长40位: OUT+YYYYMMDDHHMMSS+Username  例如: “OUT20131129130345peter1235” 
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.波音.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String API_URL_LOGIN = keydata.get("API_URL_LOGIN");
			String website = keydata.get("website");
			String uppername = keydata.get("uppername");
			
			String KeyB_createAccount = keydata.get("KeyB_createAccount");
			String KeyB_getBalance = keydata.get("KeyB_getBalance");
			String KeyB_withdraw = keydata.get("KeyB_withdraw");
			String KeyB_deposit = keydata.get("KeyB_deposit");
			String KeyB_getOrder = keydata.get("KeyB_getOrder");
			String KeyB_login = keydata.get("KeyB_login");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid =  getAPIorderno19();//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			BBINGameAPI gameAPI = new BBINGameAPI(API_URL, website, uppername, API_URL_LOGIN, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			entity.put("remitno", apiOrderid );//19位数
			entity.put("remit", Double.valueOf(money).intValue());//波音必须是正整数，不能含有小数点，以及不能小于1
			entity.put("KeyB", KeyB_withdraw);
			return gameAPI.withdraw(entity);//username,password,KeyB,remitno,remit
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.洲际.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid =  RandomString.createRandomString(32);//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			ZJGameAPI gameAPI = new ZJGameAPI(API_URL, KEY, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			entity.put("ref", apiOrderid);//32位数
			entity.put("amount", money);
			return gameAPI.withdraw(entity);//username,password,ref,amount
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.环球.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String hyAesKey = keydata.get("hyAesKey");
			String agentname = keydata.get("agentname");
			String agentpwd = keydata.get("agentpwd");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			NHQGameAPI gameAPI = new NHQGameAPI(API_URL, MD5_KEY, DES_KEY, agentname, agentpwd, hyAesKey, GAME_API_URL);
			entity.put("username", username);
			entity.put("money", money);
			
			
			String result = gameAPI.withdraw(entity).toString();//username,money
			JSONObject object = JSONObject.fromObject(result);
			//成功时返回的是该笔交易的主键，订单查询时需要传递该主键，视为订单号
			if(object.getString("code").equals("0")) {
				SystemCache.getInstance().setAPIClientOrderid(orderno, object.getString("info"));
			}
			
			return result;
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.祥瑞.gametype)) {//彩票
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String FIRST_KEY = keydata.get("FIRST_KEY");
			String LAST_KEY = keydata.get("LAST_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String agent = keydata.get("agent");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid = RandomStringNum.createRandomString(32);//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			String userpoint = keydata.get("userpoint");
			XCPGameAPI gameAPI = new XCPGameAPI(API_URL, FIRST_KEY, LAST_KEY, DES_KEY, agent, userpoint, GAME_API_URL);
			entity.put("username", username);
			entity.put("fMoney", money);
			entity.put("ordernum", apiOrderid);//订单号长度不能超过32位
			return gameAPI.withdraw(entity);//username,fMoney,ordernum
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.MG.gametype)) {//MG
			
			//根据企业查找对应的key
			String HOST = keydata.get("HOST");
			String P_USM = keydata.get("P_USM");
			String P_PWD = keydata.get("P_PWD");
			String HOR_ID = keydata.get("HOR_ID");
			String apiusername = keydata.get("apiusername");
			String apipassword = keydata.get("apipassword");
			String partnerId = keydata.get("partnerId");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid = RandomStringNum.createRandomString(20);//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			MGGameAPI gameAPI = new MGGameAPI(HOST, P_USM, P_PWD, apiusername, apipassword, partnerId, HOR_ID, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			entity.put("orderid", apiOrderid);//订单号长度应为20个字符
			entity.put("amount", money);
			return gameAPI.withdraw(entity);//username,password,orderid,amount
			
		} else if(gametype.equals(GameEnum.Enum_GameType.TTG.gametype)) {//TTG
			
			//根据企业查找对应的key
			String privateServer = keydata.get("privateServer");
			String partnerId = keydata.get("partnerId");
			String privateLoginUrl = keydata.get("privateLoginUrl");
			String prefix = keydata.get("prefix");
			String partnerId0 = keydata.get("partnerId0");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			int prefixLen = prefix.length();
			String apiOrderid = prefix + RandomString.createRandomString(50 - prefixLen);//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			TTGGameAPI gameAPI = new TTGGameAPI(privateServer, partnerId, privateLoginUrl, prefix, partnerId0, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			entity.put("exttransactionID", apiOrderid);
			entity.put("amount", money);
			return gameAPI.withdraw(entity);//username,exttransactionID,amount
			
		} else if(gametype.equals(GameEnum.Enum_GameType.DZDY.gametype)) {//DZDY
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String API_URL_LOGIN = keydata.get("API_URL_LOGIN");
			String channelid = keydata.get("channelid");
			String channelpwd = keydata.get("channelpwd");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			DZDYGameAPI gameAPI = new DZDYGameAPI(API_URL, channelid, channelpwd,API_URL_LOGIN, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			entity.put("money", money);//
			
			String result = gameAPI.withdraw(entity).toString();//username,money
			
			JSONObject object = JSONObject.fromObject(result);
			//成功时返回的是该笔交易的主键，订单查询时需要传递该主键，视为订单号
			if(object.getString("code").equals("0")) {
				SystemCache.getInstance().setAPIClientOrderid(orderno, object.getString("info"));
			}
			
			return result;
		} else if(gametype.equals(GameEnum.Enum_GameType.SGS.gametype)) {//申博
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String client_id = keydata.get("client_id");
			String client_secret = keydata.get("client_secret");
			String LobbyDomain = keydata.get("LobbyDomain");
			String istestplayer = keydata.get("istestplayer");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid = RandomString.createRandomString(20);//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			SGSGameAPI gameAPI = new SGSGameAPI(API_URL, client_id, client_secret, LobbyDomain, istestplayer, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			entity.put("orderid", apiOrderid);
			entity.put("amount", money);
			return gameAPI.withdraw(entity).toString();//username,orderid,amount
			
		} else if(gametype.equals(GameEnum.Enum_GameType.GG.gametype)) {//GG游行天下
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String cagent = keydata.get("cagent");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid =  getAPIorderno16();//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			GGGameAPI gameAPI = new GGGameAPI(API_URL, MD5_KEY, DES_KEY , cagent, GAME_API_URL);
			entity.put("loginname", username);//用户长度必须为20位
			entity.put("password", password);
			entity.put("billno", apiOrderid);
			entity.put("credit", money);
			return gameAPI.withdraw(entity).toString();//loginname,password,billno,credit
			
		} else if(gametype.equals(GameEnum.Enum_GameType.IDN.gametype)) {//IDN
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String secret_key = keydata.get("secret_key");
			String LOBBY_URL = keydata.get("LOBBY_URL");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid =  "OUT"+UUID.randomUUID().toString().replaceAll("-", "");//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			IDNGameAPI gameAPI = new IDNGameAPI(API_URL, secret_key, LOBBY_URL, GAME_API_URL);
			entity.put("username", username);//用户长度必须为15位
			entity.put("orderid", apiOrderid);
			entity.put("amount", money);
			return gameAPI.withdraw(entity).toString();//username,orderid,amount
			
		} else if(gametype.equals(GameEnum.Enum_GameType.M88.gametype)) {//M88
			
			//根据企业查找对应的key
			String SB_API_URL = keydata.get("SB_API_URL");
			String SB_LOGIN_URL = keydata.get("SB_LOGIN_URL");
			String SB_operator_id = keydata.get("SB_operator_id");
			
			String SX_API_URL = keydata.get("SX_API_URL");
			String SX_LOGIN_URL = keydata.get("SX_LOGIN_URL");
			String SX_operator_id = keydata.get("SX_operator_id");
			String SX_site_code = keydata.get("SX_site_code");
			String SX_secret_key = keydata.get("SX_secret_key");
			String SX_product_code = keydata.get("SX_product_code");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid =  "OUT"+getAPIorderno17();//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			M88GameAPI gameAPI = new M88GameAPI(SB_API_URL, SB_LOGIN_URL, SB_operator_id, SX_API_URL, SX_operator_id, SX_LOGIN_URL,  SX_site_code, SX_secret_key, SX_product_code, GAME_API_URL);
			entity.put("username", username);//用户长度必须为17位
			entity.put("amount", money);
			entity.put("trans_id", apiOrderid);
			return gameAPI.withdraw(entity).toString();//username,amount,trans_id
			
		} else if(gametype.equals(GameEnum.Enum_GameType.HAB.gametype)) {//哈巴电子
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String LOGIN_URL = keydata.get("LOGIN_URL");
			String BrandId = keydata.get("BrandId");
			String APIKey = keydata.get("APIKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid =  "OUT"+UUID.randomUUID().toString();//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			HBGameAPI gameAPI = new HBGameAPI( BrandId,  APIKey,  API_URL,  LOGIN_URL, GAME_API_URL);
			entity.put("username", username);//
			entity.put("password", password);//
			entity.put("Amount", money);//
			entity.put("RequestId", apiOrderid);//
			return gameAPI.withdraw(entity).toString();//username,password,Amount,RequestId
			
		} else if(gametype.equals(GameEnum.Enum_GameType.QTtech.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String agentname = keydata.get("agentname");
			String agentpwd = keydata.get("agentpwd");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid =  UUID.randomUUID().toString();//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			QTGameAPI gameAPI = new QTGameAPI(API_URL, agentname, agentpwd, GAME_API_URL);
			entity.put("playerId", username);
			entity.put("amount", money);
			entity.put("referenceId", apiOrderid);
			return gameAPI.withdraw(entity).toString();//playerId,amount,referenceId
			
		} else if(gametype.equals(GameEnum.Enum_GameType.GB彩票.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String TPCode = keydata.get("TPCode");
			String SecretKey = keydata.get("SecretKey");
			String GeneralKey = keydata.get("GeneralKey");
			String YourDomain = keydata.get("YourDomain");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid =  "OUT"+UUID.randomUUID().toString();//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			GBGameAPI gameAPI = new GBGameAPI( API_URL, TPCode, SecretKey, GeneralKey, YourDomain, GAME_API_URL);
			entity.put("MemberID", username);//
			entity.put("Amount", money);//
			entity.put("ExTransID", apiOrderid);//
			return gameAPI.withdraw(entity).toString();//MemberID,Amount,ExTransID
			
		} else if(gametype.equals(GameEnum.Enum_GameType.EBet.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KeyB = keydata.get("KeyB");
			String subchannelid = keydata.get("subchannelid");
			String H5LOGIN_URL = keydata.get("H5LOGIN_URL");
			String channelid = keydata.get("channelid");
			String publicKey = keydata.get("publicKey");
			String privateKey = keydata.get("privateKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid =  "OUT"+orderno;//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			
			EBetGameAPI gameAPI = new EBetGameAPI( API_URL, KeyB, subchannelid, H5LOGIN_URL,  channelid,  publicKey,  privateKey, GAME_API_URL);
			entity.put("username", username);//
			entity.put("remit", money);//
			entity.put("remitno", apiOrderid);//
			return gameAPI.withdraw(entity).toString();//username,remit,remitno
			
		} else if(gametype.equals(GameEnum.Enum_GameType.TGPlayer.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String client_id = keydata.get("client_id");
			String client_secret = keydata.get("client_secret");
			String istestplayer = keydata.get("istestplayer");
			String LobbyDomain = keydata.get("LobbyDomain");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid =  "OUT"+orderno;//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			TGPGameAPI gameAPI = new TGPGameAPI( API_URL, client_id, client_secret, istestplayer,  LobbyDomain, GAME_API_URL);
			entity.put("username", username);//
			entity.put("orderid", apiOrderid);//
			entity.put("amount", money);//
			return gameAPI.withdraw(entity).toString();//username,orderid,amount
			
		} else if(gametype.equals(GameEnum.Enum_GameType.GGPoker.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String siteId = keydata.get("siteId");
			String SecretKey = keydata.get("SecretKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid =  "OUT"+orderno;//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			double real_money = MoneyHelper.moneyFormatDouble(money * SystemCache.getInstance().getExchangeRateAll("USD"));//转美元
			
			GGPGameAPI gameAPI = new GGPGameAPI( API_URL, siteId, SecretKey, GAME_API_URL);
			entity.put("username", username);//
			entity.put("amount", real_money);//
			entity.put("requestId", apiOrderid);//
			return gameAPI.withdraw(entity).toString();//username,amount,requestId
			
		} else if(gametype.equals(GameEnum.Enum_GameType.eIBCGame.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String OpCode = keydata.get("OpCode");
			String PrivateKey = keydata.get("PrivateKey");
			String Domain = keydata.get("Domain");
			String CnameDomain = keydata.get("CnameDomain");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid =  "OUT"+orderno;//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			IBCEGameAPI gameAPI = new IBCEGameAPI( API_URL, OpCode, PrivateKey, Domain, CnameDomain, GAME_API_URL);
			entity.put("PlayerName", username);//
			entity.put("OpTransId", apiOrderid);//
			entity.put("amount", money);//
			entity.put("password", password);//
			return gameAPI.withdraw(entity).toString();//PlayerName,OpTransId,amount,password
			
		} else if(gametype.equals(GameEnum.Enum_GameType.YoPLAY.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String agentid = keydata.get("agentid");
			String agentname = keydata.get("agentname");
			String agentpwd = keydata.get("agentpwd");
			String API_LOGIN_URL = keydata.get("API_LOGIN_URL");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String apiOrderid =  getAPIorderno16();
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			YoPLAYGameAPI gameAPI = new YoPLAYGameAPI(API_URL, MD5_KEY, DES_KEY,  agentid,  agentname,  agentpwd, API_LOGIN_URL, GAME_API_URL);
			entity.put("loginname", username);//
			entity.put("password", password);//
			entity.put("acctype", "1");//
			entity.put("money", money);//
			entity.put("billno", apiOrderid);//
			return gameAPI.withdraw(entity).toString();//loginname,password,acctype,money,billno
			
		} else if(gametype.equals(GameEnum.Enum_GameType.去玩棋牌.gametype)) {
			String API_URL = keydata.get("API_URL");
			String AGENT_NAME = keydata.get("AGENT_NAME");
			String AGENT_PASSWORD = keydata.get("AGENT_PASSWORD");
			
			String apiOrderid =  "OUT"+orderno;//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			String GAME_API_URL = keydata.get("GAME_API_URL");
			
			QWPGameAPI gameAPI = new QWPGameAPI(API_URL, AGENT_NAME, AGENT_PASSWORD, GAME_API_URL);
			entity.put("Account", username);
			entity.put("Amount", money);
			entity.put("IP", "192.168.1.125");
			entity.put("OrderId", apiOrderid);
			return gameAPI.withdraw(entity).toString();
		} else if(gametype.equals(GameEnum.Enum_GameType.IM体育.gametype)) {
			String API_URL = keydata.get("API_URL");
			String MerchantCode = keydata.get("MerchantCode");
			String GAME_API_URL = keydata.get("GAME_API_URL");

			String apiOrderid =  "OUT"+orderno;//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			IMGameAPI gameAPI = new IMGameAPI(API_URL, MerchantCode, GAME_API_URL);
			entity.put("username", username);
			entity.put("Amount", money);
			entity.put("ProductWallet", "301");//IM体育
			entity.put("TransactionId", apiOrderid);
			return gameAPI.withdraw(entity).toString();//username,ProductWallet,TransactionId,Amount
		} else if (gametype.equals(GameEnum.Enum_GameType.JDB168.gametype)) {
			String GAME_API_URL = keydata.get("GAME_API_URL");
			String API_URL = keydata.get("API_URL");
			String PARENT = keydata.get("PARENT");
			String SITE = keydata.get("SITE");
			String KEY = keydata.get("KEY");
			String IV = keydata.get("IV");
			String DC = keydata.get("DC");
			
			String apiOrderid =  "OUT"+orderno;//生成API端的单号
			SystemCache.getInstance().setAPIClientOrderid(orderno, apiOrderid);
			
			JDB168GameAPI gameAPI = new JDB168GameAPI(API_URL, GAME_API_URL, PARENT, DC, SITE, IV, KEY);
			entity.put("uid", username);
			entity.put("serialNo", apiOrderid);
			entity.put("amount", money);
			return gameAPI.withdraw(entity).toString();
		}
		
		
		return Enum_MSG.参数错误.message("游戏业务类型不正确");
		
	}

	/**
	 * 游戏登录接口
	 * 
	 * @param username
	 * @param password
	 * @param gametype
	 * @param biggametype
	 * @param enterprisecode
	 * @param deviceType
	 * @param gamecode 游戏代码。目前TAG提供子游戏选择，如果传递6表示捕鱼王（mh5=y 和 gameType=6 ）
	 * @param employeecode 员工编码
	 * @param attach 附带参数。对于特殊业务，此参数会传递相关标识以做相关业务逻辑（特别注意PNG、MG）
	 * @return
	 * code=0成功，其他失败
	 *	info=错误消息/成功消息/登录地址
	 *
	 */
	public static Object login(String username, String password,String gametype,String biggametype,String enterprisecode, 
			String deviceType,String gamecode,String employeecode,String attach, Map<String, String> urlMap) {
		
		String homeurl = urlMap.get("homeurl");//网站首页
		String lobbyurl = urlMap.get("lobbyurl");//网站大厅页
		String depositurl = urlMap.get("depositurl");//网站充值页面
		String withdrawurl = urlMap.get("withdrawurl");//网站取款页面
		String usercenterurl = urlMap.get("usercenterurl");//网站会员中心页面
		
		try {
			if(attach == null) {
				if(homeurl != null && !homeurl.equals("")) {
					homeurl = URLEncoder.encode(homeurl,"UTF-8");
				}
				if(lobbyurl != null && !lobbyurl.equals("")) {
					lobbyurl = URLEncoder.encode(lobbyurl,"UTF-8");
				}
				if(depositurl != null && !depositurl.equals("")) {
					depositurl = URLEncoder.encode(depositurl,"UTF-8");
				}
				if(withdrawurl != null && !withdrawurl.equals("")) {
					withdrawurl = URLEncoder.encode(withdrawurl,"UTF-8");
				}
				if(usercenterurl != null && !usercenterurl.equals("")) {
					usercenterurl = URLEncoder.encode(usercenterurl,"UTF-8");
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
		Map<String, Object> entity = new HashMap<String, Object>();
		
		
		Map<String, String> keydata = apikeys.get(enterprisecode+gametype);//查找本企业本游戏类型的keys
		
		if(keydata == null || keydata.size() == 0) {
			keydata = initKeys(gametype, enterprisecode);
		}
		if(keydata == null || keydata.size() == 0) {
			return Enum_MSG.参数错误.message("相关API参数未找到。");
		} else {
			Iterator<String> iterator = keydata.keySet().iterator();
			while(iterator.hasNext()){
			     String key = iterator.next().toString();
			     String val = keydata.get(key);
			     if(val == null || val.equals("")) {
			    	 return Enum_MSG.参数错误.message("相关API参数未找到：参数"+key);
			     }
			}
		}
		
		if(deviceType == null || deviceType.equals("")) {
			deviceType = GameEnum.Enum_deviceType.电脑.code;
		}
		
		
		if(gametype.equals(GameEnum.Enum_GameType.AG.gametype) || gametype.equals(GameEnum.Enum_GameType.PNG.gametype)) {//TAG
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String cagent = keydata.get("cagent");
			String API_URL_GCL = keydata.get("API_URL_GCL");
			String oddtype = keydata.get("oddtype");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			TAGGameAPI api = new TAGGameAPI(API_URL, MD5_KEY, DES_KEY, cagent , API_URL_GCL, oddtype, GAME_API_URL);
			entity.put("loginname", username);
			entity.put("password", password);
			entity.put("actype", "1");//账号类型（0、试玩，1、真钱）
			entity.put("oddtype", oddtype);
			entity.put("deviceType", deviceType);
			entity.put("gameType", gamecode);
			entity.put("oddtype", oddtype);
			
			//特殊处理PNG（第一次请求这里时，返回api项目的PNG游戏大厅。第二次请求这里时，根据传入的游戏代码直接返回目标登录地址）
			if(gametype.equals(GameEnum.Enum_GameType.PNG.gametype)) {
				
				if(attach == null) {
					
					String API_ROOT = GAME_API_URL;
					if(deviceType.equals(GameEnum.Enum_deviceType.电脑.code)) {
						return Enum_MSG.成功.message(API_ROOT + "/pnggame/index?employeecode="+employeecode);
					} else {
						return Enum_MSG.成功.message(API_ROOT + "/pnggame/indexh5?employeecode="+employeecode);
					}
					
				} else {
					
					//第二次已选中了相关游戏代码
					return api.login(entity);//loginname,actype,password,oddtype,deviceType
					
				}
				
				
				
			} else {
				if(gamecode == null) {
					gamecode = "0";
					
					if(deviceType.equals(GameEnum.Enum_deviceType.手机.code)) {
						gamecode = "11";//11     HTML5大厅  (AGIN移动网页版游戏平台大厅) 
					}
					
				} 
				//结合原来第三方平台，传入该电子游戏标志时
				if(gamecode.equals("slot")) {
					gamecode = "500";
				}
				entity.put("gameType", gamecode);
				//AG的捕鱼和真人直接生成目标登录地址
				return api.login(entity);//loginname,actype,password,oddtype,deviceType
				
			}
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.AV老虎机.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String operatorID = keydata.get("operatorID");
			String operatorPassword = keydata.get("operatorPassword");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			AVGameAPI gameAPI = new AVGameAPI(API_URL, operatorID, operatorPassword, GAME_API_URL);
			entity.put("userID", username);
			entity.put("deviceType", deviceType);
			return gameAPI.login(entity);//userID,deviceType
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.PT.gametype)) {
			
			//根据企业查找对应的key
			String ENTITY_KEY = keydata.get("ENTITY_KEY");
			String adminname = keydata.get("adminname");
			String kioskname = keydata.get("kioskname");
			String IS_RELEASE = keydata.get("IS_RELEASE");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			boolean RELEASE = false;
			if(IS_RELEASE.toLowerCase().equals("true")) {
				RELEASE = true;
			}
//			System.out.println("=============PT gamecode="+biggametype);
//			System.out.println("=============PT biggametype="+biggametype);
			
			if(biggametype == null || biggametype.equals("") || biggametype.equals("null")) {
				biggametype = "DZ";
			}
			
			
			
			entity.put("playername", username);
			entity.put("password", password);
			entity.put("deviceType", deviceType);
			entity.put("playtype", biggametype);
			entity.put("homeurl", homeurl);
			
			PTGameAPI ptGameAPI = new PTGameAPI(ENTITY_KEY, adminname, kioskname , RELEASE, GAME_API_URL);
			return ptGameAPI.login(entity);//playername,password,deviceType,playtype
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.WIN88.gametype)) {
			
			//根据企业查找对应的key
			String ENTITY_KEY = keydata.get("ENTITY_KEY");
			String adminname = keydata.get("adminname");
			String kioskname = keydata.get("kioskname");
			String IS_RELEASE = keydata.get("IS_RELEASE");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			boolean RELEASE = false;
			if(IS_RELEASE.toLowerCase().equals("true")) {
				RELEASE = true;
			}
//			System.out.println("=============PT gamecode="+biggametype);
//			System.out.println("=============PT biggametype="+biggametype);
			
			if(biggametype == null || biggametype.equals("") || biggametype.equals("null")) {
				biggametype = "DZ";
			}
			
			
			
			entity.put("playername", username);
			entity.put("password", password);
			entity.put("deviceType", deviceType);
			entity.put("playtype", biggametype);
			WIN88GameAPI ptGameAPI = new WIN88GameAPI(ENTITY_KEY, adminname, kioskname , RELEASE, GAME_API_URL);
			return ptGameAPI.login(entity);//playername,password,deviceType,playtype
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.东方.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String AGENT = keydata.get("AGENT");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			OGGameAPI gameAPI = new OGGameAPI(API_URL, KEY, AGENT, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			
//			1: 视讯
//			2: 体育
//			3: 彩票
//			4: 电子游戏
//			11:新平台(明升)
//			21:手机体育,og手机版
			
			
			if(biggametype.equals("SX")) {
				entity.put("gametype", "1");
			} else if(biggametype.equals("TY")) {
				entity.put("gametype", "2");
			} else if(biggametype.equals("CP")) {
				entity.put("gametype", "3");
			} else if(biggametype.equals("DZ")) {
				entity.put("gametype", "4");
				entity.put("gamekind", "0");////
			} else {
				biggametype = "SX";
				entity.put("gametype", "1");
			}
			if(deviceType.equals(GameEnum.Enum_deviceType.手机.code)) {
				entity.put("gametype", "21");//手机版的OG真人
			}
			
			return gameAPI.login(entity);//username,password,gametype
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.东方AG.gametype)) {//已取消
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String AGENT = keydata.get("AGENT");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			AGGameAPI gameAPI = new AGGameAPI(API_URL, KEY, AGENT, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			
//			1: 视讯
//			2: 体育
//			3: 彩票
//			4: 电子游戏
//			11:新平台(明升)
//			21:手机体育,og手机版
			
			if(biggametype.equals("SX")) {
				entity.put("gametype", "1");
			} else if(biggametype.equals("TY")) {
				entity.put("gametype", "2");
			} else if(biggametype.equals("CP")) {
				entity.put("gametype", "3");
			} else if(biggametype.equals("DZ")) {
				entity.put("gametype", "4");
				entity.put("gamekind", "0");////
			}
			if(deviceType.equals(GameEnum.Enum_deviceType.手机.code)) {
				entity.put("gametype", "5");
			}
			
			return gameAPI.login(entity);//username,password,gametype
			
		} else if(gametype.equals(GameEnum.Enum_GameType.棋牌.gametype)) {
			
			
			//根据企业查找对应的key
			String serviceDomain = keydata.get("serviceDomain");
			String agentId = keydata.get("agentId");
			String apiKey = keydata.get("apiKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			QPGameAPI gameAPI = new QPGameAPI(serviceDomain, agentId, apiKey, GAME_API_URL);
			entity.put("username", username);//用户长度必须为15位
			entity.put("password", password);//
//			return gameAPI.login(entity);//username,money,orderID
			return Enum_MSG.成功.message("上分成功。请使用下载app或客户端游戏");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.沙巴.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String AGENT = keydata.get("AGENT");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			IBCGameAPI gameAPI = new IBCGameAPI(API_URL, KEY, AGENT, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			
//			1: 视讯
//			2: 体育
//			3: 彩票
//			4: 电子游戏
//			11:新平台(明升)
//			21:手机体育,og手机版
			
			if(deviceType.equals(GameEnum.Enum_deviceType.电脑.code)) {
				entity.put("gametype", "2");
			} else {
				entity.put("gametype", "21");
			}
			
			return gameAPI.login(entity);//username,password,gametype
			
		} else if(gametype.equals(GameEnum.Enum_GameType.沙龙.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String CHECK_KEY = keydata.get("CHECK_KEY");
			String LOGIN_URL = keydata.get("LOGIN_URL");
			String LOBBY_CODE = keydata.get("LOBBY_CODE");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String playtype = "SX";
			if(biggametype == null || biggametype.equals("") || biggametype.equals("null")) {
				playtype = "SX";
			} else {
				playtype = biggametype;
			}
			
			SAGameAPI gameAPI = new SAGameAPI(API_URL, KEY, MD5_KEY, DES_KEY, CHECK_KEY, LOGIN_URL, LOBBY_CODE, GAME_API_URL);
			entity.put("Username", username);
			entity.put("lobby", LOBBY_CODE);
			entity.put("deviceType", deviceType);
			entity.put("playtype", playtype);
			entity.put("GameCode", gamecode);
			
			
			//如果是DZ并且是第一次进来，则直接跳转到游戏大厅页面，带游戏code后再次调用该游戏接口
			if(playtype.equals("DZ") && attach == null) {
				
				String API_ROOT = GAME_API_URL;
				if(deviceType.equals(GameEnum.Enum_deviceType.电脑.code)) {
					return Enum_MSG.成功.message(API_ROOT + "/sagame/main?employeecode="+employeecode);
				} else {
					return Enum_MSG.成功.message(API_ROOT + "/sagame/mainh5?employeecode="+employeecode);
				}
				
			} else {
				
				//第二次已选中了相关游戏代码或者是真人游戏分之时
				return gameAPI.login(entity);//Username,lobby,deviceType
			}
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.波音.gametype)) {
			
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String API_URL_LOGIN = keydata.get("API_URL_LOGIN");
			String website = keydata.get("website");
			String uppername = keydata.get("uppername");
			
			String KeyB_createAccount = keydata.get("KeyB_createAccount");
			String KeyB_getBalance = keydata.get("KeyB_getBalance");
			String KeyB_withdraw = keydata.get("KeyB_withdraw");
			String KeyB_deposit = keydata.get("KeyB_deposit");
			String KeyB_getOrder = keydata.get("KeyB_getOrder");
			String KeyB_login = keydata.get("KeyB_login");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			BBINGameAPI gameAPI = new BBINGameAPI(API_URL, website, uppername, API_URL_LOGIN, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			entity.put("KeyB", KeyB_login);
			return gameAPI.login(entity);//username,password,KeyB
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.洲际.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			ZJGameAPI gameAPI = new ZJGameAPI(API_URL, KEY, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			entity.put("nickname", username);
			return gameAPI.login(entity);//username,password,nickname
			
		} else if(gametype.equals(GameEnum.Enum_GameType.环球.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String hyAesKey = keydata.get("hyAesKey");
			String agentname = keydata.get("agentname");
			String agentpwd = keydata.get("agentpwd");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			NHQGameAPI gameAPI = new NHQGameAPI(API_URL, MD5_KEY, DES_KEY, agentname, agentpwd, hyAesKey, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			entity.put("deviceType", deviceType);
			try {
				entity.put("lobbyurl", URLDecoder.decode(lobbyurl, "UTF-8"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return gameAPI.login(entity);//username,password,deviceType
			
		} else if(gametype.equals(GameEnum.Enum_GameType.祥瑞.gametype)) {//彩票
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String FIRST_KEY = keydata.get("FIRST_KEY");
			String LAST_KEY = keydata.get("LAST_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String agent = keydata.get("agent");
			String userpoint = keydata.get("userpoint");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			//登录的接口 只有PC版本，没有手机版本
			XCPGameAPI gameAPI = new XCPGameAPI(API_URL, FIRST_KEY, LAST_KEY, DES_KEY, agent, userpoint, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			return gameAPI.login(entity);//username,password
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.MG.gametype)) {//MG
			
			//根据企业查找对应的key
			String HOST = keydata.get("HOST");
			String P_USM = keydata.get("P_USM");
			String P_PWD = keydata.get("P_PWD");
			String HOR_ID = keydata.get("HOR_ID");
			String apiusername = keydata.get("apiusername");
			String apipassword = keydata.get("apipassword");
			String partnerId = keydata.get("partnerId");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			if(biggametype == null || biggametype.equals("") || biggametype.equals("null")) {
				biggametype = "DZ";
			}
			
			
			MGGameAPI gameAPI = new MGGameAPI(HOST, P_USM, P_PWD, apiusername, apipassword, partnerId, HOR_ID, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			entity.put("deviceType", deviceType);
			entity.put("playtype", biggametype);
			
			entity.put("depositurl", depositurl);//网站充值页面
			entity.put("homeurl", homeurl);//网站首页
			
			if(attach == null) {
				
				String API_ROOT = GAME_API_URL;
				if(deviceType.equals(GameEnum.Enum_deviceType.电脑.code)) {
					return Enum_MSG.成功.message(API_ROOT + "/mggame/index?employeecode="+employeecode+"&homeurl="+ homeurl + "&depositurl="+depositurl);
				} else {
					return Enum_MSG.成功.message(API_ROOT + "/mggame/indexh5?employeecode="+employeecode+"&homeurl="+ homeurl + "&depositurl="+depositurl);
				}
				
			} else {
				entity.put("gameId", gamecode);
				//第二次已选中了相关游戏代码
				return gameAPI.login(entity);//username,password,deviceType,playtype
				
			}
			
		} else if(gametype.equals(GameEnum.Enum_GameType.TTG.gametype)) {//TTG
			
			//根据企业查找对应的key
			String privateServer = keydata.get("privateServer");
			String partnerId = keydata.get("partnerId");
			String privateLoginUrl = keydata.get("privateLoginUrl");
			String prefix = keydata.get("prefix");
			String partnerId0 = keydata.get("partnerId0");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			TTGGameAPI gameAPI = new TTGGameAPI(privateServer, partnerId, privateLoginUrl, prefix, partnerId0, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			entity.put("password", password);
			entity.put("deviceType", deviceType);
			entity.put("tester", "0");//不是测试
			return gameAPI.login(entity);//username,password,deviceType
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.DZDY.gametype)) {//DZDY
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String API_URL_LOGIN = keydata.get("API_URL_LOGIN");
			String channelid = keydata.get("channelid");
			String channelpwd = keydata.get("channelpwd");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			DZDYGameAPI gameAPI = new DZDYGameAPI(API_URL, channelid, channelpwd,API_URL_LOGIN, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			entity.put("password", password);//
			return gameAPI.login(entity).toString();//username,password
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.SGS.gametype)) {//申博
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String client_id = keydata.get("client_id");
			String client_secret = keydata.get("client_secret");
			String LobbyDomain = keydata.get("LobbyDomain");
			String istestplayer = keydata.get("istestplayer");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			SGSGameAPI gameAPI = new SGSGameAPI(API_URL, client_id, client_secret, LobbyDomain, istestplayer, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			entity.put("deviceType", deviceType);
			entity.put("playtype", biggametype);
			
			return gameAPI.login(entity).toString();//username,deviceType,playtype
			
			/****
			if(biggametype != null && biggametype.equals("SX")) {//直达真人
				entity.put("gpcode", "SB");
				entity.put("gcode", "Sunbet_Lobby");
				return gameAPI.login(entity).toString();//username,deviceType,playtype
			}
			
			//第一次没有选中任何电子游戏类
			if(attach == null) {
				
				//在线获取支持的游戏列表数据
				entity.put("deviceType", "0");
				gameAPI.getListGames(entity);
				
				entity.put("deviceType", "1");
				gameAPI.getListGames(entity);
				
				
				String API_ROOT = GAME_API_URL;
				if(deviceType.equals(GameEnum.Enum_deviceType.电脑.code)) {
					return Enum_MSG.成功.message(API_ROOT + "/sgsgame/index?employeecode="+employeecode);
				} else {
					return Enum_MSG.成功.message(API_ROOT + "/sgsgame/indexh5?employeecode="+employeecode);
				}
				
			} else {
				//第二次已选中了相关游戏代码
				entity.put("gpcode", gamecode.split(";")[0]);
				entity.put("gcode", gamecode.split(";")[1]);
				return gameAPI.login(entity).toString();//username,deviceType,playtype
				
			}
			****/
		} else if(gametype.equals(GameEnum.Enum_GameType.GG.gametype)) {//GG游行天下
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String cagent = keydata.get("cagent");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			GGGameAPI gameAPI = new GGGameAPI(API_URL, MD5_KEY, DES_KEY , cagent, GAME_API_URL);
			entity.put("loginname", username);//用户长度必须为20位
			entity.put("password", password);
			entity.put("gamecode", gamecode);
			return gameAPI.login(entity).toString();//loginname,password
						
		} else if(gametype.equals(GameEnum.Enum_GameType.IDN.gametype)) {//IDN
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String secret_key = keydata.get("secret_key");
			String LOBBY_URL = keydata.get("LOBBY_URL");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			if(deviceType.equals(GameEnum.Enum_deviceType.手机.code)) {
				EmployeeApiAccout apiAccout =  SystemCache.getInstance().getEmployeeGameAccount(employeecode, gametype);
				return Enum_MSG.成功.message("请下载app客户端进行游戏。账号："+apiAccout.getGameaccount()+"，密码："+apiAccout.getGamepassword());
			}
			
			IDNGameAPI gameAPI = new IDNGameAPI(API_URL, secret_key, LOBBY_URL, GAME_API_URL);
			entity.put("username", username);//用户长度必须为15位
			entity.put("password", password);
			entity.put("loginIp", "192.168.1.1");
			return gameAPI.login(entity).toString();//username,password,loginIp
			
		} else if(gametype.equals(GameEnum.Enum_GameType.M88.gametype)) {//M88
			
			//根据企业查找对应的key
			String SB_API_URL = keydata.get("SB_API_URL");
			String SB_LOGIN_URL = keydata.get("SB_LOGIN_URL");
			String SB_operator_id = keydata.get("SB_operator_id");
			
			String SX_API_URL = keydata.get("SX_API_URL");
			String SX_LOGIN_URL = keydata.get("SX_LOGIN_URL");
			String SX_operator_id = keydata.get("SX_operator_id");
			String SX_site_code = keydata.get("SX_site_code");
			String SX_secret_key = keydata.get("SX_secret_key");
			String SX_product_code = keydata.get("SX_product_code");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			M88GameAPI gameAPI = new M88GameAPI(SB_API_URL, SB_LOGIN_URL, SB_operator_id, SX_API_URL, SX_operator_id, SX_LOGIN_URL,  SX_site_code, SX_secret_key, SX_product_code, GAME_API_URL);
			entity.put("username", username);//用户长度必须为17位
			entity.put("employeecode", employeecode);
			return gameAPI.login(entity).toString();//username,employeecode
			
		} else if(gametype.equals(GameEnum.Enum_GameType.HAB.gametype)) {//哈巴电子
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String LOGIN_URL = keydata.get("LOGIN_URL");
			String BrandId = keydata.get("BrandId");
			String APIKey = keydata.get("APIKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			HBGameAPI gameAPI = new HBGameAPI( BrandId,  APIKey,  API_URL,  LOGIN_URL, GAME_API_URL);
			entity.put("username", username);//
			entity.put("password", password);//
			entity.put("istrueplay", "true");//
			entity.put("ifrm", "1");//
			entity.put("keyname", gamecode);//
			entity.put("lobbyurl", homeurl);//
			
			if(attach == null) {
				
				String API_ROOT = GAME_API_URL;
				if(deviceType.equals(GameEnum.Enum_deviceType.电脑.code)) {
					return Enum_MSG.成功.message(API_ROOT + "/habgame/index?employeecode="+employeecode+"&homeurl="+ homeurl);
				} else {
					return Enum_MSG.成功.message(API_ROOT + "/habgame/indexh5?employeecode="+employeecode+"&homeurl="+ homeurl);
				}
				
			} else {
				entity.put("keyname", gamecode);//
				//第二次已选中了相关游戏代码
				return gameAPI.login(entity).toString();//username,password,istrueplay,ifrm,keyname
				
			}

			
		} else if(gametype.equals(GameEnum.Enum_GameType.QTtech.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String agentname = keydata.get("agentname");
			String agentpwd = keydata.get("agentpwd");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			QTGameAPI gameAPI = new QTGameAPI(API_URL, agentname, agentpwd, GAME_API_URL);
			entity.put("playerId", username);
			entity.put("deviceType", deviceType);
			entity.put("istrueplay", "true");
			entity.put("gameId", gamecode);
			entity.put("returnUrl", homeurl);//主页地址
			
			if(attach == null) {
				
				String API_ROOT = GAME_API_URL;
				if(deviceType.equals(GameEnum.Enum_deviceType.电脑.code)) {
					return Enum_MSG.成功.message(API_ROOT + "/qtgame/index?employeecode="+employeecode+"&homeurl="+ homeurl );
				} else {
					return Enum_MSG.成功.message(API_ROOT + "/qtgame/indexh5?employeecode="+employeecode+"&homeurl="+ homeurl );
				}
				
			} else {
				entity.put("gameId", gamecode);
				//第二次已选中了相关游戏代码
				return gameAPI.login(entity).toString();//playerId,deviceType,istrueplay,gameId,returnUrl
				
			}

			
		} else if(gametype.equals(GameEnum.Enum_GameType.GB彩票.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String TPCode = keydata.get("TPCode");
			String SecretKey = keydata.get("SecretKey");
			String GeneralKey = keydata.get("GeneralKey");
			String YourDomain = keydata.get("YourDomain");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			GBGameAPI gameAPI = new GBGameAPI( API_URL, TPCode, SecretKey, GeneralKey, YourDomain, GAME_API_URL);
			entity.put("MemberID", username);//
			entity.put("deviceType", deviceType);//
			entity.put("gamecode", biggametype);//
			entity.put("GBSN", password);//密码里面存储的就是当时创建用户留下的GBSN
			return gameAPI.login(entity).toString();//MemberID,deviceType,gamecode,GBSN
			
		} else if(gametype.equals(GameEnum.Enum_GameType.EBet.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KeyB = keydata.get("KeyB");
			String subchannelid = keydata.get("subchannelid");
			String H5LOGIN_URL = keydata.get("H5LOGIN_URL");
			String channelid = keydata.get("channelid");
			String publicKey = keydata.get("publicKey");
			String privateKey = keydata.get("privateKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			EBetGameAPI gameAPI = new EBetGameAPI( API_URL, KeyB, subchannelid, H5LOGIN_URL,  channelid,  publicKey,  privateKey, GAME_API_URL);
			entity.put("username", username);//
			entity.put("password", password);//
			return gameAPI.login(entity).toString();//username,password
			
		} else if(gametype.equals(GameEnum.Enum_GameType.TGPlayer.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String client_id = keydata.get("client_id");
			String client_secret = keydata.get("client_secret");
			String istestplayer = keydata.get("istestplayer");
			String LobbyDomain = keydata.get("LobbyDomain");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
//			 SUNBET_SX=申博视讯
//			 RT_SX=红虎视讯
//			 LX_SX=勒思视讯
//			 SUNBET_SX_DZ=申博电子和视讯
//			 IGPTech=IGPTech Mini-Game
					 
			TGPGameAPI gameAPI = new TGPGameAPI( API_URL, client_id, client_secret, istestplayer,  LobbyDomain, GAME_API_URL);
			entity.put("username", username);//
			entity.put("deviceType", deviceType);//
			entity.put("playtype", biggametype);//
			return gameAPI.login(entity).toString();//username,deviceType,playtype
			
		} else if(gametype.equals(GameEnum.Enum_GameType.GGPoker.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String siteId = keydata.get("siteId");
			String SecretKey = keydata.get("SecretKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			GGPGameAPI gameAPI = new GGPGameAPI( API_URL, siteId, SecretKey, GAME_API_URL);
//			return gameAPI.login(entity).toString();//不提供登录接口
			
//			EmployeeApiAccout apiAccout =  SystemCache.getInstance().getEmployeeGameAccount(employeecode, gametype);
			return Enum_MSG.成功.message("上分成功。");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.eIBCGame.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String OpCode = keydata.get("OpCode");
			String PrivateKey = keydata.get("PrivateKey");
			String Domain = keydata.get("Domain");
			String CnameDomain = keydata.get("CnameDomain");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String playtype = "TY";
			if(biggametype != null && playtype.equals("DZ")) {
				playtype = "DZ";
			}
			
			IBCEGameAPI gameAPI = new IBCEGameAPI( API_URL, OpCode, PrivateKey, Domain, CnameDomain, GAME_API_URL);
			entity.put("PlayerName", username);//
			entity.put("password", password);//
			entity.put("deviceType", deviceType);
			entity.put("playtype", playtype);//
			return gameAPI.login(entity).toString();//PlayerName,password,deviceType,playtype
			
		} else if(gametype.equals(GameEnum.Enum_GameType.YoPLAY.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String agentid = keydata.get("agentid");
			String agentname = keydata.get("agentname");
			String agentpwd = keydata.get("agentpwd");
			String API_LOGIN_URL = keydata.get("API_LOGIN_URL");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			String gameType = "YP800";
			if(gamecode != null && gamecode.trim().startsWith("YP")) {
				gameType = gamecode;
			}
			
			YoPLAYGameAPI gameAPI = new YoPLAYGameAPI(API_URL, MD5_KEY, DES_KEY,  agentid,  agentname,  agentpwd, API_LOGIN_URL, GAME_API_URL);
			entity.put("loginname", username);//
			entity.put("password", password);//
			entity.put("acctype", "1");//
			entity.put("lang", "1");//
			entity.put("gameType", gameType);//
			return gameAPI.login(entity).toString();//loginname,password,acctype,lang,gameType
			
		} else if (gametype.equals(GameEnum.Enum_GameType.去玩棋牌.gametype)) {
			String API_URL = keydata.get("API_URL");
			String AGENT_NAME = keydata.get("AGENT_NAME");
			String AGENT_PASSWORD = keydata.get("AGENT_PASSWORD");
			String GAME_API_URL = keydata.get("GAME_API_URL");
			
			QWPGameAPI gameAPI = new QWPGameAPI(API_URL, AGENT_NAME, AGENT_PASSWORD, GAME_API_URL);
			entity.put("Account", username);
			entity.put("Password", password);
			entity.put("gamecode", gamecode);
			entity.put("returnurl", homeurl);
			return gameAPI.login(entity).toString();
		} else if(gametype.equals(GameEnum.Enum_GameType.IM体育.gametype)) {
			String API_URL = keydata.get("API_URL");
			String MerchantCode = keydata.get("MerchantCode");
			String GAME_API_URL = keydata.get("GAME_API_URL");

			IMGameAPI gameAPI = new IMGameAPI(API_URL, MerchantCode, GAME_API_URL);
			entity.put("username", username);
			entity.put("GameCode", "IMSB");
			entity.put("Language", "ZH-CN");
			entity.put("ProductWallet", "301");//IM体育
			entity.put("deviceType", deviceType);
			return gameAPI.login(entity).toString();//username,GameCode,Language,ProductWallet,deviceType
		} else if (gametype.equals(GameEnum.Enum_GameType.JDB168.gametype)) {
			String GAME_API_URL = keydata.get("GAME_API_URL");
			String API_URL = keydata.get("API_URL");
			String PARENT = keydata.get("PARENT");
			String SITE = keydata.get("SITE");
			String KEY = keydata.get("KEY");
			String IV = keydata.get("IV");
			String DC = keydata.get("DC");
			
			JDB168GameAPI gameAPI = new JDB168GameAPI(API_URL, GAME_API_URL, PARENT, DC, SITE, IV, KEY);
			entity.put("uid", username);
			return gameAPI.login(entity).toString();
		}
		
		
		return Enum_MSG.参数错误.message("游戏业务类型不正确");
		
		
	}
	
	/**
	 * 修改密码
	 * 
	 * @param username 
	 * @param password 
	 * @param gametype
	 * @param enterprisecode
	 * @return
	 * 
	 * code=0成功，返回100表示账号已存在，其他为具体错误
	 * 
	 *	info=错误消息/成功消息/具体数据
	 *
	 */
	public static String updatePassword(String username, String password,String gametype,String enterprisecode, String newpassword) {
		Map<String, Object> entity = new HashMap<String, Object>();
		
		Map<String, String> keydata = apikeys.get(enterprisecode+gametype);//查找本企业本游戏类型的keys
		
		if(keydata == null || keydata.size() == 0) {
			keydata = initKeys(gametype, enterprisecode);
		}
		if(keydata == null || keydata.size() == 0) {
			return Enum_MSG.参数错误.message("相关API参数未找到。");
		} else {
			Iterator<String> iterator = keydata.keySet().iterator();
			while(iterator.hasNext()){
			     String key = iterator.next().toString();
			     String val = keydata.get(key);
			     if(val == null || val.equals("")) {
			    	 return Enum_MSG.参数错误.message("相关API参数未找到：参数"+key);
			     }
			}
		}
		
		if(gametype.equals(GameEnum.Enum_GameType.AG.gametype) || gametype.equals(GameEnum.Enum_GameType.PNG.gametype)) {//TAG
			
			return Enum_MSG.不支持的接口.message("不提供密码修改功能");
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.AV老虎机.gametype)) {
			
			return Enum_MSG.不支持的接口.message("不提供密码修改功能，同时该线路相关接口并不需要密码");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.PT.gametype)) {
			
			//根据企业查找对应的key
			String ENTITY_KEY = keydata.get("ENTITY_KEY");
			String adminname = keydata.get("adminname");
			String kioskname = keydata.get("kioskname");
			String IS_RELEASE = keydata.get("IS_RELEASE");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			boolean RELEASE = false;
			if(IS_RELEASE.toLowerCase().equals("true")) {
				RELEASE = true;
			}
			
			PTGameAPI ptGameAPI = new PTGameAPI(ENTITY_KEY, adminname, kioskname, RELEASE, GAME_API_URL);
			entity.put("playername", username);//用户长度不能超过30位
			entity.put("password", password);
			entity.put("newpassword", newpassword);
			return ptGameAPI.updateInfo(entity).toString();//playername,password,newpassword
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.WIN88.gametype)) {
			
			//根据企业查找对应的key
			String ENTITY_KEY = keydata.get("ENTITY_KEY");
			String adminname = keydata.get("adminname");
			String kioskname = keydata.get("kioskname");
			String IS_RELEASE = keydata.get("IS_RELEASE");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			boolean RELEASE = false;
			if(IS_RELEASE.toLowerCase().equals("true")) {
				RELEASE = true;
			}
			
			WIN88GameAPI ptGameAPI = new WIN88GameAPI(ENTITY_KEY, adminname, kioskname, RELEASE, GAME_API_URL);
			entity.put("playername", username);//用户长度不能超过30位
			entity.put("password", password);
			entity.put("newpassword", newpassword);
			return ptGameAPI.updateInfo(entity).toString();//playername,password,newpassword
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.东方.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String AGENT = keydata.get("AGENT");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			OGGameAPI gameAPI = new OGGameAPI(API_URL, KEY, AGENT, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			entity.put("password", password);
			entity.put("newpassword", newpassword);
			return gameAPI.updateInfo(entity).toString();//username,password,newpassword
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.东方AG.gametype)) {//
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String AGENT = keydata.get("AGENT");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			AGGameAPI gameAPI = new AGGameAPI(API_URL, KEY, AGENT, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			entity.put("password", password);
			entity.put("newpassword", newpassword);
			return gameAPI.updateInfo(entity).toString();//username,password,newpassword
			
		} else if(gametype.equals(GameEnum.Enum_GameType.棋牌.gametype)) {
			
			
			//根据企业查找对应的key
			String serviceDomain = keydata.get("serviceDomain");
			String agentId = keydata.get("agentId");
			String apiKey = keydata.get("apiKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			QPGameAPI gameAPI = new QPGameAPI(serviceDomain, agentId, apiKey, GAME_API_URL);
			entity.put("username", username);//用户长度必须为15位
			entity.put("password", password);
			entity.put("newpassword", newpassword);
			return gameAPI.updateInfo(entity).toString();//username,password,newpassword
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.沙巴.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String AGENT = keydata.get("AGENT");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			IBCGameAPI gameAPI = new IBCGameAPI(API_URL, KEY, AGENT, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			entity.put("password", password);
			entity.put("newpassword", newpassword);
			return gameAPI.updateInfo(entity).toString();//username,password,newpassword
			
		} else if(gametype.equals(GameEnum.Enum_GameType.沙龙.gametype)) {
			
			return Enum_MSG.不支持的接口.message("不提供密码修改功能，同时该线路相关接口并不需要密码");
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.波音.gametype)) {
			
			return Enum_MSG.不支持的接口.message("不提供密码修改功能");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.洲际.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KEY = keydata.get("KEY");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			ZJGameAPI gameAPI = new ZJGameAPI(API_URL, KEY, GAME_API_URL);
			entity.put("username", username);//用户名长度应为20个字符
			entity.put("password", password);
			entity.put("newpassword", newpassword);
			return gameAPI.updateInfo(entity).toString();//username,password,newpassword
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.环球.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String hyAesKey = keydata.get("hyAesKey");
			String agentname = keydata.get("agentname");
			String agentpwd = keydata.get("agentpwd");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			NHQGameAPI gameAPI = new NHQGameAPI(API_URL, MD5_KEY, DES_KEY, agentname, agentpwd, hyAesKey, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", password);
			entity.put("newpassword", newpassword);
			return gameAPI.updateInfo(entity).toString();//username,password,newpassword
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.祥瑞.gametype)) {//彩票
			
			return Enum_MSG.不支持的接口.message("不提供密码修改功能");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.MG.gametype)) {//MG
			
			return Enum_MSG.不支持的接口.message("不提供密码修改功能");
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.TTG.gametype)) {//TTG
			
			return Enum_MSG.不支持的接口.message("不提供密码修改功能，同时该线路相关接口并不需要密码");
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.DZDY.gametype)) {//DZDY
			
			return Enum_MSG.不支持的接口.message("不提供密码修改功能，同时该线路相关接口并不需要密码");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.SGS.gametype)) {//申博
			
			return Enum_MSG.不支持的接口.message("不提供密码修改功能，同时该线路相关接口并不需要密码");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.GG.gametype)) {//游行天下GG
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String cagent = keydata.get("cagent");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			GGGameAPI gameAPI = new GGGameAPI(API_URL, MD5_KEY, DES_KEY , cagent, GAME_API_URL);
			entity.put("loginname", username);//用户长度必须为20位
			entity.put("password", newpassword);//直接传递新密码进去重置
			return gameAPI.updateInfo(entity).toString();//loginname,password
			
		} else if(gametype.equals(GameEnum.Enum_GameType.IDN.gametype)) {//IDN
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String secret_key = keydata.get("secret_key");
			String LOBBY_URL = keydata.get("LOBBY_URL");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			IDNGameAPI gameAPI = new IDNGameAPI(API_URL, secret_key, LOBBY_URL, GAME_API_URL);
			entity.put("username", username);//用户长度必须为15位
			entity.put("password", password);
			entity.put("newpassword", newpassword);
			return gameAPI.updateInfo(entity).toString();//username,password,newpassword
			
		} else if(gametype.equals(GameEnum.Enum_GameType.M88.gametype)) {//M88
			
			return Enum_MSG.不支持的接口.message("不提供密码修改功能，同时该线路相关接口并不需要密码");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.HAB.gametype)) {//哈巴电子
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String LOGIN_URL = keydata.get("LOGIN_URL");
			String BrandId = keydata.get("BrandId");
			String APIKey = keydata.get("APIKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			HBGameAPI gameAPI = new HBGameAPI( BrandId,  APIKey,  API_URL,  LOGIN_URL, GAME_API_URL);
			entity.put("username", username);//
			entity.put("newPassword", newpassword);//
			return gameAPI.updateInfo(entity).toString();//username,newPassword
			
		} else if(gametype.equals(GameEnum.Enum_GameType.QTtech.gametype)) {
			
			return Enum_MSG.不支持的接口.message("不提供密码修改功能，同时该线路相关接口并不需要密码");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.GB彩票.gametype)) {
			
			return Enum_MSG.不支持的接口.message("不提供密码修改功能，同时该线路相关接口并不需要密码");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.EBet.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KeyB = keydata.get("KeyB");
			String subchannelid = keydata.get("subchannelid");
			String H5LOGIN_URL = keydata.get("H5LOGIN_URL");
			String channelid = keydata.get("channelid");
			String publicKey = keydata.get("publicKey");
			String privateKey = keydata.get("privateKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			EBetGameAPI gameAPI = new EBetGameAPI( API_URL, KeyB, subchannelid, H5LOGIN_URL,  channelid,  publicKey,  privateKey, GAME_API_URL);
			entity.put("username", username);//
			entity.put("password", newpassword);//
			return gameAPI.updateInfo(entity).toString();//username,password
			
		} else if(gametype.equals(GameEnum.Enum_GameType.TGPlayer.gametype)) {
			
			return Enum_MSG.不支持的接口.message("不提供密码修改功能，同时该线路相关接口并不需要密码");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.GGPoker.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String siteId = keydata.get("siteId");
			String SecretKey = keydata.get("SecretKey");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			GGPGameAPI gameAPI = new GGPGameAPI( API_URL, siteId, SecretKey, GAME_API_URL);
			entity.put("username", username);//
			entity.put("newpassword", newpassword);//
			return gameAPI.updateInfo(entity).toString();//username,newpassword
			
		} else if(gametype.equals(GameEnum.Enum_GameType.eIBCGame.gametype)) {
			
			return Enum_MSG.不支持的接口.message("不提供密码修改功能，同时该线路相关接口并不需要密码");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.YoPLAY.gametype)) {
			
			return Enum_MSG.不支持的接口.message("不提供密码修改功能，同时该线路相关接口并不需要密码");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.去玩棋牌.gametype)) {
			String API_URL = keydata.get("API_URL");
			String AGENT_NAME = keydata.get("AGENT_NAME");
			String AGENT_PASSWORD = keydata.get("AGENT_PASSWORD");
			String GAME_API_URL = keydata.get("GAME_API_URL");
			
			QWPGameAPI gameAPI = new QWPGameAPI(API_URL, AGENT_NAME, AGENT_PASSWORD, GAME_API_URL);
			entity.put("Account", username);
			entity.put("Password", newpassword);
			entity.put("IP", "192.168.1.125");
			return gameAPI.updateInfo(entity).toString();
		} else if(gametype.equals(GameEnum.Enum_GameType.IM体育.gametype)) {
			String API_URL = keydata.get("API_URL");
			String MerchantCode = keydata.get("MerchantCode");
			String GAME_API_URL = keydata.get("GAME_API_URL");

			IMGameAPI gameAPI = new IMGameAPI(API_URL, MerchantCode, GAME_API_URL);
			entity.put("username", username);
			entity.put("password", newpassword);
			return gameAPI.updateInfo(entity).toString();//username,password
		} else if (gametype.equals(GameEnum.Enum_GameType.JDB168.gametype)) {
			return Enum_MSG.不支持的接口.message("不提供密码修改功能，同时该线路相关接口并不需要密码");
		}
		
		
		return Enum_MSG.参数错误.message("游戏业务类型不正确");
	}
	
	
	/**
	 * 游戏试玩
	 * 
	 * @param username 
	 * @param password 
	 * @param gametype
	 * @param enterprisecode
	 * @return
	 * 
	 * code=0成功，返回具体的试玩链接
	 * 
	 *	info=错误消息/成功消息/具体数据
	 *
	 */
	public static String tryplay(String gametype,String enterprisecode, String gamecode, String biggametype, String deviceType) {
		Map<String, Object> entity = new HashMap<String, Object>();
		
		Map<String, String> keydata = apikeys.get(enterprisecode+gametype);//查找本企业本游戏类型的keys
		
		if(keydata == null || keydata.size() == 0) {
			keydata = initKeys(gametype, enterprisecode);
		}
		if(keydata == null || keydata.size() == 0) {
			return Enum_MSG.参数错误.message("相关API参数未找到。");
		} else {
			Iterator<String> iterator = keydata.keySet().iterator();
			while(iterator.hasNext()){
			     String key = iterator.next().toString();
			     String val = keydata.get(key);
			     if(val == null || val.equals("")) {
			    	 return Enum_MSG.参数错误.message("相关API参数未找到：参数"+key);
			     }
			}
		}
		
		
		/**************试玩分数************/
		double money = 1000;
		
		String no = getAPIorderno15();
		String username = "hytry"+no;//默认20位长度
		String password = RandomStringNum.createRandomString(8).toLowerCase();
		
		if(GameEnum.Enum_GameType.AV老虎机.gametype.equals(gametype) ) {
			username = no;
		} else if(GameEnum.Enum_GameType.棋牌.gametype.equals(gametype) ) {
			username = no;
		} else if(GameEnum.Enum_GameType.GG.gametype.equals(gametype) ) {
			username = no;
		} 
		
		
		if(gametype.equals(GameEnum.Enum_GameType.AG.gametype) || gametype.equals(GameEnum.Enum_GameType.PNG.gametype)) {
			
			/**************需要创建试玩账号，进行上分，返回游戏登录地址************/
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String cagent = keydata.get("cagent");
			String API_URL_GCL = keydata.get("API_URL_GCL");
			String oddtype = keydata.get("oddtype");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			TAGGameAPI gameAPI = new TAGGameAPI(API_URL, MD5_KEY, DES_KEY, cagent , API_URL_GCL, oddtype, GAME_API_URL);
			entity.put("loginname", username);//用户长度必须为20位
			entity.put("password", password);
			entity.put("actype", "0");//账号类型（0、试玩，1、真钱）
			entity.put("oddtype", oddtype);
			JSONObject jsonObject = JSONObject.fromObject( gameAPI.createAccount(entity).toString() );//loginname,actype,password,oddtype
			
			if(jsonObject.getString("code").equals("0")) {
				String apiOrderid = getAPIorderno16();//生成API端的单号，订单号必须是16位数的数字
				entity.put("loginname", username);
				entity.put("password", password);
				entity.put("actype", "0");//账号类型（0、试玩，1、真钱）
				entity.put("billno", apiOrderid);//
				entity.put("credit", money);
				jsonObject = JSONObject.fromObject( gameAPI.deposit(entity).toString() );//loginname,actype,password,billno,credit
				
				if(jsonObject.getString("code").equals("0")) {
					entity.put("loginname", username);
					entity.put("password", password);
					entity.put("actype", "0");//账号类型（0、试玩，1、真钱）
					entity.put("oddtype", "A");
					entity.put("deviceType", deviceType);
					entity.put("gameType", gamecode);
					
					if(gamecode == null) {
						gamecode = "0";
						if(deviceType.equals(GameEnum.Enum_deviceType.手机.code)) {
							gamecode = "11";//11     HTML5大厅  (AGIN移动网页版游戏平台大厅) 
						}
					} 
					//结合原来第三方平台，传入该电子游戏标志时
					if(gamecode.equals("slot")) {
						gamecode = "500";
					}
					entity.put("gameType", gamecode);
					//AG捕鱼gamecode=6
					//AG的捕鱼和真人直接生成目标登录地址
					return gameAPI.login(entity).toString();//loginname,actype,password,oddtype,deviceType
				}
			}
			
			
			return Enum_MSG.不支持的接口.message("获取试玩链接时出现异常，请查询日志记录");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.AV老虎机.gametype)) {
			//http://jp.tokyo.com.s3-website-ap-northeast-1.amazonaws.com/gspotslots/   官方提供的整合型试玩大厅
			return Enum_MSG.不支持的接口.message("不提供试玩");
		} else if(gametype.equals(GameEnum.Enum_GameType.PT.gametype)) {
			return Enum_MSG.成功.message("http://cache.download.banner.greatfortune88.com/casinoclient.html?language=zh-cn&game="+gamecode+"&mode=offline&currency=cny");
		} else if(gametype.equals(GameEnum.Enum_GameType.WIN88.gametype)) {
			return Enum_MSG.成功.message("http://cache.download.banner.greenjade88.com/casinoclient.html?language=zh-cn&game="+gamecode+"&mode=offline&currency=cny");
		} else if(gametype.equals(GameEnum.Enum_GameType.东方.gametype)) {
			
			/**************需要创建试玩账号，进行上分，返回游戏登录地址************/
			
			//东方这是特殊的，固定的测试代理账号
			String API_URL = "http://cashapi.673ing.com/cashapi/DoBusiness.aspx";
			String AGENT = "dailishang";
			String KEY = "1111";
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			OGGameAPI gameAPI = new OGGameAPI(API_URL, KEY, AGENT, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			entity.put("password", password);
			entity.put("limit", "1,1,1,1,1,1,1,1,1,1,1,1,1");
			entity.put("limitvideo", "1");
			entity.put("limitroulette", "1");
			JSONObject jsonObject = JSONObject.fromObject( gameAPI.createAccount(entity).toString() );//username,password,limit,limitvideo,limitroulette
			
			if(jsonObject.getString("code").equals("0")) {
				String apiOrderid = getAPIorderno16();//生成API端的单号
				entity.put("username", username);
				entity.put("password", password);
				entity.put("billno", apiOrderid);//订单号长度不能大于16位，并且是纯数字
				entity.put("credit", money);
				
				jsonObject = JSONObject.fromObject( gameAPI.deposit(entity).toString() );//username,password,billno,credit
				
				if(jsonObject.getString("code").equals("0")) {
					
					entity.put("username", username);
					entity.put("password", password);
					entity.put("gametype", "1");//WEB视讯
					if(deviceType.equals(GameEnum.Enum_deviceType.手机.code)) {
						entity.put("gametype", "5");//H5视讯
					}
					return gameAPI.login(entity).toString();//username,password,gametype
					
				}
			}
			
			return Enum_MSG.不支持的接口.message("获取试玩链接时出现异常，请查询日志记录");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.东方AG.gametype)) {//
			return Enum_MSG.不支持的接口.message("不提供试玩，并且该线路已作废");
		}  else if(gametype.equals(GameEnum.Enum_GameType.沙巴.gametype)) {
			return Enum_MSG.不支持的接口.message("不提供试玩");
		} else if(gametype.equals(GameEnum.Enum_GameType.棋牌.gametype)) {
			return Enum_MSG.不支持的接口.message("不提供试玩");
		}else if(gametype.equals(GameEnum.Enum_GameType.沙龙.gametype)) {
			return Enum_MSG.不支持的接口.message("不提供试玩");
		} else if(gametype.equals(GameEnum.Enum_GameType.波音.gametype)) {
			return Enum_MSG.不支持的接口.message("不提供试玩");
		} else if(gametype.equals(GameEnum.Enum_GameType.洲际.gametype)) {
			return Enum_MSG.不支持的接口.message("不提供试玩");
		} else if(gametype.equals(GameEnum.Enum_GameType.环球.gametype)) {
			return Enum_MSG.成功.message("http://www.hq969.com/");
		} else if(gametype.equals(GameEnum.Enum_GameType.祥瑞.gametype)) {//彩票
			return Enum_MSG.不支持的接口.message("不提供试玩，并且该线路已作废");
		} else if(gametype.equals(GameEnum.Enum_GameType.MG.gametype)) {//MG
			
			/**************需要创建试玩账号，进行上分，返回游戏登录地址************/
			
			
			return Enum_MSG.不支持的接口.message("不提供试玩");
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.TTG.gametype)) {//TTG
			/*
			http://ams-games.stg.ttms.co/casino/generic/game/game.html?gameSuite=flash&gameName=Blackjack&lang=en &playerHandle=<playerHandle>&gameType=0&gameId=5&account=<account> 
			*/
			/**************需要创建试玩账号，进行上分，返回游戏登录地址************/
			
			String privateServer = keydata.get("privateServer");
			String partnerId = keydata.get("partnerId");
			String privateLoginUrl = keydata.get("privateLoginUrl");
			String prefix = keydata.get("prefix");
			String partnerId0 = keydata.get("partnerId0");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			TTGGameAPI gameAPI = new TTGGameAPI(privateServer, partnerId, privateLoginUrl,prefix, partnerId0, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			entity.put("password", password);
			entity.put("tester", "1");//1=tester,  0=不是测试.
			JSONObject jsonObject = JSONObject.fromObject( gameAPI.createAccount(entity).toString() );//username,password
			
			if(jsonObject.getString("code").equals("0")) {
				int prefixLen = prefix.length();
				String apiOrderid = prefix + RandomStringNum.createRandomString(50 - prefixLen);//生成API端的单号
				
				entity.put("username", username);//用户长度必须为20位
				entity.put("exttransactionID", apiOrderid);
				entity.put("amount", money);
				jsonObject = JSONObject.fromObject( gameAPI.deposit(entity) );//username,exttransactionID,amount
				
				if(jsonObject.getString("code").equals("0")) {
					
					entity.put("username", username);//用户长度必须为20位
					entity.put("password", password);
					entity.put("deviceType", deviceType);
					return gameAPI.login(entity).toString();//username,password,deviceType
				}
			}
			
			return Enum_MSG.不支持的接口.message("获取试玩链接时出现异常，请查询日志记录");
			
			
		} else if(gametype.equals(GameEnum.Enum_GameType.DZDY.gametype)) {//DZDY
			
			return Enum_MSG.不支持的接口.message("不提供试玩");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.SGS.gametype)) {//申博
			
			
			/**************需要创建试玩账号，进行上分，返回游戏登录地址************/
			String API_URL = keydata.get("API_URL");
			String client_id = keydata.get("client_id");
			String client_secret = keydata.get("client_secret");
			String LobbyDomain = keydata.get("LobbyDomain");
			String istestplayer = keydata.get("istestplayer");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			
			SGSGameAPI gameAPI = new SGSGameAPI(API_URL, client_id, client_secret, LobbyDomain, istestplayer, GAME_API_URL);
			entity.put("username", username);//用户长度必须为20位
			JSONObject jsonObject = JSONObject.fromObject( gameAPI.createAccount(entity).toString() );//username,password
			
			if(jsonObject.getString("code").equals("0")) {
				
				String apiOrderid = RandomString.createRandomString(20);//生成API端的单号
				entity.put("username", username);//用户长度必须为20位
				entity.put("orderid", apiOrderid);
				entity.put("amount", money);
				
				jsonObject = JSONObject.fromObject(  gameAPI.deposit(entity).toString() );//username,orderid,amount
				
				if(jsonObject.getString("code").equals("0")) {
					
					entity.put("username", username);//用户长度必须为20位
					entity.put("deviceType", deviceType);
					entity.put("playtype", biggametype);
					
					return gameAPI.login(entity).toString();//username,deviceType,playtype
				}
			}
			
			return Enum_MSG.不支持的接口.message("获取试玩链接时出现异常，请查询日志记录");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.GG.gametype)) {//游行天下GG
			
			
			/**************需要创建试玩账号，进行上分，返回游戏登录地址************/
			
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String MD5_KEY = keydata.get("MD5_KEY");
			String DES_KEY = keydata.get("DES_KEY");
			String cagent = keydata.get("cagent");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			GGGameAPI gameAPI = new GGGameAPI(API_URL, MD5_KEY, DES_KEY , cagent, GAME_API_URL);
			entity.put("loginname", username);//用户长度必须为20位
			entity.put("password", password);
			entity.put("actype", "0");//试玩
			JSONObject jsonObject = JSONObject.fromObject(  gameAPI.createAccount(entity).toString() );//loginname,password,actype
			
			if(jsonObject.getString("code").equals("0")) {
				
				String apiOrderid =  getAPIorderno16();//生成API端的单号
				entity.put("loginname", username);//用户长度必须为20位
				entity.put("password", password);
				entity.put("billno", apiOrderid);
				entity.put("credit", money);
				jsonObject = JSONObject.fromObject(  gameAPI.deposit(entity).toString() );//loginname,password,billno,credit
				
				if(jsonObject.getString("code").equals("0")) {
					
					entity.put("loginname", username);//用户长度必须为20位
					entity.put("password", password);
					return gameAPI.login(entity).toString();//loginname,password
				}
			}
			
			return Enum_MSG.不支持的接口.message("获取试玩链接时出现异常，请查询日志记录");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.IDN.gametype)) {//IDN
			
			return Enum_MSG.不支持的接口.message("获取试玩链接时出现异常，请查询日志记录");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.M88.gametype)) {//M88
			
			return Enum_MSG.不支持的接口.message("获取试玩链接时出现异常，请查询日志记录");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.HAB.gametype)) {//哈巴电子
			
			return Enum_MSG.不支持的接口.message("获取试玩链接时出现异常，请查询日志记录");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.QTtech.gametype)) {
			
			return Enum_MSG.不支持的接口.message("获取试玩链接时出现异常，请查询日志记录");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.GB彩票.gametype)) {
			
			return Enum_MSG.不支持的接口.message("获取试玩链接时出现异常，请查询日志记录");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.EBet.gametype)) {
			
			//根据企业查找对应的key
			String API_URL = keydata.get("API_URL");
			String KeyB = keydata.get("KeyB");
			String subchannelid = keydata.get("subchannelid");
			String H5LOGIN_URL = keydata.get("H5LOGIN_URL");
			String GAME_API_URL = keydata.get("GAME_API_URL");//游戏使用的域名。代替api.hyzonghe.net
			
			return Enum_MSG.成功.message(H5LOGIN_URL);
			
		} else if(gametype.equals(GameEnum.Enum_GameType.TGPlayer.gametype)) {
			
			return Enum_MSG.不支持的接口.message("获取试玩链接时出现异常，请查询日志记录");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.GGPoker.gametype)) {
			
			return Enum_MSG.不支持的接口.message("获取试玩链接时出现异常，请查询日志记录");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.eIBCGame.gametype)) {
			
			return Enum_MSG.不支持的接口.message("获取试玩链接时出现异常，请查询日志记录");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.YoPLAY.gametype)) {
			
			return Enum_MSG.不支持的接口.message("获取试玩链接时出现异常，请查询日志记录");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.去玩棋牌.gametype)) {
			
			return Enum_MSG.不支持的接口.message("获取试玩链接时出现异常，请查询日志记录");
			
		} else if(gametype.equals(GameEnum.Enum_GameType.IM体育.gametype)) {
			
			return Enum_MSG.不支持的接口.message("获取试玩链接时出现异常，请查询日志记录");
			
		} else if (gametype.equals(GameEnum.Enum_GameType.JDB168.gametype)) {
			String GAME_API_URL = keydata.get("GAME_API_URL");
			String API_URL = keydata.get("API_URL");
			String PARENT = keydata.get("PARENT");
			String SITE = keydata.get("SITE");
			String KEY = keydata.get("KEY");
			String IV = keydata.get("IV");
			String DC = keydata.get("DC");
			
			JDB168GameAPI gameAPI = new JDB168GameAPI(API_URL, GAME_API_URL, PARENT, DC, SITE, IV, KEY);
			entity.put("uid", "try");
			return gameAPI.login(entity).toString();
		}
		
		return Enum_MSG.参数错误.message("游戏业务类型不正确");
	}
	
	
	
	
	/**
	 * 根据客户端订单号获取api的单号
	 * @param clientOrderid
	 * @return
	 */
	private static String getAPIOrderid(String clientOrderid) {
		return SystemCache.getInstance().getAPIOrderid(clientOrderid);
	}
	
	@Deprecated
	public Object getRecord(Map<String, Object> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Deprecated
	public Object updateInfo(Map<String, Object> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Deprecated
	public Object isOnLine(Map<String, Object> entity) {
		
		return Enum_MSG.参数错误.message("游戏业务类型不正确");
		
	}
	
	/***/
	private static int POKE15 = 10;
	private static String getAPIorderno15(){
		POKE15++;
		if(POKE15>=99) POKE15 = 10;
		return (new Date().getTime()*100 + POKE15) + "";
	}
	
	
	private static int POKE16 = 100;
	private static String getAPIorderno16(){
		POKE16++;
		if(POKE16>=999) POKE16 = 100;
		return (new Date().getTime()*1000 + POKE16) + "";
	}
	
	private static int POKE17 = 1000;
	private static String getAPIorderno17(){
		POKE17++;
		if(POKE17>=9990) POKE17 = 1000;
		return (new Date().getTime()*10000 + POKE17) + "";
	}
	
	private static int POKE18 = 10000;
	private static String getAPIorderno18(){
		POKE18++;
		if(POKE18>=99999) POKE18 = 10000;
		return (new Date().getTime()*100000 + POKE18) + "";
	}
	
	private static int POKE19 = 100000;
	private static String getAPIorderno19(){
		POKE19++;
		if(POKE19>=900000) POKE19 = 100000;
		return (new Date().getTime()*1000000 + POKE19) + "";
	}
	
	
	
}
