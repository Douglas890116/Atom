package com.maven.controller.member;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.cache.SystemCache;
import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployee.Enum_employeestatus;
import com.maven.entity.EnterpriseEmployeeCapitalAccount;
import com.maven.entity.EnterpriseEmployeeInformation;
import com.maven.entity.EnterpriseEmployeeInformation.Enum_status;
import com.maven.entity.EnterpriseEmployeeLevel;
import com.maven.entity.EnterpriseEmployeeType.Type;
import com.maven.entity.EnterpriseInformation;
import com.maven.entity.EnterpriseInformation.Enum_enable;
import com.maven.entity.EnterprisePaymentMethodConfig;
import com.maven.entity.EnterprisePaymentMethodConfig.Enum_paymentMethod;
import com.maven.entity.EnterpriseThirdpartyPayment;
import com.maven.entity.PaymentType.Enum_PayType;
import com.maven.entity.TakeDepositRecord;
import com.maven.entity.TakeDepositRecord.Enum_orderstatus;
import com.maven.entity.TakeDepositRecord.Enum_ordertype;
import com.maven.entity.UserLogs;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.GameAPIRequestException;
import com.maven.exception.LogicTransactionException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.game.APIServiceNew;
import com.maven.game.OrderNewUtil;
import com.maven.game.enums.GameEnum.Enum_GameType;
import com.maven.interceptor.RepeatRequestIntercept;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BankService;
import com.maven.service.BusinessEmployeeLovelService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeInformationService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseInformationService;
import com.maven.service.EnterprisePaymentMethodConfigService;
import com.maven.service.EnterpriseThirdpartyPaymentAgumentService;
import com.maven.service.EnterpriseThirdpartyPaymentService;
import com.maven.service.TakeDepositRecoredService;
import com.maven.service.UserLogsService;
import com.maven.util.AttrCheckout;
import com.maven.util.BeanToMapUtil;
import com.maven.util.Encrypt;
import com.maven.util.RandomString;
import com.maven.utils.StringUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/Funds")
public class PaymentTransferController extends BaseController{

	private static LoggerManager log = LoggerManager.getLogger(
			PaymentTransferController.class.getName(), OutputManager.LOG_USER_FUNDS);
	
	@Autowired
	private TakeDepositRecoredService takeDepositRecoredService;
	
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private EnterpriseEmployeeCapitalAccountService capitalAccountService;
	@Autowired
	private EnterpriseInformationService enterpriseInformationService;
	
	@Autowired
	private EnterpriseEmployeeInformationService  enterpriseEmployeeInformationService;
	/**第三方支付编码与秘钥*/
	@Autowired
	private EnterpriseThirdpartyPaymentAgumentService enterpriseThirdpartyPaymentAgumentService;
	/** 公司支付信息 */
	@Autowired
	private EnterpriseThirdpartyPaymentService enterpriseThirdpartyPaymentService;
	@Autowired
	private BankService bankService;

	@Autowired
	private BusinessEmployeeLovelService businessEmployeeLovelServiceImpl;
	/**公司出款方式配置*/
	@Autowired
	private EnterprisePaymentMethodConfigService enterprisePaymentMethodConfigService;
	
	@Autowired
	private UserLogsService userLogsService;
	
	
	/**
	 * 获取平台银行
	 * @return
	 */
	@RequestMapping(value="/Banks",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String banks(){
		try {
			return Enum_MSG.成功.message(bankService.getAllBankInfo());
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	/**
	 * 获取企业银行卡
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/EBankCards",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String enterpriseBankCards(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			Enterprise enterprise = BeanToMapUtil.convertMap(object, Enterprise.class);
			EnterpriseInformation __eio = new EnterpriseInformation();
			__eio.setEnterprisecode(enterprise.getEnterprisecode());
			__eio.setEnable(Byte.valueOf(Enum_enable.启用.value));
			List<EnterpriseInformation> __list =  enterpriseInformationService.select(__eio);
			Iterator<EnterpriseInformation> __data = __list.iterator();
			Map<String,EnterpriseInformation> __minbalance = new HashMap<String, EnterpriseInformation>();
			while(__data.hasNext()){
				EnterpriseInformation  __e = __data.next();
				if(__minbalance.get(__e.getBankcode())!=null){
					if(__minbalance.get(__e.getBankcode()).getCurrentbalance().compareTo(__e.getCurrentbalance())==1){
						__minbalance.put(__e.getBankcode(),__e);
					}
				}else{
					__minbalance.put(__e.getBankcode(), __e);
				}
			}
			
			//检查此人是否有存款过，如果没有，则不直接显示银行卡信息，避免恶意冻结账户
			boolean isshowrealcard = false;
			if(object.get("employeecode") != null) {
				String employeecode = object.get("employeecode").toString();
				if( !employeecode.equals("") && employeecode.length() == 8) {
					double money = takeDepositRecoredService.call_userDepositTakeMoney(employeecode, 2);//读取历史存款总额
					
					if(money > 0) {
						isshowrealcard = true;
					}
				} else {
					//如果不传递则不做限制
					isshowrealcard = true;
				}
			} else {
				//如果不传递则不做限制
				isshowrealcard = true;
			}
			
			List<Map<String,Object>> __returnListObject = new ArrayList<Map<String,Object>>();
			for (EnterpriseInformation __ei : __minbalance.values()) {
				
				//处理隐私数据
				if(isshowrealcard == false) {
					String accountname =  __ei.getAccountname();
					String openningbank = __ei.getOpenningbank();
					String openningaccount = __ei.getOpenningaccount();
					
					accountname = accountname.substring(0,1) + "**";
					if(openningbank.length() > 2) {
						openningbank = openningbank.substring(0, 2) + "************";
					} else {
						openningbank = openningbank + "************";
					}
					if(openningaccount.length() > 10) {
						openningaccount = openningaccount.substring(0,6) + "************";
					} 
					
					__ei.setAccountname(accountname);
					__ei.setOpenningaccount(openningaccount);
					__ei.setOpenningbank(openningbank);//深圳分行宝安支行
				}
				
				__returnListObject.add(BeanToMapUtil.convertBean(AttrCheckout.checkout(__ei, true, 
						new String[]{"accountname","bankcode","bankname","enterprisecode","enterpriseinformationcode","openningbank","openningaccount"}), false));
			}
			return Enum_MSG.成功.message(__returnListObject);
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	
	
	
	/**
	 * 网银转账存款
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/Saving",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String saving(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object,false,new String[]{"enterprisecode","brandcode","employeecode","orderamount","enterpriseinformationcode",
					"employeepaymentaccount","employeepaymentname","employeepaymentbank","traceip"});

			
			BigDecimal ordermount = new BigDecimal(String.valueOf(object.get("orderamount")));
			if(ordermount.doubleValue()<=0) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.金额异常.desc);
			}
			
//			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
			EnterpriseEmployee ee = enterpriseEmployeeService.takeEnterpriseEmployee(object);
			
			/**************信用代理用户不得存取款**************/
			EnterpriseEmployee parentEmployee = enterpriseEmployeeService.takeEmployeeByCode(ee.getParentemployeecode());
			if(parentEmployee != null && parentEmployee.getEmployeetypecode().equals(Type.信用代理.value)) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.信用代用户不得存取款.desc);
			}
			
			
			
			
			EnterpriseInformation _eio = new EnterpriseInformation();
			_eio.setEnterprisecode(String.valueOf(object.get("enterprisecode")));
			_eio.setEnterpriseinformationcode(String.valueOf(object.get("enterpriseinformationcode")));
			List<EnterpriseInformation> infomations = enterpriseInformationService.select(
					AttrCheckout.checkout(_eio,false,new String[]{"enterprisecode","enterpriseinformationcode"}));
			if(infomations == null || infomations.size()!=1) return Enum_MSG.逻辑事物异常.message(Enum_MSG.收款卡不存在.desc);
			EnterpriseInformation infomation = infomations.get(0);
			
			TakeDepositRecord takeDepositRecord  = BeanToMapUtil.convertMap(object, TakeDepositRecord.class);
			takeDepositRecord.setOrdernumber(RandomString.UUID());
			takeDepositRecord.setParentemployeecode(ee.getParentemployeecode());
			takeDepositRecord.setPaymenttypecode(Enum_PayType.银行卡转账.value);
			takeDepositRecord.setOrderamount(ordermount);
			takeDepositRecord.setEnterprisepaymentname(infomation.getAccountname());
			takeDepositRecord.setEnterprisepaymentaccount(infomation.getOpenningaccount());
			takeDepositRecord.setEnterprisepaymentbank(infomation.getBankcode());
			takeDepositRecord.setOrdertype((byte)Enum_ordertype.存款.value);
			takeDepositRecord.setOrderstatus((byte)Enum_orderstatus.审核中.value);
			takeDepositRecord.setOrdercreater("API");
			takeDepositRecord.setOrdercomment("API存款");
			takeDepositRecord.setOrderdate(new Date());
			takeDepositRecoredService.tc_save_money(takeDepositRecord);
			
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), ee.getLoginaccount(), 
					UserLogs.Enum_operatype.存取款业务, "银行卡转账存款"+ordermount+"元", null, null));
			
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
     * 使用java正则表达式去掉多余的.与0  
     * @param s  
     * @return   
     */    
    public static String subZeroAndDot(String s){    
        if(s.indexOf(".") > 0){    
            s = s.replaceAll("0+?$", "");//去掉多余的0    
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉    
        }    
        return s;
    }
	/**
	 * 判断是否为整数。如果为0，也返回true
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){  
	    Pattern pattern = Pattern.compile("[0-9]*");  
	    return pattern.matcher(str).matches();
	}
	/**
	 * 取款
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/Taking",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String taking(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			log.Debug(object.get("employeecode")+"用户开始取款，金额"+object);
			AttrCheckout.checkout(object,false,new String[]{"enterprisecode","brandcode","employeecode","orderamount","informationcode","traceip","fundpassword"});
			
			BigDecimal ordermount = new BigDecimal(String.valueOf(object.get("orderamount")));
			if(ordermount.doubleValue()<=0) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.金额异常.desc);
			}
			
			
			EnterpriseEmployee ee = super.menberAuthentication(enterpriseEmployeeService.takeEnterpriseEmployee(object));
			
			/**************禁用用户不得取款**************/
			if(ee.getEmployeestatus() == Enum_employeestatus.禁用.value.byteValue()) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.用户已禁用.desc);
			}
			
			/**************信用代理用户不得存取款**************/
			EnterpriseEmployee parentEmployee = enterpriseEmployeeService.takeEmployeeByCode(ee.getParentemployeecode());
			if(parentEmployee != null && parentEmployee.getEmployeetypecode().equals(Type.信用代理.value)) {
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.信用代用户不得存取款.desc);
			}
			

			//改为一分钟内禁止重复提交
			RepeatRequestIntercept.isIntercept(ee.getEmployeecode(), request.getRequestURI().replaceAll("/+", "/"), 60*1000);
			
			
			//取款逻辑
			if(ee.getEmployeelevelcode() != null && !ee.getEmployeelevelcode().trim().equals("")) {
				EnterpriseEmployeeLevel employeeLevel = businessEmployeeLovelServiceImpl.getOneObject(ee.getEmployeelevelcode());
				if(employeeLevel!=null){
					TakeDepositRecord record = takeDepositRecoredService.takeCountAndTakeTotalAmount(ee.getEmployeecode());
					if(record != null) {
						if(record.getQuantity() > employeeLevel.getTakeTimeOfDay()){
							return Enum_MSG.逻辑事物异常.message(Enum_MSG.提款次数已达上限.desc.replace("{0}", String.valueOf(employeeLevel.getTakeTimeOfDay())));
						}
						if( record.getOrderamount() != null) {
							if(record.getOrderamount().compareTo(employeeLevel.getTakeMoneyOfDay()) == 1){
								return Enum_MSG.逻辑事物异常.message(Enum_MSG.提款金额已达上限.desc.replace("{0}", String.valueOf(employeeLevel.getTakeMoneyOfDay())));
							}
						}
						if(null != record.getAllTakeMoney()){
							if(BigDecimal.valueOf(record.getAllTakeMoney()).compareTo(employeeLevel.getTakeMoneyOfDay()) == 1){
								return Enum_MSG.逻辑事物异常.message(Enum_MSG.提款金额已达上限.desc.replace("{0}", String.valueOf(employeeLevel.getTakeMoneyOfDay())));
							}
						}
					}
				}
			}

			
			if(StringUtils.isNotBlank(ee.getFundpassword()) &&!ee.getFundpassword().equals(Encrypt.MD5(String.valueOf(object.get("fundpassword"))))){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.资金密码错误.desc);
			}
			
			EnterpriseEmployeeInformation ei = BeanToMapUtil.convertMap(object, EnterpriseEmployeeInformation.class);
			List<EnterpriseEmployeeInformation> infomations = enterpriseEmployeeInformationService.select(
							AttrCheckout.checkout(ei,true, new String[]{"employeecode","informationcode"}));
			if(infomations == null || infomations.size()!=1) return Enum_MSG.逻辑事物异常.message(Enum_MSG.取款卡不存在.desc);
			EnterpriseEmployeeInformation infomation = infomations.get(0);
			if(!infomation.getStatus().equals(Enum_status.锁定.value)){
				return Enum_MSG.逻辑事物异常.message(Enum_MSG.银行卡未锁定.desc);
			}
			
			
			//
			//读取出款配置  如果是系统自动出款  则判断出款金额的上下限
			String __enterprisecode = String.valueOf(object.get("enterprisecode"));
			EnterprisePaymentMethodConfig __paymentMethodConfig = enterprisePaymentMethodConfigService.queryByCode(__enterprisecode);
			if(__paymentMethodConfig != null && Enum_paymentMethod.系统自动出款.value.equals(__paymentMethodConfig.getWithdralway())){
				
				//获取默认出款参数
				Map<String,Object>  __agument = enterpriseThirdpartyPaymentAgumentService.takeEDefualtPayAgument(__enterprisecode);
				String __thirdpartypaymenttypecode = String.valueOf(__agument.get("thirdpartypaymenttypecode"));
				// 获取自动出款的上下限额
				Map<String, Object> __map = new HashMap<String, Object>();
				__map.put("thirdpartypaymenttypecode", __thirdpartypaymenttypecode);
				List<EnterpriseThirdpartyPayment> __etpList = enterpriseThirdpartyPaymentService.findAll(__map);
				if(__etpList != null && __etpList.size() == 1) {
					EnterpriseThirdpartyPayment __etp = __etpList.get(0);
					BigDecimal minmoney = __etp.getMinmoney();
					BigDecimal maxmoney = __etp.getMaxmoney();
					if(ordermount.abs().compareTo(minmoney) < 0){
						log.Error("用户【" + ee.getLoginaccount() + "】的取款金额[" + ordermount + "]小于默认出款的最小值[" + minmoney + "]");
						return Enum_MSG.逻辑事物异常.message("取款金额小于[" + minmoney + "],不能取款");
					}
					if(ordermount.abs().compareTo(maxmoney) > 0){
						log.Error("用户【" + ee.getLoginaccount() + "】的取款金额[" + ordermount + "]大于默认出款的最大值[" + maxmoney + "]");
						return Enum_MSG.逻辑事物异常.message("取款金额大于[" + maxmoney + "],不能取款");
					}
				} else {
					log.Error("====================企业【" + __enterprisecode + "】设置的默认出款信息有误====================");
				}
			}
			
			//检查当前中心钱包账户是否够余额取款
			EnterpriseEmployeeCapitalAccount ec = capitalAccountService.takeCurrencyAccount(ee.getEmployeecode());
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
					balance = jsonObject.getDouble("info");
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
			
			
			
			TakeDepositRecord takeDepositRecord  = BeanToMapUtil.convertMap(object, TakeDepositRecord.class);
//			takeDepositRecord.setOrdernumber(RandomString.UUID());//jason20161115更新单号规则
			takeDepositRecord.setOrdernumber(OrderNewUtil.getOrdernoTake());//jason20161115更新单号规则，因为要将该批次号与下分、账变记录同步
			takeDepositRecord.setEmployeecode(ee.getEmployeecode());
			takeDepositRecord.setParentemployeecode(ee.getParentemployeecode());
			takeDepositRecord.setEnterprisecode(ee.getEnterprisecode());
			takeDepositRecord.setBrandcode(ee.getBrandcode());
			takeDepositRecord.setPaymenttypecode(Enum_PayType.银行卡转账.value);
			takeDepositRecord.setOrderamount(ordermount.negate());
			takeDepositRecord.setOrdertype((byte)Enum_ordertype.取款.value);
			takeDepositRecord.setEmployeepaymentname(infomation.getAccountname());
			takeDepositRecord.setEmployeepaymentaccount(infomation.getPaymentaccount());
			takeDepositRecord.setEmployeepaymentbank(infomation.getBankcode());
			takeDepositRecord.setOrderstatus((byte)Enum_orderstatus.审核中.value);
			takeDepositRecord.setOrdercreater("会员");
			takeDepositRecord.setOrdercomment("会员取款");
			takeDepositRecord.setOrderdate(new Date());
			
			/*************************处理H5百家乐独立版的HY真人游戏账号注册业务**************************/
			if( StringUtil.getString(object.get("H5BJL")).toUpperCase().equals("Y") ) {
				takeDepositRecord.setFavourableid("H5BJL");
			}
			
			takeDepositRecoredService.tc_take_money(takeDepositRecord);
			
			
			/*************************处理H5百家乐独立版的HY真人游戏账号注册业务**************************/
			if( StringUtil.getString(object.get("H5BJL")).toUpperCase().equals("Y") ) {
				//全部的分下了，但是要把剩余的分全部上掉
				EmployeeApiAccout eea = SystemCache.getInstance().getEmployeeGameAccount(takeDepositRecord.getEmployeecode(), Enum_GameType.环球.gametype);
				ec = capitalAccountService.takeCurrencyAccount(takeDepositRecord.getEmployeecode());
				
				String patchno = OrderNewUtil.getPatchno();
				int orderamount = ec.getBalance().intValue();
				APIServiceNew api = new APIServiceNew(takeDepositRecord.getEnterprisecode());
				JSONObject jsonObject2 = JSONObject.fromObject( api.upIntegralGame(new BigDecimal("0"), orderamount, Enum_GameType.环球.gametype, eea, patchno) );
				System.out.println(takeDepositRecord.getEmployeecode()+"取款后自动上分回去结果："+jsonObject2);
				
			}
			/*************************处理H5百家乐独立版的HY真人游戏账号注册业务**************************/
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), ee.getLoginaccount(), 
					UserLogs.Enum_operatype.存取款业务, "取款"+ordermount+"元", null, null));
			
			log.Error("================================"+object.get("employeecode")+"  用户完成取款，金额"+object.get("orderamount")  + "  批次号="+takeDepositRecord.getOrdernumber());
			
			return Enum_MSG.成功.message(null);
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch(NumberFormatException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch(GameAPIRequestException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		}catch(LogicTransactionRollBackException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.逻辑事物异常.message(e.getMessage());
		}catch(LogicTransactionException e){
			log.Error(e.getMessage(), e);
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
