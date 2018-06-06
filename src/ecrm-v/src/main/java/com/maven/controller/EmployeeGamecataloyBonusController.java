package com.maven.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.maven.constant.Constant;
import com.maven.entity.EmployeeGamecataloyBonus;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.Game;
import com.maven.entity.UserLogs;
import com.maven.entity.EnterpriseEmployeeType.Type;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.EmployeeGamecataloyBonusService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.GameService;
import com.maven.service.UserLogsService;
import com.maven.util.BeanToMapUtil;

@Controller
@RequestMapping("/GCBonus")
public class EmployeeGamecataloyBonusController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			EmployeeGamecataloyBonusController.class.getName(), OutputManager.LOG_EMPLOYEEGAMECATALOYBONUS);
	
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	
	@Autowired
	private EmployeeGamecataloyBonusService employeeGamecataloyBonusService;
	@Autowired
	private UserLogsService userLogsService;
	
	/**
	 * 批量设置洗码页面
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/bonus")
	public String bonus(HttpServletRequest request,HttpSession session,Model model){
		EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
		try {
			Map<String,BigDecimal> bonus = employeeGamecataloyBonusService.takeEmployeeGameCategoryBonusMap(ee.getEmployeecode());
			List<Game> games = gameService.takeEnterpriseGames(ee.getEnterprisecode());
			model.addAttribute("bonus", bonus);
			model.addAttribute("games", games);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/settlement/bonus";
	}
	
	/**
	 * 批量设置会员洗码
	 */
	@RequestMapping("/batchSaveBonus")
	@ResponseBody
	public Map<String,Object> batchSaveBonus(HttpSession session,HttpServletRequest request,@ModelAttribute EnterpriseEmployee enterpriseEmployee){
		try {
			Map<String,Object> object = super.getRequestParamters(request);
			//获取当前登录用户的信息
			String employeecodes = (String) request.getParameter("sign");
			if(StringUtils.isBlank(employeecodes)){
				return super.packJSON(Constant.BooleanByte.NO, "请选择需要设置洗码的用户"); 
			}
			String[] employeecode = employeecodes.split(",");
			if(!super.decodeSign(employeecode, session.getId())){
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
			Map<String,List<EmployeeGamecataloyBonus>> userbonus = new HashMap<String, List<EmployeeGamecataloyBonus>>();
			userbonus.put("__insert", new ArrayList<EmployeeGamecataloyBonus>());
			userbonus.put("__upateUp", new ArrayList<EmployeeGamecataloyBonus>());
			userbonus.put("__upateDown", new ArrayList<EmployeeGamecataloyBonus>());
			for (String __ec : employeecode) {
				EnterpriseEmployee update = enterpriseEmployeeService.takeEmployeeByCode(__ec.split("_")[1]);
				Map<String,List<EmployeeGamecataloyBonus>> __tem = bonusVerify(update, object);
				if(__tem.get("__insert")!=null)	userbonus.get("__insert").addAll(__tem.get("__insert"));
				if(__tem.get("__upateUp")!=null)	userbonus.get("__upateUp").addAll(__tem.get("__upateUp"));
				if(__tem.get("__upateDown")!=null)	userbonus.get("__upateDown").addAll(__tem.get("__upateDown"));
			}
			employeeGamecataloyBonusService.tc_settingMultiMemberBonus(userbonus);
			return super.packJSON(Constant.BooleanByte.YES, "洗码设置成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "操作失败，请稍后尝试");
		}
	}
	
	@RequestMapping(value={"/memberSetting","/agentSetting","/userSetting"})
	public String setting(HttpServletRequest request,HttpSession session , Model model){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			
			Map<String,Object> object = super.getRequestParamters(request);
			String employeecode_encrypt = String.valueOf(object.get("employeecode"));
			if(super.decodeSign(employeecode_encrypt, session.getId())){
				String employeecode = employeecode_encrypt.split("_")[1];
				//修改用户的信息
				EnterpriseEmployee user = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
				//修改用户的当前洗码
				Map<String,BigDecimal> bonus = employeeGamecataloyBonusService.takeEmployeeGameCategoryBonusMap(employeecode);
				//加载品牌接入平台信息
				List<Game> games = gameService.takeEnterpriseGames(user.getEnterprisecode());
				//上级用户的信息
				EnterpriseEmployee superior = enterpriseEmployeeService.takeEmployeeByCode(user.getParentemployeecode());
				//上级用户的洗码
				Map<String,BigDecimal> supbonus =  employeeGamecataloyBonusService.takeEmployeeGameCategoryBonusMap(superior.getEmployeecode());
				
				user.setEmployeecode(employeecode_encrypt);
				model.addAttribute("games", games);
				model.addAttribute("bonus", bonus);
				model.addAttribute("user", user);
				model.addAttribute("superior", superior);
				model.addAttribute("supbonus", supbonus);
				
				/********************取出我的下线的最高对应分红、占成、洗码值*****************************/
				//1、取出我的下线列表，含会员、代理、员工
				Map<String,Object> paramObj = new HashMap<String, Object>();
				paramObj.put("parentemployeecode", employeecode);
				List<String> employeetypecodes = new ArrayList<String>();
				employeetypecodes.add(Type.代理.value);
				employeetypecodes.add(Type.员工.value);
				employeetypecodes.add(Type.会员.value);
				paramObj.put("employeetypecodes", employeetypecodes);
				
				BigDecimal maxd_dividend = new BigDecimal(0);//下线的最高分红
				BigDecimal max_share = new BigDecimal(0);//下线的最高占成
				Map<String,BigDecimal> max_bonus = new HashMap<String, BigDecimal>(); 
				
				List<EnterpriseEmployee> listEnterpriseEmployee = enterpriseEmployeeService.selectAll(paramObj);
				List<String> employeecodes = new ArrayList<String>();
				for (EnterpriseEmployee item : listEnterpriseEmployee) {
					if( !item.getEmployeecode().equals(employeecode)) {
						employeecodes.add(item.getEmployeecode());
						
						//System.err.println("employeecode="+item.getEmployeecode()+"=Dividend="+item.getDividend()+"=Share="+item.getShare());
						if(item.getDividend().doubleValue() > maxd_dividend.doubleValue()) {
							maxd_dividend = item.getDividend();
						}
						if(item.getShare().doubleValue() > max_share.doubleValue()) {
							max_share = item.getShare();
						}
					}
				}
				//System.err.println("maxd_dividend="+maxd_dividend);
				//System.err.println("max_share="+max_share);
				
				//2、取出最大值
				if(employeecodes.size() > 0) {
					Map<String, Map<String, BigDecimal>> bonusAll = employeeGamecataloyBonusService.findGameBonus2(employeecodes);
					//System.out.println(bonusAll);
					
					for (String key : bonus.keySet()) {
						
						//遍历所有人
						BigDecimal temp = new BigDecimal(0);
						for (Map<String, BigDecimal> item : bonusAll.values()) {  
							
							if(item.get(key) != null && item.get(key).doubleValue() > temp.doubleValue()) {
								temp = item.get(key);
							}
						}
						//System.err.println(key+"=="+temp);
						max_bonus.put(key, temp);
					}
					
				}
				max_bonus.put("max_dividend", maxd_dividend);
				max_bonus.put("max_share", max_share);
				//System.out.println(max_bonus);
				model.addAttribute("submaxbonus", max_bonus);
				/********************取出我的下线的最高对应分红、占成、洗码值 the end*****************************/
				
				
				return "settlement/settlementsetting";
			}else{
				return Constant.PAGE_DECODEREFUSE;
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
		
	
	@RequestMapping("/saveSetting")
	@ResponseBody
	public Map<String,Object> saveSetting(HttpServletRequest request,HttpSession session){
		try {
			Map<String,Object> object = super.getRequestParamters(request);
			EnterpriseEmployee user = BeanToMapUtil.convertMap(object, EnterpriseEmployee.class);
			//加密验证
			if(!super.decodeSign(user.getEmployeecode(), session.getId())){
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
			String employeecode = user.getEmployeecode().split("_")[1];
			//业务逻辑验证
			EnterpriseEmployee update = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
			user.setEmployeecode(employeecode);
			if(user.getDividend()!=null&&user.getShare()!=null){
				user.setDividend(user.getDividend().divide(new BigDecimal("100")));
				user.setShare(user.getShare().divide(new BigDecimal("100")));
				dividendShareVerify(update, user.getDividend(), user.getShare());
			}
			Map<String,List<EmployeeGamecataloyBonus>> __userBonus = bonusVerify(update, object);
			//数据入库
			employeeGamecataloyBonusService.tc_settingSettleConfig(user,__userBonus);
			return super.packJSON(Constant.BooleanByte.YES, "成功修改结算配置");
		}catch(LogicTransactionRollBackException e){
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, "修改结算配置失败，请稍后尝试");
		}
		
	}
	
	/**
	 * 洗码设置审核
	 * @param ee
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private Map<String,List<EmployeeGamecataloyBonus>> bonusVerify(EnterpriseEmployee ee,Map<String,Object> object) throws Exception{
		Map<String,BigDecimal> __overbonues = employeeGamecataloyBonusService.takeEmployeeGameCategoryBonusMap(ee.getEmployeecode());
		Map<String,BigDecimal> __supbonus =  employeeGamecataloyBonusService.takeEmployeeGameCategoryBonusMap(ee.getParentemployeecode());
		Map<String,List<EmployeeGamecataloyBonus>> __userBouns = new HashMap<String, List<EmployeeGamecataloyBonus>>();
		List<EmployeeGamecataloyBonus> __upateUp =  new ArrayList<EmployeeGamecataloyBonus>();
		List<EmployeeGamecataloyBonus> __upateDown =  new ArrayList<EmployeeGamecataloyBonus>();
		List<EmployeeGamecataloyBonus> __add =  new ArrayList<EmployeeGamecataloyBonus>();
		for (String key : object.keySet()) {
			if(key.indexOf("Game")>-1){
				BigDecimal __newbonus = new BigDecimal(String.valueOf(object.get(key))).divide(new BigDecimal(100)).setScale(4);
				BigDecimal __superbonus = __supbonus.get(key);
				BigDecimal __overbonus = __overbonues.get(key);
				if(__newbonus.compareTo(new BigDecimal(0))==-1){
					throw new LogicTransactionRollBackException(key.replace("Game","")+"洗码不能小于0%");
				}
				if(__superbonus==null||__newbonus.compareTo(__superbonus)==1){
					throw new LogicTransactionRollBackException(ee.getLoginaccount()+"的"+key.replace("Game","")+"洗码不能超过上级代理");
				}
				if(__newbonus.compareTo(new BigDecimal(100))==1){
					throw new LogicTransactionRollBackException(key.replace("Game","")+"洗码不能超过100%");
				}
				String[] gamekeys = key.split("_");
				if(__overbonus==null){
					__add.add(new EmployeeGamecataloyBonus(ee.getEmployeecode(),
							ee.getParentemployeecode(),gamekeys[0],gamekeys[1],__newbonus));
				}else if(__overbonus.compareTo(__newbonus)==-1){
					__upateUp.add(new EmployeeGamecataloyBonus(ee.getEmployeecode(),
							ee.getParentemployeecode(),gamekeys[0],gamekeys[1],__newbonus));
				}else if(__overbonus.compareTo(__newbonus)==1){
					__upateDown.add(new EmployeeGamecataloyBonus(ee.getEmployeecode(),
							ee.getParentemployeecode(),gamekeys[0],gamekeys[1],__newbonus));
				}
			}
		}
		__userBouns.put("__insert", __add);
		__userBouns.put("__upateUp", __upateUp);
		__userBouns.put("__upateDown", __upateDown);
		return __userBouns;
	}
	/**
	 * 分红或占成设置审核
	 * @param employeecode
	 * @param parentemployeecode
	 * @param dividend
	 * @param share
	 * @throws Exception
	 */
	private void dividendShareVerify(EnterpriseEmployee ee,BigDecimal dividend,BigDecimal share) throws Exception{
		EnterpriseEmployee superior = enterpriseEmployeeService.takeEmployeeByCode(ee.getParentemployeecode());
		if(dividend.doubleValue()<0){
			throw new LogicTransactionRollBackException("分红不能小于"+ee.getDividend().multiply(new BigDecimal("0"))+"%");
		}if(share.doubleValue()<0){
			throw new LogicTransactionRollBackException("占成不能小于"+ee.getShare().multiply(new BigDecimal("0"))+"%");
		}else if(dividend.doubleValue()>1
				||dividend.compareTo(superior.getDividend())==1){
			throw new LogicTransactionRollBackException("下级分红不能超过上级代理分红");
		}else if(share.doubleValue()>1
				||share.compareTo(superior.getShare())==1){
			throw new LogicTransactionRollBackException("下级占成不能超过上级代理占成");
		}
		
	}

	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
