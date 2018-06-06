package com.maven.controller.activity.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.controller.member.MessageController;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.EnterpriseBrandActivityService;
import com.maven.util.AttrCheckout;

@RequestMapping("/ActivityInfo")
@Controller
public class ActivityInfoController extends BaseController{

	private static LoggerManager log = LoggerManager.getLogger(
			MessageController.class.getName(), OutputManager.LOG_BRANDACTIVITY);
	@Autowired
	private EnterpriseBrandActivityService enterpriseBrandActivityService;
	
	
	/**
	 * 查询品牌下所有活动接口
	 * @param request,session
	 * @return json 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={"/List"},produces="text/html;charset=UTF-8")
	@ResponseBody
	public String activityList(HttpServletRequest request){
		try {
			Map<String,Object> parames = (Map<String,Object>)request.getAttribute("activityparams");
			AttrCheckout.checkout(parames, false, new String[]{"enterprisebrandcode"});
			List<Map<String, Object>> activity_json = enterpriseBrandActivityService.selectActivityByBrand(parames.get("enterprisebrandcode").toString());
			return Enum_MSG.成功.message(activity_json);
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch(LogicTransactionRollBackException e){
			log.Error(e.getMessage(), e);
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
