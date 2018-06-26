package com.maven.report;

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

import com.maven.entity.BettingAllDay;
import com.maven.entity.EmployeeRelation;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.ReportDailyAgent;
import com.maven.entity.ReportWeeklyMember.Enum_status;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BettingAllDayService;
import com.maven.service.EmployeeGamecataloyBonusService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseService;
import com.maven.service.ReportDailyAgentService;
import com.maven.util.SmsUtilPublic;

@Component
@Lazy(false)
public class GenerateReportDailyAgent {
	
	private static LoggerManager log = LoggerManager.getLogger(
			GenerateReportDailyAgent.class.getName(), OutputManager.LOG_REPORTMEMBER);
	
	@SuppressWarnings("serial")
	private static List<String> ALL_AGENTTYPE = new ArrayList<String>(){{this.add(EnterpriseEmployeeType.Type.代理.value);this.add(EnterpriseEmployeeType.Type.信用代理.value);}};
	
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private EmployeeGamecataloyBonusService employeeGamecataloyBonusService;
	@Autowired
	private BettingAllDayService bettingAllDayService;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private ReportDailyAgentService reportDailyAgentService;
	
	
	@Scheduled(cron = "0 20 12 * * ?")//正式
//	@Scheduled(cron = "0 45 14 * * ?")//正式
	public void report(){
		//业务步骤
		//1从玩家日洗码记录中统计代理记录 2记录分类为游戏平台,游戏类别 3获取代理洗码计算下线所有投注额 4发放金额记录账变
		//TODO 考虑是否增加多线程
		workDetails();//正式
		
		workDetails2();
		
	}
	
	public void workDetails(){
		
		
		try {
			log.Info("=========================开始生成代理日结洗码报表=========================");
			
			
			
			List<Enterprise> es = enterpriseService.selectAll(new HashMap<String, Object>());
			for (Enterprise e : es) { //以企业级为基数执行
				log.Info("企业"+e.getEnterprisecode()+"开始生成代理日洗码报表");
				Date now = new Date();//正式
				Map<String, Object> ep = new HashMap<String, Object>();
				ep.put("enterprisecode", e.getEnterprisecode());
				ep.put("employeetypecodes", ALL_AGENTTYPE);
				
				List<EnterpriseEmployee> agents = enterpriseEmployeeService.queryAgent(ep);
				
				
				if (agents.isEmpty()) {
					log.Info("企业"+e.getEnterprisecode()+"下无代理");
					continue;//跳过，不能返回
				}
				List<String> agentId = new ArrayList<String>(); //获取所有代理code
				for (EnterpriseEmployee ee : agents) { //组装本次执行的everytimesize个代理code
					if (!agentId.contains(ee.getEmployeecode())) {
						agentId.add(ee.getEmployeecode());
					}
				}
				Map<String, Map<String, BigDecimal>> egbmap = employeeGamecataloyBonusService.findGameBonus(agentId); //根据所有用户查询用户打码比例
				log.Info("共计代理数量："+agents.size());
				
				Map<String, ReportDailyAgent> insertMap = new HashMap<String, ReportDailyAgent>();
				for (EnterpriseEmployee ee : agents) {
					List<EmployeeRelation> teamList = enterpriseEmployeeService.call_uspTakeTeamAgent(ee.getEmployeecode());
					if (teamList == null || teamList.isEmpty()) {
						continue;
					}
					List<String> now_agent_id = new ArrayList<String>();
					for (EmployeeRelation er : teamList) {
						now_agent_id.add(er.getEmployeecode());
					}
					
					ep.clear();
					ep.put("enterprisecode", e.getEnterprisecode());
					ep.put("parentemployeecodes", now_agent_id);
//					ep.put("betTime", DateUtil.add(now, Calendar.DATE, -1));//投注日期为昨天的
					ep.put("paytype", BettingAllDay.Enum_paytype.正常.value);//正常的
					ep.put("isDailyAgent", BettingAllDay.Enum_isDailyAgent.未日结.value);//代理未日结的
					List<BettingAllDay> bads = this.bettingAllDayService.selectBettingByDate(ep);
					
					log.Info("查询到日结数："+bads.size());
					
					for (BettingAllDay bad : bads) { //开始计算每个用户的投注额
						
						String nowkey = ee.getEmployeecode()+bad.getGamePlatform()+bad.getGameBigType();
						Map<String, BigDecimal> ratios = egbmap.get(ee.getEmployeecode());
						BigDecimal ratio = BigDecimal.valueOf(0.0000); 
						if(ratios == null) {
							//调试发现存在NULL
						} else {
							ratio = ratios.get(bad.getGamePlatform()+bad.getGameBigType());
						}
						ratio = ratio==null?BigDecimal.valueOf(0.0000):ratio;
						BigDecimal amount = ratio.multiply(BigDecimal.valueOf(bad.getValidMoney()));
						ReportDailyAgent rda;
						if (insertMap.containsKey(nowkey)) {
							rda = insertMap.get(nowkey);
							rda.setBet(rda.getBet().add(BigDecimal.valueOf(bad.getValidMoney())));
							rda.setAmount(rda.getAmount().add(amount));
						} else {
							rda = new ReportDailyAgent(ee.getEnterprisecode(), ee.getBrandcode(), ee.getEmployeecode(), 
									ee.getParentemployeecode(), ee.getLoginaccount(), BigDecimal.valueOf(0.00), BigDecimal.valueOf(0.00), 
									BigDecimal.valueOf(0.0000), bad.getBetDay(), now, bad.getGamePlatform(), bad.getGameBigType(), Enum_status.未发放.value);
							rda.setBet(BigDecimal.valueOf(bad.getValidMoney()));
							rda.setAmount(amount);
							rda.setRatio(ratio);
							rda.setIsWeeklyAgent(ReportDailyAgent.Enum_isWeeklyAgent.未周结.value);
							insertMap.put(nowkey, rda);
						}
						
						//更新为代理已日结
						bad.setIsDailyAgent(BettingAllDay.Enum_isDailyAgent.已日结.value);
						this.bettingAllDayService.updateByPrimary(bad);
						
					}
					
				}
				
				log.Info("企业"+e.getEnterprisecode()+"执行代理日洗码数："+insertMap.size());
				
				List<ReportDailyAgent> insertList = new ArrayList<ReportDailyAgent>(insertMap.values());
				if (insertList == null || insertList.isEmpty()) {
					continue;
				}
				this.reportDailyAgentService.saveBatch(insertList);
				log.Info("企业"+e.getEnterprisecode()+"执行代理日洗码计算完成");
			}
			
			log.Info("=========================完成生成代理日结洗码报表=========================");
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.Error("代理日洗码报表生成失败", e);
			SmsUtilPublic.sendSmsByGet01(SmsUtilPublic.__NOTI_PHONENO, "代理日洗码报表生成失败");
		}
	}

	
	public void workDetails2(){
		
		try {
			int totalCount = 0;
			
			log.Info("=========================开始生成代理日结洗码报表（补单业务，确保日代理正常与补发的区分开来）=========================");
			List<Enterprise> es = enterpriseService.selectAll(new HashMap<String, Object>());
			
			for (Enterprise e : es) { //以企业级为基数执行
				log.Info("补单：企业"+e.getEnterprisecode()+"开始生成代理日洗码报表");
				Date now = new Date();//正式
				Map<String, Object> ep = new HashMap<String, Object>();
				ep.put("enterprisecode", e.getEnterprisecode());
				ep.put("employeetypecodes", ALL_AGENTTYPE);
				
				List<EnterpriseEmployee> agents = enterpriseEmployeeService.queryAgent(ep);
				
				
				if (agents.isEmpty()) {
					log.Info("补单：企业"+e.getEnterprisecode()+"下无代理");
					continue;//跳过，不能返回
				}
				List<String> agentId = new ArrayList<String>(); //获取所有代理code
				for (EnterpriseEmployee ee : agents) { //组装本次执行的everytimesize个代理code
					if (!agentId.contains(ee.getEmployeecode())) {
						agentId.add(ee.getEmployeecode());
					}
				}
				Map<String, Map<String, BigDecimal>> egbmap = employeeGamecataloyBonusService.findGameBonus(agentId); //根据所有用户查询用户打码比例
				log.Info("补单：共计代理数量："+agents.size());
				
				Map<String, ReportDailyAgent> insertMap = new HashMap<String, ReportDailyAgent>();
				for (EnterpriseEmployee ee : agents) {
					List<EmployeeRelation> teamList = enterpriseEmployeeService.call_uspTakeTeamAgent(ee.getEmployeecode());
					if (teamList == null || teamList.isEmpty()) {
						continue;
					}
					List<String> now_agent_id = new ArrayList<String>();
					for (EmployeeRelation er : teamList) {
						now_agent_id.add(er.getEmployeecode());
					}
					
					ep.clear();
					ep.put("enterprisecode", e.getEnterprisecode());
					ep.put("parentemployeecodes", now_agent_id);
					ep.put("paytype", BettingAllDay.Enum_paytype.补发.value);//补发的
					ep.put("isDailyAgent", BettingAllDay.Enum_isDailyAgent.未日结.value);//代理未日结的
					List<BettingAllDay> bads = this.bettingAllDayService.selectBettingByDate(ep);
					
					log.Info("补单：查询到日结数："+bads.size());
					
					for (BettingAllDay bad : bads) { //开始计算每个用户的投注额
						
						String nowkey = ee.getEmployeecode()+bad.getGamePlatform()+bad.getGameBigType();
						Map<String, BigDecimal> ratios = egbmap.get(ee.getEmployeecode());
						BigDecimal ratio = BigDecimal.valueOf(0.0000); 
						if(ratios == null) {
							//调试发现存在NULL
						} else {
							ratio = ratios.get(bad.getGamePlatform()+bad.getGameBigType());
						}
						ratio = ratio==null?BigDecimal.valueOf(0.0000):ratio;
						BigDecimal amount = ratio.multiply(BigDecimal.valueOf(bad.getValidMoney()));
						ReportDailyAgent rda;
						if (insertMap.containsKey(nowkey)) {
							rda = insertMap.get(nowkey);
							rda.setBet(rda.getBet().add(BigDecimal.valueOf(bad.getValidMoney())));
							rda.setAmount(rda.getAmount().add(amount));
						} else {
							rda = new ReportDailyAgent(ee.getEnterprisecode(), ee.getBrandcode(), ee.getEmployeecode(), 
									ee.getParentemployeecode(), ee.getLoginaccount(), BigDecimal.valueOf(0.00), BigDecimal.valueOf(0.00), 
									BigDecimal.valueOf(0.0000), bad.getBetDay(), now, bad.getGamePlatform(), bad.getGameBigType(), Enum_status.未发放.value);
							rda.setBet(BigDecimal.valueOf(bad.getValidMoney()));
							rda.setAmount(amount);
							rda.setRatio(ratio);
							rda.setIsWeeklyAgent(ReportDailyAgent.Enum_isWeeklyAgent.未周结.value);
							insertMap.put(nowkey, rda);
						}
						
						//更新为代理已日结
						bad.setIsDailyAgent(BettingAllDay.Enum_isDailyAgent.已日结.value);
						this.bettingAllDayService.updateByPrimary(bad);
						
						totalCount ++;
					}
					
				}
				
				log.Info("补单：企业"+e.getEnterprisecode()+"执行代理日洗码数："+insertMap.size());
				
				List<ReportDailyAgent> insertList = new ArrayList<ReportDailyAgent>(insertMap.values());
				if (insertList == null || insertList.isEmpty()) {
					continue;
				}
				this.reportDailyAgentService.saveBatch(insertList);
				log.Info("补单：企业"+e.getEnterprisecode()+"执行代理日洗码计算完成");
			}
			
			log.Info("=========================完成生成代理日结洗码报表（补单业务，确保日代理正常与补发的区分开来）========================="+totalCount);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.Error("补单：代理日洗码报表生成失败", e);
			SmsUtilPublic.sendSmsByGet01(SmsUtilPublic.__NOTI_PHONENO, "补单：代理日洗码报表生成失败");
		}
		
	}
}
