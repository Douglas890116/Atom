package com.hy.pull.common.util.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.hy.pull.common.util.MapUtil;
import com.hy.pull.common.util.game.pt.PTUtils;
import com.maven.config.SystemConstant;
import com.maven.util.RandomString;

import net.sf.json.JSONObject;

/**
 * PT游戏接口
 * @author temdy
 */
public class PTGameAPI implements BaseInterface {
	
	private String API_URL = "https://kioskpublicapi.redhorse88.com";//公用
	private String FILE_URL = "";//公用
	private String KEY_STORE = "";//公用
	
	//测试
	private String FILE_URL_TEST = "C:/p12/vbet.1114721.p12";//公用
	private String KEY_STORE_TEST = "iQ3xuZrS";//公用
	//正式
	private String FILE_URL_RELEASE = "/data/p12/vbet.1114721.p12";//公用
	private String KEY_STORE_RELEASE = "iQ3xuZrS";//公用
	
	private String ENTITY_KEY = "89dc8ff432ed6faaae48838443dc90f069c9917c1ac4338cf118939096b5fc963606df58f94322d6fbada2bc07c05ef8add46d5962eab64b8efacfb280c3a2ec";//私有
	private String adminname = "VBETCNYHYL";//私有
	private String kioskname = "VBETCNYHYL";//私有
	private boolean IS_RELEASE = true;
	
	private String GAME_API_URL = "http://api.hyzonghe.net";//游戏使用的域名。代替api.hyzonghe.net
	
	
	
	/**
	 * 默认构造函数
	 * 
	 * 测试时可以使用，不用传递秘钥进来
	 */
	@Deprecated
	public PTGameAPI(){
		FILE_URL = FILE_URL_TEST;
		KEY_STORE = KEY_STORE_TEST;
	}
	
	/**
	 * 构建函数，初始化参数
	 * @param ENTITY_KEY 密钥ENTITY
	 */
	public PTGameAPI(String ENTITY_KEY, String adminname, String kioskname, boolean IS_RELEASE,String GAME_API_URL){
		this.ENTITY_KEY = ENTITY_KEY;
		this.adminname = adminname;
		this.kioskname = kioskname;
		this.IS_RELEASE = IS_RELEASE;
		this.GAME_API_URL = GAME_API_URL;
		
		/*********读取配置信息************/
		if(IS_RELEASE){
			FILE_URL = FILE_URL_RELEASE;
			KEY_STORE = KEY_STORE_RELEASE;
		} else {
			FILE_URL = FILE_URL_TEST;
			KEY_STORE = KEY_STORE_TEST;
		}
		
	}
	
	/**
	 * 构建函数，初始化参数
	 * @param API_URL API接口URL
	 * @param FILE_URL 证书URL
	 * @param KEY_STORE 密钥STORE
	 * @param ENTITY_KEY 密钥ENTITY
	 */
	@Deprecated
	public PTGameAPI(String API_URL,String FILE_URL,String KEY_STORE,String ENTITY_KEY){
		this.API_URL = API_URL;
		this.FILE_URL = FILE_URL;
		this.KEY_STORE = KEY_STORE;
		this.ENTITY_KEY = ENTITY_KEY;
	}

	/**
	 * 创建用户接口
	 * @param entity 参数列表
	 * playername 玩家注册名
	 * password 玩家密码 8位数
	 * 
	 * 
	 * 1.玩家注册必须加前缀，例如：HYAZHANG，PT玩家名称为大写（每个代理商都会分配一个前缀如HYL，同时注册时的用户名要求也是大写。整个用户名就是大写的）；
	   2.玩家名称最多30个字符，含前缀；
	   3.调用结果返回json示例：{"result":{"result":"New player has been created","playername":"HYLGFSD90FUDSF8DX2","password":"12345678","executiontime":{"webapi":"159.636 ms","skywind":"0.453 ms"}}}
	   4.错误：{"error":"Player password is too short","errorcode":50} 
	 * @return 返回结果
	 */
	@Override
	public Object createAccount(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() );
		try{
			if(MapUtil.isNulls(entity, "playername,password")){
				
				if( String.valueOf(entity.get("playername")).length() > 20 ) {
					return Enum_MSG.参数错误.message("用户长度不能大于20位");
				}
				if( String.valueOf(entity.get("password")).length() > 20 ) {
					return Enum_MSG.参数错误.message("密码长度不能大于20位");
				}
				
				StringBuilder url = new StringBuilder();
				url.append(API_URL);
				url.append("/player/create");
				url.append("/playername/");
				url.append(entity.get("playername"));
				url.append("/password/");
				url.append(entity.get("password"));
				url.append("/adminname/");
				url.append(this.adminname);
				url.append("/kioskname/");
				url.append(this.kioskname);
				url.append("/custom03/");
				url.append("11");//特别注意：固定值11
				url.append("/TrackingId/");//此参数跟客户端登录定制有关
				url.append(this.kioskname);//使用kiosk name赋值
				
				JSONObject object =  JSONObject.fromObject(PTUtils.getResultByURL(url.toString(),FILE_URL, KEY_STORE, ENTITY_KEY));
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "createAccount", entity.toString() , object.toString() );
				
				//存在该节点时，表示成功
				if(object.has("result")) {
					//不返回相关数据。
					return Enum_MSG.成功.message(object);
				} else {
					if(object.getString("errorcode").equals("19")) {
						return Enum_MSG.账号已存在.message(object.getString("error"));
					}
					return Enum_MSG.失败.message(object.getString("errorcode"), object.getString("error"));
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
	 * playername 玩家账号
	 * 
	 * 余额字段=BALANCE
	 * 返回json示例：{"result":{"ADDRESS":"NA","ADVERTISER":"default74","BALANCE":"0","BANNERID":"-","BIRTHDATE":"1950-01-01","BONUSBALANCE":"0","CASINONAME":"greatfortune88","CASINONICKNAME":false,"CELLPHONE":null,"CITY":"NA","CLIENTSKIN":"greatfortune88","CLIENTTYPE":"casino","CODE":"29445700","COMMENTS":"Created through Public API. ","COMPPOINTS":"0","COUNTRYCODE":"CN","CREFERER":[],"CURRENCY":"CNY","CURRENCYCODE":"CNY","CURRENTBET":"0","CURRENTBONUSBET":"0","CUSTOM01":"VBET","CUSTOM03":"11","EMAIL":"hylgfsd90fudsf8dx2@vbetcnyhyl.com","FAX":"11111","FIRSTNAME":"NA","FROZEN":"0","INTERNAL":"0","ISINGAME":false,"KIOSKADMINNAME":"VBETCNYHYL","KIOSKNAME":"VBETCNYHYL","LANGUAGECODE":"EN","LASTDEPOSITDATE":null,"LASTLOGINDATE":false,"LASTNAME":"NA","LASTWITHDRAWALDATE":null,"LOCKEDMINUTES":0,"MAXBALANCE":"0","NETLOSS":null,"NETWORKNICKNAMES":[],"NOBONUS":"0","OCCUPATION":null,"PASSWORD":"1031$qBFKeAEOHH2Uv7QLwHABVmKIIrlRz9Pv\/iYzYuFcQoDp26ExJtRWhMcVVGb5DHgZ$Gd3Nwmo6UHGRVHGCrwQGYZpTB48qYC6Zbp\/0fXMwuIc=","PENDINGBONUSBALANCE":"0","PHONE":"11111","PLAYERNAME":"HYLGFSD90FUDSF8DX2","RC_BALANCE":0,"RESERVEDBALANCE":"0","SERIAL":"74W20CARDUSER1479801613","SEX":null,"SIGNUPCLIENTPLATFORM":"download","SIGNUPDATE":"2016-11-22 16:00:13","STATE":"NA","SUSPENDED":0,"TITLE":null,"TOTALCOMPPOINTS":"0","TOTALDEPOSITS":null,"TOTALWITHDRAWALS":null,"TRACKINGID":false,"VIPLEVEL":"1","WANTMAIL":1,"WORKPHONE":null,"ZIP":"NA"}}
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object getBalance(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() );
		try{
			
//			错误代码清单：
//			10	Playername not specified
//			11	Casino name not specified
//			41	Player does not exist
//			49	Player is not allowed for the Entity
//			71	Could not load player data. Error: 'UMS API return error on access request attempt'
//			72	Could not load player data. Error: 'Service error accessing UMS API'
//			73	Could not load player data from database. Error: 'Database error occured, please contact support'. Please, try again later

//			10	Player name not specified
//			11	Casino name not specified
//			41	Player does not exist
//			49	Player is not allowed for the Entity
//			71	Could not load player data. Error: 'UMS API return error on access request attempt'
//			72	Could not load player data. Error: 'Service error accessing UMS API'
//			73	Could not load player data from database. Error: 'Database error occured, please contact support'. Please, try again later

			
			if(MapUtil.isNulls(entity, "playername")){		
				StringBuilder url = new StringBuilder();
				url.append(API_URL);
				url.append("/player/info");
				url.append("/playername/");
				url.append(entity.get("playername"));
				
				JSONObject object =  JSONObject.fromObject(PTUtils.getResultByURL(url.toString(),FILE_URL, KEY_STORE, ENTITY_KEY));
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getBalance", entity.toString() , object.toString() );
				
				//存在该节点时，表示成功
				if(object.has("result")) {
					//不返回相关数据。
					return Enum_MSG.成功.message(object.getJSONObject("result").getString("BALANCE"));
				} else {
					return Enum_MSG.失败.message(object.getString("errorcode"), object.getString("error"));
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
	 * 返回json示例：{"result":{"amount":"0.02","currentplayerbalance":"1.12","executiontime":"206.003 ms","externaltransactionid":"1479803522117","instantcashtype":null,"kiosktransactionid":"538963458","kiosktransactiontime":"2016-11-22 16:32:06","ptinternaltransactionid":"84940361890","result":"Withdraw OK"}}
	 * 
	 * 可能使用到的字段：amount=本次金额、currentplayerbalance=当前余额、result=本次操作结果
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object withdraw(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() );
		try{
			
//			错误代码清单：
//			98	InstantCash amount 'amount' is greater than balance（表示用户账户余额不足）
//			10	Player name not specified
//			11	Casino name not specified
//			13	Amount not specified
//			14	Admin not specified
//			15	Kiosk not set
//			18	The possible values of the amount can be only numbers
//			41	Player does not exist
//			43	Player does not belongs to kiosk
//			44	Player is frozen
//			47	Action is not allowed
//			48	Kiosk admin does not belong to TLE
//			49	Player is not allowed for the Entity
//			71	Could not load player data. Error: 'UMS API return error on access request attempt'
//			72	Could not load player data. Error: 'Service error accessing UMS API'
//			73	Could not load player data from database. Error: 'Database error occured, please contact support'. Please, try again later
//			75	Attribute 'attribute name' does not exist. Please enter the correct request
//			99	Player is in game
//			101	Bonus declined on withdraw
//			102	Cannot make cashout. No permissions.
//			103	Amount is below minimum allowed cashout amount
//			104	Amount is over current player balance
//			105	Withdraw operation is not allowed
//			106	Kioskadmin deposit balance update failed.
//			108	Admin is frozen
//			109	Admin internal state mismatch with player internal state
//			133	Kiosk admin does not found
//			301	API error.
//			302	External transaction id already exists

			
			if(MapUtil.isNulls(entity, "playername,amount,externaltranid")){		
				
				if( Double.valueOf(entity.get("amount").toString()) < 1.0 ) {
					return Enum_MSG.参数错误.message("取款金额不能小于1元");
				}
				
				StringBuilder url = new StringBuilder();
				url.append(API_URL);
				url.append("/player/withdraw");
				url.append("/playername/");
				url.append(entity.get("playername"));
				url.append("/amount/");
				url.append(entity.get("amount"));//float
				url.append("/isForce/");
				url.append("1");//一定要强制下分
				url.append("/adminname/");
				url.append(this.adminname);
				url.append("/externaltranid/");
				url.append(entity.get("externaltranid"));//string, AlphaNum(0-200) 。交易ID，可以不用。但强制要求必须填写，校验时可以用该单号来查询转账结果。可以是字符、数字、字母
				
				JSONObject object =  JSONObject.fromObject(PTUtils.getResultByURL(url.toString(),FILE_URL, KEY_STORE, ENTITY_KEY));
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "withdraw", entity.toString() , object.toString() );
				//存在该节点时，表示成功
				if(object.has("result")) {
					//不返回相关数据。
					return Enum_MSG.成功.message(object);
				} else {
					return Enum_MSG.失败.message(object.getString("errorcode"), object.getString("error"));
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
	 * @param entity 参数列表
	 * playername 玩家
	 * amount 分数
	 * externaltranid 交易单号
	 * 
	 * 返回json示例：{"result":{"amount":"0.02","currentplayerbalance":"1.14","executiontime":"551.757 ms","externaltransactionid":"1479803445736","instantcashtype":null,"kiosktransactionid":"538962533","kiosktransactiontime":"2016-11-22 16:30:52","ptinternaltransactionid":"84940152480","result":"Deposit OK"}}
	 * 
	 * 可能使用到的字段：amount=本次金额、currentplayerbalance=当前余额、result=本次操作结果
	 * @return 返回结果
	 */
	@Override
	public Object deposit(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() );
		try{
			
//			错误代码清单：
//			10	Player name not specified
//			11	Casino name not specified
//			13	Amount not specified
//			14	Admin not specified
//			18	The possible values of the amount can be only numbers
//			41	Player does not exist
//			43	Player does not belong to kiosk
//			44	Player is frozen
//			45	Language code is restricted for TLE
//			46	Player has waiting withdrawal requests
//			47	Action is not allowed
//			48	Kiosk admin does not belong to TLE
//			49	Player is not allowed for the Entity
//			71	Could not load player data. Error: 'UMS API return error on access request attempt'
//			72	Could not load player data. Error: 'Service error accessing UMS API'
//			73	Could not load player data from database. Error: 'Database error occured, please contact support'. Please, try again later
//			75	Attribute 'attribute name' does not exist. Please enter the correct request
//			92	Deposit not allowed
//			93	Cannot make deposit. Amount is less than minimum deposit amount for this player.
//			94	Cannot make deposit. Amount exeeds maximum deposit limit for this player.
//			95	Casino not open
//			96	Cannot make deposit. Your deposit limit for period is exceeded
//			97	Kioskadmin deposit balance insufficient.
//			106	Kioskadmin deposit balance update failed.
//			108	Admin is frozen
//			109	Admin internal state mismatch with player internal state
//			133	Kiosk admin does not found
//			148	Koisk admin code is not valid
//			170	Deposit error
//			301	API error.
//			302	External transaction id already exists

			if(MapUtil.isNulls(entity, "playername,amount,externaltranid")){		
				StringBuilder url = new StringBuilder();
				url.append(API_URL);
				url.append("/player/deposit");
				url.append("/playername/");
				url.append(entity.get("playername"));
				url.append("/amount/");
				url.append(entity.get("amount"));//float
				url.append("/adminname/");
				url.append(this.adminname);
				url.append("/externaltranid/");
				url.append(entity.get("externaltranid"));//string, AlphaNum(0-200) 。交易ID，可以不用。但强制要求必须填写，校验时可以用该单号来查询转账结果。可以是字符、数字、字母
				
				JSONObject object =  JSONObject.fromObject(PTUtils.getResultByURL(url.toString(),FILE_URL, KEY_STORE, ENTITY_KEY));
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "deposit", entity.toString() , object.toString() );
				//存在该节点时，表示成功
				if(object.has("result")) {
					//不返回相关数据。
					return Enum_MSG.成功.message(object);
				} else {
					return Enum_MSG.失败.message(object.getString("errorcode"), object.getString("error"));
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
	 * 更新信息接口（更新密码）
	 * @param entity 参数列表
	 * playername 玩家
	 * @return 返回结果
	 */
	@Override
	public Object updateInfo(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString() );
		try{
			if(MapUtil.isNulls(entity, "playername,password,newpassword")){		
				StringBuilder url = new StringBuilder();
				url.append(API_URL);
				url.append("/player/update");
				url.append("/playername/");
				url.append(entity.get("playername"));
				url.append("/password/");
				url.append(entity.get("newpassword"));
				
				
				JSONObject object =  JSONObject.fromObject(PTUtils.getResultByURL(url.toString(),FILE_URL, KEY_STORE, ENTITY_KEY));
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "updateInfo", entity.toString() , object.toString() );
				//存在该节点时，表示成功
				if(object.has("result")) {
					//不返回相关数据。
					return Enum_MSG.成功.message(object);
				} else {
					return Enum_MSG.失败.message(object.getString("errorcode"), object.getString("error"));
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
	 * 获取订单接口
	 * @param entity 参数列表
	 * 
	 * externaltransactionid 交易单号
	 * 
	 * 返回json示例：{"result":{"status":"approved","type":"withdraw","amount":".02","currentplayerbalance":"1.12","kiosktransactiontime":"2016-11-22 16:32:06","kiosktransactionid":"538963458","ptinternaltransactionid":"84940361890","ip":"180.232.108.150","username":"HYLGFSD90FUDSF8DX2","admin":"VBETCNYHYL","externaltransactionid":"1479803522117","executiontime":"22.766 ms"}}
	 * 
	 * 可能使用到的字段：status=approved审核完毕的、type=存款deposit或取款withdraw、currentplayerbalance=当前余额、amount=本单号涉及的业务
	 * 
	 * @return 返回结果
	 */
	@Override
	public Object getOrder(Map<String, Object> entity) {
		BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() );
		try{
			
//			错误代码清单：
//			11	Casino name not specified
//			16	External transaction ID not specified
//			75	Attribute 'attribute name' does not exist. Please enter the correct request

			
			if(MapUtil.isNulls(entity, "externaltransactionid")){		
				StringBuilder url = new StringBuilder();
				url.append(API_URL);
				url.append("/player/checktransaction");
				url.append("/externaltransactionid/");//交易单号
				url.append(entity.get("externaltransactionid"));
				
				JSONObject object =  JSONObject.fromObject(PTUtils.getResultByURL(url.toString(),FILE_URL, KEY_STORE, ENTITY_KEY));
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "getOrder", entity.toString() , object.toString() );
				
				//存在该节点时，表示成功
				if(object.has("result")) {
					
					if(object.getJSONObject("result").getString("status").equals("approved")) {
						return Enum_MSG.成功.message(object);
					} else {
						return Enum_MSG.失败.message("当前状态："+object.getJSONObject("result").getString("status"));
					}
					
				} else {
					return Enum_MSG.失败.message(object.getString("errorcode"), object.getString("error"));
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
		
//		游戏链接/flash URL		
//		http://cache.download.banner.greatfortune88.com/casinoclient.html
//		范例：带参数游戏链接
//		http://cache.download.banner.greatfortune88.com/casinoclient.html?game=gtsje&language=zh-cn
//		说明	
//		game=gamecode language=zh-cn
		
		
//		以下为真人:
//		Ｇame Name	Ｇame Name(Chinese ) 	Game Type	Game Code
//		Baccarat Live 7 Seat	真人百家乐（7座）	Live Games	7bal
//		Baccarat Live VIP　　　　　　　　	VIP百家乐(live)	Live Games	vbal
//		Baccarat Mini　　　	迷你百家乐(live)	Live Games	bal
//		Baccarat　Progressive（ Live）	实况累积百家乐	Live Games	plba
//		Blackjack (Multi-Player)　　　　	21点(多手）	Live Games/Multi-player Table & Card Games	mpbj
//		Blackjack Live　　　	真人21点	Live Games	bjl
//		Blackjack Surrender (Multi-Player)　　　	投降２１点(多手）	Live Games/Multi-player Table & Card Games	mpbjsd
//		Blackjack Surrender Multihand 3   	投降21点（3手）	Live Games/Multi-player Table & Card Games	bjsd_mh3
//		Blackjack Surrender Multihand 5   	投降21点（5手）	Live Games/Multi-player Table & Card Games	bjsd_mh5
//		Casino Hold'em（live)　　　	实况Casino Hold'Em	Live Games	chel
//		dual Roulette	双轮盘赌	Live Games	dual_rol
//		Dual Roulette (VF)          	双赌	Live Games	gts384
//		European Roulette (Multi-Player)　　　	欧式轮盘(多手）EU Roulette1	Live Games/Multi-player Table & Card Games	mpro
//		Exclusive Roulette (Diagonal)　　　	實況独家轮盘赌（对角线）	Live Games	rodl
//		French Roulette Live　　　	实况法式轮盘赌	Live Games	rofl
//		Japanese Solo Mahjong  	日本单人麻将	Live Games	smj
//		Japanese Solo Mahjong Pro  	日本单人麻将专家	Live Games	smpj
//		Progressive Baccarat	累积百家乐	Live Games	pba
//		Roulette Live	實況轮盘（分类）	Live Games	rol
//		Roulette Live Mini	迷你轮盘（live)	Live Games	mrol
//		Sic Bo(Live)	实况骰宝（分在桌牌）	Live Games	sbl
//		Slingshot Roulette	弹弓轮盘豪赌	Live Games	rosz
//		Unlimited Blackjack Live　　　	无限制21点（live)	Live Games	ubjl
//		WMF Solo Mahjong  　	WMF 单人麻将	Live Games	smw
//		WMF Solo Mahjong Pro   	WMF 单人麻将专业版	Live Games	smpw

		
//		以下为手机游戏：
//		Ｇame Name	Ｇame Name(Chinese ) 	Game Type	Game Code
//		Club Roulette (TV)	俱乐部轮盘－手机版	Mobile	rotv1
//		Mobile Blackjack   	21点 - 手机版	Mobile	mobbj
//		Mobile Blackjack Ace   	王牌21点－手机版	Mobile	mmbj_ace
//		Mobile Desert Treasure	沙漠的宝藏-手机版	Mobile	mobdt
//		Mobile European Roulette   	欧式轮盘－手机版	Mobile	mrn_eu
//		Mobile Premium Roulette   	奖金轮盘－手机版	Mobile	mobro
//		Mobile Roulette Native   	轮盘－手机版	Mobile	mrn
//		Mobile Royal Roulette   	皇家轮盘－手机版	Mobile	mrn_rr
//		Pink Panther Scratch (mobile)   	粉红豹刮刮乐-手机版	Mobile	mppsc
//		Rocky Scratch (mobile)   	洛基刮刮乐-手机版	Mobile	mrbsc

//		其他为电子游戏：
		
		try{
			
			if(MapUtil.isNulls(entity, "playername,password,deviceType,playtype")){		
				
				String deviceType = String.valueOf(entity.get("deviceType"));
				if( !deviceType.equals("0") && !deviceType.equals("1")) {
					return Enum_MSG.参数错误.message("PT游戏目前只能接受PC端与手机H5端的请求，即设备类型为0和1");
				}
				
				String playtype = String.valueOf(entity.get("playtype"));
				if( !playtype.equals("DZ") && !playtype.equals("SX")) {
					return Enum_MSG.参数错误.message("PT游戏目前可以接受SX和DZ大类游戏");
				}
				
				StringBuilder url = new StringBuilder();
				url.append(this.GAME_API_URL);//API路径
				
				if( deviceType.equals("0")) {//PC
					url.append("/ptgame/index?");
				} else {//H5
					url.append("/ptgame/loginh5?");
				}
				url.append("username=").append(entity.get("playername")).append("&");
				url.append("password=").append(entity.get("password")).append("&");
				url.append("homeurl=").append(entity.get("homeurl"));
					
				
//				Blackjack Pro    21点专业版 Table & Card Games psdbj  Blackjack Pro (psdbj)
				
				
				//如果是真人，则带一个默认的游戏类型参数进去，Baccarat Live 7 Seat (7bal) 游戏
				if( playtype.equals("SX")) {
					url.append("&gamecode=").append("7bal");
				}
				//http://api.hyzonghe.net/ptgame/loginh5?username=hyixiaomao&password=3pOnctRa
				
				BaseInterfaceLogUtil.addAPILog(this.getClass().getSimpleName(), "login", entity.toString() , url.toString() );
				
				return Enum_MSG.成功.message(url.toString());
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
	 * { "result": { "result": 0 } }
	 * 
	 * @return 返回结果 正确：0为否，1为是
	 */
	@Override
	public Object isOnLine(Map<String, Object> entity) {
		try{
			if(MapUtil.isNulls(entity, "playername")){		
				StringBuilder url = new StringBuilder();
				url.append(API_URL);
				url.append("/player/online");
				url.append("/playername/");
				url.append(entity.get("playername"));
				
				JSONObject object =  JSONObject.fromObject(PTUtils.getResultByURL(url.toString(),FILE_URL, KEY_STORE, ENTITY_KEY));
				//存在该节点时，表示成功
				if(object.has("result")) {
					
					if(object.getJSONObject("result").getString("result").equals("0")) {
						return Enum_MSG.成功.message(object);
					} else {
						return Enum_MSG.失败.message("当前状态："+object.getString("status"));
					}
					
				} else {
					return Enum_MSG.失败.message(object.getString("errorcode"), object.getString("error"));
				}
				
			} else {
				return Enum_MSG.参数错误.message(Enum_MSG.缺少必须的参数.desc);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			
			return Enum_MSG.接口调用错误.message(ex.getMessage());
		}
	}
	
	
	public static void main(String[] a){
		PTGameAPI pt = new PTGameAPI();
		Map<String, Object> entity = new HashMap<String, Object>();
		entity.put("playername", "HYLi1Nf1b@_*23DW0LUQ");//#&*
		entity.put("password", "12345678");
		entity.put("newpassword", "1234567812");
		entity.put("adminname", "VBETCNYHYL");
		entity.put("kioskname", "VBETCNYHYL");
		entity.put("amount", "1.12");
		entity.put("playtype", "SX");
		entity.put("deviceType", "0");
//		entity.put("externaltransactionid", "1479803522117");
		entity.put("externaltranid", UUID.randomUUID().toString());
//		System.out.println(pt.createAccount(entity));
		System.out.println(pt.getBalance(entity));//查询用户信息
		System.out.println(pt.withdraw(entity));//取款
		System.out.println(pt.deposit(entity));//存款
		System.out.println(pt.getOrder(entity));//订单查询
//		System.out.println(pt.isOnLine(entity));//是否在线
		
//		System.out.println(pt.login(entity));//
//		System.out.println(pt.updateInfo(entity));//
		
	}
}
