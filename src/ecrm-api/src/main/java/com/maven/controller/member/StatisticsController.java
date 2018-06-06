package com.maven.controller.member;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.StatisticsService;

@Controller
@RequestMapping("/Statistics")
public class StatisticsController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			StatisticsController.class.getName(), OutputManager.LOG_USER_STATISTICS);
	
	@Autowired
	private StatisticsService statisticsService;
	
	@RequestMapping("/ERegisterSave")
	@ResponseBody
	public String ERegisterOrSave(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			String enterprisecode = String.valueOf(object.get("enterprisecode"));
			Map<String,String> staticstic = statisticsService.call_ERegisterAndSave(enterprisecode);
			return Enum_MSG.成功.message(staticstic);
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
	
	

	
	
	
	
	
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}

}
