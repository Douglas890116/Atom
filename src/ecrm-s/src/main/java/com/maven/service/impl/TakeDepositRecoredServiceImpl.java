package com.maven.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maven.base.dao.BaseDao;
import com.maven.base.dao.DataSource;
import com.maven.cache.SystemCache;
import com.maven.dao.ActivityRaffleControlDao;
import com.maven.dao.EnterpriseActivityPayDao;
import com.maven.dao.EnterpriseEmployeeInformationDao;
import com.maven.dao.LogLoginDao;
import com.maven.dao.TakeDepositRecoredDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityDepositBonus;
import com.maven.entity.ActivityRaffleControl;
import com.maven.entity.DepositWithdralOrderDelegate;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EnterpriseActivityCustomization;
import com.maven.entity.EnterpriseActivityPay;
import com.maven.entity.EnterpriseBrandActivity;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeCapitalAccount;
import com.maven.entity.EnterpriseEmployeeInformation;
import com.maven.entity.PaymentType;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.entity.TakeDepositRecord;
import com.maven.entity.TakeDepositRecord.Enum_ordertype;
import com.maven.entity.UserLogs;
import com.maven.entity.WorkingFlowConfiguration;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityDepositBonus.DepositBonusModelSettings;
import com.maven.entity.ActivityTemplate.Enum_activity;
import com.maven.entity.WorkingFlowConfiguration.Enum_flowtype;
import com.maven.game.APIServiceNew;
import com.maven.logger.TLogger;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.ActivityDepositBonusService;
import com.maven.service.ActivityRaffleControlService;
import com.maven.service.DepositWithdralOrderDelegateService;
import com.maven.service.EnterpriseActivityCustomizationService;
import com.maven.service.EnterpriseBrandActivityService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.TakeDepositRecoredService;
import com.maven.service.UserLogsService;
import com.maven.util.ActivityUtils;
import com.maven.util.AttrCheckout;
import com.maven.util.JSONUnit;
import com.maven.util.StringUtils;

@Service
public class TakeDepositRecoredServiceImpl extends BaseServiceImpl<TakeDepositRecord>
		implements TakeDepositRecoredService {

	@Autowired
	private TakeDepositRecoredDao takeDepositRecoredDao;

	@Autowired
	private ActivityRaffleControlService activityRaffleControlService;
	
	@Autowired
	private EnterpriseEmployeeCapitalAccountService enterpriseEmployeeCapitalAccountService;
	
	@Autowired
	private ActivityBetRecordService activityBetRecordService;
	@Autowired
	private EnterpriseActivityCustomizationService enterpriseActivityCustomizationService;
	@Autowired
	private EnterpriseBrandActivityService enterpriseBrandActivityService;
	@Autowired
	private LogLoginDao logLoginDao;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private EnterpriseEmployeeInformationDao enterpriseEmployeeInformationDao;

	/**
	 * 订单委托
	 */
	@Autowired
	private DepositWithdralOrderDelegateService depositWithdralOrderDelegateService;
	@Autowired
	private ActivityDepositBonusService activityDepositBonusService;
	
	@Autowired
	private EnterpriseActivityPayDao enterpriseActivityPayDao;
	
	@Autowired
	private EnterpriseEmployeeCapitalAccountService capitalAccountService;
	
	@Autowired
	private UserLogsService userLogsService;

	@Override
	public BaseDao<TakeDepositRecord> baseDao() {
		return takeDepositRecoredDao;
	}

	@Override
	public Class<TakeDepositRecord> getClazz() {
		return TakeDepositRecord.class;
	}
	
	/**
	 * 根据充值金额返回返利比例
	 * @param randMoney
	 * @param recharge
	 * @return
	 */
	private double getRand(String randMoney, double recharge) {
		Map<String, Object> __object = JSONUnit.getMapFromJson("{"+randMoney+"}");
		
		String min_key = "";
		List<String> list = new ArrayList<String>();
		for (String __key : __object.keySet()) {
			list.add(__key);
		}
		Collections.sort(list);
		min_key = list.get(0);
		
		for (String __key : __object.keySet()) {
			double start = Double.valueOf(__key.split("-")[0]);
			double end   = Double.valueOf(__key.split("-")[1]);
			if(recharge >= start && recharge<= end) {
				//在这个范围内
				min_key = __key;
				break;
			}
		}
		return Double.valueOf(__object.get(min_key).toString());
	}
	
	/**
	 * 充值返利活动处理流程
	 * 
	 * 该逻辑涉及到首存和次次存：只会执行对应的一个逻辑
	 * 
	 * @param depositeRecord
	 * @param handles
	 * @param flowtype
	 * @throws Exception
	 */
	public void saveingVerify(TakeDepositRecord depositeRecord, DepositWithdralOrderDelegate handles) throws Exception {
		
		String employeecode = depositeRecord.getEmployeecode();
		//检查是否是第一次存送
		Map<String, Object> paramObj = new HashMap<String, Object>();
		paramObj.put("employeecode", depositeRecord.getEmployeecode());
		paramObj.put("flag", 2);//0=昨天 1=当天 2=历史累计
		double allmoney = takeDepositRecoredDao.userDepositTakeMoney(paramObj);
		
		if(allmoney > depositeRecord.getOrderamount().doubleValue()) {
			System.out.println(employeecode+"=saveingVerify=11="+allmoney+"="+depositeRecord.getOrderamount().doubleValue());
			//总额大于本次充值金额，非首次，则可以进入次次存业务
			
			/******************************************** 处理次次存送 the start********************************************/
			//二选一活动，不能共享
			boolean flag = doEachSaveing(depositeRecord, handles);//充值赠送高级版
			System.out.println(employeecode+"=saveingVerify=12="+flag+"=");
			if(flag == false) {
				flag = doEachSaveing2(depositeRecord, handles);//每日充值享受抽奖
				System.out.println(employeecode+"=saveingVerify=13="+flag+"=");
			}
			/******************************************** 处理次次存送 the end********************************************/
			
		} else {
			System.out.println(employeecode+"=saveingVerify=21="+allmoney+"="+depositeRecord.getOrderamount().doubleValue());
			// 处理首存业务，如果享受到了返回true
			boolean result = doFirstSaveing(depositeRecord, handles);//首存赠送
			System.out.println(employeecode+"=saveingVerify=22="+result+"=");
			//首次没有享受，可能是活动未开启/已过期/未满足条件等可能，则需要进入次次存业务
			if(result == false) {
				/******************************************** 处理次次存送 the start********************************************/
				//二选一活动，不能共享
				boolean flag = doEachSaveing(depositeRecord, handles);//充值赠送高级版
				System.out.println(employeecode+"=saveingVerify=23="+flag+"=");
				if(flag == false) {
					flag = doEachSaveing2(depositeRecord, handles);//每日充值享受抽奖
					System.out.println(employeecode+"=saveingVerify=24="+flag+"=");
				}
				/******************************************** 处理次次存送 the end********************************************/
			}
		}
		
		//end
	}
	
	private boolean doFirstSaveing(TakeDepositRecord depositeRecord, DepositWithdralOrderDelegate handles) throws Exception {
		//1=查找企业绑定的活动编号，也就是‘首存赠送’活动模板
		//2=查找有效的，正在进行中的‘首存赠送’企业活动
		//3=返利
		//4=打码
		EnterpriseActivityCustomization activityCustomization = new EnterpriseActivityCustomization();
		activityCustomization.setActivitytemplatecode(Enum_activity.首存赠送.activitycode);//首存赠送，固定值
		activityCustomization.setEnterprisecode(depositeRecord.getEnterprisecode());
		activityCustomization.setDatastatus(EnterpriseBrandActivity.Enum_status.启用.value);//有效的
		List<EnterpriseActivityCustomization> list = enterpriseActivityCustomizationService.select(activityCustomization);
		if(list == null || list.size() == 0) {
			return false;
		}
		
		final EnterpriseEmployee ee = enterpriseEmployeeService.takeEmployeeByCode(depositeRecord.getEmployeecode());
			
		for (EnterpriseActivityCustomization customization : list) {
			
			
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("enterprisecode", customization.getEnterprisecode());
			queryParams.put("ecactivitycode", customization.getEcactivitycode());
			queryParams.put("status", EnterpriseBrandActivity.Enum_status.启用.value);
			List<EnterpriseBrandActivity> listEnterpriseBrandActivity = enterpriseBrandActivityService.selectAll(queryParams);
			
			if(listEnterpriseBrandActivity == null || listEnterpriseBrandActivity.size() == 0) {
				continue;
			}
			
			for (EnterpriseBrandActivity activity : listEnterpriseBrandActivity) {

				
				if(activity == null || !activity.getStatus().equals(EnterpriseBrandActivity.Enum_status.启用.value)) {
					continue;
				}
				//检查是否指定品牌的用户才能参与该活动
				if(activity.getBrandcode() != null && !activity.getBrandcode().equals(ee.getBrandcode())) {
					continue;
				}
				
				// 活动是否进行中
				//Date1.after(Date2),当Date1大于Date2时，返回TRUE，当小于等于时，返回false
				//Date1.before(Date2)，当Date1小于Date2时，返回TRUE，当大于等于时，返回false
				Date currendate = new Date();
			    if(currendate.after(activity.getBegintime()) && currendate.before(activity.getEndtime()) ){
			    	
			    	Map<String, Object> mapAgument = enterpriseBrandActivityService.selectActivityAgument(activity.getEnterprisebrandactivitycode());
			    	
			    	String MIN_MONEY = com.maven.util.StringUtils.nullString(mapAgument.get("MIN_MONEY"));//最低限额
			    	String RAND_MONEY = com.maven.util.StringUtils.nullString(mapAgument.get("RAND_MONEY"));//各充值范围的返利比例
			    	String LSBS = com.maven.util.StringUtils.nullString(mapAgument.get("LSBS"));//提款流水倍数（可设0）
			    	String TOP_MONEY = com.maven.util.StringUtils.nullString(mapAgument.get("TOP_MONEY"));//最高上限额（填写0即不限封顶）
			    	//是否自动付款（0=非自动 1=自动）非自动时，只生成统计数据报表，不会直接增加余额（注意：仅限测试时观察数据使用。测试情况下，务必设置为0）
					String IS_AUTO_PAY = com.maven.util.StringUtils.nullString(mapAgument.get("IS_AUTO_PAY"));
					if(IS_AUTO_PAY.equals("")) {
						IS_AUTO_PAY = "0";
					}
					if(TOP_MONEY.equals("")) {
						TOP_MONEY = "0";
					}
					if(MIN_MONEY.equals("")) {
						MIN_MONEY = "0";
					}
					if(LSBS.equals("")) {
						LSBS = "0";
					}
					
					/*********************	开始返利（满足最低限额）*********************/
			    	if(depositeRecord.getOrderamount().doubleValue() >= Double.valueOf(MIN_MONEY) ) {
			    		
			    		double amount = depositeRecord.getOrderamount().doubleValue();
		    			double rand = getRand(RAND_MONEY, amount);
		    			double addMoney = amount * rand;//得到应返利金额
		    			
		    			//是否有上限要求
		    			if( !TOP_MONEY.equals("0")) {
		    				if(addMoney > Double.valueOf(TOP_MONEY)) {
		    					addMoney = Double.valueOf(TOP_MONEY);
		    				}
		    			}
		    			
		    			if(IS_AUTO_PAY.equals("1")) {
		    				
			    			//将资金存入用户账户
							enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(depositeRecord.getOrdernumber(),depositeRecord.getEmployeecode(), new BigDecimal(addMoney),
									new EmployeeMoneyChangeType(Enum_moneychangetype.优惠活动.value,Enum_moneychangetype.优惠活动.desc,Enum_moneyinouttype.进账),"活动:"+Enum_activity.首存赠送.desc+"");
							
							EnterpriseEmployeeInformation eei = enterpriseEmployeeInformationDao.queryAccountData(ee);
							String allIp = ActivityUtils.ipLink(logLoginDao.selectIpByLoginaccount(depositeRecord.getLoginaccount()));
							//增加存款红利领取记录
							ActivityDepositBonus newdepositbonusrecord = new ActivityDepositBonus();
							newdepositbonusrecord.setOrdernumber(depositeRecord.getOrdernumber());
							newdepositbonusrecord.setEmployeecode(depositeRecord.getEmployeecode());
							newdepositbonusrecord.setParentemployeecode(depositeRecord.getParentemployeecode());
							newdepositbonusrecord.setLoginaccount(depositeRecord.getLoginaccount());
							newdepositbonusrecord.setDepositamount(depositeRecord.getOrderamount().doubleValue());
							newdepositbonusrecord.setReturnratio(rand);//返奖比例
							newdepositbonusrecord.setReturnamount(addMoney);
							newdepositbonusrecord.setDeposittime(depositeRecord.getOrderdate());
							newdepositbonusrecord.setGettime(new Date());
							newdepositbonusrecord.setUniqueinfo("||"+eei.getEmail()+"|"+eei.getPhonenumber()+"|"+eei.getPaymentaccount()+"|"+allIp);
							newdepositbonusrecord.setDepositType(activity.getEnterprisebrandactivitycode().toString());
							activityDepositBonusService.saveActivityDepositBonus(newdepositbonusrecord);
						
		    			}
		    			
		    			//增加活动红利支付审核记录
		    			Date now = new Date();
		    			EnterpriseActivityPay activityPay = new EnterpriseActivityPay();
		    			activityPay.setBrandcode(ee.getBrandcode());
		    			activityPay.setEmployeecode(ee.getEmployeecode());
		    			activityPay.setEnterprisecode(ee.getEnterprisecode());
		    			activityPay.setLoginaccount(ee.getLoginaccount());
		    			activityPay.setParentemployeecode(ee.getParentemployeecode());
		    			activityPay.setAcname(activity.getActivityname());
		    			activityPay.setEcactivitycode(activity.getEcactivitycode());
		    			activityPay.setCreatetime(now);
		    			activityPay.setPaymoneyaudit(addMoney);
		    			activityPay.setPaymoneyreal(addMoney);
		    			if(IS_AUTO_PAY.equals("1")) {
		    				activityPay.setAuditer("");
			    			activityPay.setAuditremark("活动：首存赠送，自动发放");
			    			activityPay.setAudittime(now);
			    			activityPay.setPayer("");
			    			activityPay.setPaystatus(EnterpriseActivityPay.Enum_paystatus.已支付.value);
			    			activityPay.setPaytyime(now);
			    			activityPay.setPaytype(EnterpriseActivityPay.Enum_paytype.自动发放.value);
		    			} else {
		    				//activityPay.setAuditer("");
			    			//activityPay.setAuditremark("活动：注册送彩金，自动发放");
			    			//activityPay.setAudittime(now);
			    			//activityPay.setPayer("");
			    			//activityPay.setPaytyime(now);
		    				activityPay.setPaystatus(EnterpriseActivityPay.Enum_paystatus.待审核.value);
			    			activityPay.setPaytype(EnterpriseActivityPay.Enum_paytype.人工发放.value);
		    			}
		    			activityPay.setDesc("");
		    			activityPay.setLsbs(LSBS);
		    			enterpriseActivityPayDao.addBetRecord(activityPay);
		    			
						
						//手工存款加入默认打码
						double betMultiple = Double.valueOf(LSBS);
						if(betMultiple > 0) {
							ActivityBetRecord betrecord = new ActivityBetRecord();
							betrecord.setEmployeecode(depositeRecord.getEmployeecode());
							betrecord.setEcactivitycode(customization.getEcactivitycode());
							betrecord.setMustbet( (amount + addMoney) * betMultiple);
							betrecord.setAlreadybet(0.0);
							betrecord.setBetrecordstatus(Enum_betrecordstatus.未完成.value);
							betrecord.setCreatetime(new Date());
							betrecord.setLoginaccount(depositeRecord.getLoginaccount());
							betrecord.setRecharge(addMoney);//充值金额
							
							betrecord.setEnterprisecode(depositeRecord.getEnterprisecode());//企业编码
							betrecord.setBrandcode(depositeRecord.getBrandcode());//品牌编码
							betrecord.setParentemployeeaccount(depositeRecord.getParentemployeeaccount());//上级账号
							betrecord.setParentemployeecode(depositeRecord.getParentemployeecode());//上级编码
							activityBetRecordService.addActivityBetRecord(betrecord);
						}
						
						userLogsService.addActivityBetRecord(new UserLogs(activityPay.getEnterprisecode(), activityPay.getEmployeecode(), activityPay.getLoginaccount(), 
								UserLogs.Enum_operatype.红利信息业务, "获得首存赠送活动彩金"+addMoney+"元", null, null));
						
						
						return true;
			    	} //end
			    	
			    }
			}
		}
		
		return false;
	}
	/**
	 * 注意：该业务又分为次次存送业务和每日充值享受抽奖一次的活动
	 * 
	 * 对于这两个活动，不允许共享
	 * 
	 * @param depositeRecord
	 * @param handles
	 * @throws Exception
	 */
	private boolean doEachSaveing2(TakeDepositRecord depositeRecord, DepositWithdralOrderDelegate handles) throws Exception {
		String employeecode = depositeRecord.getEmployeecode();
		System.out.println(employeecode+"=saveingVerify=doEachSaveing2="+0+"=");
		
		boolean flag = false;//二选一活动，不能共享
		final EnterpriseEmployee ee = enterpriseEmployeeService.takeEmployeeByCode(depositeRecord.getEmployeecode());
		EnterpriseActivityCustomization activityCustomization = new EnterpriseActivityCustomization();
		activityCustomization = new EnterpriseActivityCustomization();
		activityCustomization.setActivitytemplatecode(Enum_activity.每日充值享受抽奖.activitycode);//每日充值享受抽奖，固定值
		activityCustomization.setEnterprisecode(depositeRecord.getEnterprisecode());
		activityCustomization.setDatastatus(EnterpriseBrandActivity.Enum_status.启用.value);//有效的
		List<EnterpriseActivityCustomization> list = enterpriseActivityCustomizationService.select(activityCustomization);
		if(list == null || list.size() == 0) {
			return false;
		}
		System.out.println(employeecode+"=saveingVerify=doEachSaveing2="+1+"=");
			
		for (EnterpriseActivityCustomization customization : list) {
			
			System.out.println(employeecode+"=saveingVerify=doEachSaveing2="+2+"=");
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("enterprisecode", customization.getEnterprisecode());
			queryParams.put("ecactivitycode", customization.getEcactivitycode());
			queryParams.put("status", EnterpriseBrandActivity.Enum_status.启用.value);
			List<EnterpriseBrandActivity> listEnterpriseBrandActivity = enterpriseBrandActivityService.selectAll(queryParams);
			
			if(listEnterpriseBrandActivity == null || listEnterpriseBrandActivity.size() == 0) {
				continue;
			}
			System.out.println(employeecode+"=saveingVerify=doEachSaveing2="+3+"=");
			for (EnterpriseBrandActivity activity : listEnterpriseBrandActivity) {

				
				if(activity == null || !activity.getStatus().equals(EnterpriseBrandActivity.Enum_status.启用.value)) {
					continue;
				}
				//检查是否指定品牌的用户才能参与该活动
				if(activity.getBrandcode() != null && !activity.getBrandcode().equals(ee.getBrandcode())) {
					continue;
				}
				System.out.println(employeecode+"=saveingVerify=doEachSaveing2="+4+"=");
				// 活动是否进行中
				//Date1.after(Date2),当Date1大于Date2时，返回TRUE，当小于等于时，返回false
				//Date1.before(Date2)，当Date1小于Date2时，返回TRUE，当大于等于时，返回false
				Date currendate = new Date();
			    if(currendate.after(activity.getBegintime()) && currendate.before(activity.getEndtime()) ){
			    	System.out.println(employeecode+"=saveingVerify=doEachSaveing2="+5+"=");
			    	Map<String, Object> __activityAgument = enterpriseBrandActivityService.selectActivityAgument(activity.getEnterprisebrandactivitycode());
			    	
			    	String REDBAG_RATE =String.valueOf(__activityAgument.get("REDBAG_RATE"));// 红包大小及概率（必填） 
					String LSBS =String.valueOf(__activityAgument.get("LSBS"));//流水倍数（可为0）
					String DAY_COUNT =String.valueOf(__activityAgument.get("DAY_COUNT"));//每日抽奖总人数（为0不限制）
					String START_MONEY =String.valueOf(__activityAgument.get("START_MONEY"));//单笔最低充值金额（为0不限制）
					String DAY_MONEY =String.valueOf(__activityAgument.get("DAY_MONEY"));//当天累计满
					
					//当天累计存款超过588，允许抽奖一次或者单笔满500
			    	
					DAY_COUNT = com.maven.util.StringUtils.nullString(__activityAgument.get("DAY_COUNT"));
					if(DAY_COUNT.equals("")) {
						DAY_COUNT = "0";
					}
					START_MONEY = com.maven.util.StringUtils.nullString(__activityAgument.get("START_MONEY"));
					if(START_MONEY.equals("")) {
						START_MONEY = "0";
					}
					DAY_MONEY = com.maven.util.StringUtils.nullString(__activityAgument.get("DAY_MONEY"));
					if(DAY_MONEY.equals("")) {
						DAY_MONEY = "0";
					}
					System.out.println(employeecode+"=saveingVerify=doEachSaveing2="+6+"="+DAY_COUNT+"="+START_MONEY+"="+DAY_MONEY);
					
					Map<String, Object> paramObj = new HashMap<String, Object>();
					paramObj.put("employeecode", depositeRecord.getEmployeecode());
					paramObj.put("flag", 1);//0=昨天 1=当天 2=历史累计
					double allmoney = takeDepositRecoredDao.userDepositTakeMoney(paramObj);
					System.out.println(employeecode+"=saveingVerify=doEachSaveing2="+7+"="+allmoney);
					
					boolean checkstatus = false;
					
			    	// 满足最低限额
			    	if(  (Double.valueOf(START_MONEY) > 0 && depositeRecord.getOrderamount().doubleValue() >= Double.valueOf(START_MONEY)  )
			    		|| ( Double.valueOf(START_MONEY) <= 0 && allmoney >= Double.valueOf(DAY_MONEY))
			    			) {
			    		System.out.println(employeecode+"=saveingVerify=doEachSaveing2="+8+"="+allmoney);
			    		//再检查是否满足人数要求限制
			    		List<ActivityRaffleControl> listActivityRaffleControl = null;
			    		if(DAY_COUNT.equals("0")) {
			    			checkstatus = true;
			    		} else {
			    			
			    			Map<String,Object> object = new HashMap<String, Object>();
			    			object.put("employeecode", ee.getEmployeecode());
			    			object.put("loginaccount", ee.getLoginaccount());
			    			object.put("createdate", StringUtils.getDate());
			    			object.put("ecactivitycode", activity.getEnterprisebrandactivitycode());
			    			listActivityRaffleControl = activityRaffleControlService.selectAll(object);
			    			if(listActivityRaffleControl == null || listActivityRaffleControl.size() < Integer.valueOf(DAY_COUNT)) {
			    				checkstatus = true;
			    			}
			    			System.out.println(employeecode+"=saveingVerify=doEachSaveing2="+9+"="+checkstatus);
			    		}
			    		
			    		//当前基本条件都满足了，再检查此人是否已经包含在领取人员里
				    	if(checkstatus == true) {
				    		System.out.println(employeecode+"=saveingVerify=doEachSaveing2="+10+"="+checkstatus);
				    		for (ActivityRaffleControl activityRaffleControl : listActivityRaffleControl) {
								if(activityRaffleControl.getEmployeecode().equals(ee.getEmployeecode())) {
									checkstatus = false;
									System.out.println(employeecode+"=saveingVerify=doEachSaveing2="+11+"="+checkstatus);
									break;
								}
							}
				    	}
			    	}
			    	System.out.println(employeecode+"=saveingVerify=doEachSaveing2="+12+"="+checkstatus);
			    	//所有条件满足
			    	if(checkstatus == true) {
			    		ActivityRaffleControl raffleControl = new ActivityRaffleControl();
						raffleControl.setAvailabletimes(1);
						raffleControl.setDonatedate(new Date());
						raffleControl.setEmployeecode(ee.getEmployeecode());
						raffleControl.setParentemployeecode(ee.getParentemployeecode());
						raffleControl.setFinishtimes(0);
						raffleControl.setEcactivitycode(activity.getEnterprisebrandactivitycode());
						raffleControl.setLoginaccount(ee.getLoginaccount());
						raffleControl.setCreatedate(Integer.valueOf(StringUtils.getDate()));
						activityRaffleControlService.addRaffleRecord(raffleControl);
						flag = true;
						System.out.println(employeecode+"=saveingVerify=doEachSaveing2="+13+"="+flag);
			    	}//完
			    	
			    }
				
			}
		}
			
		return flag;
	}
	
	/**
	 * 注意：该业务又分为次次存送业务和每日充值享受抽奖一次的活动
	 * 
	 * 对于这两个活动，不允许共享
	 * 
	 * @param depositeRecord
	 * @param handles
	 * @throws Exception
	 */
	private boolean doEachSaveing(TakeDepositRecord depositeRecord, DepositWithdralOrderDelegate handles) throws Exception {
		
		boolean flag = false;//二选一活动，不能共享
		
		/******************************************** 检查并执行充值返利活动相关业务（jason.xu）the start********************************************/
		//1=查找企业绑定的活动编号，也就是‘充值赠送高级版’活动模板
		//2=查找有效的，正在进行中的‘充值赠送高级版’企业活动
		//3=返利
		//4=打码
		EnterpriseActivityCustomization activityCustomization = new EnterpriseActivityCustomization();
		activityCustomization.setActivitytemplatecode(Enum_activity.充值赠送高级版.activitycode);//充值赠送高级版，固定值
		activityCustomization.setEnterprisecode(depositeRecord.getEnterprisecode());
		activityCustomization.setDatastatus(EnterpriseBrandActivity.Enum_status.启用.value);//有效的
		List<EnterpriseActivityCustomization> list = enterpriseActivityCustomizationService.select(activityCustomization);
		if(list == null || list.size() == 0) {
			return false;
		}
		
		final EnterpriseEmployee ee = enterpriseEmployeeService.takeEmployeeByCode(depositeRecord.getEmployeecode());
			
		for (EnterpriseActivityCustomization customization : list) {
			
			
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("enterprisecode", customization.getEnterprisecode());
			queryParams.put("ecactivitycode", customization.getEcactivitycode());
			queryParams.put("status", EnterpriseBrandActivity.Enum_status.启用.value);
			List<EnterpriseBrandActivity> listEnterpriseBrandActivity = enterpriseBrandActivityService.selectAll(queryParams);
			
			if(listEnterpriseBrandActivity == null || listEnterpriseBrandActivity.size() == 0) {
				continue;
			}
			
			for (EnterpriseBrandActivity activity : listEnterpriseBrandActivity) {

				
				if(activity == null || !activity.getStatus().equals(EnterpriseBrandActivity.Enum_status.启用.value)) {
					continue;
				}
				//检查是否指定品牌的用户才能参与该活动
				if(activity.getBrandcode() != null && !activity.getBrandcode().equals(ee.getBrandcode())) {
					continue;
				}
				
				// 活动是否进行中
				//Date1.after(Date2),当Date1大于Date2时，返回TRUE，当小于等于时，返回false
				//Date1.before(Date2)，当Date1小于Date2时，返回TRUE，当大于等于时，返回false
				Date currendate = new Date();
			    if(currendate.after(activity.getBegintime()) && currendate.before(activity.getEndtime()) ){
			    	Map<String, Object> mapAgument = enterpriseBrandActivityService.selectActivityAgument(activity.getEnterprisebrandactivitycode());
			    	
			    	String MIN_MONEY = com.maven.util.StringUtils.nullString(mapAgument.get("MIN_MONEY"));//最低限额
			    	String RAND_MONEY = com.maven.util.StringUtils.nullString(mapAgument.get("RAND_MONEY"));//各充值范围的返利比例
			    	String LSBS = com.maven.util.StringUtils.nullString(mapAgument.get("LSBS"));//提款流水倍数（可设0）
			    	String PAY_MENT_KEY = com.maven.util.StringUtils.nullString(mapAgument.get("PAY_MENT_KEY"));//限定的充值渠道（不填表示不限定）
			    	String BANK_CODE = com.maven.util.StringUtils.nullString(mapAgument.get("BANK_CODE"));//限定的银行代码（结合支付渠道）
			    	String TOP_MONEY = com.maven.util.StringUtils.nullString(mapAgument.get("TOP_MONEY"));//最高上限额（填写0即不限封顶）
			    	
			    	//是否自动付款（0=非自动 1=自动）非自动时，只生成统计数据报表，不会直接增加余额（注意：仅限测试时观察数据使用。测试情况下，务必设置为0）
					String IS_AUTO_PAY = com.maven.util.StringUtils.nullString(mapAgument.get("IS_AUTO_PAY"));
					if(IS_AUTO_PAY.equals("")) {
						IS_AUTO_PAY = "0";
					}
					if(TOP_MONEY.equals("")) {
						TOP_MONEY = "0";
					}
			    	
			    	// 满足最低限额
			    	if(depositeRecord.getOrderamount().doubleValue() >= Double.valueOf(MIN_MONEY) ) {
			    		
			    		boolean isPayMent = false;//判断渠道
			    		boolean isBankCode = false;//判断银行
			    		
			    		//	有限定支付渠道
			    		if(com.maven.util.StringUtils.handString(PAY_MENT_KEY)) {
			    			String[] payMents = PAY_MENT_KEY.split(",");
			    			for (String string : payMents) {
			    				
			    				if(!com.maven.util.StringUtils.handString(string)) {
			    					continue;
			    				}
								if(depositeRecord.getPaymenttypecode().equals(PaymentType.Enum_PayType.第三方即时支付.value) && string.trim().equals(depositeRecord.getEnterprisepaymentbank())) {//第三方支付平台存款，如P001等
									isPayMent = true;//满足
									break;
								}
								if(string.trim().equals("银行卡") && depositeRecord.getPaymenttypecode().equals(PaymentType.Enum_PayType.银行卡转账.value)) {//判断支付大类型
									isPayMent = true;//满足
									break;
								}
							}
			    		} else {
			    			//	无限定支付渠道
			    			isPayMent = true;
			    		}
			    		
			    		
			    		// 	有限定支付银行
		    			if(com.maven.util.StringUtils.handString(BANK_CODE)) {
		    				String[] bankCodes = BANK_CODE.split(",");
			    			for (String string : bankCodes) {
			    				if(!com.maven.util.StringUtils.handString(string)) {
			    					continue;
			    				}
			    				
								if(string.trim().equals(depositeRecord.getEmployeepaymentbank())) {
									isBankCode = true;//满足
									break;
								}
							}
		    				
		    			} else {
		    				//	无限定支付银行
		    				isBankCode = true;
		    			}
		    			
			    		/*********************	开始返利（当前支付方式及银行都能匹配时）*********************/
			    		if(isPayMent == true && isBankCode == true) {
			    			
			    			flag = true;//二选一活动，不能共享
			    			
			    			double amount = depositeRecord.getOrderamount().doubleValue();
			    			double rand = getRand(RAND_MONEY, amount);
			    			double addMoney = amount * rand;//得到应返利金额
			    			
			    			//是否有上限要求
			    			if( !TOP_MONEY.equals("0")) {
			    				if(addMoney > Double.valueOf(TOP_MONEY)) {
			    					addMoney = Double.valueOf(TOP_MONEY);
			    				}
			    			}
			    			
			    			if(IS_AUTO_PAY.equals("1")) {
			    				
				    			//将资金存入用户账户
								enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(depositeRecord.getOrdernumber(),depositeRecord.getEmployeecode(), new BigDecimal(addMoney),
										new EmployeeMoneyChangeType(Enum_moneychangetype.优惠活动.value,Enum_moneychangetype.优惠活动.desc,Enum_moneyinouttype.进账),"活动:"+Enum_activity.充值赠送高级版.desc+"");
								
								EnterpriseEmployeeInformation eei = enterpriseEmployeeInformationDao.queryAccountData(ee);
								String allIp = ActivityUtils.ipLink(logLoginDao.selectIpByLoginaccount(depositeRecord.getLoginaccount()));
								//增加存款红利领取记录
								ActivityDepositBonus newdepositbonusrecord = new ActivityDepositBonus();
								newdepositbonusrecord.setOrdernumber(depositeRecord.getOrdernumber());
								newdepositbonusrecord.setEmployeecode(depositeRecord.getEmployeecode());
								newdepositbonusrecord.setParentemployeecode(depositeRecord.getParentemployeecode());
								newdepositbonusrecord.setLoginaccount(depositeRecord.getLoginaccount());
								newdepositbonusrecord.setDepositamount(depositeRecord.getOrderamount().doubleValue());
								newdepositbonusrecord.setReturnratio(rand);//返奖比例
								newdepositbonusrecord.setReturnamount(addMoney);
								newdepositbonusrecord.setDeposittime(depositeRecord.getOrderdate());
								newdepositbonusrecord.setGettime(new Date());
								newdepositbonusrecord.setUniqueinfo("||"+eei.getEmail()+"|"+eei.getPhonenumber()+"|"+eei.getPaymentaccount()+"|"+allIp);
								newdepositbonusrecord.setDepositType(activity.getEnterprisebrandactivitycode().toString());
								activityDepositBonusService.saveActivityDepositBonus(newdepositbonusrecord);
							
			    			}
			    			
			    			//增加活动红利支付审核记录
			    			Date now = new Date();
			    			EnterpriseActivityPay activityPay = new EnterpriseActivityPay();
			    			activityPay.setBrandcode(ee.getBrandcode());
			    			activityPay.setEmployeecode(ee.getEmployeecode());
			    			activityPay.setEnterprisecode(ee.getEnterprisecode());
			    			activityPay.setLoginaccount(ee.getLoginaccount());
			    			activityPay.setParentemployeecode(ee.getParentemployeecode());
			    			activityPay.setAcname(activity.getActivityname());
			    			activityPay.setEcactivitycode(activity.getEcactivitycode());
			    			activityPay.setCreatetime(now);
			    			activityPay.setPaymoneyaudit(addMoney);
			    			activityPay.setPaymoneyreal(addMoney);
			    			if(IS_AUTO_PAY.equals("1")) {
			    				activityPay.setAuditer("");
				    			activityPay.setAuditremark("活动：充值赠送高级版，自动发放");
				    			activityPay.setAudittime(now);
				    			activityPay.setPayer("");
				    			activityPay.setPaystatus(EnterpriseActivityPay.Enum_paystatus.已支付.value);
				    			activityPay.setPaytyime(now);
				    			activityPay.setPaytype(EnterpriseActivityPay.Enum_paytype.自动发放.value);
			    			} else {
			    				//activityPay.setAuditer("");
				    			//activityPay.setAuditremark("活动：注册送彩金，自动发放");
				    			//activityPay.setAudittime(now);
				    			//activityPay.setPayer("");
				    			//activityPay.setPaytyime(now);
			    				activityPay.setPaystatus(EnterpriseActivityPay.Enum_paystatus.待审核.value);
				    			activityPay.setPaytype(EnterpriseActivityPay.Enum_paytype.人工发放.value);
			    			}
			    			activityPay.setDesc("");
			    			activityPay.setLsbs(LSBS);
			    			enterpriseActivityPayDao.addBetRecord(activityPay);
			    			
							
							//手工存款加入默认打码
							double betMultiple = Double.valueOf(LSBS);
							if(betMultiple > 0) {
								ActivityBetRecord betrecord = new ActivityBetRecord();
								betrecord.setEmployeecode(depositeRecord.getEmployeecode());
								betrecord.setEcactivitycode(customization.getEcactivitycode());
								betrecord.setMustbet( (amount + addMoney) * betMultiple);
								betrecord.setAlreadybet(0.0);
								betrecord.setBetrecordstatus(Enum_betrecordstatus.未完成.value);
								betrecord.setCreatetime(new Date());
								betrecord.setLoginaccount(depositeRecord.getLoginaccount());
								betrecord.setRecharge(addMoney);//充值金额
								
								betrecord.setEnterprisecode(depositeRecord.getEnterprisecode());//企业编码
								betrecord.setBrandcode(depositeRecord.getBrandcode());//品牌编码
								betrecord.setParentemployeeaccount(depositeRecord.getParentemployeeaccount());//上级账号
								betrecord.setParentemployeecode(depositeRecord.getParentemployeecode());//上级编码
								activityBetRecordService.addActivityBetRecord(betrecord);
							}
							
							userLogsService.addActivityBetRecord(new UserLogs(activityPay.getEnterprisecode(), activityPay.getEmployeecode(), activityPay.getLoginaccount(), 
									UserLogs.Enum_operatype.红利信息业务, "获得充值赠送活动彩金"+addMoney+"元", null, null));
			    			
			    		}
			    		
			    		
			    	}//	返利结束
			    	
			    }
				
			}
		}
		return flag;
		
		/********************二选一活动，不能共享***********************/
		
		
		
		/******************************************** 检查并执行充值返利活动相关业务（jason.xu）the end ********************************************/
	}
	
	public static void main(String[] args) {
		BigDecimal percent = new BigDecimal(-100);
		System.out.println(percent.negate().intValue());
		System.out.println(percent.doubleValue());
		
		for (int i = 100; i >= 0; i--) {
			System.out.println(i+":"+(new Random().nextInt(i) / 100.00));
		}
		
	}

	
	public void updateTakeDepositRecord(TakeDepositRecord depositRecord) throws Exception {
		takeDepositRecoredDao.updateTakeDepositRecord(depositRecord);
	}
	
	
	@Override
	public void tc_save_money(TakeDepositRecord depositRecord) throws Exception {
		AttrCheckout.checkout(depositRecord, false,
				new String[] { "ordernumber", "employeecode", "enterprisecode", "paymenttypecode",
						"orderdate", "orderamount", "enterprisepaymentname", "enterprisepaymentaccount",
						"enterprisepaymentbank", "employeepaymentname", "employeepaymentaccount", "employeepaymentbank",
						"ordertype", "orderstatus", "ordercreater", "traceip" });
		// 初始化委托
		WorkingFlowConfiguration workingFlow = SystemCache.getInstance().getWorkflow().first(
				depositRecord.getEnterprisecode(),
				Enum_flowtype.getFlowType(Enum_ordertype.getOrdertype(depositRecord.getOrdertype())),
				depositRecord.getOrderamount());
		DepositWithdralOrderDelegate delegate = new DepositWithdralOrderDelegate(depositRecord.getOrdernumber(),
				workingFlow==null?null:workingFlow.getFlowcode(), workingFlow==null?null:workingFlow.getHandletime());
		depositWithdralOrderDelegateService.addDelegate(delegate);
		
		// 保存存款记录
		depositRecord.setFlowcode(delegate.getFlowcode());
		depositRecord.setDelegatecode(delegate.getDelegatecode());
		takeDepositRecoredDao.saveTakeDepositRecord(depositRecord);
	}
	
	@Override
	public void tc_update_saveorder(TakeDepositRecord depositRecord) throws Exception{
		AttrCheckout.checkout(depositRecord, false,
				new String[] { "ordernumber", "employeecode", "enterprisecode", "brandcode", "paymenttypecode",
						"orderdate", "orderamount", "enterprisepaymentname", "enterprisepaymentaccount",
						"enterprisepaymentbank", "employeepaymentname", "employeepaymentaccount", "employeepaymentbank",
						"ordertype", "orderstatus", "ordercreater", "traceip" });
		// 初始化委托
		WorkingFlowConfiguration workingFlow = SystemCache.getInstance().getWorkflow().first(
				depositRecord.getEnterprisecode(),
				Enum_flowtype.getFlowType(Enum_ordertype.getOrdertype(depositRecord.getOrdertype())),
				depositRecord.getOrderamount());
				
		DepositWithdralOrderDelegate delegate = new DepositWithdralOrderDelegate(depositRecord.getOrdernumber(),
				workingFlow==null?null:workingFlow.getFlowcode(), workingFlow==null?null:workingFlow.getHandletime());
		depositWithdralOrderDelegateService.addDelegate(delegate);
		
		//更新订单工作流程、重置当前审核人
		TakeDepositRecord record = new TakeDepositRecord();
		record.setOrdernumber(depositRecord.getOrdernumber());
		record.setDelegatecode(delegate.getDelegatecode());
		record.setFlowcode(workingFlow.getFlowcode());
		record.setHandleemployee("");
		takeDepositRecoredDao.updateFlow(record);
		
		// 修改存款记录
		depositRecord = AttrCheckout.checkout(depositRecord, true,
				new String[] { "ordernumber"},new String[]{"orderamount",  "paymenttypecode","enterprisepaymentname", 
						"enterprisepaymentaccount","enterprisepaymentbank", "employeepaymentname", "employeepaymentaccount",
						"employeepaymentbank", "orderstatus","ordercomment","ordercreater", "traceip"});
		depositRecord.setFlowcode(delegate.getFlowcode());
		depositRecord.setDelegatecode(delegate.getDelegatecode());
		takeDepositRecoredDao.updateTakeDepositRecord(depositRecord);
		
	}

	@Override
	public void tc_take_money(TakeDepositRecord takeRecord) throws Exception {
		AttrCheckout.checkout(takeRecord, false,
				new String[] { "ordernumber", "employeecode", "enterprisecode",
						"orderdate", "orderamount", "employeepaymentname", "employeepaymentaccount", "employeepaymentbank",
						"ordertype", "orderstatus", "orderdate", "ordercreater", "traceip" });
		
		TLogger.getLogger().Error("================================用户编码："+takeRecord.getEmployeecode()+"  开始取款逻辑...金额："+takeRecord.getOrderamount()+" 批次号："+takeRecord.getOrdernumber());
		
		double oldOrderMoney = takeRecord.getOrderamount().doubleValue();
		
		// 进行游戏下分
		TLogger.getLogger().Error("用户编码："+takeRecord.getEmployeecode()+"  进行游戏下分  .."+"批次号："+takeRecord.getOrdernumber());
		new APIServiceNew(takeRecord.getEnterprisecode()).userShimobun(takeRecord.getEmployeecode(), Long.valueOf(takeRecord.getOrdernumber()));//传递单号也就是批次号进去
		
		
		TLogger.getLogger().Error("用户编码："+takeRecord.getEmployeecode()+"  下分全部完毕，准备开始资金转账  .."+"批次号："+takeRecord.getOrdernumber());
		
		/********对比当前余额与本次取款金额大小*******/
		EnterpriseEmployeeCapitalAccount ec = capitalAccountService.takeCurrencyAccount(takeRecord.getEmployeecode());
		double amount = takeRecord.getOrderamount().doubleValue();
		int amount_real = takeRecord.getOrderamount().intValue();//整数取款
		TLogger.getLogger().Error("=======本次取款金额"+takeRecord.getOrderamount()+", 当前余额"+ec.getBalance().doubleValue()+"");
		
		if(ec.getBalance().doubleValue() > 0 && ec.getBalance().doubleValue() < takeRecord.getOrderamount().negate().doubleValue() ) {//正数与正数比较
			amount = ec.getBalance().negate().doubleValue();//负的
			amount_real = ec.getBalance().negate().intValue();//负的
			takeRecord.setOrderamount(new BigDecimal(amount_real));
			String ordercomment = takeRecord.getOrdercomment();
			if(ordercomment == null) {
				ordercomment = "";
			}
			takeRecord.setOrdercomment(ordercomment + " ; 本次取款金额"+oldOrderMoney+"大于当前账户余额"+ec.getBalance().doubleValue()+"，系统自动修改取款金额为整数金额："+amount_real);
			TLogger.getLogger().Error("用户编码："+takeRecord.getEmployeecode()+"  本次取款金额："+oldOrderMoney+"，当前金额："+ec.getBalance().doubleValue()+"，需要修改取款金额。 批次号："+takeRecord.getOrdernumber());
		}
		
		
		
		// 进行资金操作
		enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(takeRecord.getOrdernumber(),takeRecord.getEmployeecode(), new BigDecimal(amount_real),
				new EmployeeMoneyChangeType(Enum_moneychangetype.取款.value,Enum_moneychangetype.取款.desc,Enum_moneyinouttype.出账),"操作人:"+takeRecord.getOrdercreater()+" 批次号："+takeRecord.getOrdernumber());
		TLogger.getLogger().Error("用户编码："+takeRecord.getEmployeecode()+"  取款扣款,金额："+new BigDecimal(amount_real)+" 批次号："+takeRecord.getOrdernumber());
		
		// 初始化委托
		WorkingFlowConfiguration workingFlow = SystemCache.getInstance().getWorkflow().first(
				takeRecord.getEnterprisecode(),
				Enum_flowtype.getFlowType(Enum_ordertype.getOrdertype(takeRecord.getOrdertype())),
				takeRecord.getOrderamount().negate());
		DepositWithdralOrderDelegate delegate = new DepositWithdralOrderDelegate(takeRecord.getOrdernumber(),workingFlow==null?null:workingFlow.getFlowcode(), workingFlow==null?null:workingFlow.getHandletime());
				
		depositWithdralOrderDelegateService.addDelegate(delegate);
		TLogger.getLogger().Error("用户编码："+takeRecord.getEmployeecode()+" 取款委托成功, 进入审核流程"+" 批次号："+takeRecord.getOrdernumber());
		
		// 写入取款记录
		takeRecord.setFlowcode(delegate.getFlowcode());
		takeRecord.setDelegatecode(delegate.getDelegatecode());
		takeDepositRecoredDao.saveTakeDepositRecord(takeRecord);
		TLogger.getLogger().Error("================================用户编码："+takeRecord.getEmployeecode()+" 完成取款逻辑... 批次号："+takeRecord.getOrdernumber());
	}
	
	@Override
	public void tc_update_takeorder(TakeDepositRecord takeRecord) throws Exception {
		AttrCheckout.checkout(takeRecord, false,
				new String[] { "ordernumber", "employeecode", "enterprisecode", "brandcode", 
						"orderdate", "orderamount", "employeepaymentname", "employeepaymentaccount", "employeepaymentbank",
						"ordertype", "orderstatus", "ordercreater", "traceip" });
		
		TLogger.getLogger().Error("================================用户编码："+takeRecord.getEmployeecode()+"  开始取款修改订单逻辑...金额："+takeRecord.getOrderamount()+" 批次号："+takeRecord.getOrdernumber());
		// 进行游戏下分
		new APIServiceNew(takeRecord.getEnterprisecode()).userShimobun(takeRecord.getEmployeecode(), Long.valueOf(takeRecord.getOrdernumber()));
		
		double oldOrderMoney = takeRecord.getOrderamount().doubleValue();
		
		/********对比当前余额与本次取款金额大小*******/
		TLogger.getLogger().Error("用户编码："+takeRecord.getEmployeecode()+"  下分全部完毕，准备开始资金转账  .."+"批次号："+takeRecord.getOrdernumber());
		EnterpriseEmployeeCapitalAccount ec = capitalAccountService.takeCurrencyAccount(takeRecord.getEmployeecode());
		double amount = takeRecord.getOrderamount().doubleValue();
		int amount_real = takeRecord.getOrderamount().intValue();//整数取款
		TLogger.getLogger().Error("=======本次取款金额"+takeRecord.getOrderamount()+", 当前余额"+ec.getBalance().doubleValue()+"");
		
		if(ec.getBalance().doubleValue() > 0 && ec.getBalance().doubleValue() < takeRecord.getOrderamount().negate().doubleValue() ) {//正数与正数比较
			amount = ec.getBalance().negate().doubleValue();//负的
			amount_real = ec.getBalance().negate().intValue();//负的
			takeRecord.setOrderamount(new BigDecimal(amount_real));
			String ordercomment = takeRecord.getOrdercomment();
			if(ordercomment == null) {
				ordercomment = "";
			}
			takeRecord.setOrdercomment(ordercomment + " ; 本次取款金额"+oldOrderMoney+"大于当前账户余额"+ec.getBalance().doubleValue()+"，系统自动修改取款金额为整数金额："+amount_real);
			TLogger.getLogger().Error("用户编码："+takeRecord.getEmployeecode()+"  本次取款金额："+oldOrderMoney+"，当前金额："+ec.getBalance().doubleValue()+"，需要修改取款金额。 批次号："+takeRecord.getOrdernumber());
		}
		
		
		// 进行资金操作
		enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(takeRecord.getOrdernumber(),takeRecord.getEmployeecode(), new BigDecimal(amount_real),
				new EmployeeMoneyChangeType(Enum_moneychangetype.取款.value,Enum_moneychangetype.取款.desc,Enum_moneyinouttype.出账),"操作人:"+takeRecord.getOrdercreater()+" 批次号："+takeRecord.getOrdernumber());
		// 初始化委托
		WorkingFlowConfiguration workingFlow = SystemCache.getInstance().getWorkflow().first(
				takeRecord.getEnterprisecode(),
				Enum_flowtype.getFlowType(Enum_ordertype.getOrdertype(takeRecord.getOrdertype())),
				takeRecord.getOrderamount().negate());
		DepositWithdralOrderDelegate delegate = new DepositWithdralOrderDelegate(takeRecord.getOrdernumber(),
				workingFlow==null?null:workingFlow.getFlowcode(), workingFlow==null?null:workingFlow.getHandletime());
		depositWithdralOrderDelegateService.addDelegate(delegate);
		//更新订单工作流程、重置当前审核人
		TakeDepositRecord record = new TakeDepositRecord();
		record.setOrdernumber(takeRecord.getOrdernumber());
		record.setDelegatecode(delegate.getDelegatecode());
		record.setFlowcode(workingFlow.getFlowcode());
		record.setHandleemployee("");
		takeDepositRecoredDao.updateFlow(record);
		
		// 修改取款记录
		takeRecord = AttrCheckout.checkout(takeRecord, true,
				new String[] { "ordernumber"},new String[]{"orderamount", "employeepaymentname", "employeepaymentaccount",
						"employeepaymentbank", "orderstatus","ordercomment","orderdate","ordercreater", "traceip"});
		takeRecord.setFlowcode(delegate.getFlowcode());
		takeRecord.setDelegatecode(delegate.getDelegatecode());
		takeDepositRecoredDao.updateTakeDepositRecord(takeRecord);
		
		TLogger.getLogger().Error("================================用户编码："+takeRecord.getEmployeecode()+"  完成取款修改订单逻辑..."+"批次号："+takeRecord.getOrdernumber());
	}

	/**
	 * 查询分页数据
	 * @param paramObj
	 * @return List<TakeDepositRecord>
	 */
	public List<TakeDepositRecord> findTakeDepositRecord(Map<String, Object> paramObj) throws Exception {
		return takeDepositRecoredDao.findTakeDepositRecord(paramObj);
	}

	/**
	 * 统计数据
	 * @param paramObj
	 * @return int
	 */
	public Map<String, Object> findcountTakeDepositRecord(Map<String, Object> paramObj) throws Exception {
		return takeDepositRecoredDao.countTakeDepositRecord(paramObj);
	}

	/**
	 * 根据单条订单号删除记录
	 * @param session
	 * @param request
	 * @return Map<String,String>
	 */
	public void tc_deleteTakeRepositRecord(String code) throws Exception {
		takeDepositRecoredDao.deleteTakeRepositRecord(code);
	}

	/**
	 * 删除选中的一条或者多条数据
	 * @param request
	 */
	public void tc_deleteSelectAllTakeRepositRecord(String[] array) throws Exception {
		takeDepositRecoredDao.deleteSelectAllTakeRepositRecord(array);
	}

	/**
	 * 存取款记录修改
	 * @param request
	 * @return URL
	 */
	public void tc_updateTakeDepositRecord(TakeDepositRecord takeDepositRecord) throws Exception {
		takeDepositRecoredDao.updateTakeDepositRecord(
				AttrCheckout.checkout(takeDepositRecord, false, new String[] { "ordernumber" }));
	}

	/**
	 * 根据订单号查询
	 * 
	 * @param request
	 * @return TakeDepositRecord
	 */
	public TakeDepositRecord findOrderNumberTakeDepositRecord(String orderNumber) {
		return takeDepositRecoredDao.findOrderNumberTakeDepositRecord(orderNumber);
	}

	/**
	 * 审批通过根据订单号修改订单状态以及保存审批提交的图片名称
	 * 
	 * @param TakeDepositRecord
	 */
	public int tc_updateTakeDepositRecoredStatus(TakeDepositRecord takeDepositRecord) throws Exception {
		return takeDepositRecoredDao.updateTakeDepositRecoredStatus(takeDepositRecord);
	}

	/**
	 * 根据员工编码删除所有的存取款记录
	 * 
	 * @param strings
	 * @throws Exception
	 */
	@Override
	public void tc_deleteEmployeeRecord(String[] array) throws Exception {
		takeDepositRecoredDao.deleteEmployeeRecord(array);
	}

	/**
	 * 存取款余额统计查询
	 * 
	 * @param paramObj
	 * @return List<TakeDepositRecord>
	 */
	@Override
	public List<TakeDepositRecord> findCountDepositTakeRecordDatail(Map<String, Object> paramObj) throws Exception {
		return takeDepositRecoredDao.countDepositTakeRecordDatail(paramObj);
	}

	/**
	 * 存取款余额记录数量统计
	 * 
	 * @param paramObj
	 * @return List<TakeDepositRecord>
	 */
	@Override
	public int findCountDepositTakeRecordDatailCount(Map<String, Object> paramObj) throws Exception {
		return takeDepositRecoredDao.countDepositTakeRecordDatailCount(paramObj);
	}

	@Override
	public int updateFlow(TakeDepositRecord record)throws Exception{
		 return takeDepositRecoredDao.updateFlow(AttrCheckout.checkout(record, false, new String[]{"ordernumber"}));
	}
	
	/**
	 * 根据员工编码统计该员工当天取款次数与取款总金额 
	 */
	@Override
	public TakeDepositRecord takeCountAndTakeTotalAmount(String employeecode) throws Exception {
		return takeDepositRecoredDao.takeCountAndTakeTotalAmount(employeecode);
	}
	
	/**
	 * 订单处理时间区域统计
	 * @param paramObj
	 */
	@Override
	public List<TakeDepositRecord> call_businessHandleCount(Map<String, Object> paramObj) throws Exception {
		return takeDepositRecoredDao.businessHandleCount(paramObj);
	}
	
	/**
	 * 用户存款排名报表查询
	 * @param paramObj
	 * @return
	 */
	@Override
	public List<TakeDepositRecord> call_employeeDepositRankingReportCount(Map<String, Object> paramObj) throws Exception{
		return takeDepositRecoredDao.employeeDepositRankingReportCount(paramObj);
	}
	
	/**
	 * 用户取款排名报表查询
	 * @param paramObj
	 * @return
	 */
	@Override
	public List<TakeDepositRecord> call_employeeWithdrawalsRankingReportCount(Map<String, Object> paramObj) throws Exception{
		return takeDepositRecoredDao.queryEmployeeWithdrawalsRankingReportCount(paramObj);
	}
	
	/**
	 *取款次数排名
	 * @param paramObj
	 */
	@Override
	public List<TakeDepositRecord> call_employeeWithdraNumberRanking(Map<String, Object> paramObj) throws Exception{
		return takeDepositRecoredDao.queryEmployeeWithdraNumberRanking(paramObj);
	}
	
	/**
	 * 用户存取款统计
	 */
	@Override
	public List<TakeDepositRecord> call_userDepositTakeCount(Map<String, Object> paramObj) throws Exception {
		return takeDepositRecoredDao.userDepositTakeCount(paramObj);
	}
	
	/**
	 * 用户提存比分析统计
	 */
	@Override
	public List<TakeDepositRecord> call_userDepositTakeRate(Map<String, Object> paramObj) throws Exception {
		return takeDepositRecoredDao.userDepositTakeRate(paramObj);
	}
	
	/**
	 * 用户输赢概率统计分析
	 */
	@Override
	public List<TakeDepositRecord> call_userGameWinLoseRate(Map<String, Object> paramObj) throws Exception {
		return takeDepositRecoredDao.userGameWinLoseRate(paramObj);
	}
	
	/**
	 * 统计用户当天或历史累计存款总额
	 * @param employeecode
	 * @param flag 0=昨天 1=当天 2=历史累计
	 * @return
	 * @throws Exception
	 */
	@Override
	public double call_userDepositTakeMoney(String employeecode, int flag) throws Exception {
		Map<String, Object> paramObj = new HashMap<String, Object>();
		paramObj.put("employeecode", employeecode);
		paramObj.put("flag", flag);
		return takeDepositRecoredDao.userDepositTakeMoney(paramObj);
	}
	
	/**
	 * 存储过程,总计-取款稽查
	 */
	public List<TakeDepositRecord> call_userTakemoneyInspectNew(Map<String, Object> paramObj) throws Exception {
		return takeDepositRecoredDao.userTakemoneyInspectNew(paramObj);
	}
	/**
	 * 存储过程,总计-取款稽查
	 */
	public Map<String, Object> call_userAllMoney(Map<String, Object> paramObj) throws Exception {
		return takeDepositRecoredDao.userAllMoney(paramObj);
	}

	@Override
	public int tc_cancelAudit(TakeDepositRecord takeDepositRecord) throws Exception {
		return takeDepositRecoredDao.cancelAudit(AttrCheckout.checkout(takeDepositRecord, false, new String[]{"ordernumber","handleemployee"}));
	}

	@Override
	public int updateHandEmployee(Map<String, Object> object) throws Exception {
		return takeDepositRecoredDao.updateHandEmployee(AttrCheckout.checkout(object,false,new String[]{"handleemployee","orders"}));
	}

	@Override
	public List<TakeDepositRecord> findMutilOrdersByOrdernumber(String[] orders) {
		return takeDepositRecoredDao.findMutilOrdersByOrdernumber(orders);
	}
	
	
	/**
	 * 查询会员的存取款相关汇总信息
	 */
	@Override
	public Map<String, Object> call_userAllInfoMoney(Map<String, Object> paramObj) throws Exception {
		return takeDepositRecoredDao.userAllInfoMoney(paramObj);
	}
	
	/**
	 * 查询会员的游戏和优惠和洗码等所有金额相关的数据
	 */
	@Override
	public List<TakeDepositRecord> call_userAllInfoGame(Map<String, Object> paramObj) throws Exception {
		return takeDepositRecoredDao.userAllInfoGame(paramObj);
	}

}
