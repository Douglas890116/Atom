package com.maven.controller.member;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy.pull.common.util.Base64;
import com.maven.cache.SystemCache;
import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.entity.Baccarath5Balance;
import com.maven.entity.Baccarath5Exchange;
import com.maven.entity.BettingHqNhq;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeCapitalAccount;
import com.maven.entity.EnterpriseEmployeeLevel;
import com.maven.entity.EnterpriseOperatingBrandPay;
import com.maven.entity.LogLogin;
import com.maven.entity.TakeDepositRecord;
import com.maven.entity.EnterpriseEmployee.Enum_employeestatus;
import com.maven.entity.TakeDepositRecord.Enum_ordertype;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.game.APIServiceNew;
import com.maven.game.APIServiceUtil;
import com.maven.game.enums.GameEnum.Enum_GameType;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.Baccarath5BalanceService;
import com.maven.service.Baccarath5ExchangeService;
import com.maven.service.BettingGameService;
import com.maven.service.BusinessEmployeeLovelService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseService;
import com.maven.service.LogLoginService;
import com.maven.service.TakeDepositRecoredService;
import com.maven.service.impl.BettingHqNhqServiceImpl;
import com.maven.util.AttrCheckout;
import com.maven.util.BeanToMapUtil;
import com.maven.util.Encrypt;
import com.maven.util.NHQUtils;
import com.maven.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * 针对H5百家乐独立版的接口
 * @author Administrator
 *
 */
@RequestMapping("/baccarath5")
@Controller
public class Baccarath5Controller extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			Baccarath5Controller.class.getName(), OutputManager.LOG_USER_FETCHRECORD);
	
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private Baccarath5ExchangeService baccarath5ExchangeService;
	@Autowired
	private EnterpriseEmployeeCapitalAccountService capitalAccountService;
	@Resource(name="bettingHqNhqServiceImpl")
	private BettingGameService<BettingHqNhq> bettingHqNhqService;
	@Autowired
	private LogLoginService logLoginService;
	@Autowired
	private BusinessEmployeeLovelService businessEmployeeLovelService;
	
	/**
	 * 元宝兑换金币
	 * 
	 * 也就是元宝上分,并扣减元宝
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/doExchange",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String doExchange(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[]{"enterprisecode","employeecode","money"});
			
			String enterprisecode = String.valueOf(object.get("enterprisecode"));
			String employeecode = String.valueOf(object.get("employeecode"));
			double money = Double.valueOf(object.get("money").toString());//元宝数
			
			//先查询当前的可用余额
			EnterpriseEmployeeCapitalAccount ec = capitalAccountService.takeCurrencyAccount(employeecode);
			if(ec == null){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.资金账户不存在.desc);
			} 
			
			
			Enterprise enterprise = enterpriseService.selectByPrimaryKey(enterprisecode);
			//返回真钱和元宝之间的兑换比例
			double rate2 = enterprise.getRate2();
			
			if(ec.getBalance().doubleValue() * rate2 < money) {
				return Enum_MSG.逻辑事物异常.message("当前元宝数不足以兑换！");
			}
			
			baccarath5ExchangeService.tc_exchange(enterprisecode, employeecode, money);
			
			return Enum_MSG.成功.message("兑换成功");
			
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
	 * 兑换记录
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
			object.put("field", "orderdate");
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
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.getLogin(object));
			if(ee==null) return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户名或密码错误.desc);
			if(ee.getEmployeestatus()==Enum_employeestatus.禁用.value.byteValue())
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户已禁用.desc);
			if(StringUtils.isNotBlank(ee.getFundpassword())){ 
				ee.setFundpassword("true");
			}else{ee.setFundpassword("false");}
			
			//登陆日志
			loginLog(object, ee);
			
			if(ee.getLoginpassword2() == null || ee.getLoginpassword2().equals("")) {
				//初始化
				object.put("loginpassword2", APIServiceUtil.encrypt(loginpassword, ee));//对原始密码进行加密
				object.put("loginpassword", Encrypt.MD5(loginpassword));
				object.put("employeecode", ee.getEmployeecode());
				int status = enterpriseEmployeeService.tc_restPassword(AttrCheckout.checkout(object,true,new String[]{"loginpassword","employeecode","loginpassword2"}));
			}
			
			//根据算法计算出当前用户所在的等级并返回
			EnterpriseEmployeeLevel level = getLevelH5(ee.getEnterprisecode(), ee.getEmployeecode());
			ee.setEmployeelevelcode(level.getEmployeeLevelCode());
			ee.setEmployeelevelname(level.getEmployeeLevelName());
			
			Map<String, Object> result = AttrCheckout.checkout(BeanToMapUtil.convertBean(ee,true),
					true, 
					new String[]{"loginaccount"}, new String[]{"enterprisecode","brandcode","employeecode","displayalias",
					"employeelevelcode","employeelevelname","employeetypecode",
					"employeetypename","logindatetime","lastlogintime","parentemployeecode","fundpassword","apiurl","phoneno","email","qq","wechat"
				});
			result.put("employeelevelindex", level.getOrd());//返回当前会员等级的索引
			
			/***************************H5百家乐独立版返回HY账号和密码****************************/
			// 获取游戏账号
			EmployeeApiAccout __eaa = SystemCache.getInstance().getEmployeeGameAccount(ee.getEmployeecode(), Enum_GameType.环球.gametype);
			if(__eaa != null) {
				String userpwd = Base64.encode(("user=" + __eaa.getGameaccount() + "!password=" + __eaa.getGamepassword()).getBytes());
				result.put("hyaccount", userpwd);//
			}
			/***************************H5百家乐独立版返回HY账号和密码****************************/
			
			//返回元宝兑换金币的比例
			Enterprise enterprise = enterpriseService.selectByPrimaryKey(ee.getEnterprisecode());
			double rate = enterprise.getRate();
			result.put("yunbao2jinbi_rate", rate);
			
			//返回真钱和元宝之间的兑换比例
			double rate2 = enterprise.getRate2();
			result.put("money2yuanbao_rate", rate2);
			
			
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
	 * 计算会员的成长值
	 * 
	 * 成长值=用户财富（金币+元宝*比例）*20%+游戏流水总额80%；
	 * 
	 * @param request
	 * @return
	 */
	private EnterpriseEmployeeLevel getLevelH5(String enterprisecode, String employeecode){
		try {
			Map<String,Object> object = new HashMap<String, Object>();
			
			
			//总成长值
			double money = 0;
			Enterprise enterprise = enterpriseService.selectByPrimaryKey(enterprisecode);
			//先查询当前的可用余额
			double balance = 0;
			EnterpriseEmployeeCapitalAccount ec = capitalAccountService.takeCurrencyAccount(employeecode);
			balance = ec.getBalance().doubleValue();
			
			//1、元宝转换为金币
			double rate = enterprise.getRate();
			double exchangeIn = balance;
			double exchangeOut = exchangeIn * rate;
			money += exchangeOut;
			//2、累计金币
			JSONObject jsonObject = JSONObject.fromObject(new APIServiceNew(enterprisecode).balance(Enum_GameType.环球.gametype, employeecode));
			if(jsonObject.getString("code").equals("0")) {
				money += (Double.valueOf(jsonObject.getString("info")));
			} else {
				System.out.println(employeecode+"=getLevelH5查询余额失败="+jsonObject);
			}
			System.out.println(employeecode+"=getLevelH5当前金币总额="+money);
			money = money * 0.2;//占20%
			

			//3、累计投注流水
			object.put("enterprisecode", enterprisecode);
			object.put("employeecode", employeecode);
			Map<String, Object> data = bettingHqNhqService.takeRecordCountMoney(object);
			double betmoney = 0;
			if(data != null && data.get("bettingCredits") != null) {
				betmoney = Double.valueOf(data.get("bettingCredits").toString());
				System.out.println(employeecode+"=getLevelH5当前总投注流水="+betmoney);
				betmoney = betmoney * 0.8;//占80%
			}
			money += betmoney ;
			
			System.out.println(employeecode+"=getLevelH5当前总成长值="+money);
			
			//计算
			Map<String,Object> object2 = new HashMap<String, Object>();
			object2.put("enterpriseCode", enterprisecode);
			List<EnterpriseEmployeeLevel> list = businessEmployeeLovelService.takelevelQuery(object2);
			EnterpriseEmployeeLevel level = list.get(0);
			
			for (EnterpriseEmployeeLevel enterpriseEmployeeLevel : list) {
				String conditionlevel = enterpriseEmployeeLevel.getConditionlevel();
				
				double start = Double.valueOf(conditionlevel.split("-")[0]);
				double endrt = Double.valueOf(conditionlevel.split("-")[1]);
				//刚好符合该条件
				if(money >= start && money <= endrt) {
					level = enterpriseEmployeeLevel;
					break;
				}
			}
			
			return level;
			
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
		}catch(LogicTransactionRollBackException e){
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return null;
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
