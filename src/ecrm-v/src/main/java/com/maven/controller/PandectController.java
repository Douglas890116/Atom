package com.maven.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.constant.Constant;
import com.maven.entity.EmployeeMoneyChangeType.Enum_moneychangetype;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseEmployeeType;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.EmployeeMoneyChangeService;
import com.maven.service.PandectService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/Pandect")
public class PandectController extends BaseController{

	private static LoggerManager log = LoggerManager.getLogger(EnterpriseThirdpartyPaymentController.class.getName(), OutputManager.LOG_PANDECT);
	
	@Autowired
	private PandectService pandectService;
	
	@Autowired
	private EmployeeMoneyChangeService employeeMoneyChangeService;
	
	@RequestMapping(value={"/iPandect","/APandect"})
	public String  iPandect(){
		return "/pandect/ipandect";
	}
	
	@RequestMapping(value={"/aPandect"})
	public String  aPandect(){
		return "/pandect/apandect";
	}
	
	/**
	 * 总计-团队输赢
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value={"/ELoseWin","/ALoseWin"})
	@ResponseBody
	public Map<String,Object> teamLoseWin(HttpServletRequest request,HttpSession session){
		try {
			String requestUri = request.getRequestURI().replaceAll("/+", "/");
			Map<String,Object> object = super.getRequestParamters(request);
			EnterpriseEmployee __ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			object.put("employeecode", __ee.getEmployeecode());
			if(requestUri.endsWith("/Pandect/ELoseWin")
					&&__ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.员工.value)){
				object.put("enterprisereport", true);
			}else{
				object.put("enterprisereport", false);
			}
			BigDecimal losewin = pandectService.usp_takeLoseWin(object);
			return super.packJSON(Constant.BooleanByte.YES, losewin);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	
	/**
	 * 总计-团队存款
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value={"/ESaveMoney","/ASaveMoney"})
	@ResponseBody
	public Map<String,Object> teamSaveMoney(HttpServletRequest request,HttpSession session){
		try {
			String requestUri = request.getRequestURI().replaceAll("/+", "/");
			Map<String,Object> object = super.getRequestParamters(request);
			EnterpriseEmployee __ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			object.put("employeecode", __ee.getEmployeecode());
			if(requestUri.endsWith("/Pandect/ESaveMoney")
					&&__ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.员工.value)){
				object.put("enterprisereport", true);
			}else{
				object.put("enterprisereport", false);
			}
			BigDecimal savemoney = pandectService.usp_savemoney(object);
			return super.packJSON(Constant.BooleanByte.YES, savemoney);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	
	/**
	 * 总计-团队取款
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value={"/ETakeMoney","/ATakeMoney"})
	@ResponseBody
	public Map<String,Object> teamTakeMoney(HttpServletRequest request,HttpSession session){
		try {
			String requestUri = request.getRequestURI().replaceAll("/+", "/");
			Map<String,Object> object = super.getRequestParamters(request);
			EnterpriseEmployee __ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			object.put("employeecode", __ee.getEmployeecode());
			if(requestUri.endsWith("/Pandect/ETakeMoney")
					&&__ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.员工.value)){
				object.put("enterprisereport", true);
			}else{
				object.put("enterprisereport", false);
			}
			BigDecimal savemoney = pandectService.usp_takemoney(object);
			return super.packJSON(Constant.BooleanByte.YES, savemoney);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	
	/**
	 * 总计-团队取款
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value={"/EBalance","/ABalance"})
	@ResponseBody
	public Map<String,Object> teamBalance(HttpServletRequest request,HttpSession session){
		try {
			String requestUri = request.getRequestURI().replaceAll("/+", "/");
			Map<String,Object> object = super.getRequestParamters(request);
			EnterpriseEmployee __ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			object.put("employeecode", __ee.getEmployeecode());
			if(requestUri.endsWith("/Pandect/EBalance")
					&&__ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.员工.value)){
				object.put("enterprisereport", true);
			}else{
				object.put("enterprisereport", false);
			}
			BigDecimal savemoney = pandectService.usp_balance(object);
			return super.packJSON(Constant.BooleanByte.YES, savemoney);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	

	/**
	 * 排行榜-总计-团队贡献
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value={"/EPerformance","/APerformance"})
	@ResponseBody
	public Map<String,Object> teamPerformance(HttpServletRequest request,HttpSession session){
		try {
			String requestUri = request.getRequestURI().replaceAll("/+", "/");
			Map<String,Object> object = super.getRequestParamters(request);
			EnterpriseEmployee __ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			object.put("employeecode", __ee.getEmployeecode());
			if(requestUri.endsWith("/Pandect/EPerformance")
					&&__ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.员工.value)){
				object.put("enterprisereport", true);
			}else{
				object.put("enterprisereport", false);
			}
			List<Map<String,Object>> __perList = pandectService.usp_performance(object);
			
			if(__perList!=null && __perList.size()>0){
				BigDecimal __totalmoney = new BigDecimal(0);
				BigDecimal __othernetmoney = new BigDecimal(0);
				int j = 0;
				Iterator<Map<String, Object>> __perIterator = __perList.iterator();
				while(__perIterator.hasNext()){
					j++;
					Map<String,Object> __asg = __perIterator.next();
					BigDecimal __netmoney = new BigDecimal(String.valueOf(__asg.get("netmoney")));
					__totalmoney = __totalmoney.add(__netmoney);
					if(j>10){
						__othernetmoney.add(__netmoney);
						__perIterator.remove();
					}
				}
				
				if(__othernetmoney.compareTo(new BigDecimal(0))==1){
					Map<String,Object> __otherpf = new HashMap<String, Object>();
					__otherpf.put("loginaccount", "其他");
					__otherpf.put("netmoney", __othernetmoney);
					__perList.add(__otherpf);
				}
				
				boolean __isnoperformance = __totalmoney.compareTo(new BigDecimal(0))==1;
				Object[] __data = new Object[__perList.size()];
				
				Map<String, String> result = new HashMap<String, String>();
				String title = "";
				String data = "";
				for (int i = 0;i<__perList.size();i++) {
					Map<String,Object> __s = __perList.get(i);
					__data[i] = new Object[]{String.valueOf(__s.get("loginaccount")),__isnoperformance?__s.get("netmoney"):0.00}; 
					title += ""+String.valueOf(__s.get("loginaccount"))+",";
					data += (__isnoperformance?__s.get("netmoney"):0.01)+",";
					
					
					if(Double.valueOf(__s.get("netmoney").toString()) < 0) {
						
//						负数标红
//						y : 20.1,attrs : {fill : '#ff0000'} 
						JSONObject jsonObject1 = new JSONObject();
						jsonObject1.put("fill", "#ff0000");
						JSONObject jsonObject2 = new JSONObject();
						jsonObject2.put("y", __s.get("netmoney"));
						jsonObject2.put("attrs", jsonObject1);
						
						data += (jsonObject2.toString())+",";
					} else {
						data += (__isnoperformance?__s.get("netmoney"):0.01)+",";
					}

				}
				result.put("title", title.substring(0,title.length()-1));
				result.put("data", data.substring(0,data.length()-1));
				
				return super.packJSON(Constant.BooleanByte.YES, result); 
			}else{
				return super.packJSON(Constant.BooleanByte.NO, "无团队贡献");
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	/**
	 * 列表-总计-品牌贡献
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value={"/EBrandPerformance","/ABrandPerformance"})
	@ResponseBody
	public Map<String,Object> teamBrandPerformance(HttpServletRequest request,HttpSession session){
		try {
			String requestUri = request.getRequestURI().replaceAll("/+", "/");
			Map<String,Object> object = super.getRequestParamters(request);
			EnterpriseEmployee __ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			object.put("employeecode", __ee.getEmployeecode());
			object.put("enterprisecode", __ee.getEnterprisecode());
			if(requestUri.endsWith("/Pandect/EBrandPerformance")
					&&__ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.员工.value)){
				object.put("enterprisereport", true);
			}else{
				object.put("enterprisereport", false);
			}
			List<Map<String,Object>> __perList = pandectService.usp_performanceForBrand(object);
			
			BigDecimal __othernetmoney = new BigDecimal(0);
			BigDecimal __totalmoney = new BigDecimal(0);
			int j = 0;
			Iterator<Map<String, Object>> __perIterator = __perList.iterator();
			while(__perIterator.hasNext()){
				j++;
				Map<String,Object> __asg = __perIterator.next();
				__totalmoney = __totalmoney.add(new BigDecimal(String.valueOf(__asg.get("netmoney"))));
				if(j>10){
					__othernetmoney.add(new BigDecimal(String.valueOf(__asg.get("netmoney"))));
					__perIterator.remove();
				}
			}
			if(__othernetmoney.compareTo(new BigDecimal(0))==1){
				Map<String,Object> __otherpf = new HashMap<String, Object>();
				__otherpf.put("brandname", "其他");
				__otherpf.put("brandcode", "-1");
				__otherpf.put("netmoney", __othernetmoney);
				__perList.add(__otherpf);
			}
			if(__perList.size()>0){
				Object[] __data = new Object[__perList.size()];
				boolean __isnoperformance = __totalmoney.compareTo(new BigDecimal(0))==1;
				
				Map<String, String> result = new HashMap<String, String>();
				String title = "";
				String data = "";
				for (int i = 0;i<__perList.size();i++) {
					Map<String,Object> __s = __perList.get(i);
					
					title += ""+String.valueOf(__s.get("brandname"))+",";
					if(Double.valueOf(__s.get("netmoney").toString()) < 0) {
						
//						负数标红
//						y : 20.1,attrs : {fill : '#ff0000'} 
						JSONObject jsonObject1 = new JSONObject();
						jsonObject1.put("fill", "#ff0000");
						JSONObject jsonObject2 = new JSONObject();
						jsonObject2.put("y", __s.get("netmoney"));
						jsonObject2.put("attrs", jsonObject1);
						
						data += (jsonObject2.toString())+",";
					} else {
						data += (__isnoperformance?__s.get("netmoney"):0.01)+",";
					}
					
				}
				result.put("title", title.substring(0,title.length()-1));
				result.put("data", data.substring(0,data.length()-1));
				
				return super.packJSON(Constant.BooleanByte.YES, result);
			}else{
				return super.packJSON(Constant.BooleanByte.NO, "无品牌贡献");
			}
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	
	/**
	 * 列表-总计-用户活跃度
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value={"/EUseractivity","/AUseractivity"})
	@ResponseBody
	public Map<String,Object> teamUseractivity(HttpServletRequest request,HttpSession session){
		try {
			String requestUri = request.getRequestURI().replaceAll("/+", "/");
			Map<String,Object> object = super.getRequestParamters(request);
			EnterpriseEmployee __ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			object.put("employeecode", __ee.getEmployeecode());
			if(requestUri.endsWith("/Pandect/EUseractivity")
					&&__ee.getEmployeetypecode().equals(EnterpriseEmployeeType.Type.员工.value)){
				object.put("enterprisereport", true);
			}else{
				object.put("enterprisereport", false);
			}
			List<Map<String,Object>> perList = pandectService.usp_useractivity(object);
			Map<String,Object> __data = new HashMap<String, Object>();
			for (Map<String, Object> __map : perList) {
				__data.put(String.valueOf(__map.get("activearea")), __map.get("ucount"));
			}
			List<String> __areas = Arrays.asList(new String[]{"0","3","7","15","30","90"});
			List<Integer> __activity = new ArrayList<Integer>();
			for (String __s : __areas) {
				String __ucount = String.valueOf(__data.get(__s));
				if(__ucount==null || __ucount.equals("null")){
					__ucount = "0";
				}
				__activity.add(Integer.parseInt(__ucount));
			}
			return super.packJSON(Constant.BooleanByte.YES, __activity);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}

	/**
	 * 冲正统计
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/EIntegralPlus", "/AIntegralPlus" })
	@ResponseBody
	public Map<String, Object> integralPlus(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = super.getRequestParamters(request);
			if (object.get("begintime") != null) {
				object.put("moneyChangeDate_begin", object.get("begintime"));
				object.remove("begintime");
			}
			if (object.get("endtime") != null) {
				object.put("moneyChangeDate_end", object.get("endtime"));
				object.remove("endtime");
			}
			EnterpriseEmployee __ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			object.put("employeecode", __ee.getEmployeecode());
			object.put("enterprisecode", __ee.getEnterprisecode());
			// 数据限制
			super.dataLimits(__ee, object, session);
			// 清除多余条件
			object.remove("employeecode");
			object.remove("enterprisecode");

			object.put("moneychangetypecode", Enum_moneychangetype.冲正.value);
			Map<String, Object> result = employeeMoneyChangeService.findAccountChangeCount(object);
			
			BigDecimal amountMoney = new BigDecimal(0);
			if (result.get("moneychangeamount") != null) {
			amountMoney = StringUtils.isNotBlank(result.get("moneychangeamount").toString())
					? new BigDecimal(result.get("moneychangeamount").toString()) : new BigDecimal(0);
			}
			return super.packJSON(Constant.BooleanByte.YES, amountMoney);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}

	/**
	 * 冲负统计
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/EIntegralMinus", "/AIntegralMinus" })
	@ResponseBody
	public Map<String, Object> integralMinus(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = super.getRequestParamters(request);
			if (object.get("begintime") != null) {
				object.put("moneyChangeDate_begin", object.get("begintime"));
				object.remove("begintime");
			}
			if (object.get("endtime") != null) {
				object.put("moneyChangeDate_end", object.get("endtime"));
				object.remove("endtime");
			}
			EnterpriseEmployee __ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			object.put("employeecode", __ee.getEmployeecode());
			object.put("enterprisecode", __ee.getEnterprisecode());
			// 数据限制
			super.dataLimits(__ee, object, session);
			// 清除多余条件
			object.remove("employeecode");
			object.remove("enterprisecode");

			object.put("moneychangetypecode", Enum_moneychangetype.冲负.value);
			Map<String, Object> result = employeeMoneyChangeService.findAccountChangeCount(object);
			
			BigDecimal amountMoney = new BigDecimal(0);
			if (result.get("moneychangeamount") != null) {
			amountMoney = StringUtils.isNotBlank(result.get("moneychangeamount").toString())
					? new BigDecimal(result.get("moneychangeamount").toString()) : new BigDecimal(0);
			}
					
			return super.packJSON(Constant.BooleanByte.YES, amountMoney);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}

	/**
	 * 优惠额统计
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/EDiscountAmount", "/ADiscountAmount" })
	@ResponseBody
	public Map<String, Object> discountAmount(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = super.getRequestParamters(request);
			if (object.get("begintime") != null) {
				object.put("moneyChangeDate_begin", object.get("begintime"));
				object.remove("begintime");
			}
			if (object.get("endtime") != null) {
				object.put("moneyChangeDate_end", object.get("endtime"));
				object.remove("endtime");
			}
			EnterpriseEmployee __ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			object.put("employeecode", __ee.getEmployeecode());
			object.put("enterprisecode", __ee.getEnterprisecode());
			// 数据限制
			super.dataLimits(__ee, object, session);
			// 清除多余条件
			object.remove("employeecode");
			object.remove("enterprisecode");
			
			object.put("moneychangetypecode", Enum_moneychangetype.优惠活动.value);
			Map<String, Object> result = employeeMoneyChangeService.findAccountChangeCount(object);
			
			BigDecimal amountMoney = new BigDecimal(0);
			if (result.get("moneychangeamount") != null) {
			amountMoney = StringUtils.isNotBlank(result.get("moneychangeamount").toString())
					? new BigDecimal(result.get("moneychangeamount").toString()) : new BigDecimal(0);
			}
					
			return super.packJSON(Constant.BooleanByte.YES, amountMoney);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}

	/**
	 * 洗码额统计
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = { "/EWashingAmount", "/AWashingAmount" })
	@ResponseBody
	public Map<String, Object> washingAmount(HttpServletRequest request, HttpSession session) {
		try {
			Map<String, Object> object = super.getRequestParamters(request);
			if (object.get("begintime") != null) {
				object.put("moneyChangeDate_begin", object.get("begintime"));
				object.remove("begintime");
			}
			if (object.get("endtime") != null) {
				object.put("moneyChangeDate_end", object.get("endtime"));
				object.remove("endtime");
			}
			EnterpriseEmployee __ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			object.put("employeecode", __ee.getEmployeecode());
			object.put("enterprisecode", __ee.getEnterprisecode());
			// 数据限制
			super.dataLimits(__ee, object, session);
			// 清除多余条件
			object.remove("employeecode");
			object.remove("enterprisecode");
			
			// 结算金额
			BigDecimal amountMoney = new BigDecimal(0);
			
			// 获取日结金额
			object.put("moneychangetypecode", Enum_moneychangetype.洗码日结.value);
			Map<String, Object> result = employeeMoneyChangeService.findAccountChangeCount(object);
			
			if (result.get("moneychangeamount") != null) {
			amountMoney = StringUtils.isNotBlank(result.get("moneychangeamount").toString())
					? new BigDecimal(result.get("moneychangeamount").toString()) : new BigDecimal(0);
			}
			// 获取周结金额
			object.put("moneychangetypecode", Enum_moneychangetype.洗码周结.value);
			result = employeeMoneyChangeService.findAccountChangeCount(object);
			if (result.get("moneychangeamount") != null) {
				// 加上日结金额
				amountMoney.add(StringUtils.isNotBlank(result.get("moneychangeamount").toString())
						? new BigDecimal(result.get("moneychangeamount").toString()) : new BigDecimal(0));
			}
			
			return super.packJSON(Constant.BooleanByte.YES, amountMoney);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(StringUtils.isNotBlank("null"));
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}

}