package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.ActivityLoseBonusDao;
import com.maven.dao.EnterpriseActivityPayDao;
import com.maven.dao.EnterpriseEmployeeInformationDao;
import com.maven.dao.LogLoginDao;
import com.maven.dao.TakeDepositRecoredDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityLoseBonus;
import com.maven.entity.ActivityLoseBonus.Enum_LoseBonusStatus;
import com.maven.entity.ActivityLoseBonus.LoseBonusModelSettings;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EnterpriseActivityPay;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.entity.EmployeeMoneyChangeType.moneychangetypeCategory;
import com.maven.entity.EnterpriseBrandActivity;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeInformation;
import com.maven.entity.TakeDepositRecord;
import com.maven.entity.UserLogs;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.TakeDepositRecord.Enum_orderstatus;
import com.maven.entity.TakeDepositRecord.Enum_ordertype;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.service.ActivityLoseBonusService;
import com.maven.service.EnterpriseActivityCustomizationSettingService;
import com.maven.service.EnterpriseBrandActivityService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.UserLogsService;
import com.maven.util.ActivityUtils;
import com.maven.util.ActivityUtils.ActivityCheckMessage;
import com.maven.util.Arith;
import com.maven.util.DateUtils;
import com.maven.util.RandomString;

@Service
public class ActivityLoseBonusServiceImpl extends BaseServiceImpl<ActivityLoseBonus> implements ActivityLoseBonusService{

	@Autowired
	private ActivityLoseBonusDao activityLoseBonusDao;
	@Autowired
	private TakeDepositRecoredDao takeDepositRecoredDao;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private EnterpriseActivityCustomizationSettingService enterpriseActivityCustomizationSettingService;
	@Autowired
	private LogLoginDao logLoginDao;
	@Autowired
	private ActivityBetRecordDao activityBetRecordDao;
	@Autowired
	private EnterpriseBrandActivityService enterpriseBrandActivityService;
	@Autowired
	private EnterpriseEmployeeInformationDao enterpriseEmployeeInformationDao;
	@Autowired
	private EnterpriseEmployeeCapitalAccountService enterpriseEmployeeCapitalAccountService;
	@Autowired
	private UserLogsService userLogsService;
	@Autowired
	private EnterpriseActivityPayDao enterpriseActivityPayDao;
	
	@Override
	public BaseDao<ActivityLoseBonus> baseDao() {
		return activityLoseBonusDao;
	}

	@Override
	public Class<ActivityLoseBonus> getClazz() {
		return ActivityLoseBonus.class;
	}

	@SuppressWarnings("serial")
	@Override
	public BigDecimal tc_monthLoseBonus(String employeecode, Integer brandactivitycode) throws Exception{
		//规则 1判断活动是否正常进行 2判断上月是否领取过 3计算红利派发奖金记录日志
		//1
		final EnterpriseEmployee ee = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
		EnterpriseBrandActivity brandActivity = enterpriseBrandActivityService.checkEnterpriseBrandActivity(brandactivitycode);
		if (brandActivity == null){
			throw new LogicTransactionRollBackException(ActivityCheckMessage.BRAND_ACTIVITY_NOTEXIST.desc);
		}
		EnterpriseEmployeeInformation eei = enterpriseEmployeeInformationDao.queryAccountData(ee);
		
		//2
		Map<String, Object> parameter = new HashMap<String, Object>(){{
			this.put("employeecode", ee.getEmployeecode());
			this.put("gettime", new Date());
		}};
		ActivityLoseBonus lastmonthRecord = activityLoseBonusDao.selectLastMonthRecord(parameter);
		if (lastmonthRecord != null){
			throw new LogicTransactionRollBackException(ActivityCheckMessage.GET_ALREADY.desc);
		}
		//3
		parameter = new HashMap<String, Object>(){{
			this.put("brandcode", ee.getBrandcode());
			this.put("employeecode", ee.getEmployeecode());
			this.put("ordertype", Enum_ordertype.存款.value);
			this.put("orderstatus", Enum_orderstatus.审核通过.value);
			this.put("orderStartDate", DateUtils.getfirstDayofMonth(-1));
			this.put("orderEndDate", DateUtils.getlastDayofMonth(-1));
		}};
		
		TakeDepositRecord depositSum = takeDepositRecoredDao.selectSumAmount(parameter);
		parameter.put("ordertype", Enum_ordertype.取款.value);
		TakeDepositRecord withdrawSum = takeDepositRecoredDao.selectSumAmount(parameter);
		if (depositSum == null || depositSum.getOrderamount().compareTo(withdrawSum.getOrderamount().abs()) <= 0){
			throw new LogicTransactionRollBackException(ActivityCheckMessage.NODEPOSIT_OR_NOLOSE.desc);
		}
		Double d_loseAmount = Arith.sub(depositSum.getOrderamount().doubleValue(), withdrawSum.getOrderamount().doubleValue()*-1);
		//计算红利
		Map<String, String> keyvalue = enterpriseActivityCustomizationSettingService.selectModelSettingKeyValue(brandActivity);
		Double percent = Double.valueOf(keyvalue.get(LoseBonusModelSettings.FLBFB.value));
		BigDecimal bonus = new BigDecimal(Arith.mul(d_loseAmount, Arith.div(percent, 100, 2)));
		EmployeeMoneyChangeType type =new EmployeeMoneyChangeType();
		//需要操作的用户编码
		String employeeCode = ee.getEmployeecode();
		//金额 bonus
		//活动类别
		type.setMoneychangetypeclassify(moneychangetypeCategory.活动.value);
		//活动类型
		type.setMoneychangetypecode(Enum_moneychangetype.优惠活动.value);
		//充值类型
		type.setMoneyinouttype((byte)Enum_moneyinouttype.进账.value);
		enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(RandomString.UUID(),employeeCode,bonus,type,"操作人:"+ee.getLoginaccount() + " 活动：输值月月返");
		
		String allIp = ActivityUtils.ipLink(logLoginDao.selectIpByLoginaccount(ee.getLoginaccount()));
		//增加月输值红利领取记录
		ActivityLoseBonus loseBonusRecord = new ActivityLoseBonus();
		loseBonusRecord.setEmployeecode(ee.getEmployeecode());
		loseBonusRecord.setParentemployeecode(ee.getParentemployeecode());
		loseBonusRecord.setLoginaccount(ee.getLoginaccount());
		loseBonusRecord.setLoseamount(d_loseAmount);
		loseBonusRecord.setReturnratio(percent.shortValue());
		loseBonusRecord.setReturnamount(bonus.doubleValue());
		loseBonusRecord.setGettime(new Date());
		loseBonusRecord.setLosebonusstatus(Enum_LoseBonusStatus.已派发.value);
		loseBonusRecord.setUniqueinfo("||"+eei.getEmail()+"|"+eei.getPhonenumber()+"|"+eei.getPaymentaccount()+"|"+allIp);
		activityLoseBonusDao.add("ActivityLoseBonus.insert", loseBonusRecord);
		
		Double betMultiple = Double.valueOf(keyvalue.get(LoseBonusModelSettings.LSBS.value));
		//增加打码记录
		ActivityBetRecord betrecord = new ActivityBetRecord();
		betrecord.setEmployeecode(ee.getEmployeecode());
		betrecord.setEcactivitycode(brandActivity.getEcactivitycode());
		betrecord.setMustbet(Arith.mul(bonus.doubleValue(), betMultiple));
		betrecord.setAlreadybet(0.0);
		betrecord.setBetrecordstatus(Enum_betrecordstatus.未完成.value);
		betrecord.setCreatetime(new Date());
		betrecord.setLoginaccount(ee.getLoginaccount());
		betrecord.setRecharge(bonus.doubleValue());//充值金额
		
		betrecord.setEnterprisecode(ee.getEnterprisecode());//企业编码
		betrecord.setBrandcode(ee.getBrandcode());//品牌编码
		betrecord.setParentemployeeaccount(ee.getParentemployeeaccount());//上级账号
		betrecord.setParentemployeecode(ee.getParentemployeecode());//上级编码
		activityBetRecordDao.addBetRecord(betrecord);
		
		//增加活动红利支付审核记录
		Date now = new Date();
		EnterpriseActivityPay activityPay = new EnterpriseActivityPay();
		activityPay.setBrandcode(ee.getBrandcode());
		activityPay.setEmployeecode(ee.getEmployeecode());
		activityPay.setEnterprisecode(ee.getEnterprisecode());
		activityPay.setLoginaccount(ee.getLoginaccount());
		activityPay.setParentemployeecode(ee.getParentemployeecode());
		activityPay.setAcname(brandActivity.getActivityname());
		activityPay.setEcactivitycode(brandActivity.getEcactivitycode());
		activityPay.setAuditer("");
		activityPay.setAuditremark("活动：输值月月返，自动发放");
		activityPay.setAudittime(now);
		activityPay.setCreatetime(now);
		activityPay.setPayer("");
		activityPay.setPaymoneyaudit(bonus.doubleValue());
		activityPay.setPaymoneyreal(bonus.doubleValue());
		activityPay.setPaystatus(EnterpriseActivityPay.Enum_paystatus.已支付.value);
		activityPay.setPaytyime(now);
		activityPay.setPaytype(EnterpriseActivityPay.Enum_paytype.自动发放.value);
		activityPay.setDesc("");
		activityPay.setLsbs(betMultiple+"");
		enterpriseActivityPayDao.addBetRecord(activityPay);
		
		userLogsService.addActivityBetRecord(new UserLogs(activityPay.getEnterprisecode(), activityPay.getEmployeecode(), activityPay.getLoginaccount(), 
				UserLogs.Enum_operatype.红利信息业务, "月数返利"+bonus.doubleValue(), null, null));
		
		return bonus;
	}
	
}
