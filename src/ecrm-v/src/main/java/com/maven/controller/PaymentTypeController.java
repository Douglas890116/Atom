package com.maven.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.annotation.OperationLog;
import com.maven.constant.Constant;
import com.maven.entity.PaymentType;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.PaymentTypeService;

@Controller
@RequestMapping("/paymenttype")
public class PaymentTypeController extends BaseController{
	private static LoggerManager log = LoggerManager.getLogger(PaymentTypeController.class.getName(), OutputManager.LOG_PAYMENTTYPE);
	@Autowired
	private PaymentTypeService paymentTypeService;
	
	@RequestMapping("/index")
	public String index(){
		return "/payment/paymentTypeIndex"; 
	}
	
	@RequestMapping("/add")
	public String add(){
		return "/payment/paymentTypeAdd";
	}
	
	@RequestMapping("/update")
	public String update(Model model,HttpServletRequest request,HttpSession session){
		try{
			String paymenttypecode = request.getParameter("paymenttypecode");
			//解密标识字段的值
			boolean mark = this.decodeSign(paymenttypecode, session.getId());
			if(mark){
				PaymentType paymentType = paymentTypeService.getPaymentType(paymenttypecode.split("_")[1]);
				model.addAttribute("paymentType", paymentType);
				return "/payment/paymentTypeUpdate";
			}
		}catch(Exception e){
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		return Constant.PAGE_DECODEREFUSE;
	}
	
	@RequestMapping("/data")
	@ResponseBody
	public Map<String,Object> findGameData(HttpServletRequest request,HttpSession session){
		Map<String, Object> map = getRequestParamters(request);
		try {
			List<PaymentType> list = paymentTypeService.findPaymentTypeData(map);
			int count = paymentTypeService.findPaymentTypeDataCount(map);
			//加密标识字段的值
			this.encryptSign(list,session.getId(),new String[]{"paymenttypecode"});
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
	@RequestMapping("/deletePaymentType")
	@OperationLog(OparetionDescription.PAYMENT_TYPE_DELETE)
	@ResponseBody
	public Map<String,Object> deleteGameType(HttpServletRequest request,HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//获取加密之后的员工编码
			String md5value = request.getParameter("deleteCode");
			//解密标识字段的值
			boolean mark = this.decodeSign(md5value, session.getId());
			if(mark){
				paymentTypeService.deletePaymentType(md5value.split("_")[1]);
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
	@RequestMapping("/deleteSelectPaymentType")
	@OperationLog(OparetionDescription.PAYMENT_TYPE_DELETE)
	@ResponseBody
	public Map<String,Object> deleteSelectGameType(HttpServletRequest request,HttpSession session){
		try {
			String temp = request.getParameter("sign");
			String[] array = temp.split(",");
			boolean mark = this.decodeSign(array, session.getId());
			if(mark){
				for (int i = 0; i < array.length; i++) {
					array[i]=array[i].split("_")[1];
				}
				paymentTypeService.deleteSelectPaymentType(array);
				return super.packJSON(Constant.BooleanByte.YES, "删除成功");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
		}
	}
	
	/**
	 * 保存支付类型
	 * @param session
	 * @param request
	 * @param game
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String,Object> saveGameType(HttpSession session,HttpServletRequest request,@ModelAttribute PaymentType paymentType){
		try {
			//保存新注册的员工
			paymentTypeService.savePaymentType(paymentType);
			return super.packJSON(Constant.BooleanByte.YES, "恭喜您,操作成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
			return super.packJSON(Constant.BooleanByte.NO, "对不起,操作失败");
		}
	}
	
	/**
	 * 修改支付类型
	 * @param session
	 * @param request
	 * @param game
	 * @return
	 */
	@RequestMapping("/updateSave")
	@OperationLog(OparetionDescription.PAYMENT_TYPE_UPDATE)
	@ResponseBody
	public Map<String,Object> updateGameType(HttpSession session,HttpServletRequest request,@ModelAttribute PaymentType paymentType){
		try {
			//保存新注册的员工
			paymentTypeService.updatePaymentType(paymentType);
			return super.packJSON(Constant.BooleanByte.YES, "恭喜您,操作成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
			return super.packJSON(Constant.BooleanByte.NO, "对不起,操作失败");
		}
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
