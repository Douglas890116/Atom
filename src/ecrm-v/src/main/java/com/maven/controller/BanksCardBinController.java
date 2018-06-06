package com.maven.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.cache.SystemCache;
import com.maven.constant.Constant;
import com.maven.entity.BanksCardBin;
import com.maven.entity.EnterpriseEmployee;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BanksCardBinService;
@Controller
@RequestMapping("/cardbin")
public class BanksCardBinController extends BaseController{
	private static LoggerManager log = LoggerManager.getLogger(BanksCardBinController.class.getName(), OutputManager.LOG_ENTERPRISEINFORMATION);
	
	@Autowired
	private BanksCardBinService businessEmployeeLovelService;
	
	@RequestMapping("/index")
	public String index(){
		return "/userinfo/bankCardBinIndex";
	}
	
	/**
	 * 查询数据
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> levelQuery(HttpServletRequest request,HttpSession session){
		try {
			Map<String,Object> object = getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			List<BanksCardBin> rows =  businessEmployeeLovelService.selectBetRecord(object);
//			Map<String,String> s = new HashMap<String, String>();
//			s.put("lsh", "sign");
//			super.encryptSignTarget(rows, session.getId(), s);
			int rowCount = businessEmployeeLovelService.selectBetRecordCount(object);
			return formatPagaMap(rows,rowCount);
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 保存数据
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String,Object> save(HttpServletRequest request,HttpSession session){
		EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			
			BanksCardBin employeeLevel = new BanksCardBin();
			employeeLevel.setBankprefix(request.getParameter("bankprefix"));
			employeeLevel.setBankname(request.getParameter("bankname"));
			employeeLevel.setBankcode(request.getParameter("bankcode"));
			employeeLevel.setRemark(request.getParameter("remark"));
			employeeLevel.setBanklen(Integer.valueOf(request.getParameter("banklen")));
			businessEmployeeLovelService.addActivityBetRecord(employeeLevel);
			
			//重新初始化
			SystemCache.getInstance().makeBankscardbinAll();
			
			map.put("status", "success");
			return map;
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			e.printStackTrace();
		}
		map.put("status", "failure");
		return map;
	}
	
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}

}
