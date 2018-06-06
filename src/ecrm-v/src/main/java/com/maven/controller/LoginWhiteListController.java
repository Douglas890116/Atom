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
import com.maven.entity.LoginWhiteList;
import com.maven.entity.UserLogs;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.payment.th.utils.StringUtils;
import com.maven.service.LoginWhiteListService;
import com.maven.service.UserLogsService;

/**
 * 企业登录白名单
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/LoginWhiteList")
public class LoginWhiteListController extends BaseController {
	
	private LoggerManager log = LoggerManager.getLogger(LoginWhiteListController.class.getName(),OutputManager.LOG_ACTIVITYBETRECORD);
			
	@Autowired
	private LoginWhiteListService loginWhiteListService;
	@Autowired
	private UserLogsService userLogsService;
	/**
	 * index
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		return "/permission/loginwhitelist_list";
	}
	
	/**
	 * 转到新增页面/修改页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/add","/edit"})
	public String update(Model model ,HttpSession session,HttpServletRequest request){
		try {
			// 如果不为空则需要修改
			String lsh = request.getParameter("lsh");
			if( !StringUtils.isNullOrEmpty(lsh)) {
				LoginWhiteList whiteList = loginWhiteListService.selectByPrimaryKey(lsh);
				model.addAttribute("whiteList", whiteList);
			}
			
			return "/permission/loginwhitelist_update";
		} catch (Exception e) {
			e.printStackTrace();
			log.Error(e.getMessage(), e);
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	/**
	 * 超级管理员通过权限来设置企业的白名单
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/savesa"})
	@ResponseBody
	public Map<String,Object> savesa(HttpSession session,HttpServletRequest request){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			String ip = request.getParameter("ip");
			String remark = request.getParameter("remark");
			String enterprisecode = request.getParameter("enterprisecode");
			LoginWhiteList whiteList = new LoginWhiteList();
			whiteList.setIp(ip);
			whiteList.setRemark(remark);
			whiteList.setEnterprisecode(enterprisecode);
			loginWhiteListService.saveLoginWhiteList(whiteList);
			
			userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
				     UserLogs.Enum_operatype.IP白名单业务, "新增IP白名单:"+enterprisecode, ee.getLoginaccount(), "ip:"+ip));
			
			return super.packJSON(Constant.BooleanByte.YES, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
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
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			
			// 如果不为空则需要修改
			String lsh = request.getParameter("lsh");
			String ip = request.getParameter("ip");
			String remark = request.getParameter("remark");
			if( !StringUtils.isNullOrEmpty(lsh)) {
				LoginWhiteList whiteList = loginWhiteListService.selectByPrimaryKey(lsh);
				whiteList.setIp(ip);
				whiteList.setRemark(remark);
				loginWhiteListService.updateLoginWhiteList(whiteList);
				
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.IP白名单业务, "更改IP白名单:"+whiteList.getEnterprisecode(), ee.getLoginaccount(), "ip:"+ip));
				
			} else {
				LoginWhiteList whiteList = new LoginWhiteList();
				whiteList.setIp(ip);
				whiteList.setRemark(remark);
				whiteList.setEnterprisecode(ee.getEnterprisecode());
				loginWhiteListService.saveLoginWhiteList(whiteList);
				
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.IP白名单业务, "新增IP白名单:"+whiteList.getEnterprisecode(), ee.getLoginaccount(), "ip:"+ip));
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
//	@OperationLog(OparetionDescription.ENTERPRISEEMPLOYEEINFO_DELETE)
	public Map<String,Object> deleteIp(HttpServletRequest request,HttpSession session){
		try {
			
			String temp = request.getParameter("sign");
			String[] array = temp.split(",");
			boolean mark = this.decodeSign(array, session.getId());
			if(mark){
				for (int i = 0; i < array.length; i++) {
					array[i]=array[i].split("_")[1];
				}
				loginWhiteListService.deleteSelectIp(array);
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
//	@OperationLog(OparetionDescription.ENTERPRISEEMPLOYEEINFO_DELETE)
	public Map<String,Object> deleteSelectIp(HttpServletRequest request,HttpSession session){
		try {
			String temp = request.getParameter("sign");
			System.out.println(temp);
			String[] array = temp.split(",");
			boolean mark = this.decodeSign(array, session.getId());
			if(mark){
				for (int i = 0; i < array.length; i++) {
					array[i] = array[i].split("_")[1];
				}
				loginWhiteListService.deleteSelectIp(array);
				return super.packJSON(Constant.BooleanByte.YES, "已删除");
			}else{
				return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
			
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return super.packJSON(Constant.BooleanByte.NO, Constant.AJAX_ACTIONFAILD);
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
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			//查数据
			if(!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())){
				parameter.put("enterprisecode", ee.getEnterprisecode());
			}
			List<LoginWhiteList> list = loginWhiteListService.selectAll(parameter);
			Map<String,String> s = new HashMap<String, String>();
			s.put("lsh", "sign");
			super.encryptSignTarget(list,session.getId(),s);
			
			//查总数
			int rowCount  = loginWhiteListService.selectAllCount(parameter);
			return super.formatPagaMap(list, rowCount);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	

	@Override
	public LoggerManager getLogger() {
		return log;
	}
	
}
