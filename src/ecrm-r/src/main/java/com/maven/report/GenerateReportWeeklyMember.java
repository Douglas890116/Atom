package com.maven.report;

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

import com.maven.entity.BettingAllDay;
import com.maven.entity.BettingAllDay.Enum_status;
import com.maven.entity.Enterprise;
import com.maven.entity.ReportWeeklyAgent;
import com.maven.entity.ReportWeeklyMember;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.payment.th.frdemo.MD5Encoder;
import com.maven.service.BettingAllDayService;
import com.maven.service.EmployeeGamecataloyBonusService;
import com.maven.service.EnterpriseService;
import com.maven.service.ReportWeeklyMemberService;
import com.maven.util.Arith;
import com.maven.util.DateUtils;
import com.maven.util.SmsUtilPublic;

@Component
@Lazy(false)
public class GenerateReportWeeklyMember {
	
	private static LoggerManager log = LoggerManager.getLogger(
			GenerateReportWeeklyMember.class.getName(), OutputManager.LOG_REPORTMEMBER);
	
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private BettingAllDayService bettingAllDayService;
	@Autowired
	private EmployeeGamecataloyBonusService employeeGamecataloyBonusService;
	@Autowired
	private ReportWeeklyMemberService reportWeeklyMemberService;
	
	@Scheduled(cron = "0 30 18 ? * MON")//正式
//	@Scheduled(cron = "0 30 2 ? * MON")//正式
	public void report(){
		//业务步骤
		//1从日会员洗码报表中统计上周所有洗码 2统计已发放和未发放金额 3根据会员获取会员洗码比例 4计算插入记录
		//TODO 考虑是否增加多线程
		workDetails();//正式
		
		workDetails2();
	}
	
	public void workDetails(){
		try {
			List<Enterprise> es = enterpriseService.selectAll(new HashMap<String, Object>());
			
			
			log.Info("=========================开始生成会员周结洗码报表=========================");
			
			Date lastMonday = DateUtils.getLastMonday();
			Date lastSunday = DateUtils.getLastSunday();
			
			
			
			for (Enterprise e : es) { //以企业级为基数执行
				log.Info("====企业"+e.getEnterprisecode()+"开始生成会员周洗码报表");
				
				Date now = new Date();
				Map<String, Object> ep = new HashMap<String, Object>();
				ep.put("enterprisecode", e.getEnterprisecode());
				ep.put("startBetDay", lastMonday);
				ep.put("endBetDay", lastSunday);
				ep.put("isWeeklyMember", BettingAllDay.Enum_isWeeklyMember.未周结.value);
				List<BettingAllDay> bettingallemployee = bettingAllDayService.selectBettingByDate(ep); //查询企业下上周所有游戏投注用户
				log.Info("企业"+e.getEnterprisecode()+"本周日结数："+bettingallemployee.size());
				
				List<String> set_epcode = new ArrayList<String>();
				for (BettingAllDay bad : bettingallemployee) {
					if (!set_epcode.contains(bad.getEmployeecode())) {
						set_epcode.add(bad.getEmployeecode());
					}
				}
				if (set_epcode.isEmpty()) {
					log.Info("企业"+e.getEnterprisecode()+"上周投注用户为空");
					continue;//跳过，不能返回
				}
				Map<String, Map<String, BigDecimal>> egbmap = employeeGamecataloyBonusService.findGameBonus(set_epcode); //根据所有用户查询用户打码比例
				
				Map<String, ReportWeeklyMember> weeklyMembers = new HashMap<String, ReportWeeklyMember>(); 
				
				for (BettingAllDay bad : bettingallemployee) { //计算用户周投注内容
					
					String weeklyMembersKey = bad.getUserName()+bad.getGamePlatform()+bad.getGameBigType();
					
					Map<String, BigDecimal> ratios = egbmap.get(bad.getEmployeecode());
					
					BigDecimal ratio = BigDecimal.valueOf(0.0000); 
					if(ratios == null) {
						//调试发现存在NULL
					} else {
						ratio = ratios.get(bad.getGamePlatform()+bad.getGameBigType());
					}
					ratio = ratio==null?new BigDecimal(0):ratio;
					
					ReportWeeklyMember wm = null;
					if (!weeklyMembers.containsKey(weeklyMembersKey)) {
						wm = new ReportWeeklyMember(bad.getEnterprisecode(), bad.getBrandcode(), bad.getEmployeecode(), bad.getParentemployeecode(), 
								bad.getUserName(), bad.getValidMoney(), 0.0, 0.0, ratio.doubleValue(), lastMonday, lastSunday, now,
								bad.getGamePlatform(), bad.getGameBigType(), Enum_status.未发放.value.toString());
						
						//
						wm.setPaytype(ReportWeeklyMember.Enum_paytype.正常.value);//
						wm.setPatchno(MD5Encoder.encode(wm.getLoginaccount()+wm.getGameplatform()+wm.getGametype()+lastMonday.getTime()));//批次号，同一组数据一个批次号
						
						
						if (bad.getStatus() == Enum_status.未发放.value) {
							wm.setAmount(formatDouble(bad.getRebatesCash()));
							wm.setActual(wm.getAmount());//默认实发等于应发额
							bad.setStatus(Enum_status.周结汇总.value);
							
						} else {
							wm.setSubtract(formatDouble(bad.getRebatesCash()));
						}
					} else {
						wm = weeklyMembers.get(weeklyMembersKey);
						if (bad.getStatus() == Enum_status.未发放.value) {
							wm.setBet(wm.getBet()+bad.getValidMoney());
							wm.setAmount(Arith.add(wm.getAmount(), formatDouble(bad.getRebatesCash()) ));
							wm.setActual(wm.getAmount());//默认实发等于应发额
							bad.setStatus(Enum_status.周结汇总.value);
							
						} else {
							wm.setSubtract(Arith.add(wm.getSubtract(), formatDouble(bad.getRebatesCash()) ));
						}
					}
					
					// 更新
					bad.setIsWeeklyMember(BettingAllDay.Enum_isWeeklyMember.已周结.value);
					this.bettingAllDayService.updateByPrimary(bad);
					
					weeklyMembers.put(weeklyMembersKey, wm);
					
					
				}
				reportWeeklyMemberService.insertBatch(new ArrayList<ReportWeeklyMember>(weeklyMembers.values()));
				log.Info("====企业"+e.getEnterprisecode()+"执行会员周洗码量计算完成");
			}
			
			log.Info("=========================完成生成会员周结洗码报表=========================");
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.Error("会员日洗码报表生成失败", e);
			SmsUtilPublic.sendSmsByGet01(SmsUtilPublic.__NOTI_PHONENO, "会员日洗码报表生成失败");
		}
	}
	
	public void workDetails2(){
		
		try {
			List<Enterprise> es = enterpriseService.selectAll(new HashMap<String, Object>());
			
			int totalCount = 0;
			
			log.Info("=========================开始生成会员周结洗码报表（补单业务，确保周结中存在后续补发的数据）=========================");
			
			Date lastMonday = null;
			Date lastSunday = null;
			
			for (Enterprise e : es) { //以企业级为基数执行
				log.Info("补单：企业"+e.getEnterprisecode()+"开始生成会员周洗码报表");
				
				Date now = new Date();
				Map<String, Object> ep = new HashMap<String, Object>();
				ep.put("enterprisecode", e.getEnterprisecode());
				ep.put("isWeeklyMember", BettingAllDay.Enum_isWeeklyMember.未周结.value);
				List<BettingAllDay> bettingallemployee = bettingAllDayService.selectBettingByDate(ep); //查询企业下上周所有游戏投注用户
				log.Info("补单：企业"+e.getEnterprisecode()+"本周日结数："+bettingallemployee.size());
				
				List<String> set_epcode = new ArrayList<String>();
				for (BettingAllDay bad : bettingallemployee) {
					if (!set_epcode.contains(bad.getEmployeecode())) {
						set_epcode.add(bad.getEmployeecode());
					}
				}
				if (set_epcode.isEmpty()) {
					log.Info("补单：企业"+e.getEnterprisecode()+"上周投注用户为空");
					continue;//跳过，不能返回
				}
				Map<String, Map<String, BigDecimal>> egbmap = employeeGamecataloyBonusService.findGameBonus(set_epcode); //根据所有用户查询用户打码比例
				
				Map<String, ReportWeeklyMember> weeklyMembers = new HashMap<String, ReportWeeklyMember>(); 
				
				for (BettingAllDay bad : bettingallemployee) { //计算用户周投注内容
					
					String weeklyMembersKey = bad.getUserName()+bad.getGamePlatform()+bad.getGameBigType();
					
					Map<String, BigDecimal> ratios = egbmap.get(bad.getEmployeecode());
					
					BigDecimal ratio = BigDecimal.valueOf(0.0000); 
					if(ratios == null) {
						//调试发现存在NULL
					} else {
						ratio = ratios.get(bad.getGamePlatform()+bad.getGameBigType());
					}
					ratio = ratio==null?new BigDecimal(0):ratio;
					
					//查找数据所在的起始周一与周末日期
					lastMonday = DateUtils.getLastMonday(bad.getBetDay());
					lastSunday = DateUtils.getLastSunday(bad.getBetDay());
					
					
					ReportWeeklyMember wm = null;
					if (!weeklyMembers.containsKey(weeklyMembersKey)) {
						wm = new ReportWeeklyMember(bad.getEnterprisecode(), bad.getBrandcode(), bad.getEmployeecode(), bad.getParentemployeecode(), 
								bad.getUserName(), bad.getValidMoney(), 0.0, 0.0, ratio.doubleValue(), lastMonday, lastSunday, now,
								bad.getGamePlatform(), bad.getGameBigType(), Enum_status.未发放.value.toString());
						
						
						//查找同一批次的正常周结数据，取出批次号
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("brandcode", bad.getBrandcode());
						params.put("employeecode", bad.getEmployeecode());
						params.put("enterprisecode", bad.getEnterprisecode());
						params.put("starttime", lastMonday);
						params.put("endtime", lastSunday);
						params.put("gamePlatform", bad.getGamePlatform());
						params.put("gameBigType", bad.getGameBigType());
						params.put("userName", bad.getUserName());
						params.put("parentemployeecode", bad.getParentemployeecode());
//						params.put("", ReportWeeklyAgent.Enum_paytype.正常.value);不限于正常的
						
						List<ReportWeeklyMember> list = reportWeeklyMemberService.selectAll(params);
						
						String patchno = MD5Encoder.encode(wm.getLoginaccount()+wm.getGameplatform()+wm.getGametype()+lastMonday.getTime());
						if(list != null && list.size() > 0) {
							patchno = list.get(0).getPatchno();
						}
						
						wm.setPaytype(ReportWeeklyMember.Enum_paytype.补发.value);
						wm.setPatchno(patchno);//批次号，同一组数据一个批次号
						log.Error("补单：企业"+e.getEnterprisecode()+"已找到批次号数据："+patchno);
						
						if (bad.getStatus() == Enum_status.未发放.value) {
							wm.setAmount(formatDouble(bad.getRebatesCash()));
							wm.setActual(wm.getAmount());//默认实发等于应发额
							bad.setStatus(Enum_status.周结汇总.value);
							
						} else {
							wm.setSubtract(formatDouble(bad.getRebatesCash()));
						}
					} else {
						wm = weeklyMembers.get(weeklyMembersKey);
						if (bad.getStatus() == Enum_status.未发放.value) {
							wm.setBet(wm.getBet()+bad.getValidMoney());
							wm.setAmount(Arith.add(wm.getAmount(), formatDouble(bad.getRebatesCash()) ));
							wm.setActual(wm.getAmount());//默认实发等于应发额
							bad.setStatus(Enum_status.周结汇总.value);
							
						} else {
							wm.setSubtract(Arith.add(wm.getSubtract(), formatDouble(bad.getRebatesCash()) ));
						}
					}
					
					// 更新
					bad.setIsWeeklyMember(BettingAllDay.Enum_isWeeklyMember.已周结.value);
					this.bettingAllDayService.updateByPrimary(bad);
					
					weeklyMembers.put(weeklyMembersKey, wm);
					
					totalCount ++;
				}
				reportWeeklyMemberService.insertBatch(new ArrayList<ReportWeeklyMember>(weeklyMembers.values()));
				log.Info("补单：企业"+e.getEnterprisecode()+"执行会员周洗码量计算完成");
			}
			
			log.Info("=========================完成生成会员周结洗码报表（补单业务，确保周结中存在后续补发的数据）========================="+totalCount);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.Error("补单：会员周洗码报表生成失败", e);
			SmsUtilPublic.sendSmsByGet01(SmsUtilPublic.__NOTI_PHONENO, "补单：会员周洗码报表生成失败");
		}
		
	}
	
	private static double formatDouble(Double obj) {
		if(obj == null) {
			return 0;
		} else {
			return obj.doubleValue();
		}
	}
}
