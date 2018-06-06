package com.maven.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy.pull.common.util.GBUtil;
import com.maven.config.SystemConstant;
import com.maven.constant.Constant;
import com.maven.entity.BettingGb;
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.BettingGameService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseService;
import com.maven.util.GBUtils;
import com.maven.utils.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller
@RequestMapping("/gbcgame")
public class BettingGbController extends BaseController{
private static LoggerManager log = LoggerManager.getLogger(BettingHqXcpController.class.getName(), OutputManager.LOG_BETTINGHQXCP);
	
	@Resource
	private EnterpriseEmployeeService employeeService;
	private static List<Enterprise> listEnterprise;
	@Resource
	private EnterpriseService enterpriseService;
	
	@Resource(name="bettingGbServiceImpl")
	private BettingGameService<BettingGb> bettingGameService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request,Model model){
		super.assembleEnterprise(request.getSession(), listEnterprise, enterpriseService, model, request);
		model.addAttribute("employeecode", request.getParameter("employeecode"));
		return "/gamerecord/gbgame";
	}
	
	@RequestMapping(value={"/data"})
	@ResponseBody
	public Map<String,Object> data(HttpServletRequest request , HttpServletResponse response , HttpSession session){
		try {
			Map<String,Object> object = getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);

			/*********非超级管理员时只能查询本团队内的数据************/
			if(!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())){
				super.dataLimits(ee, object,session);
			}
			super.assembleParent(object, session, ee);
			List<BettingGb> list = bettingGameService.takeRecord(object);
			for (BettingGb bettingGb : list) {
				if(bettingGb.getKenolist() != null && bettingGb.getKenolist().length() > 5) {
					bettingGb.setGametype("keno彩票");
					/*
					//组装明细数据
					JSONArray array = JSONArray.fromObject(bettingGb.getKenolist());
					if(array.size() > 0) {
						for (int i = 0; i < array.size(); i++) {
							JSONObject data = array.getJSONObject(i);
							System.out.println(data);
							JSONArray KenoBalls = data.getJSONArray("KenoBalls");
							String kenoballs = "";
							if(KenoBalls != null && KenoBalls.size() > 0) {
								kenoballs = "选球号：";
								for (int k = 0; k < KenoBalls.size(); k++) {
									kenoballs += KenoBalls.getJSONObject(i).getString("OptParam1") + ";";
								}
							}
							System.out.println("下注盘口："+GBUtils.__SrcCode.get(data.getString("SrcCode"))+"("+data.getString("SrcCode")+")" + " 下注期号："+data.getString("DrawNo")+ " 下注玩法："+data.getString("OptCode")+ " 珠仔為選號數："+data.getString("OptParam1")+ " 最大赔率："+data.getDouble("MaxRate")/100 + " 实际赔率："+data.getDouble("RealRate")/100 + " "+kenoballs);
						}
					}
					//
					*/
				} else if(bettingGb.getSsclist() != null && bettingGb.getSsclist().length() > 5) {
					bettingGb.setGametype("ssc时时彩");
					/*
					//组装明细数据
					JSONArray array = JSONArray.fromObject(bettingGb.getSsclist());
					if(array.size() > 0) {
						for (int i = 0; i < array.size(); i++) {
							JSONObject data = array.getJSONObject(i);
							System.out.println(data);
							JSONArray KenoBalls = data.getJSONArray("LottoBalls");
							String kenoballs = "";
							if(KenoBalls != null && KenoBalls.size() > 0) {
								kenoballs = "选球号：";
								for (int k = 0; k < KenoBalls.size(); k++) {
									kenoballs += KenoBalls.getJSONObject(i).getString("BallNum") + ";";
								}
							}
							
							JSONArray BallZodiac = data.getJSONArray("BallZodiac");
							String ballzodiac = "";
							if(BallZodiac != null && BallZodiac.size() > 0) {
								ballzodiac = "生肖：";
								for (int k = 0; k < BallZodiac.size(); k++) {
									ballzodiac += BallZodiac.getJSONObject(i).getString("BallZodiac") + ";";
								}
							}
							System.out.println("下注盘口："+GBUtils.__SrcCode.get(data.getString("SrcCode"))+"("+data.getString("SrcCode")+")" + " 下注期号："+data.getString("DrawNo")+ " 下注玩法："+data.getString("OptCode")+ " 選號數量："+data.getString("BallCnt")+ " 生肖："+data.getString("Zodiacs")+ " 最大赔率："+data.getDouble("MaxRate")/100 + " 实际赔率："+data.getDouble("RealRate")/100 + " "+kenoballs + " "+ballzodiac);
						}
					}
					//
					*/
				} else if(bettingGb.getLottolist() != null && bettingGb.getLottolist().length() > 5) {
					bettingGb.setGametype("lotto乐透");
					/*
					//组装明细数据
					JSONArray array = JSONArray.fromObject(bettingGb.getLottolist());
					if(array.size() > 0) {
						for (int i = 0; i < array.size(); i++) {
							JSONObject data = array.getJSONObject(i);
							System.out.println(data);
							JSONArray KenoBalls = data.getJSONArray("LottoBalls");
							String kenoballs = "";
							String ballzodiac = "";
							if(KenoBalls != null && KenoBalls.size() > 0) {
								kenoballs = "选球号：";
								ballzodiac = "生肖：";
								for (int k = 0; k < KenoBalls.size(); k++) {
									kenoballs += KenoBalls.getJSONObject(i).getString("BallNumber") + ";";
									ballzodiac += KenoBalls.getJSONObject(i).getString("BallZodiac") + ";";
								}
							}
							
							//System.out.println();
							//bettingGb.setLottolist("下注盘口："+GBUtils.__SrcCode.get(data.getString("SrcCode"))+"("+data.getString("SrcCode")+")" + " 下注期号："+data.getString("DrawNo")+ " 下注玩法："+data.getString("OptCode")+ " 選號數量："+data.getString("BallCount")+ " 生肖："+data.getString("Zodiacs")+ " 最大赔率："+data.getDouble("MaxRate")/100 + " 实际赔率："+data.getDouble("RealRate")/100 + " "+kenoballs + " "+ballzodiac);
						}
					}*/
					//
				} else if(bettingGb.getPkxlist() != null && bettingGb.getPkxlist().length() > 5) {
					bettingGb.setGametype("PK10");
				}
			}
			
			Map<String, Object> result = bettingGameService.takeRecordCountMoney(object);
			int countRecord = StringUtil.getInt(result.get("count"));
			
			Map<String, Object> data = super.formatPagaMap(list, countRecord);
			Map<String, Object> summary = new HashMap<String, Object>();
			summary.put("betmoney", StringUtil.getDouble(result.get("betmoney")));
			summary.put("netmoney", StringUtil.getDouble(result.get("netmoney")));
			data.put("summary", summary);
			
			return data;
			
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 数据导出Excel
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/exportExcel")
	public String exportExcel(HttpServletRequest request, HttpSession session, Model model){
		try {
			Map<String,Object> object = getRequestParamters(request);
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_SESSION);
			
			/*********非超级管理员时只能查询本团队内的数据************/
			if(!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())){
				super.dataLimits(ee, object,session);
			}
			List<BettingGb> list = bettingGameService.takeRecord(object);
			model.addAttribute("listData", list);
			model.addAttribute("title", "GB彩票报表数据");
		} catch (Exception e) {
			log.Error(e.getMessage(), e);
		}
		return "/gamerecord/gbgameexcel";
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
