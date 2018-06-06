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

import com.maven.annotation.OperationLog;
import com.maven.cache.SystemCache;
import com.maven.constant.Constant;
import com.maven.entity.Bank;
import com.maven.entity.EnterpriseThirdpartyPayment;
import com.maven.entity.EnterpriseThirdpartyPaymentAgument;
import com.maven.entity.ThirdpartyPaymentBank;
import com.maven.entity.ThirdpartyPaymentType;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.EnterpriseThirdpartyPaymentAgumentService;
import com.maven.service.EnterpriseThirdpartyPaymentBankService;
import com.maven.service.ThirdpartyPaymentTypeService;
/**
 * 快捷支付银行
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/thirdpartyPaymentBank")
public class ThirdpartyPaymentBankController extends BaseController{
	
	
	private static LoggerManager log = LoggerManager.getLogger(ThirdpartyPaymentBankController.class.getName(), OutputManager.LOG_ENTERPRISETHIRDPARTYPAYMENTBANK);
	@Autowired
	private EnterpriseThirdpartyPaymentBankService enterpriseThirdpartyPaymentBankService;
	@Autowired
	private EnterpriseThirdpartyPaymentAgumentService enterpriseThirdpartyPaymentAgumentService;
	@Autowired
	private ThirdpartyPaymentTypeService thirdpartyPaymentTypeService;
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
	
	@RequestMapping("/index")
	public String index(Model model){
		List<ThirdpartyPaymentType> listThirdpartyPaymentType = thirdpartyPaymentTypeService.takeThirdpartyPaymentType();
		model.addAttribute("listThirdpartyPaymentType", listThirdpartyPaymentType);
		return "/thirdpartypaymentbank/index";
	}
	
	@RequestMapping("/add")
	public String add(){
		return "/thirdpartypaymentbank/addbank";
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
			List<ThirdpartyPaymentBank> list = enterpriseThirdpartyPaymentBankService.findAll(map);
			Map<String,String> s = new HashMap<String, String>();
			s.put("id", "sign");
			super.encryptSignTarget(list, session.getId(), s);
			int count = enterpriseThirdpartyPaymentBankService.findCountAll(map);
			return super.formatPagaMap(list, count);
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
			String enterpriseThirdpartyCode = request.getParameter("deleteCode");
			boolean mark = this.decodeSign(enterpriseThirdpartyCode, session.getId());
			if(mark){
				enterpriseThirdpartyPaymentBankService.tc_delete(enterpriseThirdpartyCode.split("_")[1]);
				map.put("status", "success");
			}else{
				map.put("status", "failure");
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
			String tempArray = request.getParameter("sign");
			String[] array = tempArray.split(",");
			boolean mark = this.decodeSign(array, session.getId());
			if(mark){
				for (int i = 0; i < array.length; i++) {
					array[i]=array[i].split("_")[1];
				}
				enterpriseThirdpartyPaymentBankService.tc_deleteSelect(array);
				return super.packJSON(Constant.BooleanByte.YES, "删除成功");
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
	public Map<String,Object> enableDisable(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			EnterpriseThirdpartyPayment  enterpriseThirdpartyPayment = new EnterpriseThirdpartyPayment();
			enterpriseThirdpartyPayment.setEnterprisethirdpartycode(request.getParameter("id"));
			enterpriseThirdpartyPayment.setStatus(request.getParameter("status"));
			enterpriseThirdpartyPaymentBankService.tc_enableDisable(enterpriseThirdpartyPayment);
			map.put("status", "success");
			return map;
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
			ThirdpartyPaymentBank  thirdpartyPaymentBank = new ThirdpartyPaymentBank();
			//支付银行编码
			thirdpartyPaymentBank.setBankcode(request.getParameter("thirdpartypaymentbankname"));
			//支付类型编码
			thirdpartyPaymentBank.setThirdpartypaymenttypecode(request.getParameter("thirdpartypaymenttypecode"));
			//是否启用(默认启用)
			thirdpartyPaymentBank.setEnable("1");
			//支付银行编码
			thirdpartyPaymentBank.setPaymenttypebankcode(request.getParameter("paymenttypebankcode"));
			//调用保存方法
			enterpriseThirdpartyPaymentBankService.tc_save(thirdpartyPaymentBank,request);
			
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
	public Map<String,Object> findEnterpriseThirdpartyPaymentAgument(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Map<String,Object> object = getRequestParamters(request);
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
			EnterpriseThirdpartyPaymentAgument enterpriseThirdpartyPaymentAgument = new EnterpriseThirdpartyPaymentAgument();
			enterpriseThirdpartyPaymentAgument.setPaymentagumentcode(Integer.valueOf(request.getParameter("paymentagumentcode")));
			enterpriseThirdpartyPaymentAgument.setAgumentvalue(request.getParameter("agumentvalue"));
			enterpriseThirdpartyPaymentAgumentService.updateEnterpriseThirdpartyPaymentAgument(enterpriseThirdpartyPaymentAgument);
			map.put("status","success");
			return map;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		map.put("status", "failure");
		return map;
	}
	/**
	 * 编辑跳转
	 * @param request
	 */
	@RequestMapping("/update")
	public String update(Model model,HttpServletRequest request,HttpSession session){
		try{
			String paymentbankCode = request.getParameter("thirdpartybank");
			//解密标识字段的值
			boolean mark = this.decodeSign(paymentbankCode, session.getId());
			if(mark){
			    ThirdpartyPaymentBank paymentBank = enterpriseThirdpartyPaymentBankService.findThirdpartyPaymentBank(paymentbankCode.split("_")[1]);
			    List<ThirdpartyPaymentType> thirdpartyPaymentType = thirdpartyPaymentTypeService.takeThirdpartyPaymentType();
			    List<Bank> list =  SystemCache.getInstance().getBanks();
			    paymentBank.setSign(paymentbankCode);
				model.addAttribute("paymentBank", paymentBank);
				model.addAttribute("paymentBankList", list);
				model.addAttribute("paymentTypeList", thirdpartyPaymentType);
				return "/thirdpartypaymentbank/updateBank";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return Constant.PAGE_DECODEREFUSE;
	}
	
	/**
	 * 修改
	 * @return
	 */
	@RequestMapping("/updateSave")
	@ResponseBody
	public Map<String,Object> updateSave(HttpServletRequest request,HttpSession session){
		try {
			ThirdpartyPaymentBank  thirdpartyPaymentBank = super.getRequestParamters(request, ThirdpartyPaymentBank.class);
			if(super.decodeSign(thirdpartyPaymentBank.getSign(), session.getId())){
				//调用保存方法
				thirdpartyPaymentBank.setId(Integer.parseInt(thirdpartyPaymentBank.getSign().split("_")[1]));
				enterpriseThirdpartyPaymentBankService.tc_update(thirdpartyPaymentBank);
				return super.packJSON(Constant.BooleanByte.YES, "操作成功");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
}
