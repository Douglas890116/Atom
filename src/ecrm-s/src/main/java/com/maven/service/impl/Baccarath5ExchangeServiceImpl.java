package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hy.pull.common.util.MoneyHelper;
import com.hy.pull.common.util.api.GameAPI;
import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.cache.SystemCache;
import com.maven.constant.Enum_MSG;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.Baccarath5ExchangeDao;
import com.maven.dao.EnterpriseOperatingBrandDao;
import com.maven.dao.UserMoneyInAndOutDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.Baccarath5Balance;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.Baccarath5Exchange;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.Baccarath5Exchange.Enum_exchangeStatus;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.entity.UserMoneyInAndOut.Enum_opreatetype;
import com.maven.entity.UserMoneyInAndOut.Enum_updatecapital;
import com.maven.game.APIServiceNew;
import com.maven.game.APIServiceUtil;
import com.maven.game.OrderNewUtil;
import com.maven.game.enums.GameEnum;
import com.maven.game.enums.GameEnum.Enum_GameType;
import com.maven.logger.TLogger;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.GameApiInput;
import com.maven.entity.TakeDepositRecord;
import com.maven.entity.UserLogs;
import com.maven.entity.UserMoneyInAndOut;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.Baccarath5BalanceService;
import com.maven.service.Baccarath5ExchangeService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseService;
import com.maven.util.RandomString;

import net.sf.json.JSONObject;

@Service
public class Baccarath5ExchangeServiceImpl extends BaseServiceImpl<Baccarath5Exchange> implements Baccarath5ExchangeService {

	@Autowired
	private Baccarath5ExchangeDao activityBetRecordDao;
	@Autowired
	private Baccarath5BalanceService baccarath5BalanceService;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private UserMoneyInAndOutDao accountChangeRecordDao;
	@Autowired
	private EnterpriseEmployeeCapitalAccountService capitalAccountService;
	
	@Override
	public BaseDao<Baccarath5Exchange> baseDao() {
		return activityBetRecordDao;
	}

	@Override
	public Class<Baccarath5Exchange> getClazz() {
		return Baccarath5Exchange.class;
	}

	@Override
	public List<Baccarath5Exchange> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecord(parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecordCount(parameter);
	}
	
	@Override
	public Map<String, Object> selectBetRecordCountMoney(Map<String, Object> parameter) throws Exception {
		return activityBetRecordDao.selectBetRecordCountMoney(parameter);
	}
	
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@Override
	public void addActivityBetRecord(Baccarath5Exchange activityBetRecord) throws Exception {
		activityBetRecordDao.addBetRecord(activityBetRecord);
	}

	/**
	 * 代理转出积分到玩家
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@Override
	public void tc_transfer(String enterprisecode,String form_employeecode,String to_employeecode,double money) throws Exception {
		
		String patchno = OrderNewUtil.getPatchno();
		//减去账户金额
		capitalAccountService.tc_updateCapitalAccount(patchno, form_employeecode, new BigDecimal(money).negate(),
								new EmployeeMoneyChangeType(Enum_moneychangetype.转出金额.value,Enum_moneychangetype.转出金额.desc,Enum_moneyinouttype.出账),"操作人: "+Enum_moneychangetype.转出金额.desc+" 批次号："+patchno);
		
		//增加账户金额
		capitalAccountService.tc_updateCapitalAccount(patchno, to_employeecode, new BigDecimal(money),
								new EmployeeMoneyChangeType(Enum_moneychangetype.转入金额.value,Enum_moneychangetype.转入金额.desc,Enum_moneyinouttype.进账),"操作人: "+Enum_moneychangetype.转入金额.desc+" 批次号："+patchno);
	}
	
	/**
	 * 新元宝兑换积分
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@Override
	public void tc_dochange(String enterprisecode,String employeecode,double money) throws Exception {
		/************实施兑换过程**********/
		//0、记录兑换流水
		//1、扣减元宝
		//2、上分
		
		EnterpriseEmployee enterpriseEmployee = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
		Enterprise enterprise = enterpriseService.selectByPrimaryKey(enterprisecode);
		
		double rate = enterprise.getRate();//返回元宝兑换金币的比例
		double rate2 = enterprise.getRate2();//返回真钱和元宝之间的兑换比例
		double exchangeIn = money;
		double exchangeOut = exchangeIn * rate;
		
		//0、记录兑换流水
		String lsh = UUID.randomUUID().toString();
		Baccarath5Exchange exchange = new Baccarath5Exchange();
		exchange.setLsh(lsh);
		exchange.setBrandcode(enterpriseEmployee.getBrandcode());
		exchange.setEmployeecode(employeecode);
		exchange.setEnterprisecode(enterprisecode);
		exchange.setExchangeIn(exchangeIn);
		exchange.setExchangeOut(exchangeOut);
		exchange.setExchangeRate(rate);
		exchange.setExchangeStatus(Enum_exchangeStatus.处理中.value);//
		exchange.setExchangeTime(new Date());
		exchange.setLoginaccount(enterpriseEmployee.getLoginaccount());
		exchange.setParentemployeecode(enterpriseEmployee.getParentemployeecode());
		activityBetRecordDao.addBetRecord(exchange);
		
		
		//1、加分（同时扣减元宝）
		String patchno = OrderNewUtil.getPatchno();
		//扣减元宝余额
		baccarath5BalanceService.updateBalance(employeecode, -money);
		
		//增加账户金额
		capitalAccountService.tc_updateCapitalAccount(patchno, enterpriseEmployee.getEmployeecode(), new BigDecimal(exchangeOut),
						new EmployeeMoneyChangeType(Enum_moneychangetype.积分兑换.value,Enum_moneychangetype.积分兑换.desc,Enum_moneyinouttype.进账),"操作人: "+Enum_moneychangetype.积分兑换.desc+" 批次号："+patchno);
		
	}
	
	
	/**
	 * 元宝兑换积分
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@Override
	public void tc_exchange(String enterprisecode,String employeecode,double money) throws Exception {
		/************实施兑换过程**********/
		//0、记录兑换流水
		//1、扣减元宝
		//2、上分
		
		EnterpriseEmployee enterpriseEmployee = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
		Enterprise enterprise = enterpriseService.selectByPrimaryKey(enterprisecode);
		
		double rate = enterprise.getRate();//返回元宝兑换金币的比例
		double rate2 = enterprise.getRate2();//返回真钱和元宝之间的兑换比例
		double exchangeIn = money;
		double exchangeOut = exchangeIn * rate;
		
		//0、记录兑换流水
		String lsh = UUID.randomUUID().toString();
		Baccarath5Exchange exchange = new Baccarath5Exchange();
		exchange.setLsh(lsh);
		exchange.setBrandcode(enterpriseEmployee.getBrandcode());
		exchange.setEmployeecode(employeecode);
		exchange.setEnterprisecode(enterprisecode);
		exchange.setExchangeIn(exchangeIn);
		exchange.setExchangeOut(exchangeOut);
		exchange.setExchangeRate(rate);
		exchange.setExchangeStatus(Enum_exchangeStatus.处理中.value);//
		exchange.setExchangeTime(new Date());
		exchange.setLoginaccount(enterpriseEmployee.getLoginaccount());
		exchange.setParentemployeecode(enterpriseEmployee.getParentemployeecode());
		activityBetRecordDao.addBetRecord(exchange);
		
		
		//2、上分（同时扣减元宝）
		if(true) {
			System.out.println("调试元宝兑换：exchangeIn="+exchangeIn);
			System.out.println("调试元宝兑换：exchangeOut="+exchangeOut);
			System.out.println("调试元宝兑换：rate="+rate);
			
			BigDecimal accountfund = new BigDecimal(exchangeOut);
			String gametype = Enum_GameType.环球.gametype;
			EmployeeApiAccout eea = SystemCache.getInstance().getEmployeeGameAccount(employeecode, gametype);
			String patchno = OrderNewUtil.getPatchno();
			BigDecimal beforeAmount = new BigDecimal(0);//
			
			//调用接口上分
			double realMoney = exchangeIn / rate2;//元宝除以比例即为真钱金额
			boolean result2 = upIntegral(beforeAmount, accountfund.intValue(), gametype, eea, patchno, realMoney );
			
			if(result2) {
				exchange.setRemark("success");
				exchange.setExchangeStatus(Enum_exchangeStatus.成功.value);//
				activityBetRecordDao.update("Baccarath5Exchange.updateStatus", exchange);
				
				System.out.println("元宝兑换后上分成功");
			} else {
				exchange.setRemark("fail");
				exchange.setExchangeStatus(Enum_exchangeStatus.失败.value);//
				activityBetRecordDao.update("Baccarath5Exchange.updateStatus", exchange);
				System.out.println("元宝兑换后上分失败，请查询上分日志");
				
				//返回金额，暂时不在程序里面处理
			}
			
		}
		
	}
	
	/**
	 * 游戏上分
	 * @param orderamount 金额
	 * @param gametype 游戏类型
	 * @param employeecode 用户编码
	 * @return
	 * @throws Exception
	 */
	private boolean upIntegral(BigDecimal beforeAmount,int orderamount,String gametype ,EmployeeApiAccout ea, String patchno,double yuanbao) throws Exception{
		
		/*********获取上分的订单号**********/
		String ordernumber = OrderNewUtil.getOrdernoUP();
		
		long timesort = RandomString.SORTTIME();
		
		TLogger.getLogger().Error("用户:"+ea.getEmployeecode()+" 准备上分..批次号："+patchno);
		
		//扣除账户金额
		capitalAccountService.tc_updateCapitalAccount(patchno,ea.getEmployeecode(), new BigDecimal(yuanbao).negate(),
				new EmployeeMoneyChangeType(Enum_moneychangetype.游戏上分.value,Enum_moneychangetype.游戏上分.desc,Enum_moneyinouttype.出账),"操作人:API "+Enum_moneychangetype.游戏上分.desc+"【"+gametype+"】 批次号："+patchno);
		TLogger.getLogger().Error("用户:"+ea.getEmployeecode()+" 扣除用户账户资金,元宝："+yuanbao+"...批次号："+patchno);
		
		
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
}
