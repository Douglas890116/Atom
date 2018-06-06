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
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.EnterpriseActivityCustomization;
import com.maven.entity.EnterpriseBrandActivity;
import com.maven.entity.EnterpriseEmployee;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.EnterpriseActivityCustomizationService;
import com.maven.service.EnterpriseBrandActivityService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.utils.StringUtil;

@Controller
@RequestMapping("/ActivityBetRecord")
public class ActivityBetRecordController extends BaseController {
	
	private LoggerManager log = LoggerManager.getLogger(ActivityBetRecordController.class.getName(),
			OutputManager.LOG_ACTIVITYBETRECORD);
	@Autowired
	private ActivityBetRecordService activityBetRecordService;
	@Autowired
	private EnterpriseActivityCustomizationService enterpriseActivityCustomizationService;
	@Autowired
	private EnterpriseBrandActivityService enterpriseBrandActivityService;
	@Autowired
	private EnterpriseEmployeeService employeeService;
	
	/**
	 * 投注list页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public String betrecordIndex(HttpServletRequest request, Model model){
		
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) request.getSession().getAttribute(Constant.USER_SESSION);
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("enterprisecode", ee.getEnterprisecode());
			List<EnterpriseBrandActivity> list = enterpriseBrandActivityService.selectAll(queryParams);
			model.addAttribute("listEnterpriseBrandActivity", list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "/activitybetrecord/activitybetrecordlist";
	}
	
	/**
	 * list页面请求数据
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/data")
	@ResponseBody
	public Map<String, Object> data(HttpServletRequest request, HttpSession session){
		try {		
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> parameter = getRequestParamters(request);
			super.dataLimits(loginEmployee, parameter, session);
			
			
			if (parameter.get("parentName") != null) {
				String parentemployeeaccount = parameter.get("parentName").toString();
				EnterpriseEmployee parent = new EnterpriseEmployee();
				parent.setLoginaccount(parentemployeeaccount);
				List<EnterpriseEmployee> list = employeeService.select(parent);
				if (null != list && list.size() > 0) {
					//parameter.put("parentemployeecode", list.get(0).getEmployeecode());
					super.dataLimits(list.get(0), parameter, session);
				} else {
					// 如果查不到上级账户code，就直接传名字进去，查不出数据，表示这个账号下没有会员
					//parameter.put("parentemployeecode", parentemployeeaccount);
				}
				parameter.remove("parentName");
			}
			
			//查询企业所有活动,为所有记录的活动ID转换为活动名称
			List<EnterpriseActivityCustomization> alleac = null;
			
			/*********非超级管理员时只能查询本团队内的数据************/
			if(SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())){
				alleac = enterpriseActivityCustomizationService.selectAll(new HashMap<String, Object>());
			} else {
				alleac = enterpriseActivityCustomizationService.selectAllByEnterprisecode(loginEmployee.getEnterprisecode());//查本企业的
				parameter.put("enterprisecode", loginEmployee.getEnterprisecode());
			}
			
			
			List<ActivityBetRecord> betrecords = activityBetRecordService.selectBetRecord(parameter);
			
			Map<Integer, String> eackeyvalue = new HashMap<Integer, String>();
			for (EnterpriseActivityCustomization eac : alleac) {
				eackeyvalue.put(eac.getEcactivitycode(), eac.getActivityname());
			}
			for (ActivityBetRecord abr : betrecords) {
				if (eackeyvalue.containsKey(abr.getEcactivitycode())){
					abr.setActivityname(eackeyvalue.get(abr.getEcactivitycode()));
				} else if (abr.getEcactivitycode().equals(Enum_ecactivitycode.存款流水.value)) {
					abr.setActivityname(Enum_ecactivitycode.存款流水.desc);
				} else if (abr.getEcactivitycode().equals(Enum_ecactivitycode.返水所需流水.value)) {
					abr.setActivityname(Enum_ecactivitycode.返水所需流水.desc);
				} else {
					abr.setActivityname(Enum_ecactivitycode.UNKNOW.desc);
				}
			}
			
//			int count = activityBetRecordService.selectBetRecordCount(parameter);
//			return super.formatPagaMap(betrecords, count);
			
			Map<String, Object> result = activityBetRecordService.selectBetRecordCountMoney(parameter);
			int countRecord = StringUtil.getInt(result.get("count"));
			
			Map<String, Object> data = super.formatPagaMap(betrecords, countRecord);
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("mustbet", StringUtil.getDouble(result.get("mustbet")));
			summary.put("alreadybet", StringUtil.getDouble(result.get("alreadybet")));
			data.put("summary", summary);
			
			return data;
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
		
	/**
	 * 导出优惠活动信息
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/exportExcel")
	public String export(HttpServletRequest request, HttpSession session, Model model) {
		try {
			Map<String, Object> parameter = getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			super.dataLimits(ee, parameter, session);
			
			if (parameter.get("parentName") != null) {
				String parentemployeeaccount = parameter.get("parentName").toString();
				EnterpriseEmployee parent = new EnterpriseEmployee();
				parent.setLoginaccount(parentemployeeaccount);
				List<EnterpriseEmployee> list = employeeService.select(parent);
				if (null != list && list.size() > 0) {
					//parameter.put("parentemployeecode", list.get(0).getEmployeecode());
					super.dataLimits(list.get(0), parameter, session);
				} else {
					// 如果查不到上级账户code，就直接传名字进去，查不出数据，表示这个账号下没有会员
					//parameter.put("parentemployeecode", parentemployeeaccount);
				}
				parameter.remove("parentName");
			}
			
			//查询企业所有活动,为所有记录的活动ID转换为活动名称
			List<EnterpriseActivityCustomization> alleac = null;
			
			/*********非超级管理员时只能查询本团队内的数据************/
			if(SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())){
				alleac = enterpriseActivityCustomizationService.selectAll(new HashMap<String, Object>());
			} else {
				alleac = enterpriseActivityCustomizationService.selectAllByEnterprisecode(ee.getEnterprisecode());//查本企业的
				parameter.put("enterprisecode", ee.getEnterprisecode());
			}
			
			
			List<ActivityBetRecord> betrecords = activityBetRecordService.selectBetRecord(parameter);
			
			Map<Integer, String> eackeyvalue = new HashMap<Integer, String>();
			for (EnterpriseActivityCustomization eac : alleac) {
				eackeyvalue.put(eac.getEcactivitycode(), eac.getActivityname());
			}
			for (ActivityBetRecord abr : betrecords) {
				if (eackeyvalue.containsKey(abr.getEcactivitycode())){
					abr.setActivityname(eackeyvalue.get(abr.getEcactivitycode()));
				} else if (abr.getEcactivitycode().equals(Enum_ecactivitycode.存款流水.value)) {
					abr.setActivityname(Enum_ecactivitycode.存款流水.desc);
				} else if (abr.getEcactivitycode().equals(Enum_ecactivitycode.返水所需流水.value)) {
					abr.setActivityname(Enum_ecactivitycode.返水所需流水.desc);
				} else {
					abr.setActivityname(Enum_ecactivitycode.UNKNOW.desc);
				}
			}
			model.addAttribute("listData", betrecords);
			model.addAttribute("title", ee.getEnterprisename() + " 优惠活动数据");
		} catch (Exception e) {
			log.Error("导出优惠流水报错", e);
			return Constant.PAGE_ERROR;
		}
		return "/activitybetrecord/activitybetrecordexcel";
	}

	@Override
	public LoggerManager getLogger() {
		return log;
	}
	
}
