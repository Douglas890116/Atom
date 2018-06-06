package com.maven.controller;

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

import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.EmployeeMoneyChange;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EnterpriseEmployee;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.EmployeeMoneyChangeService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.utils.MoneyHelper;
import com.maven.utils.StringUtil;
@Controller
@RequestMapping("/employeeMoneyChange")
public class EmployeeMoneyChangeController extends BaseController{
	private static LoggerManager log = LoggerManager.getLogger(EmployeeMoneyChangeController.class.getName(), OutputManager.LOG_EMPLOYEEMONEYCHANGE);
	//账变
	@Autowired
	private EmployeeMoneyChangeService employeeMoneyChangeServiceImpl;
	@Autowired
	private EnterpriseEmployeeService employeeService;
	
	@RequestMapping("/index")
	public String index(Model model){
		model.addAttribute("moneyaddtypes",EmployeeMoneyChangeType.Enum_moneyaddtype.values());
		return "/userinfo/accountChangeRecord";
	}
	
	@RequestMapping("/findAccountChange")
	@ResponseBody
	public Map<String,Object> findAccountChange(HttpServletRequest request,HttpSession session){
		try {
			Map<String,Object> object = getRequestParamters(request);
			EnterpriseEmployee ee= (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);

			findParentEmoloyeeCode(object);
			
			/*********非超级管理员时只能查询本团队内的数据************/
			if(!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())){
				super.dataLimits(ee, object,session);
			}
			
			List<EmployeeMoneyChange> list = employeeMoneyChangeServiceImpl.findAccountChange(object);
			
			Map<String, Object> result = employeeMoneyChangeServiceImpl.findAccountChangeCount(object);
			int count = StringUtil.getInt(result.get("count"));
			for (EmployeeMoneyChange emc : list) {
				emc.setAfteramount(emc.getMoneychangeamount().add(emc.getSettlementamount()));
			}
//			return super.formatPagaMap(list, count);
			
			Map<String, Object> data = super.formatPagaMap(list, count);
			Map<String, String> summary = new HashMap<String, String>();
			summary.put("moneychangeamount", MoneyHelper.doubleFormat(result.get("moneychangeamount")));//账变前
			summary.put("settlementamount", MoneyHelper.doubleFormat(result.get("settlementamount")));//账变金额
			summary.put("afteramount", MoneyHelper.doubleFormat(result.get("moneyafteramount")));//账变后
			data.put("summary", summary);
			
			return data;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		  return super.formatPagaMap(null, 0);
		}
	}
	
	/** 导出账变清单数据
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/excelAccountChange")
	public String excelAccountChange(HttpSession session, HttpServletRequest request, Model model){
		try {
			Map<String,Object> object = getRequestParamters(request);
			EnterpriseEmployee ee= (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);

			findParentEmoloyeeCode(object);

			/*********非超级管理员时只能查询本团队内的数据************/
			if(!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())){
				super.dataLimits(ee, object,session);
			}
			
			List<EmployeeMoneyChange> list = employeeMoneyChangeServiceImpl.findAccountChange(object);
			for (EmployeeMoneyChange emc : list) {
				emc.setAfteramount(emc.getMoneychangeamount().add(emc.getSettlementamount()));
			}
			
			model.addAttribute("listEmployeeMoneyChange", list);
			model.addAttribute("title", "账户资金变动清单数据");
			
			if( !StringUtil.handleNull(object.get("moneychangetypecode")).equals("") && (list != null && list.size() > 0) ) {
				model.addAttribute("title", "账户资金变动清单数据-"+ list.get(0).getMoneychangetypename());
			}
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		
		return "/userinfo/accountChangeRecordExcel";
	}
	
	/**
	 * 查找上级
	 * @param paramObj
	 */
	private void findParentEmoloyeeCode(Map<String, Object> paramObj){
		try {
			if (paramObj.get("parentName") != null) {
				String parentemployeeaccount = paramObj.get("parentName").toString();
				EnterpriseEmployee parent = new EnterpriseEmployee();
				parent.setLoginaccount(parentemployeeaccount);
				List<EnterpriseEmployee> list = employeeService.select(parent);
				if (null != list && list.size() > 0) {
					paramObj.put("parentemployeecode", list.get(0).getEmployeecode());
				} else {
					// 如果查不到上级账户code，就直接传名字进去，查不出数据，表示这个账号下没有会员
					paramObj.put("parentemployeecode", parentemployeeaccount);
				}
				paramObj.remove("parentName");
			}
		} catch (Exception e) {
			log.Error("查找上级报错", e);
		}
	}
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
