package com.maven.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.maven.annotation.OperationLog;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseThirdpartyPayment;
import com.maven.entity.EnterpriseThirdpartyPayment.Enum_type;
import com.maven.entity.EnterpriseThirdpartyPaymentAgument;
import com.maven.entity.ThirdpartyPaymentTypeSetting;
import com.maven.entity.UserLogs;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.EnterpriseThirdpartyPaymentAgumentService;
import com.maven.service.EnterpriseThirdpartyPaymentService;
import com.maven.service.ThirdpartyPaymentTypeSettingService;
import com.maven.service.UserLogsService;
@Controller
@RequestMapping("/thirdpartyPayment")
public class EnterpriseThirdpartyPaymentController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(EnterpriseThirdpartyPaymentController.class.getName(), OutputManager.LOG_ENTERPRISE_THIRDPARTY_PAYMENT);
	
	@Autowired
	private EnterpriseThirdpartyPaymentService enterpriseThirdpartyPaymentService;
	@Autowired
	private EnterpriseThirdpartyPaymentAgumentService enterpriseThirdpartyPaymentAgumentService;
	@Autowired
	private ThirdpartyPaymentTypeSettingService thirdpartyPaymentTypeSettingService;
	@Autowired
	private UserLogsService userLogsService;
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
	
	@RequestMapping("/index")
	public String index(){
		return "/thirdpartyPayment/index";
	}
	
	@RequestMapping("/add")
	public String add(){
		return "/thirdpartyPayment/add";
	}
	
	@RequestMapping("/update")
	public String update(HttpServletRequest request,HttpSession session, Model model){
		try {
			//解密标识字段的值
			String enterpriseThirdpartyCode = request.getParameter("enterprisethirdpartycode");
			if(super.decodeSign(enterpriseThirdpartyCode, session.getId())){
				EnterpriseThirdpartyPayment  enterpriseThirdpartyPayment = enterpriseThirdpartyPaymentService.selectByPrimaryKey(enterpriseThirdpartyCode.split("_")[1]);
				model.addAttribute("enterpriseThirdpartyPayment", enterpriseThirdpartyPayment);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "/thirdpartyPayment/update";
	}
	
	/**
	 * 查询
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/data")
	@ResponseBody
	public Map<String,Object> findGameData(HttpServletRequest request,HttpSession session){
		Map<String, Object> map = getRequestParamters(request);
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			
			/*********非超级管理员时只能查询本团队内的数据************/
			if(!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())){
				map.put("enterprisecode", ee.getEnterprisecode());
			}
			
			List<EnterpriseThirdpartyPayment> list = enterpriseThirdpartyPaymentService.findAll(map);
			int count = enterpriseThirdpartyPaymentService.findCountAll(map);
			super.encryptSign(list,session.getId(),"enterprisethirdpartycode");
			return super.formatPagaMap(list, count);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询当前公司是否设置了默认出款卡信息
	 * @param request
	 * @param session
	 */
	@RequestMapping("/queryCompanyWhetherSetDefaultPaymentCard")
	@ResponseBody
	public List<EnterpriseThirdpartyPayment> queryCompanyWhetherSetDefaultPaymentCard(HttpServletRequest request,HttpSession session){
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			map.put("enterprisecode", ee.getEnterprisecode());
			map.put("isdefualttakecard", EnterpriseThirdpartyPayment.Enum_isdefualttakecard.是.value);
			List<EnterpriseThirdpartyPayment> list = enterpriseThirdpartyPaymentService.findAll(map);
			return list;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除支付类型
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/delete")
	@OperationLog(OparetionDescription.THIRDPARTYPAYMENT_DELETE)
	@ResponseBody
	public Map<String,Object> delete(HttpServletRequest request,HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			//编码
			String enterpriseThirdpartyCode = request.getParameter("deleteCode");
			//解密标识字段的值
			if(super.decodeSign(enterpriseThirdpartyCode, session.getId())){
				enterpriseThirdpartyPaymentService.tc_delete(enterpriseThirdpartyCode.split("_")[1]);
				
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.快捷支付业务, "删除企业快捷支付信息:"+enterpriseThirdpartyCode.split("_")[1], ee.getLoginaccount(), null));
				
				map.put("status", "success");
			}else{
				map.put("status", "failure");
				return map;
			}
			return map;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
			map.put("status", "failure");
			return map;
		}
	}
	
	/**
	 * 批量删除支付类型
	 * @param request
	 */
	@RequestMapping("/deleteSelect")
	@OperationLog(OparetionDescription.THIRDPARTYPAYMENT_DELETE)
	@ResponseBody
	public Map<String,Object> deleteSelect(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			String tempArray = request.getParameter("sign");
			String[] array = tempArray.split(",");
			if(super.decodeSign(array, session.getId())){
				for (int i = 0; i < array.length; i++) {
					array[i]=array[i].split("_")[1];
					
					userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
						     UserLogs.Enum_operatype.快捷支付业务, "批量删除企业快捷支付信息:"+array[i], ee.getLoginaccount(), null));
				}
				enterpriseThirdpartyPaymentService.tc_deleteSelect(array);
				return super.packJSON(Constant.BooleanByte.YES, "快捷支付删除成功");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	/**
	 * 启用与禁用方法
	 * @param request
	 * @return
	 */
	@RequestMapping("/enableDisable")
	@OperationLog(OparetionDescription.THIRDPARTYPAYMENT_ENABLE_DISABLE)
	@ResponseBody
	public Map<String,Object> enableDisable(HttpServletRequest request,HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			EnterpriseThirdpartyPayment  enterpriseThirdpartyPayment = new EnterpriseThirdpartyPayment();
			if(super.decodeSign(request.getParameter("id"), session.getId())){
				String type = request.getParameter("type");
				enterpriseThirdpartyPayment.setEnterprisethirdpartycode(request.getParameter("id").split("_")[1]);
				if(Enum_type.PC.value.equals(type)) {
					enterpriseThirdpartyPayment.setStatus(request.getParameter("status"));
				} else {
					enterpriseThirdpartyPayment.setH5status(request.getParameter("status"));
				}
				enterpriseThirdpartyPaymentService.tc_enableDisable(enterpriseThirdpartyPayment);
				
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.快捷支付业务, "启用或禁用企业快捷支付信息:"+enterpriseThirdpartyPayment.getThirdpartypaymenttypecode(), ee.getLoginaccount(), null));
				
				map.put("status", "success");
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("status", "failure");
		return map;
	}
	
	/**
	 * 设置公司的默认出款卡
	 * @param request
	 * @return
	 */
	@RequestMapping("/configDefaultPaymentCard")
	@ResponseBody
	public Map<String,Object> configDefaultPaymentCard(HttpServletRequest request,HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			EnterpriseThirdpartyPayment  enterpriseThirdpartyPayment = new EnterpriseThirdpartyPayment();
			if(super.decodeSign(request.getParameter("enterprisethirdpartycode"), session.getId())){
				enterpriseThirdpartyPayment.setEnterprisethirdpartycode(request.getParameter("enterprisethirdpartycode").split("_")[1]);
				enterpriseThirdpartyPayment.setEnterprisecode(ee.getEnterprisecode());
				enterpriseThirdpartyPaymentService.tc_update(enterpriseThirdpartyPayment);
				
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.快捷支付业务, "设置默认的企业快捷支付信息:"+enterpriseThirdpartyPayment.getThirdpartypaymenttypecode(), ee.getLoginaccount(), null));
				
				map.put("status", "success");
				return map;
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		map.put("status", "failure");
		return map;
	}
	
	/**
	 * 修改账户余额
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveCurrentbalance")
	@ResponseBody
	public Map<String,Object> saveCurrentbalance(HttpServletRequest request,HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			EnterpriseThirdpartyPayment  enterpriseThirdpartyPayment = new EnterpriseThirdpartyPayment();
			if(super.decodeSign(request.getParameter("enterprisethirdpartycode"), session.getId())){
				enterpriseThirdpartyPayment.setEnterprisethirdpartycode(request.getParameter("enterprisethirdpartycode").split("_")[1]);
				enterpriseThirdpartyPayment.setCurrentbalance(new BigDecimal(request.getParameter("currentbalance")));
				enterpriseThirdpartyPaymentService.tc_enableDisable(enterpriseThirdpartyPayment);
				
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.快捷支付业务, "企业快捷支付信息更改余额:"+request.getParameter("currentbalance"), ee.getLoginaccount(), null));
				
				map.put("status", "success");
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("status", "failure");
		return map;
	}
	
	/**
	 * 保存
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String,Object> save(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee __EEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			
			if(request.getParameter("displayname").getBytes("GB2312").length > 10) {
				super.packJSON(Constant.BooleanByte.NO, "长度不能超过10个字符，如是中文，不得超过5个汉字");
			}
			
			EnterpriseThirdpartyPayment  __ETPayment = new EnterpriseThirdpartyPayment();
			//企业编码
			__ETPayment.setEnterprisecode(__EEmployee.getEnterprisecode());
			//支付类型编码
			__ETPayment.setThirdpartypaymenttypecode(request.getParameter("thirdpartypaymenttypecode"));
			//描述
			__ETPayment.setDscription(request.getParameter("dscription"));
			__ETPayment.setDisplayname(request.getParameter("displayname"));
			__ETPayment.setIsbanks(request.getParameter("isbanks").equals("on") ? true : false);
			__ETPayment.setIsweixin(request.getParameter("isweixin").equals("on") ? true : false);
			__ETPayment.setIszhifubao(request.getParameter("iszhifubao").equals("on") ? true : false);
			__ETPayment.setMinmoney(new BigDecimal(request.getParameter("minmoney")));
			__ETPayment.setMaxmoney(new BigDecimal(request.getParameter("maxmoney")));
			if(__ETPayment.getMaxmoney().doubleValue() <= 0) {
				__ETPayment.setMaxmoney(BigDecimal.valueOf(9999999));
			}
			if(request.getParameter("ord") == null || request.getParameter("ord").equals("")) {
				__ETPayment.setOrd("99");
			} else {
				__ETPayment.setOrd(request.getParameter("ord"));
			}
			__ETPayment.setCallbackurl(request.getParameter("callbackurl"));
			
			
			//是否启用PC端支付(默认启用)
			__ETPayment.setStatus(EnterpriseThirdpartyPayment.Enum_status.启用.value);
			//是否启用H5端支付(默认启用)
			__ETPayment.setH5status(EnterpriseThirdpartyPayment.Enum_status.启用.value);
			//数据状态(默认为有效数据)
			__ETPayment.setDatastatus(String.valueOf(Constant.Enum_DataStatus.正常.value));
			
			//封装第三方支付参数
			List<EnterpriseThirdpartyPaymentAgument> __ETPaymentAguments = new ArrayList<EnterpriseThirdpartyPaymentAgument>();
			List<ThirdpartyPaymentTypeSetting> __thirdpartyPaymentSettings = thirdpartyPaymentTypeSettingService.takeThirdpartyPaymentTypeSetting(__ETPayment.getThirdpartypaymenttypecode());
			for (ThirdpartyPaymentTypeSetting thirdpartyPaymentTypeSetting : __thirdpartyPaymentSettings) {
				EnterpriseThirdpartyPaymentAgument enterpriseThirdpartyPaymentAgument = new EnterpriseThirdpartyPaymentAgument();
				enterpriseThirdpartyPaymentAgument.setPaymentsettingcode(thirdpartyPaymentTypeSetting.getPaymentsettingcode());
				enterpriseThirdpartyPaymentAgument.setAgumentvalue(request.getParameter(thirdpartyPaymentTypeSetting.getArgumentfiled()));
				__ETPaymentAguments.add(enterpriseThirdpartyPaymentAgument);
			}
			//调用保存方法
			enterpriseThirdpartyPaymentService.tc_save(__ETPayment,__ETPaymentAguments);
			
			userLogsService.addActivityBetRecord(new UserLogs(__EEmployee.getEnterprisecode(), __EEmployee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.快捷支付业务, "保存企业快捷支付信息:"+request.getParameter("displayname"), __EEmployee.getLoginaccount(), null));
			
			return super.packJSON(Constant.BooleanByte.YES, "操作成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		return super.packJSON(Constant.BooleanByte.NO, "操作失败");
	}
	
	/**
	 * 修改名称
	 * @return
	 */
	@RequestMapping("/updatename")
	@ResponseBody
	public Map<String,Object> updatename(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee __EEmployee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			if(request.getParameter("displayname").getBytes("GB2312").length > 10) {
				super.packJSON(Constant.BooleanByte.NO, "长度不能超过10个字符，如是中文，不得超过5个汉字");
			}
			
			EnterpriseThirdpartyPayment  __ETPayment = enterpriseThirdpartyPaymentService.selectByPrimaryKey(request.getParameter("enterprisethirdpartycode"));
			//描述
			__ETPayment.setDscription(request.getParameter("dscription"));
			__ETPayment.setDisplayname(request.getParameter("displayname"));
			__ETPayment.setIsbanks(request.getParameter("isbanks").equals("on") ? true : false);
			__ETPayment.setIsweixin(request.getParameter("isweixin").equals("on") ? true : false);
			__ETPayment.setIszhifubao(request.getParameter("iszhifubao").equals("on") ? true : false);
			__ETPayment.setMinmoney(new BigDecimal(request.getParameter("minmoney")));
			__ETPayment.setMaxmoney(new BigDecimal(request.getParameter("maxmoney")));
			if(request.getParameter("ord") == null || request.getParameter("ord").equals("")) {
				__ETPayment.setOrd("99");
			} else {
				__ETPayment.setOrd(request.getParameter("ord"));
			}
			__ETPayment.setCallbackurl(request.getParameter("callbackurl"));
			
			//调用保存方法
			enterpriseThirdpartyPaymentService.update(__ETPayment);
			
			userLogsService.addActivityBetRecord(new UserLogs(__EEmployee.getEnterprisecode(), __EEmployee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.快捷支付业务, "更改企业快捷支付信息:"+request.getParameter("displayname"), __EEmployee.getLoginaccount(), null));
			
			return super.packJSON(Constant.BooleanByte.YES, "操作成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		return super.packJSON(Constant.BooleanByte.NO, "操作失败");
	}
	
	/**
	 * 查询参数类型值
	 * @param request
	 * @return
	 */
	@RequestMapping("/findEnterpriseThirdpartyPaymentAgument")
	@ResponseBody
	public Map<String,Object> findEnterpriseThirdpartyPaymentAgument(HttpServletRequest request,HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Map<String,Object> object = getRequestParamters(request);
			String enterprisethirdpartycode = String.valueOf(object.get("enterprisethirdpartycode"));
			object.put("enterprisethirdpartycode",enterprisethirdpartycode.split("_")[1]);
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			map.put("enterprisecode", ee.getEnterprisecode());
			List<EnterpriseThirdpartyPaymentAgument> list = enterpriseThirdpartyPaymentAgumentService.selectUnionAll(object);
			map.put("rows", list);
			return map;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return map;
	}
	
	/**
	 * 保存参数类型修改之后的值
	 * @param request
	 */
	@RequestMapping("/updateEnterpriseThirdpartyPaymentAgument")
	@OperationLog(OparetionDescription.THIRDPARTYPAYMENT_UPDATE)
	@ResponseBody
	public Map<String,Object> updateEnterpriseThirdpartyPaymentAgument(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			EnterpriseEmployee __EEmployee = (EnterpriseEmployee) request.getSession().getAttribute(Constant.USER_SESSION);
			EnterpriseThirdpartyPaymentAgument enterpriseThirdpartyPaymentAgument = new EnterpriseThirdpartyPaymentAgument();
			enterpriseThirdpartyPaymentAgument.setPaymentagumentcode(Integer.valueOf(request.getParameter("paymentagumentcode")));
			enterpriseThirdpartyPaymentAgument.setAgumentvalue(request.getParameter("agumentvalue"));
			enterpriseThirdpartyPaymentAgumentService.updateEnterpriseThirdpartyPaymentAgument(enterpriseThirdpartyPaymentAgument);
			
			userLogsService.addActivityBetRecord(new UserLogs(__EEmployee.getEnterprisecode(), __EEmployee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.快捷支付业务, "保存企业快捷支付信息:"+request.getParameter("paymentagumentcode"), __EEmployee.getLoginaccount(), null));
			
			map.put("status","success");
			return map;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		map.put("status", "failure");
		return map;
	}
}
