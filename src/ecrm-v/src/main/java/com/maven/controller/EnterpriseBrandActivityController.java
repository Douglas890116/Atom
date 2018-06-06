package com.maven.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.maven.constant.Constant;
import com.maven.entity.EnterpriseActivityCustomization;
import com.maven.entity.EnterpriseBrandActivity;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.UserLogs;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.EnterpriseActivityCustomizationService;
import com.maven.service.EnterpriseBrandActivityService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseOperatingBrandService;
import com.maven.service.UserLogsService;

@Controller
@RequestMapping("/Activity")
public class EnterpriseBrandActivityController extends BaseController {

	private static LoggerManager log = LoggerManager.getLogger(EnterpriseBrandActivityController.class.getName(),
			OutputManager.LOG_ENTERPRISEBRANDACTIVITY);
	@Autowired
	private EnterpriseBrandActivityService enterpriseBrandActivityService;

	@Autowired
	private EnterpriseOperatingBrandService enterpriseOperatingBrandService;

	@Autowired
	private EnterpriseActivityCustomizationService enterpriseActivityCustomizationService;

	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private UserLogsService userLogsService;

	/**
	 * 企业活动列表
	 * 
	 * @return
	 */
	@RequestMapping("/Show")
	public String show() {
		return "/activitysetting/enterpriseactivitylist";
	}

	/**
	 * 企业活动数据加载-分页
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/Data")
	@ResponseBody
	public Map<String, Object> data(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> object = super.getRequestParamters(request);
			object.put("enterprisecode", ee.getEnterprisecode());
			List<EnterpriseBrandActivity> list = enterpriseBrandActivityService.selectAll(object);
			int count = enterpriseBrandActivityService.selectAllCount(object);
			Map<String, String> s = new HashMap<String, String>();
			s.put("enterprisebrandactivitycode", "sign");
			super.encryptSignTarget(list, session.getId(), s);
			return super.formatPagaMap(list, count);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 跳转企业运营活动添加页
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/Add")
	public String add(HttpServletRequest request, HttpSession session, Model model) {
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			List<EnterpriseOperatingBrand> brands = enterpriseOperatingBrandService
					.getEnterpriseBrand(ee.getEnterprisecode());
			List<EnterpriseActivityCustomization> template = enterpriseActivityCustomizationService.select(
					new EnterpriseActivityCustomization(ee.getEnterprisecode()));
			for (EnterpriseActivityCustomization __eac : template) {
				__eac.setSign(super.encryptString(String.valueOf(__eac.getEcactivitycode()), session.getId()));
				__eac.setActivitycontent(htmlspecialchars(__eac.getActivitycontent()));
			}
			model.addAttribute("templates", template);
			model.addAttribute("brands", brands);
			model.addAttribute("enterprisename", ee.getEnterprisename());
			return "/activitysetting/enterpriseactivityadd";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}

	/**
	 * 保存企业运营活动
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/Save")
	@ResponseBody
	@OperationLog(OparetionDescription.ENTERPRISEBRANDACTIVITY_ADD)
	public Map<String, Object> save(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseBrandActivity activity = super.getRequestParamters(request, EnterpriseBrandActivity.class);
			if (super.decodeSign(activity.getSign(), session.getId())) {
				EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
				activity.setEcactivitycode(Integer.parseInt(activity.getSign().split("_")[1]));
				activity.setEnterprisecode(ee.getEnterprisecode());
				enterpriseBrandActivityService.saveEnterpriseActivity(activity);
				
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.活动管理业务, "保存企业活动:"+activity.getActivityname(), ee.getLoginaccount(), null));
				
				return packJSON(Constant.BooleanByte.YES, "成功添加活动");
			} else {
				return packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}

	/**
	 * 跳转企业活动编辑页
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/Edit")
	public String edit(HttpServletRequest request, HttpSession session, Model model) {
		try {
			String sign = request.getParameter("sign");
			if (super.decodeSign(sign, session.getId())) {
				EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
				EnterpriseBrandActivity EBrandActivity = enterpriseBrandActivityService
						.selectByPrimaryKey(sign.split("_")[1]);
				EBrandActivity.setSign(sign);
				List<EnterpriseOperatingBrand> brands = enterpriseOperatingBrandService
						.getEnterpriseBrand(ee.getEnterprisecode());
				List<EnterpriseActivityCustomization> template = enterpriseActivityCustomizationService.select(
						new EnterpriseActivityCustomization(ee.getEnterprisecode()));
				if (StringUtils.isNotBlank(EBrandActivity.getEmployeecode())) {
					EnterpriseEmployee team = enterpriseEmployeeService
							.takeEmployeeByCode(EBrandActivity.getEmployeecode());
					model.addAttribute("team", team);
				}
				for (EnterpriseActivityCustomization __eac : template) {
					__eac.setActivitycontent(htmlspecialchars(__eac.getActivitycontent()));
				}
				model.addAttribute("templates", template);
				model.addAttribute("brands", brands);
				model.addAttribute("enterprisename", ee.getEnterprisename());
				model.addAttribute("activity", EBrandActivity);
				return "/activitysetting/enterpriseactivityedit";
			} else {
				return Constant.PAGE_DECODEREFUSE;
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	/**
	 * 保存修改企业活动
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/Update")
	@ResponseBody
	public Map<String,Object> update(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			EnterpriseBrandActivity activity = super.getRequestParamters(request, EnterpriseBrandActivity.class);
			if(super.decodeSign(activity.getSign(), session.getId())){
				activity.setEnterprisebrandactivitycode(Integer.parseInt(activity.getSign().split("_")[1]));
				enterpriseBrandActivityService.editEnterpriseActivity(activity);
				
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.活动管理业务, "编辑企业活动:"+activity.getActivityname(), ee.getLoginaccount(), null));
				
				return packJSON(Constant.BooleanByte.YES, "模板编辑成功");
			}else{
				return packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return packJSON(Constant.BooleanByte.NO, "模板编辑失败，请稍后尝试");
		}
	}
	
	/**
	 * 删除企业运营活动
	 * @return
	 */
	@RequestMapping("/Delete")
	@ResponseBody
	public Map<String,Object> delete(HttpServletRequest request ,HttpSession session){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			String[] sign = request.getParameterValues("sign[]");
			if(sign==null||sign.length==0) 
				return this.packJSON(Constant.BooleanByte.NO, "请选择需要删除的数据");
			
			List<String> activitycodes = new ArrayList<String>();
			for (String s : sign) {
				if(super.decodeSign(s, session.getId())){
					activitycodes.add(s.split("_")[1]);
				}else{
					return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
				}
			}
			enterpriseBrandActivityService.deleteEnterpriseActivity(activitycodes);
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.活动管理业务, "删除企业活动", ee.getLoginaccount(), null));
			
			return this.packJSON(Constant.BooleanByte.YES, "删除成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	

	@Override
	public LoggerManager getLogger() {
		return log;
	}

	private String htmlspecialchars(String str) {
		if(StringUtils.isNotBlank(str)){
			str = str.replaceAll("&amp;","&");
			str = str.replaceAll("&lt;","<");
			str = str.replaceAll("&gt;",">");
			str = str.replaceAll("&quot;","\"");
		}
		return str.replaceAll("\"", "'");
	}
}
