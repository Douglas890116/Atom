package com.maven.report.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hy.pull.common.util.MoneyHelper;
import com.hy.pull.common.util.api.GameAPI;
import com.maven.cache.SystemCache;
import com.maven.config.SystemConstant;
import com.maven.entity.BettingAllDay;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EmployeeApiAccoutPasswordJob;
import com.maven.entity.EmployeeGamecataloyBonus;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeLevel;
import com.maven.entity.EnterpriseGame;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.EnterpriseOperatingBrandGame;
import com.maven.entity.Game;
import com.maven.entity.UserLogs;
import com.maven.game.APIServiceUtil;
import com.maven.game.enums.GameEnum;
import com.maven.game.enums.GameEnum.Enum_GameType;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BettingAllDayService;
import com.maven.service.BusinessEmployeeLovelService;
import com.maven.service.EmployeeApiAccoutPasswordJobService;
import com.maven.service.EmployeeApiAccoutService;
import com.maven.service.EmployeeGamecataloyBonusService;
import com.maven.service.EnterpriseEmployeeLevelBonusService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseGameService;
import com.maven.service.EnterpriseOperatingBrandGameService;
import com.maven.service.EnterpriseOperatingBrandService;
import com.maven.service.EnterpriseService;
import com.maven.service.GameService;
import com.maven.service.UserLogsService;
import com.maven.util.DateUtils;
import com.maven.util.MailUtil;
import com.maven.util.SmsUtilPublic;

import net.sf.json.JSONObject;

/**
 * VIP等级根据投注量来升级
 * @author Administrator
 *
 */
@Component
@Lazy(false)
public class DataBetVipJob {
	
	private static LoggerManager log = LoggerManager.getLogger(DataBetVipJob.class.getName(),OutputManager.LOG_DAY_BUT_BONUS);
	
	@Autowired
	private BettingAllDayService bettingAllDayService;
	@Autowired
	private EnterpriseService enterpriseService;
	
	@Autowired
	private BusinessEmployeeLovelService businessEmployeeLovelService;
	@Autowired
	private EnterpriseEmployeeLevelBonusService enterpriseEmployeeLevelBonusService;
	@Autowired
	private EmployeeGamecataloyBonusService employeeGamecataloyBonusService;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private UserLogsService userLogsService;
	
	
	//每周一凌晨4:1分执行
	@Scheduled(cron = "0 1 4 ? * MON")
	public void work() {
		
		try {
			Date lastMonday = DateUtils.getLastMonday();
			Date lastSunday = DateUtils.getLastSunday();
			//Date reporttime = new Date();
			
			Map<String,Object> mapUpdateLevel = new HashMap<String, Object>();
			
			
			List<Enterprise> listEnterprise =  enterpriseService.selectAll(new HashMap<String, Object>());
			
			for (Enterprise enterprise : listEnterprise) {
				
				Map<String, Object> ep = new HashMap<String, Object>();
				ep.put("enterprisecode", enterprise.getEnterprisecode());
				ep.put("startBetDay", lastMonday);
				ep.put("endBetDay", lastSunday);
				List<BettingAllDay> listBettingAllDay = bettingAllDayService.selectBettingByDate(ep);
				log.Info("===================开始检查企业"+enterprise.getEnterprisename()+" 本周投注数："+listBettingAllDay.size());
				
				
				//没有投注数直接跳过
				if(listBettingAllDay == null || listBettingAllDay.size() == 0) {
					log.Info("=========企业"+enterprise.getEnterprisename()+" 本周投注数为0，跳过不处理");
					continue;
				}
				Map<String, Double> validMoney = groupByUser(listBettingAllDay);//按会员分组下
				log.Info("=========企业"+enterprise.getEnterprisename()+" 本周投注详情："+validMoney);
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("enterpriseCode", enterprise.getEnterprisecode());
				List<EnterpriseEmployeeLevel> listEnterpriseEmployeeLevel = businessEmployeeLovelService.takelevelQuery(params);
				
				for (EnterpriseEmployeeLevel enterpriseEmployeeLevel : listEnterpriseEmployeeLevel) {
					
					String LevelName = enterpriseEmployeeLevel.getEmployeeLevelName();
					String LevelCode = enterpriseEmployeeLevel.getEmployeeLevelCode();
					
					//计算升级条件
					String conditionlevel = enterpriseEmployeeLevel.getConditionlevel();
					
					//企业尚未设置
					if(conditionlevel.equals("0-0")) {
						log.Info("=========企业"+enterprise.getEnterprisename()+" ["+LevelCode+"]["+LevelName+"]没有设定自动升级条件，跳过不处理...");
						continue;
					}
					log.Info("=========企业"+enterprise.getEnterprisename()+" ["+LevelCode+"]["+LevelName+"]自动升级条件为"+conditionlevel);
					
					double start = Double.valueOf(conditionlevel.split("-")[0]);
					double endrt = Double.valueOf(conditionlevel.split("-")[1]);
					
					
					for (String employeecode : validMoney.keySet()) {  
						double value = validMoney.get(employeecode);
						
						EnterpriseEmployee user = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
						
						//刚好符合该条件
						if(value >= start && value <= endrt) {
							
							/*************修正用户等级***************/
							mapUpdateLevel = new HashMap<String, Object>();
							mapUpdateLevel.put("employeelevelcode", enterpriseEmployeeLevel.getEmployeeLevelCode());
							mapUpdateLevel.put("array", new String[] { employeecode } );
							enterpriseEmployeeService.updateEmployeeLevel(mapUpdateLevel);//修改会员等级
							
							
							/*************修正用户等级***************/
							Map<String,BigDecimal> bounsdefault = enterpriseEmployeeLevelBonusService.takeEmployeeLevelBonusMap(enterpriseEmployeeLevel.getEmployeeLevelCode());
							if(bounsdefault != null && bounsdefault.size() > 0) {
								employeeGamecataloyBonusService.updateMultiMemberBonus(getListBounsToUpdate(user, bounsdefault));//批量修改用户的洗码设置
							}
							
							/*************添加日志***************/
							addlog(user, enterpriseEmployeeLevel, user.getEmployeelevelname(), new BigDecimal(value));
							
							log.Info("=========企业"+enterprise.getEnterprisename()+" 目标等级["+LevelCode+"]["+LevelName+"] 用户："+user.getLoginaccount()+" 满足条件，已升级。本周投注："+value);
						} else {
							
							log.Info("=========企业"+enterprise.getEnterprisename()+" 目标等级["+LevelCode+"]["+LevelName+"] 用户："+user.getLoginaccount()+" 未能满足升级条件，不处理升级..."+value);
							
						}
					}
				}
				
				log.Info("===================完成检查企业"+enterprise.getEnterprisename()+" 本周投注数："+listBettingAllDay.size());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			SmsUtilPublic.sendSmsByGet01(SmsUtilPublic.__NOTI_PHONENO, "VIP等级根据投注量来升级");
		}
	}
	
	private void addlog(EnterpriseEmployee ee, EnterpriseEmployeeLevel TOemployeeLevel , String FROMemployeeLevel, BigDecimal validMoney) {
		
		String content = ee.getLoginaccount().concat("满足升级条件[").concat(TOemployeeLevel.getConditionlevel()).concat("]，本周投注量").concat(MoneyHelper.doubleFormat(validMoney.toString())).concat(",");
		if(FROMemployeeLevel != null) {
			content = content.concat("从等级[").concat(FROMemployeeLevel).concat("]");
		}
		if(TOemployeeLevel != null) {
			content = content.concat("修正为[").concat(TOemployeeLevel.getEmployeeLevelName()).concat("]");
		}
		try {
			userLogsService.addActivityBetRecord(new UserLogs(
					ee.getEnterprisecode(), 
					ee.getEmployeecode(), 
					ee.getLoginaccount(), 
					UserLogs.Enum_operatype.VIP信息业务, content, "sa", "每周VIP等级更新"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static Map<String, Double> groupByUser(List<BettingAllDay> listBettingAllDay) {
		Map<String,Double> validMoney = new HashMap<String, Double>();
		
		for (BettingAllDay bettingAllDay : listBettingAllDay) {
			
			if(validMoney.containsKey(bettingAllDay.getEmployeecode())) {
				validMoney.put(bettingAllDay.getEmployeecode(), bettingAllDay.getValidMoney() + validMoney.get(bettingAllDay.getEmployeecode()));
			} else {
				validMoney.put(bettingAllDay.getEmployeecode(), bettingAllDay.getValidMoney());
			}
		}
		
		return validMoney;
	}
	
	private static List<EmployeeGamecataloyBonus> getListBounsToUpdate(EnterpriseEmployee ee, Map<String,BigDecimal> bouns) {
		List<EmployeeGamecataloyBonus> list = new ArrayList<EmployeeGamecataloyBonus>();
		
		for (String key : bouns.keySet()) {  
			String[] gamekeys = key.split("_");
			list.add(new EmployeeGamecataloyBonus(ee.getEmployeecode(),ee.getParentemployeecode(),gamekeys[0],gamekeys[1], bouns.get(key)));
		}
		
		return list;
	}
}
