package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hy.pull.common.util.MoneyHelper;
import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.dao.ReportDailyAgentDao;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeCapitalAccount;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.entity.EmployeeMoneyChangeType.moneychangetypeCategory;
import com.maven.entity.EnterpriseEmployeeType.Type;
import com.maven.entity.ReportDailyAgent;
import com.maven.entity.ReportWeeklyMember.Enum_status;
import com.maven.service.EmployeeMoneyChangeService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.ReportDailyAgentService;
import com.maven.util.RandomString;
import com.maven.util.StringUtils;
import com.maven.utility.ClassUtil;

@Service
public class ReportDailyAgentServiceImpl extends BaseServiceImpl<ReportDailyAgent> implements ReportDailyAgentService {

	@Autowired
	private ReportDailyAgentDao reportDailyAgentDao;
	@Autowired
	private EnterpriseEmployeeCapitalAccountService enterpriseEmployeeCapitalAccountService;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private EmployeeMoneyChangeService employeeMoneyChangeService;
	
	@Override
	public void saveBatch(List<ReportDailyAgent> rdas) throws Exception{
		super.saveRecordBatch(rdas);
	}
	
	@Override
	public BaseDao<ReportDailyAgent> baseDao() {
		return reportDailyAgentDao;
	}
	
	@Override
	public Class<ReportDailyAgent> getClazz() {
		return ReportDailyAgent.class;
	}
	
	/**
	 * 数据统计之金额统计
	 * @param object
	 * @return
	 */
	@Override
	public Map<String, Object> takeRecordCountMoney(Map<String, Object> object) throws Exception  {
		return reportDailyAgentDao.takeRecordCountMoney(object);
	}

	/**
	 * 批量修改
	 * @param rdas
	 * @throws Exception
	 */
	@Override
	public void updateBatchStatus(Map<String, Object> object) throws Exception {
		reportDailyAgentDao.updateBatchStatus(object);
	}
	
	/**
	 * 主键修改
	 * @param rdas
	 * @throws Exception
	 */
	public void update(ReportDailyAgent dailyAgent) throws Exception {
		reportDailyAgentDao.update(ClassUtil.getMapId(getClazz(), new Throwable()), dailyAgent);
	}
	/**
	 * 取消支付计划
	 * @param rdas
	 * @throws Exception
	 */
	public String updateDailyPlan(List<ReportDailyAgent> rdas) throws Exception {
		StringBuffer result = new StringBuffer();
		//支付单号
		String payno = null;
		Map<String, String> paramsEmployee = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		//1=回滚账变金额
		//2=批量更新发放标志和单号
		EmployeeMoneyChangeType type =new EmployeeMoneyChangeType();
		type.setMoneychangetypeclassify(moneychangetypeCategory.常规.value);
		type.setMoneychangetypecode(Enum_moneychangetype.洗码冲减.value);
		type.setMoneyinouttype((byte)Enum_moneyinouttype.出账.value);
		
		for (ReportDailyAgent rda : rdas) {
			if(payno == null) {
				payno = "C"+rda.getEnterprisecode().substring(4, 6) + StringUtils.getCurrenDate();
				result.append("新单号："+payno);
			}
			params.put("ordernumber", rda.getPayno());
			params.put("employeecode", rda.getEmployeecode());
			params.put("enterprisecode", rda.getEnterprisecode());
			params.put("moneychangetypecode", Enum_moneychangetype.洗码日结.value);
			params = employeeMoneyChangeService.findAccountChangeCount(params);
			
			//冲减额
			double moneychangeamount = 0;
			try {
				
				moneychangeamount = params.get("moneychangeamount") == null ? 0 : Double.valueOf(params.get("moneychangeamount").toString());
				moneychangeamount = MoneyHelper.moneyFormatDouble(moneychangeamount);
				
				if(moneychangeamount > 0 && !paramsEmployee.containsKey(rda.getEmployeecode())) {
					//没有金额需要处理就不处理
					paramsEmployee.put(rda.getEmployeecode(), moneychangeamount+"");
					
					java.math.BigDecimal _acc_money = new java.math.BigDecimal( moneychangeamount+"" ).negate();//出账为负数
					
					EnterpriseEmployeeCapitalAccount ea = this.enterpriseEmployeeCapitalAccountService.takeCurrencyAccount(rda.getEmployeecode());
					if(ea.getBalance().doubleValue() < _acc_money.negate().doubleValue()) {
						String line = "取消日代理计划余额不足："+rda.getLoginaccount() + ", "+rda.getEmployeecode()+ ",总发放金额："+moneychangeamount+",当前余额："+ea.getBalance().doubleValue();
						System.out.println(line);
						result.append("<br />"+line);
					} else {
						this.enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(payno, rda.getEmployeecode(), _acc_money, type, "日洗码冲减"+rda.getLoginaccount());
						
						//信用代模式需要还上级代理的钱
						EnterpriseEmployee ee = enterpriseEmployeeService.takeEmployeeByCode(rda.getParentemployeecode());
						if(ee == null) {
							result.append("<br />"+rda.getLoginaccount()+"未能找到此人的上级账号信息");
							continue;
						}
						if(ee.getEmployeetypecode().equals(Type.信用代理.value)) {
							EmployeeMoneyChangeType a_type =new EmployeeMoneyChangeType();
							a_type.setMoneychangetypeclassify(moneychangetypeCategory.常规.value);
							a_type.setMoneychangetypecode(Enum_moneychangetype.洗码冲减.value);
							a_type.setMoneyinouttype((byte)Enum_moneyinouttype.进账.value);
							this.enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(payno, rda.getParentemployeecode(), _acc_money.negate(), a_type, "日洗码冲减"+rda.getLoginaccount());
						}
					}
				}
				
				//回写状态
				if( paramsEmployee.containsKey(rda.getEmployeecode())) {
					rda.setStatus(Enum_status.未发放.value);
					rda.setPayno(payno);
					this.update(rda);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				String line = "取消日代理计划失败："+rda.getLoginaccount() + " "+rda.getEmployeecode()+ " 回滚金额："+moneychangeamount+" 原因："+e.getMessage();
				System.out.println(line);
				result.append("<br />"+line);
				continue;
			}
					
		}
		return result.toString();
	}
	
	@Override
	public String updateProcessDaily(List<ReportDailyAgent> rdas) throws Exception {
		StringBuffer result = new StringBuffer();
		//步骤 1判断当条洗码记录是否发放过 2更新洗码记录状态为发放 3增加用户金额 4增加账变日志
		
		//支付单号
		String payno = null;
		int totalCount = 0;
		double totalMoney = 0;
		
		for (ReportDailyAgent rda : rdas) {
			if(payno == null) {
				payno = rda.getEnterprisecode().substring(4, 6) + StringUtils.getCurrenDateyyyyMMddHHmmss();
				result.append("生成支付单号："+payno);
			}
			if (rda.getStatus().equals(Enum_status.已发放.value)) {
				continue;
			}
			rda.setPayno(payno);//必须要传入单号
			totalCount ++;
			if (rda.getAmount() == null || rda.getAmount().doubleValue() <= 0){
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
				a_type.setMoneychangetypecode(Enum_moneychangetype.洗码日结.value);
				//充值类型
				a_type.setMoneyinouttype((byte)Enum_moneyinouttype.出账.value);
				this.enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(payno, rda.getParentemployeecode(), rda.getAmount().multiply(BigDecimal.valueOf(-1)), a_type, "发放日代理洗码"+rda.getLoginaccount());
						
			}
			
			rda.setStatus(Enum_status.已发放.value);
			this.update(rda);
			
			
			EmployeeMoneyChangeType type =new EmployeeMoneyChangeType();
			type.setMoneychangetypeclassify(moneychangetypeCategory.常规.value);
			type.setMoneychangetypecode(Enum_moneychangetype.洗码日结.value);
			type.setMoneyinouttype((byte)Enum_moneyinouttype.进账.value);
			totalMoney += rda.getAmount().doubleValue();
			this.enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(payno, rda.getEmployeecode(), rda.getAmount(), type, "发放日代理洗码"+rda.getLoginaccount());
					
		}
		totalMoney = MoneyHelper.moneyFormatDouble(totalMoney);
		result.append("<br />实际发放人次："+totalCount+"人次；");
		result.append("<br />实际发放洗码额："+totalMoney+"元；");
		return result.toString();
	}

}
