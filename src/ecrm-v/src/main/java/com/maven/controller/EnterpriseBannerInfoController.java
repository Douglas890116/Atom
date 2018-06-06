/**
 * 
 */
package com.maven.controller;

import java.util.ArrayList;
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
import com.maven.annotation.RequestInterval;
import com.maven.constant.Constant;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseBannerInfo;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.EnterpriseBannerInfoService;
import com.maven.service.EnterpriseOperatingBrandService;
import com.maven.service.EnterpriseService;
import com.maven.util.BeanToMapUtil;

@Controller
@RequestMapping("/banner")
public class EnterpriseBannerInfoController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			EnterpriseBannerInfoController.class.getName(), OutputManager.LOG_ENTERPRISEOPERATINGBRAND);
	
	@Autowired
	private EnterpriseService enterpriseService;
	/** 品牌 */
	@Autowired
	private EnterpriseOperatingBrandService enterpriseOperatingBrandService;
	@Autowired
	private EnterpriseBannerInfoService enterpriseBannerInfoService;
	
	
	
	
	
	@RequestMapping("/add")
	public String add(Model model ,HttpSession session){
		try {
			//查所有品牌
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			List<EnterpriseOperatingBrand> list = enterpriseOperatingBrandService.getEnterpriseBrand(ee.getEnterprisecode());
			model.addAttribute("list", list);
			return "/enterprise/banner_add";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	@RequestMapping("/edit")
	@OperationLog(OparetionDescription.ENTERPRISEOPERATINGBRAND_EDIT)
	public String edit(HttpServletRequest request, Model model ,HttpSession session){
		try {
			String brandcode = request.getParameter("lsh");
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Enterprise enterprise = enterpriseService.selectByPrimaryKey(ee.getEnterprisecode());
			EnterpriseBannerInfo brand = enterpriseBannerInfoService.selectByPrimaryKey(brandcode);
			brand.setBrandcode(brandcode);
			model.addAttribute("enterprise", enterprise);
			model.addAttribute("banner", brand);
			
			List<EnterpriseOperatingBrand> list = enterpriseOperatingBrandService.getEnterpriseBrand(ee.getEnterprisecode());
			model.addAttribute("list", list);
			
			return "/enterprise/banner_edit";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	@RequestMapping("/index")
	public String list(HttpSession session,Model model){
		EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
		List<EnterpriseOperatingBrand> list;
		try {
			list = enterpriseOperatingBrandService.getEnterpriseBrand(ee.getEnterprisecode());
			model.addAttribute("list", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/enterprise/banner_list";
	}
	
	@RequestMapping("/data")
	@ResponseBody
	public Map<String,Object> data(HttpServletRequest request , HttpSession session){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String,Object> obj = getRequestParamters(request);
			obj.put("enterprisecode", ee.getEnterprisecode());
			
			List<EnterpriseBannerInfo> rows = enterpriseBannerInfoService.selectAll(obj);
			int rowCount = enterpriseBannerInfoService.selectAllCount(obj);
			return formatPagaMap(rows,rowCount);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	@RequestMapping("/save")
	@ResponseBody
	@RequestInterval(millinsecond=3000)
	public Map<String,Object> save(HttpServletRequest request , HttpServletResponse response , HttpSession session){
		try {
			Map<String,Object> object = getRequestParamters(request);
			//获取当前登录用户的信息
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			
			String logopath = object.get("logopath").toString();
			logopath = logopath.replaceAll("http://img.hyzonghe.net:80/", "https://img.hyzonghe.net/");
			
			EnterpriseBannerInfo brand = BeanToMapUtil.convertMap(object, EnterpriseBannerInfo.class);
			brand.setEnterprisecode(employee.getEnterprisecode());
			brand.setImgpath(logopath);
			enterpriseBannerInfoService.addActivityBetRecord(brand);//
			return this.packJSON(Constant.BooleanByte.YES, "banner保存成功");
		}catch(LogicTransactionRollBackException e){
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, e.getMessage());	
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, "banner保存失败，请稍后尝试");
		}
	}
	
	
	
	
	@RequestMapping("/update")
	@ResponseBody
	@OperationLog(OparetionDescription.ENTERPRISEOPERATINGBRAND_EDIT)
	public Map<String,Object> update(HttpServletRequest request ,HttpSession session){
		try {
			Map<String,Object> object = getRequestParamters(request);
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			
			String logopath = object.get("logopath").toString();
			logopath = logopath.replaceAll("http://img.hyzonghe.net:80/", "https://img.hyzonghe.net/");
			
			EnterpriseBannerInfo brand = this.getRequestParamters(request, EnterpriseBannerInfo.class);
			brand.setEnterprisecode(employee.getEnterprisecode());
			brand.setImgpath(logopath);
			enterpriseBannerInfoService.updateBetRecord(brand);
			return this.packJSON(Constant.BooleanByte.YES, "编辑信息已保存");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	
	@RequestMapping("/delete")
	@ResponseBody
	@OperationLog(OparetionDescription.ENTERPRISEOPERATINGBRAND_DELETE)
	public Map<String,Object> delete(HttpServletRequest request ,HttpSession session){
		try {
			String[] sign = request.getParameterValues("sign[]");
			if(sign==null||sign.length==0) 
				return this.packJSON(Constant.BooleanByte.NO, "请选择需要删除的数据");
			
			List<String> brandcodes = new ArrayList<String>();
			for (String s : sign) brandcodes.add(s);
			enterpriseBannerInfoService.deleteBetRecord(brandcodes);
			return this.packJSON(Constant.BooleanByte.YES, "删除成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
		}
		
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
