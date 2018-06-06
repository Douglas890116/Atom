package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.ActivityRegBonusDao;
import com.maven.dao.EnterpriseActivityPayDao;
import com.maven.dao.EnterpriseEmployeeInformationDao;
import com.maven.dao.LogLoginDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityRegBonus;
import com.maven.entity.ActivityRegBonus.RegBonusModelSettings;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EnterpriseActivityPay;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.entity.EmployeeMoneyChangeType.moneychangetypeCategory;
import com.maven.entity.EnterpriseBrandActivity;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeInformation;
import com.maven.entity.UserLogs;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.service.ActivityRegBonusService;
import com.maven.service.EnterpriseActivityCustomizationSettingService;
import com.maven.service.EnterpriseBrandActivityService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.UserLogsService;
import com.maven.util.ActivityUtils;
import com.maven.util.ActivityUtils.ActivityCheckMessage;
import com.maven.util.ActivityUtils.ActivityUniqueinfoCheck;
import com.maven.util.Arith;
import com.maven.util.RandomString;

@Service
public class ActivityRegBonusServiceImpl extends BaseServiceImpl<ActivityRegBonus> implements ActivityRegBonusService{

	@Autowired
	private ActivityRegBonusDao activityRegBonusDao;
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
	public BaseDao<ActivityRegBonus> baseDao() {
		return activityRegBonusDao;
	}

	@Override
	public Class<ActivityRegBonus> getClazz() {
		return ActivityRegBonus.class;
	}

	@Override
	public BigDecimal tc_getRegBonus(String employeecode, Integer brandactivitycode, String ip) throws Exception {
		//ActivityCheckResult regresult = new ActivityUtils().new ActivityCheckResult();
		EnterpriseEmployee ee = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
		if (ee == null){
			throw new LogicTransactionRollBackException(ActivityCheckMessage.GET_USERNOTEXIST.desc);
		}
		EnterpriseEmployeeInformation eei = enterpriseEmployeeInformationDao.queryAccountData(ee);
		ActivityUniqueinfoCheck regbonuscheck = new ActivityUtils().new ActivityUniqueinfoCheck(ee.getLoginaccount(), "", "", eei.getEmail(),
				eei.getPhonenumber(), eei.getPaymentaccount(), ip);
		
		EnterpriseBrandActivity brandActivity = enterpriseBrandActivityService.checkEnterpriseBrandActivity(brandactivitycode);
		if (brandActivity == null){
			throw new LogicTransactionRollBackException(ActivityCheckMessage.BRAND_ACTIVITY_NOTEXIST.desc);
		}
		
		//判断规则,1判断用户名是否领取,2判断身份信息
		//1
		List<ActivityRegBonus> arb_list = activityRegBonusDao.selectByLoginaccount(regbonuscheck.getLoginaccount());
		if (arb_list.size()>0){
			//regresult.setResult(false);
			throw new LogicTransactionRollBackException(ActivityCheckMessage.GET_ALREADY.desc);
		}
		arb_list = null;
		//2
		arb_list = activityRegBonusDao.selectByUniqueinfo(regbonuscheck);
		if (arb_list.size()>0){
			//regresult.setResult(false);
			throw new LogicTransactionRollBackException(ActivityCheckMessage.GET_ALREADY.desc);
		}
		
		//计算开户红包金额,发放红包
		Map<String, String> keyvalue = enterpriseActivityCustomizationSettingService.selectModelSettingKeyValue(brandActivity);
		BigDecimal amount = new BigDecimal(this.getBonusAmount(keyvalue));
		EmployeeMoneyChangeType type =new EmployeeMoneyChangeType();
		//需要操作的用户编码
		String employeeCode = ee.getEmployeecode();
		//活动类别
		type.setMoneychangetypeclassify(moneychangetypeCategory.活动.value);
		//活动类型
		type.setMoneychangetypecode(Enum_moneychangetype.优惠活动.value);
		//充值类型
		type.setMoneyinouttype((byte)Enum_moneyinouttype.进账.value);
		enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(RandomString.UUID(),employeeCode,amount,type,"操作人:"+ee.getLoginaccount() + " 活动：体验红包");
		
		String allIp = ActivityUtils.ipLink(logLoginDao.selectIpByLoginaccount(regbonuscheck.getLoginaccount()));
		//增加体验金领取记录
		ActivityRegBonus regRecord = new ActivityRegBonus();
		regRecord.setEmployeecode(ee.getEmployeecode());
		regRecord.setParentemployeecode(ee.getParentemployeecode());
		regRecord.setLoginaccount(ee.getLoginaccount());
		regRecord.setGettime(new Date());
		regRecord.setRegbonus(amount.doubleValue());
		regRecord.setUniqueinfo(regbonuscheck.getHousenumber()+"|"+regbonuscheck.getHouseaddress()+"|"+regbonuscheck.getEmail()+"|"+regbonuscheck.getPhonenumber()+
				"|"+regbonuscheck.getPayment()+"|"+allIp);
		activityRegBonusDao.addRegBonusRecord(regRecord);
		
		double betMultiple = Double.valueOf(keyvalue.get(RegBonusModelSettings.LSBS.value));
		//增加打码记录
		ActivityBetRecord betrecord = new ActivityBetRecord();
		betrecord.setEmployeecode(ee.getEmployeecode());
		betrecord.setEcactivitycode(brandActivity.getEcactivitycode());
		betrecord.setMustbet(Arith.mul(amount.doubleValue(), betMultiple));
		betrecord.setAlreadybet(0.0);
		betrecord.setBetrecordstatus(Enum_betrecordstatus.未完成.value);
		betrecord.setCreatetime(new Date());
		betrecord.setLoginaccount(ee.getLoginaccount());
		betrecord.setRecharge(amount.doubleValue());//充值金额
		
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
		activityPay.setAuditremark("活动：注册送彩金，自动发放");
		activityPay.setAudittime(now);
		activityPay.setCreatetime(now);
		activityPay.setPayer("");
		activityPay.setPaymoneyaudit(amount.doubleValue());
		activityPay.setPaymoneyreal(amount.doubleValue());
		activityPay.setPaystatus(EnterpriseActivityPay.Enum_paystatus.已支付.value);
		activityPay.setPaytyime(now);
		activityPay.setPaytype(EnterpriseActivityPay.Enum_paytype.自动发放.value);
		activityPay.setDesc("");
		activityPay.setLsbs(betMultiple+"");
		enterpriseActivityPayDao.addBetRecord(activityPay);
		
		
		userLogsService.addActivityBetRecord(new UserLogs(activityPay.getEnterprisecode(), activityPay.getEmployeecode(), activityPay.getLoginaccount(), 
				UserLogs.Enum_operatype.红利信息业务, "注册送红包"+amount.doubleValue()+"元", null, null));
		
		
		//regresult.setResult(true);
		return amount;
	}
	
	private Integer getBonusAmount(Map<String, String> keyvalue){
		int hbsx = Integer.valueOf(keyvalue.get(RegBonusModelSettings.HBSX.value));
		int hbxx = Integer.valueOf(keyvalue.get(RegBonusModelSettings.HBXX.value));
		Random random = new Random(System.currentTimeMillis());
		int amount = random.nextInt(hbsx);
		return amount<hbxx?hbxx:amount;
		/*
		if (base <= 50){
			amount = random.nextInt(11);
			amount = amount<5?5:amount;
		} else if (base > 50 && base <= 90){
			amount = random.nextInt(18);
			amount = amount<10?10:amount;
		} else {
			amount = random.nextInt(21);
			amount = amount<18?18:amount;
		}*/
	}
	
}
