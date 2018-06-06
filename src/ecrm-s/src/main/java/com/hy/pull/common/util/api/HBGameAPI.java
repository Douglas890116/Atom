package com.hy.pull.common.util.api;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.UTCTimeUtil;
import com.hy.pull.common.util.game.hb.DepositPlayerMoneyRequest;
import com.hy.pull.common.util.game.hb.DepositPlayerMoneyResponse;
import com.hy.pull.common.util.game.hb.Game;
import com.hy.pull.common.util.game.hb.GameClientDbDTO;
import com.hy.pull.common.util.game.hb.GameRequest;
import com.hy.pull.common.util.game.hb.GameResponse;
import com.hy.pull.common.util.game.hb.HostedSoap;
import com.hy.pull.common.util.game.hb.HostedSoapProxy;
import com.hy.pull.common.util.game.hb.LoginOrCreatePlayer;
import com.hy.pull.common.util.game.hb.LoginOrCreatePlayerRequest;
import com.hy.pull.common.util.game.hb.LoginOrCreatePlayerResponse;
import com.hy.pull.common.util.game.hb.LoginUserResponse;
import com.hy.pull.common.util.game.hb.MoneyResponse;
import com.hy.pull.common.util.game.hb.PlayerCompletedGamesDTO;
import com.hy.pull.common.util.game.hb.QueryPlayerRequest;
import com.hy.pull.common.util.game.hb.QueryPlayerResponse;
import com.hy.pull.common.util.game.hb.QueryTransferRequest;
import com.hy.pull.common.util.game.hb.QueryTransferResponse;
import com.hy.pull.common.util.game.hb.ReportRequest;
import com.hy.pull.common.util.game.hb.UpdatePlayerPasswordRequest;
import com.hy.pull.common.util.game.hb.UpdatePlayerPasswordResponse;
import com.hy.pull.common.util.game.hb.WithdrawPlayerMoneyRequest;
import com.maven.config.SystemConstant;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

/**
 * HABA哈巴电子 游戏接口
 * @author Administrator
 *
 */
public class HBGameAPI implements BaseInterface {

	
	private String API_URL = "https://ws-a.insvr.com/hosted.asmx?wsdl";
	private String LOGIN_URL = "https://app-a.insvr.com/play";
	private String BrandId = "2b07f09a-ca12-e711-80c4-000d3a805b30";
	private String APIKey = "CD524E5F-4342-4AFA-91B5-42955EB109CD";
	
	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	/**
	 * 构造方法
	 */
	public HBGameAPI(String BrandId, String APIKey, String API_URL, String LOGIN_URL, String GAME_API_URL) {
		this.BrandId = BrandId;
		this.APIKey = APIKey;
		this.API_URL = API_URL;
		this.LOGIN_URL = LOGIN_URL;
		this.GAME_API_URL = GAME_API_URL;
	}
	
	/**
	 * 构造方法
	 */
	@Deprecated
	public HBGameAPI() {
		
	}
	
	/**
	 * 创建用户接口
	 * @param entity 参数列表 username,password,istrueplay
	 * 
	 * istrueplay true=真实玩家用户 false=试玩（目前试玩无法创建）
	 * @return 返回结果
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString()  );
		
		
		if(MapUtil.isNulls(entity, "username,password,istrueplay")){	
			
			if( String.valueOf(entity.get("username")).length() > 30 ) {
				return Enum_MSG.参数错误.message("用户长度不能大于30位");
			}
			if( String.valueOf(entity.get("password")).length() > 30 ) {
				return Enum_MSG.参数错误.message("密码长度不能大于30位");
			}
			
			LoginOrCreatePlayerRequest request = new LoginOrCreatePlayerRequest();
			request.setBrandId(this.BrandId);
			request.setAPIKey(this.APIKey);
			request.setPlayerHostAddress("192.168.1.1");
			request.setKeepExistingToken(true);//如果玩家已经登录，不要创建一个新的会话token
			request.setUsername(entity.get("username").toString());//最多使用150 个⫿符
			request.setPassword(entity.get("password").toString());//不限
			
			if( String.valueOf(entity.get("istrueplay")).equals("true") ) {
				request.setCurrencyCode("CNY");//请参阅货币代码的附录。注意：ZZZ代码表示免费玩货币
			} else {
				request.setCurrencyCode("ZZZ");//请参阅货币代码的附录。注意：ZZZ代码表示免费玩货币
			}
//			object.setUserAgent("");//发送玩家浏览器的用户代理⫿符串，所以我们可以在报表中显示设备 
			
			
			try {
				HostedSoap soap = new HostedSoapProxy(API_URL);
				LoginUserResponse result = soap.loginOrCreatePlayer(request);
				
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString(), result.getMessage()  );
				
				if(result.isPlayerCreated() == false) {//创建失败，账户已存在或密码错误
					return Enum_MSG.账号已存在.message(result.getToken());//不管成功失败，都要返回token，因为登录游戏时调用也是该方法用于获取token
				}
				return Enum_MSG.成功.message(result.getToken());
				
			} catch (Exception e) {
				e.printStackTrace();
				return Enum_MSG.逻辑事务异常.message("远程方法调用错误：请检查白名单IP");
			}
			
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
		
	}

	
	/**
	 * 获取余额
	 * @param entity 参数列表 username,password
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object getBalance(Map<String, Object> entity) {
		
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "username,password")){
			
			QueryPlayerRequest request = new QueryPlayerRequest();
			request.setBrandId(this.BrandId);
			request.setAPIKey(this.APIKey);
			request.setUsername(entity.get("username").toString());//最多使用150 个⫿符
			request.setPassword(entity.get("password").toString());
			
			try {
				HostedSoap soap = new HostedSoapProxy(API_URL);
				QueryPlayerResponse result = soap.queryPlayer(request);
				
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString(), result.getMessage()  );
				
				if(result.isFound() == false) {//
					return Enum_MSG.失败.message(result.getMessage());
				} else {
					return Enum_MSG.成功.message(result.getRealBalance().toString());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return Enum_MSG.逻辑事务异常.message("远程方法调用错误：请检查白名单IP");
			}
			
			
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
		
		
	}

	
	/**
	 * 下分（取款）的接口 username,password,Amount,RequestId
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object withdraw(Map<String, Object> entity) {
		
		
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "username,password,Amount,RequestId")){
			
			if( Double.valueOf(String.valueOf(entity.get("Amount"))) < 1.00 ) {
				return Enum_MSG.参数错误.message("金额不能小于1元，建议金额为大于0的正整数");
			}
			if( String.valueOf(entity.get("RequestId")).length() > 50 ) {
				return Enum_MSG.参数错误.message("订单号必须是不能大于50个字符");
			}
			if( !String.valueOf(entity.get("RequestId")).startsWith("OUT") ) {
				return Enum_MSG.参数错误.message("存款订单号必须以OUT开头");
			}
			
			WithdrawPlayerMoneyRequest request = new WithdrawPlayerMoneyRequest();
			request.setBrandId(this.BrandId);
			request.setAPIKey(this.APIKey);
			request.setCurrencyCode("CNY");//请参阅货币代码的附录。注意：ZZZ代码表示免费玩货币
			request.setUsername(entity.get("username").toString());//最多使用150 个⫿符
			request.setPassword(entity.get("password").toString());
			request.setAmount(new BigDecimal(String.valueOf(entity.get("Amount"))).negate());//必须要负数
			request.setRequestId(String.valueOf(entity.get("RequestId")));
			
			try {
				HostedSoap soap = new HostedSoapProxy(API_URL);
				MoneyResponse result = soap.withdrawPlayerMoney(request);
				
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString(), result.getMessage()  );
				
				if(result.isSuccess() == false) {//
					return Enum_MSG.失败.message(result.getMessage());
				} else {
					return Enum_MSG.成功.message(result.getRealBalance().toString());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return Enum_MSG.逻辑事务异常.message("远程方法调用错误：请检查白名单IP");
			}
			
			
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
		
		
	}

	
	/**
	 * 上分（存款）的接口 username,password,Amount,RequestId
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "username,password,Amount,RequestId")){
			
			if( Double.valueOf(String.valueOf(entity.get("Amount"))) < 1.00 ) {
				return Enum_MSG.参数错误.message("金额不能小于1元，建议金额为大于0的正整数");
			}
			if( String.valueOf(entity.get("RequestId")).length() > 50 ) {
				return Enum_MSG.参数错误.message("订单号必须是不能大于50个字符");
			}
			if( !String.valueOf(entity.get("RequestId")).startsWith("IN") ) {
				return Enum_MSG.参数错误.message("存款订单号必须以IN开头");
			}
			
			DepositPlayerMoneyRequest request = new DepositPlayerMoneyRequest();
			request.setBrandId(this.BrandId);
			request.setAPIKey(this.APIKey);
			request.setCurrencyCode("CNY");//请参阅货币代码的附录。注意：ZZZ代码表示免费玩货币
			request.setUsername(entity.get("username").toString());//最多使用150 个⫿符
			request.setPassword(entity.get("password").toString());
			request.setAmount(new BigDecimal(String.valueOf(entity.get("Amount"))));
			request.setRequestId(String.valueOf(entity.get("RequestId")));
			
			try {
				HostedSoap soap = new HostedSoapProxy(API_URL);
				MoneyResponse result = soap.depositPlayerMoney(request);
				
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString(), result.getMessage()  );
				
				if(result.isSuccess() == false) {//
					return Enum_MSG.失败.message(result.getMessage());
				} else {
					return Enum_MSG.成功.message(result.getRealBalance().toString());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return Enum_MSG.逻辑事务异常.message("远程方法调用错误：请检查白名单IP");
			}
			
			
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
		
		
	}

	@Override
	public Object getRecord(Map<String, Object> entity) {
		// TODO Auto-generated method stub
		
		Calendar calendar = Calendar.getInstance() ;  
		calendar.add(Calendar.DATE, -1);
		
		String starttime = UTCTimeUtil.getUTCTimeStr(calendar.getTime());//yyyyMMddHHmmss.UTC
		String endtime   = UTCTimeUtil.getUTCTimeStr();//yyyyMMddHHmmss.UTC
		ReportRequest request = new ReportRequest();
		request.setBrandId(this.BrandId);
		request.setAPIKey(this.APIKey);
		request.setDtStartUTC(starttime);
		request.setDtEndUTC(endtime);
		
		System.out.println("starttime="+starttime);
		System.out.println("endtime  ="+endtime);
		try {
			HostedSoap soap = new HostedSoapProxy(API_URL);
			PlayerCompletedGamesDTO[] result = soap.getBrandCompletedGameResults(request);
			
			for (PlayerCompletedGamesDTO playerCompletedGamesDTO : result) {
				System.out.print(playerCompletedGamesDTO.getBrandGameId());System.out.print("-");
				System.out.print(playerCompletedGamesDTO.getBrandId());System.out.print("-");
				System.out.print(playerCompletedGamesDTO.getCurrencyCode());System.out.print("-");
				System.out.print(playerCompletedGamesDTO.getFriendlyGameInstanceId());System.out.print("-");
				System.out.print(playerCompletedGamesDTO.getGameInstanceId());System.out.print("-");
				System.out.print(playerCompletedGamesDTO.getGameKeyName());System.out.print("-");
				System.out.print(playerCompletedGamesDTO.getPlayerId());System.out.print("-");
				System.out.print(playerCompletedGamesDTO.getUsername());System.out.print("-");
				System.out.print(playerCompletedGamesDTO.getChannelTypeId());System.out.print("-");
				System.out.print(playerCompletedGamesDTO.getDtCompleted().getTime().toLocaleString());System.out.print("-");
				System.out.print(playerCompletedGamesDTO.getDtStarted().getTime().toLocaleString());System.out.print("-");
				System.out.print(playerCompletedGamesDTO.getGameTypeId());System.out.print("-");
				
				System.out.print(playerCompletedGamesDTO.getStake().doubleValue());System.out.print("-");//投注
				System.out.print(playerCompletedGamesDTO.getPayout().doubleValue());System.out.print("-");//支付额
				System.out.print(playerCompletedGamesDTO.getJackpotWin().doubleValue());System.out.print("-");//奖池盈
				System.out.print(playerCompletedGamesDTO.getJackpotContribution().doubleValue());System.out.print("-");
				System.out.println();
				
				//比如：
				//投注25（减）40支付（减）0奖池赢= -15 盈利 （这个例子就是玩家赢，我们输）
				//如果投注25（减）15支付（减）0奖池赢= 10 盈利 （这个例子就是玩家输，我们赢了10块）
			    
			}
			
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			return Enum_MSG.逻辑事务异常.message("远程方法调用错误：请检查白名单IP");
		}
		
		
	}

	
	/**
	 * 更新信息接口
	 * 
	 * 
	 * @param entity 参数列表 username,newPassword
	 * @return 返回结果
	 */
	@Override
	public Object updateInfo(Map<String, Object> entity) {
		
		
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString()  );
		if(MapUtil.isNulls(entity, "username,newPassword")){
			
			UpdatePlayerPasswordRequest request = new UpdatePlayerPasswordRequest();
			request.setBrandId(this.BrandId);
			request.setAPIKey(this.APIKey);
			request.setUsername(entity.get("username").toString());//最多使用150 个⫿符
			request.setNewPassword(entity.get("newPassword").toString());
			
			try {
				HostedSoap soap = new HostedSoapProxy(API_URL);
				UpdatePlayerPasswordResponse result = soap.updatePlayerPassword(request);
				
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString(), result.getMessage()  );
				
				if(result.isSuccess() == false) {//
					return Enum_MSG.失败.message(result.getMessage());
				} else {
					return Enum_MSG.成功.message(result.getMessage());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return Enum_MSG.逻辑事务异常.message("远程方法调用错误：请检查白名单IP");
			}
			
			
		}  else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
		
	}

	
	/**
	 * 获取订单接口
	 * @param entity 参数列表 username,password,RequestId
	 * @return 返回结果
	 */
	@Override
	public Object getOrder(Map<String, Object> entity) {
		
		
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString()  );
		
		if(MapUtil.isNulls(entity, "username,password,RequestId")){
			
			QueryTransferRequest request = new QueryTransferRequest();
			request.setBrandId(this.BrandId);
			request.setAPIKey(this.APIKey);
			request.setRequestId(String.valueOf(entity.get("RequestId")));
			
			try {
				HostedSoap soap = new HostedSoapProxy(API_URL);
				QueryTransferResponse result = soap.queryTransfer(request);
				
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString(), result.isSuccess() + ""  );
				
				if(result.isSuccess() == false) {//
					return Enum_MSG.失败.message("错误：没有找到该交易");
				} else {
					return Enum_MSG.成功.message(result.getDtAdded().getTimeInMillis()+"");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return Enum_MSG.逻辑事务异常.message("远程方法调用错误：请检查白名单IP");
			}
			
			
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
		
		
	}

	public Object getGames() {
		GameRequest request = new GameRequest();
		request.setBrandId(this.BrandId);
		request.setAPIKey(this.APIKey);
		
		try {
			HostedSoap soap = new HostedSoapProxy(API_URL);
			GameResponse result = soap.getGames(request);
			GameClientDbDTO[] games = result.getGames();
			
			System.out.println(result.getHostedLaunchUrl());
			
			for (GameClientDbDTO gameClientDbDTO : games) {
				System.out.println(
								gameClientDbDTO.getBrandGameId() + " - "+ 
								gameClientDbDTO.getFileName() + " - "+
								gameClientDbDTO.getGameTypeName() + " - "+ 
								
								gameClientDbDTO.getBaseGameTypeId() + " - "+ 
								gameClientDbDTO.getGroupName() + " - "+ 
								gameClientDbDTO.getGameTypeName() + " - "+ 
								gameClientDbDTO.getGameTypeName() + " - "+ 
								gameClientDbDTO.getGameTypeName() + " - "+ 
								gameClientDbDTO.getGameTypeName() + " - "+ 
								gameClientDbDTO.getGameTypeName() + " - "+ 
								
								gameClientDbDTO.getKeyName());
			}
			return games;
			
		} catch (Exception e) {
			e.printStackTrace();
			return Enum_MSG.逻辑事务异常.message("远程方法调用错误：请检查白名单IP");
		}
	}
	
	/**
	 * 登录的接口
	 * @param entity 参数列表 username,password,istrueplay,ifrm,keyname
	 * 
	 * istrueplay true/false
	 * lobbyurl 网站地址
	 * ifrm 如果是iframe请传递1
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object login(Map<String, Object> entity) {
		
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() );
		
		if(MapUtil.isNulls(entity, "username,password,istrueplay,ifrm,keyname")){		
			
			JSONObject jsonObject = JSONObject.fromObject(this.createAccount(entity));
			String token = null;
			
			if(jsonObject.getString("code").equals("0") || jsonObject.getString("code").equals("100") ) {
				token = jsonObject.getString("info");
			} else {
				return Enum_MSG.逻辑事务异常.message("未能获取到token");
			}
			
			String API_ROOT = this.GAME_API_URL;
			
			StringBuilder param = new StringBuilder();
			param.append("brandid=");
			param.append(this.BrandId).append("&");
			param.append("token=");
			param.append(token).append("&");
			param.append("mode=");
			if(String.valueOf(entity.get("istrueplay")).equals("true")) {
				param.append("real").append("&");
			} else {
				param.append("fun").append("&");
			}
			
			param.append("locale=zh-CN").append("&");
			param.append("lobbyurl=");
			param.append(entity.get("lobbyurl")).append("&");
			
			if(String.valueOf(entity.get("ifrm")).equals("1")) {
				param.append("ifrm=1").append("&");
			} 
			param.append("keyname=");
			param.append(entity.get("keyname")).append("&");
			
			String url = this.LOGIN_URL.concat("?").concat(param.toString());
			
			BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() , url );
			
			//	接口调用成功
			return Enum_MSG.成功.message(this.LOGIN_URL.concat("?").concat(param.toString()));
			
		} else {
			return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
		}
	}

	@Override
	public Object isOnLine(Map<String, Object> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		HBGameAPI tag = new HBGameAPI();

		Map<String, Object> entity = new HashMap<String, Object>();
//		password=0O2eqRar, istrueplay=true, username=MOpFVk7aEV0mBclCtfYi0Om4D8NthK
		
//		entity.put("username", "MOpFVk7aEV0m*@l#tfYi0Om4D8NthK");
		entity.put("username", RandomString.createRandomString(20));
		entity.put("password", RandomString.createRandomString(8));
		entity.put("istrueplay", "true");
		System.out.println(tag.createAccount(entity));//username,password,istrueplay
		
		
		entity.put("RequestId", "IN"+RandomStringNum.createRandomString(30));
		entity.put("Amount", "5");
//		entity.put("username", "MOpFVk7aEV0mBclCtfYi0Om4D8NthK");
//		entity.put("password", "0O2eqRar");
//		System.out.println(tag.deposit(entity));//username,password,Amount,RequestId
//
//		entity.put("RequestId", "OUT"+RandomStringNum.createRandomString(30));
//		entity.put("Amount", "2");
//		System.out.println(tag.withdraw(entity));//username,password,Amount,RequestId
//
//		System.out.println(tag.getOrder(entity));//username,password,RequestId
//
		System.out.println(tag.getBalance(entity));//username,password

		entity.put("ifrm", "0");
		entity.put("istrueplay", "true");
		entity.put("keyname", "SGTheKoiGate");
		System.out.println(tag.login(entity));//username,password,istrueplay,ifrm,brandgameid
//		a1164e70-1322-48e8-9717-a50d57149c13

//		tag.getGames();//获取游戏列表，关键
//		tag.getRecord(entity);
		
	}
}
