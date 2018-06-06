package com.maven.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.entity.EnterpriseActivityCustomization;
import com.maven.entity.EnterpriseActivityPay;
import com.maven.entity.EnterpriseBrandActivity;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.UserLogs;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.EnterpriseActivityCustomizationService;
import com.maven.service.EnterpriseActivityPayService;
import com.maven.service.EnterpriseBrandActivityService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.UserLogsService;
import com.maven.util.RandomString;

/**
 * 活动红利信息
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/activitypay")
public class EnterpriseActivityPayController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			EnterpriseOperatingBrandGameController.class.getName(), OutputManager.LOG_GAMEAPIINPUT);
	
	@Autowired
	private EnterpriseActivityPayService enterpriseActivityPayService;
	@Autowired
	private EnterpriseActivityCustomizationService enterpriseActivityCustomizationService;
	@Autowired
	private EnterpriseEmployeeCapitalAccountService enterpriseEmployeeCapitalAccountService;
	@Autowired
	private EnterpriseBrandActivityService enterpriseBrandActivityService;
	@Autowired
	private UserLogsService userLogsService;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private ActivityBetRecordService activityBetRecordService;
	@Autowired
	private EnterpriseEmployeeService employeeService;
	/**
	 * index
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) request.getSession().getAttribute(Constant.USER_SESSION);
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("enterprisecode", ee.getEnterprisecode());
			List<EnterpriseBrandActivity> list = enterpriseBrandActivityService.selectAll(queryParams);
			model.addAttribute("listEnterpriseBrandActivity", list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "/activitypay/index";
	}
	
	/**
	 * 分页查询
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/pagelist")
	@ResponseBody
	public Map<String, Object> pagelist(HttpServletRequest request,Model model){
		
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) request.getSession().getAttribute(Constant.USER_SESSION);
			Map<String,Object> params = getRequestParamters(request);
			super.dataLimits(ee, params, request.getSession());
			
			if (params.get("parentName") != null) {
				String parentemployeeaccount = params.get("parentName").toString();
				EnterpriseEmployee parent = new EnterpriseEmployee();
				parent.setLoginaccount(parentemployeeaccount);
				List<EnterpriseEmployee> list = employeeService.select(parent);
				if (null != list && list.size() > 0) {
					//params.put("parentemployeecode", list.get(0).getEmployeecode());
					super.dataLimits(list.get(0), params, request.getSession());
				} else {
					// 如果查不到上级账户code，就直接传名字进去，查不出数据，表示这个账号下没有会员
					//params.put("parentemployeecode", parentemployeeaccount);
				}
				params.remove("parentName");
			}

			/*********非超级管理员时只能查询本团队内的数据************/
			
			if(!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())){
				params.put("enterprisecode", ee.getEnterprisecode());
			}
			List<EnterpriseActivityPay> listEnterpriseGame = enterpriseActivityPayService.selectAll(params);
			int count = enterpriseActivityPayService.selectAllCount(params);
			
			Map<String, Object> data = super.formatPagaMap(listEnterpriseGame, count);
			
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return null;
		
	}
	/**
	 * 导出红利信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/exportExcel")
	public String export(HttpServletRequest request, Model model) {
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) request.getSession().getAttribute(Constant.USER_SESSION);
			Map<String, Object> params = getRequestParamters(request);
			super.dataLimits(ee, params, request.getSession());
			
			if (params.get("parentName") != null) {
				String parentemployeeaccount = params.get("parentName").toString();
				EnterpriseEmployee parent = new EnterpriseEmployee();
				parent.setLoginaccount(parentemployeeaccount);
				List<EnterpriseEmployee> list = employeeService.select(parent);
				if (null != list && list.size() > 0) {
					//params.put("parentemployeecode", list.get(0).getEmployeecode());
					super.dataLimits(list.get(0), params, request.getSession());
				} else {
					// 如果查不到上级账户code，就直接传名字进去，查不出数据，表示这个账号下没有会员
					//params.put("parentemployeecode", parentemployeeaccount);
				}
				params.remove("parentName");
			}

			/********* 非超级管理员时只能查询本团队内的数据 ************/
			if (!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())) {
				params.put("enterprisecode", ee.getEnterprisecode());
			}
			List<EnterpriseActivityPay> listEnterpriseGame = enterpriseActivityPayService.selectAll(params);
			model.addAttribute("listData", listEnterpriseGame);
			model.addAttribute("title", ee.getEnterprisename() + " 红利数据");
		} catch (Exception e) {
			log.Error("导出红利信息报错", e);
			return Constant.PAGE_ERROR;
		}
		return "/activitypay/activitypayexcel";
	}
	/**
	 * 修改
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> edit(HttpServletRequest request,Model model){
		
		try {
			Map<String,Object> params = getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee) request.getSession().getAttribute(Constant.USER_SESSION);
			
			EnterpriseActivityPay data = enterpriseActivityPayService.selectByPrimaryKey(params.get("lsh").toString());
			data.setAuditremark("修改实发金额为"+data.getPaymoneyreal());
			data.setPaymoneyreal(Double.valueOf(params.get("paymoneyreal").toString()));
			enterpriseActivityPayService.updateActivityBetRecord(data);
			
			userLogsService.addActivityBetRecord(new UserLogs(data.getEnterprisecode(), data.getEmployeecode(), data.getLoginaccount(), 
				     UserLogs.Enum_operatype.优惠流水业务, "修改前金额:"+data.getPaymoneyreal(), ee.getLoginaccount(), null));
			
			return this.packJSON(Constant.BooleanByte.YES,"操作成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.packJSON(Constant.BooleanByte.YES,"操作失败，请稍后尝试");
		}		
	}
	
	/**
	 * 审核
	 * 
	 * 通过或拒绝
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/audit")
	@ResponseBody
	public Map<String, Object> audit(HttpServletRequest request,Model model){
		
		try {
			Map<String,Object> params = getRequestParamters(request);
			EnterpriseEmployee ee= (EnterpriseEmployee) request.getSession().getAttribute(Constant.USER_SESSION);
			
			EnterpriseActivityPay data = enterpriseActivityPayService.selectByPrimaryKey(params.get("lsh").toString());
			data.setAuditer(ee.getLoginaccount());
			data.setAudittime(new Date());
			data.setAuditremark(params.get("auditremark").toString());
			if(String.valueOf(params.get("auditFlag")).equals("通过")) {
				data.setPaystatus(EnterpriseActivityPay.Enum_paystatus.已审核.value);
			} else {
				data.setPaystatus(EnterpriseActivityPay.Enum_paystatus.已拒绝.value);
			}
			enterpriseActivityPayService.updateActivityBetRecord(data);
			
			userLogsService.addActivityBetRecord(new UserLogs(data.getEnterprisecode(), data.getEmployeecode(), data.getLoginaccount(), 
				     UserLogs.Enum_operatype.优惠流水业务, "审核优惠流水", ee.getLoginaccount(), null));
			
			return this.packJSON(Constant.BooleanByte.YES,"操作成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.packJSON(Constant.BooleanByte.YES,"操作失败，请稍后尝试");
		}		
	}
	
	/**
	 * 支付
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/pay")
	@ResponseBody
	public Map<String, Object> pay(HttpServletRequest request,Model model){
		
		try {
			Map<String,Object> params = getRequestParamters(request);
			
			EnterpriseEmployee ee= (EnterpriseEmployee) request.getSession().getAttribute(Constant.USER_SESSION);
			EnterpriseActivityPay data = enterpriseActivityPayService.selectByPrimaryKey(params.get("lsh").toString());
			
			enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(RandomString.UUID(), data.getEmployeecode(), new BigDecimal(data.getPaymoneyreal()), 
					new EmployeeMoneyChangeType(Enum_moneychangetype.优惠活动.value,Enum_moneychangetype.优惠活动.desc, Enum_moneyinouttype.进账), 
					"操作人:"+ee.getLoginaccount()  + " 活动："+data.getAcname());
			
			
			data.setPayer(ee.getLoginaccount());
			data.setPaytyime(new Date());
			data.setPaystatus(EnterpriseActivityPay.Enum_paystatus.已支付.value);
			enterpriseActivityPayService.updateActivityBetRecord(data);
			
			/*************加入到优惠流水那里***********/
			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("enterprisecode", data.getEnterprisecode());
			queryParams.put("ecactivitycode", data.getEcactivitycode());
			queryParams.put("status", EnterpriseBrandActivity.Enum_status.启用.value);
			List<EnterpriseBrandActivity> listEnterpriseBrandActivity = enterpriseBrandActivityService.selectAll(queryParams);
			if(listEnterpriseBrandActivity != null && listEnterpriseBrandActivity.size() > 0) {
				EnterpriseBrandActivity activity = listEnterpriseBrandActivity.get(0);
				
				boolean flag = false;
				
				Map<String, Object> mapAgument = enterpriseBrandActivityService.selectActivityAgument(activity.getEnterprisebrandactivitycode());
				if(mapAgument != null) {
					//是否自动付款（0=非自动 1=自动）
					String IS_AUTO_PAY = com.maven.util.StringUtils.nullString(mapAgument.get("IS_AUTO_PAY"));
					if( !IS_AUTO_PAY.equals("1")) {//这里判断是否需要加入打码，主要是考虑到有些活动是非自动发放的，这里手动发放后才进行打码
						flag = true;
					}
					
					//如果当前活动是后来切换为自定义活动的，则活动参数配置数据有，所有要重复检测
					EnterpriseActivityCustomization activityCustomization = enterpriseActivityCustomizationService.selectByPrimaryKey(data.getEcactivitycode().toString());
					if(activityCustomization.getActivitytemplatecode().intValue() == 9) {
						flag = true;
					}
					
				} else {
					flag = true;//自定义活动是没有参数的
				}
				
				
				if(flag) {
					double betMultiple = Double.valueOf(data.getLsbs()) ;//
					
					ActivityBetRecord betrecord = new ActivityBetRecord();
					betrecord.setEmployeecode(data.getEmployeecode());
					betrecord.setEcactivitycode(data.getEcactivitycode());
					betrecord.setMustbet( (data.getPaymoneyreal() + data.getDepositmoney() ) * betMultiple);
					betrecord.setAlreadybet(0.0);
					betrecord.setBetrecordstatus(Enum_betrecordstatus.未完成.value);
					betrecord.setCreatetime(new Date());
					betrecord.setLoginaccount(data.getLoginaccount());
					betrecord.setRecharge(data.getPaymoneyreal());//充值金额
					
					EnterpriseEmployee enterpriseEmployee = enterpriseEmployeeService.takeEmployeeByCode(data.getEmployeecode());
					betrecord.setEnterprisecode(enterpriseEmployee.getEnterprisecode());//企业编码
					betrecord.setBrandcode(enterpriseEmployee.getBrandcode());//品牌编码
					betrecord.setParentemployeeaccount(enterpriseEmployee.getParentemployeeaccount());//上级账号
					betrecord.setParentemployeecode(enterpriseEmployee.getParentemployeecode());//上级编码
					activityBetRecordService.addActivityBetRecord(betrecord);
					System.out.println(data.getLoginaccount()+"活动充值增加打码");
				}
			}
			
			
			userLogsService.addActivityBetRecord(new UserLogs(data.getEnterprisecode(), data.getEmployeecode(), data.getLoginaccount(), 
				     UserLogs.Enum_operatype.优惠流水业务, "发放优惠流水", ee.getLoginaccount(), null));
			
			return this.packJSON(Constant.BooleanByte.YES,"操作成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.packJSON(Constant.BooleanByte.YES,"操作失败，请稍后尝试："+e.getMessage());
		}		
	}
	
	
	/**
	 * 定时查询待处理的提醒
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/findWaitPayment")
	@ResponseBody
	public Map<String, Object> findWaitPayment(HttpServletRequest request,Model model){
		
		try {
			Map<String,Object> params = new HashMap<String, Object>();
			EnterpriseEmployee ee = (EnterpriseEmployee) request.getSession().getAttribute(Constant.USER_SESSION);
			params.put("enterprisecode", ee.getEnterprisecode());
			
			Map<String, Object> data = super.formatPagaMap(null, enterpriseActivityPayService.selectAllCountNoti(params));
//			System.out.println("###############################################################"+data);
			return data;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
