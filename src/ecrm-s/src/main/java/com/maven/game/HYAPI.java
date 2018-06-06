package com.maven.game;

import org.apache.commons.lang.StringUtils;

import com.maven.entity.GameApiInput;
import com.maven.game.enums.GameBigType;
import com.maven.game.enums.GameEnum;
import com.maven.logger.TLogger;

public class HYAPI {
	
	/**
	 * 服务地址
	 */
	private  String service;
	
	/**
	 * 平台ID
	 */
	private  String platform;
	
	/**
	 * DES秘钥
	 */
	private  String deskey;
	
	/**
	 * MD5秘钥
	 */
	private  String md5key;
	
	/**
	 * 
	 * @param url 服务地址
	 * @param gameCode 服务ID
	 * @param gameSign 游戏类型
	 * @param apikey1 DES秘钥
	 * @param apikey2 MD5秘钥
	 * @throws Exception 
	 */
	public HYAPI(GameApiInput arguments){
		this.service = arguments.getApiurl();
		this.platform = arguments.getPlatformcode();
		this.deskey = arguments.getApikey1();
		this.md5key = arguments.getApikey2();
	}
	

	/**
	 * 创建用户
	 * @param type
	 * @param username
	 * @param password
	 * @param usercode
	 * @param userparentcode
	 * @param usertype
	 * @return
	 * @throws Exception
	 */
	public  String createUser(String type,String username ,String password , String usercode,String userparentcode,String usertype) throws Exception{
		StringBuffer param = new StringBuffer();
		param.append("username=").append(username)
			.append("!password=").append(password)
			.append("!method=").append("createUser")
			.append("!usercode=").append(usercode)
			.append("!parentcode=").append(userparentcode)
			.append("!usertype=").append(usertype);
		if(type.equals(GameEnum.Enum_GameType.祥瑞.gametype)){
			param.append("!userpoint=").append("2.5");
		}
		String url = service+"/web?params="+HYEncrypt.encrypt(param.toString(), deskey)+"&key="+HYEncrypt.string2MD5(param.toString() + md5key)+"&platform="+platform+"&type="+type;
		TLogger.getLogger().Error("调用API接口,创建账户,参数："+param+"  URL:"+url);
		return HttpUtils.get(url);
	}
	
	/**
	 * 查询余额
	 * @param type
	 * @param username
	 * @param password
	 * @param usercode
	 * @return
	 * @throws Exception
	 */
	public  String balance(String type,String username,String password ,String usercode) throws Exception{
			StringBuffer param = new StringBuffer();
			param.append("username=").append(username)
				.append("!password=").append(password)
				.append("!method=").append("balance")
				.append("!usercode=").append(usercode);
			String url = service+"/web?params="+HYEncrypt.encrypt(param.toString(), deskey)+"&key="+HYEncrypt.string2MD5(param.toString() + md5key)+"&platform="+platform+"&type="+type;
			return HttpUtils.get(url);
	}
	
	/**
	 * 游戏上分
	 * @param username
	 * @param password
	 * @param money
	 * @param ordernum
	 * @return
	 * @throws Exception
	 */
	public  String upIntegral(String type,String username,String password,String money,String ordernum) throws Exception{
		StringBuffer param = new StringBuffer();
		param.append("username=").append(username)
			.append("!password=").append(password)
			.append("!money=").append(money)
			.append("!ordernum=").append(ordernum)
			.append("!method=").append("deposit");
		String uri = service+"/web?params="+HYEncrypt.encrypt(param.toString(), deskey)+"&key="+HYEncrypt.string2MD5(param.toString() + md5key)+"&platform="+platform+"&type="+type;
		return HttpUtils.get(uri);
	}
	
	/**
	 * 游戏下分
	 * @param username
	 * @param password
	 * @param money
	 * @param ordernum
	 * @return
	 * @throws Exception
	 */
	public  String downIntegral(String type,String username,String password,String money,String ordernum) throws Exception{
		StringBuffer param = new StringBuffer();
		param.append("username=").append(username)
			.append("!password=").append(password)
			.append("!money=").append(money)
			.append("!ordernum=").append(ordernum)
			.append("!method=").append("withdraw");
		String url = service+"/web?params="+HYEncrypt.encrypt(param.toString(), deskey)+"&key="+HYEncrypt.string2MD5(param.toString() + md5key)+"&platform="+platform+"&type="+type;
		return HttpUtils.get(url);
	}
	/**
	 * 进入游戏
	 * @param type
	 * @param username
	 * @param password
	 * @param usercode
	 * @return
	 * @throws Exception
	 */
	public  String play(String type,String playtype,String username,String password,String usercode,String devicetype) throws Exception{
		StringBuffer param = new StringBuffer();
		param.append("username=").append(username)
			.append("!password=").append(password);
		if(type.equals(GameEnum.Enum_GameType.AG.gametype)){
			if(StringUtils.isNotBlank(playtype)){
				param.append("!gtype=").append(playtype);
			}
		}
		if(type.equals(GameEnum.Enum_GameType.沙龙.gametype)){
			if(playtype.equals(GameBigType.SX)){
				param.append("!method=").append("saplayvideo");
			}else if(playtype.equals(GameBigType.DZ)){
				param.append("!method=").append("saplayslot");
			}else{
				param.append("!method=").append("saplayvideo");
			}
		}else{
			param.append("!method=").append("play");
		}
		
		param.append("!usercode=").append(usercode);
		if(StringUtils.isNotBlank(devicetype)){
			param.append("!gametype=").append(devicetype);
		}
		String url = service+"/web?params="+HYEncrypt.encrypt(param.toString(), deskey)+"&key="+HYEncrypt.string2MD5(param.toString() + md5key)+"&platform="+platform+"&type="+type;
		if(type.equals(GameEnum.Enum_GameType.AV老虎机.gametype)
				||type.equals(GameEnum.Enum_GameType.沙龙.gametype)){
			return url;
		}else{
			return HttpUtils.get(url);
		}
	}
	
	
	public static void main(String[] args) {
		try {
			GameApiInput arguments = new GameApiInput();
			String deskey = ("desnew33");	
			String md5key = ("md5new33");
			String platform = ("3a59ec9998a74b939e85e54fdf11f7c5");
			String service = ("http://77777f.com/service/");
			
			
			
			String username = "hq483992";
			String password = "112233";	
			String usercode = "323";
			String devicetype = "1";
			String playtype = "SX";
			String type = GameEnum.Enum_GameType.AG.gametype;
			
			StringBuffer param = new StringBuffer();
			param.append("username=").append(username)
				.append("!password=").append(password);
			if(type.equals(GameEnum.Enum_GameType.AG.gametype)){
				if(StringUtils.isNotBlank(playtype)){
					param.append("!gtype=").append(playtype);
				}
			}
			if(type.equals(GameEnum.Enum_GameType.沙龙.gametype)){
				if(playtype.equals(GameBigType.SX)){
					param.append("!method=").append("saplayvideo");
				}else if(playtype.equals(GameBigType.DZ)){
					param.append("!method=").append("saplayslot");
				}else{
					param.append("!method=").append("saplayvideo");
				}
			}else{
				param.append("!method=").append("play");
			}
			
			if(StringUtils.isNotBlank(devicetype)){
				param.append("!deviceType=").append(devicetype);
			}
			String url = service+"/web?params="+HYEncrypt.encrypt(param.toString(), deskey)+"&key="+HYEncrypt.string2MD5(param.toString() + md5key)+"&platform="+platform+"&type="+type;
			System.out.println(HttpUtils.get(url));;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获取数据
	 * @param type
	 * @param username
	 * @param beginTime
	 * @param endTime
	 * @param pageSize
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public  String data(String type,String username,String beginTime ,String endTime,int pageSize, int page) throws Exception{
		StringBuffer param = new StringBuffer();
		param.append("username=").append(username)
			.append("!begintime=").append(beginTime)
			.append("!endtime=").append(endTime)
			.append("!pagesize=").append(pageSize)
			.append("!page=").append(page);
		String url = service+"/info?params="+HYEncrypt.encrypt(param.toString(), deskey)+"&key="+HYEncrypt.string2MD5(param.toString() + md5key)+"&platform="+platform+"&type="+type;
		return HttpUtils.get(url);
	}
	

}
