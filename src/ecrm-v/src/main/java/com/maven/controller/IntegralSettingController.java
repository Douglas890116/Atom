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
import com.maven.entity.GameCategory;
import com.maven.entity.IntegralSetting;
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
import com.maven.service.GameCategoryService;
import com.maven.service.GameService;
import com.maven.service.IntegralSettingService;
import com.maven.service.UserLogsService;
import com.maven.util.BeanToMapUtil;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;
import com.maven.util.ActivityUtils.ActivityCheckMessage;
import com.maven.utils.ApiKeyObject;
import com.maven.utils.StringUtil;

/**
 * 
 * 积分配置
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/IntegralSetting")
public class IntegralSettingController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			EnterpriseOperatingBrandGameController.class.getName(), OutputManager.LOG_GAMEAPIINPUT);
	
	@Autowired
	private IntegralSettingService integralSettingService;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	@Autowired
	private UserLogsService userLogsService;
	@Autowired
	private GameCategoryService gameCategoryService;
	@Autowired
	private GameService gameService;
	
	/**
	 * setting
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/setting")
	public String setting(HttpServletRequest request,Model model){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)request.getSession().getAttribute(Constant.USER_SESSION);
			Map<String,Object> params = getRequestParamters(request);
			
			List<IntegralSetting> listIntegralSetting = new ArrayList<IntegralSetting>();
			
			String enterprisecode = request.getParameter("enterprisecode");
			if(enterprisecode == null) {
				params.put("enterprisecode", ee.getEnterprisecode());
			} else {
				params.put("enterprisecode", enterprisecode);
			}
			
			if(ee.getBrandcode() != null && !ee.getBrandcode().equals("")) {
				params.put("brandcode", ee.getBrandcode());
			}
			
			List<IntegralSetting> list = integralSettingService.selectAll(params);
			for (IntegralSetting integralSetting : list) {
				String key = integralSetting.getGametype()+"_"+integralSetting.getGamebigtype();
				GameCategory category = SystemCache.getInstance().getGameCategoryByCnmKey(key);
				integralSetting.setKeyname(integralSetting.getGametype() + " "+ category.getCategoryname());
			}
			
			//加载品牌接入平台信息
			List<Game> games = gameService.takeEnterpriseGames(ee.getEnterprisecode());
			for (Game game : games) {
				List<GameCategory> listGameCategory = SystemCache.getInstance().getGameCategory(game.getGametype());
				for (GameCategory gameCategory : listGameCategory) {
					String key = gameCategory.getGametype()+"_"+gameCategory.getCategorytype();
					GameCategory category = SystemCache.getInstance().getGameCategoryByCnmKey(key);
					String keyname = gameCategory.getGametype() + " "+ category.getCategoryname();
					
					boolean isadd = false;
					for (IntegralSetting temp : list) {
						if(temp.getKeyname().equals(keyname)) {
							isadd = true;
							listIntegralSetting.add(temp);
							break;
						}
					}
					if(isadd == false) {
						listIntegralSetting.add(new IntegralSetting( ee.getEnterprisecode(),  ee.getBrandcode(),  gameCategory.getGametype(),  gameCategory.getCategorytype(), keyname));
					}
				}
			}
			
			
			model.addAttribute("list", listIntegralSetting);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/integral/setting";
	}
	
	
	
	/**
	 * 保存
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest request,Model model){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)request.getSession().getAttribute(Constant.USER_SESSION);
			
			String[] lshs = request.getParameterValues("lsh");//数量不会与其他数据一样多
			String[] gametypes = request.getParameterValues("gametype");
			String[] gamebigtypes = request.getParameterValues("gamebigtype");
			String[] m2iRates = request.getParameterValues("m2iRate");
			String[] i2mRates = request.getParameterValues("i2mRate");
			String[] timeoutdays = request.getParameterValues("timeoutday");
			
			List<IntegralSetting> listIntegralSetting = new ArrayList<IntegralSetting>();
			
			for (int i = 0; i < gametypes.length; i++) {
				String gametype = gametypes[i];
				String gamebigtype = gamebigtypes[i];
				String m2iRate = m2iRates[i];
				String i2mRate = i2mRates[i];
				String timeoutday = timeoutdays[i];
				
				listIntegralSetting.add(new IntegralSetting(ee.getEnterprisecode(), ee.getBrandcode(), gametype, gamebigtype,new BigDecimal(m2iRate), new BigDecimal(i2mRate), Integer.valueOf(timeoutday)));
			}
			
			int count = integralSettingService.saveRecordBatch(listIntegralSetting);
			
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
			
			/*
			userLogsService.addActivityBetRecord(new UserLogs(data.getEnterprisecode(), data.getEmployeecode(), null, 
					UserLogs.Enum_operatype.红利信息业务, "签到领红包:发放"+data.getMoney(), ee.getLoginaccount(), null));
			*/
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
