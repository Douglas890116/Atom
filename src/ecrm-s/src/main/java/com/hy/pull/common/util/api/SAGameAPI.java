package com.hy.pull.common.util.api;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hy.pull.common.util.game.sa.GameHttpUtil;
import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.game.sa.DeEnCode;
import com.hy.pull.common.util.game.sa.MD5Encoder;
import com.hy.pull.common.util.game.sa.XmlUtil;
import com.maven.config.SystemConstant;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;
import com.maven.util.StringUtils;

/**
 * 沙龙游戏接口
 * @author temdy
 */
public class SAGameAPI implements BaseInterface {
	
	private String API_URL   = "http://api.sa-gaming.net/api/api.aspx";
	
	private String KEY = "32C3DD24BF3A4EDDB59240CCBA7E3975";
	private String MD5_KEY = "GgaIMaiNNtg";
	private String DES_KEY = "g9G16nTs";
	private String CHECK_KEY = "hygjgame20168888";
	private String LOGIN_URL = "http://xs.sa-api.net/app.aspx";
	private String LOBBY_CODE = "HuanQiuHaoYing";//登录时显示的logo代码
	
	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	
	/**
	 * 默认构造函数
	 */
	@Deprecated
	public SAGameAPI(){
		
	}
	
	/**
	 * 构建函数，初始化参数
	 * @param API_URL 接口URL
	 * @param KEY 密钥
	 * @param MD5_KEY MD5密钥
	 * @param DES_KEY DES密钥
	 * @param CHECK_KEY 检查密钥
	 */
	public SAGameAPI(String API_URL,String KEY,String MD5_KEY,String DES_KEY,String CHECK_KEY, String LOGIN_URL,String LOBBY_CODE,String GAME_API_URL){
		this.API_URL = API_URL;
		this.KEY = KEY;
		this.MD5_KEY = MD5_KEY;
		this.DES_KEY = DES_KEY;
		this.CHECK_KEY = CHECK_KEY;
		this.LOGIN_URL = LOGIN_URL;
		this.LOBBY_CODE = LOBBY_CODE;
		this.GAME_API_URL = GAME_API_URL;
	}

	/**
	 * 创建用户接口
	 * @param entity 参数列表
	 * Username 用户名 文字数字(6~20 字符) 
	 * CurrencyType 币种 CNY默认
	 * @return 返回结果
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString()  );
		try{
			
//			Username  用户名 
//			ErrorMsg  错误信息详细
			
//			ErrorMsgId 错误信息: 
//			0: 成功 
//			100: 用户名错误 
//			108: 用户名长度或者格式错误 
//			109: Check key 验证失败 
//			113: 用户名已存在 
//			114: 币种不存在 
//			128: 解密错误 
			
			if(MapUtil.isNulls(entity, "Username")){
				
				if( String.valueOf(entity.get("Username")).length() > 20 ) {
					return Enum_MSG.参数错误.message("用户长度不能大于20位");
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String Time = sdf.format(new Date());
				StringBuilder param = new StringBuilder();
				param.append("method=RegUserInfo");
				param.append("&Key=");
				param.append(KEY);
				param.append("&Time=");
				param.append(Time);
				param.append("&Checkkey=");
				param.append(CHECK_KEY);
				param.append("&Username=");
				param.append(entity.get("Username"));
				param.append("&CurrencyType=");
				param.append(entity.get("CurrencyType") == null ? "CNY" : entity.get("CurrencyType"));
				String q = DeEnCode.encrypt(param.toString(), DES_KEY);
				q=URLEncoder.encode(q,"GBK");
				String a = (param.toString()+MD5_KEY+Time+KEY);
				String s = MD5Encoder.encode(a);
				StringBuffer params = new StringBuffer();
				params.append("q=").append(q);
				params.append("&s=").append(s);
				// 调用接口
				String result = GameHttpUtil.sendPost(API_URL+"?", params.toString());
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , result );
				
				if(result.indexOf("version") > -1 && result.indexOf("xml") > -1) {//正常调用并返回xml格式数据
					
					Map<String, String> object = XmlUtil.getQueryParams(result);
					
					if(object.get("ErrorMsgId").equals("0")) {
						return Enum_MSG.成功.message(object.get("Username"));
					} else {
						if(object.get("ErrorMsgId").equals("113")) {
							return Enum_MSG.账号已存在.message(object.get("ErrorMsg"));
						}
						return Enum_MSG.失败.message(object.get("ErrorMsgId"), object.get("ErrorMsg"));
					}
					
				} else {
					return result;//出现异常
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
	 * @return 返回结果
	 */
	@Override
	public Object getBalance(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() );
		try{
			
//			<ErrorMsgId>0</ErrorMsgId>
//			<ErrorMsg>Success</ErrorMsg>
//			<IsSuccess>true</IsSuccess>
//			<Username>hylgfsd90fudsf8dx2x</Username>
//			<Balance>0</Balance>
//			<Online>false</Online>
//			<Betted>false</Betted>
//			<BettedAmount>0</BettedAmount>
			
			if(MapUtil.isNulls(entity, "Username")){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String Time = sdf.format(new Date());
				StringBuilder param = new StringBuilder();
				param.append("method=GetUserStatusDV");
				param.append("&Key=");
				param.append(KEY);
				param.append("&Time=");
				param.append(Time);
				param.append("&Username=");
				param.append(entity.get("Username"));
				String q = DeEnCode.encrypt(param.toString(), DES_KEY);
				q=URLEncoder.encode(q,"GBK");
				String a = (param.toString()+MD5_KEY+Time+KEY);
				String s = MD5Encoder.encode(a);
				StringBuffer params = new StringBuffer();
				params.append("q=").append(q);
				params.append("&s=").append(s);
				// 调用接口
				String result = GameHttpUtil.sendPost(API_URL+"?", params.toString());
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , result );
				
				if(result.indexOf("version") > -1 && result.indexOf("xml") > -1) {//正常调用并返回xml格式数据
					
					Map<String, String> object = XmlUtil.getQueryParams(result);
					
					if(object.get("ErrorMsgId").equals("0")) {
						return Enum_MSG.成功.message(object.get("Balance"));//返回Balance
					} else {
						return Enum_MSG.失败.message(object.get("ErrorMsgId"), object.get("ErrorMsg"));
					}
					
				} else {
					return result;//出现异常
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
	 * Username,OrderId,DebitAmount
	 * 
	 * 
	 * 订单ID最长40位: OUT+YYYYMMDDHHMMSS+Username  例如: “OUT20131129130345peter1235” 
	 * @param entity 参数列表
	 * @return 返回结果 当前Balance
	 */
	@Override
	public Object withdraw(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() );
		try{
			
//			<DebitBalanceResponse xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
//			  <ErrorMsgId>121</ErrorMsgId>
//			  <ErrorMsg>Not enough points to credit/debit/bet</ErrorMsg>
//			  <Username>hylgfsd90fudsf8dx2x</Username>
//			  <Balance>0</Balance>
//			  <DebitAmount>1</DebitAmount>
//			  <OrderId>OUT20161123162101hylgfsd90fudsf8dx2x</OrderId>
//			</DebitBalanceResponse>
			
			if(MapUtil.isNulls(entity, "Username,OrderId,DebitAmount")){
				
				if( String.valueOf(entity.get("OrderId")).length() > 40 ) {
					return Enum_MSG.参数错误.message("订单号长度不能超过40位");
				}
				if( !String.valueOf(entity.get("OrderId")).startsWith("OUT") ) {
					return Enum_MSG.参数错误.message("订单号请以OUT开头");
				}
				if( !String.valueOf(entity.get("OrderId")).endsWith( String.valueOf(entity.get("Username"))) ) {
					return Enum_MSG.参数错误.message("订单号请以用户名结尾");
				}
				if( Double.valueOf(entity.get("DebitAmount").toString()) < 1.0 ) {
					return Enum_MSG.参数错误.message("取款金额不能小于1元");
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String Time = sdf.format(new Date());
				StringBuilder param = new StringBuilder();
				param.append("method=DebitBalanceDV");
				param.append("&Key=");
				param.append(KEY);
				param.append("&Time=");
				param.append("&Checkkey=");
				param.append(CHECK_KEY);
				param.append(Time);
				param.append("&Username=");
				param.append(entity.get("Username"));
				param.append("&OrderId=");
				param.append(entity.get("OrderId"));
				param.append("&DebitAmount=");
				param.append(entity.get("DebitAmount"));
				
				String q = DeEnCode.encrypt(param.toString(), DES_KEY);
				q=URLEncoder.encode(q,"GBK");
				String a = (param.toString()+MD5_KEY+Time+KEY);
				String s = MD5Encoder.encode(a);
				StringBuffer params = new StringBuffer();
				params.append("q=").append(q);
				params.append("&s=").append(s);
				// 调用接口
				String result = GameHttpUtil.sendPost(API_URL+"?", params.toString());
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() , result );
				
				if(result.indexOf("version") > -1 && result.indexOf("xml") > -1) {//正常调用并返回xml格式数据
					
					Map<String, String> object = XmlUtil.getQueryParams(result);
					
					if(object.get("ErrorMsgId").equals("0")) {
						return Enum_MSG.成功.message(object.get("Balance"));//返回Balance
					} else {
						return Enum_MSG.失败.message(object.get("ErrorMsg"));
					}
					
				} else {
					return result;//出现异常
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
	 * Username,OrderId,CreditAmount
	 * 
	 * 订单ID最长40位: OUT+YYYYMMDDHHMMSS+Username  例如: “OUT20131129130345peter1235” 
	 * @param entity 参数列表
	 * @return 返回结果 当前Balance
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() );
		try{
			
//			<DebitBalanceResponse xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
//			  <ErrorMsgId>121</ErrorMsgId>
//			  <ErrorMsg>Not enough points to credit/debit/bet</ErrorMsg>
//			  <Username>hylgfsd90fudsf8dx2x</Username>
//			  <Balance>0</Balance>
//			  <DebitAmount>1</DebitAmount>
//			  <OrderId>OUT20161123162101hylgfsd90fudsf8dx2x</OrderId>
//			</DebitBalanceResponse>
			
			if(MapUtil.isNulls(entity, "Username,OrderId,CreditAmount")){
				
				if( String.valueOf(entity.get("OrderId")).length() > 40 ) {
					return Enum_MSG.参数错误.message("订单号长度不能超过40位");
				}
				if( !String.valueOf(entity.get("OrderId")).startsWith("IN") ) {
					return Enum_MSG.参数错误.message("订单号请以IN开头");
				}
				if( !String.valueOf(entity.get("OrderId")).endsWith( String.valueOf(entity.get("Username"))) ) {
					return Enum_MSG.参数错误.message("订单号请以用户名结尾");
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String Time = sdf.format(new Date());
				StringBuilder param = new StringBuilder();
				param.append("method=CreditBalanceDV");
				param.append("&Key=");
				param.append(KEY);
				param.append("&Time=");
				param.append("&Checkkey=");
				param.append(CHECK_KEY);
				param.append(Time);
				param.append("&Username=");
				param.append(entity.get("Username"));
				param.append("&OrderId=");
				param.append(entity.get("OrderId"));
				param.append("&CreditAmount=");
				param.append(entity.get("CreditAmount"));
				
				String q = DeEnCode.encrypt(param.toString(), DES_KEY);
				q=URLEncoder.encode(q,"GBK");
				String a = (param.toString()+MD5_KEY+Time+KEY);
				String s = MD5Encoder.encode(a);
				StringBuffer params = new StringBuffer();
				params.append("q=").append(q);
				params.append("&s=").append(s);
				// 调用接口
				String result = GameHttpUtil.sendPost(API_URL+"?", params.toString());
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() , result );
				
				if(result.indexOf("version") > -1 && result.indexOf("xml") > -1) {//正常调用并返回xml格式数据
					
					Map<String, String> object = XmlUtil.getQueryParams(result);
					
					if(object.get("ErrorMsgId").equals("0")) {
						return Enum_MSG.成功.message(object.get("Balance"));//返回Balance
					} else {
						return Enum_MSG.失败.message(object.get("ErrorMsg"));
					}
					
				} else {
					return result;//出现异常
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
	 * @return 返回结果
	 */
	@Override
	public Object updateInfo(Map<String, Object> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获取订单接口
	 * @param entity 参数列表
	 * OrderId 订单号 
	 * @return 返回结果
	 */
	@Override
	public Object getOrder(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() );
		try{
			if(MapUtil.isNulls(entity, "OrderId")){
				
				if( String.valueOf(entity.get("OrderId")).length() > 40 ) {
					return Enum_MSG.参数错误.message("订单号长度不能超过40位");
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String Time = sdf.format(new Date());
				StringBuilder param = new StringBuilder();
				param.append("method=CheckOrderId");
				param.append("&Key=");
				param.append(KEY);
				param.append("&Time=");
				param.append(Time);
				param.append("&Checkkey=");
				param.append(CHECK_KEY);
				param.append("&OrderId=");
				param.append(entity.get("OrderId"));
				String q = DeEnCode.encrypt(param.toString(), DES_KEY);
				q=URLEncoder.encode(q,"GBK");
				String a = (param.toString()+MD5_KEY+Time+KEY);
				String s = MD5Encoder.encode(a);
				StringBuffer params = new StringBuffer();
				params.append("q=").append(q);
				params.append("&s=").append(s);
				// 调用接口
				String result = GameHttpUtil.sendPost(API_URL+"?", params.toString());
				System.out.println("调用结果："+result);
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() , result );
				
				if(result.indexOf("version") > -1 && result.indexOf("xml") > -1) {//正常调用并返回xml格式数据
					
					Map<String, String> object = XmlUtil.getQueryParams(result);
					
					if(object.get("ErrorMsgId").equals("0")) {
						
						if(object.get("isExist").equals("true")) {
							return Enum_MSG.成功.message(object.get("ErrorMsg"));//
						} else {
							return Enum_MSG.失败.message("订单不存在");//
						}
						
					} else {
						return Enum_MSG.失败.message(object.get("ErrorMsgId"), object.get("ErrorMsg"));
					}
					
				} else {
					return result;//出现异常
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
	 * @param entity 参数列表 Username,CurrencyType,deviceType,url
	 * Username 用户名
	 * CurrencyType 币种
	 * url = 需要提交地址参数
	 * 
	 * lobby=每个代理商的LOGO标志
	 * deviceType=设备标志
	 * 
	 * GameCode=电子游戏代码，如果是DZ时，则必须要传递具体的GameCode
	 * @return 返回结果 登录地址
	 */
	@Override
	public Object login(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() );
		try{
			
//			<ErrorMsgId>0</ErrorMsgId>
//			  <ErrorMsg>Success</ErrorMsg>
//			  <Token>258287FD08BF062528E3F9E36B4F26BC</Token>
//			  <DisplayName>hylgfsd90fudsf8dx2x@xs</DisplayName>
			
			
//			游戏类型(GameType)
//			bac  百家乐 
//			dtx  龙虎 
//			sicbo  骰宝 
//			ftan  番摊 
//			rot  轮盘 
//			slot  电子游艺 
//			lottery  48 彩/48彩其他玩法 
//			minigame  小游戏 
//			
			if(MapUtil.isNulls(entity, "Username,deviceType,playtype")){
				
				String deviceType = String.valueOf(entity.get("deviceType"));
				if( !deviceType.equals("0") && !deviceType.equals("1")) {
					return Enum_MSG.参数错误.message("SA游戏目前只能接受PC端与手机H5端的请求，即设备类型为0和1");
				}
				String playtype = String.valueOf(entity.get("playtype"));
				if( !playtype.equals("DZ") && !playtype.equals("SX")) {
					return Enum_MSG.参数错误.message("SA游戏目前可以接受SX和DZ大类游戏");
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String Time = sdf.format(new Date());
				
				if(playtype.equals("SX")) {
					
					StringBuilder param = new StringBuilder();
					param.append("method=LoginRequest");
					param.append("&Key=");
					param.append(KEY);
					param.append("&Time=");
					param.append(Time);
					param.append("&Checkkey=");
					param.append(CHECK_KEY);
					param.append("&Username=");
					param.append(entity.get("Username"));
					param.append("&CurrencyType=");
					param.append(entity.get("CurrencyType") == null ? "CNY" : entity.get("CurrencyType"));
					String q = DeEnCode.encrypt(param.toString(), DES_KEY);
					q=URLEncoder.encode(q,"GBK");
					String a = (param.toString()+MD5_KEY+Time+KEY);
					String s = MD5Encoder.encode(a);
					StringBuffer params = new StringBuffer();
					params.append("q=").append(q);
					params.append("&s=").append(s);
					// 调用接口
					String result = GameHttpUtil.sendPost(API_URL+"?", params.toString());

					System.out.println("调用结果："+result);
					BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() , result );
					
					if(result.indexOf("version") > -1 && result.indexOf("xml") > -1) {//正常调用并返回xml格式数据
						
						Map<String, String> object = XmlUtil.getQueryParams(result);
						
						if(object.get("ErrorMsgId").equals("0")) {
							
							
							StringBuilder url = new StringBuilder();
							String apiroot = this.GAME_API_URL;
							
							url.append(apiroot);//API路径
							url.append("/sagame/index?");
							url.append("username=").append(entity.get("Username")).append("&");
							url.append("token=").append(object.get("Token")).append("&");
							url.append("lobby=").append(this.LOBBY_CODE).append("&");
							url.append("url=").append(URLEncoder.encode(this.LOGIN_URL,"UTF-8")).append("&");//对url进行code
							url.append("lang=").append("zh_CN").append("&");
							
							if( deviceType.equals("0")) {//PC
								//
							} else {//H5
								url.append("mobile=").append("true").append("&");
							}
							
							return Enum_MSG.成功.message(url.toString());
							
							
						} else {
							return Enum_MSG.失败.message(object.get("ErrorMsgId"), object.get("ErrorMsg"));
						}
						
					} else {
						return result;//出现异常
					}
					
				} else {//DZ
					
//					电子游艺 ID/GAME CODE ­游戏对应表
//					EG­SLOT­S001  大闹天宫  1024 x 768 ===============暂时不能用
//					EG­SLOT­S002  嫦娥奔月  1067 x 600 ===============
//					EG­SLOT­012  阿兹特克  1024 x 768 ==============
//					EG­SLOT­014  水晶海  1024 x 768 =================
//					EG­SLOT­020  对方快车  1024 X 768 ================
//					EG­SLOT­051  发大财  1024 x 768 ================
//					EG­SLOT­053  旺财  1024 x 768 =======================
//					EG­SLOT­A001  过大年  1067 X 600 
//					EG­SLOT­A002  三星报囍  1067 X 600 
//					EG­SLOT­A005  梦幻女神  1067 X 600 
//					EG­SLOT­A012  趣怪丧尸  1067 X 600 
//					EG­MINI­B001  小游戏 发发发  不适用
					
					
					StringBuilder param = new StringBuilder();
					param.append("method=EGameLoginRequest");
					param.append("&Key=");
					param.append(KEY);
					param.append("&Time=");
					param.append(Time);
					param.append("&Checkkey=");
					param.append(CHECK_KEY);
					param.append("&Username=");
					param.append(entity.get("Username"));
					param.append("&GameCode=");
					param.append(entity.get("GameCode"));//
					param.append("&CurrencyType=");
					param.append(entity.get("CurrencyType") == null ? "CNY" : entity.get("CurrencyType"));
					String q = DeEnCode.encrypt(param.toString(), DES_KEY);
					q=URLEncoder.encode(q,"GBK");
					String a = (param.toString()+MD5_KEY+Time+KEY);
					String s = MD5Encoder.encode(a);
					StringBuffer params = new StringBuffer();
					params.append("q=").append(q);
					params.append("&s=").append(s);
					// 调用接口
					String result = GameHttpUtil.sendPost(API_URL+"?", params.toString());

					System.out.println("调用结果："+result);
					BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() , result );
					
					if(result.indexOf("version") > -1 && result.indexOf("xml") > -1) {//正常调用并返回xml格式数据
						
						Map<String, String> object = XmlUtil.getQueryParams(result);
						
						if(object.get("ErrorMsgId").equals("0")) {
							
							
							//
//							anguage=0  简体中文 
//							language=1  英文 
//							language=2  繁体中文 
//							language=4  日文 (准备中) 
//							language=5  泰文 (准备中) 
//							language=6  越南文 (准备中) 
//							language=7  印尼文 (准备中) 
									
							String Token = object.get("Token");
							String GameURL = object.get("GameURL");
							String DisplayName = object.get("DisplayName");
							String url = "GameURL?token=Token&name=DisplayName&language=2";
							url = url.replaceAll("GameURL", GameURL);
							url = url.replaceAll("Token", Token);
							url = url.replaceAll("DisplayName", DisplayName);
							
							return Enum_MSG.成功.message(url.toString());
							
							
						} else {
							return Enum_MSG.失败.message(object.get("ErrorMsgId"), object.get("ErrorMsg"));
						}
						
					} else {
						return result;//出现异常
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
	 * 用以获取请求用户的状态信息
	 * 
	 * 用户在线/离线状态;
	 * 是否存在未完成下注單;
	 * 下注限额和余额. 
	 * 
	 * @param entity 参数列表
	 * Username 用户名
	 * @return 返回结果 返回true/false表示是否当前在线
	 */
	@Override
	public Object isOnLine(Map<String, Object> entity) {
		try{
			
//			<ErrorMsgId>0</ErrorMsgId>
//			<ErrorMsg>Success</ErrorMsg>
//			<IsSuccess>true</IsSuccess>
//			<Username>hylgfsd90fudsf8dx2x</Username>
//			<Balance>0</Balance>
//			<Online>false</Online>
//			<Betted>false</Betted>
//			<BettedAmount>0</BettedAmount>
			
			if(MapUtil.isNulls(entity, "Username")){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String Time = sdf.format(new Date());
				StringBuilder param = new StringBuilder();
				param.append("method=GetUserStatusDV");
				param.append("&Key=");
				param.append(KEY);
				param.append("&Time=");
				param.append(Time);
				param.append("&Username=");
				param.append(entity.get("Username"));
				String q = DeEnCode.encrypt(param.toString(), DES_KEY);
				q=URLEncoder.encode(q,"GBK");
				String a = (param.toString()+MD5_KEY+Time+KEY);
				String s = MD5Encoder.encode(a);
				StringBuffer params = new StringBuffer();
				params.append("q=").append(q);
				params.append("&s=").append(s);
				// 调用接口
				String result = GameHttpUtil.sendPost(API_URL+"?", params.toString());
				System.out.println("调用结果："+result);

				Map<String, String> object = XmlUtil.getQueryParams(result);
				
				if(object.get("ErrorMsgId").equals("0")) {
					return Enum_MSG.成功.message(object.get("Online"));//返回true/false
				} else {
					return Enum_MSG.失败.message(object.get("ErrorMsgId"), object.get("ErrorMsg"));
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
	}
	
	public static void main(String[] args) {
		SAGameAPI api = new SAGameAPI();
		
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("Username", "hylgfsd90f@#&*_dx2xx");//#&*
		entity.put("CurrencyType", "CNY");
		
//		entity.put("OrderId", "IN"+StringUtils.getCurrenDate()+entity.get("Username"));
//		entity.put("OrderId", "OUT"+StringUtils.getCurrenDate()+entity.get("Username"));
		entity.put("DebitAmount", "1.0");
		entity.put("CreditAmount", "2.0");
		entity.put("OrderId", "IN20161123162856hylgfsd90fudsf8dx2xx");
		
		entity.put("deviceType", "0");
		entity.put("lobby", "2.0");
		entity.put("playtype", "SX");
		entity.put("GameCode", "EG-SLOT-A001");
		entity.put("Username", RandomString.createRandomString(20));
		System.out.println(api.createAccount(entity));//创建用户
//		System.out.println(api.isOnLine(entity));//用户在线状态
		System.out.println(api.getBalance(entity));//用户余额
		System.out.println(api.withdraw(entity));//用户取款
		System.out.println(api.deposit(entity));//用户存款
		
		System.out.println(api.getOrder(entity));//订单查询
		System.out.println(api.login(entity));//登录
		
		
	}

}
