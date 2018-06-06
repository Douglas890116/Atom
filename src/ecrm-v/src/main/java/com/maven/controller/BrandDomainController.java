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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.annotation.OperationLog;
import com.maven.cache.SystemCache;
import com.maven.constant.Constant;
import com.maven.entity.BrandDomain;
import com.maven.entity.BrandDomain.Enum_copyright;
import com.maven.entity.BrandDomain.Enum_domaintype;
import com.maven.entity.BrandDomain.Enum_linkstatus;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.EnterpriseWebview;
import com.maven.entity.Game;
import com.maven.entity.GameCategory;
import com.maven.entity.UserLogs;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.BrandDomainService;
import com.maven.service.EmployeeGamecataloyBonusService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseOperatingBrandService;
import com.maven.service.EnterpriseService;
import com.maven.service.EnterpriseWebviewService;
import com.maven.service.GameService;
import com.maven.service.UserLogsService;
import com.maven.util.BeanToMapUtil;
import com.maven.util.RandomString;

@RequestMapping("/registerLink")
@Controller
public class BrandDomainController extends BaseController {
	
	private final static String DOMAIN_CUSTOMER ="1";
	private final static String DOMAIN_DEFUALT ="2";
	private final static String DOMAIN_ENTERPRISE ="3";
	
	private static LoggerManager log = LoggerManager.getLogger(
			BrandDomainController.class.getName(), OutputManager.LOG_BRANDDOMAIN);
	
	/** 游戏 */
	@Autowired
	private GameService gameService;
	/**  注册链接 */
	@Autowired
	private BrandDomainService brandDomainService;
	/** 企业品牌 */
	@Autowired
	private EnterpriseOperatingBrandService enterpriseOperatingBrandService;
	/** 企业  */
	@Autowired
	private EnterpriseService enterpriseService;
	/** 企业员工  */
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	/** 会员游戏返点  */
	@Autowired
	private EmployeeGamecataloyBonusService employeeGamecataloyBonusService;
	/** 企业站点模板 */
	@Autowired
	private EnterpriseWebviewService  enterpriseWebviewService;
	@Autowired
	private UserLogsService userLogsService;

	@RequestMapping("/mianDomainSetting")
	public String mianDomainSetting(HttpServletRequest request,HttpSession session, Model model) {
		return "/registerlink/mianDomainSetting";
		/*if(super.validateIsEmployee(session, model)){
			return "/registerlink/mianDomainSetting";
		}else{
			return Constant.PAGE_IDENTITY_NO_MATCH;
		}*/
	}

	@RequestMapping("/usersiteindex")
	public String userSiteDomain(HttpSession session, Model model) {
		return "/registerlink/usersiteindex";
	}
	
	@RequestMapping("/agementSiteIndex")
	public String agementSiteDomain(HttpSession session, Model model) {
		return "/registerlink/agentsiteindex";
	}

	@RequestMapping("/registerLinkIndex")
	public String registerLinkIndex(HttpSession session, Model model) {
		return "/registerlink/registerLinkIndex";
	}
	
	/**
	 * 跳转到会员站点编辑页面
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/userSiteUpdate")
	public String userSiteUpdate(HttpSession session, Model model, HttpServletRequest request) {
		try {
			
			Map<String, Object> paramObj = getRequestParamters(request);
			String domaincode = (String) paramObj.get("sign");
			if (super.decodeSign(domaincode, session.getId())) {
				int code = Integer.parseInt(domaincode.split("_")[1].split("\\|")[0]);
				
				List<EnterpriseOperatingBrand> brands ;
				EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
				List<Game> games = gameService.takeEnterpriseGames(ee.getEnterprisecode());
				Map<String, BigDecimal> bonus = employeeGamecataloyBonusService.takeEmployeeGameCategoryBonusMap(ee.getEmployeecode());
				if(StringUtils.isBlank(ee.getBrandcode())){
					brands = enterpriseOperatingBrandService.getEnterpriseBrand(ee.getEnterprisecode());
				}else{
					brands = new ArrayList<EnterpriseOperatingBrand>();
					brands.add(enterpriseOperatingBrandService.takeBrandByPrimaryKey(ee.getBrandcode()));
				}
				model.addAttribute("brands", brands);
				model.addAttribute("bonus", bonus);
				model.addAttribute("games", games);
				
				BrandDomain registerDomain = new BrandDomain();
				registerDomain.setDomaincode(code);
				registerDomain = brandDomainService.selectFirst(registerDomain);
				
				Map<String, BigDecimal> bonusItem = new HashMap<String, BigDecimal>();
				String[] strs = registerDomain.getBonus().split("\\|");
				for (String string : strs) {
					if(string != null && !string.equals("")) {
						String[] item = string.split(":");
						bonusItem.put(item[0], new BigDecimal(item[1]));
					}
				}
				model.addAttribute("registerDomain", registerDomain);
				model.addAttribute("bonusItem", bonusItem);
				
				return "/registerlink/usersiteupdate";
				
			} else {
				return Constant.PAGE_DECODEREFUSE;
			}
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	/**
	 * 保存会员站点参数
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/userSiteSave")
	@ResponseBody
	public Map<String, Object> userSiteSave(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = super.getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			// 调用保存方法
			int code = Integer.valueOf(request.getParameter("code"));
			String domainlink = request.getParameter("domainlink");
			BrandDomain registerDomain = new BrandDomain();
			registerDomain.setDomaincode(code);
			registerDomain.setDomainlink(domainlink);
			registerDomain.setBonus(bonusVerify(ee, object));//
			brandDomainService.tc_updateRegisterLink(registerDomain);
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.站点管理业务, "保存会员站点信息", ee.getLoginaccount(), null));
			
			return super.packJSON(Constant.BooleanByte.YES, "编辑成功");
		} catch (LogicTransactionRollBackException e) {
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "编辑失败："+e.getMessage());
		}
	}
	
	
	/**
	 * 跳转到代理站点编辑页面
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/agentSiteUpdate")
	public String agentSiteUpdate(HttpSession session, Model model, HttpServletRequest request) {
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			String domaincode = (String) paramObj.get("sign");
			if (super.decodeSign(domaincode, session.getId())) {
				int code = Integer.parseInt(domaincode.split("_")[1].split("\\|")[0]);
				
				List<Game> games = gameService.takeEnterpriseGames(ee.getEnterprisecode());
				Map<String, BigDecimal> bonus = employeeGamecataloyBonusService.takeEmployeeGameCategoryBonusMap(ee.getEmployeecode());
				List<EnterpriseOperatingBrand> brands = new ArrayList<EnterpriseOperatingBrand>();
				if(StringUtils.isBlank(ee.getBrandcode())){
					EnterpriseOperatingBrand allbrand = new EnterpriseOperatingBrand();
					allbrand.setBrandname("所有品牌");
					allbrand.setBrandcode("");
					brands.add(allbrand);
					brands.addAll(enterpriseOperatingBrandService.getEnterpriseBrand(ee.getEnterprisecode()));
				}else{
					brands.add(enterpriseOperatingBrandService.takeBrandByPrimaryKey(ee.getBrandcode()));
				}
				model.addAttribute("brands", brands);
				model.addAttribute("bonus", bonus);
				model.addAttribute("games", games);
				
				BrandDomain registerDomain = new BrandDomain();
				registerDomain.setDomaincode(code);
				registerDomain = brandDomainService.selectFirst(registerDomain);
				
				Map<String, BigDecimal> bonusItem = new HashMap<String, BigDecimal>();
				String[] strs = registerDomain.getBonus().split("\\|");
				for (String string : strs) {
					if(string != null && !string.equals("")) {
						String[] item = string.split(":");
						bonusItem.put(item[0], new BigDecimal(item[1]));
					}
				}
				model.addAttribute("registerDomain", registerDomain);
				model.addAttribute("bonusItem", bonusItem);
				
				List<EnterpriseWebview> templates = enterpriseWebviewService.select(new EnterpriseWebview(ee.getEnterprisecode(), EnterpriseWebview.Enum_sitetype.代理站点.value));
				model.addAttribute("templates", templates);
				return "/registerlink/agentsiteupdate";
			} else {
				return Constant.PAGE_DECODEREFUSE;
			}
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	/**
	 * 保存代理站点参数
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/agentSiteSave")
	@ResponseBody
	public Map<String, Object> agentSiteSave(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = super.getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			// 调用保存方法
			int code = Integer.valueOf(request.getParameter("code"));
			String domainlink = request.getParameter("domainlink");
			String webtemplatecode = request.getParameter("webtemplatecode");
			BrandDomain registerDomain = new BrandDomain();
			registerDomain.setDomaincode(code);
			registerDomain.setDomainlink(domainlink);
			registerDomain.setBonus(bonusVerify(ee, object));
			registerDomain.setWebtemplatecode(webtemplatecode);
			registerDomain.setDividend(new BigDecimal(request.getParameter("dividend")).divide(new BigDecimal(100)));
			registerDomain.setShare(new BigDecimal(request.getParameter("share")).divide(new BigDecimal(100)));
			brandDomainService.tc_updateRegisterLink(registerDomain);
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.站点管理业务, "保存代理站点信息", ee.getLoginaccount(), null));
			
			return super.packJSON(Constant.BooleanByte.YES, "编辑成功");
		} catch (LogicTransactionRollBackException e) {
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "编辑失败："+e.getMessage());
		}
	}
	

	/**
	 * 获取企业绑定的会员站点域名
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/takeMainDomain")
	@ResponseBody
	public Map<String, Object> takeMainDomain(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = super.getRequestParamters(request);
			object.put("domaintypes",new Byte[]{Enum_domaintype.企业域名_代理站点.value.byteValue(),Enum_domaintype.企业域名_会员站点.value.byteValue()});
			List<BrandDomain> domains = brandDomainService.selectAll(object);
			int count = brandDomainService.selectAllCount(object);
			for (BrandDomain brandDomain : domains) {
				brandDomain.setSign(super.encryptString(String.valueOf(brandDomain.getDomaincode()), session.getId()));
			}
			return super.formatPagaMap(domains, count);
		} catch (LogicTransactionRollBackException e) {
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 获取用户的推广域名
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value={"/takeUserDBODomain","/takeAgentDBODomain"})
	@ResponseBody
	public Map<String, Object> takeUserDBODomain(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			String requestUri = request.getRequestURI().replaceAll("/+", "/");
			Map<String, Object> object = super.getRequestParamters(request);
			object.put("enterprisecode",ee.getEnterprisecode());
			object.put("employeecode",ee.getEmployeecode());
			if(requestUri.indexOf("/takeUserDBODomain")>0){
				object.put("domaintypes",new Byte[]{Enum_domaintype.会员站点.value.byteValue(),Enum_domaintype.企业域名_会员站点.value.byteValue()});
			}else{
				object.put("domaintypes",new Byte[]{Enum_domaintype.代理站点.value.byteValue(),Enum_domaintype.企业域名_代理站点.value.byteValue()});
			}
			List<BrandDomain> domains = brandDomainService.selectAll(object);
			int count = brandDomainService.selectAllCount(object);
			for (BrandDomain __db : domains) {
				__db.setSign(super.encryptString(__db.getDomaincode()+"|"+__db.getDomaintype(), session.getId()));
			}
			return super.formatPagaMap(domains, count);
		} catch (LogicTransactionRollBackException e) {
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 获取企业可用的会员站点域名
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value={"/takeUSAvailableDomain","/takeASAvailableDomain"})
	@ResponseBody
	public List<String> takeEAvailableDomain(HttpServletRequest request, HttpSession session) {
		List<String> __ds = new ArrayList<String>();
		try {
			EnterpriseEmployee __ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			String requestUri = request.getRequestURI().replaceAll("/+", "/");
			BrandDomain __brandDomain = new BrandDomain();
			__brandDomain.setEnterprisecode(__ee.getEnterprisecode());
			if(requestUri.indexOf("/takeUSAvailableDomain")>0){
				__brandDomain.setDomaintype(Enum_domaintype.企业域名_会员站点.value.byteValue());
			}else{
				__brandDomain.setDomaintype(Enum_domaintype.企业域名_代理站点.value.byteValue());
			}
			__brandDomain.setCopyright(Byte.valueOf(Enum_copyright.公共.value));
			__brandDomain.setLinkstatus(Enum_linkstatus.启用.value);
			List<BrandDomain> __domains = brandDomainService.select(__brandDomain);
			for (BrandDomain __bd : __domains) {
				__ds.add(__bd.getDomainlink());
			}
			return __ds;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return __ds;
		}
	}
	
	/**
	 * 获取企业默认域名的二级域名
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/takeUDefualtDomain")
	@ResponseBody
	public String takeUDefualtDomain(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee __ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			BrandDomain __defualt = brandDomainService.takeEDefualtUSiteDomain(__ee.getEnterprisecode());
			if(__defualt==null){
				return "";
			}
			String priex = brandDomainService.takeDefualtDomainPrefix();
			return priex+"."+__defualt.getDomainlink();
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return "";
		}
	}
	
	/**
	 * 获取企业默认域名的二级域名
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/takeADefualtDomain")
	@ResponseBody
	public String takeADefualtDomain(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee __ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			BrandDomain __defualt = brandDomainService.takeEDefualtASiteDomain(__ee.getEnterprisecode());
			String priex = brandDomainService.takeDefualtDomainPrefix();
			if(__defualt==null){
				return "";
			}
			return priex+"."+__defualt.getDomainlink();
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return "";
		}
	}
	
	
	/**
	 * 获取用户的代理注册链接
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/takeAgentRegisterLink")
	@ResponseBody
	public Map<String, Object> takeAgentRegisterLink(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> object = super.getRequestParamters(request);
			object.put("enterprisecode", ee.getEnterprisecode());
			object.put("employeecode",ee.getEmployeecode());
			object.put("domaintype",Enum_domaintype.代理注册链接.value.byteValue());
			List<BrandDomain> domains = brandDomainService.selectAll(object);
			int count = brandDomainService.selectAllCount(object);
			for (BrandDomain brandDomain : domains) {
				brandDomain.setDomainlink(super.getBasePath()+"/res/register?sign="+brandDomain.getDomainlink());
				brandDomain.setSign(super.encryptString(brandDomain.getDomaincode()+"|"+brandDomain.getDomaintype(), session.getId()));
			}
			return super.formatPagaMap(domains, count);
		} catch (LogicTransactionRollBackException e) {
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 跳转到会员站点注册
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/userSiteAdd")
	public String userSiteAdd(HttpSession session, Model model) {
		try {
			List<EnterpriseOperatingBrand> brands ;
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			List<Game> games = gameService.takeEnterpriseGames(ee.getEnterprisecode());
			Map<String, BigDecimal> bonus = employeeGamecataloyBonusService.takeEmployeeGameCategoryBonusMap(ee.getEmployeecode());
			if(StringUtils.isBlank(ee.getBrandcode())){
				brands = enterpriseOperatingBrandService.getEnterpriseBrand(ee.getEnterprisecode());
			}else{
				brands = new ArrayList<EnterpriseOperatingBrand>();
				brands.add(enterpriseOperatingBrandService.takeBrandByPrimaryKey(ee.getBrandcode()));
			}
			model.addAttribute("brands", brands);
			model.addAttribute("bonus", bonus);
			model.addAttribute("games", games);
			return "/registerlink/usersiteadd";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	/**
	 * 跳转到会员站点注册
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/agentSiteAdd")
	public String agentSiteAdd(HttpSession session, Model model) {
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			List<Game> games = gameService.takeEnterpriseGames(ee.getEnterprisecode());
			Map<String, BigDecimal> bonus = employeeGamecataloyBonusService.takeEmployeeGameCategoryBonusMap(ee.getEmployeecode());
			List<EnterpriseOperatingBrand> brands = new ArrayList<EnterpriseOperatingBrand>();
			if(StringUtils.isBlank(ee.getBrandcode())){
				EnterpriseOperatingBrand allbrand = new EnterpriseOperatingBrand();
				allbrand.setBrandname("所有品牌");
				allbrand.setBrandcode("");
				brands.add(allbrand);
				brands.addAll(enterpriseOperatingBrandService.getEnterpriseBrand(ee.getEnterprisecode()));
			}else{
				brands = new ArrayList<EnterpriseOperatingBrand>();
				brands.add(enterpriseOperatingBrandService.takeBrandByPrimaryKey(ee.getBrandcode()));
			}
			List<EnterpriseWebview> templates = enterpriseWebviewService.select(new EnterpriseWebview(ee.getEnterprisecode(), EnterpriseWebview.Enum_sitetype.代理站点.value));
			model.addAttribute("brands", brands);
			model.addAttribute("bonus", bonus);
			model.addAttribute("games", games);
			model.addAttribute("templates", templates);
			return "/registerlink/agentsiteadd";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}

	/**
	 * 跳转代理注册链接
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/registerLinkAdd")
	public String registerLinkAdd(HttpSession session, Model model) {
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			List<Game> games = gameService.takeEnterpriseGames(ee.getEnterprisecode());
			Map<String, BigDecimal> bonus = employeeGamecataloyBonusService
					.takeEmployeeGameCategoryBonusMap(ee.getEmployeecode());
			List<EnterpriseOperatingBrand> brands = new ArrayList<EnterpriseOperatingBrand>();
			if(StringUtils.isBlank(ee.getBrandcode())){
				EnterpriseOperatingBrand allbrand = new EnterpriseOperatingBrand();
				allbrand.setBrandname("所有品牌");
				allbrand.setBrandcode("");
				brands.add(allbrand);
				brands.addAll(enterpriseOperatingBrandService.getEnterpriseBrand(ee.getEnterprisecode()));
			}else{
				brands.add(enterpriseOperatingBrandService.takeBrandByPrimaryKey(ee.getBrandcode()));
			}
			model.addAttribute("brands", brands);
			model.addAttribute("bonus", bonus);
			model.addAttribute("games", games);
			return "/registerlink/registerLinkAdd";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}

	/**
	 * 添加会员站点域名
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/addMainDomain")
	@ResponseBody
	@OperationLog(OparetionDescription.ENTERPRISEOPERATINGBRAND_DOMAINBIND)
	public Map<String, Object> addEnterpriseDomain(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = super.getRequestParamters(request);
			BrandDomain domain = BeanToMapUtil.convertMap(object, BrandDomain.class);
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			domain.setEnterprisecode(ee.getEnterprisecode());
			domain.setBrandcode("");
			domain.setEmployeecode("");
			domain.setParentemployeecode("");
			domain.setEmployeetype("");
			domain.setDividend(new BigDecimal(0.0));
			domain.setShare(new BigDecimal(0.0));
			domain.setBonus("...");
			domain.setCopyright(Byte.valueOf(Enum_copyright.公共.value));
			domain.setIsdefualt(Constant.BooleanByte.NO.byteValue());
			domain.setLinkstatus(String.valueOf(BrandDomain.Enum_linkstatus.启用.value));
			domain.setDatastatus(String.valueOf(Constant.Enum_DataStatus.正常.value));
			domain.setDomainlink(domain.getDomainlink().toLowerCase());//转小写
			brandDomainService.tc_save(domain);
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.站点管理业务, "注册会员站点域名:"+domain.getLinkdomain(), ee.getLoginaccount(), null));
			
			
			return super.packJSON(Constant.BooleanByte.YES, "域名绑定成功");
		} catch (LogicTransactionRollBackException e) {
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "主域名绑定失败,请稍后尝试");
		}

	}
	
	/**
	 * 注册会员站点域名
	 * @param request
	 * @param session
	 * @return Map<String,Object>
	 */
	@RequestMapping("/addUserSiteDomain")
	@ResponseBody
	public Map<String, Object> addUserSiteDomain(HttpServletRequest request, HttpSession session) {
		try {
			// 获取当前登录用户的信息
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> object = super.getRequestParamters(request);
			String domainclass = String.valueOf(object.get("domainclass")==null?"":object.get("domainclass"));
			String domainlink = "";
			if(StringUtils.isNotBlank(domainclass)&&domainclass.equals(DOMAIN_CUSTOMER)){
				domainlink = String.valueOf(object.get("customerdomain"));
			}else if(StringUtils.isNotBlank(domainclass)&&domainclass.equals(DOMAIN_DEFUALT)){
				domainlink = String.valueOf(object.get("defualtdomain"));
			}else if(StringUtils.isNotBlank(domainclass)&&domainclass.equals(DOMAIN_ENTERPRISE)){
				domainlink = String.valueOf(object.get("enterprisedomain"));
			}else{
				return super.packJSON(Constant.BooleanByte.NO, "域名类型不能为空");
			}
			if(StringUtils.isBlank(domainlink)||domainlink.equals("null")||domainlink.indexOf(".")<0){
				return super.packJSON(Constant.BooleanByte.NO, "请填写正确的域名");
			}
			
			if(domainclass.equals(DOMAIN_CUSTOMER)||domainclass.equals(DOMAIN_DEFUALT)){
				BrandDomain registerDomain  = new BrandDomain();
				if(domainclass.equals(DOMAIN_DEFUALT)){
					BrandDomain __defualt = brandDomainService.takeEDefualtUSiteDomain(ee.getEnterprisecode());
					if(__defualt==null||domainlink.indexOf(__defualt.getDomainlink())<0){
						return super.packJSON(Constant.BooleanByte.NO, "注册失败,默认域名已变更");
					}
					registerDomain.setLinkdomain(__defualt.getDomaincode());
				}
				String brandcode = object.get("brandcode")==null?null:String.valueOf(object.get("brandcode"));
				registerDomain.setEnterprisecode(ee.getEnterprisecode());
				registerDomain.setBrandcode(brandcode);
				registerDomain.setEmployeecode(ee.getEmployeecode());
				registerDomain.setParentemployeecode(ee.getParentemployeecode());
				registerDomain.setEmployeetype(EnterpriseEmployeeType.Type.会员.value);
				registerDomain.setDividend(new BigDecimal(0.0));
				registerDomain.setShare(new BigDecimal(0.0));
				registerDomain.setBonus(bonusVerify(ee, object));//
				registerDomain.setDomainlink(domainlink);
				registerDomain.setDomaintype(BrandDomain.Enum_domaintype.会员站点.value.byteValue());
				registerDomain.setCopyright(Byte.valueOf(Enum_copyright.私有.value));
				registerDomain.setIsdefualt(Constant.BooleanByte.NO.byteValue());
				registerDomain.setLinkstatus(String.valueOf(BrandDomain.Enum_linkstatus.启用.value));
				registerDomain.setDatastatus(String.valueOf(Constant.Enum_DataStatus.正常.value));
				registerDomain.setDomainlink(registerDomain.getDomainlink().toLowerCase());//转小写
				// 调用保存方法
				brandDomainService.tc_save(registerDomain);
			}else if(domainclass.equals(DOMAIN_ENTERPRISE)){
				BrandDomain __bde = brandDomainService.queryByDomainLink(domainlink);
				if(__bde==null){
					return super.packJSON(Constant.BooleanByte.NO, "注册失败,域名不存在");
				}
				if(__bde.getCopyright().equals(Byte.valueOf(Enum_copyright.私有.value))){
					return super.packJSON(Constant.BooleanByte.NO, "注册失败,域名已被使用");
				}
				if(!__bde.getEnterprisecode().equals(ee.getEnterprisecode())){
					return super.packJSON(Constant.BooleanByte.NO, "注册失败,企业未绑定该域名");
				}
				__bde.setBrandcode(String.valueOf(object.get("brandcode")));
				__bde.setEmployeecode(ee.getEmployeecode());
				__bde.setParentemployeecode(ee.getParentemployeecode());
				__bde.setEmployeetype(EnterpriseEmployeeType.Type.会员.value);
				__bde.setDividend(new BigDecimal(0.0));
				__bde.setShare(new BigDecimal(0.0));
				__bde.setBonus(bonusVerify(ee, object));
				__bde.setCopyright(Byte.valueOf(Enum_copyright.私有.value));
				__bde.setLinkstatus(String.valueOf(BrandDomain.Enum_linkstatus.启用.value));
				__bde.setDomainlink(__bde.getDomainlink().toLowerCase());//转小写
				brandDomainService.tc_updateRegisterLink(__bde);
			}
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.站点管理业务, "注册代理站点域名:"+domainlink, ee.getLoginaccount(), null));
			
			
			return super.packJSON(Constant.BooleanByte.YES, "域名注册成功");
		} catch (LogicTransactionRollBackException e) {
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "域名注册失败");
		}
	}
	
	
	/**
	 * 注册代理推广域名
	 * @param request
	 * @param session
	 * @return Map<String,Object>
	 */
	@RequestMapping("/addAgentSiteDomain")
	@ResponseBody
	public Map<String, Object> addAgentSiteDomain(HttpServletRequest request, HttpSession session) {
		try {
			// 获取当前登录用户的信息
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> object = super.getRequestParamters(request);
			String domainclass = String.valueOf(object.get("domainclass")==null?"":object.get("domainclass"));
			String domainlink = "";
			if(StringUtils.isNotBlank(domainclass)&&domainclass.equals(DOMAIN_CUSTOMER)){
				domainlink = String.valueOf(object.get("customerdomain"));
			}else if(StringUtils.isNotBlank(domainclass)&&domainclass.equals(DOMAIN_DEFUALT)){
				domainlink = String.valueOf(object.get("defualtdomain"));
			}else if(StringUtils.isNotBlank(domainclass)&&domainclass.equals(DOMAIN_ENTERPRISE)){
				domainlink = String.valueOf(object.get("enterprisedomain"));
			}else{
				return super.packJSON(Constant.BooleanByte.NO, "域名类型不能为空");
			}
			if(StringUtils.isBlank(domainlink)||domainlink.equals("null")||domainlink.indexOf(".")<0){
				return super.packJSON(Constant.BooleanByte.NO, "请填写正确的域名");
			}
			
			String brandcode = object.get("brandcode")==null?null:String.valueOf(object.get("brandcode"));
			String webtemplatecode = object.get("webtemplatecode")==null?null:String.valueOf(object.get("webtemplatecode"));
			if(domainclass.equals(DOMAIN_CUSTOMER)||domainclass.equals(DOMAIN_DEFUALT)){
				BrandDomain registerDomain  = BrandDomain.class.newInstance();
				if(domainclass.equals(DOMAIN_DEFUALT)){
					BrandDomain __defualt = brandDomainService.takeEDefualtASiteDomain(ee.getEnterprisecode());
					if(__defualt==null||domainlink.indexOf(__defualt.getDomainlink())<0){
						return super.packJSON(Constant.BooleanByte.NO, "注册失败,默认域名已变更");
					}
					registerDomain.setLinkdomain(__defualt.getDomaincode());
				}
				registerDomain.setEnterprisecode(ee.getEnterprisecode());
				registerDomain.setBrandcode(brandcode);
				registerDomain.setEmployeecode(ee.getEmployeecode());
				registerDomain.setParentemployeecode(ee.getParentemployeecode());
				registerDomain.setEmployeetype(ee.getEmployeetypecode());
				String dividend = object.get("dividend")==null?"0":String.valueOf(object.get("dividend"));
				registerDomain.setDividend(new BigDecimal(dividend).divide(new BigDecimal("100")));
				String share = object.get("share") == null? "0":String.valueOf(object.get("share"));
				registerDomain.setShare(new BigDecimal(share).divide(new BigDecimal("100")));
				registerDomain.setBonus(bonusVerify(ee, object));
				
				registerDomain.setDomainlink(domainlink);
				registerDomain.setDomaintype(BrandDomain.Enum_domaintype.代理站点.value.byteValue());
				registerDomain.setCopyright(Byte.valueOf(Enum_copyright.私有.value));
				registerDomain.setIsdefualt(Constant.BooleanByte.NO.byteValue());
				registerDomain.setLinkstatus(String.valueOf(BrandDomain.Enum_linkstatus.启用.value));
				registerDomain.setDatastatus(String.valueOf(Constant.Enum_DataStatus.正常.value));
				registerDomain.setWebtemplatecode(webtemplatecode);
				registerDomain.setDomainlink(registerDomain.getDomainlink().toLowerCase());//转小写
				// 调用保存方法
				brandDomainService.tc_save(registerDomain);
			}else if(domainclass.equals(DOMAIN_ENTERPRISE)){
				BrandDomain __bde = brandDomainService.queryByDomainLink(domainlink);
				if(__bde==null){
					return super.packJSON(Constant.BooleanByte.NO, "注册失败,域名不存在");
				}
				if(__bde.getCopyright().equals(Byte.valueOf(Enum_copyright.私有.value))){
					return super.packJSON(Constant.BooleanByte.NO, "注册失败,域名已被使用");
				}
				if(!__bde.getEnterprisecode().equals(ee.getEnterprisecode())){
					return super.packJSON(Constant.BooleanByte.NO, "注册失败,企业未绑定该域名");
				}
				__bde.setBrandcode(brandcode);
				__bde.setEmployeecode(ee.getEmployeecode());
				__bde.setParentemployeecode(ee.getParentemployeecode());
				__bde.setEmployeetype(ee.getEmployeetypecode());
				__bde.setDividend(new BigDecimal(0.0));
				__bde.setShare(new BigDecimal(0.0));
				__bde.setBonus(bonusVerify(ee, object));
				__bde.setCopyright(Byte.valueOf(Enum_copyright.私有.value));
				__bde.setLinkstatus(String.valueOf(BrandDomain.Enum_linkstatus.启用.value));
				__bde.setWebtemplatecode(webtemplatecode);
				__bde.setDomainlink(__bde.getDomainlink().toLowerCase());//转小写
				brandDomainService.tc_updateRegisterLink(__bde);
			}
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.站点管理业务, "注册代理推广域名:"+domainlink, ee.getLoginaccount(), null));
			
			return super.packJSON(Constant.BooleanByte.YES, "域名注册成功");
		} catch (LogicTransactionRollBackException e) {
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "域名注册失败");
		}
	}

	/**
	 * 生成注册链接
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/addRegisterLink")
	@ResponseBody
	public Map<String, Object> addRegisterLink(HttpServletRequest request, HttpSession session) {
		try {
			// 获取当前登录用户的信息
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> object = super.getRequestParamters(request);
			BrandDomain register = new BrandDomain();
			register.setEnterprisecode(ee.getEnterprisecode());
			register.setDomainlink(RandomString.UUID().toLowerCase());
			if(StringUtils.isBlank(ee.getBrandcode())){
				register.setBrandcode(String.valueOf(object.get("brandcode")));
			}else{
				register.setBrandcode(ee.getBrandcode());
			}
			register.setEmployeecode(ee.getEmployeecode());
			register.setParentemployeecode(ee.getParentemployeecode());
			register.setEmployeetype(ee.getEmployeetypecode());
			register.setDividend(new BigDecimal(String.valueOf(object.get("dividend"))).divide(new BigDecimal("100")));
			register.setShare(new BigDecimal(String.valueOf(object.get("share"))).divide(new BigDecimal("100")));
			register.setBonus(bonusVerify(ee, object));//
			register.setDomaintype(BrandDomain.Enum_domaintype.代理注册链接.value.byteValue());
			register.setCopyright(Byte.valueOf(Enum_copyright.私有.value));
			register.setIsdefualt(Constant.BooleanByte.NO.byteValue());
			register.setLinkstatus(String.valueOf(BrandDomain.Enum_linkstatus.启用.value));
			register.setDatastatus(String.valueOf(Constant.Enum_DataStatus.正常.value));
			dividendShareVerify(ee, register.getDividend(), register.getShare());
			// 调用保存方法
			brandDomainService.tc_save(register);
			
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.站点管理业务, "生成注册链接:"+register.getLinkdomain(), ee.getLoginaccount(), null));
			
			
			return super.packJSON(Constant.BooleanByte.YES, getBasePath()+"/res/register?sign="+register.getDomainlink());
		} catch (LogicTransactionRollBackException e) {
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "操作失败，请稍后尝试");
		}
	}

	/**
	 * 启用/禁用
	 * 
	 * @return
	 */
	@RequestMapping("/settingDomainStatus")
	@ResponseBody
	public Map<String, Object> updateDomainSate(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> paramObj = getRequestParamters(request);
			String domaincode = (String) paramObj.get("sign");
			if (super.decodeSign(domaincode, session.getId())) {
				int code = Integer.parseInt(domaincode.split("_")[1].split("\\|")[0]);
				String linkStatus = (String) paramObj.get("linkstatus");
				linkStatus = linkStatus.equals(BrandDomain.Enum_linkstatus.禁用.value)
						? BrandDomain.Enum_linkstatus.启用.value : BrandDomain.Enum_linkstatus.禁用.value;
				BrandDomain registerDomain = new BrandDomain();
				registerDomain.setDomaincode(code);
				registerDomain.setLinkstatus(linkStatus);
				brandDomainService.tc_updateRegisterLink(registerDomain);
				
				return super.packJSON(Constant.BooleanByte.YES, "操作成功");
			} else {
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (LogicTransactionRollBackException e) {
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}

	/**
	 * 设置默认域名
	 * 
	 * @return
	 */
	@RequestMapping("/settingDefualtDomain")
	@ResponseBody
	public Map<String, Object> settingDefualtDomain(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> aguments = getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			String sign = (String) aguments.get("sign");
			String domaintype = String.valueOf(aguments.get("domaintype"));
			if(StringUtils.isBlank(sign)||StringUtils.isBlank(domaintype))
				return super.packJSON(Constant.BooleanByte.NO, "请求参数错误");
			if (super.decodeSign(sign, session.getId())) {
				String domaincode = sign.split("_")[1];
				BrandDomain defualtDomain = new BrandDomain();
				defualtDomain.setEnterprisecode(ee.getEnterprisecode());
				defualtDomain.setDomaincode(Integer.parseInt(domaincode));
				defualtDomain.setDomaintype(Byte.valueOf(domaintype));
				brandDomainService.tc_SetttingDefualtDomain(defualtDomain);
				
				
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.站点管理业务, "设置默认域名:"+domaincode, ee.getLoginaccount(), null));
				
				
				return super.packJSON(Constant.BooleanByte.YES, "操作成功");
			} else {
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "操作失败,请稍后尝试");
		}
	}

	/**
	 * 删除主域名
	 * 
	 * @return
	 */
	@RequestMapping("/deleteMainDomain")
	@ResponseBody
	public Map<String, Object> deleteMainDomain(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = super.getRequestParamters(request);
			String domaincode = String.valueOf(object.get("sign"));
			if (super.decodeSign(domaincode, session.getId())) {
				int code = Integer.parseInt(domaincode.split("_")[1]);
				EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
				BrandDomain domain = new BrandDomain();
				domain.setDomaincode(code);
				domain.setEnterprisecode(ee.getEnterprisecode());
				brandDomainService.tc_DeleteMainDomain(domain);
				
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.站点管理业务, "删除域名:"+code, ee.getLoginaccount(), null));
				
				return super.packJSON(Constant.BooleanByte.YES, "删除成功");
			} else {
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (LogicTransactionRollBackException e) {
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "删除失败,请稍后尝试");
		}
	}

	/**
	 * 删除代理使用域名 
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/deleteUserDBODomain")
	@ResponseBody
	public Map<String, Object> deleteUserDBODomain(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> paramObj = getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			String sign = (String) paramObj.get("sign");
			if (this.decodeSign(sign, session.getId())) {
				String[] __emessage = sign.split("_")[1].split("\\|");
				if(__emessage.length<2) 
					super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
				BrandDomain registerDomain = new BrandDomain();
				registerDomain.setDomaincode(Integer.parseInt(__emessage[0]));
				if(__emessage[1].equals(String.valueOf(Enum_domaintype.企业域名_会员站点.value))
						||__emessage[1].equals(String.valueOf(Enum_domaintype.企业域名_代理站点.value))){
					registerDomain.setBrandcode("");
					registerDomain.setEmployeecode("");
					registerDomain.setParentemployeecode("");
					registerDomain.setDividend(new BigDecimal(0));
					registerDomain.setShare(new BigDecimal(0));
					registerDomain.setEmployeetype("");
					registerDomain.setBonus("...");
					registerDomain.setCopyright(Byte.valueOf(Enum_copyright.公共.value));
				}else{
					registerDomain.setDatastatus(String.valueOf(Constant.Enum_DataStatus.删除.value));
				}
				brandDomainService.tc_updateRegisterLink(registerDomain);
				
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.站点管理业务, "删除代理使用域名:"+__emessage[0], ee.getLoginaccount(), null));
				
				
				return super.packJSON(Constant.BooleanByte.YES, "删除成功");
			} else {
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "删除失败，请稍后尝试");
		}
	}

	/**
	 * 删除企业域名
	 * 
	 * @param request
	 */
	@RequestMapping("/deleteSelect")
	@ResponseBody
	public Map<String, Object> deleteSelectDomain(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			String temp = request.getParameter("sign");
			String[] array = temp.split(",");
			if(array.length==0) super.packJSON(Constant.BooleanByte.NO, "请选择要删除的域名");
			List<BrandDomain> __deleteObject = new ArrayList<BrandDomain>();
			for (String __s : array) {
				if (super.decodeSign(__s, session.getId())) {
					String[] __emessage = __s.split("_")[1].split("\\|");
					if(__emessage.length<2) 
						super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
					BrandDomain registerDomain = new BrandDomain();
					registerDomain.setDomaincode(Integer.parseInt(__emessage[0]));
					if(__emessage[1].equals(String.valueOf(Enum_domaintype.企业域名_会员站点.value))
							||__emessage[1].equals(String.valueOf(Enum_domaintype.企业域名_代理站点.value))){
						registerDomain.setBrandcode("");
						registerDomain.setEmployeecode("");
						registerDomain.setParentemployeecode("");
						registerDomain.setDividend(new BigDecimal(0));
						registerDomain.setShare(new BigDecimal(0));
						registerDomain.setBonus("...");
						registerDomain.setCopyright(Byte.valueOf(Enum_copyright.公共.value));
					}else{
						registerDomain.setDatastatus(String.valueOf(Constant.Enum_DataStatus.删除.value));
					}
					__deleteObject.add(registerDomain);
					
					userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
						     UserLogs.Enum_operatype.站点管理业务, "删除企业域名:"+__emessage[0], ee.getLoginaccount(), null));
					
				} else {
					return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
				}
				
			}
			brandDomainService.tc_BatchLogicDelete(__deleteObject);
			
			
			
			
			return super.packJSON(Constant.BooleanByte.YES, "删除成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "删除失败，请稍后尝试");
		}
	}

	/**
	 * 主域名唯一性验证(BUI自动验证使用)
	 */
	@RequestMapping(value = "/validateDomain", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String employeeIsExist(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = super.getRequestParamters(request);
			String domainLink = String.valueOf(object.get("domainlink"));
			int count = brandDomainService.queryDetectionDomainLinkExit(domainLink);
			if (count != 0) {
				return "该域名已被占用,请使用其他域名";
			} else {
				return "";
			}
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return "参数错误";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return "网络异常";
		}
	}
	
	/**
	 * 主域名唯一性验证(BUI自动验证使用)
	 */
	@RequestMapping(value = "/validateExpandDomain", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String expandCustomerDomainExit(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = super.getRequestParamters(request);
			String domainLink = String.valueOf(object.get("customerdomain"));
			int count = brandDomainService.queryDetectionDomainLinkExit(domainLink);
			if (count != 0) {
				return "该域名已被占用,请使用其他域名";
			} else {
				return "";
			}
		} catch (ArgumentValidationException e) {
			log.Error(e.getMessage(), e);
			return "参数错误";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return "网络异常";
		}
	}

	/**
	 * 洗码设置审核
	 * @param ee
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private String bonusVerify(EnterpriseEmployee ee, Map<String, Object> object) throws Exception {
		Map<String, BigDecimal> superbonuses = employeeGamecataloyBonusService
				.takeEmployeeGameCategoryBonusMap(ee.getEmployeecode());
		List<Game> games = gameService.takeEnterpriseGames(ee.getEnterprisecode());
		StringBuffer bonus = new StringBuffer();
		for (Game g : games) {
			for (GameCategory gc : SystemCache.getInstance().getGameCategory(g.getGametype())) {
				String key = gc.getGametype().concat("_").concat(gc.getCategorytype());
				BigDecimal userbonus = new BigDecimal(String.valueOf(object.get(key))).divide(new BigDecimal(100));
				BigDecimal superbonus = superbonuses.get(key);
				superbonus = superbonus==null?new BigDecimal(0):superbonus;
				if (userbonus.compareTo(gc.getMinbonus()) == -1 || userbonus.compareTo(new BigDecimal(0)) == -1) {
					throw new LogicTransactionRollBackException(
							gc.getGametype().replace("Game", "") + "-" + gc.getCategoryname() + "， 洗码不能小于" + gc.getMinbonus());
				} else if (userbonus.compareTo(superbonus) == 1 || userbonus.compareTo(gc.getMaxbonus()) == 1) {
					throw new LogicTransactionRollBackException(
							gc.getGametype().replace("Game", "") + "-" + gc.getCategoryname() + "，下级洗码不能超过自身洗码");
				} else {
					if (bonus.length() == 0) {
						bonus.append(gc.getGametype()).append("_").append(gc.getCategorytype()).append(":").append(userbonus);
					} else {
						bonus.append("|").append(gc.getGametype()).append("_").append(gc.getCategorytype()).append(":")
								.append(userbonus);
					}
				}
			}
		}
		return bonus.toString();
	}

	/**
	 * 分红或占成设置审核
	 * @param employeecode
	 * @param parentemployeecode
	 * @param dividend
	 * @param share
	 * @throws Exception
	 */
	private void dividendShareVerify(EnterpriseEmployee ee, BigDecimal dividend, BigDecimal share) throws Exception {
		if (dividend.doubleValue() < 0 || share.doubleValue() < 0 ){
			throw new LogicTransactionRollBackException("分红与占成不能为负数");
		}else if(dividend.compareTo(ee.getDividend()) == 1) {
			throw new LogicTransactionRollBackException("分红不能大于" + ee.getDividend().multiply(new BigDecimal(100)) + "%");
		}else if(share.compareTo(ee.getShare()) == 1) {
			throw new LogicTransactionRollBackException("占成不能大于" + ee.getShare().multiply(new BigDecimal(100)) + "%");
		}
	}
	
	/* 域名信息配置相关start */
	//域名信息管理首页
	@RequestMapping("/domainInfoIndex")
	public String domainInfoIndex(HttpServletRequest request,HttpSession session, Model model) {
		return "/registerlink/domainInfoIndex";
		/*if(super.validateIsEmployee(session, model)){
			return "/registerlink/domainInfoIndex";
		}else{
			return Constant.PAGE_IDENTITY_NO_MATCH;
		}*/
	}
	
	//域名信息管理内容
	@RequestMapping("/domainInfoData")
	@ResponseBody
	public Map<String, Object> domainInfoData(HttpServletRequest request, HttpSession session) {
		try {
			
			Map<String, Object> __Object = super.getRequestParamters(request);
			EnterpriseEmployee __EE = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			__Object.put("enterprisecode", __EE.getEnterprisecode());
			List<BrandDomain> domains = brandDomainService.selectAll(__Object);
			int count = brandDomainService.selectAllCount(__Object);
			for (BrandDomain brandDomain : domains) {
				brandDomain.setSign(super.encryptString(String.valueOf(brandDomain.getDomaincode()), session.getId()));
				if (StringUtils.isNotEmpty(brandDomain.getEnterprisecode()))
					brandDomain.setEnterpriseName(enterpriseService.selectByPrimaryKey(brandDomain.getEnterprisecode()).getEnterprisename()); //企业名称
				if (StringUtils.isNotEmpty(brandDomain.getEmployeecode())){
					EnterpriseEmployee __ee = enterpriseEmployeeService.takeEmployeeByCode(brandDomain.getEmployeecode());
					if(__ee!=null){
						brandDomain.setEmployeeName(__ee.getLoginaccount()); //所属人名称
					}
				}
				if (StringUtils.isNotEmpty(brandDomain.getParentemployeecode()) && !"00000000".equals(brandDomain.getParentemployeecode())){
					EnterpriseEmployee __ee = enterpriseEmployeeService.takeEmployeeByCode(brandDomain.getEmployeecode());
					if(__ee!=null){
						brandDomain.setParentemployeeName(__ee.getLoginaccount()); //上级名称
					}
				}
					
				if (StringUtils.isNotEmpty(brandDomain.getBrandcode())){
					EnterpriseOperatingBrand __eob = enterpriseOperatingBrandService.takeBrandByPrimaryKey(brandDomain.getBrandcode()); 
					if(__eob!=null){
						brandDomain.setBrandname(__eob.getBrandname());
					}
				}
				if (brandDomain.getDomainlink().indexOf(".")<=0) {//当domain为推荐码时，组装成普通链接
					StringBuffer cur_host = new StringBuffer(request.getServerName()+":"+request.getServerPort()+"/"+request.getContextPath());
					cur_host.append("/res/register?sign=");
					cur_host.append(brandDomain.getDomainlink());
					brandDomain.setDomainlink(cur_host.toString());
				}
			}
			return super.formatPagaMap(domains, count);
		} catch (LogicTransactionRollBackException e) {
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	
	/* 域名信息配置相关end */
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}

}
