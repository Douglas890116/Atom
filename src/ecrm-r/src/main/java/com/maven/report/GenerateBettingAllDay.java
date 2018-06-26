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

import com.hy.pull.common.util.DateUtil;
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
import com.maven.service.BettingAllDayService2;
import com.maven.service.BettingAllGameWinloseDetailService;
import com.maven.service.EmployeeGamecataloyBonusService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseService;
import com.maven.service.ReportDailyAgentService;
import com.maven.util.SmsUtilPublic;

@Component
@Lazy(false)
public class GenerateBettingAllDay {
	
	private static LoggerManager log = LoggerManager.getLogger(
			GenerateBettingAllDay.class.getName(), OutputManager.LOG_REPORTMEMBER);
	
	
//	@Autowired
//	private EnterpriseService enterpriseService;
//	@Autowired
//	private EnterpriseEmployeeService enterpriseEmployeeService;
	
	@Autowired
	private BettingAllDayService bettingAllDayService;
	@Autowired
	private BettingAllDayService2 bettingAllDay2Service;//先存储到这个表，过渡测试。如果没有问题，再存储到BettingAllDay表

	
	
	@Scheduled(cron = "0 2 11 * * ?")//正式
//	@Scheduled(cron = "0 40 16 * * ?")//测试
	public void report(){
		
		workDetails();//正式
		
	}
	
	public void workDetails(){
		
		try {
			
			String betdate = DateUtil.parse(DateUtil.add(new Date(), Calendar.DATE, -1), "yyyyMMdd") ;//投注时间为昨日的
			/**************生成批次号：
			 * 1、所有生成的BettingAllDay记录要记录此批次号
			 * 2、所有对应的原始记录也记录此批次号，便于回滚事务
			 * 3、99开头的表示正常的汇总数据，98开头的表示补单的汇总数据
			 * ***************/
			String patchno = "99" +System.currentTimeMillis() + "";
			
			
			log.Info("=========================开始生成汇总投注明细数据到日汇总表=========================");
			
			//先存储到这个表，过渡测试。如果没有问题，再存储到BettingAllDay表
			//bettingAllDay2Service.updateDoPlan(betdate, patchno);
			
			bettingAllDayService.updateDoPlan(betdate, patchno);
			
			log.Info("=========================完成生成汇总投注明细数据到日汇总表=========================");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.Error("生成汇总投注明细数据到日汇总表", e);
			SmsUtilPublic.sendSmsByGet01(SmsUtilPublic.__NOTI_PHONENO, "生成汇总投注明细数据到日汇总表");
		}
	}

	
}
