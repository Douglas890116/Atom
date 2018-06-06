package com.maven.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.annotation.OperationLog;
import com.maven.constant.Constant;
import com.maven.constant.Enum_MSG;
import com.maven.entity.Bank;
import com.maven.entity.DepositWithdralOrderDelegate;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EnterpriseBrandNotic;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeInformation;
import com.maven.entity.EnterpriseEmployeeLevel;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.EnterpriseEmployeeType.Type;
import com.maven.entity.EnterpriseInformation;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.EnterpriseWebview;
import com.maven.entity.LogLogin;
import com.maven.entity.PaymentType;
import com.maven.entity.PermissionMenu.Enum_language;
import com.maven.entity.ThirdpartyPaymentType;
import com.maven.entity.ThirdpartyPaymentTypeSetting;
import com.maven.entity.UserLogs;
import com.maven.entity.WebviewTemplate;
import com.maven.exception.ArgumentValidationException;
import com.maven.game.APIServiceNew;
import com.maven.game.APIServiceUtil;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.BankService;
import com.maven.service.BrandDomainService;
import com.maven.service.BusinessEmployeeLovelService;
import com.maven.service.BusinessEmployeeTypeService;
import com.maven.service.DepositWithdralOrderDelegateService;
import com.maven.service.EmployeeMoneyChangeTypeService;
import com.maven.service.EnterpriseBrandNoticService;
import com.maven.service.EnterpriseEmployeeInformationService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseInformationService;
import com.maven.service.EnterpriseOperatingBrandService;
import com.maven.service.EnterpriseWebviewService;
import com.maven.service.LogLoginService;
import com.maven.service.PaymentTypeService;
import com.maven.service.ThirdpartyPaymentBankService;
import com.maven.service.ThirdpartyPaymentTypeService;
import com.maven.service.ThirdpartyPaymentTypeSettingService;
import com.maven.service.UserLogsService;
import com.maven.service.WebviewTemplateService;
import com.maven.util.AttrCheckout;
import com.maven.util.BeanToMapUtil;
import com.maven.util.DomainUtils;
import com.maven.util.Encrypt;

@Controller
@RequestMapping("/common")
public class CommonController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			CommonController.class.getName(), OutputManager.LOG_COMMON);
	
	//企业银行
	@Autowired
	private EnterpriseInformationService enterpriseInformationService;
	//员工类别
	@Autowired
	private BusinessEmployeeTypeService businessEmployeeTypeService;
	//员工级别
	@Autowired
	private BusinessEmployeeLovelService businessEmployeeLovelService;
	//银行信息
	@Autowired
	private BankService bankService;
	//支付方式
	@Autowired
	private PaymentTypeService PaymentTypeService;
	//企业用户
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	//存取款审批委托
	@Autowired
	private DepositWithdralOrderDelegateService depositWithdralOrderDelegateService;
	//用户银行卡
	@Autowired
	private EnterpriseEmployeeInformationService enterpriseEmployeeInformationService;
	//登录日记
	@Autowired
	LogLoginService logLoginServiceImpl;
	//品牌
	@Autowired
	EnterpriseOperatingBrandService enterpriseOperatingBrandServiceImpl;
	//账变类型
	@Autowired
	EmployeeMoneyChangeTypeService employeeMoneyChangeTypeServiceImpl;
	//快捷支付类型
	@Autowired
	ThirdpartyPaymentTypeService thirdpartyPaymentTypeServiceImpl;
	//快捷支付银行
	@Autowired
	ThirdpartyPaymentBankService thirdpartyPaymentBankServiceImpl;
	//快捷支付类型对应的属性值
	@Autowired
	ThirdpartyPaymentTypeSettingService thirdpartyPaymentTypeSettingServiceImpl;
	//公告消息
	@Autowired
	private EnterpriseBrandNoticService enterpriseBrandNoticService;
	//UI模板
	@Autowired
	private WebviewTemplateService webviewTemplateServiceImpl;
	//企业关联模板
	@Autowired
	private EnterpriseWebviewService enterpriseWebviewImpl;
	@Autowired
	private UserLogsService userLogsService;
	// 注册链接 
	@Autowired
	private BrandDomainService brandDomainService;
	
	/**
	 * 敏捷开发中
	 * @return
	 */
	@RequestMapping("/Developing")
	public String developing(){
		return "Developing";
	}
	
	/**
	 * 调用获取员工类型数据方法
	 * @return Map<String, Object>
	 */
	@RequestMapping("/getWebviewTemplate")
	@ResponseBody
	public Map<String, Object> getWebviewTemplate(HttpServletRequest request,HttpSession session){
		Map<String, Object> map = getRequestParamters(request);
		try {
			List<EnterpriseWebview> ewList = enterpriseWebviewImpl.queryContactData(map);
			List<String> templatecodes = new ArrayList<String>();
			for (EnterpriseWebview ew1 : ewList) {
				templatecodes.add(ew1.getWebtemplatecode());
			}
			
			List<WebviewTemplate> list = webviewTemplateServiceImpl.queryTemplateInCode(templatecodes);
			map.put("obj", list);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return map;
	}
	
	/**
	 * 获取当前登录用户配置的公告消息
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/getNoticeMessage")
	@ResponseBody
	public Map<String,Object> getNoticeMessage(HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			EnterpriseBrandNotic notic = new EnterpriseBrandNotic();
			notic.setEnterprisecode(ee.getEnterprisecode());
			notic.setBrandcode(ee.getBrandcode());
			List<EnterpriseBrandNotic> list = enterpriseBrandNoticService.takeUserNotic(notic);
			StringBuffer buff = new StringBuffer();
			if(list.size()>0){
				for (EnterpriseBrandNotic brandNotic : list) {
					buff.append(brandNotic.getContent()+"  ");
				}
				map.put("notic", buff.toString());
				return map;
			}
			return map;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return map;
		}
	}
	
	
	/**
	 * 调用获取员工类型数据方法
	 * @return Map<String, Object>
	 */
	@RequestMapping("/getAllEmployeeType")
	@ResponseBody
	public Map<String, Object> getAllEmployeeType(HttpServletRequest request,HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EnterpriseEmployee __ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			List<EnterpriseEmployeeType> list = businessEmployeeTypeService.getAllEmployeeType();
			Iterator<EnterpriseEmployeeType> __iterateor = list.iterator();
			while (__iterateor.hasNext()) {
				EnterpriseEmployeeType __et = __iterateor.next();
				if(__ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.员工.value)
						&&__et.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.企业号.value)){
					__iterateor.remove();
				}
				if(__ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.代理.value)
						&&(__et.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.企业号.value)
								||__et.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.员工.value)
										||__et.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.信用代理.value))){
					__iterateor.remove();
				}
				if(__ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.信用代理.value)
						&&(__et.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.企业号.value)
								||__et.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.员工.value)
										||__et.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.代理.value))){
					__iterateor.remove();
				}
			}
			map.put("obj", list);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return map;
	}
	
	/**
	 * 调用获取员工级别数据方法
	 * @return Map<String, Object>
	 */
	@RequestMapping("/getAllEmployeeLovel")
	@ResponseBody
	public Map<String, Object> getAllEmployeeLovel(HttpServletRequest request,HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			map.put("enterpriseCode", ee.getEnterprisecode());
			List<EnterpriseEmployeeLevel> list = businessEmployeeLovelService.takelevelQuery(map);
			map.put("obj", list);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return map;
	}
	
	/**
	 * 调用获取银行信息方法
	 * @return Map<String, Object>
	 */
	@RequestMapping("/getAllBankInfo")
	@ResponseBody
	public Map<String, Object> getAllBankInfo(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Bank> list = bankService.getAllBankInfo();
			map.put("obj", list);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return map;
	}
	
	/**
	 *查询企业下面的品牌信息
	 * @return Map<String, Object>
	 */
	@RequestMapping("/loadEnterpriseBrand")
	@ResponseBody
	public Map<String, Object> loadEnterpriseBrand(HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			List<EnterpriseOperatingBrand> list = enterpriseOperatingBrandServiceImpl.getEnterpriseBrand(employee.getEnterprisecode());
			map.put("obj", list);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return map;
	}
	
	@RequestMapping("/getEmployeeBanks")
	@ResponseBody
	public Map<String,Object> getEmployeeBanks(HttpServletRequest request,HttpSession session){
		Map<String, Object> object = new HashMap<String, Object>();
		try {
			String employeecode = request.getParameter("employeecode");
			EnterpriseEmployeeInformation eei = new EnterpriseEmployeeInformation();
			eei.setEmployeecode(employeecode);
			eei.setStatus(EnterpriseEmployeeInformation.Enum_status.锁定.value);
			List<EnterpriseEmployeeInformation> eeibanks = enterpriseEmployeeInformationService.select(eei);
			if(eeibanks!=null && eeibanks.size()!=0){
				List<Map<String, Object>> banks = new ArrayList<Map<String,Object>>();
				for (EnterpriseEmployeeInformation e : eeibanks) {
					e.setInformationcode(super.encryptString(e.getInformationcode(), session.getId()));
					banks.add(AttrCheckout.checkout(BeanToMapUtil.convertBean(e, false), true, new String[]{"informationcode","accountname","paymentaccount"}));
				}
				object.put("banks", banks);
			}else{
				object.put("message", "用户未绑定银行卡或银行卡未锁定");
			}
		} catch (Exception e) {
			e.printStackTrace();
			object.put("message", Constant.AJAX_ACTIONFAILD);
		}
		return object; 
	}
	
	/**
	 * 根据银行编码查询
	 * @return Map<String, Object>
	 */
	@RequestMapping("/getBankInfo")
	@ResponseBody
	public Map<String, Object> getBankInfo(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Bank bank = bankService.getBankInfo(request.getParameter("bankcode"));
			map.put("obj", bank);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return map;
	}
	/**
	 * 查询支付方式
	 * @param request
	 * @return
	 */
	@RequestMapping("/findPaymentType")
	@ResponseBody
	public Map<String,Object> getPaymentType(){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			List<PaymentType> list = PaymentTypeService.getAllPaymentType();
			map.put("result", list);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return map;
	}
	/**
	 * 获取登录用户品牌下的所有银行卡信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/findBrandBankInformation")
	@ResponseBody
	public Map<String,Object> findBrandBankInformation(HttpSession session){
		try {
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			EnterpriseInformation eio = new EnterpriseInformation();
			eio.setEnterprisecode(employee.getEnterprisecode());
			List<EnterpriseInformation> list = enterpriseInformationService.select(eio);
			Map<String,Object> resultMap =new HashMap<String,Object>();
			resultMap.put("result", list);
			return resultMap;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 登录密码修改（当前登录用户）
	 * @return Map<String,Object>
	 */
	@RequestMapping("/updatePassword")
	@OperationLog(OparetionDescription.LOGIN_PASSWORD_UPDATE)
	@ResponseBody
	public Map<String,Object> saveModify(HttpServletRequest request, HttpSession session){
		EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
		
		Map<String,Object> obj = getRequestParamters(request);
		
		//不得包含中文汉字或中文符号，也就是不得出现全角字符
		if(com.maven.util.StringUtils.isChinese((String)obj.get("newLoginpassword"))) {
			if (Enum_language.英文.value.equals(session.getAttribute(Constant.LANGUAGE))) {
				return super.packJSON(Constant.BooleanByte.NO, "Password can not include full characters.");
			} else {
				return super.packJSON(Constant.BooleanByte.NO, Enum_MSG.不得出现中文汉字或中文标点符号的全角字符.desc);
			}
		}
		if(obj.get("newLoginpassword").toString().length() > 12) {
			if (Enum_language.英文.value.equals(session.getAttribute(Constant.LANGUAGE))) {
				return super.packJSON(Constant.BooleanByte.NO, "Password length can not be greater than 12 letters.");
			} else {
				return super.packJSON(Constant.BooleanByte.NO, "密码长度不能大于12位");
			}
		}
		if(com.maven.util.StringUtils.stringFilterCheck((String)obj.get("newLoginpassword"))) {
			if (Enum_language.英文.value.equals(session.getAttribute(Constant.LANGUAGE))) {
				return super.packJSON(Constant.BooleanByte.NO, "Password can not include special characters.");
			} else {
				return super.packJSON(Constant.BooleanByte.NO, "不能包含特殊字符，允许输入字母与数字的组合");
			}
		}
		
		obj.put("loginpassword", Encrypt.MD5((String)obj.get("loginpassword")));
		try {
			obj.put("newLoginpassword2", APIServiceUtil.encrypt((String)obj.get("newLoginpassword"), loginEmployee));//对原始密码进行加密
			obj.put("newLoginpassword", Encrypt.MD5((String)obj.get("newLoginpassword")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			int mark = enterpriseEmployeeService.updatePassword(obj);
			if(mark>0){
				
				/************************修改密码时调用接口同步更新各游戏平台的密码**************************/
				try {
					//加try块是为了避免因为更新密码时出错导致整个密码修改失败
					loginEmployee = enterpriseEmployeeService.takeEmployeeByCode(loginEmployee.getEmployeecode());
					new APIServiceNew(loginEmployee.getEnterprisecode()).updatePassword(loginEmployee);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				/************************修改密码时调用接口同步更新各游戏平台的密码**************************/
				
				userLogsService.addActivityBetRecord(new UserLogs(loginEmployee.getEnterprisecode(), loginEmployee.getEmployeecode(), null, 
					UserLogs.Enum_operatype.用户资料业务, "修改登录密码", loginEmployee.getLoginaccount(), null));
				
				if (Enum_language.英文.value.equals(session.getAttribute(Constant.LANGUAGE))) {
					return super.packJSON(Constant.BooleanByte.YES, "Password change complete.");
				} else {
					return super.packJSON(Constant.BooleanByte.YES, "密码修改成功");
				}
			}
			if (Enum_language.英文.value.equals(session.getAttribute(Constant.LANGUAGE))) {
				return super.packJSON(Constant.BooleanByte.NO, "Password change failed.");
			} else {
				return super.packJSON(Constant.BooleanByte.NO, "密码修改失败");
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			if (Enum_language.英文.value.equals(session.getAttribute(Constant.LANGUAGE))) {
				return super.packJSON(Constant.BooleanByte.NO, "System error, please try again later.");
			} else {
				return super.packJSON(Constant.BooleanByte.NO, "密码修改失败,请稍后尝试");
			}
		}
	}
	
	/**
	 * 资金密码修改
	 * @return Map<String,Object>
	 */
	@RequestMapping("/updateCapital")
	@OperationLog(OparetionDescription.CAPITAL_PASSWORD_UPDATE)
	@ResponseBody
	public Map<String,Object> updateCapital(HttpServletRequest request, HttpSession session){
		Map<String,Object> obj = getRequestParamters(request);
		if (obj.get("fundpassword") != null){
			obj.put("fundpassword", Encrypt.MD5((String)obj.get("fundpassword")));
		}
		obj.put("newCapitalpwd", Encrypt.MD5((String)obj.get("newCapitalpwd")));
		try {
			int mark = enterpriseEmployeeService.updateCapital(obj);
			if(mark>0){
				EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
				loginEmployee.setFundpassword(String.valueOf(obj.get("fundpassword")));
				session.setAttribute(Constant.USER_SESSION, loginEmployee);
				
				userLogsService.addActivityBetRecord(new UserLogs(loginEmployee.getEnterprisecode(), loginEmployee.getEmployeecode(), null, 
					UserLogs.Enum_operatype.用户资料业务, "修改资金密码", loginEmployee.getLoginaccount(), null));
				
				if (Enum_language.英文.value.equals(session.getAttribute(Constant.LANGUAGE))) {
					return super.packJSON(Constant.BooleanByte.YES, "Password change complete.");
				} else {
					return super.packJSON(Constant.BooleanByte.YES, "密码修改成功");
				}
			}
			if (Enum_language.英文.value.equals(session.getAttribute(Constant.LANGUAGE))) {
				return super.packJSON(Constant.BooleanByte.NO, "Password change failed.");
			} else {
				return super.packJSON(Constant.BooleanByte.NO, "密码修改失败");
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		if (Enum_language.英文.value.equals(session.getAttribute(Constant.LANGUAGE))) {
			return super.packJSON(Constant.BooleanByte.NO, "System error, please try again later.");
		} else {
			return super.packJSON(Constant.BooleanByte.NO, "密码修改失败,请稍后尝试");
		}
	}
	
	/**
	 * 获取随机密码
	 * @param map
	 * @return Map<String,Object>
	 */
	@RequestMapping("/getRandomPassword")
	@ResponseBody
	public Map<String,Object> getRandomPassword(HttpServletRequest request,HttpSession session){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			//当前账户
			String employeecode = request.getParameter("employeecode");
			//获取登录用户信息
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			//只有员工类型与企业类型的用户具有权限（增加代理与总代号都能重置）
//			if(loginEmployee.getEmployeetypecode().equals(Type.企业号.value) || loginEmployee.getEmployeetypecode().equals(Type.员工.value)){
			if( !loginEmployee.getEmployeetypecode().equals(Type.会员.value) ) { 
				
				//解密标识字段的值
				boolean mark = this.decodeSign(employeecode, session.getId());
				if(mark){
					String getRandomPassword = getRandomPassword(6).toString();
					map.put("status", "success");
					map.put("resetPassword", getRandomPassword);
					return map;
				}
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		map.put("status", "failure");
		return map;
	}
	
	/**
	 * 登录密码重置功能
	 * @param map
	 * @return Map<String,Object>
	 */
	@RequestMapping("/resetLoginPassword")
	@ResponseBody
	@OperationLog(OparetionDescription.ENTERPRISEEMPLOYEEINFO_RESTLOGINPASSWORD)
	public Map<String,Object> resetLoginPassword(HttpServletRequest request,HttpSession session){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			//当前账户
			String employeecode = request.getParameter("employeecode");
			String newpassword = request.getParameter("newpassword");
			
			
			//获取登录用户信息
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			//只有员工类型与企业类型的用户具有权限（增加代理与总代号都能重置）
//			if(loginEmployee.getEmployeetypecode().equals(Type.企业号.value) || loginEmployee.getEmployeetypecode().equals(Type.员工.value) ) {
			if( !loginEmployee.getEmployeetypecode().equals(Type.会员.value) ) { 
				
				
				//解密标识字段的值
				boolean mark = this.decodeSign(employeecode, session.getId());
				if(mark){
					Map<String,Object> mp = new HashMap<String, Object>();
					String getRandomPassword = newpassword;
					
					EnterpriseEmployee targetEmployee = enterpriseEmployeeService.takeEmployeeByCode(employeecode.split("_")[1]);
					
					//密码不得包含中文汉字或中文符号，也就是不得出现全角字符
					if(com.maven.util.StringUtils.isChinese(getRandomPassword)) {
//						super.packJSON(Constant.BooleanByte.NO, Enum_MSG.不得出现中文汉字或中文标点符号的全角字符.desc);
						map.put("status", "failure");
						map.put("message", "不得出现中文汉字或中文标点符号的全角字符");
						return map;
					}
					if(String.valueOf(getRandomPassword).length() > 12) {
//						super.packJSON(Constant.BooleanByte.NO, "密码长度不能大于12位");
						map.put("status", "failure");
						map.put("message", "密码长度不能大于12位");
						return map;
					}
					if(String.valueOf(getRandomPassword).length() < 6 ) {
//						super.packJSON(Constant.BooleanByte.NO, "密码长度不能大于12位");
						map.put("status", "failure");
						map.put("message", "密码长度不能小于6位");
						return map;
					}
					if(com.maven.util.StringUtils.stringFilterCheck(getRandomPassword)) {
//						return super.packJSON(Constant.BooleanByte.NO, "不能包含特殊字符，允许输入字母与数字的组合");
						map.put("status", "failure");
						map.put("message", "不能包含特殊字符，允许输入字母与数字的组合");
						return map;
					}
					if(com.maven.util.StringUtils.isCharAllOrNumberAll(getRandomPassword)) {
//						return Enum_MSG.逻辑事物异常.message("密码必须包含数字和字符");
						map.put("status", "failure");
						map.put("message", "密码应为数字+字符");
						return map;
					}
					
					System.out.println("新的登录密码="+getRandomPassword);
					mp.put("employeecode", targetEmployee.getEmployeecode());
					mp.put("loginpassword2", APIServiceUtil.encrypt(getRandomPassword, targetEmployee));//对原始密码进行加密
					mp.put("loginpassword", Encrypt.MD5(getRandomPassword));
					
					enterpriseEmployeeService.tc_restPassword(mp);
					
					
					/************************修改密码时调用接口同步更新各游戏平台的密码**************************/
					try {
						//加try块是为了避免因为更新密码时出错导致整个密码修改失败
						targetEmployee = enterpriseEmployeeService.takeEmployeeByCode(targetEmployee.getEmployeecode());
						new APIServiceNew(loginEmployee.getEnterprisecode()).updatePassword(targetEmployee);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					/************************修改密码时调用接口同步更新各游戏平台的密码**************************/
					
					userLogsService.addActivityBetRecord(new UserLogs(targetEmployee.getEnterprisecode(), targetEmployee.getEmployeecode(), targetEmployee.getLoginaccount(), 
						     UserLogs.Enum_operatype.用户资料业务, "重置登录密码", loginEmployee.getLoginaccount(), null));
					
					map.put("status", "success");
					map.put("resetPassword", getRandomPassword);
					return map;
				} else {
					map.put("status", "failure");
					map.put("message", "解密失败");
				}
			} else {
				map.put("status", "failure");
				map.put("message", "提示：不能修改非会员的密码");
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		map.put("status", "failure");
		return map;
	}
	
	/**
	 * 资金密码重置功能
	 * @param map
	 * @return Map<String,Object>
	 */
	@RequestMapping("/resetCapitalPassword")
	@ResponseBody
	@OperationLog(OparetionDescription.ENTERPRISEEMPLOYEEINFO_RESTCAPITALPASSWORD)
	public Map<String,Object> resetCapitalPassword(HttpServletRequest request,HttpSession session){
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			//当前账户
			String employeecode = request.getParameter("employeecode");
			String newpassword = request.getParameter("newpassword");
			
			//获取登录用户信息
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			//只有员工类型与企业类型的用户具有权限（增加代理与总代号都能重置）
//			if(loginEmployee.getEmployeetypecode().equals(Type.企业号.value) || loginEmployee.getEmployeetypecode().equals(Type.员工.value)){
			if( !loginEmployee.getEmployeetypecode().equals(Type.会员.value) ) { 
				
				//解密标识字段的值
				boolean mark = this.decodeSign(employeecode, session.getId());
				if(mark){
					Map<String,Object> mp = new HashMap<String, Object>();
					String getRandomPassword = newpassword;
					System.out.println("新的资金密码="+getRandomPassword);
					mp.put("employeecode", employeecode.split("_")[1]);
					mp.put("fundpassword", Encrypt.MD5(getRandomPassword));
					enterpriseEmployeeService.tc_restPassword(mp);
					map.put("status", "success");
					map.put("resetPassword", getRandomPassword);
					
					EnterpriseEmployee targetEmployee = enterpriseEmployeeService.takeEmployeeByCode(employeecode.split("_")[1]);
					
					userLogsService.addActivityBetRecord(new UserLogs(targetEmployee.getEnterprisecode(), targetEmployee.getEmployeecode(), targetEmployee.getLoginaccount(), 
						     UserLogs.Enum_operatype.用户资料业务, "重置资金密码", loginEmployee.getLoginaccount(), null));
					
					return map;
				} else {
					map.put("status", "failure");
					map.put("message", "解密失败");
				}
			} else {
				map.put("status", "failure");
				map.put("message", "提示：不能修改非会员的密码");
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		map.put("status", "failure");
		return map;
	}
	
	/**
	 * 生成一个随机密码
	 * @param passwrodLength
	 * @return
	 */
	public String getRandomPassword(int passwrodLength){
		StringBuffer sb = new StringBuffer();
		int max = 61;
		int count=0;
		Random random = new Random();
		char[] character ={'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k','l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w','x', 'y', 'z',
						   'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K','L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W','X', 'Y', 'Z', 
						   '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
		while (count < passwrodLength) {
			int index = Math.abs(random.nextInt(max));
			sb.append(character[index]);
			count++;
		}
		return sb.toString();
	}
	/**
	 * 查询存取款审批记录
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryDepositTakeApproveRecord")
	@ResponseBody
	public Map<String,Object> queryDepositTakeApproveRecord(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			DepositWithdralOrderDelegate object = getRequestParamters(request,DepositWithdralOrderDelegate.class);
			List<DepositWithdralOrderDelegate> list = depositWithdralOrderDelegateService.takeOrdernumberDeletegate(object);
			map.put("rows", list);
			return map;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return map;
	}
	/**
	 * 验证账号是否为当前登录用户的下级
	 * @param request
	 * @return ,produces="text/html;charset=UTF-8
	 */
	@RequestMapping(value="/validateEmployee")
	@ResponseBody
	public Map<String,Object> validateEmployee(HttpServletRequest request,HttpSession session){
		Map<String,Object> object = getRequestParamters(request);
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			List<EnterpriseEmployee> list = enterpriseEmployeeService.queryEmployeeIsExist(object);
			if(ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.代理.value)
					||ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.信用代理.value)){
				if(list.size() == 0 || list.size()!=1 
						|| list.get(0).getUpperlevel().indexOf(ee.getEmployeecode())<0){
					return super.packJSON(Constant.BooleanByte.NO, "输入账号不正确");
				}
			}
			if(list != null && list.size() > 0) {
				EnterpriseEmployee account = list.get(0);
				if(!account.getEmployeetypecode().equals(Type.会员.value)){
					return super.packJSON(Constant.BooleanByte.NO, "账号不是会员类型");
				}
				
				if( !ee.getEnterprisecode().equals(account.getEnterprisecode())) {
					return super.packJSON(Constant.BooleanByte.NO, "没有对该会员操作权限");
				}
				
				return super.packJSON(Constant.BooleanByte.YES, account.getEmployeecode());
			}
			return super.packJSON(Constant.BooleanByte.NO, "没有找到用户");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "用户身份验证错误");
		}
	}
	/**
	 * 验证账号是否存在,并返回该对象
	 * @param request
	 * @return ,produces="text/html;charset=UTF-8
	 */
	@RequestMapping(value="/employeeIsExist")
	@ResponseBody
	public Map<String,Object> employeeIsExist(HttpServletRequest request,HttpSession session){
		Map<String,Object> result = new HashMap<String,Object>();
		String loginaccount= request.getParameter("loginaccount");
		try {
			List<EnterpriseEmployee> list = enterpriseEmployeeService.queryEmployeeIsExist(loginaccount);
			if(list.size() == 0 || list.size()!=1){
				result.put("dont_exist", "输入账号不正确");
				return result;
			}
			
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			EnterpriseEmployee __eee = list.get(0);
			if( !ee.getEnterprisecode().equals(__eee.getEnterprisecode())) {
				result.put("dont_exist", "没有对该会员操作权限");
				return result;
			}
			
			result.put("exist", list.get(0).getEmployeecode());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return result;
	}
	/**
	 * 验证域名是否解析
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/isDomainResolution")
	@ResponseBody
	public Map<String, Object> domainCheck(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", "0");
		try {
			String domainlink= request.getParameter("domainlink");
			int count = brandDomainService.queryDetectionDomainLinkExit(domainlink);
			if (count != 0) {
				result.put("status", "-1");
				result.put("msg", "该域名被占用");
				return result;
			}
			// 验证是否为含二级域名
			if(!DomainUtils.checkDomain(domainlink)) {
				result.put("status", "-1");
				result.put("msg", "域名必须为二级域名");
				return result;
			}
			// 检测域名是否解析
			if(!DomainUtils.isDomainResolution(domainlink)) {
				result.put("status", "1");
				result.put("msg", "域名未解析");
				return result;
			}
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			result.put("status", "-1");
			result.put("msg", "参数错误");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			result.put("status", "-1");
			result.put("msg", "网络异常");
		}
		return result;
	}
	/**
	 * 查询用户登录日记
	 * @param request
	 * @return
	 */
	@RequestMapping("/findEmployeeLoginLog")
	@ResponseBody
	public Map<String,Object> findEmployeeLoginLog(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Map<String,Object> object = getRequestParamters(request);
			List<LogLogin> list = logLoginServiceImpl.findEmployeeLoginLog(object);
			map.put("rows", list);
			return map;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return map;
	}
	/**
	 * 查询账变类型
	 * @param request
	 * @return
	 */
	@RequestMapping("/findEmployeeMoneyChangeType")
	@ResponseBody
	public Map<String,Object> findEmployeeMoneyChangeType(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> parameter = getRequestParamters(request);
		try {
			List<EmployeeMoneyChangeType> list = employeeMoneyChangeTypeServiceImpl.findEmployeeMoneyChangeType(parameter);
			map.put("result", list);
			return map;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.getStackTrace();
		}
		return map;
	}
	
	/**
	 * 查询快捷支付类型
	 * @param request
	 * @return
	 */
	@RequestMapping("/loadThirdpartyPaymentType")
	@ResponseBody
	public Map<String,Object> loadThirdpartyPaymentType(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		List<ThirdpartyPaymentType> list = thirdpartyPaymentTypeServiceImpl.takeThirdpartyPaymentType();
		map.put("result", list);
		return map;
	}
	
	/**
	 * 查询快捷支付银行名称
	 * @param request
	 * @return
	 */
	@RequestMapping("/loadThirdpartyPaymentBankName")
	@ResponseBody
	public Map<String,Object> loadThirdpartyPaymentBankName(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			List<Bank> list = bankService.select(null);
			map.put("result", list);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return map;
	}
	
	
	/**
	 * 查询快捷支付类型对应的值
	 * @param request
	 * @return
	 */
	@RequestMapping("/loadThirdpartyPaymentTypeSetting")
	@ResponseBody
	public Map<String,Object> loadThirdpartyPaymentTypeSetting(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			List<ThirdpartyPaymentTypeSetting> list = thirdpartyPaymentTypeSettingServiceImpl.takeThirdpartyPaymentTypeSetting(request.getParameter("thirdpartyPaymentTypeCode"));
			request.getSession().setAttribute("thirdpartyPaymentTypeSetting", list);
			map.put("result", list);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
