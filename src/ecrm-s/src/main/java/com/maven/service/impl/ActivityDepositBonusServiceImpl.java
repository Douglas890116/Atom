package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.ActivityDepositBonusDao;
import com.maven.dao.EnterpriseActivityPayDao;
import com.maven.dao.EnterpriseEmployeeInformationDao;
import com.maven.dao.LogLoginDao;
import com.maven.dao.TakeDepositRecoredDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityDepositBonus;
import com.maven.entity.ActivityDepositBonus.DepositBonusModelSettings;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.entity.EmployeeMoneyChangeType.moneychangetypeCategory;
import com.maven.entity.EnterpriseActivityPay;
import com.maven.entity.EnterpriseBrandActivity;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeInformation;
import com.maven.entity.TakeDepositRecord;
import com.maven.entity.UserLogs;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.TakeDepositRecord.Enum_orderstatus;
import com.maven.entity.TakeDepositRecord.Enum_ordertype;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.service.ActivityDepositBonusService;
import com.maven.service.EnterpriseActivityCustomizationSettingService;
import com.maven.service.EnterpriseBrandActivityService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.UserLogsService;
import com.maven.util.ActivityUtils;
import com.maven.util.ActivityUtils.ActivityCheckMessage;
import com.maven.util.RandomString;

@Service
public class ActivityDepositBonusServiceImpl extends BaseServiceImpl<ActivityDepositBonus> implements ActivityDepositBonusService{

	@Autowired
	private ActivityDepositBonusDao activityDepositBonusDao;
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
	public BaseDao<ActivityDepositBonus> baseDao() {
		return activityDepositBonusDao;
	}

	@Override
	public Class<ActivityDepositBonus> getClazz() {
		return ActivityDepositBonus.class;
	}
	
	/**
	 * 保存记录
	 * @return
	 */
	public void saveActivityDepositBonus(ActivityDepositBonus activityDepositBonus) throws Exception {
		activityDepositBonusDao.add("ActivityDepositBonus.insert", activityDepositBonus);
	}

	@SuppressWarnings("serial")
	@Override
	public BigDecimal tc_depositBonus(String employeecode, Integer brandactivitycode) throws Exception{
		//规则 1根据loginaccount获取最后一笔存款订单 2计算返利金额 3发放返利记录日志
		final EnterpriseEmployee ee = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
		EnterpriseBrandActivity brandActivity = enterpriseBrandActivityService.checkEnterpriseBrandActivity(brandactivitycode);
		if (brandActivity == null){
			throw new LogicTransactionRollBackException(ActivityCheckMessage.BRAND_ACTIVITY_NOTEXIST.desc);
		}
		EnterpriseEmployeeInformation eei = enterpriseEmployeeInformationDao.queryAccountData(ee);
		Map<String, Object> parames = new HashMap<String, Object>(){{
			this.put("brandcode", ee.getBrandcode());
			this.put("employeecode", ee.getEmployeecode());
			this.put("ordertype", Enum_ordertype.存款.value);
			this.put("orderstatus", Enum_orderstatus.审核通过.value);
		}};
		TakeDepositRecord tdr = takeDepositRecoredDao.getLastByParames(parames);
		if (tdr == null){
			throw new LogicTransactionRollBackException(ActivityCheckMessage.NEVER_DEPOSIT.desc);
		}
		ActivityDepositBonus depositbonusrecord = activityDepositBonusDao.selectByPrimaryKey("ActivityDepositBonus.selectByPrimaryKey", tdr.getOrdernumber());
		if (depositbonusrecord != null){
			throw new LogicTransactionRollBackException(ActivityCheckMessage.DEPOSIT_ALREADYBONUS.desc);
		}
		
		//计算红利
		Map<String, String> keyvalue = enterpriseActivityCustomizationSettingService.selectModelSettingKeyValue(brandActivity);
		Double percent = Double.valueOf(keyvalue.get(DepositBonusModelSettings.FLBFB.value));
		BigDecimal bonus = new BigDecimal(tdr.getOrderamount().doubleValue()*(percent/100));
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
		enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(RandomString.UUID(),employeeCode,bonus,type,"操作人:"+ee.getLoginaccount() + " 活动：笔笔存返利");
		
		String allIp = ActivityUtils.ipLink(logLoginDao.selectIpByLoginaccount(ee.getLoginaccount()));
		//增加存款红利领取记录
		ActivityDepositBonus newdepositbonusrecord = new ActivityDepositBonus();
		newdepositbonusrecord.setOrdernumber(tdr.getOrdernumber());
		newdepositbonusrecord.setEmployeecode(ee.getEmployeecode());
		newdepositbonusrecord.setParentemployeecode(ee.getParentemployeecode());
		newdepositbonusrecord.setLoginaccount(ee.getLoginaccount());
		newdepositbonusrecord.setDepositamount(tdr.getOrderamount().doubleValue());
		newdepositbonusrecord.setReturnratio(percent);
		newdepositbonusrecord.setReturnamount(bonus.doubleValue());
		//TODO 增加活动类型
		newdepositbonusrecord.setDeposittime(tdr.getOrderdate());
		newdepositbonusrecord.setGettime(new Date());
		newdepositbonusrecord.setUniqueinfo("||"+eei.getEmail()+"|"+eei.getPhonenumber()+"|"+eei.getPaymentaccount()+"|"+allIp);
		activityDepositBonusDao.add("ActivityDepositBonus.insert", newdepositbonusrecord);
		
		double betMultiple = Double.valueOf(keyvalue.get(DepositBonusModelSettings.LSBS.value));
		//增加打码记录
		ActivityBetRecord betrecord = new ActivityBetRecord();
		betrecord.setEmployeecode(ee.getEmployeecode());
		betrecord.setEcactivitycode(brandActivity.getEcactivitycode());
		betrecord.setMustbet((tdr.getOrderamount().doubleValue()+bonus.doubleValue()) * betMultiple);
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
		activityPay.setBrandcode(eei.getBrandcode());
		activityPay.setEmployeecode(employeeCode);
		activityPay.setEnterprisecode(eei.getEnterprisecode());
		activityPay.setLoginaccount(eei.getLoginaccount());
		activityPay.setParentemployeecode(eei.getParentemployeecode());
		activityPay.setAcname(brandActivity.getActivityname());
		activityPay.setEcactivitycode(brandActivity.getEcactivitycode());
		activityPay.setAuditer("");
		activityPay.setAuditremark("活动：存款抽奖获得彩金，自动发放");
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
				UserLogs.Enum_operatype.红利信息业务, "存款赠送优惠"+bonus.doubleValue(), null, null));
		
		return bonus;
	}

	
}
