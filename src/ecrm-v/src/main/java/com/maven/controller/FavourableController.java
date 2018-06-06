package com.maven.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.core.util.UuidUtil;
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
import com.maven.entity.EnterpriseEmployeeLevel;
import com.maven.entity.EnterpriseEmployeeType.Type;
import com.maven.entity.Favourable;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.BusinessEmployeeLovelService;
import com.maven.service.EnterpriseActivityCustomizationService;
import com.maven.service.EnterpriseBrandActivityService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.FavourableService;
import com.maven.service.FavourableUserService;
import com.maven.util.BeanToMapUtil;
import com.maven.util.StringUtils;
import com.maven.utils.StringUtil;

@Controller
@RequestMapping("/favourable")
public class FavourableController extends BaseController {
	
	private LoggerManager log = LoggerManager.getLogger(FavourableController.class.getName(),
			OutputManager.LOG_ACTIVITYBETRECORD);
	@Autowired
	private FavourableService activityBetRecordService;
	@Autowired 
	private EnterpriseActivityCustomizationService customActivityService;//定制活动
	@Autowired
	private EnterpriseBrandActivityService brandActivityService;//企业绑定活动
	@Autowired
	private BusinessEmployeeLovelService businessEmployeeLovelService;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private FavourableUserService favourableUserService;
	
	/**
	 * index
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	public String betrecordIndex(HttpServletRequest request, Model model){
		
		return "/favourable/index";
	}
	
	/**
	 * add
	 * @param request
	 * @return
	 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request, Model model){
		
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) request.getSession().getAttribute(Constant.USER_SESSION);
			Map<String, Object> params2 = new HashMap<String, Object>();
			params2.put("enterpriseCode", loginEmployee.getEnterprisecode());
			List<EnterpriseEmployeeLevel> listEnterpriseEmployeeLevel = businessEmployeeLovelService.takelevelQuery(params2);
			model.addAttribute("listEnterpriseEmployeeLevel", listEnterpriseEmployeeLevel);
			
			model.addAttribute("favourable", activityBetRecordService.selectByPrimaryKey(request.getParameter("lsh")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/favourable/add";
	}
	/**
	 * edit
	 * @param request
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(HttpServletRequest request, Model model){
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) request.getSession().getAttribute(Constant.USER_SESSION);
			Map<String, Object> params2 = new HashMap<String, Object>();
			params2.put("enterpriseCode", loginEmployee.getEnterprisecode());
			List<EnterpriseEmployeeLevel> listEnterpriseEmployeeLevel = businessEmployeeLovelService.takelevelQuery(params2);
			model.addAttribute("listEnterpriseEmployeeLevel", listEnterpriseEmployeeLevel);
			
			model.addAttribute("favourable", activityBetRecordService.selectByPrimaryKey(request.getParameter("lsh")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/favourable/edit";
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
			Map<String, Object> parameter = getRequestParamters(request);
			
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			
			/*********非超级管理员时只能查询本团队内的数据************/
			if(SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())){
				//
			} else {
				parameter.put("enterprisecode", loginEmployee.getEnterprisecode());
			}
			
			List<Favourable> betrecords = activityBetRecordService.selectBetRecord(parameter);
			
			int count = activityBetRecordService.selectBetRecordCount(parameter);
			return super.formatPagaMap(betrecords, count);
			
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * save页面请求数据
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/Save")
	@ResponseBody
	public Map<String, Object> Save(HttpServletRequest request, HttpSession session){
		try {		
			Map<String, Object> parameter = getRequestParamters(request);
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			
			Favourable activityxx = BeanToMapUtil.convertMap(parameter, Favourable.class);
			activityxx.setCreatetime(new Date());
			activityxx.setLsh(UUID.randomUUID().toString());
			activityxx.setEnterprisecode(loginEmployee.getEnterprisecode());
			activityBetRecordService.addActivityBetRecord(activityxx);
			
			//添加一个定制活动
			EnterpriseActivityCustomization temp = new EnterpriseActivityCustomization();
			temp.setActivityname(activityxx.getFavourablename());
			temp.setActivitytemplatecode(9);//9是自定义活动，没有模板的
			temp.setDatastatus("1");//有效
			temp.setEnterprisecode(activityxx.getEnterprisecode());
			temp.setRemark(activityxx.getLsh());
			temp.setActivityimage("无");
			temp.setActivitycontent("无");
			customActivityService.saveCActivity(temp);
			List<EnterpriseActivityCustomization> list = customActivityService.select(temp);
			if(list != null && list.size() > 0) {
				temp = list.get(0);
			}
			
			//给企业绑定该定制活动
			EnterpriseBrandActivity activity = new EnterpriseBrandActivity();
			activity.setEnterprisecode(activityxx.getEnterprisecode());
			activity.setBrandcode(null);//品牌编码
			activity.setEmployeecode(null);//团队编码
			activity.setEcactivitycode(temp.getEcactivitycode());
			activity.setBegintime(activityxx.getStarttime());
			activity.setEndtime(activityxx.getEndtime());
			activity.setStatus("1");
			brandActivityService.saveEnterpriseActivity(activity);
			
			/*************************处理勾选的VIP等级用户加入该组业务**************************/
			String[] vips = request.getParameterValues("vips");
			for (String employeelevelcode : vips) {
				
				//根据等级编码查找所有用户
				EnterpriseEmployee paramObj = new EnterpriseEmployee();
				paramObj.setEmployeelevelcode(employeelevelcode);
				paramObj.setEmployeetypecode(Type.会员.value);
				List<EnterpriseEmployee> listEnterpriseEmployee = enterpriseEmployeeService.select(paramObj);
				
				String[] employeecodes = new String[listEnterpriseEmployee.size()];
				for (int i = 0; i < listEnterpriseEmployee.size(); i++) {
					employeecodes[i] = listEnterpriseEmployee.get(i).getEmployeecode();
				}
				favourableUserService.addBatchData(loginEmployee.getEnterprisecode(), activityxx.getLsh(), employeecodes);
			}
			/*************************处理勾选的VIP等级用户加入该组业务**************************/
			
			return super.packJSON(Constant.BooleanByte.YES, "操作成功");
			
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.YES, "操作失败："+e.getMessage());
		}
	}
	/**
	 * update页面请求数据
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/Update")
	@ResponseBody
	public Map<String, Object> Update(HttpServletRequest request, HttpSession session){
		try {		
			Map<String, Object> parameter = getRequestParamters(request);
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			
			Favourable activityBetRecord = super.getRequestParamters(request, Favourable.class);
			activityBetRecordService.updateActivityBetRecord(activityBetRecord);
			
			//更新对应的定制活动
			EnterpriseActivityCustomization temp = new EnterpriseActivityCustomization();
			temp.setRemark(activityBetRecord.getLsh());
			temp.setActivitytemplatecode(9);//9是自定义活动，没有模板的
			temp.setEnterprisecode(activityBetRecord.getEnterprisecode());
			List<EnterpriseActivityCustomization> list = customActivityService.select(temp);
			if(list != null && list.size() > 0) {
				temp = list.get(0);
				temp.setActivityname(activityBetRecord.getFavourablename());
				temp.setActivitytemplatecode(9);//9是自定义活动，没有模板的
				if(activityBetRecord.getStatus() == Favourable.Enum_status.启用.value) {
					temp.setDatastatus("1");//有效
				} else {
					temp.setDatastatus("0");//无效
				}
				temp.setEnterprisecode(activityBetRecord.getEnterprisecode());
				customActivityService.updateCActivity(temp);
				
				
				//更新对应的绑定活动
				EnterpriseBrandActivity activity = new EnterpriseBrandActivity();
				activity.setEnterprisecode(activityBetRecord.getEnterprisecode());
				activity.setEcactivitycode(temp.getEcactivitycode());
				List<EnterpriseBrandActivity> list2 = brandActivityService.select(activity);
				if(list2 != null && list2.size() > 0) {
					activity = list2.get(0);
					activity.setBegintime(activityBetRecord.getStarttime());
					activity.setEndtime(activityBetRecord.getEndtime());
					if(activityBetRecord.getStatus() == Favourable.Enum_status.启用.value) {
						activity.setStatus("1");//有效
					} else {
						activity.setStatus("0");//无效
					}
					brandActivityService.editEnterpriseActivity(activity);
				}
			}
			
			return super.packJSON(Constant.BooleanByte.YES, "操作成功");
			
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.YES, "操作失败："+e.getMessage());
		}
	}
	
	/**
	 * 更新状态
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/UpdateStatus")
	@ResponseBody
	public Map<String, Object> UpdateStatus(HttpServletRequest request, HttpSession session){
		try {		
			Map<String, Object> parameter = getRequestParamters(request);
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			
			String status = request.getParameter("status");
			
			Favourable activityBetRecord = activityBetRecordService.selectByPrimaryKey(request.getParameter("lsh"));
			activityBetRecord.setStatus(Integer.valueOf(status));
			activityBetRecordService.updateActivityBetRecord(activityBetRecord);
			
			
			//更新对应的定制活动
			EnterpriseActivityCustomization temp = new EnterpriseActivityCustomization();
			temp.setActivityimage("Favourable"+activityBetRecord.getLsh());//活动图片，无
			temp.setActivitytemplatecode(9);//9是自定义活动，没有模板的
			temp.setEnterprisecode(activityBetRecord.getEnterprisecode());
			List<EnterpriseActivityCustomization> list = customActivityService.select(temp);
			if(list != null && list.size() > 0) {
				temp = list.get(0);
				temp.setActivitycontent("优惠活动组自动创建活动");
				temp.setActivityimage("Favourable"+activityBetRecord.getLsh());//活动图片，无
				temp.setActivityname(activityBetRecord.getFavourablename());
				temp.setActivitytemplatecode(9);//9是自定义活动，没有模板的
				if(activityBetRecord.getStatus() == Favourable.Enum_status.启用.value) {
					temp.setDatastatus("1");//有效
				} else {
					temp.setDatastatus("0");//无效
				}
				temp.setEnterprisecode(activityBetRecord.getEnterprisecode());
				customActivityService.updateCActivity(temp);
				
				
				//更新对应的绑定活动
				EnterpriseBrandActivity activity = new EnterpriseBrandActivity();
				activity.setEnterprisecode(activityBetRecord.getEnterprisecode());
				activity.setEcactivitycode(temp.getEcactivitycode());
				List<EnterpriseBrandActivity> list2 = brandActivityService.select(activity);
				if(list2 != null && list2.size() > 0) {
					activity = list2.get(0);
					activity.setBegintime(activityBetRecord.getStarttime());
					activity.setEndtime(activityBetRecord.getEndtime());
					if(activityBetRecord.getStatus() == Favourable.Enum_status.启用.value) {
						activity.setStatus("1");//有效
					} else {
						activity.setStatus("0");//无效
					}
					brandActivityService.editEnterpriseActivity(activity);
				}
			}
			
			return super.packJSON(Constant.BooleanByte.YES, "操作成功");
			
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.YES, "操作失败："+e.getMessage());
		}
	}

	@Override
	public LoggerManager getLogger() {
		return log;
	}
	
}
