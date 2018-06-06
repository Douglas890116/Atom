package com.maven.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hy.pull.common.util.MoneyHelper;
import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.dao.ReportWeeklyAgentDao;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeCapitalAccount;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.entity.EmployeeMoneyChangeType.moneychangetypeCategory;
import com.maven.entity.EnterpriseEmployeeType.Type;
import com.maven.entity.ReportDailyAgent;
import com.maven.entity.ReportWeeklyAgent;
import com.maven.entity.ReportWeeklyMember.Enum_status;
import com.maven.service.EmployeeMoneyChangeService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.ReportWeeklyAgentService;
import com.maven.util.RandomString;
import com.maven.util.StringUtils;

@Service
public class ReportWeeklyAgentServiceImpl extends BaseServiceImpl<ReportWeeklyAgent> implements ReportWeeklyAgentService {

	@Autowired
	private ReportWeeklyAgentDao reportWeeklyAgentDao;
	@Autowired
	private EnterpriseEmployeeCapitalAccountService enterpriseEmployeeCapitalAccountService;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private EmployeeMoneyChangeService employeeMoneyChangeService;
	
	@Override
	public void saveBatch(List<ReportWeeklyAgent> rdas) throws Exception{
		super.saveRecordBatch(rdas);
	}
	
	@Override
	public BaseDao<ReportWeeklyAgent> baseDao() {
		return reportWeeklyAgentDao;
	}
	
	@Override
	public Class<ReportWeeklyAgent> getClazz() {
		return ReportWeeklyAgent.class;
	}

	/**
	 * 数据统计之金额统计
	 * @param object
	 * @return
	 */
	@Override
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception  {
		return reportWeeklyAgentDao.takeRecordCountMoney(object);
	}
	
	/**
	 * 取消支付计划
	 * @param rdas
	 * @throws Exception
	 */
	public String updateWeeklyPlan(List<ReportWeeklyAgent> rdas) throws Exception {
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
		for (ReportWeeklyAgent data : rdas) {
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
						String line = "取消周代理计划余额不足："+data.getLoginaccount() + ", "+data.getEmployeecode()+ ",总发放金额："+moneychangeamount+",当前余额："+ea.getBalance().doubleValue();
						System.out.println(line);
						result.append("<br />"+line);
					} else {
						this.enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(payno, data.getEmployeecode(), _acc_money , type, "周洗码冲减"+data.getLoginaccount());
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
							this.enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(payno, data.getParentemployeecode(), _acc_money.negate() , a_type, "周洗码冲减"+data.getLoginaccount());
						}
					}
				}
				
				//回写状态
				if( paramsEmployee.containsKey(data.getEmployeecode())) {
					data.setStatus(Enum_status.未发放.value);
					data.setSubtract(MoneyHelper.moneyFormatDouble(data.getAmount() - actual));
					data.setPayno(payno);
					this.update(data);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				String line = "取消周代理计划失败："+data.getLoginaccount() + " "+data.getEmployeecode()+ " 回滚金额："+moneychangeamount+" 原因："+e.getMessage();
				System.out.println(line);
				result.append("<br />"+line);
				continue;
			}
		}
		return result.toString();
	}
	
	@Override
	public String updateProcessDaily(List<ReportWeeklyAgent> rdas) throws Exception {
		StringBuffer result = new StringBuffer();
		//步骤 1判断当条洗码记录是否发放过 2更新洗码记录状态为发放 3增加用户金额 4增加账变日志
		
		//支付单号
		String payno = null;
		int totalCount = 0;
		double totalMoney = 0;
		
		for (ReportWeeklyAgent rda : rdas) {
			
			if(payno == null) {
				payno = rda.getEnterprisecode().substring(4, 6) + StringUtils.getCurrenDateyyyyMMddHHmmss();
				result.append("生成支付单号："+payno);
			}
			
			if (rda.getStatus().equals(Enum_status.已发放.value)) {
				continue;
			}
			rda.setPayno(payno);//必须要传入单号
			totalCount ++;
			if (rda.getActual() == null || rda.getActual().doubleValue() <= 0){
				rda.setStatus(Enum_status.已发放.value);
				this.update(rda);
				continue;
			}
			if(Math.abs(rda.getActual().doubleValue() - rda.getSubtract()) <= 0 ) {
				rda.setStatus(Enum_status.已发放.value);
				this.update(rda);
				continue;
			}
			
			EnterpriseEmployee ee = enterpriseEmployeeService.takeEmployeeByCode(rda.getParentemployeecode());
			if(ee == null) {
				result.append("<br />"+rda.getLoginaccount()+"未能找到此人的上级账号信息");
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
				
				java.math.BigDecimal bd1 = new java.math.BigDecimal( rda.getSubtract() - rda.getActual() );//出账应为负数
				if(bd1.doubleValue() > 0) {//
					a_type.setMoneyinouttype((byte)Enum_moneyinouttype.进账.value);
				}
				this.enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(payno, rda.getParentemployeecode(), bd1 , a_type, "发放洗码"+rda.getLoginaccount());
			}
			
			
			rda.setStatus(Enum_status.已发放.value);
			rda.setSubtract(rda.getAmount());//设置金额为相等的
			this.update(rda);
			
			java.math.BigDecimal bd2 = new java.math.BigDecimal( rda.getActual() - rda.getSubtract() );//进账应为正数
			EmployeeMoneyChangeType type =new EmployeeMoneyChangeType();
			type.setMoneychangetypeclassify(moneychangetypeCategory.常规.value);
			type.setMoneychangetypecode(Enum_moneychangetype.洗码周结.value);
			type.setMoneyinouttype((byte)Enum_moneyinouttype.进账.value);
			if(bd2.doubleValue() < 0) {
				type.setMoneyinouttype((byte)Enum_moneyinouttype.出账.value);
			}
			totalMoney += bd2.doubleValue();
			this.enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(payno, rda.getEmployeecode(), bd2 , type, "发放洗码"+rda.getLoginaccount());
					
		}
		totalMoney = MoneyHelper.moneyFormatDouble(totalMoney);
		result.append("<br />实际发放人次："+totalCount+"人次；");
		result.append("<br />实际发放洗码额："+totalMoney+"元；");
		return result.toString();
	}
	
	/**
	 * 代理补发洗码
	 * @param rdas
	 * @throws Exception
	 */
	@Override
	public void updateProcessSupplement(ReportWeeklyAgent rdas, double butMoney, double money ) throws Exception {
		
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
		ReportWeeklyAgent newItem = new ReportWeeklyAgent();
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

	public void update(ReportWeeklyAgent rdas) throws Exception {
		super.update(rdas);
	}
}
