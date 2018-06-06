/**
 * 
 */
package com.maven.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.maven.entity.EnterpriseInformationQrcode;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.EnterpriseThirdpartyPayment;
import com.maven.entity.UserLogs;
import com.maven.entity.EnterpriseThirdpartyPayment.Enum_type;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.EnterpriseInformationQrcodeService;
import com.maven.service.EnterpriseOperatingBrandService;
import com.maven.service.EnterpriseService;
import com.maven.service.UserLogsService;
import com.maven.util.BeanToMapUtil;

@Controller
@RequestMapping("/enterpriseqcode/")
public class EnterpriseInformationQrcodeController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			EnterpriseInformationQrcodeController.class.getName(), OutputManager.LOG_ENTERPRISEOPERATINGBRAND);
	
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private EnterpriseInformationQrcodeService enterpriseInformationQrcodeService;
	@Autowired
	private UserLogsService userLogsService;
	
	@RequestMapping("/add")
	public String add(Model model ,HttpSession session){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Enterprise enterprise = enterpriseService.selectByPrimaryKey(ee.getEnterprisecode());
			model.addAttribute("enterprise", enterprise);
			return "/enterprise/qrcode_add";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	@RequestMapping("/edit")
	public String edit(HttpServletRequest request, Model model ,HttpSession session){
		try {
			String brandcode = request.getParameter("lsh");
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Enterprise enterprise = enterpriseService.selectByPrimaryKey(ee.getEnterprisecode());
			EnterpriseInformationQrcode enterpriseInformationQrcode = enterpriseInformationQrcodeService.selectByPrimaryKey(brandcode);
			model.addAttribute("enterprise", enterprise);
			model.addAttribute("lsh", brandcode);
			model.addAttribute("enterpriseInformationQrcode", enterpriseInformationQrcode);
			return "/enterprise/qrcode_edit";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	@RequestMapping("/list")
	public String list(HttpSession session,Model model){
		return "/enterprise/qrcode_list";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	@RequestInterval(millinsecond=3000)
	public Map<String,Object> save(HttpServletRequest request , HttpServletResponse response , HttpSession session){
		try {
			Map<String,Object> object = getRequestParamters(request);
			//获取当前登录用户的信息
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);

			//二维码收款信息
			EnterpriseInformationQrcode brand = BeanToMapUtil.convertMap(object, EnterpriseInformationQrcode.class);
			brand.setEnterprisecode(employee.getEnterprisecode());
			brand.setBrandcode(employee.getBrandcode());
			brand.setQrurl(request.getParameter("logopath"));
			
			//创建二维码
			enterpriseInformationQrcodeService.addBetRecord(brand);
			
			userLogsService.addActivityBetRecord(new UserLogs(employee.getEnterprisecode(), employee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.二维码收款业务, "创建二维码:"+brand.getQraccountname(), employee.getLoginaccount(), null));
			
			
			return this.packJSON(Constant.BooleanByte.YES, "创建收款二维码成功");
		}catch(LogicTransactionRollBackException e){
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, e.getMessage());	
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, "创建收款二维码失败，请稍后尝试");
		}
	}
	
	
	@RequestMapping("/data")
	@ResponseBody
	public Map<String,Object> data(HttpServletRequest request , HttpSession session){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String,Object> obj = getRequestParamters(request);
			obj.put("enterprisecode", ee.getEnterprisecode());
			
			List<EnterpriseInformationQrcode> rows =  enterpriseInformationQrcodeService.selectBetRecord(obj);
			int rowCount = enterpriseInformationQrcodeService.selectBetRecordCount(obj);
			return formatPagaMap(rows,rowCount);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public Map<String,Object> update(HttpServletRequest request ,HttpSession session){
		try {
			EnterpriseInformationQrcode brand = this.getRequestParamters(request, EnterpriseInformationQrcode.class);
			brand.setLsh(new Integer(brand.getLsh().toString()));
			brand.setQrurl(request.getParameter("logopath"));
			enterpriseInformationQrcodeService.updateBetRecord(brand);
			
			EnterpriseEmployee employee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			userLogsService.addActivityBetRecord(new UserLogs(employee.getEnterprisecode(), employee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.二维码收款业务, "更新收款二维码:"+brand.getQraccountname(), employee.getLoginaccount(), null));
			
			return this.packJSON(Constant.BooleanByte.YES, "编辑信息已保存");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String,Object> delete(HttpServletRequest request ,HttpSession session){
		try {
			String[] sign = request.getParameterValues("sign[]");
			if(sign==null||sign.length==0) {
				return this.packJSON(Constant.BooleanByte.NO, "请选择需要删除的数据");
			}
			
			EnterpriseEmployee employee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			List<String> brandcodes = new ArrayList<String>();
			for (String s : sign) {
				brandcodes.add(s);
				enterpriseInformationQrcodeService.deleteBetRecord(brandcodes);
				
				
				userLogsService.addActivityBetRecord(new UserLogs(employee.getEnterprisecode(), employee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.二维码收款业务, "删除收款二维码:"+s, employee.getLoginaccount(), null));
			}
			return this.packJSON(Constant.BooleanByte.YES, "删除成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
		}
		
	}
	
	
	/**
	 * 启用与禁用方法
	 * @param request
	 * @return
	 */
	@RequestMapping("/enableDisable")
	@ResponseBody
	public Map<String,Object> enableDisable(HttpServletRequest request,HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			
			EnterpriseInformationQrcode brand = new EnterpriseInformationQrcode();
			
			String status = request.getParameter("status");
			brand.setLsh(new Integer(request.getParameter("lsh")));
			brand.setStatus(new Integer(status));
			enterpriseInformationQrcodeService.updateBetRecord(brand);
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.二维码收款业务, "启用或禁用二维码收款信息:"+brand.getLsh(), ee.getLoginaccount(), null));
			
			map.put("status", "success");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("status", "failure");
		return map;
	}
	
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
