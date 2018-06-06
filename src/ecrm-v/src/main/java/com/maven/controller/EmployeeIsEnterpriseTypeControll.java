package com.maven.controller;

import java.math.BigDecimal;
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

import com.maven.constant.Constant;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.UserLogs;
import com.maven.entity.EnterpriseEmployeeType.Type;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.UserLogsService;
/**
 * 只处理用户类型为企业号(0001)的数据
 * @author Ethan
 *
 */
@Controller
@RequestMapping("/enterpriseType")
public class EmployeeIsEnterpriseTypeControll extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			EmployeeIsEnterpriseTypeControll.class.getName(), OutputManager.LOG_EMPLOYEEISENTERPRISETYPE);
	
	/**  企业员工  */
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private UserLogsService userLogsService;
	/**
	 * 企业号员工主页面
	 * @return
	 */
	@RequestMapping("/userJsp/enterpriseIndex")
	public String enterpriseIndex(HttpSession session,Model model){
		return "/userjsp/enterpriseIndex";
	}
	/**
	 * 企业号员工新增页面
	 * @return
	 */
	@RequestMapping("/userJsp/enterpriseAdd")
	public String enterpriseAdd(){
		return "/userjsp/enterpriseAdd";
	}
	/**
	 * 只查询企业号
	 * @param request
	 * @return
	 */
	@RequestMapping("/findEmployee")
	@ResponseBody
	public Map<String,Object> findEmployee(HttpServletRequest request,HttpSession session){
		try {
			//获取登录用户的信息
			Map<String,Object> obj = getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			super.dataLimits(ee, obj,session);
			obj.put("employeetypecode", EnterpriseEmployeeType.Type.企业号.value);
			pramsSpecialHandle(obj);
			List<EnterpriseEmployee> list = enterpriseEmployeeService.findEmployee(obj);
			//加密标识字段的值
			this.encryptSign(list,session.getId(),new String[]{"employeecode","loginaccount"});
			//查询总记录条数
			int listTotal = enterpriseEmployeeService.findEmployeeCount(obj);
			return super.formatPagaMap(list, listTotal);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}

	
	/**
	 * 企业类型的用户密码修改页面
	 */
	@RequestMapping("/userJsp/enterpriseModify")
	public String rowModify(Model model, HttpServletRequest request){
		model.addAttribute("loginaccount", request.getParameter("loginaccount"));
		return "/userjsp/enterpriseModify";
	}
	
	/**
	 * 根据员工编码删除
	 * @param request
	 */
	@RequestMapping("/deleteEmployee")
	@ResponseBody
	public Map<String,String> deleteEmployee(HttpServletRequest request,HttpSession session){
		Map<String,String> map =new HashMap<String,String>();
		//获取加密之后的员工编码
		String md5Employeecode = request.getParameter("deleteCode");
		//解密标识字段的值
		boolean mark = this.decodeSign(md5Employeecode, session.getId());
		EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
		if(mark){
			String employeecode = md5Employeecode.split("_")[1];
			try {
				EnterpriseEmployee targetEmployee = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
				
				userLogsService.addActivityBetRecord(new UserLogs(targetEmployee.getEnterprisecode(), targetEmployee.getEmployeecode(), targetEmployee.getLoginaccount(), 
					     UserLogs.Enum_operatype.用户信息业务, "删除用户"+targetEmployee.getLoginaccount(), loginEmployee.getLoginaccount(), null));
			} catch (Exception e) {
				e.printStackTrace();
			}
			//调用删除方法
			try {
				//调用删除员工方法
				enterpriseEmployeeService.tc_deleteEmployee(employeecode);
				map.put("status", "success");
				
				return map;
			} catch (Exception e) {
				log.Error(e.getMessage(), e);
			}
			map.put("status", "failure");
			return map;
		}else{
			map.put("status", "failure");
			return map;
		}
		
	}
	/**
	 * 批量删除
	 * @param request
	 */
	@RequestMapping("/deleteSelectEmployee")
	@ResponseBody
	public Map<String,Object> deleteSelectEmployee(HttpServletRequest request,HttpSession session){
		Map<String,Object> map =new HashMap<String,Object>();
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			//只有员工类型与企业类型的用户具有权限
			if(loginEmployee.getEmployeetypecode().equals(Type.企业号.value) ||loginEmployee.getEmployeetypecode().equals(Type.员工.value)){
				String temp = request.getParameter("paramArray");
				String[] array = temp.split(",");
				//解密标识字段的值
				boolean mark = this.decodeSign(array, session.getId());
				if(mark){
					for (int i = 0; i < array.length; i++) {
						array[i]=array[i].split("_")[1];
					}
					
					try {
						
						for (String employeecode : array) {
							EnterpriseEmployee targetEmployee = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
							userLogsService.addActivityBetRecord(new UserLogs(targetEmployee.getEnterprisecode(), targetEmployee.getEmployeecode(), targetEmployee.getLoginaccount(), 
								     UserLogs.Enum_operatype.用户信息业务, "删除用户"+targetEmployee.getLoginaccount(), loginEmployee.getLoginaccount(), null));
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					enterpriseEmployeeService.tc_deleteSelectEmployee(array);
					map.put("status", "success");
					return map;
				}
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		map.put("status", "failure");
		return map;
	}
	
	/**
	 * 特殊参数处理
	 * @param obj
	 */
	private void pramsSpecialHandle(Map<String, Object> obj) {
		if(obj.get("dividend_begin")!=null){
			BigDecimal dividend_begin = new BigDecimal(String.valueOf(obj.get("dividend_begin")));
			obj.put("dividend_begin", dividend_begin.divide(new BigDecimal(100)));
		}
		if(obj.get("dividend_end")!=null){
			BigDecimal dividend_end = new BigDecimal(String.valueOf(obj.get("dividend_end")));
			obj.put("dividend_end", dividend_end.divide(new BigDecimal(100)));
		}
		if(obj.get("share_begin")!=null){
			BigDecimal share_begin = new BigDecimal(String.valueOf(obj.get("share_begin")));
			obj.put("share_begin", share_begin.divide(new BigDecimal(100)));
		}
		if(obj.get("share_end")!=null){
			BigDecimal share_end = new BigDecimal(String.valueOf(obj.get("share_end")));
			obj.put("share_end", share_end.divide(new BigDecimal(100)));
		}
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
