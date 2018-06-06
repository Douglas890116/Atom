package com.hy.pull.common.util.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.hy.pull.common.util.Encrypt;
import com.hy.pull.common.util.GameHttpUtil;
import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.game.NHQGame;
import com.hy.pull.common.util.game.sa.DeEnCode;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * EVEB的沙巴体育游戏接口
 * 
 * 时区是GMT-4
 * 
 * 即北京时间-12小时
 * 
 * @author temdy
 */
public class IBCEGameAPI implements BaseInterface {
	
	private String API_URL = "http://api.prod.ib.gsoft88.net/api/";
	private String OpCode = "HYFCN";
	private String PrivateKey = "SKX2342KSDZXH84";
	private String Domain = "rrc11.com";
	private String CnameDomain = "http://api.rrc11.com/";//该参数用于决定用什么域名进入EIBCController，因为要在这里面写cookies。类似于支付域名
	
	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	
//	NBBET娱乐：rrc11.com
//	东方会：rrc18.com
//	金蛋娱乐：rrc58.com
	
	/**
	 * 默认构造函数
	 */
	@Deprecated
	public IBCEGameAPI(){
		
	}
	
	/**
	 * 构建函数，初始化参数
	 * @param API_URL API接口URL
	 * @param MD5_KEY MD5密钥
	 */
	public IBCEGameAPI(String API_URL,String OpCode,String PrivateKey, String Domain, String CnameDomain, String GAME_API_URL){
		this.API_URL = API_URL;
		this.OpCode = OpCode;
		this.PrivateKey = PrivateKey;
		this.Domain = Domain;
		this.CnameDomain = CnameDomain;
		this.GAME_API_URL = GAME_API_URL;
	}
	
	/**
	 * 创建用户接口
	 * @param entity 参数列表 PlayerName,password
	 * 
	 * PlayerName=20个字符
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString()  );
		try{
			if(MapUtil.isNulls(entity, "PlayerName,password")){	
				
//				OddsType：
//				1  马来人的赔率 
//				2  香港赔率 
//				3  十进制的赔率 
//				4  印度赔率 
//				5  美国的赔率
				
				StringBuffer params = new StringBuffer();
				params.append("OpCode=").append(this.OpCode).append("&");
				params.append("PlayerName=").append(entity.get("PlayerName")).append("&");
				params.append("OddsType=").append("3").append("&");//十进制的赔率 
				params.append("MaxTransfer=").append("9999999999").append("&");
				params.append("MinTransfer=").append("1").append("");
				
				String SecurityToken = getSecurityToken("CreateMember", params.toString());
				params.append("&SecurityToken=").append(SecurityToken).append("");
				
				String result = getUrl(this.API_URL.concat("CreateMember?").concat(params.toString()));
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if(object.has("code") && object.getString("code").equals("1001")) {
					return Enum_MSG.失败.message(object.getString("info"));
				} else {
					if( object.getString("error_code").equals("0")) {
						
						return Enum_MSG.成功.message("创建成功");//
						
					} else {
						return Enum_MSG.失败.message(object.getString("error_code"), "创建玩家失败："+object.getString("message"));
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
	 * 获取余额
	 * @param entity 参数列表 username
	 * username 用户名
	 * @return 返回结果
	 */
	@Override
	public Object getBalance(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() );
		try{
			if(MapUtil.isNulls(entity, "PlayerName,password")){	
				
				StringBuffer params = new StringBuffer();
				params.append("OpCode=").append(this.OpCode).append("&");
				params.append("PlayerName=").append(entity.get("PlayerName")).append("");
				
				String SecurityToken = getSecurityToken("CheckUserBalance", params.toString());
				params.append("&SecurityToken=").append(SecurityToken).append("");
				
				String result = getUrl(this.API_URL.concat("CheckUserBalance?").concat(params.toString()));
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if(object.has("code") && object.getString("code").equals("1001")) {
					
					return Enum_MSG.失败.message(object.getString("info"));
				} else {
					if( object.getString("error_code").equals("0")) {
						JSONArray array = object.getJSONArray("Data");
						
						return Enum_MSG.成功.message(array.getJSONObject(0).getString("balance"));//
						
					} else {
						return Enum_MSG.失败.message(object.getString("error_code"), "获取用户余额失败："+object.getString("message"));
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
	 * 下分（取款）的接口
	 * @param entity 参数列表 PlayerName,OpTransId,amount,password
	 * 
	 * OpTransId=50个字符
	 * 
	 * @return 返回结果 
	 */
	@Override
	public Object withdraw(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() );
		try{
			if(MapUtil.isNulls(entity, "PlayerName,OpTransId,amount,password")){	
				
				StringBuffer params = new StringBuffer();
				params.append("OpCode=").append(this.OpCode).append("&");
				params.append("PlayerName=").append(entity.get("PlayerName")).append("&");
				params.append("OpTransId=").append(entity.get("OpTransId")).append("&");//50个字符
				params.append("amount=").append(entity.get("amount")).append("&");
				params.append("direction=").append("0").append("");//0=撤回 1=存款
				
				String SecurityToken = getSecurityToken("FundTransfer", params.toString());
				params.append("&SecurityToken=").append(SecurityToken).append("");
				
				String result = getUrl(this.API_URL.concat("FundTransfer?").concat(params.toString()));
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if(object.has("code") && object.getString("code").equals("1001")) {
					
					return Enum_MSG.失败.message(object.getString("info"));
				}else {
					if( object.getString("error_code").equals("0")) {
						
						return Enum_MSG.成功.message(object.getJSONObject("Data").getString("status"));//
						
					} else {
						return Enum_MSG.失败.message(object.getString("error_code"), "存款失败："+object.getString("message"));
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
	 * 上分（存款）的接口
	 * @param entity 参数列表 PlayerName,OpTransId,amount,password
	 * 
	 * OpTransId=最大50个字符
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() );
		try{
			if(MapUtil.isNulls(entity, "PlayerName,OpTransId,amount,password")){	
				
				StringBuffer params = new StringBuffer();
				params.append("OpCode=").append(this.OpCode).append("&");
				params.append("PlayerName=").append(entity.get("PlayerName")).append("&");
				params.append("OpTransId=").append(entity.get("OpTransId")).append("&");//50个字符
				params.append("amount=").append(entity.get("amount")).append("&");
				params.append("direction=").append("1").append("");//0=撤回 1=存款
				
				String SecurityToken = getSecurityToken("FundTransfer", params.toString());
				params.append("&SecurityToken=").append(SecurityToken).append("");
				
				String result = getUrl(this.API_URL.concat("FundTransfer?").concat(params.toString()));
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if(object.has("code") && object.getString("code").equals("1001")) {
					return Enum_MSG.失败.message(object.getString("info"));
					
				}else {
					if( object.getString("error_code").equals("0")) {
						
						return Enum_MSG.成功.message(object.getJSONObject("Data").getString("status"));//
						
					} else {
						return Enum_MSG.失败.message(object.getString("error_code"), "存款失败："+object.getString("message"));
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
	 * 获取游戏结果
	 * @param entity 参数列表
	 * @return 返回结果
	 */
	@Override
	public Object getRecord(Map<String, Object> entity) {
		
		StringBuffer params = new StringBuffer();
		params.append("OpCode=").append(this.OpCode).append("&");
		params.append("LastVersionKey=").append("0").append("");//最大值
		
		String SecurityToken = getSecurityToken("GetSportBetLog", params.toString());
		params.append("&SecurityToken=").append(SecurityToken).append("");
		
		String result = getUrl(this.API_URL.concat("GetSportBetLog?").concat(params.toString()));
		System.out.println("调用结果："+result);
		
		JSONObject object = JSONObject.fromObject(result);
		
//		{"LastVersionKey":11012527,"TotalRecord":8,"Data":[{"TransId":102054669407,"PlayerName":"hyfdfh2230","TransactionTime":"2017-05-12T02:06:19.92","MatchId":22314465,"LeagueId":8970,"LeagueName":"CHINA 2ND DIVISION","SportType":1,"AwayId":391643,"AwayIDName":"Shanghai Sunfun FC","HomeId":391651,"HomeIDName":"Zhenjiang Huasa FC","MatchDatetime":"2017-05-12T02:59:00","BetType":2,"ParlayRefNo":0,"BetTeam":"h","HDP":0.0000,"AwayHDP":0.0000,"HomeHDP":0.0000,"Odds":1.9400,"OddsType":3,"AwayScore":null,"HomeScore":null,"IsLive":"0","LastBallNo":null,"TicketStatus":"LOSE","Stake":12.0000,"WinLoseAmount":-12.0000,"WinLostDateTime":"2017-05-12T00:00:00","currency":"RMB","VersionKey":10996969},{"TransId":102054670915,"PlayerName":"hyfdfh2230","TransactionTime":"2017-05-12T02:06:30.683","MatchId":22314432,"LeagueId":11779,"LeagueName":"AUSTRALIA BRISBANE PREMIER LEAGUE RESERVE","SportType":1,"AwayId":289617,"AwayIDName":"Lions FC (R)","HomeId":292464,"HomeIDName":"Logan Lightning (R)","MatchDatetime":"2017-05-12T04:29:00","BetType":1,"ParlayRefNo":0,"BetTeam":"h","HDP":1.2500,"AwayHDP":1.2500,"HomeHDP":0.0000,"Odds":1.9000,"OddsType":3,"AwayScore":null,"HomeScore":null,"IsLive":"0","LastBallNo":null,"TicketStatus":"LOSE","Stake":10.0000,"WinLoseAmount":-10.0000,"WinLostDateTime":"2017-05-12T00:00:00","currency":"RMB","VersionKey":10998237},{"TransId":102054672652,"PlayerName":"hyfdfh2230","TransactionTime":"2017-05-12T02:06:40.633","MatchId":22314432,"LeagueId":11779,"LeagueName":"AUSTRALIA BRISBANE PREMIER LEAGUE RESERVE","SportType":1,"AwayId":289617,"AwayIDName":"Lions FC (R)","HomeId":292464,"HomeIDName":"Logan Lightning (R)","MatchDatetime":"2017-05-12T04:29:00","BetType":1,"ParlayRefNo":0,"BetTeam":"h","HDP":1.2500,"AwayHDP":1.2500,"HomeHDP":0.0000,"Odds":1.9000,"OddsType":3,"AwayScore":null,"HomeScore":null,"IsLive":"0","LastBallNo":null,"TicketStatus":"LOSE","Stake":10.0000,"WinLoseAmount":-10.0000,"WinLostDateTime":"2017-05-12T00:00:00","currency":"RMB","VersionKey":10998238},{"TransId":102054673040,"PlayerName":"hyfdfh2230","TransactionTime":"2017-05-12T02:06:48.787","MatchId":22314484,"LeagueId":8740,"LeagueName":"KOREA WOMEN K-LEAGUE","SportType":1,"AwayId":354177,"AwayIDName":"Boeun Sangmu (W)","HomeId":294198,"HomeIDName":"Icheon Daekyo (W)","MatchDatetime":"2017-05-12T05:59:00","BetType":1,"ParlayRefNo":0,"BetTeam":"h","HDP":-1.7500,"AwayHDP":0.0000,"HomeHDP":1.7500,"Odds":1.8600,"OddsType":3,"AwayScore":null,"HomeScore":null,"IsLive":"0","LastBallNo":null,"TicketStatus":"LOSE","Stake":10.0000,"WinLoseAmount":-10.0000,"WinLostDateTime":"2017-05-12T00:00:00","currency":"RMB","VersionKey":10999874},{"TransId":102054659612,"PlayerName":"hyfdfh2230","TransactionTime":"2017-05-12T02:05:09.973","MatchId":22312012,"LeagueId":11711,"LeagueName":"AUSTRALIA VICTORIA PREMIER LEAGUE","SportType":1,"AwayId":12408,"AwayIDName":"Green Gully Cavaliers","HomeId":41090,"HomeIDName":"Pascoe Vale SC","MatchDatetime":"2017-05-12T06:14:00","BetType":5,"ParlayRefNo":0,"BetTeam":"1","HDP":0.0000,"AwayHDP":1.0000,"HomeHDP":1.1000,"Odds":3.0500,"OddsType":3,"AwayScore":null,"HomeScore":null,"IsLive":"0","LastBallNo":null,"TicketStatus":"LOSE","Stake":10.0000,"WinLoseAmount":-10.0000,"WinLostDateTime":"2017-05-12T00:00:00","currency":"RMB","VersionKey":11000281},{"TransId":102054662401,"PlayerName":"hyfdfh2230","TransactionTime":"2017-05-12T02:05:28.123","MatchId":22312012,"LeagueId":11711,"LeagueName":"AUSTRALIA VICTORIA PREMIER LEAGUE","SportType":1,"AwayId":12408,"AwayIDName":"Green Gully Cavaliers","HomeId":41090,"HomeIDName":"Pascoe Vale SC","MatchDatetime":"2017-05-12T06:14:00","BetType":5,"ParlayRefNo":0,"BetTeam":"1","HDP":0.0000,"AwayHDP":1.0000,"HomeHDP":1.1000,"Odds":3.0500,"OddsType":3,"AwayScore":null,"HomeScore":null,"IsLive":"0","LastBallNo":null,"TicketStatus":"LOSE","Stake":10.0000,"WinLoseAmount":-10.0000,"WinLostDateTime":"2017-05-12T00:00:00","currency":"RMB","VersionKey":11000282},{"TransId":102054667401,"PlayerName":"hyfdfh2230","TransactionTime":"2017-05-12T02:06:03.877","MatchId":22304824,"LeagueId":247,"LeagueName":"CHINA FOOTBALL SUPER LEAGUE","SportType":1,"AwayId":293491,"AwayIDName":"Hebei China Fortune","HomeId":154969,"HomeIDName":"Guangzhou R&F","MatchDatetime":"2017-05-12T07:34:00","BetType":3,"ParlayRefNo":0,"BetTeam":"h","HDP":2.7500,"AwayHDP":0.0000,"HomeHDP":2.7500,"Odds":1.7800,"OddsType":3,"AwayScore":null,"HomeScore":null,"IsLive":"0","LastBallNo":null,"TicketStatus":"LOSE","Stake":10.0000,"WinLoseAmount":-10.0000,"WinLostDateTime":"2017-05-12T00:00:00","currency":"RMB","VersionKey":11001851},{"TransId":102054673691,"PlayerName":"hyfdfh2230","TransactionTime":"2017-05-12T02:06:56.547","MatchId":22304429,"LeagueId":45742,"LeagueName":"*ENGLISH PREMIER LEAGUE - CORNERS","SportType":1,"AwayId":32890,"AwayIDName":"Watford No.of Corners","HomeId":28193,"HomeIDName":"Everton No.of Corners","MatchDatetime":"2017-05-12T14:44:00","BetType":5,"ParlayRefNo":0,"BetTeam":"1","HDP":0.0000,"AwayHDP":1.0000,"HomeHDP":1.1200,"Odds":1.2900,"OddsType":3,"AwayScore":null,"HomeScore":null,"IsLive":"0","LastBallNo":null,"TicketStatus":"WON","Stake":10.0000,"WinLoseAmount":2.9000,"WinLostDateTime":"2017-05-12T00:00:00","currency":"RMB","VersionKey":11012527}],"error_code":0,"message":"Successfully executed"}
		
		//接口调用成功
		if( object.getString("error_code").equals("0")) {
			
			System.out.println("data="+object.get("Data"));
			
		} else {
			System.out.println("接口调用失败");
		}
		return null;
	}

	/**
	 * 更新信息接口
	 * @param entity 参数列表 username,newpassword
	 * @return 返回结果
	 */
	@Override
	public Object updateInfo(Map<String, Object> entity) {
		return null;
	}

	/**
	 * 获取订单接口
	 * @param entity 参数列表 PlayerName,OpTransId,password
	 * 
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object getOrder(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString()  );
		try{
			if(MapUtil.isNulls(entity, "PlayerName,OpTransId,password")){	
				
				StringBuffer params = new StringBuffer();
				params.append("OpCode=").append(this.OpCode).append("&");
				params.append("PlayerName=").append(entity.get("PlayerName")).append("&");
				params.append("OpTransId=").append(entity.get("OpTransId")).append("");//50个字符
				
				String SecurityToken = getSecurityToken("CheckFundTransfer", params.toString());
				params.append("&SecurityToken=").append(SecurityToken).append("");
				
				String result = getUrl(this.API_URL.concat("CheckFundTransfer?").concat(params.toString()));
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if(object.has("code") && object.getString("code").equals("1001")) {
					return Enum_MSG.失败.message(object.getString("info"));
				}else {
					if( object.getString("error_code").equals("0")) {
						
						return Enum_MSG.成功.message(object.getJSONObject("Data").getString("status"));//
						
					} else {
						return Enum_MSG.失败.message(object.getString("error_code"), "存款失败："+object.getString("message"));
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
	 * 登录的接口
	 * @param entity 参数列表 PlayerName,password,deviceType,playtype
	 * 
	 * playtype=TY/DZ
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object login(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() );
		try{
			if(MapUtil.isNulls(entity, "PlayerName,password,deviceType,playtype")){	
				
				StringBuffer params = new StringBuffer();
				params.append("OpCode=").append(this.OpCode).append("&");
				params.append("PlayerName=").append(entity.get("PlayerName")).append("");
				
				String SecurityToken = getSecurityToken("Login", params.toString());
				params.append("&SecurityToken=").append(SecurityToken).append("");
				
				String result = getUrl(this.API_URL.concat("Login?").concat(params.toString()));
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() , result );
				
				JSONObject object = JSONObject.fromObject(result);
				
				//接口调用成功
				if(object.has("code") && object.getString("code").equals("1001")) {
					
					return Enum_MSG.失败.message(object.getString("info"));
				}else {
					
					if( object.getString("error_code").equals("0")) {
						
						String sessionToken = object.getString("sessionToken");
						
						if( !this.CnameDomain.endsWith("/")) {
							this.CnameDomain = this.CnameDomain + "/";
						}
						
						StringBuilder url = new StringBuilder();
						url.append(this.CnameDomain);//API路径
						
						String deviceType = entity.get("deviceType").toString();
						String playtype = entity.get("playtype").toString();
						
						if(deviceType.equals("0")) {
							url.append("/eibcgame/index?");
						} else {
							url.append("/eibcgame/indexh5?");
						}
						
						url.append("playtype="+playtype);
						url.append("&sessionToken="+sessionToken);
						url.append("&domain="+this.Domain);
						
						return Enum_MSG.成功.message(url.toString());//
						
					} else {
						return Enum_MSG.失败.message(object.getString("error_code"), "获取sessionToken失败："+object.getString("message"));
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
	
	private String getUrl(String url) {
		//
		CloseableHttpClient httpClient = HttpClients.createDefault();
		int timeout = 10000;
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();//设置请求和传输超时时间
		
		
		HttpGet request = new HttpGet(url);
		request.setConfig(requestConfig);
	    HttpResponse response=null;
	 
		try {
			System.out.println("请求URL："+url);
			response = httpClient.execute(request);
			
			String result = EntityUtils.toString(response.getEntity());
//			System.out.println("result="+result);
			JSONObject resultObject = JSONObject.fromObject( result );
			
			if(resultObject.has("code")) {
				if(resultObject.getString("code").equals("SUCCESS")) {
					return result;
				}
				return Enum_MSG.接口调用错误.message(resultObject.getString("code") +"=" +resultObject.getString("message"));
			}
			 
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return Enum_MSG.接口调用错误.message(e.getMessage());
		}finally{
			
		}
	}
	
	public String getSecurityToken(String method,String params) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getSecurityToken", params  );
		try{
			
			String result = Encrypt.MD5(this.PrivateKey + "/api/"+method+"?" + params).toUpperCase();
			System.out.println("生成SecurityToken结果："+result);
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getSecurityToken", params , result );
			
			return result;
			
		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
	}

	public static void main(String[] args) throws Exception {
		IBCEGameAPI nhq = new IBCEGameAPI();
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("PlayerName", RandomStringNum.createRandomString(20));
		entity.put("password", "12345678");
		System.out.println(nhq.createAccount(entity));//PlayerName,password
//		
//		
		entity.put("amount", "1");
		entity.put("OpTransId", UUID.randomUUID().toString());
//		System.out.println(nhq.deposit(entity));//PlayerName,OpTransId,amount,password
		
		entity.put("amount", "1");
		entity.put("OpTransId", UUID.randomUUID().toString());
//		System.out.println(nhq.withdraw(entity));//PlayerName,OpTransId,amount,password
		
//		entity.put("newpassword", RandomString.createRandomString(8));
//		System.out.println(nhq.updateInfo(entity));//username,newpassword
		
//		System.out.println(nhq.getOrder(entity));//PlayerName,OpTransId,password
//		
//		System.out.println(nhq.getBalance(entity));//PlayerName,password
//		
		entity.put("deviceType", "0");
		entity.put("playtype", "TY");
//		System.out.println(nhq.login(entity));//PlayerName,password,deviceType,playtype
		
//		System.out.println(nhq.getRecord(entity));
		
		System.out.println(RandomString.createRandomString(20));
	}
}
