package com.maven.controller;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.constant.Constant;
import com.maven.constant.Enum_MSG;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeType.Type;
import com.maven.entity.PermissionMenu.Enum_language;
import com.maven.entity.UserLogs;
import com.maven.exception.LogicTransactionException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.CreditAgentService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.UserLogsService;
import com.maven.utils.StringUtil;


@Controller
@RequestMapping("/CreditAgent")
public class CreditAgentController extends BaseController{
	
	private final static String SHIFT_SUCCESS = "{0}成功";
	
	private final static String DECODE_FAIL = "解密失败，禁止操作";
	
	private final static String MONEY_EXCEPTION = "金额异常";
	
	private static enum OPARETE{
		IN("IN","转入"),
		OUT("OUT","转出");
		public String value;
		public String desc;
		
		private OPARETE(String value,String desc){
			this.value=value;
			this.desc=desc;
		}
	}
	
	
	private static LoggerManager log = LoggerManager.getLogger(
			BettingYgAgController.class.getName(), OutputManager.LOG_CREDITAGENT);
	
	@Autowired
	private CreditAgentService creditAgentService;
	@Autowired
	private UserLogsService userLogsService;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	/**
	 * 代理给直系下级转账与提取下级账户余额
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/shifintergral")
	@ResponseBody
	public Map<String,Object> shiftIntegral(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee e_employee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			/*
			if (!Type.信用代理.value.equals(e_employee.getEmployeetypecode())) {
				if (Enum_language.英文.value.equals(session.getAttribute(Constant.LANGUAGE))) {
					return super.packJSON(Constant.BooleanByte.NO, "Non-credit agents can not operate.");
				}
				return super.packJSON(Constant.BooleanByte.NO, "非信用代理不能操作");
			}
			*/
			String money = request.getParameter("money");
			String intergral_employee = request.getParameter("sign");
			String opreate = request.getParameter("opreate");
			if(!super.decodeSign(intergral_employee, session.getId())){
				if (Enum_language.英文.value.equals(session.getAttribute(Constant.LANGUAGE))) {
					return super.packJSON(Constant.BooleanByte.NO, "Decryption failed, prohibited operation");
				}
				return super.packJSON(Constant.BooleanByte.NO, DECODE_FAIL);
			}
			if(!StringUtil.isNumberFloat(money)){
				if (Enum_language.英文.value.equals(session.getAttribute(Constant.LANGUAGE))) {
					return super.packJSON(Constant.BooleanByte.NO, "The amount is abnormal.");
				}
				return super.packJSON(Constant.BooleanByte.NO, MONEY_EXCEPTION);
			}
			intergral_employee = intergral_employee.split("_")[1];
			EnterpriseEmployee targetObject = enterpriseEmployeeService.takeEmployeeByCode(intergral_employee);
			if (!targetObject.getParentemployeecode().equals(e_employee.getEmployeecode())) {
				if (Enum_language.英文.value.equals(session.getAttribute(Constant.LANGUAGE))) {
					return super.packJSON(Constant.BooleanByte.NO, "Non-directly superior can not operate.");
				}
				return super.packJSON(Constant.BooleanByte.NO, "非直属上级不能操作");
			}else if(opreate.equals(OPARETE.IN.value)){
				creditAgentService.tc_in_integral(e_employee.getEmployeecode(),intergral_employee, new BigDecimal(money));
				
				userLogsService.addActivityBetRecord(new UserLogs(targetObject.getEnterprisecode(), targetObject.getEmployeecode(), targetObject.getLoginaccount(), 
						UserLogs.Enum_operatype.信用代理转账, "给代理转入:"+money, e_employee.getLoginaccount(), null));
				if (Enum_language.英文.value.equals(session.getAttribute(Constant.LANGUAGE))) {
					return super.packJSON(Constant.BooleanByte.YES, "Transfer In Success.");
				}
				return super.packJSON(Constant.BooleanByte.YES, SHIFT_SUCCESS.replace("{0}", OPARETE.IN.desc));
			}else if(opreate.equals(OPARETE.OUT.value)){
				
				if( !e_employee.getEmployeetypecode().equals(Type.信用代理.value) ){
					if (Enum_language.英文.value.equals(session.getAttribute(Constant.LANGUAGE))) {
						return super.packJSON(Constant.BooleanByte.YES, "Non credit agent cannot transfer the balance from the member account!");
					}
					return super.packJSON(Constant.BooleanByte.NO, "非信用代理不能从会员账户转出余额！");
				}
				
				creditAgentService.tc_out_integral(intergral_employee,e_employee.getEmployeecode(), new BigDecimal(money));
				
				userLogsService.addActivityBetRecord(new UserLogs(targetObject.getEnterprisecode(), targetObject.getEmployeecode(), targetObject.getLoginaccount(), 
					     UserLogs.Enum_operatype.信用代理转账, "给代理转出:"+money, e_employee.getLoginaccount(), null));
				if (Enum_language.英文.value.equals(session.getAttribute(Constant.LANGUAGE))) {
					return super.packJSON(Constant.BooleanByte.YES, "Transfer Out Success.");
				}
				return super.packJSON(Constant.BooleanByte.YES, SHIFT_SUCCESS.replace("{0}", OPARETE.OUT.desc));
			}else{
				if (Enum_language.英文.value.equals(session.getAttribute(Constant.LANGUAGE))) {
					return super.packJSON(Constant.BooleanByte.YES, "Transfer In Success.");
				}
				return super.packJSON(Constant.BooleanByte.YES, SHIFT_SUCCESS.replace("{0}", OPARETE.IN.desc));
			}
		}catch(LogicTransactionException e){
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		}catch (LogicTransactionRollBackException e) {
			return super.packJSON(Constant.BooleanByte.NO, e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			if (Enum_language.英文.value.equals(session.getAttribute(Constant.LANGUAGE))) {
				return super.packJSON(Constant.BooleanByte.NO, "System error, please try again later.");
			}
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}

}
