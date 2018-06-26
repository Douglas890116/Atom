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

import com.hy.pull.common.util.DateUtil;
import com.hy.pull.common.util.api.GameAPI;
import com.maven.cache.SystemCache;
import com.maven.constant.Constant;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EmployeeApiAccoutPasswordJob;
import com.maven.entity.EmployeeMessage;
import com.maven.entity.EmployeeMessageText;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeLevel;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.EnterpriseGame;
import com.maven.entity.Game;
import com.maven.entity.UserLogs;
import com.maven.game.APIServiceUtil;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.EmployeeApiAccoutPasswordJobService;
import com.maven.service.EmployeeApiAccoutService;
import com.maven.service.EmployeeMessageService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.UserLogsService;
import com.maven.util.SmsUtilPublic;

import net.sf.json.JSONObject;

/**
 * 每月10日提醒所有后台账号进行密码更新
 * @author Administrator
 *
 */
@Component
@Lazy(false)
public class DataUpdatePwdJob {
	
	private static List<String> ALL_AGENTTYPE = new ArrayList<String>(){{
		this.add(EnterpriseEmployeeType.Type.代理.value);
		this.add(EnterpriseEmployeeType.Type.信用代理.value);
		this.add(EnterpriseEmployeeType.Type.企业号.value);
		this.add(EnterpriseEmployeeType.Type.员工.value);
	}};
	
	private static LoggerManager log = LoggerManager.getLogger(DataUpdatePwdJob.class.getName(),OutputManager.LOG_DAY_BUT_BONUS);
	
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private UserLogsService userLogsService;
	@Autowired
	private EmployeeMessageService employeeMessageService;
	
	@Scheduled(cron = "0 15 10 15 * ?")//0 15 10 15 * ? 每月15号上午10点15分触发 
	public void work() {
		
		log.Error("#########################开始每月一次的密码修改提醒#########################");
		
		try {
			Map<String, Object> paramObj = new HashMap<String, Object>();
			paramObj.put("employeestatus", 1);
			paramObj.put("datastatus", "1");
			paramObj.put("employeetypecodes", ALL_AGENTTYPE);
			List<EnterpriseEmployee> employees = enterpriseEmployeeService.selectAll(paramObj);
			
			String content = DateUtil.parse(new Date(), "yyyy/MM/dd")+"，来自系统的提醒：尊敬的用户您好，建议您每月定期修改一次密码，确保账号安全！";
			
			EnterpriseEmployee sendEmployee = enterpriseEmployeeService.takeEmployeeByCode("E0000000");
			List<EmployeeMessage> messages = new ArrayList<EmployeeMessage>();
			
			EmployeeMessageText text = new EmployeeMessageText();
			text.setContent(content);
			text.setDatastatus(String.valueOf(Constant.Enum_DataStatus.正常.value));
			
			for (EnterpriseEmployee acceptEmployee : employees) {
				
				EmployeeMessage message = new EmployeeMessage();
				message.setEnterprisecode(sendEmployee.getEnterprisecode());
				message.setBrandcode(sendEmployee.getBrandcode());
				message.setSendemployeecode(sendEmployee.getEmployeecode());
				message.setSendemployeeaccount(sendEmployee.getLoginaccount());
				message.setAcceptemployeecode(acceptEmployee.getEmployeecode());//接受会员编号
				message.setAcceptaccount(acceptEmployee.getLoginaccount());//接受会员账号
				message.setMessagetype(String.valueOf(EmployeeMessage.Enum_messagetype.系统消息.value));
				message.setReadstatus(String.valueOf(EmployeeMessage.Enum_readstatus.未阅读.value));
				messages.add(message);
			}
			
			if(messages.size() > 0) {
				employeeMessageService.tc_sendMessage(messages, text);
			}
			
			log.Error("#########################完成每月一次的密码修改提醒#########################"+messages.size()+"人");
			
		} catch (Exception e) {
			e.printStackTrace();
			SmsUtilPublic.sendSmsByGet01(SmsUtilPublic.__NOTI_PHONENO, "每月一次的密码修改提醒");
		}
		
	}
	
}
