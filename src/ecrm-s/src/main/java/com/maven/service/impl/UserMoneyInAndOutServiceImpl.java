package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hy.pull.common.util.api.GameAPI;
import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.cache.SystemCache;
import com.maven.constant.Enum_MSG;
import com.maven.dao.EnterpriseEmployeeCapitalAccountDao;
import com.maven.dao.UserMoneyInAndOutDao;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EnterpriseEmployeeCapitalAccount;
import com.maven.entity.Game;
import com.maven.entity.GameApiInput;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.entity.UserMoneyInAndOut;
import com.maven.entity.UserMoneyInAndOut.Enum_backmoney;
import com.maven.entity.UserMoneyInAndOut.Enum_opreatetype;
import com.maven.entity.UserMoneyInAndOut.Enum_updatecapital;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.game.APIServiceUtil;
import com.maven.game.OrderNewUtil;
import com.maven.game.enums.GameEnum;
import com.maven.logger.TLogger;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.UserMoneyInAndOutService;
import com.maven.util.AttrCheckout;
import com.maven.util.RandomString;

import net.sf.json.JSONObject;
/**
 * 账变记录
 * @author Ethan
 *
 */
@Service
public class UserMoneyInAndOutServiceImpl extends BaseServiceImpl<UserMoneyInAndOut>
		implements UserMoneyInAndOutService {
	
	private Integer UPDATE_MONEY_LOCK = 0;
	
	@Autowired
	private UserMoneyInAndOutDao accountChangeRecordDao;
	
	@Autowired
	private EnterpriseEmployeeCapitalAccountService capitalAccountService;
	
	@Override
	public BaseDao<UserMoneyInAndOut> baseDao() {
		return accountChangeRecordDao;
	}

	@Override
	public Class<UserMoneyInAndOut> getClazz() {
		return UserMoneyInAndOut.class;
	}
	/**
	 * 根据员工编码,添加对应的账户变动记录
	 * @param map
	 */
	public void saveMoneyInAndOutRecord(UserMoneyInAndOut object) throws Exception{
		accountChangeRecordDao.insertAccountChangeRecord(AttrCheckout.checkout(object, false, new String[]{"employeecode","parentemployeecode","gametype","moneyinoutamount","moneyinoutcomment","beforebalance","updatecapital"}));
	}
	
	/**
	 * 根据员工编码和游戏类型，对所有记录标记为已下分
	 * @param map
	 */
	public int updateIsdown(UserMoneyInAndOut object) throws Exception {
		return accountChangeRecordDao.updateIsdown(object);
	}
	/**
	 * 查询上下分记录
	 * @param object
	 * @return
	 */
	@Override
	public List<UserMoneyInAndOut> findMoneyInAndOut(Map<String, Object> object) throws Exception {
		return accountChangeRecordDao.findMoneyInAndOut(object);
	}
	
	/**
	 * 统计上下分记录
	 * @param object
	 * @return
	 */
	@Override
	public Map<String, Object> findMoneyInAndOutCount(Map<String, Object> object) throws Exception {
		return accountChangeRecordDao.findMoneyInAndOutCount(object);
	}
	
	/**
	 * 查询上下分记录
	 * @param object
	 * @return
	 */
	@Override
	public List<UserMoneyInAndOut> findMoneyInAndOutWarn(Map<String, Object> object) throws Exception {
		return accountChangeRecordDao.findMoneyInAndOutWarn(object);
	}
	/**
	 * 统计上下分记录
	 * @param object
	 * @return
	 */
	@Override
	public Map<String, Object> findMoneyInAndOutCountWarn(Map<String, Object> object) throws Exception {
		return accountChangeRecordDao.findMoneyInAndOutCountWarn(object);
	}
	
	/**
	 * 查找最后一条上分记录
	 * @param object
	 * @return
	 */
	public UserMoneyInAndOut findMaxUpRecord(String employeecode)throws Exception {
		return accountChangeRecordDao.findMaxUpRecord(employeecode);
	}

	/**
	 * 上分失败返还
	 */
	@Override
	public int tc_back_losemoney(String moneyinoutcodec,String moneychangedesc) throws Exception {
		UserMoneyInAndOut __money_in_out = super.selectByPrimaryKey(moneyinoutcodec);
		if(__money_in_out.getBackmoney()==Enum_backmoney.否.code.byteValue()
				&& __money_in_out.getOpreatetype()==Enum_opreatetype.上分.code.byteValue()
				&& __money_in_out.getUpdatecapital() != Enum_updatecapital.是.value.byteValue()){
			
			UPDATE_MONEY_LOCK = accountChangeRecordDao.updateBackState(__money_in_out.getMoneyinoutcode());//更新标记为已返回金额
			synchronized(UPDATE_MONEY_LOCK){
				if(UPDATE_MONEY_LOCK==1){
					capitalAccountService.tc_updateCapitalAccount(__money_in_out.getMoneyinoutcode(), __money_in_out.getEmployeecode(), 
							__money_in_out.getMoneyinoutamount().negate(), new EmployeeMoneyChangeType(Enum_moneychangetype.上分失败返还.value, Enum_moneyinouttype.进账), moneychangedesc);
				}
			}
		}else{
			throw new LogicTransactionRollBackException("该笔上分金额返还条件不足,禁止操作");
		}
		return 1;
	}

	@Override
	public int updateInOutState(UserMoneyInAndOut inout)  throws Exception {
		return accountChangeRecordDao.updateInOutState(AttrCheckout.checkout(inout, false, new String[]{"moneyinoutcode","updatecapital","afterbalance","moneyinoutcomment","orderno","patchno","employeecode"}));
	}
	
	
	
	/********************************************************************************************************************************************************************************/
	
	/**
	 * 转分
	 * @param gametype
	 * @param employeecode
	 * @param listGame1
	 * @return
	 * @throws Exception
	 */
	public void transfer(String gametype,String employeecode,List<Game> listGame1) throws Exception {
		
		//获取本批次号
		String patchno = OrderNewUtil.getPatchno();
		TLogger.getLogger().Error("=======================用户:"+employeecode+" 开始进行游戏转分...批次号："+patchno);
		Map<String,EmployeeApiAccout>  eapiaccounts = SystemCache.getInstance().getEmployeeAllGameAccount(employeecode);
//			List<String> brand_open_game = brandOpenGame(games);
		BigDecimal beforeAmount = new BigDecimal(0);
		if(eapiaccounts!=null){
					
			
			/******************************************************新下分逻辑（对最后一次游戏下分） **************************************************/
			/*
			//	不存在时可能是因为服务器重启过。此时查询一次数据库进行确认
			if( !APIServiceUtil.mapLastLoginGame.containsKey(employeecode)) {
				UserMoneyInAndOut temp = moneyInAndOutService.findMaxUpRecord(employeecode);
				if(temp != null) {
					APIServiceUtil.mapLastLoginGame.put(employeecode, temp);
				}
			}
			*/
			
			//	开始处理
//				if( APIServiceUtil.mapLastLoginGame.containsKey(employeecode) ) {
//					String gametypeLast = APIServiceUtil.mapLastLoginGame.get(employeecode).getGametype();
//				else {
//					TLogger.getLogger().Error("用户:"+employeecode+" 游戏转分逻辑中没有找到该人最后一次上分记录，说明还没进入过任何游戏，跳过下分...批次号："+patchno);
//				}
			
			/****************	查询目前没有进行下分的所有游戏 *********************/
			Map<String,UserMoneyInAndOut> listGametype = SystemCache.getInstance().getEmployeeAllGameUP(employeecode);
			System.out.println("#############################当前还没进行下分的游戏有："+listGametype);
				
			for (EmployeeApiAccout e : eapiaccounts.values()) {
				
				if(listGametype.containsKey(e.getGametype())){//只对最后一次的游戏进行下分，其他不处理
					
					//本次上分目标与最后一次相同时，则不下分
					if(e.getGametype().equals(gametype)) {
						
						TLogger.getLogger().Error("=======================用户:"+employeecode+" "+e.getGametype()+"最后一次上分游戏是"+gametype+"，本次上分目标是："+gametype+"，游戏类型相同，本次转分不进行下分，直接上分...批次号："+patchno);
						continue;
					}
					
					//特别处理东海国际
					BigDecimal balance = new BigDecimal("1");
					try {
						//获取游戏账户余额
						JSONObject object = JSONObject.fromObject( GameAPI.getBalance(e.getGameaccount(), e.getGamepassword(), e.getGametype(), e.getEnterprisecode()) );
						
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
							boolean success = downIntegral(balance,balance.intValue(),e.getGametype(),e, patchno);
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
					
				} else {
					TLogger.getLogger().Error("用户:"+employeecode+" "+e.getGametype()+"其他游戏跳过不下分...批次号："+patchno);
				}
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
			TLogger.getLogger().Error("用户:"+employeecode+" 正在对游戏"+ea.getGametype()+" 进行游戏上分,当前资金余额："+accountfund.intValue()+"...批次号："+patchno);
			
			upIntegral(beforeAmount,accountfund.intValue(),gametype,ea, patchno);
			
			
		} else {
			TLogger.getLogger().Error("用户:"+employeecode+" 正在对游戏"+gametype        +" 进行游戏上分,当前资金余额："+accountfund.intValue()+"，但当前此人账户余额只有"+accountfund.doubleValue()+"，所以不进行上分...批次号："+patchno);
		}
		SystemCache.getInstance().setEmployeeAllGameAccount(employeecode, eapiaccounts);
		TLogger.getLogger().Error("=======================用户:"+employeecode+" 游戏转分结束..."+"...批次号："+patchno);
		TLogger.getLogger().Error("");
	}
	
	/**
	 * 游戏上分
	 * @param orderamount 金额
	 * @param gametype 游戏类型
	 * @param employeecode 用户编码
	 * @return
	 * @throws Exception
	 */
	public boolean upIntegral(BigDecimal beforeAmount,int orderamount,String gametype ,EmployeeApiAccout ea, String patchno) throws Exception{
		
		
		/*********获取上分的订单号**********/
		String ordernumber = OrderNewUtil.getOrdernoUP();
		
		long timesort = RandomString.SORTTIME();
		
		TLogger.getLogger().Error("用户:"+ea.getEmployeecode()+" 准备上分..批次号："+patchno);
		
		//扣除账户金额
		capitalAccountService.tc_updateCapitalAccount(patchno,ea.getEmployeecode(), new BigDecimal(orderamount).negate(),
				new EmployeeMoneyChangeType(Enum_moneychangetype.游戏上分.value,Enum_moneychangetype.游戏上分.desc,Enum_moneyinouttype.出账),"操作人:API "+Enum_moneychangetype.游戏上分.desc+"【"+gametype+"】 批次号："+patchno);
		TLogger.getLogger().Error("用户:"+ea.getEmployeecode()+" 扣除用户账户资金,金额："+orderamount+"...批次号："+patchno);
		
		
		//预先记录上分日志
		UserMoneyInAndOut umiao = new UserMoneyInAndOut(Enum_opreatetype.上分,ea.getEmployeecode(),ea.getParentemployeecode(),gametype,new BigDecimal(orderamount).negate(),
				"",beforeAmount,new BigDecimal("0"),Enum_updatecapital.请求中,timesort, ordernumber, patchno, ea.getEnterprisecode(), ea.getBrandcode());
		accountChangeRecordDao.insertAccountChangeRecord(umiao);
		
		//API接口上分
		JSONObject object = JSONObject.fromObject( GameAPI.deposit(ea.getGameaccount(), ea.getGamepassword(), gametype, ea.getEnterprisecode(), ordernumber, orderamount) );
		
		TLogger.getLogger().Error("用户:"+ea.getEmployeecode()+"  " +gametype+" 游戏上分结果:"+object+"...批次号："+patchno);
		
		
		//成功
		if(object.getString("code").equals("0")){
			
			//获取游戏账户余额
			JSONObject object__balance = JSONObject.fromObject( GameAPI.getBalance(ea.getGameaccount(), ea.getGamepassword(), ea.getGametype(), ea.getEnterprisecode()) );
			
			if(object__balance.getString("code").equals("0")){
				
				BigDecimal __balancecurren = new BigDecimal(object__balance.getString("info"));
				
				int count = accountChangeRecordDao.updateInOutState(new UserMoneyInAndOut(umiao.getMoneyinoutcode(), "success", __balancecurren, Enum_updatecapital.是, ordernumber, patchno, ea.getEmployeecode() ));
				
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
				
				int count = accountChangeRecordDao.updateInOutState(new UserMoneyInAndOut(umiao.getMoneyinoutcode(), "上分成功，但二次查询余额出错："+object__balance, __balancecurren, Enum_updatecapital.是, ordernumber, patchno, ea.getEmployeecode()));
				
				TLogger.getLogger().Error("用户:"+ea.getEmployeecode()+"  " +gametype+" 更新状态成功.count="+count+",moneyinoutcode="+umiao.getMoneyinoutcode()+"。但二次查询余额出错："+object__balance+"，上分前余额:"+beforeAmount+"，本次上分金额："+orderamount+"...批次号："+umiao.getPatchno());
			}
			
			/****************	重要：上分成功需要记录 *********************/
			APIServiceUtil.mapLastLoginGame.put(umiao.getEmployeecode(), umiao);
			
			
			/****************	重要：上分成功需要记录 *********************/
			try {
				SystemCache.getInstance().addEmployeeAllGameUP(umiao.getEmployeecode(), umiao);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return true;
		}
		//失败
		else {
			
			//查询订单校验
			if( !gametype.equals(GameEnum.Enum_GameType.MG.gametype) && !gametype.equals(GameEnum.Enum_GameType.棋牌.gametype)) {//只有MG不支持订单查询
				
				JSONObject object_order = JSONObject.fromObject( GameAPI.getOrder(ea.getGameaccount(), ea.getGamepassword(), gametype, ea.getEnterprisecode(), ordernumber, orderamount) );
				if(object_order.getString("code").equals("0")){
					//订单是成功的
					accountChangeRecordDao.updateInOutState(new UserMoneyInAndOut(umiao.getMoneyinoutcode(), "上分成功，但上分返回结果不为0："+object, new BigDecimal(orderamount).negate(), Enum_updatecapital.是, ordernumber, patchno, ea.getEmployeecode()));
					
					
					/****************	重要：上分成功需要记录 *********************/
					try {
						SystemCache.getInstance().addEmployeeAllGameUP(umiao.getEmployeecode(), umiao);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					return true;
					
				} else {
					
					accountChangeRecordDao.updateInOutState(new UserMoneyInAndOut(umiao.getMoneyinoutcode(), "上分失败："+object, beforeAmount, Enum_updatecapital.否, ordernumber, patchno, ea.getEmployeecode()));
					TLogger.getLogger().Error("用户:"+ea.getEmployeecode()+"  " +gametype+" 更新状态失败。当前余额："+beforeAmount+"，上分前余额:"+beforeAmount+"，本次上分金额："+orderamount+"...批次号："+umiao.getPatchno());
					
					/*************************上分失败的话账户金额再加回来
					capitalAccountService.tc_updateCapitalAccount(patchno,ea.getEmployeecode(), new BigDecimal(orderamount),
							new EmployeeMoneyChangeType(Enum_moneychangetype.游戏上分.value,Enum_moneychangetype.游戏上分.desc,Enum_moneyinouttype.进账),"操作人:API "+Enum_moneychangetype.游戏上分.desc+gametype+"失败，返回金额. 批次号："+patchno);
					TLogger.getLogger().Error("用户:"+ea.getEmployeecode()+" 退回用户账户资金,金额："+orderamount+"...批次号："+patchno);
					******************************/
					
					return false;
				}
				
				
			} else {//如果是MG
				
				accountChangeRecordDao.updateInOutState(new UserMoneyInAndOut(umiao.getMoneyinoutcode(), "上分失败："+object, beforeAmount, Enum_updatecapital.否, ordernumber, patchno, ea.getEmployeecode()));
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
	public boolean downIntegral(BigDecimal beforeAmount,int downIntegralAmount,String gametype ,EmployeeApiAccout eaa, String patchno) throws Exception{
		
		/*********获取下分的订单号**********/
		String ordernumber = OrderNewUtil.getOrdernoDOWN();
		
		
		long timesort = RandomString.SORTTIME();
		
		UserMoneyInAndOut user_in_out = new UserMoneyInAndOut(Enum_opreatetype.下分,eaa.getEmployeecode(),eaa.getParentemployeecode(),eaa.getGametype(),new BigDecimal(downIntegralAmount),
				"",beforeAmount,new BigDecimal("0"),Enum_updatecapital.请求中,timesort,ordernumber, patchno , eaa.getEnterprisecode(), eaa.getBrandcode());
		//记录转分信息
		accountChangeRecordDao.insertAccountChangeRecord(user_in_out);
		
		//进行下分操作
		JSONObject object = JSONObject.fromObject( GameAPI.withdraw(eaa.getGameaccount(), eaa.getGamepassword(), gametype, eaa.getEnterprisecode(), ordernumber, downIntegralAmount) );
		TLogger.getLogger().Error("用户:"+eaa.getEmployeecode()+"  游戏:"+gametype+" 游戏下分结果:"+object+" 批次号："+patchno);
		
		
		//下分后余额
		BigDecimal __balancecurren = null;
		JSONObject object__balance = JSONObject.fromObject( GameAPI.getBalance(eaa.getGameaccount(), eaa.getGamepassword(), eaa.getGametype(), eaa.getEnterprisecode()) );
		if(object__balance.getString("code").equals("0")) {
			__balancecurren = new BigDecimal(object__balance.getString("info"));
		} else {
			__balancecurren = new BigDecimal("0");
		}
		TLogger.getLogger().Error("用户:"+eaa.getEmployeecode()+"  游戏:"+gametype+" 游戏下分后余额:"+object__balance+" 批次号："+patchno);
		
		
		if( object.getString("code").equals("0")) {
			
			TLogger.getLogger().Error("用户:"+eaa.getEmployeecode()+"  游戏:"+eaa.getGametype()+" 下分成功, 查询余额结果:"+object__balance+" 批次号："+patchno);
			
			//进行资金操作
			capitalAccountService.tc_updateCapitalAccount(patchno,eaa.getEmployeecode(), new BigDecimal(downIntegralAmount),
					new EmployeeMoneyChangeType(Enum_moneychangetype.游戏下分.value,Enum_moneychangetype.游戏下分.desc, Enum_moneyinouttype.进账),"操作人:API "+Enum_moneychangetype.游戏下分.desc+"【"+gametype+"】 批次号："+patchno);
			
			
			
			//修改上下分状态与下分后余额
			accountChangeRecordDao.updateInOutState(new UserMoneyInAndOut(user_in_out.getMoneyinoutcode(),!object__balance.getString("code").equals("0")? "下分成功但没有查询到最新余额。" : "success", __balancecurren, Enum_updatecapital.是, ordernumber, patchno, eaa.getEmployeecode()));
			
			
			TLogger.getLogger().Error("用户:"+eaa.getEmployeecode()+"  游戏:"+eaa.getGametype()+" 下分成功，已进行资金转账操作！ 批次号："+patchno);
			
			
			/****************	重要：下分成功需要移除 *********************/
			try {
				
				SystemCache.getInstance().removeEmployeeAllGameUP(eaa.getEmployeecode(), new UserMoneyInAndOut( eaa.getEmployeecode(),  eaa.getEnterprisecode(),  gametype) );
				
				//修改该游戏类型的所有记录为已下分
				accountChangeRecordDao.updateIsdown(new UserMoneyInAndOut( eaa.getEmployeecode(),  eaa.getEnterprisecode(),  gametype));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return true;
			
		} else {
			
			TLogger.getLogger().Error("用户:"+eaa.getEmployeecode()+"  游戏:"+eaa.getGametype()+" 下分失败,下分结果:"+object+" ,查询余额结果:"+object__balance+" 批次号："+patchno);
			//修改下分状态与下分后余额
			accountChangeRecordDao.updateInOutState(new UserMoneyInAndOut(user_in_out.getMoneyinoutcode(),object.getString("info"), __balancecurren,Enum_updatecapital.否, ordernumber, patchno, eaa.getEmployeecode()));
			return false;
			
		}
		
	}



}
