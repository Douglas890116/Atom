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
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployee.Enum_employeestatus;
import com.maven.entity.EnterpriseEmployee.Enum_onlinestatus;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.game.APIServiceUtil;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.EnterprisePaymentMethodConfig;
import com.maven.entity.UserLogs;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.EnterprisePaymentMethodConfigService;
import com.maven.service.EnterpriseService;
import com.maven.service.UserLogsService;
import com.maven.util.BeanToMapUtil;
import com.maven.util.Encrypt;

@Controller
@RequestMapping("/Enterprise")
public class EnterpriseController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			EnterpriseController.class.getName(), OutputManager.LOG_ENTERPRISE);
	
	@Autowired
	private EnterpriseService enterpriseService;
	
	/**
	 * 公司出款方式
	 */
	@Autowired
	private EnterprisePaymentMethodConfigService enterprisePaymentMethodConfigServiceImpl;
	@Autowired
	private UserLogsService userLogsService;
	
	/**
	 * 新增企业信息
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/Add")
	public String add(Model model,HttpSession session,HttpServletRequest request){
		return "/enterprise/enterprise_add";
	}
	
	@RequestMapping("/SettingStep")
	public String step(){
		return "/step";
	}
	/**
	 * 编辑企业信息
	 * @return
	 */
	@RequestMapping("/Edit")
	public String eidt(HttpServletRequest request,HttpSession session,Model model){
		//model.addAttribute("enterprise",this.getRequestParamters(request));
		
		try {
			
			Enterprise enterprise = this.getRequestParamters(request, Enterprise.class);
			if(this.decodeSign(enterprise.getSign(), session.getId())){
				Enterprise temp = enterpriseService.selectByPrimaryKey(enterprise.getSign().split("_")[1]);
				temp.setSign(enterprise.getSign());
				model.addAttribute("enterprise", temp);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/enterprise/enterprise_edit";
	}
	
	/**
	 * 跳转到企业首页
	 * @param request
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("/List")	
	public String list(HttpServletRequest request,Model model,HttpSession session){
		return "/enterprise/enterprise_list";
	}
	/**
	 * 查询企业方法
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/data")
	@ResponseBody
	public Map<String,Object> data(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String,Object> object = this.getRequestParamters(request);
			/*
			if(!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())){
				object.put("enterprisecode", ee.getEnterprisecode());
			}
			*/
			if(!("EN0000").equals(ee.getEnterprisecode())){//非管理员企业
				object.put("enterprisecode", ee.getEnterprisecode());
			}
			
			List<Enterprise> rows = enterpriseService.selectAll(object);
			Map<String,String> mapping = new HashMap<String, String>();
			mapping.put("enterprisecode", "sign");
			this.encryptSignTarget(rows, session.getId(), mapping);
			int rowCount  = enterpriseService.selectAllCount(object);
			return formatPagaMap(rows, rowCount);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 创建企业
	 * @param request
	 * @return
	 */
	@RequestMapping("/Save")
	@ResponseBody
	@RequestInterval(millinsecond=3000)
	public Map<String,Object> save(HttpServletRequest request,HttpSession session){
		try {
			Map<String,Object> object = this.getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			if(!ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.企业号.value)){
				return this.packJSON(Constant.BooleanByte.NO, "您不是企业号，无法创建企业");
			}
			EnterpriseEmployee employee = BeanToMapUtil.convertMap(object, EnterpriseEmployee.class);
			employee.setParentemployeecode(ee.getEmployeecode());
			employee.setParentemployeeaccount(ee.getLoginaccount());
			employee.setEmployeetypecode(EnterpriseEmployeeType.Type.企业号.value);
			employee.setOnlinestatus(Byte.valueOf(Enum_onlinestatus.离线.value));
			employee.setEmployeestatus(Enum_employeestatus.启用.value.byteValue());
			employee.setLoginpassword2(APIServiceUtil.encrypt(employee.getLoginpassword(), employee));//对原始密码进行加密
			employee.setLoginpassword(Encrypt.MD5(employee.getLoginpassword()));
			
			Enterprise enterprise = BeanToMapUtil.convertMap(object, Enterprise.class);
			enterprise.setIpenable(Enterprise.Enum_ipenable.启用.value);
			
			enterpriseService.tc_CreateEnterprise(enterprise,employee);
			
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.公司信息业务, "创建公司:"+enterprise.getEnterprisename(), ee.getLoginaccount(), null));
			userLogsService.addActivityBetRecord(new UserLogs(employee.getEnterprisecode(), employee.getEmployeecode(), employee.getLoginaccount(), 
				     UserLogs.Enum_operatype.用户信息业务, "创建企业号:"+employee.getLoginaccount(), ee.getLoginaccount(), null));
			
			return this.packJSON(Constant.BooleanByte.YES, "企业已创建成功");
		}catch(LogicTransactionRollBackException e){
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, "企业创建失败，请稍后尝试");
		}
	}
	
	/**
	 * 加载公司出款配置信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/loadCompanyPaymentMethodConfig")
	@ResponseBody
	public EnterprisePaymentMethodConfig loadCompanyPaymentMethodConfig(HttpServletRequest request,HttpSession session){
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			EnterprisePaymentMethodConfig  paymentMethodConfig = enterprisePaymentMethodConfigServiceImpl.queryByCode(ee.getEnterprisecode());
		return paymentMethodConfig;
	}
	
	/**
	 * 保存公司付款方式
	 * @param request
	 * @return
	 */
	@RequestMapping("/savePaymentMethod")
	@ResponseBody
	public Map<String,Object> savePaymentMethod(HttpServletRequest request,HttpSession session){
		Map<String,Object> object = this.getRequestParamters(request);
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			object.put("enterpriseCode", ee.getEnterprisecode());
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.公司信息业务, "保存出款方式:"+ee.getEnterprisecode(), ee.getLoginaccount(), null));
			
			enterprisePaymentMethodConfigServiceImpl.tc_saveAndUpdate(object);
			object.put("status", "success");
			return object;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			object.put("status", "failure");
			return object;
		}
	}
	
	
	
	/**
	 * 更新企业信息
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/Update")
	@ResponseBody
	@OperationLog(OparetionDescription.ENTERPRISE_EDIT)
	public Map<String,Object> update(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Enterprise enterprise = this.getRequestParamters(request, Enterprise.class);
			if(this.decodeSign(enterprise.getSign(), session.getId())){
				enterprise.setEnterprisecode(enterprise.getSign().split("_")[1]);
				
				enterprise.setDepositrate(Double.valueOf(enterprise.getDepositrate()) / 100 + "");
				enterprise.setTakerate(Double.valueOf(enterprise.getTakerate()) / 100 + "");
				enterpriseService.update(enterprise);
				
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.公司信息业务, "更改企业:"+enterprise, ee.getLoginaccount(), null));
				
			}
			return this.packJSON(Constant.BooleanByte.YES, "企业信息已编辑");
		} catch (Exception e) {
			log.Error(e.getMessage(),e);
			return this.packJSON(Constant.BooleanByte.NO, "修改失败，请稍后尝试");
		}
	}
	
	/**
	 * 删除企业信息
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/Delete")
	@ResponseBody
	@OperationLog(OparetionDescription.ENTERPRISE_DELETE)
	public Map<String,Object> delete(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			String[] sign = request.getParameterValues("sign[]");
			if(sign==null||sign.length==0) 
				return this.packJSON(Constant.BooleanByte.NO, "请选择需要删除的数据");
			
			List<String> enterprisecodes = new ArrayList<String>();
			for (String s : sign) {
				if(super.decodeSign(s, session.getId())){
					boolean canDelete = enterpriseService.getValidateDeleteEnterprise(s.split("_")[1]);
					if(canDelete){
						enterprisecodes.add(s.split("_")[1]);
					}
				}else{
					return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
				}
			}
			if(enterprisecodes.size()>0){
				enterpriseService.tc_logicDelete(enterprisecodes);
				return this.packJSON(Constant.BooleanByte.YES, "删除成功");
			}
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.公司信息业务, "批量删除企业", ee.getLoginaccount(), null));
			
			return this.packJSON(Constant.BooleanByte.NO, "企业现在不能删除,请先确认企业下的品牌、用户是否已经删除");
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
