package com.maven.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.annotation.OperationLog;
import com.maven.annotation.RequestInterval;
import com.maven.cache.SystemCache;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.constant.Enum_MSG;
import com.maven.entity.ApiSoltGametype;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EmployeeAvailable;
import com.maven.entity.EmployeeGamecataloyBonus;
import com.maven.entity.EmployeeMappingMenu;
import com.maven.entity.EmployeeMappingRole;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployee.Enum_employeestatus;
import com.maven.entity.EnterpriseEmployee.Enum_ipstatus;
import com.maven.entity.EnterpriseEmployee.Enum_onlinestatus;
import com.maven.entity.EnterpriseEmployeeInformation;
import com.maven.entity.EnterpriseEmployeeLevel;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.EnterpriseEmployeeType.Type;
import com.maven.entity.EnterpriseGame;
import com.maven.entity.EnterpriseOperatingBrandGame;
import com.maven.entity.Game;
import com.maven.entity.LogLogin;
import com.maven.entity.LoginWhiteList;
import com.maven.entity.PermissionMenu;
import com.maven.entity.PrivateDataAccess;
import com.maven.entity.TakeDepositRecord;
import com.maven.entity.UserLogs;
import com.maven.entity.WorkingFlowConfiguration;
import com.maven.entity.WorkingFlowObject;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.game.APIServiceNew;
import com.maven.game.APIServiceUtil;
import com.maven.game.enums.GameEnum.Enum_GameType;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.ApiSoltGametypeService;
import com.maven.service.BusinessEmployeeLovelService;
import com.maven.service.EmployeeAvailableService;
import com.maven.service.EmployeeGamecataloyBonusService;
import com.maven.service.EmployeeMappingMenuService;
import com.maven.service.EmployeeMappingRoleService;
import com.maven.service.EnterpriseEmployeeInformationService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseGameService;
import com.maven.service.EnterpriseService;
import com.maven.service.GameService;
import com.maven.service.LogLoginService;
import com.maven.service.LoginWhiteListService;
import com.maven.service.PermissionMenuService;
import com.maven.service.PrivateDataAccessService;
import com.maven.service.TakeDepositRecoredService;
import com.maven.service.UserLogsService;
import com.maven.service.WorkingFlowConfigurationService;
import com.maven.service.WorkingFlowObjectService;
import com.maven.util.AttrCheckout;
import com.maven.util.DateUtils;
import com.maven.util.Encrypt;
import com.maven.util.WebInfoHandle;
import com.maven.utils.StringUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/EEmployee")
public class EnterpriseEmployeeController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			EnterpriseEmployeeController.class.getName(), OutputManager.LOG_ENTERPRISEEMPLOYEE);
	/** 员工角色映射 */
	@Autowired
	private EmployeeMappingRoleService employeeMappingRoleService;
	/** 企业员工  */
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	/** 菜单权限组 */
	@Autowired
	private PermissionMenuService permissionMenuService;
	/** 权限映射 */
	@Autowired
	private EmployeeMappingMenuService employeeMappingMenuService;
	/** 工作流审核权限 */
	@Autowired
	private WorkingFlowObjectService workingFlowObjectService;
	/** 会员接入品台游戏返点 */
	@Autowired
	private EmployeeGamecataloyBonusService employeeGamecataloyBonusService;
	/** 登陆日志 */
	@Autowired
	private LogLoginService logLoginService;
	/** 系统接入游戏 */
	@Autowired
	private GameService gameService;
	/** 企业游戏列表 **/
	@Autowired
	private EnterpriseGameService enterpriseGameService;
	/** 隐私数据权限 */
	@Autowired
	private PrivateDataAccessService privateDataAccessService;
	/** 用户信息*/
	@Autowired
	private EnterpriseEmployeeInformationService enterpriseEmployeeInformationService;
	/** 用户有效时间 */
	@Autowired
	private EmployeeAvailableService employeeAvailableService;
	@Autowired
	private LoginWhiteListService loginWhiteListService;
	@Autowired 
	private BusinessEmployeeLovelService businessEmployeeLovelService;
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private UserLogsService userLogsService;
	/** 洗码*/
	@Autowired
	private EmployeeGamecataloyBonusService employeeGamecataloyBonusServiceImpl;
	/** 隐私数据限制 */
	@Autowired
	private PrivateDataAccessService dataAccessService;
	@Autowired
	private TakeDepositRecoredService takeDepositRecoredService;
	@Autowired
	private WorkingFlowConfigurationService workingFlowConfigurationService;
	@Autowired
	private ApiSoltGametypeService apiSoltGametypeService;
	
	/**
	 * 推送
	 * @return
	 */
	@RequestMapping("/push")
	public String push(){
		return "push";
	}
	/**
	 * 密码修改页面
	 * @return
	 */
	@RequestMapping("/userJsp/modifyPassword")
	public String userModify(){
		return "/userjsp/modifyPassword";
	}
	/**
	 * 我的会员列表页
	 * @param model
	 * @return
	 */
	@RequestMapping("/userJsp/index")
	public String forwordIndex(Model model,HttpServletRequest request){
		//用户注册报表点击查看详情需要用到该参数
		model.addAttribute("createDate", request.getParameter("createDate"));
		return "/userjsp/userindex";
	}
	
	
	/**
	 * 会员信息汇总
	 * @return
	 */
	@RequestMapping(value={"/userJsp/allinfo", "/allinfo"})
	public String allinfo(Model model,HttpServletRequest request){
		try {
			String employeecode = request.getParameter("employeecode");
			String loginaccount = request.getParameter("loginaccount");
			
			EnterpriseEmployee __ee = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
			
			Map<String, Object> paramObj = new HashMap<String, Object>();
			paramObj.put("employeecode", employeecode);
			paramObj.put("last_deposit_time", employeecode);
			paramObj.put("last_deposit_money", employeecode);
			paramObj.put("last_deposit_ordernumber", employeecode);
			paramObj.put("first_deposit_time", employeecode);
			paramObj.put("first_deposit_money", employeecode);
			paramObj.put("first_deposit_ordernumber", employeecode);
			paramObj.put("all_deposit_money", employeecode);
			paramObj.put("all_deposit_count", employeecode);
			paramObj.put("last_take_time", employeecode);
			paramObj.put("last_take_money", employeecode);
			paramObj.put("last_take_ordernumber", employeecode);
			paramObj.put("first_take_time", employeecode);
			paramObj.put("first_take_money", employeecode);
			paramObj.put("first_take_ordernumber", employeecode);
			paramObj.put("all_take_money", employeecode);
			paramObj.put("all_take_count", employeecode);
			takeDepositRecoredService.call_userAllInfoMoney(paramObj);
			paramObj = com.maven.util.StringUtils.getMapToValueZero(paramObj);
			
			
			Map<String, Object> paramObj2 = new HashMap<String, Object>();
			paramObj2.put("employeecode", employeecode);
			paramObj2.put("pmoney_acvity", employeecode);
			paramObj2.put("pmoney_daily", employeecode);
			paramObj2.put("pmoney_up_fail", employeecode);
			paramObj2.put("pmoney_add", employeecode);
			paramObj2.put("pmoney_sub", employeecode);
			List<TakeDepositRecord> list = takeDepositRecoredService.call_userAllInfoGame(paramObj2);
			paramObj2 = com.maven.util.StringUtils.getMapToValueZero(paramObj2);
			
			/*********查各个余额***********/
			List<Game> BrandGameAll = gameService.takeBrandGames(__ee.getBrandcode(),EnterpriseOperatingBrandGame.Enum_flag.正常.value);
			List<Map<String, String>> returnObject = new ArrayList<Map<String,String>>();
			Map<String, String> BalanceAll = new APIServiceNew(__ee.getEnterprisecode()).balancesAll(__ee.getEmployeecode(), __ee.getBrandcode());
			
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
			
			// 隐私数据权限
			if(null == request.getSession().getAttribute(Constant.PRIVATE_DATA_PSERSSION)) {
				__ee.setPhoneno("***********");
				__ee.setQq("***********");
				__ee.setEmail("***********");
				__ee.setWechat("***********");
			}
			
			
			if(__ee.getEmployeelevelcode() != null && !__ee.getEmployeelevelcode().equals("")) {
				EnterpriseEmployeeLevel employeeLevel = businessEmployeeLovelService.getOneObject(__ee.getEmployeelevelcode());
				if(employeeLevel != null) {
					String name = StringUtil.getString(employeeLevel.getEmployeeLevelName());
					if(name.equals("")) {
						name = "未设置";
					}
					__ee.setEmployeelevelname(name);
				}
			}
			
			model.addAttribute("result", paramObj);
			model.addAttribute("result2", paramObj2);
			model.addAttribute("list", list);
			model.addAttribute("__ee", __ee);
			model.addAttribute("listBalance", returnObject);
			
			System.out.println(paramObj);
			System.out.println(paramObj2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/userjsp/allinfo";
	}
	
	
	/**
	 * 用户信息列表页
	 * @param model
	 * @return
	 */
	@RequestMapping("/userJsp/employeeData")
	public String employeeData(Model model,HttpServletRequest request){
		//用户注册报表点击查看详情需要用到该参数
		model.addAttribute("createDate", request.getParameter("createDate"));
		
		Map<String, String> mapEnterpriseEmployeeLevel = new HashMap<String, String>();
		EnterpriseEmployee ee = (EnterpriseEmployee)request.getSession().getAttribute(Constant.USER_SESSION);
		Map<String,Object> object = new HashMap<String, Object>();
		object.put("enterpriseCode", ee.getEnterprisecode());
		
		try {
			List<EnterpriseEmployeeLevel> list =  businessEmployeeLovelService.takelevelQuery(object);
			for (EnterpriseEmployeeLevel enterpriseEmployeeLevel : list) {
				mapEnterpriseEmployeeLevel.put(enterpriseEmployeeLevel.getEmployeeLevelCode(), enterpriseEmployeeLevel.getEmployeeLevelName());
			}
			model.addAttribute("mapEnterpriseEmployeeLevel", mapEnterpriseEmployeeLevel);	
			model.addAttribute("listEnterpriseEmployeeLevel", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "/userjsp/employeeData";
	}
	
	/**
	 * 登出系统
	 * @param session
	 * @return
	 */
	@RequestMapping("/exit")
	public String exit(HttpSession session){
		session.invalidate();
		
		return "redirect:/";
	}
	/**
	 * 用户登入(除会员类型的账户不能登录,其他用户类型都可以登录)
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping("/login")
	public String login(Model model, HttpServletRequest request, HttpServletResponse response,HttpSession session){
		try {
//			System.out.println("Referer="+request.getHeader("Referer"));
			log.Debug("请求进入登陆方法");
			Map<String,Object> obj = getRequestParamters(request);
			
			String loginaccount = String.valueOf(obj.get("loginaccount"));
			String loginpassword = String.valueOf(obj.get("loginpassword"));
			
			if(com.maven.util.StringUtils.isCharAllOrNumberAll(loginpassword)) {
				model.addAttribute("msg", "不能包含特殊字符，允许输入字母与数字的组合");
				return "/login";
			}
			
			String verifyCode = ((String)session.getAttribute("verifyCode")).split("_")[0];
			String timestamp = ((String)session.getAttribute("verifyCode")).split("_")[1];
			boolean isTimeOut = System.currentTimeMillis()/1000 - Long.valueOf(timestamp) > 60;
			if (isTimeOut) {
				model.addAttribute("loginaccount", loginaccount);
				model.addAttribute("loginpassword", loginpassword);
				model.addAttribute("msg", "验证码过期");
				return "/login";
			}
			String loginCode = (String)obj.get("verifyCode");
			if(StringUtils.isBlank(verifyCode) ||StringUtils.isBlank(loginCode)
					||!verifyCode.equals(loginCode.toUpperCase())){
				model.addAttribute("loginaccount", loginaccount);
				model.addAttribute("loginpassword", loginpassword);
				model.addAttribute("msg", "验证码错误");
				return "/login";
			}
			
					
			//加密之后密码比较
			obj.put("loginpassword",Encrypt.MD5(loginpassword));
			//1:有效用户，-1:无效的用户
			obj.put("datastatus",Constant.Enum_DataStatus.正常.value);
			//(1.启用 2.锁定游戏 3.禁用)
			obj.put("employeestatus", EnterpriseEmployee.Enum_employeestatus.启用.value);
			
			
			List<EnterpriseEmployee> users = enterpriseEmployeeService.getThisLoginEmployeeMsg(obj);
			if(users.size()==1){
				//保存用户信息
				EnterpriseEmployee employee = users.get(0);
				if(employee.getEmployeetypecode().equals(Type.会员.value)){
					model.addAttribute("msg", "会员类型的用户不能登录");
					return "/login";
				}
				String loginIp = WebInfoHandle.getClientRealIP(request);
				
				//检查登录IP是否在所属企业的白名单下（//企业号/员工号）
				if( !employee.getEmployeetypecode().equals(Type.代理.value) && !employee.getEmployeetypecode().equals(Type.信用代理.value)){
						
					String enterprisecode = employee.getEnterprisecode();
					Enterprise enterprise = enterpriseService.selectByPrimaryKey(enterprisecode);
					
					String ipstatus = employee.getIpstatus();//针对具体的每个用户也可以管理
					if(ipstatus == null) {
						ipstatus = EnterpriseEmployee.Enum_ipstatus.启用.value;
					}
					
					if(enterprise.getIpenable().equals(Enterprise.Enum_ipenable.启用.value) && ipstatus.equals(EnterpriseEmployee.Enum_ipstatus.启用.value)) {
						
						Map<String, Object> parameter = getRequestParamters(request);
						parameter.put("ip", loginIp);
						parameter.put("enterprisecode", enterprisecode);
						List<LoginWhiteList> list = loginWhiteListService.selectAll(parameter);
						if(list.size() < 1){
							System.err.println(loginIp);
							model.addAttribute("msg", "IP不在许可登录范围！");
							return "/login";
						}
					}
					
				}
				
				if(employee.getLoginpassword2() == null || employee.getLoginpassword2().equals("")) {
					//初始化
					Map<String, Object> object = new HashMap<String, Object>();
					object.put("loginpassword", Encrypt.MD5(loginpassword));
					object.put("loginpassword2", APIServiceUtil.encrypt(loginpassword, employee));//对原始密码进行加密
					object.put("employeecode", employee.getEmployeecode());
					int status = enterpriseEmployeeService.tc_restPassword(AttrCheckout.checkout(object,true,new String[]{"loginpassword","employeecode","loginpassword2"}));
					employee.setLoginpassword2(object.get("loginpassword2").toString());
				}
				// 用户信息存入session
				session.setAttribute(Constant.USER_SESSION,employee);
				
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
				
				//记录登录日志
				String opareteSystem = WebInfoHandle.getRequestSystem(request);
				String browser = WebInfoHandle.getBrowser(request);
				String machinecode =  request.getParameter("fingerprintcode");
				String referer = WebInfoHandle.getReferer(request);
				logLoginService.addLoginLog(new LogLogin(employee.getEnterprisecode(),employee.getBrandcode(),employee.getEmployeecode(),employee.getParentemployeecode(),employee.getLoginaccount(),
						new Date(),loginIp,machinecode,opareteSystem,browser,referer));
				log.Debug("登陆通过");
				
				//加载语言
				String language = String.valueOf(obj.get("language"));
				session.setAttribute(Constant.LANGUAGE, language);
				
				return "redirect:/EEmployee/index";
			}else{
				log.Debug("账号错误,登陆失败");
				model.addAttribute("msg", "用户名或密码错误");
				return "/login";
			}
		}catch (Exception e) {
			if(session.getAttribute(Constant.USER_SESSION)!=null) session.removeAttribute(Constant.USER_SESSION);
			model.addAttribute("msg", "登陆失败，请稍后尝试");
			log.Error(e.getMessage(), e);
		}
		return "/login";
	}
	public static void main(String[] args) {
//		BigDecimal userbonus = new BigDecimal(0);
//		BigDecimal superbonus = new BigDecimal(0);
//		System.out.println(userbonus.doubleValue() > superbonus.doubleValue());
		
		Date date = new Date(1498462419000l);
		System.out.println(date.toLocaleString());
	}
	
	/**
	 * 
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateqt")
	@ResponseBody
	public void updateqt(HttpServletRequest request){
		try {
			Map<String,Object> parameter = new HashMap<String, Object>();
			parameter.put("biggametype", "MGGame");
			List<ApiSoltGametype> list = apiSoltGametypeService.selectAll(parameter);
			System.out.println("共找到"+list.size() + "个");
			
			int index = 0;
			String dir = "F:/需求文档/MG最新图片/MG游戏图片集合/";
			File file = null;
			for (ApiSoltGametype apiSoltGametype : list) {
				
				String filename = dir+apiSoltGametype.getImagename()+".jpg";
				
				file = new File(filename);
				if(file.exists()) {
					//System.out.println(index+"=找到了图片：" + filename);
					index ++;
					//file.renameTo(new   File(dir+apiSoltGametype.getImagename()+".jpg"));
				} else {
					filename = dir+apiSoltGametype.getImagename()+"-min.jpg";
					file = new File(filename);
					if(file.exists()) {
						file.renameTo(new   File(dir+apiSoltGametype.getImagename()+".jpg"));
					} else {
						System.out.println(apiSoltGametype.getLsh()+"=====没有找到图片：" + apiSoltGametype.getImagename());
					}
					
				}
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 查询在线用户数量
	 * 
	 *"/EEmployee/onlineAll"
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/onlineAll")
	@ResponseBody
	public Map<String,Object> onlineAll(HttpServletRequest request){
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			EnterpriseEmployee ee = (EnterpriseEmployee)request.getSession().getAttribute(Constant.USER_SESSION);
			if(ee == null) {
				map.put("count", 0);
				map.put("result", map);
				return map;
			}
			
			String enterprisecode = Encrypt.MD5("userlogintoken_"+ee.getEnterprisecode());
			Map<String,String> data1 = SystemCache.getInstance().getEmployeeOnlineAllWEB(enterprisecode);
			Map<String,String> data2 = SystemCache.getInstance().getEmployeeOnlineAllH5(enterprisecode);
			
			Map<String,String> data = new HashMap<String, String>();
			
			if(data1 != null) {
				Iterator<String> iterator = data1.keySet().iterator();
				while(iterator.hasNext()){    
				     String key = iterator.next().toString();
				     String value = data1.get(key);//存入会话的时间戳。10位数
				     data.put(key, value+"-WEB");
				}  
			}
			
			if(data2 != null) {
				Iterator<String> iterator = data2.keySet().iterator();
				while(iterator.hasNext()){    
				     String key = iterator.next().toString();
				     String value = data2.get(key);//存入会话的时间戳。10位数
				     if(data.containsKey(key)) {
				    	 data.put(key, value+"-WEB-H5");
				     } else {
				    	 data.put(key, value+"-H5");
				     }
				}  
			}
			
			map.put("results", data == null ? 0 : data.size());
			map.put("rows", data);
			
			return map;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 在线用户首页
	 * @return
	 */
	@RequestMapping("/onlineIndex")
	public String onlineIndex(HttpServletRequest request){
		
		/**
		EnterpriseEmployee ee = (EnterpriseEmployee)request.getSession().getAttribute(Constant.USER_SESSION);
		String __employeecode = ee.getEmployeecode();
		String smscode = SystemCache.getInstance().getSMScode(__employeecode);
		
		if(smscode == null) {
			smscode = RandomStringNum.createRandomString(6);
			SystemCache.getInstance().setSMScode(__employeecode, smscode);
			System.out.println("随机生成验证码："+smscode);
		}
		System.out.println(__employeecode+"的验证码是："+smscode);
		*/
		
		return "/userjsp/onlineIndex";
	}
	/**
	 * 查询在线用户列表
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/onlineData")
	@ResponseBody
	public Map<String,Object> onlineData(HttpServletRequest request){
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			EnterpriseEmployee ee = (EnterpriseEmployee)request.getSession().getAttribute(Constant.USER_SESSION);
			if(ee == null) {
				map.put("count", 0);
				map.put("result", map);
				return map;
			}
			
			String enterprisecode = Encrypt.MD5("userlogintoken_"+ee.getEnterprisecode());
			Map<String,String> data1 = SystemCache.getInstance().getEmployeeOnlineAllWEB(enterprisecode);
			Map<String,String> data2 = SystemCache.getInstance().getEmployeeOnlineAllH5(enterprisecode);
			
			if(ee.getLoginaccount().equals("dwshichang")) {//调试用
				System.out.println("data1="+data1.size());
				System.out.println("data2="+data2.size());
			}
			Map<String,String> data = new HashMap<String, String>();
			
			if(data1 != null) {
				Iterator<String> iterator = data1.keySet().iterator();
				while(iterator.hasNext()){    
				     String key = iterator.next().toString();
				     String value = data1.get(key);//存入会话的时间戳。10位数
				     data.put(key, value+"-WEB");
				}  
			}
			
			if(data2 != null) {
				Iterator<String> iterator = data2.keySet().iterator();
				while(iterator.hasNext()){    
				     String key = iterator.next().toString();
				     String value = data2.get(key);//存入会话的时间戳。10位数
				     if(data.containsKey(key)) {
				    	 data.put(key, value+"-WEB,H5");
				     } else {
				    	 data.put(key, value+"-H5");
				     }
				}  
			}
			
			String[] array = new String[data.size()];
			Iterator<String> iterator = data.keySet().iterator();
			int index = 0;
			while(iterator.hasNext()){    
			     String key = iterator.next().toString();
			     String value = data.get(key);//存入会话的时间戳。10位数
			     array[index] = key;
			     index ++;
			}  
			if(ee.getLoginaccount().equals("dwshichang")) {//调试用
				System.out.println("data="+data.size());
				System.out.println("data="+data);
			}
			
			//账号/姓名/最后登录时间/登录端
			if(array.length > 0) {
				Date date = new Date();
				List<EnterpriseEmployee> list = enterpriseEmployeeService.findEmployeeByCodes(array);
				if (list != null && list.size() > 0) {
					for (EnterpriseEmployee enterpriseEmployee : list) {
						String type = data.get(enterpriseEmployee.getEmployeecode()).split("-")[1];
						String time = data.get(enterpriseEmployee.getEmployeecode()).split("-")[0];
						time = time + "000";
						enterpriseEmployee.setEnterprisename(type);//登录端
						date = new Date(Long.valueOf(time));
						enterpriseEmployee.setLastlogintime(date);//最后操作时间。注意，这里并不是最后登录时间，而是最后操作时间。
					} 
				}
				map.put("results", list == null ? 0 : list.size());
				map.put("rows", list);
				return map;
			}
			
			map.put("results", data == null ? 0 : data.size());
			map.put("rows", new ArrayList<EnterpriseEmployee>());
			
			return map;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return null;
	}
	/**
	 * 切换系统语言
	 * @param request
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/language")
	public Map<String, Object> language(HttpServletRequest request, HttpSession session) {
		String language = request.getParameter("language");
		session.setAttribute(Constant.LANGUAGE, language);
		return packJSON(Constant.BooleanByte.YES, "修改语言成功");
	}
	/**
	 * 主页面
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/index")
	public String index(Model model,HttpSession session){
		try {
			log.Debug("进入后台主页面加载方法");
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			if(ee==null) return "redirect:/";
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
			
			//加载用户存款审核权限
			int count1 = workingFlowConfigurationService.selectByEmployeecodeCount(ee.getEmployeecode(), ee.getEnterprisecode(), WorkingFlowConfiguration.Enum_flowtype.存款工作流);
			session.setAttribute(Constant.USER_WORKING_FLOW_DEPOSIT, count1 > 0 ? true:false );
			
			//加载用户取款审核权限
			int count2 = workingFlowConfigurationService.selectByEmployeecodeCount(ee.getEmployeecode(), ee.getEnterprisecode(), WorkingFlowConfiguration.Enum_flowtype.取款工作流);
			session.setAttribute(Constant.USER_WORKING_FLOW_TAKE, count2 > 0 ? true:false );
			
			//加载用户待出款权限（此条可不加，前端已经处理过）
			//加载用户红利审核权限（此条可不加，前端已经处理过）
			
			Map<String,PermissionMenu> pColletion = (Map<String,PermissionMenu>)session.getAttribute(Constant.USER_PSERSSION);
			String language = (String)session.getAttribute(Constant.LANGUAGE);
			String[] ss = permissionMenuService.takeFormatEmployeeMenu(getBasePath(),pColletion.values(), language);
			model.addAttribute("firstMenu", ss[0]);
			model.addAttribute("mainMenu",  ss[1]);
		}catch (Exception e) {
			session.removeAttribute(Constant.USER_SESSION);
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
		log.Debug("后台主页面加载成功,开始跳转...");
	    return "/index";
	}
	
	/**
	 * 添加会员页面
	 * @return
	 */
	@RequestMapping("/userJsp/userAdd")
	public String userAdd(Model model ,HttpSession session,HttpServletRequest request){
		model.addAttribute(Constant.MENU_RELATION,request.getAttribute(Constant.MENU_RELATION));
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			List<Game> games = gameService.takeEnterpriseGames(ee.getEnterprisecode());
			Map<String,BigDecimal> bonus = employeeGamecataloyBonusService.takeEmployeeGameCategoryBonusMap(ee.getEmployeecode());
			model.addAttribute("bonus", bonus);
			model.addAttribute("games", games);
			model.addAttribute("employee", ee);
			model.addAttribute("enterprise",ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.企业号.value));
			return "/userjsp/userAdd";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	/**
	 * 针对单个用户密码修改
	 */
	@RequestMapping("/userJsp/rowModify")
	public String rowModify(Model model, HttpServletRequest request){
		model.addAttribute("loginaccount", request.getParameter("loginaccount"));
		return "/userjsp/rowModify";
	}
	/**
	 * 验证用户名是否重复
	 * @param request
	 * @return
	 */
	@RequestMapping("/employeeIsExist")
	@ResponseBody
	public Map<String,Object> employeeIsExist(HttpServletRequest request){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			//判断用户名是否存在,0:没有,大于0标示已经存在
			List<EnterpriseEmployee> list = enterpriseEmployeeService.queryEmployeeIsExist(request.getParameter("loginaccount"));
			if(0 == list.size()){
				map.put("statu", String.valueOf(0));
				return map;
			}
			map.put("statu", String.valueOf(1));
			map.put("result", list);
			return map;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 验证用户名是否存在(BUI自动验证使用)
	 */
	@RequestMapping(value="/employeeIsExistForBUI",produces="text/html;charset=utf-8")
	@ResponseBody
	public String employeeIsExist(HttpServletRequest request,HttpServletResponse response){
		try {
			List<EnterpriseEmployee> list = enterpriseEmployeeService.queryEmployeeIsExist(request.getParameter("loginaccount"));
			if(0 != list.size()){
				return "用户名已存在";
			}else{
				return "";
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return "网络异常";
		}
	}
	
	/**
	 * 注册股东、员工、代理、会员
	 * @param registerEmployee
	 * @return String
	 */
	/**
	 * 注册股东、员工、代理、会员
	 * @param registerEmployee
	 * @return String
	 */
	@RequestMapping(value = {"/saveEnterpriseEmployee"}, produces="application/json;charset=UTF-8")
	@ResponseBody
	@RequestInterval(millinsecond=3000)
	public Map<String,Object> saveEnterpriseEmployee(HttpSession session,HttpServletRequest request,@ModelAttribute EnterpriseEmployee registerEmployee){
		try {
			Map<String,Object> object = super.getRequestParamters(request);
			//获取当前登录用户的信息
			EnterpriseEmployee __ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			// 金马腾需要指定上级代理
			if (object.get("parentemployeecode") != null){
				String parentemployeecode = object.get("parentemployeecode").toString();
				Map<String, Object> params = new HashMap<String, Object>();
				// 权限设置
				params.put("enterprisecode", __ee.getEnterprisecode());
				params.put("employeecode", parentemployeecode);
				super.dataLimits(__ee, params, session);
				EnterpriseEmployee employee = enterpriseEmployeeService.takeEnterpriseEmployee(params);

				if (employee == null || employee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.会员.value))
					return super.packJSON(Constant.BooleanByte.NO, "上级用户账号错误!");
				
				registerEmployee.setParentemployeeaccount(employee.getLoginaccount());
				registerEmployee.setParentemployeecode(employee.getEmployeecode());
				object.remove("parentemployeeaccount");
				object.remove("parentemployeecode");
			} else {
				//上级编码为当前登录用户编码
				registerEmployee.setParentemployeecode(__ee.getEmployeecode());
				//员工上级账号为当前登录用户账号
				registerEmployee.setParentemployeeaccount(__ee.getLoginaccount());
			}
			//企业编码
			registerEmployee.setEnterprisecode(__ee.getEnterprisecode());
			//身份校验
			if(__ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.员工.value)){
				if(StringUtils.isBlank(registerEmployee.getEmployeetypecode())
						||registerEmployee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.企业号.value)){
					return super.packJSON(Constant.BooleanByte.NO, "你无法创建该类型账号");
				}
			}else if(__ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.代理.value)){
				if(StringUtils.isBlank(registerEmployee.getEmployeetypecode())
						||registerEmployee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.企业号.value)
						||registerEmployee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.员工.value)
						||registerEmployee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.信用代理.value)){
					return super.packJSON(Constant.BooleanByte.NO, "你无法创建该类型账号");
				}
			}else if(__ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.信用代理.value)){
				if(StringUtils.isBlank(registerEmployee.getEmployeetypecode())
						||registerEmployee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.企业号.value)
						||registerEmployee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.员工.value)
						||registerEmployee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.代理.value)){
					return super.packJSON(Constant.BooleanByte.NO, "你无法创建该类型账号");
				}
			}else if(__ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.会员.value)){
				return super.packJSON(Constant.BooleanByte.NO, "你无法创建该类型账号");
			}
			//重置会员分红与占成
			if(registerEmployee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.会员.value)){
				registerEmployee.setDividend(new BigDecimal(0));
				registerEmployee.setShare(new BigDecimal(0));
			}
			//分红校验
			if(registerEmployee.getDividend().compareTo(new BigDecimal("100"))==1
					||registerEmployee.getDividend().compareTo(new BigDecimal("0"))==-1){
				return super.packJSON(Constant.BooleanByte.NO, "分红设置区间为0%-100%");
			}
			if(registerEmployee.getShare().compareTo(new BigDecimal("100"))==1
					||registerEmployee.getShare().compareTo(new BigDecimal("0"))==-1){
				return super.packJSON(Constant.BooleanByte.NO, "用户占成区间为0%-100%");
			}
			
			//洗码校验
			List<EmployeeGamecataloyBonus> bonus = bonusVerify(registerEmployee, object);
			if(bonus.size()==0) {
				return super.packJSON(Constant.BooleanByte.NO, "请设置用户洗码");
			}
			
			if(StringUtils.isBlank(registerEmployee.getLoginaccount()) ||registerEmployee.getLoginaccount().length()<6 || registerEmployee.getLoginaccount().length()>12){
				return super.packJSON(Constant.BooleanByte.NO, Enum_MSG.用户名长度不匹配.desc);
			}
			//不得包含中文汉字或中文符号，也就是不得出现全角字符
			if(com.maven.util.StringUtils.isChinese(registerEmployee.getLoginaccount())) {
				return super.packJSON(Constant.BooleanByte.NO, Enum_MSG.不得出现中文汉字或中文标点符号的全角字符.desc);
			}
			//不得包含中文汉字或中文符号，也就是不得出现全角字符
			if(com.maven.util.StringUtils.isChinese(registerEmployee.getLoginpassword())) {
				return super.packJSON(Constant.BooleanByte.NO, Enum_MSG.不得出现中文汉字或中文标点符号的全角字符.desc);
			}
			if(com.maven.util.StringUtils.stringFilterCheck(registerEmployee.getLoginaccount())) {
				return super.packJSON(Constant.BooleanByte.NO, "不能包含特殊字符，允许输入字母与数字的组合");
			}
			if(com.maven.util.StringUtils.stringFilterCheck(registerEmployee.getLoginpassword())) {
				return super.packJSON(Constant.BooleanByte.NO, "不能包含特殊字符，允许输入字母与数字的组合");
			}
			if(registerEmployee.getLoginpassword().length() > 12) {
				return super.packJSON(Constant.BooleanByte.NO, "密码长度不能大于12位");
			}
			if(com.maven.util.StringUtils.isUpperOrUpper(registerEmployee.getLoginaccount())) {
				return super.packJSON(Constant.BooleanByte.NO, "账号不能含有空格或大写字符");
			}
			if(com.maven.util.StringUtils.isUpperOrUpper(registerEmployee.getLoginpassword())) {
				return super.packJSON(Constant.BooleanByte.NO, "密码不能含有空格或大写字符");
			}
			
			
			List<EnterpriseEmployee> isExits = enterpriseEmployeeService.queryEmployeeIsExist(registerEmployee.getLoginaccount());
			if(isExits!=null && isExits.size()>0){
				return super.packJSON(Constant.BooleanByte.NO, Enum_MSG.用户名已注册.desc);
			}
			
			
			//密码加密之后保存到数据库
			registerEmployee.setLoginpassword2(APIServiceUtil.encrypt(registerEmployee.getLoginpassword(), registerEmployee));//对原始密码进行加密
			registerEmployee.setLoginpassword(Encrypt.MD5(registerEmployee.getLoginpassword()));
			
			//资金密码加密
			registerEmployee.setFundpassword("");
			//用户昵称
			registerEmployee.setDisplayalias(registerEmployee.getDisplayalias() == null ? registerEmployee.getLoginaccount() : registerEmployee.getDisplayalias() );
			//用户分红
			registerEmployee.setDividend(registerEmployee.getDividend().divide(new BigDecimal(100)));
			//用户占成
			registerEmployee.setShare(registerEmployee.getShare().divide(new BigDecimal(100)));
			//在线状态{ 0 : 未登录 ,1 : 在线 , 2 : 下线}
			registerEmployee.setOnlinestatus(Byte.valueOf(Enum_onlinestatus.离线.value));
			//状态{1: 启用, 2:锁定游戏,3: 禁用}
			registerEmployee.setEmployeestatus(Enum_employeestatus.启用.value.byteValue());
			registerEmployee.setIpstatus(Enum_ipstatus.启用.value);
			
			boolean isDirectly = __ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.企业号.value);
			//注册账号
			enterpriseEmployeeService.tc_saveUser(registerEmployee,bonus,isDirectly);
			
			userLogsService.addActivityBetRecord(new UserLogs(registerEmployee.getEnterprisecode(), registerEmployee.getEmployeecode(), registerEmployee.getLoginaccount(), 
				     UserLogs.Enum_operatype.用户信息业务, "从后台创建用户", __ee.getLoginaccount(), null));
			
			// 新用户授权
			String rolecodes = String.valueOf(object.get("rolecodes"));
			if (!"null".equals(rolecodes) && !registerEmployee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.会员.value)) {
				String employeecode = registerEmployee.getEmployeecode();
				List<EmployeeMappingRole> emrList = new ArrayList<EmployeeMappingRole>();
				List<String> rolecodeList = new ArrayList<String>();
				for (String code : rolecodes.split(",")) {
					emrList.add(new EmployeeMappingRole(employeecode, code));
					rolecodeList.add(code);
				}
				// 保存用户授权的角色信息
				employeeMappingRoleService.tc_Authorization(employeecode, emrList, rolecodeList);
			} else if (registerEmployee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.代理.value)){
				/*************创建代理时自动为其配置默认权限*************/
				
				employeeMappingMenuService.tc_agentAuthorization(registerEmployee);
			}
			
			
			
			
			/*************************处理金蛋的GG/IDN扑克游戏账号注册业务**************************/
			if(registerEmployee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.会员.value)) {
				if(registerEmployee.getEnterprisecode().equals("EN003K")) {
					APIServiceNew api = new APIServiceNew(registerEmployee.getEnterprisecode());
					EmployeeApiAccout eea = SystemCache.getInstance().getEmployeeGameAccount(registerEmployee.getEmployeecode(), Enum_GameType.GGPoker.gametype);
					if(eea == null) {
						JSONObject jsonObject = JSONObject.fromObject( api.create(Enum_GameType.GGPoker.gametype, registerEmployee) );
						eea = SystemCache.getInstance().getEmployeeGameAccount(registerEmployee.getEmployeecode(), Enum_GameType.GGPoker.gametype);
						log.Error("===金蛋会员注册===创建GG扑克游戏账号结果："+jsonObject);
					} 
					
					eea = SystemCache.getInstance().getEmployeeGameAccount(registerEmployee.getEmployeecode(), Enum_GameType.IDN.gametype);
					if(eea == null) {
						JSONObject jsonObject = JSONObject.fromObject( api.create(Enum_GameType.IDN.gametype, registerEmployee) );
						eea = SystemCache.getInstance().getEmployeeGameAccount(registerEmployee.getEmployeecode(), Enum_GameType.IDN.gametype);
						log.Error("===金蛋会员注册===创建IDN扑克游戏账号结果："+jsonObject);
					} 
				}
			}
			/*************************处理金蛋的GG扑克游戏账号注册业务**************************/
			
			
			
			return super.packJSON(Constant.BooleanByte.YES, "创建成功");
		}catch(LogicTransactionRollBackException e){
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "操作失败，请稍后尝试");
		}
	}
	

	/**
	 * 查询当前登录用户的直线会员
	 * @param request
	 * @return
	 */
	 
	@RequestMapping("/findEmployee")
	@ResponseBody
	public Map<String,Object> findEmployee(HttpServletRequest request,HttpSession session){
		try {
			//获取登录用户的信息
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String,Object> obj = getRequestParamters(request);
			// 只有是非 企业号 或 员工号 才能查看企业下所有会员的信息
//	         if (!employee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.企业号.value) && !employee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.员工.value)) {
//	             obj.put("parentemployeecode", employee.getEmployeecode());
//	         }
			
			obj.put("enterprisecode", employee.getEnterprisecode());
			
			if(!obj.containsKey("createDate")){
				obj.put("employeetypecode", Type.会员.value);
			}
			super.dataLimits(employee, obj,session);
			//特殊参数处理
			pramsSpecialHandle(obj);
			List<EnterpriseEmployee> list = enterpriseEmployeeService.findEmployee(obj);
			for (EnterpriseEmployee __ee : list) {
				__ee.setEmployeecode(super.encryptString(__ee.getEmployeecode(), session.getId()));
				__ee.setLoginaccount(super.encryptString(__ee.getLoginaccount(), session.getId()));
				__ee.setFundpassword("");
				__ee.setLoginpassword("");
				// 隐私数据权限
				if(null == session.getAttribute(Constant.PRIVATE_DATA_PSERSSION)) {
					__ee.setPhoneno("***********");
					__ee.setQq("***********");
					__ee.setEmail("***********");
					__ee.setWechat("***********");
				}
			}
			//查询总记录条数
			long listTotal = enterpriseEmployeeService.findEmployeeCount(obj);
			Map<String,Object> map =new HashMap<String,Object>();
			map.put("rows", list);
			map.put("results", listTotal);
			return map;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 查询用户数据
	 * @param request
	 * @return
	 */
	 
	@RequestMapping("/findEmployeeData")
	@ResponseBody
	public Map<String,Object> findEmployeeData(HttpServletRequest request,HttpSession session){
		try {
			//获取登录用户的信息
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String,Object> obj = getRequestParamters(request);
			super.dataLimits(employee, obj,session);
			//特殊参数处理
			pramsSpecialHandle(obj);
			List<EnterpriseEmployee> list = enterpriseEmployeeService.findEmployee(obj);
			
			//查询级别处理
			Map<String, String> mapEnterpriseEmployeeLevel = new HashMap<String, String>();
			EnterpriseEmployee ee = (EnterpriseEmployee)request.getSession().getAttribute(Constant.USER_SESSION);
			Map<String,Object> object = new HashMap<String, Object>();
			object.put("enterpriseCode", ee.getEnterprisecode());
			List<EnterpriseEmployeeLevel> listx =  businessEmployeeLovelService.takelevelQuery(object);
			for (EnterpriseEmployeeLevel enterpriseEmployeeLevel : listx) {
				mapEnterpriseEmployeeLevel.put(enterpriseEmployeeLevel.getEmployeeLevelCode(), enterpriseEmployeeLevel.getEmployeeLevelName());
			}
			
			for (EnterpriseEmployee enterpriseEmployee : list) {
				String name = StringUtil.getString(mapEnterpriseEmployeeLevel.get(enterpriseEmployee.getEmployeelevelcode()));
				if(name.equals("")) {
					name = "未设置";
				}
				enterpriseEmployee.setEmployeelevelname(name);
			}
			
			//加密标识字段的值
			this.encryptSign(list,session.getId(),new String[]{"employeecode"});
			//查询总记录条数
			long listTotal = enterpriseEmployeeService.findEmployeeCount(obj);
			Map<String,Object> map =new HashMap<String,Object>();
			map.put("rows", list);
			map.put("results", listTotal);
			return map;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return null;
	}
    /**
     * 
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/userJsp/updateEmployeeInfo")
    @ResponseBody
	public Map<String,Object> updateEmployeeInfo(HttpServletRequest request,HttpSession session) {
        // 获取当前操作用户的信息 用于记录日志
        EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
        Map<String,Object> result = new HashMap<String, Object>();
        Map<String,Object> obj = getRequestParamters(request);
        String md5Employeecode = obj.get("employeeCode").toString();
        boolean mark = this.decodeSign(md5Employeecode, session.getId());
        if (mark) {
            String employeecode = md5Employeecode.split("_")[1];
            obj.put("employeecode", employeecode);
            try {
                if (enterpriseEmployeeService.updateInfo(obj) > 0) {
                    // 添加操作日志
                    EnterpriseEmployee target = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
                    userLogsService.addActivityBetRecord(new UserLogs(target.getEnterprisecode(), target.getEmployeecode(), target.getLoginaccount(), 
                             UserLogs.Enum_operatype.用户信息业务, "修改用户基础信息", employee.getLoginaccount(), null));
                    result.put("status", "success");
                    return result;
                }
            } catch (Exception e) {
                log.Error("修改用户基本资料报错！", e);
            }
        }
	    result.put("status", "fail");
	    return result;
	}
	/**
	 * 特殊参数处理
	 * @param obj
	 */
	private void pramsSpecialHandle(Map<String, Object> obj) {
		if(obj.get("dividend_begin")!=null){
			BigDecimal dividend_begin = new BigDecimal(String.valueOf(obj.get("dividend_begin")));
			obj.put("dividend_begin", dividend_begin.divide(new BigDecimal(100)));
		}
		if(obj.get("dividend_end")!=null){
			BigDecimal dividend_end = new BigDecimal(String.valueOf(obj.get("dividend_end")));
			obj.put("dividend_end", dividend_end.divide(new BigDecimal(100)));
		}
		if(obj.get("share_begin")!=null){
			BigDecimal share_begin = new BigDecimal(String.valueOf(obj.get("share_begin")));
			obj.put("share_begin", share_begin.divide(new BigDecimal(100)));
		}
		if(obj.get("share_end")!=null){
			BigDecimal share_end = new BigDecimal(String.valueOf(obj.get("share_end")));
			obj.put("share_end", share_end.divide(new BigDecimal(100)));
		}
	}
	
	
	/**
	 * 根据员工编码删除
	 * @param request
	 */
	@RequestMapping("/deleteEmployee")
	@ResponseBody
	public Map<String,String> deleteEmployee(HttpServletRequest request,HttpSession session){
		Map<String,String> map =new HashMap<String,String>();
		//获取加密之后的员工编码
		String md5Employeecode = request.getParameter("deleteCode");
		//解密标识字段的值
		boolean mark = this.decodeSign(md5Employeecode, session.getId());
		if(mark){
			String employeecode = md5Employeecode.split("_")[1];
			//调用删除方法
			try {
				enterpriseEmployeeService.tc_deleteEmployee(employeecode);
				
				EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
				EnterpriseEmployee target = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
				userLogsService.addActivityBetRecord(new UserLogs(target.getEnterprisecode(), target.getEmployeecode(), target.getLoginaccount(), 
					     UserLogs.Enum_operatype.用户信息业务, "删除用户", employee.getLoginaccount(), null));
				
			} catch (Exception e) {
				log.Error(e.getMessage(), e);
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
	@RequestMapping("/deleteSelectEmployee")
	@ResponseBody
	@OperationLog(OparetionDescription.ENTERPRISEEMPLOYEEINFO_DELETE)
	public Map<String,Object> deleteSelectEmployee(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			//只有员工类型与企业类型的用户具有权限
			if(loginEmployee.getEmployeetypecode().equals(Type.企业号.value) 
					||loginEmployee.getEmployeetypecode().equals(Type.员工.value)){
				String temp = request.getParameter("sign");
				String reason = request.getParameter("reason");
				String[] array = temp.split(",");
				boolean mark = this.decodeSign(array, session.getId());
				if(mark){
					for (int i = 0; i < array.length; i++) {
						array[i]=array[i].split("_")[1];
					}
					
					List<EnterpriseEmployee> targetEmployees = new ArrayList<EnterpriseEmployee>();
					for (String employeecode : array) {
						targetEmployees.add(enterpriseEmployeeService.takeEmployeeByCode(employeecode));
					}

					enterpriseEmployeeService.tc_deleteSelectEmployee(array);

					for (EnterpriseEmployee target : targetEmployees) {
						userLogsService.addActivityBetRecord(new UserLogs(target.getEnterprisecode(), target.getEmployeecode(), target.getLoginaccount(), 
							UserLogs.Enum_operatype.用户信息业务, "批量删除用户", loginEmployee.getLoginaccount(), reason));
					}
					
					
					return super.packJSON(Constant.BooleanByte.YES, "用户已删除");
				}else{
					return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
				}
			}else{
				return super.packJSON(Constant.BooleanByte.NO, "账户类型不匹配,无法删除用户");
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	/**
	 * 禁用选中的一条或者多条数据
	 * @param request
	 */
	@RequestMapping("/disableSelectEmployee")
	@ResponseBody
	//@OperationLog(OparetionDescription.ENTERPRISEEMPLOYEEINFO_DELETE)
	public Map<String,Object> disableSelectEmployee(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			//只有员工类型与企业类型的用户具有权限
			if(loginEmployee.getEmployeetypecode().equals(Type.企业号.value) || loginEmployee.getEmployeetypecode().equals(Type.员工.value)){
				String temp = request.getParameter("sign");
				String reason = request.getParameter("reason");
				String[] array = temp.split(",");
				boolean mark = this.decodeSign(array, session.getId());
				if(mark){
					for (int i = 0; i < array.length; i++) {
						array[i]=array[i].split("_")[1];
					}
					
					for (String employeecode : array) {
						EnterpriseEmployee target = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
						userLogsService.addActivityBetRecord(new UserLogs(target.getEnterprisecode(), target.getEmployeecode(), target.getLoginaccount(), 
							UserLogs.Enum_operatype.用户信息业务, "批量禁用用户", loginEmployee.getLoginaccount(), reason));
					}
					
					enterpriseEmployeeService.tc_disableSelectEmployee(array);
					return super.packJSON(Constant.BooleanByte.YES, "用户已禁用");
				}else{
					return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
				}
			}else{
				return super.packJSON(Constant.BooleanByte.NO, "账户类型不匹配,无法禁用用户");
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	/**
	 * 启用选中的一条或者多条数据
	 * @param request
	 */
	@RequestMapping("/activateSelectEmployee")
	@ResponseBody
	//@OperationLog(OparetionDescription.ENTERPRISEEMPLOYEEINFO_DELETE)
	public Map<String,Object> activateSelectEmployee(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			//只有员工类型与企业类型的用户具有权限
			if(loginEmployee.getEmployeetypecode().equals(Type.企业号.value) 
					||loginEmployee.getEmployeetypecode().equals(Type.员工.value)){
				String temp = request.getParameter("sign");
				String reason = request.getParameter("reason");
				String[] array = temp.split(",");
				boolean mark = this.decodeSign(array, session.getId());
				if(mark){
					for (int i = 0; i < array.length; i++) {
						array[i]=array[i].split("_")[1];
					}
					
					for (String employeecode : array) {
						EnterpriseEmployee target = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
						userLogsService.addActivityBetRecord(new UserLogs(target.getEnterprisecode(), target.getEmployeecode(), target.getLoginaccount(), 
							     UserLogs.Enum_operatype.用户信息业务, "批量启用用户", loginEmployee.getLoginaccount(), reason));
					}
					
					enterpriseEmployeeService.tc_activateSelectEmployee(array);
					return super.packJSON(Constant.BooleanByte.YES, "用户已启用");
				}else{
					return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
				}
			}else{
				return super.packJSON(Constant.BooleanByte.NO, "账户类型不匹配,无法启用用户");
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	
	/**
	 * 洗码设置审核
	 * @param ee 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private List<EmployeeGamecataloyBonus> bonusVerify(EnterpriseEmployee ee,Map<String,Object> object) throws Exception{
		Map<String,BigDecimal> supbonus =  employeeGamecataloyBonusService.takeEmployeeGameCategoryBonusMap(ee.getParentemployeecode());
		List<EmployeeGamecataloyBonus> bonus =  new ArrayList<EmployeeGamecataloyBonus>();
		for (String key : object.keySet()) {
			if(key.indexOf("Game")>-1){
				BigDecimal userbonus = new BigDecimal(String.valueOf(object.get(key))).divide(new BigDecimal(100));
				BigDecimal superbonus = supbonus.get(key);
				
				if(superbonus == null) {//如果设置为0时，好像不保存数据
					superbonus = new BigDecimal(0);
				}
				
//				if(userbonus.compareTo(new BigDecimal("0"))==-1){
				if(userbonus.doubleValue() < 0 ){
					throw new LogicTransactionRollBackException(key.replace("Game","")+"洗码不能小于0%");
				}
//				if(superbonus==null||userbonus.compareTo(superbonus)==1){
				if(superbonus==null||userbonus.doubleValue() > superbonus.doubleValue()){
					throw new LogicTransactionRollBackException(key.replace("Game","")+"洗码不能超过上级代理");
				}
//				if(userbonus.compareTo(new BigDecimal("100"))==1){
				if(userbonus.doubleValue() > 100.00){
					throw new LogicTransactionRollBackException(key.replace("Game","")+"洗码不能超过100%");
				}
				String[] gamekeys = key.split("_");
				bonus.add(new EmployeeGamecataloyBonus(ee.getEmployeecode(),
						ee.getParentemployeecode(),gamekeys[0],gamekeys[1],userbonus));
			}
			
		}
		return bonus;
	}
	
	@RequestMapping("/account")
	public String account(HttpServletRequest request,HttpSession session , Model model){
		try {
			String employeecode = request.getParameter("employeecode");
			EnterpriseEmployee enterpriseEmployee = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
			model.addAttribute("enterpriseEmployee", enterpriseEmployee);
			EnterpriseEmployeeInformation enterpriseEmployeeInformation = enterpriseEmployeeInformationService.queryAccountData(enterpriseEmployee);
			model.addAttribute("employeeInformation", enterpriseEmployeeInformation);
			List<EmployeeGamecataloyBonus> list = employeeGamecataloyBonusServiceImpl.findGameBonus(employeecode);
			model.addAttribute("listBonus", list);
			return "/userinfo/accountDataQuery";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		return Constant.PAGE_ACTIONFAILD;
	}
	
	@RequestMapping("/topNameSearch")
	@ResponseBody
	public String topNameSearch(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> parames = getRequestParamters(request); //以loginname为搜索条件
			//获取登录用户的信息
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String,Object> obj = getRequestParamters(request);
			obj.put("parentemployeecode", employee.getEmployeecode());
			if(!obj.containsKey("createDate")){
				obj.put("employeetypecode", Type.会员.value);
			}
			super.dataLimits(employee, obj,session);
			
			List<EnterpriseEmployee> list = enterpriseEmployeeService.findEmployee(parames);
			String userType = "";
			if (!list.isEmpty()){
				userType = list.get(0).getEmployeetypecode();
			}
			
			if(Type.代理.value.equals(userType)||Type.信用代理.value.equals(userType)) {
				return "/employeeAgent/userJsp/agentEmployeeIndex";
			} else{
				return "/EEmployee/userJsp/index";
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return Constant.PAGE_ACTIONFAILD;
	}
	
	@RequestMapping("/validTimeEmployee")
	@ResponseBody
	public Map<String, Object> validTimeEmployee(HttpServletRequest request, HttpSession session){
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			//只有员工类型与企业类型的用户具有权限
			if(loginEmployee.getEmployeetypecode().equals(Type.企业号.value) 
					||loginEmployee.getEmployeetypecode().equals(Type.员工.value)){
				String temp = request.getParameter("sign");
				String validtime = request.getParameter("validtime");
				String reason = request.getParameter("reason");
				String[] array = temp.split(",");
				boolean mark = this.decodeSign(array, session.getId());
				if(mark){
					for (int i = 0; i < array.length; i++) {
						array[i]=array[i].split("_")[1];
						EmployeeAvailable ea = new EmployeeAvailable();
						ea.setEmployeecode(array[i]);
						ea.setAvailabletime(DateUtils.FormatStandardDateStringTime(validtime));
						employeeAvailableService.deleteEmployeeAvailable(array[i]); //重复设置时先删除再新增
						employeeAvailableService.insertEmployeeAvailable(ea);
					}
					
					for (String employeecode : array) {
						EnterpriseEmployee target = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
						userLogsService.addActivityBetRecord(new UserLogs(target.getEnterprisecode(), target.getEmployeecode(), target.getLoginaccount(), 
							     UserLogs.Enum_operatype.用户信息业务, "批量设置用户有效时间", loginEmployee.getLoginaccount(), reason));
					}
					
					//enterpriseEmployeeService.tc_activateSelectEmployee(array);
					return super.packJSON(Constant.BooleanByte.YES, "所选用户已设置有效时间");
				}else{
					return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
				}
			}else{
				return super.packJSON(Constant.BooleanByte.NO, "账户类型不匹配,无法操作");
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	/**
	 * 批量设置员工等级
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/setLevel")
	@ResponseBody
	public Map<String, Object> setLevel(HttpServletRequest request, HttpSession session){
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			
			String temp = request.getParameter("sign");
			String selectLevel = request.getParameter("selectLevel");
			String reason = request.getParameter("reason");
			String[] array = temp.split(",");
			boolean mark = this.decodeSign(array, session.getId());
			if(mark){
				for (int i = 0; i < array.length; i++) {
					array[i]=array[i].split("_")[1];
				}
				
				for (String employeecode : array) {
					EnterpriseEmployee target = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
					userLogsService.addActivityBetRecord(new UserLogs(target.getEnterprisecode(), target.getEmployeecode(), target.getLoginaccount(), 
						     UserLogs.Enum_operatype.用户信息业务, "批量设置用户等级", loginEmployee.getLoginaccount(), reason));
				}
				
				Map<String,Object> object = new HashMap<String, Object>();
				object.put("employeelevelcode", selectLevel);
				object.put("array", array);
				
				enterpriseEmployeeService.updateEmployeeLevel(object);
				return super.packJSON(Constant.BooleanByte.YES, "所选用户已更新等级！");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	/**
	 * 批量会员信用评级
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/setCrditrating")
	@ResponseBody
	public Map<String, Object> setCrditrating(HttpServletRequest request, HttpSession session){
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			
			String creditrating = request.getParameter("creditrating");
			String employeecodes = request.getParameter("employeecodes");
			String reason = request.getParameter("reason");
			String[] array = employeecodes.split(",");
			if(decodeSign(array, session.getId())){
				for (int i = 0; i < array.length; i++) {
					array[i]=array[i].split("_")[1];
				}
				
				for (String employeecode : array) {
					EnterpriseEmployee target = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
					userLogsService.addActivityBetRecord(new UserLogs(target.getEnterprisecode(), target.getEmployeecode(), target.getLoginaccount(), 
						UserLogs.Enum_operatype.用户信息业务, "批量设置用户信用评级", loginEmployee.getLoginaccount(), reason));
				}
				
				Map<String,Object> object = new HashMap<String, Object>();
				object.put("creditrating", creditrating);
				object.put("array", array);
				
				enterpriseEmployeeService.updateMemberCreditRating(object);
				
				return super.packJSON(Constant.BooleanByte.YES, "用户信用评级已更新等级。");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	/**
	 * 导出用户数据到excel
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/exportEmployee")
	public String exportEmployee(HttpSession session, HttpServletRequest request, Model model){
		try {
			//获取登录用户的信息
			EnterpriseEmployee employee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String,Object> obj = getRequestParamters(request);
			
			/*********非超级管理员时只能查询本团队内的数据************/
			if(!SystemConstant.getProperties("admin.employeecode").equals(employee.getEmployeecode())){
				obj.put("enterprisecode", employee.getEnterprisecode());
			}
			
			//super.dataLimits(employee, obj,session);
			//特殊参数处理
			//pramsSpecialHandle(obj);
			
			List<EnterpriseEmployee> list = enterpriseEmployeeService.findEmployee(obj);
			
			model.addAttribute("listEnterpriseEmployee", list);
			model.addAttribute("title", "用户信息");
			model.addAttribute("size", list.size());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		return "/userjsp/employeeListExcel";
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
