package com.maven.controller.member;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.entity.EnterpriseBrandNotic;
import com.maven.exception.ArgumentValidationException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.EnterpriseBrandNoticService;
import com.maven.util.AttrCheckout;
import com.maven.util.BeanToMapUtil;

@RequestMapping("/Notic")
@Controller
public class NoticController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			NoticController.class.getName(), OutputManager.LOG_USER_NOTIC);
	
	@Autowired
	private EnterpriseBrandNoticService enterpriseBrandNoticService;

	/**
	 * 获取公告
	 * @return
	 */
	@RequestMapping(value="/Notic",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String notic(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[]{"brandcode"},new String[]{"start","limit"});
			EnterpriseBrandNotic notic = BeanToMapUtil.convertMap(object, EnterpriseBrandNotic.class);
			List<EnterpriseBrandNotic> list = enterpriseBrandNoticService.takeUserNotic(notic);
			return Enum_MSG.成功.message(list);
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
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
