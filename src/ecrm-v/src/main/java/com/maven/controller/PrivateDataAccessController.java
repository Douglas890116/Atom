package com.maven.controller;

import java.util.ArrayList;
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
import com.maven.constant.Constant;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.PrivateDataAccess;
import com.maven.entity.UserLogs;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.PrivateDataAccessService;
import com.maven.service.UserLogsService;
import com.maven.util.AttrCheckout;

@Controller
@RequestMapping("/PrivateData")
public class PrivateDataAccessController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(EnterpriseThirdpartyPaymentController.class.getName(), OutputManager.LOG_PRIVATEDATAACCESS);

	@Autowired
	private PrivateDataAccessService privateDataAccessService;
	@Autowired
	private UserLogsService userLogsService;
	@RequestMapping("/View")
	public String view(){
		return "/dataaccess/settting";
	}
	
	@RequestMapping("/ChoiceUser")
	public String managerChoiceUser(){
		return "/dataaccess/choiceuser";
	}
	
	@RequestMapping("/ViewPrivateData")
	public String viewPrivateData(){
		return "/dataaccess/privatedata";
	}
	
	@RequestMapping("/Data")
	@ResponseBody
	public Map<String,Object> data(HttpServletRequest request, HttpSession session){
		try {
			Map<String,Object> object = super.getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			object.put("enterprisecode", ee.getEnterprisecode());
			List<PrivateDataAccess> list = privateDataAccessService.selectAll(object);
			Map<String,String> encrypt = new HashMap<String, String>();
			encrypt.put("id", "sign");
			super.encryptSignTarget(list, session.getId(), encrypt);
			int count = privateDataAccessService.selectAllCount(object);
			return super.formatPagaMap(list, count);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	
	
	@RequestMapping("/SearchUser")
	@ResponseBody
	public Map<String,Object> searchUser(HttpServletRequest request , HttpSession session){
		try {
			Map<String,Object> object = super.getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			object.put("enterprisecode", ee.getEnterprisecode());
			List<PrivateDataAccess> list = privateDataAccessService.selectAccredit(object);
			for (PrivateDataAccess e : list){ 
				e.setSign(e.getEmployeecode());
			}
			Map<String,String> encrypt = new HashMap<String, String>();
			encrypt.put("employeecode", "sign");
			super.encryptSignTarget(list, session.getId(), encrypt);
			list = AttrCheckout.checkout(list, true, new String[]{"sign","displayalias","employeecode","loginaccount","displayalias","employeetypename","onlinestatus","lastlogintime"});
			int count = privateDataAccessService.selectAccreditCount(object);
			return super.formatPagaMap(list, count);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	@RequestMapping("/Add")
	@ResponseBody
	public Map<String,Object> add(HttpServletRequest request , HttpSession session){
		try {
			String[] employees = request.getParameterValues("employees[]");
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			List<PrivateDataAccess>  objects = new ArrayList<PrivateDataAccess>();
			for (String s : employees) {
				if(super.decodeSign(s, session.getId())){
					String employee = s.split("_")[1];
					objects.add(new PrivateDataAccess(ee.getEnterprisecode(), employee));
					
					userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
						     UserLogs.Enum_operatype.隐私数据业务, "保存隐私数据信息:"+employee, ee.getLoginaccount(), null));
					
					
				}else{
					return packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
				}
			}
			privateDataAccessService.addPriveDateAccess(objects);
			return packJSON(Constant.BooleanByte.YES, "隐私数据授权成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	@RequestMapping("/Delete")
	@ResponseBody
	@OperationLog(OparetionDescription.PRIVATEDATAACCESS_DELETE)
	public Map<String,Object> delete(HttpServletRequest request,HttpSession session){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			String[] sign = request.getParameterValues("sign[]");
			if(sign==null||sign.length==0) {
				return this.packJSON(Constant.BooleanByte.NO, "请选择需要删除的数据");
			}
			List<String> employees = new ArrayList<String>();
			for (String s : sign) {
				if(super.decodeSign(s, session.getId())){
					employees.add(s.split("_")[1]);
					
					userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
						     UserLogs.Enum_operatype.隐私数据业务, "删除隐私数据信息:"+s.split("_")[1], ee.getLoginaccount(), null));
					
				}else{
					return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
				}
			}
			privateDataAccessService.deleteListAccess(employees);
			return this.packJSON(Constant.BooleanByte.YES, "删除成功");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
		}
	}
	
	
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}

}
