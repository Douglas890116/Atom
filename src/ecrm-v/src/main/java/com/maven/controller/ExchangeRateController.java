package com.maven.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.annotation.OperationLog;
import com.maven.cache.SystemCache;
import com.maven.constant.Constant;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.ExchangeRate;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.ExchangeRateService;

@RequestMapping("/exchangerate")
@Controller
public class ExchangeRateController extends BaseController {
	
	private static LoggerManager log = LoggerManager.getLogger(ExchangeRateController.class.getName(), OutputManager.LOG_EXCHANGERATE);
	
	@Autowired
	private ExchangeRateService service;
	
	@RequestMapping("/list")
	public String list() {
		return "/exchangerate/list";
	}
	
	@RequestMapping("/data")
	@ResponseBody
	public Map<String,Object> data(HttpServletRequest request, HttpSession session) {
		try {
			Map<String,Object> object = getRequestParamters(request);
			List<ExchangeRate> list = service.getExchangeRateByCondition(object);
			
			Map<String,String> ss = new HashMap<String, String>();
			ss.put("id", "sign");
			super.encryptSignTarget(list, session.getId(), ss);
			int count = service.getQueryCount(object);
			return formatPagaMap(list, count);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return null;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	@OperationLog(OparetionDescription.EXCHANGE_ADD)
	public Map<String,Object> save(HttpServletRequest request, HttpSession session) {
		EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
		ExchangeRate er = new ExchangeRate();
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			er.setCurrencyname(request.getParameter("currencyname"));
			er.setCurrencycode(request.getParameter("currencycode").toUpperCase());
			er.setExchangerate(Double.parseDouble(request.getParameter("exchangerate")));
			er.setOprationtime(new Date());
			er.setOprationperson(ee.getLoginaccount());
			if (service.saveExchangeRate(er) == 1) {
				map.put("status", "success");
				
				//更新缓存
				Map<String,Object> object = new HashMap<String, Object>();
				List<ExchangeRate> list = service.getExchangeRateByCondition(object);
				Map<String, String> data = new HashMap<String, String>();
				for (ExchangeRate exchangeRate : list) {
				    data.put(exchangeRate.getCurrencycode(), (null!=exchangeRate.getExchangerate())?exchangeRate.getExchangerate().toString():"0");
				}
				SystemCache.getInstance().setExchangeRateAll(data);
				
				return map;
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		map.put("status", "failure");
		return map;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	@OperationLog(OparetionDescription.EXCHANGE_UPDATE)
	public Map<String,Object> update(HttpServletRequest request, HttpSession session) {
		EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
		ExchangeRate er = new ExchangeRate();
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			er.setId(Integer.parseInt(request.getParameter("id")));
			er.setCurrencyname(request.getParameter("currencyname").trim());
			er.setCurrencycode(request.getParameter("currencycode").trim().toUpperCase());
			er.setExchangerate(Double.parseDouble(request.getParameter("exchangerate").trim()));
			er.setOprationtime(new Date());
			er.setOprationperson(ee.getLoginaccount());
			if (service.updateExchange(er) == 1) {
				map.put("status", "success");
				
				//更新缓存
				Map<String,Object> object = new HashMap<String, Object>();
				List<ExchangeRate> list = service.getExchangeRateByCondition(object);
				Map<String, String> data = new HashMap<String, String>();
				for (ExchangeRate exchangeRate : list) {
					data.put(exchangeRate.getCurrencycode(), (null!=exchangeRate.getExchangerate())?exchangeRate.getExchangerate().toString():"0");
				}
				SystemCache.getInstance().setExchangeRateAll(data);
				
				
				return map;
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		map.put("status", "failure");
		return map;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	@OperationLog(OparetionDescription.EXCHANGE_DELETE)
	public Map<String,Object> delete(HttpServletRequest request, HttpSession session) {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String deleteCode = request.getParameter("deleteCode");
			System.out.println(deleteCode);
			if (this.decodeSign(deleteCode, session.getId())
					&& service.deleteExchangeRateById(Integer.parseInt(deleteCode.split("_")[1])) == 1) {
				map.put("status", "success");
				
				
				//更新缓存
				Map<String,Object> object = new HashMap<String, Object>();
				List<ExchangeRate> list = service.getExchangeRateByCondition(object);
				Map<String, String> data = new HashMap<String, String>();
				for (ExchangeRate exchangeRate : list) {
				    data.put(exchangeRate.getCurrencycode(), (null!=exchangeRate.getExchangerate())?exchangeRate.getExchangerate().toString():"0");
				}
				SystemCache.getInstance().setExchangeRateAll(data);
				
				
				return map;
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		map.put("status", "failure");
		return map;
	}
	
	/**
	 * 批量删除功能
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/batchDelete")
	@ResponseBody
	@OperationLog(OparetionDescription.EXCHANGE_DELETE)
	public Map<String,Object> batchDelete(HttpServletRequest request,HttpSession session){
		try {
			String temp = request.getParameter("sign");
			String[] array = temp.split(",");
			if(this.decodeSign(array, session.getId())){
				for (int i = 0; i < array.length; i++) {
					array[i]=array[i].split("_")[1];
				}
				service.deleteExchangeRatesByIds(array);
				
				//更新缓存
				Map<String,Object> object = new HashMap<String, Object>();
				List<ExchangeRate> list = service.getExchangeRateByCondition(object);
				Map<String, String> data = new HashMap<String, String>();
				for (ExchangeRate exchangeRate : list) {
				    data.put(exchangeRate.getCurrencycode(), (null!=exchangeRate.getExchangerate())?exchangeRate.getExchangerate().toString():"0");
				}
				SystemCache.getInstance().setExchangeRateAll(data);
				
				
				return super.packJSON(Constant.BooleanByte.YES, "删除成功");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		}catch (Exception e) {
				e.printStackTrace();
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	@ResponseBody
	@RequestMapping("/getExchangeRate")
	public Map<String,Object> getExchangeRate(HttpServletRequest request ){
		try {
			String currency = request.getParameter("currency");
			double balance = Double.parseDouble(request.getParameter("balance")); //余额
			double deposit = Double.parseDouble(request.getParameter("deposit")); // 存款
			double take = Double.parseDouble(request.getParameter("take")); // 取款
			
			
			ExchangeRate exchangeRate = service.getExchangeRateByCurrencyCode(currency);

			if (exchangeRate != null && exchangeRate.getExchangerate() != null) {
				double rate = exchangeRate.getExchangerate();
				
				BigDecimal newBalance = new BigDecimal(balance).multiply(new BigDecimal(rate)).setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal newDeposit = new BigDecimal(deposit).multiply(new BigDecimal(rate)).setScale(2, BigDecimal.ROUND_HALF_UP);
				BigDecimal newTake = new BigDecimal(take).multiply(new BigDecimal(rate)).setScale(2, BigDecimal.ROUND_HALF_UP);
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("balance", newBalance.toString());
				map.put("deposit", newDeposit.toString());
				map.put("take", newTake.toString());
				
				return packJSON(Constant.BooleanByte.YES, "", map);
			} else {
				return packJSON(Constant.BooleanByte.NO, "未找到"+currency+"汇率");
			}
		} catch (Exception e) {
			log.Error("获取汇率出货", e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}