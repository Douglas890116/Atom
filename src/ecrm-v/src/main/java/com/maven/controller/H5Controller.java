package com.maven.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.maven.annotation.RequestInterval;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.BettingAllDay;
import com.maven.entity.BettingAllGameWinloseDetail;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EmployeeMappingMenu;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeCapitalAccount;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.EnterpriseEmployeeType.Type;
import com.maven.entity.EnterpriseGame;
import com.maven.entity.Game;
import com.maven.entity.LogLogin;
import com.maven.entity.PermissionMenu;
import com.maven.entity.PrivateDataAccess;
import com.maven.entity.ReportDailyAgent;
import com.maven.entity.ReportWeeklyAgent;
import com.maven.entity.ReportWeeklyMember;
import com.maven.entity.TakeDepositRecord;
import com.maven.entity.TakeDepositRecord.Enum_orderstatus;
import com.maven.entity.UserLogs;
import com.maven.entity.WorkingFlowObject;
import com.maven.game.APIServiceNew;
import com.maven.game.APIServiceUtil;
import com.maven.game.enums.GameEnum.Enum_GameType;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.BettingAllDayService;
import com.maven.service.BettingAllGameWinloseDetailService;
import com.maven.service.EmployeeApiAccoutService;
import com.maven.service.EmployeeGamecataloyBonusService;
import com.maven.service.EmployeeMappingMenuService;
import com.maven.service.EmployeeMoneyChangeService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseGameService;
import com.maven.service.EnterpriseService;
import com.maven.service.GameService;
import com.maven.service.LogLoginService;
import com.maven.service.PandectService;
import com.maven.service.PermissionMenuService;
import com.maven.service.PrivateDataAccessService;
import com.maven.service.ReportDailyAgentService;
import com.maven.service.ReportWeeklyAgentService;
import com.maven.service.ReportWeeklyMemberService;
import com.maven.service.TakeDepositRecoredService;
import com.maven.service.UserLogsService;
import com.maven.service.WorkingFlowObjectService;
import com.maven.util.BankUtils;
import com.maven.util.Encrypt;
import com.maven.util.GameUtils;
import com.maven.util.RandomString;
import com.maven.util.WebInfoHandle;
import com.maven.utils.MoneyHelper;
import com.maven.utils.StringUtil;
/**
 * 后台H5版本，所有H5页面的Controller都写在这里面
 * @author klay
 *
 */
@Controller
@RequestMapping("/h5")
public class H5Controller extends BaseController {
	/**
	 * 错误页面地址
	 */
	public static final String H5_ERROR_PAGE = "/h5/layout/error";
	/**
	 * 代理类型
	 */
	private static List<String> ALL_AGENTTYPE = new ArrayList<String>(){
		private static final long serialVersionUID = 1L;
		{this.add(EnterpriseEmployeeType.Type.代理.value);this.add(EnterpriseEmployeeType.Type.信用代理.value);}
	};
	/**
	 * 写入日志
	 */
	private static LoggerManager log = LoggerManager.getLogger(EnterpriseEmployeeController.class.getName(), OutputManager.LOG_H5);
	/** 账户资金 */
	@Autowired
	private EnterpriseEmployeeCapitalAccountService enterpriseEmployeeCapitalAccountService;
	/**  会员接入品台游戏返点  */
	@Autowired
	private EmployeeGamecataloyBonusService employeeGamecataloyBonusService;
	/** 用户信息 */
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeServiceImpl;
	/** 用户账变数据 */
	@Autowired
	private EmployeeMoneyChangeService employeeMoneyChangeService;
	/** 权限映射 */
	@Autowired
	private EmployeeMappingMenuService employeeMappingMenuService;
	/** 企业员工  */
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	/** 存取款记录 */
	@Autowired
	private TakeDepositRecoredService takeDepositRecoredService;
	/** 会员周结报表 */
	@Autowired
	private ReportWeeklyMemberService reportWeeklyMemberService;
	/** 代理周结 */
	@Autowired
	private ReportWeeklyAgentService reportWeeklyAgentService;
	/** 工作流审核权限 */
	@Autowired
	private WorkingFlowObjectService workingFlowObjectService;
	/** 用户红利信息 */
	@Autowired
	private ActivityBetRecordService activityBetRecordService;
	/** 用户游戏账号信息 */
	@Autowired
	private EmployeeApiAccoutService employeeApiAccoutService; 
	/** 隐私数据权限 */
	@Autowired
	private PrivateDataAccessService privateDataAccessService;
	/** 用户输赢列表 */
	@Autowired
	private BettingAllGameWinloseDetailService winloseService;
	/** 代理结算报表 */
	@Autowired
	private ReportDailyAgentService reportDailyAgentService;
	/** 企业游戏列表 */
	@Autowired
	private EnterpriseGameService enterpriseGameService;
	/** 菜单权限组 */
	@Autowired
	private PermissionMenuService permissionMenuService;
	/** 结算报表 */
	@Autowired
	private BettingAllDayService bettingAllDayService;
	/** 会员数据 */
	@Autowired
	private EnterpriseEmployeeService employeeService;
	/** 企业信息 */
	@Autowired
	private EnterpriseService enterpriseService;
	/** 操作日志 */
	@Autowired
	private UserLogsService userLogsService;
	/** 登陆日志 */
	@Autowired
	private LogLoginService logLoginService;
	/** 团队统计数据 */
	@Autowired
	private PandectService pandectService;
	/** 系统接入游戏 */
	@Autowired
	private GameService gameService;

	
	/**
	 * 登出H5后台系统
	 * @param session
	 * @return
	 */
	@RequestMapping("/exit")
	public String exitH5(HttpSession session){
		session.invalidate();
		return "redirect:/h5";
	}
	/**
	 * 后台登录方法H5版
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public String loginH5(HttpServletRequest request, HttpSession session, Model model) {
		try {
			Map<String, Object> obj = getRequestParamters(request);
			String loginCode = (String) obj.get("verifyCode");
			
			String verifyCode = ((String)session.getAttribute("verifyCode")).split("_")[0];
			String timestamp = ((String)session.getAttribute("verifyCode")).split("_")[1];
			boolean isTimeOut = System.currentTimeMillis()/1000 - Long.valueOf(timestamp) > 60;
			if (isTimeOut) {
				model.addAttribute("msg", "验证码过期");
				return "/h5/login";
			}
			
			if (StringUtils.isBlank(verifyCode) || StringUtils.isBlank(loginCode)
					|| !verifyCode.equals(loginCode.toUpperCase())) {
				model.addAttribute("loginaccount", (String) obj.get("loginaccount"));
				model.addAttribute("loginpassword", (String) obj.get("loginpassword"));
				model.addAttribute("msg", "验证码错误");
				return "/h5/login";
			}

			String loginpassword = (String) obj.get("loginpassword");

			// 加密之后密码比较
			obj.put("loginpassword", Encrypt.MD5((String) obj.get("loginpassword")));
			// 1:有效用户，-1:无效的用户
			obj.put("datastatus", Constant.Enum_DataStatus.正常.value);
			// (1.启用 2.锁定游戏 3.禁用)
			obj.put("employeestatus", EnterpriseEmployee.Enum_employeestatus.启用.value);
			List<EnterpriseEmployee> users = enterpriseEmployeeService.getThisLoginEmployeeMsg(obj);
			if (users.size() != 1) {
				log.Debug("账号错误,登陆失败");
				model.addAttribute("message", "用户名或密码错误");
				return "/h5/login";
			}
			EnterpriseEmployee employee = users.get(0);
			// 非代理用户不能登录
			/*********非超级管理员时只能查询本团队内的数据************/
			if(!SystemConstant.getProperties("admin.employeecode").equals(employee.getEmployeecode())){
				if (employee.getEmployeetypecode().equals(Type.企业号.value)
						|| employee.getEmployeetypecode().equals(Type.员工.value)
						|| employee.getEmployeetypecode().equals(Type.会员.value)) {
						model.addAttribute("message", "非代理用户不能登录");
						return "/h5/login";
					}
			}

			if (employee.getLoginpassword2() == null || employee.getLoginpassword2().equals("")) {
				// 初始化
				Map<String, Object> object = new HashMap<String, Object>();
				object.put("loginpassword", Encrypt.MD5(loginpassword));
				object.put("loginpassword2", APIServiceUtil.encrypt(loginpassword, employee));// 对原始密码进行加密
				object.put("employeecode", employee.getEmployeecode());
				employee.setLoginpassword2(object.get("loginpassword2").toString());
			}
			// 用户信息存入session
			session.setAttribute(Constant.USER_SESSION, employee);

			List<Game> listGames = new ArrayList<Game>();// 企业游戏列表
			List<Game> allGames = gameService.findGameData(new HashMap<String, Object>());// 全部游戏
			// 获取本企业下所有游戏
			Map<String, EnterpriseGame> enterpriseGames = enterpriseGameService.takeEnterpriseGame(employee.getEnterprisecode());
			for (Game game : allGames) {
				if (enterpriseGames.containsKey(game.getGamecode())) {
					listGames.add(game);
				}
			}
			// 企业游戏信息存入session
			session.setAttribute(Constant.ENTERPRISE_GAMES, listGames);

			// 记录登录日志
			String loginIP = WebInfoHandle.getClientRealIP(request);
			String loginOS = WebInfoHandle.getRequestSystem(request);
			String browser = "";//WebInfoHandle.getBrowser(request);
			String referer = WebInfoHandle.getReferer(request);
			logLoginService.addLoginLog(new LogLogin(employee.getEnterprisecode(), employee.getBrandcode(),
					employee.getEmployeecode(), employee.getParentemployeecode(), employee.getLoginaccount(),
					new Date(), loginIP, null, loginOS, browser, referer));
			log.Debug("H5后台登陆通过，Referer：" + referer);

			return "redirect:/h5/loadSession";
		} catch (Exception e) {
			if (session.getAttribute(Constant.USER_SESSION) != null)
				session.removeAttribute(Constant.USER_SESSION);
			model.addAttribute("message", "登陆失败，请稍后尝试");
			log.Error("H5后台登录报错!", e);
		}
		return "/h5/login";
	}
	/**
	 * 加载用户session与相关权限
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/loadSession")
	public String loadSession(HttpServletRequest request, HttpSession session) {
		try {
			log.Debug("进入后开始加载用户权限...");
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			if(ee==null) return "redirect:/h5";
			//加载用户菜单权限 
			Map<String, PermissionMenu> allMenus = permissionMenuService.takeMenus();
			session.setAttribute(Constant.SYSTEM_PSERSSION, allMenus);
			if(ee.getEmployeecode().equals(SystemConstant.getProperties("admin.employeecode"))){
				session.setAttribute(Constant.USER_PSERSSION, allMenus);
			}else{
				Map<String,EmployeeMappingMenu> pColletion = employeeMappingMenuService.getUserPersseion(ee.getEmployeecode());
				if(pColletion.isEmpty()){
					return Constant.PAGE_NOPRIVILEGE;
				}
				session.setAttribute(Constant.USER_PSERSSION, permissionMenuService.takeUserMenus(allMenus,pColletion));
			}
			//加载用户工作流审核权限
			List<WorkingFlowObject> workingFlow =  workingFlowObjectService.takeEmployeeWorkFlow(ee.getEmployeecode());
			String flowcode = "";
			for (WorkingFlowObject workingFlowObject : workingFlow) {
				flowcode+=workingFlowObject.getFlowcode()+",";
			}
			session.setAttribute(Constant.USER_WORKING_FLOW,flowcode);
			
			//加载用户隐私数据权限
			List<PrivateDataAccess> priss =  privateDataAccessService.select(new PrivateDataAccess(ee.getEnterprisecode(), ee.getEmployeecode()));
			if(priss!=null && priss.size()>0){
				session.setAttribute(Constant.PRIVATE_DATA_PSERSSION, true);
			}else{
				session.removeAttribute(Constant.PRIVATE_DATA_PSERSSION);
			}
		}catch (Exception e) {
			session.removeAttribute(Constant.USER_SESSION);
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
		log.Debug("用户权限加载完毕,开始跳转至主页...");
		
		return "redirect:/h5/index";
	}
	/**
	 * H5后台首页
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String indexH5(HttpServletRequest request, HttpSession session, Model model) {
		try {
			Map<String, Object> obj = super.getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			obj.put("employeecode", ee.getEmployeecode());
			obj.put("enterprisecode", ee.getEnterprisecode());
			if(ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.企业号.value)
					|| ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.员工.value)) {
				obj.put("enterprisereport", true);
			}else{
				obj.put("enterprisereport", false);
			}
			if(obj.get("begintime") == null || obj.get("endtime") == null) {
				Calendar now = Calendar.getInstance();
				obj.put("endtime", now.getTime());
				now.add(Calendar.DATE, -30);
				obj.put("begintime", now.getTime());
			}
			// 团队输赢
			BigDecimal losewin = pandectService.usp_takeLoseWin(obj);
			model.addAttribute("losewin", losewin);
			// 充值总额
			BigDecimal savemoney = pandectService.usp_savemoney(obj);
			model.addAttribute("savemoney", savemoney);
			// 取款总额
			BigDecimal takemoney = pandectService.usp_takemoney(obj);
			model.addAttribute("takemoney", takemoney);
			// 团队余额
			BigDecimal balance = pandectService.usp_balance(obj);
			model.addAttribute("balance", balance);
			// 数据限制
			super.dataLimits(ee, obj, session);
			// 清除多余条件,会影响查询数据
			obj.remove("employeecode");
			obj.remove("enterprisecode");
			if (obj.get("begintime") != null) {
				obj.put("moneyChangeDate_begin", obj.get("begintime"));
				obj.remove("begintime");
			}
			if (obj.get("endtime") != null) {
				obj.put("moneyChangeDate_end", obj.get("endtime"));
				obj.remove("endtime");
			}
			Map<String, Object> result = new HashMap<String, Object>();
			
			// 冲正统计
			obj.put("moneychangetypecode", Enum_moneychangetype.冲正.value);
			result = employeeMoneyChangeService.findAccountChangeCount(obj);
			BigDecimal integralPlus = new BigDecimal(0);
			if(result.get("moneychangeamount")!=null)
				integralPlus = new BigDecimal(result.get("moneychangeamount").toString());
			model.addAttribute("integralPlus", integralPlus);
			
			// 冲负统计
			obj.put("moneychangetypecode", Enum_moneychangetype.冲负.value);
			result = employeeMoneyChangeService.findAccountChangeCount(obj);
			BigDecimal integralMinus = new BigDecimal(0);
			if(result.get("moneychangeamount")!=null)
				integralMinus = new BigDecimal(result.get("moneychangeamount").toString());
			model.addAttribute("integralMinus", integralMinus);
			
			// 优惠额统计
			obj.put("moneychangetypecode", Enum_moneychangetype.优惠活动.value);
			result = employeeMoneyChangeService.findAccountChangeCount(obj);
			BigDecimal discountAmount = new BigDecimal(0);
			if(result.get("moneychangeamount")!=null)
				discountAmount = new BigDecimal(result.get("moneychangeamount").toString());
			model.addAttribute("discountAmount", discountAmount);
			
			// 洗码日统计
			obj.put("moneychangetypecode", Enum_moneychangetype.洗码日结.value);
			result = employeeMoneyChangeService.findAccountChangeCount(obj);
			BigDecimal washingCodeDaily = new BigDecimal(0);
			if(result.get("moneychangeamount")!=null)
				washingCodeDaily = new BigDecimal(result.get("moneychangeamount").toString());
			// 洗码周统计
			obj.put("moneychangetypecode", Enum_moneychangetype.洗码周结.value);
			result = employeeMoneyChangeService.findAccountChangeCount(obj);
			BigDecimal washingCodeWeek = new BigDecimal(0);
			if(result.get("moneychangeamount")!=null)
				washingCodeWeek = new BigDecimal(result.get("moneychangeamount").toString());
			model.addAttribute("washCode", washingCodeDaily.add(washingCodeWeek));
		} catch (Exception e) {
			log.Error("H5后台页面index报错", e);
			return H5_ERROR_PAGE;
		}
		return "/h5/index";
	}
	/**
	 * 会员列表
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/userList")
	public String userList(HttpServletRequest request, HttpSession session, Model model) {
		try {
			//获取登录用户的信息
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String,Object> obj = getRequestParamters(request);
			// 初始化分页参数
			initPagination(obj, "lastlogintime", "desc");
			
			obj.put("enterprisecode", employee.getEnterprisecode());
			// 只查询会员信息
			obj.put("employeetypecode", Type.会员.value);
			super.dataLimits(employee, obj,session);

			List<EnterpriseEmployee> list = enterpriseEmployeeService.findEmployee(obj);
			
			//加密标识字段的值
			this.encryptSign(list,session.getId(),new String[]{"employeecode"});

			//查询总记录条数
			long listTotal = enterpriseEmployeeService.findEmployeeCount(obj);

			// 分页数据
			pageModel(model, list, (int)listTotal, 2);
			
			// 查询的参数
			model.addAttribute("params", obj);
		} catch (Exception e) {
			log.Error("H5后台跳转会员信息页面出错", e);
			return H5_ERROR_PAGE;
		}
		return "/h5/userlist";
	}
	/**
	 * 代理列表
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("agentList")
	public String agentList(HttpServletRequest request, HttpSession session, Model model) {
		try {
			//获取登录用户的信息
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String,Object> obj = getRequestParamters(request);
			
			// 初始化分页参数
			initPagination(obj, "lastlogintime", "desc");
			
			obj.put("enterprisecode", employee.getEnterprisecode());
			// 只查代理信息
			obj.put("employeetypecodes", ALL_AGENTTYPE);
			super.dataLimits(employee, obj,session);
			
			List<EnterpriseEmployee> list = enterpriseEmployeeService.findEmployee(obj);
			
			// 加密标识字段的值
			this.encryptSign(list,session.getId(),new String[]{"employeecode"});
			
			// 查询总记录条数
			int listTotal = enterpriseEmployeeService.findEmployeeCount(obj);

			// 分页数据
			pageModel(model, list, listTotal, 2);
			
			// 查询的参数
			model.addAttribute("params", obj);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return H5_ERROR_PAGE;
		}
		return "/h5/agentlist";
	}
	/**
	 * 会员存取款信息
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/takeDepositRecords")
	public String takeDepositRecords(HttpServletRequest request, HttpSession session, Model model) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> obj = getRequestParamters(request);
			
			// 初始化分页参数
			initPagination(obj, "orderdate", "desc");
			
			obj.put("order_statuss", new Integer[]{Enum_orderstatus.审核通过.value,Enum_orderstatus.拒绝.value});
			obj.put("flowcodes", new ArrayList<WorkingFlowObject>());
			super.dataLimits(loginEmployee, obj,session);
			// 查询分页数据
			List<TakeDepositRecord> list = takeDepositRecoredService.findTakeDepositRecord(obj);
			if (null != list && list.size() > 0) {
				String employeepaymentbank = "";
				for (TakeDepositRecord tdr : list) {
					employeepaymentbank = tdr.getEmployeepaymentbank();
					tdr.setEmployeepaymentbank(BankUtils.getBankName(employeepaymentbank));
					employeepaymentbank = "";
				}
			}
			// 查询记录总条数，统计数据
			Map<String, Object> result = takeDepositRecoredService.findcountTakeDepositRecord(obj);
			int listTotal = StringUtil.getInt(result.get("count"));
			
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("orderamount", StringUtil.getDouble(result.get("orderamount")));
			model.addAttribute("summary", summary);
			
			// 分页数据
			pageModel(model, list, listTotal, 2);
			
			// 查询的参数
			model.addAttribute("params", obj);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return H5_ERROR_PAGE;
		}
		model.addAttribute("ordertype", request.getParameter("ordertype"));
		model.addAttribute("ordername", request.getParameter("ordertype").equals("1") ? "存款" : "取款");
		return "/h5/depositandtakerecords";
	}
	/**
	 * 用户存取款统计
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/depositAndTakeReport")
	public String depositAndTakeReport(HttpServletRequest request, HttpSession session, Model model) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> obj = getRequestParamters(request);
			
			//初始化分页参数
			initPagination(obj, null, null);
			
			obj.put("employeecode", loginEmployee.getEmployeecode());
			// 用户存取款查询
			List<TakeDepositRecord> list = takeDepositRecoredService.call_userDepositTakeCount(obj);
			int listTotal = StringUtil.getInt(obj.get("countNumber"));
			
			// 分页数据
			pageModel(model, list, listTotal, 2);
			
			// 查询的参数
			model.addAttribute("params", obj);
		} catch (Exception e) {
			log.Error("H5后台，用户存取统计报错", e);
			return H5_ERROR_PAGE;
		}
		return "/h5/depositandtakereport";
	}
	/**
	 * 用户存取款预警
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/depositAndTakeWarning")
	public String depositAndTakeWarning(HttpServletRequest request, HttpSession session, Model model) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> obj = getRequestParamters(request);

			// 初始化分页参数
			initPagination(obj, null, null);
			
			obj.put("enterprisecode", loginEmployee.getEnterprisecode());
			// 数据查询
			List<TakeDepositRecord> list = takeDepositRecoredService.call_userDepositTakeRate(obj);
			int listTotal = StringUtil.getInt(obj.get("countNumber"));
			
			// 分页数据
			pageModel(model, list, listTotal, 2);
			
			// 查询的参数
			model.addAttribute("params", obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/h5/depositandtakewarning";
	}
	/**
	 * 用户输赢分析
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/loseWinAnalysis")
	public String loseWinReport(HttpServletRequest request, HttpSession session, Model model) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> obj = getRequestParamters(request);
			
			//初始化分页参数
			initPagination(obj, null, null);
			
			obj.put("enterprisecode", loginEmployee.getEnterprisecode());
//			this.dataLimits(loginEmployee, obj, session);
			
			List<EnterpriseEmployeeCapitalAccount> list = enterpriseEmployeeCapitalAccountService.queryUserLoseWinAnalysis(obj);
			
			Map<String, Object> result = enterpriseEmployeeCapitalAccountService.queryCountUserLoseWinAnalysis(obj);
			int listTotal = StringUtil.getInt(result.get("count"));

			// 统计数据
			Map<String, String> summary = new HashMap<String, String>();
			summary.put("accumulateddeposit", MoneyHelper.doubleFormat(result.get("accumulateddeposit")));
			summary.put("accumulatedwithdraw", MoneyHelper.doubleFormat(result.get("accumulatedwithdraw")));
			summary.put("summoney", MoneyHelper.doubleFormat(result.get("summoney")));
			model.addAttribute("summary", summary);
			
			// 分页数据
			pageModel(model, list, listTotal, 2);
			
			// 查询的参数
			model.addAttribute("params", obj);
		} catch (Exception e) {
			log.Error("H5后台，用户输赢分析报错", e);
			return H5_ERROR_PAGE;
		}
		return "/h5/losewinanalysis";
	}
	/**
	 * 输赢预警
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/loseWinWarning")
	public String loseWinWarning(HttpServletRequest request, HttpSession session, Model model) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> obj = getRequestParamters(request);
			// 初始化分页参数
			initPagination(obj, null, null);
			
			obj.put("enterprisecode", loginEmployee.getEnterprisecode());
			if(obj.get("loginaccount") != null
					&& obj.get("loginaccount").toString().trim().equals("")) {
				obj.remove("loginaccount");
			}
			// 用户存取款查询
			List<TakeDepositRecord> list = takeDepositRecoredService.call_userGameWinLoseRate(obj);
			int listTotal = StringUtil.getInt(obj.get("countNumber"));

			// 分页数据
			pageModel(model, list, listTotal, 2);
			
			// 查询的参数
			model.addAttribute("params", obj);
		} catch (Exception e) {
			log.Error("H5后台，输赢预警报错", e);
			return H5_ERROR_PAGE;
		}
		return "/h5/losewinwarning";
	}
	/**
	 * 会员游戏汇总
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/userGamesReport")
	public String userGamesReport(HttpServletRequest request, HttpSession session, Model model) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> obj = this.getRequestParamters(request);
			
			/********* 非超级管理员时只能查询本团队内的数据 ************/
			if (!SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode()))
				obj.put("enterprisecode", loginEmployee.getEnterprisecode());
			super.dataLimits(loginEmployee, obj, session);
			
			// 初始化分页参数
			initPagination(obj, "bettime", "desc");
			
			String gamePlatform = (String) obj.get("gamePlatform");
			if (StringUtils.isNotBlank(gamePlatform) && Enum_GameType.validate(gamePlatform)) {
				for (Enum_GameType game : Enum_GameType.values()) {
					if (gamePlatform.equals(game.gametype))
						obj.put("platformtype", game.bettingcode);
				}
				obj.remove("gamePlatform");
			}
			List<BettingAllGameWinloseDetail> list = winloseService.selectForPage(obj);
			if(null != list && list.size() > 0) {
				String platformType = null;
				String gameBigType  = null;
				for (int i = 0; i < list.size(); i++) {
					platformType = list.get(i).getPlatformtype();
					gameBigType  = list.get(i).getGamebigtype();
					list.get(i).setPlatformtype(GameUtils.getGameTypeNameBybettingCode(platformType));
					list.get(i).setGamebigtype(GameUtils.getGameBigTypeName(gameBigType));
				}
			}
			Map<String, Object> result = winloseService.takeRecordCountMoney(obj);
			int listTotal = StringUtil.getInt(result.get("count"));

			model.addAttribute("betmoney", StringUtil.getDouble(result.get("betmoney")));
			model.addAttribute("validbet", StringUtil.getDouble(result.get("validMoney")));
			model.addAttribute("netmoney", StringUtil.getDouble(result.get("netMoney")));
			
			// 分页数据
			pageModel(model, list, listTotal, 3);
			
			// 查询的参数
			model.addAttribute("params", obj);
		} catch (Exception e) {
			log.Error("H5后台，会员游戏汇总报错", e);
			return H5_ERROR_PAGE;
		}
		return "/h5/usergamesreport";
	}
	/**
	 * 用户打码日结算
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/userDailyReport")
	public String userDailyReport(HttpServletRequest request, HttpSession session, Model model) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> obj = this.getRequestParamters(request);
			
			if (obj.get("parentName") != null) {
				String parentemployeeaccount = obj.get("parentName").toString();
				EnterpriseEmployee parent = new EnterpriseEmployee();
				parent.setLoginaccount(parentemployeeaccount);
				List<EnterpriseEmployee> list = employeeService.select(parent);
				if (null != list && list.size() > 0) {
					obj.put("parentemployeecode", list.get(0).getEmployeecode());
				} else {
					// 如果查不到上级账户code，就直接传名字进去，查不出数据，表示这个账号下没有会员
					obj.put("parentemployeecode", parentemployeeaccount);
				}
				obj.remove("parentName");
			}
			/*********非超级管理员时只能查询本团队内的数据************/
			if( !SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())){
				obj.put("enterprisecode", loginEmployee.getEnterprisecode());
			}
			super.dataLimits(loginEmployee, obj, session);
			
			// 初始化分页参数
			initPagination(obj, "Add_Time", "desc");
			
			List<BettingAllDay> list = this.bettingAllDayService.selectForPage(obj);
			if(null != list && list.size() > 0) {
				String gamePlatform = null;
				String gameBigType  = null;
				for (int i = 0; i < list.size(); i++) {
					gamePlatform = list.get(i).getGamePlatform();
					gameBigType  = list.get(i).getGameBigType();
					list.get(i).setGamePlatform(GameUtils.getGameTypeNameByGameType(gamePlatform));
					list.get(i).setGameBigType(GameUtils.getGameBigTypeName(gameBigType));
					gamePlatform = null;
					gameBigType  = null;
				}
			}
			Map<String, Object> result = bettingAllDayService.takeRecordCountMoney(obj);
			int listTotal = StringUtil.getInt(result.get("count"));
			
			model.addAttribute("validMoney", StringUtil.getDouble(result.get("validMoney")));
			model.addAttribute("netMoney", StringUtil.getDouble(result.get("netMoney")));
			model.addAttribute("rebatesCash", StringUtil.getDouble(result.get("rebatesCash")));
			
			// 分页数据
			pageModel(model, list, listTotal, 3);
			
			// 查询的参数
			model.addAttribute("params", obj);
		} catch (Exception e) {
			log.Error("H5后台，用户打码日结算报错", e);
			return H5_ERROR_PAGE;
		}
		return "/h5/userdailyreport";
	}
	/**
	 * 用户打码周结算
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/userWeeklyReport")
	public String userWeeklyReport(HttpServletRequest request, HttpSession session, Model model) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> obj = this.getRequestParamters(request);
			
			if (obj.get("parentName") != null) {
				String parentemployeeaccount = obj.get("parentName").toString();
				EnterpriseEmployee parent = new EnterpriseEmployee();
				parent.setLoginaccount(parentemployeeaccount);
				List<EnterpriseEmployee> list = employeeService.select(parent);
				if (null != list && list.size() > 0) {
					obj.put("parentemployeecode", list.get(0).getEmployeecode());
				} else {
					// 如果查不到上级账户code，就直接传名字进去，查不出数据，表示这个账号下没有会员
					obj.put("parentemployeecode", parentemployeeaccount);
				}
				obj.remove("parentName");
			}
			/*********非超级管理员时只能查询本团队内的数据************/
			if( !SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())){
				obj.put("enterprisecode", loginEmployee.getEnterprisecode());
			}
			super.dataLimits(loginEmployee, obj, session);
			
			// 初始化分页参数
			initPagination(obj, "patchno", "desc");
			
			List<ReportWeeklyMember> list = this.reportWeeklyMemberService.selectAll(obj);
			if(null != list && list.size() > 0) {
				String gamePlatform = null;
				String gameBigType  = null;
				for (int i = 0; i < list.size(); i++) {
					gamePlatform = list.get(i).getGameplatform();
					gameBigType  = list.get(i).getGametype();
					list.get(i).setGameplatform(GameUtils.getGameTypeNameByGameType(gamePlatform));
					list.get(i).setGametype(GameUtils.getGameBigTypeName(gameBigType));
					gamePlatform = null;
					gameBigType  = null;
				}
			}
			Map<String, Object> result = reportWeeklyMemberService.takeRecordCountMoney(obj);
			int listTotal = StringUtil.getInt(result.get("count"));
			
			model.addAttribute("bet", StringUtil.getDouble(result.get("bet")));
			model.addAttribute("amount", StringUtil.getDouble(result.get("amount")));
			model.addAttribute("subtract", StringUtil.getDouble(result.get("subtract")));
			
			// 分页数据
			pageModel(model, list, listTotal, 3);
			
			// 查询的参数
			model.addAttribute("params", obj);
		} catch (Exception e) {
			log.Error("H5后台，用户打码周结算报错", e);
			return H5_ERROR_PAGE;
		}
		return "/h5/userweeklyreport";
	}
	/**
	 * 代理打码日结算
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/agentDailyReport")
	public String agentDailyReport(HttpServletRequest request, HttpSession session, Model model) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> obj = this.getRequestParamters(request);
			
			if (obj.get("parentName") != null) {
				String parentemployeeaccount = obj.get("parentName").toString();
				EnterpriseEmployee parent = new EnterpriseEmployee();
				parent.setLoginaccount(parentemployeeaccount);
				List<EnterpriseEmployee> list = employeeService.select(parent);
				if (null != list && list.size() > 0) {
					obj.put("parentemployeecode", list.get(0).getEmployeecode());
				} else {
					// 如果查不到上级账户code，就直接传名字进去，查不出数据，表示这个账号下没有会员
					obj.put("parentemployeecode", parentemployeeaccount);
				}
				obj.remove("parentName");
			}
			/*********非超级管理员时只能查询本团队内的数据************/
			if( !SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())){
				obj.put("enterprisecode", loginEmployee.getEnterprisecode());
			}
			super.dataLimits(loginEmployee, obj, session);
			
			// 初始化分页参数
			initPagination(obj, "reporttime", "desc");
			
			List<ReportDailyAgent> list = this.reportDailyAgentService.selectAll(obj);
			if(null != list && list.size() > 0) {
				String gamePlatform = null;
				String gameBigType  = null;
				for (int i = 0; i < list.size(); i++) {
					gamePlatform = list.get(i).getGameplatform();
					gameBigType  = list.get(i).getGametype();
					list.get(i).setGameplatform(GameUtils.getGameTypeNameByGameType(gamePlatform));
					list.get(i).setGametype(GameUtils.getGameBigTypeName(gameBigType));
					gamePlatform = null;
					gameBigType  = null;
				}
			}
			Map<String, Object> result = reportDailyAgentService.takeRecordCountMoney(obj);
			int listTotal = StringUtil.getInt(result.get("count"));
			
			model.addAttribute("bet", StringUtil.getDouble(result.get("bet")));
			model.addAttribute("amount", StringUtil.getDouble(result.get("amount")));
			
			// 分页数据
			pageModel(model, list, listTotal, 3);
			
			// 查询的参数
			model.addAttribute("params", obj);
		} catch (Exception e) {
			log.Error("H5后台，代理打码日结算报错", e);
			return H5_ERROR_PAGE;
		}
		return "/h5/agentdailyreport";
	}
	/**
	 * 代理打码周结算
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/agentWeeklyReport")
	public String agentWeeklyReport(HttpServletRequest request, HttpSession session, Model model) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> obj = this.getRequestParamters(request);
			
			if (obj.get("parentName") != null) {
				String parentemployeeaccount = obj.get("parentName").toString();
				EnterpriseEmployee parent = new EnterpriseEmployee();
				parent.setLoginaccount(parentemployeeaccount);
				List<EnterpriseEmployee> list = employeeService.select(parent);
				if (null != list && list.size() > 0) {
					obj.put("parentemployeecode", list.get(0).getEmployeecode());
				} else {
					// 如果查不到上级账户code，就直接传名字进去，查不出数据，表示这个账号下没有会员
					obj.put("parentemployeecode", parentemployeeaccount);
				}
				obj.remove("parentName");
			}
			/*********非超级管理员时只能查询本团队内的数据************/
			if( !SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())){
				obj.put("enterprisecode", loginEmployee.getEnterprisecode());
			}
			super.dataLimits(loginEmployee, obj, session);
			
			// 初始化分页参数
			initPagination(obj, "reporttime", "desc");
			
			List<ReportWeeklyAgent> list = this.reportWeeklyAgentService.selectAll(obj);
			if(null != list && list.size() > 0) {
				String gamePlatform = null;
				String gameBigType  = null;
				for (int i = 0; i < list.size(); i++) {
					gamePlatform = list.get(i).getGameplatform();
					gameBigType  = list.get(i).getGametype();
					list.get(i).setGameplatform(GameUtils.getGameTypeNameByGameType(gamePlatform));
					list.get(i).setGametype(GameUtils.getGameBigTypeName(gameBigType));
					gamePlatform = null;
					gameBigType  = null;
				}
			}
			Map<String, Object> result = reportWeeklyAgentService.takeRecordCountMoney(obj);
			int listTotal = StringUtil.getInt(result.get("count"));
			
			model.addAttribute("bet", StringUtil.getDouble(result.get("bet")));
			model.addAttribute("amount", StringUtil.getDouble(result.get("amount")));
			model.addAttribute("subtract", StringUtil.getDouble(result.get("subtract")));
			
			// 分页数据
			pageModel(model, list, listTotal, 3);
			
			// 查询的参数
			model.addAttribute("params", obj);
		} catch (Exception e) {
			log.Error("H5后台，代理打码周结算报错", e);
			return H5_ERROR_PAGE;
		}
		return "/h5/agentweeklyreport";
	}
	/**
	 * 代理占成结算A
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/nbReportA")
	public String nbReportA(HttpServletRequest request, HttpSession session, Model model) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			//获取当前用户的占成
			Enterprise enterprise = enterpriseService.selectByPrimaryKey(loginEmployee.getEnterprisecode());
			double curren_share = loginEmployee.getShare().doubleValue();
			double Depositrate = Double.valueOf(enterprise.getDepositrate());
			double Takerate = Double.valueOf(enterprise.getTakerate());
			
			
			Map<String, Object> obj = getRequestParamters(request);
			Object employeecode = obj.get("employeecode");
			String employeeType = StringUtil.getString(obj.get("employeeType"));//查会员时
			Object loginaccount = null;
			Object startDate = obj.get("startDate");
			Object endDate = obj.get("endDate");
			obj.remove("startDate");
			obj.remove("endDate");
			obj.remove("employeecode");
			
			// 初始化分页参数
			initPagination(obj, null, null);
			
			List<Map<String, Object>> listRecord = new ArrayList<Map<String,Object>>();
			
			if(employeeType.equals("T003")) {
				obj.put("employeetypecode", Type.会员.value);
				obj.put("parentemployeecode", employeecode);
				List<EnterpriseEmployee> listEnterpriseEmployee = enterpriseEmployeeServiceImpl.selectAll(obj);
				int listTotal = enterpriseEmployeeServiceImpl.selectAllCount(obj);
				
				
				for (EnterpriseEmployee enterpriseEmployee : listEnterpriseEmployee) {
					//查所有直线会员汇总数据
					obj.clear();
					obj.put("employeecode", enterpriseEmployee.getEmployeecode());
					obj.put("startDate", startDate);
					obj.put("endDate", endDate);
					enterpriseEmployeeServiceImpl.call_UserGameMemberReportOne(obj);
					Map<String, Object> item = new HashMap<String, Object>();
					item.put("loginaccount", enterpriseEmployee.getLoginaccount());
					item.put("employeecode", enterpriseEmployee.getEmployeecode());
					item.put("employeetypename", "个人会员");
					
					double but_money = StringUtil.getDouble(obj.get("but_money"));
					double net_money = StringUtil.getDouble(obj.get("net_money"));
					double rebates_cash = StringUtil.getDouble(obj.get("rebates_cash")) ;//游戏输赢
					double activity_money = StringUtil.getDouble(obj.get("activity_money"));
					double depositMoney = StringUtil.getDouble(obj.get("depositMoney"));
					double withdrawMoney = StringUtil.getDouble(obj.get("withdrawMoney"));
					
					//存款手续费
					double dividend = MoneyHelper.moneyFormatDouble( depositMoney * Depositrate);
					//取款手续费
					double share = MoneyHelper.moneyFormatDouble( withdrawMoney * Takerate );
					
					//代理结算额  = 使用：占成代理结算方式一
					//公式=游戏输赢 * 公司占成 + 手续费 + 优惠 + 洗码；
					double profit_amount = MoneyHelper.moneyFormatDouble( net_money * curren_share  + dividend + share + activity_money + rebates_cash);
					
					item.put("game_betting_amount", but_money);
					item.put("activity_money", activity_money);//优惠额
					item.put("lose_win_amount", net_money);
					item.put("bonus", rebates_cash);//洗码额
					item.put("countMember", 0);
					item.put("countAgent", 0);
					item.put("countEmployee", 0);
					item.put("depositMoney", depositMoney);//存款额
					item.put("withdrawMoney", withdrawMoney);//取款额
					item.put("remark", "会员个人汇总");
					
					item.put("dividend", dividend);
					item.put("share", share);
					item.put("profit_amount", profit_amount);
					
					listRecord.add(0,item);
				}
				
				// 分页数据
				pageModel(model, listRecord, listTotal, 2);
				
				// 查询的参数
				model.addAttribute("params", obj);
			} else {
				List<String> employeetypecodes = new ArrayList<String>();
				employeetypecodes.add(Type.代理.value);
				employeetypecodes.add(Type.员工.value);
				obj.put("employeetypecodes", employeetypecodes);
				//查所有的直线代理团队汇总数据
				if(employeecode == null){
					obj.put("parentemployeecode", loginEmployee.getEmployeecode());
					loginaccount = loginEmployee.getLoginaccount();
					employeecode = loginEmployee.getEmployeecode();
				} else {
					obj.put("parentemployeecode", employeecode);
					loginEmployee = enterpriseEmployeeServiceImpl.takeEmployeeByCode(String.valueOf(employeecode));
					loginaccount = loginEmployee.getLoginaccount();
					employeecode = loginEmployee.getEmployeecode();
				}
				List<EnterpriseEmployee> listEnterpriseEmployee = enterpriseEmployeeServiceImpl.selectAll(obj);
				int listTotal = enterpriseEmployeeServiceImpl.selectAllCount(obj);
				
				for (EnterpriseEmployee enterpriseEmployee : listEnterpriseEmployee) {
					
					obj.clear();
					obj.put("employeecode", enterpriseEmployee.getEmployeecode());
					obj.put("startDate", startDate);
					obj.put("endDate", endDate);
					enterpriseEmployeeServiceImpl.call_UserGameAgentReport(obj);
					
					Map<String, Object> item = new HashMap<String, Object>();
					item.put("loginaccount", enterpriseEmployee.getLoginaccount());
					item.put("employeecode", enterpriseEmployee.getEmployeecode());
					item.put("employeetypename", enterpriseEmployee.getEmployeetypename());
					
					double but_money = StringUtil.getDouble(obj.get("but_money"));
					double net_money = StringUtil.getDouble(obj.get("net_money"));
					double rebates_cash = StringUtil.getDouble(obj.get("rebates_cash")) ;//游戏输赢
					double activity_money = StringUtil.getDouble(obj.get("activity_money"));
					double depositMoney = StringUtil.getDouble(obj.get("depositMoney"));
					double withdrawMoney = StringUtil.getDouble(obj.get("withdrawMoney"));
					
					
					//存款手续费
					double dividend = MoneyHelper.moneyFormatDouble( depositMoney * Depositrate);
					//取款手续费
					double share = MoneyHelper.moneyFormatDouble( withdrawMoney * Takerate );
					
					//代理结算额  = 使用：占成代理结算方式一
					//公式=游戏输赢 * 公司占成 + 手续费 + 优惠 + 洗码；
					double profit_amount = MoneyHelper.moneyFormatDouble( net_money * curren_share  + dividend + share + activity_money + rebates_cash);
					
					item.put("game_betting_amount", but_money);
					item.put("activity_money", activity_money);
					item.put("lose_win_amount", net_money);
					item.put("bonus", rebates_cash);
					item.put("countMember", obj.get("countMember"));
					item.put("countAgent", obj.get("countAgent"));
					item.put("countEmployee", obj.get("countEmployee"));
					item.put("dividend", dividend);
					item.put("share", share);
					item.put("profit_amount", profit_amount);
					item.put("depositMoney", depositMoney);
					item.put("withdrawMoney", withdrawMoney);
					item.put("remark", "直线代理团队汇总");
					if(enterpriseEmployee.getEmployeetypecode().equals(Type.员工.value)) {
						item.put("remark", "下级员工团队汇总");
					}
					listRecord.add(item);
				}
				
				if(!loginEmployee.getEmployeetypecode().equals(Type.会员.value)) {
					//查所有直线会员汇总数据
					obj.clear();
					obj.put("employeecode", employeecode);
					obj.put("startDate", startDate);
					obj.put("endDate", endDate);
					enterpriseEmployeeServiceImpl.call_UserGameMemberReport(obj);
					Map<String, Object> item = new HashMap<String, Object>();
					item.put("loginaccount", loginaccount);
					item.put("employeecode", employeecode);
					item.put("employeetypename", "会员");
					item.put("game_betting_amount", StringUtil.getDouble(obj.get("but_money")));
					
					double but_money = StringUtil.getDouble(obj.get("but_money"));
					double net_money = StringUtil.getDouble(obj.get("net_money"));
					double rebates_cash = StringUtil.getDouble(obj.get("rebates_cash")) ;//游戏输赢
					double activity_money = StringUtil.getDouble(obj.get("activity_money"));
					double depositMoney = StringUtil.getDouble(obj.get("depositMoney"));
					double withdrawMoney = StringUtil.getDouble(obj.get("withdrawMoney"));
					
					
					//存款手续费
					double dividend = MoneyHelper.moneyFormatDouble( depositMoney * Depositrate);
					//取款手续费
					double share = MoneyHelper.moneyFormatDouble( withdrawMoney * Takerate );
					
					//代理结算额  = 使用：占成代理结算方式一
					//公式=游戏输赢 * 公司占成 + 手续费 + 优惠 + 洗码；
					double profit_amount = MoneyHelper.moneyFormatDouble( net_money * curren_share  + dividend + share + activity_money + rebates_cash);
					
					item.put("game_betting_amount", but_money);
					item.put("activity_money", activity_money);
					item.put("lose_win_amount", net_money);
					item.put("bonus", rebates_cash);
					item.put("countMember", obj.get("countMember"));
					item.put("countAgent", 0);
					item.put("countEmployee", 0);
					item.put("dividend", dividend);
					item.put("share", share);
					item.put("profit_amount", profit_amount);
					item.put("depositMoney", depositMoney);
					item.put("withdrawMoney", withdrawMoney);
					item.put("remark", "直线会员汇总");
					listRecord.add(0,item);
					
				}
				
				// 分页数据
				pageModel(model, listRecord, listTotal, 3);
				
				// 查询的参数
				model.addAttribute("params", obj);
			}
		} catch (Exception e) {
			log.Error("H5后台，代理占成结算A 报错", e);
			return H5_ERROR_PAGE;
		}
		return "/h5/nbreportA";
	}
	/**
	 * 代理占成结算B
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/nbReportB")
	public String nbReportB(HttpServletRequest request, HttpSession session, Model model) {
		try {

			EnterpriseEmployee loginEmployee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			//获取当前用户的占成
			Enterprise enterprise = enterpriseService.selectByPrimaryKey(loginEmployee.getEnterprisecode());
			double curren_share = loginEmployee.getShare().doubleValue();
			double Depositrate = Double.valueOf(enterprise.getDepositrate());
			double Takerate = Double.valueOf(enterprise.getTakerate());
			
			
			Map<String, Object> obj = getRequestParamters(request);
			Object employeecode = obj.get("employeecode");
			String employeeType = StringUtil.getString(obj.get("employeeType"));//查会员时
			Object loginaccount = null;
			Object startDate = obj.get("startDate");
			Object endDate = obj.get("endDate");
			obj.remove("startDate");
			obj.remove("endDate");
			obj.remove("employeecode");
			
			// 初始化分页参数
			initPagination(obj, null, null);
			
			List<Map<String, Object>> listRecord = new ArrayList<Map<String,Object>>();
			
			if(employeeType.equals("T003")) {
				obj.put("employeetypecode", Type.会员.value);
				obj.put("parentemployeecode", employeecode);
				List<EnterpriseEmployee> listEnterpriseEmployee = enterpriseEmployeeServiceImpl.selectAll(obj);
				int listTotal = enterpriseEmployeeServiceImpl.selectAllCount(obj);
				
				
				for (EnterpriseEmployee enterpriseEmployee : listEnterpriseEmployee) {
					//查所有直线会员汇总数据
					obj.clear();
					obj.put("employeecode", enterpriseEmployee.getEmployeecode());
					obj.put("startDate", startDate);
					obj.put("endDate", endDate);
					enterpriseEmployeeServiceImpl.call_UserGameMemberReportOne(obj);
					Map<String, Object> item = new HashMap<String, Object>();
					item.put("loginaccount", enterpriseEmployee.getLoginaccount());
					item.put("employeecode", enterpriseEmployee.getEmployeecode());
					item.put("employeetypename", "个人会员");
					
					double but_money = StringUtil.getDouble(obj.get("but_money"));
					double net_money = StringUtil.getDouble(obj.get("net_money"));
					double rebates_cash = StringUtil.getDouble(obj.get("rebates_cash")) ;//游戏输赢
					double activity_money = StringUtil.getDouble(obj.get("activity_money"));
					double depositMoney = StringUtil.getDouble(obj.get("depositMoney"));
					double withdrawMoney = StringUtil.getDouble(obj.get("withdrawMoney"));
					
					
					//存款手续费
					double dividend = MoneyHelper.moneyFormatDouble( depositMoney * Depositrate);
					//取款手续费
					double share = MoneyHelper.moneyFormatDouble( withdrawMoney * Takerate );
					
					//代理结算额  = 使用：占成代理结算方式二
					//公式=（游戏输赢-手续费-优惠-洗码）* 代理占成；
					double profit_amount = MoneyHelper.moneyFormatDouble( Math.abs(net_money) - dividend - share - activity_money - rebates_cash) * curren_share;
					
					if(net_money > 0) {//如果是赢钱，大家都不用出钱
						profit_amount = 0;
					} else {
						profit_amount = 0 - profit_amount;
					}
					
					item.put("game_betting_amount", but_money);
					item.put("activity_money", activity_money);//优惠额
					item.put("lose_win_amount", net_money);
					item.put("bonus", rebates_cash);//洗码额
					item.put("countMember", 0);
					item.put("countAgent", 0);
					item.put("countEmployee", 0);
					item.put("depositMoney", depositMoney);//存款额
					item.put("withdrawMoney", withdrawMoney);//取款额
					item.put("remark", "会员个人汇总");
					
					item.put("dividend", dividend);
					item.put("share", share);
					item.put("profit_amount", profit_amount);
					
					listRecord.add(0,item);
				}

				// 分页数据
				pageModel(model, listRecord, listTotal, 3);
				
				// 查询的参数
				model.addAttribute("params", obj);
			} else {
//				paramObj.put("employeetypecode", Type.代理.value);
				List<String> employeetypecodes = new ArrayList<String>();
				employeetypecodes.add(Type.代理.value);
				employeetypecodes.add(Type.员工.value);
				obj.put("employeetypecodes", employeetypecodes);
				//查所有的直线代理团队汇总数据
				if(employeecode == null){
					obj.put("parentemployeecode", loginEmployee.getEmployeecode());
					loginaccount = loginEmployee.getLoginaccount();
					employeecode = loginEmployee.getEmployeecode();
				} else {
					obj.put("parentemployeecode", employeecode);
					loginEmployee = enterpriseEmployeeServiceImpl.takeEmployeeByCode(String.valueOf(employeecode));
					loginaccount = loginEmployee.getLoginaccount();
					employeecode = loginEmployee.getEmployeecode();
				}
				List<EnterpriseEmployee> listEnterpriseEmployee = enterpriseEmployeeServiceImpl.selectAll(obj);
				int listTotal = enterpriseEmployeeServiceImpl.selectAllCount(obj);
				
				for (EnterpriseEmployee enterpriseEmployee : listEnterpriseEmployee) {
					
					obj.clear();
					obj.put("employeecode", enterpriseEmployee.getEmployeecode());
					obj.put("startDate", startDate);
					obj.put("endDate", endDate);
					enterpriseEmployeeServiceImpl.call_UserGameAgentReport(obj);
					
					Map<String, Object> item = new HashMap<String, Object>();
					item.put("loginaccount", enterpriseEmployee.getLoginaccount());
					item.put("employeecode", enterpriseEmployee.getEmployeecode());
					item.put("employeetypename", enterpriseEmployee.getEmployeetypename());
					
					double but_money = StringUtil.getDouble(obj.get("but_money"));
					double net_money = StringUtil.getDouble(obj.get("net_money"));
					double rebates_cash = StringUtil.getDouble(obj.get("rebates_cash")) ;//游戏输赢
					double activity_money = StringUtil.getDouble(obj.get("activity_money"));
					double depositMoney = StringUtil.getDouble(obj.get("depositMoney"));
					double withdrawMoney = StringUtil.getDouble(obj.get("withdrawMoney"));
					
					
					//存款手续费
					double dividend = MoneyHelper.moneyFormatDouble( depositMoney * Depositrate);
					//取款手续费
					double share = MoneyHelper.moneyFormatDouble( withdrawMoney * Takerate );
					
					//代理结算额  = 使用：占成代理结算方式二
					//公式=（游戏输赢-手续费-优惠-洗码）* 代理占成；
					double profit_amount = MoneyHelper.moneyFormatDouble( Math.abs(net_money) - dividend - share - activity_money - rebates_cash) * (enterpriseEmployee.getShare().doubleValue());
					
					if(net_money > 0) {//如果是赢钱，大家都不用出钱
						profit_amount = 0;
					} else {
						profit_amount = 0 - profit_amount;
					}
					
					item.put("game_betting_amount", but_money);
					item.put("activity_money", activity_money);
					item.put("lose_win_amount", net_money);
					item.put("bonus", rebates_cash);
					item.put("countMember", obj.get("countMember"));
					item.put("countAgent", obj.get("countAgent"));
					item.put("countEmployee", obj.get("countEmployee"));
					item.put("dividend", dividend);
					item.put("share", share);
					item.put("profit_amount", profit_amount);
					item.put("depositMoney", depositMoney);
					item.put("withdrawMoney", withdrawMoney);
					item.put("remark", "直线代理团队汇总");
					if(enterpriseEmployee.getEmployeetypecode().equals(Type.员工.value)) {
						item.put("remark", "下级员工团队汇总");
					}
					listRecord.add(item);
				}
				
				if(!loginEmployee.getEmployeetypecode().equals(Type.会员.value)) {
					//查所有直线会员汇总数据
					obj.clear();
					obj.put("employeecode", employeecode);
					obj.put("startDate", startDate);
					obj.put("endDate", endDate);
					enterpriseEmployeeServiceImpl.call_UserGameMemberReport(obj);
					Map<String, Object> item = new HashMap<String, Object>();
					item.put("loginaccount", loginaccount);
					item.put("employeecode", employeecode);
					item.put("employeetypename", "会员");
					item.put("game_betting_amount", StringUtil.getDouble(obj.get("but_money")));
					
					double but_money = StringUtil.getDouble(obj.get("but_money"));
					double net_money = StringUtil.getDouble(obj.get("net_money"));
					double rebates_cash = StringUtil.getDouble(obj.get("rebates_cash")) ;//游戏输赢
					double activity_money = StringUtil.getDouble(obj.get("activity_money"));
					double depositMoney = StringUtil.getDouble(obj.get("depositMoney"));
					double withdrawMoney = StringUtil.getDouble(obj.get("withdrawMoney"));
					
					
					//存款手续费
					double dividend = MoneyHelper.moneyFormatDouble( depositMoney * Depositrate);
					//取款手续费
					double share = MoneyHelper.moneyFormatDouble( withdrawMoney * Takerate );
					
					//代理结算额  = 使用：占成代理结算方式二
					//公式=（游戏输赢-手续费-优惠-洗码）* 代理占成；
					double profit_amount = MoneyHelper.moneyFormatDouble( Math.abs(net_money) - dividend - share - activity_money - rebates_cash) * curren_share;
					
					if(net_money > 0) {//如果是赢钱，大家都不用出钱
						profit_amount = 0;
					} else {
						profit_amount = 0 - profit_amount;
					}
					
					item.put("game_betting_amount", but_money);
					item.put("activity_money", activity_money);
					item.put("lose_win_amount", net_money);
					item.put("bonus", rebates_cash);
					item.put("countMember", obj.get("countMember"));
					item.put("countAgent", 0);
					item.put("countEmployee", 0);
					item.put("dividend", dividend);
					item.put("share", share);
					item.put("profit_amount", profit_amount);
					item.put("depositMoney", depositMoney);
					item.put("withdrawMoney", withdrawMoney);
					item.put("remark", "直线会员汇总");
					listRecord.add(0,item);
					
				}
				// 分页数据
				pageModel(model, listRecord, listTotal, 3);
				
				// 查询的参数
				model.addAttribute("params", obj);
			}
		} catch (Exception e) {
			log.Error("H5后台，代理占成结算B 报错", e);
			return H5_ERROR_PAGE;
		}
		return "/h5/nbreportB";
	}
	
	/**
	 * 冲正冲负
	 * @param model
	 * @return
	 */
	@RequestMapping("/plusLessMinute")
	public String plusLessMinute(Model model) {
		model.addAttribute("moneyaddtypes", EmployeeMoneyChangeType.Enum_moneyaddtype.values());
		model.addAttribute("menuNo", 3);
		return "/h5/pluslessminute";
	}
	/**
	 * 提交冲正冲负数据
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestInterval(millinsecond = 9000)
	@RequestMapping("/savePlusLessMinute")
	@OperationLog(OparetionDescription.PLUS_LESS)
	public Map<String, Object> savePlusLessMinute(HttpServletRequest request, HttpSession session) {
		try {
			EmployeeMoneyChangeType type = new EmployeeMoneyChangeType();
			// 获取当前登录用户对象
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			// 需要冲值的用户编码
			String employee = request.getParameter("employeecode");
//			String loginaccount = request.getParameter("loginaccount");
			EnterpriseEmployee targetUser = enterpriseEmployeeService.takeEmployeeByCode(employee);
			// EnterpriseEmployee targetUser =
			// enterpriseEmployeeService.queryEmployeeByLoginaccount(loginaccount);
			if (targetUser == null) {
				return super.packJSON(Constant.BooleanByte.NO, "操作失败。未能根据用户账号查找到对应账户信息！");
			}
			if (!targetUser.getEnterprisecode().equals(ee.getEnterprisecode())) {
				return super.packJSON(Constant.BooleanByte.NO, "操作失败。没有权限对其他企业的账号进行冲账操作！");
			}

			// 金额
			BigDecimal money = null;
			// 类型
			Byte moneychangetype = Byte.valueOf(request.getParameter("plusLess"));
			if (moneychangetype == Enum_moneyinouttype.进账.value) {
				type.setMoneyinouttype((byte) Enum_moneyinouttype.进账.value);
				type.setMoneychangetypecode(Enum_moneychangetype.冲正.value);
				money = BigDecimal.valueOf(Double.valueOf(request.getParameter("money")));
			} else if (moneychangetype == Enum_moneyinouttype.出账.value) {
				type.setMoneyinouttype((byte) Enum_moneyinouttype.出账.value);
				type.setMoneychangetypecode(Enum_moneychangetype.冲负.value);
				money = BigDecimal.valueOf(Double.valueOf("-" + request.getParameter("money")));
			}

			String moneyaddtype = request.getParameter("moneyaddtype");
//			String moneyaddtypeName = request.getParameter("moneyaddtypeName");
			String remark = request.getParameter("remark");

			String orderid = RandomString.UUID();
			enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(orderid, employee, money, type,
					"操作人:" + ee.getLoginaccount() + " " + remark, moneyaddtype);

			userLogsService.addActivityBetRecord(new UserLogs(targetUser.getEnterprisecode(),
					targetUser.getEmployeecode(), targetUser.getLoginaccount(), UserLogs.Enum_operatype.活动充值业务,
					"员工提交冲正冲负:" + orderid, ee.getLoginaccount(), remark));

			/************* 加入到优惠流水打码要求那里 ***********/
			String lsbs = request.getParameter("lsbs");
			double betMultiple = Double.valueOf(lsbs);
			if (moneychangetype == Enum_moneyinouttype.进账.value && betMultiple > 0) {

				ActivityBetRecord betrecord = new ActivityBetRecord();
				betrecord.setEmployeecode(targetUser.getEmployeecode());
				betrecord.setEcactivitycode(Enum_ecactivitycode.存款流水.value);
				betrecord.setMustbet(money.doubleValue() * betMultiple);
				betrecord.setAlreadybet(0.0);
				betrecord.setBetrecordstatus(Enum_betrecordstatus.未完成.value);
				betrecord.setCreatetime(new Date());
				betrecord.setLoginaccount(targetUser.getLoginaccount());
				betrecord.setRecharge(money.doubleValue());// 充值金额

				betrecord.setEnterprisecode(targetUser.getEnterprisecode());// 企业编码
				betrecord.setBrandcode(targetUser.getBrandcode());// 品牌编码
				betrecord.setParentemployeeaccount(targetUser.getParentemployeeaccount());// 上级账号
				betrecord.setParentemployeecode(targetUser.getParentemployeecode());// 上级编码
				activityBetRecordService.addActivityBetRecord(betrecord);
				System.out.println(targetUser.getLoginaccount() + "冲正增加打码");

			}

			return super.packJSON(Constant.BooleanByte.YES, "操作成功");
		} catch (Exception e) {
			log.Error("提交冲正冲负数据报错", e);
		}
		return super.packJSON(Constant.BooleanByte.NO, "操作失败");
	}
	@RequestMapping("/userBalance")
	public String userBalance(Model model) {
		model.addAttribute("menuNo", 3);
		return "h5/userbalance";
	}
	/**
	 * 创建会员页面
	 * @return
	 */
	@RequestMapping("/userAdd")
	public String userAdd(HttpSession session, Model model) {
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			// 加载游戏、分红、占成信息
			List<Game> games = gameService.takeEnterpriseGames(ee.getEnterprisecode());
			Map<String,BigDecimal> bonus = employeeGamecataloyBonusService.takeEmployeeGameCategoryBonusMap(ee.getEmployeecode());
			
			model.addAttribute("bonus", bonus);
			model.addAttribute("games", games);
			model.addAttribute("employee", ee);
		} catch (Exception e) {
			log.Error("跳转创建会员页面报错!", e);
			return H5_ERROR_PAGE;
		}
		return "/h5/useradd";
	}
	
	/**
	 * 
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("showUser")
	public String showUser(HttpServletRequest request, HttpSession session, Model model) {
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			String employeecode = request.getParameter("employeecode");
			model.addAttribute("sign", employeecode);
			if (decodeSign(employeecode, session.getId())) {
				employeecode = employeecode.split("_")[1];

				Map<String, Object> params = new HashMap<String, Object>();

				params.put("employeecode", employeecode);
				params.put("enterprisecode", ee.getEnterprisecode());
				super.dataLimits(ee, params, session);
				EnterpriseEmployee user = enterpriseEmployeeService.takeEnterpriseEmployee(params);
				if (user == null) {
					model.addAttribute("message", "用户账号错误!");
					return Constant.PAGE_LOGICVALIDATEFIAL;
				} else if (!user.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.会员.value)) {
					model.addAttribute("message", "非会员用户!");
					return Constant.PAGE_LOGICVALIDATEFIAL;
				} else {
					/* ==========获取用户游戏账号信息========== */

					Map<String, String> BalanceAll = new APIServiceNew(user.getEnterprisecode())
							.balancesAll(user.getEmployeecode(), user.getBrandcode());

					List<EmployeeApiAccout> userGameAccountList = employeeApiAccoutService
							.selectUnionGName(new EmployeeApiAccout(employeecode));
					for (EmployeeApiAccout __eaa : userGameAccountList) {
						String balancex = BalanceAll.get(__eaa.getGametype());
						if (balancex == null || !StringUtil.isNumberFloat(balancex)) {
							balancex = "0.00";
						}
						if (null == session.getAttribute(Constant.PRIVATE_DATA_PSERSSION)) {
							__eaa.setGamepassword("******");
						}
						__eaa.setBalance(new BigDecimal(MoneyHelper.doubleFormat(balancex)));
						__eaa.setSign(super.encryptString(
								__eaa.getEnterprisecode() + "|" + __eaa.getEmployeecode() + "|" + __eaa.getGametype(),
								session.getId()));
					}
					model.addAttribute("gameAccounts", userGameAccountList);
					
					/* ==========获取用户余额转分信息========== */
					// 通过Ajax获取
					
					
					model.addAttribute("employeecode", employeecode);
					model.addAttribute("loginaccount", user.getLoginaccount());
				}
			} else {
				return Constant.PAGE_DECODEREFUSE;
			}
		} catch (Exception e) {
			log.Error("跳转查看用户信息页面报错", e);
			return H5_ERROR_PAGE;
		}
		return "/h5/showuser";
	}
	@RequestMapping("/agentPointTransfer")
	public String agentPointTransfer(HttpServletRequest request, HttpSession session, Model model) {
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			String employeecode = request.getParameter("employeecode");
			model.addAttribute("sign", employeecode);
			if (decodeSign(employeecode, session.getId())) {
				employeecode = employeecode.split("_")[1];
				Map<String, Object> params = new HashMap<String, Object>();

				params.put("employeecode", employeecode);
				params.put("enterprisecode", ee.getEnterprisecode());
				super.dataLimits(ee, params, session);
				EnterpriseEmployee agent = enterpriseEmployeeService.takeEnterpriseEmployee(params);
				
				if (agent == null) {
					model.addAttribute("message", "用户账号错误!");
					return Constant.PAGE_LOGICVALIDATEFIAL;
				} else {
					model.addAttribute("loginaccount", agent.getLoginaccount());
				}
			} else {
				return Constant.PAGE_DECODEREFUSE;
			}
		} catch (Exception e) {
			log.Error("跳转到代理转分页面报错", e);
			return H5_ERROR_PAGE;
		}
		return "/h5/agentpointtransfer";
	}
	/**
	 * 当前页
	 */
	private int pageIndex;
	/**
	 * 页数
	 */
	private int pageNum;
	/**
	 * 每页数量
	 */
	private int limit;
	/**
	 * MySQL查询使用，公式：(pageIndex-1)*limit
	 */
	private int start;
	/**
	 * 排序字段
	 */
	private String field;
	/**
	 * 排序方式
	 */
	private String direction;
	/**
	 * 初始化分页参数
	 * @param __obj
	 * @param field
	 * @param direction
	 */
	private void initPagination(Map<String,Object> __obj, String __field, String __direction) {
		this.pageIndex = 1;
		this.limit = 15;
		this.start = (pageIndex-1)*limit;
		this.field = __field;
		this.direction = __direction;
		if(__obj.get("pageIndex") == null || __obj.get("limit") == null) {
			__obj.put("start", start);
			__obj.put("limit", limit);
			__obj.put("pageIndex", pageIndex-1);
			__obj.put("field", field);
			__obj.put("direction", direction);
		} else {
			pageIndex = Integer.parseInt(__obj.get("pageIndex").toString());
			limit = Integer.parseInt(__obj.get("limit").toString());
			start = (pageIndex-1)*limit;
			__obj.put("start", start);
			if(__obj.get("field") != null && __obj.get("direction") != null) {
				this.field = __obj.get("field").toString();
				this.direction = __obj.get("direction").toString();
			}
		}
	}
	/**
	 * 获取页数
	 * @param listTotal
	 * @return
	 */
	private void pageNum(int listTotal){
		this.pageNum = (int) (listTotal / limit);
		if((listTotal%limit) > 0) pageNum++;
	}
	/**
	 * 分页数据
	 * @param model		Model
	 * @param list		数据list
	 * @param listTotal 数据总数
	 * @param menuNo	菜单No.
	 */
	private void pageModel(Model model, List<?> list, int listTotal, int menuNo) {
		// 算出总页数
		pageNum(listTotal);
		model.addAttribute("limit", limit);
		model.addAttribute("list", list);
		model.addAttribute("listCount", listTotal);//数据条数
		model.addAttribute("pageIndex",pageIndex<=pageNum?pageIndex:pageNum);//当前页数
		model.addAttribute("pageNum", pageNum);//页数
		if (this.field != null && this.direction != null) {
			model.addAttribute("field", this.field);
			model.addAttribute("direction", this.direction);
		}
		// 用于页面上以及菜单的展开
		model.addAttribute("menuNo", menuNo);
	}
	@Override
	public LoggerManager getLogger() { return log; }
}