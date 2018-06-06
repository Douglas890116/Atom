package com.maven.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.ActivityRedbagDao;
import com.maven.dao.EnterpriseActivityPayDao;
import com.maven.dao.EnterpriseOperatingBrandDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityRaffleControl;
import com.maven.entity.ActivityRaffleRecord;
import com.maven.entity.ActivityRedbag;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EnterpriseActivityPay;
import com.maven.entity.EnterpriseBrandActivity;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.TakeDepositRecord;
import com.maven.entity.UserLogs;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.ActivityRaffleControlService;
import com.maven.service.ActivityRaffleRecordService;
import com.maven.service.ActivityRedbagService;
import com.maven.service.EnterpriseBrandActivityService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.TakeDepositRecoredService;
import com.maven.service.UserLogsService;
import com.maven.util.JSONUnit;
import com.maven.util.RandomString;
import com.maven.util.StringUtils;
import com.maven.util.ActivityUtils.ActivityCheckMessage;

@Service
public class ActivityRedbagServiceImpl extends BaseServiceImpl<ActivityRedbag> implements ActivityRedbagService{

	@Autowired
	private ActivityBetRecordDao activityBetRecordDao;
	@Autowired
	private EnterpriseEmployeeCapitalAccountService enterpriseEmployeeCapitalAccountService;
	@Autowired
	private EnterpriseActivityPayDao enterpriseActivityPayDao;
	@Autowired
	private EnterpriseBrandActivityService enterpriseBrandActivityService;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private ActivityRedbagDao activityRedbagDao;
	@Autowired
	private TakeDepositRecoredService takeDepositRecoredService;
	@Autowired
	private UserLogsService userLogsService;
	@Autowired
	private ActivityRaffleRecordService activityRaffleRecordService;
	
	private final static String PARAMS_RECHARGE_NUM = "RECHARGE_NUM";//至少存款次数（可为0）
	private final static String PARAMS_RECHARGE_MONEY = "RECHARGE_MONEY";// 至少存款金额（可为0） 
	private final static String PARAMS_REDBAG_RATE = "REDBAG_RATE";// 红包大小及概率（必填） 
	private final static String PARAMS_IS_AUTO_PAY = "IS_AUTO_PAY";// 是否自动发放（1=自动，2=人工审核） 
	private final static String PARAMS_GET_FUNCTION = "GET_FUNCTION";//领取方式（1=接口手动，2=登录自动领取）
	private final static String PARAMS_LSBS = "LSBS";//流水倍数（可为0）
	
	@Override
	public BaseDao<ActivityRedbag> baseDao() {
		return activityRedbagDao;
	}

	@Override
	public Class<ActivityRedbag> getClazz() {
		return ActivityRedbag.class;
	}

	@Override
	public List<ActivityRedbag> selectBetRecord(Map<String, Object> parameter) throws Exception {
		return activityRedbagDao.selectBetRecord(parameter);
	}

	@Override
	public int selectBetRecordCount(Map<String, Object> parameter) throws Exception {
		return activityRedbagDao.selectBetRecordCount(parameter);
	}
	
	@Override
	public Map<String, Object> selectBetRecordCountMoney(Map<String, Object> parameter) throws Exception {
		return activityRedbagDao.selectBetRecordCountMoney(parameter);
	}
	
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@Override
	public void addActivityBetRecord(ActivityRedbag activityBetRecord) throws Exception {
		activityRedbagDao.addBetRecord(activityBetRecord);
	}
	/**
	 * 增加需要打码的记录
	 * @param amount
	 * @param brandcode
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updateActivityBetRecord(ActivityRedbag activityBetRecord) throws Exception {
		activityRedbagDao.updateBetRecord(activityBetRecord);
	}

	private static double runRaffle(String __prizeOrOdds) {
		Map<String, Object> __object = JSONUnit.getMapFromJson(__prizeOrOdds);
		List<String> __prizeList = new ArrayList<String>();
		for (String __key : __object.keySet()) {
			double __odds = Double.parseDouble(String.valueOf(__object.get(__key)));//得到概率
			if(__odds > 0 ) {
				int __ot = (int)(__odds*100);
				for (int i=0;i<__ot;i++) {
					__prizeList.add(__key);
					//System.out.println(__key);
				}
			}
		}
		for(int i=__prizeList.size();i<=100;i++){
			__prizeList.add("0");
		}
		Collections.shuffle(__prizeList);
		int __size = new Random().nextInt(__prizeList.size());
		return Double.valueOf(__prizeList.get(__size));
	}
	
	public static void main(String[] args) {
		String __prizeOrOdds = "{8:0.7,18:0.1,28:0.1,38:0.1,58:0,68:0,88:0,98:0,138:0,168:0,188:0}";
		for (int i = 1; i <= 100; i++) {
			System.out.println(i+"="+runRaffle(__prizeOrOdds));
		}
	}
	
	/**
	 * 完善资料送红包
	 */
	@SuppressWarnings("serial")
	@Override
	public Map<String, Object> tc_redbag_userinfo(final String __employeecode, final int __enterprisebrandactivitycode, final String loginip, final String fingerprintcode) throws Exception{
		//1、检查此人当日是否已领取过红包
		//2、没有领取过的，检查是否满足存款次数与存款金额
		//3、领取红包。按照比例，随机红包
		//4、增加打码记录和待审核记录
		Map<String,Object> __returnAument = new HashMap<String, Object>();
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("employeecode", __employeecode);
		params.put("enterprisebrandactivitycode", __enterprisebrandactivitycode);
		params.put("redbagtype", ActivityRedbag.Enum_redbagtype.完善资料领红包.value.toString());
		int count = activityRedbagDao.selectBetRecordCount(params);
		if(count > 0) {
			__returnAument.put("status", "fail");
			__returnAument.put("message", "领取失败。您已经领取过红包了");
			return __returnAument;
		}
		
		//二次检查是否存在相同IP和浏览器编码的玩家领取红包
		params = new HashMap<String, Object>();
		params.put("loginip", loginip);
		params.put("fingerprintcode", fingerprintcode);
		params.put("enterprisebrandactivitycode", __enterprisebrandactivitycode);
		params.put("redbagtype", ActivityRedbag.Enum_redbagtype.完善资料领红包.value.toString());
		count = activityRedbagDao.selectBetRecordCount(params);
		if(count > 0) {
			__returnAument.put("status", "fail");
			__returnAument.put("message", "领取失败。相同IP已经领取过红包了");
			System.out.println("===================完善资料领红包，相同IP已经领取过：__employeecode="+__employeecode+" loginip="+loginip+" fingerprintcode="+fingerprintcode);
			return __returnAument;
		}
		
		Map<String,Object> __activityAgument = enterpriseBrandActivityService.selectActivityAgument(__enterprisebrandactivitycode);
		
		String REDBAG_RATE =String.valueOf(__activityAgument.get(PARAMS_REDBAG_RATE));// 红包大小及概率（必填） 
		String IS_AUTO_PAY =String.valueOf(__activityAgument.get(PARAMS_IS_AUTO_PAY));// 是否自动发放（1=自动，2=人工审核） 
		String GET_FUNCTION =String.valueOf(__activityAgument.get(PARAMS_GET_FUNCTION));//领取方式（1=接口手动，2=登录自动领取）
		String LSBS =String.valueOf(__activityAgument.get(PARAMS_LSBS));//流水倍数（可为0）
		
		
		/*********************开始抽奖************************/
    	double __prize = runRaffle("{"+REDBAG_RATE+"}");
		
		EnterpriseEmployee __employee = enterpriseEmployeeService.takeEmployeeByCode(__employeecode);
		
		ActivityRedbag activityRedbag = new ActivityRedbag();
		activityRedbag.setCreatetime(new Date());
		activityRedbag.setEmployeecode(__employee.getEmployeecode());
		activityRedbag.setEnterprisecode(__employee.getEnterprisecode());
		activityRedbag.setLoginaccount(__employee.getLoginaccount());
		activityRedbag.setLogindate(StringUtils.getDate());
		activityRedbag.setLoginip(loginip);
		activityRedbag.setLsh(UUID.randomUUID().toString());
		activityRedbag.setMoney(__prize);
		activityRedbag.setLsbs(LSBS);
		activityRedbag.setEnterprisebrandactivitycode(__enterprisebrandactivitycode+"");
		activityRedbag.setRedbagtype(ActivityRedbag.Enum_redbagtype.完善资料领红包.value);
		activityRedbag.setFingerprintcode(fingerprintcode);
		
		if(IS_AUTO_PAY.equals("") || IS_AUTO_PAY.equals("null") || IS_AUTO_PAY.equals("1")) {
			//自动发放
			activityRedbag.setStatus(ActivityRedbag.Enum_status.已发放.value);
			activityRedbag.setAuditer("系统");
			activityRedbag.setAuditremark("通过");
			activityRedbag.setAudittime(new Date());
			activityRedbag.setPayer("系统");
			activityRedbag.setPaytyime(new Date());
			
			//打码流水记录
			if(LSBS.equals("") || LSBS.equals("null")) {
				LSBS = "0";
			}
			
			EnterpriseBrandActivity brandActivity = enterpriseBrandActivityService.checkEnterpriseBrandActivity(__enterprisebrandactivitycode);
			if (brandActivity == null){
				throw new LogicTransactionRollBackException(ActivityCheckMessage.BRAND_ACTIVITY_NOTEXIST.desc);
			}
			activityRedbag.setEctivityname(brandActivity.getActivityname());
			
			double betMultiple = Double.valueOf(LSBS);
			if(betMultiple > 0) {
				ActivityBetRecord betrecord = new ActivityBetRecord();
				betrecord.setEmployeecode(__employee.getEmployeecode());
				betrecord.setEcactivitycode(brandActivity.getEcactivitycode());
				betrecord.setMustbet( (Double.valueOf(__prize) ) * betMultiple);
				betrecord.setAlreadybet(0.0);
				betrecord.setBetrecordstatus(Enum_betrecordstatus.未完成.value);
				betrecord.setCreatetime(new Date());
				betrecord.setLoginaccount(__employee.getLoginaccount());
				betrecord.setRecharge(Double.valueOf(__prize));//充值金额
				
				betrecord.setEnterprisecode(__employee.getEnterprisecode());//企业编码
				betrecord.setBrandcode(__employee.getBrandcode());//品牌编码
				betrecord.setParentemployeeaccount(__employee.getParentemployeeaccount());//上级账号
				betrecord.setParentemployeecode(__employee.getParentemployeecode());//上级编码
				activityBetRecordDao.addBetRecord(betrecord);
			}
			
			//写入帐变记录
			enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(RandomString.UUID(), __employeecode, new BigDecimal(__prize), 
					new EmployeeMoneyChangeType(Enum_moneychangetype.优惠活动.value,Enum_moneychangetype.优惠活动.desc, Enum_moneyinouttype.进账), 
					"操作人:"+__employee.getLoginaccount() +" 活动：完善资料领红包");
		} else {
			//人工审核
			activityRedbag.setStatus(ActivityRedbag.Enum_status.待审核.value);
		}
		
		userLogsService.addActivityBetRecord(new UserLogs(activityRedbag.getEnterprisecode(), activityRedbag.getEmployeecode(), activityRedbag.getLoginaccount(), 
				UserLogs.Enum_operatype.红利信息业务, "完善资料领红包"+activityRedbag.getMoney()+"元", null, null));
		
		activityRedbagDao.addBetRecord(activityRedbag);
		
		
		__returnAument.put("status", "success");
		__returnAument.put("message", "领取成功。");
		__returnAument.put("money", __prize);
		return __returnAument;
	}
	
	/**
	 * 注册送红包
	 */
	@SuppressWarnings("serial")
	@Override
	public Map<String, Object> tc_redbag_regedit(final String __employeecode, final int __enterprisebrandactivitycode, final String loginip, final String fingerprintcode) throws Exception{
		//1、检查此人当日是否已领取过红包
		//2、没有领取过的，检查是否满足存款次数与存款金额
		//3、领取红包。按照比例，随机红包
		//4、增加打码记录和待审核记录
		Map<String,Object> __returnAument = new HashMap<String, Object>();
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("employeecode", __employeecode);
		params.put("redbagtype", ActivityRedbag.Enum_redbagtype.注册红包.value.toString());
		params.put("enterprisebrandactivitycode", __enterprisebrandactivitycode);
		int count = activityRedbagDao.selectBetRecordCount(params);
		if(count > 0) {
			__returnAument.put("status", "fail");
			__returnAument.put("message", "领取失败。您已经领取过注册红包了");
			return __returnAument;
		}
		
		//二次检查是否存在相同IP和浏览器编码的玩家领取红包
		params = new HashMap<String, Object>();
		params.put("loginip", loginip);
		params.put("fingerprintcode", fingerprintcode);
		params.put("enterprisebrandactivitycode", __enterprisebrandactivitycode);
		params.put("redbagtype", ActivityRedbag.Enum_redbagtype.注册红包.value.toString());
		count = activityRedbagDao.selectBetRecordCount(params);
		if(count > 0) {
			__returnAument.put("status", "fail");
			__returnAument.put("message", "领取失败。相同IP已经领取过红包了");
			System.out.println("===================注册领红包，相同IP已经领取过：__employeecode="+__employeecode+" loginip="+loginip+" fingerprintcode="+fingerprintcode);
			return __returnAument;
		}
		
		
		Map<String,Object> __activityAgument = enterpriseBrandActivityService.selectActivityAgument(__enterprisebrandactivitycode);
		
		String REDBAG_RATE =String.valueOf(__activityAgument.get(PARAMS_REDBAG_RATE));// 红包大小及概率（必填） 
		String IS_AUTO_PAY =String.valueOf(__activityAgument.get(PARAMS_IS_AUTO_PAY));// 是否自动发放（1=自动，2=人工审核） 
		String GET_FUNCTION =String.valueOf(__activityAgument.get(PARAMS_GET_FUNCTION));//领取方式（1=接口手动，2=登录自动领取）
		String LSBS =String.valueOf(__activityAgument.get(PARAMS_LSBS));//流水倍数（可为0）
		
		
		/*********************开始抽奖************************/
    	double __prize = runRaffle("{"+REDBAG_RATE+"}");
		
		EnterpriseEmployee __employee = enterpriseEmployeeService.takeEmployeeByCode(__employeecode);
		
		ActivityRedbag activityRedbag = new ActivityRedbag();
		activityRedbag.setCreatetime(new Date());
		activityRedbag.setEmployeecode(__employee.getEmployeecode());
		activityRedbag.setEnterprisecode(__employee.getEnterprisecode());
		activityRedbag.setLoginaccount(__employee.getLoginaccount());
		activityRedbag.setLogindate(StringUtils.getDate());
		activityRedbag.setLoginip(loginip);
		activityRedbag.setLsh(UUID.randomUUID().toString());
		activityRedbag.setMoney(__prize);
		activityRedbag.setLsbs(LSBS);
		activityRedbag.setEnterprisebrandactivitycode(__enterprisebrandactivitycode+"");
		activityRedbag.setRedbagtype(ActivityRedbag.Enum_redbagtype.注册红包.value);
		activityRedbag.setFingerprintcode(fingerprintcode);
		
		if(IS_AUTO_PAY.equals("") || IS_AUTO_PAY.equals("null") || IS_AUTO_PAY.equals("1")) {
			//自动发放
			activityRedbag.setStatus(ActivityRedbag.Enum_status.已发放.value);
			activityRedbag.setAuditer("系统");
			activityRedbag.setAuditremark("通过");
			activityRedbag.setAudittime(new Date());
			activityRedbag.setPayer("系统");
			activityRedbag.setPaytyime(new Date());
			
			//打码流水记录
			if(LSBS.equals("") || LSBS.equals("null")) {
				LSBS = "0";
			}
			
			EnterpriseBrandActivity brandActivity = enterpriseBrandActivityService.checkEnterpriseBrandActivity(__enterprisebrandactivitycode);
			if (brandActivity == null){
				throw new LogicTransactionRollBackException(ActivityCheckMessage.BRAND_ACTIVITY_NOTEXIST.desc);
			}
			activityRedbag.setEctivityname(brandActivity.getActivityname());
			
			double betMultiple = Double.valueOf(LSBS);
			if(betMultiple > 0) {
				ActivityBetRecord betrecord = new ActivityBetRecord();
				betrecord.setEmployeecode(__employee.getEmployeecode());
				betrecord.setEcactivitycode(brandActivity.getEcactivitycode());
				betrecord.setMustbet( (Double.valueOf(__prize) ) * betMultiple);
				betrecord.setAlreadybet(0.0);
				betrecord.setBetrecordstatus(Enum_betrecordstatus.未完成.value);
				betrecord.setCreatetime(new Date());
				betrecord.setLoginaccount(__employee.getLoginaccount());
				betrecord.setRecharge(Double.valueOf(__prize));//充值金额
				
				betrecord.setEnterprisecode(__employee.getEnterprisecode());//企业编码
				betrecord.setBrandcode(__employee.getBrandcode());//品牌编码
				betrecord.setParentemployeeaccount(__employee.getParentemployeeaccount());//上级账号
				betrecord.setParentemployeecode(__employee.getParentemployeecode());//上级编码
				activityBetRecordDao.addBetRecord(betrecord);
			}
			
			//写入帐变记录
			enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(RandomString.UUID(), __employeecode, new BigDecimal(__prize), 
					new EmployeeMoneyChangeType(Enum_moneychangetype.优惠活动.value,Enum_moneychangetype.优惠活动.desc, Enum_moneyinouttype.进账), 
					"操作人:"+__employee.getLoginaccount() +" 活动：注册领红包");
		} else {
			//人工审核
			activityRedbag.setStatus(ActivityRedbag.Enum_status.待审核.value);
		}
		
		userLogsService.addActivityBetRecord(new UserLogs(activityRedbag.getEnterprisecode(), activityRedbag.getEmployeecode(), activityRedbag.getLoginaccount(), 
				UserLogs.Enum_operatype.红利信息业务, "注册领取红包"+activityRedbag.getMoney()+"元", null, null));
		
		activityRedbagDao.addBetRecord(activityRedbag);
		
		
		__returnAument.put("status", "success");
		__returnAument.put("message", "已领取注册彩金"+__prize+"元");
		__returnAument.put("money", __prize);
		return __returnAument;
	}
	
	/**
	 * 签到送红包
	 */
	@SuppressWarnings("serial")
	@Override
	public Map<String, Object> tc_redbag(final String __employeecode, final int __enterprisebrandactivitycode, final String loginip) throws Exception{
		//1、检查此人当日是否已领取过红包
		//2、没有领取过的，检查是否满足存款次数与存款金额
		//3、领取红包。按照比例，随机红包
		//4、增加打码记录和待审核记录
		Map<String,Object> __returnAument = new HashMap<String, Object>();
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("employeecode", __employeecode);
		params.put("logindate", StringUtils.getDate());
		params.put("enterprisebrandactivitycode", __enterprisebrandactivitycode);
		params.put("redbagtype", ActivityRedbag.Enum_redbagtype.签到红包.value.toString());
		int count = activityRedbagDao.selectBetRecordCount(params);
		if(count > 0) {
			__returnAument.put("status", "fail");
			__returnAument.put("message", "领取失败。您今天已经领取过红包了");
			return __returnAument;
		}
		
		//二次检查是否存在相同IP和浏览器编码的玩家领取红包
		params = new HashMap<String, Object>();
		params.put("loginip", loginip);
		params.put("logindate", StringUtils.getDate());
		params.put("enterprisebrandactivitycode", __enterprisebrandactivitycode);
		params.put("redbagtype", ActivityRedbag.Enum_redbagtype.签到红包.value.toString());
		count = activityRedbagDao.selectBetRecordCount(params);
		if(count > 0) {
			__returnAument.put("status", "fail");
			__returnAument.put("message", "领取失败。相同IP已经领取过红包了");
			System.out.println("===================签到领红包，相同IP已经领取过：__employeecode="+__employeecode+" loginip="+loginip);
			return __returnAument;
		}
		
		Map<String,Object> __activityAgument = enterpriseBrandActivityService.selectActivityAgument(__enterprisebrandactivitycode);
		
		String RECHARGE_NUM =String.valueOf(__activityAgument.get(PARAMS_RECHARGE_NUM));//至少存款次数（可为0）
		String RECHARGE_MONEY =String.valueOf(__activityAgument.get(PARAMS_RECHARGE_MONEY));// 至少存款金额（可为0） 
		String REDBAG_RATE =String.valueOf(__activityAgument.get(PARAMS_REDBAG_RATE));// 红包大小及概率（必填） 
		String IS_AUTO_PAY =String.valueOf(__activityAgument.get(PARAMS_IS_AUTO_PAY));// 是否自动发放（1=自动，2=人工审核） 
		String GET_FUNCTION =String.valueOf(__activityAgument.get(PARAMS_GET_FUNCTION));//领取方式（1=接口手动，2=登录自动领取）
		String LSBS =String.valueOf(__activityAgument.get(PARAMS_LSBS));//流水倍数（可为0）
		
		if(RECHARGE_NUM.equals("") || RECHARGE_NUM.equals("null") || RECHARGE_NUM.equals("0")) {
			//无要求
		} else {
			params.clear();
			params.put("enterprisecode", __employeecode);
			params.put("ordertype", "1");//存款
			params.put("orderstatus", "2");//订单状态
			int countSize = takeDepositRecoredService.findCountDepositTakeRecordDatailCount(params);
			if(countSize < Integer.valueOf(RECHARGE_NUM)) {
				__returnAument.put("status", "fail");
				__returnAument.put("message", "领取失败。您未能满足条件：存款次数不足"+RECHARGE_NUM+"次！");
				return __returnAument;
			}
		}
    	
		if(RECHARGE_MONEY.equals("") || RECHARGE_MONEY.equals("null") || RECHARGE_MONEY.equals("0")) {
			//无要求
		} else {
			double money = takeDepositRecoredService.call_userDepositTakeMoney(__employeecode, 2);//历史累计存款总额
			if(money < Double.valueOf(RECHARGE_MONEY)) {
				__returnAument.put("status", "fail");
				__returnAument.put("message", "领取失败。您未能满足条件：历史累计存款不足"+RECHARGE_MONEY+"元！");
				return __returnAument;
			}
		}
		
		/*********************开始抽奖************************/
    	double __prize = runRaffle("{"+REDBAG_RATE+"}");
		
		EnterpriseEmployee __employee = enterpriseEmployeeService.takeEmployeeByCode(__employeecode);
		
		ActivityRedbag activityRedbag = new ActivityRedbag();
		activityRedbag.setCreatetime(new Date());
		activityRedbag.setEmployeecode(__employee.getEmployeecode());
		activityRedbag.setEnterprisecode(__employee.getEnterprisecode());
		activityRedbag.setLoginaccount(__employee.getLoginaccount());
		activityRedbag.setLogindate(StringUtils.getDate());
		activityRedbag.setLoginip(loginip);
		activityRedbag.setLsh(UUID.randomUUID().toString());
		activityRedbag.setMoney(__prize);
		activityRedbag.setLsbs(LSBS);
		activityRedbag.setEnterprisebrandactivitycode(__enterprisebrandactivitycode+"");
		activityRedbag.setRedbagtype(ActivityRedbag.Enum_redbagtype.签到红包.value);
		
		if(IS_AUTO_PAY.equals("") || IS_AUTO_PAY.equals("null") || IS_AUTO_PAY.equals("1")) {
			//自动发放
			activityRedbag.setStatus(ActivityRedbag.Enum_status.已发放.value);
			activityRedbag.setAuditer("系统");
			activityRedbag.setAuditremark("通过");
			activityRedbag.setAudittime(new Date());
			activityRedbag.setPayer("系统");
			activityRedbag.setPaytyime(new Date());
			
			//打码流水记录
			if(LSBS.equals("") || LSBS.equals("null")) {
				LSBS = "0";
			}
			
			EnterpriseBrandActivity brandActivity = enterpriseBrandActivityService.checkEnterpriseBrandActivity(__enterprisebrandactivitycode);
			if (brandActivity == null){
				throw new LogicTransactionRollBackException(ActivityCheckMessage.BRAND_ACTIVITY_NOTEXIST.desc);
			}
			activityRedbag.setEctivityname(brandActivity.getActivityname());
			
			double betMultiple = Double.valueOf(LSBS);
			if(betMultiple > 0) {
				ActivityBetRecord betrecord = new ActivityBetRecord();
				betrecord.setEmployeecode(__employee.getEmployeecode());
				betrecord.setEcactivitycode(brandActivity.getEcactivitycode());
				betrecord.setMustbet( (Double.valueOf(__prize) ) * betMultiple);
				betrecord.setAlreadybet(0.0);
				betrecord.setBetrecordstatus(Enum_betrecordstatus.未完成.value);
				betrecord.setCreatetime(new Date());
				betrecord.setLoginaccount(__employee.getLoginaccount());
				betrecord.setRecharge(Double.valueOf(__prize));//充值金额
				
				betrecord.setEnterprisecode(__employee.getEnterprisecode());//企业编码
				betrecord.setBrandcode(__employee.getBrandcode());//品牌编码
				betrecord.setParentemployeeaccount(__employee.getParentemployeeaccount());//上级账号
				betrecord.setParentemployeecode(__employee.getParentemployeecode());//上级编码
				activityBetRecordDao.addBetRecord(betrecord);
			}
			
			//写入帐变记录
			enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(RandomString.UUID(), __employeecode, new BigDecimal(__prize), 
					new EmployeeMoneyChangeType(Enum_moneychangetype.优惠活动.value,Enum_moneychangetype.优惠活动.desc, Enum_moneyinouttype.进账), 
					"操作人:"+__employee.getLoginaccount() +" 活动：签到领红包");
		} else {
			//人工审核
			activityRedbag.setStatus(ActivityRedbag.Enum_status.待审核.value);
		}
		
		userLogsService.addActivityBetRecord(new UserLogs(activityRedbag.getEnterprisecode(), activityRedbag.getEmployeecode(), activityRedbag.getLoginaccount(), 
				UserLogs.Enum_operatype.红利信息业务, "签到领取红包"+activityRedbag.getMoney()+"元", null, null));
		
		activityRedbagDao.addBetRecord(activityRedbag);
		
		
		__returnAument.put("status", "success");
		__returnAument.put("message", "领取成功。");
		__returnAument.put("money", __prize);
		return __returnAument;
	}

}
