package com.maven.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.constant.Constant;
import com.maven.entity.EnterpriseBrandContact;
import com.maven.entity.EnterpriseEmployee;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.EnterpriseBrandContactService;

@RequestMapping("/brandContact")
@Controller
public class EnterpriseBrandContactController extends BaseController {
	
	private static LoggerManager log = LoggerManager.getLogger(
			EnterpriseBrandNoticController.class.getName(), OutputManager.LOG_ENTERPRISEBRANDCONTACT);
	
	@Autowired
	private EnterpriseBrandContactService enterpriseBrandContactService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request,Model model){
		model.addAttribute("brandcode", request.getParameter("brandcode"));
		model.addAttribute("brandname", request.getParameter("brandname"));
		return "/enterprise/brandContactIndex";
	}
	
	@RequestMapping("/addBrandContact")
	public String addBrandContact(HttpServletRequest request,Model model){
		try {
			model.addAttribute(Constant.MENU_RELATION,request.getAttribute(Constant.MENU_RELATION));
			Map<String,Object> parames = getRequestParamters(request);
				try {
					if(parames.containsKey("contactcode")){
						EnterpriseBrandContact brandContact = enterpriseBrandContactService.getBrandContact(parames);
						model.addAttribute("brandContact", brandContact);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				model.addAttribute("brandcode", parames.get("brandcode"));
				model.addAttribute("brandname", parames.get("brandname"));
				return "/enterprise/addBrandContact";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	@RequestMapping("/updBrandContact")
	public String updaBrandContact(HttpSession session,HttpServletRequest request,Model model){
		try {
			model.addAttribute(Constant.MENU_RELATION,request.getAttribute(Constant.MENU_RELATION));
			Map<String,Object> parames = getRequestParamters(request);
			String sign = request.getParameter("sign");
			//解密标识字段的值
			boolean mark = this.decodeSign(sign, session.getId());
			if(mark){
				if(parames.containsKey("contactcode")){
					EnterpriseBrandContact brandContact = enterpriseBrandContactService.getBrandContact(parames);
					brandContact.setSign(sign);
					model.addAttribute("brandContact", brandContact);
				}
				model.addAttribute("brandcode", parames.get("brandcode"));
				model.addAttribute("brandname", parames.get("brandname"));
				return "/enterprise/addBrandContact";
			}else{
				return Constant.PAGE_DECODEREFUSE;
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	@RequestMapping("/queryBrandContact")
	@ResponseBody
	public Map<String,Object> queryBrandContact(HttpServletRequest request,HttpSession session){
		try {
			Map<String,Object> parames = getRequestParamters(request);
			EnterpriseEmployee employee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			parames.put("enterprisecode", employee.getEnterprisecode());
			List<EnterpriseBrandContact> list = enterpriseBrandContactService.queryBrandContact(parames);
			Map<String,String> s = new HashMap<String, String>();
			s.put("contactcode", "sign");
			super.encryptSignTarget(list, session.getId(), s);
			int count = enterpriseBrandContactService.queryBrandContactCount(parames);
			return super.formatPagaMap(list, count);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	
	@RequestMapping("/saveBrandContact")
	@ResponseBody
	public Map<String,Object> saveBrandContact(HttpServletRequest request,HttpSession session,@ModelAttribute EnterpriseBrandContact brandContact){
		try {
			EnterpriseEmployee __ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			brandContact.setEnterprisecode(__ee.getEnterprisecode());
			enterpriseBrandContactService.saveBrandContact(brandContact);
			return super.packJSON(Constant.BooleanByte.YES, "添加成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	@RequestMapping("/updateBrandContact")
	@ResponseBody
	public Map<String,Object> updateBrandContact(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseBrandContact  enterpriseBrandContact = super.getRequestParamters(request, EnterpriseBrandContact.class);
			if(super.decodeSign(enterpriseBrandContact.getSign(), session.getId())){
				//调用保存方法
				enterpriseBrandContact.setContactcode(enterpriseBrandContact.getSign().split("_")[1]);
				enterpriseBrandContactService.updateBrandContact(enterpriseBrandContact);
				return super.packJSON(Constant.BooleanByte.YES, "操作成功");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	@RequestMapping("/deleteBrandContact")
	@ResponseBody
	public Map<String,Object> deleteBrandContact(HttpServletRequest request,HttpSession session){
		try {
			String deleteCode = request.getParameter("deleteCode");
			boolean mark = this.decodeSign(deleteCode, session.getId());
			String[] array = deleteCode.split(",");
			if(mark){
				for (int i = 0; i < array.length; i++) {
					array[i]=array[i].split("_")[1];
				}
				enterpriseBrandContactService.deleteBrandContact(array);
				return packJSON(Constant.BooleanByte.YES, "删除成功");
			}else{
				 return packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
		
	}
	
	@RequestMapping("/deleteBatchBrandContact")
	@ResponseBody
	public Map<String,Object> deletBatchBrandContact(HttpServletRequest request,HttpSession session){
		try {
			String temp = request.getParameter("sign");
			String[] array = temp.split(",");
			boolean mark = this.decodeSign(array, session.getId());
			if(mark){
				for (int i = 0; i < array.length; i++) {
					array[i]=array[i].split("_")[1];
				}
				enterpriseBrandContactService.deleteBrandContact(array);
				return super.packJSON(Constant.BooleanByte.YES, "删除成功");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
			
	}	
	@Override
	public LoggerManager getLogger() {
		return log;
	}

}
