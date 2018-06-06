package com.maven.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.annotation.OperationLog;
import com.maven.annotation.RequestInterval;
import com.maven.cache.SystemCache;
import com.maven.constant.Constant;
import com.maven.entity.Bank;
import com.maven.entity.DepositWithdralOrderDelegate;
import com.maven.entity.DepositWithdralOrderDelegate.Enum_auditresult;
import com.maven.entity.EmployeeMessage;
import com.maven.entity.EmployeeMessageText;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeInformation;
import com.maven.entity.EnterpriseEmployeeLevel;
import com.maven.entity.EnterpriseInformation;
import com.maven.entity.EnterprisePaymentMethodConfig;
import com.maven.entity.EnterprisePaymentMethodConfig.Enum_paymentMethod;
import com.maven.entity.EnterpriseThirdpartyPayment;
import com.maven.entity.PaymentType;
import com.maven.entity.PaymentType.Enum_PayType;
import com.maven.entity.TakeDepositRecord;
import com.maven.entity.TakeDepositRecord.Enum_orderstatus;
import com.maven.entity.TakeDepositRecord.Enum_ordertype;
import com.maven.entity.ThirdpartyPaymentBank;
import com.maven.entity.ThirdpartyPaymentType;
import com.maven.entity.UserLogs;
import com.maven.entity.WorkingFlowObject;
import com.maven.exception.GameAPIRequestException;
import com.maven.exception.LogicTransactionException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.game.APIServiceNew;
import com.maven.game.OrderNewUtil;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.payment.PayHelper;
import com.maven.payment.PayResult;
import com.maven.payment.th.utils.MD5Encoder;
import com.maven.service.BankService;
import com.maven.service.BusinessEmployeeLovelService;
import com.maven.service.DepositWithdralOrderDelegateService;
import com.maven.service.EmployeeMessageService;
import com.maven.service.EnterpriseEmployeeInformationService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseInformationService;
import com.maven.service.EnterprisePaymentMethodConfigService;
import com.maven.service.EnterpriseThirdpartyPaymentAgumentService;
import com.maven.service.EnterpriseThirdpartyPaymentService;
import com.maven.service.PandectService;
import com.maven.service.PaymentTypeService;
import com.maven.service.TakeDepositRecoredService;
import com.maven.service.ThirdpartyPaymentTypeService;
import com.maven.service.UserLogsService;
import com.maven.util.BankUtils;
import com.maven.util.RandomString;
import com.maven.utils.StringUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/takeDepositRecord")
public class TakeDepositRecordControll extends BaseController {

	private static LoggerManager log = LoggerManager.getLogger(TakeDepositRecordControll.class.getName(),
			OutputManager.LOG_TAKEDEPOSITRECORD);
	/** 存取款记录 */
	@Autowired
	private TakeDepositRecoredService takeDepositRecoredService;
	/** 公司支付信息 */
	@Autowired
	private EnterpriseThirdpartyPaymentService enterpriseThirdpartyPaymentService;
	/** 订单委托 */
	@Autowired
	private DepositWithdralOrderDelegateService depositWithdralOrderDelegateService;
	/** 企业信息 */
	@Autowired
	private EnterpriseInformationService enterpriseInformationService;
	/** 员工信息 */
	@Autowired
	private EnterpriseEmployeeInformationService enterpriseEmployeeInformationService;
	/** 支付方式 */
	@Autowired
	private PaymentTypeService paymentTypeService;
	/** 用户信息 */
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	/** 银行信息 */
	@Autowired
	private BankService bankService;
	@Autowired
	private EnterpriseEmployeeService employeeService;
	/** 用户级别*/
	@Autowired
	private BusinessEmployeeLovelService businessEmployeeLovelServiceImpl;
	/**公司出款方式配置*/
	@Autowired
	private EnterprisePaymentMethodConfigService enterprisePaymentMethodConfigService;
	/**第三方支付编码与秘钥*/
	@Autowired
	private EnterpriseThirdpartyPaymentAgumentService enterpriseThirdpartyPaymentAgumentService;
	/** 存储过程调用 */
	@Autowired
	private PandectService pandectService;
	@Autowired
	private EmployeeMessageService employeeMessageService;
	/**
	 * 支付渠道
	 */
	@Autowired
	ThirdpartyPaymentTypeService thirdpartyPaymentTypeService;
	@Autowired
	private UserLogsService userLogsService;
	
	/**
	 * 跳转到存取款主页面
	 * 
	 * @return url
	 */
	@RequestMapping("/takeDeposit/index")
	public String index(HttpServletRequest request, Model model, HttpSession session) {
		// 获取在permission_menu表里配置的参数{1:存款,2:取款}
		model.addAttribute("ordertype", request.getParameter("orderType"));
		//return super.validateIsEmployee(session, model) ? "/takedeposit/index" : Constant.PAGE_IDENTITY_NO_MATCH;
		
		try {
			List<ThirdpartyPaymentType> listThirdpartyPaymentType = thirdpartyPaymentTypeService.takeThirdpartyPaymentType();
			model.addAttribute("listThirdpartyPaymentType", listThirdpartyPaymentType);
			List<PaymentType> listPaymentType = paymentTypeService.getAllPaymentType();
			model.addAttribute("listPaymentType", listPaymentType);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return "/takedeposit/index";
	}

	/**
	 * 跳转到存取款明细页面
	 * 
	 * @return url
	 */
	@RequestMapping("/depositTakeRecordDatail")
	public String depositTakeDatail(HttpSession session) {
		return "/takedeposit/depositTakeRecordDatail";
	}

	/**
	 * 跳转到查询存款审批界面
	 */
	@RequestMapping("/depositApprove")
	public String depositApprove(HttpSession session, Model model) {
		//return super.validateIsEmployee(session, model) ? "/takedeposit/depositApproveIndex" : Constant.PAGE_IDENTITY_NO_MATCH;
		
		
		try {
			List<ThirdpartyPaymentType> listThirdpartyPaymentType = thirdpartyPaymentTypeService.takeThirdpartyPaymentType();
			model.addAttribute("listThirdpartyPaymentType", listThirdpartyPaymentType);
			List<PaymentType> listPaymentType = paymentTypeService.getAllPaymentType();
			model.addAttribute("listPaymentType", listPaymentType);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return "/takedeposit/depositApproveIndex";
	}
	
	/**
	 * 跳转到查询存款审批界面
	 */
	@RequestMapping("/ReadyDepositApprove")
	public String readyDepositApprove(HttpServletRequest request,HttpSession session, Model model) {
		try {
			//if(super.validateIsEmployee(session, model)){
				String _ordernumber = request.getParameter("ordernumber");
				if(super.decodeSign(_ordernumber, session.getId())){
					_ordernumber = _ordernumber.split("_")[1].split("\\|")[0];
					EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
					TakeDepositRecord _takeDepositRecord = takeDepositRecoredService
							.findOrderNumberTakeDepositRecord(_ordernumber);
					if(StringUtils.isNotBlank(_takeDepositRecord.getHandleemployee())
							&&!_takeDepositRecord.getHandleemployee().equals(ee.getLoginaccount())){
						model.addAttribute("approveEmplooye", _takeDepositRecord.getHandleemployee());
						return "/takedeposit/orderApproving";
					}else{
						_takeDepositRecord.setSign(super.encryptString(_takeDepositRecord.
								getOrdernumber()+"|"+_takeDepositRecord.getFlowcode(), session.getId()));
						Bank _bank = bankService.getBankInfo(_takeDepositRecord.getEnterprisepaymentbank());
						if(StringUtils.isBlank(_takeDepositRecord.getHandleemployee())){
							TakeDepositRecord handEmployye = new TakeDepositRecord();
							handEmployye.setOrdernumber(_takeDepositRecord.getOrdernumber());
							handEmployye.setHandleemployee(ee.getLoginaccount());
							takeDepositRecoredService.tc_updateTakeDepositRecord(handEmployye);
						}
						model.addAttribute("order", _takeDepositRecord);
						model.addAttribute("bank", _bank);
						return "/takedeposit/depositApprove";
					}
				}else{
					return Constant.PAGE_DECODEREFUSE;
				}
			/*}else{
				return Constant.PAGE_IDENTITY_NO_MATCH;
			}*/
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.AJAX_ACTIONFAILD;
		}
	}
	
	/**
	 * 跳转到查询取款审批界面
	 */
	@RequestMapping("/ReadyTakeApprove")
	public String readyTakeApprove(HttpServletRequest request,HttpSession session, Model model) {
		try {
			//if(super.validateIsEmployee(session, model)){
				String _ordernumber = request.getParameter("ordernumber");
				if(super.decodeSign(_ordernumber, session.getId())){
					_ordernumber = _ordernumber.split("_")[1].split("\\|")[0];
					EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
					TakeDepositRecord _takeDepositRecord = takeDepositRecoredService
							.findOrderNumberTakeDepositRecord(_ordernumber);
					if(StringUtils.isNotBlank(_takeDepositRecord.getHandleemployee())
							&&!_takeDepositRecord.getHandleemployee().equals(ee.getLoginaccount())){
						model.addAttribute("approveEmplooye", _takeDepositRecord.getHandleemployee());
						return "/takedeposit/orderApproving";
					}else{
						_takeDepositRecord.setSign(super.encryptString(_takeDepositRecord.
								getOrdernumber()+"|"+_takeDepositRecord.getFlowcode(), session.getId()));
						if(StringUtils.isBlank(_takeDepositRecord.getHandleemployee())){
							TakeDepositRecord handEmployye = new TakeDepositRecord();
							handEmployye.setOrdernumber(_takeDepositRecord.getOrdernumber());
							handEmployye.setHandleemployee(ee.getLoginaccount());
							takeDepositRecoredService.tc_updateTakeDepositRecord(handEmployye);
						}
						model.addAttribute("order", _takeDepositRecord);
						return "/takedeposit/takeApprove";
					}
				}else{
					return Constant.PAGE_DECODEREFUSE;
				}
			/*}else{
				return Constant.PAGE_IDENTITY_NO_MATCH;
			}*/
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.AJAX_ACTIONFAILD;
		}
	}
	
	/**
	 * 跳转到查询取款审批界面
	 */
	@RequestMapping("/takeApprove")
	public String takeApprove(HttpSession session, Model model) {
		//return super.validateIsEmployee(session, model) ? "/takedeposit/takeApproveIndex" : Constant.PAGE_IDENTITY_NO_MATCH;
		return "/takedeposit/takeApproveIndex";
	}
	
	/**
	 * 跳转到存取款清单查询页面
	 */
	@RequestMapping("/takedeposit")
	public String takedeposit(HttpSession session, Model model) {
		List<ThirdpartyPaymentType> listThirdpartyPaymentType = thirdpartyPaymentTypeService.takeThirdpartyPaymentType();
		model.addAttribute("listThirdpartyPaymentType", listThirdpartyPaymentType);
		
		try {
			List<PaymentType> listPaymentType = paymentTypeService.getAllPaymentType();
			model.addAttribute("listPaymentType", listPaymentType);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "/takedeposit/takedepositIndex";
	}
	/**
	 * 驳回处理页面
	 */
	@RequestMapping("/rejectedPage")
	public String rejectedPage(HttpSession session, Model model) {
		//return super.validateIsEmployee(session, model) ? "/takedeposit/rejectedIndex": Constant.PAGE_IDENTITY_NO_MATCH;
		return "/takedeposit/rejectedIndex";
	}
	
	/**
	 * 待出款处理页面
	 */
	@RequestMapping("/waitPayment")
	public String waitPayment(HttpSession session, Model model) {
		//return super.validateIsEmployee(session, model) ? "/takedeposit/waitPayment": Constant.PAGE_IDENTITY_NO_MATCH;
		return "/takedeposit/waitPayment";
	}
	
	/**
	 * 财务手动出款页面
	 */
	@RequestMapping(value={"/accountingManuallyPayment","/RefusePayment"})
	public String accountingManuallyPayment(HttpSession session,HttpServletRequest request, Model model) {
		try{
			String ordernumber = request.getParameter("ordernumber");
			if(super.decodeSign(ordernumber, session.getId())){
				TakeDepositRecord takeDepositRecord = takeDepositRecoredService.findOrderNumberTakeDepositRecord(ordernumber.split("_")[1].split("\\|")[0]);
				EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
				if(StringUtils.isNotBlank(takeDepositRecord.getHandleemployee())
						&&!takeDepositRecord.getHandleemployee().equals(ee.getLoginaccount())){
					model.addAttribute("approveEmplooye", takeDepositRecord.getHandleemployee());
					return "/takedeposit/orderApproving";
				}else{
					if(StringUtils.isBlank(takeDepositRecord.getHandleemployee())){
						TakeDepositRecord handEmployye = new TakeDepositRecord();
						handEmployye.setOrdernumber(takeDepositRecord.getOrdernumber());
						handEmployye.setHandleemployee(ee.getLoginaccount());
						takeDepositRecoredService.tc_updateTakeDepositRecord(handEmployye);
					}
					EnterpriseEmployeeInformation __eei = enterpriseEmployeeInformationService.findEnterpriseEmployeeInformation(takeDepositRecord.getEnterprisecode(),takeDepositRecord.getEmployeepaymentaccount());
					takeDepositRecord.setSign(super.encryptString(takeDepositRecord.getOrdernumber()+"|"+takeDepositRecord.getFlowcode(), session.getId()));
					model.addAttribute("order", takeDepositRecord);
					model.addAttribute("ebank", __eei);
					//该变量用来区分是财务出款提交还是拒绝提交
					model.addAttribute("orderMark", request.getParameter("orderMark"));
					return "/takedeposit/accountingManuallyPayment";
				}
			}else{
				return Constant.PAGE_DECODEREFUSE;
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.AJAX_ACTIONFAILD;
		}
	}
	

	/**
	 * 跳转取款录单页面
	 * 
	 * @return
	 */
	@RequestMapping("/TakeAdd")
	public String takeAdd(HttpSession session, Model model) {
		//return super.validateIsEmployee(session, model) ? "/takedeposit/takeAdd" : Constant.PAGE_IDENTITY_NO_MATCH;
		return "/takedeposit/takeAdd";
	}

	
	/**
	 * 跳转存款录单页面
	 * 
	 * @return
	 */
	@RequestMapping("/DepositAdd")
	public String depositAdd(HttpSession session, Model model) {
		try {
			//if (super.validateIsEmployee(session, model)) {
				EnterpriseEmployee _ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
				EnterpriseInformation _eio = new EnterpriseInformation();
				_eio.setEnterprisecode(_ee.getEnterprisecode());
				List<EnterpriseInformation> _enterprise_banks = enterpriseInformationService.select(_eio);
				List<PaymentType> _paymentType = paymentTypeService.getAllPaymentType();
				List<Bank> _banks = SystemCache.getInstance().getBanks();
				model.addAttribute("enterprisebanks", _enterprise_banks);
				model.addAttribute("paytypes", _paymentType);
				model.addAttribute("banks", _banks);
				return "/takedeposit/depositAdd";
			/*} else {
				return Constant.PAGE_IDENTITY_NO_MATCH;
			}*/
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}

	}
	
	/**
	 * 保存存款订单
	 * 
	 * @return
	 */
	@RequestMapping("/SaveDepositOrders")
	@ResponseBody
	@RequestInterval(millinsecond=3000)
	public Map<String, Object> saveDepositOrders(@ModelAttribute TakeDepositRecord takeDepositRecord,
			HttpSession session, Model model) {
		try {
			// 获取当前登录用户对象
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			// 根据企业银行卡查询单条信息
			EnterpriseInformation enterpriseInformation = enterpriseInformationService
					.selectByPrimaryKey(takeDepositRecord.getEnterprisepaymentaccount());
			if (enterpriseInformation == null) {
				return super.packJSON(Constant.BooleanByte.NO, "录单失败，收款账号不存在");
			}
			EnterpriseEmployee depositeUsers =  enterpriseEmployeeService.takeEmployeeByCode(takeDepositRecord.getEmployeecode());
			if(depositeUsers==null){
				return super.packJSON(Constant.BooleanByte.NO, "录单失败，存款用户不存在");
			}
			if(takeDepositRecord.getOrderamount()==null||takeDepositRecord.getOrderamount().doubleValue()<=0){
				return super.packJSON(Constant.BooleanByte.NO, "录单失败，存款金额不能少于或等于0");
			}
			// 32位订单号
			takeDepositRecord.setOrdernumber(RandomString.UUID());
			// 存款用户企业编码
			takeDepositRecord.setEnterprisecode(depositeUsers.getEnterprisecode());
			// 存款用户品牌编码
			takeDepositRecord.setBrandcode(depositeUsers.getBrandcode());
			// 存款用户上级编码
			takeDepositRecord.setParentemployeecode(depositeUsers.getParentemployeecode());
			// 订单创建时间
			takeDepositRecord.setOrderdate(new Date());
			// 企业收款人姓名
			takeDepositRecord.setEnterprisepaymentname(enterpriseInformation.getAccountname());
			// 企业收款账号
			takeDepositRecord.setEnterprisepaymentaccount(enterpriseInformation.getOpenningaccount());
			// 企业收付款银行编码
			takeDepositRecord.setEnterprisepaymentbank(enterpriseInformation.getBankcode());
			// 订单类型
			takeDepositRecord.setOrdertype((byte) Enum_ordertype.存款.value);
			// 订单状态
			takeDepositRecord.setOrderstatus((byte) Enum_orderstatus.审核中.value);
			// 设置订单创建人
			takeDepositRecord.setOrdercreater(ee.getLoginaccount());
			// 订单创建客户端IP
			takeDepositRecord.setTraceip(InetAddress.getLocalHost().getHostAddress());
			// 保存存取款订单
			takeDepositRecoredService.tc_save_money(takeDepositRecord);
			
			userLogsService.addActivityBetRecord(new UserLogs(takeDepositRecord.getEnterprisecode(), takeDepositRecord.getEmployeecode(), takeDepositRecord.getLoginaccount(), 
				     UserLogs.Enum_operatype.存取款业务, "员工手工提交存款补单:"+takeDepositRecord.getOrdernumber(), ee.getLoginaccount(), null));
			
			String content = "您单号为"+takeDepositRecord.getOrdernumber()+"的"+(takeDepositRecord.getOrdertype().intValue() == 1 ? "存款":"取款")+"订单已由客服手工提交！";
			sendMessage(session, content, takeDepositRecord.getEmployeecode(), takeDepositRecord.getLoginaccount());
			
			return super.packJSON(Constant.BooleanByte.YES, "录单成功");
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "录单失败，请稍后尝试");
		}
	}
	
	

	/**
	 * 保存取款订单
	 * 
	 * @return
	 */
	@RequestMapping("/SaveTakeOrders")
	@ResponseBody
	@RequestInterval(millinsecond=3000)
	public Map<String, Object> saveTakeDepositOrders(HttpServletRequest request,HttpSession session) {
		try {
			// 获取当前登录用户对象
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			TakeDepositRecord takeDepositRecord = super.getRequestParamters(request, TakeDepositRecord.class);
			if(super.decodeSign(takeDepositRecord.getEmployeepaymentaccount(), session.getId())){
				String informationcode = takeDepositRecord.getEmployeepaymentaccount().split("_")[1];
				// 根据企业银行卡查询单条信息
				EnterpriseEmployeeInformation employeeInformation = enterpriseEmployeeInformationService.findOneBankInfo(informationcode);
				if (employeeInformation == null) {
					return super.packJSON(Constant.BooleanByte.NO, "录单失败，取款银行卡不存在");
				}
				EnterpriseEmployee depositeUsers =  enterpriseEmployeeService.takeEmployeeByCode(takeDepositRecord.getEmployeecode());
				if(depositeUsers==null){
					return super.packJSON(Constant.BooleanByte.NO, "录单失败，取款用户不存在");
				}
				EnterpriseEmployeeLevel employeeLevel = businessEmployeeLovelServiceImpl.getOneObject(depositeUsers.getEmployeelevelcode());
				if(employeeLevel!=null){
					TakeDepositRecord record = takeDepositRecoredService.takeCountAndTakeTotalAmount(depositeUsers.getEmployeecode());
					if(record.getQuantity() > employeeLevel.getTakeTimeOfDay()){
						return super.packJSON(Constant.BooleanByte.NO, "您每天的提款次数为"+employeeLevel.getTakeTimeOfDay()+"笔,今天的提款次数已达上限");
					}
					if(takeDepositRecord.getOrderamount().compareTo(employeeLevel.getTakeMoneyOfDay()) == 1){
						return super.packJSON(Constant.BooleanByte.NO, "您每天的提款上限为"+employeeLevel.getTakeMoneyOfDay()+"元,今日提款已达上限");
					}
					if(null != record.getAllTakeMoney()){
						if(BigDecimal.valueOf(record.getAllTakeMoney()).compareTo(employeeLevel.getTakeMoneyOfDay()) == 1){
							return super.packJSON(Constant.BooleanByte.NO, "您每天的提款上限为"+employeeLevel.getTakeMoneyOfDay()+"元,今日提款已达上限");
						}
					}
				}
				
				if(takeDepositRecord.getOrderamount()==null||takeDepositRecord.getOrderamount().doubleValue()<=0){
					return super.packJSON(Constant.BooleanByte.NO, "录单失败，取款金额不能少于或等于0");
				}				
				// 32位订单号
//				takeDepositRecord.setOrdernumber(RandomString.UUID());//jason20161115更新单号规则
				takeDepositRecord.setOrdernumber(OrderNewUtil.getOrdernoTake());//jason20161115更新单号规则，因为要将该批次号与下分、账变记录同步
				// 存款用户企业编码
				takeDepositRecord.setEnterprisecode(depositeUsers.getEnterprisecode());
				// 存款用户品牌编码
				takeDepositRecord.setBrandcode(depositeUsers.getBrandcode());
				// 用户上级编码
				takeDepositRecord.setParentemployeecode(depositeUsers.getParentemployeecode());
				// 订单创建时间
				takeDepositRecord.setOrderdate(new Date());
				// 取款人姓名
				takeDepositRecord.setEmployeepaymentname(employeeInformation.getAccountname());
				// 取款银行
				takeDepositRecord.setEmployeepaymentbank(employeeInformation.getBankcode());
				// 取款人银行卡
				takeDepositRecord.setEmployeepaymentaccount(employeeInformation.getPaymentaccount());
				// 取款金额
				takeDepositRecord.setOrderamount(takeDepositRecord.getOrderamount().negate());
				// 订单类型
				takeDepositRecord.setOrdertype((byte) Enum_ordertype.取款.value);
				// 订单状态
				takeDepositRecord.setOrderstatus((byte) Enum_orderstatus.审核中.value);
				// 设置订单创建人
				takeDepositRecord.setOrdercreater(ee.getLoginaccount());
				// 订单创建客户端IP
				takeDepositRecord.setTraceip(InetAddress.getLocalHost().getHostAddress());
				// 保存存取款订单
				takeDepositRecoredService.tc_take_money(takeDepositRecord);
				
				userLogsService.addActivityBetRecord(new UserLogs(takeDepositRecord.getEnterprisecode(), takeDepositRecord.getEmployeecode(), takeDepositRecord.getLoginaccount(), 
					     UserLogs.Enum_operatype.存取款业务, "员工手工提交取款补单:"+takeDepositRecord.getOrdernumber(), ee.getLoginaccount(), null));
				
				String content = "您单号为"+takeDepositRecord.getOrdernumber()+"的"+(takeDepositRecord.getOrdertype().intValue() == 1 ? "存款":"取款")+"订单已由客服手工提交！";
				sendMessage(session, content, takeDepositRecord.getEmployeecode(), takeDepositRecord.getLoginaccount());
				
				return super.packJSON(Constant.BooleanByte.YES, "录单成功");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		}catch(GameAPIRequestException e){
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "下分失败，请稍后尝试");
		}catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "下分失败，请稍后尝试");
		}catch(LogicTransactionException e){
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "下分失败，请稍后尝试");
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "录单失败，请稍后尝试");
		}
	}
	
	/**
	 * 查询待出款的取款订单
	 * @param request
	 * @param session
	 * @return Map<String,Object>
	 */
	@RequestMapping("/findWaitPayment")
	@ResponseBody
	public Map<String, Object> findWaitPayment(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			findParentEmoloyeeCode(paramObj);
			super.dataLimits(loginEmployee, paramObj,session);
			paramObj.put("orderstatus", TakeDepositRecord.Enum_orderstatus.待出款.value);
			paramObj.put("ordertype", Enum_ordertype.取款.value);
			List<TakeDepositRecord> list = takeDepositRecoredService.findTakeDepositRecord(paramObj);
			String employeepaymentbank = "";
			for (TakeDepositRecord takeDepositRecord : list) {
				takeDepositRecord.setSign(super.encryptString(takeDepositRecord.getOrdernumber()+"|"+takeDepositRecord.getFlowcode(), session.getId()));
				employeepaymentbank = takeDepositRecord.getEmployeepaymentbank();
				takeDepositRecord.setEmployeepaymentbank(BankUtils.getBankName(employeepaymentbank));
				employeepaymentbank = "";
			}
			Map<String, Object> result = takeDepositRecoredService.findcountTakeDepositRecord(paramObj);
			int countRecord = StringUtil.getInt(result.get("count"));
			//return super.formatPagaMap(list, countRecord);
			
			Map<String, Object> data = super.formatPagaMap(list, countRecord);
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("orderamount", StringUtil.getDouble(result.get("orderamount")));
			data.put("summary", summary);
			return data;
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	/** 导出待出款的取款订单
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/excelWaitPayment")
	public String excelWaitPayment(Model model, HttpSession session, HttpServletRequest request) {
		
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			findParentEmoloyeeCode(paramObj);
			super.dataLimits(loginEmployee, paramObj,session);
			paramObj.put("orderstatus", TakeDepositRecord.Enum_orderstatus.待出款.value);
			paramObj.put("ordertype", Enum_ordertype.取款.value);
			List<TakeDepositRecord> list = takeDepositRecoredService.findTakeDepositRecord(paramObj);
			
			model.addAttribute("listTakeDepositRecord", list);
			model.addAttribute("title", "待出款的取款订单");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		
		return "/takedeposit/orderListExcel";
	}
	
	/**
	 * 待出款订单(批量出款与快速出款)
	 * @param session
	 * @param request
	 */
	@RequestMapping("/systemAutoPayment")
	@ResponseBody
	@RequestInterval(millinsecond=3000)
	public List<Map<String, Object>> DCK_SystemAutoPayment(HttpSession session, HttpServletRequest request){
		List<Map<String, Object>> msgList = new ArrayList<Map<String, Object>>();
		try {
			String orders = request.getParameter("orders");
			if(StringUtils.isBlank(orders)){
				Map<String, Object> promptMap = new HashMap<String, Object>();
				promptMap.put("prompt", "请选择需要出款的订单");
				msgList.add(promptMap);
				return msgList;
			}
			String[] worders = orders.split(",");
			if(!super.decodeSign(worders, session.getId())){
				Map<String, Object> promptMap = new HashMap<String, Object>();
				promptMap.put("prompt", Constant.AJAX_DECODEREFUSE);
				msgList.add(promptMap);
				return msgList;
			}else{
				for (int i=0;i<worders.length;i++ ){
					worders[i]=worders[i].split("_")[1].split("\\|")[0];
				}
			}
			EnterpriseEmployee __ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			//读取出款配置
			EnterprisePaymentMethodConfig paymentMethodConfig = enterprisePaymentMethodConfigService.queryByCode(__ee.getEnterprisecode());
			if(paymentMethodConfig == null || !Enum_paymentMethod.系统自动出款.value.equals(paymentMethodConfig.getWithdralway())){
				Map<String, Object> promptMap = new HashMap<String, Object>();
				promptMap.put("prompt", "未启用系统自动出款");
				msgList.add(promptMap);
				return msgList;
			}
			if(null == paymentMethodConfig.getUpperlimit()){
				Map<String, Object> promptMap = new HashMap<String, Object>();
				promptMap.put("prompt", "未设置自动出款下限");
				msgList.add(promptMap);
				return msgList;
			}
			//获取默认出款参数
			Map<String,Object>  __agument = enterpriseThirdpartyPaymentAgumentService.takeEDefualtPayAgument(__ee.getEnterprisecode());
			String __thirdpartypaymenttypecode = String.valueOf(__agument.get("thirdpartypaymenttypecode"));
			if(StringUtils.isBlank(__thirdpartypaymenttypecode)){
				Map<String, Object> promptMap = new HashMap<String, Object>();
				promptMap.put("prompt", "未设置默认的快捷支付");
				msgList.add(promptMap);
				return msgList;
			}

			
			//设置当前出款人
			Map<String,Object> object = new HashMap<String, Object>();
			object.put("handleemployee", __ee.getLoginaccount());
			object.put("orders", worders);
			takeDepositRecoredService.updateHandEmployee(object);
			List<TakeDepositRecord> tdorders  = takeDepositRecoredService.findMutilOrdersByOrdernumber(worders);
			
			// 获取自动出款的上下限额
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("thirdpartypaymenttypecode", __thirdpartypaymenttypecode);
			EnterpriseThirdpartyPayment etp = enterpriseThirdpartyPaymentService.findAll(map).get(0);
			BigDecimal minmoney = etp.getMinmoney();
			BigDecimal maxmoney = etp.getMaxmoney();
			
			for (TakeDepositRecord __takeRecord : tdorders) {
				Map<String, Object> errorMap = new HashMap<String, Object>();
				
				if(__takeRecord.getOrderamount().abs().compareTo(minmoney) < 0) {
					errorMap.put("orderNumber", __takeRecord.getOrdernumber());
					errorMap.put("errorMsg", "该笔订单金额小于出款最小值");
					msgList.add(errorMap);
					continue;
				}
				
				if(__takeRecord.getOrderamount().abs().compareTo(maxmoney) > 0) {
					errorMap.put("orderNumber", __takeRecord.getOrdernumber());
					errorMap.put("errorMsg", "该笔订单金额大于出款最大值");
					msgList.add(errorMap);
					continue;
				}
				
				if(!__takeRecord.getFlowcode().equals(Constant.DCKGZLBM)
						||__takeRecord.getOrderstatus()!=(byte)Enum_orderstatus.待出款.value){
					errorMap.put("orderNumber", __takeRecord.getOrdernumber());
					errorMap.put("errorMsg", "该笔订单未进入到待出款流程");
					msgList.add(errorMap);
					continue;
				}
				
				if(!__takeRecord.getHandleemployee().equals(__ee.getLoginaccount())){
					errorMap.put("orderNumber", __takeRecord.getOrdernumber());
					errorMap.put("errorMsg", "该笔订单 "+__takeRecord.getHandleemployee()+" 正在进行处理中...");
					msgList.add(errorMap);
					continue;
				}
				//判断订单金额超出自动出款上限
				if(__takeRecord.getOrderamount().abs().compareTo(paymentMethodConfig.getUpperlimit()) == 1){
					errorMap.put("orderNumber", __takeRecord.getOrdernumber());
					errorMap.put("errorMsg", "单笔订单金额不能大于公司设置的出款金额("+paymentMethodConfig.getUpperlimit()+"),请手动出款");
					msgList.add(errorMap);
					continue;
				}
				
				ThirdpartyPaymentBank __thirdpartyPaymentBank = SystemCache.getInstance().getThirdpartyPaymentBank(__thirdpartypaymenttypecode, __takeRecord.getEmployeepaymentbank());
				if(__thirdpartyPaymentBank==null){
					errorMap.put("orderNumber", __takeRecord.getOrdernumber());
					errorMap.put("errorMsg", "请联系技术人员检查当前默认出款方式是否支持会员取款银行，或者请手动出款。本次会员取款银行："+__takeRecord.getEmployeepaymentbank());
					msgList.add(errorMap);
					continue;
				}
				
				EnterpriseEmployee ____eee = enterpriseEmployeeService.takeEmployeeByCode(__takeRecord.getEmployeecode());
				
				System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + " - TakeDepositRecordControll - 准备发起代付：" + __takeRecord.getOrdernumber());
				//进行支付
				PayResult result = PayHelper.Pay(__agument, __takeRecord, __thirdpartyPaymentBank);
				//根据支付结果进行相应处理
				if(result.getResult()){
					//记录出款
					DepositWithdralOrderDelegate ndelegate = new DepositWithdralOrderDelegate(__takeRecord.getOrdernumber(), Constant.DCKGZLBM,  90);
					ndelegate.setEndtime(new Date());
					ndelegate.setAuditresult(Enum_auditresult.通过.value.byteValue());
					ndelegate.setAuditdesc(result.getPaytype());
					ndelegate.setAssignedtocode(__ee.getEmployeecode());
					ndelegate.setAssignedtoaccount(__ee.getLoginaccount());
					ndelegate.setOrdernumber(__takeRecord.getOrdernumber());
					ndelegate.setDelegatecode(__takeRecord.getDelegatecode());
					depositWithdralOrderDelegateService.updateDelegate(ndelegate);

					TakeDepositRecord takeRecord = new TakeDepositRecord();
					takeRecord.setOrdernumber(__takeRecord.getOrdernumber());
					takeRecord.setOrderstatus((byte)Enum_orderstatus.审核通过.value);
					takeRecord.setHandleovertime(new Date());
					takeRecord.setPaymenttypecode(Enum_PayType.第三方即时支付.value);
					takeRecord.setEnterprisepaymentname(etp.getDisplayname());
					takeRecord.setEnterprisepaymentaccount(etp.getEnterprisethirdpartycode());
					takeRecord.setOrdercomment("完成审批");
					takeDepositRecoredService.tc_updateTakeDepositRecord(takeRecord);
					
					userLogsService.addActivityBetRecord(new UserLogs(__takeRecord.getEnterprisecode(), __takeRecord.getEmployeecode(), __takeRecord.getLoginaccount(), 
						     UserLogs.Enum_operatype.存取款业务, "快速出款与批量出款:"+__takeRecord.getOrdernumber(), __ee.getLoginaccount(), null));
					
					String content = "您单号为"+__takeRecord.getOrdernumber()+"的"+(__takeRecord.getOrdertype().intValue() == 1 ? "存款":"取款")+"订单已完成审批，请等待到账！";
					sendMessage(session, content, __takeRecord.getEmployeecode(), ____eee.getLoginaccount());
					
				}else{
					errorMap.put("orderNumber", __takeRecord.getOrdernumber());
					errorMap.put("errorMsg", result.getDescription());
					msgList.add(errorMap);
					
					String content = "您单号为"+__takeRecord.getOrdernumber()+"的"+(__takeRecord.getOrdertype().intValue() == 1 ? "存款":"取款")+"订单出款失败，原因："+result.getDescription();
					sendMessage(session, content, __takeRecord.getEmployeecode(), ____eee.getLoginaccount());
				}
			}
			if(msgList.size()>0) return msgList;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			Map<String, Object> promptMap = new HashMap<String, Object>();
			promptMap.put("prompt", Constant.AJAX_ACTIONFAILD);
			msgList.add(promptMap);
			return msgList;
		}
		return null;
	}

	
	
	/**
	 * 待出款订单(财务手动出款)
	 * @param request
	 * @param session
	 * @return Map<String,Object>
	 */
	@RequestMapping("/confirmPayment")
	@ResponseBody
	@RequestInterval(millinsecond=3000)
	public Map<String, Object> DCK_ConfirmPayment(HttpServletRequest request, HttpSession session) {
		EnterpriseEmployee __ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
		try {
			String ordernumber = request.getParameter("ordernumber");
			if(super.decodeSign(ordernumber, session.getId())){
				ordernumber = ordernumber.split("_")[1].split("\\|")[0];
				TakeDepositRecord __takeRecord = takeDepositRecoredService.findOrderNumberTakeDepositRecord(ordernumber);
				if(!__takeRecord.getFlowcode().equals(Constant.DCKGZLBM)
						||__takeRecord.getOrderstatus()!=(byte)Enum_orderstatus.待出款.value){
					return super.packJSON(Constant.BooleanByte.NO, "订单状态异常,该订单不属于待出款订单");
				}
				DepositWithdralOrderDelegate ndelegate = new DepositWithdralOrderDelegate(ordernumber, Constant.DCKGZLBM,  90);
				ndelegate.setEndtime(new Date());
				ndelegate.setAuditresult(Enum_auditresult.通过.value.byteValue());
				ndelegate.setAuditdesc(request.getParameter("auditdesc"));
				ndelegate.setAssignedtocode(__ee.getEmployeecode());
				ndelegate.setAssignedtoaccount(__ee.getLoginaccount());
				ndelegate.setOrdernumber(__takeRecord.getOrdernumber());
				ndelegate.setDelegatecode(__takeRecord.getDelegatecode());
				ndelegate.setImgname(request.getParameter("imgname"));
				depositWithdralOrderDelegateService.updateDelegate(ndelegate);

				TakeDepositRecord takeRecord = new TakeDepositRecord();
				takeRecord.setOrdernumber(__takeRecord.getOrdernumber());
				takeRecord.setOrderstatus((byte)Enum_orderstatus.审核通过.value);
				takeRecord.setHandleovertime(new Date());
				takeRecord.setPaymenttypecode(Enum_PayType.银行卡转账.value);
				takeRecord.setEnterprisepaymentname("手动出款");
				takeRecord.setOrdercomment("完成审批");
				takeDepositRecoredService.tc_updateTakeDepositRecord(takeRecord);
				
				userLogsService.addActivityBetRecord(new UserLogs(__takeRecord.getEnterprisecode(), __takeRecord.getEmployeecode(), __takeRecord.getLoginaccount(), 
					     UserLogs.Enum_operatype.存取款业务, "财务手动出款:"+__takeRecord.getOrdernumber(), __ee.getLoginaccount(), null));
				
				String content = "您单号为"+takeRecord.getOrdernumber()+"的取款订单已由客服手动出款，请等待到账！";
				sendMessage(session, content, takeRecord.getEmployeecode(), takeRecord.getLoginaccount());
				
				return super.packJSON(Constant.BooleanByte.YES, "出款成功");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "出款失败");
		}
	}
	/**
	 * 待出款订单(财务拒绝出款)
	 * @param request
	 * @param session
	 * @return Map<String,Object>
	 */
	@RequestMapping("/refusaPayment")
	@ResponseBody
	@RequestInterval(millinsecond=3000)
	public Map<String, Object> DCK_RefusaPayment(HttpServletRequest request, HttpSession session) {
		EnterpriseEmployee __ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
		try {
			String ordernumber = request.getParameter("ordernumber");
			if(super.decodeSign(ordernumber, session.getId())){
				ordernumber = ordernumber.split("_")[1].split("\\|")[0];
				TakeDepositRecord __takeRecord = takeDepositRecoredService.findOrderNumberTakeDepositRecord(ordernumber);
				if(!__takeRecord.getFlowcode().equals(Constant.DCKGZLBM)){
					return super.packJSON(Constant.BooleanByte.NO, "订单状态异常,该订单不属于待出款订单");
				}
				DepositWithdralOrderDelegate __ndelegate = new DepositWithdralOrderDelegate();
				__ndelegate.setAuditdesc(request.getParameter("auditdesc"));
				__ndelegate.setAssignedtocode(__ee.getEmployeecode());
				__ndelegate.setAssignedtoaccount(__ee.getLoginaccount());
				__ndelegate.setImgname(request.getParameter("imgname"));
				depositWithdralOrderDelegateService.tc_refuseDrawAmount(__takeRecord, __ndelegate);
				
				userLogsService.addActivityBetRecord(new UserLogs(__takeRecord.getEnterprisecode(), __takeRecord.getEmployeecode(), __takeRecord.getLoginaccount(), 
					UserLogs.Enum_operatype.存取款业务, "财务拒绝出款:"+__takeRecord.getOrdernumber(), __ee.getLoginaccount(), null));
				
				String content = "您单号为"+__takeRecord.getOrdernumber()+"的"+(__takeRecord.getOrdertype().intValue() == 1 ? "存款":"取款")+"订单客服已拒绝出款："+request.getParameter("auditdesc");
				sendMessage(session, content, __takeRecord.getEmployeecode(), __takeRecord.getLoginaccount());
				
				return super.packJSON(Constant.BooleanByte.YES, "出款已拒绝");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "出款失败");
		}
	}
	
	/**
	 * 查询存取款记录数据
	 * 
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/findTakeDepositRecord")
	@ResponseBody
	public Map<String, Object> findTakeDepositRecord(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			findParentEmoloyeeCode(paramObj);
			paramObj.put("order_statuss", new Integer[]{Enum_orderstatus.审核通过.value,Enum_orderstatus.拒绝.value});
			paramObj.put("flowcodes", new ArrayList<WorkingFlowObject>());
			super.dataLimits(loginEmployee, paramObj,session);
			// 查询分页数据
			List<TakeDepositRecord> list = takeDepositRecoredService.findTakeDepositRecord(paramObj);
			if (null != list && list.size() > 0) {
				String employeepaymentbank = "";
				for (TakeDepositRecord tdr : list) {
					employeepaymentbank = tdr.getEmployeepaymentbank();
					tdr.setEmployeepaymentbank(BankUtils.getBankName(employeepaymentbank));
					employeepaymentbank = "";
				}
			}
			// 统计数据
			Map<String, Object> result = takeDepositRecoredService.findcountTakeDepositRecord(paramObj);
			int countRecord = StringUtil.getInt(result.get("count"));
			map.put("rows", list);
			map.put("results", countRecord);
			
			//Map<String, Object> data = super.formatPagaMap(list, countRecord);
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("orderamount", StringUtil.getDouble(result.get("orderamount")));
			map.put("summary", summary);
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return map;
	}
	
	/** 导出存取款记录数据
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/excelTakeDepositRecord")
	public String excelTakeDepositRecord(HttpSession session, HttpServletRequest request, Model model) {
		
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			findParentEmoloyeeCode(paramObj);
			paramObj.put("order_statuss", new Integer[]{Enum_orderstatus.审核通过.value,Enum_orderstatus.拒绝.value});
			paramObj.put("flowcodes", new ArrayList<WorkingFlowObject>());
			super.dataLimits(loginEmployee, paramObj,session);
			
			if(StringUtil.getInt(paramObj.get("ordertype")) == Enum_ordertype.取款.value ) {
				model.addAttribute("title", "取款记录数据");
			} else {
				model.addAttribute("title", "存款记录数据");
			}
			paramObj.put("field", "orderdate");
			paramObj.put("direction", "desc");
			List<TakeDepositRecord> list = takeDepositRecoredService.findTakeDepositRecord(paramObj);
			if (null != list && list.size() > 0) {
				String employeepaymentbank = "";
				for (TakeDepositRecord tdr : list) {
					employeepaymentbank = tdr.getEmployeepaymentbank();
					tdr.setEmployeepaymentbank(BankUtils.getBankName(employeepaymentbank));
					employeepaymentbank = "";
				}
			}
			model.addAttribute("listTakeDepositRecord", list);
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		
		return "/takedeposit/orderListExcel";
	}
	
	
	/**
	 * 查询存取款清单
	 * 
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/findTakeDepositRecordAll")
	@ResponseBody
	public Map<String, Object> findTakeDepositRecordAll(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			findParentEmoloyeeCode(paramObj);
			super.dataLimits(loginEmployee, paramObj,session);
			// 查询分页数据
			List<TakeDepositRecord> list = takeDepositRecoredService.findTakeDepositRecord(paramObj);
			// 统计数据
			Map<String, Object> result = takeDepositRecoredService.findcountTakeDepositRecord(paramObj);
			int countRecord = StringUtil.getInt(result.get("count"));
			map.put("rows", list);
			map.put("results", countRecord);
			
			//Map<String, Object> data = super.formatPagaMap(list, countRecord);
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("orderamount", StringUtil.getDouble(result.get("orderamount")));
			map.put("summary", summary);
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return map;
	}
	
	/** 导出存取款记录数据
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/excelTakeDepositRecordAll")
	public String excelTakeDepositRecordAll(HttpSession session, HttpServletRequest request, Model model) {
		
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			findParentEmoloyeeCode(paramObj);
			super.dataLimits(loginEmployee, paramObj,session);
			// 查询分页数据
			List<TakeDepositRecord> list = takeDepositRecoredService.findTakeDepositRecord(paramObj);
			
			model.addAttribute("listTakeDepositRecord", list);
			model.addAttribute("title", "存取款记录清单");
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		
		return "/takedeposit/orderListExcel";
	}

	/**
	 * 根据单条订单号删除记录
	 * 
	 * @param session
	 * @param request
	 * @return Map<String,String>
	 */
	@RequestMapping("/deleteTakeRepositRecord")
	@OperationLog(OparetionDescription.DEPOSITTAKE_DELETE)
	@ResponseBody
	public Map<String, String> deleteTakeRepositRecord(HttpSession session, HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		// 获取需要删除的CODE
		String code = request.getParameter("deleteCode");
		// 解密标识字段的值
		// boolean mark = this.decodeSign(code, session.getId());
		// 调用删除方法
		try {
			takeDepositRecoredService.tc_deleteTakeRepositRecord(code);
			
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			userLogsService.addActivityBetRecord(new UserLogs(loginEmployee.getEnterprisecode(), loginEmployee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.存取款业务, "员工删除订单:"+code, loginEmployee.getLoginaccount(), null));
			
			
			map.put("status", "success");
		} catch (Exception e) {
			map.put("status", "failure");
			log.Error(e.getMessage(), e);
		}
		return map;

	}

	/**
	 * 删除选中的一条或者多条数据
	 * 
	 * @param request
	 */
	@RequestMapping("/deleteSelectAllTakeRepositRecord")
	@OperationLog(OparetionDescription.DEPOSITTAKE_DELETE)
	public void deleteSelectEmployee(HttpServletRequest request) {
		String temp = request.getParameter("paramArray");
		String[] array = temp.split(",");
		try {
			takeDepositRecoredService.tc_deleteSelectAllTakeRepositRecord(array);
			
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) request.getSession().getAttribute(Constant.USER_SESSION);
			for (String string : array) {
				userLogsService.addActivityBetRecord(new UserLogs(loginEmployee.getEnterprisecode(), loginEmployee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.存取款业务, "员工批量删除订单:"+string, loginEmployee.getLoginaccount(), null));
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
	}

	/**
	 * 撤销审核
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/closeAuidit")
	@ResponseBody
	public Map<String, Object> cacelAudit(Model model, HttpSession session, HttpServletRequest request) {
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			String ordernumber = request.getParameter("ordernumber");
			if(super.decodeSign(ordernumber, session.getId())){
				String[] order = ordernumber.split("_")[1].split("\\|");
				ordernumber = order[0];
				TakeDepositRecord takeDepositRecord = new TakeDepositRecord();
				takeDepositRecord.setOrdernumber(ordernumber);
				takeDepositRecord.setHandleemployee(ee.getLoginaccount());
				takeDepositRecoredService.tc_cancelAudit(takeDepositRecord);
				
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.存取款业务, "员工撤销审核订单:"+takeDepositRecord.getOrdernumber(), ee.getLoginaccount(), null));
				
				if(takeDepositRecord != null && takeDepositRecord.getOrdertype() != null) {
					String content = "您单号为"+takeDepositRecord.getOrdernumber()+"的"+(takeDepositRecord.getOrdertype().intValue() == 1 ? "存款":"取款")+"订单客服已撤销审核！";
					sendMessage(session, content, takeDepositRecord.getEmployeecode(), takeDepositRecord.getLoginaccount());
				}
				
				
				return this.packJSON(Constant.BooleanByte.YES, "已撤销");
			}else{
				return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		}catch(LogicTransactionRollBackException e){
			return this.packJSON(Constant.BooleanByte.NO, e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	

	protected boolean sendMessage(HttpSession session , String content, String Acceptemployeecode, String Acceptaccount) {
		
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
	 * 存取款单据审批方法
	 * @param session
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/approve")
	@ResponseBody
	@RequestInterval(millinsecond=3000)
	public Map<String, Object> approve(Model model, HttpSession session, HttpServletRequest request) {
		try {
			// 获取当前登录用户的信息
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			DepositWithdralOrderDelegate delegete = super.getRequestParamters(request, DepositWithdralOrderDelegate.class);
			if(super.decodeSign(delegete.getOrdernumber(), session.getId())){
				String[] order = delegete.getOrdernumber().split("_")[1].split("\\|");
				delegete.setAssignedtocode(ee.getEmployeecode());
				delegete.setAssignedtoaccount(ee.getLoginaccount());
				delegete.setAuditresult(Enum_auditresult.通过.value.byteValue());
				// 获取单条记录信息
				TakeDepositRecord takeDepositRecord = takeDepositRecoredService.findOrderNumberTakeDepositRecord(order[0]);
				if(StringUtils.isNotBlank(takeDepositRecord.getFlowcode())
						&& !takeDepositRecord.getFlowcode().equals(order[1])){
					return this.packJSON(Constant.BooleanByte.NO, "审批流程不匹配,您无法审核该订单");
				}
				depositWithdralOrderDelegateService.tc_handleDeletegate(takeDepositRecord,delegete,ee);
				
				userLogsService.addActivityBetRecord(new UserLogs(takeDepositRecord.getEnterprisecode(), takeDepositRecord.getEmployeecode(), takeDepositRecord.getLoginaccount(), 
					     UserLogs.Enum_operatype.存取款业务, "存取款单据审批:"+takeDepositRecord.getOrdernumber(), ee.getLoginaccount(), null));
				
				String content = "您单号为"+takeDepositRecord.getOrdernumber()+"的"+(takeDepositRecord.getOrdertype().intValue() == 1 ? "存款":"取款")+"订单已审核通过！";
				sendMessage(session, content, takeDepositRecord.getEmployeecode(), takeDepositRecord.getLoginaccount());
				
				
				return this.packJSON(Constant.BooleanByte.YES, "您的审核已提交");
			}else{
				return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		}catch(LogicTransactionRollBackException e){
			return this.packJSON(Constant.BooleanByte.NO, e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}

	/**
	 * 存取款单拒绝方法
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/refusal")
	@ResponseBody
	@RequestInterval(millinsecond=3000)
	public Map<String, Object> refusal(Model model, HttpSession session, HttpServletRequest request) {
		try {
			// 获取当前登录用户的信息
			EnterpriseEmployee __ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			DepositWithdralOrderDelegate delegete = super.getRequestParamters(request, DepositWithdralOrderDelegate.class);
			if(super.decodeSign(delegete.getOrdernumber(), session.getId())){
				String[] order = delegete.getOrdernumber().split("_")[1].split("\\|");
				delegete.setAssignedtocode(__ee.getEmployeecode());
				delegete.setAssignedtoaccount(__ee.getLoginaccount());
				delegete.setAuditresult(Enum_auditresult.拒绝.value.byteValue());
				// 获取单条记录信息
				TakeDepositRecord takeDepositRecord = takeDepositRecoredService.findOrderNumberTakeDepositRecord(order[0]);
				if(StringUtils.isNotBlank(takeDepositRecord.getFlowcode())
						&& !takeDepositRecord.getFlowcode().equals(order[1])){
					return this.packJSON(Constant.BooleanByte.NO, "审批流程不匹配,您无法审核该订单");
				}
				depositWithdralOrderDelegateService.tc_handleDeletegate(takeDepositRecord,delegete,__ee);
				
				userLogsService.addActivityBetRecord(new UserLogs(takeDepositRecord.getEnterprisecode(), takeDepositRecord.getEmployeecode(), takeDepositRecord.getLoginaccount(), 
					     UserLogs.Enum_operatype.存取款业务, "存取款单审批拒绝:"+takeDepositRecord.getOrdernumber(), __ee.getLoginaccount(), null));
				
				String content = "您单号为"+takeDepositRecord.getOrdernumber()+"的"+(takeDepositRecord.getOrdertype().intValue() == 1 ? "存款":"取款")+"订单审核拒绝！审核结果："+delegete.getAuditdesc();
				sendMessage(session, content, takeDepositRecord.getEmployeecode(), takeDepositRecord.getLoginaccount());
				
				
				return this.packJSON(Constant.BooleanByte.YES, "您的审核已提交");
			}else{
				return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		}catch(LogicTransactionRollBackException e){
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	/**
	 * 跳转到出款失败页面
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/takeDepositFail")
	public String takeDepositFail(HttpServletRequest request, HttpSession session, Model model) {
		try {
			String ordernumber = request.getParameter("ordernumber");
			TakeDepositRecord takeDepositRecord = takeDepositRecoredService.findOrderNumberTakeDepositRecord(ordernumber);
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			if (StringUtils.isBlank(takeDepositRecord.getHandleemployee())) {
				TakeDepositRecord handEmployye = new TakeDepositRecord();
				handEmployye.setOrdernumber(takeDepositRecord.getOrdernumber());
				handEmployye.setHandleemployee(ee.getLoginaccount());
				takeDepositRecoredService.tc_updateTakeDepositRecord(handEmployye);
			}
			takeDepositRecord.setSign(super.encryptString(takeDepositRecord.getOrdernumber(), session.getId()));
			model.addAttribute("order", takeDepositRecord);

			return "/takedeposit/takedepositfail";
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.AJAX_ACTIONFAILD;
		}
	}

	/**
	 * 存取款单失败
	 * 用于出款失败后的处理
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/fail")
	@RequestInterval(millinsecond=3000)
	public Map<String, Object> fail(HttpServletRequest request, HttpSession session) {
		try {
			// 获取当前登录用户的信息
			EnterpriseEmployee __ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			DepositWithdralOrderDelegate delegete = super.getRequestParamters(request, DepositWithdralOrderDelegate.class);
			if (super.decodeSign(delegete.getOrdernumber(), session.getId())) {
				delegete.setOrdernumber(delegete.getOrdernumber().split("_")[1]);
				delegete.setAssignedtocode(__ee.getEmployeecode());
				delegete.setAssignedtoaccount(__ee.getLoginaccount());
				delegete.setAuditresult(Enum_auditresult.拒绝.value.byteValue());
				// 获取单条记录信息
				TakeDepositRecord takeDepositRecord = takeDepositRecoredService.findOrderNumberTakeDepositRecord(delegete.getOrdernumber());

				depositWithdralOrderDelegateService.tc_handleDeletegate(takeDepositRecord, delegete, __ee);

				userLogsService.addActivityBetRecord(new UserLogs(takeDepositRecord.getEnterprisecode(),
						takeDepositRecord.getEmployeecode(), takeDepositRecord.getLoginaccount(),
						UserLogs.Enum_operatype.存取款业务, "存取款单出款失败:" + takeDepositRecord.getOrdernumber(),
						__ee.getLoginaccount(), delegete.getAuditdesc()));

				String content = "您单号为" + takeDepositRecord.getOrdernumber() + "的"
						+ (takeDepositRecord.getOrdertype().intValue() == 1 ? "存款" : "取款") + "订单审核拒绝！审核结果："
						+ delegete.getAuditdesc();
				sendMessage(session, content, takeDepositRecord.getEmployeecode(), takeDepositRecord.getLoginaccount());

				return this.packJSON(Constant.BooleanByte.YES, "处理成功 !");
			} else {
				return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	/**
	 * 存取款单据驳回方法
	 * 
	 * @param session
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/dismiss")
	@ResponseBody
	@RequestInterval(millinsecond=3000)
	public Map<String, Object> dismiss(Model model, HttpSession session, HttpServletRequest request) {
		try {
			// 获取当前登录用户的信息
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			DepositWithdralOrderDelegate delegete = super.getRequestParamters(request, DepositWithdralOrderDelegate.class);
			if(super.decodeSign(delegete.getOrdernumber(), session.getId())){
				String[] order = delegete.getOrdernumber().split("_")[1].split("\\|");
				delegete.setAssignedtocode(ee.getEmployeecode());
				delegete.setAssignedtoaccount(ee.getLoginaccount());
				delegete.setAuditresult(Enum_auditresult.驳回.value.byteValue());
				// 获取单条记录信息
				TakeDepositRecord takeDepositRecord = takeDepositRecoredService.findOrderNumberTakeDepositRecord(order[0]);
				if(StringUtils.isNotBlank(takeDepositRecord.getFlowcode())
						&& !takeDepositRecord.getFlowcode().equals(order[1])){
					return this.packJSON(Constant.BooleanByte.NO, "审批流程不匹配,您无法审核该订单");
				}
				depositWithdralOrderDelegateService.tc_handleDeletegate(takeDepositRecord,delegete,ee);
				
				userLogsService.addActivityBetRecord(new UserLogs(takeDepositRecord.getEnterprisecode(), takeDepositRecord.getEmployeecode(), takeDepositRecord.getLoginaccount(), 
					     UserLogs.Enum_operatype.存取款业务, "存取款单审批驳回:"+takeDepositRecord.getOrdernumber(), ee.getLoginaccount(), null));
				
				String content = "您单号为"+takeDepositRecord.getOrdernumber()+"的"+(takeDepositRecord.getOrdertype().intValue() == 1 ? "存款":"取款")+"订单已驳回！审核结果："+delegete.getAuditdesc();
				sendMessage(session, content, takeDepositRecord.getEmployeecode(), takeDepositRecord.getLoginaccount());
				
				
				return this.packJSON(Constant.BooleanByte.YES, "您的审核已提交");
			}else{
				return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	/**
	 * 稽查
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/inspect")
	public String inspect(Model model, HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			
			String __ordernumber = request.getParameter("ordernumber");
//			if(super.decodeSign(__ordernumber, session.getId())){
//			if( true ){
//				__ordernumber = __ordernumber.split("_")[1].split("\\|")[0];
				
				/**旧的
				Map<String,Object> __object = new HashMap<String, Object>();
				__object.put("ordernumber", __ordernumber);
				Map<String, Object> __inspect = pandectService.usp_takemoney_inspect(__object);
				model.addAttribute("inspect", __inspect);
				return "/takedeposit/takeInspect";
				*/
				
				
				Map<String,Object> __object = new HashMap<String, Object>();
				__object.put("ordernumber", __ordernumber);
				List<TakeDepositRecord> list = takeDepositRecoredService.call_userTakemoneyInspectNew(__object);
				
				List<TakeDepositRecord> listGame = new ArrayList<TakeDepositRecord>();//GAME 最近游戏统计
				List<TakeDepositRecord> listActivity = new ArrayList<TakeDepositRecord>();//ACTIVITY 最近优惠领取统计
				List<TakeDepositRecord> listMoneyInout = new ArrayList<TakeDepositRecord>();//MONEY_INOUT 最近上分统计
				
				for (TakeDepositRecord takeDepositRecord : list) {
					if(takeDepositRecord.getPtype().equals("GAME")) {
						listGame.add(takeDepositRecord);
					} else if(takeDepositRecord.getPtype().equals("ACTIVITY")) {
						listActivity.add(takeDepositRecord);
					} else if(takeDepositRecord.getPtype().equals("MONEY_INOUT")) {
						listMoneyInout.add(takeDepositRecord);
					}
				}
				if(__object.get("need_stream_money") == null) {
					__object.put("need_stream_money","0");
				}
				if(__object.get("curren_money") == null) {
					__object.put("curren_money","0");
				}
				
				//实时查询当前所有平台的余额总数
				String brandcode = request.getParameter("brandcode");
				String employeecode = request.getParameter("employeecode");
				JSONObject jsonObject = JSONObject.fromObject( new APIServiceNew(loginEmployee.getEnterprisecode()).balances(employeecode, brandcode) );
				if(jsonObject.getString("code").equals("0")) {
					__object.put("curren_money",jsonObject.get("info"));
				}
				
				model.addAttribute("inspect", __object);
				model.addAttribute("listGame", listGame);
				model.addAttribute("listActivity", listActivity);
				model.addAttribute("listMoneyInout", listMoneyInout);
				System.out.println(__object);
				return "/takedeposit/takeInspectNew";
//			}else{
//				model.addAttribute("message", "因您不是当前处理人，无法进行稽核或审批处理");
//				return Constant.PAGE_IDENTITY_NO_MATCH;
//			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	

	/**
	 * 查询处理中的存款单审核记录
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/findDepositApproveRecord")
	@ResponseBody
	public Map<String, Object> findDepositApproveRecord(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			findParentEmoloyeeCode(paramObj);
			paramObj.put("ordertype", Enum_ordertype.存款.value);
			paramObj.put("orderstatus", Enum_orderstatus.审核中.value);
			super.dataLimits(loginEmployee, paramObj,session);
			
			// 查询分页数据
			List<TakeDepositRecord> list = takeDepositRecoredService.findTakeDepositRecord(paramObj);
			String __flowcode = String.valueOf(session.getAttribute(Constant.USER_WORKING_FLOW));
			if(StringUtils.isNotBlank(__flowcode)){
				/**/
				Calendar __time = Calendar.getInstance();
//				System.out.println("当前的时间："+__time.getTime().toLocaleString());
				__time.add(Calendar.MINUTE,  - 2);//2分钟后
				Date __dtime = __time.getTime();
//				System.out.println("减去后时间："+__dtime.toLocaleString());
				
				
				for (TakeDepositRecord __deposit : list) {
					if(__deposit.getPaymenttypecode().equals(Enum_PayType.第三方即时支付.value) &&__deposit.getOrderdate().after(__dtime)){
						
						__deposit.setHandleemployee("自动审核...");
						
						if(StringUtils.isBlank(__deposit.getFlowcode())
								||(StringUtils.isNotBlank(__flowcode)
										&&__flowcode.indexOf(__deposit.getFlowcode())>-1)){
							__deposit.setSign(super.encryptString(__deposit.getOrdernumber()+"|"+__deposit.getEnterprisepaymentbank(), session.getId()));
						}
					}else{
						if(StringUtils.isBlank(__deposit.getFlowcode())
								||(StringUtils.isNotBlank(__flowcode)
										&&__flowcode.indexOf(__deposit.getFlowcode())>-1)){
							__deposit.setSign(super.encryptString(__deposit.getOrdernumber()+"|"+__deposit.getEnterprisepaymentbank(), session.getId()));
						}
					}
				}
			}
			// 统计数据
			Map<String, Object> result = takeDepositRecoredService.findcountTakeDepositRecord(paramObj);
			int countRecord = StringUtil.getInt(result.get("count"));
			map.put("rows", list);
			map.put("results", countRecord);
			

//			Map<String, Object> data = super.formatPagaMap(list, countRecord);
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("orderamount", StringUtil.getDouble(result.get("orderamount")));
			map.put("summary", summary);
			
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return map;
	}
	
	
	/** 导出处理中的存款单审核记录
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/excelDepositApproveRecord")
	public String excelDepositApproveRecord(Model model, HttpSession session, HttpServletRequest request) {
		
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			findParentEmoloyeeCode(paramObj);
			paramObj.put("ordertype", Enum_ordertype.存款.value);
			paramObj.put("orderstatus", Enum_orderstatus.审核中.value);
			super.dataLimits(loginEmployee, paramObj,session);
			
			List<TakeDepositRecord> list = takeDepositRecoredService.findTakeDepositRecord(paramObj);
			Calendar __time = Calendar.getInstance();
			__time.set(Calendar.MINUTE, -5);
			Date __dtime = __time.getTime();
			for (TakeDepositRecord __deposit : list) {
				if(__deposit.getPaymenttypecode().equals(Enum_PayType.第三方即时支付.value) &&__deposit.getOrderdate().after(__dtime)){
					__deposit.setHandleemployee("自动审核...");
				}
			}
			model.addAttribute("listTakeDepositRecord", list);
			model.addAttribute("title", "处理中的存款单审核记录");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		
		return "/takedeposit/orderListExcel";
	}

	/**
	 * 查询处理中的取款单审核记录
	 * 
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/findTakeApproveRecord")
	@ResponseBody
	public Map<String, Object> findTakeApproveRecord(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			findParentEmoloyeeCode(paramObj);
			paramObj.put("ordertype", Enum_ordertype.取款.value);
			paramObj.put("orderstatus", Enum_orderstatus.审核中.value);
			super.dataLimits(loginEmployee, paramObj,session);
			// 查询分页数据
			List<TakeDepositRecord> list = takeDepositRecoredService.findTakeDepositRecord(paramObj);
			String __flowcode = String.valueOf(session.getAttribute(Constant.USER_WORKING_FLOW));
			if(StringUtils.isNotBlank(__flowcode)){
				for (TakeDepositRecord takeDepositRecord : list) {
					if(StringUtils.isBlank(takeDepositRecord.getFlowcode())
							||(StringUtils.isNotBlank(__flowcode)
									&&__flowcode.indexOf(takeDepositRecord.getFlowcode())>-1)){
						takeDepositRecord.setSign(super.encryptString(takeDepositRecord.getOrdernumber()+"|"+takeDepositRecord.getEnterprisepaymentbank(), session.getId()));
					}
				}
			}
			
			// 统计数据
			Map<String, Object> result = takeDepositRecoredService.findcountTakeDepositRecord(paramObj);
			int countRecord = StringUtil.getInt(result.get("count"));
			map.put("rows", list);
			map.put("results", countRecord);
			
			//Map<String, Object> data = super.formatPagaMap(list, countRecord);
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("orderamount", StringUtil.getDouble(result.get("orderamount")));
			map.put("summary", summary);
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return map;
	}

	
	/** 导出处理中的取款单审核记录
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/excelTakeApproveRecord")
	public String excelTakeApproveRecord(Model model, HttpSession session, HttpServletRequest request) {
		
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			findParentEmoloyeeCode(paramObj);
			paramObj.put("ordertype", Enum_ordertype.取款.value);
			paramObj.put("orderstatus", Enum_orderstatus.审核中.value);
			super.dataLimits(loginEmployee, paramObj,session);
			
			List<TakeDepositRecord> list = takeDepositRecoredService.findTakeDepositRecord(paramObj);
			model.addAttribute("listTakeDepositRecord", list);
			model.addAttribute("title", "处理中的取款单审核记录");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		
		return "/takedeposit/orderListExcel";
	}
	
	public static void main(String[] args) {
		System.out.println(MD5Encoder.encode("123456"));;
	}
	/**
	 * 统计当前登录用户下面所有待处理的存款单与取款单数量
	 * 
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/countRecord")
	@ResponseBody
	public Map<String, Object> countRecord(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			paramObj.put("ordertype", Enum_ordertype.取款.value);
			paramObj.put("orderstatus", Enum_orderstatus.审核中.value);
			super.dataLimits(loginEmployee, paramObj,session);
			// 所有取款单据数量
			Map<String, Object> result = takeDepositRecoredService.findcountTakeDepositRecord(paramObj);
			int allTakeCount = StringUtil.getInt(result.get("count"));
			map.put("allTakeCount", allTakeCount);
			// 所有存款单据数量
			paramObj.put("ordertype", Enum_ordertype.存款.value);
			result = takeDepositRecoredService.findcountTakeDepositRecord(paramObj);
			int allDepositCount = StringUtil.getInt(result.get("count"));
			map.put("allDepositCount", allDepositCount);
			return map;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return map;
	}

	/**
	 * @see 查询审核通过的存取款所有明细记录
	 * @param session
	 * @param request
	 * @return Map<String,Object>
	 */
	@RequestMapping("/queryDepositTakeRecodeDatail")
	@ResponseBody
	public Map<String, Object> queryDepositTakeRecodeDatail(HttpSession session, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			paramObj.put("orderstatus", Enum_orderstatus.审核通过.value);
			super.dataLimits(loginEmployee, paramObj,session);
			// 查询分页数据
			List<TakeDepositRecord> list = takeDepositRecoredService.findTakeDepositRecord(paramObj);
			// 统计数据
			Map<String, Object> result = takeDepositRecoredService.findcountTakeDepositRecord(paramObj);
			int countRecord = StringUtil.getInt(result.get("count"));
			map.put("rows", list);
			map.put("results", countRecord);
			return map;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return map;
	}

	/**
	 * 编辑存款订单 
	 * @param request
	 * @return URL
	 */
	@RequestMapping("/editDepositOrders")
	@OperationLog(OparetionDescription.DEPOSIT_UPDATE)
	public String editDepositOrders(HttpSession session, HttpServletRequest request, Model model) {
		String orderNumber = request.getParameter("orderNumber");
		try {
			if (this.decodeSign(orderNumber, session.getId())) {
				TakeDepositRecord takeDepositRecord = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderNumber.split("_")[1]);
				takeDepositRecord.setSign(super.encryptString(takeDepositRecord.getOrdernumber(), session.getId()));
				EnterpriseInformation ei = new EnterpriseInformation();
				ei.setEnterprisecode(takeDepositRecord.getEnterprisecode());
				List<EnterpriseInformation> _enterprise_banks = enterpriseInformationService.select(ei);
				List<PaymentType> _paymentType = paymentTypeService.getAllPaymentType();
				List<Bank> _banks = SystemCache.getInstance().getBanks();
				model.addAttribute("enterprisebanks", _enterprise_banks);
				model.addAttribute("paytypes", _paymentType);
				model.addAttribute("banks", _banks);
				model.addAttribute("order", takeDepositRecord);
				if(takeDepositRecord.getOrdertype()==Enum_ordertype.存款.value){
					return "/takedeposit/depositOrderUpdate";
				}else{
					return Constant.PAGE_PARAMSERROR;
				}
			}else{
				return Constant.PAGE_DECODEREFUSE;
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	/**
	 * 编辑取款订单 
	 * @param request
	 * @return URL
	 */
	@RequestMapping("/editTakeOrders")
	@OperationLog(OparetionDescription.TAKE_UPDATE)
	public String editTakeOrders(HttpSession session, HttpServletRequest request, Model model) {
		String orderNumber = request.getParameter("orderNumber");
		try {
			if (this.decodeSign(orderNumber, session.getId())) {
				TakeDepositRecord takeDepositRecord = takeDepositRecoredService.findOrderNumberTakeDepositRecord(orderNumber.split("_")[1]);
				takeDepositRecord.setSign(super.encryptString(takeDepositRecord.getOrdernumber(), session.getId()));
				takeDepositRecord.setOrderamount(takeDepositRecord.getOrderamount().negate());
				if(takeDepositRecord.getOrdertype()==Enum_ordertype.取款.value){
					EnterpriseEmployeeInformation eei = new EnterpriseEmployeeInformation();
					eei.setEmployeecode(takeDepositRecord.getEmployeecode());
					eei.setStatus(EnterpriseEmployeeInformation.Enum_status.锁定.value);
					List<EnterpriseEmployeeInformation> eeibanks = enterpriseEmployeeInformationService.select(eei);
					super.encryptSign(eeibanks, session.getId(), "informationcode");
					model.addAttribute("banks", eeibanks);
					model.addAttribute("order", takeDepositRecord);
					return "/takedeposit/takeOrderUpdate";
				}else{
					return Constant.PAGE_PARAMSERROR;
				}
			}else{
				return Constant.PAGE_DECODEREFUSE;
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	
	/**
	 * 修改存款订单
	 * 
	 * @param request
	 * @return URL
	 */
	@RequestMapping("/updateDepositOrders")
	@OperationLog(OparetionDescription.DEPOSIT_UPDATE)
	@ResponseBody
	public Map<String, Object> updateDepositOrders(@ModelAttribute TakeDepositRecord takeDepositRecord,
			HttpSession session, HttpServletRequest request) {
		try {
			if(super.decodeSign(takeDepositRecord.getOrdernumber(), session.getId())){
				EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
				// 查询首款卡是否存在
				EnterpriseInformation enterpriseInformation = enterpriseInformationService
						.selectByPrimaryKey(takeDepositRecord.getEnterprisepaymentaccount());
				if (enterpriseInformation == null) {
					return super.packJSON(Constant.BooleanByte.NO, "录单失败，收款账号不存在");
				}
				if(takeDepositRecord.getOrderamount()==null||takeDepositRecord.getOrderamount().doubleValue()<0){
					return super.packJSON(Constant.BooleanByte.NO, "录单失败，存款金额不能少于0");
				}
				String  ordernumber = takeDepositRecord.getOrdernumber().split("_")[1];
				TakeDepositRecord depositOrder = takeDepositRecoredService.findOrderNumberTakeDepositRecord(ordernumber);
				// 企业收款人姓名
				depositOrder.setEnterprisepaymentname(enterpriseInformation.getAccountname());
				// 企业收款账号
				depositOrder.setEnterprisepaymentaccount(enterpriseInformation.getOpenningaccount());
				// 企业收付款银行编码
				depositOrder.setEnterprisepaymentbank(enterpriseInformation.getBankcode());
				// 存款人姓名
				takeDepositRecord.setEmployeepaymentname(takeDepositRecord.getEmployeepaymentname());
				// 存款银行
				takeDepositRecord.setEmployeepaymentbank(takeDepositRecord.getEmployeepaymentbank());
				// 存款人银行卡
				depositOrder.setEmployeepaymentaccount(takeDepositRecord.getEmployeepaymentaccount());
				// 取款金额
				depositOrder.setOrderamount(takeDepositRecord.getOrderamount());
				// 支付类型
				depositOrder.setPaymenttypecode(takeDepositRecord.getPaymenttypecode());
				// 订单状态
				depositOrder.setOrderstatus((byte) Enum_orderstatus.审核中.value);
				// 订单备注
				depositOrder.setOrdercomment(takeDepositRecord.getOrdercomment());
				// 订单时间
				depositOrder.setOrderdate(new Date());
				// 设置订单创建人
				depositOrder.setOrdercreater(ee.getLoginaccount());
				// 订单创建客户端IP
				depositOrder.setTraceip(InetAddress.getLocalHost().getHostAddress());
				
				// 保存存取款订单
				takeDepositRecoredService.tc_update_saveorder(depositOrder);
				
				userLogsService.addActivityBetRecord(new UserLogs(takeDepositRecord.getEnterprisecode(), takeDepositRecord.getEmployeecode(), takeDepositRecord.getLoginaccount(), 
					     UserLogs.Enum_operatype.存取款业务, "员工修改存款订单信息:"+takeDepositRecord.getOrdernumber(), ee.getLoginaccount(), null));

				String content = "您单号为"+takeDepositRecord.getOrdernumber()+"的存款订单信息已被客服修改";
				sendMessage(session, content, takeDepositRecord.getEmployeecode(), takeDepositRecord.getLoginaccount());
				
				return super.packJSON(Constant.BooleanByte.YES, "修改成功");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "订单修改失败");
		}
	}

	/**
	 * 修改取款订单
	 * 
	 * @param request
	 * @return URL
	 */
	@RequestMapping("/updateTakeOrders")
	@OperationLog(OparetionDescription.TAKE_UPDATE)
	@ResponseBody
	public Map<String, Object> updateTakeOrders(@ModelAttribute TakeDepositRecord takeDepositRecord,
			HttpSession session, HttpServletRequest request) {
		try {
			// 获取当前登录用户对象
			if(super.decodeSign(takeDepositRecord.getEmployeepaymentaccount(), session.getId())
					&&super.decodeSign(takeDepositRecord.getOrdernumber(), session.getId())){
				EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
				String informationcode = takeDepositRecord.getEmployeepaymentaccount().split("_")[1];
				// 根据企业银行卡查询单条信息
				EnterpriseEmployeeInformation employeeInformation = enterpriseEmployeeInformationService.findOneBankInfo(informationcode);
				if (employeeInformation == null) {
					return super.packJSON(Constant.BooleanByte.NO, "录单失败，取款银行卡不存在");
				}
				if(takeDepositRecord.getOrderamount()==null||takeDepositRecord.getOrderamount().doubleValue()<0){
					return super.packJSON(Constant.BooleanByte.NO, "录单失败，取款金额不能少于0");
				}
				String  ordernumber = takeDepositRecord.getOrdernumber().split("_")[1];
				TakeDepositRecord takeOrder = takeDepositRecoredService.findOrderNumberTakeDepositRecord(ordernumber);
				// 取款人姓名
				takeOrder.setEmployeepaymentname(employeeInformation.getAccountname());
				// 取款银行
				takeOrder.setEmployeepaymentbank(employeeInformation.getBankcode());
				// 取款人银行卡
				takeOrder.setEmployeepaymentaccount(employeeInformation.getPaymentaccount());
				// 取款金额
				takeOrder.setOrderamount(takeDepositRecord.getOrderamount().negate());
				// 订单备注
				takeOrder.setOrdercomment(takeDepositRecord.getOrdercomment());
				// 订单状态
				takeOrder.setOrderstatus((byte) Enum_orderstatus.审核中.value);
				// 订单时间
				takeOrder.setOrderdate(new Date());
				// 设置订单创建人
				takeOrder.setOrdercreater(ee.getLoginaccount());
				// 订单创建客户端IP
				takeOrder.setTraceip(InetAddress.getLocalHost().getHostAddress());
				// 保存存取款订单
				takeDepositRecoredService.tc_update_takeorder(takeOrder);
				
				userLogsService.addActivityBetRecord(new UserLogs(takeDepositRecord.getEnterprisecode(), takeDepositRecord.getEmployeecode(), takeDepositRecord.getLoginaccount(), 
					     UserLogs.Enum_operatype.存取款业务, "员工修改取款订单信息:"+takeDepositRecord.getOrdernumber(), ee.getLoginaccount(), null));
				
				String content = "您单号为"+takeDepositRecord.getOrdernumber()+"的取款订单信息已被客服修改";
				sendMessage(session, content, takeDepositRecord.getEmployeecode(), takeDepositRecord.getLoginaccount());
				
				
				return super.packJSON(Constant.BooleanByte.YES, "录单成功");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "录单失败，请稍后尝试");
		}
	}

	/**
	 * 查询被驳回的所有订单
	 * @param request
	 * @param session
	 * @return Map<String,Object>
	 */
	@RequestMapping("/findRejectData")
	@ResponseBody
	public Map<String, Object> findRejectData(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> paramObj = getRequestParamters(request);
			findParentEmoloyeeCode(paramObj);
			super.dataLimits(loginEmployee, paramObj,session);
			paramObj.put("orderstatus", TakeDepositRecord.Enum_orderstatus.驳回.value);
			List<TakeDepositRecord> list = takeDepositRecoredService.findTakeDepositRecord(paramObj);
			Map<String,String> encripyTarget = new HashMap<String, String>();
			encripyTarget.put("ordernumber", "sign");
			this.encryptSignTarget(list, session.getId(), encripyTarget);
			
			Map<String, Object> result = takeDepositRecoredService.findcountTakeDepositRecord(paramObj);
			int countRecord = StringUtil.getInt(result.get("count"));
			
			//return super.formatPagaMap(list, countRecord);
			Map<String, Object> data = super.formatPagaMap(list, countRecord);
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("orderamount", StringUtil.getDouble(result.get("orderamount")));
			data.put("summary", summary);
			
			return data;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	/* 代理金额操作相关 start */
	
	/**
	 * 跳转代理取款页面
	 * 
	 * @return
	 */
	@RequestMapping("/agentTakeAdd")
	public String agentTakeAdd(HttpSession session, Model model) {
		EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
		model.addAttribute("employeeAgent", ee);
		//return super.validateIsEmployee(session, model) ? "/takedeposit/agentTakeAdd" : Constant.PAGE_IDENTITY_NO_MATCH;
		return "/takedeposit/agentTakeAdd";
	}
	
	/**
	 * 代理取款订单
	 * 
	 * @return
	 */
	@RequestMapping("/agentWithdrawOrders")
	@ResponseBody
	@RequestInterval(millinsecond=3000)
	public Map<String, Object> agentWithdrawOrders(HttpServletRequest request,HttpSession session) {
		try {
			// 获取当前登录用户对象
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			if(ee==null){
				return super.packJSON(Constant.BooleanByte.NO, "提款失败，回话过期");
			}
			TakeDepositRecord takeDepositRecord = super.getRequestParamters(request, TakeDepositRecord.class);
			if(super.decodeSign(takeDepositRecord.getEmployeepaymentaccount(), session.getId())){
				String informationcode = takeDepositRecord.getEmployeepaymentaccount().split("_")[1];
				// 根据企业银行卡查询单条信息
				EnterpriseEmployeeInformation employeeInformation = enterpriseEmployeeInformationService.findOneBankInfo(informationcode);
				if (employeeInformation == null) {
					return super.packJSON(Constant.BooleanByte.NO, "录单失败，取款银行卡不存在");
				}
				/*EnterpriseEmployee depositeUsers =  enterpriseEmployeeService.takeEmployeeByCode(takeDepositRecord.getEmployeecode());
				if(depositeUsers==null){
					return super.packJSON(Constant.BooleanByte.NO, "录单失败，取款用户不存在");
				}*/
				EnterpriseEmployeeLevel employeeLevel = businessEmployeeLovelServiceImpl.getOneObject(ee.getEmployeelevelcode());
				if(employeeLevel!=null){
					TakeDepositRecord record = takeDepositRecoredService.takeCountAndTakeTotalAmount(ee.getEmployeecode());
					if(record.getQuantity() > employeeLevel.getTakeTimeOfDay()){
						return super.packJSON(Constant.BooleanByte.NO, "您每天的提款次数为"+employeeLevel.getTakeTimeOfDay()+"笔,今天的提款次数已达上限");
					}
					if(takeDepositRecord.getOrderamount().compareTo(employeeLevel.getTakeMoneyOfDay()) == 1){
						return super.packJSON(Constant.BooleanByte.NO, "您每天的提款上限为"+employeeLevel.getTakeMoneyOfDay()+"元,今日提款已达上限");
					}
					if(null != record.getAllTakeMoney()){
						if(BigDecimal.valueOf(record.getAllTakeMoney()).compareTo(employeeLevel.getTakeMoneyOfDay()) == 1){
							return super.packJSON(Constant.BooleanByte.NO, "您每天的提款上限为"+employeeLevel.getTakeMoneyOfDay()+"元,今日提款已达上限");
						}
					}
				}
				
				if(takeDepositRecord.getOrderamount()==null||takeDepositRecord.getOrderamount().doubleValue()<0){
					return super.packJSON(Constant.BooleanByte.NO, "录单失败，取款金额不能少于0");
				}
				
				// 32位订单号
//				takeDepositRecord.setOrdernumber(RandomString.UUID());//jason20161115更新单号规则
				takeDepositRecord.setOrdernumber(OrderNewUtil.getOrdernoTake());//jason20161115更新单号规则，因为要将该批次号与下分、账变记录同步
				// 存款用户企业编码
				takeDepositRecord.setEnterprisecode(ee.getEnterprisecode());
				// 存款用户品牌编码
				takeDepositRecord.setBrandcode(ee.getBrandcode());
				// 用户上级编码
				takeDepositRecord.setParentemployeecode(ee.getParentemployeecode());
				// 订单创建时间
				takeDepositRecord.setOrderdate(new Date());
				// 取款人姓名
				takeDepositRecord.setEmployeepaymentname(employeeInformation.getAccountname());
				// 取款银行
				takeDepositRecord.setEmployeepaymentbank(employeeInformation.getBankcode());
				// 取款人银行卡
				takeDepositRecord.setEmployeepaymentaccount(employeeInformation.getPaymentaccount());
				// 取款金额
				takeDepositRecord.setOrderamount(takeDepositRecord.getOrderamount().negate());
				// 订单类型
				takeDepositRecord.setOrdertype((byte) Enum_ordertype.取款.value);
				// 订单状态
				takeDepositRecord.setOrderstatus((byte) Enum_orderstatus.审核中.value);
				// 设置订单创建人
				takeDepositRecord.setOrdercreater(ee.getLoginaccount());
				// 订单创建客户端IP
				takeDepositRecord.setTraceip(InetAddress.getLocalHost().getHostAddress());
				// 保存存取款订单
				takeDepositRecoredService.tc_take_money(takeDepositRecord);
				
				userLogsService.addActivityBetRecord(new UserLogs(takeDepositRecord.getEnterprisecode(), takeDepositRecord.getEmployeecode(), takeDepositRecord.getLoginaccount(), 
					     UserLogs.Enum_operatype.存取款业务, "代理提交取款订单:"+takeDepositRecord.getOrdernumber(), ee.getLoginaccount(), null));
				
				
				return super.packJSON(Constant.BooleanByte.YES, "录单成功");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		}catch(GameAPIRequestException e){
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "下分失败，请稍后尝试");
		}catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "下分失败，请稍后尝试");
		}catch(LogicTransactionException e){
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "下分失败，请稍后尝试");
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "录单失败，请稍后尝试");
		}
	}
	/**
	 * 查找上级
	 * @param paramObj
	 */
	private void findParentEmoloyeeCode(Map<String, Object> paramObj){
		try {
			if (paramObj.get("parentName") != null) {
				String parentemployeeaccount = paramObj.get("parentName").toString();
				EnterpriseEmployee parent = new EnterpriseEmployee();
				parent.setLoginaccount(parentemployeeaccount);
				List<EnterpriseEmployee> list = employeeService.select(parent);
				if (null != list && list.size() > 0) {
					paramObj.put("parentemployeecode", list.get(0).getEmployeecode());
				} else {
					// 如果查不到上级账户code，就直接传名字进去，查不出数据，表示这个账号下没有会员
					paramObj.put("parentemployeecode", parentemployeeaccount);
				}
				paramObj.remove("parentName");
			}
		} catch (Exception e) {
			log.Error("查找上级报错", e);
		}
	}
	/* 代理金额操作相关end */
	/*@Autowired
	private ActivityRegBonusService activityRegBonusService;
	@Autowired
	private ActivityDepositBonusService activityDepositBonusService;
	@Autowired
	private ActivityLoseBonusService activityLoseBonusService;
	@RequestMapping("/testActivity")
	public String testActivity(HttpServletRequest request, HttpSession session){
		ActivityUniqueinfoCheck regbonuscheck = new ActivityUtils().new ActivityUniqueinfoCheck();
		try {
			regbonuscheck.setLoginaccount("Ethan");
			regbonuscheck.setHousenumber("anfjsj");
			regbonuscheck.setHouseaddress("风科技人企业111");
			regbonuscheck.setEmail("fabch@163.com");
			regbonuscheck.setPhonenumber("13533221547");
			regbonuscheck.setPayment("66225544778855865411");
			regbonuscheck.setIp("103.27.230.229");
			//activityRegBonusService.tc_getRegBonus("Ethan", 4, "103.27.230.229");
			//activityDepositBonusService.tc_depositBonus("vinson", 5);
			activityLoseBonusService.tc_monthLoseBonus("Ethan", 3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}*/
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
	
}
