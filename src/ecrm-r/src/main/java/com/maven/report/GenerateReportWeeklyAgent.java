package com.maven.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.maven.entity.ReportDailyAgent;
import com.maven.entity.ReportWeeklyAgent;
import com.maven.entity.ReportWeeklyMember;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.payment.th.frdemo.MD5Encoder;
import com.maven.service.ReportDailyAgentService;
import com.maven.service.ReportWeeklyAgentService;
import com.maven.util.DateUtils;
import com.maven.util.SmsUtilPublic;

@Component
@Lazy(false)
public class GenerateReportWeeklyAgent {
	
	private static LoggerManager log = LoggerManager.getLogger(
			GenerateReportWeeklyAgent.class.getName(), OutputManager.LOG_REPORTMEMBER);
	
	@Autowired
	private ReportDailyAgentService reportDailyAgentService;
	@Autowired
	private ReportWeeklyAgentService reportWeeklyAgentService;
	
	// 每周一的凌晨4点
	@Scheduled(cron = "0 40 18 ? * MON")//正式
//	@Scheduled(cron = "0 40 2 ? * MON")//正式
	public void report(){
		//业务步骤
		//1从玩家日洗码记录中统计代理记录 2记录分类为游戏平台,游戏类别 3获取代理洗码计算下线所有投注额 4发放金额记录账变
		//TODO 考虑是否增加多线程
		workDetails();//正式
		
		workDetails2();
	}
	
	public void workDetails(){
		try {
			
			log.Info("=========================开始生成代理周结洗码报表=========================");
			
			Date lastMonday = DateUtils.getLastMonday();
			Date lastSunday = DateUtils.getLastSunday();
			Date reporttime = new Date();
			
			Map<String, Object> ep = new HashMap<String, Object>();
			ep.put("startDate", lastMonday);
			ep.put("endDate", lastSunday);
			ep.put("isWeeklyAgent", ReportDailyAgent.Enum_isWeeklyAgent.未周结.value);
			
			List<ReportDailyAgent> listReportDailyAgent = this.reportDailyAgentService.selectAll(ep);
			log.Info("查询到本周日结代理数："+listReportDailyAgent.size());
			
			Map<String, ReportWeeklyAgent> insertMap = new HashMap<String, ReportWeeklyAgent>();
			List<String> codes = new ArrayList<String>() ;
			
			for (ReportDailyAgent reportDailyAgent : listReportDailyAgent) {
				String key = reportDailyAgent.getEmployeecode()+reportDailyAgent.getGameplatform()+reportDailyAgent.getGametype();
				
				Double subtract = reportDailyAgent.getStatus().equals(ReportDailyAgent.Enum_status.已发放.value) ? reportDailyAgent.getAmount().doubleValue() : 0;
				
				if(reportDailyAgent.getStatus().equals(ReportDailyAgent.Enum_status.未发放.value)) {//需要标记为已发放的
					codes.add(reportDailyAgent.getReportcode()+"");
				}
				
				// 代理的
				ReportWeeklyAgent item;
				if (insertMap.containsKey(key)) {
					item = insertMap.get(key);
					item.setBet(item.getBet() + reportDailyAgent.getBet().doubleValue());//累计投注量
					item.setAmount(item.getAmount() + reportDailyAgent.getAmount().doubleValue());//累计打码
					item.setActual(item.getAmount());//默认实发等于应发额
					
					// 累计已发放金额
					item.setSubtract(item.getSubtract() + subtract);/** 需要减去的已发放金额. */
					
				} else {
					item = new ReportWeeklyAgent();
					item.setAmount(reportDailyAgent.getAmount().doubleValue());
					item.setActual(item.getAmount());//默认实发等于应发额
					item.setBet(reportDailyAgent.getBet().doubleValue());
					item.setBrandcode(reportDailyAgent.getBrandcode());
					item.setEmployeecode(reportDailyAgent.getEmployeecode());
					item.setEnterprisecode(reportDailyAgent.getEnterprisecode());
					item.setStarttime(lastMonday);
					item.setEndtime(lastSunday);
					item.setGameplatform(reportDailyAgent.getGameplatform());
					item.setGametype(reportDailyAgent.getGametype());
					item.setLoginaccount(reportDailyAgent.getLoginaccount());
					item.setParentemployeecode(reportDailyAgent.getParentemployeecode());
					item.setRatio(reportDailyAgent.getRatio().doubleValue());
					item.setReporttime(reporttime);
					item.setStatus(ReportDailyAgent.Enum_status.未发放.value);
					item.setPaytype(ReportWeeklyAgent.Enum_paytype.正常.value);//
					item.setPatchno(MD5Encoder.encode(reportDailyAgent.getEmployeecode()+reportDailyAgent.getGameplatform()+reportDailyAgent.getGametype()+lastMonday.getTime()));//批次号，同一组数据一个批次号
					item.setSubtract(subtract);/** 需要减去的已发放金额. */
					
					insertMap.put(key, item);
				}
				
				//更新状态
				reportDailyAgent.setIsWeeklyAgent(ReportDailyAgent.Enum_isWeeklyAgent.已周结.value);
				reportDailyAgentService.update(reportDailyAgent);
				
			}
			// 再检查所有记录是否存在已全部发放的情况，如果是则该记录需要标记为已发放
			if(insertMap.size() > 0) {
				Iterator<ReportWeeklyAgent> values = insertMap.values().iterator(); 
				while(values.hasNext()) { 
					ReportWeeklyAgent item = (ReportWeeklyAgent) values.next(); 
					if(Math.abs(item.getAmount().doubleValue() - item.getSubtract()) < 0.5 ) {//视为打码总额与已发放金额相等
						item.setStatus(ReportDailyAgent.Enum_status.已发放.value);
					}
				} 
				List<ReportWeeklyAgent> insertList = new ArrayList<ReportWeeklyAgent>(insertMap.values());
				this.reportWeeklyAgentService.saveBatch(insertList);
			}
			
			//还没发放的全部改为周结汇总，表示由周结业务来发放
			if(codes.size() > 0) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("codes", codes.toArray());
				reportDailyAgentService.updateBatchStatus(params);
			}
			
			
			log.Info("=========================完成生成代理周结洗码报表=========================");
		} catch (Exception e) {
			e.printStackTrace();
			log.Error("代理周洗码报表生成失败", e);
			SmsUtilPublic.sendSmsByGet01(SmsUtilPublic.__NOTI_PHONENO, "代理周洗码报表生成失败");
		}
	}
	
	
	public void workDetails2(){
		try {
			
			int totalCount = 0;
			
			log.Info("=========================开始生成代理周结洗码报表（补单业务，确保周结中存在后续补发的数据）=========================");
			
			Date reporttime = new Date();
			
			Map<String, Object> ep = new HashMap<String, Object>();
			ep.put("isWeeklyAgent", ReportDailyAgent.Enum_isWeeklyAgent.未周结.value);
			
			List<ReportDailyAgent> listReportDailyAgent = this.reportDailyAgentService.selectAll(ep);
			log.Info("补单：查询到本周日结代理数："+listReportDailyAgent.size());
			
			Map<String, ReportWeeklyAgent> insertMap = new HashMap<String, ReportWeeklyAgent>();
			List<String> codes = new ArrayList<String>() ;
			
			Date lastMonday = null;
			Date lastSunday = null;
			
			for (ReportDailyAgent reportDailyAgent : listReportDailyAgent) {
				String key = reportDailyAgent.getEmployeecode()+reportDailyAgent.getGameplatform()+reportDailyAgent.getGametype();
				
				Double subtract = reportDailyAgent.getStatus().equals(ReportDailyAgent.Enum_status.已发放.value) ? reportDailyAgent.getAmount().doubleValue() : 0;
				
				if(reportDailyAgent.getStatus().equals(ReportDailyAgent.Enum_status.未发放.value)) {//需要标记为已发放的
					codes.add(reportDailyAgent.getReportcode()+"");
				}
				
				//查找数据所在的起始周一与周末日期
				lastMonday = DateUtils.getLastMonday(reportDailyAgent.getTime());
				lastSunday = DateUtils.getLastSunday(reportDailyAgent.getTime());
				
				
				// 代理的
				ReportWeeklyAgent item;
				if (insertMap.containsKey(key)) {
					item = insertMap.get(key);
					item.setBet(item.getBet() + reportDailyAgent.getBet().doubleValue());//累计投注量
					item.setAmount(item.getAmount() + reportDailyAgent.getAmount().doubleValue());//累计打码
					item.setActual(item.getAmount());//默认实发等于应发额
					
					// 累计已发放金额
					item.setSubtract(item.getSubtract() + subtract);/** 需要减去的已发放金额. */
					
				} else {
					item = new ReportWeeklyAgent();
					item.setAmount(reportDailyAgent.getAmount().doubleValue());
					item.setActual(item.getAmount());//默认实发等于应发额
					item.setBet(reportDailyAgent.getBet().doubleValue());
					item.setBrandcode(reportDailyAgent.getBrandcode());
					item.setEmployeecode(reportDailyAgent.getEmployeecode());
					item.setEnterprisecode(reportDailyAgent.getEnterprisecode());
					item.setStarttime(lastMonday);
					item.setEndtime(lastSunday);
					item.setGameplatform(reportDailyAgent.getGameplatform());
					item.setGametype(reportDailyAgent.getGametype());
					item.setLoginaccount(reportDailyAgent.getLoginaccount());
					item.setParentemployeecode(reportDailyAgent.getParentemployeecode());
					item.setRatio(reportDailyAgent.getRatio().doubleValue());
					item.setReporttime(reporttime);
					item.setStatus(ReportDailyAgent.Enum_status.未发放.value);
					item.setPaytype(ReportWeeklyAgent.Enum_paytype.补发.value);//
					item.setSubtract(subtract);/** 需要减去的已发放金额. */
					
					
					//查找同一批次的正常周结数据，取出批次号
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("brandcode", reportDailyAgent.getBrandcode());
					params.put("employeecode", reportDailyAgent.getEmployeecode());
					params.put("enterprisecode", reportDailyAgent.getEnterprisecode());
					params.put("starttime", lastMonday);
					params.put("endtime", lastSunday);
					params.put("gameplatform", reportDailyAgent.getGameplatform());
					params.put("gametype", reportDailyAgent.getGametype());
					params.put("loginaccount", reportDailyAgent.getLoginaccount());
					params.put("parentemployeecode", reportDailyAgent.getParentemployeecode());
//					params.put("", ReportWeeklyAgent.Enum_paytype.正常.value);不限于正常的
					
					List<ReportWeeklyAgent> list = reportWeeklyAgentService.selectAll(params);
					
					String patchno = MD5Encoder.encode(reportDailyAgent.getEmployeecode()+reportDailyAgent.getGameplatform()+reportDailyAgent.getGametype()+lastMonday.getTime());
					if(list != null && list.size() > 0) {
						patchno = list.get(0).getPatchno();
					}
					
					item.setPatchno(patchno);//批次号，同一组数据一个批次号
					log.Error("补单：员工"+reportDailyAgent.getEmployeecode()+"已找到批次号数据："+patchno);
					
					insertMap.put(key, item);
				}
				
				//更新状态
				reportDailyAgent.setIsWeeklyAgent(ReportDailyAgent.Enum_isWeeklyAgent.已周结.value);
				reportDailyAgentService.update(reportDailyAgent);
				
				totalCount ++;
				
			}
			// 再检查所有记录是否存在已全部发放的情况，如果是则该记录需要标记为已发放
			if(insertMap.size() > 0) {
				Iterator<ReportWeeklyAgent> values = insertMap.values().iterator(); 
				while(values.hasNext()) { 
					ReportWeeklyAgent item = (ReportWeeklyAgent) values.next(); 
					if(Math.abs(item.getAmount().doubleValue() - item.getSubtract()) < 0.5 ) {//视为打码总额与已发放金额相等
						item.setStatus(ReportDailyAgent.Enum_status.已发放.value);
					}
				} 
				List<ReportWeeklyAgent> insertList = new ArrayList<ReportWeeklyAgent>(insertMap.values());
				this.reportWeeklyAgentService.saveBatch(insertList);
			}
			
			//还没发放的全部改为周结汇总，表示由周结业务来发放
			if(codes.size() > 0) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("codes", codes.toArray());
				reportDailyAgentService.updateBatchStatus(params);
			}
			
			
			log.Info("=========================完成生成代理周结洗码报表（补单业务，确保周结中存在后续补发的数据）========================="+totalCount);
		} catch (Exception e) {
			e.printStackTrace();
			SmsUtilPublic.sendSmsByGet01(SmsUtilPublic.__NOTI_PHONENO, "补单：代理周洗码报表生成失败");
		}
	}
}
