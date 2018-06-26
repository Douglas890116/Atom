package com.maven.report.activity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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

import com.hy.pull.common.util.api.GameAPI;
import com.maven.cache.SystemCache;
import com.maven.entity.DataHandle;
import com.maven.entity.DataHandleMaintenance;
import com.maven.entity.DataHandleMaintenance.Enum_flag;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EmployeeApiAccoutPasswordJob;
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
import com.maven.service.DataHandleMaintenanceService;
import com.maven.service.DataHandleService;
import com.maven.service.EmployeeApiAccoutPasswordJobService;
import com.maven.service.EmployeeApiAccoutService;
import com.maven.service.EnterpriseGameService;
import com.maven.service.EnterpriseOperatingBrandGameService;
import com.maven.service.EnterpriseOperatingBrandService;
import com.maven.service.GameService;
import com.maven.service.UserLogsService;
import com.maven.util.MailUtil;
import com.maven.util.SmsUtilPublic;

import net.sf.json.JSONObject;

/**
 * 定时检测拉数据任务器是否中断
 * @author Administrator
 *
 */
@Component
@Lazy(false)
public class DataGameCheckJob2 {
	
	private static LoggerManager log = LoggerManager.getLogger(DataGameCheckJob2.class.getName(),OutputManager.LOG_DAY_BUT_BONUS);
	
	@Autowired
	private UserLogsService userLogsService;
	@Autowired
	private DataHandleMaintenanceService dataHandleMaintenanceService;
	@Autowired
	private DataHandleService dataHandleService;
	
	public static void main(String[] args) {
		
		
	}
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
	
	@Scheduled(fixedDelay = (1000 * 60 * 10) )//5分钟检查一次
	public void work() {
		
		Date currendate = new Date();
		
		StringBuffer buffer = new StringBuffer();
		
		boolean issend = false;//有异常的就要发送邮件
		
		log.Error("#########################开始定时检测拉数据任务器是否中断#########################");
		buffer.append("#########################开始定时检测拉数据任务器是否中断#########################").append("\r\n");
			
		try {
			List<DataHandle> betrecords = dataHandleService.selectBetRecord(new HashMap<String, Object>());
			for (DataHandle dataHandle : betrecords) {
				String gametype = dataHandle.getGametype();
				if(gametype == null || gametype.equals(Enum_GameType.GGPoker.gametype) ) {
					continue;
				}
				
				if(dataHandle.getLasttime().length() == 14) {
					Date date = sdf.parse(dataHandle.getLasttime());
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.MINUTE, +30);
					
					if(calendar.getTime().getTime() < currendate.getTime()) {//超过30分钟
						DataHandleMaintenance data = dataHandleMaintenanceService.selectByPrimaryKey(dataHandle.getGametype());
						
						//排除维护的情况
						if(data != null && data.getFlag().intValue() == Enum_flag.正常.value.intValue()) {
							issend = true;
							buffer.append("===================超时任务：".concat(dataHandle.getHandlecode())).append("\r\n");
							SmsUtilPublic.sendSmsByGet01(SmsUtilPublic.__NOTI_PHONENO, dataHandle.getHandlecode());
						}
						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		log.Error("#########################完成定时检测拉数据任务器是否中断#########################");
		buffer.append("#########################完成定时检测拉数据任务器是否中断#########################").append("\r\n");
		
		if(issend) {
			MailUtil.send( "拉数据任务器中断通知", "", buffer.toString());
		}
		
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
