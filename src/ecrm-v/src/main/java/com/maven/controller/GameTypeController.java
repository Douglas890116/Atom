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
import com.maven.entity.Game;
import com.maven.logger.LoggerManager;
import com.maven.logger.OparetionDescription;
import com.maven.logger.OutputManager;
import com.maven.service.GameService;

@Controller
@RequestMapping("/gametype")
public class GameTypeController extends BaseController{
	private static LoggerManager log = LoggerManager.getLogger(GameTypeController.class.getName(), OutputManager.LOG_GAMETYPE);
	@Autowired
	private GameService gameServiceImpl;
	
	@RequestMapping("/index")
	public String index(){
		return "/game/gameIndex"; 
	}
	
	@RequestMapping("/add")
	public String add(){
		return "/game/gameAdd";
	}
	
	@RequestMapping("/update")
	public String update(Model model,HttpServletRequest request,HttpSession session){
		try{
			String gamecode = request.getParameter("gamecode");
			//解密标识字段的值
			boolean mark = this.decodeSign(gamecode, session.getId());
			if(mark){
				Game game = gameServiceImpl.getGame(gamecode.split("_")[1]);
				model.addAttribute("game", game);
				return "/game/gameUpdate";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return Constant.PAGE_DECODEREFUSE;
	}
	
	@RequestMapping("/data")
	@ResponseBody
	public Map<String,Object> findGameData(HttpServletRequest request,HttpSession session){
		Map<String, Object> map = getRequestParamters(request);
		try {
			List<Game> list = gameServiceImpl.findGameData(map);
			int count = gameServiceImpl.findGameDataCount(map);
			//加密标识字段的值
			this.encryptSign(list,session.getId(),new String[]{"gamecode"});
			return super.formatPagaMap(list, count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除游戏类型
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/deleteGameType")
	@ResponseBody
	public Map<String,Object> deleteGameType(HttpServletRequest request,HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//获取加密之后的员工编码
			String md5value = request.getParameter("deleteCode");
			//解密标识字段的值
			boolean mark = this.decodeSign(md5value, session.getId());
			if(mark){
				gameServiceImpl.deleteGameType(md5value.split("_")[1]);
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
	 * 批量删除
	 * @param request
	 */
	@RequestMapping("/deleteSelectGameType")
	@OperationLog(OparetionDescription.GAME_TYPE_DELETE)
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
				gameServiceImpl.deleteSelectGameType(array);
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
	 * 保存游戏类型
	 * @param session
	 * @param request
	 * @param game
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String,Object> saveGameType(HttpSession session,HttpServletRequest request,@ModelAttribute Game game){
		try {
			//保存新注册的员工
			gameServiceImpl.saveGameType(game);
			return super.packJSON(Constant.BooleanByte.YES, "恭喜您,操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return super.packJSON(Constant.BooleanByte.NO, "对不起,操作失败");
		}
	}
	
	/**
	 * 修改游戏类型
	 * @param session
	 * @param request
	 * @param game
	 * @return
	 */
	@RequestMapping("/updateSave")
	@OperationLog(OparetionDescription.GAME_TYPE_UPDATE)
	@ResponseBody
	public Map<String,Object> updateGameType(HttpSession session,HttpServletRequest request,@ModelAttribute Game game){
		try {
			//修改游戏类型
			gameServiceImpl.updateGameType(game);
			return super.packJSON(Constant.BooleanByte.YES, "恭喜您,操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			return super.packJSON(Constant.BooleanByte.NO, "对不起,操作失败");
		}
	}
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}
}
