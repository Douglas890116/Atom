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

import com.maven.annotation.OperationLog;
import com.maven.annotation.PrivacyData;
import com.maven.constant.Constant;
import com.maven.entity.EmployeeGamecataloyBonus;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeInformation;
import com.maven.entity.EnterpriseEmployeeInformation.Enum_status;
import com.maven.entity.EnterpriseEmployeeType.Type;
import com.maven.entity.UserLogs;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.EmployeeGamecataloyBonusService;
import com.maven.service.EnterpriseEmployeeInformationService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.PrivateDataAccessService;
import com.maven.service.UserLogsService;
import com.maven.utils.StringUtil;

@Controller
@RequestMapping("/EmployeeInformation")
public class EnterpriseEmployeeInformationContoller extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			EnterpriseEmployeeInformationContoller.class.getName(), OutputManager.LOG_ENTERPRISEEMPLOYEEINFORMATION);
	/** 用户信息*/
	@Autowired
	private EnterpriseEmployeeInformationService enterpriseEmployeeInformationService;
	/** 企业员工  */
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	//洗码
	@Autowired
	EmployeeGamecataloyBonusService employeeGamecataloyBonusServiceImpl;
	@Autowired
	private UserLogsService userLogsService;
	/** 隐私数据限制 */
	@Autowired
	private PrivateDataAccessService dataAccessService;
	/**
	 * 用户银行操作主页面
	 * 除员工与企业类型用户外其它都用户类型都不允许使用
	 * @param model
	 * @return string
	 */
	@RequestMapping("/userInfo/index")
	public String forwordIndex(HttpSession session){
		return "/userinfo/index";
	}
	
	/**
	 * 用户银行操作主页面
	 * 除员工与企业类型用户外其它都用户类型都不允许使用
	 * @param model
	 * @return string
	 */
	@RequestMapping("/myBank/index")
	public String myBankIndex(HttpSession session){
		return "/userinfo/myBankIndex";
	}
	
	/**
	 * 账户总览显示页面
	 */
	@RequestMapping("/accountDataQuery")
	public String accountDataQuery(HttpSession session,Model model){
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			EnterpriseEmployeeInformation enterpriseEmployeeInformation = enterpriseEmployeeInformationService.queryAccountData(loginEmployee);
			if(enterpriseEmployeeInformation != null) {
				model.addAttribute("employeeInformation", enterpriseEmployeeInformation);
				EnterpriseEmployee enterpriseEmployee = enterpriseEmployeeService.takeEmployeeByCode(enterpriseEmployeeInformation.getEmployeecode());
				model.addAttribute("enterpriseEmployee", enterpriseEmployee);
				List<EmployeeGamecataloyBonus> list = employeeGamecataloyBonusServiceImpl.findGameBonus(enterpriseEmployeeInformation.getEmployeecode());
				model.addAttribute("listBonus", list);
			}
			
			return "/userinfo/accountDataQuery";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		return Constant.PAGE_ACTIONFAILD;
	}
	
	/**
	 * 银行添加页面
	 * @return
	 */
	@RequestMapping("/userInfo/userInfoAdd")
	public String userInfoAdd(HttpSession session,Model model){
		EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
		model.addAttribute("employee", employee);
		return "/userinfo/userInfoAdd";
	}

	/**
	 * 银行添加页面
	 * @return
	 */
	@RequestMapping("/userInfo/addUserInfo")
	public String addUserInfo(HttpSession session,Model model){
		EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
		return "/userinfo/addUserInfo";
	}
	
	/**
	 *添加银行卡时验证同一个品牌下(brandcode)银行卡号是否存在 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/validateBankCardNumber",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String validateBankCardNumber(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			EnterpriseEmployeeInformation e = getRequestParamters(request,EnterpriseEmployeeInformation.class);
			e.setEnterprisecode(employee.getEnterprisecode());
			List<EnterpriseEmployeeInformation> list = enterpriseEmployeeInformationService.select(e);
			if(0 == list.size()){
				return "";
			}
			return "该卡号已经绑定";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return "绑定异常，请稍后重试";
		}
	}
	/**
	 * 保存用户银行信息
	 * @return
	 */
	@RequestMapping("/saveEnterpriseEmployeeInformation")
	@ResponseBody
	public Map<String,Object> saveEnterpriseEmployeeInformation(@ModelAttribute EnterpriseEmployeeInformation enterpriseEmployeeInformation,HttpSession session){
		try {
			//	户名不得包含数字
			if(StringUtil.isNumeric(enterpriseEmployeeInformation.getAccountname())) {
				return super.packJSON(Constant.BooleanByte.NO, "户名不得包含数字！");
			}
			
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			enterpriseEmployeeInformation.setEnterprisecode(employee.getEnterprisecode());
			enterpriseEmployeeInformation.setBrandcode(employee.getBrandcode());
			enterpriseEmployeeInformation.setEmployeecode(employee.getEmployeecode());
			enterpriseEmployeeInformation.setParentemployeecode(employee.getParentemployeecode());
			Map<String,Object> paramter = new HashMap<String,Object>();
			paramter.put("paymentaccount", enterpriseEmployeeInformation.getPaymentaccount());
			paramter.put("accountname", enterpriseEmployeeInformation.getAccountname());
			EnterpriseEmployeeInformation infomation =  enterpriseEmployeeInformationService.queryBankCardIsNoExist(paramter);
			if(null == infomation){
				enterpriseEmployeeInformationService.tc_saveEnterpriseEmployeeInformation(enterpriseEmployeeInformation);
				
				userLogsService.addActivityBetRecord(new UserLogs(enterpriseEmployeeInformation.getEnterprisecode(), enterpriseEmployeeInformation.getEmployeecode(), enterpriseEmployeeInformation.getLoginaccount(), 
					     UserLogs.Enum_operatype.银行卡业务, "银行卡绑定", employee.getLoginaccount(), null));
				
				return super.packJSON(Constant.BooleanByte.YES, "银行卡绑定成功");
			}
			return super.packJSON(Constant.BooleanByte.NO, "该用户银行卡已经被绑定");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "银行卡绑定失败，请稍后尝试");
		}
	}
	
	/**
	 * 保存用户银行信息
	 * @return
	 */
	@RequestMapping("/addEnterpriseEmployeeInformation")
	@ResponseBody
	public Map<String,Object> addEnterpriseEmployeeInformation(@ModelAttribute EnterpriseEmployeeInformation enterpriseEmployeeInformation,HttpSession session){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			String employeecode = enterpriseEmployeeInformation.getEmployeecode();
			
			EnterpriseEmployee employee = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
			if (employee == null || !employee.getEnterprisecode().equals(ee.getEnterprisecode()))
				return packJSON(Constant.BooleanByte.NO, "用户不存在");
			if (!employee.getEmployeetypecode().equals(Type.会员.value))
				return packJSON(Constant.BooleanByte.NO, "非会员用户不能操作");
			
			
			enterpriseEmployeeInformation.setEnterprisecode(employee.getEnterprisecode());
			enterpriseEmployeeInformation.setBrandcode(employee.getBrandcode());
			enterpriseEmployeeInformation.setEmployeecode(employee.getEmployeecode());
			enterpriseEmployeeInformation.setParentemployeecode(employee.getParentemployeecode());
			Map<String,Object> paramter = new HashMap<String,Object>();
			paramter.put("paymentaccount", enterpriseEmployeeInformation.getPaymentaccount());
			paramter.put("accountname", enterpriseEmployeeInformation.getAccountname());
			EnterpriseEmployeeInformation infomation =  enterpriseEmployeeInformationService.queryBankCardIsNoExist(paramter);
			if(null == infomation){
				enterpriseEmployeeInformationService.tc_saveEnterpriseEmployeeInformation(enterpriseEmployeeInformation);
				
				userLogsService.addActivityBetRecord(new UserLogs(enterpriseEmployeeInformation.getEnterprisecode(), enterpriseEmployeeInformation.getEmployeecode(), enterpriseEmployeeInformation.getLoginaccount(), 
					     UserLogs.Enum_operatype.银行卡业务, "银行卡绑定", ee.getLoginaccount(), null));
				
				return super.packJSON(Constant.BooleanByte.YES, "银行卡绑定成功");
			}
			return super.packJSON(Constant.BooleanByte.NO, "该用户银行卡已经被绑定");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "银行卡绑定失败，请稍后尝试");
		}
	}
	
	/**
	 * 分页查询
	 * @param request
	 * @return Map<String,Object>
	 */
	 
	@RequestMapping("/findBankInfo")
	@ResponseBody
	@PrivacyData(fileds = {"phonenumber","qq","skype","email"})
	public Map<String,Object> findEmployeeBankInfo(HttpServletRequest request,HttpSession session){
		try {
			long date  = System.currentTimeMillis();
			Map<String,Object> obj = getRequestParamters(request);
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			//代理只能查询自己的银行卡信息
			if(loginEmployee.getEmployeetypecode().equals(Type.代理.value)
					||loginEmployee.getEmployeetypecode().equals(Type.信用代理.value)){
				obj.put("employeecode", loginEmployee.getEmployeecode());
			}else{
				//加载用户所有下级信息
				super.dataLimits(loginEmployee, obj,session);
			}
			//分页查询
			List<EnterpriseEmployeeInformation> list = enterpriseEmployeeInformationService.findEmployeeInfo(obj);
			//查询总记录条数
			int countNumber = enterpriseEmployeeInformationService.findEmployeeInfoCount(obj);
			//加密字段的值
			this.encryptSign(list, session.getId(), "informationcode");
			log.Debug("数据查询耗时："+(System.currentTimeMillis()-date));
			return super.formatPagaMap(list, countNumber);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 我的银行卡-分页查询
	 * @param request
	 * @return Map<String,Object>
	 */
	 
	@RequestMapping("/findMyBankInfo")
	@ResponseBody
	//@PrivacyData(fileds = {"phonenumber","qq","skype","email"})
	public Map<String,Object> findMyBankInfo(HttpServletRequest request,HttpSession session){
		try {
			long date  = System.currentTimeMillis();
			Map<String,Object> obj = getRequestParamters(request);
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			//只查询自己的银行卡信息
			obj.put("employeecode", loginEmployee.getEmployeecode());
			//分页查询
			List<EnterpriseEmployeeInformation> list = enterpriseEmployeeInformationService.findEmployeeInfo(obj);
			//查询总记录条数
			int countNumber = enterpriseEmployeeInformationService.findEmployeeInfoCount(obj);
			//加密字段的值
			this.encryptSign(list, session.getId(), "informationcode");
			log.Debug("数据查询耗时："+(System.currentTimeMillis()-date));
			return super.formatPagaMap(list, countNumber);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 所有会员银行卡
	 * 
	 * 只有本企业的企业号能查询本企业的银行卡
	 * 
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/indexBankInfoList")
	public String indexBankInfoList(HttpSession session,Model model){
		EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
		
		//放开权限，能进来的就可以修改
//		if(!loginEmployee.getEmployeetypecode().equals(Type.企业号.value) ){
			
//			model.addAttribute("message", "非企业号不能访问该信息");
//			return Constant.PAGE_IDENTITY_NO_MATCH;
//		}
		return "/userinfo/indexBankInfoList";
	}
	/**
	 * 所有会员银行卡
	 * 
	 * 只有本企业的企业号能查询本企业的银行卡
	 * 
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/findBankInfoList")
	@ResponseBody
	public Map<String, Object> findBankInfoList(HttpServletRequest request, HttpSession session) {
		try {
			long date = System.currentTimeMillis();
			Map<String, Object> obj = getRequestParamters(request);
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			obj.put("enterprisecode", loginEmployee.getEnterprisecode());
			super.dataLimits(loginEmployee, obj, session);
			// 分页查询
			List<EnterpriseEmployeeInformation> list = enterpriseEmployeeInformationService.findEmployeeInfo(obj);
			if (null == session.getAttribute(Constant.PRIVATE_DATA_PSERSSION)) {
				for (int i = 0; i < list.size(); i++) {
					list.get(i).setPaymentaccount("****************");
					list.get(i).setPhonenumber("***********");
					list.get(i).setQq("***********");
					list.get(i).setEmail("***********");
					list.get(i).setSkype("***********");
				}
			}
			// 查询总记录条数
			int countNumber = enterpriseEmployeeInformationService.findEmployeeInfoCount(obj);
			// 加密字段的值
			this.encryptSign(list, session.getId(), "informationcode");
			log.Debug("数据查询耗时：" + (System.currentTimeMillis() - date));

			return super.formatPagaMap(list, countNumber);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	
	
	/**
	 * 账户总览--查询当前登录用户的信息
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/queryAccountData")
	@ResponseBody
	public Map<String,Object> queryAccountData(HttpServletRequest request,HttpSession session){
		try {
			//Map<String,Object> obj = getRequestParamters(request);
			//EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			//List<EmployeeAccount> list = enterpriseEmployeeInformationService.queryAccountData(loginEmployee);
			/*//分页查询
			List<EnterpriseEmployeeInformation> list = enterpriseEmployeeInformationService.findEmployeeInfo(obj);
			//查询总记录条数
			int countNumber = enterpriseEmployeeInformationService.findEmployeeInfoCount(obj);
			//加密字段的值
			this.encryptSign(list, session.getId(), "informationcode");*/
			return super.formatPagaMap(null, 0);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 根据员工Code查询信息
	 */
	@RequestMapping("/updateBankInfo")
	@OperationLog(OparetionDescription.EMPLOYEEBANKCARDINFO_UPDATE)
	public String updateEmployeeBankInfo(Model model, HttpServletRequest request, HttpSession session) {
		try {
			if (super.decodeSign(request.getParameter("informationcode"), session.getId())) {
				String informationcode = request.getParameter("informationcode").split("_")[1];
				EnterpriseEmployeeInformation enterpriseEmployeeInformation = enterpriseEmployeeInformationService
						.findOneBankInfo(informationcode);
				enterpriseEmployeeInformation.setInformationcode(request.getParameter("informationcode"));
				model.addAttribute("bankInfo", enterpriseEmployeeInformation);
				// 当请求从 会员银行卡来时，返回列表就返回会员银行卡
				if (null != request.getParameter("from") && request.getParameter("from").equals("admin")) {
					model.addAttribute("fromTag", "/EmployeeInformation/indexBankInfoList");
				}
				return "/userinfo/updateBankInfo";
			} else {
				return Constant.PAGE_DECODEREFUSE;
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	/**
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateStatus")
	public Map<String, Object> changeUserBankStatus(HttpServletRequest request, HttpSession session) {
		try {
			if (super.decodeSign(request.getParameter("informationcode"), session.getId())) {

				String informationcode = request.getParameter("informationcode").split("_")[1];
				String status = request.getParameter("status");
				EnterpriseEmployeeInformation eei = new EnterpriseEmployeeInformation();
				eei.setInformationcode(informationcode);
				eei.setStatus(status);
				int result = enterpriseEmployeeInformationService.tc_lockEmployeeInformation(eei);
				if (result == 1) {
					return packJSON(Constant.BooleanByte.YES, "修改成功");
				} else {
					return packJSON(Constant.BooleanByte.NO, "修改失败");
				}
			} else {
				return packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error("修改会员银行卡状态报错", e);
			return packJSON(Constant.BooleanByte.NO, "操作失败，请稍后再试");
		}
	}
	
	/**
	 * 管理员修改用户的银行卡信息
	 * @param enterpriseEmployeeInformation
	 * @return url
	 */
	@RequestMapping("/updateEnterpriseEmployeeInformation")
	@ResponseBody
	@OperationLog(OparetionDescription.EMPLOYEEBANKCARDINFO_UPDATE)
	public Map<String,Object> updateEnterpriseEmployeeInformation(@ModelAttribute EnterpriseEmployeeInformation enterpriseEmployeeInformation,HttpSession session){
		try {
			boolean mark  = this.decodeSign(enterpriseEmployeeInformation.getInformationcode(), session.getId());
			if(mark){
				
				//	户名不得包含数字
				if(StringUtil.isNumeric(enterpriseEmployeeInformation.getAccountname())) {
					return super.packJSON(Constant.BooleanByte.NO, "户名不得包含数字！");
				}
				
				//修改之后银行卡为锁定状态
				EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
				enterpriseEmployeeInformation.setInformationcode(enterpriseEmployeeInformation.getInformationcode().split("_")[1]);
//				enterpriseEmployeeInformation.setEmployeecode(loginEmployee.getEmployeecode());
				enterpriseEmployeeInformation.setEnterprisecode(loginEmployee.getEnterprisecode());
				enterpriseEmployeeInformationService.tc_updateEnterpriseEmployeeInformationByAdmin(enterpriseEmployeeInformation);
				
				userLogsService.addActivityBetRecord(new UserLogs(enterpriseEmployeeInformation.getEnterprisecode(), enterpriseEmployeeInformation.getEmployeecode(), enterpriseEmployeeInformation.getLoginaccount(), 
					     UserLogs.Enum_operatype.银行卡业务, "更改银行卡", loginEmployee.getLoginaccount(), null));
				
				
				return super.packJSON(Constant.BooleanByte.YES, "修改成功");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO,"修改失败,请稍后尝试");
		}
	}
	
	/**
	 * 银行信息单条记录删除
	 * @param request
	 */
	@RequestMapping("/deleteBankInfo")
	@OperationLog(OparetionDescription.EMPLOYEEBANKCARDINFO_DELETE)
	@ResponseBody
	public Map<String,Object> deleteEmployeeBankInfo(HttpServletRequest request,HttpSession session){
		Map<String,Object> map =new HashMap<String,Object>();
		//获取需要删除的CODE
		String informationcode = request.getParameter("deleteCode");
		//解密标识字段的值
		boolean mark = this.decodeSign(informationcode, session.getId());
		if(mark){
			//调用删除方法
			try {
				enterpriseEmployeeInformationService.tc_deleteBankInfo(informationcode.split("_")[1]);
				
				EnterpriseEmployeeInformation enterpriseEmployeeInformation = enterpriseEmployeeInformationService.findOneBankInfo(informationcode.split("_")[1]);
				EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
				userLogsService.addActivityBetRecord(new UserLogs(enterpriseEmployeeInformation.getEnterprisecode(), enterpriseEmployeeInformation.getEmployeecode(), enterpriseEmployeeInformation.getLoginaccount(), 
					     UserLogs.Enum_operatype.银行卡业务, "删除银行卡", loginEmployee.getLoginaccount(), null));
				
			} catch (Exception e) {
				System.err.println("用户银行资料删除失败！！！");
				e.printStackTrace();
			}
			map.put("status", "success");
			return map;
		}else{
			map.put("status", "failure");
			return map;
		}
		
	}
	/**
	 * 删除选中的一条或者多条数据
	 * @param request
	 */
	@RequestMapping("/deleteSelectBankInfo")
	@ResponseBody
	@OperationLog(OparetionDescription.EMPLOYEEBANKCARDINFO_DELETE)
	public Map<String,Object> deleteSelectEmployeeBankInfo(HttpServletRequest request,HttpSession session){
		try {
			String temp = request.getParameter("sign");
			String[] array = temp.split(",");
			boolean mark  = this.decodeSign(array, session.getId());
			if(mark){
				for (int i = 0; i < array.length; i++) {
					array[i]=array[i].split("_")[1];
				}
				
				EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
				for (String string : array) {
					EnterpriseEmployeeInformation enterpriseEmployeeInformation = enterpriseEmployeeInformationService.findOneBankInfo(string);
					userLogsService.addActivityBetRecord(new UserLogs(enterpriseEmployeeInformation.getEnterprisecode(), enterpriseEmployeeInformation.getEmployeecode(), enterpriseEmployeeInformation.getLoginaccount(), 
						     UserLogs.Enum_operatype.银行卡业务, "批量删除银行卡", loginEmployee.getLoginaccount(), null));
				}
				
				enterpriseEmployeeInformationService.tc_deleteSelectBankInfo(array);
				return super.packJSON(Constant.BooleanByte.YES, "已删除银行卡");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	/**
	 * 解锁银行卡
	 */
	@RequestMapping("unlockingBank")
	@ResponseBody
	@OperationLog(OparetionDescription.EMPLOYEEBANKCARDINFO_UNLOCKINGBANKCARD)
	public Map<String,String> unlockingBank(HttpServletRequest request,HttpSession session){
		Map<String,String> map = new HashMap<String, String>();
		try {
			EnterpriseEmployeeInformation employeeInformation = new EnterpriseEmployeeInformation();
			String informationcode = request.getParameter("informationcode");
			boolean mark = this.decodeSign(informationcode, session.getId());
			if(mark){
				EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
				employeeInformation.setInformationcode(informationcode.split("_")[1]);
				employeeInformation.setEnterprisecode(loginEmployee.getEnterprisecode());;
				employeeInformation.setStatus(Enum_status.解锁.value);
				enterpriseEmployeeInformationService.tc_lockEmployeeInformation(employeeInformation);
				map.put("status", "success");
				
				userLogsService.addActivityBetRecord(new UserLogs(employeeInformation.getEnterprisecode(), employeeInformation.getEmployeecode(), employeeInformation.getLoginaccount(), 
					     UserLogs.Enum_operatype.银行卡业务, "解锁银行卡", loginEmployee.getLoginaccount(), null));
				
				
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("status", "failure");
		return map;
	}
	/**
	 * 锁定银行卡
	 */
	@RequestMapping("lockingBank")
	@ResponseBody
	@OperationLog(OparetionDescription.EMPLOYEEBANKCARDINFO_LOCKINGBANKCARD)
	public Map<String,String> lockingBank(HttpServletRequest request,HttpSession session){
		Map<String,String> map = new HashMap<String, String>();
		try {
			EnterpriseEmployeeInformation employeeInformation = new EnterpriseEmployeeInformation();
			String informationcode = request.getParameter("informationcode");
			boolean mark = this.decodeSign(informationcode, session.getId());
			if(mark){
				EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
				employeeInformation.setInformationcode(informationcode.split("_")[1]);
				employeeInformation.setEnterprisecode(loginEmployee.getEnterprisecode());
				employeeInformation.setStatus(Enum_status.锁定.value);
				enterpriseEmployeeInformationService.tc_lockEmployeeInformation(employeeInformation);
				map.put("status", "success");
				
				userLogsService.addActivityBetRecord(new UserLogs(employeeInformation.getEnterprisecode(), employeeInformation.getEmployeecode(), employeeInformation.getLoginaccount(), 
					     UserLogs.Enum_operatype.银行卡业务, "锁定银行卡", loginEmployee.getLoginaccount(), null));
				return map;
			}
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
