package com.maven.controller.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.cache.SystemCache;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.entity.BrandDomain;
import com.maven.entity.BrandDomain.Enum_copyright;
import com.maven.entity.BrandDomain.Enum_domaintype;
import com.maven.entity.BrandDomain.Enum_linkstatus;
import com.maven.entity.EnterpriseBrandContact;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.EnterpriseOperatingBrandPay;
import com.maven.entity.PlatformApiOutput;
import com.maven.entity.TakeDepositRecord;
import com.maven.exception.ArgumentValidationException;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BrandDomainService;
import com.maven.service.EnterpriseBrandContactService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseOperatingBrandPayService;
import com.maven.service.EnterpriseOperatingBrandService;
import com.maven.util.AttrCheckout;
import com.maven.util.BeanToMapUtil;
import com.maven.util.Encrypt;
import com.maven.util.ExpiryMap;
import com.maven.util.RegularCheck;

@RequestMapping("/Domain")
@Controller
public class OpareteBrandController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			OpareteBrandController.class.getName(), OutputManager.LOG_USER_OPARETEBRAND);
	
	@Autowired
	private BrandDomainService brandDomainService;
	
	@Autowired
	private EnterpriseOperatingBrandService enterpriseOperatingBrandService;
	
	@Autowired
	private EnterpriseBrandContactService enterpriseBrandContactService;
	@Autowired
	private EnterpriseOperatingBrandPayService enterpriseOperatingBrandPayService;
	@Autowired
	private EnterpriseEmployeeService enterpriseEmployeeService;
	
	
	/**
	 * ExpiryMap<域名，域名信息>
	 */
	private static ExpiryMap<String, Map<String,Object>> domainConfigMap = new ExpiryMap<String, Map<String,Object>>();
	/**
	 * 企业KEY动态交换
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/enterpriseInfo",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String enterpriseInfo(HttpServletRequest request){
		try{
			Map<String,Object> object = super.fixedCommunicationDecode(SystemConstant.getProperties(Constant.WEB_TEMPLATE_MD5Key),
					SystemConstant.getProperties(Constant.WEB_TEMPLATE_AESKey),super.getRequestParamters(request));
			AttrCheckout.checkout(object, false, new String[]{"enterprisecode"});
			String enterprisecode = String.valueOf(object.get("enterprisecode"));
			PlatformApiOutput key = SystemCache.getInstance().getPlatformApiOutput(enterprisecode);
			if(key!=null){
				String s = key.getApikey2()+"&"+key.getApikey1();
				Map<String,String> returnObject = new HashMap<String, String>();
				returnObject.put("params", Encrypt.AESEncrypt(s, SystemConstant.getProperties(Constant.WEB_TEMPLATE_AESKey)));
				returnObject.put("signature", Encrypt.MD5(s+SystemConstant.getProperties(Constant.WEB_TEMPLATE_MD5Key)));
				return Enum_MSG.成功.message(returnObject);
			}else{
				return Enum_MSG.参数错误.message(Enum_MSG.企业API许可为空.desc);
			}
		}catch(ArgumentValidationException e){
			log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	/**
	 * 域名对应的企业与品牌信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/TakeDomainConfig",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String TakeDomainConfig(HttpServletRequest request){
		try {
			// 取得基础参数
			Map<String,Object> object = super.getRequestParamters(request);
			String domain = String.valueOf(object.get("domain"));
			if(domain == null || domain.equals("") || domain.equals("null") ) {
				return Enum_MSG.系统错误.message(null);
			}
			domain = RegularCheck.takeDomain(domain);
			if(!RegularCheck.isDomain(domain)) {
				return Enum_MSG.系统错误.message(null);
			}
			
			Map<String,Object> packObject = new HashMap<String, Object>();
			
			// 验证域名是否存在
			Object obj = domainConfigMap.isInvalid(domain);
			
			if (obj != null && !obj.equals(-1)) {
				packObject = (Map<String, Object>) obj;
			} else {
				BrandDomain augold =  brandDomainService.queryByDomainLink(domain);
				if(augold==null||augold.getCopyright().equals(Enum_copyright.公共.value)
						||augold.getDomaintype()==Enum_domaintype.代理注册链接.value.byteValue()
						||StringUtils.isBlank(augold.getBrandcode())) {
					return Enum_MSG.逻辑事物异常.message(Enum_MSG.域名未注册.desc); 
				}
				if(augold.getLinkstatus().equals(Enum_linkstatus.禁用.value)){
					return Enum_MSG.逻辑事物异常.message(Enum_MSG.域名已禁用.desc);
				}
				List<String> links = new ArrayList<String>();
				links.add(augold.getDomainlink());
				EnterpriseOperatingBrand brand = enterpriseOperatingBrandService.takeBrandByPrimaryKey(augold.getBrandcode());
				BrandDomain __agentDomain = brandDomainService.selectFirst(new BrandDomain(augold.getEmployeecode(),Enum_linkstatus.启用,
						new Byte[]{Enum_domaintype.代理站点.value.byteValue(),Enum_domaintype.企业域名_代理站点.value.byteValue()}));
				
				packObject.put("enterprisecode", brand.getEnterprisecode());
				packObject.put("brandcode", brand.getBrandcode());
				packObject.put("brandname", brand.getBrandname());
				packObject.put("sign", brand.getWebtemplate().getSign());
				packObject.put("templatetype", brand.getWebtemplate().getTemplatetype());
				packObject.put("logopath", brand.getLogopath());
				packObject.put("links", links);
				packObject.put("agentsite", __agentDomain==null?"":__agentDomain.getDomainlink());
				//查域名所属用户
				EnterpriseEmployee __ee = enterpriseEmployeeService.takeEmployeeByCode(augold.getEmployeecode());
				if(__ee != null) {
					packObject.put("domain_employeecode", __ee.getEmployeecode());
					packObject.put("domain_loginaccount", __ee.getLoginaccount());
					packObject.put("domain_displayalias", __ee.getDisplayalias());
				}
				
				
				//返回此品牌的支付域名
				TakeDepositRecord takeDepositRecord = new TakeDepositRecord();
				takeDepositRecord.setBrandcode(brand.getBrandcode());
				takeDepositRecord.setEnterprisecode(brand.getEnterprisecode());
				String paycallbackurl = getRootPath(request, takeDepositRecord);
				packObject.put("paycallbackurl", paycallbackurl);
				
				domainConfigMap.put(domain, packObject, 5 * 60 * 1000);
			}
			
			return Enum_MSG.成功.message(packObject);
	   }catch(ArgumentValidationException e){
		   log.Error(e.getMessage(), e);
			return Enum_MSG.参数错误.message(e.getMessage());
		}catch (Exception e) {
			log.Error(e.getMessage(), e);
			return Enum_MSG.系统错误.message(null);
		}
	}
	
	/**
	 * 获取项目根目录
	 * 
	 * @return
	 */
	private String getRootPath(HttpServletRequest request, TakeDepositRecord takeDepositRecord) {
		String apiurl = getBasePath();//默认
		try {
			
			//登录时返回该品牌下（如有品牌）或该企业配置的自定义api接口域名
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("enterprisecode", takeDepositRecord.getEnterprisecode());
			params.put("brandcode", takeDepositRecord.getBrandcode());
			params.put("datastatus", EnterpriseOperatingBrandPay.Enum_Datastatus.有效.value);
			List<EnterpriseOperatingBrandPay> list = enterpriseOperatingBrandPayService.selectAll(params);
			
			if(list != null && list.size() > 0) {
				apiurl = list.get(0).getPaycallbackurl();
			}
			log.Info("支付域名："+takeDepositRecord.getEnterprisecode() + " " + apiurl);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!apiurl.endsWith("/")) {
			apiurl = apiurl+"/" ;
		}
		return apiurl;
	}
	
	/**
	 * 域名信息定时同步接口
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/TakeAllDomainConfig",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String augold(HttpServletRequest request){
	   try {
			List<EnterpriseOperatingBrand>  brand = enterpriseOperatingBrandService.select(null);
			Map<String, List<String>> categoryAugold =  brandDomainService.takeAllExpandDomain();
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			
			
			
			
			for (EnterpriseOperatingBrand e : brand) {
				List<String> domainConfig = categoryAugold.get(e.getBrandcode());
				if(domainConfig!=null && domainConfig.size()!=0){
					Map<String,Object> packObject = new HashMap<String, Object>();
					packObject.put("enterprisecode", e.getEnterprisecode());
					packObject.put("brandcode", e.getBrandcode());
					packObject.put("brandname", e.getBrandname());
					packObject.put("sign", e.getWebtemplate().getSign());
					packObject.put("templatetype", e.getWebtemplate().getTemplatetype());
					packObject.put("logopath", e.getLogopath());
					packObject.put("links", domainConfig);
					
					//返回此品牌的支付域名
					TakeDepositRecord takeDepositRecord = new TakeDepositRecord();
					takeDepositRecord.setBrandcode(e.getBrandcode());
					takeDepositRecord.setEnterprisecode(e.getEnterprisecode());
					String paycallbackurl = getRootPath(request, takeDepositRecord);
					packObject.put("paycallbackurl", paycallbackurl);
					
					list.add(packObject);
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
	 * 品牌联系方式
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/Contact",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String customerServiceContact(HttpServletRequest request){
		try {
			Map<String,Object> __object = super.apiDecode(super.getRequestParamters(request));
			AttrCheckout.checkout(__object, false, new String[]{"enterprisecode","brandcode"});
			List<EnterpriseBrandContact>  __list = enterpriseBrandContactService.queryBrandContact(__object);
			Map<String,List<Map<String,Object>>> __contact = new HashMap<String, List<Map<String,Object>>>();
			for (EnterpriseBrandContact __ebc : __list) {
				if(__contact.get(__ebc.getContacttype())==null){
					__contact.put(__ebc.getContacttype(),new ArrayList<Map<String,Object>>());
				}
				__contact.get(__ebc.getContacttype()).add(
					AttrCheckout.checkout(BeanToMapUtil.convertBean(__ebc, false), true, new String[]{"content","contenttype","contacttitle"}));
			}
			return Enum_MSG.成功.message(__contact);
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
