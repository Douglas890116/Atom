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
import com.maven.constant.Constant;
import com.maven.entity.Bank;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseInformation;
import com.maven.entity.UserLogs;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.BankService;
import com.maven.service.EnterpriseInformationService;
import com.maven.service.UserLogsService;


@Controller
@RequestMapping("/EInformation")
public class EnterpriseInformationController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			EnterpriseInformationController.class.getName(), OutputManager.LOG_ENTERPRISEINFORMATION);
	
	@Autowired
	private EnterpriseInformationService enterpriseInformationService;
	
	@Autowired
	private BankService bankServiceImpl;
	@Autowired
	private UserLogsService userLogsService;
	
	@RequestMapping("/add")
	public String add(HttpSession session,Model model){
		try {
			List<Bank> banks = bankServiceImpl.select(null);
			model.addAttribute("banks", banks);
			return "/enterprise/enterpriseinfomation_add";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	@RequestMapping("/list")
	public String list(HttpSession session,Model model){
		return "/enterprise/enterpriseinfomation_list";
	}
	/**
	 * 企业支付管理信息保存
	 * @param model
	 * @param response
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String,Object> save(Model model, HttpServletResponse response,HttpSession session,HttpServletRequest request){
		try {
			EnterpriseInformation ei = getRequestParamters(request,EnterpriseInformation.class);
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			ei.setEnterprisecode(ee.getEnterprisecode());
			ei.setBrandcode(ee.getBrandcode());
			enterpriseInformationService.add(ei);
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.公司信息业务, "保存企业银行卡:"+ei.getAccountname(), ee.getLoginaccount(), null));
			
			
			return super.packJSON(Constant.BooleanByte.YES,"添加企业资金账户成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO,"企业资金账户创建失败");
		}
	}
	/**
	 * 企业支付管理查询
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("/data")
	@ResponseBody
	public Map<String,Object> data(HttpServletRequest request , HttpServletResponse response , HttpSession session){
		try {
			Map<String,Object> object = getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			object.put("enterprisecode", ee.getEnterprisecode());
			List<EnterpriseInformation> rows =  enterpriseInformationService.selectAll(object);
			int rowCount = enterpriseInformationService.selectAllCount(object);
			super.encryptSign(rows,session.getId(),"enterpriseinformationcode");
			return formatPagaMap(rows,rowCount);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
		
	}
	/**
	 * 跳转到企业支付管理修改页面
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/edit")
	public String editselect(Model model,HttpSession session,HttpServletRequest request){
		try {
			Map<String,Object> params = super.getRequestParamters(request);
			String sign = (String)params.get("sign");
			if(this.decodeSign(sign, session.getId())){
				EnterpriseInformation ss = enterpriseInformationService.selectByPrimaryKey(sign.split("_")[1]);
				ss.setSign(sign);
				List<Bank> banks = bankServiceImpl.select(null);
				model.addAttribute("banks", banks);
				model.addAttribute("object", ss);
			}else{
				return Constant.PAGE_DECODEREFUSE;
			}
			return "/enterprise/enterpriseinfomation_edit";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	/**
	 * 企业支付管理修改
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/update")
	@OperationLog(OparetionDescription.COMPANY_BANK_CARD_UPDATE)
	@ResponseBody
	public Map<String,Object> edit(Model model,HttpSession session,HttpServletRequest request){
		try {
			EnterpriseInformation ei = super.getRequestParamters(request,EnterpriseInformation.class);
			if(super.decodeSign(ei.getSign(), session.getId())){
				String enterpriseinformationcode = ei.getSign().split("_")[1];
				ei.setEnterpriseinformationcode(enterpriseinformationcode);
				enterpriseInformationService.update(ei);
				
				EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.公司信息业务, "更改企业银行卡:"+ei.getAccountname(), ee.getLoginaccount(), null));
				
				
				return this.packJSON(Constant.BooleanByte.YES,"修改企业银行成功");
			}else{
				return this.packJSON(Constant.BooleanByte.NO,Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO,"操作失败，请稍后尝试");
		}
		
	}
	/**
	 * 企业支付管理删除
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/delete")
	@OperationLog(OparetionDescription.COMPANY_BANK_CARD_DELETE)
	@ResponseBody
	public Map<String,Object> delete(HttpSession session,HttpServletRequest request){
		try {
			EnterpriseInformation ei = super.getRequestParamters(request,EnterpriseInformation.class);
			if(super.decodeSign(ei.getSign(), session.getId())){
				ei.setEnterpriseinformationcode(ei.getSign().split("_")[1]);
				enterpriseInformationService.delete(ei);
				
				EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.公司信息业务, "删除企业银行卡:"+ei.getEnterpriseinformationcode(), ee.getLoginaccount(), null));
				
				return super.packJSON(Constant.BooleanByte.YES, "删除成功");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "删除失败，请稍后尝试");
		}
	}
	
	
	@RequestMapping("/BatchDelete")
	@OperationLog(OparetionDescription.COMPANY_BANK_CARD_DELETE)
	@ResponseBody
	public Map<String,Object> delete(HttpServletRequest request,HttpSession session){
		try {
			String[] sign = request.getParameterValues("sign[]");
			if(sign==null||sign.length==0) {
				return this.packJSON(Constant.BooleanByte.NO, "请选择需要删除的数据");
			}
			
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			
			List<String> enterpriseinformationcodes = new ArrayList<String>();
			for (String s : sign) {
				if(super.decodeSign(s, session.getId())){
					enterpriseinformationcodes.add(s.split("_")[1]);
					enterpriseInformationService.tc_logicDelete(enterpriseinformationcodes);
					
					
					userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
						     UserLogs.Enum_operatype.公司信息业务, "批量删除企业银行卡:"+enterpriseinformationcodes, ee.getLoginaccount(), null));
					
					
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
