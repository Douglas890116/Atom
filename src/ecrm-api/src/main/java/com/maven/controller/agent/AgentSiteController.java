package com.maven.controller.agent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.controller.member.EmployeeController;
import com.maven.entity.AgentSiteContact;
import com.maven.entity.BrandDomain;
import com.maven.entity.BrandDomain.Enum_copyright;
import com.maven.entity.BrandDomain.Enum_domaintype;
import com.maven.entity.BrandDomain.Enum_linkstatus;
import com.maven.entity.EmployeeGamecataloyBonus;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployee.Enum_employeestatus;
import com.maven.entity.EnterpriseEmployee.Enum_onlinestatus;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.UserLogs;
import com.maven.entity.WebviewTemplate;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.game.APIServiceUtil;
import com.maven.game.HttpUtils;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.AgentSiteContactService;
import com.maven.service.BrandDomainService;
import com.maven.service.EmployeeGamecataloyBonusService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseOperatingBrandService;
import com.maven.service.EnterpriseService;
import com.maven.service.UserLogsService;
import com.maven.service.WebviewTemplateService;
import com.maven.util.AttrCheckout;
import com.maven.util.BeanToMapUtil;
import com.maven.util.Encrypt;
import com.maven.util.RegularCheck;

@RequestMapping("/Agent")
@Controller
public class AgentSiteController extends BaseController{
	
	private static LoggerManager LOG = LoggerManager.getLogger(EmployeeController.class.getName(), OutputManager.LOG_UER_AGENT);
	
	private static Map<String,String> GAMETYPEDICTIONARY = new HashMap<String, String>();
	
	static{
		GAMETYPEDICTIONARY.put("BBINGame", "BBIN (波音)");
		GAMETYPEDICTIONARY.put("AGGame", "OG-AG (东方-AG)");
		GAMETYPEDICTIONARY.put("XCPGame", "XR (彩票)");
		GAMETYPEDICTIONARY.put("TAGGame", "AG (亚游)");
		GAMETYPEDICTIONARY.put("PTGame", "PT (小游戏)");
		GAMETYPEDICTIONARY.put("OGGame", "OG (东方)");
		GAMETYPEDICTIONARY.put("QPGame", "QP (棋牌)");
		GAMETYPEDICTIONARY.put("IBCGame", "SB (沙巴体育)");
		GAMETYPEDICTIONARY.put("NHQGame", "HY (好赢)");
		GAMETYPEDICTIONARY.put("YAGGame", "YAG(云谷-AG)");
		
		GAMETYPEDICTIONARY.put("MGGame", "MG (小游戏)");
		GAMETYPEDICTIONARY.put("TTGGame", "TTG (小游戏)");
		GAMETYPEDICTIONARY.put("PNGGame", "PNG (小游戏)");
		
		GAMETYPEDICTIONARY.put("GGGame", "GG (小游戏)");
		GAMETYPEDICTIONARY.put("SGSGame", "SGS (申博)");
		GAMETYPEDICTIONARY.put("IDNGame", "IDN (棋牌)");
		GAMETYPEDICTIONARY.put("M88Game", "M88");
		GAMETYPEDICTIONARY.put("HABGame", "HAB");
		
		GAMETYPEDICTIONARY.put("CP", "彩票");
		GAMETYPEDICTIONARY.put("TY", "体育");
		GAMETYPEDICTIONARY.put("SX", "视讯");
		GAMETYPEDICTIONARY.put("DZ", "电子游艺");
		GAMETYPEDICTIONARY.put("QP", "棋牌");
	}
	
	@Autowired
	private BrandDomainService brandDomainService;
	
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	
	@Autowired
	private EmployeeGamecataloyBonusService employeeGamecataloyBonusService;
	
	@Autowired
	private EnterpriseOperatingBrandService enterpriseOperatingBrandService;
	
	@Autowired
	private WebviewTemplateService webviewTemplateService;
	
	@Autowired
	private EnterpriseService enterpriseService;
	
	@Autowired
	private AgentSiteContactService agentSiteContactService;
	
	@Autowired
	private UserLogsService userLogsService;
	
	@RequestMapping(value={"/testjson"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String ss(HttpServletResponse response,HttpServletRequest request) throws Exception{
		response.setHeader("Access-Control-Allow-Origin", "*");
		return HttpUtils.getcontent("http://ip.ws.126.net/ipquery?ip="+request.getParameter("ip"));
	}
	
	/**
	 * 企业品牌游戏
	 * @param __request
	 * @return
	 */
	@RequestMapping(value="/AgentDomain",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String agentDomain(HttpServletRequest __request,HttpServletResponse __response){
		 try {
			   	Map<String,Object> object = super.getRequestParamters(__request);
				String domain = String.valueOf(object.get("domain"));
				domain = RegularCheck.takeDomain(domain);
				BrandDomain augold =  brandDomainService.queryByDomainLink(domain);
				if(augold==null||augold.getCopyright().equals(Enum_copyright.公共.value)
						||augold.getDomaintype()==Enum_domaintype.会员站点.value.byteValue()
						||augold.getDomaintype()==Enum_domaintype.企业域名_会员站点.value.byteValue()) {
					return Enum_MSG.逻辑事物异常.message(Enum_MSG.域名未注册.desc);
				}
				if(augold.getLinkstatus().equals(Enum_linkstatus.禁用.value)){
					return Enum_MSG.逻辑事物异常.message(Enum_MSG.域名已禁用.desc);
				}
				WebviewTemplate __template = webviewTemplateService.selectByPrimaryKey(augold.getWebtemplatecode());
				Enterprise __eps = enterpriseService.selectByPrimaryKey(augold.getEnterprisecode());
				AgentSiteContact __contact = agentSiteContactService.selectByDomaincode(augold.getDomaincode());
				Map<String,Object> __returnAgument = new HashMap<String, Object>();
				__returnAgument.put("enterprisecode", augold.getEnterprisecode());
				__returnAgument.put("enterprisename", __eps.getEnterprisename());
				__returnAgument.put("template", __template.getSign());
				
				// 新增品牌号
				__returnAgument.put("employeecode", augold.getEmployeecode());
				__returnAgument.put("parentemployeecode", augold.getParentemployeecode());
				__returnAgument.put("brandcode", augold.getBrandcode());
				__returnAgument.put("brandname", augold.getBrandname());
				if(__contact!=null){
					__returnAgument.put("qq", __contact.getQq());
					__returnAgument.put("skype", __contact.getSkype());
					__returnAgument.put("vchat", __contact.getVchat());
					__returnAgument.put("email", __contact.getEmail());
					__returnAgument.put("phone", __contact.getPhone());
				}
				BrandDomain __menberDomain = brandDomainService.selectFirst(new BrandDomain(augold.getEmployeecode(),Enum_linkstatus.启用,
						new Byte[]{Enum_domaintype.会员站点.value.byteValue(),Enum_domaintype.企业域名_会员站点.value.byteValue()}));
				__returnAgument.put("managersite", SystemConstant.getProperties("manager.server"));
				__returnAgument.put("menbersite", __menberDomain.getDomainlink());
				//返回logo地址
				EnterpriseOperatingBrand brand = enterpriseOperatingBrandService.takeBrandByPrimaryKey(augold.getBrandcode());
				__returnAgument.put("logopath", brand.getLogopath());
				
				return Enum_MSG.成功.message(__returnAgument);
		   }catch(ArgumentValidationException e){
			   	LOG.Error(e.getMessage(), e);
				return Enum_MSG.参数错误.message(e.getMessage());
		   }catch (LogicTransactionRollBackException e) {
			   LOG.Error(e.getMessage(), e);
			   return Enum_MSG.逻辑事物异常.message(e.getMessage());
		   }catch (Exception e) {
				LOG.Error(e.getMessage(), e);
				return Enum_MSG.系统错误.message(null);
		   }
	}

	/**
	 * 代理注册,返回用户的分红、占成、各个游戏的洗码
	 * @return
	 */
	@RequestMapping(value={"/Register"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String register(HttpServletRequest __request){
		try {
			//参数解密与签名认证
			Map<String,Object> __object = super.apiDecode(super.getRequestParamters(__request));
			//必填参数验证
			AttrCheckout.checkout(__object, false, new String[]{"enterprisecode","domain","loginaccount","loginpassword"});
			
			//获取并解析站点域名
			String __registerDomian = String.valueOf(__object.get("domain"));
			__registerDomian = RegularCheck.takeDomain(__registerDomian);
			//获取站点对应的注册参数
			BrandDomain __registerSiteAgument = brandDomainService.queryByDomainLink(__registerDomian);
			
			//判断站点是否为启用的代理注册站点
			if(__registerSiteAgument==null
					||__registerSiteAgument.getCopyright().equals(Enum_copyright.公共.value)
					||(__registerSiteAgument.getDomaintype()!=Enum_domaintype.代理站点.value.byteValue()
						&&__registerSiteAgument.getDomaintype()!=Enum_domaintype.企业域名_代理站点.value.byteValue())) 
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.域名未注册.desc);
			if(__registerSiteAgument.getLinkstatus().equals(Enum_linkstatus.禁用.value)){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.域名已禁用.desc);
			}
			
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
			
			//根据站点对应的注册参数设置用户信息
			__agentObject.setBrandcode(__registerSiteAgument.getBrandcode());
			//获取推广用户的信息
			EnterpriseEmployee employee = enterpriseEmployeeService.takeEmployeeByCode(__registerSiteAgument.getEmployeecode());
			__agentObject.setParentemployeecode(employee.getEmployeecode());
			
			__agentObject.setLoginpassword2(APIServiceUtil.encrypt(__agentObject.getLoginpassword(), __agentObject));//对原始密码进行加密
			__agentObject.setLoginpassword(Encrypt.MD5(__agentObject.getLoginpassword()));
			
			__agentObject.setDividend(__registerSiteAgument.getDividend());
			__agentObject.setShare(__registerSiteAgument.getShare());
			__agentObject.setRegistercode(__registerDomian);
			__agentObject.setEmployeetypecode(__registerSiteAgument.getEmployeetype());
			__agentObject.setFundpassword("");
			String __displayias = __agentObject.getLoginaccount().length()>8?
					__agentObject.getLoginaccount().substring(0, 8):__agentObject.getLoginaccount().substring(0, __agentObject.getLoginaccount().length());
			__agentObject.setDisplayalias(__displayias);
			List<EmployeeGamecataloyBonus> __bonusList = new ArrayList<EmployeeGamecataloyBonus>();
			String[] __temp = __registerSiteAgument.getBonus().split("\\|");
			for (int i = 0; i < __temp.length; i++) {
				EmployeeGamecataloyBonus person = new EmployeeGamecataloyBonus();
				String[] str1 = __temp[i].split(":");
				for (int j = 0; j < str1.length; j++) {
					if(j%2==0){
						String[] str2 = str1[j].split("_");
						for (int k = 0; k < str2.length; k++) {
							if(k%2==0){
								person.setCategorytype(str2[k]);
							}else{
								person.setGametype(str2[k]);
							}
						}
					}else{
						person.setBonus(new BigDecimal(str1[j]));
					}
				}
				__bonusList.add(person);
			}
			__agentObject.setOnlinestatus(Byte.valueOf(Enum_onlinestatus.离线.value));
			__agentObject.setEmployeestatus(Enum_employeestatus.启用.value.byteValue());
			
			__agentObject.setParentemployeeaccount(employee.getLoginaccount());
			//注册账号
			boolean isDirectly = employee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.企业号.value);
			enterpriseEmployeeService.tc_saveUser(__agentObject,__bonusList,isDirectly);
			
			//封装返回数据
			List<Map<String,String>> __bonusMapList = new ArrayList<Map<String,String>>(); 
			for (EmployeeGamecataloyBonus __egb : __bonusList) {
				Map<String,String> __egbm = new HashMap<String, String>();
				__egbm.put("categorytype", __egb.getCategorytype());
				__egbm.put("gametype", __egb.getGametype());
				__egbm.put("bonus", __egb.getBonus().toString());
				__egbm.put("gamename", GAMETYPEDICTIONARY.get(__egb.getGametype()));
				__egbm.put("categoryname", GAMETYPEDICTIONARY.get(__egb.getCategorytype()));
				__bonusMapList.add(__egbm);
			}
			//默认生成企业代理站点域名
			Map<String,Object> __returnAgument = new HashMap<String, Object>();
//			使用错误方法，此处该获取默认代理域名 
//			BrandDomain __defualt = brandDomainService.takeEDefualtUSiteDomain(__agentObject.getEnterprisecode());
			
			BrandDomain __defualt = brandDomainService.takeEDefualtASiteDomain(__agentObject.getEnterprisecode());
			if(__defualt!=null){
				System.err.println("代理注册调试域名1："+__defualt.getDomainlink());
				String __priex = brandDomainService.takeDefualtDomainPrefix();
				System.err.println("代理注册调试域名2："+__priex);
				__returnAgument.put("defualtdomain", __priex+"."+__defualt.getDomainlink());
				__returnAgument.put("cnamedomain", __defualt.getDomainlink());
			}
			
			
			__returnAgument.put("employeecode", __agentObject.getEmployeecode());
			//占成信息
			__returnAgument.put("share", __agentObject.getShare());
			//分红信息
			__returnAgument.put("dividend", __agentObject.getShare());
			//用户返点信息
			__returnAgument.put("bonus", __bonusMapList);
			
			userLogsService.addActivityBetRecord(new UserLogs(__agentObject.getEnterprisecode(), __agentObject.getEmployeecode(), __agentObject.getLoginaccount(), 
					UserLogs.Enum_operatype.用户资料业务, "代理注册用户", null, null));
			
			
			return Enum_MSG.成功.message(__returnAgument);
		}catch(ArgumentValidationException __ex){
			LOG.Error(__ex.getMessage(), __ex);
			return Enum_MSG.参数错误.message(__ex.getMessage());
		}catch(LogicTransactionRollBackException __ex){
			return Enum_MSG.逻辑事物异常.message(__ex.getMessage());
		}catch (Exception __ex) {
			LOG.Error(__ex.getMessage(), __ex);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	
	/**
	 * 创建代理站点,返回站点各个游戏的洗码,代理拥有的品牌
	 * @return
	 */
	@RequestMapping(value={"/FoundAgentSite"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String foundAgentSite(HttpServletRequest __request,HttpServletResponse __response){
		try {
			//参数解密与签名认证
			Map<String,Object> __object = super.apiDecode(super.getRequestParamters(__request));
		
			//必填参数验证
			AttrCheckout.checkout(__object, false, new String[]{"enterprisecode","employeecode","agentsitedomain","domainlink","dividend","share","qq","email"},new String[]{"skype","vchat","phone"});
			
			//注册代理信息
			EnterpriseEmployee __registerAgent = super.agentAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(__object));
			
			//注册站点参数
			BrandDomain __registerSiteAgument = brandDomainService.queryByDomainLink(String.valueOf(__object.get("agentsitedomain")));
			
			//填充代理站点参数
			BrandDomain __registerSite  = BeanToMapUtil.convertMap(__object, BrandDomain.class);
			BrandDomain __defualt = brandDomainService.takeEDefualtASiteDomain(__registerAgent.getEnterprisecode());
			if(__defualt!=null&&__registerSite.getDomainlink().indexOf(__defualt.getDomainlink())>-1){
				__registerSite.setLinkdomain(__defualt.getDomaincode());
			}
			__registerSite.setEnterprisecode(__registerAgent.getEnterprisecode());
			__registerSite.setBrandcode(__registerAgent.getBrandcode());
			__registerSite.setEmployeecode(__registerAgent.getEmployeecode());
			__registerSite.setParentemployeecode(__registerAgent.getParentemployeecode());
			__registerSite.setEmployeetype(__registerAgent.getEmployeetypecode());
			__registerSite.setDividend(dividendVerify(__registerAgent, __object));
			__registerSite.setShare(shareVerify(__registerAgent, __object));
			__registerSite.setBonus(bonusVerify(__registerAgent, __object));
			__registerSite.setDomaintype(BrandDomain.Enum_domaintype.代理站点.value.byteValue());
			__registerSite.setCopyright(Byte.valueOf(Enum_copyright.私有.value));
			__registerSite.setIsdefualt(Constant.BooleanByte.NO.byteValue());
			__registerSite.setLinkstatus(String.valueOf(BrandDomain.Enum_linkstatus.启用.value));
			__registerSite.setDatastatus(String.valueOf(Constant.Enum_DataStatus.正常.value));
			__registerSite.setWebtemplatecode(__registerSiteAgument.getWebtemplatecode());
			
			AgentSiteContact __registerSiteContact  = BeanToMapUtil.convertMap(__object, AgentSiteContact.class);
			// 调用保存方法
			brandDomainService.tc_saveAgentDomainAndContact(__registerSite,__registerSiteContact);
			
			Map<String,Object> __returnAgument = new HashMap<String, Object>();
			//代理可运营品牌
			List<EnterpriseOperatingBrand> __eobs = new ArrayList<EnterpriseOperatingBrand>();
			if(StringUtils.isNotBlank(__registerAgent.getBrandcode())){
				EnterpriseOperatingBrand __eob = enterpriseOperatingBrandService.selectByPrimaryKey(__registerAgent.getBrandcode());
				__eobs.add(__eob);
			}else{
				List<EnterpriseOperatingBrand> __eobss = enterpriseOperatingBrandService.getEnterpriseBrand(__registerAgent.getEnterprisecode());
				__eobs.addAll(__eobss);
			}
			List<Map<String,Object>> __eobsM = new ArrayList<Map<String,Object>>();
			for (EnterpriseOperatingBrand __epb : __eobs) {
				Map<String,Object> __epbM = new HashMap<String, Object>();
				__epbM.put("brandcode", __epb.getBrandcode());
				__epbM.put("brandname", __epb.getBrandname());
				__eobsM.add(__epbM);
			}
			//会员站点默认生成域名
			BrandDomain __defualtUSite = brandDomainService.takeEDefualtUSiteDomain(__registerAgent.getEnterprisecode());
			if(__defualtUSite!=null){
				String __priex = brandDomainService.takeDefualtDomainPrefix();
				__returnAgument.put("defualtdomain", __priex+"."+__defualtUSite.getDomainlink());
				__returnAgument.put("cnamedomain", __defualtUSite.getDomainlink());
			}
			__returnAgument.put("employeecode", __registerAgent.getEmployeecode());
			__returnAgument.put("bonus", parseBonus(__registerSite.getBonus(),null));
			__returnAgument.put("brand", __eobsM);
			
			userLogsService.addActivityBetRecord(new UserLogs(__registerAgent.getEnterprisecode(), __registerAgent.getEmployeecode(), __registerAgent.getLoginaccount(), 
					UserLogs.Enum_operatype.站点管理业务, "创建代理站点"+__registerSite.getDomainlink(), null, null));
			
			
			return Enum_MSG.成功.message(__returnAgument); 
		}catch(ArgumentValidationException e){
		   	LOG.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
	   }catch (LogicTransactionRollBackException e) {
		   LOG.Error(e.getMessage(), e);
		   return Enum_MSG.逻辑事物异常.message(e.getMessage());
	   }catch (Exception e) {
			LOG.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
	   }
	}
	
	/**
	 * 创建会员站点,返回是否成功标识
	 * @return
	 */
	@RequestMapping(value={"/FoundUserSite"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String foundUserSite(HttpServletRequest __request, HttpServletResponse __response){
		try {
			//参数解密与签名认证
			Map<String,Object> __object = super.apiDecode(super.getRequestParamters(__request));
		
			//必填参数验证
			AttrCheckout.checkout(__object, false, new String[]{"enterprisecode","employeecode","domainlink","brandcode"});
			
			//注册代理信息
			EnterpriseEmployee __registerAgent = super.agentAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(__object));
			
			//填充会员站点参数
			BrandDomain __registerSite  = BeanToMapUtil.convertMap(__object, BrandDomain.class);
			BrandDomain __defualt = brandDomainService.takeEDefualtUSiteDomain(__registerAgent.getEnterprisecode());
			if(__defualt!=null&&__registerSite.getDomainlink().indexOf(__defualt.getDomainlink())>-1){
				__registerSite.setLinkdomain(__defualt.getDomaincode());
			}
			__registerSite.setEnterprisecode(__registerAgent.getEnterprisecode());
			__registerSite.setEmployeecode(__registerAgent.getEmployeecode());
			__registerSite.setParentemployeecode(__registerAgent.getParentemployeecode());
			__registerSite.setEmployeetype(EnterpriseEmployeeType.Type.会员.value);
			__registerSite.setDividend(new BigDecimal("0"));
			__registerSite.setShare(new BigDecimal("0"));
			__registerSite.setBonus(bonusVerify(__registerAgent, __object));
			__registerSite.setDomaintype(BrandDomain.Enum_domaintype.会员站点.value.byteValue());
			__registerSite.setCopyright(Byte.valueOf(Enum_copyright.私有.value));
			__registerSite.setIsdefualt(Constant.BooleanByte.NO.byteValue());
			__registerSite.setLinkstatus(String.valueOf(BrandDomain.Enum_linkstatus.启用.value));
			__registerSite.setDatastatus(String.valueOf(Constant.Enum_DataStatus.正常.value));
			// 调用保存方法
			brandDomainService.tc_save(__registerSite);
			
			//返回参数封装
			Map<String,Object> __returnAgument = new HashMap<String, Object>();
			__returnAgument.put("managersite", SystemConstant.getProperties("manager.server"));

			Map<String,Object> __selectAgentDomains = new HashMap<String, Object>();
			__selectAgentDomains.put("domaintypes",new Byte[]{BrandDomain.Enum_domaintype.代理站点.value.byteValue(),BrandDomain.Enum_domaintype.会员站点.value.byteValue()});
			__selectAgentDomains.put("employeecode", __registerSite.getEmployeecode());
			List<BrandDomain> __brandDomains = brandDomainService.selectAll(__selectAgentDomains);
			
			for (BrandDomain __bd : __brandDomains) {
				if(__bd.getDomaintype()
						==BrandDomain.Enum_domaintype.会员站点.value.byteValue()){
					__returnAgument.put("membersite", __bd.getDomainlink());
					
				}
				if(__bd.getDomaintype()
						==BrandDomain.Enum_domaintype.代理站点.value.byteValue()){
					__returnAgument.put("agentsite", __bd.getDomainlink());
				}
			}
			
			userLogsService.addActivityBetRecord(new UserLogs(__registerAgent.getEnterprisecode(), __registerAgent.getEmployeecode(), __registerAgent.getLoginaccount(), 
					UserLogs.Enum_operatype.站点管理业务, "创建会员站点"+__registerSite.getDomainlink(), null, null));
			
			return Enum_MSG.成功.message(__returnAgument); 
		}catch(ArgumentValidationException e){
		   	LOG.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
	   }catch (LogicTransactionRollBackException e) {
		   LOG.Error(e.getMessage(), e);
		   return Enum_MSG.逻辑事物异常.message(e.getMessage());
	   }catch (Exception e) {
			LOG.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
	   }
	}
	
	
	/**
	 * 查询用户的分红、占成、洗码信息 ，后台管理地址
	 * @return
	 */
	@Deprecated
	@RequestMapping(value={"/AgentInfo"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String agentInfo(HttpServletRequest __request, HttpServletResponse __response){
		try{
			//参数解密与签名认证
			Map<String,Object> __object = super.apiDecode(super.getRequestParamters(__request));
			//必填参数验证
			AttrCheckout.checkout(__object, false, new String[]{"enterprisecode","employeecode"});
			
			//用户名、分红、占成
			EnterpriseEmployee __agentUser = enterpriseEmployeeService.takeEnterpriseEmployee(__object);
			
			//洗码
			List<EmployeeGamecataloyBonus> __egbs =  employeeGamecataloyBonusService.takeEmployeeGameCategoryBonus(__agentUser.getEmployeecode());
			List<Map<String,String>> __egbsM = new  ArrayList<Map<String,String>>();
			for (EmployeeGamecataloyBonus __eb : __egbs) {
				Map<String,String> __em = new HashMap<String, String>();
				__em.put("categorytype", __eb.getCategorytype());
				__em.put("categoryname", GAMETYPEDICTIONARY.get(__eb.getCategorytype()));
				__em.put("gametype", __eb.getGametype());
				__em.put("gamename", GAMETYPEDICTIONARY.get(__eb.getGametype()));
				__egbsM.add(__em);
			}
			
			
			//返回参数封装
			Map<String,Object> __returnAgument = new HashMap<String, Object>();
			__returnAgument.put("managerurl", SystemConstant.getProperties("manager.server"));
			__returnAgument.put("loginaccount", __agentUser.getLoginaccount());
			__returnAgument.put("displayalias", __agentUser.getDisplayalias());
			__returnAgument.put("dividend", __agentUser.getDividend());
			__returnAgument.put("share", __agentUser.getShare());
			__returnAgument.put("bonus", __egbsM);
			return Enum_MSG.成功.message(__returnAgument);
		} catch (LogicTransactionRollBackException __ex) {
			return Enum_MSG.逻辑事物异常.message(__ex.getMessage());
		} catch (Exception e) {
			LOG.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	/**
	 *  代理站点与分红、占成、洗码信息
	 * @return
	 */
	@Deprecated
	@RequestMapping(value={"/AgentSiteInfo"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String agentSiteInfo(HttpServletRequest __request, HttpServletResponse __response){
		try{
			//参数解密与签名认证
			Map<String,Object> __object = super.apiDecode(super.getRequestParamters(__request));
			//必填参数验证
			AttrCheckout.checkout(__object, false, new String[]{"enterprisecode","employeecode"});
			
			__object.put("domaintype", Enum_domaintype.代理站点.value.byteValue());
			
			List<BrandDomain> __brandDomains = brandDomainService.selectAll(__object);
			List<Map<String,Object>> __brandDomainsM = new ArrayList<Map<String,Object>>();
			for (BrandDomain __domain : __brandDomains) {
				Map<String,Object> __domianM = new HashMap<String, Object>();
				__domianM.put("domainlink", __domain.getDomainlink());
				__domianM.put("bonus", parseBonus(__domain.getBonus(), null));
				__domianM.put("dividend", __domain.getDividend());
				__domianM.put("share", __domain.getShare());
				__brandDomainsM.add(__domianM);
			}
			return Enum_MSG.成功.message(__brandDomainsM);
		} catch (LogicTransactionRollBackException __ex) {
			return Enum_MSG.逻辑事物异常.message(__ex.getMessage());
		} catch (Exception e) {
			LOG.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	 
	/**
	 *  会员站点与洗码信息
	 * @return
	 */
	@Deprecated
	@RequestMapping(value={"/MemberSiteInfo"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String memberSiteInfo(HttpServletRequest __request, HttpServletResponse __response){
		try{
			//参数解密与签名认证
			Map<String,Object> __object = super.apiDecode(super.getRequestParamters(__request));
			//必填参数验证
			AttrCheckout.checkout(__object, false, new String[]{"enterprisecode","employeecode"});

			__object.put("domaintype", Enum_domaintype.会员站点.value.byteValue());
			
			List<BrandDomain> __brandDomains = brandDomainService.selectAll(__object);
			List<Map<String,Object>> __brandDomainsM = new ArrayList<Map<String,Object>>();
			for (BrandDomain __domain : __brandDomains) {
				Map<String,Object> __domianM = new HashMap<String, Object>();
				__domianM.put("domainlink", __domain.getDomainlink());
				__domianM.put("bonus", parseBonus(__domain.getBonus(), null));
				__brandDomainsM.add(__domianM);
			}
			return Enum_MSG.成功.message(__brandDomainsM);
		} catch (LogicTransactionRollBackException __ex) {
			return Enum_MSG.逻辑事物异常.message(__ex.getMessage());
		} catch (Exception e) {
			LOG.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	
	private List<Map<String,String>> parseBonus(String __agentBonus,String __resertBonus){
		List<Map<String,String>> __bonusList = new ArrayList<Map<String,String>>();
		String[] __temp = __agentBonus.split("\\|");
		for (int i = 0; i < __temp.length; i++) {
			Map<String,String> __egbm =  new HashMap<String, String>();
			String[] str1 = __temp[i].split(":");
			for (int j = 0; j < str1.length; j++) {
				if(j%2==0){
					String[] str2 = str1[j].split("_");
					for (int k = 0; k < str2.length; k++) {
						if(k%2==0){
							__egbm.put("categorytype", str2[k]);
							__egbm.put("categoryname", GAMETYPEDICTIONARY.get(str2[k]));
						}else{
							__egbm.put("gametype", str2[k]);
							__egbm.put("gamename", GAMETYPEDICTIONARY.get(str2[k]));
						}
					}
				}else{
					if(StringUtils.isNotBlank(__resertBonus)){
						__egbm.put("bonus", "0");			
					}else{
						__egbm.put("bonus", str1[j]);
					}
				}
			}
			__bonusList.add(__egbm);
		}
		return __bonusList;
	}
	
	
	
	/**
	 * 占成校验
	 * @param __agent
	 * @param ee
	 * @param __agument
	 * @return
	 */
	private BigDecimal shareVerify(EnterpriseEmployee __agent,Map<String,Object> __agument){
		BigDecimal __share = new BigDecimal(String.valueOf(__agument.get("share")));
		if(__share.compareTo(new BigDecimal("100"))==1
				||__share.compareTo(new BigDecimal("0"))==-1){
			throw new LogicTransactionRollBackException("占成设置区间为0%-100%");
		}
		if(__share.compareTo(__agent.getShare().multiply(new BigDecimal("100")))==1){
			throw new LogicTransactionRollBackException("占成设置区间不能大于"+__agent.getShare());
		}
		return __share.divide(new BigDecimal("100"));
	}
	
	
	/**
	 * 分红校验
	 * @param __agent
	 * @param ee
	 * @param __agument
	 * @return
	 */
	private BigDecimal dividendVerify(EnterpriseEmployee __agent,Map<String,Object> __agument){
		BigDecimal __dividend = new BigDecimal(String.valueOf(__agument.get("dividend")));
		if(__dividend.compareTo(new BigDecimal("100"))==1
				||__dividend.compareTo(new BigDecimal("0"))==-1){
			throw new LogicTransactionRollBackException("分红设置区间为0%-100%");
		}
		if(__dividend.compareTo(__agent.getDividend().multiply(new BigDecimal("100")))==1){
			throw new LogicTransactionRollBackException("分红设置区间不能大于"+__agent.getDividend());
		}
		return __dividend.divide(new BigDecimal("100"));
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
	
	
	@Override
	public LoggerManager getLogger() {
		return LOG;
	}

}
