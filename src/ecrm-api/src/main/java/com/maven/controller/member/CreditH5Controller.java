package com.maven.controller.member;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy.pull.common.util.Base64;
import com.hy.pull.common.util.SCom;
import com.hy.pull.common.util.api.FileHelper;
import com.maven.cache.SystemCache;
import com.maven.constant.Constant;
import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.entity.AgentSiteContact;
import com.maven.entity.Baccarath5Balance;
import com.maven.entity.Baccarath5Exchange;
import com.maven.entity.Baccarath5Rebate;
import com.maven.entity.BettingAllDay;
import com.maven.entity.BettingHqNhq;
import com.maven.entity.BrandDomain;
import com.maven.entity.DepositWithdralOrderDelegate;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EmployeeApiAccoutPasswordJob;
import com.maven.entity.EmployeeGamecataloyBonus;
import com.maven.entity.EmployeeMessage;
import com.maven.entity.EmployeeMessage.Enum_readstatus;
import com.maven.entity.EmployeeMessageText;
import com.maven.entity.EmployeeMoneyChange;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseBrandNotic;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeCapitalAccount;
import com.maven.entity.EnterpriseEmployeeLevel;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.EnterpriseOperatingBrandGame;
import com.maven.entity.EnterpriseOperatingBrandPay;
import com.maven.entity.Game;
import com.maven.entity.LogLogin;
import com.maven.entity.TakeDepositRecord;
import com.maven.entity.UserLogs;
import com.maven.entity.BrandDomain.Enum_copyright;
import com.maven.entity.BrandDomain.Enum_domaintype;
import com.maven.entity.BrandDomain.Enum_linkstatus;
import com.maven.entity.DepositWithdralOrderDelegate.Enum_auditresult;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EnterpriseEmployee.Enum_employeestatus;
import com.maven.entity.EnterpriseEmployee.Enum_onlinestatus;
import com.maven.entity.EnterpriseEmployeeType.Type;
import com.maven.entity.PaymentType.Enum_PayType;
import com.maven.entity.TakeDepositRecord.Enum_orderstatus;
import com.maven.entity.TakeDepositRecord.Enum_ordertype;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.game.APIServiceNew;
import com.maven.game.APIServiceUtil;
import com.maven.game.OrderNewUtil;
import com.maven.game.enums.GameEnum;
import com.maven.game.enums.GameEnum.Enum_GameType;
import com.maven.interceptor.RepeatRequestIntercept;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.Baccarath5BalanceService;
import com.maven.service.Baccarath5ExchangeService;
import com.maven.service.Baccarath5RebateService;
import com.maven.service.BettingAllDayService;
import com.maven.service.BettingGameService;
import com.maven.service.BrandDomainService;
import com.maven.service.BusinessEmployeeLovelService;
import com.maven.service.DepositWithdralOrderDelegateService;
import com.maven.service.EmployeeApiAccoutPasswordJobService;
import com.maven.service.EmployeeApiAccoutService;
import com.maven.service.EmployeeGamecataloyBonusService;
import com.maven.service.EmployeeMessageService;
import com.maven.service.EmployeeMoneyChangeService;
import com.maven.service.EnterpriseBrandNoticService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseService;
import com.maven.service.GameService;
import com.maven.service.LogLoginService;
import com.maven.service.TakeDepositRecoredService;
import com.maven.service.UserLogsService;
import com.maven.service.impl.BettingHqNhqServiceImpl;
import com.maven.util.AttrCheckout;
import com.maven.util.BeanToMapUtil;
import com.maven.util.Encrypt;
import com.maven.util.ExpiryMap;
import com.maven.util.NHQUtils;
import com.maven.util.RegularCheck;
import com.maven.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * 信用代理系统H5
 * @author Administrator
 *
 */
@RequestMapping("/credit")
@Controller
public class CreditH5Controller extends BaseController{
	
	private static List<String> ALL_AGENTTYPE = new ArrayList<String>(){{this.add(EnterpriseEmployeeType.Type.代理.value);this.add(EnterpriseEmployeeType.Type.信用代理.value);}};
	
	private static LoggerManager log = LoggerManager.getLogger(
			CreditH5Controller.class.getName(), OutputManager.LOG_USER_FETCHRECORD);
	
	@Autowired
	private EmployeeGamecataloyBonusService employeeGamecataloyBonusService;
	@Autowired
	private BrandDomainService brandDomainService;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private Baccarath5ExchangeService baccarath5ExchangeService;
	@Resource(name="bettingHqNhqServiceImpl")
	private BettingGameService<BettingHqNhq> bettingHqNhqService;
	@Autowired
	private LogLoginService logLoginService;
	@Autowired
	private BusinessEmployeeLovelService businessEmployeeLovelService;
	@Autowired
	private UserLogsService userLogsService;
	@Autowired
	private EmployeeMoneyChangeService employeeMoneyChangeService;
	@Autowired
	private GameService gameService;
	@Autowired
	private EnterpriseEmployeeCapitalAccountService capitalAccountService;
	@Autowired
	private EmployeeApiAccoutPasswordJobService employeeApiAccoutPasswordJobService;
	@Autowired
	private TakeDepositRecoredService takeDepositRecoredService;
	@Autowired
	private EmployeeApiAccoutService employeeApiAccoutService;
	@Autowired
	private EmployeeMessageService employeeMessageService;
	@Autowired
	private Baccarath5BalanceService baccarath5BalanceService;
	@Autowired
	private Baccarath5RebateService baccarath5RebateService;
	@Autowired
	private BettingAllDayService bettingAllDayService;
	@Autowired
	private DepositWithdralOrderDelegateService depositWithdralOrderDelegateService;
	@Autowired
	private EnterpriseBrandNoticService enterpriseBrandNoticService;
	
	/**
	 * 专用登录登陆
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/login",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String login(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[]{"loginaccount","loginpassword","loginip","browserversion","opratesystem"});
			String loginpassword = String.valueOf(object.get("loginpassword"));
			object.put("loginpassword", Encrypt.MD5(String.valueOf(object.get("loginpassword"))));
			EnterpriseEmployee ee = enterpriseEmployeeService.getLogin(object);
			if(ee==null) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户名或密码错误.desc);
			}
			if(ee.getEmployeestatus()==Enum_employeestatus.禁用.value.byteValue()){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户已禁用.desc);
			}
			if(StringUtils.isNotBlank(ee.getFundpassword())){ 
				ee.setFundpassword("true");
			}else{
				ee.setFundpassword("false");
			}
			
			if( !ee.getEmployeetypecode().equals(Type.代理.value) && !ee.getEmployeetypecode().equals(Type.信用代理.value)){
				return Enum_MSG.逻辑事物异常.message("非代理用户不能登录！");
			}
			
			//登陆日志
			loginLog(object, ee);
			
			if(ee.getLoginpassword2() == null || ee.getLoginpassword2().equals("")) {
				//初始化
				object.put("loginpassword2", APIServiceUtil.encrypt(loginpassword, ee));//对原始密码进行加密
				object.put("loginpassword", Encrypt.MD5(loginpassword));
				object.put("employeecode", ee.getEmployeecode());
				int status = enterpriseEmployeeService.tc_restPassword(AttrCheckout.checkout(object,true,new String[]{"loginpassword","employeecode","loginpassword2"}));
			}
			
			
			
			Map<String, Object> result = AttrCheckout.checkout(BeanToMapUtil.convertBean(ee,true),
					true, 
					new String[]{"loginaccount"}, new String[]{"enterprisecode","brandcode","employeecode","displayalias",
					"employeelevelcode","employeelevelname","employeetypecode",
					"employeetypename","logindatetime","lastlogintime","parentemployeecode","parentemployeeaccount","fundpassword","apiurl","phoneno","phonestatus","email","qq","wechat"
				});
			//根据算法计算出当前用户所在的等级并返回
//			EnterpriseEmployeeLevel level = getLevelH5(ee.getEnterprisecode(), ee.getEmployeecode());
//			ee.setEmployeelevelcode(level.getEmployeeLevelCode());
//			ee.setEmployeelevelname(level.getEmployeeLevelName());
//			result.put("employeelevelindex", level.getOrd());//返回当前会员等级的索引
			
			
			//修改最后登录时间
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("lastlogintime", new Date());
			data.put("employeecode", ee.getEmployeecode());
			enterpriseEmployeeService.tc_restPassword(data);
			
			
			
			System.out.println("Server Print:"+Enum_MSG.成功.message(ee));
			return Enum_MSG.成功.message(result);
			
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch(LogicTransactionRollBackException e){
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	/**
	 * 用户注册
	 * @return
	 */
	@RequestMapping(value={"/RegisterUser"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String RegisterUser(HttpServletRequest __request){
		try {
			//参数解密与签名认证
			Map<String,Object> __object = super.apiDecode(super.getRequestParamters(__request));
			//必填参数验证
			AttrCheckout.checkout(__object, false, new String[]{"enterprisecode","parentemployeecode","loginaccount","loginpassword","fundpassword","displayalias"});
			String parentemployeecode = __object.get("parentemployeecode").toString();
						
			//填充参数到对象
			EnterpriseEmployee __agentObject = BeanToMapUtil.convertMap(__object, EnterpriseEmployee.class);

			//关键参数验证
			if(StringUtils.isBlank(__agentObject.getLoginaccount()) ||__agentObject.getLoginaccount().length()<6 ||__agentObject.getLoginaccount().length()>12){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户名长度不匹配.desc);
			}
			//不得包含中文汉字或中文符号，也就是不得出现全角字符
			if(com.maven.util.StringUtils.isChinese(__agentObject.getLoginaccount())) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.不得出现中文汉字或中文标点符号的全角字符.desc);
			}
			//不得包含中文汉字或中文符号，也就是不得出现全角字符
			if(com.maven.util.StringUtils.isChinese(__agentObject.getLoginpassword())) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.不得出现中文汉字或中文标点符号的全角字符.desc);
			}
			if(com.maven.util.StringUtils.stringFilterCheck(__agentObject.getLoginaccount())) {
				return Enum_MSG.逻辑事物异常.message("不能包含特殊字符，允许输入字母与数字的组合");
			}
			if(__agentObject.getLoginpassword().length() > 12) {
				return Enum_MSG.逻辑事物异常.message("密码长度不能大于12位");
			}
			if(com.maven.util.StringUtils.stringFilterCheck(__agentObject.getLoginpassword())) {
				return Enum_MSG.逻辑事物异常.message("不能包含特殊字符，允许输入字母与数字的组合");
			}
			if(com.maven.util.StringUtils.isUpperOrUpper(__agentObject.getLoginaccount())) {
				return Enum_MSG.逻辑事物异常.message("账号不能含有空格或大写字符");
			}
			if(com.maven.util.StringUtils.isUpperOrUpper(__agentObject.getLoginpassword())) {
				return Enum_MSG.逻辑事物异常.message("密码不能含有空格或大写字符");
			}
			
			List<EnterpriseEmployee> isExits = enterpriseEmployeeService.queryEmployeeIsExist(__agentObject.getLoginaccount());
			if(isExits!=null && isExits.size()>0){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户名已注册.desc);
			}
			
			//获取推广用户的信息
			EnterpriseEmployee employee = enterpriseEmployeeService.takeEmployeeByCode(parentemployeecode);
			//根据站点对应的注册参数设置用户信息
			__agentObject.setBrandcode(employee.getBrandcode());
			__agentObject.setParentemployeecode(employee.getEmployeecode());
			__agentObject.setEmployeetypecode(EnterpriseEmployeeType.Type.会员.value);
			__agentObject.setLoginpassword2(APIServiceUtil.encrypt(__agentObject.getLoginpassword(), __agentObject));//对原始密码进行加密
			__agentObject.setLoginpassword(Encrypt.MD5(__agentObject.getLoginpassword()));
			__agentObject.setFundpassword(Encrypt.MD5(__agentObject.getFundpassword()));
			__agentObject.setDividend(new BigDecimal("0"));
			__agentObject.setShare(new BigDecimal("0"));
			__agentObject.setRegistercode(null);
			__agentObject.setOnlinestatus(Byte.valueOf(Enum_onlinestatus.离线.value));
			__agentObject.setEmployeestatus(Enum_employeestatus.启用.value.byteValue());
			__agentObject.setParentemployeeaccount(employee.getLoginaccount());
			//注册账号
			boolean isDirectly = employee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.企业号.value);
			enterpriseEmployeeService.tc_saveUser(__agentObject,null,isDirectly);
			
			userLogsService.addActivityBetRecord(new UserLogs(__agentObject.getEnterprisecode(), __agentObject.getEmployeecode(), __agentObject.getLoginaccount(), 
					UserLogs.Enum_operatype.用户资料业务, "会员注册用户", null, null));
			
			
			return Enum_MSG.成功.message("注册成功");
		}catch(ArgumentValidationException __ex){
			log.Error(__ex.getMessage(), __ex);
			return Enum_MSG.参数错误.message(__ex.getMessage());
		}catch(LogicTransactionRollBackException __ex){
			return Enum_MSG.逻辑事物异常.message(__ex.getMessage());
		}catch (Exception __ex) {
			log.Error(__ex.getMessage(), __ex);
			return Enum_MSG.系统错误.message(null);
		}
	}
	/**
	 * 代理注册
	 * @return
	 */
	@RequestMapping(value={"/RegisterAgent"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String RegisterAgent(HttpServletRequest __request){
		try {
			//参数解密与签名认证
			Map<String,Object> __object = super.apiDecode(super.getRequestParamters(__request));
			//必填参数验证
			AttrCheckout.checkout(__object, false, new String[]{"enterprisecode","parentemployeecode","loginaccount","loginpassword","fundpassword","displayalias"});
			String parentemployeecode = __object.get("parentemployeecode").toString();
						
			//填充参数到对象
			EnterpriseEmployee __agentObject = BeanToMapUtil.convertMap(__object, EnterpriseEmployee.class);

			//关键参数验证
			if(StringUtils.isBlank(__agentObject.getLoginaccount()) ||__agentObject.getLoginaccount().length()<6 ||__agentObject.getLoginaccount().length()>12){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户名长度不匹配.desc);
			}
			//不得包含中文汉字或中文符号，也就是不得出现全角字符
			if(com.maven.util.StringUtils.isChinese(__agentObject.getLoginaccount())) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.不得出现中文汉字或中文标点符号的全角字符.desc);
			}
			//不得包含中文汉字或中文符号，也就是不得出现全角字符
			if(com.maven.util.StringUtils.isChinese(__agentObject.getLoginpassword())) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.不得出现中文汉字或中文标点符号的全角字符.desc);
			}
			if(com.maven.util.StringUtils.stringFilterCheck(__agentObject.getLoginaccount())) {
				return Enum_MSG.逻辑事物异常.message("不能包含特殊字符，允许输入字母与数字的组合");
			}
			if(__agentObject.getLoginpassword().length() > 12) {
				return Enum_MSG.逻辑事物异常.message("密码长度不能大于12位");
			}
			if(com.maven.util.StringUtils.stringFilterCheck(__agentObject.getLoginpassword())) {
				return Enum_MSG.逻辑事物异常.message("不能包含特殊字符，允许输入字母与数字的组合");
			}
			if(com.maven.util.StringUtils.isUpperOrUpper(__agentObject.getLoginaccount())) {
				return Enum_MSG.逻辑事物异常.message("账号不能含有空格或大写字符");
			}
			if(com.maven.util.StringUtils.isUpperOrUpper(__agentObject.getLoginpassword())) {
				return Enum_MSG.逻辑事物异常.message("密码不能含有空格或大写字符");
			}
			
			List<EnterpriseEmployee> isExits = enterpriseEmployeeService.queryEmployeeIsExist(__agentObject.getLoginaccount());
			if(isExits!=null && isExits.size()>0){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户名已注册.desc);
			}
			
			//获取推广用户的信息
			EnterpriseEmployee employee = enterpriseEmployeeService.takeEmployeeByCode(parentemployeecode);
			//根据站点对应的注册参数设置用户信息
			__agentObject.setBrandcode(employee.getBrandcode());
			__agentObject.setParentemployeecode(employee.getEmployeecode());
			__agentObject.setEmployeetypecode(EnterpriseEmployeeType.Type.代理.value);
			__agentObject.setLoginpassword2(APIServiceUtil.encrypt(__agentObject.getLoginpassword(), __agentObject));//对原始密码进行加密
			__agentObject.setLoginpassword(Encrypt.MD5(__agentObject.getLoginpassword()));
			__agentObject.setFundpassword(Encrypt.MD5(__agentObject.getFundpassword()));
			__agentObject.setDividend(new BigDecimal("0"));
			__agentObject.setShare(new BigDecimal("0"));
			__agentObject.setRegistercode(null);
			__agentObject.setOnlinestatus(Byte.valueOf(Enum_onlinestatus.离线.value));
			__agentObject.setEmployeestatus(Enum_employeestatus.启用.value.byteValue());
			__agentObject.setParentemployeeaccount(employee.getLoginaccount());
			//注册账号
			boolean isDirectly = employee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.企业号.value);
			enterpriseEmployeeService.tc_saveUser(__agentObject,null,isDirectly);
			
			Map<String,Object> __returnAgument = new HashMap<String, Object>();
			/******生成代理站点域名******/
			BrandDomain __defualt = brandDomainService.takeEDefualtASiteDomain(__agentObject.getEnterprisecode());
			if(__defualt!=null){
				System.err.println("代理注册调试域名1："+__defualt.getDomainlink());
				String __priex = brandDomainService.takeDefualtDomainPrefix();
				String domainlink = __priex+"."+__defualt.getDomainlink();
				System.err.println("代理注册调试域名2："+__priex);
				__returnAgument.put("defualtdomain_agent", domainlink);
				__returnAgument.put("cnamedomain_agent", __defualt.getDomainlink());
				
				
				//填充代理站点参数
				BrandDomain __registerSite  = new BrandDomain();
				__registerSite.setLinkdomain(__defualt.getDomaincode());
				__registerSite.setDomainlink(domainlink);
				__registerSite.setEnterprisecode(__agentObject.getEnterprisecode());
				__registerSite.setBrandcode(__agentObject.getBrandcode());
				__registerSite.setEmployeecode(__agentObject.getEmployeecode());
				__registerSite.setParentemployeecode(__agentObject.getParentemployeecode());
				__registerSite.setEmployeetype(__agentObject.getEmployeetypecode());
				__registerSite.setDividend(new BigDecimal("0"));
				__registerSite.setShare(new BigDecimal("0"));
				__registerSite.setBonus(bonusVerify(__agentObject, __object));
				__registerSite.setDomaintype(BrandDomain.Enum_domaintype.代理站点.value.byteValue());
				__registerSite.setCopyright(Byte.valueOf(Enum_copyright.私有.value));
				__registerSite.setIsdefualt(Constant.BooleanByte.NO.byteValue());
				__registerSite.setLinkstatus(String.valueOf(BrandDomain.Enum_linkstatus.启用.value));
				__registerSite.setDatastatus(String.valueOf(Constant.Enum_DataStatus.正常.value));
				__registerSite.setWebtemplatecode(__defualt.getWebtemplatecode());
				
				AgentSiteContact __registerSiteContact  = new AgentSiteContact();
				__registerSiteContact.setEmail(__agentObject.getEmail());
				__registerSiteContact.setPhone(__agentObject.getPhoneno());
				__registerSiteContact.setQq(__agentObject.getQq());
				__registerSiteContact.setSkype(__agentObject.getWechat());
				__registerSiteContact.setVchat(__agentObject.getWechat());
				// 调用保存方法
				brandDomainService.tc_saveAgentDomainAndContact(__registerSite,__registerSiteContact);
			}
			
			/******生成会员站点域名******/
			__defualt = brandDomainService.takeEDefualtUSiteDomain(employee.getEnterprisecode());
			if(__defualt!=null){
				String __priex = brandDomainService.takeDefualtDomainPrefix();
				String domainlink = __priex+"."+__defualt.getDomainlink();
				__returnAgument.put("defualtdomain_member", domainlink);
				__returnAgument.put("cnamedomain_member", __defualt.getDomainlink());
				
				BrandDomain __registerSite  = new BrandDomain();
				//填充会员站点参数
				__registerSite.setDomainlink(domainlink);
				__registerSite.setLinkdomain(__defualt.getDomaincode());
				__registerSite.setEnterprisecode(__agentObject.getEnterprisecode());
				__registerSite.setEmployeecode(__agentObject.getEmployeecode());
				__registerSite.setBrandcode(__agentObject.getBrandcode());
				__registerSite.setParentemployeecode(__agentObject.getParentemployeecode());
				__registerSite.setEmployeetype(EnterpriseEmployeeType.Type.会员.value);
				__registerSite.setDividend(new BigDecimal("0"));
				__registerSite.setShare(new BigDecimal("0"));
				__registerSite.setBonus(bonusVerify(__agentObject, __object));
				__registerSite.setDomaintype(BrandDomain.Enum_domaintype.会员站点.value.byteValue());
				__registerSite.setCopyright(Byte.valueOf(Enum_copyright.私有.value));
				__registerSite.setIsdefualt(Constant.BooleanByte.NO.byteValue());
				__registerSite.setLinkstatus(String.valueOf(BrandDomain.Enum_linkstatus.启用.value));
				__registerSite.setDatastatus(String.valueOf(Constant.Enum_DataStatus.正常.value));
				// 调用保存方法
				brandDomainService.tc_save(__registerSite);
			}
			
			userLogsService.addActivityBetRecord(new UserLogs(__agentObject.getEnterprisecode(), __agentObject.getEmployeecode(), __agentObject.getLoginaccount(), 
					UserLogs.Enum_operatype.用户资料业务, "代理注册用户", null, null));
			
			
			return Enum_MSG.成功.message(__returnAgument);
		}catch(ArgumentValidationException __ex){
			log.Error(__ex.getMessage(), __ex);
			return Enum_MSG.参数错误.message(__ex.getMessage());
		}catch(LogicTransactionRollBackException __ex){
			return Enum_MSG.逻辑事物异常.message(__ex.getMessage());
		}catch (Exception __ex) {
			log.Error(__ex.getMessage(), __ex);
			return Enum_MSG.系统错误.message(null);
		}
	}
	/**
	 * 洗码设置审核
	 * @param ee 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private String bonusVerify(EnterpriseEmployee ee,Map<String,Object> object) throws Exception{
		Map<String,BigDecimal> __supBonus =  employeeGamecataloyBonusService.takeEmployeeGameCategoryBonusMap(ee.getParentemployeecode());
		StringBuffer __agentSiteBonus = new StringBuffer();
		for (String key : __supBonus.keySet()) {
				BigDecimal __userBonus = new BigDecimal(String.valueOf(object.get(key)==null?"0":object.get(key))).divide(new BigDecimal(100));
				BigDecimal __parentBonus = __supBonus.get(key);
				if(__userBonus.compareTo(new BigDecimal("0"))==-1){
					throw new LogicTransactionRollBackException(key.replace("Game","")+"洗码不能小于0%");
				}
				if(__userBonus.compareTo(new BigDecimal("100"))==1){
					throw new LogicTransactionRollBackException(key.replace("Game","")+"洗码不能超过100%");
				}
				if(__parentBonus==null||__userBonus.compareTo(__parentBonus)==1){
					throw new LogicTransactionRollBackException(key.replace("Game","")+"洗码不能超过上级代理");
				}
				if (__agentSiteBonus.length() == 0) {
					__agentSiteBonus.append(key).append(":").append(__userBonus);
				} else {
					__agentSiteBonus.append("|").append(key).append(":").append(__userBonus);
				}
		}
		return __agentSiteBonus.toString();
	}
	
	/**
	 * 激活/冻结代理用户或玩家用户
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/dofreeze", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String dofreeze(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[] { "employeecode", "loginaccount", "biztype"});
			
			String employeecode = object.get("employeecode").toString();
			String loginaccount = object.get("loginaccount").toString();
			String biztype = object.get("biztype").toString();//冻结/激活
			
			EnterpriseEmployee formEmployee = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
			
			EnterpriseEmployee acceptEmployee = new EnterpriseEmployee();
			acceptEmployee.setLoginaccount(loginaccount);
			acceptEmployee.setEnterprisecode(formEmployee.getEnterprisecode());
			acceptEmployee = enterpriseEmployeeService.getGGPokerLogin(acceptEmployee);
			if(acceptEmployee == null) {
				return Enum_MSG.用户身份异常.message("未能查询到该会员，请确认账号是否完整");
			}
			if( !acceptEmployee.getParentemployeecode().equals(formEmployee.getEmployeecode())) {
				return Enum_MSG.用户身份异常.message(loginaccount+"不是您的直线会员，无权操作！");
			}
			
			if(acceptEmployee.getEmployeestatus().toString().equals(Enum_employeestatus.禁用.value.toString()) && biztype.equals("冻结")) {
				return Enum_MSG.逻辑事物异常.message("该用户当前是冻结状态！");
			}
			if(acceptEmployee.getEmployeestatus().toString().equals(Enum_employeestatus.启用.value.toString()) && biztype.equals("激活")) {
				return Enum_MSG.逻辑事物异常.message("该用户当前是激活状态！");
			}
			
			String[] array = new String[1];
			array[0] = acceptEmployee.getEmployeecode();
			if(biztype.equals("冻结")) {
				enterpriseEmployeeService.tc_disableSelectEmployee(array);//禁用
			} else if(biztype.equals("激活")) {
				enterpriseEmployeeService.tc_activateSelectEmployee(array);//启用
			}
			
			return Enum_MSG.成功.message("操作成功");
		}catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	/**
	 * 重置登录密码
	 * @return
	 */
	@RequestMapping(value="/resetLoginPassword",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String resetLoginPassword(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object,false, new String[]{"loginaccount","newloginpassword","employeecode"});
			String employeecode = object.get("employeecode").toString();		
			
//			EnterpriseEmployee ee = (enterpriseEmployeeService.takeEnterpriseEmployee(object));
			EnterpriseEmployee ee = new EnterpriseEmployee();
			ee.setLoginaccount(object.get("loginaccount").toString());
			ee = enterpriseEmployeeService.getGGPokerLogin(ee);
			if(ee == null) {
				return Enum_MSG.用户身份异常.message("未能查询到该会员，请确认账号是否完整");
			}
			if( !ee.getParentemployeecode().equals(employeecode)) {
				return Enum_MSG.用户身份异常.message(ee.getLoginaccount()+"不是您的直线会员，无权操作！");
			}
			
			//密码不得包含中文汉字或中文符号，也就是不得出现全角字符
			if(com.maven.util.StringUtils.isChinese(String.valueOf(object.get("newloginpassword")))) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.不得出现中文汉字或中文标点符号的全角字符.desc);
			}
			if(String.valueOf(object.get("newloginpassword")).length() > 12) {
				return Enum_MSG.逻辑事物异常.message("密码长度不能大于12位");
			}
			if(com.maven.util.StringUtils.stringFilterCheck(String.valueOf(object.get("newloginpassword")))) {
				return Enum_MSG.逻辑事物异常.message("不能包含特殊字符，允许输入字母与数字的组合");
			}
			
			if(String.valueOf(object.get("newloginpassword")).length() > 12) {
				return Enum_MSG.逻辑事物异常.message("密码长度不能大于12位");
			}
			if(com.maven.util.StringUtils.isUpperOrUpper(String.valueOf(object.get("newloginpassword")))) {
				return Enum_MSG.逻辑事物异常.message("密码不能含有空格或大写字符");
			}
			if(com.maven.util.StringUtils.isCharAllOrNumberAll(String.valueOf(object.get("newloginpassword")))) {
				return Enum_MSG.逻辑事物异常.message("密码应为数字+字符");
			}
			
			object.put("loginpassword2", APIServiceUtil.encrypt(String.valueOf(object.get("newloginpassword")), ee));//对原始密码进行加密
			object.put("loginpassword", Encrypt.MD5(String.valueOf(object.get("newloginpassword"))));
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), ee.getLoginaccount(), 
					UserLogs.Enum_operatype.用户资料业务, "重置了登录密码", null, null));
			
			int status = enterpriseEmployeeService.tc_restPassword(AttrCheckout.checkout(object,true,new String[]{"loginpassword","employeecode","loginpassword2"}));
			if(status==1) {
				
				/************************修改密码时调用接口同步更新各游戏平台的密码**************************/
				try {
					//加try块是为了避免因为更新密码时出错导致整个密码修改失败
					ee = enterpriseEmployeeService.takeEmployeeByCode(ee.getEmployeecode());
					new APIServiceNew(ee.getEnterprisecode()).updatePassword(ee);
				} catch (Exception e) {
					e.printStackTrace();
				}
				/************************修改密码时调用接口同步更新各游戏平台的密码**************************/
				
				
				return Enum_MSG.成功.message(null);
			}
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch(LogicTransactionRollBackException e){
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
		return Enum_MSG.参数错误.message(Enum_MSG.修改失败.desc);
	}
	/**
	 * 修改资金密码
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updatefpwd",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updateFunpassword(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object,false, new String[]{"employeecode","newfundpassword"});
			EnterpriseEmployee ee = enterpriseEmployeeService.takeEnterpriseEmployee(object);
			if(StringUtils.isNotBlank(ee.getFundpassword())){
				if(!ee.getFundpassword().equals(Encrypt.MD5(""+String.valueOf(object.get("oldfundpassword"))))) 
					return Enum_MSG.逻辑事物异常.message(Enum_MSG.资金密码错误.desc);
			}
			
			//密码不得包含中文汉字或中文符号，也就是不得出现全角字符
			if(com.maven.util.StringUtils.isChinese(String.valueOf(object.get("newfundpassword")))) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.不得出现中文汉字或中文标点符号的全角字符.desc);
			}
			if(com.maven.util.StringUtils.stringFilterCheck(String.valueOf(object.get("newfundpassword")))) {
				return Enum_MSG.逻辑事物异常.message("不能包含特殊字符，允许输入字母与数字的组合");
			}
			if(com.maven.util.StringUtils.isCharAllOrNumberAll(String.valueOf(object.get("newfundpassword")))) {
				return Enum_MSG.逻辑事物异常.message("密码应为数字和字符的组合");
			}
					
			object.put("fundpassword", Encrypt.MD5(String.valueOf(object.get("newfundpassword"))));
			int status = enterpriseEmployeeService.tc_restPassword(AttrCheckout.checkout(object,true,new String[]{"fundpassword"},new String[]{"employeecode"}));
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), ee.getLoginaccount(), 
					UserLogs.Enum_operatype.用户资料业务, "修改了资金密码", null, null));
			
			
			if(status==1) {
				return Enum_MSG.成功.message(null);
			}
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch(LogicTransactionRollBackException e){
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
		return Enum_MSG.参数错误.message(Enum_MSG.修改失败.desc);
	}
	
	/**
	 * 修改登录密码
	 * @return
	 */
	@RequestMapping(value="/updatepwd",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updateLoginPassword(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object,false, new String[]{"employeecode","oldloginpassword","newloginpassword"});
			EnterpriseEmployee ee = (enterpriseEmployeeService.takeEnterpriseEmployee(object));
			if(ee.getLoginpassword()!=null && !ee.getLoginpassword().equals(Encrypt.MD5(String.valueOf(object.get("oldloginpassword"))))) { 
				return Enum_MSG.参数错误.message(Enum_MSG.登录密码错误.desc);
			}
			
			//密码不得包含中文汉字或中文符号，也就是不得出现全角字符
			if(com.maven.util.StringUtils.isChinese(String.valueOf(object.get("newloginpassword")))) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.不得出现中文汉字或中文标点符号的全角字符.desc);
			}
			if(String.valueOf(object.get("newloginpassword")).length() > 12) {
				return Enum_MSG.逻辑事物异常.message("密码长度不能大于12位");
			}
			if(com.maven.util.StringUtils.stringFilterCheck(String.valueOf(object.get("newloginpassword")))) {
				return Enum_MSG.逻辑事物异常.message("不能包含特殊字符，允许输入字母与数字的组合");
			}
			
			if(String.valueOf(object.get("newloginpassword")).length() > 12) {
				return Enum_MSG.逻辑事物异常.message("密码长度不能大于12位");
			}
			if(com.maven.util.StringUtils.isUpperOrUpper(String.valueOf(object.get("newloginpassword")))) {
				return Enum_MSG.逻辑事物异常.message("密码不能含有空格或大写字符");
			}
			if(com.maven.util.StringUtils.isCharAllOrNumberAll(String.valueOf(object.get("newloginpassword")))) {
				return Enum_MSG.逻辑事物异常.message("密码应为数字+字符");
			}
			
			object.put("loginpassword2", APIServiceUtil.encrypt(String.valueOf(object.get("newloginpassword")), ee));//对原始密码进行加密
			object.put("loginpassword", Encrypt.MD5(String.valueOf(object.get("newloginpassword"))));
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), ee.getLoginaccount(), 
					UserLogs.Enum_operatype.用户资料业务, "修改了登录密码", null, null));
			
			int status = enterpriseEmployeeService.tc_restPassword(AttrCheckout.checkout(object,true,new String[]{"loginpassword","employeecode","loginpassword2"}));
			if(status==1) {
				
				/************************修改密码时调用接口同步更新各游戏平台的密码**************************/
				try {
					//加try块是为了避免因为更新密码时出错导致整个密码修改失败
					ee = enterpriseEmployeeService.takeEmployeeByCode(ee.getEmployeecode());
					new APIServiceNew(ee.getEnterprisecode()).updatePassword(ee);
				} catch (Exception e) {
					e.printStackTrace();
				}
				/************************修改密码时调用接口同步更新各游戏平台的密码**************************/
				
				
				return Enum_MSG.成功.message(null);
			}
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch(LogicTransactionRollBackException e){
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
		return Enum_MSG.参数错误.message(Enum_MSG.修改失败.desc);
	}
	private static List<String> errorphone = new ArrayList<String>(){{
//		1700,1701,1702,是电信
//		1703,1705,1706,是移动
//		1704,1707,1708,1709,171全段，是联通
		this.add("1700");
		this.add("1701");
		this.add("1702");
		this.add("1703");
		this.add("1705");
		this.add("1706");
		this.add("1704");
		this.add("1707");
		this.add("1708");
		this.add("1709");
		this.add("171");
		this.add("173");
	}};
	private static Map<String, String> mapVerifycode = new HashMap<String, String>();
	private static ExpiryMap<String, String> mapip = new ExpiryMap<String, String>();
	/**
	 * 修改用户资料
	 * 
	 * qq=qq号码
	 * email=邮箱号码
	 * phoneno=手机号码
	 * 
	 * @return
	 */
	@RequestMapping(value="/updateInfo",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updateInfo(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object,false, new String[]{"employeecode"});
			
			EnterpriseEmployee ee = (enterpriseEmployeeService.takeEnterpriseEmployee(object));
			Map<String, Object> prams = new HashMap<String, Object>();
			prams.put("employeecode", ee.getEmployeecode());
			
			if(object.containsKey("phoneno")) {
				String phoneno = String.valueOf(object.get("phoneno"));
				if(phoneno.length() > 0 && phoneno.length() != 11) {
					return Enum_MSG.逻辑事物异常.message("手机号码格式不正确");
				}
				for (String string : errorphone) {
					if(phoneno.startsWith(string)) {
						return Enum_MSG.逻辑事物异常.message("输入的号码段为无效虚拟手机号码，请使用实名制手机号码");
					}
				}
				//验证码
				if(object.containsKey("verifycode")) {
					if(String.valueOf(object.get("verifycode")).length() > 0 && 
							com.maven.util.StringUtils.isNumber(String.valueOf(object.get("verifycode"))) &&
							String.valueOf(object.get("verifycode")).length() != 4
							) {
						return Enum_MSG.逻辑事物异常.message("验证码格式不正确");
					}
					String checkno = mapVerifycode.get(phoneno);
					if( !checkno.equals(String.valueOf(object.get("verifycode")))) {
						return Enum_MSG.逻辑事物异常.message(Enum_MSG.短信验证码错误.desc);
					}
				}
				
				//验证完毕时
				prams.put("phoneno", phoneno);
			}
			
			if(object.containsKey("email")) {
				if(String.valueOf(object.get("email")).length() > 0) {
					return Enum_MSG.逻辑事物异常.message("邮箱格式不正确");
				}
				//验证完毕时
				prams.put("email", String.valueOf(object.get("email")));
			}
			
			if(object.containsKey("qq")) {
				if(String.valueOf(object.get("qq")).length() > 0 && !com.maven.util.StringUtils.isNumber(String.valueOf(object.get("qq")))) {
					return Enum_MSG.逻辑事物异常.message("QQ号码格式不正确");
				}
				//验证完毕时
				prams.put("qq", String.valueOf(object.get("qq")));
			}
			if(object.containsKey("displayalias")) {
				if(String.valueOf(object.get("displayalias")).length() < 1 ) {
					return Enum_MSG.逻辑事物异常.message("姓名格式不正确");
				}
				//验证完毕时
				prams.put("displayalias", String.valueOf(object.get("displayalias")));
			}
			if(object.containsKey("wechat")) {
				if(String.valueOf(object.get("wechat")).length() < 1 ) {
					return Enum_MSG.逻辑事物异常.message("微信号格式不正确");
				}
				//验证完毕时
				prams.put("wechat", String.valueOf(object.get("wechat")));
			}
			if(object.containsKey("phonestatus")) {
				if( !String.valueOf(object.get("phonestatus")).equals("1") ) {
					return Enum_MSG.逻辑事物异常.message("手机号码验证状态格式填写错误");
				}
				//验证完毕时
				prams.put("phonestatus", "1");
			}
			if(prams.size() > 1) {
				int status = enterpriseEmployeeService.updateInfo(prams);
				if(status==1) {
					
					userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), ee.getLoginaccount(), 
							UserLogs.Enum_operatype.用户资料业务, "修改了邮箱、qq、手机号码等", null, null));
					
					return Enum_MSG.成功.message(null);
				}
			} else {
				return Enum_MSG.失败.message("没有任何数据更新");
			}
			
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch(LogicTransactionRollBackException e){
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
		return Enum_MSG.参数错误.message(Enum_MSG.修改失败.desc);
	}
	
	/**
	 * 用户账变记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/findAccountChange",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String findAccountChange(HttpServletRequest request){
		try {
			//employeecode
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			
			if(object.get("startDate") != null && object.get("startDate").toString().length() == 10) {
				object.put("startdate", object.get("startDate").toString().replaceAll("-", ""));
				object.remove("startDate");
			}
			if(object.get("endDate") != null && object.get("endDate").toString().length() == 10) {
				object.put("enddate", object.get("endDate").toString().replaceAll("-", ""));
				object.remove("endDate");
			}
			
			EnterpriseEmployee ee = (enterpriseEmployeeService.takeEnterpriseEmployee(object));
			
			
			List<EmployeeMoneyChange> list = employeeMoneyChangeService.findAccountChange(object);
			Map<String, Object> result = employeeMoneyChangeService.findAccountChangeCount(object);
			int count = StringUtil.getInt(result.get("count"));
			for (EmployeeMoneyChange emc : list) {
				emc.setAfteramount(emc.getMoneychangeamount().add(emc.getSettlementamount()));
			}
			Map<String,Object> returnObject = new HashMap<String,Object>();
			returnObject.put("record", list);
			returnObject.put("count", count);
			returnObject.put("sumamount", result.get("moneychangeamount"));
			return Enum_MSG.成功.message(returnObject);
			
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch(LogicTransactionRollBackException e){
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	/**
	 * 平台返现记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/findFanList",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String findFanList(HttpServletRequest request){
		try {
			//employeecode
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			
			if(object.get("startDate") != null && object.get("startDate").toString().length() == 10) {
				object.put("startdate", object.get("startDate").toString().replaceAll("-", ""));
				object.remove("startDate");
			}
			if(object.get("endDate") != null && object.get("endDate").toString().length() == 10) {
				object.put("enddate", object.get("endDate").toString().replaceAll("-", ""));
				object.remove("endDate");
			}
			object.put("field", "orderdate");
			object.put("direction", "desc");
			
			EnterpriseEmployee ee = enterpriseEmployeeService.takeEnterpriseEmployee(object);
			
		  	
			List<Baccarath5Rebate> list = baccarath5RebateService.selectForPage(object);
			Map<String, Object> result = baccarath5RebateService.takeRecordCountMoney(object);
			int count = StringUtil.getInt(result.get("count"));
			Map<String,Object> returnObject = new HashMap<String,Object>();
			returnObject.put("record", list);
			returnObject.put("count", count);
			returnObject.put("sumamount", result.get("money"));//元宝总额
			returnObject.put("orderamount", result.get("orderamount"));//交易总额
			returnObject.put("rebatemoney", result.get("rebatemoney"));//返现总额
			return Enum_MSG.成功.message(returnObject);
			
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch(LogicTransactionRollBackException e){
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	
	/**
	 * 平台下分的指定目标，需要传递分数进来
	 * 
	 * 1、只下分。
	 * 2、只下指定平台的分到中心钱包
	 * 
	 * 
	 * @param gameType
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/downIntegralGame", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String downIntegralGame(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[] { "parentemployeecode", "loginaccount", "gametype" , "brandcode" , "money"});
			String brandcode = String.valueOf(object.get("brandcode"));
			String gametype = String.valueOf(object.get("gametype"));
			BigDecimal accountfund = new BigDecimal(object.get("money").toString());
			String enterprisecode = object.get("enterprisecode").toString();
			String loginaccount = object.get("loginaccount").toString();
			String parentemployeecode = object.get("parentemployeecode").toString();
			
			EnterpriseEmployee ee = new EnterpriseEmployee();
			ee.setLoginaccount(loginaccount);
			ee.setEnterprisecode(enterprisecode);
			ee.setParentemployeecode(parentemployeecode);
			ee = enterpriseEmployeeService.getGGPokerLogin(ee);
			if(ee == null) {
				return Enum_MSG.用户身份异常.message("未能查询到该会员，请确认账号是否完整");
			}
			
			String employeecode = ee.getEmployeecode();
			
			if(accountfund.intValue() <= 0) {
				return Enum_MSG.参数错误.message("转账金额不能少于1元");
			}
			if (!Enum_GameType.validate(gametype)) {
				return Enum_MSG.参数错误.message(Enum_MSG.游戏类型错误.desc);
			}
			log.Debug("游戏类型验证通过....");
					
			//三秒限时
			RepeatRequestIntercept.isIntercept(ee.getEmployeecode(), request.getRequestURI().replaceAll("/+", "/"), 3000);
			
			
			List<Game> games = gameService.takeBrandGames(brandcode,EnterpriseOperatingBrandGame.Enum_flag.正常.value);
			if (isOpenGame(brandcode, gametype,games)==null){
				
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.游戏维护中.desc);
				
			} else {
				
				APIServiceNew api = new APIServiceNew(ee.getEnterprisecode());
				EmployeeApiAccout eea = SystemCache.getInstance().getEmployeeGameAccount(ee.getEmployeecode(), gametype);
				
				if(eea == null) {
					return Enum_MSG.逻辑事物异常.message("该平台不存在玩家账号");
				}
				
				if(eea.getStatus().equals(EmployeeApiAccout.Enum_status.禁用.value)) {
					return Enum_MSG.用户未启用该游戏.message(Enum_MSG.游戏维护中.desc);
				}
				
				//先查询当前的可用余额
				EnterpriseEmployeeCapitalAccount ec = capitalAccountService.takeCurrencyAccount(employeecode);
				if(ec == null){
					return Enum_MSG.逻辑事物异常.message(Enum_MSG.资金账户不存在.desc);
				} 
				
				//下分前先查询余额
				JSONObject jsonObject = JSONObject.fromObject( api.balance(gametype, employeecode) );
				if(jsonObject.getString("code").equals("0")) {
					
					String patchno = OrderNewUtil.getPatchno();
					BigDecimal balance = new BigDecimal(jsonObject.getString("info"));
					
					if(accountfund.intValue() > balance.intValue()) {
						return Enum_MSG.逻辑事物异常.message("操作失败。转出金额"+accountfund.doubleValue()+" 小于余额："+balance.doubleValue());
					}
					
					//调用接口下分
					jsonObject = JSONObject.fromObject( api.downIntegralGame(balance, accountfund.intValue(), gametype, eea, patchno) );
					
					if(jsonObject.getString("code").equals("0")) {
						
						log.Error(ee.getEmployeecode() + " 用户手动下分已完毕。"+gametype);
						
						userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), ee.getLoginaccount(), 
								UserLogs.Enum_operatype.游戏信息业务, "手动下分"+gametype+"到中心钱包", null, null));
					} else {
						
						log.Error(ee.getEmployeecode() + "手动下分失败，原因："+jsonObject.getString("info"));
						return Enum_MSG.失败.message("手动下分失败，原因："+jsonObject.getString("info"));
					}
					
				} else {
					
					log.Error(ee.getEmployeecode() +"请稍后再试。手动下分前查询余额失败，原因："+jsonObject.getString("info"));
					return Enum_MSG.失败.message("请稍后再试。手动下分前查询余额失败，原因："+jsonObject.getString("info"));
				}
				
				return Enum_MSG.成功.message("手动下分已完毕");
			}
		}catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	/**
	 * 平台上分的指定目标，需要传递分数进来
	 * 
	 * 1、只上分。
	 * 2、只上当前可用余额的分，其他平台不自动下分
	 * 
	 * 
	 * @param gameType
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/upIntegralGame", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String upIntegralGame(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));

			AttrCheckout.checkout(object, false, new String[] { "parentemployeecode", "loginaccount", "gametype" , "brandcode" , "money"});
			String brandcode = String.valueOf(object.get("brandcode"));
			String gametype = String.valueOf(object.get("gametype"));
			BigDecimal accountfund = new BigDecimal(object.get("money").toString());
			String enterprisecode = object.get("enterprisecode").toString();
			String loginaccount = object.get("loginaccount").toString();
			String parentemployeecode = object.get("parentemployeecode").toString();
			
			EnterpriseEmployee ee = new EnterpriseEmployee();
			ee.setLoginaccount(loginaccount);
			ee.setEnterprisecode(enterprisecode);
			ee.setParentemployeecode(parentemployeecode);
			ee = enterpriseEmployeeService.getGGPokerLogin(ee);
			if(ee == null) {
				return Enum_MSG.用户身份异常.message("未能查询到该会员，请确认账号是否完整");
			}
			
			String employeecode = ee.getEmployeecode();
			
			if(accountfund.intValue() <= 0) {
				return Enum_MSG.参数错误.message("转账金额不能少于1元");
			}
			if (!Enum_GameType.validate(gametype)) {
				return Enum_MSG.参数错误.message(Enum_MSG.游戏类型错误.desc);
			}
			log.Debug("游戏类型验证通过....");
					
			//三秒限时
			RepeatRequestIntercept.isIntercept(ee.getEmployeecode(), request.getRequestURI().replaceAll("/+", "/"), 3000);
			
			
			List<Game> games = gameService.takeBrandGames(brandcode,EnterpriseOperatingBrandGame.Enum_flag.正常.value);
			if (isOpenGame(brandcode, gametype,games)==null){
				
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.游戏维护中.desc);
				
			} else {
				
				//	游戏密码是否已同步完毕（2分钟同步一次必要的密码）
				if( isPasswordCompelted(ee , gametype) ) {
					return Enum_MSG.逻辑事物异常.message(Enum_MSG.游戏密码同步中.desc);
				}
				
				//业务逻辑：检查是否已存在账号，如果不存在则需要先创建。然后调用转分接口进行转分。
				APIServiceNew api = new APIServiceNew(ee.getEnterprisecode());
				EmployeeApiAccout eea = SystemCache.getInstance().getEmployeeGameAccount(ee.getEmployeecode(), gametype);
				
				if(eea == null) {
					JSONObject jsonObject = JSONObject.fromObject( api.create(gametype, ee) );
					log.Error("创建游戏账号结果："+jsonObject);
				}
				eea = SystemCache.getInstance().getEmployeeGameAccount(ee.getEmployeecode(), gametype);
				
				if(eea.getStatus().equals(EmployeeApiAccout.Enum_status.禁用.value)) {
					return Enum_MSG.用户未启用该游戏.message(Enum_MSG.游戏维护中.desc);
				}
				
				//先查询当前的可用余额
				EnterpriseEmployeeCapitalAccount ec = capitalAccountService.takeCurrencyAccount(employeecode);
				if(ec == null){
					return Enum_MSG.逻辑事物异常.message(Enum_MSG.资金账户不存在.desc);
				} 
				//进行上分
				if(ec.getBalance().intValue() < accountfund.intValue() ){
					return Enum_MSG.逻辑事物异常.message("您当前的余额不足，请查看资金是否有遗留，或请充值。当前余额："+ec.getBalance());
				} 
				
				//上分前先查询余额
				JSONObject jsonObject = JSONObject.fromObject( api.balance(gametype, employeecode) );
				if(jsonObject.getString("code").equals("0")) {
					
					String patchno = OrderNewUtil.getPatchno();
					BigDecimal beforeAmount = new BigDecimal(jsonObject.getString("info"));
					
					//调用接口上分
					jsonObject = JSONObject.fromObject( api.upIntegralGame(beforeAmount, accountfund.intValue(), gametype, eea, patchno) );
					
					if(jsonObject.getString("code").equals("0")) {
						
						log.Error(ee.getEmployeecode() + " 用户手动上分已完毕。"+gametype);
						
						userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), ee.getLoginaccount(), 
								UserLogs.Enum_operatype.游戏信息业务, "手动上分到游戏平台"+gametype+"", null, null));
					} else {
						
						log.Error(ee.getEmployeecode() + "手动上分失败，原因："+jsonObject.getString("info"));
						return Enum_MSG.失败.message("手动上分失败，原因："+jsonObject.getString("info"));
					}
					
				} else {
					
					log.Error(ee.getEmployeecode() +"请稍后再试。手动上分前查询余额失败，原因："+jsonObject.getString("info"));
					return Enum_MSG.失败.message("请稍后再试。手动上分前查询余额失败，原因："+jsonObject.getString("info"));
				}
				
				return Enum_MSG.成功.message("手动上分已完毕");
			}
		}catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	
	
	/**
	 * 查询单个账户余额
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/balance", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String balance(HttpServletRequest request) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			String gameType = String.valueOf(object.get("gameType"));
			if (!Enum_GameType.validate(gameType)) {
				return Enum_MSG.参数错误.message(Enum_MSG.游戏类型错误.desc);
			}
			EnterpriseEmployee ee = (enterpriseEmployeeService.takeEnterpriseEmployee(object));
			
			return new APIServiceNew(ee.getEnterprisecode()).balance(gameType, ee.getEmployeecode());
			

		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		} catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}

	/**
	 * 获取用户所有余额
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/balances", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String balances(HttpServletRequest request) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			EnterpriseEmployee ee = (enterpriseEmployeeService.takeEnterpriseEmployee(object));
			
			return new APIServiceNew(ee.getEnterprisecode()).balances(ee.getEmployeecode(), ee.getBrandcode());
			
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		} catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	/**
	 * 检查并返回用户信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkEmployee", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String checkEmployee(HttpServletRequest request) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[] { "parentemployeecode", "loginaccount"});
			
			String enterprisecode = object.get("enterprisecode").toString();
			String loginaccount = object.get("loginaccount").toString();
			String parentemployeecode = object.get("parentemployeecode").toString();
			
			EnterpriseEmployee acceptEmployee = new EnterpriseEmployee();
			acceptEmployee.setLoginaccount(loginaccount);
			acceptEmployee.setEnterprisecode(enterprisecode);
			acceptEmployee.setParentemployeecode(parentemployeecode);
			acceptEmployee = enterpriseEmployeeService.getGGPokerLogin(acceptEmployee);
			if(acceptEmployee == null) {
				return Enum_MSG.用户身份异常.message("未能查询到该会员，请确认账号是否完整");
			}
			
			return Enum_MSG.成功.message(acceptEmployee); 
			
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		} catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	/**
	 * 获取用户所有余额，多列
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/balancesAll", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String balancesAll(HttpServletRequest request) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[] { "parentemployeecode", "loginaccount"});
			
			String enterprisecode = object.get("enterprisecode").toString();
			String loginaccount = object.get("loginaccount").toString();
			String parentemployeecode = object.get("parentemployeecode").toString();
			
			EnterpriseEmployee acceptEmployee = new EnterpriseEmployee();
			acceptEmployee.setLoginaccount(loginaccount);
			acceptEmployee.setEnterprisecode(enterprisecode);
			acceptEmployee.setParentemployeecode(parentemployeecode);
			acceptEmployee = enterpriseEmployeeService.getGGPokerLogin(acceptEmployee);
			if(acceptEmployee == null) {
				return Enum_MSG.用户身份异常.message("未能查询到该会员，请确认账号是否完整");
			}
			
			
			List<Game> BrandGameAll = gameService.takeBrandGames(acceptEmployee.getBrandcode(), EnterpriseOperatingBrandGame.Enum_flag.正常.value);
			
			List<Map<String, String>> returnObject = new ArrayList<Map<String,String>>();
			Map<String, String> BalanceAll = new APIServiceNew(acceptEmployee.getEnterprisecode()).balancesAll(acceptEmployee.getEmployeecode(), acceptEmployee.getBrandcode());
			
			//中心钱包永远在第一位
			String balance = BalanceAll.get("CENTER");
			Map<String, String> data = new HashMap<String, String>();
			data.put("gamecode", "00000");
			data.put("gametype", "CENTER");
			data.put("gamename", "中心钱包");
			data.put("gamebalance", balance == null ? "0.00" : balance);
			returnObject.add(data);
			
			for (Game game : BrandGameAll) {
				String balancex = BalanceAll.get(game.getGametype());
				Map<String, String> datax = new HashMap<String, String>();
				datax.put("gamecode", game.getGamecode());
				datax.put("gametype", game.getGametype());
				datax.put("gamename", game.getGamename());
				datax.put("gamebalance", balancex == null ? "0.00" : balancex);
				
				returnObject.add(datax);
			}
			
			return Enum_MSG.成功.message(returnObject); 
			
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		} catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	/**
	 * 获取用户指定时间段内的存取款统计等
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/allMoney", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String allMoney(HttpServletRequest request) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			EnterpriseEmployee ee = (enterpriseEmployeeService.takeEnterpriseEmployee(object));
			
			//获取存取款、领取优惠额
			object.put("start_time", null);
			object.put("end_time", null);
			Map<String, Object> object2 = takeDepositRecoredService.call_userAllMoney(object);
			
//			#{total_deposit_money,mode=OUT,jdbcType=DECIMAL},
//			#{total_take_money,mode=OUT,jdbcType=DECIMAL},
//			#{total_activity_money,mode=OUT,jdbcType=DECIMAL},
//			#{total_bet_money,mode=OUT,jdbcType=DECIMAL},
//			#{total_net_money,mode=OUT,jdbcType=DECIMAL},
//			#{total_stream_money,mode=OUT,jdbcType=DECIMAL}
			
			return Enum_MSG.成功.message(object2);
			
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		} catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	@RequestMapping(value = "/sendMessage", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String sendMessage(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[] { "employeecode", "loginaccount" , "content"});
			
			String employeecode = object.get("employeecode").toString();
			String loginaccount = object.get("loginaccount").toString();
			String content = object.get("content").toString();
			
			EnterpriseEmployee sendEmployee = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
			
			EnterpriseEmployee acceptEmployee = new EnterpriseEmployee();
			acceptEmployee.setLoginaccount(loginaccount);
			acceptEmployee.setEnterprisecode(sendEmployee.getEnterprisecode());
			acceptEmployee = enterpriseEmployeeService.getGGPokerLogin(acceptEmployee);
			if(acceptEmployee == null) {
				return Enum_MSG.用户身份异常.message("未能查询到该会员，请确认账号是否完整");
			}
			List<EmployeeMessage> messages = new ArrayList<EmployeeMessage>();
			
			EmployeeMessageText text = new EmployeeMessageText();
			text.setContent(content);
			text.setDatastatus(String.valueOf(Constant.Enum_DataStatus.正常.value));
			
			EmployeeMessage message = new EmployeeMessage();
			message.setEnterprisecode(sendEmployee.getEnterprisecode());
			message.setBrandcode(sendEmployee.getBrandcode());
			message.setSendemployeecode(sendEmployee.getEmployeecode());
			message.setSendemployeeaccount(sendEmployee.getLoginaccount());
			message.setAcceptemployeecode(acceptEmployee.getEmployeecode());//接受会员编号
			message.setAcceptaccount(acceptEmployee.getLoginaccount());//接受会员账号
			message.setMessagetype(String.valueOf(EmployeeMessage.Enum_messagetype.系统消息.value));
			message.setReadstatus(String.valueOf(EmployeeMessage.Enum_readstatus.未阅读.value));
			messages.add(message);
			
			if(messages.size() > 0) {
				employeeMessageService.tc_sendMessage(messages, text);
			}
			
			return Enum_MSG.成功.message("已发送成功");
			
		}catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	/**
	 * 我的代理团队层级数据分页
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/myAgentList", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String myAgentList(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> paramsObj = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(paramsObj, false, new String[] {"enterprisecode","employeecode","start","limit"});
			
			String employeecode = paramsObj.get("employeecode").toString();
			
			//获取登录用户的信息
			EnterpriseEmployee employee = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
			// 只有是非 企业号 或 员工号 才能查看企业下所有会员的信息
			if (!employee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.企业号.value) && 
					!employee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.员工.value) && 
					!employee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.会员.value)) {
//				paramsObj.put("parentemployeecode", employee.getEmployeecode());
			}
			paramsObj.put("employeetypecode", Type.代理.value);
			paramsObj.put("enterprisecode", employee.getEnterprisecode());
			paramsObj.put("field", "logindatetime");
			paramsObj.put("direction", "asc");
			paramsObj.remove("employeecode");
			
			super.dataLimits(employee, paramsObj, session);
			
			List<EnterpriseEmployee> list = enterpriseEmployeeService.findEmployee(paramsObj);
			for (EnterpriseEmployee __ee : list) {
//				__ee.setEmployeecode(super.encryptString(__ee.getEmployeecode(), session.getId()));
//				__ee.setLoginaccount(super.encryptString(__ee.getLoginaccount(), session.getId()));
				__ee.setFundpassword("");
				__ee.setLoginpassword("");
				// 非直接上级代理不能查看联系方式
				if( !__ee.getParentemployeecode().equals(employee.getEmployeecode())) {
					__ee.setPhoneno("***********");
					__ee.setQq("***********");
					__ee.setEmail("***********");
					__ee.setWechat("***********");
				}
			}
			//查询总记录条数
			int count = enterpriseEmployeeService.findEmployeeCount(paramsObj);
			
			Map<String,Object> returnObject = new HashMap<String,Object>();
			returnObject.put("record", list);
			returnObject.put("count", count);
			
			return Enum_MSG.成功.message(returnObject);
			
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		} catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	/**
	 * 我的会员信息层级数据分页
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/myMemberList", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String myMemberList(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> paramsObj = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(paramsObj, false, new String[] {"enterprisecode","employeecode","start","limit"});
			
			String employeecode = paramsObj.get("employeecode").toString();
			
			//获取登录用户的信息
			EnterpriseEmployee employee = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
			// 只有是非 企业号 或 员工号 才能查看企业下所有会员的信息
			if (!employee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.企业号.value) && 
					!employee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.员工.value) && 
					!employee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.会员.value)) {
//				paramsObj.put("parentemployeecode", employee.getEmployeecode());
			}
			paramsObj.put("employeetypecode", Type.会员.value);
			paramsObj.put("enterprisecode", employee.getEnterprisecode());
			paramsObj.put("field", "logindatetime");
			paramsObj.put("direction", "asc");
			paramsObj.remove("employeecode");
			
			super.dataLimits(employee, paramsObj, session);
			
			List<EnterpriseEmployee> list = enterpriseEmployeeService.findEmployee(paramsObj);
			for (EnterpriseEmployee __ee : list) {
//				__ee.setEmployeecode(super.encryptString(__ee.getEmployeecode(), session.getId()));
//				__ee.setLoginaccount(super.encryptString(__ee.getLoginaccount(), session.getId()));
				__ee.setFundpassword("");
				__ee.setLoginpassword("");
				// 非直接上级代理不能查看联系方式
				if( !__ee.getParentemployeecode().equals(employee.getEmployeecode())) {
					__ee.setPhoneno("***********");
					__ee.setQq("***********");
					__ee.setEmail("***********");
					__ee.setWechat("***********");
				}
			}
			//查询总记录条数
			int listTotal = enterpriseEmployeeService.findEmployeeCount(paramsObj);
			
			Map<String,Object> returnObject = new HashMap<String,Object>();
			returnObject.put("record", list);
			returnObject.put("count", listTotal);
			
			return Enum_MSG.成功.message(returnObject);
			
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		} catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	
	/**
	 * 查询会员的信息
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/takeEmployee", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String takeEmployee(HttpServletRequest request) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[] { "employeecode" });
			EnterpriseEmployee ee = enterpriseEmployeeService.takeEnterpriseEmployee(object);
			
			//先查询用户的积分
			EnterpriseEmployeeCapitalAccount ec = capitalAccountService.takeCurrencyAccount(ee.getEmployeecode());
			if(ec == null){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.资金账户不存在.desc);
			} 
			
			Map<String, Object> result = BeanToMapUtil.convertBean(ee, true);
			//返回真钱和元宝之间的兑换比例
			Enterprise enterprise = enterpriseService.selectByPrimaryKey(ee.getEnterprisecode());
			double rate = enterprise.getRate();//返回元宝兑换积分的比例
			double rate2 = enterprise.getRate2();//返回真钱和元宝之间的兑换比例
			result.put("yunbao2jifen_rate", rate);
			result.put("money2yuanbao_rate", rate2);
			result.put("money_jifen", ec.getBalance().doubleValue());//返回积分
			
			//查累计充值/取款信息
			/*#{last_deposit_time,mode=OUT,jdbcType=TIMESTAMP},
			#{last_deposit_money,mode=OUT,jdbcType=DECIMAL},
			#{last_deposit_ordernumber,mode=OUT,jdbcType=VARCHAR},
			#{first_deposit_time,mode=OUT,jdbcType=TIMESTAMP},
			#{first_deposit_money,mode=OUT,jdbcType=DECIMAL},
			#{first_deposit_ordernumber,mode=OUT,jdbcType=VARCHAR},
			#{all_deposit_money,mode=OUT,jdbcType=DECIMAL},
			#{all_deposit_count,mode=OUT,jdbcType=INTEGER},
			
			#{last_take_time,mode=OUT,jdbcType=TIMESTAMP},
			#{last_take_money,mode=OUT,jdbcType=DECIMAL},
			#{last_take_ordernumber,mode=OUT,jdbcType=VARCHAR},
			#{first_take_time,mode=OUT,jdbcType=TIMESTAMP},
			#{first_take_money,mode=OUT,jdbcType=DECIMAL},
			#{first_take_ordernumber,mode=OUT,jdbcType=VARCHAR},
			#{all_take_money,mode=OUT,jdbcType=DECIMAL},
			#{all_take_count,mode=OUT,jdbcType=INTEGER}*/
			object.clear();
			object.put("employeecode", ee.getEmployeecode());
			Map<String, Object> mapresult = takeDepositRecoredService.call_userAllInfoMoney(object);
			if(mapresult == null) {
				mapresult = new HashMap<String, Object>();
				mapresult.put("last_deposit_time", 0);
				mapresult.put("last_deposit_money", 0);
				mapresult.put("last_deposit_ordernumber", 0);
				mapresult.put("first_deposit_time", 0);
				mapresult.put("first_deposit_money", 0);
				mapresult.put("first_deposit_ordernumber", 0);
				mapresult.put("all_deposit_money", 0);
				mapresult.put("all_deposit_count", 0);
				mapresult.put("last_take_time", 0);
				mapresult.put("last_take_money", 0);
				mapresult.put("last_take_ordernumber", 0);
				mapresult.put("first_take_time", 0);
				mapresult.put("first_take_money", 0);
				mapresult.put("first_take_ordernumber", 0);
				mapresult.put("all_take_money", 0);
				mapresult.put("all_take_count", 0);
			}
			result.put("moneyAll", mapresult);
			
			//查累计投注情况
			Map<String, Object> map = bettingAllDayService.takeRecordCountMoney(object);
			result.put("validMoney", map.get("validMoney") == null ? "0" : map.get("validMoney"));
			result.put("netMoney", map.get("netMoney") == null ? "0" : map.get("netMoney"));
			result.put("betMoney", map.get("betMoney") == null ? "0" : map.get("betMoney"));
			
			//查累计积分转入额
			Map<String, Object> paramsObj = new HashMap<String, Object>();
			paramsObj.put("employeecode", ee.getEmployeecode());
			paramsObj.put("enterprisecode", ee.getEnterprisecode());
			paramsObj.put("moneychangetypecode", Enum_moneychangetype.转入金额.value);
			Map<String, Object> resultTransfer = employeeMoneyChangeService.findAccountChangeCount(paramsObj);
			if(resultTransfer != null) {
				result.put("transferamount", SCom.getDouble(resultTransfer.get("moneychangeamount")));
			} else {
				result.put("transferamount", "0.00");
			}
			
			return Enum_MSG.成功.message(result);
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		} catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	/**
	 * 查询代理的账户相关统计数据信息
	 * 
	 * （同时代理登录后调用的也是该接口）
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/takeUserInfo", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String takeUserInfo(HttpServletRequest request) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[] { "employeecode" });
			EnterpriseEmployee ee = enterpriseEmployeeService.takeEnterpriseEmployee(object);
			
			//先查询用户的积分
			EnterpriseEmployeeCapitalAccount ec = capitalAccountService.takeCurrencyAccount(ee.getEmployeecode());
			if(ec == null){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.资金账户不存在.desc);
			} 
			Baccarath5Balance balance = baccarath5BalanceService.selectByPrimaryKey(ee.getEmployeecode());
			if(balance == null) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.元宝账户不存在.desc);
			}
			
			
			Map<String, Object> result = BeanToMapUtil.convertBean(ee, true);
			//返回真钱和元宝之间的兑换比例
			Enterprise enterprise = enterpriseService.selectByPrimaryKey(ee.getEnterprisecode());
			double rate = enterprise.getRate();//返回元宝兑换积分的比例
			double rate2 = enterprise.getRate2();//返回真钱和元宝之间的兑换比例
			result.put("yunbao2jifen_rate", rate);
			result.put("money2yuanbao_rate", rate2);
			result.put("money_jifen", ec.getBalance().doubleValue());//返回积分
			result.put("money_yuanbao", balance.getBalance().doubleValue());//返回元宝
			
			//查询代理用户当前等级
			//查询代理用户个人信息
			
			//查询代理用户的会员和代理团队数据
			EnterpriseEmployee __temp = enterpriseEmployeeService.call_Count(ee.getEmployeecode());
			result.put("agentCount", __temp.getAgentCount());
			result.put("memberCount", __temp.getMemberCount());
			//直线会员和直线代理
			Map<String, Object> paramsObj = new HashMap<String, Object>();
			paramsObj.put("enterprisecode", ee.getEnterprisecode());
			paramsObj.put("parentemployeecode", ee.getEmployeecode());
			paramsObj.put("employeetypecode", EnterpriseEmployeeType.Type.代理.value);
			result.put("agentCount1", enterpriseEmployeeService.findEmployeeCount(paramsObj));
			paramsObj.put("employeetypecode", EnterpriseEmployeeType.Type.会员.value);
			result.put("memberCount1", enterpriseEmployeeService.findEmployeeCount(paramsObj));
			//查询直线会员和直线代理的积分和元宝余额
			__temp = enterpriseEmployeeService.call_Balances(ee.getEmployeecode());
			result.put("money_jifen1", __temp.getDividend().doubleValue());//返回积分
			result.put("money_yuanbao1", __temp.getShare().doubleValue());//返回元宝
			
			//查累计充值金额
			object.put("ordertype", Enum_ordertype.存款.value);
			object.put("orderstatus", Enum_orderstatus.审核通过.value);
			Map<String, Object> resultDeposit = takeDepositRecoredService.findcountTakeDepositRecord(object);
			if(resultDeposit != null) {
				result.put("orderamount", resultDeposit.get("orderamount"));
			} else {
				result.put("orderamount", "0.00");
			}
			
			//查累计积分转出额
			paramsObj = new HashMap<String, Object>();
			paramsObj.put("employeecode", ee.getEmployeecode());
			paramsObj.put("enterprisecode", ee.getEnterprisecode());
			paramsObj.put("moneychangetypecode", Enum_moneychangetype.转出金额.value);
			Map<String, Object> resultTransfer = employeeMoneyChangeService.findAccountChangeCount(paramsObj);
			if(resultTransfer != null) {
				result.put("transferamount", StringUtil.getDouble(resultTransfer.get("moneychangeamount")));
			} else {
				result.put("transferamount", "0.00");
			}
			
			//查累计累计返现金额
			paramsObj = new HashMap<String, Object>();
			paramsObj.put("employeecode", ee.getEmployeecode());
			paramsObj.put("enterprisecode", ee.getEnterprisecode());
			Map<String, Object> resultFan = baccarath5RebateService.takeRecordCountMoney(object);
			if(resultFan != null) {
				result.put("rebatemoney", StringUtil.getDouble(resultFan.get("rebatemoney")));
			} else {
				result.put("rebatemoney", "0.00");
			}
			
			//查询消分申请待处理记录
			paramsObj = new HashMap<String, Object>();
			paramsObj.put("parentemployeecode", ee.getEmployeecode());
			paramsObj.put("enterprisecode", ee.getEnterprisecode());
			paramsObj.put("ordertype", Enum_ordertype.取款.value);
			paramsObj.put("orderstatus", Enum_orderstatus.审核中.value);
			Map<String, Object> resultTake = takeDepositRecoredService.findcountTakeDepositRecord(paramsObj);
			if(resultTake != null) {
				result.put("takeCount", resultTake.get("count").toString());
			} else {
				result.put("takeCount", "0");
			}
			
			return Enum_MSG.成功.message(result);
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		} catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	/**
	 * 查询代理的元宝和积分余额
	 * 
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/takeUserMoney", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String takeUserMoney(HttpServletRequest request) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[] { "employeecode" });
			EnterpriseEmployee ee = enterpriseEmployeeService.takeEnterpriseEmployee(object);
			
			//先查询用户的积分
			EnterpriseEmployeeCapitalAccount ec = capitalAccountService.takeCurrencyAccount(ee.getEmployeecode());
			if(ec == null){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.资金账户不存在.desc);
			} 
			Baccarath5Balance balance = baccarath5BalanceService.selectByPrimaryKey(ee.getEmployeecode());
			if(balance == null) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.元宝账户不存在.desc);
			}
			
			Map<String, Object> result = new HashMap<String, Object>();
			//返回真钱和元宝之间的兑换比例
			Enterprise enterprise = enterpriseService.selectByPrimaryKey(ee.getEnterprisecode());
			double rate = enterprise.getRate();//返回元宝兑换积分的比例
			double rate2 = enterprise.getRate2();//返回真钱和元宝之间的兑换比例
			result.put("yunbao2jifen_rate", rate);
			result.put("money2yuanbao_rate", rate2);
			
			result.put("money_jifen", ec.getBalance().doubleValue());//返回积分
			result.put("money_yuanbao", balance.getBalance().doubleValue());//返回元宝
			
			return Enum_MSG.成功.message(result);
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		} catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	/**
	 * 代理的元宝兑换成积分
	 * 
	 * 兑换积分后才能给直线会员转分
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/dochange", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String dochange(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[] { "employeecode", "money", "fundpassword"});
			
			String employeecode = object.get("employeecode").toString();
			double money = Double.valueOf(object.get("money").toString());
			if( !com.maven.util.StringUtils.isNumber(object.get("money").toString())) {
				return Enum_MSG.逻辑事物异常.message("兑换失败，兑换元宝数应该是个整数");
			}
			
			EnterpriseEmployee formEmployee = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
			String fundpassword = Encrypt.MD5(String.valueOf(object.get("fundpassword")));
			if(fundpassword==null || !fundpassword.equals(formEmployee.getFundpassword())){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.资金密码错误.desc);
			}
			
			Baccarath5Balance balance = baccarath5BalanceService.selectByPrimaryKey(employeecode);
			if(balance == null) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.元宝账户不存在.desc);
			}
			
			//验证当前代理元宝是否充足
			if(balance.getBalance() < money) {
				return Enum_MSG.逻辑事物异常.message("兑换失败，当前元宝余额不足："+balance.getBalance()+"元宝");
			}
			
			//兑换积分
			//扣减代理余额元宝和增加代理积分余额
			baccarath5ExchangeService.tc_dochange(formEmployee.getEnterprisecode(), employeecode, money);
			
			return Enum_MSG.成功.message("兑换成功");
			
		}catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	/**
	 * 元宝兑换积分记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/exchangeOrder",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String exchangeOrder(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			
			AttrCheckout.checkout(object,true,new String[]{"enterprisecode","employeecode","start","limit"},
					new String[]{"exchangeStatus"});
			object.put("field", "exchange_time");
			object.put("direction", "desc");
			
			if(object.get("orderdate_begin") != null ) {
				if(object.get("orderdate_begin").toString().length() == 10) {
					object.put("startDate", object.get("orderdate_begin").toString().replaceAll("-", ""));
				} else {
					//
				}
			}
			if(object.get("orderdate_end") != null ) {
				if(object.get("orderdate_end").toString().length() == 10) {
					object.put("endDate", object.get("orderdate_end").toString().replaceAll("-", ""));
				} else {
					//
				}
			}
			
			System.out.println("===============================查询兑换记录条件："+object);
					
			List<Baccarath5Exchange> list = baccarath5ExchangeService.selectBetRecord(object);
			
			int count = baccarath5ExchangeService.selectBetRecordCount(object);
			Map<String,Object> parameterMap = new HashMap<String,Object>(count);
			parameterMap.put("record", list);
			parameterMap.put("count", count);
			return Enum_MSG.成功.message(parameterMap);
			
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch(LogicTransactionRollBackException e){
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	
	/**
	 * 代理向直线玩家转分
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/transfer", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String transfer(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[] { "employeecode", "loginaccount" , "money", "fundpassword", "operatype"});
			
			String employeecode = object.get("employeecode").toString();
			String loginaccount = object.get("loginaccount").toString();
			double money = Double.valueOf(object.get("money").toString());
			String operatype = SCom.getString(object.get("operatype"));
			if(operatype.equals("") || operatype.equals("null")) {
				operatype = "transferIn";
			}
			
			
			if( !com.maven.util.StringUtils.isNumber(object.get("money").toString())) {
				return Enum_MSG.逻辑事物异常.message("转出失败，转移积分应该是个整数");
			}
			
			EnterpriseEmployee formEmployee = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
			String fundpassword = Encrypt.MD5(String.valueOf(object.get("fundpassword")));
			if(fundpassword==null || !fundpassword.equals(formEmployee.getFundpassword())){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.资金密码错误.desc);
			}
			
			EnterpriseEmployee acceptEmployee = new EnterpriseEmployee();
			acceptEmployee.setLoginaccount(loginaccount);
			acceptEmployee.setEnterprisecode(formEmployee.getEnterprisecode());//限制本企业
			acceptEmployee = enterpriseEmployeeService.getGGPokerLogin(acceptEmployee);
			if(acceptEmployee == null) {
				return Enum_MSG.用户身份异常.message("未能查询到该会员，请确认账号是否完整");
			}
			if( !acceptEmployee.getParentemployeecode().equals(formEmployee.getEmployeecode())) {
				return Enum_MSG.用户身份异常.message(loginaccount+"不是您的直线会员，无权操作！");
			}
			
			//验证当前代理余额是否充足
			if(operatype.equals("transferIn")) {
				EnterpriseEmployeeCapitalAccount ec = capitalAccountService.takeCurrencyAccount(formEmployee.getEmployeecode());
				if(ec == null){
					return Enum_MSG.逻辑事物异常.message(Enum_MSG.资金账户不存在.desc);
				} 
				if(ec.getBalance().doubleValue() < money) {
					return Enum_MSG.逻辑事物异常.message("转出失败，当前积分余额不足："+ec.getBalance().doubleValue()+"积分");
				}
				//实施转移积分到玩家
				//扣减代理余额元宝和积分
				baccarath5ExchangeService.tc_transfer(formEmployee.getEnterprisecode(), formEmployee.getEmployeecode(), acceptEmployee.getEmployeecode(), money);
				
			} else if(operatype.equals("transferOut")) {
				
				if( !formEmployee.getEmployeetypecode().equals(Type.信用代理.value) ){
					return Enum_MSG.逻辑事物异常.message("非信用代理不能从会员账户转出余额！");
				}
				
				EnterpriseEmployeeCapitalAccount ec = capitalAccountService.takeCurrencyAccount(acceptEmployee.getEmployeecode());
				if(ec == null){
					return Enum_MSG.逻辑事物异常.message(Enum_MSG.资金账户不存在.desc);
				} 
				if(ec.getBalance().doubleValue() < money) {
					return Enum_MSG.逻辑事物异常.message("转出失败，当前积分余额不足："+ec.getBalance().doubleValue()+"积分");
				}
				//实施转移积分到玩家
				//扣减代理余额元宝和积分
				baccarath5ExchangeService.tc_transfer(formEmployee.getEnterprisecode(), acceptEmployee.getEmployeecode(), formEmployee.getEmployeecode(), money);
			}
			
			return Enum_MSG.成功.message("操作成功");
			
		}catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	/**
	 * 代理向直线玩家转分记录查询
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/transferList", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String transferList(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[] {"enterprisecode","parentemployeecode","start","limit"});
			String loginaccount = String.valueOf(object.get("loginaccount"));
			String enterprisecode = String.valueOf(object.get("enterprisecode"));
			
			object.put("field", "moneychangedate");
			object.put("direction", "desc");
			object.put("moneychangetypecode", Enum_moneychangetype.转入金额.value);
			
			if(object.get("startDate") != null && object.get("startDate").toString().length() == 10) {
				object.put("startDate", object.get("startDate").toString().replaceAll("-", ""));
			}
			if(object.get("endDate") != null && object.get("endDate").toString().length() == 10) {
				object.put("endDate", object.get("endDate").toString().replaceAll("-", ""));
			}
			
			if(!loginaccount.equals("null") && loginaccount.length() > 0) {
				EnterpriseEmployee acceptEmployee = new EnterpriseEmployee();
				acceptEmployee.setLoginaccount(loginaccount);
				acceptEmployee.setEnterprisecode(enterprisecode);
				acceptEmployee = enterpriseEmployeeService.getGGPokerLogin(acceptEmployee);
				if(acceptEmployee == null) {
					return Enum_MSG.用户身份异常.message("未能查询到该会员，或该会员不是您的直属会员");
				}
				object.put("employeecode", acceptEmployee.getEmployeecode());
				object.remove("loginaccount");
			}
			
			
			List<EmployeeMoneyChange> list = employeeMoneyChangeService.findAccountChange(object);
			Map<String, Object> result = employeeMoneyChangeService.findAccountChangeCount(object);
			int count = StringUtil.getInt(result.get("count"));
			for (EmployeeMoneyChange emc : list) {
				emc.setAfteramount(emc.getMoneychangeamount().add(emc.getSettlementamount()));
			}
			Map<String,Object> returnObject = new HashMap<String,Object>();
			returnObject.put("record", list);
			returnObject.put("count", count);
			returnObject.put("sumamount", SCom.getDouble(result.get("moneychangeamount")));
			return Enum_MSG.成功.message(returnObject);
			
			
		}catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	/**
	 * 玩家向代理提交申请兑现
	 * 
	 * 消分，也就是等同于现金网的取款。
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/withdraw", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String withdraw(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[] {"enterprisecode","brandcode","employeecode","orderamount","traceip","fundpassword"});
			
			String employeecode = object.get("employeecode").toString();
			double orderamount = Double.valueOf(object.get("orderamount").toString());
			
			if( !com.maven.util.StringUtils.isNumber(object.get("orderamount").toString())) {
				return Enum_MSG.逻辑事物异常.message("申请失败，积分额应该是个整数");
			}
			BigDecimal ordermount = new BigDecimal(String.valueOf(object.get("orderamount")));
			if(ordermount.doubleValue()<=0) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.金额异常.desc);
			}
			
			EnterpriseEmployee ee = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
			String fundpassword = Encrypt.MD5(String.valueOf(object.get("fundpassword")));
			if(fundpassword==null || !fundpassword.equals(ee.getFundpassword())){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.资金密码错误.desc);
			}
			//验证当前代理余额是否充足
			EnterpriseEmployeeCapitalAccount ec = capitalAccountService.takeCurrencyAccount(employeecode);
			if(ec == null){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.资金账户不存在.desc);
			} 
			if(ec.getBalance().doubleValue() < ordermount.abs().doubleValue()) {
				
				//判断是否为10的倍数整数
				if( ordermount.intValue() % 10 != 0 ) {//个位数非0时返回
					return Enum_MSG.逻辑事物异常.message(Enum_MSG.只能按整数取款.desc);
				}
				
				//实时查询余额并做验证
				String result = new APIServiceNew(ee.getEnterprisecode()).balances(ee.getEmployeecode(), ee.getBrandcode());
				log.Error(ee.getLoginaccount()+" ====================开始提款验证：orderamount="+String.valueOf(object.get("orderamount")));
				log.Error(ee.getLoginaccount()+" ====================提款申请先查询余额结果："+result);
				JSONObject jsonObject = JSONObject.fromObject(result);
				double balance = 0;
				if(jsonObject.getString("code").equals("1")) {
					balance = jsonObject.getDouble("info") + ec.getBalance().doubleValue();
				} else {
					log.Error(ee.getLoginaccount()+" ====================因线路问题，未能查询到游戏平台的当前余额，请稍后再试。");
					return Enum_MSG.逻辑事物异常.message("因线路问题，未能查询到游戏平台的当前余额，请稍后再试。");
				}
				if(balance <= 0) {
					log.Error(ee.getLoginaccount()+" ====================当前余额为0，无法提交提款申请。（可能是线路原因导致不能查询到当前余额，请稍后再试）");
					return Enum_MSG.逻辑事物异常.message("当前余额为0，无法提交提款申请。（可能是线路原因导致不能查询到当前余额，请稍后再试）");
				}
				if(ordermount.doubleValue() > balance) {
					log.Error(ee.getLoginaccount()+" ====================当前余额小于提款金额，无法提交提款申请。balance="+balance);
					return Enum_MSG.逻辑事物异常.message("当前余额小于提款金额，无法提交提款申请。");
				}
				
			}
			
			//保存取款单
			TakeDepositRecord takeDepositRecord  = BeanToMapUtil.convertMap(object, TakeDepositRecord.class);
			takeDepositRecord.setOrdernumber(OrderNewUtil.getOrdernoTake());//jason20161115更新单号规则，因为要将该批次号与下分、账变记录同步
			takeDepositRecord.setEmployeecode(ee.getEmployeecode());
			takeDepositRecord.setParentemployeecode(ee.getParentemployeecode());
			takeDepositRecord.setEnterprisecode(ee.getEnterprisecode());
			takeDepositRecord.setBrandcode(ee.getBrandcode());
			takeDepositRecord.setPaymenttypecode(Enum_PayType.银行卡转账.value);
			takeDepositRecord.setOrderamount(ordermount.negate());
			takeDepositRecord.setOrdertype((byte)Enum_ordertype.取款.value);
			takeDepositRecord.setEmployeepaymentname("");
			takeDepositRecord.setEmployeepaymentaccount("");
			takeDepositRecord.setEmployeepaymentbank("");
			takeDepositRecord.setOrderstatus((byte)Enum_orderstatus.审核中.value);
			takeDepositRecord.setOrdercreater("会员"+ee.getDisplayalias());
			takeDepositRecord.setOrdercomment("会员消分");
			takeDepositRecord.setOrderdate(new Date());
			if(takeDepositRecord.getFavourableid() != null ){
				if( takeDepositRecord.getFavourableid().trim().length() <= 0 ) {
					takeDepositRecord.setFavourableid(null);
				}
			}
			takeDepositRecoredService.tc_take_money(takeDepositRecord);
			
			
			return Enum_MSG.成功.message("申请已提交");
			
		}catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	/**
	 * 玩家向代理提交申请兑现记录查询
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/withdrawList", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String withdrawList(HttpServletRequest request, HttpSession session) {
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object,true,new String[]{"enterprisecode","parentemployeecode"},
					new String[]{"ordernumber","orderdate","paymenttypecode","orderamount","employeepaymentaccount","orderstatus"});
			
			object.put("ordertype", Enum_ordertype.取款.value);
			object.put("field", "orderdate");
			object.put("direction", "desc");
			
			if(object.get("orderdate_begin") != null ) {
				if(object.get("orderdate_begin").toString().length() == 10) {
					object.put("orderStartDate", object.get("orderdate_begin").toString().replaceAll("-", ""));
				} else {
					//
				}
			}
			if(object.get("orderdate_end") != null ) {
				if(object.get("orderdate_end").toString().length() == 10) {
					object.put("orderEndDate", object.get("orderdate_end").toString().replaceAll("-", ""));
				} else {
					//
				}
			}
			
			object.remove("orderdate_begin");
			object.remove("orderdate_end");
			
			System.out.println("===============================查询存取款记录条件："+object);
					
			List<TakeDepositRecord> list = takeDepositRecoredService.findTakeDepositRecord(object);
			
			Map<String, Object> result = takeDepositRecoredService.findcountTakeDepositRecord(object);
			int count = Integer.valueOf(result.get("count").toString());
			Map<String,Object> parameterMap = new HashMap<String,Object>(count);
			parameterMap.put("record", list);
			parameterMap.put("count", count);
			parameterMap.put("sumamount", result.get("orderamount"));//总记
			
			return Enum_MSG.成功.message(parameterMap);
			
		}catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	/**
	 * 查询订单表详情
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/orderInfo", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String orderInfo(HttpServletRequest request, HttpSession session) {
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object,true,new String[]{"enterprisecode" ,"ordernumber"});
			String ordernumber = String.valueOf(object.get("ordernumber"));
			TakeDepositRecord depositRecord = takeDepositRecoredService.findOrderNumberTakeDepositRecord(ordernumber);
			if(depositRecord == null) {
				return Enum_MSG.逻辑事物异常.message("单号错误，没有找到交易信息");
			}
			return Enum_MSG.成功.message(depositRecord);
			
		}catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	/**
	 * 代理处理消分申请
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/dowithdraw", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String dowithdraw(HttpServletRequest request, HttpSession session) {
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object,true,new String[]{"enterprisecode" ,"employeecode","ordernumber" ,"ordercomment", "orderstatus"});
			String ordernumber = String.valueOf(object.get("ordernumber"));
			String ordercomment = String.valueOf(object.get("ordercomment"));
			String orderstatus = String.valueOf(object.get("orderstatus"));
			TakeDepositRecord depositRecord = takeDepositRecoredService.findOrderNumberTakeDepositRecord(ordernumber);
			EnterpriseEmployee __ee = enterpriseEmployeeService.takeEnterpriseEmployee(object);
			depositRecord.setOrdercomment(ordercomment);
			
			if(orderstatus.equals(Enum_orderstatus.审核通过.value+"")) {
				
				DepositWithdralOrderDelegate handles = new DepositWithdralOrderDelegate();
				handles.setAuditresult(Enum_auditresult.通过.value.byteValue());
				handles.setAuditdesc(ordercomment);
				handles.setAssignedtocode("线下");
				handles.setAssignedtoaccount(__ee.getLoginaccount());
				depositWithdralOrderDelegateService.tc_autoHandleDeletegate(depositRecord, handles);
				
				
				takeDepositRecoredService.tc_updateTakeDepositRecord(depositRecord);
				
				userLogsService.addActivityBetRecord(new UserLogs(depositRecord.getEnterprisecode(), depositRecord.getEmployeecode(), depositRecord.getLoginaccount(), 
					     UserLogs.Enum_operatype.存取款业务, "消分申请单审批通过:"+depositRecord.getOrdernumber(), __ee.getLoginaccount(), null));
				
				String content = "您单号为"+depositRecord.getOrdernumber()+"的"+(depositRecord.getOrdertype().intValue() == 1 ? "存款":"取款")+"消分申请审核通过！";
				sendMessage(session, content, depositRecord.getEmployeecode(), depositRecord.getLoginaccount());
				
				return Enum_MSG.成功.message("操作成功");
			} else if(orderstatus.equals(Enum_orderstatus.拒绝.value+"")) {

				DepositWithdralOrderDelegate delegete = new DepositWithdralOrderDelegate();
				delegete.setAuditdesc(ordercomment);
				delegete.setAssignedtocode(__ee.getEmployeecode());
				delegete.setAssignedtoaccount(__ee.getLoginaccount());
				delegete.setAuditresult(Enum_auditresult.拒绝.value.byteValue());
				depositWithdralOrderDelegateService.tc_handleDeletegate(depositRecord, delegete,__ee);
				
				takeDepositRecoredService.tc_updateTakeDepositRecord(depositRecord);
				
				
				userLogsService.addActivityBetRecord(new UserLogs(depositRecord.getEnterprisecode(), depositRecord.getEmployeecode(), depositRecord.getLoginaccount(), 
					     UserLogs.Enum_operatype.存取款业务, "消分申请单审批拒绝:"+depositRecord.getOrdernumber(), __ee.getLoginaccount(), null));
				
				String content = "您单号为"+depositRecord.getOrdernumber()+"的"+(depositRecord.getOrdertype().intValue() == 1 ? "存款":"取款")+"消分申请审核拒绝！审核结果："+delegete.getAuditdesc();
				sendMessage(session, content, depositRecord.getEmployeecode(), depositRecord.getLoginaccount());
				
				return Enum_MSG.成功.message("操作成功");
			} 
			
			return Enum_MSG.成功.message("操作失败，不能识别的状态码");
			
		}catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (LogicTransactionRollBackException e) {
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	private boolean sendMessage(HttpSession session , String content, String Acceptemployeecode, String Acceptaccount) {
		try {
			List<EmployeeMessage> messages = new ArrayList<EmployeeMessage>();
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			
			EmployeeMessageText text = new EmployeeMessageText();
			text.setContent(content);
			text.setDatastatus(String.valueOf(Constant.Enum_DataStatus.正常.value));
			
			EmployeeMessage message = new EmployeeMessage();
			message.setEnterprisecode(ee.getEnterprisecode());
			message.setBrandcode(ee.getBrandcode());
			message.setSendemployeecode(ee.getEmployeecode());
			message.setSendemployeeaccount(ee.getLoginaccount());
			message.setAcceptemployeecode(Acceptemployeecode);//接受会员编号
			message.setAcceptaccount(Acceptaccount);//接受会员账号
			message.setMessagetype(String.valueOf(EmployeeMessage.Enum_messagetype.系统消息.value));
			message.setReadstatus(String.valueOf(EmployeeMessage.Enum_readstatus.未阅读.value));
			messages.add(message);
			
			employeeMessageService.tc_sendMessage(messages, text);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * 查询公告和消息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/messages",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String messages(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[]{"brandcode","employeecode"});
			EnterpriseEmployee ee = enterpriseEmployeeService.takeEnterpriseEmployee(object);
			
			//查通用公告
			List<EnterpriseBrandNotic> listNoti = enterpriseBrandNoticService.takeNotic(object);
			
			//查我的消息
			object.clear();
			object.put("acceptemployeecode", ee.getEmployeecode());
			object.put("readstatus", Enum_readstatus.未阅读.value);
			List<EmployeeMessage> listMessages =  employeeMessageService.selectAll(object);
			
			Map<String,Object> resultMap = new HashMap<String, Object>();
			resultMap.put("listMessages", listMessages);
			resultMap.put("listNoti", listNoti);
			
			return Enum_MSG.成功.message(resultMap);
			
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch(LogicTransactionRollBackException e){
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	/**
	 * 标记站内消息为已读状态
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updateMessage",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updateMessage(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[]{"messagecode"});
			
			String messagecode = object.get("messagecode").toString();
			
			int count = employeeMessageService.tc_updateMStatus(Integer.valueOf(messagecode));
			if(count > 0) {
				return Enum_MSG.成功.message("标记成功");
			} else {
				return Enum_MSG.失败.message("标记失败");
			}
			
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch(LogicTransactionRollBackException e){
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	/**
	 * 只分页查询我的消息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/myMessages",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String myMessages(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[]{"brandcode", "messagecode"},new String[]{"start","limit"});
			EnterpriseEmployee ee = enterpriseEmployeeService.takeEnterpriseEmployee(object);
			
			//查我的消息
			object.put("enterprisecode", ee.getEnterprisecode());
			object.put("employeecode", ee.getEmployeecode());
			object.put("field", "readstatus");
			object.put("direction", "desc");
			List<EmployeeMessage> listMessages =  employeeMessageService.selectAll(object);
			int count = employeeMessageService.selectAllCount(object);
			
			return Enum_MSG.成功.message(formatPagaMap(listMessages, count));
			
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch(LogicTransactionRollBackException e){
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	
	/**
	 * 获取日结报表汇总数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/RecordsDayAll",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String RecordsDayAll(HttpServletRequest request){
		try {
			
			
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[]{"parentemployeecode","start","limit"});
			
			System.out.println("查游戏记录原参数："+object);
			
			String startDate = String.valueOf(object.get("startDate"));
			String endDate = String.valueOf(object.get("endDate"));
			String loginaccount = String.valueOf(object.get("loginaccount"));
			
			if(!loginaccount.equals("null") && loginaccount.length() > 0) {
				
				EnterpriseEmployee acceptEmployee = new EnterpriseEmployee();
				acceptEmployee.setLoginaccount(loginaccount);
				acceptEmployee.setEnterprisecode(String.valueOf(object.get("enterprisecode")));
				acceptEmployee = enterpriseEmployeeService.getGGPokerLogin(acceptEmployee);
				if(acceptEmployee == null) {
					return Enum_MSG.用户身份异常.message("未能查询到该会员，或该会员不是您的直属会员");
				}
				object.put("userName", loginaccount);
				object.remove("loginaccount");
			}
			if(!startDate.equals("null") && startDate.length() > 0) {
				object.put("startdate", startDate.replaceAll("-", ""));
				object.remove("startDate");
			} else {
				object.put("startdate", com.maven.util.StringUtils.getDateLast());//默认当日
				object.remove("startDate");
			}
			if(!endDate.equals("null") && endDate.length() > 0) {
				object.put("enddate", endDate.replaceAll("-", ""));
				object.remove("endDate");
			} else {
				object.put("enddate", com.maven.util.StringUtils.getDateLast());//默认当日
				object.remove("endDate");
			}
			
			
			Map<String,Object> returnObject = new HashMap<String,Object>();
			object.put("direction", "desc");
			object.put("field", "Bet_Day");
			
			System.out.println("查游戏记录后参数："+object);
			
			
			List<BettingAllDay> list = bettingAllDayService.selectForPage(object);
			for (BettingAllDay item : list) {
				
				item.setGamePlatform(Enum_GameType.getname(item.getGamePlatform()) );
				
			}
			Map<String, Object> map = bettingAllDayService.takeRecordCountMoney(object);
			returnObject.put("record", list);
			returnObject.put("count", map.get("count"));
			returnObject.put("validMoney", map.get("validMoney") == null ? "0" : map.get("validMoney"));
			returnObject.put("netMoney", map.get("netMoney") == null ? "0" : map.get("netMoney"));
			
			return Enum_MSG.成功.message(returnObject);
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch(LogicTransactionRollBackException e){
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	/**
	 * 登陆日志
	 * @param object
	 * @param ee
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	private void loginLog(Map<String, Object> object, EnterpriseEmployee ee)
			throws ClassNotFoundException, InstantiationException, ParseException, IllegalAccessException,
			IntrospectionException, InvocationTargetException, Exception {
		LogLogin loginLog = BeanToMapUtil.convertMap(object, LogLogin.class);
		loginLog.setEnterprisecode(ee.getEnterprisecode());
		loginLog.setBrandcode(ee.getBrandcode());
		loginLog.setEmployeecode(ee.getEmployeecode());
		loginLog.setParentemployeecode(ee.getParentemployeecode());
		loginLog.setLoginaccount(ee.getLoginaccount());
		loginLog.setLogintime(new Date());
		loginLog.setMachinecode(StringUtil.getString(object.get("fingerprintcode")) );//浏览器唯一编码
		loginLog.setRefererurl(String.valueOf(object.get("domain")));
		logLoginService.addLoginLog(loginLog); ;
	}
	/**
	 * 验证游戏开关是否打开
	 * 
	 * @param brandcode
	 * @param gameType
	 * @return
	 * @throws Exception
	 */
	private Game isOpenGame(String brandcode, String gameType,List<Game> games ) throws Exception {
		for (Game game : games) {
			if (gameType.equals(game.getGametype())) {
				return game;
			}
		}
		return null;
	}
	/**
	 * 验证游戏密码是否已改完，如果没同步完成则不能上下分和进入游戏，否则会异常
	 * 
	 * 只检查有密码的游戏平台
	 * 
	 * @param brandcode
	 * @param gameType
	 * @return
	 * @throws Exception
	 */
	private Enum_GameType[] gametypeArray = GameEnum.Enum_GameType.values();
	private boolean isPasswordCompelted(EnterpriseEmployee ee ,String gametype) throws Exception {
		
		//需要修改的记录放到队列中
		for (Enum_GameType enum_GameType : gametypeArray) {
			
			//正在操作的游戏需要是有密码业务的
			if(enum_GameType.gametype.equals(gametype) && enum_GameType.updatepassword == true) {
				
				//查询最新的待处理的任务
				EmployeeApiAccoutPasswordJob accoutPasswordJob = new EmployeeApiAccoutPasswordJob();
				accoutPasswordJob.setUpdatestatus(0);
				accoutPasswordJob.setGametype(gametype);
				accoutPasswordJob.setEmployeecode(ee.getEmployeecode());
				List<EmployeeApiAccoutPasswordJob> list = employeeApiAccoutPasswordJobService.findList(accoutPasswordJob);//查询上次处理失败需要处理的任务
				if(list != null && list.size() > 0) {
					return true;
				}
				
				//再查处理失败的
				accoutPasswordJob.setUpdatestatus(2);
				list.addAll(employeeApiAccoutPasswordJobService.findList(accoutPasswordJob));
				
				if(list != null && list.size() > 0) {
					return true;
				}
				
			}
		}
		
		return false;
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
