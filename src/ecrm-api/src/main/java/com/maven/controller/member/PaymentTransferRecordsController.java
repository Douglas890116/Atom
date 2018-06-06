package com.maven.controller.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.entity.TakeDepositRecord;
import com.maven.entity.TakeDepositRecord.Enum_ordertype;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.TakeDepositRecoredService;
import com.maven.util.AttrCheckout;

@RequestMapping("/Fetch")
@Controller
public class PaymentTransferRecordsController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			PaymentTransferRecordsController.class.getName(), OutputManager.LOG_USER_FETCHRECORD);
	
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;

	@Autowired
	private TakeDepositRecoredService takeDepositRecoredService;
	
	/**
	 * 存款记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/SaveOrder",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String saveOrder(HttpServletRequest request){
		try {
			return depositWithdrawOrder(Enum_ordertype.存款.value,request);
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
	 * 取款记录
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/TakeOrder",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String takeOrder(HttpServletRequest request){
		try {
			return depositWithdrawOrder(Enum_ordertype.取款.value,request);
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
	
	private static int formatInteger(Object obj) {
		if(obj == null) {
			return 0;
		} else {
			return Integer.parseInt(obj.toString());
		}
	}
	/**
	 * 存取款订单
	 * @param ordertype
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private String depositWithdrawOrder(int ordertype , HttpServletRequest request) throws Exception{
		Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
		AttrCheckout.checkout(object,true,new String[]{"enterprisecode","employeecode","start","limit"},
				new String[]{"ordernumber","orderdate","paymenttypecode","orderamount","employeepaymentaccount","orderstatus"});
		enterpriseEmployeeService.takeEnterpriseEmployee(object);
		object.put("ordertype", ordertype);
		object.put("field", "orderdate");
		object.put("direction", "desc");
		
		if(object.get("orderdate_begin") != null ) {
			if(object.get("orderdate_begin").toString().length() == 10) {
				object.put("orderStartDate", object.get("orderdate_begin").toString().replaceAll("-", ""));
			} else {
				//
			}
		}
		if(object.get("orderdate_end") != null ) {
			if(object.get("orderdate_end").toString().length() == 10) {
				object.put("orderEndDate", object.get("orderdate_end").toString().replaceAll("-", ""));
			} else {
				//
			}
		}
		
		object.remove("orderdate_begin");
		object.remove("orderdate_end");
		
		System.out.println("===============================查询存取款记录条件："+object);
				
		List<TakeDepositRecord> list = takeDepositRecoredService.findTakeDepositRecord(object);
		
		Map<String, Object> result = takeDepositRecoredService.findcountTakeDepositRecord(object);
		int count = formatInteger(result.get("count"));
		Map<String,Object> parameterMap = new HashMap<String,Object>(count);
		parameterMap.put("record", list);
		parameterMap.put("count", count);
		parameterMap.put("sumamount", result.get("orderamount"));//总记
		return Enum_MSG.成功.message(parameterMap);
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
