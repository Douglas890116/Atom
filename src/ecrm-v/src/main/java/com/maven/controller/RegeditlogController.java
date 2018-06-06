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
import com.maven.entity.EnterpriseEmployeeRegeditLog;
import com.maven.entity.EnterpriseGame;
import com.maven.entity.Game;
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
import com.maven.service.EnterpriseEmployeeRegeditLogService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseGameService;
import com.maven.service.EnterpriseService;
import com.maven.service.GameService;
import com.maven.util.BeanToMapUtil;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;
import com.maven.util.ActivityUtils.ActivityCheckMessage;
import com.maven.utils.ApiKeyObject;
import com.maven.utils.StringUtil;

/**
 * 注册日志分析
 * 
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/regeditlog")
public class RegeditlogController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			EnterpriseOperatingBrandGameController.class.getName(), OutputManager.LOG_GAMEAPIINPUT);
	
	@Autowired
	private EnterpriseEmployeeRegeditLogService enterpriseEmployeeRegeditLogService;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	
	/**
	 * index
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model){
		
		return "/regeditlog/index";
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
			List<EnterpriseEmployeeRegeditLog> listEnterpriseGame = enterpriseEmployeeRegeditLogService.selectBetRecordGroup(params);
			int count = enterpriseEmployeeRegeditLogService.selectBetRecordGroupCount(params);
			if(listEnterpriseGame == null) {
				listEnterpriseGame = new ArrayList<EnterpriseEmployeeRegeditLog>();
			}
			
			
			Map<String, Object> data = super.formatPagaMap(listEnterpriseGame, count);
			
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return null;
		
	}
	
	/**
	 * emplist
	 * @param request
	 * @return
	 */
	@RequestMapping("/emplist")
	public String emplist(HttpServletRequest request, Model model){
		model.addAttribute("ip", request.getParameter("ip"));
		return "/regeditlog/emplist";
	}
	
	/**
	 * 分页查询
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/data")
	@ResponseBody
	public Map<String, Object> data(HttpServletRequest request,Model model){
		
		try {
			Map<String,Object> params = getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee) request.getSession().getAttribute(Constant.USER_SESSION);
			if(!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())){
				params.put("enterprisecode", ee.getEnterprisecode());
			}
			
			List<EnterpriseEmployeeRegeditLog> listEnterpriseGame = enterpriseEmployeeRegeditLogService.selectBetRecord(params);
			int count = enterpriseEmployeeRegeditLogService.selectBetRecordCount(params);
			
			Map<String, Object> data = super.formatPagaMap(listEnterpriseGame, count);
			
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return null;
		
	}
	
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
