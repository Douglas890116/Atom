package com.maven.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.annotation.OperationLog;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.EmployeeMessage;
import com.maven.entity.EmployeeMessage.Enum_messagetype;
import com.maven.entity.EmployeeMessageText;
import com.maven.entity.EnterpriseEmployee;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.EmployeeMessageService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.util.AttrCheckout;

@RequestMapping("/Message")
@Controller
public class EmployeeMessageController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			EmployeeMessageController.class.getName(), OutputManager.LOG_EMPLOYEEMESSAGE);
	
	@Autowired
	private EmployeeMessageService employeeMessageService;
	
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	
	@RequestMapping("/SystemMessage")
	public String systemMessage(){
		return "/message/sysmessage";
	}
	@RequestMapping("/ManagerMessage")
	public String managerMessage(){
		return "/message/managermessage";
	}
	@RequestMapping("/AgentMessage")
	public String agentMessage(){
		return "/message/agentmessage";
	}
	@RequestMapping("/AgentChoiceUser")
	public String choiceUser(){
		return "/message/agentchoiceuser";
	}
	@RequestMapping("/AgentMSend")
	public String systemMessageSend(){
		return "/message/agentmessagesend";
	}
	@RequestMapping("/ManagerSend")
	public String managetMessageSend(){
		return "/message/managermessagesend";
	}
	@RequestMapping("/ManagerChoiceUser")
	public String managerChoiceUser(){
		return "/message/managerchoiceuser";
	}
	@RequestMapping("/ReplyMessage")
	public String replyMessage(HttpServletRequest request,HttpSession session,Model model){
		String sign = request.getParameter("sendemployeecode");
		String[] account = sign.split("\\|");
		if(StringUtils.isBlank(sign)||account.length!=4)
			return Constant.PAGE_PARAMSERROR;
		if(!super.decodeSign(sign, session.getId()))
			return Constant.PAGE_DECODEREFUSE;
		model.addAttribute("acceptemployeecode", sign);
		model.addAttribute("acceptaccount", account[1]);
		return "/message/messagereply";
	}
	
	@RequestMapping("/SystemData")
	@ResponseBody
	public Map<String,Object> sysData(HttpServletRequest request,HttpSession session){
		try {
			Map<String,Object> object = super.getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			object.put("enterprisecode", ee.getEnterprisecode());
			object.put("employeecode", ee.getEmployeecode());
			object.put("messagetype",EmployeeMessage.Enum_messagetype.系统消息.value);
			return pakegeMessageResult(session, object);
		} catch (Exception e) {
			log.Error(e.getMessage(),e);
			return super.formatPagaMap(null, 0);
		}
	}
	
	
	@RequestMapping("/ManagerData")
	@ResponseBody
	public Map<String,Object> managerData(HttpServletRequest request,HttpSession session){
		try {
			Map<String,Object> object = super.getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			
			/*********非超级管理员时只能查询本团队内的数据************/
			if(!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode()))
				super.dataLimits(ee, object,session);
			
			return pakegeMessageResult(session, object);
		} catch (Exception e) {
			log.Error(e.getMessage(),e);
			return super.formatPagaMap(null, 0);
		}
	}
	
	@RequestMapping("/AgentData2")
	@ResponseBody
	public Map<String,Object> agentData2(HttpServletRequest request,HttpSession session){
		try {
			Map<String,Object> object = super.getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
//			object.put("enterprisecode", ee.getEnterprisecode());
			object.put("acceptemployeecode", ee.getEmployeecode());
// 			object.put("messagetype",EmployeeMessage.Enum_messagetype.代理消息.value);
//			object.put("readstatus", EmployeeMessage.Enum_readstatus.未阅读.value);
			return pakegeMessageResult(session, object);
		} catch (Exception e) {
			log.Error(e.getMessage(),e);
			return super.formatPagaMap(null, 0);
		}
	}
	
	@RequestMapping("/AgentData")
	@ResponseBody
	public Map<String,Object> agentData(HttpServletRequest request,HttpSession session){
		try {
			Map<String,Object> object = super.getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
//			object.put("enterprisecode", ee.getEnterprisecode());
			object.put("acceptemployeecode", ee.getEmployeecode());
// 			object.put("messagetype",EmployeeMessage.Enum_messagetype.代理消息.value);
			object.put("readstatus", EmployeeMessage.Enum_readstatus.未阅读.value);
			return pakegeMessageResult(session, object);
		} catch (Exception e) {
			log.Error(e.getMessage(),e);
			return super.formatPagaMap(null, 0);
		}
	}
	
	@RequestMapping("/ReplyMessageSave")
	@ResponseBody
	public Map<String,Object> replyMessageSave(HttpServletRequest request,HttpSession session){
		try {
			String sign = request.getParameter("sign");
			String content = request.getParameter("content");
			if(!super.decodeSign(sign, session.getId()))
				return packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			String[] infos = sign.split("_")[1].split("\\|");
			if(infos.length!=4) 
				return packJSON(Constant.BooleanByte.NO, "参数错误，禁止操作");
			
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);

			EmployeeMessageText text = new EmployeeMessageText();
			text.setContent(content);
			text.setDatastatus(String.valueOf(Constant.Enum_DataStatus.正常.value));
			
			EmployeeMessage message = new EmployeeMessage();
			message.setEnterprisecode(ee.getEnterprisecode());
			message.setBrandcode(ee.getBrandcode());
			message.setSendemployeecode(ee.getEmployeecode());
			message.setSendemployeeaccount(ee.getLoginaccount());
			message.setAcceptemployeecode(infos[0]);
			message.setAcceptaccount(infos[1]);
			message.setMessagetype(String.valueOf(infos[2]));
			message.setReplaycode(Integer.parseInt(infos[3]));
			message.setReadstatus(String.valueOf(EmployeeMessage.Enum_readstatus.未阅读.value));
			
			List<EmployeeMessage> messages = new ArrayList<EmployeeMessage>();
			messages.add(message);
			employeeMessageService.tc_sendMessage(messages, text);
			return super.packJSON(Constant.BooleanByte.YES, "消息已发送");
		} catch (Exception e) {
			log.Error(e.getMessage(),e);
			return super.packJSON(Constant.BooleanByte.NO, "发送失败，请稍后尝试");
		}
	}
	
	@RequestMapping("/SendMessage")
	@ResponseBody
	public Map<String,Object> saveMessage(HttpServletRequest request,HttpSession session){
		try {
			return sendMessage(request, session,EmployeeMessage.Enum_messagetype.代理消息);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "发送失败，请稍后尝试");
		}
	}
	
	@RequestMapping("/SendSysMessage")
	@ResponseBody
	public Map<String,Object> saveSysMessage(HttpServletRequest request,HttpSession session){
		try {
			sendMessage(request, session,EmployeeMessage.Enum_messagetype.系统消息);
			return super.packJSON(Constant.BooleanByte.YES, "消息已发送");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "发送失败，请稍后尝试");
		}
	}
	
	private Map<String, Object> sendMessage(HttpServletRequest request, HttpSession session,Enum_messagetype messagetype) throws Exception {
		String[] accepter  = request.getParameterValues("accepter");
		String content = request.getParameter("content");
		String messageType = request.getParameter("messagetype");
		List<EmployeeMessage> messages = new ArrayList<EmployeeMessage>();
		EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
		if(accepter.length==0) return packJSON(Constant.BooleanByte.NO, "请选择收信人");
		if(StringUtils.isBlank(content)) return packJSON(Constant.BooleanByte.NO, "请填写消息内容");
		
		EmployeeMessageText text = new EmployeeMessageText();
		text.setContent(content);
		text.setDatastatus(String.valueOf(Constant.Enum_DataStatus.正常.value));
		for (String s : accepter) {
			if(!super.decodeSign(s, session.getId()))
				return packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			String[] infos = s.split("_")[1].split("\\|");
			if(infos.length!=2) 
				return packJSON(Constant.BooleanByte.NO, "参数错误，禁止操作");
			EmployeeMessage message = new EmployeeMessage();
			message.setEnterprisecode(ee.getEnterprisecode());
			message.setBrandcode(ee.getBrandcode());
			message.setSendemployeecode(ee.getEmployeecode());
			message.setSendemployeeaccount(ee.getLoginaccount());
			message.setAcceptemployeecode(infos[0]);
			message.setAcceptaccount(infos[1]);
			message.setMessagetype(StringUtils.isNotBlank(messageType) ? messageType : String.valueOf(messagetype.value));
			message.setReadstatus(String.valueOf(EmployeeMessage.Enum_readstatus.未阅读.value));
			messages.add(message);
		}
		employeeMessageService.tc_sendMessage(messages, text);
		return super.packJSON(Constant.BooleanByte.YES, "消息已发送");
	}
	
	
	@RequestMapping("/AgentSearchAccount")
	@ResponseBody
	public Map<String,Object> agentSearchAccount(HttpServletRequest request , HttpSession session){
		try {
			Map<String,Object> object = super.getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			object.put("selfecode", ee.getEmployeecode());
			object.put("superiorecode",ee.getParentemployeecode());
			List<EnterpriseEmployee> list = enterpriseEmployeeService.takeDerictly(object);
			for (EnterpriseEmployee e : list) 
				e.setSign(e.getEmployeecode()+"|"+e.getLoginaccount());
			super.encryptSign(list, session.getId(), "sign");
			list = AttrCheckout.checkout(list, true, new String[]{"sign","employeecode","loginaccount","displayalias","employeetypename","onlinestatus","lastlogintime"});
			int count = enterpriseEmployeeService.takeDerictlyCount(object);
			return super.formatPagaMap(list, count);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return null;
	}
	
	@RequestMapping("/ManagerSearchAccount")
	@ResponseBody
	public Map<String,Object> managerSearchAccount(HttpServletRequest request , HttpSession session){
		try {
			Map<String,Object> object = super.getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			super.dataLimits(ee, object,session);
			List<EnterpriseEmployee> list = enterpriseEmployeeService.findEmployee(object);
			for (EnterpriseEmployee e : list) 
				e.setSign(e.getEmployeecode()+"|"+e.getLoginaccount());
			super.encryptSign(list, session.getId(), "sign");
			list = AttrCheckout.checkout(list, true, new String[]{"sign","employeecode","loginaccount","displayalias","employeetypename","onlinestatus","lastlogintime"});
			int count = enterpriseEmployeeService.findEmployeeCount(object);
			return super.formatPagaMap(list, count);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return null;
	}
	
	@RequestMapping("/DelMessage")
	@OperationLog(OparetionDescription.MESSAGE_MANAGER_DELETE)
	@ResponseBody
	public Map<String,Object> delMessage(HttpServletRequest request,HttpSession session){
		try {
			String sign = request.getParameter("sign");
			if(!super.decodeSign(sign, session.getId()))
				return packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			String[] infos = sign.split("_")[1].split("\\|");
			if(StringUtils.isBlank(sign)||infos.length!=4)
				return packJSON(Constant.BooleanByte.NO, "参数错误，禁止操作");
			employeeMessageService.tc_delMessage(Integer.parseInt(infos[3]));
			return super.packJSON(Constant.BooleanByte.YES, "消息已删除");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "消息失败，请稍后重试");
		}
	}
	
	@RequestMapping("/UpdataStatus")
	@ResponseBody
	public Map<String,Object> updateMStatus(HttpServletRequest request,HttpSession session){
		try {
			String sign = request.getParameter("sign");
			if(!super.decodeSign(sign, session.getId()))
				return packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			String[] infos = sign.split("_")[1].split("\\|");
			if(StringUtils.isBlank(sign)||infos.length!=4)
				return packJSON(Constant.BooleanByte.NO, "参数错误，禁止操作");
			employeeMessageService.tc_updateMStatus(Integer.parseInt(infos[3]));
			return super.packJSON(Constant.BooleanByte.YES, "已读");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "消息失败，请稍后重试");
		}
	}
	
	private Map<String, Object> pakegeMessageResult(HttpSession session, Map<String, Object> object) throws Exception {
		List<EmployeeMessage> list =  employeeMessageService.selectAll(object);
		for (EmployeeMessage e : list) 
			e.setSign(e.getSendemployeecode()+"|"+e.getSendemployeeaccount()+"|"+e.getMessagetype()+"|"+e.getMessagecode());
		super.encryptSign(list, session.getId(), "sign");
		int count = employeeMessageService.selectAllCount(object);
		return super.formatPagaMap(list, count);
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}

