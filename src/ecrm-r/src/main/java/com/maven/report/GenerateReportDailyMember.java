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
import com.maven.entity.Enterprise;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BettingAllDayService;
import com.maven.service.EmployeeGamecataloyBonusService;
import com.maven.service.EnterpriseService;
import com.maven.util.Arith;
import com.maven.util.SmsUtilPublic;

@Component
@Lazy(false)
public class GenerateReportDailyMember {
	
	private static LoggerManager log = LoggerManager.getLogger(
			GenerateReportDailyMember.class.getName(), OutputManager.LOG_REPORTMEMBER);
	
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private BettingAllDayService bettingAllDayService;
	@Autowired
	private EmployeeGamecataloyBonusService employeeGamecataloyBonusService;
	
	@Scheduled(cron = "0 10 12 * * ?")//正式
//	@Scheduled(cron = "0 20 13 * * ?")//测试
	public void report(){
		//业务步骤
		//1获取所有企业 2根据企业所有会员昨日投注额 3根据会员获取会员洗码比例 4计算插入记录
		//TODO 考虑是否增加多线程
		workDetails();//正式
		
		workDetails2();
	}
	
	public void workDetails(){
		try {
			
			log.Info("=========================开始生成会员日结洗码报表=========================");
			
			
			Date now = new Date();//正式
			Map<String, Object> params = new HashMap<String, Object>();
			List<Enterprise> es = enterpriseService.selectAll(params);
			for (Enterprise e : es) { //以企业级为基数执行
				log.Info("企业"+e.getEnterprisecode()+"开始生成会员洗码报表");
				
				Map<String, Object> ep = new HashMap<String, Object>();
				ep.put("enterprisecode", e.getEnterprisecode());
//				ep.put("betTime", DateUtil.add(now, Calendar.DATE, -1));//投注日期为昨天的
				ep.put("rebates", false);//未洗码的
				ep.put("paytype", BettingAllDay.Enum_paytype.正常.value);//正常的
				List<BettingAllDay> bettingallemployee = bettingAllDayService.selectBettingByDate(ep); //查询企业下昨日所有游戏投注用户
				log.Info("企业"+e.getEnterprisecode()+"日结数："+bettingallemployee.size());
				
				List<String> set_epcode = new ArrayList<String>();
				for (BettingAllDay bad : bettingallemployee) {
					if (!set_epcode.contains(bad.getEmployeecode())) {
						set_epcode.add(bad.getEmployeecode());
					}
				}
				if (set_epcode.isEmpty()) {
					log.Info("企业"+e.getEnterprisecode()+"昨日投注用户为空");
					continue;//跳过，不能返回
				}
				log.Info("企业"+e.getEnterprisecode()+"代理数："+set_epcode.size());
				
				Map<String, Map<String, BigDecimal>> egbmap = employeeGamecataloyBonusService.findGameBonus(set_epcode); //根据所有用户查询用户打码比例
				for (BettingAllDay bad : bettingallemployee) { //计算用户投注内容
					Map<String, BigDecimal> ratios = egbmap.get(bad.getEmployeecode());
					BigDecimal ratio = new BigDecimal(0);
					if(ratios == null) {
						//调试发现存在NULL
					} else {
						ratio = ratios.get(bad.getGamePlatform()+bad.getGameBigType());
					}
					double rebatesCash = 0.0;
					if (ratio != null && ratio.doubleValue() > 0) {
						rebatesCash = Arith.mul(bad.getValidMoney(), ratio.doubleValue());
						bad.setRatio(ratio.doubleValue());//记录比例
					} else {
						bad.setRatio(0.0);//记录比例
						log.Info("会员"+bad.getEmployeecode()+"游戏"+bad.getGamePlatform()+"大类"+bad.getGameBigType()+"配置的打码量小于等于0");
					}
					bad.setRebates(true);//标识为已洗码
					bad.setRebatesCash(rebatesCash);
					this.bettingAllDayService.updateByPrimary(bad);
				}
				log.Info("企业"+e.getEnterprisecode()+"执行会员洗码量计算完成");
			}
			
			log.Info("=========================完成生成会员日结洗码报表=========================");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.Error("会员日洗码报表生成失败", e);
			SmsUtilPublic.sendSmsByGet01(SmsUtilPublic.__NOTI_PHONENO, "会员日洗码报表生成失败");
		}
	}
	
	
	public void workDetails2(){
		
		try {
			
			/********************************************************补单业务******************************************************************/
			List<Enterprise> es = enterpriseService.selectAll(new HashMap<String, Object>());
			
			int totalCount = 0;
			
			log.Info("=========================开始生成会员日结洗码报表（补单业务，确保日结表的返水额不为空NULL）=========================");
			for (Enterprise e : es) { //以企业级为基数执行
				log.Info("补单：企业"+e.getEnterprisecode()+"开始生成会员洗码报表");
				
				Map<String, Object> ep = new HashMap<String, Object>();
				ep.put("enterprisecode", e.getEnterprisecode());
				ep.put("rebates", false);//未洗码的
				ep.put("paytype", BettingAllDay.Enum_paytype.补发.value);//补发的
				List<BettingAllDay> bettingallemployee = bettingAllDayService.selectBettingByDate(ep); //查询企业下昨日所有游戏投注用户
				log.Info("补单：企业"+e.getEnterprisecode()+"日结数："+bettingallemployee.size());
				
				List<String> set_epcode = new ArrayList<String>();
				for (BettingAllDay bad : bettingallemployee) {
					if (!set_epcode.contains(bad.getEmployeecode())) {
						set_epcode.add(bad.getEmployeecode());
					}
				}
				if (set_epcode.isEmpty()) {
					log.Info("补单：企业"+e.getEnterprisecode()+"没有需要补单的数据");
					continue;//跳过，不能返回
				}
				log.Info("补单：企业"+e.getEnterprisecode()+"代理数："+set_epcode.size());
				
				Map<String, Map<String, BigDecimal>> egbmap = employeeGamecataloyBonusService.findGameBonus(set_epcode); //根据所有用户查询用户打码比例
				for (BettingAllDay bad : bettingallemployee) { //计算用户投注内容
					Map<String, BigDecimal> ratios = egbmap.get(bad.getEmployeecode());
					BigDecimal ratio = new BigDecimal(0);
					if(ratios == null) {
						//调试发现存在NULL
					} else {
						ratio = ratios.get(bad.getGamePlatform()+bad.getGameBigType());
					}
					double rebatesCash = 0.0;
					if (ratio != null && ratio.doubleValue() > 0) {
						rebatesCash = Arith.mul(bad.getBetMoney(), ratio.doubleValue());
					} else {
						log.Info("补单：会员"+bad.getEmployeecode()+"游戏"+bad.getGamePlatform()+"大类"+bad.getGameBigType()+"配置的打码量小于等于0");
					}
					bad.setRebates(true);//标识为已洗码
					bad.setRebatesCash(rebatesCash);
					this.bettingAllDayService.updateByPrimary(bad);
					
					totalCount ++;
				}
				log.Info("补单：企业"+e.getEnterprisecode()+"执行会员洗码量计算完成");
			}
			
			log.Info("=========================完成生成会员日结洗码报表（补单业务，确保日结表的返水额不为空NULL）========================="+totalCount);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.Error("补单：会员日洗码报表生成失败", e);
			SmsUtilPublic.sendSmsByGet01(SmsUtilPublic.__NOTI_PHONENO, "补单：会员日洗码报表生成失败");
		}
		
	}
	
	public static void main(String[] args) {
		GenerateReportDailyMember dailyMember = new GenerateReportDailyMember();
		dailyMember.report();
	}
}
