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

import com.hy.pull.common.util.api.GameAPI;
import com.maven.cache.SystemCache;
import com.maven.constant.Enum_MSG;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeCapitalAccount;
import com.maven.entity.EnterpriseEmployeeType;
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
import com.maven.service.EmployeeApiAccoutService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.GamePlatformPrefixService;
import com.maven.service.UserMoneyInAndOutService;
import com.maven.util.JSONUnit;
import com.maven.util.RandomString;
import com.maven.util.StringUtils;
import com.maven.utility.SpringContextHolder;

import net.sf.json.JSONObject;

public class APIService {
	
	
	
	
	
	
	private EnterpriseEmployeeService employeeService;
	
	private EnterpriseEmployeeCapitalAccountService capitalAccountService;
	
	private UserMoneyInAndOutService moneyInAndOutService;
	
	private GameApiInput GApiInput;
	
	private HYAPI hYGameAPI;
	
	private GamePlatformPrefixService gamePlatformPrefixService;
	
	public APIService(String enterprisecode){
		this.GApiInput = SystemCache.getInstance().getGameApiInputMap(enterprisecode);
		if(GApiInput==null) 
			throw new ArgumentValidationException("未加载到企业API许可信息");
		hYGameAPI = new HYAPI(GApiInput);
		employeeService = SpringContextHolder.getBean("enterpriseEmployeeServiceImpl");
		capitalAccountService = 
			SpringContextHolder.getBean("enterpriseEmployeeCapitalAccountServiceImpl");
		moneyInAndOutService  = SpringContextHolder.getBean("userMoneyInAndOutServiceImpl");
		gamePlatformPrefixService  = SpringContextHolder.getBean("gamePlatformPrefixServiceImpl");
	}
	
	/**
	 * 创建游戏账号
	 * @param gametype 游戏类型
	 * @param ee EnterpriseEmployee 对象
	 * @return
	 * @throws Exception
	 */
	public  String create(String gametype,EnterpriseEmployee ee) throws Exception{
		return create(gametype,ee,false);
	}
	
	/**
	 * 用户登录
	 * @param gametype 游戏类型
	 * @param ee EnterpriseEmployee 对象
	 * @return
	 * @throws Exception
	 */
	public String play(String gametype,String playtype,String employeecode,List<Game> games,String devicetype) throws Exception{
		EmployeeApiAccout ea = SystemCache.getInstance().getEmployeeGameAccount(employeecode, gametype);
		if(ea ==null) return Enum_MSG.用户不存在.message(null);
		
		this.transfer(gametype,employeecode,games);
		
		
		
		return hYGameAPI.play(gametype,playtype,ea.getGameaccount(), ea.getGamepassword(), ea.getEmployeecode(),devicetype);
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
		String info = hYGameAPI.createUser(gametype,username,password, username,
				username, GameEnum.Enum_usertype.测试.value);
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
		if(ea ==null) return Enum_MSG.用户不存在.message(null);
		String info = hYGameAPI.balance(gametype,ea.getGameaccount(), ea.getGamepassword(), ea.getEmployeecode());
		if(codeParse(info, "1")){
			HYAPIMessage ae = (HYAPIMessage)JSONUnit.getDTO(info, HYAPIMessage.class);
			return  successInfo(new BigDecimal(ae.getInfo()).setScale(2, RoundingMode.HALF_UP).toString());
		}
		return  info;
	}
	
	/**
	 * 获取用户的所有账户余额
	 * @param employeecode 用户编码
	 * @return
	 * @throws Exception
	 */
	public String balances(String employeecode) throws Exception{
		BigDecimal money = new BigDecimal("0");
		//获取接口余额
		Map<String,EmployeeApiAccout>  eapiaccounts = SystemCache.getInstance().getEmployeeAllGameAccount(employeecode);
		if(eapiaccounts!=null){
			for (EmployeeApiAccout e : eapiaccounts.values()) {
				if(e.isExitmoney()){
					
					
					String gametype = e.getGametype();
					if(gametype.equals(Enum_GameType.MG.gametype) || 
							gametype.equals(Enum_GameType.TTG.gametype)  || 
							gametype.equals(Enum_GameType.PNG.gametype) ||
							gametype.equals(Enum_GameType.AG.gametype) ||
							gametype.equals(Enum_GameType.东方.gametype) ||
							gametype.equals(Enum_GameType.沙巴.gametype) || 
							gametype.equals(Enum_GameType.DZDY.gametype) ||
							gametype.equals(Enum_GameType.PT.gametype) ||
							gametype.equals(Enum_GameType.沙龙.gametype) ||
							gametype.equals(Enum_GameType.环球.gametype) ||
							gametype.equals(Enum_GameType.祥瑞.gametype) || 
							gametype.equals(Enum_GameType.棋牌.gametype)) {//特别处理东海国际
						
						JSONObject object = JSONObject.fromObject( GameAPI.getBalance(e.getGameaccount(), e.getGamepassword(), e.getGametype(), e.getEnterprisecode()) );
						if(object.getString("code").equals("0")){
							BigDecimal m = new BigDecimal(object.getString("info"));
							if(m.compareTo(new BigDecimal("0"))>0){
								money = money.add(m);
							}
						}
						
					} else {
						
						try {
							String info = this.balance(e.getGametype(),employeecode);
							if(codeParse(info, "1")){
								HYAPIMessage ae = (HYAPIMessage)JSONUnit.getDTO(info, HYAPIMessage.class);
								BigDecimal m = new BigDecimal(ae.getInfo());
								if(m.compareTo(new BigDecimal("0"))>0){
									money = money.add(m);
								}
							}
						} catch (SocketTimeoutException e2) {
							TLogger.getLogger().Error(e2.getMessage(), e2);
						}catch (ConnectTimeoutException e2) {
							TLogger.getLogger().Error(e2.getMessage(), e2);
						}catch (IOException e2) {
							TLogger.getLogger().Error(e2.getMessage(), e2);
						}
						
						
					}
					
				}
			}
		}
		//获取账户余额
		EnterpriseEmployeeCapitalAccount ec = capitalAccountService.takeCurrencyAccount(employeecode);
		if(ec==null) return  Enum_MSG.资金账户不存在.desc;
		money =  money.add(ec.getBalance());
		return successInfo(money.setScale(2, RoundingMode.HALF_UP).toString());
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
			
			//	不存在时可能是因为服务器重启过。此时查询一次数据库进行确认
			if( !APIServiceUtil.mapLastLoginGame.containsKey(employeecode)) {
				UserMoneyInAndOut temp = moneyInAndOutService.findMaxUpRecord(employeecode);
				if(temp != null) {
					APIServiceUtil.mapLastLoginGame.put(employeecode, temp);
				}
			}
			
			//	开始处理
			if( APIServiceUtil.mapLastLoginGame.containsKey(employeecode) ) {
				
				String gametypeLast = APIServiceUtil.mapLastLoginGame.get(employeecode).getGametype();
				
				TLogger.getLogger().Error("用户:"+employeecode+" 最后一次上分游戏是"+gametypeLast+"，开始对它下分...批次号："+patchno);
				
				for (EmployeeApiAccout e : eapiaccounts.values()) {
					
					if(gametypeLast.equals(e.getGametype())){//只对最后一次的游戏进行下分，其他不处理
						
						if(gametypeLast.equals(Enum_GameType.MG.gametype) || 
								gametypeLast.equals(Enum_GameType.TTG.gametype)  || 
								gametypeLast.equals(Enum_GameType.PNG.gametype) ||
								gametypeLast.equals(Enum_GameType.AG.gametype) ||
								gametypeLast.equals(Enum_GameType.东方.gametype) ||
								gametypeLast.equals(Enum_GameType.沙巴.gametype) || 
								gametypeLast.equals(Enum_GameType.DZDY.gametype) || 
								gametypeLast.equals(Enum_GameType.PT.gametype) ||
								gametypeLast.equals(Enum_GameType.沙龙.gametype) ||
								gametypeLast.equals(Enum_GameType.环球.gametype) || 
								gametypeLast.equals(Enum_GameType.祥瑞.gametype) || 
								gametypeLast.equals(Enum_GameType.棋牌.gametype)) {//特别处理东海国际
							
							APIServiceNew api = new APIServiceNew(e.getEnterprisecode());
							
							try {
								JSONObject object = JSONObject.fromObject( GameAPI.getBalance(e.getGameaccount(), e.getGamepassword(), e.getGametype(), e.getEnterprisecode()) );
								if(object.getString("code").equals("0")){
									
									BigDecimal balance = new BigDecimal(object.getString("info"));
									//进行下分
									if(balance.intValue()>1){
										boolean success = api.downIntegral(balance,balance.intValue(),e.getGametype(),e, patchno+"");
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
							
						} else {
							
							try {
								String info = hYGameAPI.balance(e.getGametype(),e.getGameaccount(), e.getGamepassword(), e.getEmployeecode()); 
								if(codeParse(info, "1")){
									HYAPIMessage ae = (HYAPIMessage)JSONUnit.getDTO(info, HYAPIMessage.class);
									BigDecimal balance = new BigDecimal(ae.getInfo());
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
								TLogger.getLogger().Error("用户:"+employeecode+"  用户批量下分异常："+e2.getMessage()+" 批次号："+patchno, e2);
							}
							
						}
						
						
					} else {
						TLogger.getLogger().Error("用户:"+employeecode+" 最后一次上分游戏是"+gametypeLast+"，"+e.getGametype()+"其他游戏跳过不下分...批次号："+patchno);
					}
				}
				
				TLogger.getLogger().Error("用户:"+employeecode+" 最后一次上分游戏是"+gametypeLast+"，完成对它下分...批次号："+patchno);
				
			} else {
				TLogger.getLogger().Error("用户:"+employeecode+" 游戏转分逻辑中没有找到该人最后一次上分记录，说明还没进入过任何游戏，跳过下分...批次号："+patchno);
				
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
					
				String info = hYGameAPI.balance(e.getGametype(),e.getGameaccount(), e.getGamepassword(), e.getEmployeecode()); 
				if(codeParse(info, "1")){
					HYAPIMessage ae = (HYAPIMessage)JSONUnit.getDTO(info, HYAPIMessage.class);
					BigDecimal balance = new BigDecimal(ae.getInfo());
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
					TLogger.getLogger().Debug("用户:"+e.getLoginaccount()+"  游戏:"+e.getGametype()+" 下分失败,原因:"+info);
					throw new LogicTransactionRollBackException(Enum_MSG.下分失败.desc +" 批次号："+patchno);
				}
		}
		throw new LogicTransactionRollBackException(Enum_MSG.用户未启用该游戏.desc);
	}
	
	
	
	/**
	 * 进入游戏-转账
	 * @return
	 * @throws Exception
	 */
	public void transfer(String gametype,String employeecode,List<Game> games) throws Exception{
		
		//	获取本批次号
		String patchno = OrderNewUtil.getPatchno();
		TLogger.getLogger().Error("=======================用户:"+employeecode+" 开始进行游戏转分...批次号："+patchno);
		Map<String,EmployeeApiAccout>  eapiaccounts = SystemCache.getInstance().getEmployeeAllGameAccount(employeecode);
		List<String> brand_open_game = brandOpenGame(games);
		BigDecimal beforeAmount = new BigDecimal(0);
		if(eapiaccounts!=null){
					
			
			/******************************************************新下分逻辑（对最后一次游戏下分） **************************************************/
			
			//	不存在时可能是因为服务器重启过。此时查询一次数据库进行确认
			if( !APIServiceUtil.mapLastLoginGame.containsKey(employeecode)) {
				UserMoneyInAndOut temp = moneyInAndOutService.findMaxUpRecord(employeecode);
				if(temp != null) {
					APIServiceUtil.mapLastLoginGame.put(employeecode, temp);
				}
			}
			
			//	开始处理
			if( APIServiceUtil.mapLastLoginGame.containsKey(employeecode) ) {
				
				String gametypeLast = APIServiceUtil.mapLastLoginGame.get(employeecode).getGametype();
				
				TLogger.getLogger().Error("用户:"+employeecode+" 最后一次上分游戏是"+gametypeLast+"，开始对它下分...批次号："+patchno);
				
				for (EmployeeApiAccout e : eapiaccounts.values()) {
					
					TLogger.getLogger().Error("=======================用户:"+employeecode+" 开始循环"+e.getGametype());////////////
					
					//本次上分目标与最后一次相同时，则不下分
					if(gametypeLast.equals(gametype)) {
						
						TLogger.getLogger().Error("=======================用户:"+employeecode+" "+e.getGametype()+"最后一次上分游戏是"+gametypeLast+"，本次上分目标是："+gametype+"，游戏类型相同，本次转分不进行下分，直接上分...批次号："+patchno);
						continue;
					}
					
					if(gametypeLast.equals(e.getGametype())){//只对最后一次的游戏进行下分，其他不处理
						
						
						//特别处理东海国际
						if(e.getGametype().equals(Enum_GameType.MG.gametype) || 
								e.getGametype().equals(Enum_GameType.TTG.gametype)  || 
								e.getGametype().equals(Enum_GameType.PNG.gametype) || 
								e.getGametype().equals(Enum_GameType.AG.gametype) || 
								e.getGametype().equals(Enum_GameType.东方.gametype) ||
								e.getGametype().equals(Enum_GameType.沙巴.gametype) || 
								e.getGametype().equals(Enum_GameType.DZDY.gametype) ||
								e.getGametype().equals(Enum_GameType.PT.gametype) ||
								e.getGametype().equals(Enum_GameType.沙龙.gametype) ||
								e.getGametype().equals(Enum_GameType.环球.gametype) || 
								e.getGametype().equals(Enum_GameType.祥瑞.gametype) || 
								e.getGametype().equals(Enum_GameType.棋牌.gametype)) {
							
							TLogger.getLogger().Error("==========用户:"+employeecode+" 游戏类型"+e.getGametype());//////////////////////
							
							BigDecimal balance = new BigDecimal("1");
							try {
								//获取游戏账户余额
								JSONObject object = JSONObject.fromObject( GameAPI.getBalance(e.getGameaccount(), e.getGamepassword(), e.getGametype(), e.getEnterprisecode()) );
								
								TLogger.getLogger().Error("==========用户:"+employeecode+" 游戏类型"+e.getGametype() + " 余额="+object.getString("info"));//////////////////////
								
								if(object.getString("code").equals("0")){
									
									balance = new BigDecimal(object.getString("info"));
									if(e.getGametype().equals(gametype)){
										beforeAmount = balance;
									}
									e.setExitmoney(true);
								}
								//进行下分
								if(balance.intValue()>1){
									TLogger.getLogger().Error("用户:"+employeecode+" 正在进行对游戏"+e.getGametype()+" 进行游戏下分,金额："+balance.intValue()+"...批次号："+patchno);
									APIServiceNew apiServiceNew = new APIServiceNew(e.getEnterprisecode());
									boolean success = apiServiceNew.downIntegral(balance,balance.intValue(),e.getGametype(),e, patchno);
									if(gametype.equals(e.getGametype())&&success){
										beforeAmount = balance.subtract(new BigDecimal(balance.intValue()));
									}
									if(success&&balance.compareTo(new BigDecimal(balance.intValue()))==0){
										e.setExitmoney(false);
									}
									TLogger.getLogger().Error("==========用户:"+employeecode+" 游戏类型"+e.getGametype() + " 余额="+object.getString("info") + " downIntegral="+success);//////////////////////
								}
								if(balance.compareTo(new BigDecimal(0))==0){
									e.setExitmoney(false);
								}
							} catch (Exception e2) {
								if(balance.doubleValue()>0) e.setExitmoney(true);
								e2.printStackTrace();
								TLogger.getLogger().Error("用户:"+employeecode+" "+e.getGametype()+" 游戏转分失败"+"...批次号："+patchno,e2);
							}
							
						} else {
							
							BigDecimal balance = new BigDecimal("1");
							try {
								//获取游戏账户余额
								String info = hYGameAPI.balance(e.getGametype(),e.getGameaccount(), e.getGamepassword(), e.getEmployeecode()); 
								if(codeParse(info, "1")){
									HYAPIMessage ae = (HYAPIMessage)JSONUnit.getDTO(info, HYAPIMessage.class);
									balance = new BigDecimal(ae.getInfo());
									if(e.getGametype().equals(gametype)){
										beforeAmount = balance;
									}
									e.setExitmoney(true);
								}
								//进行下分
								if(balance.intValue()>1){
									TLogger.getLogger().Error("用户:"+employeecode+" 正在进行对游戏"+e.getGametype()+" 进行游戏下分,金额："+balance.intValue()+"...批次号："+patchno);
									boolean success = this.downIntegral(balance,balance.intValue(),e.getGametype(),e, patchno);
									if(gametype.equals(e.getGametype())&&success){
										beforeAmount = balance.subtract(new BigDecimal(balance.intValue()));
									}
									if(success&&balance.compareTo(new BigDecimal(balance.intValue()))==0){
										e.setExitmoney(false);
									}
								}
								if(balance.compareTo(new BigDecimal(0))==0){
									e.setExitmoney(false);
								}
							} catch (Exception e2) {
								if(balance.doubleValue()>0) e.setExitmoney(true);
								e2.printStackTrace();
								TLogger.getLogger().Error("用户:"+employeecode+" "+e.getGametype()+" 游戏转分失败"+"...批次号："+patchno,e2);
							}
							
						}
						
					} else {
						TLogger.getLogger().Error("用户:"+employeecode+" "+e.getGametype()+"其他游戏跳过不下分...批次号："+patchno);
					}
				}
				
				TLogger.getLogger().Error("用户:"+employeecode+" 最后一次上分游戏是"+gametypeLast+"，完成对它下分...批次号："+patchno);
				
			} else {
				TLogger.getLogger().Error("用户:"+employeecode+" 游戏转分逻辑中没有找到该人最后一次上分记录，说明还没进入过任何游戏，跳过下分...批次号："+patchno);
			}
			/*****************************************************新下分逻辑 （对最后一次游戏下分） ***************************************************/
			
		}
		
		//获取用户账户余额
		EnterpriseEmployeeCapitalAccount ec = capitalAccountService.takeCurrencyAccount(employeecode);
		if(ec == null){
			Exception ex = new Exception(Enum_MSG.资金账户不存在.desc);
			TLogger.getLogger().Error(employeecode,ex);
			throw ex;
		} 
		BigDecimal accountfund = ec.getBalance();
		//进行上分
		if(accountfund.intValue()>1){
			EmployeeApiAccout ea = eapiaccounts.get(gametype);
			ea.setExitmoney(true);
			TLogger.getLogger().Error("用户:"+employeecode+" 正在对游戏"+ea.getGametype()+" 进行游戏上分,金额："+accountfund.intValue()+"...批次号："+patchno);
			this.upIntegral(beforeAmount,accountfund.intValue(),gametype,ea, patchno);
		} else {
			TLogger.getLogger().Error("用户:"+employeecode+" 正在对游戏"+gametype        +" 进行游戏上分,金额："+accountfund.intValue()+"，但当前此人账户余额只有"+accountfund.doubleValue()+"，所以不进行上分...批次号："+patchno);
		}
		SystemCache.getInstance().setEmployeeAllGameAccount(employeecode, eapiaccounts);
		TLogger.getLogger().Error("=======================用户:"+employeecode+" 游戏转分结束..."+"...批次号："+patchno);
		TLogger.getLogger().Error("");
		
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
	 * 获取游戏数据
	 * @param gamecode
	 * @param username
	 * @param beginTime
	 * @param endTime
	 * @param pageSize
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public  String data(String gametype,String brandecode,String username ,String beginTime ,String endTime ,int pageSize,int page) throws Exception{
		return hYGameAPI.data(gametype,username, beginTime, endTime, pageSize, page);
	}
	
	
	
	/**
	 * 游戏上分
	 * @param orderamount 金额
	 * @param gametype 游戏类型
	 * @param employeecode 用户编码
	 * @return
	 * @throws Exception
	 */
	private  boolean upIntegral(BigDecimal beforeAmount,int orderamount,String gametype ,EmployeeApiAccout ea, String patchno) throws Exception{
		
//		String ordernumber = String.valueOf(System.currentTimeMillis());
		
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
		String info = hYGameAPI.upIntegral(gametype,ea.getGameaccount(), ea.getGamepassword(), String.valueOf(orderamount), ordernumber);
		TLogger.getLogger().Error("用户:"+ea.getEmployeecode()+"  " +gametype+" 游戏上分结果:"+info+"...批次号："+patchno);
		HYAPIMessage infoMessage = (HYAPIMessage)JSONUnit.getDTO(info, HYAPIMessage.class);
		
		if(codeParse(info, "1")){
			return checkIsBalance(beforeAmount, orderamount, gametype, ea, infoMessage, umiao);
		}else{
			return checkIsBalance(beforeAmount, orderamount, gametype, ea, infoMessage, umiao);
		}
	}

	/**
	 * 上分后尝试校验对方平台当前余额是否与上分金额一致
	 * 
	 * 校验逻辑：（上分后查询到的余额 - 上分前的余额）   >= 本次上分的金额
	 * 
	 * @param beforeAmount
	 * @param orderamount
	 * @param gametype
	 * @param ea
	 * @param infoMessage
	 * @param umiao
	 * @return
	 * @throws Exception
	 */
	private boolean checkIsBalance(BigDecimal beforeAmount, int orderamount, String gametype, EmployeeApiAccout ea,
			HYAPIMessage infoMessage, UserMoneyInAndOut umiao) throws Exception {
		
		boolean issuccess = false;
		TLogger.getLogger().Error("用户:"+ea.getEmployeecode()+"  " +gametype+" 准备执行上分后的金额校验...批次号："+umiao.getPatchno());
		for(int i=0;i<GApiInput.getTrytime();i++){
			//上分后余额
			String balance = hYGameAPI.balance(gametype,ea.getGameaccount(), ea.getGamepassword(), ea.getEmployeecode()); 
			TLogger.getLogger().Error(ea.getEmployeecode()+" " +gametype+" 第"+(i+1)+"次尝试。余额查询结果："+balance+"...批次号："+umiao.getPatchno());
			HYAPIMessage balancecurren = (HYAPIMessage)JSONUnit.getDTO(balance, HYAPIMessage.class);//当前游戏平台最新的余额信息
			
			
			if(NumberUtils.isNumber(balancecurren.getInfo())){
				issuccess = true;
				BigDecimal __balancecurren = new BigDecimal(balancecurren.getInfo());
				if(__balancecurren.subtract(beforeAmount).intValue() >= orderamount ){
					
					
					moneyInAndOutService.updateInOutState(new UserMoneyInAndOut(umiao.getMoneyinoutcode(), "success", __balancecurren, Enum_updatecapital.是, umiao.getOrderno(), umiao.getPatchno(), ea.getEmployeecode()));
					
					TLogger.getLogger().Error("用户:"+ea.getEmployeecode()+"  " +gametype+" 更新状态成功。当前余额："+__balancecurren+"，上分前余额:"+beforeAmount+"，本次上分金额："+orderamount+"...批次号："+umiao.getPatchno());
					
					/****************	重要：上分成功需要记录 *********************/
					APIServiceUtil.mapLastLoginGame.put(umiao.getEmployeecode(), umiao);
					
					
					return true;
				} else {
					String info = infoMessage.getInfo();
					if(infoMessage.getCode() == 1005 || infoMessage.getInfo().trim().equals("money is not enough")) {
						info = "提示：平台商在游戏方的余额不足，请联系管理员尽快充值！！！";
					}
					moneyInAndOutService.updateInOutState(new UserMoneyInAndOut(umiao.getMoneyinoutcode(), info, __balancecurren, Enum_updatecapital.否, umiao.getOrderno(), umiao.getPatchno(), ea.getEmployeecode()));
					
					TLogger.getLogger().Error("用户:"+ea.getEmployeecode()+"  " +gametype+" 更新状态失败。当前余额："+__balancecurren+"，上分前余额:"+beforeAmount+"，本次上分金额："+orderamount+"...批次号："+umiao.getPatchno());
					
					break;
				}
			} else {
				TLogger.getLogger().Error("用户:"+ea.getEmployeecode()+"  " +gametype+" 余额返回非金额数字，不处理...批次号："+umiao.getPatchno());
			}
			
		}
		
		
		if(!issuccess){
			String info = infoMessage.getInfo();
			if(infoMessage.getCode() == 1005 || infoMessage.getInfo().trim().equals("money is not enough")) {
				info = "提示：平台商在游戏方的余额不足，请联系管理员尽快充值！！！";
			}
			moneyInAndOutService.updateInOutState(new UserMoneyInAndOut(umiao.getMoneyinoutcode(), info, new BigDecimal(orderamount).negate(), Enum_updatecapital.否, umiao.getOrderno(), umiao.getPatchno(), ea.getEmployeecode()));
		}
		return false;
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
//		//理应余额
//		BigDecimal shouldBalance = beforeAmount.subtract(new BigDecimal(downIntegralAmount));
		
		
//		String  ordernumber = String.valueOf(System.currentTimeMillis());
		
		/*********获取下分的订单号**********/
		String ordernumber = OrderNewUtil.getOrdernoDOWN();
		
		
		long timesort = RandomString.SORTTIME();
		
		UserMoneyInAndOut user_in_out = new UserMoneyInAndOut(Enum_opreatetype.下分,eaa.getEmployeecode(),eaa.getParentemployeecode(),eaa.getGametype(),new BigDecimal(downIntegralAmount),
				"",beforeAmount,new BigDecimal("0"),Enum_updatecapital.请求中,timesort,ordernumber, patchno , eaa.getEnterprisecode(), eaa.getBrandcode());
		//记录转分信息
		moneyInAndOutService.saveMoneyInAndOutRecord(user_in_out);
		//进行下分操作
		String info = hYGameAPI.downIntegral(gametype,eaa.getGameaccount(), eaa.getGamepassword(), String.valueOf(downIntegralAmount), ordernumber);
		HYAPIMessage downInfo = (HYAPIMessage)JSONUnit.getDTO(info, HYAPIMessage.class);
		TLogger.getLogger().Error("用户:"+eaa.getEmployeecode()+"  游戏:"+gametype+" 游戏下分结果:"+info+" 批次号："+patchno);
		
		/************************/
		//下分后余额
		String balance = hYGameAPI.balance(gametype,eaa.getGameaccount(), eaa.getGamepassword(), eaa.getEmployeecode());
		HYAPIMessage ae = (HYAPIMessage)JSONUnit.getDTO(balance, HYAPIMessage.class);
		TLogger.getLogger().Error("用户:"+eaa.getEmployeecode()+"  游戏:"+gametype+" 游戏下分后余额:"+balance+" 批次号："+patchno);
		
		
		BigDecimal afterBalance = new BigDecimal(0);
		boolean isQuerySuccess = false;//查询是否成功
		//查询成功
		if(NumberUtils.isNumber(ae.getInfo())){
			afterBalance = new BigDecimal(ae.getInfo());
			isQuerySuccess = true;
		} else {
			TLogger.getLogger().Error("用户:"+eaa.getEmployeecode()+"  游戏:"+eaa.getGametype()+" 查询余额接口调用返回错误信息，查询余额结果:"+ae.getInfo()+" 批次号："+patchno);
			
			//尝试二次查询余额
			for(int i=0;i<GApiInput.getTrytime();i++){
				//上分后余额
				balance = hYGameAPI.balance(gametype,eaa.getGameaccount(), eaa.getGamepassword(), eaa.getEmployeecode()); 
				TLogger.getLogger().Error(eaa.getEmployeecode()+" " +gametype+" 第"+(i+1)+"次尝试。余额查询结果："+balance+"...批次号："+patchno);
				ae = (HYAPIMessage)JSONUnit.getDTO(balance, HYAPIMessage.class);
				if(NumberUtils.isNumber(ae.getInfo())){
					isQuerySuccess = true;
					afterBalance = new BigDecimal(ae.getInfo());
					TLogger.getLogger().Error(eaa.getEmployeecode()+" " +gametype+" 尝试查询成功。余额查询结果："+balance+"...批次号："+patchno);
					break;
				}
				
			}
		}
		
		
		/****************************************************************************************
		 * 
		 * 注意：发现通过小兰的第三方接口，有时候下分成功了，但是小兰那里也返回{"info":"system error","code":"1001"}错误，但是通过查询余额，又显示为0，说明已成功。
		 * 
		 * 对此，除了需要对下分成功状态时做操作，也要对查询余额确认正确的时候做操作。
		 * 
		 * 
		 * 注意：20161212发现有一个用户7秒内提交了两次取款申请，第一次下分成功，第二次下分失败，所以对此校验金额的逻辑再次修正，即除非下分状态成功与金额校验都成功，才处理
		 * *************************************************************************************/
		boolean isSuccess = false;
		
		if(downInfo.getInfo().equals("success")){//下分成功时
			TLogger.getLogger().Error("用户:"+eaa.getEmployeecode()+"  游戏:"+eaa.getGametype()+" 下分成功,下分结果:"+info+" 批次号："+patchno);
			isSuccess = true;
		} 
		
		if(isSuccess == true && isQuerySuccess == true && beforeAmount.subtract(afterBalance).intValue() >= downIntegralAmount ){//余额查询确认成功时
			isSuccess = true;
			TLogger.getLogger().Error("用户:"+eaa.getEmployeecode()+"  游戏:"+eaa.getGametype()+" 下分后进行余额校验::::显示下分成功！下分前余额："+beforeAmount+"，本次下分金额："+downIntegralAmount+" ，下分后余额："+afterBalance+" ...批次号："+patchno);
		}
		
		if( isSuccess == true) {
			
			TLogger.getLogger().Error("用户:"+eaa.getEmployeecode()+"  游戏:"+eaa.getGametype()+" 下分成功,下分结果:"+info+" ,查询余额结果:"+balance+" 批次号："+patchno);
			//修改上下分状态与下分后余额
			moneyInAndOutService.updateInOutState(new UserMoneyInAndOut(user_in_out.getMoneyinoutcode(),"success",afterBalance,Enum_updatecapital.是, ordernumber, patchno, eaa.getEmployeecode()));
			//进行资金操作
			capitalAccountService.tc_updateCapitalAccount(patchno,eaa.getEmployeecode(), new BigDecimal(downIntegralAmount),
					new EmployeeMoneyChangeType(Enum_moneychangetype.游戏下分.value,Enum_moneychangetype.游戏下分.desc, Enum_moneyinouttype.进账),"操作人:API "+Enum_moneychangetype.游戏下分.desc+eaa.getGametype()+" 批次号："+patchno);
			
			TLogger.getLogger().Error("用户:"+eaa.getEmployeecode()+"  游戏:"+eaa.getGametype()+" 下分成功，已进行资金转账操作！ 批次号："+patchno);
			
			return true;
			
		} else {
			
			TLogger.getLogger().Error("用户:"+eaa.getEmployeecode()+"  游戏:"+eaa.getGametype()+" 下分失败,下分结果:"+info+" ,查询余额结果:"+balance+" 批次号："+patchno);
			//修改下分状态与下分后余额
			moneyInAndOutService.updateInOutState(new UserMoneyInAndOut(user_in_out.getMoneyinoutcode(),downInfo.getInfo(),afterBalance,Enum_updatecapital.否, ordernumber, patchno, eaa.getEmployeecode()));
			return false;
			
		}
		
	}
	
	
	
	/**
	 * 创建用户
	 * @param ee
	 * @return
	 * @throws Exception
	 */
	private  String create(String gametype,EnterpriseEmployee ee,boolean isSuffix) throws Exception{
		if(ee.getParentemployeecode()!=null && !ee.getEmployeecode().equals(ee.getParentemployeecode())){
			//验证上级是否存在API游戏账号
			EmployeeApiAccout parentApiAccout =  SystemCache.getInstance().getEmployeeGameAccount(ee.getParentemployeecode(), gametype);
			//如果不存API游戏账号在则创建
			if(parentApiAccout==null){
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
			}
		}
		
		/*****查找对应企业对应游戏的前缀配置参数*****/
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("enterprisecode", ee.getEnterprisecode());
		params.put("gamePlatform", gametype);
		List<GamePlatformPrefix> listConfig = gamePlatformPrefixService.selectAll(params);
		String game_prefix = "";
		if(listConfig != null && listConfig.size() > 0) {
			GamePlatformPrefix prefix = listConfig.get(0);
			game_prefix = prefix.getPrefixcode();
		}
		
		
		//生成API账号
		String username =  isSuffix ? ee.getLoginaccount()+RandomString.createRandomString(2).toLowerCase():ee.getLoginaccount();
		//增加配置好的前缀参数
		username = game_prefix+username;
		
		username = StringUtils.stringFilterReplace(username);//过滤特殊字符，否则有些平台不允许特殊字符存在，导致重复创建账号
		
		
		/*****取消写死的业务
		if(gametype.equals(GameEnum.Enum_GameType.PT.gametype)){
			username =  isSuffix ? 
					(Enum_GameType.PT.prefix + ee.getLoginaccount()+RandomString.createRandomString(2))
					:(Enum_GameType.PT.prefix + ee.getLoginaccount());
		}
		*/
		
		/**
		if(username.length()<8) username = username+RandomString.createRandomString(14-username.length()).toLowerCase();
		if(username.length()>12) username = username.substring(0, 12)+username.substring(username.length()-2, username.length());
		*/
		
		/************************************************************
		 * 1、游戏账号长度一致最大为16位（除了MG游戏。待接入）
		 * 
		 * 2、小于16位时，补充随机数
		 * 
		 * 3、大于16位时，切除中间部分
		 * ******************************************************/
		int max = 16;
		int step = 8;
		if(GameEnum.Enum_GameType.AV老虎机.gametype.equals(gametype) ) {//AV必须要加前缀，也就是有12位数是可以使用的
			max = 15;
			step = 7;
		} else if(GameEnum.Enum_GameType.PT.gametype.equals(gametype) ) {
			max = 12;
			step = 6;
		}
		if(username.length()<max) {
			username = username+RandomString.createRandomString(max-username.length()).toLowerCase();
		} else if(username.length() > max) {
			username = username.substring(0, step)+username.substring(username.length()-(max-step), username.length());
		} else if(username.length() == max) {//如果位数相等，则从后面截取两位
			username = username.substring(0, username.length() - 2) + RandomString.createRandomString(2).toLowerCase();
		}
		
		
		String password = RandomString.createRandomString(8).toLowerCase();
		TLogger.getLogger().Debug("生成游戏账号,用户名："+username+"  密码:"+password);
		System.out.println("生成游戏账号,用户名："+username+"("+username.length()+"位数)  密码:"+password);
		
		//创建API游戏账号
		String parentcode = ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.企业号.value)
				?ee.getEmployeecode():ee.getParentemployeecode();
		String info = hYGameAPI.createUser(gametype,username,password, ee.getEmployeecode(),
				parentcode, GameEnum.Enum_usertype.userType(ee.getEmployeetypecode()));
		if(codeParse(info, "1")){
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
			apiAccount.setGamepassword(password);
			apiAccount.setCreatetime(new Date());//
			service.tc_createApiAccount(apiAccount);
		}else if(codeParse(info, "1004")){
			//用户名已存在
			TLogger.getLogger().Error("游戏账号"+username+"已存在,再次创建");
			return create(gametype,ee,true);
		}else if(codeParse(info, "1001")){
			//API报错
			TLogger.getLogger().Error("========用户"+ee.getLoginaccount()+"创建"+gametype+"游戏账号失败,返回结果"+info);
			throw new Exception(Enum_MSG.游戏API系统异常.desc);
		}
		return info;
	}
	
	private boolean codeParse(String info ,String code){
		return info.indexOf("\"code\":\""+code+"\"")>-1;
	}
	
	private String successInfo(String content){
		return "{\"info\":\""+content+"\",\"code\":\"1\"}";
	}
	public static void main(String[] args) {
		String username = "zj520.";
		System.out.println(StringUtils.stringFilterCheck(username));
		System.out.println(StringUtils.stringFilterReplace(username));
		System.out.println("035e9598-de29-45c1-9a1b-45ef55bcd9f5".length());
	}

}

