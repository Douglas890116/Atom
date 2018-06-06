package com.maven.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.constant.Constant;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseWebview;
import com.maven.entity.WebviewTemplate;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.EnterpriseService;
import com.maven.service.EnterpriseWebviewService;
import com.maven.service.WebviewTemplateService;

@RequestMapping("/brandTemplate")
@Controller
public class WebviewTemplateController extends BaseController{
	
	@Autowired
	private WebviewTemplateService webviewTemplateServiceImpl;
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private EnterpriseWebviewService enterpriseWebviewImpl;
	
	private static LoggerManager log = LoggerManager.getLogger(
			EnterpriseBrandNoticController.class.getName(), OutputManager.LOG_WEBVIWETEMPLATE);
	
	@RequestMapping("/index")
	public String index(){
		return "/enterprise/templateIndex";
	}
	
	@RequestMapping("/addTemplate")
	public String addTemplate(HttpServletRequest request,Model model){
		model.addAttribute(Constant.MENU_RELATION,request.getAttribute(Constant.MENU_RELATION));
		Map<String,Object> parames = getRequestParamters(request);
		try {
			if(parames.containsKey("webtemplatecode")){
				WebviewTemplate webviewTemplate = webviewTemplateServiceImpl.getWebviewTemplate(parames);
				model.addAttribute("template", webviewTemplate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/enterprise/addTemplate";
	}
	
	@RequestMapping("/updTemplate")
	public String updTemplate(HttpSession session,HttpServletRequest request,Model model){
		try {
			Map<String,Object> parames = getRequestParamters(request);
			String sign = request.getParameter("sign");
			//解密标识字段的值
			boolean mark = this.decodeSign(sign, session.getId());
			if(mark){
				try {
					if(parames.containsKey("webtemplatecode")){
						WebviewTemplate webviewTemplate = webviewTemplateServiceImpl.getWebviewTemplate(parames);
						webviewTemplate.setSign1(sign);
						model.addAttribute("template", webviewTemplate);
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.Error(e.getMessage(), e);
				}
				return "/enterprise/addTemplate";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constant.PAGE_DECODEREFUSE;
		
		
	}
	
	
	@RequestMapping("/queryTemplateData")
	@ResponseBody
	public Map<String,Object> queryTemplateData(HttpServletRequest request,HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> parames = getRequestParamters(request);
		List<WebviewTemplate> list = webviewTemplateServiceImpl.queryTemplateData(parames);
		Map<String,String> s = new HashMap<String, String>();
		s.put("webtemplatecode", "sign1");
		super.encryptSignTarget(list, session.getId(), s);
		int count = webviewTemplateServiceImpl.queryTemplateDataCount(parames);
		map.put("rows", list);
		map.put("results", count);
		return map;
	}
	
	
	@RequestMapping("/saveTemplate")
	@ResponseBody
	public Map<String,Object> saveTemplate(HttpServletRequest request,HttpSession session,@ModelAttribute WebviewTemplate webviewTemplate){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			webviewTemplateServiceImpl.saveTemplate(webviewTemplate);
			map.put("status", 1);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("status", 0);
		return map;
	}
	
	@RequestMapping("/updateTemplate")
	@ResponseBody
	public Map<String,Object> updateTemplate(HttpServletRequest request,HttpSession session,@ModelAttribute WebviewTemplate webviewTemplate){
		try {
			WebviewTemplate  webviewtemplate= super.getRequestParamters(request, WebviewTemplate.class);
			if(super.decodeSign(webviewtemplate.getSign1(), session.getId())){
				//调用保存方法
				webviewTemplateServiceImpl.updateTemplate(webviewtemplate);
				return super.packJSON(Constant.BooleanByte.YES, "操作成功");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	@RequestMapping("/deleteBatchTemplate")
	@ResponseBody
	public Map<String,Object> deleteTemplate(HttpServletRequest request,HttpSession session){
		try {
			String temp = request.getParameter("sign");
			String[] array = temp.split(",");
			boolean mark = this.decodeSign(array, session.getId());
			if(mark){
				for (int i = 0; i < array.length; i++) {
					array[i]=array[i].split("_")[1];
				}
				webviewTemplateServiceImpl.deleteTemplate(array);
				return super.packJSON(Constant.BooleanByte.YES, "删除成功");
			}else{
				 packJSON(Constant.BooleanByte.NO, "删除失败");
			}
		}catch (Exception e) {
				e.printStackTrace();
				log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
		return packJSON(Constant.BooleanByte.NO, "删除失败");
	}
	
	@RequestMapping("/deleteTemplate")
	@ResponseBody
	public Map<String,Object> deleteBatchTemplate(HttpServletRequest request,HttpSession session){
		try {
			String deleteCode = request.getParameter("sign");
			boolean mark = this.decodeSign(deleteCode, session.getId());
			String[] array = deleteCode.split(",");
			if(mark){
				for (int i = 0; i < array.length; i++) {
					array[i]=array[i].split("_")[1];
				}
				webviewTemplateServiceImpl.deleteTemplate(array);
				return packJSON(Constant.BooleanByte.YES, "删除成功");
			}else{
				 packJSON(Constant.BooleanByte.NO, "删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
		return packJSON(Constant.BooleanByte.NO, "删除失败");
		
	}
	
	@RequestMapping("/contactIndex")
	public String contactIndex(){
		return "/enterprise/templateContactIndex";
	}
	
	@RequestMapping("/templateContactAdd")
	public String templateContactAdd(HttpServletRequest request, HttpSession session, Model model){
		try {
			Map<String,Object> parames = getRequestParamters(request);
			String sign = request.getParameter("sign");
			//解密标识字段的值
			boolean mark = this.decodeSign(sign, session.getId());
			if(mark){
				try {
					if(parames.containsKey("enterprisecode")){
						Enterprise enterprise = enterpriseService.selectByPrimaryKey(String.valueOf(parames.get("enterprisecode")));
						enterprise.setSign(sign);
						model.addAttribute("enterprise", enterprise);
					}
					//获取所有系统的模板
					//List<WebviewTemplate> wtList = webviewTemplateServiceImpl.queryTemplateData(null);
					//model.addAttribute("wtList", wtList);
				} catch (Exception e) {
					e.printStackTrace();
					log.Error(e.getMessage(), e);
				}
				return "/enterprise/templateContactAdd";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constant.PAGE_DECODEREFUSE;
		
	}
	
	@RequestMapping("/contactWebviews")
	@ResponseBody
	public Map<String, Object> contactWebviews(HttpServletRequest request, HttpSession session){
		Map<String, Object> parames = getRequestParamters(request);
		Map<String, Object> result = new HashMap<String, Object>();
		//获取所有系统的模板
		Map<String, Object> paramesForSitetype = new HashMap<String, Object>();
		if (parames.containsKey("sitetype")){
			paramesForSitetype.put("sitetype", parames.get("sitetype"));
		}
		List<WebviewTemplate> wtList = webviewTemplateServiceImpl.queryTemplateData(paramesForSitetype);
		//获取当前enterprise已经有的模板
		List<EnterpriseWebview> ewList = new ArrayList<EnterpriseWebview>();
		if (parames.containsKey("sign")){
			if (this.decodeSign(parames.get("sign").toString(), session.getId())){
				ewList = enterpriseWebviewImpl.queryContactData(parames);
			}
		}
		
		result.put("wtList", wtList);
		result.put("ewList", ewList);
		return result;
	}
	
	@RequestMapping("/saveTemplateContact")
	@ResponseBody
	public Map<String, Object> saveTemplateContact(HttpServletRequest request, HttpServletResponse response, HttpSession session){
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String,Object> parames = getRequestParamters(request);
		try {
			//删除目前所有enterprise下的所有模板,然后重新保存一次
			if (parames.get("enterprisecode") != null){
				enterpriseWebviewImpl.deleteByParameter(parames);		
				
				if (parames.get("hide") != null){
					String enterprisecode = String.valueOf(parames.get("enterprisecode"));
					Set<String> templatecodes = new HashSet<String>( Arrays.asList( String.valueOf(parames.get("hide")).split(",")));
					//List<EnterpriseWebview> ews = new ArrayList<EnterpriseWebview>();
					for (String tempcode : templatecodes) {
						EnterpriseWebview ew = new EnterpriseWebview();
						ew.setEnterprisecode(enterprisecode);
						ew.setWebtemplatecode(tempcode);
						ew.setSitetype(parames.get("sitetype").toString());
						enterpriseWebviewImpl.saveContact(ew);
					}
				}
			}
			res.put("status", 1);
			return res;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		res.put("status", 0);
		return res;
	}
	
	@RequestMapping("/queryTemplateContactData")
	@ResponseBody
	public Map<String,Object> queryTemplateContactData(HttpServletRequest request,HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> parames = getRequestParamters(request);
		try {
			List<Enterprise> enterpriseList = enterpriseService.selectAll(parames);
			List<EnterpriseWebview> list = enterpriseWebviewImpl.selectAll(parames);
			List<WebviewTemplate> wtList = webviewTemplateServiceImpl.queryTemplateData(null);
			
			//组装企业目前已有模板，显示用
			for (Enterprise e1 : enterpriseList) {
				Set<String> e1views = new HashSet<String>();
				StringBuffer sbu = new StringBuffer();
				for (EnterpriseWebview ew1 : list) { 
					if (e1.getEnterprisecode().equals(ew1.getEnterprisecode())){
						e1views.add(ew1.getWebtemplatecode());
					}
				}
				
				for (WebviewTemplate wt1 : wtList) {
					if (e1views.contains(wt1.getWebtemplatecode())){
						sbu.append(wt1.getTemplatename() + ",");
					}
				}
				
				e1.setWebviews(sbu.toString());
			}
			
			Map<String,String> s = new HashMap<String, String>();
			s.put("enterprisecode", "sign");
			super.encryptSignTarget(enterpriseList, session.getId(), s);
			int count = enterpriseService.selectAllCount(parames);
			map.put("rows", enterpriseList);
			map.put("results", count);
			return map;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	/** 代理模板相关 start */
	
	@RequestMapping("/agentIndex")
	public String agentIndex(){
		return "/enterprise/agentTemplateIndex";
	}
	
	@RequestMapping("/agentContactIndex")
	public String agentContactIndex(){
		return "/enterprise/agentTemplateContactIndex";
	}
	
	@RequestMapping("/addAgentTemplate")
	public String addAgentTemplate(HttpServletRequest request,Model model){
		model.addAttribute(Constant.MENU_RELATION,request.getAttribute(Constant.MENU_RELATION));
		Map<String,Object> parames = getRequestParamters(request);
		try {
			if(parames.containsKey("webtemplatecode")){
				WebviewTemplate webviewTemplate = webviewTemplateServiceImpl.getWebviewTemplate(parames);
				model.addAttribute("template", webviewTemplate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/enterprise/addAgentTemplate";
	}
	
	@RequestMapping("/updAgentTemplate")
	public String updAgentTemplate(HttpSession session,HttpServletRequest request,Model model){
		try {
			Map<String,Object> parames = getRequestParamters(request);
			String sign = request.getParameter("sign");
			//解密标识字段的值
			boolean mark = this.decodeSign(sign, session.getId());
			if(mark){
				try {
					if(parames.containsKey("webtemplatecode")){
						WebviewTemplate webviewTemplate = webviewTemplateServiceImpl.getWebviewTemplate(parames);
						webviewTemplate.setSign1(sign);
						model.addAttribute("template", webviewTemplate);
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.Error(e.getMessage(), e);
				}
				return "/enterprise/addAgentTemplate";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constant.PAGE_DECODEREFUSE;
		
		
	}
	
	@RequestMapping("/agentTemplateContactAdd")
	public String agentTemplateContactAdd(HttpServletRequest request, HttpSession session, Model model){
		try {
			Map<String,Object> parames = getRequestParamters(request);
			String sign = request.getParameter("sign");
			//解密标识字段的值
			boolean mark = this.decodeSign(sign, session.getId());
			if(mark){
				try {
					if(parames.containsKey("enterprisecode")){
						Enterprise enterprise = enterpriseService.selectByPrimaryKey(String.valueOf(parames.get("enterprisecode")));
						enterprise.setSign(sign);
						model.addAttribute("enterprise", enterprise);
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.Error(e.getMessage(), e);
				}
				return "/enterprise/agentTemplateContactAdd";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constant.PAGE_DECODEREFUSE;
		
	}
	
	/** 代理模板相关 end */
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
	
}
