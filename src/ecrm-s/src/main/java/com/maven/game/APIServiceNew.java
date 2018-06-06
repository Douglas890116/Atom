package com.maven.game;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hy.pull.common.util.MoneyHelper;
import com.hy.pull.common.util.api.GameAPI;
import com.maven.cache.SystemCache;
import com.maven.constant.Enum_MSG;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EmployeeApiAccoutPasswordJob;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeCapitalAccount;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.EnterpriseOperatingBrandGame;
import com.maven.entity.Game;
import com.maven.entity.GameApiInput;
import com.maven.entity.GamePlatformPrefix;
import com.maven.entity.UserMoneyInAndOut;
import com.maven.entity.UserMoneyInAndOut.Enum_opreatetype;
import com.maven.entity.UserMoneyInAndOut.Enum_updatecapital;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.game.enums.GameEnum;
import com.maven.game.enums.GameEnum.Enum_GameType;
import com.maven.logger.TLogger;
import com.maven.service.EmployeeApiAccoutPasswordJobService;
import com.maven.service.EmployeeApiAccoutService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseService;
import com.maven.service.GamePlatformPrefixService;
import com.maven.service.GameService;
import com.maven.service.UserLogsService;
import com.maven.service.UserMoneyInAndOutService;
import com.maven.util.JSONUnit;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;
import com.maven.util.StringUtils;
import com.maven.utility.SpringContextHolder;

import net.sf.json.JSONObject;

/**
 * 本接口类，成功返回代码0.
 * 
 * 原APIService的返回1
 * @author Administrator
 *
 */
public class APIServiceNew {
	
	
	
	
	private HYAPI hYGameAPI;
	private GameApiInput GApiInput;
	private EnterpriseEmployeeService employeeService;
	private EnterpriseEmployeeCapitalAccountService capitalAccountService;
	private UserMoneyInAndOutService moneyInAndOutService;
	private GamePlatformPrefixService gamePlatformPrefixService;
	private EmployeeApiAccoutService employeeApiAccoutService;
	private EmployeeApiAccoutPasswordJobService employeeApiAccoutPasswordJobService;
	private GameService gameService;
	private EnterpriseService enterpriseService;
	
	public APIServiceNew(String enterprisecode){
		/***/
		this.GApiInput = SystemCache.getInstance().getGameApiInputMap(enterprisecode);
		if(GApiInput==null) {
			throw new ArgumentValidationException("未加载到企业API许可信息");
		}
		hYGameAPI = new HYAPI(GApiInput);
		employeeService = SpringContextHolder.getBean("enterpriseEmployeeServiceImpl");
		
		gameService = SpringContextHolder.getBean("gameServiceImpl");
		capitalAccountService = SpringContextHolder.getBean("enterpriseEmployeeCapitalAccountServiceImpl");
		moneyInAndOutService  = SpringContextHolder.getBean("userMoneyInAndOutServiceImpl");
		gamePlatformPrefixService  = SpringContextHolder.getBean("gamePlatformPrefixServiceImpl");
		employeeApiAccoutService  = SpringContextHolder.getBean("employeeApiAccoutServiceImpl");
		employeeApiAccoutPasswordJobService  = SpringContextHolder.getBean("employeeApiAccoutPasswordJobServiceImpl");
		enterpriseService  = SpringContextHolder.getBean("enterpriseServiceImpl");
	}
	
	
	
	/**
	 * 用户登录
	 * @param gametype 游戏平台
	 * @param playtype	游戏大类，如DZ
	 * @param employeecode 玩家编码
	 * @param games 游戏列表
	 * @param devicetype	设备类型
	 * @param gamecode	游戏子代码
	 * @return
	 * @throws Exception
	 */
	public String play(String gametype,String playtype,String employeecode,List<Game> listGame,String devicetype,String gamecode, Map<String, String> urlMap) throws Exception{
		EmployeeApiAccout ea = SystemCache.getInstance().getEmployeeGameAccount(employeecode, gametype);
		if(ea ==null) {
			return Enum_MSG.用户不存在.message(null);
		}
		if(ea.getStatus().equals(EmployeeApiAccout.Enum_status.禁用.value)) {
			return Enum_MSG.用户未启用该游戏.message(null);
		}
		
		/************重要：进入游戏前进行转分************/
		Enterprise enterprise = enterpriseService.selectByPrimaryKey(ea.getEnterprisecode());
		if(enterprise != null && enterprise.getTransfertype().equals(Enterprise.Enum_transfertype.自动转分.value)) {
			this.transfer(gametype,employeecode,listGame);
		}
		
		return GameAPI.login(ea.getGameaccount(), ea.getGamepassword(), gametype, playtype, ea.getEnterprisecode(), devicetype, gamecode, employeecode, null, urlMap).toString();
	}
	
	/**
	 * 游戏试玩
	 * @param gametype
	 * @return
	 * @throws Exception
	 */
	public String tryPlay(String gametype,String playtype,String devicetype) throws Exception{
		String username = RandomString.createRandomString(8);
		String password = RandomString.createRandomString(8);
		//创建API游戏账号
		String info = hYGameAPI.createUser(gametype,username,password, username, username, GameEnum.Enum_usertype.测试.value);
		if(codeParse(info, "1")){
			//进入游戏
			return hYGameAPI.play(gametype,playtype,username, password, username,devicetype);
		}else if(codeParse(info, "1004")){
			return tryPlay(gametype,playtype,devicetype);
		}else if(codeParse(info, "1001")){
			throw new Exception(Enum_MSG.游戏API系统异常.desc);
		}
		return info;
	}
	
	/**
	 * 查询余额
	 * @param gametype 游戏类型
	 * @param employeecode 用户编码
	 * @return
	 * @throws Exception
	 */
	public  String balance(String gametype,String employeecode) throws Exception{
		EmployeeApiAccout ea = SystemCache.getInstance().getEmployeeGameAccount(employeecode, gametype);
		if(ea ==null) {
			return Enum_MSG.用户不存在.message(null);
		}
		
		return GameAPI.getBalance(ea.getGameaccount(), ea.getGamepassword(), gametype, ea.getEnterprisecode()).toString();
		
	}
	/**
	 * 验证游戏开关是否打开
	 * 
	 * 余额查询时跳过关闭的游戏，否则一直出不来余额
	 * 
	 * @param brandcode
	 * @param gameType
	 * @return
	 * @throws Exception
	 */
	private Game isOpenGame(String brandcode, String gameType,List<Game> games ) throws Exception {
		for (Game game : games) {
			if (gameType.equals(game.getGametype())) {
				return game;
			}
		}
		return null;
	}
	
	/**
	 * 获取用户的所有账户余额-ALL多列表

		忽略平台维护中的状态

	 * @param employeecode 用户编码
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> balancesAll2(String employeecode,String brandcode) throws Exception{
		
		Map<String, String> data = new HashMap<String, String>();
		
		//获取接口余额
		Map<String,EmployeeApiAccout>  eapiaccounts = SystemCache.getInstance().getEmployeeAllGameAccount(employeecode);
		
		List<Game> games = gameService.takeBrandGames(brandcode,EnterpriseOperatingBrandGame.Enum_flag.正常.value);
		
		if(eapiaccounts!=null){
			for (EmployeeApiAccout e : eapiaccounts.values()) {
					
				//	已关闭的游戏，不查询余额，否则首页余额一直出不来
				if (isOpenGame(brandcode, e.getGametype(), games) == null) {
					//data.put(e.getGametype(), "查询余额失败：当前游戏维护中");
					//continue;
				}
				
				try {
					JSONObject object = JSONObject.fromObject( GameAPI.getBalance(e.getGameaccount(), e.getGamepassword(), e.getGametype(), e.getEnterprisecode()) );
					if(object.getString("code").equals("0")){
						data.put(e.getGametype(), MoneyHelper.doubleFormat(object.getString("info")));
					} else {
						data.put(e.getGametype(), "查询余额失败："+object.getString("info"));
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					data.put(e.getGametype(), "查询异常："+e2.getMessage());
				}
			}
		}
		//获取账户余额
		EnterpriseEmployeeCapitalAccount ec = capitalAccountService.takeCurrencyAccount(employeecode);
		if(ec==null) {
			data.put("CENTER", "没有资金账户，没有中心钱包");
		} else {
			data.put("CENTER", MoneyHelper.doubleFormat(ec.getBalance().toString()));
		}
		return data;
	}
	
	/**
	 * 获取用户的所有账户余额-ALL多列表
	 * @param employeecode 用户编码
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> balancesAll(String employeecode,String brandcode) throws Exception{
		
		Map<String, String> data = new HashMap<String, String>();
		
		//获取接口余额
		Map<String,EmployeeApiAccout>  eapiaccounts = SystemCache.getInstance().getEmployeeAllGameAccount(employeecode);
		
		List<Game> games = gameService.takeBrandGames(brandcode,EnterpriseOperatingBrandGame.Enum_flag.正常.value);
		
		if(eapiaccounts!=null){
			for (EmployeeApiAccout e : eapiaccounts.values()) {
					
				//	已关闭的游戏，不查询余额，否则首页余额一直出不来
				if (isOpenGame(brandcode, e.getGametype(), games) == null) {
					//return Enum_MSG.逻辑事物异常.message(Enum_MSG.游戏维护中.desc);
					data.put(e.getGametype(), "查询余额失败：当前游戏维护中");
					continue;
				}
				
				try {
					JSONObject object = JSONObject.fromObject( GameAPI.getBalance(e.getGameaccount(), e.getGamepassword(), e.getGametype(), e.getEnterprisecode()) );
					if(object.getString("code").equals("0")){
						data.put(e.getGametype(), MoneyHelper.doubleFormat(object.getString("info")));
					} else {
						data.put(e.getGametype(), "查询余额失败："+object.getString("info"));
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					data.put(e.getGametype(), "查询异常："+e2.getMessage());
				}
			}
		}
		//获取账户余额
		EnterpriseEmployeeCapitalAccount ec = capitalAccountService.takeCurrencyAccount(employeecode);
		if(ec==null) {
			data.put("CENTER", "没有资金账户，没有中心钱包");
		} else {
			data.put("CENTER", MoneyHelper.doubleFormat(ec.getBalance().toString()));
		}
		return data;
	}
	
	/**
	 * 获取用户的所有账户余额
	 * @param employeecode 用户编码
	 * @return
	 * @throws Exception
	 */
	public String balances(String employeecode,String brandcode) throws Exception{
		BigDecimal money = new BigDecimal("0");
		//获取接口余额
		Map<String,EmployeeApiAccout>  eapiaccounts = SystemCache.getInstance().getEmployeeAllGameAccount(employeecode);
		
		List<Game> games = gameService.takeBrandGames(brandcode,EnterpriseOperatingBrandGame.Enum_flag.正常.value);
		
		
		
		if(eapiaccounts!=null){
			for (EmployeeApiAccout e : eapiaccounts.values()) {
				if(e.isExitmoney()){
					
					//	已关闭的游戏，不查询余额，否则首页余额一直出不来
					if (isOpenGame(brandcode, e.getGametype(), games) == null) {
						//return Enum_MSG.逻辑事物异常.message(Enum_MSG.游戏维护中.desc);
						continue;
					}
					
					try {
						JSONObject object = JSONObject.fromObject( GameAPI.getBalance(e.getGameaccount(), e.getGamepassword(), e.getGametype(), e.getEnterprisecode()) );
						if(object.getString("code").equals("0")){
							BigDecimal m = new BigDecimal(object.getString("info"));
							if(m.compareTo(new BigDecimal("0"))>0){
								money = money.add(m);
							}
						}
					} catch (Exception e2) {
						e2.printStackTrace();
						money = money.add(new BigDecimal("0"));
					}
					
				}
			}
		}
		//获取账户余额
		EnterpriseEmployeeCapitalAccount ec = capitalAccountService.takeCurrencyAccount(employeecode);
		if(ec==null) {
			return  Enum_MSG.资金账户不存在.desc;
		}
		money =  money.add(ec.getBalance());
		
		return Enum_MSG.成功.message(money.setScale(2, RoundingMode.HALF_UP).toString());
	}
	
	/**
	 * 用户下分
	 * @param employeecode
	 * @param patchno 本批次号
	 * @return
	 * @throws Exception
	 */
	public void userShimobun(String employeecode, long patchno)throws Exception{
		Map<String,EmployeeApiAccout>  eapiaccounts = SystemCache.getInstance().getEmployeeAllGameAccount(employeecode);
		if(eapiaccounts!=null){
			
			TLogger.getLogger().Error("用户:"+employeecode+"  开始取款前的下分操作,批次号："+patchno);
			
			
			/******************************************************新下分逻辑（对最后一次游戏下分） **************************************************/
			
			/***
			//	不存在时可能是因为服务器重启过。此时查询一次数据库进行确认
			if( !APIServiceUtil.mapLastLoginGame.containsKey(employeecode)) {
				UserMoneyInAndOut temp = moneyInAndOutService.findMaxUpRecord(employeecode);
				if(temp != null) {
					APIServiceUtil.mapLastLoginGame.put(employeecode, temp);
				}
			}
			****/
			
			//	开始处理
//			if( APIServiceUtil.mapLastLoginGame.containsKey(employeecode) ) {
//				String gametypeLast = APIServiceUtil.mapLastLoginGame.get(employeecode).getGametype();
//			}
//			else {
//				TLogger.getLogger().Error("用户:"+employeecode+" 游戏转分逻辑中没有找到该人最后一次上分记录，说明还没进入过任何游戏，跳过下分...批次号："+patchno);
//			}
			
			Map<String,UserMoneyInAndOut> listGametype = SystemCache.getInstance().getEmployeeAllGameUP(employeecode);
				
			
			for (EmployeeApiAccout e : eapiaccounts.values()) {
				
				if(listGametype.containsKey(e.getGametype())){//只对最后一次的游戏进行下分，其他不处理
					try {
						
						JSONObject object = JSONObject.fromObject( GameAPI.getBalance(e.getGameaccount(), e.getGamepassword(), e.getGametype(), e.getEnterprisecode()) );
						
						if(object.getString("code").equals("0")){
							
							BigDecimal balance = new BigDecimal(object.getString("info"));
							//进行下分
							if(balance.intValue()>1){
								boolean success = this.downIntegral(balance,balance.intValue(),e.getGametype(),e, patchno+"");
								if(!success){
									e.setExitmoney(true);
									SystemCache.getInstance().setEmployeeAllGameAccount(employeecode, eapiaccounts);
									TLogger.getLogger().Error("用户:"+employeecode+"    游戏:"+e.getGametype()+" 下分失败,金额:"+balance.intValue());
									throw new LogicTransactionException("游戏:"+e.getGametype()+" 下分失败,金额:"+balance.intValue()+" 批次号："+patchno);
								}else if(success&&balance.compareTo(new BigDecimal(balance.intValue()))==0){
									e.setExitmoney(false);
								}
							}
							if(balance.compareTo(new BigDecimal("0"))==0){
								e.setExitmoney(false);
							}
						}
					} catch (Exception e2) {
						e2.printStackTrace();
						TLogger.getLogger().Error("用户:"+employeecode+"  用户批量下分异常："+e2.getMessage()+" 批次号："+patchno, e2);
					}
				}
				
			}
				
			/*****************************************************新下分逻辑 （对最后一次游戏下分） ***************************************************/
			
			
			SystemCache.getInstance().setEmployeeAllGameAccount(employeecode, eapiaccounts);
			TLogger.getLogger().Error("用户:"+employeecode+"  完成取款前的下分操作,批次号："+patchno);
			
		}
		
	}
	
	public String userShimobun(String employeecode,String gametype)throws Exception{
		Map<String,EmployeeApiAccout>  __eaa = SystemCache.getInstance().getEmployeeAllGameAccount(employeecode);
		EmployeeApiAccout e = __eaa.get(gametype);
		if(e!=null){
			
			//	获取本批次号
			String patchno = OrderNewUtil.getPatchno();
					
			JSONObject object = JSONObject.fromObject( GameAPI.getBalance(e.getGameaccount(), e.getGamepassword(), e.getGametype(), e.getEnterprisecode()) );
			
			if(object.getString("code").equals("0")){
				
				BigDecimal balance = new BigDecimal(object.getString("info"));
				//进行下分
				if(balance.intValue()>1){
					boolean success = this.downIntegral(balance,balance.intValue(),e.getGametype(),e, patchno);
					if(!success){
						throw new LogicTransactionException("游戏："+gametype+" 下分失败,金额"+balance.intValue() +" 批次号："+patchno);
					}
					if(success&&balance.compareTo(new BigDecimal(balance.intValue()))==0){
						e.setExitmoney(false);
						SystemCache.getInstance().setEmployeeAllGameAccount(employeecode, __eaa);
					}
					return String.valueOf(balance.doubleValue()-balance.intValue());
				}else{
					throw new LogicTransactionException("游戏余额小于1,"+Enum_MSG.下分失败.desc +" 批次号："+patchno);
				}
			}else{
				TLogger.getLogger().Debug("用户:"+e.getLoginaccount()+"  游戏:"+e.getGametype()+" 下分失败,原因:"+object.getString("info"));
				throw new LogicTransactionRollBackException(Enum_MSG.下分失败.desc +" 批次号："+patchno);
			}
		}
		throw new LogicTransactionRollBackException(Enum_MSG.用户未启用该游戏.desc);
	}
	
	
	
	/**
	 * 进入游戏-转账
	 * 
	 * List<Game> listGame 参数可为空，没用到
	 * @return
	 * @throws Exception
	 */
	public void transfer(String gametype,String employeecode,List<Game> listGame1) throws Exception{
		
		moneyInAndOutService.transfer(gametype, employeecode, listGame1);
	}

	/**
	 * 品牌开放游戏
	 * @param games
	 * @return
	 */
	private List<String>  brandOpenGame(List<Game> games) {
		List<String> brand_open_game = new ArrayList<String>();
		for (Game game : games) {
			brand_open_game.add(game.getGametype());
		}
		return brand_open_game;
	}
	
	/**
	 * 游戏下分
	 * 
	 * 
	 * @param orderamount 金额
	 * @param gametype 游戏类型
	 * @param employeecode 用户编码
	 * @return
	 * @throws Exception
	 */
	public String downIntegralGame(BigDecimal beforeAmount,int orderamount,String gametype ,EmployeeApiAccout ea, String patchno) throws Exception{
		
		//调用service层的东西
		if(moneyInAndOutService.downIntegral(beforeAmount, orderamount, gametype, ea, patchno)) {
			return Enum_MSG.成功.message("0","下分成功");
		} else {
			return Enum_MSG.失败.message("1","下分失败");
		}
	}
	
	/**
	 * 游戏上分
	 * 
	 * 
	 * @param orderamount 金额
	 * @param gametype 游戏类型
	 * @param employeecode 用户编码
	 * @return
	 * @throws Exception
	 */
	public String upIntegralGame(BigDecimal beforeAmount,int orderamount,String gametype ,EmployeeApiAccout ea, String patchno) throws Exception{
		
		//调用service层的东西
		if(moneyInAndOutService.upIntegral(beforeAmount, orderamount, gametype, ea, patchno)) {
			return Enum_MSG.成功.message("0","上分成功");
		} else {
			return Enum_MSG.失败.message("1","上分失败");
		}
	}
	
	/**
	 * 游戏上分
	 * 
	 * 
	 * @param orderamount 金额
	 * @param gametype 游戏类型
	 * @param employeecode 用户编码
	 * @return
	 * @throws Exception
	 */
	private boolean upIntegral(BigDecimal beforeAmount,int orderamount,String gametype ,EmployeeApiAccout ea, String patchno) throws Exception{
		
		//调用service层的东西
		if(true) {
			return moneyInAndOutService.upIntegral(beforeAmount, orderamount, gametype, ea, patchno);
		}
		
				
		
		
		
		
		
		/*********获取上分的订单号**********/
		String ordernumber = OrderNewUtil.getOrdernoUP();
		
		long timesort = RandomString.SORTTIME();
		
		TLogger.getLogger().Error("用户:"+ea.getEmployeecode()+" 准备上分..批次号："+patchno);
		
		//扣除账户金额
		capitalAccountService.tc_updateCapitalAccount(patchno,ea.getEmployeecode(), new BigDecimal(orderamount).negate(),
				new EmployeeMoneyChangeType(Enum_moneychangetype.游戏上分.value,Enum_moneychangetype.游戏上分.desc,Enum_moneyinouttype.出账),"操作人:API "+Enum_moneychangetype.游戏上分.desc+gametype+" 批次号："+patchno);
		TLogger.getLogger().Error("用户:"+ea.getEmployeecode()+" 扣除用户账户资金,金额："+orderamount+"...批次号："+patchno);
		
		
		//预先记录上分日志
		UserMoneyInAndOut umiao = new UserMoneyInAndOut(Enum_opreatetype.上分,ea.getEmployeecode(),ea.getParentemployeecode(),gametype,new BigDecimal(orderamount).negate(),
				"",beforeAmount,new BigDecimal("0"),Enum_updatecapital.请求中,timesort, ordernumber, patchno, ea.getEnterprisecode(), ea.getBrandcode());
		moneyInAndOutService.saveMoneyInAndOutRecord(umiao);
		
		//API接口上分
		JSONObject object = JSONObject.fromObject( GameAPI.deposit(ea.getGameaccount(), ea.getGamepassword(), gametype, ea.getEnterprisecode(), ordernumber, orderamount) );
		
		TLogger.getLogger().Error("用户:"+ea.getEmployeecode()+"  " +gametype+" 游戏上分结果:"+object+"...批次号："+patchno);
		
		
		//成功
		if(object.getString("code").equals("0")){
			
			//获取游戏账户余额
			JSONObject object__balance = JSONObject.fromObject( GameAPI.getBalance(ea.getGameaccount(), ea.getGamepassword(), ea.getGametype(), ea.getEnterprisecode()) );
			
			if(object__balance.getString("code").equals("0")){
				
				BigDecimal __balancecurren = new BigDecimal(object__balance.getString("info"));
				
				int count = moneyInAndOutService.updateInOutState(new UserMoneyInAndOut(umiao.getMoneyinoutcode(), "success", __balancecurren, Enum_updatecapital.是, ordernumber, patchno, ea.getEmployeecode() ));
				
				TLogger.getLogger().Error("用户:"+ea.getEmployeecode()+"  " +gametype+" 更新状态成功.count="+count+",moneyinoutcode="+umiao.getMoneyinoutcode()+"。当前余额："+__balancecurren+"，上分前余额:"+beforeAmount+"，本次上分金额："+orderamount+"...批次号："+umiao.getPatchno()+"");
				
			} else {
				
				BigDecimal __balancecurren = new BigDecimal(orderamount).negate();
				//重复五次
				//尝试二次查询余额
				GameApiInput GApiInput = SystemCache.getInstance().getGameApiInputMap(ea.getEnterprisecode());
				for(int i=0;i<GApiInput.getTrytime();i++){
					//上分后余额
					object__balance = JSONObject.fromObject( GameAPI.getBalance(ea.getGameaccount(), ea.getGamepassword(), ea.getGametype(), ea.getEnterprisecode()) ); 
					TLogger.getLogger().Error(ea.getEmployeecode()+" " +gametype+" 第"+(i+1)+"次尝试。余额查询结果："+object__balance+"...批次号："+patchno);
					if(object.getString("code").equals("0")){
						__balancecurren = new BigDecimal(object__balance.getString("info"));
						TLogger.getLogger().Error(ea.getEmployeecode()+" " +gametype+" 尝试查询成功。余额查询结果："+object__balance+"...批次号："+patchno);
						break;
					}
					
				}
				
				int count = moneyInAndOutService.updateInOutState(new UserMoneyInAndOut(umiao.getMoneyinoutcode(), "上分成功，但二次查询余额出错："+object__balance, __balancecurren, Enum_updatecapital.是, ordernumber, patchno, ea.getEmployeecode()));
				
				TLogger.getLogger().Error("用户:"+ea.getEmployeecode()+"  " +gametype+" 更新状态成功.count="+count+",moneyinoutcode="+umiao.getMoneyinoutcode()+"。但二次查询余额出错："+object__balance+"，上分前余额:"+beforeAmount+"，本次上分金额："+orderamount+"...批次号："+umiao.getPatchno());
			}
			
			/****************	重要：上分成功需要记录 *********************/
			APIServiceUtil.mapLastLoginGame.put(umiao.getEmployeecode(), umiao);
			
			return true;
		}
		//失败
		else {
			
			//查询订单校验
			if( !gametype.equals(GameEnum.Enum_GameType.MG.gametype) && !gametype.equals(GameEnum.Enum_GameType.棋牌.gametype)) {//只有MG不支持订单查询
				
				JSONObject object_order = JSONObject.fromObject( GameAPI.getOrder(ea.getGameaccount(), ea.getGamepassword(), gametype, ea.getEnterprisecode(), ordernumber, orderamount) );
				if(object_order.getString("code").equals("0")){
					//订单是成功的
					moneyInAndOutService.updateInOutState(new UserMoneyInAndOut(umiao.getMoneyinoutcode(), "上分成功，但上分返回结果不为0："+object, new BigDecimal(orderamount).negate(), Enum_updatecapital.是, ordernumber, patchno, ea.getEmployeecode()));
					return true;
					
				} else {
					
					moneyInAndOutService.updateInOutState(new UserMoneyInAndOut(umiao.getMoneyinoutcode(), "上分失败："+object, beforeAmount, Enum_updatecapital.否, ordernumber, patchno, ea.getEmployeecode()));
					TLogger.getLogger().Error("用户:"+ea.getEmployeecode()+"  " +gametype+" 更新状态失败。当前余额："+beforeAmount+"，上分前余额:"+beforeAmount+"，本次上分金额："+orderamount+"...批次号："+umiao.getPatchno());
					
					/*************************上分失败的话账户金额再加回来
					capitalAccountService.tc_updateCapitalAccount(patchno,ea.getEmployeecode(), new BigDecimal(orderamount),
							new EmployeeMoneyChangeType(Enum_moneychangetype.游戏上分.value,Enum_moneychangetype.游戏上分.desc,Enum_moneyinouttype.进账),"操作人:API "+Enum_moneychangetype.游戏上分.desc+gametype+"失败，返回金额. 批次号："+patchno);
					TLogger.getLogger().Error("用户:"+ea.getEmployeecode()+" 退回用户账户资金,金额："+orderamount+"...批次号："+patchno);
					******************************/
					
					return false;
				}
				
				
			} else {//如果是MG
				
				moneyInAndOutService.updateInOutState(new UserMoneyInAndOut(umiao.getMoneyinoutcode(), "上分失败："+object, beforeAmount, Enum_updatecapital.否, ordernumber, patchno, ea.getEmployeecode()));
				TLogger.getLogger().Error("用户:"+ea.getEmployeecode()+"  " +gametype+" 更新状态失败。当前余额："+beforeAmount+"，上分前余额:"+beforeAmount+"，本次上分金额："+orderamount+"...批次号："+umiao.getPatchno());
				
				/*************************上分失败的话账户金额再加回来
				capitalAccountService.tc_updateCapitalAccount(patchno,ea.getEmployeecode(), new BigDecimal(orderamount),
						new EmployeeMoneyChangeType(Enum_moneychangetype.游戏上分.value,Enum_moneychangetype.游戏上分.desc,Enum_moneyinouttype.进账),"操作人:API "+Enum_moneychangetype.游戏上分.desc+gametype+"失败，返回金额. 批次号："+patchno);
				TLogger.getLogger().Error("用户:"+ea.getEmployeecode()+" 加回用户账户资金,金额："+orderamount+"...批次号："+patchno);
				******************************/
				
				return false;
			}
			
		}
	}

	
	
	
	/**
	 * 游戏下分
	 * @param orderamount 金额
	 * @param gametype 游戏类型
	 * @param employeecode 用户编码
	 * @return
	 * @throws Exception
	 */
	protected boolean downIntegral(BigDecimal beforeAmount,int downIntegralAmount,String gametype ,EmployeeApiAccout eaa, String patchno) throws Exception{
		//调用service层的东西
		if(true) {
			return moneyInAndOutService.downIntegral(beforeAmount, downIntegralAmount, gametype, eaa, patchno);
		}
		
		/*********获取下分的订单号**********/
		String ordernumber = OrderNewUtil.getOrdernoDOWN();
		
		
		long timesort = RandomString.SORTTIME();
		
		UserMoneyInAndOut user_in_out = new UserMoneyInAndOut(Enum_opreatetype.下分,eaa.getEmployeecode(),eaa.getParentemployeecode(),eaa.getGametype(),new BigDecimal(downIntegralAmount),
				"",beforeAmount,new BigDecimal("0"),Enum_updatecapital.请求中,timesort,ordernumber, patchno , eaa.getEnterprisecode(), eaa.getBrandcode());
		//记录转分信息
		moneyInAndOutService.saveMoneyInAndOutRecord(user_in_out);
		
		
		//进行下分操作
		JSONObject object = JSONObject.fromObject( GameAPI.withdraw(eaa.getGameaccount(), eaa.getGamepassword(), gametype, eaa.getEnterprisecode(), ordernumber, downIntegralAmount) );
		TLogger.getLogger().Error("用户:"+eaa.getEmployeecode()+"  游戏:"+gametype+" 游戏下分结果:"+object+" 批次号："+patchno);
		
		
		//下分后余额
		JSONObject object__balance = JSONObject.fromObject( GameAPI.getBalance(eaa.getGameaccount(), eaa.getGamepassword(), eaa.getGametype(), eaa.getEnterprisecode()) );
		BigDecimal __balancecurren = null;
		if(object__balance.getString("code").equals("0")) {
			__balancecurren = new BigDecimal(object__balance.getString("info"));
		} else {
			__balancecurren = new BigDecimal("0");
		}
		
		TLogger.getLogger().Error("用户:"+eaa.getEmployeecode()+"  游戏:"+gametype+" 游戏下分后余额:"+object__balance+" 批次号："+patchno);
		
		
		if( object.getString("code").equals("0")) {
			
			TLogger.getLogger().Error("用户:"+eaa.getEmployeecode()+"  游戏:"+eaa.getGametype()+" 下分成功, 查询余额结果:"+object__balance+" 批次号："+patchno);
			//修改上下分状态与下分后余额
			moneyInAndOutService.updateInOutState(new UserMoneyInAndOut(user_in_out.getMoneyinoutcode(),(!object__balance.getString("code").equals("0")? "下分成功但没有查询到最新余额。" : "success"), __balancecurren, Enum_updatecapital.是, ordernumber, patchno, eaa.getEmployeecode()));
			
			//修改该游戏类型的所有记录为已下分
			moneyInAndOutService.updateIsdown(new UserMoneyInAndOut( eaa.getEmployeecode(),  eaa.getEnterprisecode(),  gametype));
					
			
			//进行资金操作
			capitalAccountService.tc_updateCapitalAccount(patchno,eaa.getEmployeecode(), new BigDecimal(downIntegralAmount),
					new EmployeeMoneyChangeType(Enum_moneychangetype.游戏下分.value,Enum_moneychangetype.游戏下分.desc, Enum_moneyinouttype.进账),"操作人:API "+Enum_moneychangetype.游戏下分.desc+eaa.getGametype()+" 批次号："+patchno);
			
			TLogger.getLogger().Error("用户:"+eaa.getEmployeecode()+"  游戏:"+eaa.getGametype()+" 下分成功，已进行资金转账操作！ 批次号："+patchno);
			
			return true;
			
		} else {
			
			TLogger.getLogger().Error("用户:"+eaa.getEmployeecode()+"  游戏:"+eaa.getGametype()+" 下分失败,下分结果:"+object+" ,查询余额结果:"+object__balance+" 批次号："+patchno);
			//修改下分状态与下分后余额
			moneyInAndOutService.updateInOutState(new UserMoneyInAndOut(user_in_out.getMoneyinoutcode(),object.getString("info"), __balancecurren,Enum_updatecapital.否, ordernumber, patchno, eaa.getEmployeecode()));
			return false;
			
		}
		
	}
	
	
	/**
	 * 批量修改游戏账号
	 * @param gametype 游戏类型
	 * @param ee EnterpriseEmployee 对象
	 * @return
	 * @throws Exception
	 */
	public String updatePassword(EnterpriseEmployee ee) throws Exception{
		
		
		//查找此人所有游戏账号数据
		Map<String,EmployeeApiAccout>  eapiaccounts = SystemCache.getInstance().getEmployeeAllGameAccount(ee.getEmployeecode());
		
		
		//尝试获取用户的原始密码
		String newpassword = ee.getLoginpassword2();
		try {
			//密码为空，说明该用户早已存在
			if(ee.getLoginpassword2() == null || ee.getLoginpassword2().equals("")) {
				//没有得到真实的密码，则此次不修改
				return Enum_MSG.失败.message("原始密码加密数据为空，没有得到真实的密码，此次不修改对应游戏平台的密码");
			} else {
				
				newpassword = APIServiceUtil.decrypt(newpassword, ee);//解密成功
			}
			
		} catch (Exception e) {
			//未能解密
			TLogger.getLogger().Error("=====================================================原始密码加密数据未能解密，没有得到真实的密码，此次不修改对应游戏平台的密码："+ee.getLoginaccount());
			e.printStackTrace();
			return Enum_MSG.失败.message("原始密码加密数据未能解密，没有得到真实的密码，此次不修改对应游戏平台的密码");
		}
		
		//需要修改的记录放到队列中
		Enum_GameType[] array = GameEnum.Enum_GameType.values();
		if(eapiaccounts != null && eapiaccounts.size() > 0) {
			
			
			String gamepassword = APIServiceUtil.encrypt(newpassword, ee.getParentemployeecode());//新密码加密，需要存储到队列表
			
			for (EmployeeApiAccout accout : eapiaccounts.values()) {
				
				String username = accout.getGameaccount();
				String password = accout.getGamepassword();
				String enterprisecode = accout.getEnterprisecode();
				String gametype = accout.getGametype();
				
				//对于支持密码修改的游戏平台，把修改密码的任务加到清单，按顺序执行该队列
				for (Enum_GameType enum_GameType : array) {
					
//					if(enum_GameType.gametype.equals(gametype) && enum_GameType.updatepassword == true) {//支持密码修改的才加入到队列
					
				}
				
				
				if(gametype.equals(GameEnum.Enum_GameType.GGPoker.gametype)) {
					
					//加到任务队列
					EmployeeApiAccoutPasswordJob accoutPasswordJob = new EmployeeApiAccoutPasswordJob();
					accoutPasswordJob.setCreatetime(new Date());
					accoutPasswordJob.setEmployeecode(accout.getEmployeecode());
					accoutPasswordJob.setGameaccount(username);
					accoutPasswordJob.setGamepassword(gamepassword);
					accoutPasswordJob.setGametype(gametype);
//					accoutPasswordJob.setLoginaccount(accout.getLoginaccount());
					accoutPasswordJob.setLoginaccount(APIServiceUtil.encrypt(password, ee.getParentemployeecode()));//这里储存旧密码
					accoutPasswordJob.setEnterprisecode(enterprisecode);
					accoutPasswordJob.setUpdatestatus(0);//待处理
					employeeApiAccoutPasswordJobService.add(accoutPasswordJob);
				}
				
			}
		}
		
		return Enum_MSG.成功.message("游戏账号密码数据已更新");
	}
	
	
//	private static Map<String,String> mapGamePlatformPrefix = null;
	private static List<String> gameaccountChar = new ArrayList<String>() {{
		this.add("@");//全支持
		this.add("_");//全支持
		this.add("#");//部分支持
		this.add("&");//部分支持
		this.add("*");//部分支持
	}};
	/**
	 * 创建游戏账号
	 * @param gametype 游戏类型
	 * @param ee EnterpriseEmployee 对象
	 * @return
	 * @throws Exception
	 */
	public  String create(String gametype,EnterpriseEmployee ee) throws Exception{
		
		/*****查找对应企业对应游戏的前缀配置参数*****/
		Map<String,Object> params = new HashMap<String, Object>();
		
		/*********************
		if(mapGamePlatformPrefix == null || mapGamePlatformPrefix.size() == 0) {
			mapGamePlatformPrefix = new HashMap<String, String>();
			
			List<GamePlatformPrefix> listGamePlatformPrefix = gamePlatformPrefixService.selectAll(params);//查整个前缀配置库
			for (GamePlatformPrefix gamePlatformPrefix : listGamePlatformPrefix) {
				
				String key = gamePlatformPrefix.getEnterprisecode() + gamePlatformPrefix.getGamePlatform();
				String value = gamePlatformPrefix.getPrefixcode();
				
				mapGamePlatformPrefix.put(key, value);
			}
		}
		
		String game_prefix = mapGamePlatformPrefix.get(ee.getEnterprisecode() + gametype);
		******/
		
		
		String game_prefix = SystemCache.getInstance().getPlatformPrefix(ee.getEnterprisecode() , gametype);
		
		if(game_prefix == null || game_prefix.equals("")) {
			
			if(!GameEnum.Enum_GameType.GGPoker.gametype.equals(gametype)) {//GGPoker扑克不管他
				game_prefix = "";
				TLogger.getLogger().Error("##################################################创建游戏账号异常，没有找到对应游戏平台的前缀设置：Enterprisecode="+ee.getEnterprisecode() +" gametype="+gametype);
				throw new Exception(Enum_MSG.游戏API系统异常.desc);
			}
			
		}
		game_prefix = game_prefix.trim();
		
		
		//尝试第一次以原始账号密码创建
		String username = ee.getLoginaccount();
		//增加配置好的前缀参数
		username = game_prefix + username;
		//过滤原始账号中的特殊字符，否则有些平台不允许特殊字符存在，导致重复创建账号
		username = StringUtils.stringFilterReplace(username);
		
		if(username.length() <= 0 ) {
			
			TLogger.getLogger().Error("##################################################创建游戏账号异常。用户账号不符合账号规范，含有多个无效字符，请废弃该账号并重新注册一个账号。Loginaccount="+ee.getLoginaccount()+" Employeecode="+ee.getEmployeecode());
			throw new Exception("用户账号不符合账号规范，含有多个无效字符，请废弃该账号并重新注册一个账号。Loginaccount="+ee.getLoginaccount()+" Employeecode="+ee.getEmployeecode());
		}
		
		String password = null;
		try {
			//密码为空，说明该用户早已存在
			if(ee.getLoginpassword2() == null || ee.getLoginpassword2().equals("")) {
				password = RandomStringNum.createRandomString(8).toLowerCase();
			} else {
				password = APIServiceUtil.decrypt(ee.getLoginpassword2(), ee);//解密
				System.out.println(ee.getLoginaccount()+"============成功解密："+password);
			}
			
			if(GameEnum.Enum_GameType.波音.gametype.equals(gametype) && password != null && password.length() > 12) {//12位密码
				password = RandomStringNum.createRandomString(8).toLowerCase();
			} else if(GameEnum.Enum_GameType.棋牌.gametype.equals(gametype)  && password != null && password.length() > 15) {//15位密码
				password = RandomStringNum.createRandomString(8).toLowerCase();
			} else if(GameEnum.Enum_GameType.GG.gametype.equals(gametype)  && password != null && password.length() > 12) {//12位密码
				password = RandomStringNum.createRandomString(8).toLowerCase();
			} else if(password != null && password.length() > 20) {//其他12位数
				password = RandomStringNum.createRandomString(8).toLowerCase();
			} 
			/*
			else if(password != null && password.length() < 8 ) {//不能小于8位数
				password = RandomStringNum.createRandomString(8).toLowerCase();
			} 
			*/
			if(GameEnum.Enum_GameType.IDN.gametype.equals(gametype) ) {//IDN的密码不能全数字
				if(StringUtils.isNumber(password)) {
					password = "idn"+password;
				}
			}
			
		} catch (Exception e) {
			//未能解密
			TLogger.getLogger().Error("=====================================================生成游戏账号,原始密码解密失败："+ee.getLoginaccount());
			password = RandomString.createRandomString(8).toLowerCase();
			if(GameEnum.Enum_GameType.IDN.gametype.equals(gametype) ) {//IDN的密码不能全数字
				if(StringUtils.isNumber(password)) {
					password = "idn"+password;
				}
			}
			e.printStackTrace();
		}
		
		return create( gametype, ee, username, password , "");//尝试第一次以原始账号密码创建
	}
	
	
	/**
	 * 创建用户
	 * @param ee
	 * @param username 计划创建的游戏账号
	 * @param password 计划创建的游戏账号密码
	 * @param suffix 后缀
	 * @return
	 * @throws Exception
	 */
	private  String create(String gametype,EnterpriseEmployee ee, String username, String password, String suffix) throws Exception{
		
		if(ee.getParentemployeecode()!=null && !ee.getEmployeecode().equals(ee.getParentemployeecode())){
			//验证上级是否存在API游戏账号
			EmployeeApiAccout parentApiAccout =  SystemCache.getInstance().getEmployeeGameAccount(ee.getParentemployeecode(), gametype);
			//如果不存API游戏账号在则创建
			if(parentApiAccout==null){
				/****是否需要建立上级账号，需要审计
				EnterpriseEmployee employeeApiAccount = employeeService.takeEmployeeByCode(ee.getParentemployeecode());
				if(employeeApiAccount!=null){
					String info = create(gametype,employeeApiAccount,false);
					if(!codeParse(info, "1")){
						TLogger.getLogger().Error("用户"+ee.getLoginaccount()+"创建"+gametype+"游戏账号失败,返回结果"+info);
						throw new Exception(Enum_MSG.游戏API系统异常.desc);
					}
				}else{
					//如果不存在上级则该账号为顶级代理用户
					ee.setParentemployeecode(ee.getEmployeecode());
				}
				*/
			}
		}
		
		
		
		/************************************************************
		 * 1、用户名一律20位长度。（AV老虎机、棋牌、GG必须15位数、PT可以最长20位数）。
		 * 
		 * 2、小于16位时，补充随机数
		 * 
		 * 3、大于16位时，切除中间部分
		 * ******************************************************/
		int max = 20;
		int step = 10;
		if(GameEnum.Enum_GameType.AV老虎机.gametype.equals(gametype) ) {//AV必须要加前缀，也就是有12位数是可以使用的
			max = 15;
			step = 7;
		} else if(GameEnum.Enum_GameType.棋牌.gametype.equals(gametype) ) {
			max = 15;
			step = 7;
		} else if(GameEnum.Enum_GameType.GG.gametype.equals(gametype) ) {
			max = 15;
			step = 7;
		} else if(GameEnum.Enum_GameType.PT.gametype.equals(gametype) || GameEnum.Enum_GameType.WIN88.gametype.equals(gametype) ) {
			max = 20;
			step = 8;
		} else if(GameEnum.Enum_GameType.IDN.gametype.equals(gametype) ) {
			max = 15;
			step = 7;
		} else if(GameEnum.Enum_GameType.M88.gametype.equals(gametype) ) {
			max = 17;
			step = 8;
		} 
		
		/*********加上后缀**********/
		username = username + suffix ;
		
		if(username.length() < max) {
			//小于最大长度时，不要求一定要为最大值
			//username = username+RandomString.createRandomString(max-username.length()).toLowerCase();
			
		} else if(username.length() > max) {
			username = username.substring(0, step) + username.substring(username.length()-(max-step), username.length());
		} else if(username.length() == max) {//如果位数相等，则从中间替换两位
			username = username.substring(0, step) + RandomString.createRandomString(2).toLowerCase() + username.substring(username.length()-(max-step) + 2, username.length());
		}
		
		username = username.toLowerCase();//全部小写
		
		JSONObject object = JSONObject.fromObject( GameAPI.createAccount(username, password, gametype, ee.getEnterprisecode()) );//创建
		/******
		JSONObject object = JSONObject.fromObject( GameAPI.createAccountTest(username, password, gametype, ee.getEnterprisecode()) );//创建
		*/
		
		
		if(object.getString("code").equals("0")){
			//创建成功,保存API账号
			EmployeeApiAccoutService service = SpringContextHolder.getBean("employeeApiAccoutServiceImpl");
			EmployeeApiAccout apiAccount = new EmployeeApiAccout();
			apiAccount.setGametype(gametype);
			apiAccount.setEnterprisecode(ee.getEnterprisecode());
			apiAccount.setBrandcode(ee.getBrandcode());
			apiAccount.setEmployeecode(ee.getEmployeecode());
			apiAccount.setParentemployeecode(ee.getParentemployeecode());
			apiAccount.setLoginaccount(ee.getLoginaccount());
			apiAccount.setGameaccount(username);
			
			/*******************因GB彩票在登录过程中，需要使用GBSN参数进行获取TOKEN，故将此参数存储起来*******************/
			if(GameEnum.Enum_GameType.GB彩票.gametype.equals(gametype) ) {
				apiAccount.setGamepassword(object.getString("info"));//GBSN
			} else {
				apiAccount.setGamepassword(password);//password
			}
			apiAccount.setCreatetime(new Date());
			apiAccount.setBalance(new BigDecimal(0));
			apiAccount.setStatus(EmployeeApiAccout.Enum_status.启用.value);
			service.tc_createApiAccount(apiAccount);
		} else if(object.getString("code").equals("100")){//账号已存在
			
			/*****添加后缀重试一遍（波音、沙龙、洲际、GG游行天下  不能包含任何特殊字符）*****/
			String temp_suffix = "";
			
			if(GameEnum.Enum_GameType.波音.gametype.equals(gametype) || 
					GameEnum.Enum_GameType.沙龙.gametype.equals(gametype) || 
					GameEnum.Enum_GameType.洲际.gametype.equals(gametype) || 
					GameEnum.Enum_GameType.IDN.gametype.equals(gametype) || 
					GameEnum.Enum_GameType.M88.gametype.equals(gametype) || 
					GameEnum.Enum_GameType.GG.gametype.equals(gametype) ) {
				
				/**********加两位随机数字************/
				temp_suffix = RandomStringNum.createRandomString(2);
				
			} else {
				/**********加后缀************/
				
				//查找真正的后缀
				String ___suffix = "";
				Enum_GameType[] array = GameEnum.Enum_GameType.values();
				for (Enum_GameType enum_GameType : array) {
					if(enum_GameType.gametype.equals(gametype)) {
						___suffix = enum_GameType.suffix;
						break;
					}
				}
				
				//检查当前的账号是否已存在某个特殊字符，如果已存在，则依次取下一个再尝试创建
				String old_suffix = ___suffix;
				
				int index = -1 ;
				for (int i = 0; i < username.length(); i++) {
					char cc = username.charAt(i);
					if(gameaccountChar.contains( String.valueOf(cc)) ) {
						index = gameaccountChar.indexOf( String.valueOf(cc) );
						old_suffix =  String.valueOf(cc) + old_suffix;
					}
				}
				if(index >= gameaccountChar.size()) {//没有更多预备特殊字符可选择
					TLogger.getLogger().Error("##################################################创建游戏账号异常，已存在："+object+"，没有更多预备特殊字符可选择. username="+username+"&password="+password+"&gametype="+gametype+"&enterprisecode="+ee.getEnterprisecode());
					throw new Exception(Enum_MSG.游戏API系统异常.desc);
				}
				index ++;
				
				try {
					temp_suffix = gameaccountChar.get(index) + ___suffix;//新的完整后缀
					username = username.replaceAll(old_suffix, "");
				} catch (Exception e) {
					e.printStackTrace();
					//没有更多字符型的后缀可用，则开始使用数字
					temp_suffix = RandomStringNum.createRandomString(2);
				}
				
			}
			
			create(gametype, ee, username, password, temp_suffix);//二次或三次尝试创建
			
			
		} else {
			//API报错
			TLogger.getLogger().Error("##################################################创建游戏账号异常："+object+" username="+username+"&password="+password+"&gametype="+gametype+"&enterprisecode="+ee.getEnterprisecode());
			throw new Exception(Enum_MSG.游戏API系统异常.desc);
		}
		
		TLogger.getLogger().Error("=====================================================生成游戏账号,username="+username+"&password="+password+"&gametype="+gametype+"&enterprisecode="+ee.getEnterprisecode());
		
		
		return object.toString();
		
	}
	
	private boolean codeParse(String info ,String code){
		return info.indexOf("\"code\":\""+code+"\"")>-1;
	}
	
//	private String successInfo(String content){
//		return "{\"info\":\""+content+"\",\"code\":\"1\"}";
//	}
	
	public static void main(String[] args) {
		String username = "520";
		System.out.println(StringUtils.stringFilterCheck(username));
		System.out.println(StringUtils.stringFilterReplace(username));
		System.out.println(StringUtils.isNumber(username));
	}

}

