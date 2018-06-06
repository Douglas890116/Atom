package com.maven.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.annotation.OperationLog;
import com.maven.constant.Constant;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.UserLogs;
import com.maven.entity.WorkingFlowConfiguration;
import com.maven.entity.WorkingFlowObject;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.UserLogsService;
import com.maven.service.WorkingFlowConfigurationService;
import com.maven.service.WorkingFlowObjectService;

@Controller
@RequestMapping("/workingFlow")
public class WorkingFlowConfigurationControll extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			WorkingFlowConfigurationControll.class.getName(), OutputManager.LOG_WORKINGFLOWCONFIGURATION);
	
	@Autowired
	private WorkingFlowConfigurationService workingFlowConfigurationService;
	@Autowired
	private WorkingFlowObjectService workingFlowObjectService;
	@Autowired
	private UserLogsService userLogsService;
	/**
	 * 存款设置首页
	 * @return
	 */
	@RequestMapping("/depositIndex")
	public String depositWorkingFlowConfiguration(){
		return "/workingflow/depositIndex";
	}
	
	/**
	 * 取款设置首页
	 * @return
	 */
	@RequestMapping("/takeIndex")
	public String takeWorkingFlowConfiguration(){
		return "/workingflow/takeIndex";
	}
	
	/**
	 * 存款审核流程新增页面
	 * @return
	 */
	@RequestMapping("/depositWorkingFlowConfigurationAdd")
	public String depositWorkingFlowConfigurationAdd(){
		return "/workingflow/depositWorkingFlowConfigurationAdd";
	}
	
	/**
	 * 取款审核流程新增页面
	 * @return
	 */
	@RequestMapping("/takeWorkingFlowConfigurationAdd")
	public String takeWorkingFlowConfigurationAdd(){
		return "/workingflow/takeWorkingFlowConfigurationAdd";
	}
	
	/**
	 * 跳转到流程审批人添加页面
	 */
	@RequestMapping(value={"/addDepositWorkingFlowApproveEmployee","/addTakeWorkingFlowApproveEmployee"})
	public String addDepositWorkingFlowApproveEmployee(Model model,HttpServletRequest request){
		model.addAttribute("flowcode",request.getParameter("flowcode"));
		return "/workingflow/addDepositWorkingFlowApproveEmployee";
	}
	
	/**
	 * 查询需要修改的存款审核流程
	 * @return
	 */
	@RequestMapping("/depositWorkingFlowConfigurationUpdate")
	public String depositWorkingFlowConfigurationUpdate(HttpSession session,HttpServletRequest request,Model model){
		try {
			WorkingFlowConfiguration _object = getRequestParamters(request,WorkingFlowConfiguration.class);
			//从session里面取出品牌编码
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			_object.setEnterprisecode(employee.getEnterprisecode());
			_object.setFlowtype((short)WorkingFlowConfiguration.Enum_flowtype.存款工作流.value);
			//调用存款工作流查询接口
			WorkingFlowConfiguration _wcf = workingFlowConfigurationService.selectFirst(_object);
			model.addAttribute("WorkingFlowConfiguration", _wcf);
			return "/workingflow/depositWorkingFlowConfigurationUpdate";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	/**
	 * 取款工作流配置查询
	 * @return
	 */
	@RequestMapping("/queryTakeWorkingFlowConfiguration")
	@ResponseBody
	public Map<String,Object> queryTakeWorkingFlowConfiguration(HttpServletRequest request,HttpSession session){
		try {
			Map<String,Object> map = getRequestParamters(request);
			//从session里面取出品牌编码
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			map.put("enterprisecode", employee.getEnterprisecode());
			//调用取款工作流查询接口
			List<WorkingFlowConfiguration> list = workingFlowConfigurationService.queryWithdrawlWorkingFlow(map);
			for (WorkingFlowConfiguration w : list) {
				w.setSign(super.encryptString(w.getFlowcode(), session.getId()));
			}
			int count = workingFlowConfigurationService.queryWithdrawlWorkingFlowCount(map);
			return super.formatPagaMap(list,count);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	/**
	 * 存款工作流配置查询
	 * @return
	 */
	@RequestMapping("/queryDepositWorkingFlowConfiguration")
	@ResponseBody
	public Map<String,Object> queryDepositWorkingFlowConfiguration(HttpServletRequest request,HttpSession session){
		try {
			Map<String,Object> map = getRequestParamters(request);
			//从session里面取出企业编码
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			map.put("enterprisecode", employee.getEnterprisecode());
			//调用存款工作流查询接口
			List<WorkingFlowConfiguration> list = workingFlowConfigurationService.queryRechargeWorkingFlow(map);
			for (WorkingFlowConfiguration w : list) {
				w.setSign(super.encryptString(w.getFlowcode(), session.getId()));
			}
			int count = workingFlowConfigurationService.queryRechargeWorkingFlowCount(map);
			return super.formatPagaMap(list,count);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	/**
	 * 存款工作流配置添加
	 * @param request
	 */
	@RequestMapping("/saveDepositWorkingFlowConfiguration")
	@ResponseBody
	public  Map<String,Object>  saveDepositWorkingFlowConfiguration(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			WorkingFlowConfiguration object = getRequestParamters(request,WorkingFlowConfiguration.class);
			object.setEnterprisecode(ee.getEnterprisecode());
			//调用存款工作流保存方法
			workingFlowConfigurationService.addRechargeWorkingFlow(object);
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.流程管理业务, "保存存款工作流配置:"+object.getFlowname(), ee.getLoginaccount(), null));
			
			return super.packJSON(Constant.BooleanByte.YES, "添加成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "添加失败,请稍后尝试");
		}
	}
	
	/**
	 * 存款工作流配置修改
	 * @param request
	 */
	@RequestMapping("/updateDepositWorkingFlowConfiguration")
	@OperationLog(OparetionDescription.DEPOSIT_TAKE_WORKING_FLOW_CONFIGURATION_UPDATE)
	@ResponseBody
	public Map<String,Object> updateDepositWorkingFlowConfiguration(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			WorkingFlowConfiguration object = getRequestParamters(request,WorkingFlowConfiguration.class);
			object.setEnterprisecode(ee.getEnterprisecode());
			////调用存款工作流修改方法
			workingFlowConfigurationService.editRechargeWorkingFlow(object);
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.流程管理业务, "更改存款工作流配置:"+object.getFlowcode(), ee.getLoginaccount(), null));
			
			return super.packJSON(Constant.BooleanByte.YES, "修改成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "修改失败,请稍后尝试");
		}
	}
	
	/**
	 * 存款工作流配置删除
	 * @param request
	 */
	@RequestMapping("/depositWorkingFlowConfigurationDelete")
	@OperationLog(OparetionDescription.DEPOSIT_TAKE_WORKING_FLOW_CONFIGURATION_DELETE)
	@ResponseBody
	public Map<String,Object> depositWorkingFlowConfigurationDelete(HttpServletRequest request,HttpSession session){
		try {
			String sign = request.getParameter("sign");
			if(super.decodeSign(sign, session.getId())){
				EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
				workingFlowConfigurationService.delWorkingFlow(sign.split("_")[1],ee.getEnterprisecode());
				
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.流程管理业务, "删除存款工作流配置:"+sign.split("_")[1], ee.getLoginaccount(), null));
				
				return super.packJSON(Constant.BooleanByte.YES, "删除成功");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	/**
	 * 取款工作流配置保存
	 * @param request
	 */
	@RequestMapping("/saveTakeWorkingFlowConfiguration")
	@ResponseBody
	public Map<String,Object> saveTakeWorkingFlowConfiguration(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			WorkingFlowConfiguration object = getRequestParamters(request,WorkingFlowConfiguration.class);
			object.setEnterprisecode(ee.getEnterprisecode());
			//调用取款工作流保存方法
			workingFlowConfigurationService.addWithdrawlWorkingFlow(object);
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.流程管理业务, "保存取款工作流配置:"+object.getFlowname(), ee.getLoginaccount(), null));
			
			
			return super.packJSON(Constant.BooleanByte.YES, "修改成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "修改失败,请稍后尝试");
		}
	}
	
	/**
	 * 查询需要修改的取款审核流程
	 * @return
	 */
	@RequestMapping("/takeWorkingFlowConfigurationUpdate")
	public String takeWorkingFlowConfigurationUpdate(HttpSession session,HttpServletRequest request,Model model){
		try {
			WorkingFlowConfiguration _object = getRequestParamters(request,WorkingFlowConfiguration.class);
			//从session里面取出企业编码
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			_object.setEnterprisecode(employee.getEnterprisecode());
			_object.setFlowtype((short)WorkingFlowConfiguration.Enum_flowtype.取款工作流.value);
			//调用取款工作流查询方法
			WorkingFlowConfiguration _wfc = workingFlowConfigurationService.selectFirst(_object);
			model.addAttribute("WorkingFlowConfiguration", _wfc);
			return "/workingflow/takeWorkingFlowConfigurationUpdate";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	/**
	 * 取款工作流配置修改
	 * @param request
	 */
	@RequestMapping("/updateTakeWorkingFlowConfiguration")
	@OperationLog(OparetionDescription.DEPOSIT_TAKE_WORKING_FLOW_CONFIGURATION_UPDATE)
	@ResponseBody
	public Map<String,Object> updateTakeWorkingFlowConfiguration(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			WorkingFlowConfiguration object = getRequestParamters(request,WorkingFlowConfiguration.class);
			object.setEnterprisecode(ee.getEnterprisecode());
			//调用取款工作流修改方法
			workingFlowConfigurationService.editWithdrawlWorkingFlow(object);
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.流程管理业务, "修改取款工作流配置:"+object.getFlowcode(), ee.getLoginaccount(), null));
			
			
			return super.packJSON(Constant.BooleanByte.YES, "修改成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "修改失败,请稍后尝试");
		}
	}
	
	/**
	 * 取款工作流配置删除
	 * @param request
	 */
	@RequestMapping("/takeWorkingFlowConfigurationDelete")
	@OperationLog(OparetionDescription.DEPOSIT_TAKE_WORKING_FLOW_CONFIGURATION_DELETE)
	@ResponseBody
	public Map<String,Object> takeWorkingFlowConfigurationDelete(HttpServletRequest request,HttpSession session){
		try {
			String sign = request.getParameter("sign");
			if(super.decodeSign(sign, session.getId())){
				EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
				workingFlowConfigurationService.delWorkingFlow(sign.split("_")[1],ee.getEnterprisecode());
				
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.流程管理业务, "删除取款工作流配置:"+sign.split("_")[1], ee.getLoginaccount(), null));
				
				return super.packJSON(Constant.BooleanByte.YES, "删除成功");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	/**
	 * 根据部门编码与流程编码分别查询该部门有权限审批与没有权限审批的人员
	 * @param request
	 */
	@RequestMapping("/getData")
	@ResponseBody
	public Map<String, List<EnterpriseEmployee>> getData(HttpServletRequest request,HttpSession session){
		try {
			Map<String,Object> map = getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			map.put("enterprisecode", ee.getEnterprisecode());
			return workingFlowObjectService.takeGrantEmployee(map);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	/**
	 * 保存添加的流程审批人
	 * @param request
	 * @return Map<String,String>
	 */
	@RequestMapping("/saveWorkFlowApproveEmployee")
	@OperationLog(OparetionDescription.DEPOSIT_TAKE_WORKING_FLOW_CONFIGURATION_SET_APPROVE)
	@ResponseBody
	public Map<String,String> saveWorkFlowApproveEmployee(HttpServletRequest request){
		Map<String,String> map = new HashMap<String, String>();
		EnterpriseEmployee ee = (EnterpriseEmployee)request.getSession().getAttribute(Constant.USER_SESSION);
		List<WorkingFlowObject> list = new ArrayList<WorkingFlowObject>();
		try {
			String flowcode = request.getParameter("flowcode");
			String[] employee = request.getParameter("employee").split(",");
			for (int i = 0; i < employee.length; i++) {
				list.add(new WorkingFlowObject(employee[i],flowcode));
			}
			int mark = workingFlowObjectService.addFlowObject(flowcode, list);
			if(mark > 0){
				map.put("status", "0");
				
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.流程管理业务, "添加或更改流程审批人:"+flowcode, ee.getLoginaccount(), null));
				
				
				return map;
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return map;
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
