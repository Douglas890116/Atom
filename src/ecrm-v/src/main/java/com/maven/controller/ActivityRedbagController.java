package com.maven.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy.pull.common.util.api.AVGameAPI;
import com.hy.pull.common.util.api.BaseInterfaceLog;
import com.hy.pull.common.util.api.GameAPI;
import com.hy.pull.common.util.game.av.AVUtil;
import com.maven.cache.SystemCache;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.dao.ActivityBetRecordDao;
import com.maven.dao.EnterpriseActivityPayDao;
import com.maven.entity.ActivityBetRecord;
import com.maven.entity.ActivityRedbag;
import com.maven.entity.ApiSoltGametype;
import com.maven.entity.EmployeeMessage;
import com.maven.entity.EmployeeMoneyChangeType;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseActivityPay;
import com.maven.entity.EnterpriseBrandActivity;
import com.maven.entity.EmployeeMessage.Enum_readstatus;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneyinouttype;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseGame;
import com.maven.entity.Game;
import com.maven.entity.UserLogs;
import com.maven.entity.ActivityBetRecord.Enum_betrecordstatus;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.game.OrderNewUtil;
import com.maven.game.enums.GameEnum;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.ActivityBetRecordService;
import com.maven.service.ActivityRedbagService;
import com.maven.service.ApiSoltGametypeService;
import com.maven.service.EmployeeMessageService;
import com.maven.service.EnterpriseActivityPayService;
import com.maven.service.EnterpriseBrandActivityService;
import com.maven.service.EnterpriseEmployeeCapitalAccountService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseGameService;
import com.maven.service.EnterpriseService;
import com.maven.service.GameService;
import com.maven.service.UserLogsService;
import com.maven.util.BeanToMapUtil;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;
import com.maven.util.ActivityUtils.ActivityCheckMessage;
import com.maven.utils.ApiKeyObject;
import com.maven.utils.StringUtil;

/**
 * 签到领红包
 * 
 * 记录及审核
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/redbag")
public class ActivityRedbagController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			EnterpriseOperatingBrandGameController.class.getName(), OutputManager.LOG_GAMEAPIINPUT);
	
	@Autowired
	private ActivityRedbagService activityRedbagService;
	@Autowired
	private EnterpriseEmployeeCapitalAccountService enterpriseEmployeeCapitalAccountService;
	@Autowired
	private EnterpriseBrandActivityService enterpriseBrandActivityService;
	@Autowired
	private ActivityBetRecordService activityBetRecordService;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	 private UserLogsService userLogsService;
	
	/**
	 * index
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model){
		
		return "/activitypay/redbag_index";
	}
	
	/**
	 * 分页查询
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/pagelist")
	@ResponseBody
	public Map<String, Object> pagelist(HttpServletRequest request,Model model){
		
		try {
			Map<String,Object> params = getRequestParamters(request);
			
			/*********非超级管理员时只能查询本团队内的数据************/
			EnterpriseEmployee ee = (EnterpriseEmployee) request.getSession().getAttribute(Constant.USER_SESSION);
			if(!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())){
				params.put("enterprisecode", ee.getEnterprisecode());
			}
			List<ActivityRedbag> listEnterpriseGame = activityRedbagService.selectBetRecord(params);
			int count = activityRedbagService.selectBetRecordCount(params);
			
			Map<String, Object> data = super.formatPagaMap(listEnterpriseGame, count);
			
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return null;
		
	}
	
	
	
	/**
	 * 修改（暂无修改方法）
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> edit(HttpServletRequest request,Model model){
		
		try {
			Map<String,Object> params = getRequestParamters(request);
			
//			EnterpriseActivityPay data = activityRedbagService.selectByPrimaryKey(params.get("lsh").toString());
//			data.setAuditremark("修改实发金额为"+data.getPaymoneyreal());
//			data.setPaymoneyreal(Double.valueOf(params.get("paymoneyreal").toString()));
//			enterpriseActivityPayService.updateActivityBetRecord(data);
			
			return this.packJSON(Constant.BooleanByte.YES,"操作成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.packJSON(Constant.BooleanByte.YES,"操作失败，请稍后尝试");
		}		
	}
	
	/**
	 * 审核
	 * 
	 * 通过或拒绝
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/audit")
	@ResponseBody
	public Map<String, Object> audit(HttpServletRequest request,Model model){
		
		try {
			Map<String,Object> params = getRequestParamters(request);
			EnterpriseEmployee ee= (EnterpriseEmployee) request.getSession().getAttribute(Constant.USER_SESSION);
			
			ActivityRedbag data = activityRedbagService.selectByPrimaryKey(params.get("lsh").toString());
			data.setAuditer(ee.getLoginaccount());
			data.setAudittime(new Date());
			data.setAuditremark(params.get("auditremark").toString());
			if(String.valueOf(params.get("auditFlag")).equals("通过")) {
				data.setStatus(ActivityRedbag.Enum_status.审核通过.value);
			} else {
				data.setStatus(ActivityRedbag.Enum_status.审核拒绝.value);
			}
			activityRedbagService.updateActivityBetRecord(data);
			
			userLogsService.addActivityBetRecord(new UserLogs(data.getEnterprisecode(), data.getEmployeecode(), data.getLoginaccount(), 
					UserLogs.Enum_operatype.红利信息业务, "签到领红包:审核"+data.getMoney(), ee.getLoginaccount(), null));
			
			return this.packJSON(Constant.BooleanByte.YES,"操作成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.packJSON(Constant.BooleanByte.YES,"操作失败，请稍后尝试");
		}		
	}
	
	/**
	 * 支付
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/pay")
	@ResponseBody
	public Map<String, Object> pay(HttpServletRequest request,Model model){
		
		try {
			Map<String,Object> params = getRequestParamters(request);
			
			EnterpriseEmployee ee= (EnterpriseEmployee) request.getSession().getAttribute(Constant.USER_SESSION);
			
			
			ActivityRedbag data = activityRedbagService.selectByPrimaryKey(params.get("lsh").toString());
			
			enterpriseEmployeeCapitalAccountService.tc_updateCapitalAccount(RandomString.UUID(), data.getEmployeecode(), new BigDecimal(data.getMoney()), 
					new EmployeeMoneyChangeType(Enum_moneychangetype.优惠活动.value,Enum_moneychangetype.优惠活动.desc, Enum_moneyinouttype.进账), 
					"操作人:"+ee.getLoginaccount()  + " 活动：签到领红包");
			
			data.setPayer(ee.getLoginaccount());
			data.setPaytyime(new Date());
			data.setStatus(ActivityRedbag.Enum_status.已发放.value);
			activityRedbagService.updateActivityBetRecord(data);
			
			//记录打码流水
			EnterpriseEmployee __employee = enterpriseEmployeeService.takeEmployeeByCode(data.getEmployeecode());
			String LSBS = data.getLsbs();
			double betMultiple = Double.valueOf(LSBS);
			EnterpriseBrandActivity brandActivity = null;
			if(betMultiple > 0) {
				brandActivity = enterpriseBrandActivityService.checkEnterpriseBrandActivity(Integer.valueOf(data.getEnterprisebrandactivitycode()));
				if (brandActivity == null){
					throw new LogicTransactionRollBackException(ActivityCheckMessage.BRAND_ACTIVITY_NOTEXIST.desc);
				}
				
				ActivityBetRecord betrecord = new ActivityBetRecord();
				betrecord.setEmployeecode(__employee.getEmployeecode());
				betrecord.setEcactivitycode(brandActivity.getEcactivitycode());
				betrecord.setMustbet( (Double.valueOf(data.getMoney()) ) * betMultiple);
				betrecord.setAlreadybet(0.0);
				betrecord.setBetrecordstatus(Enum_betrecordstatus.未完成.value);
				betrecord.setCreatetime(new Date());
				betrecord.setLoginaccount(__employee.getLoginaccount());
				betrecord.setRecharge(Double.valueOf(data.getMoney()));//充值金额
				
				betrecord.setEnterprisecode(__employee.getEnterprisecode());//企业编码
				betrecord.setBrandcode(__employee.getBrandcode());//品牌编码
				betrecord.setParentemployeeaccount(__employee.getParentemployeeaccount());//上级账号
				betrecord.setParentemployeecode(__employee.getParentemployeecode());//上级编码
				activityBetRecordService.addActivityBetRecord(betrecord);
			}
			
			userLogsService.addActivityBetRecord(new UserLogs(data.getEnterprisecode(), data.getEmployeecode(), null, 
					UserLogs.Enum_operatype.红利信息业务, "签到领红包:发放"+data.getMoney(), ee.getLoginaccount(), null));
			
			return this.packJSON(Constant.BooleanByte.YES,"操作成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.packJSON(Constant.BooleanByte.YES,"操作失败，请稍后尝试："+e.getMessage());
		}		
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
