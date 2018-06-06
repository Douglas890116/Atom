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

import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.ActivityTemplate;
import com.maven.entity.ActivityTemplateSetting;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseActivityCustomization;
import com.maven.entity.EnterpriseActivityCustomizationSetting;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.UserLogs;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.ActivityTemplateService;
import com.maven.service.ActivityTemplateSettingService;
import com.maven.service.EnterpriseActivityCustomizationService;
import com.maven.service.EnterpriseActivityCustomizationSettingService;
import com.maven.service.EnterpriseService;
import com.maven.service.UserLogsService;

@Controller
@RequestMapping("/EACustomization")
public class EnterpriseActivityCustomizationController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			EnterpriseActivityCustomizationController.class.getName(), OutputManager.LOG_ENTERPRISEACTIVITYCUSTOMIZATION);
	
	@Autowired
	private EnterpriseActivityCustomizationService enterpriseActivityCustomizationService;
	
	@Autowired
	private EnterpriseActivityCustomizationSettingService enterpriseActivityCustomizationSettingService;
	
	@Autowired
	private ActivityTemplateSettingService activityTemplateSettingService;
	
	@Autowired
	private ActivityTemplateService activityTemplateService;
	
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private UserLogsService userLogsService;
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("/Index")
	private String index(Model model, HttpSession session){
		
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			List<Enterprise> listEnterprise = null;
			
			/*********非超级管理员时只能查询本团队内的数据************/
			if( !SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("enterprisecode", ee.getEnterprisecode());
				listEnterprise = enterpriseService.selectAll(params);
			} else {
				listEnterprise = enterpriseService.selectAll(null);
			}
			
			List<ActivityTemplate> listActivityTemplate = activityTemplateService.selectAll(null);
			
			model.addAttribute("listActivityTemplate", listActivityTemplate);
			model.addAttribute("listEnterprise", listEnterprise);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/activitycustomization/customizationactivitylist";
	}
	
	/**
	 * 添加页
	 * @param model
	 * @return
	 */
	@RequestMapping("/Add")
	private String add(Model model, HttpSession session){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			List<Enterprise> __enterprises = null;
			
			/*********非超级管理员时只能查询本团队内的数据************/
			if( !SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("enterprisecode", ee.getEnterprisecode());
				__enterprises = enterpriseService.selectAll(params);
			} else {
				__enterprises = enterpriseService.selectAll(null);
			}
			
			
			List<ActivityTemplate> __template = activityTemplateService.select(null);
			model.addAttribute("enterprise", __enterprises);
			model.addAttribute("template", __template);
			return "/activitycustomization/activitycustomizationadd";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	/**
	 * 编辑页
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/Edit")
	public String edit(HttpServletRequest request,HttpSession session,Model model){
		try {
			String __ecactivitycode = request.getParameter("ecactivitycode");
			EnterpriseActivityCustomization __activity = enterpriseActivityCustomizationService.selectByPrimaryKey(__ecactivitycode);
			__activity.setActivitycontent(htmlspecialchars(__activity.getActivitycontent()));
			EnterpriseActivityCustomization __eac = new EnterpriseActivityCustomization();
			__eac.setEnterprisecode(__activity.getEnterprisecode());
			List<EnterpriseActivityCustomization> __activitylist = enterpriseActivityCustomizationService.select(__eac);
			List<ActivityTemplate> __template = activityTemplateService.select(null);
			
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			List<Enterprise> __enterprises = null;
			
			/*********非超级管理员时只能查询本团队内的数据************/
			if( !SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("enterprisecode", ee.getEnterprisecode());
				__enterprises = enterpriseService.selectAll(params);
			} else {
				__enterprises = enterpriseService.selectAll(null);
			}
			
			model.addAttribute("activity", __activity);
			model.addAttribute("activityList", __activitylist);
			model.addAttribute("template", __template);
			model.addAttribute("enterprise", __enterprises);
			return "/activitycustomization/activitycustomizationedit";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	
	/**
	 * 参数设定
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/Setting")
	public String agumentSetting(HttpServletRequest request,HttpSession session,Model model){
		try {
			String __activitytemplatecode = request.getParameter("activitytemplatecode");
			String __ecactivitycode = request.getParameter("ecactivitycode");
			List<ActivityTemplateSetting> __list = activityTemplateSettingService.select(new ActivityTemplateSetting(Integer.parseInt(__activitytemplatecode)));
			Map<Integer,String> __auments = enterpriseActivityCustomizationSettingService.selectActivityAgument(Integer.parseInt(__ecactivitycode));
			model.addAttribute("setting", __list);
			model.addAttribute("auments", __auments);
			model.addAttribute("activitytemplatecode", __activitytemplatecode);
			model.addAttribute("ecactivitycode", __ecactivitycode);
			return "/activitycustomization/activityagumentsetting";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	/**
	 * 分页查询
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/Data")
	@ResponseBody
	public Map<String,Object> data(HttpServletRequest request,HttpSession session){
		try {
			Map<String,Object> __object = super.getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			
			/*********非超级管理员时只能查询本团队内的数据************/
			if( !SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())){
				__object.put("enterprisecode", ee.getEnterprisecode());
			} 
			
			
			List<EnterpriseActivityCustomization> __list = enterpriseActivityCustomizationService.selectAll(__object);
			for (EnterpriseActivityCustomization __eac : __list) {
				__eac.setSign(super.encryptString(String.valueOf(__eac.getEcactivitycode()), session.getId()));
			}
			int __listCount = enterpriseActivityCustomizationService.selectAllCount(__object);
			return super.formatPagaMap(__list, __listCount);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 数据保存
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/Save")
	@ResponseBody
	public Map<String,Object> save(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			EnterpriseActivityCustomization __object = super.getRequestParamters(request,EnterpriseActivityCustomization.class);
			String[] unshares = request.getParameterValues("unshare");
			__object.setUnshare(StringUtils.join(unshares,","));
			
			String activityimage = __object.getActivityimage();
			activityimage = activityimage.replaceAll("http://img.hyzonghe.net:80/", "https://img.hyzonghe.net/");
			String activityimagehfive = __object.getActivityimagehfive();
			activityimagehfive = activityimagehfive.replaceAll("http://img.hyzonghe.net:80/", "https://img.hyzonghe.net/");
			
			__object.setActivityimage(activityimagehfive);
			__object.setActivityimagehfive(activityimagehfive);
			enterpriseActivityCustomizationService.saveCActivity(__object);
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.活动管理业务, "保存定制活动:"+__object.getActivityname(), ee.getLoginaccount(), null));
			
			return super.packJSON(Constant.BooleanByte.YES, "成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return  super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	/**
	 * 数据修改
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/Update")
	@ResponseBody
	public Map<String,Object> update(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			EnterpriseActivityCustomization __object = super.getRequestParamters(request,EnterpriseActivityCustomization.class);
			String[] unshares = request.getParameterValues("unshare");
			__object.setUnshare(StringUtils.join(unshares,","));
			
			String activityimage = __object.getActivityimage();
			activityimage = activityimage.replaceAll("http://img.hyzonghe.net:80/", "https://img.hyzonghe.net/");
			String activityimagehfive = __object.getActivityimagehfive();
			activityimagehfive = activityimagehfive.replaceAll("http://img.hyzonghe.net:80/", "https://img.hyzonghe.net/");
			
			__object.setActivityimage(activityimagehfive);
			__object.setActivityimagehfive(activityimagehfive);
			enterpriseActivityCustomizationService.updateCActivity(__object);
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.活动管理业务, "更改定制活动:"+__object.getActivityname(), ee.getLoginaccount(), null));
			
			return super.packJSON(Constant.BooleanByte.YES, "成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return  super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);	
		}
	}
	
	@RequestMapping("/SaveSetting")
	@ResponseBody
	public Map<String,Object> saveSetting(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			String __activitytemplatecode = request.getParameter("activitytemplatecode");
			String __ecactivitycode = request.getParameter("ecactivitycode");
			Integer __ecace = Integer.parseInt(__ecactivitycode);
			List<ActivityTemplateSetting> __list = activityTemplateSettingService.select(new ActivityTemplateSetting(Integer.parseInt(__activitytemplatecode)));
			List<EnterpriseActivityCustomizationSetting> __auments = new ArrayList<EnterpriseActivityCustomizationSetting>();
			for (ActivityTemplateSetting __at : __list) {
				String __augmentValue = request.getParameter(__at.getAgementname());
				__auments.add(new EnterpriseActivityCustomizationSetting(__ecace,__at.getActivitysettingcode(),__augmentValue));
			}
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.活动管理业务, "更改定制活动参数:"+__ecactivitycode, ee.getLoginaccount(), null));
			
			enterpriseActivityCustomizationSettingService.saveAgument(__ecace,__auments);
			return super.packJSON(Constant.BooleanByte.YES, "成功");
		} catch (Exception e) {
			log.Error(e.getMessage(),e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	/**
	 * 查看活动
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/View")
	@ResponseBody
	public Map<String,Object> viewActivity(HttpServletRequest request,HttpSession session){
		try {
			String __ecactivitycode = request.getParameter("ecactivitycode");
			EnterpriseActivityCustomization __activity = enterpriseActivityCustomizationService.selectByPrimaryKey(__ecactivitycode);
			__activity.setActivitycontent(htmlspecialchars(__activity.getActivitycontent()));
			return super.packJSON(Constant.BooleanByte.YES, __activity);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	@RequestMapping("/Delete")
	@ResponseBody
	public Map<String,Object> delete(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			String[] sign = request.getParameterValues("sign[]");
			if(sign==null||sign.length==0) 
				return this.packJSON(Constant.BooleanByte.NO, "请选择需要删除的数据");
			List<String> ids = new ArrayList<String>();
			for (String s : sign) {
				if(super.decodeSign(s, session.getId())){
						ids.add(s.split("_")[1]);
				}else{
					return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
				}
			}
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.活动管理业务, "删除定制活动", ee.getLoginaccount(), null));
			
			enterpriseActivityCustomizationService.tc_logicDelete(ids);
			return this.packJSON(Constant.BooleanByte.YES, "删除成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
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

	@Override
	public LoggerManager getLogger() {
		return log;
	}
 
}
