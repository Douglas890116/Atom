package com.maven.controller;

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

import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseOperatingBrand;
import com.maven.entity.EnterpriseOperatingBrandPay;
import com.maven.entity.LoginWhiteList;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.payment.th.utils.StringUtils;
import com.maven.service.EnterpriseOperatingBrandPayService;
import com.maven.service.EnterpriseOperatingBrandService;
import com.maven.service.EnterpriseService;
import com.maven.service.LoginWhiteListService;

/**
 * 品牌对应的支付回调管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/EnterpriseOperatingBrandPay")
public class EnterpriseOperatingBrandPayController extends BaseController {
	
	private LoggerManager log = LoggerManager.getLogger(EnterpriseOperatingBrandPayController.class.getName(),OutputManager.LOG_ACTIVITYBETRECORD);
			
	@Autowired
	private EnterpriseOperatingBrandPayService enterpriseOperatingBrandPayService;
	@Autowired
	private EnterpriseOperatingBrandService enterpriseOperatingBrandService;
	@Autowired
	private EnterpriseService enterpriseService;
	
	/**
	 * index
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model ){
		try {
			String _sign = request.getParameter("sign");
			if(_sign == null) {
				_sign = request.getSession().getAttribute("brandpay_sign").toString();
			}
			if (this.decodeSign(_sign, request.getSession().getId())) {
				String enterprisecode = _sign.split("_")[1];
				model.addAttribute("sign", _sign);
				model.addAttribute("enterprise", enterpriseService.selectByPrimaryKey(enterprisecode));
				request.getSession().setAttribute("brandpay_sign", _sign);
				return "/enterprise/brandpay_list";
			}else {
				return Constant.PAGE_DECODEREFUSE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
		
		
		
	}
	
	/**
	 * 转到新增页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model ,HttpSession session,HttpServletRequest request){
		try {
			String _sign = request.getParameter("sign");
			if (this.decodeSign(_sign, request.getSession().getId())) {
				String enterprisecode = _sign.split("_")[1];
				
				List<EnterpriseOperatingBrand> list = enterpriseOperatingBrandService.getEnterpriseBrand(enterprisecode);
				model.addAttribute("listEnterpriseOperatingBrand", list);
				model.addAttribute("enterprise", enterpriseService.selectByPrimaryKey(enterprisecode));
				return "/enterprise/brandpay_add";
			}else {
				return Constant.PAGE_DECODEREFUSE;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	/**
	 * 转到编辑页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/update")
	public String update(Model model ,HttpSession session,HttpServletRequest request){
		try {
			String _sign = request.getParameter("sign");
			if (this.decodeSign(_sign, request.getSession().getId())) {
				String enterprisecode = _sign.split("_")[1];
				
				List<EnterpriseOperatingBrand> list = enterpriseOperatingBrandService.getEnterpriseBrand(enterprisecode);
				model.addAttribute("listEnterpriseOperatingBrand", list);
				model.addAttribute("enterprise", enterpriseService.selectByPrimaryKey(enterprisecode));
				model.addAttribute("enterpriseOperatingBrandPay", enterpriseOperatingBrandPayService.selectByPrimaryKey(request.getParameter("lsh")));
				return "/enterprise/brandpay_edit";
			}else {
				return Constant.PAGE_DECODEREFUSE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	/**
	 * list页面请求数据
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/data")
	@ResponseBody
	public Map<String, Object> data(HttpServletRequest request, HttpSession session){
		try {			
			Map<String, Object> parameter = getRequestParamters(request);
			String _sign = request.getParameter("sign");
			String enterprisecode = _sign.split("_")[1];
			
			List<EnterpriseOperatingBrand> listEnterpriseOperatingBrand = enterpriseOperatingBrandService.getEnterpriseBrand(enterprisecode);
			Map<String, EnterpriseOperatingBrand> mapEnterpriseOperatingBrand = new HashMap<String, EnterpriseOperatingBrand>();
			for (EnterpriseOperatingBrand enterpriseOperatingBrand : listEnterpriseOperatingBrand) {
				mapEnterpriseOperatingBrand.put(enterpriseOperatingBrand.getBrandcode(), enterpriseOperatingBrand);
			}
			
			//查数据
			parameter.put("enterprisecode", enterprisecode);
			parameter.put("datastatus", EnterpriseOperatingBrandPay.Enum_Datastatus.有效.value);
			List<EnterpriseOperatingBrandPay> list = enterpriseOperatingBrandPayService.selectAll(parameter);
			for (EnterpriseOperatingBrandPay enterpriseOperatingBrandPay : list) {
				if(enterpriseOperatingBrandPay.getBrandcode() == null) {
					enterpriseOperatingBrandPay.setBrandname(" 无品牌 ");
				} else {
					enterpriseOperatingBrandPay.setBrandname( mapEnterpriseOperatingBrand.get(enterpriseOperatingBrandPay.getBrandcode()).getBrandname() );
				}
			}
			Map<String,String> s = new HashMap<String, String>();
			s.put("lsh", "sign");
			super.encryptSignTarget(list,session.getId(),s);
			
			//查总数
			int rowCount  = enterpriseOperatingBrandPayService.selectAllCount(parameter);
			return super.formatPagaMap(list, rowCount);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * save/update
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/save"})
	@ResponseBody
	public Map<String,Object> save(HttpSession session,HttpServletRequest request){
		try {
//			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			String _sign = request.getSession().getAttribute("brandpay_sign").toString();
			String enterprisecode = _sign.split("_")[1];
			
			// 如果不为空则需要修改
			String lsh = request.getParameter("lsh");
			String adminurl = request.getParameter("adminurl");
			String paycallbackurl = request.getParameter("paycallbackurl");
			String brandcode = request.getParameter("brandcode");
			if( StringUtils.isNullOrEmpty(brandcode)) {
				brandcode = null;
			}
			if(!paycallbackurl.toLowerCase().startsWith("http://") && !paycallbackurl.toLowerCase().startsWith("https://")) {
				return super.packJSON(Constant.BooleanByte.NO, "必须以http或https开头");
			}
			if(!adminurl.toLowerCase().startsWith("http://") && !adminurl.toLowerCase().startsWith("https://")) {
				return super.packJSON(Constant.BooleanByte.NO, "必须以http或https开头");
			}
			
			EnterpriseOperatingBrandPay item = null;
			if( !StringUtils.isNullOrEmpty(lsh)) {
				item = enterpriseOperatingBrandPayService.selectByPrimaryKey(lsh);
				item.setPaycallbackurl(paycallbackurl);
				item.setAdminurl(adminurl);
				enterpriseOperatingBrandPayService.updateEnterpriseOperatingBrandPay(item);
			} else {
				item = new EnterpriseOperatingBrandPay();
				item.setBrandcode(brandcode);
				item.setEnterprisecode(enterprisecode);
				item.setDatastatus(EnterpriseOperatingBrandPay.Enum_Datastatus.有效.value);
				List<EnterpriseOperatingBrandPay> list = enterpriseOperatingBrandPayService.select(item);
				if(list != null && list.size() > 0) {
					return super.packJSON(Constant.BooleanByte.NO, "该品牌下已绑定了一个域名，请先删除！");
				}
				
				item.setPaycallbackurl(paycallbackurl);
				item.setAdminurl(adminurl);
				enterpriseOperatingBrandPayService.saveEnterpriseOperatingBrandPay(item);
			}
			
			return super.packJSON(Constant.BooleanByte.YES, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	/**
	 * 主键删除
	 * @param request
	 */
	@RequestMapping("/deleteIp")
	@ResponseBody
	public Map<String,Object> deleteIp(HttpServletRequest request,HttpSession session){
		try {
			
			String temp = request.getParameter("sign");
			String[] array = temp.split(",");
			boolean mark = this.decodeSign(array, session.getId());
			if(mark){
				for (int i = 0; i < array.length; i++) {
					array[i]=array[i].split("_")[1];
				}
				enterpriseOperatingBrandPayService.deleteSelect(array);
				return super.packJSON(Constant.BooleanByte.YES, "删除成功");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, "删除失败");
			}
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	/**
	 * 删除选中的一条或者多条数据
	 * @param request
	 */
	@RequestMapping("/deleteSelectIp")
	@ResponseBody
	public Map<String,Object> deleteSelectIp(HttpServletRequest request,HttpSession session){
		try {
			String temp = request.getParameter("sign");
			String[] array = temp.split(",");
			boolean mark = this.decodeSign(array, session.getId());
			if(mark){
				for (int i = 0; i < array.length; i++) {
					array[i] = array[i].split("_")[1];
				}
				enterpriseOperatingBrandPayService.deleteSelect(array);
				return super.packJSON(Constant.BooleanByte.YES, "已删除");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
			
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	
	

	@Override
	public LoggerManager getLogger() {
		return log;
	}
	
}
