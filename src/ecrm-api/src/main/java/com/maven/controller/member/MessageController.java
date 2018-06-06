package com.maven.controller.member;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.entity.EmployeeMessage;
import com.maven.entity.EmployeeMessage.Enum_readstatus;
import com.maven.entity.EnterpriseEmployee;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.EmployeeMessageService;
import com.maven.service.EnterpriseEmployeeService;

@Controller
@RequestMapping("/UserMessage")
public class MessageController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			MessageController.class.getName(), OutputManager.LOG_USER_MESSAGE);
	
	@Autowired
	private EmployeeMessageService employeeMessageService;
	
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	
	/**
	 * 获取站内消息列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/SysMessage",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String sysMessage(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
			object.put("enterprisecode", ee.getEnterprisecode());
			object.put("employeecode", ee.getEmployeecode());
			object.put("messagetype",EmployeeMessage.Enum_messagetype.系统消息.value);
			
			object.put("field", "readstatus");
			object.put("direction", "asc");
			
			List<EmployeeMessage> list =  employeeMessageService.selectAll(object);
			int count = employeeMessageService.selectAllCount(object);
			return Enum_MSG.成功.message(formatPagaMap(list, count));
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch(LogicTransactionRollBackException e){
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	/**
	 * 标记站内消息为已读状态
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updateSysMessage",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updateSysMessage(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
			
			String messagecode = object.get("messagecode").toString();
			
			int count = employeeMessageService.tc_updateMStatus(Integer.valueOf(messagecode));
			if(count > 0) {
				return Enum_MSG.成功.message("标记成功");
			} else {
				return Enum_MSG.失败.message("标记失败");
			}
			
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch(LogicTransactionRollBackException e){
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	@RequestMapping(value="/AgentMessage",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String agentMessage(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
			object.put("enterprisecode", ee.getEnterprisecode());
			object.put("employeecode", ee.getEmployeecode());
			object.put("messagetype",EmployeeMessage.Enum_messagetype.代理消息.value);
			List<EmployeeMessage> list =  employeeMessageService.selectAll(object);
			int count = employeeMessageService.selectAllCount(object);
			return Enum_MSG.成功.message(formatPagaMap(list, count));
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch(LogicTransactionRollBackException e){
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}

	@RequestMapping(value="/MessageCount",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String messageCount(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
			object.put("enterprisecode", ee.getEnterprisecode());
			object.put("employeecode", ee.getEmployeecode());
			object.put("readstatus", Enum_readstatus.未阅读.value);
			int count = employeeMessageService.selectAllCount(object);
			return Enum_MSG.成功.message(String.valueOf(count));
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch(LogicTransactionRollBackException e){
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}

}
