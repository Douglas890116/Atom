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
import com.maven.entity.BrandDomain;
import com.maven.entity.EnterpriseBannerInfo;
import com.maven.entity.EnterpriseBrandContact;
import com.maven.entity.EnterpriseBrandNotic;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseThirdpartyPayment;
import com.maven.entity.PlatformApiOutput;
import com.maven.exception.ArgumentValidationException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BrandDomainService;
import com.maven.service.EnterpriseBannerInfoService;
import com.maven.service.EnterpriseBrandContactService;
import com.maven.service.EnterpriseBrandNoticService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseThirdpartyPaymentService;
import com.maven.service.PlatformApiOutputService;
import com.maven.util.AttrCheckout;
import com.maven.util.BeanToMapUtil;

/**
 * 这是应用级别的接口。
 * 给Java或php实现端定时调用，以实现数据同步，得到缓存的作用。避免每个会员都调用所需要的数据
 * 
 * 凡是涉及企业的机密信息的，根据原来提供的接口，根据需要采集
 * 凡是不涉及机密信息的，每次批量采集
 * @author Administrator
 *
 */
@RequestMapping("/task")
@Controller
public class SynchronizeController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			SynchronizeController.class.getName(), OutputManager.LOG_USER_NOTIC);
	
	@Autowired
	private EnterpriseBrandNoticService enterpriseBrandNoticService;
	@Autowired
	private BrandDomainService brandDomainService;
	@Autowired
	private EnterpriseBrandContactService enterpriseBrandContactService;
	@Autowired
	private EnterpriseBannerInfoService enterpriseBannerInfoService;
	@Autowired
	private PlatformApiOutputService platformApiOutputService;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	
	
	private Map<String, String> __mapAgentType = new HashMap<String, String>();
	/**
	 * 返回所有域名信息
	 * 
	 * enterprisecode=企业编码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/findDomainAll",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String findDomainAll(HttpServletRequest request){
	   try {
		   EnterpriseEmployee ee = null;
		   //只查询有效的
		   Map<String,Object> params = super.getRequestParamters(request);
		   params.put("datastatus", "1");
		   List<BrandDomain> list = brandDomainService.selectAll(params);
		   for (BrandDomain brandDomain : list) {
			   
			   String employeecode = brandDomain.getEmployeecode();
			   if(__mapAgentType.containsKey(employeecode)) {
				   brandDomain.setAgenttype(__mapAgentType.get(employeecode));
			   } else {
				   ee = enterpriseEmployeeService.takeEmployeeByCode(employeecode);
				   __mapAgentType.put(employeecode, ee.getEmployeetypecode());//存储起来
				   
				   brandDomain.setAgenttype(__mapAgentType.get(employeecode));
			   }
		   }
		   
		   return Enum_MSG.成功.message(list);
	   }catch(ArgumentValidationException e){
		   log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	/**
	 * 获取所有公告
	 * @return
	 */
	@RequestMapping(value="/findNoticAll",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String notic(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			
			object.clear();
			object.put("datastatus", "1");
			List<EnterpriseBrandNotic> list = enterpriseBrandNoticService.takeNotic(object);
			
			return Enum_MSG.成功.message(list);
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	/**
	 * 获取所有品牌的联系方式
	 * @return
	 */
	@RequestMapping(value="/findContactAll",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String findContactAll(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			
			object.clear();
			object.put("datastatus", "1");
			List<EnterpriseBrandContact> list = enterpriseBrandContactService.queryBrandContact(object);
			
			return Enum_MSG.成功.message(list);
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	/**
	 * 获取所有banner数据
	 * @return
	 */
	@RequestMapping(value="/findBannerAll",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String findBannerAll(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			
			object.clear();
			object.put("datastatus", "1");
			List<EnterpriseBannerInfo> list = enterpriseBannerInfoService.selectBetRecord(object);
			
			return Enum_MSG.成功.message(list);
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	/**
	 * 获取所有企业的通信秘钥数据
	 * @return
	 */
	@RequestMapping(value="/findPlatformAll",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String findPlatformAll(HttpServletRequest request){
		try {
			Map<String,Object> object = super.apiDecode(super.getRequestParamters(request));
			
			object.clear();
			object.put("datastatus", "1");
			List<PlatformApiOutput> list = platformApiOutputService.selectAll(object);
			
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
