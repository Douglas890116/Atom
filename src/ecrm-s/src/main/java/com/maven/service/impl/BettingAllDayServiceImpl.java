package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hy.pull.common.util.DateUtil;
import com.hy.pull.common.util.MoneyHelper;
import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.dao.BettingAllDayDao;
import com.maven.dao.BettingAllGameWinloseDetailDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.BettingAllDay;
import com.maven.entity.BettingAllDay.Enum_status;
import com.maven.entity.BettingAllDay2;
import com.maven.entity.BettingAllGameWinloseDetail;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.entity.EmployeeMoneyChangeType.moneychangetypeCategory;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeCapitalAccount;
import com.maven.entity.ReportWeeklyMember;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.EnterpriseEmployeeType.Type;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.BettingAllDayService;
import com.maven.service.BettingAllGameWinloseDetailService;
import com.maven.service.EmployeeMoneyChangeService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.util.RandomString;
import com.maven.util.StringUtils;

@Service
public class BettingAllDayServiceImpl extends BaseServiceImpl<BettingAllDay> implements BettingAllDayService{

	@Autowired
	private BettingAllDayDao BettingAllDayDao;
	@Autowired
	private EnterpriseEmployeeCapitalAccountService enterpriseEmployeeCapitalAccountService;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private ActivityBetRecordService activityBetRecordService;
	@Autowired
	private EmployeeMoneyChangeService employeeMoneyChangeService;
	@Autowired
	private BettingAllGameWinloseDetailDao bettingAllGameWinloseDetailDao;
	
	@Override
	public BaseDao<BettingAllDay> baseDao() {
		return BettingAllDayDao;
	}

	@Override
	public Class<BettingAllDay> getClazz() {
		return BettingAllDay.class;
	}
	
	/**
	 * 做汇总计划
	 * 
	 * 将投注明细数据汇总到bettingAllDay表
	 * @param planDate 要汇总的日期
	 * @param newPatchNo 汇总后的批次号
	 * 
	 * @throws Exception
	 */
	@Override
	public void updateDoPlan(String planDate,String newPatchNo) throws Exception {
		
		/*******************1= 先汇总所有明细数据到汇总表*******************/
		Map<String, Object> paramObj = new HashMap<String, Object>();
		paramObj.put("bettime", planDate);
		List<BettingAllGameWinloseDetail> __list = bettingAllGameWinloseDetailDao.selectGroup(paramObj);
		
		Map<String, EnterpriseEmployee> mapUser = new HashMap<String, EnterpriseEmployee>();
		
		Date addTime = new Date();
		Date betDay = DateUtil.parse(planDate, "yyyyMMdd");
		EnterpriseEmployee __ee = null;
		String enterprisecode, brandcode, employeecode, userName ,parentemployeecode = null;
		
		List<BettingAllDay> __listAdd = new ArrayList<BettingAllDay>();
		for (BettingAllGameWinloseDetail data : __list) {
			
			//将投注额、输赢额、有效投注额全为0的数据排除掉（GG扑克有很多这种没用的数据）
			if(data.getBetmoney() == 0 && data.getNetmoney() == 0 && data.getValidbet() == 0) {
				//
				continue;
			} else {
				
				employeecode = data.getEmployeecode();
				if(mapUser.containsKey(employeecode)) {
					__ee = mapUser.get(employeecode);
				} else {
					__ee = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
				}
				enterprisecode = __ee.getEnterprisecode();
				brandcode = __ee.getBrandcode();
				parentemployeecode = __ee.getParentemployeecode();
				userName = __ee.getLoginaccount();
				
				__listAdd.add(new BettingAllDay( enterprisecode,   brandcode, 
						employeecode, parentemployeecode, userName, data.getGametype(), data.getGamebigtype(),
						null, betDay, data.getBetmoney(), data.getNetmoney(), data.getValidbet(), addTime, newPatchNo));
			}
		}
		
		if(__listAdd.size() > 0) {
			BettingAllDayDao.saveRecordBatch(__listAdd);
		}
		System.out.println("updateDoPlan=生成汇总记录数"+__listAdd.size());
		
		/*******************2= 将汇总的明细数据标记批次号*******************/
		paramObj.clear();
		paramObj.put("bettime", planDate);
		paramObj.put("patchno", newPatchNo);
		int count = bettingAllGameWinloseDetailDao.updateByPatchno(paramObj);
		System.out.println("updateDoPlan=回写单号数"+count);
	}
	

	/**
	 * 数据统计之金额统计
	 * @param object
	 * @return
	 */
	@Override
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception {
		return BettingAllDayDao.takeRecordCountMoney(object);
	}
	
	/**
	 * 用户输钱排名
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<BettingAllDay> call_userLoseRanking(Map<String, Object> paramObj) throws Exception {
		return BettingAllDayDao.queryUserLoseRanking(paramObj);
	}
	
	/**
	 * 用户赢钱排名
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<BettingAllDay> call_userWinRanking(Map<String, Object> paramObj) throws Exception {
		return BettingAllDayDao.queryUserWinRanking(paramObj);
	}
	
	/**
	 * 查询用户输赢游戏记录
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<BettingAllDay> queryUserLoseWinGameRecord(Map<String, Object> paramObj) throws Exception {
		return BettingAllDayDao.queryUserLoseWinGameRecord(paramObj);
	}
	/**
	 * 统计用户输赢游戏记录
	 * @param paramObj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int queryCountUserLoseWinGameRecord(Map<String, Object> paramObj) throws Exception {
		return BettingAllDayDao.countUserLoseWinGameRecord(paramObj);
	}

	/**
	 * 查询会员编码
	 * @param paramObj
	 * @return
	 */
	@Override
	public List<BettingAllDay> selectBettingByDate(Map<String, Object> paramObj) throws Exception {
		return BettingAllDayDao.selectBettingByDate(paramObj);
	}
	
	/**
	 * 根据主键更新
	 * @param bettingAllDay
	 * @throws Exception
	 */
	@Override
	public void updateByPrimary(BettingAllDay bettingAllDay) throws Exception {
		this.BettingAllDayDao.updateByPrimary(bettingAllDay);
	}

	/**
	 * 分页查询
	 * @param paramObj
	 * @throws Exception
	 */
	@Override
	public List<BettingAllDay> selectForPage(Map<String, Object> paramObj) throws Exception {
		return this.BettingAllDayDao.selectForPage(paramObj);
	}
	
	/**
	 * 分页查询 记录总数
	 * @param paramObj
	 * @throws Exception
	 */
	@Override
	public int selectForPageCount(Map<String, Object> paramObj) throws Exception {
		return this.BettingAllDayDao.selectForPageCount(paramObj);
	}

	/**
	 * 根据参数查询
	 * @param paramObj
	 * @throws Exception
	 */
	@Override
	public List<BettingAllDay> select(Map<String, Object> paramObj) throws Exception {
		return this.BettingAllDayDao.select(paramObj);
	}
	
	/**
	 * 取消支付计划
	 * @param paramObj
	 * @throws Exception
	 */
	@Override
	public String updateDailyPlan(List<BettingAllDay> processList) throws Exception {
		StringBuffer result = new StringBuffer();
		
		Map<String, String> paramsEmployee = new HashMap<String, String>();
		String payno = null;
		
		//1=回滚账变金额
		//2=批量更新发放标志和单号
		EmployeeMoneyChangeType type =new EmployeeMoneyChangeType();
		type.setMoneychangetypeclassify(moneychangetypeCategory.常规.value);
		type.setMoneychangetypecode(Enum_moneychangetype.洗码冲减.value);
		type.setMoneyinouttype((byte)Enum_moneyinouttype.出账.value);
		
		Map<String, Object> deleBetParams = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		for (BettingAllDay data : processList) {
			if(data == null) {
				continue;
			}
			
			if(payno == null) {
				payno = "C"+data.getEnterprisecode().substring(4, 6) + StringUtils.getCurrenDate();
				result.append("新单号："+payno);
			}
			params.put("ordernumber", data.getPayno());
			params.put("employeecode", data.getEmployeecode());
			params.put("enterprisecode", data.getEnterprisecode());
			params.put("moneychangetypecode", Enum_moneychangetype.洗码日结.value);
			params = employeeMoneyChangeService.findAccountChangeCount(params);
			
			//冲减额
			double moneychangeamount = 0;
			try {
				
				moneychangeamount = params.get("moneychangeamount") == null ? 0 : Double.valueOf(params.get("moneychangeamount").toString());
				moneychangeamount = MoneyHelper.moneyFormatDouble(moneychangeamount);
				
				
				if(moneychangeamount > 0 && !paramsEmployee.containsKey(data.getEmployeecode())) {
					//没有金额需要处理就不处理
					paramsEmployee.put(data.getEmployeecode(), moneychangeamount+"");
					
					java.math.BigDecimal _acc_money = new java.math.BigDecimal( moneychangeamount+"" ).negate();//出账为负数
					
					EnterpriseEmployeeCapitalAccount ea = this.enterpriseEmployeeCapitalAccountService.takeCurrencyAccount(data.getEmployeecode());
					if(ea.getBalance().doubleValue() < _acc_money.negate().doubleValue()) {
						String line = "取消日会员计划余额不足："+data.getUserName() + ", "+data.getEmployeecode()+ ",总发放金额："+moneychangeamount+",当前余额："+ea.getBalance().doubleValue();
						System.out.println(line);
						result.append("<br />"+line);
					} else {
						this.enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(payno, data.getEmployeecode(), _acc_money, type, "日洗码冲减"+data.getUserName());
						
						//信用代模式需要扣上级代理的钱
						EnterpriseEmployee ee = enterpriseEmployeeService.takeEmployeeByCode(data.getParentemployeecode());
						if(ee == null) {
							result.append("<br />"+data.getUserName()+"未能找到此人的上级账号信息");
							continue;
						}
						if(ee.getEmployeetypecode().equals(Type.信用代理.value)) {
							EmployeeMoneyChangeType a_type =new EmployeeMoneyChangeType();
							//活动类别
							a_type.setMoneychangetypeclassify(moneychangetypeCategory.常规.value);
							//活动类型
							a_type.setMoneychangetypecode(Enum_moneychangetype.洗码冲减.value);
							//充值类型
							a_type.setMoneyinouttype((byte)Enum_moneyinouttype.进账.value);
							this.enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(payno, data.getParentemployeecode(), _acc_money.negate(), a_type, "日洗码冲减"+data.getUserName());
						}
					}
				}
				
				//清除打码记录
				deleBetParams.put("employeecode", data.getEmployeecode());
				deleBetParams.put("ordernumber", data.getPayno());
				activityBetRecordService.deleteByConditions(deleBetParams);
				
				//回写状态
				if( paramsEmployee.containsKey(data.getEmployeecode())) {
					data.setStatus(Enum_status.未发放.value);
					data.setPayno(payno);
					this.updateByPrimary(data);
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				String line = "取消日会员计划失败："+data.getUserName() + " "+data.getEmployeecode()+ " 回滚金额："+moneychangeamount+" 原因："+e.getMessage();
				System.out.println(line);
				result.append("<br />"+line);
				continue;
			}
		}
		return result.toString();
	}

	/**
	 * 发放list中的所有洗码记录
	 * @param paramObj
	 * @throws Exception
	 */
	@Override
	public String updateProcessDaily(List<BettingAllDay> processList) throws Exception {
		StringBuffer result = new StringBuffer();
		//步骤 1判断当条洗码记录是否发放过 2更新洗码记录状态为发放 3增加用户金额 4增加账变日志
		
		//支付单号
		String payno = null;
		int totalCount = 0;
		double totalMoney = 0;
		
		for (BettingAllDay bad : processList) {
			if(payno == null) {
				payno = bad.getEnterprisecode().substring(4, 6) + StringUtils.getCurrenDateyyyyMMddHHmmss();
				result.append("生成支付单号："+payno);
			}
			bad.setPayno(payno);//必须要传入单号
			totalCount ++;
			if (bad.getStatus().equals(Enum_status.已发放.value)) {
				continue;
			}
			if (bad.getRebatesCash() == null || bad.getRebatesCash()<= 0){
				bad.setStatus(Enum_status.已发放.value);
				this.updateByPrimary(bad);
				continue;
			}
			
			EnterpriseEmployee ee = enterpriseEmployeeService.takeEmployeeByCode(bad.getParentemployeecode());
			if(ee == null) {
				result.append("<br />"+bad.getUserName()+"未能找到此人的上级账号信息");
				continue;
			}
			if(ee.getEmployeetypecode().equals(Type.信用代理.value)) {//信用代模式需要扣上级代理的钱
				EmployeeMoneyChangeType a_type =new EmployeeMoneyChangeType();
				//活动类别
				a_type.setMoneychangetypeclassify(moneychangetypeCategory.常规.value);
				//活动类型
				a_type.setMoneychangetypecode(Enum_moneychangetype.洗码日结.value);
				//充值类型
				a_type.setMoneyinouttype((byte)Enum_moneyinouttype.出账.value);
				this.enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(payno, bad.getParentemployeecode(), new BigDecimal(-bad.getRebatesCash()), a_type, "发放洗码"+bad.getUserName());
			}
			
			bad.setStatus(Enum_status.已发放.value);
			this.updateByPrimary(bad);
			
			EmployeeMoneyChangeType type =new EmployeeMoneyChangeType();
			type.setMoneychangetypeclassify(moneychangetypeCategory.常规.value);
			type.setMoneychangetypecode(Enum_moneychangetype.洗码日结.value);
			type.setMoneyinouttype((byte)Enum_moneyinouttype.进账.value);
			/******检查并创建资金账户
			EnterpriseEmployeeCapitalAccount ec = enterpriseEmployeeCapitalAccountService.takeCurrencyAccount(bad.getEmployeecode());
			if(ec == null){
				enterpriseEmployeeCapitalAccountService.tc_saveEmployeeCapitalAccount(
						new EnterpriseEmployeeCapitalAccount(bad.getEmployeecode(), bad.getParentemployeecode()));
			} 
			*****/
			totalMoney += bad.getRebatesCash();
			this.enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(payno, bad.getEmployeecode(), new BigDecimal(bad.getRebatesCash()), type, "发放洗码"+bad.getUserName());
			
			
			//加入打码
			ActivityBetRecord betrecord = new ActivityBetRecord();
			betrecord.setOrdernumber(payno);
			betrecord.setEmployeecode(bad.getEmployeecode());
			betrecord.setEcactivitycode(Enum_ecactivitycode.返水所需流水.value);
			betrecord.setMustbet( (bad.getRebatesCash()) * 1);
			betrecord.setAlreadybet(0.0);
			betrecord.setBetrecordstatus(Enum_betrecordstatus.未完成.value);
			betrecord.setCreatetime(new Date());
			betrecord.setLoginaccount(bad.getUserName());
			betrecord.setRecharge(0.0);//充值金额
			betrecord.setEnterprisecode(bad.getEnterprisecode());//企业编码
			betrecord.setBrandcode(bad.getBrandcode());//品牌编码
			betrecord.setParentemployeeaccount(ee.getParentemployeeaccount());//上级账号
			betrecord.setParentemployeecode(bad.getParentemployeecode());//上级编码
			activityBetRecordService.addActivityBetRecord(betrecord);
					
		}
		totalMoney = MoneyHelper.moneyFormatDouble(totalMoney);
		result.append("<br />实际发放人次："+totalCount+"人次；");
		result.append("<br />实际发放洗码额："+totalMoney+"元；");
		return result.toString();
	}
	
}
