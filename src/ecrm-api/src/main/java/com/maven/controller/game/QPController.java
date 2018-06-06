package com.maven.controller.game;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy.pull.common.util.api.GameAPI;
import com.hy.pull.common.util.game.qp.QPUtil;
import com.maven.cache.SystemCache;
import com.maven.cache.factory.impl.RedisCache;
import com.maven.constant.Enum_MSG;
import com.maven.controller.BaseController;
import com.maven.entity.EmployeeApiAccout;
import com.maven.entity.EmployeeMessage;
import com.maven.entity.Enterprise;
import com.maven.entity.EmployeeMessage.Enum_readstatus;
import com.maven.entity.EnterpriseEmployee;
import com.maven.entity.Game;
import com.maven.exception.ArgumentValidationException;
import com.maven.exception.LogicTransactionRollBackException;
import com.maven.game.APIServiceNew;
import com.maven.game.enums.GameBigType;
import com.maven.game.enums.GameEnum.Enum_deviceType;
import com.maven.logger.LoggerManager;
import com.maven.logger.OutputManager;
import com.maven.service.EmployeeMessageService;
import com.maven.service.EnterpriseEmployeeService;
import com.maven.service.EnterpriseService;
import com.maven.service.GameService;
import com.maven.utils.StringUtil;

import net.sf.json.JSONObject;

/**
 * 帝王棋牌接口-
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/qpgame")
public class QPController extends BaseController{
	
	private static LoggerManager log = LoggerManager.getLogger(QPController.class.getName(), OutputManager.LOG_USER_MESSAGE);
	private static String gametype = "QPGame";//棋牌
	
	
	@Autowired
	private EnterpriseService enterpriseService;
	private static List<Enterprise> listEnterprise = null;
	
	
	/**
	 * 通知触发器
	 * 
	 * 1、棋牌客户端登录或退出等事件时，调用该接口，做通知。
	 * 2、api项目得到通知时，做上下分等具体事项
	 * 3、api项目开放棋牌用户账号查询的接口
	 * @param request
	 * @return
	 */
	@RequestMapping(value={"/trigger"}, produces="text/html;charset=UTF-8")
	@ResponseBody
	public String trigger(HttpServletRequest request){
		
		Map<String,Object> __parames = super.getRequestParamters(request);
		
//		参数说明：
//		agentid 代理账号，data 业务参数密文
		
//		data结构是一串加密后的密文，需要使用秘钥解密，解密后是一段json字符串，结构如下：
//		{"Account":"123456789","Type":"login"}
//		Account=用户账号
//		OptType=事件类型，分login/logout等
//		
		String data = String.valueOf(__parames.get("data"));
		String agentId = String.valueOf(__parames.get("agentid"));
		String enterprisecode = null;
		String apiKey = null;
		String Account = null;
		String OptType = null;
		String employeecode = null;
		
		if( data == null) {
			log.Error("data参数未传递");
			return Enum_MSG.参数错误.message("data参数未传递");
		}
		if( agentId == null) {
			log.Error("agentid参数未传递");
			return Enum_MSG.参数错误.message("agentid参数未传递");
		}
		
		try {
			if(listEnterprise == null) {
				listEnterprise = enterpriseService.selectAll(new HashMap<String, Object>());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String, String> keydata = new HashMap<String, String>();
		for (Enterprise enterprise : listEnterprise) {
			keydata = SystemCache.getInstance().getAPIEnterpriseKyes(enterprise.getEnterprisecode(), gametype, keydata);
			if(keydata != null && keydata.size() > 0 && keydata.get("agentId") != null && keydata.get("agentId").equals(agentId)) {
				enterprisecode = enterprise.getEnterprisecode();
				apiKey = keydata.get("apiKey");
			}
		}
		
		if(apiKey == null) {
			log.Error("未能找到指定代理的秘钥信息，可能是agentid参数不匹配，或代理未配置相关代理账号对应的秘钥信息");
			return Enum_MSG.参数错误.message("未能找到指定代理的秘钥信息，可能是agentid参数不匹配，或代理未配置相关代理账号对应的秘钥信息");
		}
		
		try {
			System.out.println("请求密文："+data);
			System.out.println("获取通信秘钥："+apiKey);
			data = QPUtil.Decrypt(data, apiKey);
			System.out.println("密文解密："+data);
			JSONObject object = JSONObject.fromObject(data);
			Account = object.getString("Account");
			OptType = object.getString("OptType");//
			
			System.out.println("Account="+Account);
			System.out.println("OptType="+OptType);
			
		} catch (Exception e) {
			e.printStackTrace();
			return Enum_MSG.失败.message("解密处理异常："+e.getMessage());
		}
		
		//	获取玩家对应的employeecode
		try {
			//	查找游戏账号对应的员工编码
			EmployeeApiAccout eea = RedisCache.getInstance().getEmployeeApiAccount(enterprisecode, Account, gametype);
			if(eea == null) {
				return Enum_MSG.失败.message("未能根据玩家账号查找到完整的账号信息");
			}
			
			if(eea.getStatus().equals(EmployeeApiAccout.Enum_status.禁用.value)) {
				return Enum_MSG.用户未启用该游戏.message(null);
				//model.addAttribute("message", Enum_MSG.用户未启用该游戏.desc);
				//return "/error/error";
			}
			
			
			employeecode = eea.getEmployeecode();
			
		} catch (Exception e) {
			e.printStackTrace();
			return Enum_MSG.失败.message("查找玩家的账号信息异常："+e.getMessage());
		}
		
		try {
			
			//客户端玩家登录时，给他上下分
			if(OptType != null && OptType.equals("login")) {
				
				APIServiceNew apiServiceNew = new APIServiceNew(enterprisecode);
				//	执行转分（先下分，再上分）、获取登录地址（棋牌没有登录web或H5的地址可用）
				//play方法=String gametype,String playtype,String employeecode,List<Game> games,String devicetype,String gamecode
				
				String result = apiServiceNew.play(gametype, GameBigType.QP, employeecode, null, Enum_deviceType.电脑.code, "", new HashMap<String, String>());
				
				JSONObject object = JSONObject.fromObject(result);
				//操作成功
				if(object.getString("code").equals("0")) {
					log.Error("处理成功，已为用户上分完毕.Account="+Account+",employeecode="+employeecode);
					return Enum_MSG.成功.message("处理成功，已为用户"+Account+"上分完毕");
				} else {
					log.Error("处理失败："+object.getString("info")+".Account="+Account+",employeecode="+employeecode);
					return Enum_MSG.失败.message("处理失败："+object.getString("info"));
				}
				
			} else {
				log.Error("未能识别的触发事件"+OptType);
				return Enum_MSG.失败.message("未能识别的触发事件"+OptType);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return Enum_MSG.失败.message("上分出现异常："+e.getMessage());
		}
		
	}
	
	
	@Override
	public LoggerManager getLogger() {
		return log;
	}

}
