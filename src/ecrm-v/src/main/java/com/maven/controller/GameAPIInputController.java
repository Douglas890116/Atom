package com.maven.controller;

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
import com.maven.entity.Enterprise;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.EnterpriseGame;
import com.maven.entity.Game;
import com.maven.entity.GameApiInput;
import com.maven.entity.UserLogs;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.EnterpriseGameService;
import com.maven.service.GameApiInputService;
import com.maven.service.GameService;
import com.maven.service.UserLogsService;

@Controller
@RequestMapping("/InputAPI")
public class GameAPIInputController extends BaseController {
	
	private static LoggerManager log = LoggerManager.getLogger(
			EnterpriseOperatingBrandGameController.class.getName(), OutputManager.LOG_GAMEAPIINPUT);

	@Autowired
	private GameApiInputService gameApiInputService;

	@Autowired
	private GameService gameService;
	
	@Autowired
	private EnterpriseGameService enterpriseGameService;
	@Autowired
	private UserLogsService userLogsService;
	/**
	 * API设置
	 * 
	 * @return
	 */
	@RequestMapping("/SettingAPI")
	public String settingApi(HttpServletRequest request, HttpSession session, Model model) {
		try {
			String _sign = request.getParameter("sign");
			if (this.decodeSign(_sign, session.getId())) {
				String enterprisecode = _sign.split("_")[1];
				GameApiInput _input_api = gameApiInputService.takeGameAPI(enterprisecode);
				if (_input_api != null) {
					_input_api.setApicode(this.encryptString(_input_api.getApicode(), session.getId()));
				}
				List<Game> _all_game = gameService.getAllGame();
				Map<String,EnterpriseGame> _enterprise_game = enterpriseGameService.takeEnterpriseGame(enterprisecode);
				for (Game _m : _all_game) {
					if(_enterprise_game.get(_m.getGamecode())!=null){
						_m.setIschoice(true);
					}
				}
				model.addAttribute("enterprisecode",_sign);
				model.addAttribute("api", _input_api);
				
				
				/*********非超级管理员时只能查询本团队内的数据************/
				EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
				if(!SystemConstant.getProperties("admin.employeecode").equals(ee.getEmployeecode())){
					List<Game> listgame = new ArrayList<Game>();
					for (Game _m : _all_game) {
						if(_m.isIschoice() == true){
							listgame.add(_m);
						}
					}
					model.addAttribute("isadmin", false);
					model.addAttribute("games", listgame);
				} else {
					model.addAttribute("isadmin", true);
					model.addAttribute("games", _all_game);
				}
				
				
				model.addAttribute(Constant.MENU_RELATION, 
						request.getAttribute(Constant.MENU_RELATION));
				return "/enterprise/apisetting";
			} else {
				return Constant.PAGE_DECODEREFUSE;
			}
		} catch (Exception e) {
			log.Error(e.getMessage(),e);
			e.printStackTrace();
			return Constant.PAGE_ACTIONFAILD;
		}
	}
	
	@RequestMapping("/Save")
	@ResponseBody
//	@OperationLog(OparetionDescription.ENTERPRISE_API)
	public Map<String,Object> save(HttpServletRequest request, HttpSession session){
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee) session.getAttribute(Constant.USER_SESSION);
			GameApiInput _input_api = this.getRequestParamters(request, GameApiInput.class);
			String[] _arr_enterprisegames = request.getParameterValues("enterprisegames");
			String message = "操作成功";
			if(this.decodeSign(_input_api.getApicode(), session.getId())
					&&this.decodeSign(_input_api.getEnterprisecode(), session.getId())){
				String _enterprisecode = _input_api.getEnterprisecode().split("_")[1];
				_input_api.setApicode(_input_api.getApicode().split("_")[1]);
				_input_api.setEnterprisecode(_enterprisecode);
				List<EnterpriseGame> _etgames = extractEnterpriseGames(_arr_enterprisegames,_enterprisecode);
				//查询目前库中的配置，对比本次调整是否有新增游戏，不管有没有去除游戏
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("enterprisecode", _enterprisecode);
				List<EnterpriseGame> __currenList = enterpriseGameService.selectAll(params);
				List<EnterpriseGame> __addList = diffrentEnterpriseGames(__currenList, _etgames);
				gameApiInputService.updateGameInputApi(_input_api, _etgames, __addList);//只传递本次新增加的游戏进去
				
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.公司信息业务, "更改API证书信息", ee.getLoginaccount(), null));
				
				message = "证书已修改";
			}else if(this.decodeSign(_input_api.getEnterprisecode(), session.getId())){
				String _enterprisecode = _input_api.getEnterprisecode().split("_")[1];
				_input_api.setEnterprisecode(_enterprisecode);
				_input_api.setOutputapistatus(Constant.BooleanByte.YES.byteValue());
				List<EnterpriseGame> _etgames = extractEnterpriseGames(_arr_enterprisegames,_enterprisecode);
				gameApiInputService.addGameInputApi(_input_api,_etgames);
				message = "已添加证书";
				
				userLogsService.addActivityBetRecord(new UserLogs(ee.getEnterprisecode(), ee.getEmployeecode(), null, 
					     UserLogs.Enum_operatype.公司信息业务, "保存API证书信息", ee.getLoginaccount(), null));
				
			}else{
				return this.packJSON(Constant.BooleanByte.NO, Constant.AJAX_DECODEREFUSE);
			}
			return this.packJSON(Constant.BooleanByte.YES,message);
		} catch (Exception e) {
			log.Error(e.getMessage(),e);
			return this.packJSON(Constant.BooleanByte.YES,"操作失败，请稍后尝试");
		}
	}
	private List<EnterpriseGame> diffrentEnterpriseGames(List<EnterpriseGame> currenData, List<EnterpriseGame> newData) {
		List<EnterpriseGame> _list_enterprisegames = new ArrayList<EnterpriseGame>();
		Map<String, EnterpriseGame> currenMap = new HashMap<String, EnterpriseGame>();
		
		for (EnterpriseGame enterpriseGame : currenData) {
			currenMap.put(enterpriseGame.getGamecode(), enterpriseGame);
		}
		for (EnterpriseGame enterpriseGame : newData) {
			if( !currenMap.containsKey(enterpriseGame.getGamecode())) {//当前库中没有该游戏，属于新增
				_list_enterprisegames.add(enterpriseGame);
			}
		}
		return _list_enterprisegames;
	}
	private List<EnterpriseGame> extractEnterpriseGames(String[] _arr_enterprisegames,String enterprisecode) {
		if(_arr_enterprisegames==null || _arr_enterprisegames.length==0) return null;
		List<EnterpriseGame> _list_enterprisegames = new ArrayList<EnterpriseGame>();
		for (String e : _arr_enterprisegames) {
			_list_enterprisegames.add(new EnterpriseGame(null, enterprisecode, e));
		}
		return _list_enterprisegames;
	}

	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
