package com.maven.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy.pull.common.util.game.sa.MD5Encoder;
import com.maven.constant.Constant;
import com.maven.constant.Enum_MSG;
import com.maven.entity.BrandDomain;
import com.maven.entity.BrandDomain.Enum_copyright;
import com.maven.entity.BrandDomain.Enum_linkstatus;
import com.maven.entity.EmployeeGamecataloyBonus;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployee.Enum_employeestatus;
import com.maven.entity.EnterpriseEmployee.Enum_onlinestatus;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.UserLogs;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.game.APIServiceNew;
import com.maven.game.APIServiceUtil;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.payment.th.utils.ArgumentValidationException;
import com.maven.service.BrandDomainService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseOperatingBrandService;
import com.maven.service.UserLogsService;
import com.maven.util.BeanToMapUtil;

@Controller
@RequestMapping("/res")
public class AgentRegisterController extends BaseController {

	private static LoggerManager log = LoggerManager.getLogger(AgentRegisterController.class.getName(),OutputManager.LOG_AGENTREGISTER);

	/** 企业员工 */
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	/** 注册链接 */
	@Autowired
	private BrandDomainService brandDomainService;
	/** 企业品牌 */
	@Autowired
	private EnterpriseOperatingBrandService enterpriseOperatingBrandService;
	@Autowired
	private UserLogsService userLogsService;
	
	/**
	 * 根据推广链接进入用户注册页面
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/register")
	public String register(HttpServletRequest request, HttpSession session) {
		
		session.setAttribute("registercode", request.getParameter("sign"));
		return "/registerlink/agentregister";
	}

	/**
	 * 根据链接代理类型账号
	 * 
	 * @param enterpriseEmployee
	 * @return String
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> saveAgentEmployee(HttpSession session, HttpServletRequest request) {
		try {
			Map<String,Object> __rmsg = new HashMap<String, Object>(); 
			Map<String, Object> object = super.getRequestParamters(request);
			// 注册链接编码
			String registercode = (String) session.getAttribute("registercode");
			// 根据注册链接编码查询
			BrandDomain employeeRDomain = brandDomainService.queryByDomainLink(registercode);
			if (employeeRDomain == null || employeeRDomain.getCopyright().equals(Enum_copyright.私有.value)) {
				return super.packJSON(Constant.BooleanByte.NO, "注册链接不存在");
			}
			if (employeeRDomain.getLinkstatus().equals(Enum_linkstatus.禁用.value)) {
				return super.packJSON(Constant.BooleanByte.NO, "注册链接已禁用");
			}
			EnterpriseEmployee __enterpriseEmployee = BeanToMapUtil.convertMap(object, EnterpriseEmployee.class);
			if(StringUtils.isBlank(__enterpriseEmployee.getLoginaccount())
					||__enterpriseEmployee.getLoginaccount().length()<6
					||__enterpriseEmployee.getLoginaccount().length()>12){
				return super.packJSON(Constant.BooleanByte.NO, Enum_MSG.用户名长度不匹配.desc);
			}
			
			//不得包含中文汉字或中文符号，也就是不得出现全角字符
			if(com.maven.util.StringUtils.isChinese(__enterpriseEmployee.getLoginaccount())) {
				return super.packJSON(Constant.BooleanByte.NO, Enum_MSG.不得出现中文汉字或中文标点符号的全角字符.desc);
			}
			//不得包含中文汉字或中文符号，也就是不得出现全角字符
			if(com.maven.util.StringUtils.isChinese(__enterpriseEmployee.getLoginpassword())) {
				return super.packJSON(Constant.BooleanByte.NO, Enum_MSG.不得出现中文汉字或中文标点符号的全角字符.desc);
			}
			if(com.maven.util.StringUtils.stringFilterCheck(__enterpriseEmployee.getLoginaccount())) {
				return super.packJSON(Constant.BooleanByte.NO, "不能包含特殊字符，允许输入字母与数字的组合");
			}
			if(__enterpriseEmployee.getLoginpassword().length() > 12) {
				return super.packJSON(Constant.BooleanByte.NO, "密码长度不能大于12位");
			}
			if(com.maven.util.StringUtils.stringFilterCheck(__enterpriseEmployee.getLoginpassword())) {
				return super.packJSON(Constant.BooleanByte.NO, "不能包含特殊字符，允许输入字母与数字的组合");
			}
			List<EnterpriseEmployee> isExits = enterpriseEmployeeService.queryEmployeeIsExist(__enterpriseEmployee.getLoginaccount());
			if(isExits!=null && isExits.size()>0){
				return super.packJSON(Constant.BooleanByte.NO, Enum_MSG.用户名已注册.desc);
			}
			
			__enterpriseEmployee.setLoginpassword2(APIServiceUtil.encrypt(__enterpriseEmployee.getLoginpassword(), __enterpriseEmployee));//对原始密码进行加密
			__enterpriseEmployee.setLoginpassword(MD5Encoder.encode(__enterpriseEmployee.getLoginpassword()));//对密码加密
			
			
			
			EnterpriseEmployee __parentEmployee = enterpriseEmployeeService.takeEmployeeByCode(employeeRDomain.getEmployeecode());
			StringBuffer __sbf = createAgent(registercode, employeeRDomain, __enterpriseEmployee, __parentEmployee);

			EnterpriseOperatingBrand __eob = new EnterpriseOperatingBrand();
			__eob.setEnterprisecode(__enterpriseEmployee.getEnterprisecode());
			__eob = enterpriseOperatingBrandService.selectFirst(__eob);
			if (__eob == null) {
				__rmsg.put("status", 1);
				__rmsg.put("username", __enterpriseEmployee.getLoginaccount());
				__rmsg.put("login", super.getBasePath());
				return __rmsg;
			}
			BrandDomain __defualtDomain = new BrandDomain();
			__defualtDomain.setEnterprisecode(__enterpriseEmployee.getEnterprisecode());
			__defualtDomain.setBrandcode(__eob.getBrandcode());
			__defualtDomain.setEmployeecode(__enterpriseEmployee.getEmployeecode());
			__defualtDomain.setParentemployeecode(__enterpriseEmployee.getParentemployeecode());
			__defualtDomain.setEmployeetype(EnterpriseEmployeeType.Type.会员.value);
			__defualtDomain.setDividend(new BigDecimal(0.0));
			__defualtDomain.setShare(new BigDecimal(0.0));
			__defualtDomain.setBonus(__sbf.toString());
			__defualtDomain.setDomaintype(BrandDomain.Enum_domaintype.会员站点.value.byteValue());
			__defualtDomain.setCopyright(Byte.valueOf(Enum_copyright.私有.value));
			__defualtDomain.setIsdefualt(Constant.BooleanByte.NO.byteValue());
			__defualtDomain.setLinkstatus(String.valueOf(BrandDomain.Enum_linkstatus.启用.value));
			__defualtDomain.setDatastatus(String.valueOf(Constant.Enum_DataStatus.正常.value));
			String __domain = brandDomainService.tc_createAgentDefualtDomain(__defualtDomain);
			
			userLogsService.addActivityBetRecord(new UserLogs(__enterpriseEmployee.getEnterprisecode(), __enterpriseEmployee.getEmployeecode(), __enterpriseEmployee.getLoginaccount(), 
				     UserLogs.Enum_operatype.用户信息业务, "根据链接注册代理:"+employeeRDomain.getLinkdomain(), null, null));
			
			
			if (__domain == null) {
				__rmsg.put("status", 1);
				__rmsg.put("username", __enterpriseEmployee.getLoginaccount());
				__rmsg.put("login", super.getBasePath());
				return __rmsg;
			} else {
				__rmsg.put("status", 2);
				__rmsg.put("username", __enterpriseEmployee.getLoginaccount());
				__rmsg.put("login", super.getBasePath());
				__rmsg.put("domain", __domain);
				return __rmsg;
			}
		}catch(LogicTransactionRollBackException e){
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(0, "注册失败，请稍后尝试");
		}
	}

	private StringBuffer createAgent(String registercode, BrandDomain employeeRDomain,
			EnterpriseEmployee __enterpriseEmployee, EnterpriseEmployee __parentEmployee) throws Exception {
		// 分红
		__enterpriseEmployee.setDividend(employeeRDomain.getDividend());
		// 占成
		__enterpriseEmployee.setShare(employeeRDomain.getShare());
		// 绑定推广链接
		__enterpriseEmployee.setRegistercode(registercode);
		// 员工类型编码
		__enterpriseEmployee.setEmployeetypecode(employeeRDomain.getEmployeetype());
		// 员工的上级编码为当前登录用户编码
		__enterpriseEmployee.setParentemployeecode(employeeRDomain.getEmployeecode());
		// 员工的上级账号为当前登录用户账号
		__enterpriseEmployee.setParentemployeeaccount(__parentEmployee.getLoginaccount());
		// 企业编码
		__enterpriseEmployee.setEnterprisecode(employeeRDomain.getEnterprisecode());
		// 员工品牌编码为当前登录用户的
		__enterpriseEmployee.setBrandcode(employeeRDomain.getBrandcode());
		// 在线状态{ 0 : 未登录 ,1 : 在线 , 2 : 下线}
		__enterpriseEmployee.setOnlinestatus(Byte.valueOf(Enum_onlinestatus.离线.value));
		// 员工状态{0:逻辑删除,1: 启用, 2:锁定游戏,4: 禁用}
		__enterpriseEmployee.setEmployeestatus(Enum_employeestatus.启用.value.byteValue());
		//默认资金密码为空
		__enterpriseEmployee.setFundpassword("");
		// 洗码数据封装
		List<EmployeeGamecataloyBonus> list = new ArrayList<EmployeeGamecataloyBonus>();
		// 分割洗码字符串
		String[] temp = employeeRDomain.getBonus().split("\\|");
		StringBuffer __sbf = new StringBuffer();
		for (int i = 0; i < temp.length; i++) {
			EmployeeGamecataloyBonus person = new EmployeeGamecataloyBonus();
			String[] str1 = temp[i].split(":");
			__sbf.append(str1[0]).append(":0");
			for (int j = 0; j < str1.length; j++) {
				if (j % 2 == 0) {
					String[] str2 = str1[j].split("_");
					for (int k = 0; k < str2.length; k++) {
						if (k % 2 == 0) {
							person.setCategorytype(str2[k]);
						} else {
							person.setGametype(str2[k]);
						}
					}
				} else {
					person.setBonus(BigDecimal.valueOf(Double.valueOf(str1[j])));
				}
			}
			list.add(person);
		}
		if (list.size() == 0) {
			throw new ArgumentValidationException("代理注册链接:" + employeeRDomain.getDomainlink() + " 洗码为空");
		}
		boolean isDirectly = __parentEmployee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.企业号.value);
		enterpriseEmployeeService.tc_saveUser(__enterpriseEmployee, list, isDirectly);
		return __sbf;
	}

	@Override
	public LoggerManager getLogger() {
		return log;
	}

}
