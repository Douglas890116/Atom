package com.maven.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy.pull.common.util.api.AVGameAPI;
import com.hy.pull.common.util.api.BaseInterfaceLog;
import com.hy.pull.common.util.api.GameAPI;
import com.hy.pull.common.util.game.av.AVUtil;
import com.maven.cache.SystemCache;
import com.maven.constant.Constant;
import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.entity.ApiSoltGametype;
import com.maven.entity.EmployeeMessage;
import com.maven.entity.Enterprise;
import com.maven.entity.EmployeeMessage.Enum_readstatus;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseGame;
import com.maven.entity.Game;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.game.OrderNewUtil;
import com.maven.game.enums.GameEnum;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.ApiSoltGametypeService;
import com.maven.service.EmployeeMessageService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseGameService;
import com.maven.service.EnterpriseService;
import com.maven.service.GameService;
import com.maven.util.BeanToMapUtil;
import com.maven.util.RandomString;
import com.maven.util.RandomStringNum;
import com.maven.utils.ApiKeyObject;
import com.maven.utils.StringUtil;

/**
 * API游戏电子老虎机分类管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/apigametype")
public class GameTypeSoltController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(
			EnterpriseOperatingBrandGameController.class.getName(), OutputManager.LOG_GAMEAPIINPUT);
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private ApiSoltGametypeService apiSoltGametypeService;
	
	/**
	 * index
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, Model model){
		try {
			Map<String,Object> params = new HashMap<String, Object>();
			List<Game> listGame = gameService.findGameData(params);
			model.addAttribute("listGame", listGame);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/enterprise/apigametype_list";
	}
	
	/**
	 * edit
	 * @param request
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(HttpServletRequest request, Model model){
		
		try {
			Map<String,Object> params = new HashMap<String, Object>();
			List<Game> listGame = gameService.findGameData(params);
			model.addAttribute("listGame", listGame);
			if(request.getParameter("lsh") != null) {
				model.addAttribute("apiSoltGametype", apiSoltGametypeService.selectByPrimaryKey(request.getParameter("lsh")));
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/enterprise/apigametype_edit";
	}
	
	/**
	 * 启用/禁用WEB
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateweb")
	@ResponseBody
	public Map<String, Object> updateweb(HttpServletRequest request,Model model){
		
		try {
			Map<String,Object> params = getRequestParamters(request);
			
			ApiSoltGametype data = apiSoltGametypeService.selectByPrimaryKey(request.getParameter("lsh"));
			data.setIsweb(params.get("isweb").toString());
			data.setCreatetime(new Date());
			apiSoltGametypeService.updateApiSoltGametype(data);
			
			/*************更新缓存
			List<Map<String, String>> list = SystemCache.getInstance().getApiSoltGametypeList(data.getBiggametype());
			for (Map<String, String> map : list) {
				try {
					//目标 源头
					BeanUtils.copyProperties(map, data);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			SystemCache.getInstance().setApiSoltGametypeList(data.getBiggametype(), list);
			*************/
			
			return this.packJSON(Constant.BooleanByte.YES,"操作成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.packJSON(Constant.BooleanByte.YES,"操作失败，请稍后尝试");
		}		
	}
	
	/**
	 * 启用/禁用H5
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateh5")
	@ResponseBody
	public Map<String, Object> updateh5(HttpServletRequest request,Model model){
		
		try {
			Map<String,Object> params = getRequestParamters(request);
			
			ApiSoltGametype data = apiSoltGametypeService.selectByPrimaryKey(request.getParameter("lsh"));
			data.setIsh5(params.get("ish5").toString());
			data.setCreatetime(new Date());
			apiSoltGametypeService.updateApiSoltGametype(data);
			
			/*************更新缓存
			List<Map<String, String>> list = SystemCache.getInstance().getApiSoltGametypeList(data.getBiggametype());
			for (Map<String, String> map : list) {
				try {
					//目标 源头
					BeanUtils.copyProperties(map, data);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			SystemCache.getInstance().setApiSoltGametypeList(data.getBiggametype(), list);
			*************/
			
			
			return this.packJSON(Constant.BooleanByte.YES,"操作成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.packJSON(Constant.BooleanByte.YES,"操作失败，请稍后尝试");
		}		
	}
	
	/**
	 * 保存
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest request,Model model){
		
		try {
			Map<String,Object> params = getRequestParamters(request);
			
			ApiSoltGametype data = BeanToMapUtil.convertMap(params, ApiSoltGametype.class);
			data.setCreatetime(new Date());
			if(data.getLsh() == null ) {
				apiSoltGametypeService.saveApiSoltGametype(data);
			} else {
				apiSoltGametypeService.updateApiSoltGametype(data);
			}
			
			/*************更新缓存
			List<Map<String, String>> list = SystemCache.getInstance().getApiSoltGametypeList(data.getBiggametype());
			if(list == null) {
				list = new ArrayList<Map<String,String>>();
			}
			Map<String, String> map = new HashMap<String, String>();
			try {
				//目标 源头
				BeanUtils.copyProperties(map, data);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
			if(data.getLsh() == null ) {
				list.add(map);
			} else {
				for (Map<String, String> item : list) {
					if(item.get("lsh").equals(map.get("lsh"))) {
						item.putAll(map);
					}
				}
			}
			SystemCache.getInstance().setApiSoltGametypeList(data.getBiggametype(), list);
			*************/
			
			
			return this.packJSON(Constant.BooleanByte.YES,"操作成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.packJSON(Constant.BooleanByte.YES,"操作失败，请稍后尝试");
		}		
	}
	
	/**
	 * apikey分页查询
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/pagelist")
	@ResponseBody
	public Map<String, Object> pagelist(HttpServletRequest request,Model model){
		
		try {
			Map<String,Object> params = getRequestParamters(request);
			List<ApiSoltGametype> listEnterpriseGame = apiSoltGametypeService.selectAll(params);
			int count = apiSoltGametypeService.selectAllCount(params);
			
			Map<String, Object> data = super.formatPagaMap(listEnterpriseGame, count);
			
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return null;
		
	}
	
	
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
