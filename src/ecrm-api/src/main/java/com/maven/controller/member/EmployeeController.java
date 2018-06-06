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
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy.pull.common.util.Base64;
import com.maven.cache.SystemCache;
import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.entity.ActivityTemplate.Enum_activity;
import com.maven.entity.BrandDomain;
import com.maven.entity.BrandDomain.Enum_copyright;
import com.maven.entity.BrandDomain.Enum_domaintype;
import com.maven.entity.BrandDomain.Enum_linkstatus;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EmployeeGamecataloyBonus;
import com.maven.entity.EmployeeMoneyChange;
import com.maven.entity.EnterpriseActivityCustomization;
import com.maven.entity.EnterpriseActivityPay;
import com.maven.entity.EnterpriseBrandActivity;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployee.Enum_employeestatus;
import com.maven.entity.EnterpriseEmployee.Enum_onlinestatus;
import com.maven.entity.EnterpriseEmployeeInformation;
import com.maven.entity.EnterpriseEmployeeInformation.Enum_status;
import com.maven.entity.EnterpriseEmployeeLevel;
import com.maven.entity.EnterpriseEmployeeRegeditLog;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.entity.EnterpriseOperatingBrandGame;
import com.maven.entity.EnterpriseOperatingBrandPay;
import com.maven.entity.Favourable;
import com.maven.entity.FavourableUser;
import com.maven.entity.Game;
import com.maven.entity.LogLogin;
import com.maven.entity.PlatformApiOutput;
import com.maven.entity.UserLogs;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.game.APIServiceNew;
import com.maven.game.APIServiceUtil;
import com.maven.game.enums.GameEnum;
import com.maven.game.enums.GameEnum.Enum_GameType;
import com.maven.interceptor.RepeatRequestIntercept;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.ActivityRedbagService;
import com.maven.service.Baccarath5BalanceService;
import com.maven.service.BrandDomainService;
import com.maven.service.BusinessEmployeeLovelService;
import com.maven.service.EmployeeApiAccoutService;
import com.maven.service.EmployeeMoneyChangeService;
import com.maven.service.EnterpriseActivityCustomizationService;
import com.maven.service.EnterpriseActivityPayService;
import com.maven.service.EnterpriseBrandActivityService;
import com.maven.service.EnterpriseEmployeeInformationService;
import com.maven.service.EnterpriseEmployeeRegeditLogService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseOperatingBrandPayService;
import com.maven.service.FavourableService;
import com.maven.service.FavourableUserService;
import com.maven.service.GameService;
import com.maven.service.LogLoginService;
import com.maven.service.UserLogsService;
import com.maven.util.AttrCheckout;
import com.maven.util.BankCardsBlacklistUtil;
import com.maven.util.BankCheckUtil;
import com.maven.util.BeanToMapUtil;
import com.maven.util.Encrypt;
import com.maven.util.ExpiryMap;
import com.maven.util.RandomStringNum;
import com.maven.util.RegularCheck;
import com.maven.util.SmsUtilPublic;
import com.maven.util.WebInfoHandle;
import com.maven.utils.StringUtil;
import com.maven.utils.UserIDPORT;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/User")
public class EmployeeController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			EmployeeController.class.getName(), OutputManager.LOG_UER_EMPLOYEE);
	
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private Baccarath5BalanceService baccarath5BalanceService;
	
	@Autowired
	private EnterpriseEmployeeInformationService enterpriseEmployeeInformationService;
	
	@Autowired
	private BrandDomainService brandDomainService;
	
	@Autowired
	private LogLoginService logLoginService;
	@Autowired
	private BusinessEmployeeLovelService businessEmployeeLovelService;
	@Autowired
	private EnterpriseOperatingBrandPayService enterpriseOperatingBrandPayService;
	
	@Autowired
	private EnterpriseActivityCustomizationService enterpriseActivityCustomizationService;
	
	@Autowired
	private EnterpriseBrandActivityService enterpriseBrandActivityService;
	@Autowired
	private ActivityRedbagService activityRedbagService;
	@Autowired
	private EnterpriseEmployeeRegeditLogService enterpriseEmployeeRegeditLogService;
	@Autowired
	private EmployeeMoneyChangeService employeeMoneyChangeService;
	@Autowired
	private UserLogsService userLogsService;
	@Autowired
	private FavourableUserService favourableUserService;
	@Autowired
	private FavourableService favourableService;
	@Autowired
	private EnterpriseActivityPayService enterpriseActivityPayService;
	@Autowired 
	private EnterpriseActivityCustomizationService customActivityService;//定制活动
	@Autowired
	private EmployeeApiAccoutService employeeApiAccoutService;
	@Autowired
	private GameService gameService;
	
	/**
	 * 登陆
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
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.getLogin(object));
			if(ee==null) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户名或密码错误.desc);
			}
			if(ee.getEmployeestatus() == Enum_employeestatus.禁用.value.byteValue()) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户已禁用.desc);
			}
			if(ee.getEmployeestatus() == Enum_employeestatus.待激活.value.byteValue()) {
				return Enum_MSG.账号尚未激活.message(ee.getEmployeecode());
			}
			if(StringUtils.isNotBlank(ee.getFundpassword())){ 
				ee.setFundpassword("true");
			}else{
				ee.setFundpassword("false");
			}
			
			//登陆日志
			loginLog(object, ee);
			
			//登录时返回该品牌下（如有品牌）或该企业配置的自定义api接口域名
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("enterprisecode", ee.getEnterprisecode());
			params.put("brandcode", ee.getBrandcode());
			params.put("datastatus", EnterpriseOperatingBrandPay.Enum_Datastatus.有效.value);
			List<EnterpriseOperatingBrandPay> list = enterpriseOperatingBrandPayService.selectAll(params);
			String apiurl = getBasePath();//默认
			if(list != null && list.size() > 0) {
				apiurl = list.get(0).getPaycallbackurl();
			}
			ee.setApiurl(apiurl);
			
			
			if(ee.getLoginpassword2() == null || ee.getLoginpassword2().equals("")) {
				//初始化
				object.put("loginpassword2", APIServiceUtil.encrypt(loginpassword, ee));//对原始密码进行加密
				object.put("loginpassword", Encrypt.MD5(loginpassword));
				object.put("employeecode", ee.getEmployeecode());
				int status = enterpriseEmployeeService.tc_restPassword(AttrCheckout.checkout(object,true,new String[]{"loginpassword","employeecode","loginpassword2"}));
			}
			
			
			/***************************处理签到领红包****************************/
			doRedbagActivity(ee, String.valueOf(object.get("loginip")));
			/***************************处理签到领红包****************************/
			
			
			
			
			Map<String, Object> result = AttrCheckout.checkout(BeanToMapUtil.convertBean(ee,true),
					true, 
					new String[]{"loginaccount"}, new String[]{"enterprisecode","brandcode","employeecode","displayalias",
					"employeelevelcode","employeelevelname","employeetypecode",
					"employeetypename","logindatetime","lastlogintime","parentemployeecode","parentemployeeaccount","fundpassword","apiurl","phoneno","phonestatus","email","qq","wechat","alipay"
				});
			
			//返回上次登录的IP和浏览器码信息
			LogLogin temp = new LogLogin(ee.getEnterprisecode(), ee.getBrandcode(), ee.getEmployeecode() );
			temp = logLoginService.selectFirst(temp);
			if(temp != null) {
				String machinecode = temp.getMachinecode();
				if(machinecode == null || machinecode.equals("")) {
					machinecode = String.valueOf(object.get("fingerprintcode"));
				}
				result.put("last_fingerprintcode", machinecode);//上次登录的浏览器唯一标识
				result.put("last_loginip", temp.getLoginip());//上次登录的IP地址
			} else {
				result.put("last_fingerprintcode", String.valueOf(object.get("fingerprintcode")));//上次登录的浏览器唯一标识
				result.put("last_loginip", object.get("loginip").toString());//上次登录的IP地址
			}
			
			
			
			/***************************H5百家乐独立版返回HY账号和密码****************************20171123注释。暂时没有该业务
			// 获取游戏账号
			EmployeeApiAccout __eaa = SystemCache.getInstance().getEmployeeGameAccount(ee.getEmployeecode(), Enum_GameType.环球.gametype);
			if(__eaa != null) {
				String userpwd = Base64.encode(("user=" + __eaa.getGameaccount() + "!password=" + __eaa.getGamepassword()).getBytes());
				result.put("hyaccount", userpwd);//
			}
			***************************H5百家乐独立版返回HY账号和密码****************************/
			
			
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
	 * 获取用户信息-专用于帝王棋牌充值按钮
	 * 
	 * type   :m=手机端，p=pc端
	 * 
	 * account=xxxxx&type=m
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/takeEmployeeLoginaccount2",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String takeEmployeeLoginaccount2(HttpServletRequest request){
		try {
			Map<String,Object> object = super.getRequestParamters(request);
			
			System.out.println("帝王棋牌异步登录请求参数："+object);
			
			String username = object.get("account").toString();
			
			EmployeeApiAccout apiAccout = new EmployeeApiAccout();
			apiAccout.setGameaccount(username);
			apiAccout.setGametype(Enum_GameType.棋牌.gametype);
			apiAccout = employeeApiAccoutService.selectFirst(apiAccout);
			if(apiAccout == null) {
				System.out.println("不能根据游戏账号查找到会员信息："+username);
				return Enum_MSG.参数错误.message("不能根据游戏账号查找到会员信息："+username);
			}
			
			EnterpriseEmployee ee = enterpriseEmployeeService.takeEmployeeByCode(apiAccout.getEmployeecode());
			
			if(StringUtils.isNotBlank(ee.getFundpassword())){
				ee.setFundpassword("true");
			}else{
				ee.setFundpassword("false");
			}
			
			//会员等级
			if(StringUtil.getString(ee.getEmployeelevelcode()).equals("")) {
				ee.setEmployeelevelcode("");
				ee.setEmployeelevelname("未设置");
				ee.setEmployeelevelord("0");
			} else {
				EnterpriseEmployeeLevel data = businessEmployeeLovelService.getOneObject(ee.getEmployeelevelcode());
				System.out.println(ee.getEmployeelevelcode() + "=" +data.getEmployeeLevelName());
				ee.setEmployeelevelname(data.getEmployeeLevelName() );
				ee.setEmployeelevelord(data.getOrd()+"");
			}
			
			Map<String, Object> result = AttrCheckout.checkout(BeanToMapUtil.convertBean(ee,true),
					true, 
					new String[]{"loginaccount"}, new String[]{"enterprisecode","brandcode","employeecode","displayalias",
					"employeelevelcode","employeelevelname","employeelevelord","employeetypecode",
					"employeetypename","logindatetime","lastlogintime","parentemployeecode","fundpassword","phoneno","email","qq"
				});
					
			BrandDomain augold = new BrandDomain();//
			augold.setEmployeecode(ee.getParentemployeecode());
			augold.setLinkstatus(BrandDomain.Enum_linkstatus.启用.value);
			augold.setDomaintype(Byte.valueOf(BrandDomain.Enum_domaintype.企业域名_会员站点.value.toString()));
			augold.setIsdefualt(Byte.valueOf("1"));//默认
			augold.setDatastatus("1");
			augold =  brandDomainService.selectFirst(augold);
			/*
			List<BrandDomain> list = brandDomainService.select(augold);
			for (BrandDomain data : list) {
				if(data.getDomainlink().indexOf("678") > -1) {
					augold = data;
					break;
				}
			}
			*/
			result.put("domainlink", augold.getDomainlink());
			
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
	 * 获取用户信息-专用于GGPOKER收银台按钮直接登录
	 * loginaccount参数必传
	 * 
	 * username=tony&date=2017-05-15T13:12:23Z&token=17acf9535a0f4530847127c9a1f2a5cb
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/takeEmployeeLoginaccount",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String takeEmployeeLoginaccount(HttpServletRequest request){
		try {
			Map<String,Object> object = super.getRequestParamters(request);
			
			//token : MD5(SecretKey + username + reverse(SecretKey) + date)
			//token : MD5("123a456" + "myuser" + "654a321" + "2015-05-15T13:12:23Z")
			//token : MD5("123a456myuser654a3212015-05-15T13:12:23Z")
			
			System.out.println("GG扑克异步登录请求参数："+object);
			
			String username = object.get("username").toString();
			String secretKey = null;
			String date = object.get("date").toString();
			String token = object.get("token").toString();
			
			EmployeeApiAccout apiAccout = new EmployeeApiAccout();
			apiAccout.setGametype(Enum_GameType.GGPoker.gametype);
			apiAccout.setGameaccount(username);
			apiAccout = employeeApiAccoutService.selectFirst(apiAccout);
			if(apiAccout == null) {
				System.out.println("不能根据游戏账号查找到会员信息："+username);
				return Enum_MSG.参数错误.message("不能根据游戏账号查找到会员信息："+username);
			}
			
			
			Map<String, String> keydata = new HashMap<String, String>();
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(apiAccout.getEnterprisecode(),Enum_GameType.GGPoker.gametype, keydata);
			secretKey = keydata.get("SecretKey");
			
			String __reverse_SecretKey = new StringBuffer(secretKey).reverse().toString();
			String datax = secretKey.concat(username).concat(__reverse_SecretKey).concat(date);
			String md5 = Encrypt.MD5(datax);
			
			if( !md5.equals(token)) {
				System.out.println("验证token失败。 token原文："+datax+"，加密后token："+md5);
				return Enum_MSG.参数错误.message("验证token失败。 token原文："+datax+"，加密后token："+md5);
			}
			
			
			EnterpriseEmployee ee = enterpriseEmployeeService.takeEmployeeByCode(apiAccout.getEmployeecode());
			
			if(StringUtils.isNotBlank(ee.getFundpassword())){
				ee.setFundpassword("true");
			}else{
				ee.setFundpassword("false");
			}
			
			double TakeMoneyOfDay = 100000;
			int TakeTimeOfDay = 5;
			
			//会员等级
			if(StringUtil.getString(ee.getEmployeelevelcode()).equals("")) {
				ee.setEmployeelevelcode("");
				ee.setEmployeelevelname("未设置");
				ee.setEmployeelevelord("0");
			} else {
				EnterpriseEmployeeLevel data = businessEmployeeLovelService.getOneObject(ee.getEmployeelevelcode());
				System.out.println(ee.getEmployeelevelcode() + "=" +data.getEmployeeLevelName());
				ee.setEmployeelevelname(data.getEmployeeLevelName() );
				ee.setEmployeelevelord(data.getOrd()+"");
				TakeMoneyOfDay = (data.getTakeMoneyOfDay() == null ? TakeMoneyOfDay : data.getTakeMoneyOfDay().doubleValue());
				TakeTimeOfDay = data.getTakeTimeOfDay() == 0 ? TakeTimeOfDay : data.getTakeTimeOfDay();
			}
			
			Map<String, Object> result = AttrCheckout.checkout(BeanToMapUtil.convertBean(ee,true),
					true, 
					new String[]{"loginaccount"}, new String[]{"enterprisecode","brandcode","employeecode","displayalias",
					"employeelevelcode","employeelevelname","employeelevelord","employeetypecode",
					"employeetypename","logindatetime","lastlogintime","parentemployeecode","fundpassword","phoneno","email","qq"
				});
					
			BrandDomain augold = new BrandDomain();//
			augold.setEmployeecode(ee.getParentemployeecode());
			augold.setLinkstatus(BrandDomain.Enum_linkstatus.启用.value);
			augold.setDomaintype(Byte.valueOf(BrandDomain.Enum_domaintype.企业域名_会员站点.value.toString()));
			augold.setIsdefualt(Byte.valueOf("1"));//默认
			augold.setDatastatus("1");
			augold =  brandDomainService.selectFirst(augold);
			/*
			List<BrandDomain> list = brandDomainService.select(augold);
			for (BrandDomain data : list) {
				if(data.getDomainlink().indexOf("678") > -1) {
					augold = data;
					break;
				}
			}
			*/
			result.put("domainlink", augold.getDomainlink());
			
			result.put("takeMoneyOfDay", TakeMoneyOfDay);//每日最多取款总额
			result.put("takeTimeOfDay", TakeTimeOfDay);//每日最多取款次数
			
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
	 * 获取企业的VIP等级列表
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/findEmployeeLovel",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String findEmployeeLovel(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			String enterprisecode = String.valueOf(request.getParameter("enterprisecode"));
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("enterpriseCode", enterprisecode);
			List<EnterpriseEmployeeLevel> list = businessEmployeeLovelService.takelevelQuery(params);
			
			return Enum_MSG.成功.message(list);
			
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
	 * 签到领红包 
	 * 
	 * 对所有企业有作用
	 * 
	 * @param employee
	 * @param loginip
	 * @throws Exception
	 */
	private void doRedbagActivity(EnterpriseEmployee employee, String loginip) throws Exception {
		log.Info("=====================================================================================================");
		log.Info("开始 【签到领红包】逻辑");
		
		/**************************************1、检查所有在参与该活动的企业		 *************************************/
		/**************************************2、检查所有绑定该活动的企业		 *************************************/
		/**************************************3、检查所有绑定并有效的该活动的企业**************************************/
//		1:0.41,2:0.29,3:0.15,4:0.10,5:0.05
		
		EnterpriseActivityCustomization customization = new EnterpriseActivityCustomization();
		customization.setDatastatus("1");
		customization.setActivitytemplatecode(Enum_activity.签到领红包.activitycode);
		customization.setEnterprisecode(employee.getEnterprisecode());
		List<EnterpriseActivityCustomization> listEnterpriseActivityCustomization = enterpriseActivityCustomizationService.select(customization);
		
		if(listEnterpriseActivityCustomization == null || listEnterpriseActivityCustomization.size() == 0) {
			//当前用户所在企业没有定制该模板
			return ;
		}
		
		EnterpriseActivityCustomization item = listEnterpriseActivityCustomization.get(0);
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("enterprisecode", item.getEnterprisecode());
		queryParams.put("ecactivitycode", item.getEcactivitycode());
		queryParams.put("status", EnterpriseBrandActivity.Enum_status.启用.value);
		List<EnterpriseBrandActivity> listEnterpriseBrandActivity = enterpriseBrandActivityService.selectAll(queryParams);
		if(listEnterpriseBrandActivity == null || listEnterpriseBrandActivity.size() == 0) {
			//当前用户所在企业没有定制该模板的活动
			return ;
		}
		EnterpriseBrandActivity activity = listEnterpriseBrandActivity.get(0);
		if(activity == null || !activity.getStatus().equals(EnterpriseBrandActivity.Enum_status.启用.value)) {
			//没有开启
			return ;
		}
		// 活动是否进行中
		//Date1.after(Date2),当Date1大于Date2时，返回TRUE，当小于等于时，返回false
		//Date1.before(Date2)，当Date1小于Date2时，返回TRUE，当大于等于时，返回false
		Date currendate = new Date();
	    if( ! ( currendate.after(activity.getBegintime()) && currendate.before(activity.getEndtime()) ) ){
	    	//活动不在有效时间范围内
	    	return ;
	    }
	    //检查是否指定品牌的用户才能参与该活动
		if(activity.getBrandcode() != null && !activity.getBrandcode().equals(employee.getBrandcode())) {
	    	return;
		}
		
		Map<String,Object> __activityAgument = enterpriseBrandActivityService.selectActivityAgument(activity.getEnterprisebrandactivitycode());
		
		String GET_FUNCTION = String.valueOf(__activityAgument.get("GET_FUNCTION"));//领取方式（1=接口手动，2=登录自动领取）
		if(GET_FUNCTION.equals("1")){
			return;
		}
	    
	    //  调用接口处理自动领取红包
	    Map<String, Object> result = activityRedbagService.tc_redbag(employee.getEmployeecode(), activity.getEnterprisebrandactivitycode(), loginip);
	    log.Error("=====================================================会员签到领红包结果："+result + " ;"+employee.getLoginaccount());
	    
		log.Info("结束 【签到领红包】逻辑");
		log.Info("=====================================================================================================");
	}
	
	/**
	 * 获取当前用户可以选择的优惠
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/findUserFavourable",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String findUserFavourable(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[]{"employeecode"});
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
			
			FavourableUser condition = new FavourableUser();
			condition.setEmployeecode(String.valueOf(object.get("employeecode")));
			condition.setEnterprisecode(ee.getEnterprisecode());
			List<FavourableUser> list = favourableUserService.select(condition);//查询此人当前拥有的优惠组
			
			//排查一次性优惠的享受情况，如已享受过则不能再申请
			Date currendate = new Date();
			List<FavourableUser> data = new ArrayList<FavourableUser>();
			for (FavourableUser favourableUser : list) {
				
				// 活动是否进行中
				//Date1.after(Date2),当Date1大于Date2时，返回TRUE，当小于等于时，返回false
				//Date1.before(Date2)，当Date1小于Date2时，返回TRUE，当大于等于时，返回false
			    if( !(currendate.after(favourableUser.getStarttime()) && currendate.before(favourableUser.getEndtime()) )){
			    	continue ;
			    }
			    
				boolean remove = false;
				
				if(favourableUser.getIsonce().intValue() == Favourable.Enum_isonce.一次.value) {
					
					EnterpriseActivityCustomization temp = new EnterpriseActivityCustomization();
					temp.setRemark(favourableUser.getFavourableid());
					temp.setActivitytemplatecode(9);//9是自定义活动，没有模板的
					temp.setDatastatus("1");//有效
					temp.setEnterprisecode(favourableUser.getEnterprisecode());
					List<EnterpriseActivityCustomization> listtemp = customActivityService.select(temp);
					if(listtemp != null && listtemp.size() > 0) {
						temp = listtemp.get(0);
					}
					if(temp.getEcactivitycode() != null) {
						EnterpriseActivityPay activityPay = new EnterpriseActivityPay();
						activityPay.setEnterprisecode(ee.getEnterprisecode());
						activityPay.setEmployeecode(ee.getEmployeecode());
						activityPay.setEcactivitycode(temp.getEcactivitycode());
						activityPay.setPaystatus(EnterpriseActivityPay.Enum_paystatus.已支付.value);
						List<EnterpriseActivityPay> list2 = enterpriseActivityPayService.select(activityPay);
						if(list2 != null && list2.size() > 0) {
							remove = true;
						}
					}
					
				}
				
				if( !remove) {
					data.add(favourableUser);
				} 
			}
			
			
			return Enum_MSG.成功.message(data);
				
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
	 * 测试游戏账号批量创建
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/makeEmployeeAccountTest",produces="text/html;charset=UTF-8")
	@ResponseBody
	@Deprecated
	public String makeEmployeeAccountTest(HttpServletRequest request){
		try {
			Map<String,Object> object = super.getRequestParamters(request);
			
			APIServiceNew apiServiceNew = new APIServiceNew(object.get("enterprisecode").toString());
			
			//当前数据库所有用户
			List<EnterpriseEmployee> list = enterpriseEmployeeService.select(null);
			
			
			//所有游戏平台
			int index = 0;
			Enum_GameType[] array = GameEnum.Enum_GameType.values();
			for (Enum_GameType enum_GameType : array) {
				
				for (EnterpriseEmployee item : list) {
					
					Object result = apiServiceNew.create(enum_GameType.gametype, item);
					System.out.println(index + "=" + result);
					index ++;
				}
				
			}
			
			return Enum_MSG.成功.message("创建完毕");
				
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
	 * 获取用户的游戏账号信息-单个
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/takeEmployeeAccountOne",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String takeEmployeeAccountOne(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
			
			/**************禁用用户不得取款**************/
			if(ee.getEmployeestatus() == Enum_employeestatus.禁用.value.byteValue()) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户已禁用.desc);
			}
			
			EmployeeApiAccout eea = SystemCache.getInstance().getEmployeeGameAccount(ee.getEmployeecode(), object.get("gametype").toString());
			if(eea == null) {
				return Enum_MSG.逻辑事物异常.message(object.get("gametype")+"还没有建立游戏账号，请先注册账号");
			} else {
				
				Map<String, String> value = new HashMap<String, String>();
				value.put("gameaccount", eea.getGameaccount());
				
				String gamepassword = eea.getGamepassword();
				String loginpassword = ee.getLoginpassword2();
				
				if(loginpassword != null && !loginpassword.equals("")) {
					loginpassword = APIServiceUtil.decrypt(loginpassword, ee);//解密
					if(gamepassword.equals(loginpassword)) {
						gamepassword = "同登录密码";
					}
				}
				
				
				value.put("gamepassword", gamepassword);
				
				PlatformApiOutput key = SystemCache.getInstance().getPlatformApiOutput(ee.getEnterprisecode());
				return Enum_MSG.成功.message(Encrypt.AESUNURLEncrypt(value.toString(), key.getApikey1()));
				
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
	 * 获取用户的游戏账号信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/takeEmployeeAccount",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String takeEmployeeAccount(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			
			String loginpasswordx = String.valueOf(object.get("loginpassword"));
			object.put("loginpassword", Encrypt.MD5(loginpasswordx));
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.getLogin(object));
			if(ee == null) {
				return Enum_MSG.逻辑事物异常.message("账号密码验证失败");
			}
			
			/**************禁用用户不得取款**************/
			if(ee.getEmployeestatus() == Enum_employeestatus.禁用.value.byteValue()) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户已禁用.desc);
			}
			
//			Map<String, Game> BrandGameAll = gameService.takeBrandGamesMap(ee.getBrandcode());
			
			Map<String, EmployeeApiAccout> map = SystemCache.getInstance().getEmployeeAllGameAccount(ee.getEmployeecode());
			List<Map<String, String>> result = new ArrayList<Map<String,String>>();
			
			if(map == null || map.size() == 0) {
				return Enum_MSG.逻辑事物异常.message("还没有建立相关游戏账号，请先注册账号");
			} else {
				
				String loginpassword = ee.getLoginpassword2();
				if(loginpassword != null && !loginpassword.equals("")) {
					loginpassword = APIServiceUtil.decrypt(loginpassword, ee);//解密
				}
				
				for (String key : map.keySet()) { 
					
					EmployeeApiAccout item = map.get(key);
//					Game game = BrandGameAll.get(item.getGametype());
					
					Map<String, String> data = new HashMap<String, String>();
					
					data.put("gamename", Enum_GameType.getname(item.getGametype()));
//					data.put("gamecode", game.getGamecode());
					data.put("gametype", item.getGametype());
					data.put("gameaccount", item.getGameaccount());
					
					String gamepassword = item.getGamepassword();
					if(gamepassword.equals(loginpassword)) {
						//gamepassword = "同登录密码";
					}
					
					data.put("gamepassword", gamepassword);
					result.add(data);
					
				}  
				
//				PlatformApiOutput key = SystemCache.getInstance().getPlatformApiOutput(ee.getEnterprisecode());
//				return Enum_MSG.成功.message(Encrypt.AESUNURLEncrypt(result.toString(), key.getApikey1()));
				return Enum_MSG.成功.message(result);
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
	 * 获取用户信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/takeEmployee",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String takeEmployee(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
			if(StringUtils.isNotBlank(ee.getFundpassword())){
				ee.setFundpassword("true");
			}else{
				ee.setFundpassword("false");
			}
			
			double TakeMoneyOfDay = 100000;
			int TakeTimeOfDay = 5;
			
			//会员等级
			if(StringUtil.getString(ee.getEmployeelevelcode()).equals("")) {
				ee.setEmployeelevelcode("");
				ee.setEmployeelevelname("未设置");
				ee.setEmployeelevelord("0");
			} else {
				EnterpriseEmployeeLevel data = businessEmployeeLovelService.getOneObject(ee.getEmployeelevelcode());
				System.out.println(ee.getEmployeelevelcode() + "=" +data.getEmployeeLevelName());
				ee.setEmployeelevelname(data.getEmployeeLevelName() );
				ee.setEmployeelevelord(data.getOrd()+"");
				TakeMoneyOfDay = (data.getTakeMoneyOfDay() == null ? TakeMoneyOfDay : data.getTakeMoneyOfDay().doubleValue());
				TakeTimeOfDay = data.getTakeTimeOfDay() == 0 ? TakeTimeOfDay : data.getTakeTimeOfDay();
			}
			
			//登录时返回该品牌下（如有品牌）或该企业配置的自定义api接口域名
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("enterprisecode", ee.getEnterprisecode());
			params.put("brandcode", ee.getBrandcode());
			params.put("datastatus", EnterpriseOperatingBrandPay.Enum_Datastatus.有效.value);
			List<EnterpriseOperatingBrandPay> list = enterpriseOperatingBrandPayService.selectAll(params);
			String apiurl = getBasePath();//默认
			if(list != null && list.size() > 0) {
				apiurl = list.get(0).getPaycallbackurl();
			}
			ee.setApiurl(apiurl);
			
			Map<String, Object> result = AttrCheckout.checkout(BeanToMapUtil.convertBean(ee,true),true, 
				new String[]{"loginaccount"}, new String[]{"enterprisecode","brandcode","employeecode","displayalias",
				"employeelevelcode","employeelevelname","employeelevelord","employeetypecode","parentemployeeaccount",
				"employeetypename","logindatetime","lastlogintime","parentemployeecode","fundpassword","phoneno","phonestatus","email",
				"qq","wechat","alipay","apiurl"
			});
					
			//检查是否已领取注册送红包
			Map<String,Object> parames = new HashMap<String, Object>();
			parames.put("employeecode", ee.getEmployeecode());
			parames.put("redbagtype", 2);
			int count = activityRedbagService.selectBetRecordCount(parames);
			if(count > 0) {
				result.put("is_regedit_bag", "true");//是否已领取
			} else {
				result.put("is_regedit_bag", "false");//是否可领取
			}
			
			result.put("takeMoneyOfDay", TakeMoneyOfDay);//每日最多取款总额
			result.put("takeTimeOfDay", TakeTimeOfDay);//每日最多取款次数
			
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
	 * 用户银行卡
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/UBankCards",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String UBankCards(HttpServletRequest request){
		try {
			//System.out.println("解密前="+super.getRequestParamters(request));
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
//			super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
			//System.out.println("解密前="+object);
			
			EnterpriseEmployeeInformation ei = BeanToMapUtil.convertMap(object, EnterpriseEmployeeInformation.class);
			List<EnterpriseEmployeeInformation> list = enterpriseEmployeeInformationService.select(
					AttrCheckout.checkout(ei,true, new String[]{"employeecode"}, new String[]{"informationcode","bankcode"}));

			if(object.get("informationcode")==null){
				return Enum_MSG.成功.message(list);
			}else{
				return Enum_MSG.成功.message(list.size()==0?"{}":list.get(0));
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
			
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
			
			
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
	 * 添加用户银行卡
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/AddUBankCard",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addUBankCard(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			object = AttrCheckout.checkout(object,true,new String[]{"enterprisecode","bankcode","employeecode","paymentaccount","accountname","openningbank","fundpassword"},
					new String[]{"phonenumber","qq","skype","email","infomationcomment"});
			EnterpriseEmployee ee = enterpriseEmployeeService.takeEnterpriseEmployee(object);
			
			/**************禁用用户不得取款**************/
			if(ee.getEmployeestatus() == Enum_employeestatus.禁用.value.byteValue()) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户已禁用.desc);
			}
			
			
			String fundpassword = Encrypt.MD5(String.valueOf(object.get("fundpassword")));
			if(fundpassword==null || !fundpassword.equals(ee.getFundpassword())){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.资金密码错误.desc);
			}
			
			EnterpriseEmployeeInformation ss = BeanToMapUtil.convertMap(object,EnterpriseEmployeeInformation.class);
			String bankcode = ss.getBankcode();
			
			//	户名不得包含数字
			if( (!bankcode.equals("B019") && !bankcode.equals("B020")) && com.maven.util.StringUtils.isNumeric(ss.getAccountname())) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.开户名称不得包含数字.desc);
			}
			
			//  卡bin方式校验银行卡格式规范
			if( (!bankcode.equals("B019") && !bankcode.equals("B020")) && BankCheckUtil.checkBankCard(ss.getPaymentaccount()) == null) {
				return Enum_MSG.逻辑事物异常.message("无法识别输入的银行卡号，可能是卡号不正确。请联系客服核实该信息");
			}
			// 检查银行卡号是否在黑名单中
			if (ss.getPaymentaccount() != null && !BankCardsBlacklistUtil.checkUserInfo(ss.getPaymentaccount())) {
				return Enum_MSG.逻辑事物异常.message("此银行卡号存在异常，请您核实之后与客服联系咨询");
			}
			// 检查手机号码是否在黑名单中
			if (ss.getPhonenumber() != null && !BankCardsBlacklistUtil.checkUserInfo(ss.getPhonenumber())) {
				return Enum_MSG.逻辑事物异常.message("此电话号码存在异常，请您核实之后与客服联系咨询");
			}
			/************************************
			以下信息请注意参考处理：姓名王勇，电话号是18656588180，
			开户地址：哈尔滨，经核实这个人是专业做恶意举报的一位人士，
			经常提现消费了去举报要求退款，常使用的银行卡有
			光大银行卡号：6226620306419987，
			农业银行卡号：6228480178500953975，
			中国银行卡号：6217855300024014973
			请各位商户务必在所有网站屏蔽这个人的注册.
			**************************************/
			
			//周伟超 建设银行 6217001500001379074、周伟超 工商银行  6222031206000181727
			//姓名：叶冠基 手机号码13424261271 交易卡号6226097812597715 
			//姓名：陶远林 手机号码 18513139448 交易卡号6214857556077446
			//姓名：王飞 手机号码：18256560131 交易卡号6217256300009023887
			//姓名：陈林 手机号码15798950232
			if(ss.getPaymentaccount().equals("6226620306419987") || ss.getPaymentaccount().equals("6228480178500953975")
					|| ss.getPaymentaccount().equals("6217855300024014973") || ss.getPaymentaccount().equals("6217001500001379074")
					|| ss.getPaymentaccount().equals("6222031206000181727") || ss.getPaymentaccount().equals("6226097812597715")
					|| ss.getPaymentaccount().equals("6214857556077446")|| ss.getPaymentaccount().equals("6217256300009023887")){
				
				return Enum_MSG.逻辑事物异常.message("此银行卡号存在异常，请您核实之后与客服联系咨询");
			}
			if(ss.getPhonenumber() != null && 
					(  ss.getPhonenumber().equals("18656588180") || ss.getPhonenumber().equals("13424261271")
					|| ss.getPhonenumber().equals("18513139448") || ss.getPhonenumber().equals("18256560131")
					|| ss.getPhonenumber().equals("15798950232") )){
					
					return Enum_MSG.逻辑事物异常.message("此电话号码存在异常，请您核实之后与客服联系咨询");
				}
			
			ss.setEnterprisecode(ee.getEnterprisecode());
			ss.setBrandcode(ee.getBrandcode());
			ss.setParentemployeecode(ee.getParentemployeecode());
			enterpriseEmployeeInformationService.tc_saveEnterpriseEmployeeInformation(ss);
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), ee.getLoginaccount(), 
					UserLogs.Enum_operatype.银行卡业务, "绑定了新的银行卡:"+ss.getBankname(), null, null));
			
			return  Enum_MSG.成功.message(null);
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
	 * 编辑用户银行卡
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/EditUBankCard",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String  editUserBankCard(HttpServletRequest request){
		try {
			log.Debug("调用接口EditUBankCard,修改用户银行卡");
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			object = AttrCheckout.checkout(object,true,new String[]{"enterprisecode","employeecode","informationcode","fundpassword"},
					new String[]{"phonenumber","qq","skype","email","paymentaccount","accountname","openningbank","bankcode"});
			EnterpriseEmployee ee = enterpriseEmployeeService.takeEnterpriseEmployee(object);
			
			/**************禁用用户不得取款**************/
			if(ee.getEmployeestatus() == Enum_employeestatus.禁用.value.byteValue()) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户已禁用.desc);
			}
			
			
			String fundpassword = Encrypt.MD5(String.valueOf(object.get("fundpassword")));
			if(fundpassword==null || !fundpassword.equals(ee.getFundpassword())){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.资金密码错误.desc);
			}
			EnterpriseEmployeeInformation ss = BeanToMapUtil.convertMap(object,EnterpriseEmployeeInformation.class);
			String bankcode = ss.getBankcode();
			
			//	户名不得包含数字
			if( (!bankcode.equals("B019") && !bankcode.equals("B020")) && com.maven.util.StringUtils.isNumeric(ss.getAccountname())) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.开户名称不得包含数字.desc);
			}
			
			//  卡bin方式校验银行卡格式规范
			if( (!bankcode.equals("B019") && !bankcode.equals("B020")) && BankCheckUtil.checkBankCard(ss.getPaymentaccount()) == null) {
				return Enum_MSG.逻辑事物异常.message("无法识别输入的银行卡号，可能是卡号不正确。请联系客服核实该信息");
			}
			
			// 检查银行卡号是否在黑名单中
			if (ss.getPaymentaccount() != null && !BankCardsBlacklistUtil.checkUserInfo(ss.getPaymentaccount())) {
				return Enum_MSG.逻辑事物异常.message("此银行卡号存在异常，请您核实之后与客服联系咨询");
			}
			
			// 检查手机号码是否在黑名单中
			if (ss.getPhonenumber() != null && !BankCardsBlacklistUtil.checkUserInfo(ss.getPhonenumber())) {
				return Enum_MSG.逻辑事物异常.message("此电话号码存在异常，请您核实之后与客服联系咨询");
			}
			/************************************
			以下信息请注意参考处理：姓名王勇，电话号是18656588180，
			开户地址：哈尔滨，经核实这个人是专业做恶意举报的一位人士，
			经常提现消费了去举报要求退款，常使用的银行卡有
			光大银行卡号：6226620306419987，
			农业银行卡号：6228480178500953975，
			中国银行卡号：6217855300024014973
			请各位商户务必在所有网站屏蔽这个人的注册.
			**************************************/
			//周伟超 建设银行 6217001500001379074、周伟超 工商银行  6222031206000181727
			//姓名：叶冠基 手机号码13424261271 交易卡号6226097812597715 
			//姓名：陶远林 手机号码 18513139448 交易卡号6214857556077446
			//姓名：王飞 手机号码：18256560131 交易卡号6217256300009023887
			//姓名：陈林 手机号码15798950232
			if(ss.getPaymentaccount().equals("6226620306419987") || ss.getPaymentaccount().equals("6228480178500953975")
				|| ss.getPaymentaccount().equals("6217855300024014973") || ss.getPaymentaccount().equals("6217001500001379074")
				|| ss.getPaymentaccount().equals("6222031206000181727") || ss.getPaymentaccount().equals("6226097812597715")
				|| ss.getPaymentaccount().equals("6214857556077446")|| ss.getPaymentaccount().equals("6217256300009023887")){
				
				return Enum_MSG.逻辑事物异常.message("此银行卡号存在异常，请您核实之后与客服联系咨询");
				
			}
			if(ss.getPhonenumber() != null && 
				(  ss.getPhonenumber().equals("18656588180") || ss.getPhonenumber().equals("13424261271")
				|| ss.getPhonenumber().equals("18513139448") || ss.getPhonenumber().equals("18256560131")
				|| ss.getPhonenumber().equals("15798950232") )){
				
				return Enum_MSG.逻辑事物异常.message("此电话号码存在异常，请您核实之后与客服联系咨询");
			}

			enterpriseEmployeeInformationService.tc_updateEnterpriseEmployeeInformation(ss);
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), ee.getLoginaccount(), 
					UserLogs.Enum_operatype.银行卡业务, "更改了银行卡:"+ss.getBankname(), null, null));
			
			
			log.Debug("用户银行卡修改成功");
			return Enum_MSG.成功.message(null);
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch(LogicTransactionRollBackException e){
			log.Error(e.getMessage(), e);
			e.printStackTrace();
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	/**
	 * 设置默认用户银行卡
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/DefaultUBankCard",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String DefaultUBankCard(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			object = AttrCheckout.checkout(object,true,new String[]{"enterprisecode","employeecode","informationcode"});
			EnterpriseEmployee ee = enterpriseEmployeeService.takeEnterpriseEmployee(object);
			
			String informationcode = String.valueOf(object.get("informationcode"));
			EnterpriseEmployeeInformation __eei = enterpriseEmployeeInformationService.findOneBankInfo(informationcode);
			if(__eei==null) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.取款卡不存在.desc);
			}
			if(!__eei.getStatus().equals(Enum_status.解锁.value) && ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.会员.value)){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.银行卡未解锁.desc);
			}
			
			EnterpriseEmployeeInformation ei = new EnterpriseEmployeeInformation();
			ei.setEmployeecode(ee.getEmployeecode());
			ei.setInfomationcomment("默认");
			ei = enterpriseEmployeeInformationService.selectFirst(ei);
			if( ei == null) {
				//当前没有默认银行卡
			} else {
				ei.setInfomationcomment("");
				enterpriseEmployeeInformationService.tc_updateEnterpriseEmployeeInformationByAdmin(ei);
			}
					
			
			__eei.setInfomationcomment("默认");
			int status = enterpriseEmployeeInformationService.tc_updateEnterpriseEmployeeInformationByAdmin(__eei);
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), ee.getLoginaccount(), 
					UserLogs.Enum_operatype.银行卡业务, "设置了默认的银行卡:"+__eei.getBankname(), null, null));
			
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
	 * 删除用户银行卡
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/DeleteUBankCard",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String delUBankCard(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			object = AttrCheckout.checkout(object,true,new String[]{"enterprisecode","employeecode","informationcode","fundpassword"});
			EnterpriseEmployee ee = enterpriseEmployeeService.takeEnterpriseEmployee(object);
			String fundpassword = Encrypt.MD5(String.valueOf(object.get("fundpassword")));
			if(fundpassword==null || !fundpassword.equals(ee.getFundpassword())){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.资金密码错误.desc);
			}
			String informationcode = String.valueOf(object.get("informationcode"));
			EnterpriseEmployeeInformation __eei = enterpriseEmployeeInformationService.findOneBankInfo(informationcode);
			if(__eei==null) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.取款卡不存在.desc);
			}
			if(!__eei.getStatus().equals(Enum_status.解锁.value) && ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.会员.value)){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.银行卡未解锁.desc);
			}
			int status = enterpriseEmployeeInformationService.tc_deleteEmployeeBankCard(ee.getEmployeecode(), informationcode);
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), ee.getLoginaccount(), 
					UserLogs.Enum_operatype.银行卡业务, "删除了的银行卡:"+__eei.getBankname(), null, null));
			
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
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
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

	public static void main(String[] args) {
		//System.out.println(Encrypt.MD5("4ujsx2qjpqg" + "egg2230" + new StringBuffer("4ujsx2qjpqg").reverse().toString() + "xffdfdfdfd"));
		Object val = -1;
		if(val instanceof Integer && Integer.valueOf(val.toString()) == -1) {
			System.out.println("是的");
		}
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
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
			if(ee.getLoginpassword()!=null && !ee.getLoginpassword().equals(Encrypt.MD5(String.valueOf(object.get("oldloginpassword"))))) { 
				return Enum_MSG.参数错误.message(Enum_MSG.登录密码错误.desc);
			}
			
			/**************禁用用户不得取款**************/
			if(ee.getEmployeestatus() == Enum_employeestatus.禁用.value.byteValue()) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户已禁用.desc);
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
	
	/**
	 * 重置登录密码
	 * @return
	 */
	@RequestMapping(value="/resetLoginPassword",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String resetLoginPassword(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object,false, new String[]{"loginaccount","newloginpassword"});
					
//			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
			EnterpriseEmployee ee = new EnterpriseEmployee();
			ee.setLoginaccount(object.get("loginaccount").toString());
			ee = enterpriseEmployeeService.getGGPokerLogin(ee);
			if(ee == null) {
				return Enum_MSG.逻辑事物异常.message("账号不存在："+object.get("loginaccount").toString());
			}
			
			/**************禁用用户不得取款**************/
			if(ee.getEmployeestatus() == Enum_employeestatus.禁用.value.byteValue()) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户已禁用.desc);
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

	private static List<String> errorphone = new ArrayList<String>(){/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
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
	}};
	private static Map<String, String> mapVerifycode = new HashMap<String, String>();
	private static ExpiryMap<String, String> mapip = new ExpiryMap<String, String>();
	/**
	 * 注册时获取短信验证码
	 * @param phoneno 手机号码
	 * @return String
	 */
	@RequestMapping(value="/getVerifycode",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getVerifycode(HttpSession session,HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			
			String phoneno = StringUtil.handleNull(object.get("phoneno"));
			String ip = StringUtil.handleNull(object.get("ip"));
			
			//120秒限时，禁止重复提交
			RepeatRequestIntercept.isIntercept(phoneno, request.getRequestURI().replaceAll("/+", "/"), 5 * 60 *1000);
			
			RepeatRequestIntercept.isIntercept("ip"+ip, request.getRequestURI().replaceAll("/+", "/"), 5 * 60 *1000);
			
			System.out.println(StringUtil.getStryyyyMMddHHmmss()+"===========phoneno="+phoneno+"=ip="+ip);
			
			if(phoneno.equals("") || phoneno.length() != 11) {
				return Enum_MSG.逻辑事物异常.message("手机号码格式不正确");
			}
			if(ip.trim().equals("") ) {
				return Enum_MSG.逻辑事物异常.message("请传递IP获取");
			}
			for (String string : errorphone) {
				if(phoneno.startsWith(string)) {
					return Enum_MSG.逻辑事物异常.message("输入的号码段为无效虚拟手机号码，请使用实名制手机号码");
				}
			}
			
			/**/
			//验证IP是否频繁
			boolean falg = false;
			if( !mapip.containsKey(ip.trim()) ) {
				falg = true;
			} else {
				Object val = mapip.isInvalid(ip.trim());
				if(val instanceof Integer && Integer.valueOf(val.toString()) == -1) {
					falg = true;
				}
			}
			//不存在或过期时装入，并标志过期时间
			if( falg == true) {
				mapip.put(ip.trim(), phoneno, 5 * 60 * 1000);
			} else {
				return Enum_MSG.逻辑事物异常.message("刚才获取的短信验证码还有效，请输入原验证码");
			}
			
			
			String __employeecode = phoneno;//此时没有注册成为会员，只能用手机号码代替会员编码
			
			String smscode = SystemCache.getInstance().getSMScode(__employeecode);
			if(smscode == null) {
				smscode = RandomStringNum.createRandomString(4);
				SystemCache.getInstance().setSMScode(__employeecode, smscode);
				System.out.println(__employeecode+"随机生成验证码："+smscode);
				
				/****/
				//发送验证码
				Map<String, String> result = SmsUtilPublic.sendSmsByGet1(phoneno, smscode);
				if(result == null) {
					return Enum_MSG.逻辑事物异常.message("短信验证码发送异常，请稍后再试");
				}
				
				if(result.get("code").equals("1")) {
					mapVerifycode.put(phoneno, smscode);
					return Enum_MSG.成功.message(smscode);
				} else {
					return Enum_MSG.逻辑事物异常.message("验证码发送失败，错误："+result.get("msg"));
				}
				
			}
//			return Enum_MSG.成功.message(RandomStringNum.createRandomString(4));
			return Enum_MSG.成功.message(smscode);
			
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
	 * 注册时获取短信验证码
	 * 
	 * H5专用
	 * 
	 * @param phoneno 手机号码
	 * @return String
	 */
	@RequestMapping(value="/getVerifycodeH5",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getVerifycodeH5(HttpSession session,HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			
			String phoneno = StringUtil.handleNull(object.get("phoneno"));
			String ip = StringUtil.handleNull(object.get("ip"));
			
			//120秒限时，禁止重复提交
			RepeatRequestIntercept.isIntercept(phoneno, request.getRequestURI().replaceAll("/+", "/"), 5 * 60 *1000);
			
			
			System.out.println(StringUtil.getStryyyyMMddHHmmss()+"===========phoneno="+phoneno+"=ip="+ip);
			
			if(phoneno.equals("") || phoneno.length() != 11) {
				return Enum_MSG.逻辑事物异常.message("手机号码格式不正确");
			}
			for (String string : errorphone) {
				if(phoneno.startsWith(string)) {
					return Enum_MSG.逻辑事物异常.message("输入的号码段为无效虚拟手机号码，请使用实名制手机号码");
				}
			}
			
			
			String __employeecode = phoneno;//此时没有注册成为会员，只能用手机号码代替会员编码
			
			String smscode = SystemCache.getInstance().getSMScode(__employeecode);
			if(smscode == null) {
				smscode = RandomStringNum.createRandomString(4);
				SystemCache.getInstance().setSMScode(__employeecode, smscode);
				System.out.println(__employeecode+"随机生成验证码："+smscode);
				
			}
			
			//发送验证码
			Map<String, String> result = SmsUtilPublic.sendSmsByGet1(phoneno, smscode);
			if(result == null) {
				return Enum_MSG.逻辑事物异常.message("短信验证码发送异常，请稍后再试");
			}
			
			if(result.get("code").equals("1")) {
				mapVerifycode.put(phoneno, smscode);
				return Enum_MSG.成功.message(smscode);
			} else {
				return Enum_MSG.逻辑事物异常.message("验证码发送失败，错误："+result.get("msg"));
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
	 * 根据推广链接注册会员和代理类型账号
	 * @param enterpriseEmployee
	 * @return String
	 */
	@RequestMapping(value="/register",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String saveEmployee(HttpSession session,HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[]{"enterprisecode","brandcode","domain","loginaccount","loginpassword"});
			String registerDomian = String.valueOf(object.get("domain"));
			registerDomian = RegularCheck.takeDomain(registerDomian);
			BrandDomain registerSiteAgument = brandDomainService.queryByDomainLink(registerDomian);
			
			if(registerSiteAgument==null
					||registerSiteAgument.getCopyright().toString().equals(Enum_copyright.公共.value)
					||registerSiteAgument.getDomaintype()==Enum_domaintype.代理注册链接.value.byteValue()
					||StringUtils.isBlank(registerSiteAgument.getBrandcode())) 
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.域名未注册.desc);
			if(registerSiteAgument.getLinkstatus().equals(Enum_linkstatus.禁用.value)){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.域名已禁用.desc);
			}
			
			EnterpriseEmployee enterpriseEmployee = BeanToMapUtil.convertMap(object, EnterpriseEmployee.class);
			
			if(StringUtils.isBlank(enterpriseEmployee.getLoginaccount())
					||enterpriseEmployee.getLoginaccount().length()<6
					||enterpriseEmployee.getLoginaccount().length()>12){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户名长度不匹配.desc);
			}
			
			//不得包含中文汉字或中文符号，也就是不得出现全角字符
			if(com.maven.util.StringUtils.isChinese(enterpriseEmployee.getLoginaccount())) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.不得出现中文汉字或中文标点符号的全角字符.desc);
			}
			//不得包含中文汉字或中文符号，也就是不得出现全角字符
			if(com.maven.util.StringUtils.isChinese(enterpriseEmployee.getLoginpassword())) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.不得出现中文汉字或中文标点符号的全角字符.desc);
			}
			if(com.maven.util.StringUtils.stringFilterCheck(enterpriseEmployee.getLoginaccount())) {
				return Enum_MSG.逻辑事物异常.message("账号不能包含特殊字符，允许输入字母与数字的组合");
			}
			if(com.maven.util.StringUtils.stringFilterCheck(enterpriseEmployee.getLoginpassword())) {
				return Enum_MSG.逻辑事物异常.message("密码不能包含特殊字符，允许输入字母与数字的组合");
			}
			if(enterpriseEmployee.getLoginpassword().length() > 12) {
				return Enum_MSG.逻辑事物异常.message("密码长度不能大于12位");
			}
			if(com.maven.util.StringUtils.isUpperOrUpper(enterpriseEmployee.getLoginaccount())) {
				return Enum_MSG.逻辑事物异常.message("账号不能含有空格或大写字符");
			}
			if(com.maven.util.StringUtils.isUpperOrUpper(enterpriseEmployee.getLoginpassword())) {
				return Enum_MSG.逻辑事物异常.message("密码不能含有空格或大写字符");
			}
			if(com.maven.util.StringUtils.isCharAllOrNumberAll(enterpriseEmployee.getLoginpassword())) {
				return Enum_MSG.逻辑事物异常.message("密码应为数字和字符的组合");
			}
			if(com.maven.util.StringUtils.isCharAllOrNumberAll(enterpriseEmployee.getLoginaccount())) {
				return Enum_MSG.逻辑事物异常.message("账号应为数字和字符的组合");
			}
			
			List<EnterpriseEmployee> isExits = enterpriseEmployeeService.queryEmployeeIsExist(enterpriseEmployee.getLoginaccount());
			if(isExits!=null && isExits.size()>0){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户名已注册.desc);
			}
			
			/***********************针对注册时有手机号码验证码的时候************************/
			if(object.get("phoneno") != null) {
				String phoneno = object.get("phoneno").toString();
				if(phoneno.equals("") || phoneno.length() != 11) {
					return Enum_MSG.逻辑事物异常.message("手机号码格式不正确");
				}
				if(StringUtil.checkPhoneNo(phoneno) == false) {
					return Enum_MSG.逻辑事物异常.message("手机号码格式不正确");
				}
			}
			if(object.get("phoneno") != null && object.get("phoneno").toString().length() == 11 && 
					object.get("verifycode") != null && object.get("verifycode").toString().length() == 4 && 
					mapVerifycode.containsKey(object.get("phoneno").toString()) ) {
				
				String verifycode = object.get("verifycode").toString();
				String phoneno = object.get("phoneno").toString();
				String checkno = mapVerifycode.get(phoneno);
				if( !checkno.equals(verifycode)) {
					return Enum_MSG.逻辑事物异常.message(Enum_MSG.短信验证码错误.desc);
				}
				
			} 
			if(object.get("phoneno") != null && object.get("phoneno").toString().length() == 11 ) {
				//验证是否存在该手机号码
				EnterpriseEmployee temp = new EnterpriseEmployee();
				temp.setDatastatus("1");
				temp.setPhoneno(object.get("phoneno").toString());
				isExits = enterpriseEmployeeService.select(temp);
				if(isExits!=null && isExits.size()>0){
					return Enum_MSG.逻辑事物异常.message(Enum_MSG.该手机号已注册过.desc);
				}
			}
			/***********************针对注册时有手机号码验证码的时候************************/
			
			//获取推广用户的信息
			EnterpriseEmployee employee = enterpriseEmployeeService.takeEmployeeByCode(registerSiteAgument.getEmployeecode());
			
			//员工的上级编码为当前登录用户编码
			enterpriseEmployee.setParentemployeecode(employee.getEmployeecode());
			//登陆密码加密
			enterpriseEmployee.setLoginpassword2(APIServiceUtil.encrypt(enterpriseEmployee.getLoginpassword(), enterpriseEmployee));//对原始密码进行加密
			enterpriseEmployee.setLoginpassword(Encrypt.MD5(enterpriseEmployee.getLoginpassword()));
			
			//分红
			enterpriseEmployee.setDividend(registerSiteAgument.getDividend());
			//占成
			enterpriseEmployee.setShare(registerSiteAgument.getShare());
			//绑定推广链接
			enterpriseEmployee.setRegistercode(registerDomian);
			//员工类型编码
			enterpriseEmployee.setEmployeetypecode(registerSiteAgument.getEmployeetype());
			//默认资金密码为空
			enterpriseEmployee.setFundpassword("");
			
			/*************************处理H5百家乐独立版的HY真人游戏账号注册业务**************************/
			if( StringUtil.getString(object.get("H5BJL")).toUpperCase().equals("Y") ) {
				enterpriseEmployee.setFundpassword(Encrypt.MD5("123456789"));
			}
			/*************************处理H5百家乐独立版的HY真人游戏账号注册业务**************************/
			
			//默认昵称
			String displayalias = StringUtil.getString(object.get("displayalias")).trim();
			if(displayalias != null && !displayalias.equals("null") && displayalias.trim().length() >= 2) {
				//有填写姓名
			} else {
				displayalias = enterpriseEmployee.getLoginaccount().length()>8?
						enterpriseEmployee.getLoginaccount().substring(0, 8):enterpriseEmployee.getLoginaccount().substring(0, enterpriseEmployee.getLoginaccount().length());
			}
			enterpriseEmployee.setDisplayalias(displayalias);
			
			
			//洗码数据封装
			List<EmployeeGamecataloyBonus> list = new ArrayList<EmployeeGamecataloyBonus>();
			//分割洗码字符串
			String[] temp = registerSiteAgument.getBonus().split("\\|");
			for (int i = 0; i < temp.length; i++) {
				EmployeeGamecataloyBonus person = new EmployeeGamecataloyBonus();
				String[] str1 = temp[i].split(":");
				for (int j = 1; j <= str1.length; j++) {
					if((j & 1) == 1){
						String[] str2 = str1[j-1].split("_");
						for (int k = 1; k <= str2.length; k++) {
							if((k & 1) == 1){
								person.setGametype(str2[k-1]);
							}else{
								person.setCategorytype(str2[k-1]);
							}
						}
					}else{
						person.setBonus(BigDecimal.valueOf(Double.valueOf(str1[j-1])));
					}
				}
				list.add(person);
			}
			//在线状态{ 0 : 未登录 ,1 : 在线 , 2 : 下线}
			enterpriseEmployee.setOnlinestatus(new Byte("0"));
			//员工状态{0:逻辑删除,1: 启用, 2:锁定游戏,4: 禁用}
			enterpriseEmployee.setEmployeestatus(new Byte("1"));
			
			//员工的上级账号为当前登录用户账号
			enterpriseEmployee.setParentemployeeaccount(employee.getLoginaccount());
			//手机号码
			enterpriseEmployee.setPhoneno( StringUtil.getString(object.get("phoneno")) );
			//注册账号
			boolean isDirectly = employee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.企业号.value);
			enterpriseEmployeeService.tc_saveUser(enterpriseEmployee,list,isDirectly);
			
			
			/*************************记录注册IP到日志，便于分析安全风险**************************/
			String loginIp = object.get("ip").toString();
			EnterpriseEmployeeRegeditLog iemm = new EnterpriseEmployeeRegeditLog();
			iemm.setCreatetime(new Date());
			iemm.setEmployeecode(enterpriseEmployee.getEmployeecode());
			iemm.setEnterprisecode(enterpriseEmployee.getEnterprisecode());
			iemm.setIp(loginIp);
			iemm.setLoginaccount(enterpriseEmployee.getLoginaccount());
			iemm.setLsh(UUID.randomUUID().toString());
			iemm.setParentemployeeaccount(enterpriseEmployee.getParentemployeeaccount());
			iemm.setRemark( StringUtil.getString(object.get("fingerprintcode")) );//浏览器唯一编码
			enterpriseEmployeeRegeditLogService.addActivityBetRecord(iemm);
			/*************************记录注册IP到日志，便于分析安全风险**************************/
			
			
			
			/*************************处理默认优惠组业务**************************暂时先取消，之前来博有这个需求
			Favourable condition = new Favourable();
			condition.setEnterprisecode(employee.getEnterprisecode());
			condition.setIsdeault(Favourable.Enum_isdeault.默认.value);
			condition.setStatus(Favourable.Enum_status.启用.value);
			List<Favourable> templist = favourableService.select(condition);//查询此人当前拥有的优惠组
			
			Date currendate = new Date();
			for (Favourable favourable : templist) {
				// 活动是否进行中
				//Date1.after(Date2),当Date1大于Date2时，返回TRUE，当小于等于时，返回false
				//Date1.before(Date2)，当Date1小于Date2时，返回TRUE，当大于等于时，返回false
			    if( !(currendate.after(favourable.getStarttime()) && currendate.before(favourable.getEndtime()) )){
			    	continue ;
			    }
			    FavourableUser user = new FavourableUser(UUID.randomUUID().toString(), favourable.getLsh(), employee.getEnterprisecode(), employee.getEmployeecode());
			    favourableUserService.addActivityBetRecord(user);
			}
			*************************处理默认优惠组业务**************************/
			
			
			/*************************处理默认等级VIP组业务**************************/
			Map<String, Object> params2 = new HashMap<String, Object>();
			params2.put("isdefault", EnterpriseEmployeeLevel.Enum_isdeault.默认.value);
			params2.put("enterpriseCode", enterpriseEmployee.getEnterprisecode());
			List<EnterpriseEmployeeLevel> list3 = businessEmployeeLovelService.takelevelQuery(params2);
			if(list3 != null && list3.size() > 0) {
				EnterpriseEmployeeLevel tt = list3.get(0);
				//放入该组
				String[] array = {enterpriseEmployee.getEmployeecode()};
				Map<String,Object> object2 = new HashMap<String, Object>();
				object2.put("employeelevelcode", tt.getEmployeeLevelCode());
				object2.put("array", array);
				enterpriseEmployeeService.updateEmployeeLevel(object2);
			}
			/*************************处理默认等级VIP组业务**************************/
			
			
			/*************************处理金蛋的GG/IDN扑克游戏账号注册业务**************************/
			if(enterpriseEmployee.getEnterprisecode().equals("EN003K")) {
				
				List<Game> games = gameService.takeBrandGames(enterpriseEmployee.getBrandcode(), EnterpriseOperatingBrandGame.Enum_flag.正常.value);
				if (isOpenGame(enterpriseEmployee.getBrandcode(), Enum_GameType.GGPoker.gametype,games)==null){
					//return Enum_MSG.逻辑事物异常.message(Enum_MSG.游戏维护中.desc);
					System.out.println("游戏维护中跳过");
				} else {
					APIServiceNew api = new APIServiceNew(enterpriseEmployee.getEnterprisecode());
					EmployeeApiAccout eea = SystemCache.getInstance().getEmployeeGameAccount(enterpriseEmployee.getEmployeecode(), Enum_GameType.GGPoker.gametype);
					if(eea == null) {
						JSONObject jsonObject = JSONObject.fromObject( api.create(Enum_GameType.GGPoker.gametype, enterpriseEmployee) );
						eea = SystemCache.getInstance().getEmployeeGameAccount(enterpriseEmployee.getEmployeecode(), Enum_GameType.GGPoker.gametype);
						log.Error("===金蛋会员注册===创建GG扑克游戏账号结果："+jsonObject);
					} 
					/**
					eea = SystemCache.getInstance().getEmployeeGameAccount(enterpriseEmployee.getEmployeecode(), Enum_GameType.IDN.gametype);
					if(eea == null) {
						JSONObject jsonObject = JSONObject.fromObject( api.create(Enum_GameType.IDN.gametype, enterpriseEmployee) );
						eea = SystemCache.getInstance().getEmployeeGameAccount(enterpriseEmployee.getEmployeecode(), Enum_GameType.IDN.gametype);
						log.Error("===金蛋会员注册===创建IDN扑克游戏账号结果："+jsonObject);
					} 
					*/
				}
				
			}
			/*************************处理金蛋的GG扑克游戏账号注册业务**************************/
			
			
			
			/*************************处理H5百家乐独立版的HY真人游戏账号注册业务**************************/
			if( StringUtil.getString(object.get("H5BJL")).toUpperCase().equals("Y") ) {
				APIServiceNew api = new APIServiceNew(enterpriseEmployee.getEnterprisecode());
				EmployeeApiAccout eea = SystemCache.getInstance().getEmployeeGameAccount(enterpriseEmployee.getEmployeecode(), Enum_GameType.环球.gametype);
				if(eea == null) {
					JSONObject jsonObject = JSONObject.fromObject( api.create(Enum_GameType.环球.gametype, enterpriseEmployee) );
					eea = SystemCache.getInstance().getEmployeeGameAccount(enterpriseEmployee.getEmployeecode(), Enum_GameType.环球.gametype);
					log.Error("===H5百家乐独立版===创建HY真人游戏账号结果："+jsonObject);
				} 
			}
			/*************************处理H5百家乐独立版的HY真人游戏账号注册业务**************************/
			
			
			/*************************处理帝王的帝王棋牌游戏账号注册业务**************************
			if(enterpriseEmployee.getEnterprisecode().equals("EN003A")) {
				APIServiceNew api = new APIServiceNew(enterpriseEmployee.getEnterprisecode());
				EmployeeApiAccout eea = SystemCache.getInstance().getEmployeeGameAccount(enterpriseEmployee.getEmployeecode(), Enum_GameType.棋牌.gametype);
				if(eea == null) {
					JSONObject jsonObject = JSONObject.fromObject( api.create(Enum_GameType.棋牌.gametype, enterpriseEmployee) );
					eea = SystemCache.getInstance().getEmployeeGameAccount(enterpriseEmployee.getEmployeecode(), Enum_GameType.棋牌.gametype);
					log.Error("===帝王会员注册===创建帝王棋牌游戏账号结果："+jsonObject);
				} 
			}
			*************************处理帝王的帝王棋牌游戏账号注册业务**************************/
			
			
			userLogsService.addActivityBetRecord(new UserLogs(enterpriseEmployee.getEnterprisecode(), enterpriseEmployee.getEmployeecode(), enterpriseEmployee.getLoginaccount(), 
					UserLogs.Enum_operatype.用户资料业务, "注册会员", null, null));
			
			return Enum_MSG.成功.message(null);
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
	 * 好盈现金网微信注册
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/registerhyxjw",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String saveEmployeeForHYXJW(HttpSession session,HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[]{"enterprisecode","brandcode","domain","loginaccount","loginpassword"});
			String registerDomian = String.valueOf(object.get("domain"));
			registerDomian = RegularCheck.takeDomain(registerDomian);
			BrandDomain registerSiteAgument = brandDomainService.queryByDomainLink(registerDomian);
			
			if(registerSiteAgument==null
					||registerSiteAgument.getCopyright().toString().equals(Enum_copyright.公共.value)
					||registerSiteAgument.getDomaintype()==Enum_domaintype.代理注册链接.value.byteValue()
					||StringUtils.isBlank(registerSiteAgument.getBrandcode())) 
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.域名未注册.desc);
			if(registerSiteAgument.getLinkstatus().equals(Enum_linkstatus.禁用.value)){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.域名已禁用.desc);
			}
			
			EnterpriseEmployee enterpriseEmployee = BeanToMapUtil.convertMap(object, EnterpriseEmployee.class);
			
			if(StringUtils.isBlank(enterpriseEmployee.getLoginaccount())
					||enterpriseEmployee.getLoginaccount().length()<6
					||enterpriseEmployee.getLoginaccount().length()>12){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户名长度不匹配.desc);
			}
			
			//不得包含中文汉字或中文符号，也就是不得出现全角字符
			if(com.maven.util.StringUtils.isChinese(enterpriseEmployee.getLoginaccount())) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.不得出现中文汉字或中文标点符号的全角字符.desc);
			}
			//不得包含中文汉字或中文符号，也就是不得出现全角字符
			if(com.maven.util.StringUtils.isChinese(enterpriseEmployee.getLoginpassword())) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.不得出现中文汉字或中文标点符号的全角字符.desc);
			}
			if(com.maven.util.StringUtils.stringFilterCheck(enterpriseEmployee.getLoginaccount())) {
				return Enum_MSG.逻辑事物异常.message("账号不能包含特殊字符，允许输入字母与数字的组合");
			}
			if(com.maven.util.StringUtils.stringFilterCheck(enterpriseEmployee.getLoginpassword())) {
				return Enum_MSG.逻辑事物异常.message("密码不能包含特殊字符，允许输入字母与数字的组合");
			}
			if(enterpriseEmployee.getLoginpassword().length() > 12) {
				return Enum_MSG.逻辑事物异常.message("密码长度不能大于12位");
			}
			if(com.maven.util.StringUtils.isUpperOrUpper(enterpriseEmployee.getLoginaccount())) {
				return Enum_MSG.逻辑事物异常.message("账号不能含有空格或大写字符");
			}
			if(com.maven.util.StringUtils.isUpperOrUpper(enterpriseEmployee.getLoginpassword())) {
				return Enum_MSG.逻辑事物异常.message("密码不能含有空格或大写字符");
			}
			if(com.maven.util.StringUtils.isCharAllOrNumberAll(enterpriseEmployee.getLoginpassword())) {
				return Enum_MSG.逻辑事物异常.message("密码应为数字和字符的组合");
			}
//			if(com.maven.util.StringUtils.isCharAllOrNumberAll(enterpriseEmployee.getLoginaccount())) {
//				return Enum_MSG.逻辑事物异常.message("账号应为数字和字符的组合");
//			}
			
			List<EnterpriseEmployee> isExits = enterpriseEmployeeService.queryEmployeeIsExist(enterpriseEmployee.getLoginaccount());
			// 好盈现金网以手机号做用户名，则不验证用户名，直接验证手机号码
//			if(isExits!=null && isExits.size()>0){
//				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户名已注册.desc);
//			}
			
			/***********************针对注册时有手机号码验证码的时候************************/
			if(object.get("phoneno") != null) {
				String phoneno = object.get("phoneno").toString();
				if(phoneno.equals("") || phoneno.length() != 11) {
					return Enum_MSG.逻辑事物异常.message("手机号码格式不正确");
				}
				if(StringUtil.checkPhoneNo(phoneno) == false) {
					return Enum_MSG.逻辑事物异常.message("手机号码格式不正确");
				}
			}
//			if(object.get("phoneno") != null && object.get("phoneno").toString().length() == 11 && 
//					object.get("verifycode") != null && object.get("verifycode").toString().length() == 4 && 
//					mapVerifycode.containsKey(object.get("phoneno").toString()) ) {
//				
//				String verifycode = object.get("verifycode").toString();
//				String phoneno = object.get("phoneno").toString();
//				String checkno = mapVerifycode.get(phoneno);
//				if( !checkno.equals(verifycode)) {
//					return Enum_MSG.逻辑事物异常.message(Enum_MSG.短信验证码错误.desc);
//				}
//				
//			}
			if(object.get("phoneno") != null && object.get("phoneno").toString().length() == 11 ) {
				//验证是否存在该手机号码
				EnterpriseEmployee temp = new EnterpriseEmployee();
				temp.setDatastatus("1");
				temp.setPhoneno(object.get("phoneno").toString());
				isExits = enterpriseEmployeeService.select(temp);
				if(isExits!=null && isExits.size()>0){
					return Enum_MSG.逻辑事物异常.message(Enum_MSG.该手机号已注册过.desc);
				}
			}
			/***********************针对注册时有手机号码验证码的时候************************/
			
			//获取推广用户的信息
			EnterpriseEmployee employee = enterpriseEmployeeService.takeEmployeeByCode(registerSiteAgument.getEmployeecode());
			
			//员工的上级编码为当前登录用户编码
			enterpriseEmployee.setParentemployeecode(employee.getEmployeecode());
			//登陆密码加密
			enterpriseEmployee.setLoginpassword2(APIServiceUtil.encrypt(enterpriseEmployee.getLoginpassword(), enterpriseEmployee));//对原始密码进行加密
			enterpriseEmployee.setLoginpassword(Encrypt.MD5(enterpriseEmployee.getLoginpassword()));
			
			//分红
			enterpriseEmployee.setDividend(registerSiteAgument.getDividend());
			//占成
			enterpriseEmployee.setShare(registerSiteAgument.getShare());
			//绑定推广链接
			enterpriseEmployee.setRegistercode(registerDomian);
			//员工类型编码
			enterpriseEmployee.setEmployeetypecode(registerSiteAgument.getEmployeetype());
			//默认资金密码为空
			enterpriseEmployee.setFundpassword("");
			
			//默认昵称
			String displayalias = StringUtil.getString(object.get("displayalias")).trim();
			if(displayalias != null && !displayalias.equals("null") && StringUtils.isNotBlank(displayalias)) {
				//有填写姓名
			} else {
				displayalias = enterpriseEmployee.getLoginaccount().length()>8?
						enterpriseEmployee.getLoginaccount().substring(0, 8):enterpriseEmployee.getLoginaccount().substring(0, enterpriseEmployee.getLoginaccount().length());
			}
			enterpriseEmployee.setDisplayalias(displayalias);
			
			
			//洗码数据封装
			List<EmployeeGamecataloyBonus> list = new ArrayList<EmployeeGamecataloyBonus>();
			//分割洗码字符串
			String[] temp = registerSiteAgument.getBonus().split("\\|");
			for (int i = 0; i < temp.length; i++) {
				EmployeeGamecataloyBonus person = new EmployeeGamecataloyBonus();
				String[] str1 = temp[i].split(":");
				for (int j = 1; j <= str1.length; j++) {
					if((j & 1) == 1){
						String[] str2 = str1[j-1].split("_");
						for (int k = 1; k <= str2.length; k++) {
							if((k & 1) == 1){
								person.setGametype(str2[k-1]);
							}else{
								person.setCategorytype(str2[k-1]);
							}
						}
					}else{
						person.setBonus(BigDecimal.valueOf(Double.valueOf(str1[j-1])));
					}
				}
				list.add(person);
			}
			//在线状态{ 0 : 未登录 ,1 : 在线 , 2 : 下线}
			enterpriseEmployee.setOnlinestatus(new Byte("0"));
			//员工状态{0:逻辑删除,1: 启用, 2:锁定游戏,4: 禁用}
			enterpriseEmployee.setEmployeestatus(new Byte("1"));
			
			//员工的上级账号为当前登录用户账号
			enterpriseEmployee.setParentemployeeaccount(employee.getLoginaccount());
			//手机号码
			enterpriseEmployee.setPhoneno( StringUtil.getString(object.get("phoneno")));
			//注册账号
			boolean isDirectly = employee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.企业号.value);
			enterpriseEmployeeService.tc_saveUser(enterpriseEmployee,list,isDirectly);
			
			
			/*************************记录注册IP到日志，便于分析安全风险**************************/
			String loginIp = object.get("ip").toString();
			EnterpriseEmployeeRegeditLog iemm = new EnterpriseEmployeeRegeditLog();
			iemm.setCreatetime(new Date());
			iemm.setEmployeecode(enterpriseEmployee.getEmployeecode());
			iemm.setEnterprisecode(enterpriseEmployee.getEnterprisecode());
			iemm.setIp(loginIp);
			iemm.setLoginaccount(enterpriseEmployee.getLoginaccount());
			iemm.setLsh(UUID.randomUUID().toString());
			iemm.setParentemployeeaccount(enterpriseEmployee.getParentemployeeaccount());
			iemm.setRemark( StringUtil.getString(object.get("fingerprintcode")) );//浏览器唯一编码
			enterpriseEmployeeRegeditLogService.addActivityBetRecord(iemm);
			/*************************记录注册IP到日志，便于分析安全风险**************************/
			
			
			/*************************处理默认等级VIP组业务**************************/
			Map<String, Object> params2 = new HashMap<String, Object>();
			params2.put("isdefault", EnterpriseEmployeeLevel.Enum_isdeault.默认.value);
			params2.put("enterpriseCode", enterpriseEmployee.getEnterprisecode());
			List<EnterpriseEmployeeLevel> list3 = businessEmployeeLovelService.takelevelQuery(params2);
			if(list3 != null && list3.size() > 0) {
				EnterpriseEmployeeLevel tt = list3.get(0);
				//放入该组
				String[] array = {enterpriseEmployee.getEmployeecode()};
				Map<String,Object> object2 = new HashMap<String, Object>();
				object2.put("employeelevelcode", tt.getEmployeeLevelCode());
				object2.put("array", array);
				enterpriseEmployeeService.updateEmployeeLevel(object2);
			}
			/*************************处理默认等级VIP组业务**************************/
			
			userLogsService.addActivityBetRecord(new UserLogs(enterpriseEmployee.getEnterprisecode(), enterpriseEmployee.getEmployeecode(), enterpriseEmployee.getLoginaccount(), 
					UserLogs.Enum_operatype.用户资料业务, "注册会员", null, null));
			
			return Enum_MSG.成功.message(null);
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
	
	private void loginStatus(EnterpriseEmployee ee) throws Exception {
		EnterpriseEmployee status = new EnterpriseEmployee();
		status.setEmployeecode(ee.getEmployeecode());
		status.setOnlinestatus(Byte.valueOf(Enum_onlinestatus.在线.value));
		status.setLastlogintime(new Date());
		enterpriseEmployeeService.updateOnlineStatus(status);
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
	 * H5登陆
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/H5Login",produces="text/html;charset=UTF-8")
	@ResponseBody
	@Deprecated
	public String h5Login(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[]{"loginaccount","loginpassword"});
			object.put("loginpassword", Encrypt.MD5(String.valueOf(object.get("loginpassword")))); 
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.getLogin(object));
			if(ee==null) return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户名或密码错误.desc);
			object.put("loginip", WebInfoHandle.getClientRealIP(request)); 
			//object.put("browserversion", WebInfoHandle.getBrowser(request));
			object.put("opratesystem", WebInfoHandle.getRequestSystem(request));
			object.put("refererurl", WebInfoHandle.getReferer(request));
			if(StringUtils.isNotBlank(ee.getFundpassword())){ee.setFundpassword("true");
			}else{ee.setFundpassword("false");}
			
			//登陆日志
			loginLog(object, ee);
			//登陆状态
			loginStatus(ee);
			
			
			return Enum_MSG.成功.message(AttrCheckout.checkout(BeanToMapUtil.convertBean(ee,true),
					true, 
					new String[]{"loginaccount"}, new String[]{"enterprisecode","brandcode","employeecode",
					"employeelevelcode","employeelevelname","employeetypecode",
					"employeetypename","logindatetime","lastlogintime","parentemployeecode","fundpassword"
				}));
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
			
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
			Map<String, Object> prams = new HashMap<String, Object>();
			prams.put("employeecode", ee.getEmployeecode());
			
			//如果当前状态是未激活的，则改为激活状态
			if(ee.getEmployeestatus() == Enum_employeestatus.待激活.value.byteValue()) {
				prams.put("employeestatus", Enum_employeestatus.启用.value.byteValue());
			}
			
			
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
				if(String.valueOf(object.get("email")).length() > 0 && !com.maven.util.StringUtils.checkEmail(String.valueOf(object.get("email")))) {
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
				if(String.valueOf(object.get("wechat")).length() < 5 ) {
					return Enum_MSG.逻辑事物异常.message("微信号格式不正确");
				}
				//验证完毕时
				prams.put("wechat", String.valueOf(object.get("wechat")));
			}
			if(object.containsKey("alipay")) {
				if(String.valueOf(object.get("alipay")).length() < 5 ) {
					return Enum_MSG.逻辑事物异常.message("支付宝账号格式不正确");
				}
				//验证完毕时
				prams.put("alipay", String.valueOf(object.get("alipay")));
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
	 * 测试使用方法
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/test",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String test(HttpServletRequest request){
		try {
			
			String idcard = "6230943570000185206";
			
			//  卡bin方式校验银行卡格式规范
			if(BankCheckUtil.checkBankCard(idcard) == null) {
				return Enum_MSG.逻辑事物异常.message("无法识别输入的银行卡号，可能是卡号不正确。请联系客服核实该信息");
			}
			
			
			return  Enum_MSG.成功.message(null);
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
	 * 根据账号验证是否存在
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/checkUserAccount",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String checkUserAccount(HttpServletRequest request){
		try {
			
			Map<String,Object> object = super.getRequestParamters(request);
			AttrCheckout.checkout(object,false, new String[]{"loginaccount"});
			
			String loginaccount = request.getParameter("loginaccount");
			EnterpriseEmployee ee = new EnterpriseEmployee();
			ee.setLoginaccount(loginaccount);
			ee = enterpriseEmployeeService.takeEmployeeByPhoneno(ee);
			
			if(ee == null) {
				return Enum_MSG.逻辑事物异常.message("账户不存在");
			}
			if(!ee.getDatastatus().equals("1")) {
				return Enum_MSG.逻辑事物异常.message("账户已删除");
			}
			if(!ee.getEmployeestatus().toString().equals(Enum_employeestatus.禁用.value.toString())) {
				return Enum_MSG.逻辑事物异常.message("账户已禁用");
			}
			return  Enum_MSG.成功.message("账户已存在并正常使用");
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
	 * 根据手机号码验证是否存在
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/checkUserPhoneno",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String checkUserPhoneno(HttpServletRequest request){
		try {
			
			Map<String,Object> object = super.getRequestParamters(request);
			AttrCheckout.checkout(object,false, new String[]{"phoneno"});
			
			String phoneno = request.getParameter("phoneno");
			EnterpriseEmployee ee = new EnterpriseEmployee();
			ee.setPhoneno(phoneno);
			ee = enterpriseEmployeeService.takeEmployeeByPhoneno(ee);
			
			if(ee == null) {
				return Enum_MSG.逻辑事物异常.message("手机号码对应的账户不存在");
			}
			if(ee.getEmployeestatus().toString().equals(Enum_employeestatus.禁用.value.toString())) {
				return Enum_MSG.逻辑事物异常.message("该账户已禁用");
			}
			return  Enum_MSG.成功.message(ee);//账户已存在并正常使用
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
	
	private static int totalCount = 0;
	/**
	 * 帝王VIP卡片地推活动批量产生会员账号。
	 * 
	 * 仅限内部调用
	 * jason20171123
	 * @param enterpriseEmployee
	 * @return String
	 */
	@RequestMapping(value="/patch_dw_users",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String patch_dw_users(HttpSession session,HttpServletRequest request){
		try {
			Map<String,Object> object = super.getRequestParamters(request);
			AttrCheckout.checkout(object, false, new String[]{"agentno","usernumber","startpoke"});
			if( !StringUtil.getCurrentDate().equals("20171124")) {
				return Enum_MSG.逻辑事物异常.message("操作失败。不在允许的时间范围内");
			}
			String enterprisecode = "EN003A";
			String brandcode = "EB0000B7";
			
			String agentno = String.valueOf(object.get("agentno"));
			int usernumber = Integer.valueOf(String.valueOf(object.get("usernumber")));
			int startpoke = Integer.valueOf(String.valueOf(object.get("startpoke")));
			
			String registerDomian = agentno+".677722.com";
			
			registerDomian = RegularCheck.takeDomain(registerDomian);
			BrandDomain registerSiteAgument = brandDomainService.queryByDomainLink(registerDomian);
			
			if(registerSiteAgument==null
					||registerSiteAgument.getCopyright().toString().equals(Enum_copyright.公共.value)
					||registerSiteAgument.getDomaintype()==Enum_domaintype.代理注册链接.value.byteValue()
					||StringUtils.isBlank(registerSiteAgument.getBrandcode())) 
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.域名未注册.desc);
			if(registerSiteAgument.getLinkstatus().equals(Enum_linkstatus.禁用.value)){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.域名已禁用.desc);
			}
			
			//获取推广用户的信息
			EnterpriseEmployee employee = enterpriseEmployeeService.takeEmployeeByCode(registerSiteAgument.getEmployeecode());
			//注册账号
			boolean isDirectly = employee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.企业号.value);
			//洗码数据封装
			List<EmployeeGamecataloyBonus> list = new ArrayList<EmployeeGamecataloyBonus>();
			//分割洗码字符串
			String[] temp = registerSiteAgument.getBonus().split("\\|");
			for (int i = 0; i < temp.length; i++) {
				EmployeeGamecataloyBonus person = new EmployeeGamecataloyBonus();
				String[] str1 = temp[i].split(":");
				for (int j = 1; j <= str1.length; j++) {
					if((j & 1) == 1){
						String[] str2 = str1[j-1].split("_");
						for (int k = 1; k <= str2.length; k++) {
							if((k & 1) == 1){
								person.setGametype(str2[k-1]);
							}else{
								person.setCategorytype(str2[k-1]);
							}
						}
					}else{
						person.setBonus(BigDecimal.valueOf(Double.valueOf(str1[j-1])));
					}
				}
				list.add(person);
			}
			
			int count = 0;
			UserIDPORT.setStartPoke(startpoke);//设置起始索引
			for (int m = 0; m < usernumber; m++) {
				String loginaccount = "dw"+agentno+""+UserIDPORT.getAgentUserNo();
				String loginpassword = RandomStringNum.createRandomString(6);
				
				
				EnterpriseEmployee enterpriseEmployee = new EnterpriseEmployee();
				enterpriseEmployee.setEnterprisecode(enterprisecode);
				enterpriseEmployee.setBrandcode(brandcode);
				enterpriseEmployee.setLoginaccount(loginaccount);
				enterpriseEmployee.setLoginpassword(loginpassword);
				
				List<EnterpriseEmployee> isExits = enterpriseEmployeeService.queryEmployeeIsExist(enterpriseEmployee.getLoginaccount());
				if(isExits!=null && isExits.size()>0){
					System.out.println("批量生成失败："+Enum_MSG.用户名已注册.desc);
					continue;
				}
				
				enterpriseEmployee.setAlipay("批量生成");//标记，方便导出，需要清空的
				enterpriseEmployee.setWechat(loginpassword);//标记，方便导出，需要清空的
				
				enterpriseEmployee.setEmployeelevelcode("003B");/****默认等级VIP****/
				//员工的上级编码为当前登录用户编码
				enterpriseEmployee.setParentemployeecode(employee.getEmployeecode());
				//登陆密码加密
				enterpriseEmployee.setLoginpassword2(APIServiceUtil.encrypt(enterpriseEmployee.getLoginpassword(), enterpriseEmployee));//对原始密码进行加密
				enterpriseEmployee.setLoginpassword(Encrypt.MD5(enterpriseEmployee.getLoginpassword()));
				//分红
				enterpriseEmployee.setDividend(registerSiteAgument.getDividend());
				//占成
				enterpriseEmployee.setShare(registerSiteAgument.getShare());
				//绑定推广链接
				enterpriseEmployee.setRegistercode(registerDomian);
				//员工类型编码
				enterpriseEmployee.setEmployeetypecode(registerSiteAgument.getEmployeetype());
				//默认资金密码为空
				enterpriseEmployee.setFundpassword("");
				//在线状态{ 0 : 未登录 ,1 : 在线 , 2 : 下线}
				enterpriseEmployee.setOnlinestatus(new Byte("0"));
				//员工状态{0:逻辑删除,1: 启用, 2:锁定游戏,3: 禁用,4: 等待激活}
				enterpriseEmployee.setEmployeestatus(new Byte("4"));
				//员工的上级账号为当前登录用户账号
				enterpriseEmployee.setParentemployeeaccount(employee.getLoginaccount());
				//默认昵称
				String displayalias = null;
				displayalias = enterpriseEmployee.getLoginaccount().length()>8?
						enterpriseEmployee.getLoginaccount().substring(0, 8):enterpriseEmployee.getLoginaccount().substring(0, enterpriseEmployee.getLoginaccount().length());
				enterpriseEmployee.setDisplayalias(displayalias);
				enterpriseEmployeeService.tc_saveUser(enterpriseEmployee,list,isDirectly);
				
				System.out.println("=================当前第"+m+"个,"+loginaccount+","+loginpassword);
				count ++;
			}
			totalCount += count;
			
			return Enum_MSG.成功.message("共完成个数："+count);
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
	
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
	
	
}
