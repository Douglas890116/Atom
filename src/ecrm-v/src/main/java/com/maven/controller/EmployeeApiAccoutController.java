package com.maven.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy.pull.common.util.api.GameAPI;
import com.maven.annotation.OperationLog;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseGame;
import com.maven.entity.Game;
import com.maven.entity.UserLogs;
import com.maven.exception.LogicTransactionException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.game.APIService;
import com.maven.game.APIServiceNew;
import com.maven.game.HYAPIMessage;
import com.maven.game.enums.GameEnum.Enum_GameType;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.EmployeeApiAccoutService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseGameService;
import com.maven.service.EnterpriseService;
import com.maven.service.GameService;
import com.maven.service.UserLogsService;
import com.maven.util.JSONUnit;
import com.maven.utils.MoneyHelper;
import com.maven.utils.StringUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/EmployeeAccout")
public class EmployeeApiAccoutController extends BaseController{
	
	private static LoggerManager log = 
			LoggerManager.getLogger(BettingHqXcpController.class.getName(), OutputManager.LOG_EMPLOYEEAPIACCOUT);
	
	@Autowired
	private EmployeeApiAccoutService employeeApiAccoutService; 
	
	@Autowired
	private EnterpriseGameService enterpriseGameService;
	@Autowired
	private EnterpriseService enterpriseService;
	@Autowired
	private GameService gameService;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	
	/**
	 * 查看用户游戏账号
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/userAccout")
	@ResponseBody
	public Map<String,Object> userGameAccout(HttpServletRequest request,HttpSession session){
		try {
			
			String __employeecode = request.getParameter("employeecode");
			if(super.decodeSign(__employeecode, session.getId())){
				
				EnterpriseEmployee ee = enterpriseEmployeeService.takeEmployeeByCode(__employeecode.split("_")[1]);
				
				Map<String, String> BalanceAll = new APIServiceNew(ee.getEnterprisecode()).balancesAll(ee.getEmployeecode(), ee.getBrandcode());
				
				List<EmployeeApiAccout> __list = employeeApiAccoutService.selectUnionGName(new EmployeeApiAccout(__employeecode.split("_")[1]));
				for (EmployeeApiAccout __eaa : __list) {
					String balancex = BalanceAll.get(__eaa.getGametype());
					if(balancex == null || !StringUtil.isNumberFloat(balancex)) {
						balancex = "0.00";
					}
					__eaa.setBalance(new BigDecimal(MoneyHelper.doubleFormat(balancex)));
					__eaa.setSign(super.encryptString(__eaa.getEnterprisecode()+"|"+__eaa.getEmployeecode()+"|"+__eaa.getGametype(), session.getId()));
							
				}
				return super.formatPagaMap(__list, 10);
			}
			return super.formatPagaMap(null, 10);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	
	/**
	 * 查询单个账户余额
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/balance")
	@ResponseBody
	public Map<String,Object> balance(HttpServletRequest request,HttpSession session){
		try {
			String __sign = request.getParameter("sign");
			if(super.decodeSign(__sign, session.getId())){
				String[] __auments = __sign.split("_")[1].split("\\|");
				String __enterprisecode = __auments[0];
				String __employeecode = __auments[1];
				String __gametype = __auments[2];
				
				//特别处理东海国际
				String __result =  new APIServiceNew(__enterprisecode).balance(__gametype,__employeecode);//新接口
				JSONObject object = JSONObject.fromObject( __result );
				
				if(object.getString("code").equals("0")){
					return super.packJSON(Constant.BooleanByte.YES, object.getString("info"));
				} else {
					return super.packJSON(Constant.BooleanByte.NO, __result);
				}
				
				
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		}catch(LogicTransactionRollBackException e){
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	/**
	 * 手动下分
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/userShimobun")
	@ResponseBody
	public Map<String,Object> userShimobun(HttpServletRequest request,HttpSession session){
		try {
			String __sign = request.getParameter("sign");
			if(super.decodeSign(__sign, session.getId())){
				String[] __auments = __sign.split("_")[1].split("\\|");
				String __enterprisecode = __auments[0];
				String __employeecode = __auments[1];
				String __gametype = __auments[2];
				
				//特别处理东海国际
				String __result =  new APIServiceNew(__enterprisecode).userShimobun(__employeecode, __gametype);
				
				return super.packJSON(Constant.BooleanByte.YES, __result);
				
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		}catch(LogicTransactionException e){
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		}catch(LogicTransactionRollBackException e){
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	@RequestMapping("/gameList")
	public String gameList(HttpServletRequest request,Model model){
		
		EnterpriseEmployee loginEmployee = (EnterpriseEmployee) request.getSession().getAttribute(Constant.USER_SESSION);
		/*********非超级管理员时只能查询本团队内的数据************/
		if(!SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())){
			return Constant.PAGE_NOPRIVILEGE;
		}
		
		try {
			Map<String,Object> params = new HashMap<String, Object>();
			List<Enterprise> listEnterprise = enterpriseService.selectAll(params);
			List<Game> listGame = gameService.findGameData(params);
			model.addAttribute("listEnterprise", listEnterprise);
			model.addAttribute("listGame", listGame);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/enterprise/gamelist";
	}
	
	/**
	 * 查看企业下的游戏列表
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/gameListData")
	@ResponseBody
	public Map<String,Object> gameListData(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee loginEmployee = (EnterpriseEmployee) request.getSession().getAttribute(Constant.USER_SESSION);
			/*********非超级管理员时只能查询本团队内的数据************/
			if(!SystemConstant.getProperties("admin.employeecode").equals(loginEmployee.getEmployeecode())){
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
			
			Map<String,Object> params = getRequestParamters(request);
			List<EnterpriseGame> listEnterpriseGame = enterpriseGameService.selectAll(params);
			for (EnterpriseGame enterpriseGame : listEnterpriseGame) {
				enterpriseGame.setSign(super.encryptString(
						enterpriseGame.getEnterprisecode()+"|"+enterpriseGame.getGametype(), session.getId()));
			}
			/**
			//	对主键加密
			Map<String,String> s = new HashMap<String, String>();
			s.put("code", "sign");
			super.encryptSignTarget(listEnterpriseGame,session.getId(),s);
			*/
			
			
			int count = enterpriseGameService.selectAllCount(params);
			return super.formatPagaMap(listEnterpriseGame, count);
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	
	/**
	 * 批量查询余额
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/batchBalance")
	@ResponseBody
	public Map<String,Object> batchBalance(HttpServletRequest request,HttpSession session){
		try {
			String __sign = request.getParameter("sign");
			if(super.decodeSign(__sign, session.getId())){
				String[] __auments = __sign.split("_")[1].split("\\|");
				String __enterprisecode = __auments[0];
				String __gametype = __auments[1];
				
				APIServiceNew api = new APIServiceNew(__enterprisecode);
				
				EmployeeApiAccout temp = new EmployeeApiAccout();
				temp.setEnterprisecode(__enterprisecode);
				temp.setGametype(__gametype);
				List<EmployeeApiAccout> listEmployeeApiAccout = employeeApiAccoutService.selectUnionGName(temp);
				List<String> list = new ArrayList<String>();
				
				for (EmployeeApiAccout employeeApiAccout : listEmployeeApiAccout) {
					String __result =  api.balance(__gametype,listEmployeeApiAccout.get(0).getEmployeecode());
					HYAPIMessage ae = (HYAPIMessage)JSONUnit.getDTO(__result, HYAPIMessage.class);
					if(ae.getCode()==1){
						list.add(employeeApiAccout.getLoginaccount()+"=查询成功="+ae.getInfo()+"；<br />");
					}else{
						list.add(employeeApiAccout.getLoginaccount()+"=查询失败="+ae.getInfo()+"；<br />");
					}
				}
				
				if(list.size() == 0) {
					list.add("该企业该游戏平台下没有实际游戏用户！");
				} else {
					list.add("<font color='red'>总人数："+list.size()+"人</font>");
				}
				
				return super.packJSON(Constant.BooleanByte.YES, "查询完毕",list);
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		}catch(LogicTransactionRollBackException e){
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	/**
	 * 批量下分
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/batchDown")
	@ResponseBody
//	@OperationLog(OparetionDescription.BAHCT_USER_SHIMOBUN)
	public Map<String,Object> batchDown(HttpServletRequest request,HttpSession session){
		try {
			String __sign = request.getParameter("sign");
			if(super.decodeSign(__sign, session.getId())){
				String[] __auments = __sign.split("_")[1].split("\\|");
				String __enterprisecode = __auments[0];
				String __gametype = __auments[1];
				
				APIServiceNew api = new APIServiceNew(__enterprisecode);
				
				EmployeeApiAccout temp = new EmployeeApiAccout();
				temp.setEnterprisecode(__enterprisecode);
				temp.setGametype(__gametype);
				List<EmployeeApiAccout> listEmployeeApiAccout = employeeApiAccoutService.selectUnionGName(temp);
				
				List<String> list = new ArrayList<String>();
				for (EmployeeApiAccout employeeApiAccout : listEmployeeApiAccout) {
					
					String msg = "";
					
					//	先查询余额，因为只有
					String __result =  api.balance(__gametype,listEmployeeApiAccout.get(0).getEmployeecode());
					HYAPIMessage ae = (HYAPIMessage)JSONUnit.getDTO(__result, HYAPIMessage.class);
					if(ae.getCode()==1){
						msg = (employeeApiAccout.getLoginaccount()+"=查询成功="+ae.getInfo()+"；");
						
						BigDecimal balance = new BigDecimal(ae.getInfo());
						//进行下分
						if(balance.intValue()>1){
							__result =  api.userShimobun(employeeApiAccout.getEmployeecode(), __gametype);
							
							msg += (employeeApiAccout.getLoginaccount()+"=下分成功，余额="+__result+"；<br />");
						} else {
							msg += (employeeApiAccout.getLoginaccount()+"=下分取消，金额小于1；<br />");
						}
					}else{
						msg += (employeeApiAccout.getLoginaccount()+"=查询失败="+ae.getInfo()+"；<br />");
					}
					
					list.add(msg);
				}
				if(list.size() == 0) {
					list.add("该企业该游戏平台下没有实际游戏用户！");
				} else {
					list.add("<font color='red'>总人数："+list.size()+"人</font>");
				}
				
				return super.packJSON(Constant.BooleanByte.YES, "下分完毕",list);
				
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		}catch(LogicTransactionRollBackException e){
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}

	@Override
	public LoggerManager getLogger() {
		return log;
	}

}
