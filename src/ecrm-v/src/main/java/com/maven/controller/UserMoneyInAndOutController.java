package com.maven.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.maven.annotation.OperationLog;
import com.maven.annotation.RequestInterval;
import com.maven.cache.SystemCache;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.entity.ActivityBetRecord.Enum_ecactivitycode;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseActivityCustomization;
import com.maven.entity.EnterpriseActivityPay;
import com.maven.entity.EnterpriseBrandActivity;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.Game;
import com.maven.entity.UserLogs;
import com.maven.entity.UserMoneyInAndOut;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.EmployeeApiAccoutService;
import com.maven.service.EnterpriseActivityCustomizationService;
import com.maven.service.EnterpriseActivityPayService;
import com.maven.service.EnterpriseBrandActivityService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseService;
import com.maven.service.UserLogsService;
import com.maven.service.UserMoneyInAndOutService;
import com.maven.util.GameUtils;
import com.maven.util.RandomString;
import com.maven.utils.ExcelRead;
import com.maven.utils.MoneyHelper;
import com.maven.utils.StringUtil;

@Controller
@RequestMapping("/moneyInAndOut")
public class UserMoneyInAndOutController extends BaseController {

	private static LoggerManager log = LoggerManager.getLogger(UserMoneyInAndOutController.class.getName(),
			OutputManager.LOG_MONEYINANDOUT);

	// 上下分
	@Autowired
	private UserMoneyInAndOutService ueserMoneyInAndOutService;

	@Autowired
	private EnterpriseEmployeeCapitalAccountService enterpriseEmployeeCapitalAccountService;

	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;

	@Autowired
	private EnterpriseActivityCustomizationService enterpriseActivityCustomizationService;
	@Autowired
	private EnterpriseBrandActivityService enterpriseBrandActivityService;
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private EnterpriseActivityPayService enterpriseActivityPayService;
	@Autowired
	private UserLogsService userLogsService;
	@Autowired
	private EmployeeApiAccoutService apiAccoutService;
	@Autowired
	private ActivityBetRecordService activityBetRecordService;

	private static List<Enterprise> listEnterprise;

	@RequestMapping("/index")
	@SuppressWarnings("unchecked")
	public String index(HttpServletRequest request, HttpSession session, Model model) {
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> params = getRequestParamters(request);
			if (listEnterprise == null || listEnterprise.size() == 0) {
				listEnterprise = enterpriseService.selectAll(params);
			}
			/********* 非超级管理员时只能查询本团队内的数据 ************/

			if (!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())) {
				List<Enterprise> listEnterpriseTemp = new ArrayList<Enterprise>();
				for (Enterprise enterprise : listEnterprise) {
					if (enterprise.getEnterprisecode().equals(ee.getEnterprisecode())) {
						listEnterpriseTemp.add(enterprise);
					}
				}

				model.addAttribute("listEnterprise", listEnterpriseTemp);
			} else {
				model.addAttribute("listEnterprise", listEnterprise);
			}
			// 获取企业游戏列表
			List<Game> listGame = (List<Game>) session.getAttribute(Constant.ENTERPRISE_GAMES);
			model.addAttribute("listGame", listGame);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/userinfo/moneyInAndOutRecord";

	}

	// 冲正冲负
	@RequestMapping("/plusLessMinute")
	public String plusLessMinute(Model model) {
		model.addAttribute("moneyaddtypes", EmployeeMoneyChangeType.Enum_moneyaddtype.values());
		return "/userinfo/plusLessMinute";
	}

	/**
	 * 推广活动充值页面
	 * 
	 * @return
	 */
	@RequestMapping("/activityRecharge")
	@SuppressWarnings("unchecked")
	public String activityRechargeIndex(HttpServletRequest request, HttpSession session, Model model) {
		try {
			// 查询企业所有活动,为所有记录的活动ID转换为活动名称
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			// List<EnterpriseActivityCustomization>
			// listEnterpriseActivityCustomization =
			// enterpriseActivityCustomizationService.selectAllByEnterprisecode(ee.getEnterprisecode());
			// model.addAttribute("listEnterpriseActivityCustomization",
			// listEnterpriseActivityCustomization);

			Map<String, Object> queryParams = new HashMap<String, Object>();
			queryParams.put("enterprisecode", ee.getEnterprisecode());
			queryParams.put("status", EnterpriseBrandActivity.Enum_status.启用.value);
			List<EnterpriseBrandActivity> listEnterpriseBrandActivity = enterpriseBrandActivityService
					.selectAll(queryParams);
			model.addAttribute("listEnterpriseBrandActivity", listEnterpriseBrandActivity);
			

			// 获取企业游戏列表
			List<Game> listGame = (List<Game>) session.getAttribute(Constant.ENTERPRISE_GAMES);

			model.addAttribute("listGame", listGame);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/userinfo/activityRechargeIndex";
	}

	/**
	 * 提交推广活动充值数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveActivityRecharge")
	@OperationLog(OparetionDescription.ACTIVITY_RECHARGE)
	@ResponseBody
	@RequestInterval(millinsecond = 9000)
	public Map<String, Object> saveActivityRecharge(HttpServletRequest request, HttpSession session) {
		try {
			EnterpriseEmployee eex = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			
			
			
			/**************新逻辑，要经审核.增加活动红利支付审核记录******************/
			EnterpriseActivityCustomization enterpriseBrandActivity = enterpriseActivityCustomizationService.selectByPrimaryKey(request.getParameter("activityRechargeType"));
			
			BigDecimal money = BigDecimal.valueOf(Double.valueOf(request.getParameter("money")));//金额
			BigDecimal depositMoney = BigDecimal.valueOf(Double.valueOf(request.getParameter("depositMoney")));//金额
			
			String employeeCode = request.getParameter("employeecode");//需要操作的用户编码

			EnterpriseEmployee ee = enterpriseEmployeeService.takeEmployeeByCode(employeeCode);
			
			if( !ee.getEnterprisecode().equals(eex.getEnterprisecode())) {
				return super.packJSON(Constant.BooleanByte.NO, "操作失败：没有权限对该会员操作");
			}

			Date now = new Date();
			EnterpriseActivityPay activityPay = new EnterpriseActivityPay();
			activityPay.setBrandcode(ee.getBrandcode());
			activityPay.setEmployeecode(ee.getEmployeecode());
			activityPay.setEnterprisecode(ee.getEnterprisecode());
			activityPay.setLoginaccount(ee.getLoginaccount());
			activityPay.setParentemployeecode(ee.getParentemployeecode());
			activityPay.setAcname(enterpriseBrandActivity.getActivityname());
			activityPay.setEcactivitycode(enterpriseBrandActivity.getEcactivitycode());
			activityPay.setCreatetime(now);
			activityPay.setPaymoneyaudit(money.doubleValue());
			activityPay.setPaymoneyreal(money.doubleValue());
			//activityPay.setAuditer("");
			//activityPay.setAuditremark("活动：注册送彩金，自动发放");
			//activityPay.setAudittime(now);
			//activityPay.setPayer("");
			//activityPay.setPaytyime(now);
			activityPay.setPaystatus(EnterpriseActivityPay.Enum_paystatus.待审核.value);
			activityPay.setPaytype(EnterpriseActivityPay.Enum_paytype.人工发放.value);
			activityPay.setDesc(request.getParameter("desc"));
			activityPay.setLsbs(request.getParameter("lsbs"));
			activityPay.setDepositmoney(depositMoney.doubleValue());
			enterpriseActivityPayService.addActivityBetRecord(activityPay);

			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(),
					ee.getLoginaccount(), UserLogs.Enum_operatype.活动充值业务, "员工提交活动充值:" + money.doubleValue(),
					eex.getLoginaccount(), request.getParameter("desc")));

			// 禁用游戏账户
			String[] array = request.getParameterValues("disableSelectEmployee");
			Map<String, String> selectData = new HashMap<String, String>();
			if (array != null && array.length > 0) {

				for (int i = 0; i < array.length; i++) {
					selectData.put(array[i], null);
				}
				List<EmployeeApiAccout> listEmployeeApiAccout = apiAccoutService
						.getAllEmployeeApiAccout(ee.getEmployeecode());
				for (EmployeeApiAccout employeeApiAccout : listEmployeeApiAccout) {
					if (selectData.containsKey(employeeApiAccout.getGametype())
							&& employeeApiAccout.getStatus().equals(EmployeeApiAccout.Enum_status.启用.value)) {
						employeeApiAccout.setStatus(EmployeeApiAccout.Enum_status.禁用.value);
						apiAccoutService.updatePassword(employeeApiAccout);

						// 更新缓存
						Map<String, EmployeeApiAccout> eapiaccounts = SystemCache.getInstance()
								.getEmployeeAllGameAccount(employeeApiAccout.getEmployeecode());
						eapiaccounts.put(employeeApiAccout.getGametype(), employeeApiAccout);
						SystemCache.getInstance().setEmployeeAllGameAccount(employeeApiAccout.getEmployeecode(),
								eapiaccounts);

						userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(),
								ee.getLoginaccount(), UserLogs.Enum_operatype.游戏信息业务, "员工提交活动充值并自动禁用游戏账户",
								eex.getLoginaccount(), employeeApiAccout.getGametype()));
					}
				}

			}

			return super.packJSON(Constant.BooleanByte.YES, "操作成功，请通知风控审核及发放！！！");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		return super.packJSON(Constant.BooleanByte.NO, "操作失败");
	}

	/**
	 * 推广活动批量充值页面
	 * 
	 * @return
	 */
	@RequestMapping("/activityBatchRecharge")
	public String activityBatchRecharge() {
		return "/userinfo/activityBatchRecharge";
	}

	/**
	 * Excel上传并导入
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/uploadRecord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadRecord(MultipartHttpServletRequest request, HttpSession session) {
		EnterpriseEmployee eex = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
		Map<String, MultipartFile> fileMap = request.getFileMap();
		Map<String, Object> map = new HashMap<String, Object>();
		if (null != fileMap && fileMap.size() == 1) {
			try {
				List<EnterpriseActivityPay> eapList = new ArrayList<EnterpriseActivityPay>();
				List<UserLogs> logList = new ArrayList<UserLogs>();
				ExcelRead er = new ExcelRead(enterpriseActivityCustomizationService, enterpriseEmployeeService, eex);
				for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
					MultipartFile Mfile = entity.getValue();
					er.getExcelInfo(Mfile);
				}
				Map<String, Object> result = er.getResult();
				eapList = (List<EnterpriseActivityPay>) result.get("eapList");
				logList = (List<UserLogs>) result.get("logList");
				if (null != eapList && eapList.size() > 0) {
					int rowCount = er.getTotalRows() - 1;// 去掉标题行
					String errMsg = er.getErrorInfo();
					int reusltNum = enterpriseActivityPayService.batchAddActivityBetRecord(eapList);
					userLogsService.batchAddActivityBetRecord(logList);
					map.put("msg", "上传" + rowCount + "条数据，成功导入" + reusltNum + "条数据<br/>" + errMsg);
					map.put("status", true);
				} else {
					String errMsg = er.getErrorInfo();
					map.put("status", false);
					map.put("msg", "数据导入失败：" + errMsg);
				}
				return map;
			} catch (Exception e) {
				log.Error(e.getMessage(), e);
				e.printStackTrace();
			}
		} else {
			map.put("msg", "上传文件数量有误!");
		}
		map.put("status", false);
		return map;
	}

	/**
	 * 查询上下分记录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/findMoneyInAndOut")
	@ResponseBody
	public Map<String, Object> findMoneyInAndOut(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);

			/********* 非超级管理员时只能查询本团队内的数据 ************/
			if (!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())) {
				// 非管理员不能查询所有企业条件
				if (object.get("enterprisecode") == null || object.get("enterprisecode").toString().equals("")) {
					// return super.packJSON(Constant.BooleanByte.NO,
					// "你不能查询所有企业");
					object.put("enterprisecode", ee.getEnterprisecode());
				}

				super.dataLimits(ee, object, session);
			}

			List<UserMoneyInAndOut> list = ueserMoneyInAndOutService.findMoneyInAndOut(object);
			if (null != list && list.size() > 0) {
				String gameType = null;
				for (int i = 0; i < list.size(); i++) {
					gameType = list.get(i).getGametype();
					list.get(i).setGametype(GameUtils.getGameTypeNameByGameType(gameType));
					gameType = null;
				}
			}
			/********* 非超级管理员时只能查询本团队内的数据 ************/
			if (SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())) {
				for (UserMoneyInAndOut userMoneyInAndOut : list) {
					userMoneyInAndOut.setEmployeename(
							userMoneyInAndOut.getEnterprisecode() + "-" + userMoneyInAndOut.getEmployeecode());
				}
			}

			Map<String, Object> result = ueserMoneyInAndOutService.findMoneyInAndOutCount(object);
			int count = StringUtil.getInt(result.get("count"));
			super.encryptSign(list, session.getId(), "moneyinoutcode");
			// return super.formatPagaMap(list, count);

			Map<String, Object> data = super.formatPagaMap(list, count);
			Map<String, String> summary = new HashMap<String, String>();
			summary.put("beforebalance", MoneyHelper.doubleFormat(result.get("beforebalance")));// 上下分前
			summary.put("moneyinoutamount", MoneyHelper.doubleFormat(result.get("moneyinoutamount")));// 上下分金额
			summary.put("afterbalance", MoneyHelper.doubleFormat(result.get("afterbalance")));// 上下分后
			data.put("summary", summary);

			return data;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
			return super.formatPagaMap(null, 0);
		}
	}

	/**
	 * 提交冲正冲负数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/savePlusLessMinute")
	@OperationLog(OparetionDescription.PLUS_LESS)
	@ResponseBody
	@RequestInterval(millinsecond = 9000)
	public Map<String, Object> savePlusLessMinute(HttpServletRequest request, HttpSession session) {
		try {
			EmployeeMoneyChangeType type = new EmployeeMoneyChangeType();
			// 获取当前登录用户对象
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			// 需要冲值的用户编码
			String employee = request.getParameter("employeecode");
			String loginaccount = request.getParameter("loginaccount");
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
			String moneyaddtypeName = request.getParameter("moneyaddtypeName");
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
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		return super.packJSON(Constant.BooleanByte.NO, "操作失败");
	}

	/**
	 * 返还上分失败金额
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/backMoney")
	@ResponseBody
	public Map<String, Object> backMoney(HttpServletRequest request, HttpSession session) {
		try {
			String moneyinoutcode = request.getParameter("moneyinoutcode");
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);

			if (super.decodeSign(moneyinoutcode, session.getId())) {
				moneyinoutcode = moneyinoutcode.split("_")[1];
				ueserMoneyInAndOutService.tc_back_losemoney(moneyinoutcode, "操作人:" + ee.getLoginaccount());
				return super.packJSON(Constant.BooleanByte.YES, "金额已返还");
			} else {
				return super.packJSON(Constant.BooleanByte.YES, Constant.AJAX_DECODEREFUSE);
			}
		} catch (LogicTransactionRollBackException e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
			return super.packJSON(Constant.BooleanByte.NO, "操作失败");
		}
	}

	@RequestMapping("/warn")
	public String warn(HttpServletRequest request, HttpSession session, Model model) {
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			Map<String, Object> params = getRequestParamters(request);
			if (listEnterprise == null || listEnterprise.size() == 0) {
				listEnterprise = enterpriseService.selectAll(params);
			}
			/********* 非超级管理员时只能查询本团队内的数据 ************/

			if (!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())) {
				List<Enterprise> listEnterpriseTemp = new ArrayList<Enterprise>();
				for (Enterprise enterprise : listEnterprise) {
					if (enterprise.getEnterprisecode().equals(ee.getEnterprisecode())) {
						listEnterpriseTemp.add(enterprise);
					}
				}

				model.addAttribute("listEnterprise", listEnterpriseTemp);
			} else {
				model.addAttribute("listEnterprise", listEnterprise);
			}
			// 获取企业游戏列表
			List<Game> listGame = (List<Game>) session.getAttribute(Constant.ENTERPRISE_GAMES);
			model.addAttribute("listGame", listGame);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/userinfo/moneyInAndOutRecordWarn";

	}
	/**
	 * 查询上下分异常记录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/warnData")
	@ResponseBody
	public Map<String, Object> warnData(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);

			/********* 非超级管理员时只能查询本团队内的数据 ************/
			if (!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())) {
				// 非管理员不能查询所有企业条件
				if (object.get("enterprisecode") == null || object.get("enterprisecode").toString().equals("")) {
					// return super.packJSON(Constant.BooleanByte.NO,
					// "你不能查询所有企业");
					object.put("enterprisecode", ee.getEnterprisecode());
				}

				super.dataLimits(ee, object, session);
			}
			
			double money = Double.valueOf(object.get("money").toString());
			
			List<UserMoneyInAndOut> list = ueserMoneyInAndOutService.findMoneyInAndOutWarn(object);
			if (null != list && list.size() > 0) {
				for (UserMoneyInAndOut item : list) {
					item.setGametype(GameUtils.getGameTypeNameByGameType(item.getGametype()));
					if(item.getUpdatecapital().toString().equals("1") && item.getMoneyinoutamount().doubleValue() >= money) {
						item.setMoneyinoutcomment("<font color='red'><b>上下分金额超出风险值</b></font>");
					}
				}
			}
			/********* 非超级管理员时只能查询本团队内的数据 ************/
			if (SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())) {
				for (UserMoneyInAndOut userMoneyInAndOut : list) {
					userMoneyInAndOut.setEmployeename(
							userMoneyInAndOut.getEnterprisecode() + "-" + userMoneyInAndOut.getEmployeecode());
				}
			}

			Map<String, Object> result = ueserMoneyInAndOutService.findMoneyInAndOutCountWarn(object);
			int count = StringUtil.getInt(result.get("count"));

			return super.formatPagaMap(list, count);

		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
			return super.formatPagaMap(null, 0);
		}
	}
	
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
