package com.maven.report.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hy.pull.common.util.DateUtil;
import com.hy.pull.common.util.MoneyHelper;
import com.hy.pull.common.util.SCom;
import com.hy.pull.common.util.api.GameAPI;
import com.maven.cache.SystemCache;
import com.maven.entity.DataHandleMaintenance;
import com.maven.entity.DataHandleMaintenance.Enum_flag;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EmployeeApiAccoutPasswordJob;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeLevel;
import com.maven.entity.EnterpriseGame;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.EnterpriseOperatingBrandGame;
import com.maven.entity.EnterpriseReportDates;
import com.maven.entity.Game;
import com.maven.entity.UserLogs;
import com.maven.game.APIServiceUtil;
import com.maven.game.enums.GameEnum;
import com.maven.game.enums.GameEnum.Enum_GameType;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.DataHandleMaintenanceService;
import com.maven.service.EmployeeApiAccoutPasswordJobService;
import com.maven.service.EmployeeApiAccoutService;
import com.maven.service.EnterpriseGameService;
import com.maven.service.EnterpriseOperatingBrandGameService;
import com.maven.service.EnterpriseOperatingBrandService;
import com.maven.service.EnterpriseReportDatesService;
import com.maven.service.EnterpriseService;
import com.maven.service.GameService;
import com.maven.service.UserLogsService;
import com.maven.util.MailUtil;
import com.maven.util.SmsUtilPublic;

import net.sf.json.JSONObject;

/**
 * 按企业生成每日的报表统计数据，仅供查询使用
 * @author Administrator
 *
 */
@Component
@Lazy(false)
public class DataEnterpriseReportDatesJob {
	
	private static LoggerManager log = LoggerManager.getLogger(DataEnterpriseReportDatesJob.class.getName(),OutputManager.LOG_DAY_BUT_BONUS);
	
	@Autowired
	private UserLogsService userLogsService;
	@Autowired
	private EnterpriseReportDatesService enterpriseReportDatesService;
	@Autowired
	private EnterpriseService enterpriseService;
	
	@Scheduled(cron = "0 1 9 * * ?")//正式
	public void work() {
		
		
		log.Error("#########################开始企业生成每日的报表统计数据#########################");
			
		try {
			List<Enterprise> es = enterpriseService.selectAll(new HashMap<String, Object>());
			List<EnterpriseReportDates> __list = new ArrayList<EnterpriseReportDates>();
			Date createtime = new Date();
			int reportdate = SCom.getInt(DateUtil.parse(DateUtil.add(new Date(), Calendar.DATE, -1), "yyyyMMdd"));
			
			Map<String, Object> paramObj = new HashMap<String, Object>();
			
			//以企业级为基数执行
			for (Enterprise enterprise : es) { 
				String enterprisecode = enterprise.getEnterprisecode();
				
				paramObj.clear();
				paramObj.put("in_enterprisecode", enterprisecode);
				paramObj.put("in_reportdate", reportdate);
				List<EnterpriseReportDates> listdata = enterpriseReportDatesService.call_userDepositTakeReport(paramObj);
				
				int memberRegeditCount = SCom.getInt(paramObj.get("memberRegeditCount"));
				int agentRegeditCount = SCom.getInt(paramObj.get("agentRegeditCount"));
				int loginCount = SCom.getInt(paramObj.get("loginCount"));
				
				int firstDepositCount = SCom.getInt(paramObj.get("firstDepositCount"));
				double firstDepositMoney = SCom.getDouble(paramObj.get("firstDepositMoney"));
				
				int secondDepositCount = SCom.getInt(paramObj.get("secondDepositCount"));
				double secondDepositMoney = SCom.getDouble(paramObj.get("secondDepositMoney"));
				int noSecondCount = SCom.getInt(paramObj.get("noSecondCount"));
				int noThreeCount = SCom.getInt(paramObj.get("noThreeCount"));
				
				int todayDepositCount = SCom.getInt(paramObj.get("todayDepositCount"));
				int todayDepositCount1 = 0;//存款人次
				double todayDepositMoney = 0;
				
				int todayTakeCount = SCom.getInt(paramObj.get("todayTakeCount"));
				int todayTakeCount1 = 0;//取款人次
				double todayTakeMoney = 0;
				
				double todayBetmoney = SCom.getDouble(paramObj.get("todayBetmoney"));
				double todayNetmoney = SCom.getDouble(paramObj.get("todayNetmoney"));
				double todayVaildmoney = SCom.getDouble(paramObj.get("todayVaildmoney"));
				
				double todayProsmoney = 0;
				double todayConsmoney = 0;
				
				int todayPreferentialCount = 0;
				double todayPreferentialMoney = 0;
				
				int todayWashCount = 0;
				double todayWashMoney = 0;
				
				for (EnterpriseReportDates item : listdata) {
					String code = item.getMoneychangetypecode();
					String amount = item.getMoneychangeamount();
					String count = item.getMoneychangecount();
					
					if(Enum_moneychangetype.存款.value.equals(code)) {
						todayDepositCount1 = SCom.getInt(count);
						todayDepositMoney = SCom.getDouble(amount);
					} else if(Enum_moneychangetype.取款.value.equals(code) || 
							Enum_moneychangetype.取款驳回.value.equals(code) || 
							Enum_moneychangetype.取款拒绝.value.equals(code)) {
						todayTakeCount1 += SCom.getInt(count);
						todayTakeMoney += SCom.getDouble(amount);
					} else if(Enum_moneychangetype.冲正.value.equals(code)) {
						todayProsmoney = SCom.getDouble(amount);
					} else if(Enum_moneychangetype.冲负.value.equals(code)) {
						todayConsmoney = SCom.getDouble(amount);
					} else if(Enum_moneychangetype.优惠活动.value.equals(code)) {
						todayPreferentialCount = SCom.getInt(count);
						todayPreferentialMoney = SCom.getDouble(amount);
					} else if(Enum_moneychangetype.洗码日结.value.equals(code) ||
							 Enum_moneychangetype.周结校验补发.value.equals(code) ||
							 Enum_moneychangetype.洗码周结.value.equals(code) ||
							 Enum_moneychangetype.洗码冲减.value.equals(code) ) {
						todayWashCount += SCom.getInt(count);
						todayWashMoney += SCom.getDouble(amount);
					} 
				}
				
				firstDepositMoney = MoneyHelper.moneyFormatDouble(firstDepositMoney);
				secondDepositMoney = MoneyHelper.moneyFormatDouble(secondDepositMoney);
				todayDepositMoney = MoneyHelper.moneyFormatDouble(todayDepositMoney);
				todayTakeMoney = MoneyHelper.moneyFormatDouble(todayTakeMoney);
				todayBetmoney = MoneyHelper.moneyFormatDouble(todayBetmoney);
				todayNetmoney = MoneyHelper.moneyFormatDouble(todayNetmoney);
				todayVaildmoney = MoneyHelper.moneyFormatDouble(todayVaildmoney);
				todayProsmoney = MoneyHelper.moneyFormatDouble(todayProsmoney);
				todayConsmoney = MoneyHelper.moneyFormatDouble(todayConsmoney);
				todayPreferentialMoney = MoneyHelper.moneyFormatDouble(todayPreferentialMoney);
				todayWashMoney = MoneyHelper.moneyFormatDouble(todayWashMoney);
				
				
				__list.add(
				new EnterpriseReportDates(enterprisecode, reportdate, memberRegeditCount,
						agentRegeditCount,  loginCount,  firstDepositCount,  firstDepositMoney,
						 secondDepositCount,  secondDepositMoney,  noSecondCount,  noThreeCount,
						 todayDepositCount,  todayDepositCount1,  todayDepositMoney,  todayTakeCount,
						 todayTakeCount1,  todayTakeMoney,  todayBetmoney,  todayNetmoney,
						 todayVaildmoney,  todayProsmoney,  todayConsmoney,  todayPreferentialCount,
						 todayPreferentialMoney,  todayWashCount,  todayWashMoney,  createtime));
			}
			
			if(__list.size() > 0) {
				enterpriseReportDatesService.saveBatchRecord(__list);
				log.Error("#########################批量生成记录数："+__list.size()+"条#########################");
			}
		} catch (Exception e) {
			e.printStackTrace();
			SmsUtilPublic.sendSmsByGet01(SmsUtilPublic.__NOTI_PHONENO, "企业生成每日的报表统计数据");
		}
		
		log.Error("#########################完成企业生成每日的报表统计数据#########################");
		
	}
	
	
	
	private void addlog(EnterpriseGame enterpriseGame, Game game, String status) {
		
		String content = status + " 企业下所有品牌的"+game.getGamename();
		try {
			userLogsService.addActivityBetRecord(new UserLogs(
					enterpriseGame.getEnterprisecode(), 
					null, 
					null, 
					UserLogs.Enum_operatype.游戏信息业务, content, "sa", "自动检查平台维护情况"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
