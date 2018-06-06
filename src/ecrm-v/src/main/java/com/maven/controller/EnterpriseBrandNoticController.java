package com.maven.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.annotation.OperationLog;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.EnterpriseBrandNotic;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.UserLogs;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.EnterpriseBrandNoticService;
import com.maven.service.EnterpriseOperatingBrandService;
import com.maven.service.UserLogsService;

@Controller
@RequestMapping("/BrandNotic")
public class EnterpriseBrandNoticController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			EnterpriseBrandNoticController.class.getName(), OutputManager.LOG_ENTERPRISEBRANDNOTIC);
	
	@Autowired
	private EnterpriseBrandNoticService enterpriseBrandNoticService;
	
	@Autowired
	private EnterpriseOperatingBrandService  enterpriseOperatingBrandService;
	@Autowired
	private UserLogsService userLogsService;
	
	
	@RequestMapping("/index")
	public String index(){
		return "/notic/noticpublish";
	}
	
	@RequestMapping("/add")
	public String add(HttpSession session,Model model){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			EnterpriseOperatingBrand brand = new EnterpriseOperatingBrand();
			brand.setEnterprisecode(ee.getEnterprisecode());
			List<EnterpriseOperatingBrand> brands = enterpriseOperatingBrandService.select(brand);
			model.addAttribute("brands", brands);
			return "/notic/noticpublicadd";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
		
	}
	
	@RequestMapping("/noticshow")
	public String noticshow(HttpSession session,Model model){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			EnterpriseBrandNotic notic = new EnterpriseBrandNotic();
			notic.setEnterprisecode(ee.getEnterprisecode());
			notic.setBrandcode(ee.getBrandcode());
			List<EnterpriseBrandNotic> list = enterpriseBrandNoticService.takeUserNotic(notic);
			model.addAttribute("notics", list);
			return "/notic/noticshow";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	@RequestMapping("/data")
	@ResponseBody
	public Map<String,Object> data(HttpServletRequest request , HttpServletResponse response , HttpSession session){
		try {
			Map<String,Object> object = getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			
			/*********非超级管理员时只能查询本团队内的数据************/
			if(!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())){
				object.put("enterprisecode", ee.getEnterprisecode());
			}
			
			List<EnterpriseBrandNotic> list = enterpriseBrandNoticService.takeNotic(object);
			super.encryptSign(list, session.getId(), new String[]{"noticcode"});
			int count = enterpriseBrandNoticService.takeNoticCount(object);
			return super.formatPagaMap(list, count);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	@RequestMapping("/save")
	@ResponseBody
	public Map<String,Object> save(HttpServletRequest request,HttpSession session,Model model){
		try {
			Map<String,Object> object = getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			object.put("enterprisecode", ee.getEnterprisecode());
			object.put("notictype", "1");
			enterpriseBrandNoticService.addNotic(object);
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.运营公告业务, "创建公告:"+object.get("title"), ee.getLoginaccount(), null));
			
			return super.packJSON(Constant.BooleanByte.YES, "公告已成功创建");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "创建失败，请稍后尝试："+e.getMessage());
		}
	}
	
	@RequestMapping("/edit")
	public String edit(HttpServletRequest request,Model model,HttpSession session){
		String noticcode = request.getParameter("noticcode");
		try {
			if(super.decodeSign(noticcode, session.getId())){
				String code = noticcode.split("_")[1];
				EnterpriseBrandNotic notic = enterpriseBrandNoticService.takeNoticByCode(code);
				notic.setNoticcode(noticcode);
				EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
				EnterpriseOperatingBrand brand = new EnterpriseOperatingBrand();
				brand.setEnterprisecode(ee.getEnterprisecode());
				List<EnterpriseOperatingBrand> brands = enterpriseOperatingBrandService.select(brand);
				model.addAttribute("brands", brands);
				model.addAttribute("notic", notic);
				return "/notic/noticpublicedit";
			}else{
				return Constant.PAGE_DECODEREFUSE;
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	@RequestMapping("/update")
	@OperationLog(OparetionDescription.BRAND_NOTIC_UPDATE)
	@ResponseBody
	public Map<String,Object> update(HttpServletRequest request,HttpSession session){
		try {
			Map<String,Object> object = getRequestParamters(request);
			String noticcode = request.getParameter("noticcode");
			if(super.decodeSign(noticcode, session.getId())){
				object.put("noticcode", noticcode.split("_")[1]);
				EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
				object.put("enterprisecode", ee.getEnterprisecode());
				enterpriseBrandNoticService.eidtNotic(object);
				
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.运营公告业务, "更改公告:"+object.get("title"), ee.getLoginaccount(), null));
				
				return super.packJSON(Constant.BooleanByte.YES, "编辑公告成功");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "编辑失败，请稍后尝试");
		}
	}
	
	@RequestMapping("/delete")
	@OperationLog(OparetionDescription.BRAND_NOTIC_DELETE)
	@ResponseBody
	public Map<String,Object> delete(HttpServletRequest request,HttpSession session){
		try {
			Map<String,Object> object = getRequestParamters(request);
			String noticcode = request.getParameter("code");
			if(super.decodeSign(noticcode, session.getId())){
				object.put("noticcode", noticcode.split("_")[1]);
				EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
				object.put("brandcode", ee.getBrandcode());
				enterpriseBrandNoticService.deleteNotic(object);
				
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.运营公告业务, "删除公告:"+object.get("title"), ee.getLoginaccount(), null));
				
				return super.packJSON(Constant.BooleanByte.YES, "公告已删除");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		}catch(Exception e){
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "删除失败，请稍后尝试");
		}
		
	}

	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
