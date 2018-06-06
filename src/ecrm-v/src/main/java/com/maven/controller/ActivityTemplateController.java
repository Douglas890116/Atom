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
import com.maven.entity.ActivityTemplate;
import com.maven.entity.UserLogs;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.ActivityTemplateService;

@Controller
@RequestMapping("/ActivityTemplate")
public class ActivityTemplateController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			ActivityTemplateController.class.getName(), OutputManager.LOG_ACTIVITYTEMPLATE);
	
	@Autowired
	private ActivityTemplateService activityTemplateService;
	
	@RequestMapping("/Show")
	public  String show(){
		return "/activitytemplate/activitytemplatelist";
	}
	
	@RequestMapping("/Add")
	public String add(Model model,HttpSession session){
		try {
			return "/activitytemplate/activitytemplateadd";
		} catch (Exception e) {
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	@RequestMapping("/Edit")
	public String edit(HttpServletRequest request, HttpSession session ,Model model){
		try {
			String sign = request.getParameter("sign");
			if(super.decodeSign(sign, session.getId())){
				ActivityTemplate t = activityTemplateService.selectByPrimaryKey(sign.split("_")[1]);
				t.setSign(sign);
				model.addAttribute("template", t);
				return "/activitytemplate/activitytemplateedit";
			}else{
				return Constant.PAGE_DECODEREFUSE;
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	@RequestMapping("/Data")
	@ResponseBody
	public Map<String,Object> data(HttpServletRequest request,HttpSession session){
		try {
			Map<String,Object> object = super.getRequestParamters(request);
			List<ActivityTemplate> list = activityTemplateService.selectAll(object);
			int count = activityTemplateService.selectAllCount(object);
			Map<String,String> s = new HashMap<String, String>();
			s.put("activitycode", "sign");
			super.encryptSignTarget(list,session.getId(),s);
			return super.formatPagaMap(list, count);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	@RequestMapping("/Save")
	@ResponseBody
	public Map<String,Object> save(HttpServletRequest request){
		try {
			ActivityTemplate template = super.getRequestParamters(request, ActivityTemplate.class);
			activityTemplateService.tc_addActivityTemplate(template);
			return packJSON(Constant.BooleanByte.YES, "模板创建成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return packJSON(Constant.BooleanByte.NO, "模板创建失败，请稍后尝试");
		}
	}
	
	@RequestMapping("/Update")
	@ResponseBody
	public Map<String,Object> update(HttpServletRequest request,HttpSession session){
		try {
			ActivityTemplate template = super.getRequestParamters(request, ActivityTemplate.class);
			if(super.decodeSign(template.getSign(), session.getId())){
				template.setActivitycode(Integer.parseInt(template.getSign().split("_")[1]));
				activityTemplateService.tc_editActivityTemplate(template);
				return packJSON(Constant.BooleanByte.YES, "模板编辑成功");
			}else{
				return packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return packJSON(Constant.BooleanByte.NO, "模板编辑失败，请稍后尝试");
		}
	}
	
	@RequestMapping("/Delete")
	@ResponseBody
	public Map<String,Object> delete(HttpServletRequest request,HttpSession session){
		try {
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
			activityTemplateService.tc_deleteActivityTemplate(activitycodes);
			return this.packJSON(Constant.BooleanByte.YES, "删除成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
		}
	}
	
	/**
	 * 查看模板
	 * @return
	 */
	@RequestMapping("/View")
	@ResponseBody
	public Map<String,Object> viewTemple(HttpServletRequest request,HttpSession session){
		try {
			Map<String,Object> result = new HashMap<String, Object>();
			String sign = request.getParameter("sign");
			if(super.decodeSign(sign, session.getId())){
				ActivityTemplate t = activityTemplateService.selectByPrimaryKey(sign.split("_")[1]);
				result.put("status", Constant.BooleanByte.YES);
				result.put("template", t);
			}else{
				result.put("status", Constant.BooleanByte.NO);
				result.put("message", Constant.AJAX_DECODEREFUSE);
			}
			return result;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}

	@Override
	public LoggerManager getLogger() {
		return log;
	}
	

}
