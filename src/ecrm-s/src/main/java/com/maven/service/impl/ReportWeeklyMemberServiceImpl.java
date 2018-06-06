package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hy.pull.common.util.MoneyHelper;
import com.maven.base.dao.BaseDao;
import com.maven.constant.Enum_MSG;
import com.maven.dao.ReportWeeklyMemberDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeCapitalAccount;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.entity.EmployeeMoneyChangeType.moneychangetypeCategory;
import com.maven.entity.EnterpriseEmployeeType.Type;
import com.maven.entity.ReportDailyAgent;
import com.maven.entity.ReportWeeklyAgent;
import com.maven.entity.ReportWeeklyMember;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.ReportWeeklyMember.Enum_status;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.EmployeeMoneyChangeService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.ReportWeeklyMemberService;
import com.maven.util.RandomString;
import com.maven.util.StringUtils;

@Service
public class ReportWeeklyMemberServiceImpl extends BaseServiceImpl<ReportWeeklyMember> implements ReportWeeklyMemberService {

	@Autowired
	private ReportWeeklyMemberDao reportWeeklyMemberDao;
	@Autowired
	private EnterpriseEmployeeCapitalAccountService enterpriseEmployeeCapitalAccountService;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private ActivityBetRecordService activityBetRecordService;
	@Autowired
	private EmployeeMoneyChangeService employeeMoneyChangeService;
	
	/**
	 * 数据统计之金额统计
	 * @param object
	 * @return
	 */
	@Override
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception  {
		return reportWeeklyMemberDao.takeRecordCountMoney(object);
	}
	
	/**
	 * 批量新增会员周洗码
	 */
	@Override
	public void insertBatch(List<ReportWeeklyMember> rwms) throws Exception {
		//List<ReportWeeklyMember> rwms = new ArrayList<ReportWeeklyMember>(rwmsmap.values());
		this.reportWeeklyMemberDao.insertBatch(rwms);
	}
	
	
	/**
	 * 取消支付计划
	 * @param paramObj
	 * @throws Exception
	 */
	@Override
	public String updateWeeklyPlan(List<ReportWeeklyMember> processList) throws Exception {
		StringBuffer result = new StringBuffer();
		Map<String, String> paramsEmployee = new HashMap<String, String>();
		String payno = null;
		
		//1=回滚账变金额
		//2=批量更新发放标志和单号
		EmployeeMoneyChangeType type =new EmployeeMoneyChangeType();
		type.setMoneychangetypeclassify(moneychangetypeCategory.常规.value);
		type.setMoneychangetypecode(Enum_moneychangetype.洗码冲减.value);
		type.setMoneyinouttype((byte)Enum_moneyinouttype.出账.value);
		
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> deleBetParams = new HashMap<String, Object>();
		for (ReportWeeklyMember data : processList) {
			if(data == null) {
				continue;
			}
			double actual = data.getActual() == null ? 0 : data.getActual();
			
			if(payno == null) {
				payno = "C"+data.getEnterprisecode().substring(4, 6) + StringUtils.getCurrenDate();
				result.append("新单号："+payno);
			}
			params.put("ordernumber", data.getPayno());
			params.put("employeecode", data.getEmployeecode());
			params.put("enterprisecode", data.getEnterprisecode());
			params.put("moneychangetypecode", Enum_moneychangetype.洗码周结.value);
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
						String line = "取消周会员计划余额不足："+data.getLoginaccount() + ", "+data.getEmployeecode()+ ",总发放金额："+moneychangeamount+",当前余额："+ea.getBalance().doubleValue();
						System.out.println(line);
						result.append("<br />"+line);
					} else {
						this.enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(payno, data.getEmployeecode(), _acc_money, type, "周洗码冲减"+data.getLoginaccount());
						//信用代模式需要扣上级代理的钱
						EnterpriseEmployee ee = enterpriseEmployeeService.takeEmployeeByCode(data.getParentemployeecode());
						if(ee == null) {
							result.append("<br />"+data.getLoginaccount()+"未能找到此人的上级账号信息");
							continue;
						}
						if(ee.getEmployeetypecode().equals(Type.信用代理.value)) {
							EmployeeMoneyChangeType a_type =new EmployeeMoneyChangeType();
							a_type.setMoneychangetypeclassify(moneychangetypeCategory.常规.value);
							a_type.setMoneychangetypecode(Enum_moneychangetype.洗码冲减.value);
							a_type.setMoneyinouttype((byte)Enum_moneyinouttype.进账.value);
							this.enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(payno, data.getParentemployeecode(), _acc_money.negate(), a_type, "周洗码冲减"+data.getLoginaccount());
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
					data.setSubtract(MoneyHelper.moneyFormatDouble(data.getAmount() - actual));
					data.setPayno(payno);
					this.reportWeeklyMemberDao.updateByPrimary(data);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				String line = "取消周会员计划失败："+data.getLoginaccount() + " "+data.getEmployeecode()+ " 回滚金额："+moneychangeamount+" 原因："+e.getMessage();
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
	public String updateProcessWeekly(List<ReportWeeklyMember> processList) throws Exception {
		StringBuffer result = new StringBuffer();
		//步骤 1判断当条洗码记录是否发放过 2更新洗码记录状态为发放 3增加用户金额 4增加账变日志
		
		//支付单号
		String payno = null;
		int totalCount = 0;
		double totalMoney = 0;
		
		for (ReportWeeklyMember rwm : processList) {
			
			if(payno == null) {
				payno = rwm.getEnterprisecode().substring(4, 6) + StringUtils.getCurrenDateyyyyMMddHHmmss();
				result.append("生成支付单号："+payno);
			}
			
			if (Enum_status.已发放.value.equals(rwm.getStatus())) {
				continue;
			}
			rwm.setPayno(payno);//必须要传入单号
			totalCount ++;
			
			if (rwm.getActual() == null || rwm.getActual() <= 0){
				rwm.setStatus(Enum_status.已发放.value);
				this.reportWeeklyMemberDao.updateByPrimary(rwm);
				continue;
			}
			if(Math.abs(rwm.getActual().doubleValue() - rwm.getSubtract()) <= 0 ) {
				rwm.setStatus(Enum_status.已发放.value);
				this.reportWeeklyMemberDao.updateByPrimary(rwm);
				continue;
			}
			
			
			EnterpriseEmployee ee = enterpriseEmployeeService.takeEmployeeByCode(rwm.getParentemployeecode());
			if(ee == null) {
				result.append("<br />"+rwm.getLoginaccount()+"未能找到此人的上级账号信息");
				continue;
			}
			if(ee.getEmployeetypecode().equals(Type.信用代理.value)) {//信用代模式需要扣上级代理的钱
				EmployeeMoneyChangeType a_type =new EmployeeMoneyChangeType();
				//活动类别
				a_type.setMoneychangetypeclassify(moneychangetypeCategory.常规.value);
				//活动类型
				a_type.setMoneychangetypecode(Enum_moneychangetype.洗码周结.value);
				//充值类型
				a_type.setMoneyinouttype((byte)Enum_moneyinouttype.出账.value);
				
				java.math.BigDecimal bd1 = new java.math.BigDecimal( -rwm.getActual() );//出账应为负数
				if(bd1.doubleValue() > 0) {//
					a_type.setMoneyinouttype((byte)Enum_moneyinouttype.进账.value);
				}
				this.enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(payno, rwm.getParentemployeecode(), bd1, a_type, "发放洗码"+rwm.getLoginaccount());
			}
					
			rwm.setStatus(Enum_status.已发放.value);
			rwm.setSubtract(rwm.getAmount());//设置金额为相等的
			this.reportWeeklyMemberDao.updateByPrimary(rwm);
			
			EmployeeMoneyChangeType type =new EmployeeMoneyChangeType();
			type.setMoneychangetypeclassify(moneychangetypeCategory.常规.value);
			type.setMoneychangetypecode(Enum_moneychangetype.洗码周结.value);
			type.setMoneyinouttype((byte)Enum_moneyinouttype.进账.value);
			java.math.BigDecimal bd2 = new java.math.BigDecimal( rwm.getActual() );//进账应为正数
			if(bd2.doubleValue() < 0) {
				type.setMoneyinouttype((byte)Enum_moneyinouttype.出账.value);
			}
			
			/******检查并创建资金账户
			EnterpriseEmployeeCapitalAccount ec = capitalAccountService.takeCurrencyAccount(rwm.getEmployeecode());
			if(ec == null){
				enterpriseEmployeeCapitalAccountService.tc_saveEmployeeCapitalAccount(
						new EnterpriseEmployeeCapitalAccount(rwm.getEmployeecode(), rwm.getParentemployeecode()));
			} 
			*****/
			totalMoney += bd2.doubleValue();
			this.enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(payno, rwm.getEmployeecode(), bd2, type, "发放洗码"+rwm.getLoginaccount());
			
			
			//加入打码
			ActivityBetRecord betrecord = new ActivityBetRecord();
			betrecord.setOrdernumber(payno);
			betrecord.setEmployeecode(rwm.getEmployeecode());
			betrecord.setEcactivitycode(Enum_ecactivitycode.返水所需流水.value);
			betrecord.setMustbet( (bd2.doubleValue()) * 1);
			betrecord.setAlreadybet(0.0);
			betrecord.setBetrecordstatus(Enum_betrecordstatus.未完成.value);
			betrecord.setCreatetime(new Date());
			betrecord.setLoginaccount(rwm.getLoginaccount());
			betrecord.setRecharge(0.0);//充值金额
			betrecord.setEnterprisecode(rwm.getEnterprisecode());//企业编码
			betrecord.setBrandcode(rwm.getBrandcode());//品牌编码
			betrecord.setParentemployeeaccount(ee.getParentemployeeaccount());//上级账号
			betrecord.setParentemployeecode(rwm.getParentemployeecode());//上级编码
			activityBetRecordService.addActivityBetRecord(betrecord);
					
		}
		totalMoney = MoneyHelper.moneyFormatDouble(totalMoney);
		result.append("<br />实际发放人次："+totalCount+"人次；");
		result.append("<br />实际发放洗码额："+totalMoney+"元；");
		return result.toString();
	}
	
	
	/**
	 * 会员补发洗码（生成）
	 * @param rdas
	 * @throws Exception
	 */
	@Override
	public void updateProcessSupplement(ReportWeeklyMember rdas, double butMoney, double money ) throws Exception {
		
		//1增加补发记录 2修改正常记录为已发放 3增加账变日志
		
		/******jason20170403修改，补发时不直接发放，改为先保存补发记录，后续手动补发
		EmployeeMoneyChangeType a_type =new EmployeeMoneyChangeType();
		//账变类别
		a_type.setMoneychangetypeclassify(moneychangetypeCategory.活动.value);
		//账变类型
		a_type.setMoneychangetypecode(Enum_moneychangetype.周结校验补发.value);
		//充值类型
		a_type.setMoneyinouttype((byte)Enum_moneyinouttype.出账.value);
		java.math.BigDecimal bd1 = new java.math.BigDecimal( 0 - money );//出账应为负数
		this.enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(RandomString.UUID(), rdas.getParentemployeecode(), bd1 , a_type, "洗码校验补发success");
		*******/
		
		/******只生成记录*****/
		/******发放的同时自动标记为发放*****/
		ReportWeeklyMember newItem = new ReportWeeklyMember();
		newItem.setBet(butMoney);//需打码金额
		newItem.setAmount(money);//需发放金额
		newItem.setSubtract(money);//已发放金额
		newItem.setActual(money);//实发
		newItem.setBrandcode(rdas.getBrandcode());
		newItem.setEmployeecode(rdas.getEmployeecode());
		newItem.setEnterprisecode(rdas.getEnterprisecode());
		newItem.setStarttime(rdas.getStarttime());
		newItem.setEndtime(rdas.getEndtime());
		newItem.setGameplatform(rdas.getGameplatform());
		newItem.setGametype(rdas.getGametype());
		newItem.setLoginaccount(rdas.getLoginaccount());
		newItem.setParentemployeecode(rdas.getParentemployeecode());
		newItem.setRatio(rdas.getRatio().doubleValue());
		newItem.setReporttime(new Date());//记录时间
		newItem.setStatus(ReportDailyAgent.Enum_status.未发放.value);
		newItem.setPaytype(ReportWeeklyAgent.Enum_paytype.补发.value);//这是补发记录。允许多次补发
		newItem.setPatchno(rdas.getPatchno());//批次号
		super.save(newItem);
		
		/******jason20170403修改，补发时不直接发放，改为先保存补发记录，后续手动补发
		java.math.BigDecimal bd2 = new java.math.BigDecimal( money );//进账应为正数
		EmployeeMoneyChangeType type =new EmployeeMoneyChangeType();
		type.setMoneychangetypeclassify(moneychangetypeCategory.活动.value);
		type.setMoneychangetypecode(Enum_moneychangetype.周结校验补发.value);
		type.setMoneyinouttype((byte)Enum_moneyinouttype.进账.value);
		this.enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(RandomString.UUID(), rdas.getEmployeecode(), bd2 , type, "洗码校验补发success");
		*******/
		
	}
	/**
	 * 代理补发洗码
	 * @param rdas
	 * @throws Exception
	 */
	public void update(ReportWeeklyMember rdas) throws Exception {
		super.update(rdas);
	}

	@Override
	public BaseDao<ReportWeeklyMember> baseDao() {
		return this.reportWeeklyMemberDao;
	}

	@Override
	public Class<ReportWeeklyMember> getClazz() {
		return ReportWeeklyMember.class;
	}

}
