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

import com.maven.constant.Constant;
import com.maven.entity.ActivityTemplateSetting;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.ActivityTemplateSettingService;

@Controller
@RequestMapping("/ATemplateSetting")
public class ActivityTemplateSettingController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			ActivityTemplateSettingController.class.getName(), OutputManager.LOG_ACTIVITYTEMPLATESETTING);
	
	@Autowired
	private ActivityTemplateSettingService activityTemplateSettingService;
	
	/**
	 * 参数配置列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/Show")
	public String show(HttpServletRequest request,Model model){
		model.addAttribute("activitycode", request.getParameter("sign"));
		return "/activitytemplate/activitytemplatesettinglist";
	}
	@RequestMapping("/Data")
	@ResponseBody
	public Map<String,Object> data(HttpServletRequest request,HttpSession session){
		try {
			Map<String,Object> object = super.getRequestParamters(request);
			String activitycode = String.valueOf(object.get("activitycode"));
			if(!super.decodeSign(activitycode, session.getId())) return null;
			object.put("activitycode", activitycode.split("_")[1]);
			List<ActivityTemplateSetting> list  = activityTemplateSettingService.selectAll(object);
			int count = activityTemplateSettingService.selectAllCount(object);
			Map<String,String> encrypt = new HashMap<String, String>();
			encrypt.put("activitysettingcode", "sign");
			super.encryptSignTarget(list, session.getId(), encrypt);
			return formatPagaMap(list, count);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 跳转：添加参数
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/Add")
	public String add(HttpServletRequest request,Model model){
		try {
			model.addAttribute("sign", request.getParameter("sign"));
			return "/activitytemplate/activitytemplatesettingadd";
		} catch (Exception e) {
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	/**
	 * 保存参数
	 * @param request
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/Save")
	@ResponseBody
	public Map<String,Object> save(HttpServletRequest request,Model model,HttpSession session){
		try {
			ActivityTemplateSetting setting = super.getRequestParamters(request, ActivityTemplateSetting.class);
			if(super.decodeSign(setting.getSign(), session.getId())){
				setting.setActivitycode(Integer.parseInt(setting.getSign().split("_")[1]));
				activityTemplateSettingService.tc_saveActivityTemplateSetting(setting);
				return packJSON(Constant.BooleanByte.YES, "成功添加参数");
			}else{
				return packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	/**
	 * 跳转：编辑
	 * @param request
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/Edit")
	public String edit(HttpServletRequest request,Model model,HttpSession session){
		try {
			String sign = request.getParameter("sign");
			if(super.decodeSign(sign, session.getId())){
				ActivityTemplateSetting setting = activityTemplateSettingService.selectByPrimaryKey(sign.split("_")[1]);
				setting.setSign(sign);
				model.addAttribute("setting", setting);
				return "/activitytemplate/activitytemplatesettingedit";
			}else{
				return Constant.AJAX_DECODEREFUSE;
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.AJAX_ACTIONFAILD;
		}
	}
	
	/**
	 * 修改参数
	 * @param request
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/Update")
	@ResponseBody
	public Map<String,Object> update(HttpServletRequest request,Model model,HttpSession session){
		try {
			ActivityTemplateSetting setting = super.getRequestParamters(request, ActivityTemplateSetting.class);
			if(super.decodeSign(setting.getSign(), session.getId())){
				setting.setActivitysettingcode(Integer.parseInt(setting.getSign().split("_")[1]));
				activityTemplateSettingService.tc_updateActivityTemplateSetting(setting);
				return packJSON(Constant.BooleanByte.YES, "修改成功");
			}else{
				return packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	/**
	 * 删除参数
	 * @return
	 */
	@RequestMapping("/Delete")
	@ResponseBody
	public Map<String,Object> delete(HttpServletRequest request ,HttpSession session){
		try {
			String[] sign = request.getParameterValues("sign[]");
			if(sign==null||sign.length==0) 
				return this.packJSON(Constant.BooleanByte.NO, "请选择需要删除的数据");
			
			List<String> activitysettingcodes = new ArrayList<String>();
			for (String s : sign) {
				if(super.decodeSign(s, session.getId())){
					activitysettingcodes.add(s.split("_")[1]);
				}else{
					return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
				}
			}
			activityTemplateSettingService.tc_deleteBatchActivityTemplateSetting(activitysettingcodes);
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

}
