package com.test;
/*package com.maven.test;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maven.controller.BaseController;
import com.maven.entity.EnterpriseEmployee;
import com.maven.game.GameEnum;
import com.maven.game.HYAPIHelper;
import com.maven.util.Constant;

@Controller
@RequestMapping("/gameapi")
public class GameApiController extends BaseController{
	
	*//**
	 * 登录游戏
	 * @param request
	 * @param session
	 * @return
	 *//*
	@RequestMapping("/login")
	@ResponseBody
	public String login(String loginName, String password, String gameType, String md5Info){
		try {
			//验证md5 信息 合法性，暂不处理。
			//验证用户是否有效。
			if(loginName != null && true){
				//假设有效
				//不需要做 Session保存。
				return GameEnum.Enum_MSG.成功.desc;
			}else{
				return GameEnum.Enum_MSG.用户不存在.desc;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return GameEnum.Enum_MSG.系统错误.desc;
		}
	}
	
	*//**
	 * 进行游戏
	 * @param gameType
	 * @param session
	 * @return
	 *//*
	@RequestMapping("/play")
	@ResponseBody
	public Map<String, Object> play(String loginName, String password, String gameType, String md5Info){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//验证md5 信息 合法性，暂不处理。
			//验证用户是否有效。
			if(loginName != null && true){
				//假设有效
				//不需要做 Session保存。
				//内部根据loginName获取 对应游戏的 账户。
				map.put("code", 1);
				map.put("info", "URL值");
			} else {
				map.put("code", 1006);
				map.put("info", "用户不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", 1001);
			map.put("info", "系统错误");
		}
		return map;
	}
	
	*//**
	 * 获取游戏数据
	 * @param session
	 * @param request
	 * @return
	 *//*
	@RequestMapping("/data")
	@ResponseBody
	public String data(HttpSession session,HttpServletRequest request){
		//用户页面 无此
		return null;
	}
	
	*//**
	 * 用户上分
	 * @param session
	 * @param request
	 * @return
	 *//*
	@RequestMapping("/deposite")
	@ResponseBody
	public String deposite(String loginName, String password, String gameType, Double money, String md5Info){
		try {
			//验证md5 信息 合法性，暂不处理。
			//验证用户是否有效。
			if(loginName != null && true){
				//假设有效
				//不需要做 Session 验证 保存。
				//内部根据loginName获取 对应游戏的 账户,进行上分。
				return GameEnum.Enum_MSG.成功.desc;
			} else {
				return GameEnum.Enum_MSG.用户不存在.desc;
			}
		}catch(NumberFormatException e){
			e.printStackTrace();
			return GameEnum.Enum_MSG.参数错误.desc;
		}catch (Exception e) {
			e.printStackTrace();
			return GameEnum.Enum_MSG.系统错误.desc;
		}
	}
	
	
	*//**
	 * 用户下分
	 * @param session
	 * @param request
	 * @return
	 *//*
	@RequestMapping("/withdraw")
	@ResponseBody
	public String withdraw(HttpSession session,HttpServletRequest request){
		//同上 deposite
		Map<String,Object> object = super.getRequestParamters(request);
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_GAME_SESSION);
			if(ee==null) return GameEnum.Enum_MSG.Session失效.desc;
			if(!GameEnum.Enum_usertype.userType(ee.getEmployeetypecode()).equals(GameEnum.Enum_usertype.会员.value))return  GameEnum.Enum_MSG.非会员用户.desc;
			
			HYAPIHelper api = new HYAPIHelper((String)object.get("gameType"), ee.getEmployeecode());
			return api.withdraw((String)object.get("money"));
		}catch(NumberFormatException e){
			e.printStackTrace();
			return GameEnum.Enum_MSG.参数错误.desc;
		}catch (Exception e) {
			e.printStackTrace();
			return GameEnum.Enum_MSG.系统错误.desc;
		}
	}
	
	@RequestMapping("/balance")
	@ResponseBody
	public String balance(HttpSession session,HttpServletRequest request){
		//同上 deposite 差别 成功 返回 分数。建议使用 Map～ map 在 @ResponseBody 作用下 自动转 Json。 你的枚举类 已经转 json 了 没必要 再@ResponseBody
		Map<String,Object> object = super.getRequestParamters(request);
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_GAME_SESSION);
			if(ee==null) return GameEnum.Enum_MSG.Session失效.desc;
			if(!GameEnum.Enum_usertype.userType(ee.getEmployeetypecode()).equals(GameEnum.Enum_usertype.会员.value))return  GameEnum.Enum_MSG.非会员用户.desc;
			HYAPIHelper api = new HYAPIHelper((String)object.get("gameType"), ee.getEmployeecode());
			return api.balance();
		}catch(NumberFormatException e){
			e.printStackTrace();
			return GameEnum.Enum_MSG.参数错误.desc;
		}catch (Exception e) {
			e.printStackTrace();
			return GameEnum.Enum_MSG.系统错误.desc;
		}
	}
	
	
	@RequestMapping("/balances")
	@ResponseBody
	public String balances(HttpSession session,HttpServletRequest request){
		//同上 balance
		try {
			EnterpriseEmployee ee = (EnterpriseEmployee)session.getAttribute(Constant.USER_GAME_SESSION);
			if(ee==null)  return GameEnum.Enum_MSG.Session失效.desc;
			if(!GameEnum.Enum_usertype.userType(ee.getEmployeetypecode()).equals(GameEnum.Enum_usertype.会员.value))return  GameEnum.Enum_MSG.非会员用户.desc;
			
			HYAPIHelper api = new HYAPIHelper();
			double info = api.balances(ee.getEmployeecode());
			return "{\"info\":\""+info+"\",\"code\":\"1\"}";
		}catch(NumberFormatException e){
			e.printStackTrace();
			return GameEnum.Enum_MSG.参数错误.desc;
		}catch (Exception e) {
			e.printStackTrace();
			return GameEnum.Enum_MSG.系统错误.desc;
		}
	}
	
	
	*//**
	 * 登录游戏
	 * @param gameType
	 * @param ee
	 * @return
	 * @throws Exception
	 *//*
//	private String playAction(String gameType,EnterpriseEmployee ee) throws Exception{
//		HYAPIHelper api = new HYAPIHelper(gameType, ee.getEmployeecode());
//		String info = api.login();
//		if(info.indexOf("\"code\":\"1006\"")>-1){
//			info =  api.createUser(ee);
//			if(info.indexOf("\"info\":\"success\"")>-1){
//				return playAction(gameType, ee);
//			}
//			return info;
//		}else{
//			return info;
//		}
//	}
	
	
}
*/