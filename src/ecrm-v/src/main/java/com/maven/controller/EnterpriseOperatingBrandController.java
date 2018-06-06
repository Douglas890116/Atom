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
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.UserLogs;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.EnterpriseOperatingBrandService;
import com.maven.service.EnterpriseService;
import com.maven.service.UserLogsService;
import com.maven.util.BeanToMapUtil;

@Controller
@RequestMapping("/EOBrand/")
public class EnterpriseOperatingBrandController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			EnterpriseOperatingBrandController.class.getName(), OutputManager.LOG_ENTERPRISEOPERATINGBRAND);
	
	@Autowired
	private EnterpriseService enterpriseService;
	/** 品牌 */
	@Autowired
	private EnterpriseOperatingBrandService enterpriseOperatingBrandService;
	@Autowired
	private UserLogsService userLogsService;
	
	@RequestMapping("/add")
	public String add(Model model ,HttpSession session){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Enterprise enterprise = enterpriseService.selectByPrimaryKey(ee.getEnterprisecode());
			model.addAttribute("enterprise", enterprise);
			return "/enterprise/brand_add";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	@RequestMapping("/Edit")
	@OperationLog(OparetionDescription.ENTERPRISEOPERATINGBRAND_EDIT)
	public String edit(HttpServletRequest request, Model model ,HttpSession session){
		try {
			String brandcode = request.getParameter("brandcode");
			if(this.decodeSign(brandcode, session.getId())){
				EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
				Enterprise enterprise = enterpriseService.selectByPrimaryKey(ee.getEnterprisecode());
				EnterpriseOperatingBrand brand = enterpriseOperatingBrandService.takeBrandByPrimaryKey(brandcode.split("_")[1]);
				brand.setBrandcode(brandcode);
				model.addAttribute("enterprise", enterprise);
				model.addAttribute("brand", brand);
				return "/enterprise/brand_edit";
			}else{
				return Constant.PAGE_DECODEREFUSE;
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	@RequestMapping("/list")
	public String list(HttpSession session,Model model){
		return "/enterprise/brand_list";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	@RequestInterval(millinsecond=3000)
	public Map<String,Object> save(HttpServletRequest request , HttpServletResponse response , HttpSession session){
		try {
			Map<String,Object> object = getRequestParamters(request);
			//获取当前登录用户的信息
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			if(!employee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.企业号.value)){
				return super.packJSON(Constant.BooleanByte.NO, "您不是企业号,无法创建品牌");
			}

			//品牌信息
			EnterpriseOperatingBrand brand = BeanToMapUtil.convertMap(object, EnterpriseOperatingBrand.class);
			brand.setEnterprisecode(employee.getEnterprisecode());
		
			//创建品牌
			enterpriseOperatingBrandService.tc_createBrand(brand);
			
			userLogsService.addActivityBetRecord(new UserLogs(employee.getEnterprisecode(), employee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.品牌信息业务, "创建品牌:"+brand.getBrandname(), employee.getLoginaccount(), null));
			
			
			return this.packJSON(Constant.BooleanByte.YES, "创建品牌成功");
		}catch(LogicTransactionRollBackException e){
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, e.getMessage());	
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, "创建品牌失败，请稍后尝试");
		}
	}
	
	
	@RequestMapping("/data")
	@ResponseBody
	public Map<String,Object> data(HttpServletRequest request , HttpSession session){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String,Object> obj = getRequestParamters(request);
			obj.put("enterprisecode", ee.getEnterprisecode());
			List<EnterpriseOperatingBrand> rows =  enterpriseOperatingBrandService.getAllBrand(obj);
			int rowCount = enterpriseOperatingBrandService.getAllBrandCount(obj);
			super.encryptSign(rows, session.getId(), "brandcode","enterprisecode");
			return formatPagaMap(rows,rowCount);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	@RequestMapping("/Update")
	@ResponseBody
	@OperationLog(OparetionDescription.ENTERPRISEOPERATINGBRAND_EDIT)
	public Map<String,Object> update(HttpServletRequest request ,HttpSession session){
		try {
			EnterpriseOperatingBrand brand = this.getRequestParamters(request, EnterpriseOperatingBrand.class);
			if(this.decodeSign(brand.getBrandcode(), session.getId())){
				brand.setBrandcode(brand.getBrandcode().split("_")[1]);
				enterpriseOperatingBrandService.updateBrand(brand);
				
				EnterpriseEmployee employee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
				userLogsService.addActivityBetRecord(new UserLogs(employee.getEnterprisecode(), employee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.品牌信息业务, "更新品牌:"+brand.getBrandcode(), employee.getLoginaccount(), null));
				
				return this.packJSON(Constant.BooleanByte.YES, "编辑信息已保存");
			}else{
				return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	@RequestMapping("/UpdateDefualtShip")
	@ResponseBody
	@OperationLog(OparetionDescription.ENTERPRISEOPERATINGBRAND_EDIT)
	public Map<String,Object> updateDefualtShip(HttpServletRequest request ,HttpSession session){
		try {
			EnterpriseOperatingBrand brand = this.getRequestParamters(request, EnterpriseOperatingBrand.class);
			if(this.decodeSign(brand.getBrandcode(), session.getId())){
				brand.setBrandcode(brand.getBrandcode().split("_")[1]);
				enterpriseOperatingBrandService.updateDefualtChip(brand);
				
				EnterpriseEmployee employee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
				userLogsService.addActivityBetRecord(new UserLogs(employee.getEnterprisecode(), employee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.品牌信息业务, "更新品牌默认打码:"+brand.getBrandcode(), employee.getLoginaccount(), null));
				
				return this.packJSON(Constant.BooleanByte.YES, "默认打码已修改");
			}else{
				return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	@RequestMapping("/Delete")
	@ResponseBody
	@OperationLog(OparetionDescription.ENTERPRISEOPERATINGBRAND_DELETE)
	public Map<String,Object> delete(HttpServletRequest request ,HttpSession session){
		try {
			String[] sign = request.getParameterValues("sign[]");
			if(sign==null||sign.length==0) {
				return this.packJSON(Constant.BooleanByte.NO, "请选择需要删除的数据");
			}
			
			EnterpriseEmployee employee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			List<String> brandcodes = new ArrayList<String>();
			for (String s : sign) {
				if(super.decodeSign(s, session.getId())){
					brandcodes.add(s.split("_")[1]);
					enterpriseOperatingBrandService.deleteBrand(brandcodes);
					
					
					userLogsService.addActivityBetRecord(new UserLogs(employee.getEnterprisecode(), employee.getEmployeecode(), null, 
						     UserLogs.Enum_operatype.品牌信息业务, "删除品牌:"+s.split("_")[1], employee.getLoginaccount(), null));
				}else{
					return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
				}
			}
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
