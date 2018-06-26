package com.maven.report.activity;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.maven.entity.ActivityTemplate.Enum_activity;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.entity.EmployeeMoneyChangeType.moneychangetypeCategory;
import com.maven.cache.SystemCache;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.BettingAllDay;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EnterpriseActivityCustomization;
import com.maven.entity.EnterpriseBrandActivity;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.UserLogs;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityButBonus;
import com.maven.entity.EnterpriseEmployeeType.Type;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.ActivityButBonusService;
import com.maven.service.BettingAllDayService;
import com.maven.service.EnterpriseActivityCustomizationService;
import com.maven.service.EnterpriseBrandActivityService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.TakeDepositRecoredService;
import com.maven.service.UserLogsService;
import com.maven.util.JSONUnit;
import com.maven.util.RandomString;
import com.maven.util.SmsUtilPublic;
import com.maven.utils.StringUtil;
/**
 * 日投注返利活动自动化处理逻辑
 * @author Administrator
 *
 */
@Component
@Lazy(false)
public class DataButBonus {
	private static LoggerManager log = LoggerManager.getLogger(DataButBonus.class.getName(),OutputManager.LOG_DAY_BUT_BONUS);
			
	
	@Autowired
	private ActivityButBonusService activityButBonusService;
	
	@Autowired
	private EnterpriseActivityCustomizationService enterpriseActivityCustomizationService;
	
	@Autowired
	private EnterpriseBrandActivityService enterpriseBrandActivityService;
	
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	
	@Autowired
	private ActivityBetRecordService activityBetRecordService;  
	
	@Autowired
	private EnterpriseEmployeeCapitalAccountService enterpriseEmployeeCapitalAccountService;
	@Autowired
	private TakeDepositRecoredService takeDepositRecoredService;
	@Autowired
	private UserLogsService userLogsService;
	

	@Scheduled(fixedDelay = (1000*60*10) )//10分钟检查一次
	public void work() {
		try {
			
			log.Info("=====================================================================================================");
			log.Info("开始 日投注返利活动自动化处理逻辑");
			
			/**************************************1、检查所有在参与该活动的企业		 *************************************/
			/**************************************2、检查所有绑定该活动的企业		 *************************************/
			/**************************************3、检查所有绑定并有效的该活动的企业**************************************/
			/**************************************4、检查是否到达返水时间（参数可设定具体开始返水时间）*********************/
			
			
			EnterpriseActivityCustomization customization = new EnterpriseActivityCustomization();
			customization.setDatastatus("1");
			customization.setActivitytemplatecode(Enum_activity.日投注返水高级版.activitycode);
			List<EnterpriseActivityCustomization> listEnterpriseActivityCustomization = enterpriseActivityCustomizationService.select(customization);
			
			log.Info("检查所有在参与该活动的企业。");
			
			for (EnterpriseActivityCustomization item : listEnterpriseActivityCustomization) {
				
				log.Info("检查所有在参与该活动的企业。共计"+listEnterpriseActivityCustomization.size()+"个");
				
				Map<String, Object> queryParams = new HashMap<String, Object>();
				queryParams.put("enterprisecode", item.getEnterprisecode());
				queryParams.put("ecactivitycode", item.getEcactivitycode());
				queryParams.put("status", EnterpriseBrandActivity.Enum_status.启用.value);
				List<EnterpriseBrandActivity> listEnterpriseBrandActivity = enterpriseBrandActivityService.selectAll(queryParams);
				
				log.Info("检查所有绑定该活动的企业。");
				
				if(listEnterpriseBrandActivity == null || listEnterpriseBrandActivity.size() == 0) {
					continue;
				}
				log.Info("检查所有绑定该活动的企业。共计"+listEnterpriseBrandActivity.size()+"个");
				
				for (EnterpriseBrandActivity activity : listEnterpriseBrandActivity) {
					
					if(activity == null || !activity.getStatus().equals(EnterpriseBrandActivity.Enum_status.启用.value)) {
						continue;
					}
					
					
					log.Info("检查所有绑定并有效的该活动的企业。");
					
					// 活动是否进行中
					//Date1.after(Date2),当Date1大于Date2时，返回TRUE，当小于等于时，返回false
					//Date1.before(Date2)，当Date1小于Date2时，返回TRUE，当大于等于时，返回false
					Date currendate = new Date();
				    if(currendate.after(activity.getBegintime()) && currendate.before(activity.getEndtime()) ){
				    	
				    	log.Info("检查所有绑定并有效的该活动的企业。"+activity.getActivityname()+" 正有效进行中");
				    	
				    	Map<String, Object> mapAgument = enterpriseBrandActivityService.selectActivityAgument(activity.getEnterprisebrandactivitycode());
				    	
				    	log.Info("检查所有绑定并有效的该活动的企业。"+activity.getActivityname()+" 活动详细参数："+mapAgument);
				    	
				    	//格式：18点，以24小时制为准，只能配置整点的数字小时参数
				    	String START_TIME = com.maven.util.StringUtils.nullString(mapAgument.get(AppConstants.START_TIME));
				    	
				    	
				    	int hour = 18;//无配置时默认18时
				    	if(com.maven.util.StringUtils.handString(START_TIME)) {
				    		hour = Integer.valueOf(START_TIME);
				    	}
				    	
				    	/***************************************重点。避免同一天返水多次*********************************************/
				    	String lastDate = SystemCache.getInstance().getActivityRunFlag(activity.getEnterprisebrandactivitycode().toString());
				    	if(lastDate != null && StringUtil.getCurrentDate().equals(lastDate) ) {
				    		log.Info("检查到当前企业当天已经执行过，跳过不再执行。 "+activity.getActivityname()+" lastDate="+lastDate);
				    		continue;
				    	}
				    	
				    	
				    	
				    	log.Info("检查到当前企业上一次执行日期是 lastDate="+lastDate);
				    	
				    	Calendar calendar = Calendar.getInstance();
				    	if(calendar.get(Calendar.HOUR_OF_DAY) >= hour ) {
				    		log.Info("检查活动是否到达返水时间。已到达时间，开始执行="+hour);
				    		
				    		workDetails(mapAgument, activity);
				    		
				    		//	该企业当天执行完毕后，打上标志。确保每天只执行一次
				    		SystemCache.getInstance().setActivityRunFlag(activity.getEnterprisebrandactivitycode().toString(), StringUtil.getCurrentDate());
				    		
				    		log.Info("当前企业已执行完毕，标记最后执行日期为。 "+activity.getActivityname()+" lastDate="+StringUtil.getCurrentDate());
				    	} else {
				    		log.Info("检查活动是否到达返水时间。未到达时间，跳过执行="+hour);
				    	}
				    	
				    	
				    } else {
				    	log.Info("检查所有绑定并有效的该活动的企业。"+activity.getActivityname()+"不在活动有效时间内");
				    }
				    
				}
				
			}
			log.Info("结束 日投注返利活动自动化处理逻辑");
			log.Info("=====================================================================================================");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	/**
	 * 实现两位小数点输出(double),建议不做显示使用，只作为计算使用
	 * 
	 * @param dou
	 *            金额
	 * @return 四舍五入之后的结果
	 */
	private static double moneyFormatDouble(double dou) {
		return moneyFormatDouble(Math.round(dou * 100) / (double) 100,"####0.00");
	}
	/**
	 * 实现指定格式金额输出,建议不做显示使用，只作为计算使用
	 * 
	 * @param dou
	 *            金额
	 * @param formater
	 *            格式
	 * @return 格式化之后的结果
	 */
	private static double moneyFormatDouble(double dou, String formater) {
		java.text.DecimalFormat df = new java.text.DecimalFormat(formater);

		double _dou = dou;
		if (_dou < 0) {
			_dou = -_dou;
			return -Double.parseDouble(df.format(_dou));
		} else {
			return Double.parseDouble(df.format(dou));
		}
	}
	
	public void workDetails(Map<String, Object> mapAgument , EnterpriseBrandActivity activity){
		
		/************日投注量与返点比例配置：平台=大类小类标识=大类或小类code：{比例}**************/
//	    '1=SX': {0-100000=0.006,100000-1000000=0.007, 1000000-100000000=0.008},
//    	'2=LHJ': {0-100000=0.006,100000-1000000=0.007, 1000000-100000000=0.008},
//		'1=TY': {0-100000=0.006,100000-1000000=0.007, 1000000-100000000=0.008}
		
		//是否自动付款（0=非自动 1=自动）非自动时，只生成统计数据报表，不会直接增加余额（注意：仅限测试时观察数据使用。测试情况下，务必设置为0）
		String IS_AUTO_PAY = com.maven.util.StringUtils.nullString(mapAgument.get(AppConstants.IS_AUTO_PAY));
		if(IS_AUTO_PAY.equals("")) {
			IS_AUTO_PAY = "0";
		}
		
		//用户历史充值总额要求（无要求填写0）
		String TOTAL_DEPOSIT_MONEY = com.maven.util.StringUtils.nullString(mapAgument.get(AppConstants.TOTAL_DEPOSIT_MONEY));
		if(TOTAL_DEPOSIT_MONEY.equals("")) {
			TOTAL_DEPOSIT_MONEY = "0";
		}
		//用户当日充值总额要求（无要求填写0）
		String TODAY_DEPOSIT_MONEY = com.maven.util.StringUtils.nullString(mapAgument.get(AppConstants.TODAY_DEPOSIT_MONEY));
		if(TODAY_DEPOSIT_MONEY.equals("")) {
			TODAY_DEPOSIT_MONEY = "0";
		}
		//提款需流水倍数（无要求填写0）
		String LSBS = com.maven.util.StringUtils.nullString(mapAgument.get(AppConstants.LSBS));
		if(LSBS.equals("")) {
			LSBS = "0";
		}
		
    	String RAND_MONEY = com.maven.util.StringUtils.nullString(mapAgument.get(AppConstants.RAND_MONEY));
    	RAND_MONEY = "{"+RAND_MONEY+"}";
    	Map<String, String> __object = JSONUnit.getMapFromJsonNew(RAND_MONEY);
    	
    	
    	try {
			Date now = new Date();
			int createdate = Integer.valueOf(StringUtil.getCurrentDate());
			Calendar satrtTime = Calendar.getInstance();
			
			satrtTime.add(Calendar.DATE, -1);
			satrtTime.set(Calendar.HOUR_OF_DAY, 0);
			satrtTime.set(Calendar.MINUTE, 0);
			satrtTime.set(Calendar.SECOND, 0);
			
			Calendar endTime = Calendar.getInstance();
			endTime.add(Calendar.DATE, -1);
			endTime.set(Calendar.HOUR_OF_DAY, 23);
			endTime.set(Calendar.MINUTE, 59);
			endTime.set(Calendar.SECOND, 59);
    		
    		Iterator<String> iterator = __object.keySet().iterator();
    		while (iterator.hasNext()) {
    			
    			String key = iterator.next();//'1=TY'
    			Map<String, String> valueMap = JSONUnit.getMapFromJsonNew(__object.get(key));//{1000000-100000000=0.008, 0-100000=0.006, 100000-1000000=0.007}
    			
    			if(key.startsWith("1=")) {
    				//	查询所有表以大类分组的情况
    				String bigGameType = key.split("=")[1];
    				log.Info("正在处理企业 "+activity.getActivityname()+" 的大类统计数据："+bigGameType);
    				
    				//统计
    				Map<String, Object> queryParams = new HashMap<String, Object>();
    				queryParams.put("enterprisecode", activity.getEnterprisecode());
    				queryParams.put("gamebigtype", bigGameType);
    				queryParams.put("brandcode", activity.getBrandcode());
    				queryParams.put("startDate", satrtTime.getTime());
    				queryParams.put("endDate", endTime.getTime());
    				List<EnterpriseEmployee> listEnterpriseEmployee = enterpriseEmployeeService.call_uspActivityButmoneyEnterprisecodeBigType(queryParams);
    				
    				log.Info("统计到企业"+activity.getActivityname()+"昨日有"+listEnterpriseEmployee.size()+"人参与了投注");
    				log.Info("检查到企业"+activity.getActivityname()+"对用户的历史充值总额要求是："+TOTAL_DEPOSIT_MONEY);
    				log.Info("检查到企业"+activity.getActivityname()+"对用户当日充值总额要求是："+TODAY_DEPOSIT_MONEY);
    				for (EnterpriseEmployee enterpriseEmployee : listEnterpriseEmployee) {
    					
    					//检查是否指定品牌的用户才能参与该活动
    					if(activity.getBrandcode() != null && !activity.getBrandcode().equals(enterpriseEmployee.getBrandcode())) {
    						continue;
    					}
    					
    					String employeecode = enterpriseEmployee.getEmployeecode();
    					String loginaccount = enterpriseEmployee.getLoginaccount();
    					
    					//统计历史累计存款
    					if(Double.valueOf(TOTAL_DEPOSIT_MONEY) > 0) {
    						double totalmoney = takeDepositRecoredService.call_userDepositTakeMoney(employeecode, 2);//历史
    						
    						if(totalmoney >= Double.valueOf(TOTAL_DEPOSIT_MONEY) ) {
    							log.Info("检查到员工"+employeecode+"的历史累计存款总额已满足条件："+totalmoney+"。");
    						} else {
    							log.Info("检查到员工"+employeecode+"没有历史累计存款总额未能满足条件："+totalmoney+"，跳过不执行。");
        						continue;
    						}
    						
    					}
    					
    					//统计当日累计存款
    					if(Double.valueOf(TODAY_DEPOSIT_MONEY) > 0) {
    						double totalmoney = takeDepositRecoredService.call_userDepositTakeMoney(employeecode, 0);//昨日
    						
    						if(totalmoney >= Double.valueOf(TODAY_DEPOSIT_MONEY) ) {
    							log.Info("检查到员工"+employeecode+"的昨日累计存款总额已满足条件："+totalmoney+"。");
    						} else {
    							log.Info("检查到员工"+employeecode+"没有昨日累计存款总额未能满足条件："+totalmoney+"，跳过不执行。");
        						continue;
    						}
    						
    					}
    					
    					double game_betting_amount = moneyFormatDouble(enterpriseEmployee.getGame_betting_amount().doubleValue());
    					double addmoney = 0;
    					double rand = getRand(valueMap, game_betting_amount);//检查此人在哪个范围，返回返点比例。如果大于0，需要返点
    					
    					//根据统计出来的金额，计算应返点金额并返点
    					if(rand > 0) {
    						addmoney = moneyFormatDouble(game_betting_amount * rand);
    					}
						
    					if(addmoney > 0) {
    						
    						if(IS_AUTO_PAY.equals("1")) {
    							//将资金存入用户账户
    							enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(RandomString.UUID() ,employeecode, new BigDecimal(addmoney),
    									new EmployeeMoneyChangeType(Enum_moneychangetype.优惠活动.value,Enum_moneychangetype.优惠活动.desc,Enum_moneyinouttype.进账),"活动:日投注返水高级版");
    							
    						}
    						
    						
							//添加打码记录
    						ActivityBetRecord betrecord = new ActivityBetRecord();
							betrecord.setEmployeecode(employeecode);
							betrecord.setEcactivitycode(activity.getEcactivitycode());
							if(Double.valueOf(LSBS) <= 0) {//如果不需要流水
								betrecord.setMustbet(0.0);
								betrecord.setAlreadybet(0.0);
								betrecord.setBetrecordstatus(Enum_betrecordstatus.已完成.value);
							} else {
								betrecord.setMustbet(addmoney);
								betrecord.setAlreadybet(0.0);
								betrecord.setBetrecordstatus(Enum_betrecordstatus.未完成.value);
							}
							betrecord.setCreatetime(now);
							betrecord.setLoginaccount(loginaccount);
							betrecord.setRecharge(addmoney);//充值金额
							betrecord.setEnterprisecode(enterpriseEmployee.getEnterprisecode());//企业编码
							betrecord.setBrandcode(enterpriseEmployee.getBrandcode());//品牌编码
							betrecord.setParentemployeeaccount(enterpriseEmployee.getParentemployeeaccount());//上级账号
							betrecord.setParentemployeecode(enterpriseEmployee.getParentemployeecode());//上级编码
							activityBetRecordService.addActivityBetRecord(betrecord);
							
    						
							//添加领取记录
							ActivityButBonus butBonus = new ActivityButBonus();
							butBonus.setButmoney(game_betting_amount);
							butBonus.setBonusmoney(addmoney);
							butBonus.setBonusrand(rand);
							butBonus.setBonustime(now);
							butBonus.setCreatedate(createdate);
							butBonus.setCreatetime(now);
							butBonus.setEmployeecode(employeecode);
							butBonus.setEnterprisebrandactivitycode(activity.getEnterprisebrandactivitycode());
							butBonus.setEnterprisecode(activity.getEnterprisecode());
							butBonus.setBrandcode(activity.getBrandcode());
							butBonus.setLoginaccount(loginaccount);
							butBonus.setPayType(ActivityButBonus.Enum_PayType.日投注.value);
							butBonus.setRemark(key);
							if(IS_AUTO_PAY.equals("1")) {
								butBonus.setStatus(ActivityButBonus.Enum_Status.已领取.value);
							} else {
								butBonus.setStatus(ActivityButBonus.Enum_Status.未领取.value);
							}
							activityButBonusService.saveActivityButBonus(butBonus);
							
							
							userLogsService.addActivityBetRecord(new UserLogs(butBonus.getEnterprisecode(), betrecord.getEmployeecode(), betrecord.getLoginaccount(), 
								     UserLogs.Enum_operatype.红利信息业务, "获得日投注返利活动奖励:"+addmoney, null, null));
							
    					}
    					
    					log.Info("计算到企业"+activity.getActivityname()+"员工"+employeecode+"的投注额是"+game_betting_amount+"， 返点比例是"+rand+"，应返点金额是"+addmoney);
    					
					}
    				
    				
    			} else if(key.startsWith("2=")) {
    				//	查询所有表以小类分组的情况
    				String gameType = key.split("=")[1];
    				log.Info("正在处理企业"+activity.getActivityname()+"的小类统计数据："+gameType);
    				
    				//统计
    				Map<String, Object> queryParams = new HashMap<String, Object>();
    				queryParams.put("enterprisecode", activity.getEnterprisecode());
    				queryParams.put("gametype", gameType);
    				queryParams.put("brandcode", activity.getBrandcode());
    				queryParams.put("startDate", satrtTime.getTime());
    				queryParams.put("endDate", endTime.getTime());
    				List<EnterpriseEmployee> listEnterpriseEmployee = enterpriseEmployeeService.call_uspActivityButmoneyEnterprisecodeSmallType(queryParams);
    				
    				log.Info("统计到企业"+activity.getActivityname()+"昨日有"+listEnterpriseEmployee.size()+"人参与了投注");
    				log.Info("检查到企业"+activity.getActivityname()+"对用户的历史充值总额要求是："+TOTAL_DEPOSIT_MONEY);
    				log.Info("检查到企业"+activity.getActivityname()+"对用户当日充值总额要求是："+TODAY_DEPOSIT_MONEY);
    				
    				for (EnterpriseEmployee enterpriseEmployee : listEnterpriseEmployee) {
    					
    					String employeecode = enterpriseEmployee.getEmployeecode();
    					String loginaccount = enterpriseEmployee.getLoginaccount();
    					
    					//统计历史累计存款
    					if(Double.valueOf(TOTAL_DEPOSIT_MONEY) > 0) {
    						double totalmoney = takeDepositRecoredService.call_userDepositTakeMoney(employeecode, 2);
    						
    						if(totalmoney >= Double.valueOf(TOTAL_DEPOSIT_MONEY) ) {
    							log.Info("检查到员工"+employeecode+"的历史累计存款总额已满足条件："+totalmoney+"。");
    						} else {
    							log.Info("检查到员工"+employeecode+"没有历史累计存款总额未能满足条件："+totalmoney+"，跳过不执行。");
        						continue;
    						}
    						
    					}
    					
    					//统计当日累计存款
    					if(Double.valueOf(TODAY_DEPOSIT_MONEY) > 0) {
    						double totalmoney = takeDepositRecoredService.call_userDepositTakeMoney(employeecode, 0);//昨日
    						
    						if(totalmoney >= Double.valueOf(TODAY_DEPOSIT_MONEY) ) {
    							log.Info("检查到员工"+employeecode+"的昨日累计存款总额已满足条件："+totalmoney+"。");
    						} else {
    							log.Info("检查到员工"+employeecode+"没有昨日累计存款总额未能满足条件："+totalmoney+"，跳过不执行。");
        						continue;
    						}
    						
    					}
    					
    					double game_betting_amount = moneyFormatDouble( enterpriseEmployee.getGame_betting_amount().doubleValue() );
    					double addmoney = 0;
    					double rand = getRand(valueMap, game_betting_amount);//检查此人在哪个范围，返回返点比例。如果大于0，需要返点
    					
    					//根据统计出来的金额，计算应返点金额并返点
    					if(rand > 0) {
    						addmoney = moneyFormatDouble( game_betting_amount * rand );
    					}
    					
    					if(addmoney > 0) {
    						
    						if(IS_AUTO_PAY.equals("1")) {
    							//将资金存入用户账户
    							enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(RandomString.UUID() ,employeecode, new BigDecimal(addmoney),
    									new EmployeeMoneyChangeType(Enum_moneychangetype.优惠活动.value,Enum_moneychangetype.优惠活动.desc,Enum_moneyinouttype.进账),"活动:日投注返水高级版");
    							
    						}
    						
    						//添加打码记录
    						ActivityBetRecord betrecord = new ActivityBetRecord();
							betrecord.setEmployeecode(employeecode);
							betrecord.setEcactivitycode(activity.getEcactivitycode());
							if(Double.valueOf(LSBS) <= 0) {//如果不需要流水
								betrecord.setMustbet(0.0);
								betrecord.setAlreadybet(0.0);
								betrecord.setBetrecordstatus(Enum_betrecordstatus.已完成.value);
							} else {
								betrecord.setMustbet(addmoney);
								betrecord.setAlreadybet(0.0);
								betrecord.setBetrecordstatus(Enum_betrecordstatus.未完成.value);
							}
							betrecord.setCreatetime(now);
							betrecord.setLoginaccount(loginaccount);
							betrecord.setRecharge(addmoney);//充值金额
							betrecord.setEnterprisecode(enterpriseEmployee.getEnterprisecode());//企业编码
							betrecord.setBrandcode(enterpriseEmployee.getBrandcode());//品牌编码
							betrecord.setParentemployeeaccount(enterpriseEmployee.getParentemployeeaccount());//上级账号
							betrecord.setParentemployeecode(enterpriseEmployee.getParentemployeecode());//上级编码
							activityBetRecordService.addActivityBetRecord(betrecord);
							
							//添加领取记录
							ActivityButBonus butBonus = new ActivityButBonus();
							butBonus.setButmoney(game_betting_amount);
							butBonus.setBonusmoney(addmoney);
							butBonus.setBonusrand(rand);
							butBonus.setBonustime(now);
							butBonus.setCreatedate(createdate);
							butBonus.setCreatetime(now);
							butBonus.setEmployeecode(employeecode);
							butBonus.setEnterprisebrandactivitycode(activity.getEnterprisebrandactivitycode());
							butBonus.setEnterprisecode(activity.getEnterprisecode());
							butBonus.setBrandcode(activity.getBrandcode());
							butBonus.setLoginaccount(loginaccount);
							butBonus.setPayType(ActivityButBonus.Enum_PayType.日投注.value);
							butBonus.setRemark(key);
							if(IS_AUTO_PAY.equals("1")) {
								butBonus.setStatus(ActivityButBonus.Enum_Status.已领取.value);
							} else {
								butBonus.setStatus(ActivityButBonus.Enum_Status.未领取.value);
							}
							activityButBonusService.saveActivityButBonus(butBonus);
    					}
    					
    					log.Info("计算到企业"+activity.getActivityname()+"员工"+employeecode+"的投注额是"+game_betting_amount+"， 返点比例是"+rand+"，应返点金额是"+addmoney);
					}
    				
    				
    			}
    			
    		}
    		
    		log.Info("日投注返利活动 已处理完毕");
    		
			
		} catch (Exception e) {
			SmsUtilPublic.sendSmsByGet01(SmsUtilPublic.__NOTI_PHONENO, "日投注返利活动自动化处理逻辑出现异常");
			log.Error("日投注返利活动自动化处理逻辑出现异常："+e.getMessage(), e);
		}
		
    	
	}

	public static void main(String[] args) throws IOException {
		/*
		String RAND_MONEY = "'1=SX': {0-100000=0.006,100000-1000000=0.007, 1000000-100000000=0.008},'2=LHJ': {0-100000=0.006,100000-1000000=0.007, 1000000-100000000=0.008}, '1=TY': {0-100000=0.006,100000-1000000=0.007, 1000000-100000000=0.008}";
		RAND_MONEY = "{"+RAND_MONEY+"}";
    	Map<String, String> __object = JSONUnit.getMapFromJsonNew(RAND_MONEY);
		
    	Iterator<String> iterator = __object.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();//'1=TY'
			Map<String, String> value = JSONUnit.getMapFromJsonNew(__object.get(key));//{1000000-100000000=0.008, 0-100000=0.006, 100000-1000000=0.007}
			if(key.startsWith("1=")) {
				//	查询所有表以大类分组的情况
				String bigGameType = key.split("=")[1];
				
				double xx = 5000000;
				double rand = getRand(value, xx);
				System.out.println(rand);
				
			} else if(key.startsWith("2=")) {
				//	查询所有表以小类分组的情况
				String gameType = key.split("=")[1];
				
				
			}
			
		}
		*/
		Map<String, String> value = JSONUnit.getMapFromJsonNew("{1000000-100000000=0.008, 0-100000=0.006, 100000-1000000=0.007}");
		double xx = 500000000;
		double rand = getRand(value, xx);
		System.out.println(rand);
		
	}

	private static double getRand(Map<String, String> __object ,double butMoney) {
		String min_key = "";
		for (String __key : __object.keySet()) {
			double start = Double.valueOf(__key.split("-")[0]);
			double end   = Double.valueOf(__key.split("-")[1]);
			if(butMoney >= start && butMoney<= end) {
				//在这个范围内
				min_key = __key;
				break;
			}
		}
		return StringUtil.getDouble(__object.get(min_key));
	}
}
